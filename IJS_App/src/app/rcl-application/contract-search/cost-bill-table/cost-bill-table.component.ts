import { Component, OnInit, EventEmitter, Output, ViewChild, Input,ChangeDetectorRef, ChangeDetectionStrategy } from '@angular/core';
import { GridOptions } from 'ag-grid';
import { SpinnerServiceService } from "../../../common-services/spinner-service.service";
import { ServerErrorcodeService } from "../../../common-services/server-errorcode.service";
import { SortSearchTableService } from '../sort-search-table.service';
import { ContractSearchService } from '../contract-search.service';
import * as $ from 'jquery';
import { SpecialHandlingService } from "app/common-services/special-handling.service";
import { ImdgClassService } from "app/common-services/imdg-class.service";
import { PortClassService } from "app/common-services/port-class.service";
import { SessionTimeOutService } from "app/common-services/session-time-out.service";

declare var UIkit: any;

@Component({
  selector: 'app-cost-bill-table',
  templateUrl: './cost-bill-table.component.html',
  styleUrls: ['./cost-bill-table.component.scss'],
  changeDetection:ChangeDetectionStrategy.OnPush,
})
export class CostBillTableComponent implements OnInit {

  constructor(private cd: ChangeDetectorRef,public _joborderService: ContractSearchService, private _spinner: SpinnerServiceService, public _serverErrorCode: ServerErrorcodeService, public _sortTable: SortSearchTableService,private shService:SpecialHandlingService,private portService:PortClassService,private imdgService:ImdgClassService,private _sessionTimeOutService:SessionTimeOutService) {
  }

  @Output() public showAddEditCostRate = new EventEmitter();
  @ViewChild('billTable') _billTable: any;
  @ViewChild('costGrid') _costGrid: any;

  @Input() private userType: string;
  @Input() private contractSelectedRow: any;
  @Input() private termCodesList: any = [];
  @Input() private eqTypeList: any = [];
  @Input() billRowData: any[] = [];
  @Input() costRowData: any[] = [];
  @Input() contractResulttableDataRowObj: any;
  @Input() exemptedFlag: boolean;
  @Output() myclick: EventEmitter<any> = new EventEmitter();
  //#NIIT CR4 >>>>BEGIN
  @Input() oogCodeList: any;
  @Input() portCodeList: any;
  @Input() imdgCodeList: any;
  @Input() selectedRowOnClick: any;
  //#NIIT CR4 >>>>END
  private gridOptions: GridOptions;
  private defaultColDef;
  private domLayout;
  private columnDefs: any[];
  public deleteTextMsg: any;

  ijsRateVOList = [];
  addEditCostRateObj = {};
  successTextMsg: string;
  successtextFlag: boolean;
  eqType = [];

  allColumns = [
    { name: 'startDate', visible: true, id: "Effective Date" },
    { name: 'endDate', visible: true, id: "Expiry Date" },
    { name: 'mtOrLaden', visible: true, id: "mtOrLaden" },
    { name: 'service', visible: true, id: "Service" },
    { name: 'vesselCodes', visible: true, id: "Vessel Codes" },
    { name: 'rateBasis', visible: true, id: "Rate Basis" },
    { name: 'eqCatq', visible: true, id: "Eq Catq" },
    { name: 'rateStatus', visible: true, id: "Rate Status" },
    { name: 'chargeCode', visible: true, id: "Charge Code" },
    { name: 'calcMethod', visible: true, id: " Calc Method" },
    { name: 'eqType', visible: true, id: "Eq Type" },
    { name: 'upto', visible: true, id: "Upto" },
    { name: 'uom', visible: true, id: "UOM" },
    { name: 'splHandling', visible: true, id: "Spl Handling" },
    { name: 'currency', visible: true, id: "Currency" },
    { name: 'portCode', visible: true, id: "Port Class Code" },//#NIIT CR4 
    { name: 'imdgCode', visible: true, id: "IMDG Details" },//#NIIT CR4 
    //{ name: 'oogSetUpList', visible: false, id: "OOG SetUp List" },
    { name: 'oogCode', visible: true, id: "OOG SetUp" },//#NIIT CR4 
    { name: 'splCostByBlOrBooking', visible: true, id:"SPLCostByBlOrBooking" },
    { name: 'rate20', visible: true, id: "Rate 20" },
    { name: 'rate40', visible: true, id: "Rate 40" },
    { name: 'rate45', visible: true, id: "Rate 45" },
    { name: 'lumpSum', visible: true, id: "Lump Sum" }
  ];

