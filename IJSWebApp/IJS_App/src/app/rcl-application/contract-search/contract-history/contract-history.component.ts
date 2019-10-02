import { Component, OnInit, ViewChild, ChangeDetectorRef } from '@angular/core';
import { NgModel, NG_VALUE_ACCESSOR, NG_VALIDATORS, NG_ASYNC_VALIDATORS } from '@angular/forms';
import { NgbModal, NgbModalOptions } from "@ng-bootstrap/ng-bootstrap";
declare var UIkit: any;
import { ContractSearchService } from "../contract-search.service";
import { SpinnerServiceService } from "../../../common-services/spinner-service.service";
import { SortSearchTableService } from '../sort-search-table.service';
import { ServerErrorcodeService } from "../../../common-services/server-errorcode.service";
import { trigger, state, style, animate, transition, keyframes } from '@angular/animations';
import { SessionTimeOutService } from "app/common-services/session-time-out.service";


@Component({
  selector: 'app-contract-history',
  templateUrl: './contract-history.component.html',
  styleUrls: ['./contract-history.component.scss']
})
export class ContractHistoryComponent implements OnInit {
  
  resultsPerPage: number = 10;
  vendorDetaisSortData = [{ label: 'Activity',value: 'activitySeq'}, {label: 'Date',value: 'activityDate',}, {label: 'User', value: 'activityUser'}]
  lookUpSortData = [{ label: 'Ascending',value: 'asnd'}, {label: 'Descending',value: 'dsnd'}]
  
  looUpOrderBy: any;  
  lookupSortIn: any;
  historyData: any = {   
    contract: {},
    action: "history"
  };
  tableData: any = [];
  errorText: string;
  showHistoryFlag:boolean = false;

  constructor(public _joborderService: ContractSearchService, private _spinner: SpinnerServiceService,  public _serverErrorCode: ServerErrorcodeService, public _sortTable: SortSearchTableService,private _sessionTimeOutService:SessionTimeOutService) { }
  
 sortLookUpDataByColumn () {
    this._sortTable.sortTableData(this.tableData, this.lookupSortIn, this.looUpOrderBy);
  }
  sortLookUpDataByOrder () {
    this._sortTable.sortTableData(this.tableData, this.lookupSortIn, this.looUpOrderBy);
  }
  
  showModal(r,e) {
    this.looUpOrderBy = "asnd"; //default order by ascending 
    this.lookupSortIn = "activitySeq"; //default sort by activity
    delete this.historyData.contract.contractId;
    this.tableData = [];
    this.getHistoryData(r, e);    
    UIkit.modal('#modal-center').show();    
  }
  getHistoryData (r, e) {
    this.historyData.contract.contractId = r.contractId;    
    this._spinner.showSpinner();
    var backendData = this._joborderService.getData(this.historyData);
    
    backendData.subscribe(
      (data) => {
        if(data == "userSessionExpired"){
          UIkit.modal('#modal-center').hide();
          this._sessionTimeOutService.checkSessionTimeout(data);
          UIkit.modal('#modal-center').hide();
        }
        else if (data.hasOwnProperty("errorCode")) { 
          this.errorText = this._serverErrorCode.errorCodes[data["errorCode"]];          
          this.showHistoryFlag = true; 
        }
        else {
          this.tableData = data["searchResult"]["result"];
          this.showHistoryFlag = false; 
        }
        this._spinner.hideSpinner();
      }
    )
    
  }
  ngOnInit() {
    
  }

}
