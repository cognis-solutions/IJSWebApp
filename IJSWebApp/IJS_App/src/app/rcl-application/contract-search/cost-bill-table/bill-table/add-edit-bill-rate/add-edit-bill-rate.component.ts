import { Component, OnInit, ViewChild, Input, Output, EventEmitter,ChangeDetectorRef, ChangeDetectionStrategy } from '@angular/core';
import { ContractSearchService } from "../../../contract-search.service";
declare var jQuery: any;
declare var UIkit: any;
import { SpinnerServiceService } from "../../../../../common-services/spinner-service.service";
import { ServerErrorcodeService } from "../../../../../common-services/server-errorcode.service";
import { ContractSearchComponent } from "../../../contract-search.component";
import { LookUpdataServiceService } from '../../../../../common-services/look-updata-service.service';
import { SortSearchTableService } from '../../../sort-search-table.service';
import { BillMappingData } from './add-edit-bill-mapping';
import * as $ from 'jquery';
import { SessionTimeOutService } from "app/common-services/session-time-out.service";

@Component({
  selector: 'app-add-edit-bill-rate',
  templateUrl: './add-edit-bill-rate.component.html',
  styleUrls: ['./add-edit-bill-rate.component.scss']
})
export class AddEditBillRateComponent implements OnInit {

  @Output() successMsg: EventEmitter<any> = new EventEmitter();
  @Output() eventFlag: EventEmitter<any> = new EventEmitter();
  @Output()  hideAddNewBillRate: EventEmitter<any> = new EventEmitter();
  @Output()  hideAdd: EventEmitter<any> = new EventEmitter();
  @Output()  reFreshBillTable: EventEmitter<any> = new EventEmitter();
  
  @Input() billRowData: any[] = [];
  @Input() public billTableRowData: any;
  addEditBillRateObj = {
    ijsRateVO: {
      mtOrLaden: "Laden",
      rateBasis: "S",
      eqCatq: "B",
      rateStatus: "O",
      //impOrExp: "ALL",
      eqType: "All",
      service: '***'
    }
  };

  //addEditBillRateObj.ijsRateVO.portCode;
  public _billMappingData: BillMappingData;

  addEditLabel: any;



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
  addEditMultiSelectConfig: any = {
    highlight: true,
    create: false,
    persist: true,
    plugins: ['dropdown_direction', 'remove_button'],
    dropdownDirection: 'down',
    labelField: 'label',
    valueField: 'value',
    searchField: ['label'],
    maxItems: 6
  };
  successTextMsg: string;
  successtextFlag: boolean;
  @Output() public hideAddNewCostRate = new EventEmitter();
  @ViewChild('BillServiceLookup') _billServiceLookup: any;
  @ViewChild('BillServiceLookup') _oogDimentionSetup1: any;
  @Input() private eqTypeList: any;
  @Input() private termCodesList: any;
  @Input()  userTypeforBill: any;

  currencyLookUpData: any = [{ "dropDownData": [{ "label": "Currency Code", "value": "CURRENCY_CODE" }, { "label": "Currency Name", "value": "CURRENCY_NAME" }] }];



  constructor(private cd: ChangeDetectorRef,public _joborderService: ContractSearchService, private _spinner: SpinnerServiceService, public _serverErrorCode: ServerErrorcodeService, public _sortTable: SortSearchTableService,private _sessionTimeOutService:SessionTimeOutService) {
    this._billMappingData = new BillMappingData();  
    
  }

 
  showServiceLookUp(e) {
    this._billServiceLookup.showModal();
  }




  getTodaytDate() {
    var today = new Date();
    var dd = today.getDate();
    var mm = today.getMonth() + 1; //January is 0!
    var yyyy = today.getFullYear();
    var dd1;
    var mm1;
    var today1;

    if (dd < 10) {
      dd1 = '0' + dd;     
    } else {
      dd1 = '' + dd;
    }
    if (mm < 10) {
      mm1 = '0' + mm;
    } else {
      mm1 = '' + mm;
    }
    today1 = dd1 + '/' + mm1 + '/' + yyyy;
    return today1;
  }

  closeWarning() {
    UIkit.modal('#date-warning-modal').hide();
  }

  process(date) {
    var parts = date.split("/");
    var date: any = new Date(parts[1] + "/" + parts[0] + "/" + parts[2]);
    return date.getTime();
  }

  expiryDate: Date;
  today: Date;
  action: String;
  warningTextMsg: String;
  