  selectedRowData: any;
  saveCostRateErrorMsg: any[] = [];
  //agGridRateFlag: boolean = true;

  ngOnInit() {//ngOnInit start  

    this.columnDefs = [
      {
        columnName: 'Effective Date', columnId: 'startDate', colCellCss: {
          width: '120px',
        }
      },
      {
        columnName: 'Expiry Date', columnId: 'endDate', colCellCss: {
          width: '120px',
        }
      },
      {
        columnName: 'Service', columnId:'service', colCellCss: { width: '120px',}
      },
      {
        columnName: 'Vessel', columnId: 'vesselCodes', colCellCss: {
          width: '120px',
        }
      },
      {
        columnName: 'MT/LADEN', columnId: 'mtOrLaden', colCellCss: {
          width: '120px',
        }
      },
      {
        columnName: 'Rate Basis', columnId: 'rateBasis', colCellCss: {
          width: '120px',
        }
      },
      {
        columnName: 'Equipment Cateogary', columnId: 'eqCatq', colCellCss: {
          width: '120px',
        }
      },
      {
        columnName: 'Rate Status', columnId: 'rateStatus', colCellCss: {
          width: '120px',
        }
      },
      // {
      //   columnName: 'Term', columnId: 'term', colCellCss: {
      //     width: '120px',
      //   }
      // },
      {
        columnName: 'Equipment Type', columnId: 'eqType', colCellCss: {
          width: '120px',

        }
      },
      {
        columnName: 'Upto (KILO)', columnId: 'upto', colCellCss: {
          width: '120px',

        }
      },
      {
        columnName: 'Special Handling', columnId: 'splHandling', colCellCss: {
          width: '120px',

        }
      },
      {
        columnName: 'Currency', columnId: 'currency', colCellCss: {
          width: '120px',

        }
      },
      //#NIIT CR4 >>>>BEGIN
      {
        columnName: 'Port Class', columnId: 'portCode', colCellCss: {
          width: '120px',

        }
      },
      {
        columnName: 'Imdg Details', columnId: 'imdgCode', colCellCss: {
          width: '120px',

        }
      },
      {
        columnName: 'Oog Setup', columnId: 'oogCode', colCellCss: {
          width: '120px',

        }
      },
      //#NIIT CR4 >>>>END
      {
        columnName: 'Special Cost By Customer', columnId: 'splCostByBlOrBooking', colCellCss: {
          width: '190px',

        }
      },
      //#NIIT CR4 >>>>BEGIN
      {
        columnName: 'Rate20', columnId: 'rate20', checkRate20:'checkRate20', colCellCss: {
          width: '240px',

        }
      },
      {
        columnName: 'Rate40', columnId: 'rate40',checkRate40:'checkRate40',  colCellCss: {
          width: '240px',

        }
      },
      {
        columnName: 'Rate45', columnId: 'rate45',checkRate45:'checkRate45',  colCellCss: {
          width: '240px',

        }
      },
      //#NIIT CR4 >>>>END
      {
        columnName: 'Lump sum', columnId: 'lumpSum', colCellCss: {
          width: '120px',
        }
      },
    ];

  }//ngOnInit End

  deleteContractRecord(rowObj, e) {
    this.deleteTextMsg = "Do you want to delete?";
    UIkit.modal('#delete-cost-warnings-modal').show();
  }

  closeWarning() {
    UIkit.modal('#delete-cost-warnings-modal').hide();
    this._costGrid.settingButton(this.costRates);
  }

  closedWarning(){
    UIkit.modal('#priority-warning-modal').hide();
  }

  closeVesselValidationWarning(){
    UIkit.modal('#vessel-warning-modal').hide();
  }


  ngOnChanges() { }
  myfunc(e) {
  this.myclick.emit();
}

  refreshTable(tableData, rowObj) {
    this._spinner.showSpinner();
    this.contractResulttableDataRowObj = rowObj;

    if (!this._billTable) {
      tableData.forEach(element => {
        if (element.vesselCodes == undefined || element.vesselCodes == null) {
          element.vesselCodes = "***"
        }
        if (element.service == undefined || element.service == null) {
          element.service = "***"
        }
        if (element.splHandling == undefined || element.splHandling == null) {
          element.splHandling = "N"
        }
        if (element.rateBasis == "S") {
          element.lumpSum = null;
        }
        if (element.rateBasis == "L") {
          element.rate20 = null;
          element.rate40 = null;
          element.rate45 = null;
        }
      });
      this.costRowData = tableData;
       this.cd.markForCheck();
    } else {
      this._billTable.contractResulttableDataRowObj = rowObj;
       this.cd.markForCheck();
    }
    this.rowSelectedFlag = false;
    this._spinner.hideSpinner();
  }

