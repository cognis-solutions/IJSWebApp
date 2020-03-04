import { Component, OnInit, Output, EventEmitter,ComponentRef } from '@angular/core';
import * as $ from 'jquery';
declare var UIkit: any;
import { LookUpdataServiceService } from '../../../common-services/look-updata-service.service';
import { ServerErrorcodeService } from "../../../common-services/server-errorcode.service";
import { SortSearchTableService } from '../../../rcl-application//contract-search/sort-search-table.service';
import { ImdgClassService } from '../../../common-services/imdg-class.service';
import { Observable } from 'rxjs/Observable';
import { ModalDialogModule, IModalDialog, ModalDialogService, IModalDialogOptions, IModalDialogButton } from 'ngx-modal-dialog';
import * as _ from 'lodash';
import { ContractSearchService } from "app/rcl-application/contract-search/contract-search.service";
import { SpinnerServiceService } from "app/common-services/spinner-service.service";
import { SessionTimeOutService } from "app/common-services/session-time-out.service";

@Component({
  selector: 'app-imdg-class-setup',
  templateUrl: './imdg-class-setup.component.html',
  styleUrls: ['./imdg-class-setup.component.scss']
})
export class ImdgClassSetupComponent implements OnInit,IModalDialog {

  constructor(private imdgService:ImdgClassService,public _joborderService: ContractSearchService,private _spinner: SpinnerServiceService,private _sessionTimeOutService:SessionTimeOutService) {
    this.actionButtons = [
      { text: 'Add', onAction:() => this.addRow()
        }, // no special processing here
      { text: 'Delete', onAction: () => this.deleteRow()
        },
      { text: 'Save', onAction: () => this.saveRow() 
        },
      { text: 'Close', onAction: () => this.close() }
    ];
   }
  @Output() public imDgClassDataList = new EventEmitter();
  @Output() public imDgClassDataListforAddEdit = new EventEmitter();
  imDgList: any = [];
  elementsToDel: any = [];
  actionButtons: IModalDialogButton[];
  //checking from which component it is being called
  componentName: string;
  showAlertPopup:boolean;
  showMandatoryFieldPopup:boolean;
  showClassMandatoryFieldPopup:boolean;
  showValidValuesError:boolean;
  showIncludeExcludeUnnoValuesError:boolean;
  selectedContractRow:any=[];
  dialogInit(reference: ComponentRef<IModalDialog>, options: Partial<IModalDialogOptions<any>>) {
    // no processing needed
    this.componentName = options.data.checkComponent;
    this.selectedContractRow = options.data.selectedContarctRow;
    //console.log("dialog oninit called of IMDG Class Component"+ JSON.stringify(options.data));
    this.showMOdal(options.data.imdgList,options.data.checkComponent, options.data.selectedContarctRow.fromTerminal);      
  }
  
  terminalDepotCode:any;
  showMOdal(val, callingComponent,terminalDepotCode) {     
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
      "imdgClass": undefined,
      "excludeUnno": undefined,
      "includeUnno": undefined,      
      "action":"N"
    }
    //#NIIT CR4 >>>>END
    this.imdgService.setImdgList(rowObj);
    this.imDgList = this.imdgService.imdgList;

