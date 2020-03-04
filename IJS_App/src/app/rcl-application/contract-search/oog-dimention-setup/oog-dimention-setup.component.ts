import { Component, OnInit, EventEmitter, Output, Input, ChangeDetectorRef, ChangeDetectionStrategy, ComponentRef } from '@angular/core';
import * as $ from 'jquery';
declare var UIkit: any;
import { LookUpdataServiceService } from '../../../common-services/look-updata-service.service';
import { ServerErrorcodeService } from "../../../common-services/server-errorcode.service";
import {SpecialHandlingService } from '../../../common-services/special-handling.service';
import { SortSearchTableService } from '../../../rcl-application//contract-search/sort-search-table.service';
import { ModalDialogModule, IModalDialog, ModalDialogService, IModalDialogOptions, IModalDialogButton } from 'ngx-modal-dialog';
import * as _ from 'lodash';
import { Observable } from 'rxjs/Observable';
import { SpinnerServiceService } from "app/common-services/spinner-service.service";
import { ContractSearchService } from "app/rcl-application/contract-search/contract-search.service";
import { SessionTimeOutService } from "app/common-services/session-time-out.service";


@Component({
  selector: 'app-oog-dimention-setup',
  templateUrl: './oog-dimention-setup.component.html',
  styleUrls: ['./oog-dimention-setup.component.scss'],
})
export class OogDimentionSetupComponent implements OnInit,IModalDialog  {
  actionButtons: IModalDialogButton[];
  constructor(private cd: ChangeDetectorRef,private shService:SpecialHandlingService,private _spinner: SpinnerServiceService,public _joborderService: ContractSearchService,private _sessionTimeOutService:SessionTimeOutService) {
    this.actionButtons = [
      { text: 'Add', onAction:() => this.addRow() }, // no special processing here
      { text: 'Delete', onAction: () => this.deleteRow() },
      { text: 'Save', onAction: () => this.saveRow() },
      { text: 'Close', onAction: () => this.close() },
      
    ];
   }
  @Output() public oogSetDataList = new EventEmitter();
  @Output() public oogSetDataListforAddEdit = new EventEmitter();
  oogSetUpList: any = [];
  elementsToDel: any = [];
  //@Input() targetOogList;
  showValidValuesError:boolean;
  myObservable:Observable<any>;
  showAlertPopup:boolean;
  showMandatoryFieldPopup:boolean;
  minMaxValidationPopup:boolean;
  addEditSelectConfig: any = {
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

  dropOptions = [
    {
      label: "UC",
      value: "UC"
    },
    {
      label: "OOG",
      value: "OG"
    },

  ]
  //checking from which component it is being called
  componentName: string;
  selectedContractRow:any=[];
  dialogInit(reference: ComponentRef<IModalDialog>, options: Partial<IModalDialogOptions<any>>) {
    // no processing needed    
    this.selectedContractRow = options.data.selectedContarctRow;
    this.showModal(options.data.oogList,options.data.checkComponent,options.data.selectedContarctRow.fromTerminal);      
  }


  terminalDepotCode:any;
  showModal(val,callingComponent,terminalDepotCode) {
    this.componentName = callingComponent;    
    this.elementsToDel = [];
    this.terminalDepotCode = terminalDepotCode;//#NIIT CR4 >>>>BEGIN
    //UIkit.modal('#oog-setup-modal').show();   
  }

  //select Check box
  checkSelectedRow(e, rowData, i) {
    if (e.target.checked == true) {
      this.elementsToDel.push(rowData);//#NIIT CR4 >>>>BEGIN
    } else {
      if (this.elementsToDel) {
        this.elementsToDel = this.deleteObjByProperty(this.elementsToDel, 'seqNo', rowData.seqNo);        
      }
    }    
  }

  //add row in table
  errorText: string;
  addRow() : boolean {
    let rowObj = {
      "oogSetupCode": undefined,
      "minOverHeight": '*',
      "maxOverHeight": '*',
      "minOverLength": '*',
      "maxOverLength": '*',
      "minOverWidth": '*',
      "maxOverWidth": '*',
      "action":"N"
    }    
    this.shService.setOogList(rowObj);
    this.oogSetUpList = this.shService.oogList;

    //scroll added data to the bottom
    var scrollValue= $('.oogClassTable').height(); 
    var actualScrVal = scrollValue + 50;
    setTimeout(function() {
      $('.oogClassTable').scrollTop(actualScrVal);
    }, 300);


    return false;    
  }

  //delete row
  //#NIIT CR4 >>>>BEGIN
  oogDataAfterDelete:any=[];
  oogListDeleteData:any=[];
  deleteRowValidation:boolean;
  confirmDeleteValidation:boolean;
  alertRecordsDeleted:boolean;
  deleteRow() : boolean {
    //if row is not selected
    if(this.elementsToDel.length == 0){
      this.deleteRowValidation = true;
    }
    //if row is slected
    else{
       this.confirmDeleteValidation = true;
    }     
      return false;
  }

  //if yes is selected from popup
  delNewRecordsOnly:boolean;
  delDbRecords:boolean;
  deleteOogList(){
        
    var indexArray:any =[];
    this.oogSetUpList = this.shService.oogList;
    var dbRecords:any = [];
    var newRecords:any= [];

    //logic for only add
    for (var index = 0; index < this.elementsToDel.length; index++) {
      var element = this.elementsToDel[index];      

      //if only db
      if(element.hasOwnProperty('oldOogSetupCode') && ((element.action == undefined)  || (element.action == "U"))){
        this.delDbRecords = true;
        dbRecords.push(element);
      }      
       else{
        indexArray.push(element.seqNo); 
        this.delNewRecordsOnly = true;       
      }
    }
    
    for (var i = 0; i < indexArray.length; i++) {
      for (var j = 0; j < this.oogSetUpList.length; j++) {
        var element = this.oogSetUpList[j];

        if(indexArray[i]  == element.seqNo){
          this.oogSetUpList = this.deleteObjByProperty(this.oogSetUpList, 'seqNo', element.seqNo);
        }        
      }     
    } 
    if(this.oogSetUpList.length !=0 ){
      this.shService.oogList = this.oogSetUpList;
      this.shService.oogList.forEach((element)=>{
        if(element['action'] !=undefined && element['action']=="N"){
          newRecords.push(element);
        }
      })
    }


	  //#NIIT CR4 >>>>BEGIN
    dbRecords.forEach((element)=>{
      element['action']="D";
      this.oogListDeleteData.push({"oogSetupCode":element.oogSetupCode,"action":element.action});
    });

    this._spinner.showSpinner();
      
    var backendData = this._joborderService.deleteOogSetupFromList({"terminalDepotCode": this.terminalDepotCode,"oogSetUpList":this.oogListDeleteData});
    backendData.subscribe(
      (data) => {
        if(data == "userSessionExpired"){
          this._sessionTimeOutService.checkSessionTimeout(data);
        }
        else if (data.hasOwnProperty("errorCode")) { 

        } else {          
          this.oogDataAfterDelete = data['oogSetUpList'];    
          this.shService.oogList = this.oogDataAfterDelete;
          this.shService.oogList = this.shService.oogList.concat(newRecords);
          this.elementsToDel = [];
          this.oogListDeleteData= [];
          this._spinner.hideSpinner();
          this.alertRecordsDeleted = true;
          this.confirmDeleteValidation = false;//after delete close the confirmation popup
        }
      }); 
  }

  editedOogList:any=[];
  oogDataEdited(oogRow){    
    this.shService.oogList.forEach((element)=>{
      if(element['oogSetupCode'] == oogRow.oogSetupCode && element['action'] !="N"){
        element['action']="U";
      }
    });    
	//#NIIT CR4 >>>>END
  }

  saveRowValidation:boolean;
  saveRow() : boolean {
    let filteredOOGSetUpList = [];
    let duplicateFlag : boolean =false;   
    this.oogSetUpList = this.shService.oogList;
    let oogMandatoryFlag: boolean = false;
    let inValidFlag: boolean = false;
    let saveCheck:boolean = false;
    let compareMinMax:boolean =  false;

    for(var i = 0; i < this.oogSetUpList.length ; i++){
      if((this.oogSetUpList[i].oogSetupCode === undefined || this.oogSetUpList[i].oogSetupCode.trim().length == 0)||
         (this.oogSetUpList[i].minOverWidth === undefined || this.oogSetUpList[i].minOverWidth.trim().length == 0)||
         (this.oogSetUpList[i].minOverLength === undefined || this.oogSetUpList[i].minOverLength.trim().length == 0)||
         (this.oogSetUpList[i].minOverHeight === undefined || this.oogSetUpList[i].minOverHeight.trim().length == 0)||
         (this.oogSetUpList[i].maxOverWidth === undefined || this.oogSetUpList[i].maxOverWidth.trim().length == 0)||
         (this.oogSetUpList[i].maxOverLength === undefined || this.oogSetUpList[i].maxOverLength.trim().length == 0)||
         (this.oogSetUpList[i].maxOverHeight === undefined || this.oogSetUpList[i].maxOverHeight.trim().length == 0)){
            oogMandatoryFlag = true;
            break;
          } 
      for(var j= i+1 ; j < this.oogSetUpList.length ; j++){    
        if((this.oogSetUpList[j].oogSetupCode === undefined || this.oogSetUpList[j].oogSetupCode.trim().length == 0) ||
			    (this.oogSetUpList[j].minOverWidth === undefined || this.oogSetUpList[j].minOverWidth.trim().length == 0)||
          (this.oogSetUpList[j].minOverLength === undefined || this.oogSetUpList[j].minOverLength.trim().length == 0)||
          (this.oogSetUpList[j].minOverHeight === undefined || this.oogSetUpList[j].minOverHeight.trim().length == 0)||
          (this.oogSetUpList[j].maxOverWidth === undefined || this.oogSetUpList[j].maxOverWidth.trim().length == 0)||
          (this.oogSetUpList[j].maxOverLength === undefined || this.oogSetUpList[j].maxOverLength.trim().length == 0)||
          (this.oogSetUpList[j].maxOverHeight === undefined || this.oogSetUpList[j].maxOverHeight.trim().length == 0)){
              oogMandatoryFlag = true;
              break;
            } 

        if((this.oogSetUpList[i].oogSetupCode != undefined && this.oogSetUpList[j].oogSetupCode != undefined) && (this.oogSetUpList[i].oogSetupCode.toUpperCase() == this.oogSetUpList[j].oogSetupCode.toUpperCase())){
              duplicateFlag = true;            
              break;               
            } 
        //check if multiple rows have all values *    
        if((this.oogSetUpList[i].minOverWidth == "*" && this.oogSetUpList[j].minOverWidth == "*") &&
          (this.oogSetUpList[i].minOverLength == "*" && this.oogSetUpList[j].minOverLength == "*") &&
          (this.oogSetUpList[i].minOverHeight == "*" && this.oogSetUpList[j].minOverHeight == "*") &&
          (this.oogSetUpList[i].maxOverWidth == "*" && this.oogSetUpList[j].maxOverWidth == "*") &&
          (this.oogSetUpList[i].maxOverLength == "*" && this.oogSetUpList[j].maxOverLength == "*") &&
          (this.oogSetUpList[i].maxOverHeight == "*" && this.oogSetUpList[j].maxOverHeight == "*")){
              duplicateFlag = true;            
              break;               
            }      
      }

      if(oogMandatoryFlag){
        break;
      }

      if(duplicateFlag){
        break;
      }

      //check for min lesser than max
      if(((this.oogSetUpList[i].minOverHeight != "*" &&  this.oogSetUpList[i].maxOverHeight != "*") && (this.oogSetUpList[i].minOverHeight > this.oogSetUpList[i].maxOverHeight)) ||
        ((this.oogSetUpList[i].minOverLength != "*" &&  this.oogSetUpList[i].maxOverLength != "*") && (this.oogSetUpList[i].minOverLength > this.oogSetUpList[i].maxOverLength)) ||
        ((this.oogSetUpList[i].minOverWidth != "*" &&  this.oogSetUpList[i].maxOverWidth != "*") && (this.oogSetUpList[i].minOverWidth > this.oogSetUpList[i].maxOverWidth))){
          compareMinMax = true; 
      }

      //if neither row is added or deleted
      if(this.oogSetUpList[i].action!=undefined && (this.oogSetUpList[i].action=='N' || this.oogSetUpList[i].action=='U')){
         saveCheck=true;
      }
    }

    //alert to ask user to fill all the mandatory fields
    if(oogMandatoryFlag){
      this.showMandatoryFieldPopup=true;      
      return false;
    }
    

    this.oogSetUpList.forEach(element => {
      var minOverWidthData;    
      var minOverLengthData;
      var minOverHeightData;
      var maxOverWidthData;    
      var maxOverLengthData;
      var maxOverHeightData;
	  //#NIIT CR4 >>>>BEGIN
      var valid:boolean;
      var regex1 = RegExp('^([A-Za-z0-9])+$');

      if(element.minOverWidth != "0"){
        minOverWidthData= element.minOverWidth/element.minOverWidth;
      }      
      if(element.minOverLength != "0"){
         minOverLengthData= element.minOverLength/element.minOverLength; 
      } 
      if(element.minOverHeight != "0"){
        minOverHeightData= element.minOverHeight/element.minOverHeight;
      }
      if(element.maxOverWidth != "0"){
         maxOverWidthData= element.maxOverWidth/element.maxOverWidth;
      }
      if(element.maxOverLength != "0"){
         maxOverLengthData= element.maxOverLength/element.maxOverLength;     
      }
      if(element.maxOverHeight != "0"){
        maxOverHeightData= element.maxOverHeight/element.maxOverHeight;
      }

      //to check if oogsetup code is valid or invalid     
      var oogSetupStr = element.oogSetupCode;
      valid = regex1.test(oogSetupStr);

      var isNegativeValue:boolean;  
      var minOverWidthInteger = parseInt(element.minOverWidth);
      var minOverLengthInteger = parseInt(element.minOverLength);
      var minOverHeightInteger = parseInt(element.minOverHeight);
      var maxOverWidthInteger = parseInt(element.maxOverWidth);
      var maxOverLengthInteger = parseInt(element.maxOverLength);
      var maxOverHeightInteger = parseInt(element.maxOverHeight);
      //check if the input value is negative or not
      if(minOverWidthInteger < 0 || minOverLengthInteger < 0 || minOverHeightInteger < 0 || maxOverWidthInteger < 0 || maxOverLengthInteger < 0 || maxOverHeightInteger < 0){
        isNegativeValue = true;
      }
      
      
      if(((element.minOverWidth != '' &&  Number.isNaN(minOverWidthData) && element.minOverWidth!="*") || 
          (element.minOverLength != '' &&  Number.isNaN(minOverLengthData) && element.minOverLength!="*") ||
          (element.minOverHeight != '' &&   Number.isNaN(minOverHeightData) && element.minOverHeight!="*") || 
          (element.maxOverWidth != '' &&  Number.isNaN(maxOverWidthData) && element.maxOverWidth!="*") || 
          (element.maxOverLength != '' &&  Number.isNaN(maxOverLengthData) && element.maxOverLength!="*")||      
          (element.maxOverHeight != '' &&  Number.isNaN(maxOverHeightData) && element.maxOverHeight!="*") ||
          (valid == false) || (isNegativeValue == true))){
            inValidFlag=true;                 
          }         
        });


	  //#NIIT CR4 >>>>END
    if(inValidFlag){      
      this.showValidValuesError=true;
      return false;
    }

    //if min greter than max then ask user to input min value lesser than max
    if(compareMinMax){
      this.minMaxValidationPopup=true;      
      return false;
    }

    if(!duplicateFlag){ 
      
      //alert when no row is added or updated
      if(!saveCheck){
          this.saveRowValidation = true;
          return;  
      }   

      filteredOOGSetUpList = this.shService.oogList;     
      if(this.componentName == "addEditCostRate"){
        this.shService.currentNameSubjectforAddEdit.next(filteredOOGSetUpList);    //make new subject
      }else{
        this.shService.currentNameSubject.next(filteredOOGSetUpList); 
      }       
      
      filteredOOGSetUpList = [];  
       return false;  //#NIIT CR4 >>>>BEGIN
        
    } else{
       this.showAlertPopup=true;
       return false;
       //UIkit.modal('#oog-setup-modal').hide(); 
       //UIkit.modal('#duplicate-warning-modal').show();       
    }
   
  }

  ngOnInit() {   
    
  } 

  getOogList(){
    return  this.shService.oogList;
  }

  openOogModal(){
     //UIkit.modal('#oog-setup-modal').show();  
  }

  close() : boolean{
    this.shService.refreshOogSubject.next(this.selectedContractRow)
    return true;
  }

  oogSaved(){
    UIkit.modal('#oog-success-modal').show();
  }

  //delete element from array
  deleteObjByProperty(arr, attr, value) {
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
}
