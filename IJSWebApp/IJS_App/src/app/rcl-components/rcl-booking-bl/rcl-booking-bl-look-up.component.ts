import { Component, OnInit, ViewChild, Input, Output, EventEmitter, ViewEncapsulation, Optional, Inject } from '@angular/core';
import { NgModel, NG_VALUE_ACCESSOR, NG_VALIDATORS, NG_ASYNC_VALIDATORS } from '@angular/forms';
import { noop } from 'rxjs/util/noop';
import { SpinnerServiceService } from "../../common-services/spinner-service.service";
import { LookUpdataServiceService } from '../../common-services/look-updata-service.service';
import { ServerErrorcodeService } from "../../common-services/server-errorcode.service";
import { SortSearchTableService } from '../../rcl-application//contract-search/sort-search-table.service';

import { Http, Response, Headers, URLSearchParams, RequestOptions } from '@angular/http';
import { Observable } from 'rxjs/Observable';
import 'rxjs/add/operator/map';
import 'rxjs/add/operator/catch';


import {NgxPaginationModule} from 'ngx-pagination';
import * as $ from 'jquery';
declare var UIkit: any;




import { trigger, state, style, animate, transition, keyframes } from '@angular/animations';
import {NgbModal, ModalDismissReasons, NgbModalRef, NgbModalOptions} from '@ng-bootstrap/ng-bootstrap';
import { SessionTimeOutService } from "app/common-services/session-time-out.service";

@Component({
  selector: 'app-rcl-booking-bl-modal',
  templateUrl: './rcl-booking-bl-look-up.component.html',
  styleUrls: ['./rcl-booking-bl-look-up.component.scss'],
  providers: [
    {
      provide: NG_VALUE_ACCESSOR,
      useExisting: RclBookingBLLookUpComponent,
      multi: true
    }
  ],
  encapsulation: ViewEncapsulation.Emulated
})
export class RclBookingBLLookUpComponent {
  @ViewChild(NgModel) model: NgModel;
  @Input() public label: string;
  @Input() public data: any[];
  @Input() public placeholder: string = "";
  @Input() public helptext: string;
  @Input() public lookupName: string;
  @Input() public klass: string;
  @Input() public compid: String;
  @Input() public lookUpvalue: String;
  @Input() public required: boolean;
  @Input() public disabled: boolean;
  @Input() public fromType: string;
  @Output() lookUpvalueChange = new EventEmitter();
  @Output() refreshJoMaintenanceTable = new EventEmitter(); //#NIIT CR6 >>>>

  
  change(newValue) {
    this.lookUpvalue = newValue;
    this.lookUpvalueChange.emit(newValue);
  }

  private joNumber: string;
  private ngbmodalRef: NgbModalRef;
  private selectedBkgBlList = [];
  private deleteCriteriaList = [];
  previouselement: any;
  public active: boolean = false;
  _value: any = "";
  looUpval: any;
  options: NgbModalOptions = {
    size: 'sm'
  };
  openModal: boolean = false;

  showlookuptable = true;
  locLookUptableData = [];
  lumpSumData = []; //for lump sum
  lookUpTableData = []; //for other data
  resultsPerPageArr = [1, 2, 3, 4, 5, 6, 7, 8, 9, 10];
  countrySortData = [{ label: 'Country', value: 'countryCode' }, { label: 'Country Name', value: 'countryName', }, { label: 'Status', value: 'status' }]
  joLogSortData = [{ label: 'Booking/BL', value: 'BookingBL' }, { label: 'SOCCOC', value: 'SOCCOC', }, { label: 'Status', value: 'Status' }, { label: 'Type', value: 'Type' }
    , { label: 'Service', value: 'service' }, { label: 'Vessel', value: 'vessel' }, { label: 'Voyage', value: 'voyage' }, { label: 'ETA', value: 'ETA' }
    , { label: 'ETD', value: 'ETD' }, { label: 'Size', value: 'Size' }, { label: 'Type', value: 'Type' }, { label: 'TotalContainer', value: 'TotalContainer' }
    , { label: 'Currency', value: 'Currency' }, { label: 'Amount', value: 'Amount' }, { label: 'AmountUSD', value: 'AmountUSD' }]
  lookUpSortData = [{ label: 'Ascending', value: 'asnd' }, { label: 'Descending', value: 'dsnd' }]
  lookupErrorCodetext: any;
  lookupErrorCodeShow: boolean = false;
  resultsPerPage = 5;
  looUpOrderBy: any;
  lookupSortIn: any;
  private seachValueList = [];


