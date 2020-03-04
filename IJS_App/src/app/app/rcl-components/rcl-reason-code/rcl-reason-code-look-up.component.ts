import { Component, Optional, Inject, ViewChild, Input, ViewEncapsulation, Output, EventEmitter } from '@angular/core';
import { NgModel, NG_VALUE_ACCESSOR, NG_VALIDATORS, NG_ASYNC_VALIDATORS } from '@angular/forms';
import { noop } from 'rxjs/util/noop';
import { NgxPaginationModule } from 'ngx-pagination';
import * as $ from 'jquery';
declare var UIkit: any;
import { SpinnerServiceService } from "../../common-services/spinner-service.service";
import { LookUpdataServiceService } from '../../common-services/look-updata-service.service';
import { ServerErrorcodeService } from "../../common-services/server-errorcode.service";
import { SortSearchTableService } from '../../rcl-application//contract-search/sort-search-table.service';
import { Http, Response, Headers, URLSearchParams, RequestOptions } from '@angular/http';
import { Observable } from 'rxjs/Observable';
import { trigger, state, style, animate, transition, keyframes } from '@angular/animations';
import { NgbModal, ModalDismissReasons, NgbModalRef, NgbModalOptions } from '@ng-bootstrap/ng-bootstrap';
import { SessionTimeOutService } from "app/common-services/session-time-out.service";

@Component({
  selector: 'app-rcl-rsn-cd-modal',
  templateUrl: './rcl-reason-code-look-up.component.html',
  styleUrls: ['./rcl-reason-code-look-up.component.scss'],
  providers: [
    {
      provide: NG_VALUE_ACCESSOR,
      useExisting: RclRsnCdLookUpComponent,
      multi: true
    }
  ],
  encapsulation: ViewEncapsulation.Emulated
})
export class RclRsnCdLookUpComponent {
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


  private ngbmodalRef: NgbModalRef;
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
  resultsPerPageArr = [1, 2, 3, 4, 5, 6, 7, 8, 9, 10];
  countrySortData = [{ label: 'Country', value: 'countryCode' }, { label: 'Country Name', value: 'countryName', }, { label: 'Status', value: 'status' }]
  vendorSortData = [{ label: 'Reason Code', value: 'ReasonCode' }, { label: 'Reason Description', value: 'Description' }]
  lookUpSortData = [{ label: 'Ascending', value: 'asnd' }, { label: 'Descending', value: 'dsnd' }]
  rsnCodeLookUpData: any = [{ "label": "Reason Code", "value": "Reason_Code", "dropDownData": [{ "label": "Reason Code", "value": "reason_code" }, { "label": "Reason Description", "value": "Description" }, { "label": "Status", "value": "Status" }] }, { "label": "Reason Description", "value": "Description" }];
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
  WildCard:boolean; //wild card checked variable
  //Dropdown select value
  selectedDropDown: string;
  // Placeholders for the callbacks
  public _onTouchedCallback: (_: any) => void = noop;

  public _onChangeCallback: (_: any) => void = noop;

  public touchedFlag: boolean = false;

  constructor( @Optional() @Inject(NG_VALIDATORS) validators: Array<any>,
    @Optional() @Inject(NG_ASYNC_VALIDATORS) asyncValidators: Array<any>, private modalService: NgbModal, private _spinner: SpinnerServiceService, private _lookupData: LookUpdataServiceService, public _serverErrorCode: ServerErrorcodeService, public _sortTable: SortSearchTableService, private _http: Http,private _sessionTimeOutService:SessionTimeOutService) {

  }


  //Set touched on blur


  writeValue(value: any) {
    this.lookUpvalue = value;
  }

  propagateChange = (_: any) => { };
  registerOnChange(fn) {
    this.propagateChange = fn;
  }
  // From ControlValueAccessor interface
  registerOnTouched(fn: any) {
    this._onTouchedCallback = fn;
  }

  //Set touched on blur
  onTouched() {
    this._onTouchedCallback(null);
    this.touchedFlag = true;
  }


  //lookup inupts show hide
  onLookupShowHide(e, selectedvalue) {
    if (this.previouselement != undefined && this.previouselement != e.target.previousElementSibling) {
      this.previouselement.previousElementSibling.setAttribute("hidden", "hidden");
      this.previouselement.removeAttribute("hidden");
    }
    this.previouselement = e.target;
    e.target.setAttribute("hidden", "hidden");
    e.target.previousElementSibling.removeAttribute("hidden");
    for (let i = 0; i < this.data[0]['dropDownData'].length; i++) {
      if (selectedvalue == this.data[0]['dropDownData'][i]['label']) {
        this.selectedDropDown = this.data[0]['dropDownData'][i]['value'];
      }
    }
  }

  //lookup hide
  onClickOutside(event) {
    if (event && event['value'] === true) {
      this.active = false;
    }
  }

