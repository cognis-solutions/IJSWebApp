import { Component, OnInit, ViewChild, Input, Output, EventEmitter } from '@angular/core';
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
declare var UIkit: any;
declare var jQuery: any;
import * as _ from 'lodash';
import * as $ from 'jquery';
import { SessionTimeOutService } from "app/common-services/session-time-out.service";
import { ProcessjoSearchService } from 'app/rcl-application/process-jo/processjo-search.service';


@Component({
  selector: 'app-equipment-browser-lookup',
  templateUrl: './equipment-browser-lookup.component.html',
  styleUrls: ['./equipment-browser-lookup.component.scss']
})
export class EquipmentBrowserLookupComponent implements OnInit {
  emptyContainerListArray: any = []; //#NIIT CR3	
  showDuplicateError: boolean; //#NIIT CR3
  showNegativeError: boolean; //#NIIT CR3

  @Output() editAdhocList = new EventEmitter(); //#NIIT CR3
  @Output() equipmentBrowserArray = new EventEmitter(); //#NIIT CR3
  @Output() selectUpdateEqList = new EventEmitter();
  @Input() private addHocType: any;
  @Input() singleSelect: boolean;
  @Input() private routeListDtl: any;
  @Output() valueChange = new EventEmitter();

  
  private openEmptyEquipemtModal: boolean = false;//#NIIT CR3
  private openModal: boolean = false;
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

  private selectedDropDown1;
  private selectedDropDown2;
  private selectedDropDown3;
  private eqBrowserDropDownData = [
    {
      label: "Equipment No",
      value: "EQEQTN"//eqpNum"
    },
    {
      label: "Category",
      value: "EQCATG"//catg"
    },
    {
      label: "Size",
      value: "EQSIZE"//size"
    }, {
      label: "Type",
      value: "EQTYPE"//type"
    },
    {
      label: "Point",
      value: "EQCUPT"//point"
    },
    {
      label: "Activity",
      value: "EQCUST"//activity"
    },
    {
      label: "Activity Date",
      value: "EQCUMD"//activityDate"
    },
    {
      label: "Activity Time",
      value: "RECORD_ADD_TIME"//activityDate"
    },
    {
      label: "Terminal",
      value: "EQCUTM"//terminal"
    },
    {
      label: "Service",
      value: "EQFIL2"//service"
    },
    {
      label: "Vessel",
      value: "EQVESS"//vessel"
    },
    {
      label: "Voyage",
      value: "EQVOYN"//voyage"
    },
    {
      label: "Direction",
      value: "EQDIRI"//direction"
    },
    {
      label: "Origin",
      value: "EQORIG"//origin"
    },
    {
      label: "POL",
      value: "EQLDPT"//pol"
    },
    {
      label: "POT",
      value: "EQTRPT"//pot"
    },
    {
      label: "POD",
      value: "EQDCPT"//pod"
    },
    {
      label: "Destination",
      value: "EQDEST"//destination"
    },
    {
      label: "Owner",
      value: "EQOWNR"//owner"
    },
    {
      label: "Booking No",
      value: "EQBOOK"//booking"
    },
    {
      label: "BL No",
      value: "EQBOLN"//bl"
    }
  ]
  private eqBrowserDropDownData1 = [
    {
      label: "EQUALS",
      value: "equals"
    },
    {
      label: "NOT EQUALS",
      value: "!equals"
    },
    {
      label: "LIKE",
      value: "like"
    }
  ];
  private eqBrowserDropDownData2 = [
    {
      label: "AND",
      value: "AND"
    },
    {
      label: "OR",
      value: "OR"
    }
  ];

  // #NIIT CR3	>>>> BEGIN
  private rateOptions = [
    {label: "20", value: "20"},
		{label: "40", value: "40"},
		{label: "45", value: "45"},
  ];
  // #NIIT CR3	>>>> END

