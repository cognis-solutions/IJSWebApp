/* --------------------------------------------------------
contract-search.component.ts
--------------------------------------------------------
Copyright RCL Public Co., Ltd. 2007
--------------------------------------------------------
Author NIIT 5/10/17
- Change Log -------------------------------------------
## DD/MM/YY -User- -TaskRef- -ShortDescription-
01 31/10/07 PIE Added checking validation
02 24/11/07 MNW BUG.934 Added support for relative numbers
03 28/11/07 SPD BUG.123 Changed to support enhanced features
-------------------------------------------------------- */
import { Component, OnInit, ViewChild, ChangeDetectorRef } from '@angular/core';
import { trigger, state, style, animate, transition, keyframes } from '@angular/animations';
import { NgbModal, NgbModalOptions } from "@ng-bootstrap/ng-bootstrap";
import { SpinnerServiceService } from "../../common-services/spinner-service.service";
import { ContractSearchService } from "./contract-search.service";
import { ServerErrorcodeService } from "../../common-services/server-errorcode.service";
import { SortSearchTableService } from "./sort-search-table.service";
import { HeaderGroupComponent } from './header-group/header-group.component';
import { WindowRefService } from "../../common-services/window-ref.service";
import { UserTypeService } from "../../user/user-type.service";
import { UserService } from "../../user/user.service";
declare var jQuery: any;
declare var UIkit: any;
import * as $ from 'jquery';
import { RclappUrlService } from "../../common-services/rclapp-url.service"
import { LookUpdataServiceService } from '../../common-services/look-updata-service.service';
import * as FileSaver from 'file-saver';
import { SessionTimeOutService } from "app/common-services/session-time-out.service";
import { NewContractComponent } from "./new-contract/new-contract.component";

@Component({
  selector: 'app-contract-search',
  templateUrl: './contract-search.component.html',
  styleUrls: ['./contract-search.component.scss'],
  providers: [HeaderGroupComponent]
})
export class ContractSearchComponent implements OnInit {
  //
  errorText = "New errror message is display here."
  simpleContSearchData: any = {
    contractParam: {
      searchScreenParam: {}
    },
    contract: {},
    action: "search"
  };
  
