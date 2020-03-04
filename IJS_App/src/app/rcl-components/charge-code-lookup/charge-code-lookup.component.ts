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
import { trigger, state, style, animate, transition,keyframes } from '@angular/animations';
import {NgbModal, ModalDismissReasons, NgbModalRef, NgbModalOptions} from '@ng-bootstrap/ng-bootstrap';
import { SessionTimeOutService } from "app/common-services/session-time-out.service";



@Component({
  selector: 'app-charge-code-lookup',
  templateUrl: './charge-code-lookup.component.html',
  styleUrls: ['./charge-code-lookup.component.scss'],
    providers: [
    {
      provide: NG_VALUE_ACCESSOR,
      useExisting: ChargeCodeLookupComponent,
      multi: true
    }    
  ],
})
export class ChargeCodeLookupComponent  {
  
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

  
  lookUpSortData = [{ label: 'Ascending',value: 'asnd'}, {label: 'Descending',value: 'dsnd'}]
  lookupErrorCodetext: any;
  lookupErrorCodeShow: boolean = false;
  resultsPerPage = 5;
  looUpOrderBy: any;  
  lookupSortIn: any;
  pc: number = 1;
  
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

  constructor( @Optional() @Inject(NG_VALIDATORS) validators: Array<any>,
    @Optional() @Inject(NG_ASYNC_VALIDATORS) asyncValidators: Array<any>, private modalService: NgbModal, private _spinner: SpinnerServiceService, private _lookupData: LookUpdataServiceService, public _serverErrorCode: ServerErrorcodeService, public _sortTable: SortSearchTableService,private _sessionTimeOutService:SessionTimeOutService) {
    
  }

  ngOnInit() {
  }

    change(newValue) {
      this.lookUpvalue = newValue;
      this.lookUpvalueChange.emit(newValue);
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
    this.touchedFlag=true;
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
    for(let i=0; i < this.data[0]['dropDownData'].length; i++) {
      if(selectedvalue == this.data[0]['dropDownData'][i]['label']){
        this.selectedDropDown = this.data[0]['dropDownData'][i]['value'];
      }
    } 
  }
  
    //lookup hide
  onClickOutside(event) {
    if(event && event['value'] === true) {
      this.active = false;
    }
  }
  
    //lookup modal
  openLookUpModal (e, popupContant){    
    this.showlookuptable = true;
    this.locLookUptableData = [];
    this.lookupErrorCodeShow=false;
    this.lookupSortIn = undefined;  
    this.pc = 1;
    
    this.openModal = true;
    setTimeout(function() {
    //your code to be executed after 1 second
    UIkit.modal('#charge-code-lookup-modal-center').show();
    }, 100);    
  }
  //close look up Modal
  
  
   getLocLookUpData(lookupTpye, type, eve, inpuvaluevalue, wildCard) {     
     
    this._spinner.showSpinner();    
    eve.stopPropagation();
    eve.preventDefault();
    eve.stopImmediatePropagation();
    var backendData = this._lookupData.getDataLookupService(lookupTpye, type, eve, inpuvaluevalue, wildCard);
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
    UIkit.modal('#charge-code-lookup-modal-center').hide();
    this.locLookUptableData = [];
    this.lookupErrorCodetext= undefined;
    this.lookupErrorCodeShow=false;
    this.showlookuptable = true;
    this.lookupSortIn = undefined;
    $('html').removeAttr('class');
    $('#charge-code-lookup-modal-center').remove();    
  }
   resetPickDropModal(e) {
    this.openModal = false;
    this.showlookuptable = true;
    this.selectedDropDown = undefined;
    this.locLookUptableData = [];
    this.lookupErrorCodeShow=false;
    this._value = undefined;
    this.lookupSortIn = undefined;   
    this.resultsPerPage = 5;
    $('html').removeAttr('class');
    $('#charge-code-lookup-modal-center').remove();  
    
  }
  

}