  private showlookuptable = false;
  private resultsPerPageArr = [1, 2, 3, 4, 5, 6, 7, 8, 9, 10];
  private resultsPerPage = 5;
  private looUpOrderBy: any;
  private lookupSortIn: any;
  private selectedRow: any = [];
  private lookupErrorCodeShow: boolean = false;
  private lookupErrorCodetext: string;
  private _textValue:string;
  private contractId;
  private uikitEqModal = UIkit.modal('#equipment-browser-modal-center');
  crossFlag:boolean=false; // #NIIT CR3


  private eqBrowserSortData = [{ label: "Activity Time", value: "ativityTime" },{ label: "Equipment No", value: "eqpNum" }, { label: "Category", value: "catg" }, { label: "Size", value: "size" }, { label: "Type", value: "type" }, { label: "Point", value: "point" }, { label: "Activity", value: "activity" }, { label: "Activity Date", value: "activityDate" }, { label: "Terminal", value: "terminal" }, { label: "Service", value: "service" }, { label: "Vessel", value: "vessel" }, { label: "Voyage", value: "voyage" }, { label: "Direction", value: "direction" }, { label: "Origin", value: "origin" }, { label: "POL", value: "pol" }, { label: "POT", value: "pot" }, { label: "POD", value: "pod" }, { label: "Destination", value: "destination" }, { label: "Owner", value: "owner" }, { label: "Booking No", value: "booking" }, { label: "BL No", value: "bl" }];
  private lookUpSortData = [{ label: 'Ascending', value: 'asnd' }, { label: 'Descending', value: 'dsnd' }]

  private locLookUpEqBrowserTableData: any = [];

  constructor(public _joborderService: ProcessjoSearchService,private _spinner: SpinnerServiceService, private _lookupData: LookUpdataServiceService, public _serverErrorCode: ServerErrorcodeService, public _sortTable: SortSearchTableService, private _http: Http,private _sessionTimeOutService:SessionTimeOutService) { }

  ngOnInit() {
    this.selectedDropDown1 = this.eqBrowserDropDownData[0].value;
    this.selectedDropDown2 = this.eqBrowserDropDownData1[0].value;
    this.selectedDropDown3 = this.eqBrowserDropDownData2[0].value;    
    this.getEquipmentList(); //to get eqTypeOptions // #NIIT CR3
    if(this.eqTypeOptions.length != 0){
      this.addEmptyEquipment(); //to show a row added by default while opening the popup // #NIIT CR3
    } else{
      this.eqTypeOptions.push({label: "**",value: "**"});
    }
  }

  equipData;
  invokingComponent:string;
  sendingReplaceEquipmentData(selectedEquipmentData,checkComponent){
    this.invokingComponent = checkComponent;
    this.equipData = selectedEquipmentData;        
  }

  //method for open the lookup
  
