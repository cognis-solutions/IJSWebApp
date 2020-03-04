import { Component, OnInit, ViewChild, Output, EventEmitter, Input } from '@angular/core';
import * as _ from 'lodash';
declare var UIkit: any;
declare var jQuery: any;
import * as $ from 'jquery';



import { NgModel, NG_VALUE_ACCESSOR, NG_VALIDATORS, NG_ASYNC_VALIDATORS } from '@angular/forms';
import { noop } from 'rxjs/util/noop';



import { Http, Response, Headers, URLSearchParams, RequestOptions } from '@angular/http';
import { Observable } from 'rxjs/Observable';
import 'rxjs/add/operator/map';
import 'rxjs/add/operator/catch';
import { SessionTimeOutService } from "app/common-services/session-time-out.service";
import { TestBed } from '@angular/core/testing';

declare var UIkit: any;
declare var jQuery: any;
import { RouteListModalComponent } from "../../../rcl-components/route-list-modal/route-list-modal.component";
//import { RouteListModalComponent } from "../../../rcl-components/route-list-modal/route-list-modal.component";
import { ProcessjoSortSearchTableService } from "app/rcl-application/process-jo/processjo-sort-search-table.service";
import { SpinnerServiceService } from "app/common-services/spinner-service.service";
import { LookUpdataServiceService } from 'app/common-services/look-updata-service.service';
import { ServerErrorcodeService } from "app/common-services/server-errorcode.service";
import { SortSearchTableService } from '../../contract-search/sort-search-table.service';
import { ProcessjoSearchService } from "../processjo-search.service";
import { debug } from 'util';
import { modelGroupProvider } from '@angular/forms/src/directives/ng_model_group';
@Component({
  selector: 'app-process-joadhoc',
  templateUrl: './process-joadhoc.component.html',
  styleUrls: ['./process-joadhoc.component.scss']
})
export class ProcessJOAdhocComponent implements OnInit {



  //vendorCode: string = "";
  selectedRowToUpdateRoute: any;
  EqDetailsList: any = [];
  processJoSearchData: any;
  routeListBookingType: string = "adhoc";
  routeContractID; //variable to hold the selected route contractID
  private pc = 1;
  @ViewChild('joSummary2') joSummary2: any;
  @ViewChild('prosessjoRouteList') _prosessjoRouteList;
  @ViewChild('prosessjoEquipmentBrowser') _prosessjoEquipmentBrowser;
  @Output() closeAddHoc: EventEmitter<any> = new EventEmitter();
  //@Output() prosessjoRouteList;
  @Input() private routeListInputValue: any;
  @Input() private addHocTypeName: any;
  @Input() private addHocTypeCode: any;
  @Input() private value: any;
  @Input() private routeDetails: any;
  @Output() uploadExcel: EventEmitter<any> = new EventEmitter();
  //@Input() prosessjoRouteList: RouteListModalComponent;
  callingComponent : string;
  @Input() private uploadListLimit: any;//#NIIT CR3 
  //@Input() private addHocSearchData: any;
 // private routeListModalComponent:RouteListModalComponent
  @Input() selectUpdateRouteListSearch:any;
  private resultsPerPage = 5;
  routeListModalComponentobject:RouteListModalComponent;
  contractIDS:any
  _sessionTimeOutService: SessionTimeOutService;
  constructor(public _rocessjoSearchService: ProcessjoSearchService,public _sortTable: SortSearchTableService, private _spinner: SpinnerServiceService,private  _lookupData: LookUpdataServiceService, private _serverErrorCode: ServerErrorcodeService, private _http: Http,_sessionTimeOutService: SessionTimeOutService) { }

  fromLoc:any;
  toLoc:any;
  fromTerminal:any;
  toTerminal:any;
  froLocType:any;
  toLocType:any;
  transportType:any;