  rowSelectedFlag: boolean = false;
  selectedRowId: number; 
  getAllCostTableRows(e){
    let costData = [] = e;    
  }


  EditedNonDeletedRows = [];
  deleteRow(e) {
    
    var selectedDeleteItems = [] = e;
    var changedDeletedRows = [];    
    this.EditedNonDeletedRows = this.costRowData.filter(word => word.operation == "addCostRate" && word.editRow == true);

    //filtering on the basis of edit row being true and delete operation
    const deleteEditedRow = selectedDeleteItems.filter(word => word.operation == "deleteCostRate" && word.editRow == true);
    deleteEditedRow.forEach(element => {
      if(element.editRow == true){
          element.editRow = false;
          element['deletedFlag']=true;
      }  
      //changing the editrow flag and adding a delete flag and pushing it to an array    
      changedDeletedRows.push(element);
    });

    //filtering on the basis of editrow being false or empty and operation being delete
    const deleteRowNonEdited = selectedDeleteItems.filter(word => word.operation == "deleteCostRate" && (word.editRow == null || word.editRow == false)); 
    
    //array having all the rows with deleted operation
    var deleteData = changedDeletedRows.concat(deleteRowNonEdited);

    //array with only deleted operation
    deleteData = deleteData.filter(word => word.operation == "deleteCostRate" && word.deletedFlag == null); 


    this.ijsRateVOList = [];
    this.addEditCostRateObj = {};
    deleteData.forEach(element => {
      let ijsRate = element;
      this.ijsRateVOList.push(ijsRate);
    });
    
    if(deleteData.length > 0){
      this.deleteCostBillData();      
    }     
    this.getDeletedRowdata();
  }

  deleteCostBillData(){
    this.addEditCostRateObj["routingNumber"] = this.contractSelectedRow.routingId;
    this.addEditCostRateObj["ijsRateList"] = this.ijsRateVOList;
    this.addEditCostRateObj["action"] = 'deleteCostRate';
     //#NIIT CR4 >>>>BEGIN
    this.addEditCostRateObj["paymentFSC"] = this.contractSelectedRow.paymentFsc;
    this.addEditCostRateObj["rateType"] = "COST_RATE";
     //#NIIT CR4 >>>>END
    this._spinner.showSpinner();
    this.agGridCostFlag = false;
    var backendData = this._joborderService.saveCostRateData(this.addEditCostRateObj);
    backendData.subscribe(
      (data) => {
        if(data == "userSessionExpired"){
          this._sessionTimeOutService.checkSessionTimeout(data);
        }
        else if (data.errorMsgList.length > 0) {
          this._spinner.hideSpinner();
          this.deleteTextMsg = "Record could not be deleted.";
          this.cd.markForCheck();
          UIkit.modal('#delete-result-modal').show();
        }
        else if (data.successMgsList.length > 0) {
          this._spinner.hideSpinner();
          this.deleteTextMsg = "Record successfully deleted.";
          this.cd.markForCheck();
          UIkit.modal('#delete-result-modal').show();
        }
      },
      (err) => {
        this._spinner.hideSpinner();
        this.deleteTextMsg = "Error occured while deleting.";
        this.cd.markForCheck();
        UIkit.modal('#delete-result-modal').show();
      }
    )
  }

  closeErrorWarning() {
    this.getDeletedRowdata();
    UIkit.modal('#delete-result-modal').hide();
  }

  rejectRow(e) {
    this.addEditCostRateObj = {};
    this.ijsRateVOList = [];
    e.forEach(element => {
      let ijsRate = element;
      this.ijsRateVOList.push(ijsRate);
    });
    this.addEditCostRateObj["routingNumber"] = this.contractSelectedRow.routingId;
    this.addEditCostRateObj["action"] = "rejectCostRate";
    this.addEditCostRateObj["ijsRateList"] = this.ijsRateVOList;
     //#NIIT CR4 >>>>BEGIN
    this.addEditCostRateObj["paymentFSC"] = this.contractSelectedRow.paymentFsc;
    this.addEditCostRateObj["rateType"] = "COST_RATE";
    this.rateType = "COST_RATE";
     //#NIIT CR4 >>>>END
    this.getBackEndData(this.addEditCostRateObj);
  }