  openLookUpModal() {
    
    this.seachCriteriaList = [];
    this.crossFlag = false; // #NIIT CR3
    if(this.invokingComponent == "joMaintainenance"){
      this.contractId = this.equipData.contractId;
      this.crossFlag = true; // #NIIT CR3
    } else{
     if(this._joborderService.processJoSearchData['processJoParam']['processJoType'] =="ER"||
     this._joborderService.processJoSearchData['processJoParam']['processJoType'] =="LAH"){
      this.contractId="";
     }
     else{
      this.contractId = this.routeListDtl.contractId;
     }
    }   
    jQuery("#equipment-browser-modal-center").detach();
    
    
    if(this.invokingComponent == "joMaintainenance"){
      if(this.equipData.bk_bl_ad != "AD"){
        if(this.equipData.bk_bl_ad == "BL"){
          var obj1 = {
            "selectedDropDown1": "EQBOLN",
            "selectedDropDown2": "equals",
            "textValue": this.equipData.BkgOrBLNo,
            "selectedDropDown3": this.eqBrowserDropDownData2[0].value,
            "displayText": "BL No"
          }
        } else{
          var obj1 = {
            "selectedDropDown1": "EQBOOK",
            "selectedDropDown2": "equals",
            "textValue": this.equipData.BkgOrBLNo,
            "selectedDropDown3": this.eqBrowserDropDownData2[0].value,
            "displayText": "Booking No"
          }
        }   
      }      
    
     var obj2 = {
      "selectedDropDown1": "EQSIZE",
      "selectedDropDown2": "equals",
      "textValue": this.equipData.contSize,
      "selectedDropDown3": this.eqBrowserDropDownData2[0].value,
      "displayText": "Size"
    }

    var obj3 = {
      "selectedDropDown1": "EQTYPE",
      "selectedDropDown2": "equals",
      "textValue": this.equipData.contType,
      "selectedDropDown3": this.eqBrowserDropDownData2[0].value,
      "displayText": "Type"
    }
        if (this.seachCriteriaList.length == 0) {
          if(obj1 != undefined){
             this.seachCriteriaList.push(obj1);
          }         
          this.seachCriteriaList.push(obj2);
          this.seachCriteriaList.push(obj3);
        } 
    } else {
    this._textValue = undefined;
    //this.seachCriteriaList = [];
    this.selectedDropDown1 = this.eqBrowserDropDownData[0].value
    this.selectedDropDown2 = this.eqBrowserDropDownData1[0].value
    this.selectedDropDown3 = this.eqBrowserDropDownData2[0].value
  }
      
    this.openModal = true;
    this.showlookuptable = true;
    this.lookupErrorCodeShow = false;
    this.lookupSortIn='eqpNum';
    this.looUpOrderBy='asnd';  

    setTimeout(function () {
      UIkit.modal('#equipment-browser-modal-center').show();
    }, 100);

    
  }
allRoutingIds:any=[];
  prepairRautingId(vendors)
  {
    //debugger;
   
    for (let i = 0; i < vendors.length; i++) {
      
        this.allRoutingIds.push(vendors[i]["contractId"]);
      
      
  }
}