  billRateDataObject = {   
      operationMessage: "",
      operationFlag: true,        
  };

  sendDataToBackEnd() {
    this.successTextMsg = "";
    this.expiryDate = this.addEditBillRateObj.ijsRateVO['endDate'];
    this.today = this.getTodaytDate();
    this.action = this.addEditBillRateObj['action'];
    if (this.action == "editBillingRate" && this.process(this.today) > this.process(this.expiryDate)) {
      this.warningTextMsg = "Expiry Date Cannot be less than Current Date";
      //this.successMsg.emit(this.warningTextMsg);      
      //UIkit.modal('#date-warning-modal').show();
      $('#date-warning-modal').addClass('uk-open').show();
      this.cd.markForCheck(); // marks path
    }
    else {
      this._spinner.showSpinner();
      if (this.addEditBillRateObj.ijsRateVO.eqType) {
        this.addEditBillRateObj.ijsRateVO.eqType = this.addEditBillRateObj.ijsRateVO.eqType.toString();
      }
      if (this.addEditBillRateObj.ijsRateVO['term']) {
        this.addEditBillRateObj.ijsRateVO['term'] = this.addEditBillRateObj.ijsRateVO['term'].toString();
      }     
      var backendData = this._joborderService.costRateGetData(this.addEditBillRateObj);
      backendData.subscribe(
        (data) => { 
        
        if(data == "userSessionExpired"){
          this._sessionTimeOutService.checkSessionTimeout(data);
        }      
        else if (data.hasOwnProperty("errorCode")) {
          if (data["errorCode"].includes("IJS_MSG_")) {
            this.successTextMsg = this._serverErrorCode.checkError(data["errorCode"]);            
            this.successtextFlag = true;
            this.billRateDataObject.operationMessage = this.successTextMsg;
            this.billRateDataObject.operationFlag = this.successtextFlag;
            this.successMsg.emit(this.billRateDataObject);           
            this.cd.markForCheck(); // marks path
          } else {
            this.successTextMsg = this._serverErrorCode.checkError(data["errorCode"]);
            this.successtextFlag = false; 
            this.billRateDataObject.operationMessage = this.successTextMsg;
            this.billRateDataObject.operationFlag = this.successtextFlag;
            this.successMsg.emit(this.billRateDataObject);   
            this.cd.markForCheck(); // marks path   
          }
        }       
        this.cd.markForCheck();      
        this._spinner.hideSpinner(); 
      }
    )
  }
  }
  
  serviceRowSelected(e) {
    this.addEditBillRateObj.ijsRateVO['service'] = e;
  }
  closeAddEditBillRate(e) {    
   this.reFreshBillTable.emit();  
  }

  closeModal() {   
    $('#add-edit-bill-success').addClass('uk-open').hide();      
  }

  closeAddEditBillTable (e) {
    this.hideAddNewBillRate.emit(e);  
  }

  showOOGModal(e) {    
    if(e.target.disabled == true) {
      e.preventDefault();
    } else {
      this._oogDimentionSetup1.showMOdal();
    }    
  }
  getValueOogSetup(e) {
    for(let i=0; i< e.length; i++) {      
      this.addEditBillRateObj.ijsRateVO['oogSetup'] = "chek_text"
    }    
    this.addEditBillRateObj.ijsRateVO['oogSetupList'] = e;
  }

  ngOnInit() {
    this._billMappingData.eQTypeOptions = this.eqTypeList;
  }
  
  //enabling and disabling of fields with value change
  RateBasisChanged(e) {
    let rateBasis = e;
    if (rateBasis == "S") {
      this.addEditBillRateObj.ijsRateVO['lumpSum'] = null;
    }
    if (rateBasis == "B") {
      this.addEditBillRateObj.ijsRateVO['rate20'] = null;
      this.addEditBillRateObj.ijsRateVO['rate40'] = null;
      this.addEditBillRateObj.ijsRateVO['rate45'] = null;

    }
    if (rateBasis == "L") {
      this.addEditBillRateObj.ijsRateVO['rate20'] = null;
      this.addEditBillRateObj.ijsRateVO['rate40'] = null;
      this.addEditBillRateObj.ijsRateVO['rate45'] = null;
    }

    rateBasis = null;
  }

  setRateStatus(){
     if(this.userTypeforBill != "Global"){
      this.addEditBillRateObj.ijsRateVO['rateStatus'] = "O";
    } else{
      this.addEditBillRateObj.ijsRateVO['rateStatus'] = "A";
    }  
  }
}
