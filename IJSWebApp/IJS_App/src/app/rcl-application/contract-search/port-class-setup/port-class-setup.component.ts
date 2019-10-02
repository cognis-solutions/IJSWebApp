import { Component, OnInit, Output, EventEmitter, ComponentRef } from '@angular/core';
import * as $ from 'jquery';
declare var UIkit: any;
import { LookUpdataServiceService } from '../../../common-services/look-updata-service.service';
import { ServerErrorcodeService } from "../../../common-services/server-errorcode.service";
import { SortSearchTableService } from '../../../rcl-application//contract-search/sort-search-table.service';
import { PortClassService } from '../../../common-services/port-class.service';
import { Observable } from 'rxjs/Observable';
import { ModalDialogModule, IModalDialog, ModalDialogService, IModalDialogOptions, IModalDialogButton } from 'ngx-modal-dialog';
import * as _ from 'lodash';
import { ContractSearchService } from "app/rcl-application/contract-search/contract-search.service";
import { SpinnerServiceService } from "app/common-services/spinner-service.service";
import { SessionTimeOutService } from "app/common-services/session-time-out.service";

@Component({
  selector: 'app-port-class-setup',
  templateUrl: './port-class-setup.component.html',
  styleUrls: ['./port-class-setup.component.scss']
})
export class PortClassSetupComponent implements OnInit,IModalDialog {
  showAlertPopup:boolean;
  actionButtons: IModalDialogButton[];
  constructor(private portService:PortClassService,public _joborderService: ContractSearchService,private _spinner: SpinnerServiceService,private _sessionTimeOutService:SessionTimeOutService) {
    this.actionButtons = [
      { text: 'Add', onAction:() => this.addRow() }, // no special processing here
      { text: 'Delete', onAction: () => this.deleteRow() },
      { text: 'Save', onAction: () => this.saveRow() },
      { text: 'Close', onAction: () => this.close() }
    ];
   }
  @Output() public portClassDataList = new EventEmitter();
  @Output() public portClassDataListforAddEdit = new EventEmitter();
  @Output() public closeAndRefreshTable = new EventEmitter();
  showMandatoryFieldPopup:boolean;
  showClassMandatoryFieldPopup:boolean;
  portList: any = [];
  elementsToDel: any = [];
  componentName: string;
  showValidValuesError:boolean; 
  showIncludeExcludeUnnoValuesError:boolean;
  deleteRowValidation:boolean;
  confirmDeleteValidation:boolean;
   ngOnInit() {    
    
  }

  selectedContractRow:any=[];
  dialogInit(reference: ComponentRef<IModalDialog>, options: Partial<IModalDialogOptions<any>>) {
     // no processing needed
     this.componentName = options.data.checkComponent;
     this.selectedContractRow = options.data.selectedContarctRow;    
    this.showMOdal(options.data.portClassList,options.data.checkComponent,options.data.selectedContarctRow.fromTerminal);      
  }


  terminalDepotCode:any;
  showMOdal(val,callingComponent,terminalDepotCode) {   
    this.elementsToDel = [];
    this.terminalDepotCode = terminalDepotCode;//#NIIT CR4 >>>>BEGIN   
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
    
    //#NIIT CR4 >>>>BEGIN
    let rowObj = {
      "portImdgCode":undefined,
      "portClass": undefined,
      "excludeUnno": undefined,
      "includeUnno": undefined,     
      "action":"N"
    }
    //#NIIT CR4 >>>>END
    this.portService.setPortClassList(rowObj);
    this.portList = this.portService.portClassList;
    //scroll added data to the bottom
    var scrollValue= $('.portClassTable').height(); 
    var actualScrVal = scrollValue + 50;
    setTimeout(function() {
      $('.portClassTable').scrollTop(actualScrVal);
    }, 300);     
   
    return false;
      
  }

