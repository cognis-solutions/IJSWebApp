import { Component, OnInit, EventEmitter, Output, Input,ChangeDetectorRef,ChangeDetectionStrategy, ViewChild } from '@angular/core';
import { GridOptions } from 'ag-grid';
import { SpinnerServiceService } from "../../../../common-services/spinner-service.service";
import { ServerErrorcodeService } from "../../../../common-services/server-errorcode.service";
import { SortSearchTableService } from '../../sort-search-table.service';
import { ContractSearchService } from '../../contract-search.service';
import * as $ from 'jquery';
import { SessionTimeOutService } from "app/common-services/session-time-out.service";
declare var jQuery: any;
declare var UIkit: any;

@Component({
  selector: 'app-bill-table',
  templateUrl: './bill-table.component.html',
  styleUrls: ['./bill-table.component.scss'],
  changeDetection:ChangeDetectionStrategy.OnPush,
})
export class BillTableComponent implements OnInit {

  constructor(private cd: ChangeDetectorRef,public _joborderService: ContractSearchService, private _spinner: SpinnerServiceService, public _serverErrorCode: ServerErrorcodeService, public _sortTable: SortSearchTableService,private _sessionTimeOutService:SessionTimeOutService) {
  }
  // private gridOptions: GridOptions;
  private domLayout;
  private columnDefs: any[];
  private selectedRowData: any;
  private contractResulttableDataRowObj: any;
  private agGridRateFlag: boolean = true;
  addEditCostRateObj: any = {};
  ijsRateVOList: any[] = [];
  saveCostRateErrorMsg: any[];


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
    { name: 'oogSetup', visible: true, id: "OOG SetUp" },
    //{ name: 'splCostByBlOrBooking', visible: true, id:"SPLCostByBlOrBooking" },
    { name: 'rate20', visible: true, id: "Rate 20" },
    { name: 'rate40', visible: true, id: "Rate 40" },
    { name: 'rate45', visible: true, id: "Rate 45" },
    { name: 'lumpSum', visible: true, id: "Lump Sum" }
  ];
  addEditBillRateComponentFlag: boolean = true;
  @Input() public billExemptedFlag: any;
  @Input() public billTableRowData: any;
  @Input() public billTableRowObj: any;
  @Input() private termCodesList: any;
  @Input() private eqTypeList: any;
  @Input() private userType: string;
  @Input() private contractSelectedRow: any;
  @Output() billclick: EventEmitter<any> = new EventEmitter();
  
  @ViewChild('addeEditBillRate') _addeEditBillRate: any;
  @ViewChild('billGrid') _billGrid: any;


  ngOnInit() {
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
        columnName: 'Service', columnId: 'service', colCellCss: {
          width: '120px',
        }
      },
      // {
      //   columnName: 'Vessel', columnId: 'vesselCodes', colCellCss: {
      //     width: '120px',
      //   }
      // },
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
      //   // columnName: 'Term', columnId: 'term', colCellCss: {
      //   //   width: '120px',
      //   // }
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
      //#NIIT CR4 >>>>BEGIN
      // {
      //   columnName: 'Special Handling', columnId: 'splHandling', colCellCss: {
      //     width: '120px',

      //   }
      // },
      //#NIIT CR4 >>>>END
      {
        columnName: 'Currency', columnId: 'currency', colCellCss: {
          width: '120px',

        }
      },
      //#NIIT CR4 >>>>BEGIN
      // {
      //   columnName: 'Port Class', columnId: 'portCode', colCellCss: {
      //     width: '120px',

      //   }
      // },
      // {
      //   columnName: 'Imdg Details', columnId: 'imdgCode', colCellCss: {
      //     width: '120px',

      //   }
      // },
      // {
      //   columnName: 'Oog Setup', columnId: 'oogSetup', colCellCss: {
      //     width: '120px',

      //   }
      // },
      //#NIIT CR4 >>>>END
      {
        columnName: 'Rate20', columnId: 'rate20',checkRate20:'checkRate20', colCellCss: {
          width: '240px',

        }
      },
      {
        columnName: 'Rate40', columnId: 'rate40',checkRate40:'checkRate40', colCellCss: {
          width: '240px',

        }
      },
      {
        columnName: 'Rate45', columnId: 'rate45',checkRate45:'checkRate45', colCellCss: {
          width: '240px',

        }
      },
      //#NIIT CR4 >>>>BEGIN
      // {
      //   columnName: 'Lump sum', columnId: 'lumpSum', colCellCss: {
      //     width: '120px',
      //   }
      // },
      //#NIIT CR4 >>>>END
    ];

    // this.gridOptions = {
    //   columnDefs: this.columnDefs,
    //   rowHeight: 48,
    //   onColumnVisible: (event) => {
    //     for (let i = 0; i < event.columns.length; i++) {
    //       this.updateColStatus(event.columns[i].colId, event.visible);
    //     }
    //   }

    // };

    //this.billTableRowData = this.billTableRowData;
    this.contractResulttableDataRowObj = this.billTableRowObj

    this.domLayout = "autoHeight";   
  }

  ngOnChanges() { }

  updateColStatus(colname, val) {
    for (let i = 0; i < this.allColumns.length; i++) {
      if (this.allColumns[i].name == colname) {
        this.allColumns[i].visible = val;
      }
    }
  }
  

  billRefreshTable(tableData, rowObj) {
    this.contractResulttableDataRowObj = rowObj;
    this.billTableRowData = tableData;
    this.selectedRowData = false;
    this._spinner.hideSpinner();
  }


  rowSelectedFlag: boolean = false;
  selectedRowId: number;
  private onRowSelected(e) {
    if (e.node.selected == false) {
      if (this.selectedRowId == e.node.id) {
        this.rowSelectedFlag = false
        this.selectedRowData = {};
      }
    } else {
      this.rowSelectedFlag = true;
      this.selectedRowData = e.node.data;
      this.selectedRowId = e.node.id;
    }   
  }

  closeWarning() { 
    $('#delete-bill-warning-modal').addClass('uk-open').hide();
  }
  
  closedWarning(){
    UIkit.modal('#delete-warnings-modal2').hide();
    this._billGrid.settingButton(this.billRates);    
  }

  deleteTextMsg: string;
  deleteBillRecord(e) {
    this.deleteTextMsg = "Do you want to delete";
    $('#delete-bill-warning-modal').addClass('uk-open').show();    
  }

  

  EditedNonDeletedRows = [];
  selectedDeleteItems = [];
  deleteRowBillRate(e) {

    this.selectedDeleteItems = e;
    var changedDeletedRows = [];   

    //filter on the basis of operation being addBillRate and row being edited...this will be used in refresh table function to show edited rows
    this.EditedNonDeletedRows = this.billTableRowData.filter(word => word.operation == "addBillingRate" && word.editRow == true);
    
    //filtering on the basis of edit row being true and delete operation
    const deleteEditedRow = this.selectedDeleteItems.filter(word => word.operation == "deleteBillingRate" && word.editRow == true);
    deleteEditedRow.forEach(element => {
      if(element.editRow == true){
          element.editRow = false;
          element['deletedFlag']=true;
      }  
      //changing the editrow flag and adding a delete flag and pushing it to an array    
      changedDeletedRows.push(element);
    });
    
    //filtering on the basis of editrow being false or empty and operation being delete
    const deleteRowNonEdited = this.selectedDeleteItems.filter(word => word.operation == "deleteBillingRate" && word.editRow == null); 
    
    //array having all the rows with deleted operation
    var deleteData = changedDeletedRows.concat(deleteRowNonEdited);

    //array with only deleted operation
    deleteData = deleteData.filter(word => word.operation == "deleteBillingRate" && word.deletedFlag == null); 

    
    this.ijsRateVOList = [];
    this.addEditCostRateObj = {};
    deleteData.forEach(element => {
      let ijsRate = element;
      this.ijsRateVOList.push(ijsRate);
    });

    if(deleteData.length > 0){
      this.deleteBillData();      
    }     
    this.getDeletedRowdata();
  }

  closeErrorWarning() {
    this.getDeletedRowdata();
    $('#delete-result-modal').addClass('uk-open').hide();   
  }

  
  deleteBillData(){
    this.addEditCostRateObj["routingNumber"] = this.contractSelectedRow.routingId;
    this.addEditCostRateObj["ijsRateList"] = this.ijsRateVOList;
    this.addEditCostRateObj["action"] = 'deleteBillingRate';
    //#NIIT CR4 >>>>BEGIN
    this.addEditCostRateObj["paymentFSC"] = this.contractSelectedRow.paymentFsc;
    this.addEditCostRateObj["rateType"] = "BILLING_RATE";
    //#NIIT CR4 >>>>END
    this._spinner.showSpinner();
    this.agGridRateFlag = false;
    var backendData = this._joborderService.saveCostRateData(this.addEditCostRateObj);
    backendData.subscribe(
      (data) => {
        if(data == "userSessionExpired"){
          this._sessionTimeOutService.checkSessionTimeout(data);
        }
        else if(data.errorMsgList.length > 0 )
         {
          this._spinner.hideSpinner();
          this.deleteTextMsg="Record could not be deleted.";
         // UIkit.modal('#delete-result-modal').show();
         $('#delete-result-modal').addClass('uk-open').show();
         }
        else if(data.successMgsList.length > 0 )
          {
           this._spinner.hideSpinner();
           this.deleteTextMsg="Record successfully deleted.";
          // UIkit.modal('#delete-result-modal').show();
          $('#delete-result-modal').addClass('uk-open').show();
          }
        },
      (err) => {
        this._spinner.hideSpinner();
        this.deleteTextMsg="Error occured while deleting.";
       // UIkit.modal('#delete-result-modal').show();
        $('#delete-result-modal').addClass('uk-open').show();
      }
    )
  }

  //for delete functionality
  getDeletedRowdata(){
    //this.agGridRateFlag = false;
    this._spinner.showSpinner();
    //#NIIT CR4 >>>>BEGIN
    var backendData = this._joborderService.getCostRateTableData({"routingID": this.contractResulttableDataRowObj.routingId,"fsc":this.contractResulttableDataRowObj.paymentFsc,"rateType":"BILLING_RATE","terminalDepotCode":this.contractResulttableDataRowObj.fromTerminal});
    backendData.subscribe(
      (data) => {       
        if(data == "userSessionExpired"){
          this._sessionTimeOutService.checkSessionTimeout(data);
        }
        else if (data.hasOwnProperty("errorCode")) {
          this.deleteBillRefreshTable([], this.contractResulttableDataRowObj,this.EditedNonDeletedRows)
        } else {
          this.agGridRateFlag = true;
          this.deleteBillRefreshTable(data['billingRateResults']['results'], this.contractResulttableDataRowObj,this.EditedNonDeletedRows);
          this._spinner.hideSpinner();
        }
      });
  }


  //for delete fumctionality
  deleteBillRefreshTable(tableData, rowObj,editedNonDeletedObj) {
    this.contractResulttableDataRowObj = rowObj;

    var deleteData = tableData.concat(editedNonDeletedObj);

    //  this.gridOptions.rowData = tableData;
    //this.gridOptions.api.setRowData(this.gridOptions.rowData);

    this.billTableRowData = deleteData;
    //this.gridOptions.rowData = tableData;
    //this.gridOptions.api.setRowData(this.gridOptions.rowData);

    this.selectedRowData = false;
    this._spinner.hideSpinner();
  }

  rejectRowBillRate(e) {
    this.addEditCostRateObj = {};
    this.ijsRateVOList = [];
    e.forEach(element => {
      let ijsRate = element;
      this.ijsRateVOList.push(ijsRate);
    });
    this.addEditCostRateObj["routingNumber"] = this.contractSelectedRow.routingId;
    this.addEditCostRateObj["action"] = "rejectBillingRate";
    this.addEditCostRateObj["ijsRateList"] = this.ijsRateVOList;
    //#NIIT CR4 >>>>BEGIN
    this.addEditCostRateObj["paymentFSC"] = this.contractSelectedRow.paymentFsc;
    this.addEditCostRateObj["rateType"] = "BILLING_RATE";
    //#NIIT CR4 >>>>END
    this.getBackEndData(this.addEditCostRateObj);

  }

  approveRowBillRate(e) {
    this.addEditCostRateObj = {};
    this.ijsRateVOList = [];
    e.forEach(element => {
      let ijsRate = element;
      this.ijsRateVOList.push(ijsRate);
    });
    this.addEditCostRateObj["routingNumber"] = this.contractSelectedRow.routingId;
    this.addEditCostRateObj["action"] = "approveBillingRate";
    this.addEditCostRateObj["ijsRateList"] = this.ijsRateVOList;
    //#NIIT CR4 >>>>BEGIN
    this.addEditCostRateObj["paymentFSC"] = this.contractSelectedRow.paymentFsc;
    this.addEditCostRateObj["rateType"] = "BILLING_RATE";
    //#NIIT CR4 >>>>END
    this.getBackEndData(this.addEditCostRateObj);
  }


  //Add bill rate component row
  showAddBillRate(e) {
    this.addEditBillRateComponentFlag = false;

    this._addeEditBillRate
      .addEditCostRateObj = {
        ijsRateVO: {
          mtOrLaden: "Laden",
          rateBasis: "S",
          eqCatq: "B",
          rateStatus: "O",
          service: '***',
          impOrExp: "ALL",
          splHandling: "ALL",
          eqType: "",
          mot: this.contractResulttableDataRowObj['transMode'],
          endDate: this.contractSelectedRow.endDate,
          startDate: this.contractSelectedRow.startDate,
          currency: this.contractSelectedRow.currency
        },
        action: "addBillingRate",
        routingNumber: this.contractResulttableDataRowObj.routingId

      };
    this._addeEditBillRate.addEditLabel = "Add";
    jQuery("html, body").animate({ scrollTop: 0 }, 900);
  }

  //edit bill rate component row
  showEditBillRate(e) {

    this.addEditBillRateComponentFlag = false;
    this._addeEditBillRate.addEditCostRateObj.routingNumber = this.contractResulttableDataRowObj.routingId
    this._addeEditBillRate.addEditCostRateObj.action = "editBillingRate";
    this._addeEditBillRate.addEditCostRateObj.ijsRateVO = this._addeEditBillRate._billMappingData.dataMappingMethod(this.selectedRowData);

    this._addeEditBillRate.addEditCostRateObj.ijsRateVO.mot = this.contractResulttableDataRowObj['transMode'];
    this._addeEditBillRate.addEditCostRateObj.ijsRateVO.mtOrLaden = this.selectedRowData['mtOrLaden'];


    if (this._addeEditBillRate.addEditCostRateObj.ijsRateVO['eqType']) {
      this._addeEditBillRate.addEditCostRateObj.ijsRateVO['eqType'] = this.selectedRowData['eqType'].split(",")
    }
    //#NIIT CR4 >>>>BEGIN
    this._addeEditBillRate.addEditCostRateObj.ijsRateVO.portCode = "";
    this._addeEditBillRate.addEditCostRateObj.ijsRateVO.imdgCode = "";
    //#NIIT CR4 >>>>END
    this._addeEditBillRate.addEditCostRateObj.ijsRateVO.oogSetup = "";
    this._addeEditBillRate.addEditCostRateObj.ijsRateVO.splCostByBlOrBooking = "";
    this._addeEditBillRate.addEditCostRateObj.ijsRateVO.currency = this.selectedRowData['currency'];
    this._addeEditBillRate.addEditLabel = "Edit";

    jQuery("html, body").animate({ scrollTop: 0 }, 900);
  }

  //hide the add edit bill rate component
  hideAddEditBillRate(e) {
    this.addEditBillRateComponentFlag = true;
    this.selectedRowData = undefined;
    this.billclick.emit();
    this.getRowdata();
    
  }

  reFreshBillTable(e){
    //alert("Refershing table");
    this.getRowdata();
  }


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

      }
    )
  }




  getRowdata() {
    //this.agGridRateFlag = false;
    this._spinner.showSpinner();
    //#NIIT CR4 >>>>BEGIN
    var backendData = this._joborderService.getCostRateTableData({"routingID": this.contractResulttableDataRowObj.routingId,"fsc":this.contractResulttableDataRowObj.paymentFsc,"rateType":"BILLING_RATE","terminalDepotCode":this.contractResulttableDataRowObj.fromTerminal});
    backendData.subscribe(
      (data) => {      
        if(data == "userSessionExpired"){
          this._sessionTimeOutService.checkSessionTimeout(data);
        }
        else if (data.hasOwnProperty("errorCode")) {
          this.billRefreshTable([], this.contractResulttableDataRowObj)
        } else {
          this.agGridRateFlag = true;
          this.billRefreshTable(data['billingRateResults']['results'], this.contractResulttableDataRowObj);
          this._spinner.hideSpinner();
        }
      });
  }

  rateTextMsg:any;
  saveBilldata(e) {
   // alert('333');
    let contFlag : boolean =true;
    this.ijsRateVOList = [];
    this.addEditCostRateObj = {};   
    e.forEach(element => {
      let ijsRate = element;//{ijsRateVO:element};
      ijsRate["mot"] = this.contractSelectedRow.transMode;
      ijsRate.errorAllreadySet = false;
      ijsRate.errorMsg = undefined;
      if (element.rate20 < 0 || element.rate40 < 0 || element.rate45 < 0 || element.lumpSum < 0 || element.upto < 0) {
        element.selectedFlag = false;
        contFlag = false;
        this.rateTextMsg = "Rates/Lump Sum/Upto cannot be negative.";
        this.cd.markForCheck();
        $('#priority-warning-modal').addClass('uk-open').show();
       // UIkit.modal('#priority-warning-modal').show();
        this.billRates = element;
        return contFlag;
      } else {
        ijsRate["eqTypeList"] = ijsRate["eqType"];
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

  public billRates = [];
  saveContract(){
    this.saveCostRateErrorMsg = [];
    this.billRates = [];
    this.addEditCostRateObj["routingNumber"] = this.contractSelectedRow.routingId;
    this.addEditCostRateObj["contractNumber"] = this.contractSelectedRow.contractId;
    this.addEditCostRateObj["ijsRateList"] = this.ijsRateVOList;
    this.addEditCostRateObj["action"] = "addEditBillRateList";
    //#NIIT CR4 >>>>BEGIN
    this.addEditCostRateObj["paymentFSC"] = this.contractSelectedRow.paymentFsc;
    this.addEditCostRateObj["rateType"] = "BILLING_RATE";
    //#NIIT CR4 >>>>END    
    this._spinner.showSpinner();
    this.agGridRateFlag = false;
    var backendData = this._joborderService.saveBillRateData(this.addEditCostRateObj);
    backendData.subscribe(
      (data) => {       
        if(data == "userSessionExpired"){
          this._sessionTimeOutService.checkSessionTimeout(data);
        }
        else{
        data.billingRateResults.results.forEach(element => {
          if (element.errorAllreadySet == true) {
            //this.saveCostRateErrorMsg.push('Bill rate for Start date - ' + element.startDate + ', End date - ' + element.endDate + ', Mt/Laden - ' + element.mtOrLaden + ' could not be Saved Successfully.');
            this.saveCostRateErrorMsg.push(this._serverErrorCode.checkError(element.errorMsg));
            element.editRow = true;
            element.errorAllreadySet = false;
            element.errorMsg = undefined;
            element['eqType'] = element['eqType'].split(',');
            element.selectedFlag = true;      
            element.portCode = undefined; //to show fields during error //#NIIT CR4 
            element.imdgCode = undefined; //to show fields during error //#NIIT CR4 
            element.oogSetup = undefined; //to show fields during error
            element.custType = undefined; //to show fields during error
            element.lumpSum = undefined; //to show fields during error
            this.billRates.push(element);
            this.cd.markForCheck();
          }
        });
        if (this.saveCostRateErrorMsg.length == 0) {
          this.saveCostRateErrorMsg.push('Bill rate saved successfully');
          this.cd.markForCheck();
        }
        this.billTableRowData = data.billingRateResults.results;        
        this.agGridRateFlag = true;
        this._spinner.hideSpinner();
        this.cd.markForCheck();
        $('#delete-warnings-modal2').addClass('uk-open').show(); 
      }
    },
      (err) => {
        this._spinner.hideSpinner();
        this.cd.markForCheck();
        $('#delete-warnings-modal2').addClass('uk-open').show(); 
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

