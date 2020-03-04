import { Component, OnInit, Input, Output, EventEmitter, ViewChild , ChangeDetectionStrategy, ChangeDetectorRef } from '@angular/core';
import * as _ from 'lodash';
import { TableMappingData } from './add-edit-mappings';
import { SpinnerServiceService } from "../../common-services/spinner-service.service";
import { ServerErrorcodeService } from "../../common-services/server-errorcode.service";
import { ContractSearchService } from '../contract-search/contract-search.service';
import { UserTypeService } from "../../user/user-type.service";
declare var jQuery: any;
declare var UIkit: any;
import * as uikit from 'uikit';
import * as $ from 'jquery'
import { SessionTimeOutService } from "app/common-services/session-time-out.service";

@Component({
  selector: 'app-billing-table',
  templateUrl: './billing-table.component.html',
  styleUrls: ['./billing-table.component.scss'],
  changeDetection:ChangeDetectionStrategy.OnPush
})
export class BillingTableComponent implements OnInit {

  value: any;
  @Input() exemptedBillFlag: boolean;
  @Input() columnDefs: any[];
  @Input() rowData: any[] = [];
  @Input() termCodesList: any;
  @Input() eqTypeList: any;
  @Input() selectedContarctRow: any;
  @ViewChild('addeEditBillRate') _addeEditBillRate: any;

  @Output() save: EventEmitter<any> = new EventEmitter();
  @Output() deleteRow: EventEmitter<any> = new EventEmitter();
  @Output() approveRow: EventEmitter<any> = new EventEmitter();
  @Output() rejectRow: EventEmitter<any> = new EventEmitter();
  @Output() reFreshBillTable: EventEmitter<any> = new EventEmitter();

  addEditBillRateObj = {
    ijsRateVO: {
      mtOrLaden: "Laden",
      rateBasis: "S",
      eqCatq: "B",
      rateStatus: "O",
      //impOrExp: "ALL",
      eqType: "***",
      service: '***'
    }
  };

  private selectedRowData: any;
  private agGridRateFlag: boolean = true;

  _mappingData: any;

  newRow: any = {
    // 'startDate': undefined,
    // 'endDate': undefined,
    // 'service': undefined,
    // 'vesselCodes': undefined,
    // 'mtOrLaden': undefined,
    // 'rateBasis': undefined,
    // 'eqCatq': undefined,
    // 'rateStatus': undefined,
    // 'term': undefined,
    // 'eqType': undefined,
    // 'upto': undefined,
    // 'splHandling': undefined,
    // 'currency': undefined,
    // 'portCode': undefined,
    // 'imdgCode': undefined,
    // 'oogSetup': undefined,
    // 'rate20': undefined,
    // 'rate40': undefined,
    // 'rate45': undefined,
    // 'lumpSum': undefined,
  };



  constructor(private cd: ChangeDetectorRef,public _joborderService: ContractSearchService, private _spinner: SpinnerServiceService, public _serverErrorCode: ServerErrorcodeService,private _userTypeService: UserTypeService,private _sessionTimeOutService:SessionTimeOutService) {
    this._mappingData = new TableMappingData();
    jQuery(document).ready(function() {
      jQuery(window).keydown(function(event){
        if(event.keyCode == 13) {
          jQuery(".lookup-container").hide();
          event.preventDefault();
          return false;
        }
      });
    });
  }

  userType: string;
  ngOnInit() {
    this._mappingData.eQTypeOptions = this.eqTypeList;
   
    setInterval(()=>{
      this.cd.markForCheck();
    },200)
    this.userType = this._userTypeService.getValue();
    this.tempRowData = this.rowData;   
    this.columnDefs.forEach(element => {
      this.newRow[element.columnId] = undefined;
    });
    this.newRow.edit = true;
  }


  ediTable = true;
  private checkedSelectedRows: any = [];

