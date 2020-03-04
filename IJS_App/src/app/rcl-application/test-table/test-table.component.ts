import { Component, OnInit, Input, Output, ViewChild, EventEmitter,DoCheck, ChangeDetectorRef, ChangeDetectionStrategy,ViewContainerRef } from '@angular/core';
import * as _ from 'lodash';
import { TableMappingData } from './add-edit-mappings';
import {SpecialHandlingService } from '../../common-services/special-handling.service';
import {PortClassService } from '../../common-services/port-class.service';
import {ImdgClassService } from '../../common-services/imdg-class.service';
import { ModalDialogModule,IModalDialog,ModalDialogService } from 'ngx-modal-dialog';
import { PortClassSetupComponent } from '../contract-search/port-class-setup/port-class-setup.component';
import { ImdgClassSetupComponent } from '../contract-search/imdg-class-setup/imdg-class-setup.component';
import { OogDimentionSetupComponent } from '../contract-search/oog-dimention-setup/oog-dimention-setup.component';
import * as uikit from 'uikit';
import * as $ from 'jquery';
import { ContractSearchService } from "app/rcl-application/contract-search/contract-search.service";
import { SpinnerServiceService } from "app/common-services/spinner-service.service";
import { SessionTimeOutService } from "app/common-services/session-time-out.service";
declare var jQuery: any;
declare var UIkit: any;

@Component({
  selector: 'app-test-table',
  templateUrl: './test-table.component.html',
  styleUrls: ['./test-table.component.scss'],
  changeDetection:ChangeDetectionStrategy.OnPush,
})
export class TestTableComponent implements OnInit {
  value: any;


  private domLayout;
  private selectedRowData: any;
  private contractResulttableDataRowObj: any;
  private agGridRateFlag: boolean = true;
  addEditCostRateObj: any = {};
  ijsRateVOList: any[] = [];
  saveCostRateErrorMsg: any[];
  agGridCostFlag: boolean;
  private modalService:any;
  private viewRef:any;

  @Input() columnDefs: any[];
  @Input() rowData: any[] = [];
  @Input() termCodesList: any;
  @Input() eqTypeList: any;
  @Input() selectedContarctRow: any;
  @Input() userType: any;
  @Input() private contractSelectedRow: any;
  @Input() contractExemptedFlag: boolean;

  //#NIIT CR4 >>>>BEGIN
  @Input() oogCodeListData: any;
  @Input() portCodeListData: any;
  @Input() imdgCodeListData: any;
  @Input() userSelectedRowOnClick: any;
  oogOptions:any=[];
  portOptions:any=[];
  imdgOptions:any=[];
  //#NIIT CR4 >>>>END

  _mappingData: any;

  newRow: any = {};


  @Output() save: EventEmitter<any> = new EventEmitter();
  @Output() deleteRow: EventEmitter<any> = new EventEmitter();
  @Output() approveRow: EventEmitter<any> = new EventEmitter();
  @Output() rejectRow: EventEmitter<any> = new EventEmitter();
  @Output() costBillTable: EventEmitter<any> = new EventEmitter();
  @Output() checkingUserType: EventEmitter<any> = new EventEmitter();
  @Output() saveOogData: EventEmitter<any> = new EventEmitter();//#NIIT CR4
  @Output() savePortImdgClassData: EventEmitter<any> = new EventEmitter();//#NIIT CR4 
  @Output() refreshTableData: EventEmitter<any> = new EventEmitter();//#NIIT CR4
  @ViewChild('OogDimentionSetup') _oogDimentionSetup: any;
  @ViewChild('spclCostBlBooking') _spclCostBlBooking: any;
  @ViewChild('imDgClassSetup') _imDgClassSetup: any;
  @ViewChild('portClassSetup') _portClassSetup: any;
  @ViewChild('costBillForm') _costBillForm: any;

  @ViewChild('addeEditBillRate') _addeEditBillRate: any;