  ngOnInit()
   {
  
    //this.insertLegshowRouteList();
    
  //nikash
this.fromLoc = this._rocessjoSearchService.processJoSearchData.processJoParam.fromLocation
this.toLoc= this._rocessjoSearchService.processJoSearchData.processJoParam.toLocation;
this.fromTerminal = this._rocessjoSearchService.processJoSearchData.processJoParam.fromTerminal
this.toTerminal=this._rocessjoSearchService.processJoSearchData.processJoParam.toTerminal;
this.froLocType = this._rocessjoSearchService.processJoSearchData.processJoParam.fromLocType
this.toLocType= this._rocessjoSearchService.processJoSearchData.processJoParam.toLocType;
this.transportType = this._rocessjoSearchService.processJoSearchData.processJoParam.transMode;
this.vendoCode= this._rocessjoSearchService.processJoSearchData.processJoParam.vendorCode;
//this.contractId= this._rocessjoSearchService.processJoSearchData.processJoParam.contractId;

 let searchDetails = {
  "fromLocType" : this.froLocType,
  "toLocType": this.toLocType,
  "fromLocation": this.fromLoc,
  "fromTerminal": this.fromTerminal,
  "toLocation": this.toLoc,
  "toTerminal": this.toTerminal,
  "transMode" : this.transportType,
  "vendorCode": this.vendoCode,
 
}

  this.newAdhoc(searchDetails);

  this.serviceForEqBrowser();
  }

  insertLegshowRouteList() {    
 //this._prosessjoRouteList.joType = this.addHocTypeCode;//jo type    
    this.callingComponent = "adHocType"
    this._prosessjoRouteList.openLookUpModal(this.callingComponent);
    //this.filteredEquipmentBrowserList = []; //empty the list when insert leg is clicked 
    //this.eqListFromUpload = [];//empty the list coming from upload equipment
    //this.eqListFromAvailableEquipments = [];//empty the list coming from available equipment
    //this._prosessjoEquipmentBrowser.emptyContainerList = []; //empty the container list when insert leg is clicked
  }
//nikash
  newAdhoc(searchDetails){
    this.selectedRowToUpdateRoute = searchDetails;
  
  }
  contractId:any;
  days:any;
  dist : any;
  hr :any;
  uom:any;
  routingId:any;
  priority:any;
  fromLocation:any;
  fromLocType:any;
  toLocation:any;
 
  quantity:any;
  EqDetails:{};
  EqList:string[];
  errorTextMsg:any;