  //lookup modal
  openLookUpModal() {
    this._value = undefined;
    this.showlookuptable = true;
    this.locLookUptableData = [];
    this.lookupErrorCodeShow = false;
    this.lookupSortIn = 'ReasonCode'; //default sort by value
    this.looUpOrderBy = 'asnd'; //default order by value
    this.selectedDropDown = this.rsnCodeLookUpData[0]['value'];    
    this.WildCard = true; //to mark wild card as checked by default
    this.openModal = true;
    setTimeout(function () {
      //your code to be executed after 1 second
      UIkit.modal('#reason-code-lookup-modal-center').show();
    }, 100);
  }
  //close look up Modal


  getBackEndData() {
    return this._http.get("/IJSWebApp/assets/jsons/reasoncode.json")
      .map(this.extractData)
      .catch(this.handleErrorObservable);
  }

  private extractData(res: Response) {
    let body = res.json();
    return body;
  }
  private handleErrorObservable(error: Response | any) {
    return Observable.throw(error);
  }


  getRsnLookUpData(eve, findfror, inpuvaluevalue, wildCard) {

    this._spinner.showSpinner();
    eve.stopPropagation();
    eve.preventDefault();
    eve.stopImmediatePropagation();
    var backendData = this._lookupData.getDataLookupServiceJOAll('getReasonCd', findfror, eve, inpuvaluevalue, wildCard,'','','','','');
    backendData.subscribe(
      (data) => {
        if(data == "userSessionExpired"){
          UIkit.modal('#reason-code-lookup-modal-center').hide();
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
      }
    )    
  }

  sortLookUpDataByColumn() {
    this._sortTable.sortTableData(this.locLookUptableData, this.lookupSortIn, this.looUpOrderBy);
  }
  sortLookUpDataByOrder() {
    this._sortTable.sortTableData(this.locLookUptableData, this.lookupSortIn, this.looUpOrderBy);
  }




  selectRowData(e) {
    this.openModal = false;
    this.lookUpvalue = e.target.parentElement.children[1].textContent;
    this.change(this.lookUpvalue);
    this.locLookUptableData = [];
    this.lookupErrorCodetext = undefined;
    this.lookupErrorCodeShow = false;
    this.showlookuptable = true;
    this.lookupSortIn = undefined;
    $('html').removeAttr('class');
    //$('#contry-vendor-lookup-modal-center').remove();    
  }
  resetPickDropModal(e) {
    this.openModal = false;
    this.showlookuptable = true;
    this.selectedDropDown = undefined;
    this.locLookUptableData = [];
    this.lookupErrorCodeShow = false;
    this._value = undefined;
    this.lookupSortIn = undefined;
    $('html').removeAttr('class');
    this.resultsPerPage = 5;
    this.looUpOrderBy = undefined; //to reset the order by value
    // $('#contry-vendor-lookup-modal-center').remove();
    $('#delete-lumpsum-modal').parent().parent().css({'overflow':'visible','top':'50px','overflow-y':'auto'}); 
    // $('#add-empty-equipment-setup-modal').parent().parent().parent().css({'overflow':'visible','top':'50px','overflow-y':'auto'});         
    // $('#add-empty-equipment-setup-modal').addClass('uk-open').hide(); 
  }


  hideReasonCode(e) {   
    this.openModal = false;
    UIkit.modal('#reason-code-lookup-modal-center').hide();
    $('#delete-lumpsum-modal').parent().parent().css({'overflow':'visible','top':'50px','overflow-y':'auto'}); 
    //$('#add-empty-equipment-setup-modal').parent().parent().parent().css({'overflow':'visible','top':'50px','overflow-y':'auto'});         
    //$('#add-empty-equipment-setup-modal').addClass('uk-open').hide(); 
  }
  @Output() selectUpdateReasonCodeList = new EventEmitter();
  private selectReasonCode(e) {
     if(this.checkedRow == undefined){
       //show error
       this.lookupErrorCodeShow = true;
       this.lookupErrorCodetext = "Please select a reason code to create job order.";
     }
     else
      {
        UIkit.modal('#reason-code-lookup-modal-center').hide();
        this.selectUpdateReasonCodeList.emit(this.checkedRow);   
        this.checkedRow = undefined;
      }
  }

  @Output() saveReasonCodeList = new EventEmitter();
  private saveReasonCode(e) {
        let reasonCod: string = "ADTRUC";
        this.saveReasonCodeList.emit(reasonCod);   
        
      
  }

  checkedRow: any;
  private selectTableRowCheckBoxes(e, row) {
    if (e.target.checked) {
      $(".tableCheckBox input").prop('checked', false);
      $(e.target).prop('checked', true);
      this.checkedRow = row;
      this.lookupErrorCodeShow = false; //to hide error
      this.lookupErrorCodetext = undefined; 
    }
    else{
      this.checkedRow = undefined;
    }
  }
}
