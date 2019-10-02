import { Component, OnInit, ViewChild, Input, Output, EventEmitter } from '@angular/core';
import { NgModel, NG_VALUE_ACCESSOR, NG_VALIDATORS, NG_ASYNC_VALIDATORS } from '@angular/forms';
import { noop } from 'rxjs/util/noop';
import { SpinnerServiceService } from "../../common-services/spinner-service.service";
import { LookUpdataServiceService } from '../../common-services/look-updata-service.service';
import { ServerErrorcodeService } from "../../common-services/server-errorcode.service";
import { SortSearchTableService } from '../../rcl-application//contract-search/sort-search-table.service';

import { Http, Response, Headers, URLSearchParams, RequestOptions } from '@angular/http';
import { Observable } from 'rxjs/Observable';
import 'rxjs/add/operator/map';
import 'rxjs/add/operator/catch';
import { SessionTimeOutService } from "app/common-services/session-time-out.service";

declare var UIkit: any;
declare var jQuery: any;


@Component({
  selector: 'app-route-list-modal',
  templateUrl: './route-list-modal.component.html',
  styleUrls: ['./route-list-modal.component.scss']
})
export class RouteListModalComponent implements OnInit {


  //@Input() private vendorCode: string;
  @Input() private bookingType: any;
  @Input() private inputValueLoc: any;
  @Input() private inputValueTerminal: any;
  @Input() private inputValueLocType: any;
  @Input() private inputSaleDateOrJobOrdDate: any;
  @Input() private vendorCode: any;
  @Input() private transportType: any;
  @Input() private joType: any;
  
  actionparam = "lookupRouteList";
  @Output() selectUpdateRouteList = new EventEmitter();

  private openModal: boolean = false;
  private showlookuptable = false;
  private resultsPerPageArr = [1, 2, 3, 4, 5, 6, 7, 8, 9, 10];
  private resultsPerPage = 5;
  private looUpOrderBy: any;
  private lookupSortIn: any;
  private selectedRow: any = [];
  private lookupErrorCodeShow: boolean = false;
  private lookupErrorCodetext: string;
  private routeListSortData = [{ label: 'Contract #', value: 'contractId' }, { label: 'Priority', value: 'priority', }, { label: 'Currency	', value: 'currency' }, { label: 'Leg Type	', value: 'legType' }];
  private lookUpSortData = [{ label: 'Ascending', value: 'asnd' }, { label: 'Descending', value: 'dsnd' }]

  private routeListTableData = [];
  private pc: number = 1;
  checkComponent:any;
  constructor(private _spinner: SpinnerServiceService, private _lookupData: LookUpdataServiceService, public _serverErrorCode: ServerErrorcodeService, public _sortTable: SortSearchTableService, private _http: Http,private _sessionTimeOutService:SessionTimeOutService) { }

  ngOnInit() {
    //this.openLookUpModal();
  }
  openLookUpModal(callingComponent) {
    jQuery("#route-list-modal-center").detach();
    this.openModal = true;
    this.showlookuptable = true;
    this.lookupErrorCodeShow = false;
    this.looUpOrderBy="asnd";
    this.lookupSortIn = "contractId";
    this.selectedRow = [];
    this.checkComponent = callingComponent;
    this._spinner.showSpinner();
    this.getLocLookUpDataRouteList(this.actionparam, this.bookingType, this.inputValueLoc,this.inputValueTerminal,this.inputValueLocType,this.inputSaleDateOrJobOrdDate,this.vendorCode,this.transportType,this.joType,this.checkComponent);
    setTimeout(function () {
      UIkit.modal('#route-list-modal-center').show();
    }, 100);
  }

  getLocLookUpDataRouteList(actionparam, bookingType, inputValueLoc,inputValueTerminal,inputValueLocType,inputSaleDateOrJobOrdDate,vendorCode,transportType,joType,checkComponent) {
    this._spinner.showSpinner();
    var backendData = this._lookupData.getDataLookupServiceJORouting(actionparam, bookingType, '', inputValueLoc, '',inputValueTerminal,inputValueLocType,inputSaleDateOrJobOrdDate,vendorCode,transportType,joType,checkComponent);
    backendData.subscribe(
      (data) => {
        if(data == "userSessionExpired"){
          UIkit.modal('#route-list-modal-center').hide();
          this._sessionTimeOutService.checkSessionTimeout(data);
        }
        else if (data.hasOwnProperty("errorCode")) {
          this.lookupErrorCodetext = this._serverErrorCode.checkError(data["errorCode"]);
          this.lookupErrorCodeShow = true;
          this.showlookuptable = true;
        }
        else {
          this.showlookuptable = false;
          this.lookupErrorCodetext = undefined;
          this.lookupErrorCodeShow = false;
          this.routeListTableData = data;
        }
        this.pc = 1;
        this._spinner.hideSpinner();
      },
      (err) => {
        this.lookupErrorCodetext = "Something went wrong please try again";
        this.lookupErrorCodeShow = true;
        this._spinner.hideSpinner();
      }
    )
  }


  sortLookUpDataByColumn() {
    this._sortTable.sortTableData(this.routeListTableData, this.lookupSortIn, this.looUpOrderBy);
  }
  sortLookUpDataByOrder() {
    this._sortTable.sortTableData(this.routeListTableData, this.lookupSortIn, this.looUpOrderBy);
  }

  resetRouteListModal(e) {
    this.routeListTableData = [];
    this.selectedRow = [];
    this.openModal = false;
    UIkit.modal('#route-list-modal-center').hide();
  }

  selected:boolean;
  //select Check box    
  private getRowData(e, rowData, i) {
    this.selectedRow = [];
    let selected = rowData.selected;
    this.deselectAll(this.routeListTableData);
    rowData.selected = !selected;
    rowData['checked'] = rowData.selected;
    this.selectedRow.push(rowData);
    // if (e.target.checked == true) {
    //   rowData['selected'] = true;
    //   this.selectedRow.push(rowData);
    // } else {
    //   this.selectedRow = this.deleteObjByRoutingId(this.selectedRow, 'routingId', rowData.routingId);
    // }
  }

  deselectAll(arr: any[]){
    arr.forEach(val =>{
    if(val.selected){
        val.selected = false;
        val.checked = false;
    }})
  }

  //delete element from array
  private deleteObjByRoutingId(arr, attr, value) {
    var i = arr.length;
    while (i--) {
      if (arr[i] && arr[i].hasOwnProperty(attr) && arr[i][attr] === value) {
        arr.splice(i, 1);
      }
    }
    return arr;
  }

  updateRoute() {   
    if (this.selectedRow.length == 1) {
      UIkit.modal('#route-list-modal-center').hide();
      this.selectUpdateRouteList.emit(this.selectedRow[0]);
      this.lookupErrorCodeShow = false;
      this.lookupErrorCodetext = '';
      this.openModal = false;
      this.selectedRow = [];
      //UIkit.modal('#route-list-modal-center').hide();

    } else if (this.selectedRow.length > 1) {
      this.lookupErrorCodeShow = true;
      this.lookupErrorCodetext = 'You can only select one row';
    } else {
      this.lookupErrorCodeShow = true;
      this.lookupErrorCodetext = 'Select a row';
    }
  }

}