  //config variable for first select dropdown
  lookUpConfig: any = {
    highlight: false,
    create: false,
    persist: true,
    plugins: ['dropdown_direction', 'remove_button'],
    dropdownDirection: 'down',
    labelField: 'label',
    valueField: 'value',
    searchField: ['label'],
    maxItems: 1
  };

  //Dropdown select value
  selectedDropDown: string;
  // Placeholders for the callbacks
  public _onTouchedCallback: (_: any) => void = noop;

  public _onChangeCallback: (_: any) => void = noop;

  public touchedFlag: boolean = false;

  private routeListTableData = [];

  constructor( @Optional() @Inject(NG_VALIDATORS) validators: Array<any>,
    @Optional() @Inject(NG_ASYNC_VALIDATORS) asyncValidators: Array<any>, private modalService: NgbModal, private _spinner: SpinnerServiceService, private _lookupData: LookUpdataServiceService, public _serverErrorCode: ServerErrorcodeService, public _sortTable: SortSearchTableService, private _http: Http,private _sessionTimeOutService:SessionTimeOutService) {

  }




  getBackEndData() {
    return this._http.get("/IJSWebApp/assets/jsons/bookingBL.json")
      .map(this.extractData)
      .catch(this.handleErrorObservable);
  }

  private extractData(res: Response) {
    let body = res.json();
    return body;
  }
  private handleErrorObservable(error: Response | any) {
    return Observable.throw(error.status);
  }

  //lookup modal
  // openLookUpModal (e, popupContant){  let routeBackEndData = this.getBackEndData();
  containerType;  
  openLookUpModal(e) {
    
    this.showlookuptable = true;
    this.locLookUptableData = [];
    this.lookupErrorCodeShow = false;
    this.lookupSortIn = "BookingBL";
    this.looUpOrderBy = "asnd";
    this.openModal = true;
    this.joNumber = e.JoNumber;
    this.seachValueList = [];
    this.containerType = e.contDetailJO[0].contType;
    this.selectedBkgBlList = [];
    this.deleteCriteriaList = [];
    let obj = { "key": "bkgOrBLNo", "value": e.JoNumber };
    this.seachValueList.push(obj);
    this.getLocLookUpData('getBkgBlPopUp', '', '', this.seachValueList, '');
    //  UIkit.modal('#booking-bl-lookup-modal-center').show();
    // setTimeout(function () {
    //   //your code to be executed after 1 second
    //   UIkit.modal('#booking-bl-lookup-modal-center').show();
    // }, 100);
  }
  //close look up Modal


  getLocLookUpData(lookupTpye, type, eve, inpuvaluevalue, wildCard) {

    this._spinner.showSpinner();
    var backendData = this._lookupData.getDataLookupServiceJOAll(lookupTpye, type, eve, inpuvaluevalue, wildCard,'','');
    backendData.subscribe(
      (data) => {
        if(data == "userSessionExpired"){
          UIkit.modal('#booking-bl-lookup-modal-center').hide();
          this._sessionTimeOutService.checkSessionTimeout(data);
        }
        else if (data.hasOwnProperty("errorCode")) {
          this.lookupErrorCodetext = this._serverErrorCode.checkError(data["errorCode"]);
          this.lookupErrorCodeShow = true;
          this.showlookuptable = true;
        }
        else {
          this.showlookuptable = false;
          this.lookupErrorCodetext = undefined;
          this.lookupErrorCodeShow = false;
          this.lookUpTableData = data;  
          const lumpSumTableData = this.lookUpTableData.filter(term => term.Size =="**" && term.containerType=="**");       
          const result =  this.lookUpTableData.filter(term => term.Size !="**");
          this.locLookUptableData = result;
          this.lumpSumData = lumpSumTableData; 
          UIkit.modal('#booking-bl-lookup-modal-center').show();
        }
        //binding container type 
        // this.locLookUptableData.forEach((element)=>{
        //     element.containerType= this.containerType;
        // })
        this._spinner.hideSpinner();        
      },
      (err) => {
        this._spinner.hideSpinner();
        // A client-side or network error occurred. Handle it accordingly.
        this.lookupErrorCodetext = "Something Went wrong!! Please Try Again"
        this.lookupErrorCodeShow = true;
      }
    )
  }