  private mtLadenOptions: any = [
    {
      label: 'MT',
      value: 'MT',
    }, {
      label: 'LADEN',
      value: 'LADEN',
    }
  ];
  private rateBasisOptions: any = [
    {
      label: 'Size/Type',
      value: 'S'
     }
    //, {
    //   label: 'BKG/BL',
    //   value: 'B'
    // },
    // {
    //   label: 'Lump Sum',
    //   value: 'L'
    // }
  ];
  private eQCatgOptions: any = [
    {
      label: 'Chassis/trailer',
      value: 'S',
    }, {
      label: 'Box',
      value: 'B',
    }
  ];
  private rateStatusOptions: any = [
    {
      label: 'Open',
      value: 'O',
    }, {
      label: 'Pending',
      value: 'P',
    },
    {
      label: 'Approved',
      value: 'A',
    },
    {
      label: 'Rejected',
      value: 'R',
    }
  ]
  private eQTypeOptions: any = [
    {
      label: '**',
      value: 'ALL',
    }, {
      label: 'GP',
      value: 'GP',
    }, {
      label: 'HC',
      value: 'HC',
    }, {
      label: 'TK',
      value: 'TK',
    }
  ];
  private termOptions: any = [];
  private calcMethodOptions: any = [
    {
      label: 'Tier*UOM',
      value: '1',
    }, {
      label: 'Fix*UOM',
      value: '2',
    }, {
      label: 'Tier Amount',
      value: '3',
    }, {
      label: 'Fix Amount',
      value: '4',
    }
  ];
  private uomOptions: any = [
    {
      label: 'Kilo',
      value: 'K',
    }, {
      label: 'Ton',
      value: 'T',
    }
  ];
  private impExpOptions: any = [
    {
      label: 'All',
      value: "**",
    }, {
      label: 'IMP',
      value: 'IMP',
    }, {
      label: 'EXP',
      value: 'EXP',
    }
  ];
  private splHandlingOptions: any = [

    {
      label: 'Normal',
      value: "N",
    },
    {
      label: 'Reefer DG',
      value: 'RDG',
    },
    {
      label: 'OOG DG',
      value: 'ODG',
    },
    {
      label: 'RF',
      value: 'RF',
    }, {
      label: 'OOG',
      value: '0G',
    }, {
      label: 'DG',
      value: 'D1',
    }, {
      label: 'Door Ajar',
      value: 'DA',
    }, {
      label: 'Open Door',
      value: 'OD',
    }, {
      label: 'BBK',
      value: 'BBK',
    }
  ];

  addEditSelectConfig: any = {
    highlight: false,
    create: false,
    persist: true,
    plugins: ['dropdown_direction', 'remove_button'],
    dropdownDirection: 'up',
    labelField: 'label',
    valueField: 'value',
    searchField: ['label'],
    maxItems: 1
  };
  addEditMultiSelectConfig: any = {
    highlight: true,
    create: false,
    persist: true,
    plugins: ['dropdown_direction', 'remove_button'],
    dropdownDirection: 'up',
    labelField: 'label',
    valueField: 'value',
    searchField: ['label'],
    maxItems: 6
  };
  vesselLookUpData: any = [{ "dropDownData": [{ "label": "Vessel Code", "value": "VSVESS" }, { "label": "Vessel Name", "value": "VSLGNM" }, { "label": "Operator Code", "value": "VSOPCD" }] }];
  currencyLookUpData: any = [{ "dropDownData": [{ "label": "Currency Code", "value": "CURRENCY_CODE" }, { "label": "Currency Name", "value": "CURRENCY_NAME" }] }];


