import { Component, OnInit, EventEmitter, Output,ViewChild,Input} from '@angular/core';
import * as $ from 'jquery';

declare var UIkit: any;
import { SpinnerServiceService } from "../../../common-services/spinner-service.service";
import { LookUpdataServiceService } from '../../../common-services/look-updata-service.service';
import { ServerErrorcodeService } from "../../../common-services/server-errorcode.service";
import { SortSearchTableService } from '../../../rcl-application//contract-search/sort-search-table.service';
import { SessionTimeOutService } from "app/common-services/session-time-out.service";
declare var jQuery: any;
declare var UIkit: any;
@Component({
  selector: 'app-service-lookup',
  templateUrl: './service-lookup.component.html',
  styleUrls: ['./service-lookup.component.scss']
})
export class ServiceLookupComponent implements OnInit {


   // @ViewChild(NgModel) model: NgModel;
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

  public active:boolean = false;
  private selectedRow: any = [];
   pc: number = 1;
  
  openModal: boolean = false;
  showlookuptable = true;
  lookupErrorCodeShow: boolean = false;
  locLookUptableData: any = [];
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
  serviceDropdownData = [
    {
      label: "Service Code",
      value: "SWSRVC"
    }, {
      label: "Description",
      value: "SWSRVD"
    }, {
      label: "Remarks",
      value: "SWRMKS"
    }, {
      label: "STATUS",
      value: "SWRCST"
    },
  ]
  resultsPerPage: number = 5;  
  resultsPerPageArr = [1,2,3,4,5,6,7,8,9,10];
  lookupErrorCodetext: string;
  previouselement: any;
  //lookUpvalue: string;
  selectedDropDown: string;
  serviceWildCard: boolean;
  _value: string;
  looUpOrderBy: any;  
  lookupSortIn: any;
  serviceSortData = [{ label: 'Service Code',value: 'serviceCode'}, {label: 'Description Name',value: 'description',}, {label: 'Reamrks', value: 'remarks'}, {label: 'Status', value: 'status'}]
  lookUpSortData = [{ label: 'Ascending',value: 'asnd'}, {label: 'Descending',value: 'dsnd'}]

    constructor(private _spinner: SpinnerServiceService, private _lookupData: LookUpdataServiceService, public _serverErrorCode: ServerErrorcodeService, public _sortTable: SortSearchTableService,private _sessionTimeOutService:SessionTimeOutService) {
      jQuery(document).ready(function(){
        
         jQuery('.lookup-wrapper').on('click', function(e){
   jQuery(".table-container").css("overflow", "hidden");
   e.stopPropagation();
         });
   })
  }
  
  //@Output() lookUpvalueChange = new EventEmitter();

  ngOnInit() {
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

  //lookup modal
  openLookUpModal (e, popupContant){  
    jQuery('#service-lookup-modal').detach();
    //$('#service-lookup-modal').detach();   
    this.selectedDropDown = this.serviceDropdownData[0].value;
    this.looUpOrderBy = "asnd";
    this.serviceWildCard= true;
    this.showlookuptable = true;
    this.locLookUptableData = [];
    this.selectedRow = [];
    this.lookupErrorCodeShow=false;        
    this.openModal = true;    
    setTimeout(function() {
    //your code to be executed after 1 second
    UIkit.modal('#service-lookup-modal').show();
    }, 100);    
  }

 /* 
 showModal (){    
    this.showlookuptable = true;
    this.locLookUptableData = [];
    this.lookupErrorCodeShow=false;
    this.lookupSortIn = undefined;  
    
    this.openModal = true;
    setTimeout(function() {
    //your code to be executed after 1 second
    UIkit.modal('#service-lookup-modal').show();
    }, 100);    
  }
  */
  //close look up Modal
  
  
   getLocLookUpData(lookupTpye, type, eve, inpuvaluevalue, wildCard) {     
    this.lookupSortIn = "serviceCode";
    this._spinner.showSpinner();    
    eve.stopPropagation();
    eve.preventDefault();
    eve.stopImmediatePropagation();
    var backendData = this._lookupData.getDataLookupService(lookupTpye, type, eve, inpuvaluevalue, wildCard);
    backendData.subscribe(
      (data) => { 
          if(data == "userSessionExpired"){
            UIkit.modal('#service-lookup-modal').hide();
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
  
  
    
  
  change(newValue) {
      this.lookUpvalue = newValue;
      this.lookUpvalueChange.emit(newValue);
    }
  
  selectRowData (e) {    
    this.openModal = false;
    this.lookUpvalueChange.emit(e.target.parentElement.children[1].textContent);
    UIkit.modal('#service-lookup-modal').hide();
    this.locLookUptableData = [];
    this.resultsPerPage = 5;
    this.lookupErrorCodetext= undefined;
    this.selectedDropDown = undefined;
    this._value = undefined;
    this.serviceWildCard = false
    this.lookupErrorCodeShow=false;
    this.showlookuptable = true;
    this.lookupSortIn = undefined;
    $('html').removeAttr('class');
    $('#service-lookup-modal').remove();    
  }
   resetPickDropModal(e) {
    this.openModal = false;
    this.showlookuptable = true;
    this.locLookUptableData = [];
    this.lookupErrorCodeShow=false;
    this.lookupSortIn = undefined;   
    this.selectedDropDown = this.serviceDropdownData[0].value;
    this.looUpOrderBy = "asnd";
    this.resultsPerPage = 5;
    this.lookupErrorCodetext = undefined;
    this.selectedDropDown = undefined;
    this._value = undefined;
    this.serviceWildCard = false
    
    
    $('html').removeAttr('class');
    $('#service-lookup-modal').remove();  
    
  }

   
  onTouched() {
    
  }
 
}