  private selectAllCheckBox: any; selectAllContract
  contractSearchData: any;
  private userType: string;
  baseUrl: any;
  hideResults = false; //to show and hide he rsults and switch over filterseach view  
  tableDataContractSearch = [];
  tableDataContractSearch1 = []; //for showing the data of result table
  contractshowfilter: boolean = false; //to show and hide filter
  pageNumber = 1; //for pagination 
  totalPages: number;
  resultsPerPage: number = 5;
  resultsPerPageArr = [1, 2, 3, 4, 5, 6, 7, 8, 9, 10];
  errorCodetext: string;
  errorCodeShow: boolean = false;
  validationTextFlag: boolean = false;
  validationText: string;
  showNewContractPage: boolean = false;
  simpleSearchTab: boolean = true;
  costBillTableFlag: boolean = false;
  costBillTableData: any = [];
  resultDataROwSelected: any;
  showContarctHistoryFlag: boolean = false
  showContarctVendorDetailsFlag: boolean = false;
  numberOFRowSelected = 0;
  selectedContractArray = [];
  dateRange: any;
  pickupAndDropoffLoc: any;
  addEditCostRateComponentFlag: boolean = true;
  p: number = 1;
  locationPickUpAndDropOff: string;
  locselectName: string;
  locationCodePickUp: string; //Pick up location
  locationCodeDropOff: string;  //Drop off location 
  advanceLocPickupTpye: string; //pickup Loaction type
  advanceLocDropOffType: string; //Dropof Loaction type
  _value: string;
  showlookuptable: boolean = true;
  showLocErrorText: boolean = false;
  errorTextLookUp: string
  locLookUptableData: any = [];
  //sorting of look up data variable 
  looUpOrderBy: any;
  lookupSortIn: any;
  lookupFilterBy: any;     //filter
  lookupWildCard: boolean;
  lookupErrorCodetext: any;
  lookupErrorCodeShow: boolean = false;
  activeTab: string = "tab-advanceSearch";
  compareSearchData: any = {};
  showCompareFlag: boolean = false;
  selectedContractRowArray = [];
  userFsc: string;
  currentPage: number = 1;
  advanceLocOptions = [
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
      label: 'Haulage Location',
      value: 'Haulage'
    }
  ];

  //for purchase term
  purchaseSelectConfig: any = {
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

  //LookUp Search Options
  doorLookUpOption = [
    {
      label: 'Point Code',
      value: 'POINT_CODE'
    },
    {
      label: 'Point Name',
      value: 'POINT_NAME'
    },
    {
      label: 'Country Code',
      value: 'COUNTRY_CODE'
    },
    {
      label: 'Zone Code',
      value: 'ZONE_CODE'
    },
    {
      label: 'State Code',
      value: 'STATE_CODE'
    },
    {
      label: 'Status',
      value: 'RECORD_STATUS'
    }
  ]
  terminalLookUpOption = [
    {
      label: 'Terminal',
      value: 'TQTERM'
    },
    {
      label: 'Terminal Name',
      value: 'TQTRNM'
    },
    {
      label: 'Port',
      value: 'TQPORT'
    },
    {
      label: 'FSC',
      value: 'TQOFFC'
    },
    {
      label: 'Status',
      value: 'TQRCST'
    }
  ]
  depotLookUpOption = [
    {
      label: 'Depot Code',
      value: 'TQTERM'
    },
    {
      label: 'Depot Name',
      value: 'TQTRNM'
    },
    {
      label: 'Depot Port',
      value: 'TQPORT'
    },
    {
      label: 'Point Code',
      value: 'POINT_CODE'
    },
    {
      label: 'Status',
      value: 'TQRCST'
    }
  ]
  haulageLookUpOption = [
    {
      label: 'Haulage Location',
      value: 'HAULAGE_LOCATION_CODE'
    },
    {
      label: 'Inland Point',
      value: 'INLAND_POINT'
    },
    {
      label: 'Haulage Location Name',
      value: 'HAULAGE_LOCATION_NAME'
    },
    {
      label: 'Status',
      value: 'RECORD_STATUS'
    }
  ]


  filterData: any = [
    // { name: "Lock Type", type: "radio", data: [{ id: "Door", text: "Door" }, { id: "Terminal", text: "Terminal" }, { id: "Depot", text: "Depot" }, { id: "Haulage", text: "Haulage Loc." }] },
    { name: "Sort in", type: "radio", data: [{ id: "vendorCode", text: "Vendor id" }, { id: "vendorName", text: "Vendor name" }, { id: "contractId", text: "Contract id" }, { id: "startDate", text: "Start date" }, { id: "priority", text: "Priority" }] },
    { name: "Order by", type: "radio", data: [{ id: "asnd", text: "Ascending" }, { id: "dsnd", text: "Descending" }, { id: "hp", text: "High Priority" }, { id: "lp", text: "Low Priority" }] },
    { name: "Filter by", type: "radio", data: [{ id: "MT", text: "Only Empty Rate" }, { id: "LADEN", text: "Only Laden Rate" },] },
    // { name: "Status", type: "radio", data: [{ id: "Active", text: "Active" }, { id: "Suspend", text: "Suspend" }] }
  ]; //this is the data to be implemented in filter 

  //filter data selected
  filterDataSelectedComp: any = {
    "filterDataArr": []
  };

  countryLookUpData: any = [{ "label": "Country", "value": "Country", "dropDownData": [{ "label": "Country", "value": "SCCODE" }, { "label": "Country Name", "value": "SCNAME" }, { "label": "Status", "value": "SCRCST" }] }]
  vendorLookUpData: any = [{ "label": "Vendor", "value": "Vendor", "dropDownData": [{ "label": "Vendor", "value": "VCVNCD" }, { "label": "Vendor Name", "value": "VCVDNM" }, { "label": "Vendor Type", "value": "VCVDTY" }, { "label": "City", "value": "VCCITY" }, { "label": "Country", "value": "VCCNTY" }, { "label": "State", "value": "VCSTAT" }] }, { "label": "Vendor Name", "value": "Vendor" }]

  //config variable for first select dropdown
  custTypeSelectConfig: any = {
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
      value: 'active',
    },
    {
      label: 'Suspend',
      value: 'suspend',
    },
    {
      label: 'Pending',
      value: 'Pending'
    },
    {
      label: 'Entry',
      value: 'Entry'
    }
  ]
  userData: any;
  editNewcontractData = {};

  @ViewChild('contractHistory') _contractHistory: any;
  @ViewChild('criteriaSelectCustType') _selectizeInput2: any;
  @ViewChild('contarctSearchFilter') _contarctSearchFilter: any;
  @ViewChild('advanceSerchTab') _advanceSerchTab: any;
  @ViewChild('costBillTableRef') _costBillTableRef: any;
  @ViewChild('newAndEditContract') _newAndEditContract: any;
  @ViewChild('vendorDetailsModal') _vendorDetailsModal: any;
  @ViewChild('showCompareModal') _showCompareModal: any;
  @ViewChild('tabset') _tabset: any;
  @ViewChild('addEditCostRateComponent') _addEditCostRateComponent: any;
  @ViewChild('contractPPTDHLookup') _contractPPTDHLookup: any;

  constructor(public cdRef: ChangeDetectorRef, public spinner: SpinnerServiceService, public _joborderService: ContractSearchService, public _serverErrorCode: ServerErrorcodeService, public _sortTable: SortSearchTableService, private _userTypeService: UserTypeService, private _rclappUrlService: RclappUrlService, private _lookupData: LookUpdataServiceService, private _userService: UserService,private _sessionTimeOutService:SessionTimeOutService,  public _newContractService: ContractSearchService) {

    console.log(this.simpleContSearchData);
   
  }

  ngAfterViewChecked() {
    this.cdRef.detectChanges();
  }
  ngAfterViewInit() {
    this.userType = this._userTypeService.getValue();
    if (!this.userType) {
      this.userType = "modifyOnly"
    }
    this.userData = this._userService.startupData();
    this.userFsc = this.userData.fscCode;    
  }
  userAuthType: string;
  fscCurr: string;
  //Show New Contract Compoenet

  newContractAction: string;
  showHideNewContract() {
    this.newContractAction = "New";
    this.showNewContractPage = true;
    this.errormessage = true;
    this.serverErrormessage = true;
  }
 

  addRow() {
    // let rowObj = {
    //   "oogSetupCode": undefined,
    //   "minOverHeight": undefined,
    //   "maxOverHeight": undefined,
    //   "minOverLength": undefined,
    //   "maxOverLength": undefined,
    //   "minOverWidth": undefined,
    //   "maxOverWidth": undefined
    // }
    // if (this.oogSetUpList.length < 2) {
    //   this.oogSetUpList.push(rowObj);
    // } else {
    //   this.errorText = "Maximum two row can added."
    // }
    this.errorText = "New errror message is display here."
    // }
  }
  //Show Edit Contract Component
  showEditContract(rowObj, e) {
    this.editNewcontractData = rowObj;
    this.newContractAction = "Edit";
    this.showNewContractPage = true;
    
  }

  //Show Copy Contract Compoenet
  showCopyContract(rowObj, e) {
    this.newContractAction = "Copy";
    this.editNewcontractData = rowObj;   
    this.editNewcontractData['status'] = "Entry"; //for showing default status as Entry on copy
    this.showNewContractPage = true;
  }

  //Show Edit Contract Component from panel button
  showEditContractPanelButton(e) {
    var rowObj = this.resultDataROwSelected;
    this.showEditContract(rowObj, e);
  }

  //Show Edit Contract Component from panel button
  showCopyContractPanelButton(e) {
    var rowObj = this.resultDataROwSelected;
    this.showCopyContract(rowObj, e);


  }

  //Contract search tabs changes
  jobSearchTabChange(e) {
    if (this.activeTab == "tab-simpleSearch")
      this.simpleSearchTab = true;
    else if (this.activeTab == "tab-advanceSearch")
      this.simpleSearchTab = false;
    this.activeTab = this._tabset.activeId;
  }

  //showhide filter
  showhideFilter(e) {
    if (this.contractshowfilter) {
      this.contractshowfilter = false;
      document.getElementById("filterButton").classList.remove("filterButtonClicked");
    }
    else {
      this.contractshowfilter = true;
      document.getElementById("filterButton").classList.add("filterButtonClicked");
    }
  }

  //filter data from filter comoponent
  filterDataSelected(filterDataIncompo) {
    this.filterDataSelectedComp = {};
    this.filterDataSelectedComp = this._contarctSearchFilter.filterData;
  }

  //findJobOrder to find data from backend
  searchJobOrder(element) {
    if(this.filterDataSelectedComp.orderBy == undefined){
      this.filterDataSelectedComp.orderBy = "asnd";
    }    
    console.log(this.filterDataSelectedComp);
    this.getFilterCriteria();//to get filter criteria
    //this.filterDataSelectedComp.orderBy = "asnd";
    var findButton = document.getElementById('find-button');
    findButton.innerHTML = '<i class="fa fa-search"></i> <span>&nbsp;LOADING...</span>'
    document.getElementById("filterButton").classList.remove("filterButtonClicked");
    this.contractshowfilter = false;
    this.errorCodeShow = false;
    this.validationText = this.formValidationSimpleSearch();

    if (this.validationText == "true") {
      this.validationTextFlag = false;
      this.getBackEndData(findButton);
    } else {
      this.validationTextFlag = true;
      findButton.innerHTML = '<i class="fa fa-search"></i> <span>&nbsp;FIND</span>'
    }

  }

  searchJobOrderForDelete(element) {
    var findButton = document.getElementById('find-button');
    findButton.innerHTML = '<i class="fa fa-search"></i> <span>&nbsp;LOADING...</span>'
    document.getElementById("filterButton").classList.remove("filterButtonClicked");
    this.contractshowfilter = false;
    this.errorCodeShow = false;
    this.validationText = this.formValidationSimpleSearch();

    if (this.validationText == "true") {
      this.validationTextFlag = false;
      this.getBackEndDataAfterDelete(findButton);
    } else {
      this.validationTextFlag = true;
      findButton.innerHTML = '<i class="fa fa-search"></i> <span>&nbsp;FIND</span>'
    }
  }

  getBackEndDataAfterDelete(findButton) {
    this.spinner.showSpinner();
    var backendData = this._joborderService.getData(this.simpleContSearchData);
    backendData.subscribe(
      (data) => {
        if(data == "userSessionExpired"){
          this._sessionTimeOutService.checkSessionTimeout(data);
        }
        else if (data.hasOwnProperty("errorCode")) {
          if (data["errorCode"] = "IJS_EX_10001") {
            this.changeFind();
          }
          else {
            this.errorCodetext = this._serverErrorCode.checkError(data["errorCode"]);
            UIkit.modal('#errorCodeShow-Modal').show();
          }
        }
        else {
          this.tableDataContractSearch1 = data;
          this.addRemoveSortFilter(this.filterDataSelectedComp, this.tableDataContractSearch1);
          this.calCPages();
          this.hideResults = true;
        }
        this.selectedContractArray = [];
        this.numberOFRowSelected = 0;
        this.selectedRow = undefined;
        this.selectAllCheckBox.checked = false;
        this.spinner.hideSpinner();
        if (findButton !== undefined)
          findButton.innerHTML = '<i class="fa fa-search"></i> <span>&nbsp;FIND</span>'
      },
      (err) => {
        this.spinner.hideSpinner();
        // A client-side or network error occurred. Handle it accordingly.
        findButton.innerHTML = '<i class="fa fa-search"></i> <span>&nbsp;FIND</span>'
        this.serverErrorCodetext = "There is DB connection issue.Please try again.";
        UIkit.modal('#serverErrorCodeModal').show();
      }
    )
  }

  //cross button click in filter to get data from backend
  searchChnageJobData(simpleSearch,propName) {

    if (this.simpleContSearchData.contractParam.searchScreenParam.hasOwnProperty(propName)) {
      if (this.simpleContSearchData.contractParam.searchScreenParam['propName'] == 'countryCode') {
        delete this.simpleContSearchData.contractParam.searchScreenParam.countryCode;        
      } else if(this.simpleContSearchData.contractParam.searchScreenParam['propName'] == 'vendorCode'){
        delete this.simpleContSearchData.contractParam.searchScreenParam.vendorCode;
      } else if(this.simpleContSearchData.contractParam.searchScreenParam['propName'] == 'contractNumber'){
        delete this.simpleContSearchData.contractParam.searchScreenParam.contractNumber;
      } else if(this.simpleContSearchData.contractParam.searchScreenParam['propName'] == 'priority'){
        delete this.simpleContSearchData.contractParam.searchScreenParam.priority;
      } else if(this.simpleContSearchData.contractParam.searchScreenParam['propName'] == 'status'){
        delete this.simpleContSearchData.contractParam.searchScreenParam.status;
      } else if (this.simpleContSearchData.contractParam.searchScreenParam['propName'] == 'fromLocType') {
        delete this.simpleContSearchData.contractParam.searchScreenParam.fromLocType;
        delete this.simpleContSearchData.contractParam.searchScreenParam.fromLocation;
        delete this.simpleContSearchData.contractParam.searchScreenParam.fromTerminal;
      } else if(this.simpleContSearchData.contractParam.searchScreenParam['propName'] == 'fromLocation'){
        delete this.simpleContSearchData.contractParam.searchScreenParam.fromLocation;
      } else if(this.simpleContSearchData.contractParam.searchScreenParam['propName'] == 'fromTerminal'){
        delete this.simpleContSearchData.contractParam.searchScreenParam.fromTerminal;
      } else if (this.simpleContSearchData.contractParam.searchScreenParam['propName'] == 'toLocType') {
        delete this.simpleContSearchData.contractParam.searchScreenParam.toLocType;
        delete this.simpleContSearchData.contractParam.searchScreenParam.toLocation;
        delete this.simpleContSearchData.contractParam.searchScreenParam.toTerminal;
      } else if(this.simpleContSearchData.contractParam.searchScreenParam['propName'] == 'toLocation'){
        delete this.simpleContSearchData.contractParam.searchScreenParam.toLocation;
      } else if(this.simpleContSearchData.contractParam.searchScreenParam['propName'] == 'toTerminal'){
        delete this.simpleContSearchData.contractParam.searchScreenParam.toTerminal;
      }
      // else {
      //   delete this.simpleContSearchData.contractParam.searchScreenParam[propName];
      // }
    } else {
      delete this.simpleContSearchData.contractParam.dateRange;
    }

    this._selectizeInput2.valueAccessor.resetSelectSize();
    if (this.formValidationSimpleSearch() == "true") {
      this.getBackEndData(undefined);
    } else {
      UIkit.modal('#changeSearch-Modal').show();
    }

  }
  errormessage:boolean= true;
  serverErrormessage:boolean= true;

  //Get the search data from backend service 
  serverErrorCodetext: string;
  getBackEndData(findButton) {
    this.spinner.showSpinner();
    this.currentPage = 1;
    this.simpleContSearchData.action='search';
    this.serverErrormessage = true;
    var backendData = this._joborderService.getData(this.simpleContSearchData);
    backendData.subscribe(
      (data) => {
        if(data == "userSessionExpired"){
          this._sessionTimeOutService.checkSessionTimeout(data);
        }
        else if (data.hasOwnProperty("errorCode")) {
          this.errorCodetext = this._serverErrorCode.checkError(data["errorCode"]);
         // UIkit.modal('#errorCodeShow-Modal').show();
         this.errormessage = false;
         this.cdRef.markForCheck();
        }
        else {
          this.costBillTableFlag = false; //#NIIT CR4 >>>> to hide cost-bill rates when contract is edited and data is refreshed
          this.tableDataContractSearch1 = data;
          
          this.addRemoveSortFilter(this.filterDataSelectedComp, this.tableDataContractSearch1);
          //console.log(this.tableDataContractSearch1);
          this.calCPages();
          this.errormessage = true;
          this.hideResults = true;
          this.cdRef.markForCheck();
        }
        this.selectedContractArray = [];
        this.numberOFRowSelected = 0;
        this.selectedRow = undefined;
        this.selectAllCheckBox.checked = false;
        this.spinner.hideSpinner();
        if (findButton !== undefined)
          findButton.innerHTML = '<i class="fa fa-search"></i> <span>&nbsp;FIND</span>'
      },
      (err) => {
        this.spinner.hideSpinner();
        // A client-side or network error occurred. Handle it accordingly.
        findButton.innerHTML = '<i class="fa fa-search"></i> <span>&nbsp;FIND</span>'
        this.serverErrormessage = false;
        //this.errormessage = true;
        this.serverErrorCodetext = "There is an issue on DB connection,  Please try again.";
        this.cdRef.markForCheck();
       // UIkit.modal('#serverErrorCodeModal').show();
      }
    )
  }

  //check form validation
  formValidationSimpleSearch() {
    let transtype = this.simpleContSearchData.contractParam.transMode;
    let vCode = this.simpleContSearchData.contractParam.searchScreenParam.vendorCode;
    let cNumber = this.simpleContSearchData.contractParam.searchScreenParam.contractNumber;
    let cCode = this.simpleContSearchData.contractParam.searchScreenParam.countryCode;
    let prty = this.simpleContSearchData.contractParam.searchScreenParam.priority;
    let status = this.simpleContSearchData.contractParam.searchScreenParam.status;
    let datevalues = this.simpleContSearchData.contractParam.dateRange;
    let fromLoc = this.simpleContSearchData.contractParam.searchScreenParam.fromLocType;
    let toLoc = this.simpleContSearchData.contractParam.searchScreenParam.toLocType;
    let purchaseTerm = this.simpleContSearchData.contractParam.searchScreenParam.purchaseTerm;
    console.log(this.filterDataSelectedComp.filterBy);
    if("MT" === this.filterDataSelectedComp.filterBy) {
      this.simpleContSearchData.contractParam.searchScreenParam.filterBy = this.filterData[2].data[0].id
    }
    else if("LADEN" === this.filterDataSelectedComp.filterBy) {
      this.simpleContSearchData.contractParam.searchScreenParam.filterBy = this.filterData[2].data[1].id;
    }
    else {
      this.simpleContSearchData.contractParam.searchScreenParam.filterBy = null;
    }

      console.log(this.simpleContSearchData);

    if (transtype == undefined && vCode == undefined && cNumber == undefined && cCode == undefined && prty == undefined && status == undefined && datevalues == undefined && fromLoc == undefined && toLoc == undefined && purchaseTerm == undefined) {
      
      return "Please provide atleast one input field";

    } else {
      return "true";
    }
  }

  //to calculate number of buttons in
  calCPages() {
    if (this.tableDataContractSearch.length % this.resultsPerPage == 0) {
      this.totalPages = this.tableDataContractSearch.length / this.resultsPerPage;
    }
    else {
      this.totalPages = Math.floor(this.tableDataContractSearch.length / this.resultsPerPage) + 1;
    }
  }

  //code for filter the data od the screen
  addRemoveSortFilter(filterDataSelectedComp, tableDataContractSearch1) {
    this.tableDataContractSearch = tableDataContractSearch1;
    
    if (filterDataSelectedComp !== undefined) {
      if (filterDataSelectedComp.filterDataArr.length > 0) {
        for (var index = 0; index < filterDataSelectedComp.filterDataArr.length; index++) {
          if (filterDataSelectedComp.filterDataArr[index].hasOwnProperty("Lock Type")) {
            this.tableDataContractSearch = this.tableDataContractSearch.filter((obj) => {
              return (filterDataSelectedComp.filterDataArr[index]['Lock Type'] == obj.fromLocType) || (filterDataSelectedComp.filterDataArr[index]['Lock Type'] == obj.toLocType);
            });
          }
          if (filterDataSelectedComp.filterDataArr[index].hasOwnProperty("Status")) {
            this.tableDataContractSearch = this.tableDataContractSearch.filter((obj) => {
              return (filterDataSelectedComp.filterDataArr[index]['Status']) == (obj.status);
            });
          }
        }
      }
      this.tableDataContractSearch = this._sortTable.sortTableData(this.tableDataContractSearch, filterDataSelectedComp.sortIn, filterDataSelectedComp.orderBy);
      //sort on the basis of priority
      this.tableDataContractSearch.sort(function(a, b) {
        return a.priority - b.priority});
      console.log(this.tableDataContractSearch);
    }
  }

  //reset simple serch section
  resetSearch() {
    this.errormessage = true;
    this.serverErrormessage=true;
    this._selectizeInput2.valueAccessor.resetSelectSize();
    this.simpleContSearchData = {
      contractParam: {
        searchScreenParam: {}
      },
      contract: {},
      action: "search"
    };
    this.validationTextFlag = false;
  }

  //remove filter criteriea using bubbles
  removeFilter(filterObj) {
    if (typeof filterObj === "string") {
      if (filterObj === this.filterDataSelectedComp.sortIn) {
        delete this.filterDataSelectedComp.sortIn;
      }
      if (filterObj === this.filterDataSelectedComp.orderBy) {
        delete this.filterDataSelectedComp.orderBy;
      }
  //remove filter-By criteriea using bubbles    
      if (filterObj === this.filterDataSelectedComp.filterBy) {
        delete this.filterDataSelectedComp.filterBy;
      }
    }
    if (typeof filterObj === "object") {
      for (var index = 0; index < this.filterDataSelectedComp.filterDataArr.length; index++) {
        if (Object.getOwnPropertyNames(this.filterDataSelectedComp.filterDataArr[index])[0] == Object.getOwnPropertyNames(filterObj)[0]) {
          this.filterDataSelectedComp.filterDataArr.splice(index, 1);
        }
      }
    }
    this.addRemoveSortFilter(this.filterDataSelectedComp, this.tableDataContractSearch1);
  }

  //Chnage button changes view to Simple or Advance search view
  changeFind() {    
    this.checkedSelectedRows = [];
    this.p = null || undefined;
    this.resultsPerPage = 5;
    this.hideResults = false;
    this.errorCodeShow = false;
    this.filterDataSelectedComp = {};
    this.filterDataSelectedComp.filterDataArr = [];
    this.numberOFRowSelected = 0;
    this.costBillTableFlag = false;
    this.p = 1;
    this.checkRowSelected = false;

  }

  onChangeOrderBy(orderBy) {
    this.getFilterCriteria();
    this._sortTable.sortTableData(this.tableDataContractSearch, this.filterDataSelectedComp.sortIn, orderBy)
  }

  onChangePageNumber(e) {
    this.calCPages();
  }

  selected:boolean;
  selectTableRow(e, rowObj,i) {
    this.checkedSelectedRows = [];//#NIIT CR2
    this.numberOFRowSelected = 0;
    //#NIIT CR2  >>>>>BEGIN
    let selected = rowObj.selected;
    //method call to deselect all the checked contracts if any when a contract is clicked
    this.deselectAll(this.tableDataContractSearch);
    //if user selected contract by clicking checkbox
    // then make selected false when a contract is clicked so that check state is maintained
    if(rowObj.clickCheckBox == true || selected == true){
       selected = false;
    }
    rowObj.selected = !selected;
    rowObj['checked'] = rowObj.selected;   
    this.numberOFRowSelected++;
    this.checkedSelectedRows.push(rowObj);
    this.resultDataROwSelected = rowObj; //to display data when contract is selected and edit is clicked 
    this.selectedContractRowArray.push(rowObj);
    this.selectedContractArray.push(rowObj.contractId);
    //#NIIT CR2 END<<<<<<<<
    this.selectedRow = rowObj;
    this.selectedRowOnClick = rowObj;
    this.checkExemptedFlag = false;
    this.showCostRateTable(e, rowObj);//method call to show cost table when a contract is clicked
    

  }

   //to deselect all the checked contracts if any, when a contract is clicked
   //#NIIT CR2  >>>>>BEGIN
   deselectAll(arr: any[]){
    arr.forEach(val =>{
    if(val.selected){
        val.selected = false;
        val.checked = false;
    }})
  }
  //#NIIT CR2 END<<<<<<<<

  checkRowSelected: boolean = false;
  //Functionlity When check resultTable row  
  selectedRow: any;
  selectedRowOnClick:any;
  checkedSelectedRows: any = [];
  selectTableRowCheckBoxes(e, rowObj) {
    this.checkRowSelected = e.target.checked;    
    if (e.target.checked) {
      this.numberOFRowSelected++;
      //#NIIT CR2
      let selected = rowObj.selected;
      rowObj['clickCheckBox'] = true;
      rowObj.selected = !selected;
      rowObj['checked'] = rowObj.selected;
      //#NIIT CR2     
      this.resultDataROwSelected = rowObj;
      this.checkedSelectedRows.push(rowObj);     
      this.selectedContractRowArray.push(rowObj);
      this.selectedContractArray.push(rowObj.contractId);
      this.selectedRow = rowObj; 
    } else {
      this.numberOFRowSelected--;
      if(rowObj.checked == false){
        rowObj.selected = false;
      }
      this.checkedSelectedRows = this.deleteObjByContractId(this.checkedSelectedRows, 'routingId', rowObj.routingId);
      this.selectedRow = undefined;
      // this.selectedRowOnClick = undefined;
      const index: number = this.selectedContractArray.indexOf(rowObj.contractId);
      if (index !== -1) {
        this.selectedContractArray.splice(index, 1);
      }

      if (this.selectAllCheckBox.checked == true) {
        this.selectAllCheckBox.checked = false;
      }

    }
  }

  //delete element from array
  deleteObjByContractId(arr, attr, value) {
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


  checkExemptedFlag: boolean;
  //Show Cost and Rate Table For highlighted Row
  showCostRateTable(e, rowObj) {
    this.costBillTableFlag = false;
    if(rowObj.exempted == true){
      this.checkExemptedFlag = true;
    }
    this.getBackEndDataCostRateTable(rowObj);
    jQuery(e.target).closest('.card .slidein-from-top').closest('.data-card-block-container').children().removeClass('selectedRow');
    jQuery(e.target).closest('.card .slidein-from-top').addClass('selectedRow');

  }

  termCodesList: any[] = [];
  eqTypeList: any[] = [];
  costRowData: any = [];
  billRowData: any[] = [];
  oogCodeList:any[]=[];  //#NIIT CR4
  portCodeList:any[]=[];  //#NIIT CR4
  imdgCodeList:any[]=[];  //#NIIT CR4
  costExchangeRateError = []; //#NIIT CR4
  costRateErrorFlag:boolean = false; //#NIIT CR4
  getBackEndDataCostRateTable(rowObj) {
    this.costRateErrorFlag = false; //make exchange rate error flag false initially //#NIIT CR4
    this.costExchangeRateError = []; //make exchange rate array empty initially //#NIIT CR4
    this.spinner.showSpinner(); 
    //#NIIT CR4 >>>>BEGIN
    var backendData = this._joborderService.getCostRateTableData({"routingID": rowObj.routingId,"fsc":rowObj.paymentFsc,"rateType":"COST_RATE","terminalDepotCode":rowObj.fromTerminal});
    backendData.subscribe(
      (data) => {
        if(data == "userSessionExpired"){
          this._sessionTimeOutService.checkSessionTimeout(data);
        }
        else if (data.hasOwnProperty("errorCode")) {
          //this._costBillTableRef.refreshTable([], rowObj)
          //this._costBillTableRef.agGridRateFlag = true;          
          this.costBillTableFlag = false;
        } else {
          this.costBillTableFlag = true;
          this.costRowData = data["rateResults"]["results"];
          this.billRowData = data["billingRateResults"]["results"];
          this.cdRef.markForCheck();

          //this._costBillTableRef.refreshTable(data["rateResults"]["results"], rowObj);
          //this._costBillTableRef.billTableRowData = data["billingRateResults"]["results"];

          // if (data["termCodesList"]) {
          //   let termCodesListTemp = [];
          //   for (let i = 0; i < data["termCodesList"].length; i++) {
          //     termCodesListTemp.push({ label: data["termCodesList"][i]["termCode"], value: data["termCodesList"][i]["termCode"] });
          //   }
          //   //this._addEditCostRateComponent._mappingData.termOptions = termCodesListTemp;
          //   this.termCodesList = termCodesListTemp;
          // }
          if (data["eqTypeList"]) {
            let eqTypeListTemp = [];
            for (let i = 0; i < data["eqTypeList"].length; i++) {
              eqTypeListTemp.push({ label: data["eqTypeList"][i]["eqType"], value: data["eqTypeList"][i]["eqType"] });
            }
            // this._addEditCostRateComponent._mappingData.eQTypeOptions = eqTypeListTemp;
            this.eqTypeList = eqTypeListTemp;
          }

          //#NIIT CR4 >>>>BEGIN
          //Oog Code List
          if(data.costRateSetup != undefined && data.costRateSetup["oogCodeList"]){
            let oogCodeListTemp = [];
            for (let i = 0; i < data.costRateSetup.oogCodeList.length; i++) {
              oogCodeListTemp.push({ label: data.costRateSetup.oogCodeList[i], value: data.costRateSetup.oogCodeList[i] });
            }            
            this.oogCodeList = oogCodeListTemp;            
          }

          //port Code List
          if(data.costRateSetup != undefined && data.costRateSetup["portCodeList"]){
            let portCodeListTemp = [];
            for (let i = 0; i < data.costRateSetup.portCodeList.length; i++) {
              portCodeListTemp.push({ label: data.costRateSetup.portCodeList[i], value: data.costRateSetup.portCodeList[i] });
            }            
            this.portCodeList = portCodeListTemp;            
          }

          //imdg Code List
          if(data.costRateSetup != undefined && data.costRateSetup["imdgCodeList"]){
            let imdgCodeListTemp = [];
            for (let i = 0; i < data.costRateSetup.imdgCodeList.length; i++) {
              imdgCodeListTemp.push({ label: data.costRateSetup.imdgCodeList[i], value: data.costRateSetup.imdgCodeList[i] });
            }            
            this.imdgCodeList = imdgCodeListTemp;            
          }
          //#NIIT CR4 >>>>END


          //this._costBillTableRef.agGridRateFlag = false;
          jQuery("html, body").animate({ scrollTop: jQuery(document).height() }, 900);
        }
        this.selectAllCheckBox.checked = false;
        this.cdRef.markForCheck();
        this.spinner.hideSpinner();
		    //#NIIT CR4 BEGIN
        //to check no exchange rate is available
        this.costRowData.forEach((element)=>{

          if(element.exchangeError != undefined){
            this.costRateErrorFlag = true;
            this.costExchangeRateError.push({"currencyError" :element.exchangeError});
          }

        });
        
        //if exchnage rate error is present than show error
        if(this.costRateErrorFlag){
          // UIkit.modal('#cost-exchange-error').show();
          $('#cost-exchange-error').addClass('uk-open').show(); //#NIIT CR4 >>>>
        }
		    //#NIIT CR4 END
        

      },
      (err) => {
        this.spinner.hideSpinner();
        // A client-side or network error occurred. Handle it accordingly.        
        this.serverErrorCodetext = "There is an issue on DB connection, Please try again.";
        this.cdRef.markForCheck();
        UIkit.modal('#serverErrorCodeModal').show();
      });

  }

  //Show contact history popup  
  showContarctHistory(row, e) {
    this.showContarctHistoryFlag = true;

    if (e.target.className === "btn-table-tooltip" || e.target.className == "fa fa-question-circle") {
      this._contractHistory.showModal(row, e);
    } else {
      this.checkedSelectedRows[0];
      this._contractHistory.showModal(this.checkedSelectedRows[0], e);
    }
  }

  //Show Vendor Details popup  
  showVendorDetails(row, e) {
    this.showContarctVendorDetailsFlag = true;
    this._vendorDetailsModal.showModal(row, e);
  }

  //suspend the results
  suspendContractRecord(rowObj, e) {
    if (e.target.className === "btn-table-tooltip") {
      this.selectedContractArray = [];
      this.selectedContractArray.push(rowObj.contractId)
    } else { }
    var suspendData = { contractsList: this.selectedContractArray, action: "suspend" };
    this.spinner.showSpinner();
    var backendData = this._joborderService.getData(suspendData);
    backendData.subscribe(
      (data) => {
        if(data == "userSessionExpired"){
          this._sessionTimeOutService.checkSessionTimeout(data);
        }
        else if (data.hasOwnProperty("errorCode")) {
          this.errorCodetext = this._serverErrorCode.checkError(data["errorCode"]);
          UIkit.modal('#errorSuspendShow-Modal').show();
          //  this.errormessage = false;
        }
        else {
          this.refreshTableData();
        }
        this.spinner.hideSpinner();
        this.selectedContractArray = [];
        this.numberOFRowSelected = 0;
        this.selectedRow = undefined;
        this.selectAllCheckBox.checked = false;
      }
    )
  }

  closeWarning() {
    UIkit.modal('#delete-warning-modal').hide();
  }
  deleteTextMsg: string;

  deleteContractRecord(rowObj, e) {
    this.deleteTextMsg = "Do you want to delete";
    UIkit.modal('#delete-warning-modal').show();
  }
  //delete the results from table
  deleteContractRecordConfirm(rowObj, e) {
    UIkit.modal('#delete-warning-modal').hide();

    if (e.target.className === "btn-table-tooltip") {
      this.selectedContractArray = [];
      this.selectedContractArray.push(rowObj.contractId)
    }
    var deleteData = { contractsList: this.selectedContractArray, action: "delete" };

    this.spinner.showSpinner();
    var backendData = this._joborderService.getData(deleteData);

    backendData.subscribe(
      (data) => {
        if(data == "userSessionExpired"){
          this._sessionTimeOutService.checkSessionTimeout(data);
        }
        else if (data.hasOwnProperty("errorCode")) {
          
          this.errorCodetext = this._serverErrorCode.checkError(data["errorCode"]);
           UIkit.modal('#delSuccessShow-Modal').show();

        }
        else {
          this.errorCodetext = "Record Successfully Deleted.";
          UIkit.modal('#delSuccessShow-Modal').show();

          //this.refreshTableDelete();
        }
        this.spinner.hideSpinner();
        this.selectedContractArray = [];
        this.numberOFRowSelected = 0;
        this.selectedRow = undefined;
        this.selectAllCheckBox.checked = false;
      }
    )
  }

  //Compare contract 
  compareContractRecord(e) {
    this.showCompareFlag = true;
    this._showCompareModal.showModal(this.checkedSelectedRows[0]);
  }

  //refresh the results 
  refreshTableData() {
    this.searchJobOrder("");
    this.numberOFRowSelected = 0;
    this.selectedRow = undefined;
    this.selectAllCheckBox.checked = false;
  }

  refreshTableDelete() {
    this.searchJobOrderForDelete("");
    this.numberOFRowSelected = 0;
    this.selectedRow = undefined;
    this.selectAllCheckBox.checked = false;
  }

  //add edit cost row page show
  showAddEditNewCostRate(e) {
    this.addEditCostRateComponentFlag = false;
    this._addEditCostRateComponent.addEditCostRateObj.action = e.action;
    this._addEditCostRateComponent.addEditCostRateObj.routingNumber = e.routingNumber;
    this._addEditCostRateComponent.addEditCostRateObj.contractId = e.contractId;
    this._addEditCostRateComponent.addEditCostRateObj.ijsRateVO = this._addEditCostRateComponent._mappingData.dataMappingMethod(e.rowData);


    if (e.action == "editCostRate") {
      if (this._addEditCostRateComponent.addEditCostRateObj.ijsRateVO['eqType']) {
        this._addEditCostRateComponent.addEditCostRateObj.ijsRateVO['eqType'] = this._addEditCostRateComponent.addEditCostRateObj.ijsRateVO['eqType'].split(',');
      }
      if (this._addEditCostRateComponent.addEditCostRateObj.ijsRateVO['term']) {
        this._addEditCostRateComponent.addEditCostRateObj.ijsRateVO['term'] = this._addEditCostRateComponent.addEditCostRateObj.ijsRateVO['term'].split(',');
      }
      if (this._addEditCostRateComponent.addEditCostRateObj.ijsRateVO['perTrip'] == "true") {
        this._addEditCostRateComponent.addEditCostRateObj.ijsRateVO['perTrip'] = true;
      } else {
        this._addEditCostRateComponent.addEditCostRateObj.ijsRateVO['perTrip'] = false;
      }
      if (!this._addEditCostRateComponent.addEditCostRateObj.ijsRateVO['uom']) {
        this._addEditCostRateComponent.addEditCostRateObj.ijsRateVO['uom'] = 'K'
      }
      if (!this._addEditCostRateComponent.addEditCostRateObj.ijsRateVO['service']) {
        this._addEditCostRateComponent.addEditCostRateObj.ijsRateVO['service'] = "***"
      }
      if (!this._addEditCostRateComponent.addEditCostRateObj.ijsRateVO['vesselCodes']) {
        this._addEditCostRateComponent.addEditCostRateObj.ijsRateVO['vesselCodes'] = "***"
      }

      this._addEditCostRateComponent.addEditCostRateObj.ijsRateVO['currency'] = this.selectedRow.currency;
      this._addEditCostRateComponent.addEditLabel = "Edit";

    } else if (e.action == "addCostRate") {
      this._addEditCostRateComponent
        .addEditCostRateObj = {
          ijsRateVO: {
            mtOrLaden: "LADEN",
            rateBasis: "S",
            eqCatq: "B",
            rateStatus: "O",
            impOrExp: "ALL",
            splHandling: "N",
            eqType: "",
            oogSetup: "",
            service: "***",
            vesselCodes: "***",
            manualRate: true,
            uom: 'K',
            mot: e.rowData.mot,
            startDate: this.selectedRow.startDate,
            endDate: this.selectedRow.endDate,
            currency: this.selectedRow.currency
          },
          action: e.action,
          routingNumber: e.routingNumber
        };
      this._addEditCostRateComponent.addEditLabel = "Add";

    }
    jQuery("html, body").animate({ scrollTop: 0 }, "slow");
  }
  //add edit cost row page hide
  closeAddEditCost(e) {
    this.addEditCostRateComponentFlag = true;
    this._costBillTableRef.getRowdata();
  }

  //show file upload component
  fileUploadFlag: boolean = true;
  uploadExcelFile(e) {
    this.fileUploadFlag = false;    
  }

  downoadResultsExcelFile(e) {
    this.spinner.showSpinner();
    let downloadSearchData = this.simpleContSearchData;
    downloadSearchData["action"] = "contractDownload"
   
    this._joborderService.downloadContract(downloadSearchData)
    .subscribe(
      data => {
        FileSaver.saveAs(data, "IJS_CONTRACT_DOWNLOAD");
        this.spinner.hideSpinner();
        //window.open(window.URL.createObjectURL(data));
        //FileSaver.saveAs(data, "file");
      }
    );
	//window.open(searchResultDownloadUrl) //by NIIT
  }

  doGeneratePortPair() {
    let downloadSearchData = this.simpleContSearchData;
    downloadSearchData["action"] = "contractDownload"
   // let searchResultDownloadUrl = this._rclappUrlService.url + "/IJSWebApp/contractSearch.do?" + "data=" + JSON.stringify(downloadSearchData);
    //window.open(encodeURI(searchResultDownloadUrl)); //by RCL
    var backendData = this._joborderService.doGeneratePortPair();

    backendData.subscribe(
      (data) => {
        if(data == "userSessionExpired"){
          this._sessionTimeOutService.checkSessionTimeout(data);
        }
        else if (data.hasOwnProperty("errorCode")) {
          
          this.errorCodetext = this._serverErrorCode.checkError(data["errorCode"]) + ':'+ data["nextGPPTime"];
          this.spinner.hideSpinner();
          UIkit.modal('#genPortPairShow-Modal').show();

        }
        else {
          this.errorCodetext = "Generate Port Pair successful.";
          this.spinner.hideSpinner();
          UIkit.modal('#genPortPairShow-Modal').show();

          //this.refreshTableDelete();
        }
        //this.spinner.hideSpinner();
       
      }
    )
   
  }

  closeFileUpload(e) {
    this.fileUploadFlag = true;
  }

  //>>>>>>>>>>>>>>>>>>>>>>>>>>>>><<<<<<<<<<<<<<<<<<<<<<<<>>>>>>>>>>>>>>>>>>>>>>>//
  //-----------------------Merging Changes-------------------------------------//
  //>>>>>>>>>>>>>>>>>>>>>>>>>>>>><<<<<<<<<<<<<<<<<<<<<<<<>>>>>>>>>>>>>>>>>>>>>//


  //open the pop Up
  // openPickupDropoffPopUp() {
  //   UIkit.modal('#PickDropModal').show();
  // }
  dropOffDropDownChange(e) {
    this.simpleContSearchData.contractParam.searchScreenParam["toLocation"] = undefined;
    this.simpleContSearchData.contractParam.searchScreenParam["toTerminal"] = undefined;
  }
  pickUpDropDownChange(e) {
    this.simpleContSearchData.contractParam.searchScreenParam["fromLocation"] = undefined;
    this.simpleContSearchData.contractParam.searchScreenParam["fromTerminal"] = undefined;
  }

  // pick drop Chnage

  //open pickup lookup
  openFromLookup(selectedValue, fromOrTo) {
    this.locselectName = fromOrTo;
    this._contractPPTDHLookup.openFromLookup(selectedValue, fromOrTo);
    this.looUpOrderBy = "asnd";
  }
  //open to lookup modal
  private openToLookup(selectedValue, fromOrTo) {
    this.locselectName = fromOrTo;
    this._contractPPTDHLookup.openToLookup(selectedValue, fromOrTo);
    this.looUpOrderBy = "asnd";
  }

  //Toggle modals
  pickDropModalLookUpGoBack() {
    UIkit.modal('#PickDropModal', { bgclose: false }).show();
    UIkit.modal('#PickDropModalLookUp').hide();
    this._value = undefined
    this.lookupSortIn = undefined;
  }
  //destroy modal popups
  resetPickDropModal(e) {
    this.showlookuptable = true;
    this.locLookUptableData = [];
    this.advanceLocPickupTpye = undefined;
    this.advanceLocDropOffType = undefined;
    this.locationCodePickUp = undefined;
    this.locationCodeDropOff = undefined;
    this.lookupErrorCodetext = undefined;
    this.lookupErrorCodeShow = false;
    this._value = undefined;
    this.lookupSortIn = undefined;
    this.lookupWildCard = false;
  }


  getLocLookUpData(advanceLocPickupTpye, type, eve, inpuvaluevalue, wildCard) {
  
    this.spinner.showSpinner();
    eve.stopPropagation();
    eve.preventDefault();
    eve.stopImmediatePropagation();

    var backendData = this._lookupData.getDataLookupService(advanceLocPickupTpye, type, eve, inpuvaluevalue, wildCard);
    backendData.subscribe(
      (data) => {
        if(data == "userSessionExpired"){
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
          this.locLookUptableData = data;
        }
        this.spinner.hideSpinner();
      }
    )
  }

  private selectRowData(e: any) {
    let type: string = e[1];
    let event: any = e[0];

    if (type == "Door") {
      if (this.locselectName == "to") {
        this.simpleContSearchData.contractParam.searchScreenParam["toLocation"] = event.target.parentElement.children[1].textContent;
      }
      else {
        this.simpleContSearchData.contractParam.searchScreenParam["fromLocation"] = event.target.parentElement.children[1].textContent;
      }
    }
    else if (type == "Terminal") {
      if (this.locselectName == "to") {

        this.simpleContSearchData.contractParam.searchScreenParam["toLocation"] = event.target.parentElement.children[3].textContent;
        this.simpleContSearchData.contractParam.searchScreenParam["toTerminal"] = event.target.parentElement.children[1].textContent;
      }
      else {

        this.simpleContSearchData.contractParam.searchScreenParam["fromLocation"] = event.target.parentElement.children[3].textContent;
        this.simpleContSearchData.contractParam.searchScreenParam["fromTerminal"] = event.target.parentElement.children[1].textContent;
      }
    }
    else if (type == "Depot") {
      if (this.locselectName == "to") {
        this.simpleContSearchData.contractParam.searchScreenParam["toTerminal"] = event.target.parentElement.children[1].textContent;
        this.simpleContSearchData.contractParam.searchScreenParam["toLocation"] = event.target.parentElement.children[5].textContent;
      }
      else {
        this.simpleContSearchData.contractParam.searchScreenParam["fromTerminal"] = event.target.parentElement.children[1].textContent;;
        this.simpleContSearchData.contractParam.searchScreenParam["fromLocation"] = event.target.parentElement.children[5].textContent;
      }
    }
    else if (type == "Haulage") {

      if (this.locselectName == "to") {
        this.simpleContSearchData.contractParam.searchScreenParam["toTerminal"] = event.target.parentElement.children[1].textContent;;
        this.simpleContSearchData.contractParam.searchScreenParam["toLocation"] = event.target.parentElement.children[2].textContent;
        //  this.locationCodeDropOff = type + "-" + event.target.parentElement.children[1].textContent + "," + event.target.parentElement.lastElementChild.innerText;
      }
      else {
        this.simpleContSearchData.contractParam.searchScreenParam["fromTerminal"] = event.target.parentElement.children[1].textContent;
        this.simpleContSearchData.contractParam.searchScreenParam["fromLocation"] = event.target.parentElement.children[2].textContent;
        // this.locationCodePickUp = type + "-" + event.target.parentElement.children[1].textContent + "," + event.target.parentElement.lastElementChild.innerText;
      }
    }
    else if (type == "Port") {
      if (this.locselectName == "to") {
        this.simpleContSearchData.contractParam.searchScreenParam["toTerminal"] = event.target.parentElement.children[1].textContent;
        // this.locationCodeDropOff = type + "-" + event.target.parentElement.children[1].textContent + "," + event.target.parentElement.lastElementChild.innerText;
      }
      else {
        this.simpleContSearchData.contractParam.searchScreenParam["fromTerminal"] = event.target.parentElement.children[1].textContent;
      }
    }
  }


  selectLocLookUpValue(e) {
    var isValidated = this.locLookupValidation();
    if (isValidated) {
      this.locationPickUpAndDropOff = this.locationCodePickUp + " / " + this.locationCodeDropOff
      UIkit.modal('#PickDropModal').hide();
      this.locLookUptableData = [];
      this.lookupErrorCodetext = undefined;
      this.lookupErrorCodeShow = false;
      this.showlookuptable = true;
      this._value = undefined;
    }
  }
  //validation 
  locLookupValidation() {
    if (this.locationCodePickUp != undefined && this.locationCodeDropOff) {
      this.showLocErrorText = false;
      return true;
    }
    else {
      if (this.locationCodePickUp == undefined && this.locationCodeDropOff == undefined) {
        this.errorTextLookUp = "Please provide both PickUp and DropOff fields."
        this.showLocErrorText = true;
        return false;
      }
      else if (this.locationCodePickUp == undefined && this.locationCodeDropOff) {
        this.errorTextLookUp = "Please provide PickUp field."
        this.showLocErrorText = true;
        return false;
      }
      else if (this.locationCodePickUp && this.locationCodeDropOff == undefined) {
        this.errorTextLookUp = "Please provide DropOff field."
        this.showLocErrorText = true;
        return false;
      }
    }
  }

  sortLookUpDataByColumn(e) {
    this.lookupSortIn = e.target.value;
    this._sortTable.sortTableData(this.locLookUptableData, this.lookupSortIn, this.looUpOrderBy);
  }
  sortLookUpDataByOrder(e) {
    this._sortTable.sortTableData(this.locLookUptableData, this.lookupSortIn, this.looUpOrderBy);
  }
  //lookup data by filterby
  sortLookUpDataByFilterBy(e) {
    this.lookupFilterBy = e.target.value;
    this._sortTable.sortTableData(this.locLookUptableData, this.lookupSortIn, this.looUpOrderBy);
  }

  contractselectTableRowCheckBoxAll(e) {
    this.selectAllCheckBox = e.target;
    // jQuery('#contract-table app-rcl-checkbox input[type="checkbox"]').prop('checked', e.target.checked);
    if (e.target.checked) {

      this.tableDataContractSearch.forEach(element => {
        //element.checked = true;
        //#NIIT CR2 >>>>BEGIN
        //let selected = element.selected;
        element['clickCheckBox'] = true;
        //element.selected = !selected;
        element.selected = true;
        element['checked'] = element.selected;
        //#NIIT CR2 <<<<END
      });
      this.checkedSelectedRows = Object.assign({}, this.tableDataContractSearch);
      //enable disable buttons when only single record is selected
      if(this.tableDataContractSearch.length==1){      
       this.numberOFRowSelected = 1; 
      }    

    } else {
      //this.checkedJoNumber = [];
      this.selectedContractArray = [];
      this.selectedContractRowArray = [];
      this.checkedSelectedRows = [];
      this.numberOFRowSelected = 0;
      this.tableDataContractSearch.forEach(element => {
        element.checked = false;
        element.selected = false; //#NIIT CR2 
      });

    }
    this.contractrObjectForActions();
  }

  contractrObjectForActions() {
    this.selectedContractRowArray = [];
    this.selectedContractArray = [];
    //this.numberOFRowSelected = 0;
    for (let i = 0; i < this.checkedSelectedRows.length; i++) {
      this.numberOFRowSelected++
      this.selectedContractArray.push(this.checkedSelectedRows[i].contractId);
      this.selectedContractRowArray.push(this.checkedSelectedRows[i]);
    }
  }

  contractTablePageChange(e) {
    this.p = e;
    if (this.selectAllCheckBox['checked']) {
      //this.checkedJoNumber = [];
      this.checkedSelectedRows = [];
      this.selectAllCheckBox['checked'] = true;
    }
   
  }

  ngOnInit() {
    this.selectAllCheckBox = jQuery('#selectAllContract input[type="checkbox"]');
    this.getTermData();
  }

  getFilterCriteria(){
    if(this.filterDataSelectedComp.sortIn=="vendorCode"){
      this.filterDataSelectedComp.sortInText="Vendor id";
    }
    if(this.filterDataSelectedComp.sortIn=="vendorName"){
      this.filterDataSelectedComp.sortInText="Vendor name";
    }
    if(this.filterDataSelectedComp.sortIn=="contractId"){
      this.filterDataSelectedComp.sortInText="Contract id";
    }
    if(this.filterDataSelectedComp.sortIn=="startDate"){
      this.filterDataSelectedComp.sortInText="Start date";
    }
    if(this.filterDataSelectedComp.sortIn=="priority"){
      this.filterDataSelectedComp.sortInText="Priority";
    }
    if(this.filterDataSelectedComp.orderBy=="asnd"){
      this.filterDataSelectedComp.orderByText="Ascending";
    }    
    if(this.filterDataSelectedComp.orderBy=="dsnd"){
      this.filterDataSelectedComp.orderByText="Descending";
    }
    if(this.filterDataSelectedComp.orderBy=="hp"){
      this.filterDataSelectedComp.orderByText="High Priority";
    }
    if(this.filterDataSelectedComp.orderBy=="lp"){
      this.filterDataSelectedComp.orderByText="Low Priority";
    }
  //adding filter By options  
    if(this.filterDataSelectedComp.filterBy=="er"){
      this.filterDataSelectedComp.filterByText="Empty Rate";
    }
    if(this.filterDataSelectedComp.filterBy=="lr"){
      this.filterDataSelectedComp.filterByText="Laden Rate";
    }
  }

  //#NIIT CR1 >>>>BEGIN
  correctPriorities(e){
    this.spinner.showSpinner();

    let correctPriorityOnj = {
      action: "priorityCorrection",
      contract: {
        vendorCode: this.checkedSelectedRows[0]['vendorCode'],
        fromLocType: this.checkedSelectedRows[0]['fromLocType'],
        fromLocation: this.checkedSelectedRows[0]['fromLocation'],
        fromTerminal: this.checkedSelectedRows[0]['fromTerminal'],
        toLocType: this.checkedSelectedRows[0]['toLocType'],
        toLocation: this.checkedSelectedRows[0]['toLocation'],
        toTerminal: this.checkedSelectedRows[0]['toTerminal'],
        startDate: this.checkedSelectedRows[0]['startDate'],
        endDate: this.checkedSelectedRows[0]['endDate'],
        transMode : this.checkedSelectedRows[0]['transMode'],
        contractId : this.checkedSelectedRows[0]['contractId'] 
      }
    }  
    var backendData = this._joborderService.getData(correctPriorityOnj);
    backendData.subscribe(
      (data) => {
        if(data == "userSessionExpired"){
          this._sessionTimeOutService.checkSessionTimeout(data);
        }
        else if(data["errorCode"] == "GBL_IJS_EX_10001"){
          this.errorCodetext = "Priority could not be corrected,please verify the cost data."
          $('#priority-corrected-error').addClass('uk-open').show();
        }
        else if (data.hasOwnProperty("errorCode")) {
          this.errorCodetext = this._serverErrorCode.checkError(data["errorCode"]);
          $('#priority-corrected-error').addClass('uk-open').show();
          //  this.errormessage = false;
        }
        else {
          this.refreshTableData();
          this.numberOFRowSelected = 0;
          $('#priority-corrected-success').addClass('uk-open').show();
        }
        this.spinner.hideSpinner();
        this.selectedContractArray = [];        
        this.selectedRow = undefined;
        this.selectAllCheckBox.checked = false;       
      }
    )
  }
 

//get purchase term data
  getTermData() {
    //this.spinner.showSpinner();
    var backendResult = this._newContractService.getData({ "action": "getTermData" });
    backendResult.subscribe(
      (data) => {
        if(data == "userSessionExpired"){
            this._sessionTimeOutService.checkSessionTimeout(data);
        }
        else if (data.hasOwnProperty("errorCode")) {

        } else {
          if (data["termList"]) {
            this.termCodesList.push({ label: 'All', value: 'All' });
            for (let i = 0; i < data["termList"].length; i++) {
              this.termCodesList.push({ label: data["termList"][i]["termCode"], value: data["termList"][i]["termCode"] });
              this.termCodesList.sort((a,b) => (a.value > b.value) ? 1 : ((b.value > a.value) ? -1 : 0)); //to sort the term code list
            }
          }
        }        
      }
    )
  }
  closePriorityChangedSuccess(e){
    $('#priority-corrected-success').addClass('uk-open').hide();
  }

  closePriorityChangedError(e){
    $('#priority-corrected-error').addClass('uk-open').hide();
  }
  //#NIIT CR1 >>>>END

}