  approveRow(e) {
    this.addEditCostRateObj = {};
    this.ijsRateVOList = [];
    e.forEach(element => {
      let ijsRate = element;
      this.ijsRateVOList.push(ijsRate);
    });
    this.addEditCostRateObj["routingNumber"] = this.contractSelectedRow.routingId;
    this.addEditCostRateObj["action"] = "approveCostRate";
    this.addEditCostRateObj["ijsRateList"] = this.ijsRateVOList;
     //#NIIT CR4 >>>>BEGIN
    this.addEditCostRateObj["paymentFSC"] = this.contractSelectedRow.paymentFsc;
    this.addEditCostRateObj["rateType"] = "COST_RATE";
    this.rateType = "COST_RATE";
     //#NIIT CR4 >>>>END
    this.getBackEndData(this.addEditCostRateObj);
  }

  oogSetUpListData:any=[];
  oogResponseData:any=[];
  //#NIIT CR4 >>>>BEGIN
  //method to save oog list and return cost row data
  saveOogDataList(e){    
    this.oogSetUpListData = e;
    this._spinner.showSpinner();
    var backendData = this._joborderService.saveAndGetOogList({"terminalDepotCode": this.contractResulttableDataRowObj.fromTerminal,"oogSetUpList":this.oogSetUpListData});
    backendData.subscribe(
      (data) => {
        if(data == "userSessionExpired"){
          this._sessionTimeOutService.checkSessionTimeout(data);
        }
        else if (data.hasOwnProperty("errorCode")) {          
        
        } else {          
          this.oogResponseData = data['oogSetUpList'];    
          this.shService.oogList = this.oogResponseData;  
          this.shService.oogList.sort((a,b) => (a['oogSetupCode'] < b['oogSetupCode']) ? -1 : ((b['oogSetupCode'] < a['oogSetupCode']) ? 1 : 0)); //to sort the oog class list in sorted order    
          this._spinner.hideSpinner();
          this._costGrid.oogSavedSuccessfully();//to show confirmation of saved successfully
          }
      });
  }

  //method to save port list and return cost row data
  portImdgSetUpListData:any=[];
  responseData:any=[];
  errorFlag:boolean;
  savePortImdgDataList(e){
    this.errorFlag = false;   
    if(e.type == "port"){
      var action = "savePortSetup";
      var portImdgType = "PORT";
      this.portImdgSetUpListData = e.filteredPortClassSetupList;
    }
    if(e.type == "imdg"){
      var action = "saveImdgSetup";
      var portImdgType = "IMDG";
      this.portImdgSetUpListData = e.filteredImdgClassSetupList;      
    }
    this._spinner.showSpinner();
    var backendData = this._joborderService.saveAndGetPortImdgList({"terminalDepotCode": this.contractResulttableDataRowObj.fromTerminal,"portImdgList":this.portImdgSetUpListData,"action":action,"portImdgType":portImdgType});
    backendData.subscribe(
      (data) => {
        if(data == "userSessionExpired"){
          this._sessionTimeOutService.checkSessionTimeout(data);
        }
        else if (data.hasOwnProperty("errorCode")) {    

        } else {
         
          if( data['portList'] ){
            this.responseData =  data['portList'];    
            this.responseData.forEach((element)=>{
              if(element['errorMsgCd'] == "IJS_RATE_EX_10018"){
                element['errorMessage'] = this._serverErrorCode.checkError("IJS_RATE_EX_10018");  
                element['displayError'] = true; 
                this.errorFlag = true;              
              }
            });             
            this.portService.portClassList = this.responseData;
            this.portService.portClassList.sort((a,b) => (a['portImdgCode'] < b['portImdgCode']) ? -1 : ((b['portImdgCode'] < a['portImdgCode']) ? 1 : 0)); //to sort the oog class list in sorted order       
          }

          if(data['imdgList']){
            this.responseData = data['imdgList'];  
            this.responseData.forEach((element)=>{
              if(element['errorMsgCd'] == "IJS_RATE_EX_10019"){
                element['errorMessage'] = this._serverErrorCode.checkError("IJS_RATE_EX_10019");  
                element['displayError'] = true;
                this.errorFlag = true;              
              }
            });          
            this.imdgService.imdgList = this.responseData; 
            this.imdgService.imdgList.sort((a,b) => (a['portImdgCode'] < b['portImdgCode']) ? -1 : ((b['portImdgCode'] < a['portImdgCode']) ? 1 : 0)); //to sort the oog class list in sorted order       
          } 
             
          this._spinner.hideSpinner();
          if(!this.errorFlag){
            if(portImdgType == 'PORT' ){
            this._costGrid.portSavedSuccessfully();
            }else if(portImdgType == 'IMDG'){
              this._costGrid.imdgSavedSuccessfully();
            }
          }         
          
          
        }
      });
  }