  mydata: any;
  // rowData = [{ "endDate": "10/10/2013", "startDate": "01/10/2012", "service": "***", "rateBasis": "Size/Type", "eqCatq": "Box", "rateStatus": "Open", "chargeCode": "11001", "calcMethod": "Fix Amount", "eqType": "GP,HC", "upto": 0, "currency": "USD", "rate20": 170, "rate40": 340, "rate45": 0, "errorMsg": "IJS_MSG_1004", "portAndImdgSeqNum": 0, "oogSetupSeqNum": 0, "costRateSequenceNum": 1, "costRateDetailSeqNum": 0, "eqTypeSeqNum": 0, "termSeqNum": 0, "detailSeqNumbers": "1,2,3", "vesselSeq": 0 }, { "rateStatus": "Open", "endDate": "10/10/2013", "service": "***", "eqCatq": "Box", "chargeCode": "11001", "calcMethod": "Fix Amount", "eqType": "GP,HC", "rateBasis": "Size/Type", "upto": 0, "currency": "USD", "rate20": 0, "rate40": 340, "rate45": 0, "errorMsg": "IJS_MSG_1004", "portAndImdgSeqNum": 0, "oogSetupSeqNum": 0, "costRateSequenceNum": 1, "costRateDetailSeqNum": 0, "eqTypeSeqNum": 0, "termSeqNum": 0, "detailSeqNumbers": "1,2,3", "vesselSeq": 0, "startDate": "01/10/2012" }]
  hideHeader(e) {
    this.mydata = e
    jQuery(".table-header-cell").eq(this.mydata).toggle();
    jQuery(".resp-table-row").each(function () {
      var myVal = e;
      jQuery(this).find('.table-body-cell').eq(myVal).toggle();
      //  jQuery(this).next('.table-body-cell').eq(this.mydata).hide();
    })

  }

  refreshTable(row_data) {
    this.rowData = row_data;    
  }

  colmDataEntries(colmData) {
    let arryPro = Object.keys(colmData);
    return arryPro;
  }
  
  reFreshBillTableFunc(e) {
    this.addEditBillRateComponentFlag = true;
    //alert("success Close");
    this.checkedSelectedRows = [];
    this.reFreshBillTable.emit(e);
  }

  hideAddNewBillRate(e) {
    this.addEditBillRateComponentFlag = true;
    //alert("Normal Close");
    this.reFreshBillTable.emit(e);
    this.checkedSelectedRows =  [];
    
  }

  tempRowData: any[] = [];
  addRow(e) {
    let a: any = {};
    this.columnDefs.forEach(element => {
      a[element.columnId] = undefined;
    });
    a['editRow'] = true;
    a['selectedFlag'] = true;
    a['operation'] = 'addBillingRate';
    a.mtOrLaden = "LADEN";
    a.rateBasis = 'S';
    a.eqType = ["**"];
    a.eqCatq = "B";
    if(this.userType != "Global"){
      a.rateStatus = "O";
    }
    a.rateStatus = "A";
    a.impOrExp = "ALL";
    a.splHandling = "";
    a.service = "***";
    a.vesselCodes = "***";
    a.startDate = this.selectedContarctRow.startDate;
    a.endDate = this.selectedContarctRow.endDate;
    a.currency = this.selectedContarctRow.currency;



    this.tempRowData.push(a);
    this.checkedSelectedRows.push(a);
    this.rowData = this.tempRowData;
    a = {};    
  }

  showHideRow(colmData) {
    if (!colmData.editRow) {
      colmData.editRow = true;
    }
  }

  private costSelectTableRowCheckBox(e, rowObj) {
    if (e.target.checked) {
      this.checkedSelectedRows.push(rowObj);
    } else {
      this.checkedSelectedRows = this.deleteObj(this.checkedSelectedRows, rowObj);
    }
  }

  //delete element from array
  deleteObj(arr, rowObj) {
    var i = arr.length;
    while (i--) {
      if (_.isEqual(arr[i], rowObj)) {
        arr.splice(i, 1);
      }
    }
    return arr;
  }