  constructor(private cd: ChangeDetectorRef,private shService:SpecialHandlingService,private portService:PortClassService,private imdgService:ImdgClassService,modalService: ModalDialogService, viewRef: ViewContainerRef,public _contractSearchService: ContractSearchService,private _spinner: SpinnerServiceService,private _sessionTimeOutService:SessionTimeOutService) {
    this.modalService = modalService;
    this.viewRef = viewRef;
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

  ngOnChanges(){
    
    //#NIIT CR4 >>>>BEGIN
    this.oogOptions = [];
    this.portOptions = [];
    this.imdgOptions = [];
    this.oogOptions = this.oogCodeListData;
    this.portOptions = this.portCodeListData;
    this.imdgOptions = this.imdgCodeListData;
    this.tempRowData = this.rowData;
    this.checkedSelectedRows = []; //to make this object empty when test table is refreshed on any operation
    //#NIIT CR4 >>>>END
  }


  ngOnInit() { 
    
    //#NIIT CR4 >>>>BEGIN
    this.oogOptions = this.oogCodeListData;
    this.portOptions = this.portCodeListData;
    this.imdgOptions = this.imdgCodeListData;
    //#NIIT CR4 >>>>END
    
   
    //to handle data coming from backend when Rate Basis is BKG/BL or Lump Sum
    this.rowData.forEach(element => {
      if((element.rateBasis=="BKG/BL" || element.rateBasis=="Lump Sum") && element.editRow!=true){
        element.mtOrLaden=null;
        element.eqCatq=null;
        element.eqType=null;
      }
    });   
    this._mappingData.eQTypeOptions = this.eqTypeList; //to show backend values in eqtype   
    
      setInterval(()=>{
        this.cd.markForCheck();
      },200)
   
    this.tempRowData = this.rowData;    
    this.columnDefs.forEach(element => {
      this.newRow[element.columnId] = undefined;
    });
    this.newRow.edit = true;


    this.shService.currentNameSubject.subscribe((val)=>{
        try{
          this.getValueOogSetup(val);
        } catch(error){
          console.log("error in current name subject");
        }               
      });

    this.portService.portClassSubject.subscribe((val)=>{       
      try{
        this.getValuePortClassSetup(val);
      } catch(error){
        console.log("error")
      }           
    });  

    //#NIIT CR4 >>>>BEGIN
    //refreshing the table on button close
    this.portService.refreshSubject.subscribe((val)=>{
      try{
        this.refreshCostTable(val);
      } catch(error){
        console.log("error")
      } 
    });    

    //refreshing the table on button close
    this.shService.refreshOogSubject.subscribe((val)=>{
      try{
        this.refreshCostTable(val);
      } catch(error){
        console.log("error")
      } 
    });
    
    //refreshing the table on button close
    this.imdgService.refreshImdgSubject.subscribe((val)=>{
      try{
        this.refreshCostTable(val);
      } catch(error){
        console.log("error")
      } 
    });
    //#NIIT CR4 >>>>END

    this.imdgService.imdgClassSubject.subscribe((val)=>{     
      try{
        this.getValueimDgClassSetup(val);
      } catch(error){
        console.log("error in imdg class subject"); 
      }
    });

  }


  ediTable = true;
  public checkedSelectedRows: any = [];

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
      value: 'S',
    }, {
      label: 'BKG/BL',
      value: 'B'
    },
    {
      label: 'Lump Sum',
      value: 'L'
    }
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