  eqNumList(){

    for(var i =0;i<this.filteredEquipmentBrowserList.length;i++)
    {
      this.eqNumberList.push(this.filteredEquipmentBrowserList[i].eqpNum);
    }
}

vendors:any;
 getCostCalculation(e)
 {
  //debugger;
  this.showRouteListModalNew();

  for(var i =0;i<this.filteredEquipmentBrowserList.length;i++)
  {
    this.contSize= this.filteredEquipmentBrowserList[i].size;
    this.contrType= this.filteredEquipmentBrowserList[i].type;
  
    this.quantity= this.filteredEquipmentBrowserList[i].quantityValue

  }
   

    this.EqDetails=this.filteredEquipmentBrowserList;
    this.eqNumList();
    this.joType = this.addHocTypeCode;
    this.actionparam="joSummeryAdhoc"
    this.callingComponent = "processJo";
    this.openModal=true;
    this.openlookUpforCostCalc(
      this.actionparam, 
      this.transportType,
      this.contSize,
      this.contrType,
      this.contractId,
      this.cntSplHandling,
      this.fromLocType,
      this.fromLocation,
      this.fromTerminal,
      this.toLocType,
      this.toLocation,
      this.toTerminal,
      this.days,
      this.dist,
      this.hr,
      this.uom,
      this.routingId,
      this.priority,
      this.joType, 
      this.vendoCode,
      this.EqDetails,
      this.eqNumberList,
      this.vendors
     ); 
    
     
    
   }
   openlookUpforCostCalc(
    actionparam, 
    transportType,
    contSize,
    contrType,
    contractId,
    cntSplHandling,
    fromLocType,
    fromLocation,
    fromTerminal,
    toLocType,
    toLocation,
    toTerminal,
    days,
    dist,
    hr,
    uom,
    routingId,
    priority,
    joType, 
    vendoCode,
    EqDetails,
    eqNumberList,
    vendors)
  {   
    $("#adhoc101").addClass('uk-modal-container').addClass('uk-modal').addClass('uk-open').hide();
    this._spinner.showSpinner();
       this._lookupData.getDataForADHoc(
        actionparam, 
        transportType,
        contSize,
        contrType,
        contractId,
        cntSplHandling,
        fromLocType,
        fromLocation,
        fromTerminal,
        toLocType,
        toLocation,
        toTerminal,
        days,
        dist,
        hr,
        uom,
        routingId,
        priority,
        joType, 
        vendoCode,
        EqDetails,
        eqNumberList,
        vendors).subscribe( (data)=> {
     
        
           
             
               if(data[0]["errorCode"]!=undefined){
                window.dispatchEvent(new Event('resize'));
                UIkit.modal("#lookup-popup-input-vendor-open-Cost").show();
              }
              if(data[0].vendorAfterCalc==undefined){
                 if(data[0].vendor==undefined)
                 {
                   var errorCod={'errorCode':"IJS_ADHOC_1000031"};
                  
                  data[0].vendorAfterCalc=[errorCod];
                 }else{
                  var errorCod={'errorCode':"IJS_ADHOC_10031"};
                  
                data[0].vendorAfterCalc=[errorCod];
                 }
              }
              if(data[0].vendorAfterCalc.hasOwnProperty('errorCode'))
              {
               //UIkit.modal('#booking-bl-process-jo').hide();
             
               if(data[0].vendorAfterCalc.errorCode=="IJS_EX_10042"){
                this.errorTextMsg = "Error in Creating JO Summary";
               }else if(data[0].vendorAfterCalc.errorCode=="IJS_EX_10043")
               {
                 this.errorTextMsg =  "No Container Available"
               }else if(data[0].vendorAfterCalc.errorCode=="IJS_EX_10031")
               {
                 this.errorTextMsg =  "Rate Not Found for Booking/BL:"
               }
               else if(data[0].vendorAfterCalc.errorCode=="IJS_ADHOC_10031")
               {
                 this.errorTextMsg =  "Rate Not Found for the selected container(s)";
               }
               else if(data[0].vendorAfterCalc.errorCode=="IJS_EX_10032")
               {
                 this.errorTextMsg =  "JO Cost calculation Failed.";
               }
               else if(data[0].vendorAfterCalc.errorCode=="IJS_EX_10033")
               {
                 this.errorTextMsg =  "There is an issue on exchange rate processing";
               }else if(data[0].vendorAfterCalc.errorCode=="IJS_ADHOC_1000031")
               {
                 this.errorTextMsg =  "There is no Vanders Available for this Location";
               }
             
               UIkit.modal('#rate-not-found-for-process-jo1-Adhoc').show();
     
              }
           
      
        //this.routeListTableDataAdHoc=this.getuiJosnForAVvamder(data);
              if(data[0].vendor.length!=0){
        if(data[0].vendorAfterCalc!=undefined){
        var tempresult=this.getuiJosnForAVvamder(data);
      
        if(tempresult!=null){ 
         
      
          $("#adhoc101").addClass('uk-modal-container').addClass('uk-modal').addClass('uk-open').show();
 
          this.routeListTableDataAdHoc=tempresult;
      
          //UIkit.modal('#booking-bl-process-jo').show();
            //$("#booking-bl-process-jo'").addClass('uk-open').show();
            // $("#adhoc101").addClass('uk-open').show();
           
        
       
       
        }else{
          this.routeListTableDataAdHoc=null;
          UIkit.modal('#lookup-popup-input-vendor-open-Cost').show();
        }
        
      }
    }
    else 
    {

      UIkit.modal('#no-vendors-foound-error').show();
    }
    this.pc = 1;
    this._spinner.hideSpinner();
    }
   
      )
 }