  editSelectedRow() {
    this.rowData.forEach(element => {
      if (element.selectedFlag == true) {

        element = this._mappingData.dataMappingMethod(element);

        if (!element.operation) {
          element.operation = 'editBillingRate';
        }
        let seletedRow = Object.keys(element);
        this.columnDefs.forEach(ele => {
          if (!seletedRow.includes(ele.columnId)) {
            element[ele.columnId] = undefined;
          }
        });
        if (element['eqType']) {
          element['eqType'] = element['eqType'].split(',');
        }
        if (!element['service']) {
          element['service'] = "***"
        }

        element['currency'] = element.currency;
        element.editRow = true;
      }
    });    
  }

  copyRow(e) {
    if (this.checkedSelectedRows.length == 1) {
      let copiedRow = Object.assign({}, this.checkedSelectedRows[0]);
      copiedRow.operation = 'addBillingRate';
      // let copiedRow = this.checkedSelectedRows[0]; 
      copiedRow = this._mappingData.dataMappingMethod(copiedRow);
      let seletedRow = Object.keys(copiedRow);
      this.columnDefs.forEach(ele => {
        if (!seletedRow.includes(ele.columnId)) {
          copiedRow[ele.columnId] = undefined;
        }
      });

      if (copiedRow['eqType']) {
        copiedRow['eqType'] = copiedRow['eqType'].split(',');
      }
      copiedRow.editRow = true;
      //copiedRow.operation = 'addCostRate';
      copiedRow.selectedFlag = false;
      //this.checkedSelectedRows.push(copiedRow);
      this.checkedSelectedRows[0].selectedFlag = false;
      copiedRow.selectedFlag = true;
      this.checkedSelectedRows.push(copiedRow);
      this.rowData.push(copiedRow);
    }
  }

  saveRows() {
    //alert('222');
    let filteredArrays = [];
    this.checkedSelectedRows.forEach(element => {
      if (element.editRow == true) {
        filteredArrays.push(element);
      }
    });
    this.save.emit(filteredArrays);    
  }

  settingButton(billRateData){
    this.checkedSelectedRows = billRateData;   
  }

  deleteTextMsg:any;
  deleteBillRecord() {
    this.deleteTextMsg = "Do you want to delete?";  
    $('#delete-bill-warnings-modal').addClass('uk-open').show();
  }

  closeWarning() {    
    $('#delete-bill-warnings-modal').addClass('uk-open').hide();
  }

  deleteSelectedRows() {
    try {
      this.checkedSelectedRows.forEach(element => {
      element.operation = 'deleteBillingRate'
    });
    $('#delete-bill-warnings-modal').addClass('uk-open').hide();
    this.deleteRow.emit(this.checkedSelectedRows);
    this.tempRowData = this.tempRowData.filter(word => word.deletedFlag != true );
    this.checkedSelectedRows = [];
    } catch (error) {
      console.log("error while deleting rate records in bill table");
    }    
  }



  approveSelectedRows() {
    this.checkedSelectedRows.forEach(element => {
      element.operation = 'approveBillingRate';
      let x = jQuery("#resp-table-body").find(".form-control").hasClass('ng-valid');
      if (x == true) {
        UIkit.modal('#reject-warnings-modal').show();
        return false;
  
      }
      else {
        this.approveRow.emit(this.checkedSelectedRows);
        this.checkedSelectedRows = [];
      }
       
    });
    

  }

  rejectSelectedRows() {
    this.checkedSelectedRows.forEach(element => {
      element.operation = 'rejectBillingRate'
      let x = jQuery("#resp-table-body").find(".form-control").hasClass('ng-valid');
      if (x == true) {
        UIkit.modal('#reject-warnings-modal').show();
        return false;
  
      }
      else {
        this.rejectRow.emit(this.checkedSelectedRows);
        this.checkedSelectedRows = [];
      }
       
    });

  }

