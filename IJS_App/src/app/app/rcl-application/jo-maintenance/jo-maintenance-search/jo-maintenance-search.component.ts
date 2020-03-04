import { Component, OnInit, ViewChild, Input, Output, EventEmitter }from '@angular/core';
declare var UIkit: any;
declare var jQuery: any;
import { WindowRefService } from "../../../common-services/window-ref.service";
import { UserTypeService } from "../../../user/user-type.service";
import { SpinnerServiceService } from "../../../common-services/spinner-service.service";
import { ServerErrorcodeService } from "../../../common-services/server-errorcode.service";
import { JoMaintenanceSearchService } from "../jo-maintenance-search.service";
import { JoMaintenanceSortingService } from "../jo-maintenance-sorting.service";
import {NgbTooltip} from '@ng-bootstrap/ng-bootstrap';
import { SessionTimeOutService } from "app/common-services/session-time-out.service";

@Component({
  selector: 'app-jo-maintenance-search',
  templateUrl: './jo-maintenance-search.component.html',
  styleUrls: ['./jo-maintenance-search.component.scss']
})
export class JoMaintenanceSearchComponent implements OnInit {

  maintainJoShowfilter: boolean = false;
  //this is the data to be implemented in filter 

  maintainJoSearchData: any = {
    "maintainJoParam": {
    },
    "action": "maintainJoSearch"
  }