 resultJson=[];
 getuiJosnForAVvamder(data){      
  this.resultJson=[];
  let forSortcost=[];
  let resultJsonObj={};
 // let costCalJson=[]; 
  let temp=[];
 // var temp1=[];
  for(var i=0;i<data[0].vendorAfterCalc.length;i++)
  {
    if(data[0].vendorAfterCalc[i]["amount"]!=undefined && data[0].vendorAfterCalc[i]["amount"]!="" && data[0].vendorAfterCalc[i]!=null)
    {
    forSortcost=data[0].vendorAfterCalc[i];
    }
  }
  if(forSortcost.length==0){
  // alert("rate not found");
   return null;
  }else{
   var countContainerno=0;
  data[0].vendorAfterCalc.sort(function(a, b){return  a.amount - b.amount });

 // temp=data[0].vendorAfterCalc;
 
 for(var i=0;i<data[0].vendorAfterCalc.length;i++)
 {
      var tempCount=0;
      if(temp.length==0)
      {
        temp.push(data[0].vendorAfterCalc[i]);
      }else{
        for(var j=0;j<temp.length;j++)
        {
          if(data[0].vendorAfterCalc[i]['contractNumber']==temp[j]['contractNumber'] &&
          data[0].vendorAfterCalc[i]['vendorCode']==temp[j]['vendorCode'] 
          )
           {
            if(Number(data[0].vendorAfterCalc[i]['amount'])>Number(temp[j]['amount'])){
             temp[j]= data[0].vendorAfterCalc[i];
             tempCount=0;
             break;
            }else{
             tempCount=0;
             break;
            }
          }else{
           tempCount++;
          }
        }
        if(tempCount>0)
        {
         temp.push(data[0].vendorAfterCalc[i]);
        }
      }
 }


  for(var j=0;j<temp.length;j++){
  for(var i = 0; i < data[0].vendor.length; i++){
    if((data[0].vendor[i]["contractId"]==temp[j]["contractNumber"])&&(data[0].vendor[i]["vendorCode"]==temp[j]["vendorCode"]))
    {
     
     if(this.resultJson.length==0){

      
       resultJsonObj=data[0].vendor[i];
       resultJsonObj["amount"]=temp[j]["amount"];
       resultJsonObj["priority"]='1';
       resultJsonObj["bkgOrBLNumber"] =this.bkgOrBLNumber;
       resultJsonObj["bkgOrBLType"] =this.bkgOrBLType;
       resultJsonObj["cntSize"] =this.contSize;
       resultJsonObj["cntType"] =this.contrType;
       resultJsonObj["cntSplHandling"] =this.cntSplHandling;
       resultJsonObj["routingNumber"] =temp[j]["routingNumber"];
       resultJsonObj["fromLocationTyp"]=temp[j]["toLocationTyp"];
       resultJsonObj["transportMode"]=temp[j]["toLocationTyp"];
       break;
     }
     else
     {
       resultJsonObj=data[0].vendor[i];
       resultJsonObj["amount"]=temp[j]["amount"];
       resultJsonObj["priority"]='1';
       resultJsonObj["bkgOrBLNumber"] =this.bkgOrBLNumber;
       resultJsonObj["bkgOrBLType"] =this.bkgOrBLType;
       resultJsonObj["cntSize"] =this.contSize;
       resultJsonObj["cntType"] =this.contrType;
       resultJsonObj["cntSplHandling"] =this.cntSplHandling;
       resultJsonObj["routingNumber"] =temp[j]["routingNumber"];
       resultJsonObj["fromLocationTyp"]=temp[j]["toLocationTyp"];
       resultJsonObj["transportMode"]=temp[j]["toLocationTyp"];
       break;
     }
   }
 }
  this.resultJson.push(resultJsonObj);
}

this.resultJson.sort(function(a, b){return  a.amount - b.amount });
var priority = 0;
for(var i =0 ;i<this.resultJson.length;i++)
{  
 priority++;
 for(var j =i ;j<this.resultJson.length;j++){
   if(this.resultJson[j].amount==this.resultJson[i].amount){
     this.resultJson[j].priority= (priority);
     i=j;
   }
   else{
     this.resultJson[j].priority=priority;
     break;
   }
}
}
 

return this.resultJson;  
}
 }

closeRoutingAdf()
 {
// UIkit.modal("#adhoc101").hide();
 //$("#adhoc101").addClass('uk-open').hide();
 $("#adhoc101").addClass('uk-modal-container').addClass('uk-modal').addClass('uk-open').hide();
   //this.openModal = false;
  this.resultJson=[]; 
 // UIkit.modal("#adhoc101").hide();
 
  
 }

