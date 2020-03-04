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
import { Http, Response, Headers, URLSearchParams, RequestOptions } from '@angular/http';

import { trigger, state, style, animate, transition,keyframes } from '@angular/animations';
import {NgbModal, ModalDismissReasons, NgbModalRef, NgbModalOptions} from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Observable';
import { SessionTimeOutService } from "app/common-services/session-time-out.service";

declare var jQuery: any;
declare var UIkit: any;
@Component({
  selector: 'app-vessel-lookup',
  templateUrl: './vessel-lookup.component.html',
  styleUrls: ['./vessel-lookup.component.scss'],
    providers: [
    {
      provide: NG_VALUE_ACCESSOR,
      useExisting: VesselLookupComponent,
      multi: true
    }    
  ],
  encapsulation: ViewEncapsulation.Emulated
})
export class VesselLookupComponent {
  
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
  @Input() public readOnly: boolean; 
  @Output() lookUpvalueChange = new EventEmitter();
  
    change(newValue) {
      this.lookUpvalue = newValue;
      this.lookUpvalueChange.emit(newValue);
    }
  

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
  
  vesselSortData = [{label: 'Vessel Code',value: 'vesselCode'}, {label: 'Vessel Name', value: 'vesselName'}, {label: 'Operator Code', value: 'operatorCode'}]
  lookUpSortData = [{ label: 'Ascending',value: 'asnd'}, {label: 'Descending',value: 'dsnd'}]
  lookupErrorCodetext: any;
  lookupErrorCodeShow: boolean = false;
  resultsPerPage = 5;
  looUpOrderBy: any;  
  lookupSortIn: any;
  private selectedRow: any = [];
  vendorWildCard:boolean;
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
    @Optional() @Inject(NG_ASYNC_VALIDATORS) asyncValidators: Array<any>, private modalService: NgbModal, private _spinner: SpinnerServiceService, private _lookupData: LookUpdataServiceService, public _serverErrorCode: ServerErrorcodeService, public _sortTable: SortSearchTableService, private _http: Http,private _sessionTimeOutService:SessionTimeOutService) {
      jQuery(document).ready(function(){
        
         jQuery('.lookup-wrapper').on('click', function(e){
        
         var x = e.pageX-40 ; //to manage lookups on scrolling
           
   jQuery(".table-container").css({"overflow":"hidden"});
   jQuery(".lookup-container").css("left", x +'px'); //to manage lookups on scrolling
 
   e.stopPropagation();
   $("html, body").animate({ scrollTop: $(document).height() }, 1000);
   return false;
 
         });
      
   })
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
      jQuery(".table-container").css("overflow-x", "scroll");
    }
    
   
  }

  private extractData(res: Response) {
    let body = res.json();
    return body;
  }
  private handleErrorObservable(error: Response | any) {
    return Observable.throw(error);
  }  
  
  //lookup modal
  openLookUpModal (e, popupContant){  
    $('#vessel-lookup-modal-center').detach();   
    this.showlookuptable = true;
    this.locLookUptableData = [];
    this.selectedRow = [];
    this.lookupErrorCodeShow=false;     
   //this.resultsPerPage= undefined;
    this.lookupSortIn = 'vesselCode';
    this.looUpOrderBy = 'asnd';
    
    this.openModal = true;
    this.vendorWildCard= true;
    this.selectedDropDown = this.data[0]['dropDownData'][0]['value'];
    setTimeout(function() {
    //your code to be executed after 1 second
    UIkit.modal('#vessel-lookup-modal-center').show();
    }, 100);    
  }
  //close look up Modal
  
    //select Check box    
  private getRowData(e, rowData, i) {
    if (e.target.checked == true) {
      this.selectedRow.push(rowData);
    } else {
      this.selectedRow = this.deleteObjByVesselCode(this.selectedRow, 'vesselCode', rowData.vesselCode);
    }
  }

  //delete element from array
  private deleteObjByVesselCode(arr, attr, value) {
    var i = arr.length;
    while (i--) {
      if (arr[i] && arr[i].hasOwnProperty(attr) && arr[i][attr] === value) {
        arr.splice(i, 1);
      }
    }
    return arr;
  }
  
   getLocLookUpData(lookupTpye, type, eve, inpuvalue, wildCard) {    
     this.lookupSortIn = "vesselCode";
    this._spinner.showSpinner();    
    eve.stopPropagation();
    eve.preventDefault();
    eve.stopImmediatePropagation();
    var backendData = this._lookupData.getDataLookupService(lookupTpye, type, eve, inpuvalue, wildCard);
    backendData.subscribe(
      (data) => {   
        if(data == "userSessionExpired"){
          UIkit.modal('#vessel-lookup-modal-center').hide();
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
      }
    ) 
   }
  
  sortLookUpDataByColumn () {
    this._sortTable.sortTableData(this.locLookUptableData, this.lookupSortIn, this.looUpOrderBy);
  }
  sortLookUpDataByOrder () {
    this._sortTable.sortTableData(this.locLookUptableData, this.lookupSortIn, this.looUpOrderBy);
  }
  
  updateRoute (e) {  
    this._value = undefined;  
    this.openModal = false;
    this.lookUpvalue = "";
    this.resultsPerPage = 5;
    this.selectedRow.forEach(element => {
      this.lookUpvalue += element.vesselCode + ',';
    });
    this.lookUpvalue = this.lookUpvalue.substring(0, this.lookUpvalue.length - 1); 
    this.change(this.lookUpvalue);    
    UIkit.modal('#vessel-lookup-modal-center').hide();
    this.locLookUptableData = [];
    this.lookupErrorCodetext= undefined;
    this.lookupErrorCodeShow=false;
    this.showlookuptable = true;
    this.lookupSortIn = undefined;
    $('html').removeAttr('class');
    //$('#vessel-lookup-modal-center').remove();    
  }
  

  
  
   resetPickDropModal(e) {
    this.openModal = false;
    this.showlookuptable = true;
    this.selectedDropDown = undefined;
    this.locLookUptableData = [];
    this.selectedRow = [];
    this.lookupErrorCodeShow=false;
    this._value = undefined;
    this.lookupSortIn = undefined;   
    this.resultsPerPage = 5;
    $('html').removeAttr('class');
   // UIkit.modal('#vessel-lookup-modal-center').hide();
    //$('#vessel-lookup-modal-center').remove();      
  }

}
