import { Component, OnInit, ViewChild, Input, Output, EventEmitter} from '@angular/core';
import { Http, Response, Headers, URLSearchParams, RequestOptions } from '@angular/http';
import { Observable } from 'rxjs/Observable';
import 'rxjs/add/operator/map';
import 'rxjs/add/operator/catch'
import { LookUpdataServiceService } from '../../../common-services/look-updata-service.service';
import { SpinnerServiceService } from "../../../common-services/spinner-service.service";
import { ServerErrorcodeService } from "../../../common-services/server-errorcode.service";
import { ProcessjoSortSearchTableService } from "../processjo-sort-search-table.service";
import { ProcessjoSearchService } from "../processjo-search.service";
declare var jQuery: any;
import * as $ from 'jquery';
import { SessionTimeOutService } from "app/common-services/session-time-out.service";

declare var UIkit: any;

@Component({
  selector: 'app-jo-summary-table',
  templateUrl: './jo-summary-table.component.html',
  styleUrls: ['./jo-summary-table.component.scss']
})
export class JoSummaryTableComponent implements OnInit {

  private showLocErrorText: boolean = false;
  private validationTextFlag: boolean = false;
  private validationText: string;
  private errorTextLookUp: string;
  private Remark: string; //Remark
  processJoType: any;
  joSummerySaveObj: any = {};

  joSummeryTableData: any = []


  summaryJoSearchData: any = {
    "summaryJoParam": {
      //   transMode: "Select Transport",
      //   bookingType: "Booking/BL #"
      amount: "120000"
    },
    "action": "joSearch"
  }

  fscLookUpData: any = [{ "label": "FSC", "value": "fsc", "dropDownData": [{ "label": "FSC Code", "value": "FSCCD" }, { "label": "FSC Description", "value": "FSCDESC" }, { "label": "Company Name", "value": "CMPNM" }, { "label": "City", "value": "FSCCITY" }, { "label": "State", "value": "FSCSTAT" }, { "label": "Country", "value": "FSCCCNTY" }, { "label": "Status", "value": "FSCSTS" }] }, { "label": "FSC Name", "value": "FSCNM" }];

  @ViewChild('rclReasonCode') _rclRsnCd;
  @Output() changeFindSummery: EventEmitter<any> = new EventEmitter();
  @Output() hideJoSummery: EventEmitter<any> = new EventEmitter();
  @Output() enableButtons: EventEmitter<any> = new EventEmitter();
  

  constructor(private _spinner: SpinnerServiceService, private _processjoSearchService: ProcessjoSearchService, public _serverErrorCode: ServerErrorcodeService, private _http: Http,private _sessionTimeOutService:SessionTimeOutService) { }

  ngOnInit() { }



  errorCodetext: string;
  joCreationSuccess: boolean = false;
  allJobOrders:any;
  getPrcessJoBackEndData() {
    var joCreationFlag: boolean = false;
    this.joCreationSuccess = false;
    var joData = [];
    this.errorCodetext = "";
    this._spinner.showSpinner();
    let routeBackEndData = this._processjoSearchService.getProcessjoSearchData(this.joSummerySaveObj);
    routeBackEndData.subscribe(
      (data) => {
        if(data == "userSessionExpired"){
              this._sessionTimeOutService.checkSessionTimeout(data);
        }
        data.result.forEach(element =>{
        if(element.hasOwnProperty("errorCode")){
          joCreationFlag = true;  
          this.errorCodetext =  this.errorCodetext + "\r\n" + this._serverErrorCode.checkError(element["errorCode"]) +  element["bokingBL"];
          
          // UIkit.modal('#process-jo-summery-error-modal').show();
          // this._spinner.hideSpinner();  
        } else { 
          element.jobOrders.forEach(element1 => {
            if(element1.mtOrLaden=="F"){
              element1.mtOrLaden="Laden";
              element1['percent']="100%"
            }else{
               element1.mtOrLaden="MT";
               element1['percent']="0%"
            }
          });         
            joData.push(element);
            this.joCreationSuccess = true;

          }
        });
        
        
        if(joCreationFlag){
          UIkit.modal('#process-jo-summery-error-modal').show();
         
        }
        this.joSummeryTableData = joData;
        //to get comma seperated list of all the job orders
        const joSet = new Set();

        for (var index = 0; index < this.joSummeryTableData.length; index++) {

          joData[index].jobOrders.forEach((element)=>{
            if(!(joSet.has(element.jobOrder))){
                joSet.add(element.jobOrder)
                }
              });
             }
          this.allJobOrders = Array.from(joSet).toString();          
          this._spinner.hideSpinner();
      },
      (err) => {
        this._spinner.hideSpinner();
        console.log(err)
      }
    );
  }