  saveAllSelectNew(e)
  {
    //debugger;
    this.getCostCalculation(e);
      
   }
  contractNum:any;
  saveChangeVendorObjNew: any = {};
  actionparam = "lookupRouteList";
  contSize:any ;
  contrType:any;
  bkgOrBLNumber:any;
  cntSplHandling:any;
  inputValueLoc :any;
  inputValueTerminal :any;
  inputValueLocType :any;
  transportType2 :any;//transport mode
  joType :any;//jo type
  vendoCode : any;//vendor code
  bookingType : any;
  inputSaleDateOrJobOrdDate:any;
  bargeValue:any;
  bkgOrBLType:any;
  private routeListTableData = [];
  private routeListTableDataAdHoc=[];
  private tableDataAdHoc:{};
  singleloc :any;
  private openModal: boolean = false;
 showRouteListModalNew() 
   {
 

this.vendors
   = {
  
      'findForLoc':this.fromLoc + '/' + this.toLoc,
      'findForTerminal': this.fromTerminal + '/' + this.toTerminal,
      'findForLocType': this.froLocType + '/' + this.toLocType,
      'transMode': this.transportType,
      'callingComponent':"adhoc",
      'eqpList':this.eqNumberList,
      'eqDetails': this.EqDetails,
      'singleloc':"single"
    };
    
   }
 updateRoute(e) {
   //UIkit.modal('#lookup-popup-input-vendor').hide();
   //$("#successfull-Job-Order-modal-sections").addClass('uk-open').hide();
    this.routeDetails = e;
   this.selectedRowToUpdateRoute = e;
    // this.selectedRowToUpdateRoute["contractId"] = this.selectedRow[0]["contractId"];
    // this.selectedRowToUpdateRoute["vendorCode"] = this.selectedRow[0]["vendorCode"];
    // this.selectedRowToUpdateRoute["days"] = this.selectedRow[0]["days"];
    // this.selectedRowToUpdateRoute["distance"] = this.selectedRow[0]["distance"];
    // this.selectedRowToUpdateRoute["hours"] = this.selectedRow[0]["hours"];
    // this.selectedRowToUpdateRoute["uom"] = this.selectedRow[0]["uom"];
    // this.selectedRowToUpdateRoute["routingId"] = this.selectedRow[0]["routingId"];
    
  }

 checkIfOthersAreSelected:boolean=false;
  selectedRow = {};
  removeChecked ={};
  
  getRowData(e,tablerow,i)
  {
   
      let selected = tablerow.selected;
      tablerow.selected = !selected;
      tablerow['checked'] = tablerow.selected;
     this.selectedRow = tablerow;
     if(tablerow['checked']){
       tablerow.selected=false;
    }
     
     if(this.isEmpty(this.removeChecked))
     {
      this.removeChecked=tablerow;
     }
     if(JSON.stringify(this.removeChecked)!=JSON.stringify(tablerow))
     {
      this.removeChecked['checked']=false;
      this.removeChecked=tablerow;
      
     }


     if(tablerow['priority']>1)
     {
       alert('The Selected row has Less Priority!')
     }
 

  }

  compareJsonObj(obj1,obj2)
  {
   // debugger;
    var obj1 = obj1;
    var obj2 = obj2;

var flag=true;

if(Object.keys(obj1).length==Object.keys(obj2).length){
    for(var i=0;i<Object.keys(obj1).length;i++) { 
        if(obj1[i] == obj2[i]) {
            continue;  
        }
        else {
            flag=false;
            break;
        }
    }
}
else {
    flag=false;
}
return flag;
  }
  closeJoWarning(e){
    UIkit.modal('#priorityErrorModal').hide(); 
  }