    //scroll added data to the bottom
    //#NIIT CR4 >>>>BEGIN
    var scrollValue= $('.imdgClassTable').height(); 
    var actualScrVal = scrollValue + 50;
    setTimeout(function() {
      $('.imdgClassTable').scrollTop(actualScrVal);
    }, 300);
    //#NIIT CR4 >>>>END
    return false;   
  }

  //delete row
  imdgDataAfterDelete:any=[];
  imdgListDeleteData:any=[];
  deleteRowValidation:boolean;
  confirmDeleteValidation:boolean;
  alertRecordsDeleted:boolean;
  deleteRow() : boolean {
    //#NIIT CR4 >>>>BEGIN
    //if row is not selected
    if(this.elementsToDel.length == 0){
      this.deleteRowValidation = true;
    }
    //if row is slected
    else{
       this.confirmDeleteValidation = true;
    }    
    //#NIIT CR4 >>>>END 
      return false;
    }

   //#NIIT CR4 >>>>BEGIN 
   //if yes is selected from popup
  delNewRecordsOnly:boolean;
  delDbRecords:boolean; 
  deleteImdgList(){   

    var indexArray:any =[];
    this.imDgList = this.imdgService.imdgList;
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
      for (var j = 0; j < this.imDgList.length; j++) {
        var element = this.imDgList[j];

        if(indexArray[i]  == element.seqNo){
          this.imDgList = this.deleteObjByProperty(this.imDgList, 'seqNo', element.seqNo);
        }        
      }     
    } 
    if(this.imDgList.length !=0 ){
      this.imdgService.imdgList = this.imDgList;
      this.imdgService.imdgList.forEach((element)=>{
        if(element['action'] !=undefined && element['action']=="N"){
          newRecords.push(element);
        }
      })
    }


	  //#NIIT CR4 >>>>BEGIN
    this.elementsToDel.forEach((element)=>{
      element['action']="D";
      this.imdgListDeleteData.push({"portImdgCode":element.portImdgCode,"portImdgSeqNo":element.portImdgSeqNo,"action":element.action});
    });   

    var action = "saveImdgSetup";    
    var portImdgType = "IMDG";     
    this._spinner.showSpinner();
    var backendData = this._joborderService.deletePortImdgSetupFromList({"terminalDepotCode": this.terminalDepotCode,"imdgList":this.imdgListDeleteData,"action":action,"portImdgType":portImdgType});
    backendData.subscribe(
      (data) => {
        if(data == "userSessionExpired"){
          this._sessionTimeOutService.checkSessionTimeout(data);
        }
        else if (data.hasOwnProperty("errorCode")) {          
        
        } else {          
          this.imdgDataAfterDelete = data['imdgList'];    
          this.imdgService.imdgList = this.imdgDataAfterDelete;
          this.imdgService.imdgList = this.imdgService.imdgList.concat(newRecords);
          this.elementsToDel = [];
          this.imdgListDeleteData= [];
          this._spinner.hideSpinner();
          this.alertRecordsDeleted = true;
          this.confirmDeleteValidation = false;//after delete close the confirmation popup
        }
      });        
  }
  //#NIIT CR4 >>>>END

  saveRowValidation:boolean;
  saveRow() : boolean {    
    let filteredImdgList = [];
    let duplicateFlag : boolean =false;   
    this.imDgList = this.imdgService.imdgList;
    let imdgMandatoryFlag: boolean = false;
    let classMandatoryFlag: boolean = false;
    let inValidFlag: boolean = false;
    let saveCheck:boolean = false;
    let includeExcludeUnnoInValidFlag: boolean = false;   
    

     for(var i = 0; i < this.imDgList.length ; i++){
        if((this.imDgList[i].portImdgCode === undefined || this.imDgList[i].portImdgCode.trim().length == 0)){
            imdgMandatoryFlag = true;
            break;
          } 
          if((this.imDgList[i].imdgClass === undefined || this.imDgList[i].imdgClass.trim().length == 0)){
            classMandatoryFlag = true;
            break;
          } 
      for(var j= i+1 ; j < this.imDgList.length ; j++){  
        if((this.imDgList[j].portImdgCode === undefined || this.imDgList[j].portImdgCode.trim().length == 0)){
          imdgMandatoryFlag = true;
          break;
        }  
        if((this.imDgList[j].imdgClass === undefined || this.imDgList[j].imdgClass.trim().length == 0)){
          classMandatoryFlag = true;
          break;
        }
        if((this.imDgList[i].portImdgCode != undefined && this.imDgList[j].portImdgCode != undefined) && (this.imDgList[i].portImdgCode.toUpperCase() == this.imDgList[j].portImdgCode.toUpperCase()) &&
           (this.imDgList[i].imdgClass != undefined && this.imDgList[j].imdgClass != undefined) && (this.imDgList[i].imdgClass == this.imDgList[j].imdgClass) &&
           ((this.imDgList[i].includeUnno != undefined && this.imDgList[j].includeUnno != undefined) && (this.imDgList[i].includeUnno == this.imDgList[j].includeUnno) ||
           (this.imDgList[i].excludeUnno != undefined && this.imDgList[j].excludeUnno != undefined) && (this.imDgList[i].excludeUnno == this.imDgList[j].excludeUnno))){
          duplicateFlag = true;            
          break;               
        }  
        if((this.imDgList[i].portImdgCode != undefined && this.imDgList[j].portImdgCode != undefined) && (this.imDgList[i].portImdgCode.toUpperCase() == this.imDgList[j].portImdgCode.toUpperCase()) &&
           (this.imDgList[i].imdgClass != undefined && this.imDgList[j].imdgClass != undefined) && (this.imDgList[i].imdgClass == this.imDgList[j].imdgClass) &&
           ((this.imDgList[i].includeUnno == undefined && this.imDgList[j].includeUnno == undefined) || (this.imDgList[i].includeUnno == null && this.imDgList[j].includeUnno == null)) &&
           ((this.imDgList[i].excludeUnno == undefined && this.imDgList[j].excludeUnno == undefined) || (this.imDgList[i].excludeUnno == null && this.imDgList[j].excludeUnno == null))){
          duplicateFlag = true;            
          break;               
        }
        // if((this.imDgList[i].imdgClass == this.imDgList[j].imdgClass) && (this.imDgList[i].includeUnno == this.imDgList[j].includeUnno) && (this.imDgList[i].excludeUnno == this.imDgList[j].excludeUnno)){
        //   duplicateFlag = true;            
        //   break;               
        // }  
      }
      if(imdgMandatoryFlag){
        break;
      }

      if(classMandatoryFlag){
        break;
      }

      if(duplicateFlag){
        break;
      }

      //if neither row is added or deleted
      if(this.imDgList[i].action!=undefined && (this.imDgList[i].action=='N' || this.imDgList[i].action=='U')){
         saveCheck=true;
      }
    }

    if(imdgMandatoryFlag){
      this.showMandatoryFieldPopup=true;      
      return false;
    }

    if(classMandatoryFlag){
      this.showClassMandatoryFieldPopup=true;      
      return false;
    }    

    this.imDgList.forEach(element => {
      var imdgClassData;    
      var includeUnnoData;
      var excludeUnnoData;      
      imdgClassData= element.imdgClass/element.imdgClass;      
      includeUnnoData= element.includeUnno/element.includeUnno;     
      excludeUnnoData= element.excludeUnno/element.excludeUnno;     
       
      
      if(((Number.isNaN(imdgClassData)) || (element.includeUnno != null && Number.isNaN(includeUnnoData))|| (element.excludeUnno != null && Number.isNaN(excludeUnnoData)))){
        inValidFlag=true;                 
      }         
    });

    //validation for include unno and exclude unno
    for (var index = 0; index < this.imDgList.length; index++) {
      var element = this.imDgList[index];
      
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

      filteredImdgList = this.imdgService.imdgList;
      if(this.componentName == "addEditCostRate"){
        this.imdgService.imdgClassSubjectforAddEdit.next(filteredImdgList); //make new subject
      }else{
        this.imdgService.imdgClassSubject.next(filteredImdgList);
      }     
       
      filteredImdgList = [];
      return false;  
        
    } else{
      this.showAlertPopup=true;      
      return false;              
    }

  }

  ngOnInit() {
    // this.addRow();
  }

  closeWindow() {
    this.imDgList = [];    
  }

  getImdgClassList(){
    return  this.imdgService.imdgList;
  }
  
  openImdgClassModal(){
      
  }

  //#NIIT CR4 >>>>BEGIN
  imdgDataEdited(imdgRow){    
    
    for (var index = 0; index < this.imdgService.imdgList.length; index++) {
      var element = this.imdgService.imdgList[index];
      
      if(element['portImdgCode'] == imdgRow.portImdgCode && element['portImdgSeqNo'] == imdgRow.portImdgSeqNo && element['action'] !="N"){
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
    this.imdgService.refreshImdgSubject.next(this.selectedContractRow)
    return true;
  }

  
  imdgSaved(){
    UIkit.modal('#imdg-success-modal').show();
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