  //validation 
  remarkLookupValidation() {
    if (this.Remark) {
      this.showLocErrorText = false;
      return true;
    }
    else {
      if (this.Remark == undefined || this.Remark == "") {
        this.errorTextLookUp = "Please provide Remark field."
        this.showLocErrorText = true;
        return false;
      }
    }
  }

  resetPickDropModal(e) {
    this.Remark = "";
  }

  insertShowRclRsnCd(e) {
    
     var hasLumpSum = false;
    // //check if the job order contains lumpsum
    for (var index = 0; index < this.joSummeryTableData.length; index++) {
      this.joSummeryTableData[index].jobOrders.forEach((element)=>{
            if(element.rateBasis == "L"){
             hasLumpSum = true;
           }
       });        
     }

     if (this.processJoType == 'LAH' || this.processJoType == 'ER') {

    //   //if job orders have lumpsum then ask user whether to create job order or not
      if(hasLumpSum){
         $('#create-adhoc-jo-modal').addClass('uk-open').show(); //#NIIT CR6 >>>>

         //to scroll at the top of the screen
      $('#delete-lumpsum-modal').parent().parent().scrollTop(0);
        $('#delete-lumpsum-modal').parent().parent().css({'overflow':'hidden','top':'50px'});
         

      //$('#add-empty-equipment-setup-modal').parent().parent().parent().scrollTop(0); 
       //$('#add-empty-equipment-setup-modal').parent().parent().parent().css({'overflow':'hidden','top':'50px'});
   }
   //if job orders don't have lump sum then create job orders directly
    else{
      this._rclRsnCd.saveReasonCode();
        
     }

      
  } else {   

      //if job orders have lumpsum then ask user whether to create job order or not
      if(hasLumpSum){
       $('#create-jo-modal').addClass('uk-open').show(); //#NIIT CR6 >>>>
    }
   //if job orders don't have lump sum then create job orders directly
    else{
   this.processJoSummeryBlorBK(e);
    }
     
   }
  }
  //@Output() selectUpdateReasonCodeList = new EventEmitter();
  //checkedRow:any;
  // processJo(e)
  // {
  //  console.log(event);
  //  this.processJoSummeryBlorBK(e)
   
    
  // }
  


  //#NIIT CR6 >>>>BEGIN
  closeWarningForJoAdhoc(e){
    $('#create-adhoc-jo-modal').addClass('uk-open').hide();
    $('#delete-lumpsum-modal').parent().parent().css({'overflow':'visible','top':'50px','overflow-y':'auto'});   

    // $('#add-empty-equipment-setup-modal').parent().parent().parent().css({'overflow':'visible','top':'50px','overflow-y':'auto'});         
    // $('#add-empty-equipment-setup-modal').addClass('uk-open').hide(); 
  }
  //#NIIT CR6 >>>>END

  //when the user click yes incase job orders have lumpsum incase of ad-hoc
  processJoSummeryForAdoc(e){
    this._rclRsnCd.openLookUpModal();
  }