  //jobOrders:any=[];
  updateRouteForNewSave()
  {
   // debugger;
    
   if(!this.isEmpty(this.selectedRow)){
    //UIkit.modal("#adhoc101").hide();
   // $("#adhoc101").addClass('uk-open').hide();
   $("#adhoc101").addClass('uk-modal-container').addClass('uk-modal').addClass('uk-open').hide();
    //$(".uk-modal-container").hide();
    this.newAdhoc(this.selectedRow);
    this.saveAdhocJoSummery();
  }
  else {
    this.lookupErrorCodeShow = true;
    this.lookupErrorCodetext = 'Select a row';
  }
   // this.joSummary2.adHocSummary();
  }
  private lookupErrorCodeShow: boolean = false;
  private lookupErrorCodetext: string;
  isEmpty(obj) {
    for(var key in obj) {
        if(obj.hasOwnProperty(key))
            return false;
    }
    return true;
}


serviceForEqBrowser() 
   {
    //jQuery("#lookup-popup-input-vendor").detach()
    this.inputValueLoc = this.fromLoc + '/' + this.toLoc;
    this.inputValueTerminal = this.fromTerminal + '/' + this.toTerminal;
    this.inputValueLocType = this.froLocType + '/' + this.toLocType;
    this.transportType = this.transportType;//transport mode
    this.callingComponent = "adhoc";
    this.openModal=true;
    this.singleloc= "single";
    var actionparam= "lookupRouteList";
    this.vendorsDetailsForEqBrowser(actionparam, "",
    this.inputValueLoc,
    this.inputValueTerminal,this.inputValueLocType,"",
    "",this.transportType,"",this.callingComponent,
    "","","","","",this.singleloc);
    // setTimeout(function () {
    //   UIkit.modal('#lookup-popup-input-vendor').show();
    // }, 100);
    
   }
   vendorsDetailsForEqBrowser(actionparam,bkgOrBLType,
    inputValueLoc,
    inputValueTerminal,inputValueLocType,inputSaleDateOrJobOrdDate,
    vendorCode,transportType,joType,callingComponent,
    bargeValue,contSize,contrType,bkgOrBLNumber,cntSplHandling,singleloc)
    {  var  lstContainerl=[];
      var  lstContainere=[];
    this._spinner.showSpinner();
      this._lookupData.getDataLookupServiceJORoutingNew(actionparam,bkgOrBLType,
      inputValueLoc,
      inputValueTerminal,inputValueLocType,inputSaleDateOrJobOrdDate,
      vendorCode,transportType,joType,callingComponent,
      bargeValue,contSize,contrType,bkgOrBLNumber,cntSplHandling,singleloc,lstContainerl,lstContainere).subscribe(
        data => {
         // debugger;
          this._rocessjoSearchService.vendorDetails=data;
          this.assign(data[0]['contractId']);
          
          this._spinner.hideSpinner();
      }

     
      )
    
   }


assign(data){
  this.contractIDS=data;
}


//adHocCompData:[];
   addEquipmentBrowserModal(e) {
   //this._rocessjoSearchService.adHocCompData=this.selectedRowToUpdateRoute;
 //  this.serviceForEqBrowser();
   this._prosessjoEquipmentBrowser.openLookUpModal();
  }
  //prosessjoRouteList:any =this._prosessjoRouteList;
  eqNumberList:any = [];
  eqNumberListDeatils:any=[]; //#NIIT CR3
  equipmentBrowserList:any=[];
  filteredEquipmentBrowserList:any=[];
  eqListFromUpload:any = [];//list coming from upload equipment
  eqListFromAvailableEquipments:any = [];//list coming from available equipment
  eqListFromUnavailableEquipments:any = [];//list coming from unavailable equipment
  updateEqDetails(e) {
    
    this.EqDetailsList = e;

    //maintain list coming from excel upload
    if(this.EqDetailsList.eqListSource == "uploadEquipment"){
      if(this.eqListFromUpload.length == 0){
        this.eqListFromUpload = this.EqDetailsList;
      }else{
        this.eqListFromUpload = this.eqListFromUpload.concat(this.EqDetailsList);
        this.eqListFromUpload = this.getUniqueEquipmentList(this.eqListFromUpload);
      }      
    }
    //maintain list coming from available equipments
    if(this.EqDetailsList.eqListSource == "availableEquipment"){
      if(this.eqListFromAvailableEquipments.length == 0){
        this.eqListFromAvailableEquipments = this.EqDetailsList;
      }else{
        this.eqListFromAvailableEquipments = this.eqListFromAvailableEquipments.concat(this.EqDetailsList);
        this.eqListFromAvailableEquipments = this.getUniqueEquipmentList(this.eqListFromAvailableEquipments);
      }        
    }

    //maintain list coming from unAvailable equipments
    if(this.EqDetailsList.eqListSource == "unavailableEquipment"){      
        this.eqListFromUnavailableEquipments = this.EqDetailsList;          
    }

    this.fileUploadFlag = true;

    this.filteredEquipment();   

    //to scroll to the bottom of the page
    //#NIIT CR3 BEGIN
    setTimeout(function() {
    var scrollValue= $('.TableHeightCalculate').height();
    $('#add-empty-equipment-setup-modal').parent().parent().parent().scrollTop(scrollValue);
    }, 0);
    //#NIIT CR3 END

}
  
  
  //show file upload component
  