  clearField(e, currentRow) {
    if (e == '0G') {
      currentRow['portCode'] = undefined;
      currentRow['imdgCode'] = undefined;
    } else if (e == 'N') {
      currentRow['portCode'] = undefined;
      currentRow['imdgCode'] = undefined;
      currentRow['oogCode'] = undefined;
    } else if (e == 'RDG') {
      currentRow['portCode'] = undefined;
      currentRow['imdgCode'] = undefined;
      currentRow['oogCode'] = undefined;
    } else if (e == 'ODG') {
      currentRow['portCode'] = undefined;
      currentRow['imdgCode'] = undefined;
      currentRow['oogSetup'] = undefined;
    } else if (e == 'RF') {
      currentRow['portCode'] = undefined;
      currentRow['imdgCode'] = undefined;
      currentRow['oogSetup'] = undefined;
    } else if (e == 'D1') {
      currentRow['portCode'] = undefined;
      currentRow['imdgCode'] = undefined;
      currentRow['oogSetup'] = undefined;
    } else if (e == 'DA') {
      currentRow['portCode'] = undefined;
      currentRow['imdgCode'] = undefined;
      currentRow['oogSetup'] = undefined;
    } else if (e == 'OD') {
      currentRow['portCode'] = undefined;
      currentRow['imdgCode'] = undefined;
      currentRow['oogSetup'] = undefined;
    } else if (e == 'BBK') {
      currentRow['portCode'] = undefined;
      currentRow['imdgCode'] = undefined;
      currentRow['oogSetup'] = undefined;
    }
  }
  addEditBillRateComponentFlag: boolean = true;
  //Add bill rate component row

  showAddBillRate(e) {
    this.addEditBillRateComponentFlag = false;

    this._addeEditBillRate
      .addEditBillRateObj = {
        ijsRateVO: {
          mtOrLaden: "LADEN",
          rateBasis: "S",
          eqCatq: "B",
          rateStatus: "O",
          service: '***',
          impOrExp: "ALL",
          splHandling: "ALL",
          eqType: "**", //double ** asterik as mapping done wrt to data coming from backend
          mot: this.selectedContarctRow['transMode'],
          endDate: this.selectedContarctRow.endDate,
          startDate: this.selectedContarctRow.startDate,
          currency: this.selectedContarctRow.currency
        },
        action: "addBillingRate",
         routingNumber: this.selectedContarctRow.routingId

      };
    this._addeEditBillRate.setRateStatus(); //to set rateStatus on the basis of user type  
    this._addeEditBillRate.addEditLabel="Add";

    //UIkit.modal('#add-edit-bill-container').show();
    jQuery("html, body").animate({ scrollTop: 0 }, 900);

  }

  showEditBillRate() {
    this.checkedSelectedRows[0];
    this.addEditBillRateComponentFlag = false;
    this._addeEditBillRate.addEditBillRateObj.routingNumber = this.selectedContarctRow.routingId
    this._addeEditBillRate.addEditBillRateObj.action = "editBillingRate";
    let  checkedRowDataTemp =  Object.assign({},this.checkedSelectedRows[0]);
    //this._addeEditBillRate.addEditBillRateObj.ijsRateVO = this._addeEditBillRate._billMappingData.dataMappingMethod(this.checkedSelectedRows[0]);
    this._addeEditBillRate.addEditBillRateObj.ijsRateVO = this._addeEditBillRate._billMappingData.dataMappingMethod(checkedRowDataTemp);

    this._addeEditBillRate.addEditBillRateObj.ijsRateVO.mot = this.selectedContarctRow['transMode'];
    
    //this._addeEditBillRate.addEditBillRateObj.ijsRateVO.mtOrLaden = this.selectedRowData['mtOrLaden'];
    //this._addeEditBillRate.addEditBillRateObj.ijsRateVO.mtOrLaden = this.selectedRowData['mtOrLaden']; 

    //this._addeEditBillRate.addEditBillRateObj.ijsRateVO['eqType'] = this.selectedRowData['eqType'].split(",")
    
         if (this._addeEditBillRate.addEditBillRateObj.ijsRateVO['eqType']) {
           this._addeEditBillRate.addEditBillRateObj.ijsRateVO['eqType'] = this._addeEditBillRate.addEditBillRateObj.ijsRateVO['eqType'].split(",")
         }

    this._addeEditBillRate.addEditBillRateObj.ijsRateVO.portCode = "";
    this._addeEditBillRate.addEditBillRateObj.ijsRateVO.imdgCode = "";
    this._addeEditBillRate.addEditBillRateObj.ijsRateVO.oogSetup = "";
    this._addeEditBillRate.addEditBillRateObj.ijsRateVO.splCostByBlOrBooking = "";
    //#NIIT CR4 commented as it was taking the contract currency
    //this._addeEditBillRate.addEditBillRateObj.ijsRateVO.currency = this.selectedContarctRow['currency'];
    this._addeEditBillRate.addEditLabel = "Edit";  
   // UIkit.modal('#add-edit-bill-container').show();
    jQuery("html, body").animate({ scrollTop: 0 }, 900);

  }

