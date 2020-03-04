import { Component, OnInit, Output, Input, EventEmitter } from '@angular/core';
declare var UIkit: any;
declare var jQuery: any;
import { Injectable } from '@angular/core';
import { Http, Response, Headers, URLSearchParams, RequestOptions } from '@angular/http';
import { Observable } from 'rxjs/Observable';
import 'rxjs/add/operator/map';
import 'rxjs/add/operator/catch';
import { RclappUrlService } from "../../../common-services/rclapp-url.service"
import { SpinnerServiceService } from "../../../common-services/spinner-service.service";
import { ServerErrorcodeService } from "../../../common-services/server-errorcode.service";
import { SortSearchTableService } from "../sort-search-table.service";
import * as $ from 'jquery';
import { SessionTimeOutService } from "app/common-services/session-time-out.service";

@Component({
  selector: 'app-exempted-customer',
  templateUrl: './exempted-customer.component.html',
  styleUrls: ['./exempted-customer.component.scss']
})
export class ExemptedCustomerComponent implements OnInit {

  @Input() private selectedContarctRow: string;
  @Input() private currentClickedRow: string;  
  @Input() private contarctRow: any; //#NIIT CR4 
  //#NIIT CR6 >>>>BEGIN
  @Input() private checkComponent: string; 
  invokingComponent:string;
  //#NIIT CR6 >>>>END
  private routingId: string;
  private contractId: string;
  private tableDataExCust: any = [];
  private addOrEditAction: string
  private errorText: string
  private errorTextFlag: boolean = false;
  private selectAllCheckBox: any;

  constructor(private _http: Http, private _rclappUrlService: RclappUrlService, public spinner: SpinnerServiceService, private _serverErrorCode: ServerErrorcodeService, private _sort: SortSearchTableService,private _sessionTimeOutService:SessionTimeOutService) { }