  fileUploadFlag: boolean = true;
  uploadExcelFile(e) {   
    //this.serviceForEqBrowser();
    //debugger;
    this.routeContractID=this.contractIDS;
    //this.routeContractID=
    this.fileUploadFlag = false;
   // this._prosessjoEquipmentBrowser.openLookUpModal();
  // this.uploadExcel.emit({"fileUploadFlag": this.fileUploadFlag ,"contractId": this.routeContractID,"jobOrderType":this.addHocTypeCode});
  }

  closeFileUpload(e) {
    this.fileUploadFlag = true;
  }

  closeAddHocSideBar(e) {
    this.closeAddHoc.emit(e);
  }

  private showHideflag = false;
  errorCodetext:string; //#NIIT CR3
  private saveAdhocJoSummery() {

    this.selectedRowToUpdateRoute.selected=false;
    this.filteredEquipmentBrowserList.forEach(row => {
    
      this.eqNumberList.push(row.eqpNum); 
      this.eqNumberListDeatils.push({"eqType":row.type,"eqSize":row.size,"eqNumber":row.eqpNum}); //#NIIT CR3
    }); 
    
    this.joSummary2.processJoType = this.addHocTypeCode;
    this.joSummary2.joSummerySaveObj = {
      'routingList': this.selectedRowToUpdateRoute,
      'eqList': this.eqNumberList,
      'eqDetails': this.eqNumberListDeatils, //#NIIT CR3
      'processJoType': this.addHocTypeCode,
      'vendorCode':this.selectedRowToUpdateRoute['vendorCode'],
      'action': 'joSummeryAdhoc'
    }
    //#NIIT CR3 BEGIN
    let limit = this.uploadListLimit;
    if(this.eqNumberListDeatils.length > limit){//#NIIT CR3
      this.errorCodetext = "JO can be created for upto "+ this.uploadListLimit +" containers." //#NIIT CR3 
      UIkit.modal('#equipmentLimitExceedModal').show();  //#NIIT CR3    
    } else{
      this.showHideflag = true;
      this.joSummary2.getPrcessJoBackEndData();
    }
    //#NIIT CR3 END 
    this.eqNumberList = [];
    this.eqNumberListDeatils = []; //#NIIT CR3
  }
  
  hideJoSummery($event) {
    this.showHideflag = false;
  }
  
  //#NIIT CR3 BEGIN	
  //method to open the empty equipment containers modal 		
  openAddEmptyEquipmentsModal(e){ 
    this._prosessjoEquipmentBrowser.openEmptyEquipmentsModal();
  }
  //#NIIT CR3 END	

  //#NIIT CR3 BEGIN	
  //method to remove objects based on the delete criteria
  removeFromfilteredEquipmentBrowserList(e){
    var deleteCriteria = e;    
    if(deleteCriteria.length != 0){      
      this.filteredEquipmentBrowserList = [];
    }
  }
  //#NIIT CR3 END	

  //adhoc list edited therefore empty the master list
  adhocListEdited(e){
    this.filteredEquipmentBrowserList = [];
  }
  
  //#NIIT CR3 END

  //#NIIT CR3 BEGIN
  //method to remove duplicate elements from list
  getUniqueEquipmentList(listParam){    
    const list = listParam.filter(function({eqpNum}) {
      const key =`${eqpNum}`;
      return !this.has(key) && this.add(key);
    }, new Set);

    return list;
  }
  //#NIIT CR3 END

  //#NIIT CR3 BEGIN
  //method to get final equipment browser list
  filteredEquipment(){

    //checking list coming from excel upload
    if(this.eqListFromUpload.length != 0){
      this.filteredEquipmentBrowserList = this.filteredEquipmentBrowserList.concat(this.eqListFromUpload);
    }

    //checking list coming from equipment browser
    if(this.eqListFromAvailableEquipments.length !=0){
      this.filteredEquipmentBrowserList = this.filteredEquipmentBrowserList.concat(this.eqListFromAvailableEquipments);
    }

    //checking list coming from unavailable equipments
    if(this.eqListFromUnavailableEquipments.length !=0){
      this.filteredEquipmentBrowserList = this.filteredEquipmentBrowserList.concat(this.eqListFromUnavailableEquipments);
    }

    //call method and get final list of unique eqlist
    this.filteredEquipmentBrowserList = this.getUniqueEquipmentList(this.filteredEquipmentBrowserList);
    
  }
  //#NIIT CR3 END  
  
}