  hideAddEditBillRate(e) {
  
    this.addEditBillRateComponentFlag = true;
    this.selectedRowData = undefined;
    this.getRowdata();
  }


  getRowdata() {
    this.agGridRateFlag = false;
    this._spinner.showSpinner();
    //#NIIT CR4 >>>>BEGIN
    var backendData = this._joborderService.getCostRateTableData({"routingID": this.selectedContarctRow.routingId,"fsc": this.selectedContarctRow.paymentFsc,"rateType":"BILLING_RATE","terminalDepotCode":this.selectedContarctRow.fromTerminal});
    backendData.subscribe(
      (data) => {      
        if(data == "userSessionExpired"){
          this._sessionTimeOutService.checkSessionTimeout(data);
        }        
        else if (data.hasOwnProperty("errorCode")) {
          this.billRefreshTable([], this.selectedContarctRow)
        } else {
          this.billRefreshTable(data['billingRateResults']['results'], this.selectedContarctRow);
          this.agGridRateFlag = true;
          this._spinner.hideSpinner();
        }
      });
  }

  //enabling and disabling of fields with value change
  RateBasisChanged(e) {
    let rateBasis = e;
    this.rowData.forEach(element => {
      if (rateBasis == "S") {
        element.lumpSum = null;
      }
      if (rateBasis == "B") {
        element.rate20 = null;
        element.rate40 = null;
        element.rate45 = null;
      }
      if (rateBasis == "L") {
        element.rate20 = null;
        element.rate40 = null;
        element.rate45 = null;
      }
    })
    rateBasis = null;
  }

  //billOperationData = {};
  successtextFlag:boolean;
  successTextMsg: string;
  printSuccessMsg(e) {
    //this.billOperationData = e;
    this.successtextFlag = e.operationFlag; 
    this.successTextMsg = e.operationMessage;    
    setTimeout(()=>{
      UIkit.modal('#add-edit-bill').show();
    },1000);
    //this.reFreshBillTableFunc(e);
    //alert('checking value ' + JSON.stringify(this.billOperationData));
  }

  closeAddEditBillRate(e){
     UIkit.modal('#add-edit-bill').hide();
     this.reFreshBillTableFunc(e);
  }
  

  billRefreshTable(tableData, rowObj) {
    this.selectedContarctRow = rowObj;
    this.billRefreshTable = tableData;
    //   this.gridOptions.api.setRowData(this.gridOptions.rowData);
    this.selectedRowData = false;

  }
  showpopdiv:boolean = true;
  showPopUp(e){
    this.showpopdiv = !this.showpopdiv;
  }

  //#NIIT CR4 >>>>BEGIN
  closePopup(){
    this.showpopdiv = !this.showpopdiv;
  }
  //#NIIT CR4 >>>>END

}
