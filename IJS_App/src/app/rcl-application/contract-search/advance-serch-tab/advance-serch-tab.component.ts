import { Component, OnInit, ViewChild} from '@angular/core';
import { LookUpdataServiceService } from '../../../common-services/look-updata-service.service';
declare var UIkit: any;
declare var jQuery: any;
declare const $: any;
import { ServerErrorcodeService } from "../../../common-services/server-errorcode.service";
import { SpinnerServiceService } from "../../../common-services/spinner-service.service";
;
import { ContractSearchService } from "../../contract-search/contract-search.service";
import { SortSearchTableService } from "../sort-search-table.service";
import { ContractSearchComponent } from "../contract-search.component";
import { SessionTimeOutService } from "app/common-services/session-time-out.service";




@Component({
  selector: 'app-advance-serch-tab',
  templateUrl: './advance-serch-tab.component.html',
  styleUrls: ['./advance-serch-tab.component.scss']
})
export class AdvanceSerchTabComponent implements OnInit {

  advanceSearchData = {
    "contractParam": {
      "transMode": "Select Transport",
      "searchScreenParam": {
      }
    },
    "action": "search"
  }

  countryLookUpData: any = [{"label":"Country", "value":"Country","dropDownData": [{"label":"Country", "value":"SCCODE"},{"label":"Country Name", "value":"SCNAME"},{"label":"Status", "value":"SCRCST"}]}]
  vendorLookUpData: any = [{"label":"Vendor", "value":"Vendor","dropDownData": [{"label":"Vendor", "value":"VCVNCD"},{"label":"Vendor Name", "value":"VCVDNM"},{"label":"Vendor Type", "value":"VCVDTY"}, {"label":"City", "value":"VCCITY"}, {"label":"Country", "value":"VCCNTY"}, {"label":"State", "value":"VCSTAT"}]}, {"label":"Vendor Name", "value":"Vendor"}]
  
  locationCodePickUp: string; //Pick up location
  locationCodeDropOff: string;  //Drop off location 
  advanceLocPickupTpye: string; //pickup Loaction type
  advanceLocDropOffType: string; //Dropof Loaction type
  showLocErrorText: boolean = false;
  errorTextLookUp: string
  contractshowfilter: boolean = false;
  locationPickUpAndDropOff: string;
  doorLookUpSelected: string = "POINT_CODE";
  terminalLookUpSelected: string = "TQTERM";
  depotLookUpSelected: string = "TQTERM"
  haulageLookUpSelected: string = "HAULAGE_LOCATION_CODE";
  errorCodeShow = true;
  tableDataContractSearch = [];

  locLookUptableData: any = []
  lookupErrorCodetext: any;
  lookupErrorCodeShow: boolean = false;
  locselectName: string;  
  
  showlookuptable: boolean = true;
  _value: string;
  advanceValidationText: any;
  advancevalidationTextFlag: boolean = false;
  
  //sorting of look up data variable 
  looUpOrderBy: any;  
  lookupSortIn: any;
  resultsPerPage = 5;
  resultsPerPageArr = [1,2,3,4,5,6,7,8,9,10];
  lookupWildCard: boolean;
  
  @ViewChild('advanceDropLoc') advanceDropLoc: any;
  @ViewChild('advancePickLoc') advancePickLoc: any;
  @ViewChild('vehicleTip') vehicleTip;
  @ViewChild('inputlookup_1') inputlookup_1: any;
  @ViewChild('inputlookup_2') inputlookup_2: any;
  