  sortLookUpDataByColumn() {
    this._sortTable.sortTableData(this.locLookUptableData, this.lookupSortIn, this.looUpOrderBy);
  }
  sortLookUpDataByOrder() {
    this._sortTable.sortTableData(this.locLookUptableData, this.lookupSortIn, this.looUpOrderBy);
  }


  resetPickDropModal(e) {
    this.openModal = false;
    this.showlookuptable = true;
    this.selectedDropDown = undefined;
    this.locLookUptableData = [];
    this.lookupErrorCodeShow = false;
    this._value = undefined;
    this.lookupSortIn = undefined;
    this.checkedSelectedRows = [];
    $('html').removeAttr('class');
    $('#booking-bl-lookup-modal-center').remove();

  }


  private hideBlBkgPopUp() {
    this.selectedBkgBlList = [];
    this.deleteCriteriaList = [];
    //this.openModal = false;
    UIkit.modal('#booking-bl-lookup-modal-center').hide();
    
  }


  private selectRowForDelete(e, row) {
    if ($(e.target).closest('tr').hasClass('row-selected')) {
      $(e.target).closest('tr').removeClass('row-selected');
      this.selectedBkgBlList = this.deleteObjByBookingBLNumber(this.selectedBkgBlList, 'BookingBL', row.BookingBL);
    } else {
      $(e.target).closest('tr').addClass('row-selected');
      this.selectedBkgBlList.push(row);
    }

  }

  //delete element from array
  private deleteObjByBookingBLNumber(arr, attr, value) {
    let i = arr.length;
    while (i--) {
      if (arr[i]
        && arr[i].hasOwnProperty(attr)
        && (arguments.length > 2 && arr[i][attr] === value)) {
        arr.splice(i, 1);
      }
    }
    return arr;
  }


  private removeBlBkg(e, joNumber, totalRow) {

    if(this.selectedBkgBlList.length==0){
      this.lookupErrorCodetext = "Please select a booking for deletion."
      this.lookupErrorCodeShow = true;

    }else if (this.selectedBkgBlList.length < totalRow.length) {
      this.joNumber = joNumber;
      let i = this.selectedBkgBlList.length;
      for (let a = 0; a < this.selectedBkgBlList.length; a++) {
        let obj = {
          "BookingBL": this.selectedBkgBlList[a].BookingBL,
          "headerId": this.selectedBkgBlList[a].headerId,
          "detailId": this.selectedBkgBlList[a].detailId,
          "Size": this.selectedBkgBlList[a].Size,
          "specialHandling": this.selectedBkgBlList[a].specialHandling,
          "containerType": this.selectedBkgBlList[a].containerType
        }
        this.deleteCriteriaList.push(obj);
      }
      this._spinner.showSpinner();
      var backendData = this._lookupData.getDataLookupServiceJOAll('delBkgBl', joNumber, '', this.deleteCriteriaList, '','','');
      backendData.subscribe(
        (data) => {
          //if (data.hasOwnProperty("errorCode")) {
          // if (this._serverErrorCode.checkError(data["errorCode"])=='IJS_MSG_1022') {
          if(data == "userSessionExpired"){
            this._sessionTimeOutService.checkSessionTimeout(data);
          }  
          else if (data["errorCode"] == 'IJS_MSG_1022') {
            this.lookupErrorCodetext = this._serverErrorCode.checkError(data["errorCode"]);
            this.getLocLookUpData('getBkgBlPopUp', '', '', this.seachValueList, '');
            this.showlookuptable = true;
            this.locLookUptableData = data;
            this.lookupErrorCodeShow = true;
          }
          else {
            this.lookupErrorCodetext = this._serverErrorCode.checkError(data["errorCode"]);
            this.lookupErrorCodeShow = true;
            this.showlookuptable = true;
          }
          //this.selectedBkgBlList=[];
          this._spinner.hideSpinner();
        },
        (err) => {
          this._spinner.hideSpinner();
          // A client-side or network error occurred. Handle it accordingly.
          this.lookupErrorCodetext = "Something Went wrong!! Please Try Again"
          this.lookupErrorCodeShow = true;
        }
      )
    }
    else {
      this.lookupErrorCodetext = "All bookings cannot be removed"
      this.lookupErrorCodeShow = true;
    }
  }