  //refresh tables on the button click port,imdg or oog popups
  refreshTableData(e){    
    this.rateType = "COST_RATE";
    this.getRowdata();
  }
  //#NIIT CR4 >>>>END

  //Get the search data from backend service 
  getBackEndData(searchObj) {
    this._spinner.showSpinner();
    var backendData = this._joborderService.costRateGetData(searchObj);
    backendData.subscribe(
      (data) => {
        if(data == "userSessionExpired"){
          this._sessionTimeOutService.checkSessionTimeout(data);
        } 
        else if (data['successMgsList'].length > 0) {
          this.getRowdata();
        }
        //this._spinner.hideSpinner();
      }
    )
  }

  showAllCostTableRows(e){
   this.rateType = "COST_RATE";//#NIIT CR4 
   this.getRowdata();
  }
  agGridCostFlag = true;
  
  //#NIIT CR4 >>> BEGIN 
  costExchangeRateError = []; 
  costRateErrorFlag:boolean = false; 
  billExchangeRateError = []; 
  billRateErrorFlag:boolean = false; 
  //#NIIT CR4 >>> END 
  getRowdata() {

  //#NIIT CR4 >>> BEGIN 
  this.costExchangeRateError = [];
  this.costRateErrorFlag = false;
  this.billExchangeRateError = []; 
  this.billRateErrorFlag = false; 
  //#NIIT CR4 >>> END 

    //this.agGridCostFlag = false
    this._spinner.showSpinner();
    //#NIIT CR4 >>>>BEGIN
    var backendData = this._joborderService.getCostRateTableData({"routingID": this.contractResulttableDataRowObj.routingId,"fsc":this.contractResulttableDataRowObj.paymentFsc,"rateType":this.rateType,"terminalDepotCode":this.contractResulttableDataRowObj.fromTerminal});
    backendData.subscribe(
      (data) => {
        if(data == "userSessionExpired"){
          this._sessionTimeOutService.checkSessionTimeout(data);
        }
        else if (data.hasOwnProperty("errorCode")) {
          this.refreshTable([], this.contractResulttableDataRowObj);
           this.cd.markForCheck();
        } else {
          this.agGridCostFlag = true;
          this.billRowData = data['billingRateResults']['results'];
          //#NIIT CR4 >>>>BEGIN
          //Oog Code List
          if(data.costRateSetup != undefined && data.costRateSetup["oogCodeList"]){
            let oogCodeListTemp = [];
            for (let i = 0; i < data.costRateSetup.oogCodeList.length; i++) {
              oogCodeListTemp.push({ label: data.costRateSetup.oogCodeList[i], value: data.costRateSetup.oogCodeList[i] });
            }            
            this.oogCodeList = oogCodeListTemp;            
          }

          //port Code List
          if(data.costRateSetup != undefined && data.costRateSetup["portCodeList"]){
            let portCodeListTemp = [];
            for (let i = 0; i < data.costRateSetup.portCodeList.length; i++) {
              portCodeListTemp.push({ label: data.costRateSetup.portCodeList[i], value: data.costRateSetup.portCodeList[i] });
            }            
            this.portCodeList = portCodeListTemp;
          }

          //imdg Code List
          if(data.costRateSetup != undefined && data.costRateSetup["imdgCodeList"]){
            let imdgCodeListTemp = [];
            for (let i = 0; i < data.costRateSetup.imdgCodeList.length; i++) {
              imdgCodeListTemp.push({ label: data.costRateSetup.imdgCodeList[i], value: data.costRateSetup.imdgCodeList[i] });
            }            
            this.imdgCodeList = imdgCodeListTemp;            
          }
          //#NIIT CR4 >>>>END

          this.refreshTable(data['rateResults']['results'], this.contractResulttableDataRowObj);

        //   //when bill rate tab is clicked
        //   if(this.rateType == "COST_RATE"){

        //     var costRowData = data['rateResults']['results'];

        //     //to check no exchange rate is available
        //     costRowData.forEach((element)=>{

        //     if(element.exchangeError != undefined){
        //           this.costRateErrorFlag = true;
        //           this.costExchangeRateError.push({"currencyError" :element.exchangeError});
        //         }
        //     });
        
        //   //if exchnage rate error is present than show error
        //   if(this.costRateErrorFlag){
        //     UIkit.modal('#cost-tab-exchange-error').show();
        //   }
      
        // }
        // //when bill rate tab is clicked
        // else if(this.rateType == "BILLING_RATE"){

        //     //to check no exchange rate is available
        //     this.billRowData.forEach((element)=>{

        //     if(element.exchangeError != undefined){
        //           this.billRateErrorFlag = true;
        //           this.billExchangeRateError.push({"currencyError" :element.exchangeError});
        //         }
        //     });
        
        //   //if exchnage rate error is present than show error
        //   if(this.billRateErrorFlag){
        //     UIkit.modal('#bill-tab-exchange-error').show();
        //   }
            
        // }   

          this.cd.markForCheck();
          //this._spinner.hideSpinner();
        }
      });
  }