  //Location type lookupss
  advanceLocConfig: any = {
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

  //options variable for advance Loc Options dropdown
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
      label: 'Haulage Loc.',
      value: 'Haulage'
    }
  ];

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
  
  //LookUp sort options
  doorLookUpSortData = [{ label: 'Point Code',value: 'pointCode'}, {label: 'Point Name',value: 'poingName'}, {label: 'Country Code', value: 'countryCode'}, {label: 'Zone Code', value: 'zoneCode'}, {label: 'State Code', value: 'stateCode'}]
  
  terminalLookUpSortData = [{ label: 'Terminal',value: 'terminal'}, {label: 'Terminal Name',value: 'terminalName'}, {label: 'Port', value: 'port'}, {label: 'FSC', value: 'fsc'}]
  
  depotLookUpSortData = [{ label: 'Depot Code',value: 'depot'}, {label: 'Depot Name',value: 'depotName'}, {label: 'Depot Port', value: 'depotPort'}, {label: 'Point Code', value: 'fsc'}]
  
   haulageLookUpSortData = [{ label: 'Haulage Location Code',value: 'haulageLocationCode'}, {label: 'Inland Point',value: 'inlandPoint',}, {label: 'Haulage Location Name', value: 'hulageLocationName'}]
  
  lookUpSortData = [{ label: 'Ascending',value: 'asnd'}, {label: 'Descending',value: 'dsnd'}]
  

  constructor(private _lookupData: LookUpdataServiceService, public spinner: SpinnerServiceService, public _serverErrorCode: ServerErrorcodeService, public _contractSearchComponent: ContractSearchComponent, private _backendServ: ContractSearchService, public _sortTable: SortSearchTableService,private _sessionTimeOutService:SessionTimeOutService) { }

  ngOnInit() {
  }

  //show and hide vehicle tool tip
  showHideToolTip() {
    if (this.vehicleTip.isOpen()) {
      this.vehicleTip.close();
    }
    else {
      this.vehicleTip.open();
    }
  }
  //showhide filter
  showhideFilter(e) {
    this._contractSearchComponent.showhideFilter(e); 
  }
  resetAdvanceSearch() {
    this.advanceSearchData = {
      "contractParam": {
        "transMode": "Select Transport",
        "searchScreenParam": {
        }
      },
      "action": "search"
    }
    this.locationPickUpAndDropOff = undefined;

  }

  //get data from server
  advanceSearchBackendData() {

    this._contractSearchComponent.contractshowfilter = false;    

    this.advanceValidationText = this.formValidationAdvanceSearch();
    
    if (this.advanceValidationText == "true") {
      this.advancevalidationTextFlag = false;
      this.spinner.showSpinner();
      this._contractSearchComponent.contractSearchData = this.advanceSearchData
      var backenddata = this._backendServ.getData(this.advanceSearchData);
      backenddata.subscribe(
        (data) => {
          if(data == "userSessionExpired"){
            this._sessionTimeOutService.checkSessionTimeout(data);
          }
          else if (data.hasOwnProperty("errorCode")) {
            this._contractSearchComponent.errorCodetext = this._serverErrorCode.checkError(data["errorCode"]);
            this._contractSearchComponent.errorCodeShow = true;

            UIkit.modal('#errorCodeShow-Modal').show();
          }
          else {
            this._contractSearchComponent.tableDataContractSearch1 = data;
            this._contractSearchComponent.addRemoveSortFilter(this._contractSearchComponent.filterDataSelectedComp, this._contractSearchComponent.tableDataContractSearch1);
            this._contractSearchComponent.calCPages();
            this._contractSearchComponent.hideResults = true;
            this._contractSearchComponent.errorCodeShow = false;
          }
          this.spinner.hideSpinner();
        }
      )

    } else {
      this.advancevalidationTextFlag = true;
    }
  }

  formValidationAdvanceSearch() {    
    var transtype = this.advanceSearchData.contractParam.transMode;
    var datevalues = this.advanceSearchData.contractParam.searchScreenParam["dateRange"];
    var vendorValue = this.advanceSearchData.contractParam.searchScreenParam["vendorCode"];
    var countryValue = this.advanceSearchData.contractParam.searchScreenParam["countryCode"];
    var contractRoutingValue = this.advanceSearchData.contractParam.searchScreenParam["contractRouting"];    
    var locationPickUpAndDropOff = this.locationPickUpAndDropOff; 
    
    if (transtype == "Select Transport" && datevalues == undefined && (vendorValue == "" || vendorValue == undefined) && (countryValue == "" || countryValue == undefined) && contractRoutingValue == undefined && locationPickUpAndDropOff == undefined) {
      return "Please provide atleast one input field";
    } else {
       return "true";
    }
   
  }

  //open the pop Up
  openPickupDropoffPopUp() {
    UIkit.modal('#PickDropModal', {bgclose: false}).show();
  }
  //open pickup lookup
  openPickupLookup(e) {
    this.locselectName = e.target.id;
    if ((this.advanceLocPickupTpye && this.locselectName == "PickUpLoc") || (this.advanceLocDropOffType && this.locselectName == "DropOffLoc")) {
      UIkit.modal('#PickDropModal').hide();
      UIkit.modal('#PickDropModalLookUp', {bgclose: false}).show();
      this.showLocErrorText = false;
    } else {
      this.errorTextLookUp = "Please select correct dropdown."
      this.showLocErrorText = true;
    }
  }
  
  //Toggle modals
  pickDropModalLookUpGoBack() {
    UIkit.modal('#PickDropModal', {bgclose: false}).show();
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
    this.lookupErrorCodetext= undefined;
    this.lookupErrorCodeShow=false;
    this._value = undefined;
    this.lookupSortIn = undefined;
    this.lookupWildCard = false;
    this.advanceDropLoc.valueAccessor.resetSelectSize();
    this.advancePickLoc.valueAccessor.resetSelectSize();      
  }
  //
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
  //
  selectRowData(e, type) {   

    if (this.locselectName == "PickUpLoc") {
      this.locationCodePickUp = this.advanceLocPickupTpye + "-" + e.target.parentElement.children[1].textContent + "," + e.target.parentElement.lastElementChild.innerText;
      this.advanceSearchData.contractParam.searchScreenParam["fromLocType"] = type;

      if (type == "Terminal" || type == "Depot") {

        this.advanceSearchData.contractParam.searchScreenParam["fromTerminal"] = e.target.parentElement.children[1].textContent;
        this.advanceSearchData.contractParam.searchScreenParam["fromLocation"] = e.target.parentElement.children[3].textContent;

      } else if (type == "Door") {
        this.advanceSearchData.contractParam.searchScreenParam["fromTerminal"] = e.target.parentElement.children[1].textContent;
        delete this.advanceSearchData.contractParam.searchScreenParam["fromLocation"]
      } else if (type == "Haulage") {
        this.advanceSearchData.contractParam.searchScreenParam["fromTerminal"] = e.target.parentElement.children[1].textContent;
        this.advanceSearchData.contractParam.searchScreenParam["fromLocation"] = e.target.parentElement.children[2].textContent;
      }


    } else if (this.locselectName == "DropOffLoc") {
      this.locationCodeDropOff = this.advanceLocDropOffType + "-" + e.target.parentElement.children[1].textContent + "," + e.target.parentElement.lastElementChild.innerText;

      this.advanceSearchData.contractParam.searchScreenParam["toLocType"] = type;
      if (type == "Terminal" || type == "Depot") {
        this.advanceSearchData.contractParam.searchScreenParam["toTerminal"] = e.target.parentElement.children[1].textContent;
        this.advanceSearchData.contractParam.searchScreenParam["toLocation"] = e.target.parentElement.children[3].textContent;
      } else if (type == "Door") {
        this.advanceSearchData.contractParam.searchScreenParam["toTerminal"] = e.target.parentElement.children[1].textContent;
        delete this.advanceSearchData.contractParam.searchScreenParam["toLocation"]
      } else if (type == "Haulage") {
        this.advanceSearchData.contractParam.searchScreenParam["toTerminal"] = e.target.parentElement.children[1].textContent;
        this.advanceSearchData.contractParam.searchScreenParam["toLocation"] = e.target.parentElement.children[2].textContent;
      }
    }
    
    UIkit.modal('#PickDropModal').show();
    UIkit.modal('#PickDropModalLookUp').hide();
    this.locLookUptableData = [];
    this.lookupErrorCodetext= undefined;
    this.lookupErrorCodeShow=false;
    this._value = undefined;
    this.showlookuptable = true;
    this.lookupSortIn = undefined;
    this.lookupWildCard = false;

  }
  selectLocLookUpValue(e) {
    var isValidated = this.locLookupValidation();
    if (isValidated) {
      this.locationPickUpAndDropOff = this.locationCodePickUp + " / " + this.locationCodeDropOff
      UIkit.modal('#PickDropModal').hide();
      this.locLookUptableData = [];
      this.lookupErrorCodetext= undefined;
      this.lookupErrorCodeShow=false;
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
  
  //Sort 
}