  successfullJobOrder: any = []
  errorText: string
  processJoSummeryBlorBK(e) {
    
    let processjoFieldList = [];
    this.joSummeryTableData.forEach(element => {
      let tempobj = {
        'routingId': element.joSummery.routingId,
        'vendorCode': element.joSummery.vendorCode,
        'remark': element.joSummery.remark,
        'amount': element.joSummery.amount,
        'joDate': element.joSummery.joDate,
        'jobOrder': element.joSummery.jobOrder,
        'slNumber': element.joSummery.slNumber,
        'paymentFSC': element.joSummery.paymentFSC
      }
      processjoFieldList.push(tempobj);
    });
    let tempObj = {
      "routingId": this.joSummerySaveObj.lstJOSummaryParam[0].routingId,
      "vendorCode": this.joSummerySaveObj.vendorCode,
      "processJoType": this.processJoType,
      "action": 'createJob',
    }
    tempObj['processjoFieldList'] = processjoFieldList

    let routeBackEndData = this._processjoSearchService.getProcessjoSearchData(tempObj);
    this._spinner.showSpinner();
    routeBackEndData.subscribe(
      (data) => {
        if(data == "userSessionExpired"){
          UIkit.modal('#successfull-Job-Order-modal-sections').hide();
          this._sessionTimeOutService.checkSessionTimeout(data);              
          }
        else if (data.hasOwnProperty("errorCode")) {
          this.errorText = data['errorCode'];
        } else {
          this.successfullJobOrder = data;
          UIkit.modal('#successfull-Job-Order-modal-sections').show();          
          //this.joSummeryTableData = [];
        }
        this._spinner.hideSpinner();
      },
      (err) => {
        this._spinner.hideSpinner();
        console.log(err)
      }
    );
  }

  resonCode: any;
  updateResonCode(e) {
    this.resonCode = e;
    this.processJoSummeryAddHoc(this.resonCode)
  }

  processJoSummeryAddHoc(reasonCode) {
    let processjoFieldList = []

    this.joSummeryTableData.forEach(element => {
      let tempobj = {
        'routingId': element.joSummery.routingId,
        'vendorCode': element.joSummery.vendorCode,
        'remark': element.joSummery.remark,
        'amount': element.joSummery.amount,
        'joDate': element.joSummery.joDate,
        'jobOrder': element.joSummery.jobOrder,
        'paymentFSC': element.joSummery.paymentFSC
      }
      processjoFieldList.push(tempobj);
    });
    let tempObj = {
      "reasonCode": "ADTRU",
      "routingId": this.joSummerySaveObj.routingList.routingId,
      "vendorCode": this.joSummerySaveObj.vendorCode,
      "processJoType": this.processJoType,
      "action": 'createJoAdhoc',
    }
    tempObj['processjoFieldList'] = processjoFieldList

    let routeBackEndData = this._processjoSearchService.getProcessjoSearchData(tempObj);
    this._spinner.showSpinner();
    routeBackEndData.subscribe(
      (data) => {
        if(data == "userSessionExpired"){
          UIkit.modal('#successfull-Job-Order-modal-sections').hide();
          this._sessionTimeOutService.checkSessionTimeout(data);              
        }
        else if (data.hasOwnProperty("errorCode")) {

        } else {
          this.successfullJobOrder = data;
          UIkit.modal('#successfull-Job-Order-modal-sections').show();
        }     
          this._spinner.hideSpinner();  
      },      
      (err) => {
        this._spinner.hideSpinner();
        console.log(err)
      }
    );
  }

  changeFind(e) {
    UIkit.modal('#successfull-Job-Order-modal-sections').hide();
    this.changeFindSummery.emit(e);
  }

