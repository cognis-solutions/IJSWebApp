import { Component, OnInit, ViewChild, Input, Output, EventEmitter }from '@angular/core';
declare var UIkit: any;
declare var jQuery: any;
import { WindowRefService } from "../../../common-services/window-ref.service";
import { UserTypeService } from "../../../user/user-type.service";
import { RclappUrlService } from "../../../common-services/rclapp-url.service"
import { SpinnerServiceService } from "../../../common-services/spinner-service.service";
import { ServerErrorcodeService } from "../../../common-services/server-errorcode.service";
import { JoInquiryService } from "../jo-inquiry.service";
import { SessionTimeOutService } from "app/common-services/session-time-out.service";


@Component({
  selector: 'app-jo-inquiry-search',
  templateUrl: './jo-inquiry-search.component.html',
  styleUrls: ['./jo-inquiry-search.component.scss']
})
export class JoInquirySearchComponent implements OnInit {

  maintainJoShowfilter: boolean = false;
  //this is the data to be implemented in filter 

  maintainJoSearchData: any = {
    "maintainJoParam": {
      jobOrdTyp: "Job Order Type",
      jobOrdSts: "Job Order Status"
    },
    "action": "joSearch"
  }
  maintainJoSelectConfig: any = {
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
  maintainJoLegTypeSelectOptions: any;
  maintainJoLegTypeSelectAllOptions: any = [
    {
      label: 'Single',
      value: 'Single',
    }, {
      label: 'xxxxxxx',
      value: 'xxxxxxx',
    },
  ];
  maintainJoTypeSelectBLOptions: any = [
    {
      label: 'Sealeg',
      value: 'SEALEG',
    }, {
      label: 'Inter Terminal',
      value: 'IT'
    },
    {
      label: 'Empty Repo',
      value: 'ER'
    }, {
      label: 'Laden Ad-hoc',
      value: 'LAH'
    },
  ];
  maintainJoTypeSelectBookingOptions: any = [
    {
      label: 'Export Truck/Rail',
      value: 'ETR',
    }, {
      label: 'Import Truck/Rail',
      value: 'ITR',
    }, {
      label: 'Inter Terminal',
      value: 'IT'
    },
    {
      label: 'Empty Repo',
      value: 'ER'
    }, {
      label: 'Laden Ad-hoc',
      value: 'LAH'
    },
  ];

  //filter data selected
  filterDataSelectedComp: any = {
    "filterDataArr": []
  };
  maintainJoLocOptions = [
    {
      label: 'Port',
      value: 'Port'
    },
    {
      label: 'Door',
      value: 'Door'
    }, {
      label: 'Terminal',
      value: 'Terminal'
    }, {
      label: 'Depot',
      value: 'Depot'
    }, {
      label: 'Haulage Loc.',
      value: 'Haulage'
    }
  ];

  maintainJoPOLOptions: any = [
    {
      label: 'POL1',
      value: 'POL1',
    }, {
      label: 'POL2',
      value: 'POL2',
    },
  ];

  maintainJoPODOptions: any = [
    {
      label: 'POD1',
      value: 'POD1',
    }, {
      label: 'POD2',
      value: 'POD2',
    },
  ];

  countryLookUpData: any = [{ "label": "Country", "value": "Country", "dropDownData": [{ "label": "Country", "value": "SCCODE" }, { "label": "Country Name", "value": "SCNAME" }, { "label": "Status", "value": "SCRCST" }] }]

  maintainJoVendorLookUpData: any = [{ "label": "Vendor", "value": "Vendor", "dropDownData": [{ "label": "Vendor", "value": "VCVNCD" }, { "label": "Vendor Name", "value": "VCVDNM" }, { "label": "Vendor Type", "value": "VCVDTY" }, { "label": "City", "value": "VCCITY" }, { "label": "Country", "value": "VCCNTY" }, { "label": "State", "value": "VCSTAT" }] }, { "label": "Vendor Name", "value": "Vendor" }];

  maintainJoserviceVesselVoyagelookupData: any = [{ "label": "Service/Vessel/Voyage", "value": "serviceVesselLookup", "dropDownData": [{ "label": "Service", "value": "VVSRVC" }, { "label": "Vessel", "value": "VVVESS" }, { "label": "Voyage", "value": "VVVOYN" }] }];

  maintainJoKeywordlookupData: any = [{ "label": "Booking", "value": "Booking" }, { "label": "BL", "value": "BL" }, { "label": "Routing", "value": "Routing" }, { "label": "Contract", "value": "Contract" }, { "label": "Container", "value": "Container" }, { "label": "Service", "value": "Service" }, { "label": "Vessel", "value": "Vessel" }, { "label": "Voyage", "value": "Voyage" }];

  maintainJoRoutinglookupData: any = [{ "label": "Routing", "value": "Routing" }, { "label": "Contract", "value": "Contract" }, { "label": "Container", "value": "Container" }];

  maintainJoCostlookupData: any = [{ "label": "JOCostFrom", "value": "JO Cost From" }, { "label": "JOCostTo", "value": "JO Cost To" }];

  fscLookUpData: any = [{ "label": "FSC", "value": "fsc", "dropDownData": [{ "label": "FSC Code", "value": "FSCCD" }, { "label": "FSC Description", "value": "FSCDESC" }, { "label": "Company Name", "value": "CMPNM" }, { "label": "City", "value": "FSCCITY" }, { "label": "State", "value": "FSCSTAT" }, { "label": "Country", "value": "FSCCCNTY" }, { "label": "Status", "value": "FSCSTS" }] }, { "label": "FSC Name", "value": "FSCNM" }];

  private showLocErrorText: boolean = false;
  private validationTextFlag: boolean = false;
  private validationText: string;
  private errorTextLookUp: string;
  private processJoPOL: string; //Pick up location
  private processJoPOD: string;  //Drop off location 
  private locationCodePickUp: string; //Pick up location
  private locationCodeDropOff: string;  //Drop off location 
  private processJoLocPickupTpye: string; //pickup Loaction type
  private processJoLocDropOffType: string; //Dropof Loaction type
  private fromOrTo: string;

  @ViewChild('maintainJoSearchFilter') maintainJoSearchFilter: any;
  @Input() private filterData: any;
  @ViewChild('maintainJoJobOrdTypTip') maintainJoJobOrdTypTip;
  @ViewChild('maintainJoJobOrdStsTip') maintainJoJobOrdStsTip;
  @ViewChild('processJoDropLoc') advanceDropLoc: any;
  @ViewChild('maintainJoPickLoc') advancePickLoc: any;
  @ViewChild('maintainJoPPTDHLookup') maintainJoPPTDHLookup: any;
  @ViewChild('maintainJoPOLPODLookup') maintainJoPOLPODLookup: any;
  @ViewChild('rclContainer') _rclContainer;
  @ViewChild('rclJoLog') _rclJoLog;
  @ViewChild('rclReasonCode') _rclRsnCd;
  @ViewChild('rclBookingBL') _rclBookingBL;
  @ViewChild('rclDGIMDG') _rclDGIMDG;
  @Output() searchDataJoInquiry: EventEmitter<any> = new EventEmitter();

  constructor(private spinner: SpinnerServiceService, private _serverErrorCode: ServerErrorcodeService, /*private _sortTable: ProcessjoSortSearchTableService,*/ private _userTypeService: UserTypeService, private _rclappUrlService: RclappUrlService , private _joInquiryService: JoInquiryService,private _sessionTimeOutService:SessionTimeOutService) { }

  ngOnInit() {
    this.maintainJoLegTypeSelectOptions = this.maintainJoLegTypeSelectAllOptions
  }
  private showHideJobOrdTypeToolTip() {
    if (this.maintainJoJobOrdTypTip.isOpen()) {
      this.maintainJoJobOrdTypTip.close();
    }
    else {
      this.maintainJoJobOrdTypTip.open();
    }
  }

  //show hide vehicletool tip
  private showHideJobOrdStsToolTip() {
    if (this.maintainJoJobOrdStsTip.isOpen()) {
      this.maintainJoJobOrdStsTip.close();
    }
    else {
      this.maintainJoJobOrdStsTip.open();
    }
  }

  //showhide filter
  showhideFilter(e) {
    if (this.maintainJoShowfilter) {
      this.maintainJoShowfilter = false;
      document.getElementById("filterButton").classList.remove("filterButtonClicked");
    }
    else {
      this.maintainJoShowfilter = true;
      document.getElementById("filterButton").classList.add("filterButtonClicked");
    }
  }

  //filter data from filter comoponent
  private filterDataSelected(filterDataIncompo) {
    this.filterDataSelectedComp = this.maintainJoSearchFilter.filterData;
  }

  private openPickupDropoffPopUp(e) {
    if (e.target.disabled) {
      e.stopPropogation();
    } else {
      UIkit.modal('#maintainJOPickDropModal').show();
    }
  }

  private openPOLPODPopUp(e) {
    if (e.target.disabled) {
      e.stopPropogation();
    } else {
      UIkit.modal('#maintainJOPOLPODModal').show();
    }
  }

  private showHideflag = false;
  private hideshowAdvanceDiv(e) {
    if (this.showHideflag) {
      this.showHideflag = false;
    } else {
      this.showHideflag = true;
    }
  }

  // selectPOLPODLookUpValue(e) {
  //   var isValidated = this.POLPODLookupValidation();
  //   if (isValidated) {

  //     if (this.processJoPOL && this.processJoPOD) {
  //       this.maintainJoSearchData.maintainJoParam.polPod = this.processJoPOL + " / " + this.processJoPOD
  //     }
  //     UIkit.modal('#maintainJOPOLPODModal').hide();
  //   }
  // }

  //validation 
  // POLPODLookupValidation() {
  //   if (this.processJoPOL && this.processJoPOD) {
  //     this.showLocErrorText = false;
  //     return true;
  //   }
  //   else {
  //     if (this.processJoPOL == undefined && this.processJoPOD == undefined) {
  //       this.errorTextLookUp = "Please provide both one of the POL and POD fields."
  //       this.showLocErrorText = true;
  //       return false;
  //     }
  //   }
  // }



  selectLocLookUpValue(e) {
    var isValidated = this.locLookupValidation();
    if (isValidated) {

      if (this.locationCodePickUp && this.locationCodeDropOff) {
        this.maintainJoSearchData.maintainJoParam.pptdhVal = this.locationCodePickUp + " / " + this.locationCodeDropOff
      } else if (this.locationCodePickUp) {
        this.maintainJoSearchData.maintainJoParam.pptdhVal = this.locationCodePickUp;
      } else if (this.locationCodeDropOff) {
        this.maintainJoSearchData.maintainJoParam.pptdhVal = this.locationCodeDropOff;
      }
      UIkit.modal('#maintainJOPickDropModal').hide();
    }
  }

  //validation 
  locLookupValidation() {
    if (this.locationCodePickUp || this.locationCodeDropOff) {
      this.showLocErrorText = false;
      return true;
    }
    else {
      if (this.locationCodePickUp == undefined && this.locationCodeDropOff == undefined) {
        this.errorTextLookUp = "Please provide atleast one of the PickUp and DropOff fields."
        this.showLocErrorText = true;
        return false;
      }
    }
  }

  //open from lookup modal
  private openFromLookup(selectedValue, fromOrTo) {
    this.fromOrTo = fromOrTo;
    this.maintainJoPPTDHLookup.openFromLookup(selectedValue, fromOrTo);
  }

  //open to lookup modal
  private openToLookup(selectedValue, fromOrTo) {
    this.fromOrTo = fromOrTo;
    this.maintainJoPPTDHLookup.openToLookup(selectedValue, fromOrTo);
  }

  //open from lookup modal
  // private openFromPOLPODLookup(selectedValue, fromOrTo) {
  //   this.fromOrTo = fromOrTo;
  //   this.maintainJoPOLPODLookup.openFromLookup("Port", "from");
  // }

  //open to lookup modal
  // private openToPOLPODLookup(selectedValue, fromOrTo) {
  //   this.fromOrTo = fromOrTo;
  //   this.maintainJoPOLPODLookup.openToLookup("Port", "to");
  // }

  private resetSearch() {
    this.maintainJoSearchData = {
      "maintainJoParam": {
        jobOrdTyp: "Job Order Type",
        jobOrdSts: "Job Order Status"
      },
      "action": "joSearch"
    }
    this.validationTextFlag = false;
    this.validationText = ""
  }

  insertLegshowRouteList(e) {
    this._rclContainer.openLookUpModal();
  }

  insertShowRclJoLog(e) {
    this._rclJoLog.openLookUpModal();
  }

  insertShowRclRsnCd(e) {
    this._rclRsnCd.openLookUpModal();
  }

  insertShowBookingBL(e) {
    this._rclBookingBL.openLookUpModal();
  }

  insertShowDGIMDG(e) {
    this._rclDGIMDG.openLookUpModal();
  }

  //fill the data of code when row selected
  private rowSelected(e: any) {
    let type: string = e[1];
    let event: any = e[0];

    if (type == "Door") {
      if (this.fromOrTo == "to") {

        this.maintainJoSearchData.maintainJoParam["toLocation"] = event.target.parentElement.children[1].textContent;

        this.locationCodeDropOff = type + "-" + event.target.parentElement.children[1].textContent + "," + event.target.parentElement.lastElementChild.innerText;
      }
      else {
        this.maintainJoSearchData.maintainJoParam["fromLocation"] = event.target.parentElement.children[1].textContent;

        this.locationCodePickUp = type + "-" + event.target.parentElement.children[1].textContent + "," + event.target.parentElement.lastElementChild.innerText;
      }
    }
    else if (type == "Terminal") {

      if (this.fromOrTo == "to") {

        this.locationCodeDropOff = type + "-" + event.target.parentElement.children[1].textContent + "," + event.target.parentElement.lastElementChild.innerText;
        this.maintainJoSearchData.maintainJoParam["toLocation"] = event.target.parentElement.children[1].textContent;
      }
      else {
        this.locationCodePickUp = type + "-" + event.target.parentElement.children[1].textContent + "," + event.target.parentElement.lastElementChild.innerText;
        this.maintainJoSearchData.maintainJoParam["fromLocation"] = event.target.parentElement.children[1].textContent;
      }

    }
    else if (type == "Depot") {
      if (this.fromOrTo == "to") {
        this.locationCodeDropOff = type + "-" + event.target.parentElement.children[1].textContent + "," + event.target.parentElement.lastElementChild.innerText;
        this.maintainJoSearchData.maintainJoParam["toLocation"] = event.target.parentElement.children[1].textContent;
      }
      else {
        this.locationCodePickUp = type + "-" + event.target.parentElement.children[1].textContent + "," + event.target.parentElement.lastElementChild.innerText;
        this.maintainJoSearchData.maintainJoParam["fromLocation"] = event.target.parentElement.children[1].textContent;

      }
    }
    else if (type == "Haulage") {

      if (this.fromOrTo == "to") {
        this.locationCodeDropOff = type + "-" + event.target.parentElement.children[1].textContent + "," + event.target.parentElement.lastElementChild.innerText;
        this.maintainJoSearchData.maintainJoParam["toLocation"] = event.target.parentElement.children[1].textContent;
      }
      else {
        this.locationCodePickUp = type + "-" + event.target.parentElement.children[1].textContent + "," + event.target.parentElement.lastElementChild.innerText;
        this.maintainJoSearchData.maintainJoParam["fromLocation"] = event.target.parentElement.children[1].textContent;

      }
    }
    else if (type == "Port") {

      if (this.fromOrTo == "to") {
        this.locationCodeDropOff = type + "-" + event.target.parentElement.children[1].textContent + "," + event.target.parentElement.lastElementChild.innerText;
        this.maintainJoSearchData.maintainJoParam["toLocation"] = event.target.parentElement.children[1].textContent;
      }
      else {
        this.locationCodePickUp = type + "-" + event.target.parentElement.children[1].textContent + "," + event.target.parentElement.lastElementChild.innerText;
        this.maintainJoSearchData.maintainJoParam["fromLocation"] = event.target.parentElement.children[1].textContent;
      }
    }

    UIkit.modal('#maintainJOPickDropModal').show();
  }

  // //fill the data of code when row selected
  // private rowSelectedPOLPOD(e: any) {
  //   let type: string = e[1];
  //   let event: any = e[0];

  //   if (type == "Port") {

  //     if (this.fromOrTo == "to") {
  //       this.processJoPOD = type + "-" + event.target.parentElement.children[1].textContent + "," + event.target.parentElement.lastElementChild.innerText;
  //       this.maintainJoSearchData.maintainJoParam["toLocation"] = event.target.parentElement.children[1].textContent;
  //     }
  //     else {
  //       this.processJoPOL = type + "-" + event.target.parentElement.children[1].textContent + "," + event.target.parentElement.lastElementChild.innerText;
  //       this.maintainJoSearchData.maintainJoParam["fromLocation"] = event.target.parentElement.children[1].textContent;
  //     }
  //   }

  //   UIkit.modal('#maintainJOPOLPODModal').show();
  // }

  // private searchJoInquiry(e) {
  //   let searchInputOutPutData = {
  //     "filterInputData": this.filterDataSelectedComp,
  //     "searchInputData": this.maintainJoSearchData
  //   }

  //   this.searchDataJpInquiry.emit(searchInputOutPutData)
  // }

private searchJoInquiry(e) {    
    let findButton = document.getElementById('find-button-inquiryJo');
        findButton.innerHTML = '<i class="fa fa-search"></i> <span>&nbsp;LOADING...</span>'    
        this.getBackEndData(findButton);    
  }
  
  //set the value of Contract, routing, Container 
  private setMaintainJoRoutingContractContainer(e) {
    this.maintainJoSearchData.maintainJoParam.routContractOrContType = e.type;
    this.maintainJoSearchData.maintainJoParam.routContractOrContValue = e.value;
  }
  
  //set the value of JoCost
  private setMaintainJoCost (e) {
    this.maintainJoSearchData.maintainJoParam.joCostTyp = e.type;
    this.maintainJoSearchData.maintainJoParam.joCostValue = e.value;
  }
  
  //set the value of BookingOrBL
  private setMaintainJoBookingOrBL(e) {
    this.maintainJoSearchData.maintainJoParam.bookingOrBlType = e.type;
    this.maintainJoSearchData.maintainJoParam.bookingOrBlValue = e.value;
  }
  


  errorCodetext: string;
  tableDataMaintenanceJoSearch1: any = [];
  serverErrorCodetext: string;  
  private getBackEndData (findButton) {
    this.spinner.showSpinner();
    let backendData = this._joInquiryService.getJoInquiryData(this.maintainJoSearchData);
    backendData.subscribe(
      (data) => {
        if(data == "userSessionExpired"){
          this._sessionTimeOutService.checkSessionTimeout(data);
        }
        else if (data.hasOwnProperty("errorCode")) {
          this.errorCodetext = this._serverErrorCode.checkError(data["errorCode"]);
          UIkit.modal('#processjo-error-code-modal').show();
        }
        else {
          this.tableDataMaintenanceJoSearch1 = data;
          
          
          let searchInputOutPutData = {
            "filterInputData": this.filterDataSelectedComp,
            "searchInputData": this.maintainJoSearchData,
            "searchOutputData": this.tableDataMaintenanceJoSearch1,
            "filteredSearchOutputData": this.tableDataMaintenanceJoSearch1
          }
         
          this.searchDataJoInquiry.emit(searchInputOutPutData);
        }
        this.spinner.hideSpinner();
        if (findButton !== undefined)
          findButton.innerHTML = '<i class="fa fa-search"></i> <span>&nbsp;FIND</span>'
      },
      (err) => {
        this.spinner.hideSpinner();
        // A client-side or network error occurred. Handle it accordingly.
        findButton.innerHTML = '<i class="fa fa-search"></i> <span>&nbsp;FIND</span>'
        this.serverErrorCodetext = "Something Went wrong,  Please try again.";
        UIkit.modal('#serverErrorCodeModal').show();
      }
    )
  }


}