  //delete row
  portDataAfterDelete:any=[];
  portListDeleteData:any=[];
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
  deletePortList(){
    var indexArray:any =[];
    this.portList = this.portService.portClassList;
    var dbRecords:any = [];
    var newRecords:any= [];

    //logic for only add
    for (var index = 0; index < this.elementsToDel.length; index++) {
      var element = this.elementsToDel[index];      

      //if only db
      if(element.hasOwnProperty('oldPortImdgCode') && ((element.action == undefined)  || (element.action == "U"))){
        this.delDbRecords = true;
        dbRecords.push(element);
      }      
       else{
        indexArray.push(element.seqNo); 
        this.delNewRecordsOnly = true;       
      }
    }
    
    for (var i = 0; i < indexArray.length; i++) {
      for (var j = 0; j < this.portList.length; j++) {
        var element = this.portList[j];

        if(indexArray[i]  == element.seqNo){
          this.portList = this.deleteObjByProperty(this.portList, 'seqNo', element.seqNo);
        }        
      }     
    } 
    if(this.portList.length !=0 ){
      this.portService.portClassList = this.portList;
      this.portService.portClassList.forEach((element)=>{
        if(element['action'] !=undefined && element['action']=="N"){
          newRecords.push(element);
        }
      })
    }
    
	  //#NIIT CR4 >>>>BEGIN
    dbRecords.forEach((element)=>{
      element['action']="D";
      this.portListDeleteData.push({"portImdgCode":element.portImdgCode,"portImdgSeqNo":element.portImdgSeqNo, "action":element.action});
    });   

    var action = "savePortSetup";    
    var portImdgType = "PORT"; 
    this._spinner.showSpinner();
      var backendData = this._joborderService.deletePortImdgSetupFromList({"terminalDepotCode": this.terminalDepotCode,"portList":this.portListDeleteData,"action":action,"portImdgType":portImdgType});
      backendData.subscribe(
      (data) => {
        if(data == "userSessionExpired"){
          this._sessionTimeOutService.checkSessionTimeout(data);
        }
        else if (data.hasOwnProperty("errorCode")) {

        } else {                    
          this.portDataAfterDelete = data['portList'];    
          this.portService.portClassList = this.portDataAfterDelete;
          this.portService.portClassList = this.portService.portClassList.concat(newRecords);
          this.elementsToDel = [];
          this.portListDeleteData= [];
          this._spinner.hideSpinner();
          this.alertRecordsDeleted = true;
          this.confirmDeleteValidation = false;//after delete close the confirmation popup
        }
      });    
  }

