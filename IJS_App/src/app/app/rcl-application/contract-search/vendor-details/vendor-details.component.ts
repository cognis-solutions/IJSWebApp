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
  selector: 'app-vendor-details',
  templateUrl: './vendor-details.component.html',
  styleUrls: ['./vendor-details.component.scss']
})
export class VendorDetailsComponent implements OnInit {

  constructor(public _joborderService: ContractSearchService, private _spinner: SpinnerServiceService,  public _serverErrorCode: ServerErrorcodeService, public _sortTable: SortSearchTableService,private _sessionTimeOutService:SessionTimeOutService) { }
  
  vendorRecivedData: any = {
    contact: {},
    address: {}    
  };
  vendorDetailsData: any = {   
    contract: {},
    action: "vendor_details"
  };
  
  showVendorDetailsFlag: boolean = false;
  errorText: string;
  
  showModal(r,e) {
    this.getVendorDetailsData(r, e);    
    UIkit.modal('#modal-center-vendor-details').show();    
  }
  getVendorDetailsData (r, e) {
    this.vendorDetailsData.contract.vendorCode = r.vendorCode;    
    this._spinner.showSpinner();
    var backendData = this._joborderService.getData(this.vendorDetailsData);
    
    backendData.subscribe(
      (data) => {
        if(data == "userSessionExpired"){
          this._sessionTimeOutService.checkSessionTimeout(data);
        }
        else if (data.hasOwnProperty("errorCode")) {
          this.errorText = this._serverErrorCode.errorCodes[data["errorCode"]];          
          this.showVendorDetailsFlag = true; 
        }
        else {
          this.vendorRecivedData = data["searchResult"]["result"][0];
          this.showVendorDetailsFlag = false;
        }
        this.vendorRecivedData.vendorName = r.vendorName;
        this._spinner.hideSpinner();
      }
    )    
  }

  ngOnInit() {
  }
  
}
