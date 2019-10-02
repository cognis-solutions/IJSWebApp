import { Component, Optional, Inject, ViewChild, Input, ViewEncapsulation, Output, EventEmitter } from '@angular/core';
import { NgModel, NG_VALUE_ACCESSOR, NG_VALIDATORS, NG_ASYNC_VALIDATORS } from '@angular/forms';
import { noop } from 'rxjs/util/noop';
import {NgxPaginationModule} from 'ngx-pagination';
import * as $ from 'jquery';
declare var UIkit: any;
import { SpinnerServiceService } from "../../common-services/spinner-service.service";
import { LookUpdataServiceService } from '../../common-services/look-updata-service.service';
import { ServerErrorcodeService } from "../../common-services/server-errorcode.service";
import { SortSearchTableService } from '../../rcl-application//contract-search/sort-search-table.service';
import { trigger, state, style, animate, transition, keyframes } from '@angular/animations';
import {NgbModal, ModalDismissReasons, NgbModalRef, NgbModalOptions} from '@ng-bootstrap/ng-bootstrap';
import { SessionTimeOutService } from "app/common-services/session-time-out.service";

@Component({
  selector: 'app-service-vessel-voyage-lookup',
  templateUrl: './service-vessel-voyage-lookup.component.html',
  styleUrls: ['./service-vessel-voyage-lookup.component.scss'],
  providers: [
    {
      provide: NG_VALUE_ACCESSOR,
      useExisting: ServiceVesselVoyageLookupComponent,
      multi: true
    }
  ]
})
export class ServiceVesselVoyageLookupComponent {


  @ViewChild(NgModel) model: NgModel;
  @Input() public label: string; selectVal
  @Input() public data: any[];
  @Input() public placeholder: string = "";
  @Input() public helptext: string;
  @Input() public lookupName: string;
  @Input() public klass: string;
  @Input() public compid: String;
  @Input() public valToSelect: String;
  @Input() public lookUpvalue: String;
  @Input() public required: boolean;
  @Input() public disabled: boolean;
  @Output() lookUpvalueChange = new EventEmitter();
  @Output() svvValueChange = new EventEmitter();  


  private ngbmodalRef: NgbModalRef;
  previouselement: any;
  public active: boolean = false;
  _value: string;
  looUpval: any;
  options: NgbModalOptions = {
    size: 'sm'
  };
  totalRecords: number = 0;
  private openModal: boolean = false;
  private showlookuptable = true;
  private locLookUptableData = [];
  private resultsPerPageArr = [1, 2, 3, 4, 5, 6, 7, 8, 9, 10];

  private lookUpSortData = [{ label: 'Ascending', value: 'asc' }, { label: 'Descending', value: 'desc' }]
  private lookupErrorCodetext: any;
  private lookupErrorCodeShow: boolean = false;
  resultsPerPage:number = 5;
  looUpOrderBy: any;
  lookupSortIn: any;
  pc: number = 1; 
  WildCard: boolean;
  private svvSortData = [{ label: 'Service', value: 'service' }, { label: 'Vessel', value: 'vessel', }, { label: 'Voyage', value: 'voyage' }, { label: 'Direction', value: 'direction' }]

