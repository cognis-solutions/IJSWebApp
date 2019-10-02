import { Component, Optional, Inject, ViewChild,ElementRef, Input, ViewEncapsulation, Output, EventEmitter } from '@angular/core';
import { NgModel, NG_VALUE_ACCESSOR, NG_VALIDATORS, NG_ASYNC_VALIDATORS } from '@angular/forms';
import { noop } from 'rxjs/util/noop';
import {NgxPaginationModule} from 'ngx-pagination';
declare var UIkit: any;
declare var jQuery: any;
import { SpinnerServiceService } from "../../common-services/spinner-service.service";
import { LookUpdataServiceService } from '../../common-services/look-updata-service.service';
import { ServerErrorcodeService } from "../../common-services/server-errorcode.service";
import { SortSearchTableService } from '../../rcl-application//contract-search/sort-search-table.service';


import { trigger, state, style, animate, transition, keyframes } from '@angular/animations';
import {NgbModal, ModalDismissReasons, NgbModalRef, NgbModalOptions} from '@ng-bootstrap/ng-bootstrap';
import { SessionTimeOutService } from "app/common-services/session-time-out.service";

@Component({
  selector: 'app-rcl-input-look-up',
  templateUrl: './rcl-input-look-up.component.html',
  styleUrls: ['./rcl-input-look-up.component.scss'],
  host: {
    '(document:click)': 'onClick($event)',
  },
  providers: [
    {
      provide: NG_VALUE_ACCESSOR,
      useExisting: RclInputLookUpComponent,
      multi: true
    }
  ],
  encapsulation: ViewEncapsulation.Emulated
})
export class RclInputLookUpComponent {
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
  @Output() vendorNameChange = new EventEmitter();
  @Output() transportModeList = new EventEmitter();

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
  pc: number = 1;
  

  showlookuptable = true;
  locLookUptableData = [];
  resultsPerPageArr = [1, 2, 3, 4, 5, 6, 7, 8, 9, 10];
  countrySortData = [{ label: 'Country', value: 'countryCode' }, { label: 'Country Name', value: 'countryName', }, { label: 'Status', value: 'status' }]
  vendorSortData = [{ label: 'Vendor', value: 'venodrCode' }, { label: 'Vendor Name', value: 'vendorName', }, { label: 'Vendor Type', value: 'vendorType' }, { label: 'City', value: 'city' }, { label: 'State', value: 'state' }, { label: 'Country', value: 'country' }]
  lookUpSortData = [{ label: 'Ascending', value: 'asnd' }, { label: 'Descending', value: 'dsnd' }]
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
  selectedDropDown: string;
  // Placeholders for the callbacks
  public _onTouchedCallback: (_: any) => void = noop;

  public _onChangeCallback: (_: any) => void = noop;

  public touchedFlag: boolean = false;

  constructor(private _eref: ElementRef, @Optional() @Inject(NG_VALIDATORS) validators: Array<any>,
    @Optional() @Inject(NG_ASYNC_VALIDATORS) asyncValidators: Array<any>, private modalService: NgbModal, private _spinner: SpinnerServiceService, private _lookupData: LookUpdataServiceService, public _serverErrorCode: ServerErrorcodeService, public _sortTable: SortSearchTableService,private _sessionTimeOutService:SessionTimeOutService) {
    jQuery(document).ready(function() {
      jQuery(window).keydown(function(event){
        if(event.keyCode == 13) {
          jQuery(".lookup-container").hide();
          event.preventDefault();
          return false;
        }
      });
    });
  }
  onClick(event) {
    if (!this._eref.nativeElement.contains(event.target)) // or some similar check
    this.active = false;
    //this.transportModeList.emit(); //by RCL
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
    // this.transportModeList.emit();
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
    this.transportModeList.emit();
  }

  //lookup modal
  openLookUpModal(e, popupContant) {
    jQuery('#contry-vendor-lookup-modal-center').detach();
    this.selectedDropDown = this.data[0]['dropDownData'][0]['value']; //to have one value selected by default
    this.vendorWildCard = true; // to make wild card checked by default
     this.countryWildCard = true;  
    this.showlookuptable = true;
    this.locLookUptableData = [];
    this.lookupErrorCodeShow = false;
    this.looUpOrderBy = "asnd";
    //this.resultsPerPage = undefined;
    this.openModal = true;
    setTimeout(function () {
      //your code to be executed after 1 second
      UIkit.modal('#contry-vendor-lookup-modal-center').show();
    }, 100);
  }
  //close look up Modal


  getLocLookUpData(lookupTpye, type, eve, inpuvaluevalue, wildCard) {
 if(lookupTpye == "Vendor") {
  this.lookupSortIn = "venodrCode";
 }
 if(lookupTpye == "Country") {
  this.lookupSortIn = "countryCode";
 }
    this._spinner.showSpinner();
    eve.stopPropagation();
    eve.preventDefault();
    eve.stopImmediatePropagation();
    var backendData = this._lookupData.getDataLookupService(lookupTpye, type, eve, inpuvaluevalue, wildCard);
    backendData.subscribe(
      (data) => {
        if(data == "userSessionExpired"){
          UIkit.modal('#contry-vendor-lookup-modal-center').hide();
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
        this.pc = 1;
        this._spinner.hideSpinner();
      },
      (err) => {
        this._spinner.hideSpinner();
        // A client-side or network error occurred. Handle it accordingly.
        this.lookupErrorCodetext = "Please provide correct search criteria."
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


  vendorName: string;
  selectRowData(e) {
    this.openModal = false;
    this.lookUpvalue = e.target.parentElement.children[1].textContent;

    this.change(this.lookUpvalue);
    if (this.lookupName == 'Vendor Lookup') {
      this.vendorName = e.target.parentElement.children[2].textContent;
      this.vendorNameEmit(this.vendorName);
    }
    UIkit.modal('#contry-vendor-lookup-modal-center').hide();
    //UIkit.modal('.lookup-container').hide();

    this.resetPickDropModal(e);
    jQuery("#inputlookup3").focus(); 
    
  }
  vendorNameEmit(vName) {
    this.vendorNameChange.emit(vName);
  }

  vendorWildCard;
  countryWildCard;
  resetPickDropModal(e) {
    this.locLookUptableData = [];
    this.lookupErrorCodetext = undefined;
    this.lookupErrorCodeShow = false;
    this.showlookuptable = true;
    this.lookupSortIn = undefined;
    this.openModal = false;    
    this.selectedDropDown = undefined;    
    this._value = undefined; 
    this.countryWildCard = false;   
    this.vendorWildCard = false;
    this.resultsPerPage=5;
        //this.selectedDropDown = undefined;
    //this._value = undefined; 
    jQuery('html').removeAttr('class');
    // $('#contry-vendor-lookup-modal-center').remove();  

  }


resetValue(e){
  
}

  
}