  //for delete functionality
   getDeletedRowdata() {
    //this.agGridCostFlag = false
    this._spinner.showSpinner();
    //#NIIT CR4 >>>>BEGIN
    var backendData = this._joborderService.getCostRateTableData({"routingID": this.contractResulttableDataRowObj.routingId,"fsc":this.contractResulttableDataRowObj.paymentFsc,"rateType":"COST_RATE","terminalDepotCode":this.contractResulttableDataRowObj.fromTerminal});
    backendData.subscribe(
      (data) => {
        if(data == "userSessionExpired"){
          this._sessionTimeOutService.checkSessionTimeout(data);
        }
        else if (data.hasOwnProperty("errorCode")) {
          this.refreshDeletedTable([], this.contractResulttableDataRowObj,this.EditedNonDeletedRows)
          this.cd.markForCheck();
        } else {
            this.agGridCostFlag = true;
            this.refreshDeletedTable(data['rateResults']['results'], this.contractResulttableDataRowObj,this.EditedNonDeletedRows);
            this.cd.markForCheck();
            this._spinner.hideSpinner();
          }
        });
  }

  //for delete functionality
  refreshDeletedTable(tableData, rowObj, editedNonDeletedObj) {       
        this.contractResulttableDataRowObj = rowObj;       
        var deletedData = tableData.concat(editedNonDeletedObj);

    if (!this._billTable) {
    deletedData.forEach(element => {
        if (element.vesselCodes == undefined || element.vesselCodes == null) {
          element.vesselCodes = "***"
        }
        if (element.service == undefined || element.service == null) {
          element.service = "***"
        }
        if (element.splHandling == undefined || element.splHandling == null) {
          element.splHandling = "N"
        }
        if (element.rateBasis == "S") {
          element.lumpSum = null;
        }
        if (element.rateBasis == "L") {
          element.rate20 = null;
          element.rate40 = null;
          element.rate45 = null;
        }
      });
      this.costRowData = deletedData;      
      this.cd.markForCheck();
    } else {
      this._billTable.contractResulttableDataRowObj = rowObj;
      this.cd.markForCheck();
    }
    this.rowSelectedFlag = false;
  }

  rateType:string;  
  costBillTabChange(e) {

    //#NIIT CR4 >>>>BEGIN
    //when cost rate tab is clicked
    if(e.nextId == "tab-costRate"){
      this.rateType = "COST_RATE";
    }
    //when bill rate tab is clicked
    else if(e.nextId == "tab-billRate"){
      this.rateType = "BILLING_RATE";
    }    
    //#NIIT CR4 >>>>END
    
    if (e.activeId = "tab-billRate") {
      this.getRowdata();
    }
  }

