import { Component, OnInit, ViewChild, ChangeDetectorRef } from '@angular/core';
import { NgbModal, NgbModalOptions } from "@ng-bootstrap/ng-bootstrap";
import { ContractSearchService } from "../contract-search.service";
import { SpinnerServiceService } from "../../../common-services/spinner-service.service";
import { SortSearchTableService } from '../sort-search-table.service';
import { ServerErrorcodeService } from "../../../common-services/server-errorcode.service";
import { trigger, state, style, animate, transition, keyframes } from '@angular/animations';
import { SessionTimeOutService } from "app/common-services/session-time-out.service";
declare var UIkit: any;


@Component({
  selector: 'app-compare',
  templateUrl: './compare.component.html',
  styleUrls: ['./compare.component.scss']
})
export class CompareComponent implements OnInit {

  constructor(public _joborderService: ContractSearchService, private _spinner: SpinnerServiceService, public _serverErrorCode: ServerErrorcodeService, public _sortTable: SortSearchTableService,private _sessionTimeOutService:SessionTimeOutService) { }

  vendorCompareData: any = []
  showCompareFlag: boolean = false;
  errorText: string;
  rowObj: any = {};

  showModal(r) {
    this.getCompareData(r);
    UIkit.modal('#modal-center-compare').show();
  }
  getCompareData(rowObj) {
    this.rowObj = rowObj;
    let compareSearchData = {
      action: "compare",
      contract: {
        vendorCode: rowObj.vendorCode,
        fromLocation: rowObj.fromLocation,
        fromTerminal: rowObj.fromTerminal,
        toLocation: rowObj.toLocation,
        toTerminal: rowObj.toTerminal,
        startDate: rowObj.startDate,
        endDate: rowObj.endDate,
        transMode : rowObj.transMode,
      }
    }

    this._spinner.showSpinner();
    this.vendorCompareData = [];
    this.showCompareFlag = false; 
    var backendData = this._joborderService.getData(compareSearchData);
    backendData.subscribe(
      (data) => {
        if(data == "userSessionExpired"){
          UIkit.modal('#modal-center-compare').hide();
          this._sessionTimeOutService.checkSessionTimeout(data);
        }
        else if (data.hasOwnProperty("errorCode")) {          
          this.errorText = this._serverErrorCode.errorCodes[data["errorCode"]];          
          this.showCompareFlag = true;    
        }
        else {
          this.vendorCompareData = data;
          this.showCompareFlag = false;  
        }
        this._spinner.hideSpinner();
      }
    )
  }
  ngOnInit() {
  }

}