  typeCheck:any; 
  fromLoaction:any;
  fromTerminal:any;
  getLocLookUpData() {
    //debugger;
    //this._joborderService.vendorDetails;
   
    //this._joborderService.vendorDetails
    if(this._joborderService.vendorDetails!=null||this._joborderService.vendorDetails!=undefined){
    this.prepairRautingId(this._joborderService.vendorDetails);
    }
    this._spinner.showSpinner();
    let addhoc;
    if( this._joborderService.processJoSearchData!=undefined){
   
    if (this._joborderService.processJoSearchData['processJoParam']['processJoType'] =="ER") {
      addhoc = 'E'
      this.fromLoaction= this._joborderService.processJoSearchData['processJoParam']['fromLocation'];
      this.fromTerminal=  this._joborderService.processJoSearchData['processJoParam']['fromTerminal'];
    } else if (this._joborderService.processJoSearchData.processJoParam['processJoType'] =="LAH") {
      addhoc = 'L'
      this.fromLoaction= this._joborderService.processJoSearchData['processJoParam']['fromLocation'];
      this.fromTerminal=  this._joborderService.processJoSearchData['processJoParam']['fromTerminal'];
    }
  }
    if(this.invokingComponent == 'joMaintainenance'){
      this.typeCheck = this.equipData.contEmptyOrLaden;
     
    } else {
      this.typeCheck = addhoc;
    }

    let point= this.routeListDtl; 
    let terminal= this.routeListDtl;
    if( this._joborderService.processJoSearchData!=undefined){
    if(this._joborderService.processJoSearchData.processJoParam['processJoType'] =="ER"||
    this._joborderService.processJoSearchData.processJoParam['processJoType'] =="LAH")
    {
    var backendData = this._lookupData.getDataLookupServiceJOAll('getJOEquioment', this.typeCheck, '', this.seachCriteriaList, '',this.contractId,'', this.fromLoaction,this.fromTerminal,this.allRoutingIds);
    }
  }else{
    var backendData = this._lookupData.getDataLookupServiceJOAll('getJOEquioment', this.typeCheck, '', this.seachCriteriaList, '',this.contractId,'',this.equipData.fromLoaction,this.equipData.fromTerminal,this.allRoutingIds);
         }
    backendData.subscribe(
      (data) => {
        if(data == "userSessionExpired"){
          UIkit.modal('#equipment-browser-modal-center').hide();
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
          this.locLookUpEqBrowserTableData = this.removeDuplicate( data);
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


  private returnJosn: any = [];
  removeDuplicate(data)
  {


    //return this.removeDuplicates(data);
    return this.removeDuplicates(data,'eqpNum'); 

  }
  removeDuplicates(array,id)
  {
    //let lookup = new Set();
  
 //  return array.filter(obj => !lookup.has(obj[id]) && lookup.add(obj[id]));

var obj={};
   for ( var i = 0, len = array.length; i < len; i++ ){
    if(!obj[array[i][id]]) 
    obj[array[i][id]] = array[i];
  }
  var newArr = [];
  for ( var key in obj ) newArr.push(obj[key]);
  return newArr;
}
    





  //method to add the equipment query for search
  seachCriteriaList = [];
  
  addEquipmentQuery(selectedDropDown1, selectedDropDown2, _textValue, selectedDropDown3) {   
    this.getSelectedDropDown1Text(selectedDropDown1);
    let obj = {
      "selectedDropDown1": selectedDropDown1,
      "selectedDropDown2": selectedDropDown2,
      "textValue": _textValue,
      "selectedDropDown3": selectedDropDown3,
      "displayText": this.selectedDropDown1Text
    }

    if (this.seachCriteriaList.length == 0) {
      this.seachCriteriaList.push(obj);
    } else {
      if (!(_.isEqual(this.seachCriteriaList[this.seachCriteriaList.length - 1], obj))) {
        this.seachCriteriaList.push(obj);
      }
    }
    this._textValue= '';
  }

  selectedDropDown1Text: string
  getSelectedDropDown1Text(e) {    
    this.eqBrowserDropDownData.forEach(element => {
      if(element.value == e) {
        this.selectedDropDown1Text = element.label;
      }
    });
  }

  //method to remove the equipment query for search
  removeEquipmentQuery(item, i) {
    this.seachCriteriaList.splice(i, 1);
  }

  //method to sort the data
  sortLookUpDataByColumn() {
    this._sortTable.sortTableData(this.locLookUpEqBrowserTableData, this.lookupSortIn, this.looUpOrderBy);
  }

  //method to sort the data
  sortLookUpDataByOrder() {
    this._sortTable.sortTableData(this.locLookUpEqBrowserTableData, this.lookupSortIn, this.looUpOrderBy);
  }



  //selectedRow: any;
  checkedSelectedRows: any = [];
  private selectTableRowCheckBoxes(e, rowObj) {
    if (this.singleSelect) {
      if (e.target.checked) {
        jQuery(".tableCheckBox input").prop('checked', false);
        jQuery(e.target).prop('checked', true);
        this.checkedSelectedRows = [rowObj]
      }
      else {
        this.checkedSelectedRows = []
      };
    } else {
      if (e.target.checked) {
        this.checkedSelectedRows.push(rowObj);
      } else {
        this.checkedSelectedRows = this.deleteObjByEqNumber(this.checkedSelectedRows, 'eqpNum', rowObj.eqpNum);
      }
    }


  }

  //delete element from array
  private deleteObjByEqNumber(arr, attr, value) {
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

  updateEquipment() {
    this.openModal = false;
    UIkit.modal('#equipment-browser-modal-center').hide();
    //alert(this.checkedSelectedRows.length)
    this.checkedSelectedRows['eqListSource'] = "availableEquipment";
    this.selectUpdateEqList.emit(this.checkedSelectedRows);
    this.checkedSelectedRows = [];
  }
  resetEqBrowserModal(e) {    
    this.openModal = false;
    UIkit.modal('#equipment-browser-modal-center').hide();
    this.checkedSelectedRows = [];
  }
  
  equipData1;
  invokingComponent1:string;
  sendingAddEquipmentData(selectedEquipmentData,checkComponent){
    this.invokingComponent1 = checkComponent;
    this.equipData1 = selectedEquipmentData;        
  }
  //method to open empty equipment modal
  // #NIIT CR3	>>>> BEGIN
  openEmptyEquipmentsModal(rowObj){
	  //debugger;
	  if(rowObj!=undefined){
	  if(rowObj.contDetailJO && rowObj.contDetailJO.length!=0){
		rowObj.contDetailJO[0].contEmptyOrLaden;
		this.addhocCheck="E";
    }else{
      var tempJOT=rowObj.jobOrdType;
     var array = tempJOT.split(" ");
    if(array[1]="Empty"){this.addhocCheck='E';}
      else if(array[1]="Laden"){this.addhocCheck='L';}
    }
	  }
    //console.log(rowObj);
    //alert("?Equipment");
    $('#add-empty-equipment-setup-modal').parent().parent().parent().scrollTop(0); 
    $('#add-empty-equipment-setup-modal').parent().parent().parent().css({'overflow':'hidden','top':'50px'}); 
    this.openEmptyEquipemtModal = true;
    
    //if list is empty the open the container with default one row
    if(this.emptyContainerList.length == 0){
      this.addEmptyEquipment()
    }
    this.deselectAll(this.emptyContainerList); // #NIIT CR3	

     setTimeout(function () {        
        $('#add-empty-equipment-setup-modal').addClass('uk-open').show();      
    }, 100);    

  }
  // #NIIT CR3	>>>> END

  // #NIIT CR3	>>>> END
  deselectAll(arr: any[]){
    arr.forEach(val =>{
    if(val.checked){        
        val.checked = false;
    }})
    this.elementsToDel = [];
  }
  // #NIIT CR3	>>>> END

  emptyContainerList:any = [];
  eqTypeOptions:any=[];
  //method to add empty equipment container row
  // #NIIT CR3	>>>> BEGIN
  addEmptyEquipment(){
    //alert("addEmptyEquipment???????????.....")
    let rowObj = {
      "type": this.eqTypeOptions[0].value,
      "size": this.rateOptions[0].value,
      "quantityValue": undefined,
      "containerNumbers":[]
    }

    this.emptyContainerList.push(rowObj);

  } 
  // #NIIT CR3	>>>> END  

  showAlertPopup:boolean=false;
  addhocCheck;
  //method to save empty equipments
  // #NIIT CR3	>>>> BEGIN
  saveEmptyEquipment(jsonArray){
    //alert("helo");
	
    console.log(jsonArray);
    this.selectUpdateEqList.emit(this.emptyContainerList);
      let inValidFlag: boolean = false;
      let duplicateFlag: boolean = false;
      let negativeQuantityFlag: boolean = false;
      //checking whether job order type is Empty Repo or Laden Ad Hoc
      if( this._joborderService.processJoSearchData!= null &&  this._joborderService.processJoSearchData.processJoParam != null && this._joborderService.processJoSearchData.processJoParam.processJoType!= null){

      
      this.addhocCheck = this._joborderService.processJoSearchData.processJoParam.processJoType.substring(0,1);
      }
      if (this.addHocType == 'Empty Repo') {
        this.addhocCheck = 'E'
      } else if (this.addHocType == 'Laden Ad-hoc') {
        this.addhocCheck = 'L'
      }
      
      //check for duplicate rows 
      for(var i = 0; i < this.emptyContainerList.length ; i++){     
        for(var j= i+1 ; j < this.emptyContainerList.length ; j++){ 
                
          if((this.emptyContainerList[i].type == this.emptyContainerList[j].type)
              && (this.emptyContainerList[i].size == this.emptyContainerList[j].size) 
            ){          
          
            //this.showAlertPopup = true;
            duplicateFlag = true;            
            break;               
          }  
        }      

        if(duplicateFlag){
          break;
        }      
      }

      //check for incorrect input
      this.emptyContainerList.forEach(element => {
      var quantityValueType;
      //if quantity entered is less than 1
        if(element.quantityValue < 1){
          negativeQuantityFlag=true;
        }
        else {
          quantityValueType= element.quantityValue/element.quantityValue;
           if(((Number.isNaN(quantityValueType)))){
             inValidFlag=true;     
           return false;                      
          }
        }
      });

      //if qunatity entered is negative or zero
      if(negativeQuantityFlag){
        this.showNegativeError=true;
        return false;
      }

      //if invalid value is entered in quanity  
      if(inValidFlag){      
        this.showDuplicateError=true; 
        return false;     
      }

      //if duplicate rows are added
      if(duplicateFlag){
        this.showAlertPopup = true;
        return false;
      } 

      //if input criteria is correct
      if(inValidFlag ||  duplicateFlag){
        return;
      } else{
          this.getEmptyUnavailableEquipmentList(); //method to get the list of unavailable equipment numbers
          this.getEqList();          
          this.equipmentBrowserArray.emit(this.deleteCriteriaList);
          this.unavilableEqList['eqListSource'] = "unavailableEquipment";
          this.selectUpdateEqList.emit(this.unavilableEqList);          
          $('#add-empty-equipment-setup-modal').parent().parent().parent().css({'overflow':'visible','top':'50px','overflow-y':'auto'});         
          $('#add-empty-equipment-setup-modal').addClass('uk-open').hide(); 
          this.deleteCriteriaList = [];
          this.unavilableEqList = [];
		}

   }
   // #NIIT CR3	>>>> END  
  
  // #NIIT CR3	>>>> BEGIN
  resetEmptyEquipmentList(){
    $('#add-empty-equipment-setup-modal').parent().parent().parent().css({'overflow':'visible','top':'50px','overflow-y':'auto'});         
    //alert("resetEmptyEquipmentList method called");
  }
  // #NIIT CR3	>>>> END

  elementsToDel: any = [];
  deleteCriteriaList:any = []; //array holding the delete criteria 
  //Select check boxes from empty equipment modal
  // #NIIT CR3	>>>> BEGIN
  checkSelectedRow(e, rowData, i) {
    if (e.target.checked == true) {
      this.elementsToDel.push(i);
      this.deleteCriteriaList.push(rowData);
    } else {
      if (this.elementsToDel) {
        let x = this.elementsToDel.length
        while (x--) {
          if (this.elementsToDel[x] == i) {
            this.elementsToDel.splice(x, 1);
            this.deleteCriteriaList.splice(x,1);
          }
        }
      }
    }
  }
  // #NIIT CR3	>>>> END

  //Delete row from empty equipment modal 
  // #NIIT CR3	>>>> BEGIN
  deleteSelectedRow() {    
    let x = this.elementsToDel.length;
    this.elementsToDel = this.elementsToDel.sort();
    while (x--) {
      let y = this.emptyContainerList.length;
      while (y--) {
        if (this.elementsToDel[x] == y) {
          this.emptyContainerList.splice(y, 1); 
          this.emptyContainerList.forEach(element => {
            element.containerNumbers= []; //empty the containers again while deleting so that container numbers are generated in sequenece again
          });         
        }
      }
    }
    this.elementsToDel = [];
    //this.deleteCriteriaList = [];    
    this.emptyContainerListArray = []; //male list empty and fill it again
   
  }
  // #NIIT CR3	>>>> END


  //method to get the unavailable equipment numbers for a particular size type combination
  // #NIIT CR3	>>>> BEGIN
  containerDetailID:any = 1;
  getEmptyUnavailableEquipmentList(){
      this.emptyContainerListArray = [];
      this.containerDetailID = 1; 
      this.emptyContainerList.forEach(element => {
            if(element.size == "20"){
              for (var index = 0; index < element.quantityValue; index++) {                               
                element.containerNumbers.push(this.getEquipmentNumber(element.size,element.type,this.containerDetailID));                
                this.containerDetailID++;                               
              }
               this.emptyContainerListArray.push(element); 
            }
            if(element.size == "40"){
              for (var index = 0; index < element.quantityValue; index++) {               
                element.containerNumbers.push(this.getEquipmentNumber(element.size,element.type,this.containerDetailID));                
                this.containerDetailID++;                                
              }
              this.emptyContainerListArray.push(element);
            }
            if(element.size == "45"){
              for (var index = 0; index < element.quantityValue; index++) {               
                element.containerNumbers.push(this.getEquipmentNumber(element.size,element.type,this.containerDetailID));                
                this.containerDetailID++                               
              }
              this.emptyContainerListArray.push(element); 
            }            
        });
        
    }
    // #NIIT CR3	>>>> END

    //method to get the unique available equipment numbers
    // #NIIT CR3	>>>> BEGIN
    getEquipmentNumber(size,type,containerDetailID){
      return size+type+this.addhocCheck+containerDetailID;     
    }  
    // #NIIT CR3	>>>> END   

    unavilableEqList:any = [];
    //method to get the list of unavilable equipment containers that will be emmited 
    // #NIIT CR3	>>>> BEGIN
    getEqList(){
      for (var i = 0; i < this.emptyContainerListArray.length; i++) {
          for (var j = 0; j < this.emptyContainerListArray[i].containerNumbers.length; j++) {          
            let unavilableEqListObj = {
                "type": this.emptyContainerListArray[i].type,
                "size": this.emptyContainerListArray[i].size,              
                "eqpNum":this.emptyContainerListArray[i].containerNumbers[j],
              }
              this.unavilableEqList.push(unavilableEqListObj);
          }      
      }
     var filteredUnavilableEqList = this.unavilableEqList.filter(function({eqpNum}) {
      const key =`${eqpNum}`;
      return !this.has(key) && this.add(key);
    }, new Set);
    this.unavilableEqList = filteredUnavilableEqList;
    this.emptyContainerListArray = [];
  }
    // #NIIT CR3	>>>> END
  
  // #NIIT CR3	>>>> BEGIN
  //method called when anything is changed   
  change(valueChanged) {      
      this.emptyContainerList.forEach(element => {
            element.containerNumbers= []; //empty the containers again while deleting so that container numbers are generated in sequenece again
      });   
      this.editAdhocList.emit(''); //emit event to empty master list when adhoc edited
  }
  // #NIIT CR3	>>>> END


  // #NIIT CR3	>>>> BEGIN
  //to get the list of eqTypeOptions from backend
  getEquipmentList(){
    var backendData = this._lookupData.getUnavailableEquipmentsType('adhocEquipList');
    backendData.subscribe(
      (data) => {      
        if(data == "userSessionExpired"){
          this._sessionTimeOutService.checkSessionTimeout(data);
        }  
        else if (data.hasOwnProperty("errorCode")) {
          this.lookupErrorCodetext = this._serverErrorCode.checkError(data["errorCode"]);
          // this.lookupErrorCodeShow = true;
          // this.showlookuptable = true;
        }
        else {
          var termList = data;
          for (let i = 0; i < termList.length; i++) {
              this.eqTypeOptions.push({ label: termList[i], value: termList[i] });              
            }
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
  // #NIIT CR3	>>>> END

}