  //public enableFlag:boolean;
  //public Errorcounter : number = 0;
  //new grid save data
  rateTextMsg: any;
  saveCostRate(e) {
    //this.enableFlag = false;
    let contFlag : boolean =true;
    this.ijsRateVOList = [];
    this.addEditCostRateObj = {};
    e.forEach(element => {
      let ijsRate = element;//{ijsRateVO:element};     
      ijsRate["mot"] = this.contractSelectedRow.transMode;
      ijsRate.errorAllreadySet = false;
      ijsRate.errorMsg = undefined;
      if (element.rate20 < 0 || element.rate40 < 0 || element.rate45 < 0 || element.lumpSum < 0 || element.upto < 0) {
        element.selectedFlag = true;
        contFlag = false;
        this.rateTextMsg = "Rates/Lump Sum/Upto cannot be negative.";
        this.cd.markForCheck();
        //this.Errorcounter += 1;
        this.costRates = element;
        UIkit.modal('#priority-warning-modal').show();
        return contFlag;       
      } else if(element.splHandling == 'D1' && element.imdgCode ==undefined && element.portCode == undefined ){
         element.selectedFlag = true;
         contFlag = false;
         this.rateTextMsg = "Enter either Port Class Code or IMDG Details.";
         this.cd.markForCheck();
         //this.Errorcounter += 1;
         this.costRates = element;
         UIkit.modal('#priority-warning-modal').show();
         return contFlag;   
      } else if(element.splHandling == "0G" && element.oogCode == undefined){
         element.selectedFlag = true;
         contFlag = false;
         this.rateTextMsg = "Enter OOG Details.";
         this.cd.markForCheck();
         //this.Errorcounter += 1;
         this.costRates = element;
         UIkit.modal('#priority-warning-modal').show();
         return contFlag;   
      }
      //else if(element.vesselCodes == ""){
       // ijsRate["vesselCodes"] = "***" //if vessel code is null make it default ***
        //element.selectedFlag = false;
       // contFlag = true;
        //this.rateTextMsg = "Vessel cannot be empty";
        //UIkit.modal('#vessel-warning-modal').show();
        //return contFlag;
      //} 
      else {
        if(element.vesselCodes == ""){
          ijsRate["vesselCodes"] = "***";
        }
        // if(element.oogSetUpList[0].maxOverHeight == "*" 
        //    || element.oogSetUpList[0].maxOverLength == "*"
        //    || element.oogSetUpList[0].maxOverWidth == "*"
        //    || element.oogSetUpList[0].minOverHeight == "*"
        //    || element.oogSetUpList[0].minOverLength == "*"
        //    || element.oogSetUpList[0].minOverWidth == "*"){
        //     element.oogSetUpList[0].maxOverHeight = -1;
        //     element.oogSetUpList[0].maxOverLength = -1;
        //     element.oogSetUpList[0].maxOverWidth = -1;
        //     element.oogSetUpList[0].minOverHeight = -1;
        //     element.oogSetUpList[0].minOverLength = -1;
        //     element.oogSetUpList[0].minOverWidth = -1;
        //     element.oogSetup = "UC:-1:-1:-1:-1:-1:-1"
        // }

        if(element.oogSetUpList != undefined){
          if(element.oogSetUpList[0].maxOverHeight == "*"){
           element.oogSetUpList[0].maxOverHeight = -1;
          }
          if(element.oogSetUpList[0].maxOverLength == "*"){
            element.oogSetUpList[0].maxOverLength = -1;
          }
          if(element.oogSetUpList[0].maxOverWidth == "*"){
            element.oogSetUpList[0].maxOverWidth = -1;
          }
          if(element.oogSetUpList[0].minOverHeight == "*"){
            element.oogSetUpList[0].minOverHeight = -1;
          }
          if(element.oogSetUpList[0].minOverLength == "*"){
            element.oogSetUpList[0].minOverLength = -1;
          }
          if(element.oogSetUpList[0].minOverWidth == "*"){
            element.oogSetUpList[0].minOverWidth = -1;
          }    
        }
        
        if (typeof element['eqType'] === "string"){
           element['eqType'] = element['eqType'].split(',');
          // ijsRate["eqTypeList"] = element['eqType'];
        } else{
            ijsRate["eqTypeList"] = ijsRate["eqType"];
        }                 
      
        //ijsRate["eqType"] = null;
        this.ijsRateVOList.push(ijsRate);
        //this.saveContract();
      }
    });

   

    
    if(contFlag){
       e.forEach(element => {
        let ijsRate = element;       
        ijsRate["eqType"] = null;
      });

    this.saveContract();
  }
  }