  resetJoSummeryTable(e) {
    let tempObj = {
      'action': 'resetJoSummury'
    }
    let routeBackEndData = this._processjoSearchService.getProcessjoSearchData(tempObj);
    routeBackEndData.subscribe(
      (data) => {
         if(data == "userSessionExpired"){
              this._sessionTimeOutService.checkSessionTimeout(data);
          }
         else if(data['errorCode'] == 'IJS_MSG_JO_RESET_SUCCESS' ) { //this code for msg
           this.joSummeryTableData = [];
           this.hideJoSummery.emit(e);
           this._spinner.hideSpinner();
         }
        this.hideJoSummery.emit(e);
        this._spinner.hideSpinner();
      },
      (err) => {
        this.hideJoSummery.emit(e);
        this._spinner.hideSpinner();
        console.log(err)
      }
    );
  }

  closeErrModal(e) {
    //this.hideJoSummery.emit(e);
    if(this.joCreationSuccess != true){    
      this.enableButtons.emit(e);
    }
    UIkit.modal('#process-jo-summery-error-modal').hide();
  }


  //#NIIT CR6 >>>>BEGIN
  lumpSumToDel:any = [];
  disableLumpSumbtn:boolean = true;
  //method to select the lump sum records to delete
  joSummarySelected(e, jobOrderRowData , lumpsumId){
    if (e.target.checked) {      
      this.lumpSumToDel.push(jobOrderRowData);      
    } else {      
      this.lumpSumToDel = this.deleteObjByLumpSumID(this.lumpSumToDel, 'lumpsumId', jobOrderRowData.lumpsumId);
    }
    
    if(this.lumpSumToDel.length > 0){
      this.disableLumpSumbtn = false;
    }else{
      this.disableLumpSumbtn = true;
    }
  }