  saveRowValidation:boolean;
  saveRow() : boolean {
    let filteredPortList = [];
    let duplicateFlag : boolean =false; 
    let portMandatoryFlag: boolean = false;
    let classMandatoryFlag: boolean = false;
    this.portList = this.portService.portClassList;
    let inValidFlag: boolean = false;
    let includeExcludeUnnoInValidFlag: boolean = false;
    let saveCheck:boolean = false;
    

    for(var i = 0; i < this.portList.length ; i++){

      if((this.portList[i].portImdgCode === undefined || this.portList[i].portImdgCode.trim().length == 0)){
            portMandatoryFlag = true;
            break;
          } 
      if((this.portList[i].portClass === undefined || this.portList[i].portClass.trim().length == 0)){
            classMandatoryFlag = true;
            break;
          }    
      for(var j= i+1 ; j < this.portList.length ; j++){ 
        if((this.portList[j].portImdgCode === undefined || this.portList[j].portImdgCode.trim().length == 0)){
            portMandatoryFlag = true;
            break;
          }  
        if((this.portList[j].portClass === undefined || this.portList[j].portClass.trim().length == 0)){
            classMandatoryFlag = true;
            break;
          }         
        if((this.portList[i].portImdgCode != undefined && this.portList[j].portImdgCode != undefined) && (this.portList[i].portImdgCode.toUpperCase() == this.portList[j].portImdgCode.toUpperCase()) &&
           (this.portList[i].portClass != undefined && this.portList[j].portClass != undefined) && (this.portList[i].portClass == this.portList[j].portClass) &&
           ((this.portList[i].includeUnno != undefined && this.portList[j].includeUnno != undefined) && (this.portList[i].includeUnno == this.portList[j].includeUnno) ||
           (this.portList[i].excludeUnno != undefined && this.portList[j].excludeUnno != undefined) && (this.portList[i].excludeUnno == this.portList[j].excludeUnno))){
          duplicateFlag = true;            
          break;               
        }  
        if((this.portList[i].portImdgCode != undefined && this.portList[j].portImdgCode != undefined) && (this.portList[i].portImdgCode.toUpperCase() == this.portList[j].portImdgCode.toUpperCase()) &&
           (this.portList[i].portClass != undefined && this.portList[j].portClass != undefined) && (this.portList[i].portClass == this.portList[j].portClass) &&
           ((this.portList[i].includeUnno == undefined && this.portList[j].includeUnno == undefined) || (this.portList[i].includeUnno == null && this.portList[j].includeUnno == null)) &&
           ((this.portList[i].excludeUnno == undefined && this.portList[j].excludeUnno == undefined) || (this.portList[i].excludeUnno == null && this.portList[j].excludeUnno == null))){
          duplicateFlag = true;            
          break;               
        }
        // if((this.portList[i].portClass == this.portList[j].portClass) && (this.portList[i].includeUnno == this.portList[j].includeUnno) && (this.portList[i].excludeUnno == this.portList[j].excludeUnno)){
        //   duplicateFlag = true;            
        //   break;               
        // }  
      }

      if(portMandatoryFlag){
        break;
      }

      if(classMandatoryFlag){
        break;
      }

      if(duplicateFlag){
        break;
      }

      //if neither row is added or deleted
      if(this.portList[i].action!=undefined && (this.portList[i].action=='N' ||this.portList[i].action=='U')){
         saveCheck=true;
      }
    }

    if(portMandatoryFlag){
      this.showMandatoryFieldPopup=true;      
      return false;
    }

    if(classMandatoryFlag){
      this.showClassMandatoryFieldPopup=true;      
      return false;
    }

    this.portList.forEach(element => {
      var portClassData;    
      var includeUnnoData;
      var excludeUnnoData;      
      portClassData= element.portClass/element.portClass;      
      includeUnnoData= element.includeUnno/element.includeUnno;     
      excludeUnnoData= element.excludeUnno/element.excludeUnno;  

      if(((Number.isNaN(portClassData)) || (element.includeUnno != null && Number.isNaN(includeUnnoData))|| (element.excludeUnno != null &&  Number.isNaN(excludeUnnoData)))){
        inValidFlag=true;                 
      }         
    });

    //validation for include unno and exclude unno
    for (var index = 0; index < this.portList.length; index++) {
      var element = this.portList[index];
      
      var includeUnnoValid:boolean = true; 
      var excludeUnnoValid:boolean = true;

      //regex to check if length of the number is 4 or not
      var regex = RegExp('^[0-9]{4}$'); 
      
      if(element['includeUnno'] != undefined){
         includeUnnoValid = regex.test(element.includeUnno);
      }else if(element['excludeUnno'] != undefined){
         excludeUnnoValid = regex.test(element.excludeUnno);
      }

      if(!includeUnnoValid){
        includeExcludeUnnoInValidFlag=true;        
        break;  
      }

      if(!excludeUnnoValid){
        includeExcludeUnnoInValidFlag=true;        
        break;  
      }
      
    }

    if(inValidFlag){      
      this.showValidValuesError=true;
      return false;
    }

    //if include or exclude unno is not valid show error
    if(includeExcludeUnnoInValidFlag){
      this.showIncludeExcludeUnnoValuesError = true;
      return false;
    }

    

    if(!duplicateFlag){
      //alert when no row is added or updated
      if(!saveCheck){
          this.saveRowValidation = true;
          return;  
      }

      filteredPortList = this.portService.portClassList;           
      
      if(this.componentName == "addEditCostRate"){       
        this.portService.portClassSubjectforAddEdit.next(filteredPortList); //make new subject
      }else{
        this.portService.portClassSubject.next(filteredPortList); 
      }
      
      filteredPortList = [];      
      return false; //#NIIT CR4 >>>>BEGIN
        
    } else{
      //alert('duplicate record exists');
      this.showAlertPopup=true;
      return false;              
    }
    
   
    
  }

  getPortClassList(){
    return  this.portService.portClassList;
  }
  
  openPortClassModal(){
     //UIkit.modal('#port-class-setup-modal').show();  
  }

  //#NIIT CR4 >>>>BEGIN
  portDataEdited(portRow){

    for (var index = 0; index < this.portService.portClassList.length; index++) {
      var element = this.portService.portClassList[index];      
         
      if(element['portImdgCode'] == portRow.portImdgCode && element['portImdgSeqNo'] == portRow.portImdgSeqNo && element['action'] !="N"){
        element['action']="U";
      }
      if(element['includeUnno'] != undefined && element['includeUnno'].trim().length == 0){        
        element['includeUnno'] = null;
        element['excludeUnno'] = undefined;
      } 
      else if(element['excludeUnno'] != undefined && element['excludeUnno'].trim().length == 0){        
        element['excludeUnno'] = null;
        element['includeUnno'] = undefined;        
      }
    }
  }

  close() : boolean{    
    this.portService.refreshSubject.next(this.selectedContractRow)
    return true;
  }

  portSaved(){
    UIkit.modal('#port-success-modal').show();
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
  //#NIIT CR4 >>>>END

  

}