  //config variable for first select dropdown
  private lookUpConfig: any = {
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

  constructor( @Optional() @Inject(NG_VALIDATORS) validators: Array<any>,
    @Optional() @Inject(NG_ASYNC_VALIDATORS) asyncValidators: Array<any>, private modalService: NgbModal, private _spinner: SpinnerServiceService, private _lookupData: LookUpdataServiceService, public _serverErrorCode: ServerErrorcodeService, public _sortTable: SortSearchTableService,private _sessionTimeOutService:SessionTimeOutService) {

  }
 
  change(newValue,placeholder) {
    this.svvDataObject.placeholder = placeholder;    
    if(placeholder == "Service"){
      this.svvDataObject.serviceValue = newValue;     
    }else if(placeholder == "Vessel"){
      this.svvDataObject.vesselValue = newValue;       
    }else if(placeholder == "Voyage"){
      this.svvDataObject.voyageValue = newValue;      
    }     
    if(placeholder != undefined && placeholder != ''){
     this.svvValueChange.emit(this.svvDataObject);   
    } else{
      this.lookUpvalue = newValue;
      this.lookUpvalueChange.emit(newValue);
    }    
  }

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

  openLookUpModal(e, popupContant) {
    
    this.showlookuptable = true;
    this.locLookUptableData = [];
    this.lookupErrorCodeShow = false;
    this.lookupSortIn = "service";
    this.WildCard = true; //to make wild card default checked
    //for default lookup value according to service/vessel/voyage  
    if (this.valToSelect == "service"){
      this.selectedDropDown = this.data[0]['dropDownData'][0]['value'];
    } else if(this.valToSelect == "vessel"){
      this.selectedDropDown = this.data[0]['dropDownData'][1]['value'];
    } else if(this.valToSelect == "voyage"){
      this.selectedDropDown = this.data[0]['dropDownData'][2]['value'];
    }
    this.looUpOrderBy = "asc";
    this.openModal = true;
    setTimeout(function () {
      //your code to be executed after 1 second
      UIkit.modal('#svv-lookup-modal-center').show();
    }, 100);
  }
  getLocLookUpData(lookupTpye, type, eve, inpuvaluevalue, wildCard, pageNo, requestChanged, lookupSortIn, looUpOrderBy,nopInSvv) {
    //this.lookupSortIn = "service";
    this._spinner.showSpinner();    
    // eve.stopPropagation();
    // eve.preventDefault();
    // eve.stopImmediatePropagation();

    //TODO : revert afterwards
    let backendData = this._lookupData.getPagingDataLookupService(lookupTpye, type, eve, inpuvaluevalue, wildCard, pageNo, requestChanged, lookupSortIn, looUpOrderBy,this.nopInSvv);
    //let backendData = this._lookupData.getPagingDataLookupServiceSVV(lookupTpye, type, eve, inpuvaluevalue, wildCard);
    backendData.subscribe(
      (data) => {
        if(data == "userSessionExpired"){
          UIkit.modal('#svv-lookup-modal-center').hide();
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
          this.locLookUptableData = data['lookupSearchResult']['result'];
          this.totalRecords = data['totalRecords'];
          this.pc = pageNo;
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
  
  
  sortLookUpDataByColumn() {
    this._sortTable.sortTableData(this.locLookUptableData, this.lookupSortIn, this.looUpOrderBy);
  }
  sortLookUpDataByOrder() {
    this._sortTable.sortTableData(this.locLookUptableData, this.lookupSortIn, this.looUpOrderBy);
  }


  svvDataObject = {   
      serviceValue: "",
      vesselValue: "",
      voyageValue: "",
      placeholder: ""       
  };

  private selectRowData(e) {
    this.openModal = false;
    this.svvDataObject.serviceValue = e.target.parentElement.children[1].textContent;  
    this.svvDataObject.vesselValue = e.target.parentElement.children[2].textContent;
    this.svvDataObject.voyageValue = e.target.parentElement.children[3].textContent; 
    this.change(this.svvDataObject,''); 
    // if(this.valToSelect == "service") {
    //   this.lookUpvalue = e.target.parentElement.children[1].textContent;
    //   const lookUpvalue2 = e.target.parentElement.children[2].textContent;
    //   const lookUpvalue3 = e.target.parentElement.children[3].textContent;
    //   this.change(this.lookUpvalue);
    // } else if (this.valToSelect == "vessel") {
    //   this.lookUpvalue = e.target.parentElement.children[2].textContent;
    //   this.change(this.lookUpvalue);
    // } else if (this.valToSelect == "voyage") {
    //   this.lookUpvalue = e.target.parentElement.children[3].textContent;
    //   this.change(this.lookUpvalue);
    // } else if (this.valToSelect == "direction") {
    //   this.lookUpvalue = e.target.parentElement.children[4].textContent;
    //   this.change(this.lookUpvalue);
    // }    
    
    UIkit.modal('#svv-lookup-modal-center').hide();
    this._value = undefined;
    this.resultsPerPage = 5;
    this.locLookUptableData = [];
    this.lookupErrorCodetext = undefined;
    this.lookupErrorCodeShow = false;
    this.showlookuptable = true;
    this.lookupSortIn = undefined;
    $('html').removeAttr('class');
    $('#svv-lookup-modal-center').remove();
  }
  resetPickDropModal(e) {
    this.openModal = false;
    this.showlookuptable = true;
    this.selectedDropDown = undefined;
    this.locLookUptableData = [];
    this.lookupErrorCodeShow = false;
    this._value = undefined;
    this.lookupSortIn = undefined;
    this.looUpOrderBy = "asc";
    this.resultsPerPage = 5;
    // $('html').removeAttr('class');
    // $('#svv-lookup-modal-center').remove();
    // this.pc = 1;

  }

  ngOnInit() {
  }

  nopInSvv = this.resultsPerPage; //variable to show no of records in current page
    onChangingSvvPageNumber(e){
    this.nopInSvv = e.target.value;    
    this.getLocLookUpData(this.data[0].value, this.selectedDropDown,'', this._value, this.WildCard, this.pc, true, this.lookupSortIn, this.looUpOrderBy,this.nopInSvv);    
  }

}