  elementsToDel:any = [];
  checkSelectedRow(e,jobOrderRowData, row, id){
    if (e.target.checked) {      
      this.elementsToDel.push(row);
    } else {
      if (this.elementsToDel) {
        let x = this.elementsToDel.length
        while (x--) {
          if (this.elementsToDel[x] == id) {
            this.elementsToDel.splice(x, 1);
          }
        }
      }
    }
  }


  //#NIIT CR6 >>>> BEGIN
  checkedSelectedRows:any = [];
  //method to select lump sum records to delete
  lumpSumCheckBoxSelected(e,rowObj){
    if (e.target.checked) {
      this.checkedSelectedRows.push(rowObj);
      //this.deleteLumpSumRecords();
      
    } else {
      this.checkedSelectedRows = this.deleteObjByLumpSumID(this.checkedSelectedRows, 'costId', rowObj.costId);
    }
  }

  //method to delete lump sum records based on the attribute and a value
  deleteObjByLumpSumID(arr, attr, value){
    var i = arr.length;
    while (i--) {
      if (arr[i]
        && arr[i].hasOwnProperty(attr)
        && (arguments.length > 2 && arr[i][attr] === value)) {
        arr.splice(i, 1);
      }
    }
    return arr;
  }

  //method to ask user whether to delete lump sum records or not
  deleteLumpSum(){
    $('#delete-lookup-lumpsum-modal').addClass('uk-open').show(); //#NIIT CR6 >>>>
  }

  lumpSumDelText:any = '';
  //method to delete lump sum records after the user confirmation
  deleteLumpSumConfirm(){
    var lumpSumDelObj = this.deleteLumpSumRecords();
    
    let deleteLumpSumObject = { joLumpsumIds: lumpSumDelObj, action: "delBookingBlLumpsum" };
    this._spinner.showSpinner();
    let backendData = this._lookupData.deleteBookingBlLumpSum(deleteLumpSumObject);
    backendData.subscribe(
      (data) => {
        if(data == "userSessionExpired"){
          this._sessionTimeOutService.checkSessionTimeout(data);
        }
        else if (!data['errorCode'].includes("MSG")) {
          this._spinner.hideSpinner();          
        }
        else {
          this.lumpSumDelText = this._serverErrorCode.checkError(data["errorCode"]);
          UIkit.modal('#booking-bl-lookup-modal-center').hide();           
          this._spinner.hideSpinner();
          this.getSuccessMsg();
        }        
      }
    )
  }
  
  //method to show user that lump sum records have been successfully deleted
  getSuccessMsg(){
    $('#delete-lumpsum-success-modal').addClass('uk-open').show(); //show success
  }

  //method to refresh the bkg/bl lookup after the lump sum records have been successfully deleted
  refreshLookupModal(){
    $('#delete-lumpsum-success-modal').addClass('uk-open').hide(); //show success
    this.refreshJoMaintenanceTable.emit('refreshTable'); //to refresh the jo maintenance table after deleting lumpsum from bkgbl popup
    this.getLocLookUpData('getBkgBlPopUp', '', '', this.seachValueList, '');
  }

  lumpSumID:any = [];
  //method to create json format for lump sum records with job order and lump sum ids
  deleteLumpSumRecords(){

    var joNumber = this.joNumber;
    var lumpSumObjJoMaintenance = [];

     const filteredJoSet = new Set();
     this.checkedSelectedRows.forEach((element)=>{
            if(!(filteredJoSet.has(element.costId))){
                filteredJoSet.add(element.costId)
                }
     });             
    
     this.lumpSumID = Array.from(filteredJoSet).toString();
     lumpSumObjJoMaintenance.push(joNumber + ":"+ this.lumpSumID); 
     return lumpSumObjJoMaintenance;
  } 
  //#NIIT CR6 >>>> BEGIN

}