  joMaintananceSummary: any=
  { 
    "joSummary":{

  },
  "action":"maintainJoSearch"

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
      label: 'Truck',
      value: 'Truck',
    },
    {
      label: 'Rail',
      value: 'Rail',
    }, 
    {
      label: 'Barge',
      value: 'Barge',
    },
    {
      label: 'Feeder',
      value: 'Feeder',
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

  maintainJoRoutinglookupData: any = [{ "label": "Contract", "value": "Contract" }, { "label": "Container", "value": "Container" }];

  maintainJoCostlookupData: any = [{ "label": "JOCostFrom", "value": "JO Cost From" }, { "label": "JOCostTo", "value": "JO Cost To" }];

  maintainJoBookinglookupData: any = [{ "label": "Booking", "value": "Booking" }, { "label": "BL", "value": "BL" }]

  fscLookUpData: any = [{ "label": "FSC", "value": "fsc", "dropDownData": [{ "label": "FSC Code", "value": "FSCCD" }, { "label": "FSC Description", "value": "FSCDESC" }, { "label": "Company Name", "value": "CMPNM" }, { "label": "City", "value": "FSCCITY" }, { "label": "State", "value": "FSCSTAT" }, { "label": "Country", "value": "FSCCCNTY" }, { "label": "Status", "value": "FSCSTS" }] }, { "label": "FSC Name", "value": "FSCNM" }];

  maintainJoCOCOptions: any = [
    {
      label: 'SOC',
      value: 'S'
    }, {
      label: 'COC',
      value: 'C'
    }
  ];

  elements: any = 
  [
    {jotype: 'Sealeg',issued: '', started: '', waitelisted: '', rejected: '',cancelled: '', completed: ''},
    {jotype: 'Export',issued: '', started: '', waitelisted: '', rejected: '',cancelled: '', completed: ''},
    {jotype: 'Import',issued: '', started: '', waitelisted: '', rejected: '',cancelled: '', completed: '' },
    {jotype: 'Internal Terminal', issued: '', started: '', waitelisted: '', rejected: '',cancelled: '', completed: ''},
    {jotype: 'Empty Repo',issued: '', started: '', waitelisted: '', rejected: '',cancelled: '', completed: '' },
    {jotype: 'Laden AdHoc',issued: '', started: '', waitelisted: '', rejected: '',cancelled: '', completed: '' },
    {jotype: 'Total', issued: '', started: '', waitelisted: '', rejected: '',cancelled: '', completed: ''}
  ];

  headElements = ['JO Type','Issued', 'Started','Waitelisted/Pending', 'Rejected', 'Cancelled', 'Completed'];

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
  private joTypeData = [{ id: "S", text: "Sealeg" }, { id: "O", text: "Export/Truck Rail" }, { id: "I", text: "Import/Truck Rail" }, { id: "T", text: "Inter Terminal" }, { id: "A", text: "AdHoc Empty Repo" }, { id: "L", text: "AdHoc Laden" }]
  private joOrdStseData: any;
  private jobOrdTypeText = "Job Order Type";
  private jobOrdStsText = "Job Order status";
  checkBoxValue : boolean;
  checkBoxValue1: boolean;
  checkBoxJoCreation: boolean;


  @Input() private filterData: any;
  @Input() private componentType: any;
  @ViewChild('maintainJoSearchFilter') maintainJoSearchFilter: any;
  @ViewChild('maintainJoJobOrdTypTip') maintainJoJobOrdTypTip;
  @ViewChild('maintainJoJobOrdStsTip') maintainJoJobOrdStsTip;
  @ViewChild('maintainJoDropLoc') maintainJoDropLoc: any;
  @ViewChild('maintainJoPickLoc') maintainJoPickLoc: any;
  @ViewChild('maintainJoPPTDHLookup') maintainJoPPTDHLookup: any;
  @ViewChild('maintainJoPOLPODLookup') maintainJoPOLPODLookup: any;
  @ViewChild('rclContainer') _rclContainer;
  @ViewChild('rclJoLog') _rclJoLog;
  @ViewChild('rclReasonCode') _rclRsnCd;
  @ViewChild('rclBookingBL') _rclBookingBL;
  @ViewChild('rclDGIMDG') _rclDGIMDG;
  @ViewChild('maintainJobTypTipContent') public _maintainJobTypTipContent: NgbTooltip;
  @ViewChild('maintainJobStsTipContent') public _maintainJobStsTipContent: NgbTooltip;
  @Input() private userType: string;


  @Output() searchDataJoInquiry: EventEmitter<any> = new EventEmitter();



  constructor(private spinner: SpinnerServiceService, private _serverErrorCode: ServerErrorcodeService, private _sortTable: JoMaintenanceSortingService, private _userTypeService: UserTypeService, public _maintenanceService: JoMaintenanceSearchService,private _sessionTimeOutService:SessionTimeOutService) { }

  ngOnInit() {
    //alert(this.userType);
   // alert(this._userTypeService['appUserType'])
    this.maintainJoSearchData.maintainJoParam['componentType'] = this.componentType;    
    this.joOrdStseData = [];
    if(this.maintainJoSearchData.maintainJoParam['componentType'] == 'joinquiry') {
      this.joOrdStseData = [{ id: "S", text: "Start" },{ id: "E", text: "Entry" }, { id: "I", text: "Issued" }, { id: "D", text: "Waitlisted" }, { id: "W", text: "Rejected" }, { id: "P", text: "Pending" }, { id: "M", text: "Completed" }, { id: "C", text: "Cancelled" }]
    } else {
      this.joOrdStseData = [{ id: "S", text: "Start" },{ id: "E", text: "Entry" }, { id: "I", text: "Issued" }, { id: "D", text: "Waitlisted" }, { id: "W", text: "Rejected" }, { id: "P", text: "Pending" }]
    }
    
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

  selectJotypeValue(e) {
    this.joTypeData.forEach(ele => {
      if (ele.text == e) {
        this.maintainJoSearchData.maintainJoParam.jobOrdTyp = ele.id;
      }
    });    
  }


  selectjobOrdStsValue(e) {
    this.joOrdStseData.forEach(ele => {
      if (ele.text == e) {
        this.maintainJoSearchData.maintainJoParam.jobOrdSts = ele.id;
        this.maintainJoSearchData.maintainJoParam.jobOrdStsText = ele.text;
      }
    });    
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

  // private openPOLPODPopUp(e) {
  //   if (e.target.disabled) {
  //     e.stopPropogation();
  //   } else {
  //     UIkit.modal('#maintainJOPOLPODModal').show();
  //   }
  // }

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

  // onClickOutsideTooltip1(event) {
  //   this._maintainJobTypTipContent.close();
  //   this._maintainJobStsTipContent.close();
  // }


  //select value of location form slect button
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


  dropOffDropDownChange(e) {
    this.maintainJoSearchData.maintainJoParam["toLocation"] = undefined;
    this.maintainJoSearchData.maintainJoParam["toTerminal"] = undefined;
  }
  pickUpDropDownChange(e) {
    this.maintainJoSearchData.maintainJoParam["fromLocation"] = undefined;
    this.maintainJoSearchData.maintainJoParam["fromTerminal"] = undefined;
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
    this.maintainJoSearchData = 
    {
      "maintainJoParam": {},
      "action": "joSearch"
    }
    this.jobOrdTypeText = "Job Order Type";
    this.jobOrdStsText = "Job Order status";
    this.validationTextFlag = false;
    this.validationText = "";
    this.checkBoxValue = false;
    this.checkBoxValue1 = false;
    //this.checkBoxValue2=false;
    this.errormessage = true;
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
        this.maintainJoSearchData.maintainJoParam["toLocType"] = type;
        this.maintainJoSearchData.maintainJoParam["toLocation"] = event.target.parentElement.children[1].textContent;
      }
      else {
        this.maintainJoSearchData.maintainJoParam["fromLocType"] = type;
        this.maintainJoSearchData.maintainJoParam["fromLocation"] = event.target.parentElement.children[1].textContent;
      }
    }
    else if (type == "Terminal") {
      if (this.fromOrTo == "to") {
        this.maintainJoSearchData.maintainJoParam["toLocType"] = type;
        this.maintainJoSearchData.maintainJoParam["toLocation"] = event.target.parentElement.children[3].textContent;
        this.maintainJoSearchData.maintainJoParam["toTerminal"] = event.target.parentElement.children[1].textContent;
      }
      else {
        this.maintainJoSearchData.maintainJoParam["fromLocType"] = type;
        this.maintainJoSearchData.maintainJoParam["fromLocation"] = event.target.parentElement.children[3].textContent;
        this.maintainJoSearchData.maintainJoParam["fromTerminal"] = event.target.parentElement.children[1].textContent;
      }
    }
    else if (type == "Depot") {
      if (this.fromOrTo == "to") {
        this.maintainJoSearchData.maintainJoParam["toLocType"] = type;
        this.maintainJoSearchData.maintainJoParam["toTerminal"] = event.target.parentElement.children[1].textContent;
        this.maintainJoSearchData.maintainJoParam["toLocation"] = event.target.parentElement.children[5].textContent;
      }
      else {
        this.maintainJoSearchData.maintainJoParam["fromLocType"] = type;
        this.maintainJoSearchData.maintainJoParam["fromTerminal"] = event.target.parentElement.children[1].textContent;;
        this.maintainJoSearchData.maintainJoParam["fromLocation"] = event.target.parentElement.children[5].textContent;
      }
    }
    else if (type == "Haulage") {

      if (this.fromOrTo == "to") {
        this.maintainJoSearchData.maintainJoParam["toLocType"] = type;
        this.maintainJoSearchData.maintainJoParam["toTerminal"] = event.target.parentElement.children[1].textContent;;
        this.maintainJoSearchData.maintainJoParam["toLocation"] = event.target.parentElement.children[2].textContent;
        //  this.locationCodeDropOff = type + "-" + event.target.parentElement.children[1].textContent + "," + event.target.parentElement.lastElementChild.innerText;
      }
      else {
        this.maintainJoSearchData.maintainJoParam["fromLocType"] = type;
        this.maintainJoSearchData.maintainJoParam["fromTerminal"] = event.target.parentElement.children[1].textContent;
        this.maintainJoSearchData.maintainJoParam["fromLocation"] = event.target.parentElement.children[2].textContent;
        // this.locationCodePickUp = type + "-" + event.target.parentElement.children[1].textContent + "," + event.target.parentElement.lastElementChild.innerText;
      }
    }
    else if (type == "Port") {
      if (this.fromOrTo == "to") {
        this.maintainJoSearchData.maintainJoParam["toLocType"] = type;
        this.maintainJoSearchData.maintainJoParam["toTerminal"] = event.target.parentElement.children[1].textContent;
        // this.locationCodeDropOff = type + "-" + event.target.parentElement.children[1].textContent + "," + event.target.parentElement.lastElementChild.innerText;
      }
      else {
        this.maintainJoSearchData.maintainJoParam["fromLocType"] = type;
        this.maintainJoSearchData.maintainJoParam["fromTerminal"] = event.target.parentElement.children[1].textContent;
      }
    }
  }

  //fill the data of code when row selected
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


  //destroy modal popups
  // resetPickDropModal(e) {
  //   this.locationCodePickUp = undefined;
  //   this.locationCodeDropOff = undefined;
  //   this.maintainJoDropLoc.valueAccessor.resetSelectSize();
  //   this.maintainJoPickLoc.valueAccessor.resetSelectSize();
  // }

  private searchMaintenanceJoOrder(e) {

    this.errormessage = true;

  

    //show default sort in sort in 
    if(this.filterDataSelectedComp.sortIn ==undefined){
       this.filterDataSelectedComp.sortIn = "JoNumber";        
    }
    //show default order in order by
    if(this.filterDataSelectedComp.orderBy ==undefined){       
      this.filterDataSelectedComp.orderBy = "asnd"; 
    }

    this.getFilterCriteria();//to get filter criteria

    let findButton = document.getElementById('find-button');
    findButton.innerHTML = '<i class="fa fa-search"></i> <span>&nbsp;LOADING...</span>'
    this.getBackEndData(findButton);
  }

  //set the value of Contract, routing, Container 
  private setMaintainJoRoutingContractContainer(e) {
    this.maintainJoSearchData.maintainJoParam.routContractOrContType = e.type;
    this.maintainJoSearchData.maintainJoParam.routContractOrContValue = e.value;
  }

  //set the value of JoCost
  private setMaintainJoCost(e) {
    this.maintainJoSearchData.maintainJoParam.joCostTyp = e.type;
    this.maintainJoSearchData.maintainJoParam.joCostValue = e.value;
  }

  //set the value of BookingOrBL
  private setMaintainJoBookingOrBL(e) {
    this.maintainJoSearchData.maintainJoParam.bookingOrBlType = e.type;
    this.maintainJoSearchData.maintainJoParam.bookingOrBlValue = e.value;
  }


  errormessage:boolean= true; 
  errorCodetext: string;
  tableDataMaintenanceJoSearch1: any = [];
  serverErrorCodetext: string;
  private getBackEndData(findButton) {
    this.spinner.showSpinner();
    this.maintainJoSearchData['action'] = 'maintainJoSearch';
    if (this.componentType == 'jomaintenance') {
      this.maintainJoSearchData.maintainJoParam['componentType']='JOMAINTENANCE'
    }
    this.maintainJoSearchData.maintainJoParam['pageNo']  = 1;
    this.maintainJoSearchData.maintainJoParam['requestChanged']  = true;
    this.maintainJoSearchData.maintainJoParam['noOfRecPerPage']  = 10;
    this.maintainJoSearchData.maintainJoParam['checkBoxJoCreation'] = this.checkBoxJoCreation; 
    
    let searchObj = this.maintainJoSearchData;



    let backendData = this._maintenanceService.getMaintenanceData(searchObj);
    backendData.subscribe(
      (data) => {
        if(data == "userSessionExpired"){
          this._sessionTimeOutService.checkSessionTimeout(data);
        }
        else if (data.hasOwnProperty("errorCode")) {
          this.errorCodetext = this._serverErrorCode.checkError(data["errorCode"]);
          this.errormessage = false;
          if(this.errorCodetext == undefined){
            this.errorCodetext = "There is an issue on DB connection,  Please try again.";
          }
          //UIkit.modal('#maintainjo-error-code-modal').show();
        }
        else {
          this.tableDataMaintenanceJoSearch1 = data;

          let searchInputOutPutData = {
            "filterInputData": this.filterDataSelectedComp,
            "searchInputData": this.maintainJoSearchData,
            //"searchOutputData": this._sortTable.sortTableData(this.tableDataMaintenanceJoSearch1, this.filterDataSelectedComp.sortIn, this.filterDataSelectedComp.orderBy),
            "searchOutputData": this._sortTable.sortTableData(this.tableDataMaintenanceJoSearch1.searchResult.result, this.filterDataSelectedComp.sortIn, this.filterDataSelectedComp.orderBy),
            "jobOrdTypeText": this.jobOrdTypeText,
            "jobOrdStsText": this.jobOrdStsText,
            "totalRecords": this.tableDataMaintenanceJoSearch1.totalRecords,
            "checkSocCocType": this.maintainJoSearchData.maintainJoParam.SocOrCoc
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
        this.errorCodetext = "There is an issue on DB connection,  Please try again.";
        this.errormessage = false;
        //UIkit.modal('#maintainjo-error-code-modal').show();
      }
    )
  }

  getLookupValues(e){    
    this.maintainJoSearchData.maintainJoParam['serviceVal'] = e.serviceValue;
    this.maintainJoSearchData.maintainJoParam['vesselVal'] = e.vesselValue;
    this.maintainJoSearchData.maintainJoParam['voyageVal'] = e.voyageValue;
  }

  getSVVValue(e){
    if(e.placeholder == "Service"){
      this.maintainJoSearchData.maintainJoParam['serviceVal']  = e.serviceValue;
    } else if(e.placeholder == "Vessel"){
      this.maintainJoSearchData.maintainJoParam['vesselVal'] = e.vesselValue;
    } else if(e.placeholder == "Voyage"){
      this.maintainJoSearchData.maintainJoParam['voyageVal'] = e.voyageValue;
    }     
  }

  getFilterCriteria(){
    if(this.filterDataSelectedComp.sortIn=="JoNumber"){
      this.filterDataSelectedComp.sortInText="Job Order";
      this.maintainJoSearchData.maintainJoParam['orderBy'] = "jo_number";
    }
    if(this.filterDataSelectedComp.sortIn=="orderDate"){
      this.filterDataSelectedComp.sortInText="Order Date";
      this.maintainJoSearchData.maintainJoParam['orderBy'] = "order_date";
    }
    if(this.filterDataSelectedComp.sortIn=="approveDate"){
      this.filterDataSelectedComp.sortInText="Approve Date";
      this.maintainJoSearchData.maintainJoParam['orderBy'] = "approve_date";
    }
    if(this.filterDataSelectedComp.sortIn=="vendorID"){
      this.filterDataSelectedComp.sortInText="Vendor Code";
      this.maintainJoSearchData.maintainJoParam['orderBy'] = "vendor_id";
    }
    if(this.filterDataSelectedComp.sortIn=="vendorName"){
      this.filterDataSelectedComp.sortInText="Vendor Name";
      this.maintainJoSearchData.maintainJoParam['orderBy'] = "vendor_name";
    }
    if(this.filterDataSelectedComp.sortIn=="amountNum"){
      this.filterDataSelectedComp.sortInText="Amount";
      this.maintainJoSearchData.maintainJoParam['orderBy'] = "jo_cost";
    }   
    if(this.filterDataSelectedComp.orderBy=="asnd"){
      this.filterDataSelectedComp.orderByText="Ascending";
      this.maintainJoSearchData.maintainJoParam['orderType'] = "ASC";
    } 
    if(this.filterDataSelectedComp.orderBy=="dsnd"){
      this.filterDataSelectedComp.orderByText="Descending";
      this.maintainJoSearchData.maintainJoParam['orderType'] = "DESC";
    } 
    
  }


  // private summary() {
           
  //   let findButton = document.getElementById('summary-btn');
  //   findButton.innerHTML = '<i class="fa fa-search"></i> <span>&nbsp;LOADING...</span>'
  //   this.getBackEndDataForSummary(findButton);

    
  //   }

//   summary()
// {
//   UIkit.modal('#requiredFields-warning-modal').show();

//  let summaryObj = this.joMaintananceSummary;

//   let backendData = this._maintenanceService.getMaintenanceDataSummary(summaryObj);
//     backendData.subscribe(
//       (data) => {
        
//         this.spinner.hideSpinner();
        
//       }
      
//     );

// }

}