  //rate options for location user
  private rateStatusOptionsforLocationUser: any = [
    {
      label: 'Open',
      value: 'O',
    }, {
      label: 'Pending',
      value: 'P',
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
        label: '',
        value: "EMPTY"
    },

    {
      label: 'Normal',
      value: "N",
    },
    {
      label: 'Reefer DG',
      value: 'RDG',
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
    },
    {
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


  tempRowData: any[] = [];
  addFormFlag: boolean;
  addRow(e) {
    if(this.rowData != undefined && this.rowData.length == 0){
      this.tempRowData = [];
    }
    let a: any = {};
    this.columnDefs.forEach(element => {
      a[element.columnId] = undefined;
    });
    this.addFormFlag = true
    a['selectedFlag'] = true;
    a['editRow'] = true;
    a['operation'] = 'addCostRate';
    a.eqType = ["**"];
    a.eqCatq = "B";
    a.mtOrLaden = "LADEN";
    a.rateBasis = 'S';
    if(this.userType != "Global"){
      a.rateStatus = "O";
    } else{
      a.rateStatus = "A";
    }    
    a.impOrExp = "ALL";
    a.splHandling = "N"; //to show normal in special handling by default
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

  convertOOgValue(obj, val) {
    return;
  }

  editSelectedRow() {
    this.rowData.forEach(element => {
      this.addFormFlag = true;
      if (element.selectedFlag == true) {
        element = this._mappingData.dataMappingMethod(element);
        if (!element.operation) {
          element.operation = 'editCostRate';
        }
        let seletedRow = Object.keys(element);
        this.columnDefs.forEach(ele => {
          if (!seletedRow.includes(ele.columnId)) {
            element[ele.columnId] = undefined;
          }
        });
        if(element.rateBasis == 'B' || element.rateBasis == 'L'){
          element.splHandling = undefined;
        }
        if (element['eqType']) {
          element['eqType'] = element['eqType'].split(',');
        }
        if (!element['service']) {
          element['service'] = "***"
        }
        if (!element['vesselCodes']) {
          element['vesselCodes'] = "***"
        }
        element['currency'] = element.currency;
        element.editRow = true;
      }
    });   
  }

  copyRow(e) {
    if (this.checkedSelectedRows.length == 1) {
      let copiedRow = Object.assign({}, this.checkedSelectedRows[0]);
      this.addFormFlag = true;      
      copiedRow = this._mappingData.dataMappingMethod(copiedRow);
      let seletedRow = Object.keys(copiedRow);
      this.columnDefs.forEach(ele => {
        if (!seletedRow.includes(ele.columnId)) {
          copiedRow[ele.columnId] = undefined;
        }
      });
      copiedRow.editRow = true;
      if (copiedRow['eqType']) {
        copiedRow['eqType'] = copiedRow['eqType'].split(',');        
      }
      if (!copiedRow['service']) {
        copiedRow['service'] = "***"
      }
      if (!copiedRow['vesselCodes']) {
        copiedRow['vesselCodes'] = "***"
      }
      copiedRow.operation = 'addCostRate';
      this.checkedSelectedRows[0].selectedFlag = false;
      copiedRow.selectedFlag = true;
      this.checkedSelectedRows.push(copiedRow);
      this.rowData.push(copiedRow);
    }
  }


  saveRows() {   
    let filterdArr = [];
    this.checkedSelectedRows.forEach(element => {
      if (element.editRow == true) {
        filterdArr.push(element);
      }
    });
    
    this.save.emit(filterdArr);
   
  }

  settingButton(costRateData){
    this.checkedSelectedRows = costRateData;    
  }


  deleteTextMsg: any;
  modal:any;
  deleteCostRecord() {
    this.deleteTextMsg = "Do you want to delete?";  
    $('#delete-warnings-modal').addClass('uk-open').show();
  }

  closeWarning() {   
   $('#delete-warnings-modal').addClass('uk-open').hide();
  }

  deleteSelectedRows(): void {
    $('#delete-warnings-modal').addClass('uk-open').hide();  
    this.checkedSelectedRows.forEach(element => {
      element.operation = 'deleteCostRate'
    });

    this.deleteRow.emit(this.checkedSelectedRows);
    this.tempRowData = this.tempRowData.filter(word => word.deletedFlag != true );
    this.checkedSelectedRows = [];
  }


  approveSelectedRows() {
    this.checkedSelectedRows.forEach(element => {
      element.operation = 'apporveCostRate';
      let x = jQuery("#resp-table-body").find(".form-control").hasClass('ng-valid');
      if (x == true) {
        UIkit.modal('#reject-warnings-modal, #reject-warnings-modal ').show();
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
      element.operation = 'rejectCostRate';

    let x = jQuery("#resp-table-body").find(".form-control").hasClass('ng-valid');
    if (x == true) {
      UIkit.modal('#reject-warnings-modal, #reject-warnings-modal ').show();
      return false;

    }
    else {
      this.rejectRow.emit(this.checkedSelectedRows);
      this.checkedSelectedRows = [];
    }
     
    });
   
  }


  //enabling and disabling of fields with value change
  RateBasisChanged(e,index) {
    let rateBasis = e;    
    let indexCount=0;
    this.rowData.forEach(element => {
      if(index==indexCount){
        if (rateBasis == "S") {
          element.lumpSum = null;
          element.splHandling = 'N';
        }
        if (rateBasis == "B" || rateBasis == "L") {
          element.rate20 = null;
          element.rate40 = null;
          element.rate45 = null;
          element.splHandling = null;
          element.mtOrLaden = null;
          element.eqCatq = null;
          element.upto = null;
          element.eqType = null;
          element.service= "***";
          element.vesselCodes= "***";
        }
        // if (rateBasis == "L") {
        //   element.rate20 = null;
        //   element.rate40 = null;
        //   element.rate45 = null;
        //   element.splHandling = 'EMPTY';
        // }
       
      }
      indexCount=indexCount+1;
    })
  }


  clearField(e) {
    if (e == '0G') {
      this.rowData[this.cuurentLookupIndex]['portCode'] = undefined;
      this.rowData[this.cuurentLookupIndex]['imdgCode'] = undefined;
    } else if (e == 'N') {
      this.rowData[this.cuurentLookupIndex]['portCode'] = undefined;
      this.rowData[this.cuurentLookupIndex]['imdgCode'] = undefined;
      this.rowData[this.cuurentLookupIndex]['oogCode'] = undefined;
    } else if (e == 'RDG') {
      this.rowData[this.cuurentLookupIndex]['portCode'] = undefined;
      this.rowData[this.cuurentLookupIndex]['imdgCode'] = undefined;
      this.rowData[this.cuurentLookupIndex]['oogCode'] = undefined;
    } else if (e == 'ODG') {
      this.rowData[this.cuurentLookupIndex]['portCode'] = undefined;
      this.rowData[this.cuurentLookupIndex]['imdgCode'] = undefined;
      this.rowData[this.cuurentLookupIndex]['oogCode'] = undefined;
    } else if (e == 'RF') {
      this.rowData[this.cuurentLookupIndex]['portCode'] = undefined;
      this.rowData[this.cuurentLookupIndex]['imdgCode'] = undefined;
      this.rowData[this.cuurentLookupIndex]['oogCode'] = undefined;
    } else if (e == 'D1') {
      this.rowData[this.cuurentLookupIndex]['portCode'] = undefined;
      this.rowData[this.cuurentLookupIndex]['imdgCode'] = undefined;
      this.rowData[this.cuurentLookupIndex]['oogCode'] = undefined;
    } else if (e == 'DA') {
      this.rowData[this.cuurentLookupIndex]['portCode'] = undefined;
      this.rowData[this.cuurentLookupIndex]['imdgCode'] = undefined;
      this.rowData[this.cuurentLookupIndex]['oogCode'] = undefined;
    } else if (e == 'OD') {
      this.rowData[this.cuurentLookupIndex]['portCode'] = undefined;
      this.rowData[this.cuurentLookupIndex]['imdgCode'] = undefined;
      this.rowData[this.cuurentLookupIndex]['oogCode'] = undefined;
    } else if (e == 'BBK') {
      this.rowData[this.cuurentLookupIndex]['portCode'] = undefined;
      this.rowData[this.cuurentLookupIndex]['imdgCode'] = undefined;
      this.rowData[this.cuurentLookupIndex]['oogCode'] = undefined;
    }
  }

  cuurentLookupIndex: number;

  oogSetupList:any=[];
  filteredOogSetupList:any=[];
  //#NIIT CR4 >>>>BEGIN
  getValueOogSetup(e) {     
    this.oogSetupList = e;    
    this.oogSetupList.forEach((element)=>{
      if(element.action == "N" || element.action == "U"){
        this.filteredOogSetupList.push(element);
      }
    })
    this.saveOogData.emit(this.filteredOogSetupList);
    this.oogSetupList = [];
    this.filteredOogSetupList = [];
  }
  

  imdgClassSetupList:any=[];
  filteredImdgClassSetupList:any=[];
  getValueimDgClassSetup(e) {
    this.imdgClassSetupList = e;    
    this.imdgClassSetupList.forEach((element)=>{
      if(element.action == "N" || element.action == "U"){
        this.filteredImdgClassSetupList.push(element);
      }
    });
    var imdgClassObject = {"filteredImdgClassSetupList": this.filteredImdgClassSetupList , "type":"imdg"}
    this.savePortImdgClassData.emit(imdgClassObject);
    this.imdgClassSetupList = [];
    this.filteredImdgClassSetupList = [];
  }

  
  portClassSetupList:any=[];
  filteredPortClassSetupList:any=[];
  getValuePortClassSetup(e) {
    this.portClassSetupList = e;    
    this.portClassSetupList.forEach((element)=>{
      if(element.action == "N" || element.action == "U"){
        if(element.errorMsgCd !=undefined){          
          delete element['errorMsgCd'];
          delete element['errorMessage'];
          delete element['displayError'];
        }
        this.filteredPortClassSetupList.push(element);
      }
    });
    var portClassObject = {"filteredPortClassSetupList": this.filteredPortClassSetupList , "type":"port"}
    this.savePortImdgClassData.emit(portClassObject);
    this.portClassSetupList = [];
    this.filteredPortClassSetupList = [];        
  }

  //#NIIT CR4 >>>>END

  checkComponent : string = "testTable"; //to checkComponent
  //for port class lookup
  showPortClassModal(e, currRow, i) {
    if (e.target.disabled == true) {
      e.preventDefault();
    } else {      
      this.getPortImdgData("searchPortSetup","PORT");//#NIIT CR4
    }
  }

  openNewDialog(portClassList,checkComponent,selectedContarctRow) {
  this.modalService.openDialog(this.viewRef, {
    title: 'Port Class',
    data: { portClassList : portClassList, checkComponent : checkComponent,selectedContarctRow:selectedContarctRow},
    childComponent: PortClassSetupComponent
    });
  }


  //for imdg lookup
  showimDgModal(e, currRow, i) {
    if (e.target.disabled == true) {
      e.preventDefault();
    } else {      
      this.getPortImdgData("searchImdgSetup","IMDG");//#NIIT CR4          
    }
  }

  openImdgNewDialog(imdgList,checkComponent, selectedContarctRow) {
  this.modalService.openDialog(this.viewRef, {
    title: 'IMDG Class',
    data: { imdgList : imdgList, checkComponent : checkComponent , selectedContarctRow:selectedContarctRow},
    childComponent: ImdgClassSetupComponent
    });
  }

  //oogList = [];
  //for oog poopup
  showOOGModal(e) {
    if (e.target.disabled == true) {
      e.preventDefault();
    } else {
      //#NIIT CR4 >>>>BEGIN
      //method to check if ooglist exists for the setup
      this.getOogData();      
    }
  }

  //get the ooglist for the setup
  //#NIIT CR4 >>>>BEGIN
  getOogData(){    
    this._spinner.showSpinner();
    var backendData = this._contractSearchService.getOOgList({"terminalDepotCode": this.selectedContarctRow.fromTerminal,"action":"searchOOGSetup"});
    backendData.subscribe(
      (data) => {
        if(data == "userSessionExpired"){
          this._sessionTimeOutService.checkSessionTimeout(data);
        }
        else if (data.hasOwnProperty("errorCode")) {
         
        } else { 
          var index = 1;                    
          this.shService.oogList = [...data['oogSetUpList']];
          this.shService.oogList.forEach((element)=>{
              element['seqNo'] = index;
              index++;
            });
          this.shService.oogList.sort((a,b) => (a['oogSetupCode'] < b['oogSetupCode']) ? -1 : ((b['oogSetupCode'] < a['oogSetupCode']) ? 1 : 0)); //to sort the oog class list in sorted order                      
          this.openOogNewDialog(this.shService.oogList,this.checkComponent,this.selectedContarctRow);
          this._spinner.hideSpinner();
        }
      });
  }
  //#NIIT CR4 >>>>END

  //get the portlist or imdg list data depending upon request for the setup
  //#NIIT CR4 >>>>BEGIN
  getPortImdgData(action,portImdgType){    
    this._spinner.showSpinner();
    var backendData = this._contractSearchService.getPortImdgList({"terminalDepotCode": this.selectedContarctRow.fromTerminal,"action":action,"portImdgType":portImdgType});
    backendData.subscribe(
      (data) => {
        if(data == "userSessionExpired"){
          this._sessionTimeOutService.checkSessionTimeout(data);
        }
        else if (data.hasOwnProperty("errorCode")) {
         
        } else {
          //if action is searchPortSetup 
          if(data['portList'] != undefined){
            var index = 1;
            this.portService.portClassList = [...data['portList']];
            this.portService.portClassList.forEach((element)=>{
              element['seqNo'] = index;
              index++;
            })
            this.portService.portClassList.sort((a,b) => (a['portImdgCode'] < b['portImdgCode']) ? -1 : ((b['portImdgCode'] < a['portImdgCode']) ? 1 : 0)); //to sort the port class list in sorted order       
            this.openNewDialog(this.portService.portClassList,this.checkComponent,this.selectedContarctRow);
          }                 
          
          //if action is searchImdgSetup
          if(data['imdgList'] != undefined){
            var index = 1;
            this.imdgService.imdgList = [...data['imdgList']];
            this.imdgService.imdgList.forEach((element)=>{
              element['seqNo'] = index;
              index++;
            })
            this.imdgService.imdgList.sort((a,b) => (a['portImdgCode'] < b['portImdgCode']) ? -1 : ((b['portImdgCode'] < a['portImdgCode']) ? 1 : 0)); //to sort the imdg class list in sorted order       
            this.openImdgNewDialog(this.imdgService.imdgList,this.checkComponent,this.selectedContarctRow); 
           } 
          this._spinner.hideSpinner();
        }
      });
  }

  refreshCostTable(e){    
    this.refreshTableData.emit(e);
  }
  //#NIIT CR4 >>>>END

  openOogNewDialog(oogList,checkComponent,selectedContarctRow) {
  this.modalService.openDialog(this.viewRef, {
    title: 'OOG Dimention Setup',
    data: { oogList : oogList, checkComponent : checkComponent,selectedContarctRow:selectedContarctRow},
    childComponent: OogDimentionSetupComponent
    });
  }

  showExemptedCustomer: boolean;
  currentComponent:string;//#NIIT CR6 >>>>
  currentClickedRow = {};
  showExempted(e, currRow, i) {
    //to prevent dialog from opening when field is disabled
    if(e.target.disabled == true){
       e.preventDefault();
    }else{
       this.currentClickedRow = currRow;
       this.currentComponent = 'testTable';//#NIIT CR6 >>>>
       this.showExemptedCustomer = true;
    }   
  }

  hideExemptedCustomer(e) {
    this.showExemptedCustomer = false;
    this.currentClickedRow['splCostByBlOrBooking'] = e.tempString; //#NIIT CR6 >>>>
  }

  addEditBillRateComponentFlag: boolean = true;
  //Add bill rate component row
  showAddBillRate(e) {
    this.addEditBillRateComponentFlag = false;


    this._addeEditBillRate
      .addEditCostRateObj = {
        ijsRateVO: {
          mtOrLaden: "LADEN", //to get LADEN on clicking Add Cost Rate
          rateBasis: "S",
          eqCatq: "B",
          rateStatus: "O",
          service: '***',
          vesselCodes: "***",
          impOrExp: "ALL",
          splHandling: "N", //to show normal in special handling in add cost rate popup
          eqType: "**", //to get Equipment Type as ** on clicking Add Cost Rate mapping wrt backend data
          mot: this.selectedContarctRow['transMode'],
          endDate: this.selectedContarctRow.endDate,
          startDate: this.selectedContarctRow.startDate,
          currency: this.selectedContarctRow.currency
        },
        action: "addCostRate",
        routingNumber: this.selectedContarctRow.routingId

      };
      this._addeEditBillRate.setRateStatus(); //to set rateStatus on the basis of user type
      this._addeEditBillRate.addEditLabel="Add";
//    UIkit.modal('#add-edit-bill-container').show();
    jQuery("html, body").animate({ scrollTop: 0 }, 900);
  }


  showEditBillRate(e) {

    this.checkedSelectedRows[0];
    this._addeEditBillRate.disableClose = false; //#NIIT CR4 >>>>BEGIN
    this.addEditBillRateComponentFlag = false;
    this._addeEditBillRate.addEditCostRateObj.routingNumber = this.selectedContarctRow.routingId
    this._addeEditBillRate.addEditCostRateObj.action = "editCostRate";
    let  checkedRowDataTemp =  Object.assign({},this.checkedSelectedRows[0]);    
    this._addeEditBillRate.addEditCostRateObj.ijsRateVO = this._mappingData.dataMappingMethod(checkedRowDataTemp);


    this._addeEditBillRate.addEditCostRateObj.ijsRateVO.mot = this.selectedContarctRow['transMode'];
    
    if (this._addeEditBillRate.addEditCostRateObj.ijsRateVO['eqType']) {
      this._addeEditBillRate.addEditCostRateObj.ijsRateVO['eqType'] = this._addeEditBillRate.addEditCostRateObj.ijsRateVO['eqType'].split(",")
    }
    this._addeEditBillRate.addEditLabel = "Edit";
   
   // UIkit.modal('#add-edit-bill-container').show();
    jQuery("html, body").animate({ scrollTop: 0 }, 900);
  }

 successtextFlag:boolean;
 successTextMsg: string;
 //getting the values of flag and message from child component
 gettingEditCostDetails(e){  
    this.successtextFlag =  e.costOperationFlag; 
    this.successTextMsg = e.costOperationMessage;   
    setTimeout(()=>{
      //UIkit.modal('#add-edit-cost').show();
      $('#add-edit-cost').addClass('uk-open').show(); //#NIIT CR4 >>>>
    },1000);    
 }

  hideAddNewCostRate(e) {
//  alert(e + "test");
    this.addEditBillRateComponentFlag = true;
    this.costBillTable.emit(e);
    this.checkedSelectedRows = [];
  }

  //closing the popup on clicking close and showing updated data in the table
  closeAddEditCostRate(e){
    //UIkit.modal('#add-edit-cost').hide();
    $('#add-edit-cost').addClass('uk-open').hide();//#NIIT CR4 >>>>
    this.hideAddNewCostRate(e);
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

  
  specialHandlingChanged(e,index){
   let splHandling = e;
   let indexCount=0;
    this.rowData.forEach(element => {
      if(index==indexCount){
       
      if (splHandling == "N" || splHandling == "RF" || splHandling == "DA" || splHandling == "OD" || splHandling == "BBK" || splHandling == "ODG") {
        element.portCode = null;        
        element.imdgCode = null;
        element.oogCode = null;        
      }
      if (splHandling == "RDG" || splHandling == "D1") {
        element.oogCode = null;         
      }
      if (splHandling == "0G") {
        element.portCode = null;
        element.imdgCode = null;
      }      
    }
    indexCount=indexCount+1;
    });
  }  

  //#NIIT CR4 >>>>BEGIN
  portSavedSuccessfully(){   
    this._portClassSetup.portSaved();
  }

  imdgSavedSuccessfully(){
    this._imDgClassSetup.imdgSaved();
  }

  oogSavedSuccessfully(){
    this._oogDimentionSetup.oogSaved();
  }
  //#NIIT CR4 >>>>END
}
