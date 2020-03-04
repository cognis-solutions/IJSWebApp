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




import { trigger, state, style, animate, transition,keyframes } from '@angular/animations';
import {NgbModal, ModalDismissReasons, NgbModalRef, NgbModalOptions} from '@ng-bootstrap/ng-bootstrap';
import { SessionTimeOutService } from "app/common-services/session-time-out.service";

@Component({
  selector: 'app-rcl-jo-log-modal',
  templateUrl: './rcl-jo-log-look-up.component.html',
  styleUrls: ['./rcl-jo-log-look-up.component.scss'],
  providers: [
    {
      provide: NG_VALUE_ACCESSOR,
      useExisting: RclJOLogLookUpComponent,
      multi: true
    }    
  ],
  encapsulation: ViewEncapsulation.Emulated
})
export class RclJOLogLookUpComponent {
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
  @Output() lookUpvalueChange = new EventEmitter();

  
  change(newValue) {
      this.lookUpvalue = newValue;
      this.lookUpvalueChange.emit(newValue);
    }
  
  private joNumber: string;
  private ngbmodalRef: NgbModalRef;
  previouselement: any;
  public active:boolean = false;
  _value:any = "";
  looUpval: any;
  options: NgbModalOptions = {
  size: 'sm'
  };
  openModal: boolean = false; 
  
  showlookuptable = true;
  locLookUptableData = [];
  resultsPerPageArr = [1,2,3,4,5,6,7,8,9,10];
  countrySortData = [{ label: 'Country',value: 'countryCode'}, {label: 'Country Name',value: 'countryName',}, {label: 'Status', value: 'status'}]
  joLogSortData = [{ label: 'SR',value: 'SR'}, {label: 'Activity',value: 'Activity',}, {label: 'Date', value: 'Date'}, {label: 'User', value: 'User'}]
  lookUpSortData = [{ label: 'Ascending',value: 'asnd'}, {label: 'Descending',value: 'dsnd'}]
  lookupErrorCodetext: any;
  lookupErrorCodeShow: boolean = false;
  resultsPerPage = 5;
  looUpOrderBy: any;  
  lookupSortIn: any;


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
  selectedDropDown: string ;
  // Placeholders for the callbacks
  public _onTouchedCallback: (_: any) => void = noop;

  public _onChangeCallback: (_: any) => void = noop;

  public touchedFlag: boolean = false;

  private routeListTableData = [];  

  constructor( @Optional() @Inject(NG_VALIDATORS) validators: Array<any>,
    @Optional() @Inject(NG_ASYNC_VALIDATORS) asyncValidators: Array<any>, private modalService: NgbModal, private _spinner: SpinnerServiceService, private _lookupData: LookUpdataServiceService, public _serverErrorCode: ServerErrorcodeService, public _sortTable: SortSearchTableService, private _http: Http,private _sessionTimeOutService:SessionTimeOutService) {
    
  }




  getBackEndData() {
    return this._http.get("/IJSWebApp/assets/jsons/jolog.json")
      .map(this.extractData)
      .catch(this.handleErrorObservable);
  }  

  private extractData(res: Response) {
    let body = res.json();
    return body;
  }
  private handleErrorObservable(error: Response | any) {
    console.log(error.message || error.status);
    return Observable.throw(error.status);
  }  
  
  //lookup modal
 // openLookUpModal (e, popupContant){  let routeBackEndData = this.getBackEndData();
  openLookUpModal (row){       
    this.showlookuptable = true;
    this.locLookUptableData = [];
    this.lookupErrorCodeShow=false;
    this.lookupSortIn = 'SR';  
    this.looUpOrderBy = 'asnd';
    //let routeBackEndData = this.getBackEndData();
    this.openModal = true;
    this.joNumber = row.JoNumber;
    this.getLocLookUpData('getJOLog','','',row.JoNumber,'');
    
    setTimeout(function() {
    //your code to be executed after 1 second
    UIkit.modal('#jo-log-lookup-modal-center').show();
    }, 100);    
  }
  //close look up Modal

  getLocLookUpData(lookupTpye, type, eve, inpuvaluevalue, wildCard) {     
    
   this._spinner.showSpinner();    
   var backendData = this._lookupData.getDataLookupServiceJOAll(lookupTpye, type, eve, inpuvaluevalue, wildCard,'','','','','');
   backendData.subscribe(
     (data) => { 
        if(data == "userSessionExpired"){
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
           this.locLookUptableData = data;
         }
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

  sortLookUpDataByColumn () {
    this._sortTable.sortTableData(this.locLookUptableData, this.lookupSortIn, this.looUpOrderBy);
  }
  sortLookUpDataByOrder () {
    this._sortTable.sortTableData(this.locLookUptableData, this.lookupSortIn, this.looUpOrderBy);
  }


  selectRowData (e) {    
    this.openModal = false;
    this.lookUpvalue = e.target.parentElement.children[1].textContent;
    this.change(this.lookUpvalue);    
    UIkit.modal('#contry-vendor-lookup-modal-center').hide();
    this.locLookUptableData = [];
    this.lookupErrorCodetext= undefined;
    this.lookupErrorCodeShow=false;
    this.showlookuptable = true;
    this.lookupSortIn = undefined;
    $('html').removeAttr('class');
    $('#contry-vendor-lookup-modal-center').remove();    
  }
   resetPickDropModal(e) {
    this.openModal = false;
    this.showlookuptable = true;
    this.selectedDropDown = undefined;
    this.locLookUptableData = [];
    this.lookupErrorCodeShow=false;
    this._value = undefined;
    this.lookupSortIn = undefined; 
    this.looUpOrderBy = undefined;  
    $('html').removeAttr('class');
    $('#contry-vendor-lookup-modal-center').remove();  
    
  }  
  
}