  selectConfig: any = {
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

  statusOptions = [
    {
      label: 'Active',
      value: 'A'
    },
    {
      label: 'Suspended',
      value: 'S'
    }
  ];

  custTypeOptions = [
    {
      label: 'Shipper',
      value: 'Shipper'
    },
    {
      label: 'Consignee',
      value: 'Consignee'
    }
  ];




  customerLookupData: any = [{ "label": "Customer Lookup", "value": "exemptCustomerLookup", "dropDownData": [{ "label": "Customer Code", "value": "CUCUST" }, { "label": "Name", "value": "CUNAME" }, { "label": "Finance Interface Customer Code", "value": "CUBILL" }, { "label": "City", "value": "CUCITY" }, { "label": "Country", "value": "CUCOUN" }, { "label": "Status", "value": "STATUS" }] }];

  custId: string;
  custName: string;
  custType: string;
  status: string
  remark: string;
  checkedSelectedRows: any = [];

  ngOnInit() {       

    //#NIIT CR6 >>>>BEGIN
    //checking the invoking component 
    if(this.checkComponent == 'testTable'){
      this.invokingComponent = this.checkComponent;
    }
    else if(this.checkComponent == 'addEditCostRate'){
      this.invokingComponent = this.checkComponent;
    }    
    //#NIIT CR6 >>>>END

    this.getBackEndDataExCust();
    this.selectAllCheckBox = jQuery('#selectAllCheck')[0]
  }
  @Output() private closeExemptedCustomer = new EventEmitter();

  searchExemptedCustumer() {
    this.getBackEndDataExCust();
  }


  hideExemptedCustomer(e) {
    let tempString = "";
    this.tableDataExCust.forEach(element => {
      tempString = tempString + element.custId + ','
    });
    //#NIIT CR4
    this.closeExemptedCustomer.emit({"tempString": tempString, "exemptedDataFlag":this.exemptedDataFlag});
  }

  addOrEdit: string;
  addExemptedCustumer(e) {
    this.addOrEdit = 'Add';
    this.addOrEditAction = 'exemptAdd';
    this.custId = undefined;
    this.custName = undefined;
    //#NIIT CR4 >>>>BEGIN
    this.custType = this.custTypeOptions[0]['value']; //to have one value selected by default
    this.status = "A"; //to show default status as active
    this.remark = undefined;
    this.showExemptedCustumer(e);
  }

  //#NIIT CR4
  getCustomerName(e){
    this.custName = e;
  }

  editExemptedCustumer(e) {
    this.addOrEdit = 'Edit';
    this.addOrEditAction = 'exemptEdit';
    this.custId = this.checkedSelectedRows[0].custId;
    this.custName = this.checkedSelectedRows[0].custName;
    this.custTypeOptions.forEach(element => {
      if (this.checkedSelectedRows[0].custType == element.label) {
        this.custType = element.value;
      }
    });
    this.statusOptions.forEach(element => {
      if (this.checkedSelectedRows[0].status == element.label) {
        this.status = element.value;
      }
    });
    this.remark = this.checkedSelectedRows[0].remark;
    this.showExemptedCustumer(e);
  }
  showExemptedCustumer(e) {
    // UIkit.modal('#exemted-customer-modal-center').show();
    $('#exemted-customer-modal-center').addClass('uk-open').show(); //#NIIT CR4 >>>>END
  }


  private exCustSelectTableRowCheckBox(e, rowObj) {
    if (e.target.checked) {
      this.checkedSelectedRows.push(rowObj);      
    } else {
      this.checkedSelectedRows = this.deleteObjByCustId(this.checkedSelectedRows, 'custId', rowObj.custId);
    }
  }

  exCustSelectTableRowCheckBoxAll(e) {
    this.selectAllCheckBox = e.target;
    if (e.target.checked) {
      this.tableDataExCust.forEach(element => {
        element['checked'] = true;
      });
      this.checkedSelectedRows = this.tableDataExCust;
    } else {
      this.checkedSelectedRows.forEach(element => {
        element['checked'] = false;
      });
      this.checkedSelectedRows = [];
    }
  }



  //delete element from array
  deleteObjByCustId(arr, attr, value) {
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


  exemptedDataFlag:boolean;//#NIIT CR4
  getBackEndDataExCust() {
    this.spinner.showSpinner();
    let backendData = this.searchBackendDataExCust();
    backendData.subscribe(
      (data) => {
        if(data == "userSessionExpired"){
          this._sessionTimeOutService.checkSessionTimeout(data);
        }
        else if (data['errorCode'] == 'IJS_EX_10001') {          
          this.errorText = this._serverErrorCode.checkError(data["errorCode"]);
          this.tableDataExCust = []; 
         } else {          
          this.tableDataExCust = data;  
          this.exemptedDataFlag = true;//#NIIT CR4
          this.errorTextFlag = false;
        }
        this.checkedSelectedRows = [];
        this.selectAllCheckBox.checked = false;
        this.spinner.hideSpinner()
      },
      (err) => {
        setTimeout(() =>
          this.spinner.hideSpinner()
          , 0);
      });
  }

  searchBackendDataExCust() {

    let searchDataObj = {
        "searchParam": {          
          'contractId': this.invokingComponent == 'testTable' ? this.selectedContarctRow['contractId'] : this.contarctRow['contractId'],//#NIIT CR6 >>>>
          'routingNumber': this.invokingComponent == 'testTable' ? this.selectedContarctRow['routingId'] : this.contarctRow['routingId'],//#NIIT CR6 >>>>
          'costRateSequenceNum': this.currentClickedRow['costRateSequenceNum'],
          'costRateDetailSeqNum': this.currentClickedRow['costRateDetailSeqNum'],
          'detailSeqNumbers': this.currentClickedRow['detailSeqNumbers']
        },
        'action': 'exemptSearch'
    }
    let seachdata = JSON.stringify(searchDataObj);
    let searchParams = new URLSearchParams();
    searchParams.set("data", seachdata);

    return this._http.get(this._rclappUrlService.url + '/IJSWebApp/exemptCustSearch.do', { search: searchParams })
      .map(this.extractData)
      .catch(this.handleErrorObservable);
  }

  failureTextMsg="";
  saveData(action) {
    this.spinner.showSpinner();
    let backendData = this.sendDataToBackendExCust(action);
    backendData.subscribe(
      (data) => {
        if(data == "userSessionExpired"){
          this._sessionTimeOutService.checkSessionTimeout(data);
        }
        else if (data['errorCode'].includes('MSG')) {
           this.spinner.hideSpinner();
          //UIkit.modal('#exemted-customer-modal-center').hide();
          $('#exemted-customer-modal-center').addClass('uk-open').hide(); //#NIIT CR4 >>>>END
          this.getBackEndDataExCust();
         
        }else if(data['errorCode'].includes('IJS_RATE_EX_10016')){
          this.spinner.hideSpinner();
          this.failureTextMsg=this._serverErrorCode.checkError(data['errorCode']);
          UIkit.modal('#show-failure-modal').show();
        }
      },
      (err) => {
        this.spinner.hideSpinner();
      });
  }
closeError(){
   $('html').removeAttr('class');
   $('#show-failure-modal').remove();
  //UIkit.modal('#show-failure-modal').hide();
}
  sendDataToBackendExCust(action) {

    let searchDataObj = {
        "custSaveList": {
          'contractId': this.invokingComponent == 'testTable' ? this.selectedContarctRow['contractId'] : this.contarctRow['contractId'],//#NIIT CR6 >>>>
          'routingNumber': this.invokingComponent == 'testTable' ? this.selectedContarctRow['routingId'] : this.contarctRow['routingId'],//#NIIT CR6 >>>>
          'costRateSequenceNum': this.currentClickedRow['costRateSequenceNum'],
          'custId': this.custId,
          'custType': this.custType,
          'status': this.status,
          'remark': this.remark,
          'detailSeqNumbers': this.currentClickedRow['detailSeqNumbers']
        },
        'action': action
    }

    let seachdata = JSON.stringify(searchDataObj);
    let searchParams = new URLSearchParams();
    searchParams.set("data", seachdata);

    return this._http.get(this._rclappUrlService.url + '/IJSWebApp/exemptCustSearch.do', { search: searchParams })
      .map(this.extractData)
      .catch(this.handleErrorObservable);
  }


  deleteExemptedCustumer(e) {
    this.spinner.showSpinner();
    let backendData = this.deleteDataToBackendExCust();
    backendData.subscribe(
      (data) => {
        if(data == "userSessionExpired"){
          this._sessionTimeOutService.checkSessionTimeout(data);
        }
        else if (data['errorCode'].includes('MSG')) {
          //UIkit.modal('#exemted-customer-modal-center').hide();
          this.spinner.hideSpinner()
          this.getBackEndDataExCust();
         
        }
      },
      (err) => {
        this.spinner.hideSpinner()
      });
  }

  deleteDataToBackendExCust() {
    let searchDataObj = {
      "custDelList": [],
      'action': 'exemptDelete'
    }
    this.checkedSelectedRows.forEach(element => {
      
      let temp = {
          'contractId': this.invokingComponent == 'testTable' ? this.selectedContarctRow['contractId'] : this.contarctRow['contractId'],//#NIIT CR6 >>>>
          'routingNumber': this.invokingComponent == 'testTable' ? this.selectedContarctRow['routingId'] : this.contarctRow['routingId'],//#NIIT CR6 >>>>
          'costRateSequenceNum': this.currentClickedRow['costRateSequenceNum'],
          'custId': element.custId,
          'custType':element.custType,
          'detailSeqNumbers': this.currentClickedRow['detailSeqNumbers']
      }
      searchDataObj.custDelList.push(temp);
    });

    let seachdata = JSON.stringify(searchDataObj);
    let searchParams = new URLSearchParams();
    searchParams.set("data", seachdata);
    
    return this._http.get(this._rclappUrlService.url + '/IJSWebApp/exemptCustSearch.do', { search: searchParams })
      .map(this.extractData)
      .catch(this.handleErrorObservable);
  }

  private extractData(res: Response) {
    let body = res.json();
    return body;
  }
  private handleErrorObservable(error: Response | any) {
    return Observable.throw(error);
  }
  
  onChangeOrderBy (orderby) {
      this.tableDataExCust = this._sort.sortTableData(this.tableDataExCust, 'custId', orderby);
  }


}