  //method to delete the array objects based on attribute and value
  deleteObjByLumpSumID(arr, attr, value){
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

  //method to ask user whether to delete the lump sum records or not
  deleteLumpSum(e){
     $('#delete-lumpsum-modal').addClass('uk-open').show(); //#NIIT CR6 >>>>

     //to scroll to the top
     $('#delete-lumpsum-modal').parent().parent().scrollTop(0);
     $('#delete-lumpsum-modal').parent().parent().css({'overflow':'hidden','top':'50px'});

     //$('#add-empty-equipment-setup-modal').parent().parent().parent().scrollTop(0); 
     //$('#add-empty-equipment-setup-modal').parent().parent().parent().css({'overflow':'hidden','top':'50px'});
  }

  //#NIIT CR6 >>>>BEGIN
  closeWarning(e){
    $('#delete-lumpsum-modal').addClass('uk-open').hide();
    $('#delete-lumpsum-modal').parent().parent().css({'overflow':'visible','top':'50px','overflow-y':'auto'});         


    // $('#add-empty-equipment-setup-modal').parent().parent().parent().css({'overflow':'visible','top':'50px','overflow-y':'auto'});         
    // $('#add-empty-equipment-setup-modal').addClass('uk-open').hide(); 
  }
   //#NIIT CR6 >>>>END

  //method to delete lump sum records after user confirmation
  deleteLumpSumConfirm(){
   
    var prevJobOrder = '';
    var jobOrder = '';
    var nextJobOrder='';
    var deleteLumpSumObject = [];
    var jobOrders = [];
     var lumpSum ='';
     var count=1;

     for (var i = 0; i < this.lumpSumToDel.length; i++) {
        count=1;
        if(i==0){
          jobOrders.push(this.lumpSumToDel[i].jobOrder);
        }else if(jobOrders.indexOf(this.lumpSumToDel[i].jobOrder)>=0){
         continue;
       }else{
         jobOrders.push(this.lumpSumToDel[i].jobOrder);
       }
       for (var j = 0; j < this.lumpSumToDel.length; j++) {
          if(this.lumpSumToDel[i].jobOrder==this.lumpSumToDel[j].jobOrder){
            if(count==1){
              lumpSum=this.lumpSumToDel[j].lumpsumId;
            }else{
              lumpSum=lumpSum+","+this.lumpSumToDel[j].lumpsumId;
            }
           count=count+1;
          }
       }
       deleteLumpSumObject.push(this.lumpSumToDel[i].jobOrder + ":"+ lumpSum );
     }

     //call method to delete the lum sum records from jo summary
     this.deleteLumpSumRows(deleteLumpSumObject);     
     
  }

  //method to delete lump sum records
  deleteLumpSumRows(lumpSumObject){
    this.disableLumpSumbtn = true; //disable the delete lump sum button
    var joCreationFlag: boolean = false;
    this.joCreationSuccess = false;
    var joData = [];
    this.errorCodetext = "";
    this._spinner.showSpinner();
    let routeBackEndData = this._processjoSearchService.deleteProcessJoLumpSum({"jobOrderLst":lumpSumObject,"action":"deleteLumpsum","allJobOrders":this.allJobOrders});
    routeBackEndData.subscribe((data)=>{

      if(data == "userSessionExpired"){
         this._sessionTimeOutService.checkSessionTimeout(data);
      }
      //if only single record is present in lump sum and that is deleted
      else if(data['errorCode'] == "NO_RATE_AVAILABLE" ) {
        this.errorCodetext =  this._serverErrorCode.checkError(data["errorCode"]);
        $('#no-rate-available-process-jo-modal').addClass('uk-open').show();         
        this._spinner.hideSpinner();
      } 
      else{      
      data.result.forEach(element =>{        
          if(element.hasOwnProperty("errorCode")){
          joCreationFlag = true;  
          this.errorCodetext =  this.errorCodetext + "\r\n" + this._serverErrorCode.checkError(element["errorCode"]) +  element["bokingBL"];          
        } else { 
          element.jobOrders.forEach(element1 => {
            if(element1.mtOrLaden=="F"){
              element1.mtOrLaden="Laden";
              element1['percent']="100%"
            }else{
               element1.mtOrLaden="MT";
               element1['percent']="0%"
            }
          });         
            joData.push(element);
            this.joCreationSuccess = true;
          }
        });        
        
        if(joCreationFlag){
          UIkit.modal('#process-jo-summery-error-modal').show();
        }

        //to get comma seperated list of all the job orders
        const filteredJoSet = new Set();

        for (var index = 0; index < this.joSummeryTableData.length; index++) {

          joData[index].jobOrders.forEach((element)=>{
            if(!(filteredJoSet.has(element.jobOrder))){
                filteredJoSet.add(element.jobOrder)
                }
              });
             }
          this.allJobOrders = Array.from(filteredJoSet).toString();

        this.joSummeryTableData = joData;
        this.lumpSumToDel = [];        
        this._spinner.hideSpinner();
        $('#delete-lumpsum-success-process-jo-modal').addClass('uk-open').show();         
      }
     },
      (err)=>{
        this._spinner.hideSpinner();
        console.log(err)
      });
     }

     //method to hide to lump sum records deletion success
     refreshJoSummary(){
       $('#delete-lumpsum-success-process-jo-modal').addClass('uk-open').hide();
       $('#delete-lumpsum-modal').parent().parent().css({'overflow':'visible','top':'50px','overflow-y':'auto'});
       //$('#add-empty-equipment-setup-modal').parent().parent().parent().css({'overflow':'visible','top':'50px','overflow-y':'auto'});         
       //$('#add-empty-equipment-setup-modal').addClass('uk-open').hide();    
     }

     resetJoSummary(){
      this.joSummeryTableData = [];
      this.hideJoSummery.emit('');
      $('#no-rate-available-process-jo-modal').addClass('uk-open').hide();
      $('#delete-lumpsum-modal').parent().parent().css({'overflow':'visible','top':'50px','overflow-y':'auto'}); 
      // $('#add-empty-equipment-setup-modal').parent().parent().parent().css({'overflow':'visible','top':'50px','overflow-y':'auto'});         
      // $('#add-empty-equipment-setup-modal').addClass('uk-open').hide();     
     }
  //#NIIT CR6 >>>>END

}