  public counter : number = 0;
  public costRates = [];
  saveContract() {
    this.saveCostRateErrorMsg = [];
    this.costRates = [];
    this.addEditCostRateObj["routingNumber"] = this.contractSelectedRow.routingId;
    this.addEditCostRateObj["contractNumber"] = this.contractSelectedRow.contractId;
    this.addEditCostRateObj["ijsRateList"] = this.ijsRateVOList;
    this.addEditCostRateObj["action"] = "addEditCostRateList";
    //#NIIT CR4 >>>>BEGIN
    this.addEditCostRateObj["paymentFSC"] = this.contractSelectedRow.paymentFsc;
    this.addEditCostRateObj["rateType"] = "COST_RATE";
    //#NIIT CR4 >>>>END    
    this._spinner.showSpinner();
    this.agGridCostFlag = false;
    var backendData = this._joborderService.saveCostRateData(this.addEditCostRateObj);
    backendData.subscribe((data) => {
        if(data == "userSessionExpired"){
          this._sessionTimeOutService.checkSessionTimeout(data);
        }
        else {        
        data.rateResults.results.forEach(element => {
          if (element.errorAllreadySet == true) {           
            this.saveCostRateErrorMsg.push(this._serverErrorCode.checkError(element.errorMsg));
            if(element.rateBasis=="Size/Type"){
               element['eqType'] = element['eqType'].split(',');
            }
           
            element.selectedFlag = true;
			      element.editRow = true;
            element.errorAllreadySet = false;
            element.errorMsg = undefined;

            if(element.rateBasis=="BKG/BL" || element.rateBasis=="Lump Sum"){
              // element.mtOrLaden=undefined;
              // element.eqCatq=undefined;
              // element.eqType=undefined;
              if(element.rateBasis=="BKG/BL"){
                 element.rateBasis="B";
              }
              if(element.rateBasis=="Lump Sum"){
                element.rateBasis="L";
              }             
              element.rate20 = undefined;
              element.rate40 = undefined;
              element.rate45 = undefined;
              element.splHandling = undefined;
              element.mtOrLaden = undefined;
              element.eqCatq = undefined;
              element.upto = undefined;
              element.eqType = undefined;
            }
            
            //showing fields in editable mode with values during error
            if(element.portCode == undefined){
              element.portCode = undefined;
            }
            if(element.imdgCode == undefined){
              element.imdgCode = undefined;
            }
            if(element.oogCode == undefined){
              element.oogCode = undefined;
            }  
			      //#NIIT CR4
            if(element.splCostByBlOrBooking == undefined){
              element.splCostByBlOrBooking = undefined;
            }   
            if(element.lumpSum == undefined){
              element.lumpSum = undefined;
            }        
            if(element.splCostByBlOrBooking == undefined){
              element.splCostByBlOrBooking = undefined;
            }   
            //element.portCode = undefined; //to show fields during error
            //element.imdgCode = undefined; //to show fields during error
            //element.oogSetup = undefined; //to show fields during error
            //element.splCostByBlOrBooking = undefined; //to show fields during error //#NIIT CR4
            //element.lumpSum = undefined; //to show fields during error            
            this.counter += 1;  
            this.costRates.push(element);          
            this.cd.markForCheck();
          }            
        });
         
        if (this.saveCostRateErrorMsg.length == 0) {
          this.saveCostRateErrorMsg.push('Cost rate saved successfully');
          this.cd.markForCheck();
        }
        this.costRowData = data.rateResults.results;
        this.agGridCostFlag = true;
        this._spinner.hideSpinner();
        this.cd.markForCheck();      
       $('#delete-warnings-modal1').addClass('uk-open').show();
      }
    },
      (err) => {
        this._spinner.hideSpinner();
        this.cd.markForCheck();
        $('#delete-warnings-modal1').addClass('uk-open').show();        
      }
    )

    
  }



}




function deltaIndicator(params, field) {

  let template = document.createElement('template');
  if (params.data.errorMsg) {
    template.innerHTML = "<div style='position: relative; bottom: 20px; left: 20px;'><div  class='row' style='width: 375px; height: 18px;'><div style='font-size: 12px; width: 800px; z-index: 99; background: #f9c0c5; position:relative; color:red;' id='" + params.rowIndex + "'>&nbsp;&nbsp;This Record has expired but you can expand the expire date</div></div><div  class='row'><div style='height:20px;width:100%;z-index: 99 !important;top:25px'> " + params.value + "</div></div></div>";
  } else {
    template.innerHTML = "<div><div style='height:20px; width:100%; left: 10px; position: relative; z-index: 99 !important;'> " + params.value + "</div></div>"
  }
  return template.innerHTML;
}

function perTripCheck(params, field) {
  let template = document.createElement('template');
  if (params.data.perTrip == 'true') {
    template.innerHTML = '<div style="left: 10px;display: inline-block;position: relative;" class="">' + params.data.rate20 + ' </div>' + '<label class="check-container" style="width: 20px;display: inline; left: 45px; position: relative;"><input name="status" value="Start" type="checkbox" checked="' + true + '" (change)="filterTableDataFromFilter($event)"><span class="checkmark"></span></label>'
  } else {
    template.innerHTML = '<div style="left: 10px;display: inline-block;position: relative;" class="">' + params.data.rate20 + ' </div>' + '<label class="check-container" style="width: 20px;display: inline; left: 45px; position: relative;"><input name="status" value="Start" type="checkbox" (change)="filterTableDataFromFilter($event)"><span class="checkmark"></span></label>'
  }
  return template.innerHTML;
}