import { Component, OnInit, ViewChild, Input, Output, EventEmitter } from '@angular/core';
declare var UIkit: any;
declare var jQuery: any;
import { WindowRefService } from "../../../common-services/window-ref.service";
import { UserTypeService } from "../../../user/user-type.service";
import { RclappUrlService } from "../../../common-services/rclapp-url.service"
import { SpinnerServiceService } from "../../../common-services/spinner-service.service";
import { ServerErrorcodeService } from "../../../common-services/server-errorcode.service";
import { ProcessjoSortSearchTableService } from "../processjo-sort-search-table.service";
import { ProcessjoSearchService } from "../processjo-search.service";
import { SessionTimeOutService } from "app/common-services/session-time-out.service";

@Component({
  selector: 'app-processjo-search',
  templateUrl: './processjo-search.component.html',
  styleUrls: ['./processjo-search.component.scss']
})
export class ProcessjoSearchComponent implements OnInit {


  processJoShowfilter: boolean = false;
  //this is the data to be implemented in filter 

  processJoSearchData: any = {
    "processJoParam": {
      transMode: "Select Transport"
    },
    "action": "joSearch"
  }
  bookingTypeText: string = "Booking/BL #";

  processJoSelectConfig: any = {
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
  processJoTypeSelectOptions: any;
  processJoTypeSelectAllOptions: any = [
    {
      label: 'Sea Leg',
      value: 'SEALEG',
    }, {
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
  processJoTypeSelectBLOptions: any = [
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
  processJoTypeSelectBookingOptions: any = [
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

  processJoCOCOptions: any;
  processJoPortTypeOptions: any = [
    {
      label: 'Port',
      value: 'port'
    }, {
      label: 'Inland Point',
      value: 'inlandPoint'
    }
  ]
  //filter data selected
  filterDataSelectedComp: any = {
    "filterDataArr": []
  };
  processJoLocOptions = [
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

  processJoVendorLookUpData: any = [{ "label": "Vendor", "value": "Vendor", "dropDownData": [{ "label": "Vendor", "value": "VCVNCD" }, { "label": "Vendor Name", "value": "VCVDNM" }, { "label": "Vendor Type", "value": "VCVDTY" }, { "label": "City", "value": "VCCITY" }, { "label": "Country", "value": "VCCNTY" }, { "label": "State", "value": "VCSTAT" }] }, { "label": "Vendor Name", "value": "Vendor" }];

  processJoserviceVesselVoyagelookupData: any = [{ "label": "Service/Vessel/Voyage", "value": "serviceVesselLookup", "dropDownData": [{ "label": "Service", "value": "VVSRVC" }, { "label": "Vessel", "value": "VVVESS" }, { "label": "Voyage", "value": "VVVOYN" }] }]

  joTypeSelected: string = "Empty Repo Ad-hoc";
  private locselectName: string;
  private locationCodePickUp: string; //Pick up location
  private locationCodeDropOff: string;  //Drop off location 
  private processJoLocPickupTpye: string; //pickup Loaction type
  private processJoLocDropOffType: string; //Dropof Loaction type
  private showLocErrorText: boolean = false;
  private errorTextLookUp: string
  private fromOrTo: string;
  private validationTextFlag: boolean = false;
  private validationText: string;
  private serverErrorCodetext: string;
  private errorCodetext: string;
  private tableDataProcessJoSearch1: any;
  checkBoxValue: boolean;
  private processJoTransModeData = [{ id: "Truck", text: "truck" }, { id: "Barge", text: "barge" }, { id: "Feeder", text: "feeder" }, { id: "Rail", text: "rail" }]
  private processjoBookingType = [{ id: "Booking", text: "Booking" }, { id: "BL", text: "BL" }]

  @ViewChild('proceessJovehicleTip') proceessJovehicleTip;
  @ViewChild('proceessJoBookingTip') proceessJoBookingTip;
  @ViewChild('processJoDropLoc') advanceDropLoc: any;
  @ViewChild('processJoPickLoc') advancePickLoc: any;
  @ViewChild('processJoPPTDHLookup') processJoPPTDHLookup: any;
  @ViewChild('processjoSearchFilter') processjoSearchFilter: any;
  @Output() searchDataProcessJo: EventEmitter<any> = new EventEmitter();
  @Output() showAddHocComp: EventEmitter<any> = new EventEmitter();
  @Input() private filterData: any;

  constructor(private spinner: SpinnerServiceService, private _serverErrorCode: ServerErrorcodeService, private _sortTable: ProcessjoSortSearchTableService, private _userTypeService: UserTypeService, private _rclappUrlService: RclappUrlService, public _joborderService: ProcessjoSearchService,private _sessionTimeOutService:SessionTimeOutService) { }

  ngOnInit() {
    this.processJoCOCOptions = [
      {
        label: 'COC',
        value: 'c'
      },
      {
        label: 'SOC',
        value: 's'
      },
    ];
    this.processJoTypeSelectOptions = this.processJoTypeSelectAllOptions
  }

  getSearchingData() {
    return this.processJoSearchData;
  }
  //showhide filter
  showhideFilter(e) {
    if (this.processJoShowfilter) {
      this.processJoShowfilter = false;
      document.getElementById("filterButton").classList.remove("filterButtonClicked");
    }
    else {
      this.processJoShowfilter = true;
      document.getElementById("filterButton").classList.add("filterButtonClicked");
    }
  }

  //show hide vehicletool tip
  private showHideVehicleToolTip() {
    if (this.proceessJovehicleTip.isOpen()) {
      this.proceessJovehicleTip.close();
    }
    else {
      this.proceessJovehicleTip.open();
      if(this.processJoSearchData.processJoParam.transMode=='Select Transport'){
        this.processJoSearchData.processJoParam.transMode='Truck';
        if(this.processJoSearchData.processJoParam.bookingType=='Booking'){
          this.copyProcessJoTypeSelectAllOption = this.processJoTypeSelectAllOptions;
          const filteredProcessJoTypeSelectAllOption = this.copyProcessJoTypeSelectAllOption.filter(element => element.value != 'ITR' && element.value != 'IT' && element.value != 'SEALEG');
          this.processJoTypeSelectOptions=filteredProcessJoTypeSelectAllOption;
        }
        if(this.processJoSearchData.processJoParam.bookingType=='BL'){
          this.copyProcessJoTypeSelectAllOption = this.processJoTypeSelectAllOptions;
          const filteredProcessJoTypeSelectAllOption = this.copyProcessJoTypeSelectAllOption.filter(element => element.value != 'ITR' && element.value != 'IT' && element.value != 'SEALEG');
          this.processJoTypeSelectOptions=filteredProcessJoTypeSelectAllOption;
        }
      }
    }
  }

  //show hide Booking tool tip
  private showHideBookingToolTip() {
    if (this.proceessJoBookingTip.isOpen()) {
      this.proceessJoBookingTip.close();
    }
    else {
      this.proceessJoBookingTip.open();
      this.processJoSearchData.processJoParam.vendorCode;
      if(this.processJoSearchData.processJoParam.bookingType==undefined){
        this.processJoSearchData.processJoParam.bookingType='Booking';
        this.bookingTypeText='Booking';
          if(this.processJoSearchData.processJoParam.transMode == "Truck" || this.processJoSearchData.processJoParam.transMode == "Rail"){
            this.copyProcessJoTypeSelectAllOption = this.processJoTypeSelectAllOptions;
            const filteredProcessJoTypeSelectAllOption = this.copyProcessJoTypeSelectAllOption.filter(element => element.value != 'ITR' && element.value != 'IT' && element.value != 'SEALEG');
            this.processJoTypeSelectOptions=filteredProcessJoTypeSelectAllOption;
          }
          if(this.processJoSearchData.processJoParam.transMode == "Barge" || this.processJoSearchData.processJoParam.transMode == "Feeder"){
            this.copyProcessJoTypeSelectAllOptiononTransportBaisis = this.processJoTypeSelectBLOptions;
            const filteredProcessJoTypeSelectAllOptionOnTransportBasis = this.copyProcessJoTypeSelectAllOptiononTransportBaisis.filter(element => element.value != 'IT');
            this.processJoTypeSelectOptions=filteredProcessJoTypeSelectAllOptionOnTransportBasis;
          }        
      }
    }
  }

  private onChangeProcessJOType(e) {    
    this.processJoSearchData.processJoParam.processJoType = e;
    if (this.processJoSearchData.processJoParam.processJoType == 'ER' || this.processJoSearchData.processJoParam.processJoType == 'LAH') {
      this.processJoSearchData = {
        "processJoParam": {
          transMode: "Select Transport",
          processJoType: e
        },
        "action": "joSearch"
      }
      this.bookingTypeText = "Booking/BL #";
      this.processJoCOCOptions = [{ label: 'COC', value: 'c' }];
    } else {
      this.processJoCOCOptions = [{ label: 'COC', value: 'c' }, { label: 'SOC', value: 's' }];
    }
  }

  copyProcessJoTypeSelectAllOptiononTransportBaisis = [];
  private setJobOrederTypeOptions(transMode) {   
    if (transMode == "Truck" || transMode == "Rail") {
      if(this.processJoSearchData.processJoParam.bookingType == "Booking"){
          this.copyProcessJoTypeSelectAllOption = this.processJoTypeSelectAllOptions;
          const filteredProcessJoTypeSelectAllOption = this.copyProcessJoTypeSelectAllOption.filter(element => element.value != 'ITR' && element.value != 'IT' && element.value != 'SEALEG');
          this.processJoTypeSelectOptions=filteredProcessJoTypeSelectAllOption;
      }else{
          this.copyProcessJoTypeSelectAllOption = this.processJoTypeSelectAllOptions;
          const filteredProcessJoTypeSelectAllOption = this.copyProcessJoTypeSelectAllOption.filter(element => element.value != 'ETR'  && element.value != 'SEALEG');
          this.processJoTypeSelectOptions=filteredProcessJoTypeSelectAllOption;          
       }     
     } else if (transMode == "Barge" || transMode == "Feeder") {
       if(this.processJoSearchData.processJoParam.bookingType == "Booking"){
          this.copyProcessJoTypeSelectAllOptiononTransportBaisis = this.processJoTypeSelectBLOptions;
          const filteredProcessJoTypeSelectAllOptionOnTransportBasis = this.copyProcessJoTypeSelectAllOptiononTransportBaisis.filter(element => element.value != 'IT');
          this.processJoTypeSelectOptions=filteredProcessJoTypeSelectAllOptionOnTransportBasis;
       } else{        
        this.processJoTypeSelectOptions = this.processJoTypeSelectBLOptions;
       }       
     }
  }

  copyProcessJoTypeSelectAllOption = [];
  private changeBookingTypeValue(e) {
    this.processjoBookingType.forEach(ele => {
      if (ele.text == e) {
        this.processJoSearchData.processJoParam.bookingType = ele.id;
      }
    });

    if(this.processJoSearchData.processJoParam.bookingType == "Booking"){
        if(this.processJoSearchData.processJoParam.transMode == "Truck" || this.processJoSearchData.processJoParam.transMode == "Rail"){
          this.copyProcessJoTypeSelectAllOption = this.processJoTypeSelectAllOptions;
          const filteredProcessJoTypeSelectAllOption = this.copyProcessJoTypeSelectAllOption.filter(element => element.value != 'ITR' && element.value != 'IT' && element.value != 'SEALEG');
          this.processJoTypeSelectOptions = filteredProcessJoTypeSelectAllOption;
        } else{
          this.copyProcessJoTypeSelectAllOption = this.processJoTypeSelectBLOptions;
          const filteredProcessJoTypeSelectAllOption = this.copyProcessJoTypeSelectAllOption.filter(element => element.value != 'IT');
          this.processJoTypeSelectOptions = filteredProcessJoTypeSelectAllOption;
         }            
    } else {
      if(this.processJoSearchData.processJoParam.transMode == "Truck" || this.processJoSearchData.processJoParam.transMode == "Rail"){
        this.copyProcessJoTypeSelectAllOption = this.processJoTypeSelectAllOptions;
        const filteredProcessJoTypeSelectAllOption = this.copyProcessJoTypeSelectAllOption.filter(element => element.value != 'ETR'  && element.value != 'SEALEG');
        this.processJoTypeSelectOptions=filteredProcessJoTypeSelectAllOption;
      } else{
        this.processJoTypeSelectOptions = this.processJoTypeSelectBLOptions;
      }      
    }
   
  }

  //open pick and drop interface modal
  private openPickupDropoffPopUp(e) {
    if (e.target.disabled) {
      e.stopPropogation();
    } else {
      UIkit.modal('#ProcessJOPickDropModal').show();
    }
  }

  //open from lookup modal
  private openFromLookup(selectedValue, fromOrTo) {
    this.locselectName = fromOrTo;
    this.processJoPPTDHLookup.openFromLookup(selectedValue, fromOrTo);
  }

  //open to lookup modal
  private openToLookup(selectedValue, fromOrTo) {
    this.locselectName = fromOrTo;
    this.processJoPPTDHLookup.openToLookup(selectedValue, fromOrTo);
  }

  dropOffDropDownChange(e) {
    this.processJoSearchData.processJoParam["toLocation"] = undefined;
    this.processJoSearchData.processJoParam["toTerminal"] = undefined;
  }
  pickUpDropDownChange(e) {
    this.processJoSearchData.processJoParam["fromLocation"] = undefined;
    this.processJoSearchData.processJoParam["fromTerminal"] = undefined;
  }

  //fill the data of code when row selected
  private rowSelected(e: any) {
    let type: string = e[1];
    let event: any = e[0];
    if (type == "Door") {
      if (this.locselectName == "to") {
        this.processJoSearchData.processJoParam["toLocation"] = event.target.parentElement.children[1].textContent;
      }
      else {
        this.processJoSearchData.processJoParam["fromLocation"] = event.target.parentElement.children[1].textContent;
      }
    }
    else if (type == "Terminal") {
      if (this.locselectName == "to") {
        this.processJoSearchData.processJoParam["toLocation"] = event.target.parentElement.children[3].textContent;
        this.processJoSearchData.processJoParam["toTerminal"] = event.target.parentElement.children[1].textContent;
      }
      else {
        this.processJoSearchData.processJoParam["fromLocation"] = event.target.parentElement.children[3].textContent;
        this.processJoSearchData.processJoParam["fromTerminal"] = event.target.parentElement.children[1].textContent;
      }
    }
    else if (type == "Depot") {
      if (this.locselectName == "to") {
        this.processJoSearchData.processJoParam["toTerminal"] = event.target.parentElement.children[1].textContent;
        this.processJoSearchData.processJoParam["toLocation"] = event.target.parentElement.children[5].textContent;
      }
      else {
        this.processJoSearchData.processJoParam["fromTerminal"] = event.target.parentElement.children[1].textContent;;
        this.processJoSearchData.processJoParam["fromLocation"] = event.target.parentElement.children[5].textContent;
      }
    }
    else if (type == "Haulage") {
      if (this.locselectName == "to") {
        this.processJoSearchData.processJoParam["toTerminal"] = event.target.parentElement.children[1].textContent;;
        this.processJoSearchData.processJoParam["toLocation"] = event.target.parentElement.children[2].textContent;
        //  this.locationCodeDropOff = type + "-" + event.target.parentElement.children[1].textContent + "," + event.target.parentElement.lastElementChild.innerText;
      }
      else {
        this.processJoSearchData.processJoParam["fromTerminal"] = event.target.parentElement.children[1].textContent;
        this.processJoSearchData.processJoParam["fromLocation"] = event.target.parentElement.children[2].textContent;
        // this.locationCodePickUp = type + "-" + event.target.parentElement.children[1].textContent + "," + event.target.parentElement.lastElementChild.innerText;
      }
    }
    else if (type == "Port") {
      if (this.locselectName == "to") {
        this.processJoSearchData.processJoParam["toTerminal"] = event.target.parentElement.children[1].textContent;
        // this.locationCodeDropOff = type + "-" + event.target.parentElement.children[1].textContent + "," + event.target.parentElement.lastElementChild.innerText;
      }
      else {
        this.processJoSearchData.processJoParam["fromTerminal"] = event.target.parentElement.children[1].textContent;
      }
    }

  }

  //seach for process jo order
  private searchProcessJoOrder() {
    //validation when jo type is inter terminal or any transport mode is selected

    if(this.processJoSearchData.processJoParam.processJoType == 'IT' || this.processJoSearchData.processJoParam.fromLocType != undefined || this.processJoSearchData.processJoParam.toLocType != undefined ){
        
        if(((this.processJoSearchData.processJoParam.vendorCode != undefined &&
          this.processJoSearchData.processJoParam.processJoType == 'IT') && 
          (this.processJoSearchData.processJoParam.fromLocType == undefined ||
          this.processJoSearchData.processJoParam.toLocType == undefined))){
          UIkit.modal('#requiredFields-warning-modal').show();
          return;
      }  

      //when jo type is inter terminal and either of from location and to location is blank
      if(((this.processJoSearchData.processJoParam.processJoType == 'IT') && 
           (this.processJoSearchData.processJoParam.fromLocType == undefined ||
           this.processJoSearchData.processJoParam.toLocType == undefined))){
           UIkit.modal('#requiredFields-warning-modal').show();
           return;
      }  

      //when jo type is inter terminal and vendor code is blank
       if(this.processJoSearchData.processJoParam.processJoType == 'IT' && this.processJoSearchData.processJoParam.vendorCode == undefined){
            UIkit.modal('#requiredFields-warning-modal').show();
            return;
      }  

      //  if((this.processJoSearchData.processJoParam.vendorCode == undefined 
      //   || this.processJoSearchData.processJoParam.fromLocType == undefined 
      //   || this.processJoSearchData.processJoParam.toLocType == undefined)){
      //   UIkit.modal('#requiredFields-warning-modal').show();
      //   return;
      // }  


      if(this.processJoSearchData.processJoParam.fromLocType == "Port" 
      && this.processJoSearchData.processJoParam.fromTerminal == undefined){
	    UIkit.modal('#requiredFields-warning-modal').show();
      return;
      }   
    
      if(this.processJoSearchData.processJoParam.toLocType == "Port" 
        && this.processJoSearchData.processJoParam.toTerminal == undefined){
        UIkit.modal('#requiredFields-warning-modal').show();
        return;
      } 
      if(this.processJoSearchData.processJoParam.fromLocType == "Door"  
        && this.processJoSearchData.processJoParam.fromLocation == undefined ){
        UIkit.modal('#requiredFields-warning-modal').show();
        return;
      }   
      if(this.processJoSearchData.processJoParam.toLocType == "Door"
        && this.processJoSearchData.processJoParam.toLocation == undefined){
        UIkit.modal('#requiredFields-warning-modal').show();
        return;
      }     
      if(this.processJoSearchData.processJoParam.fromLocType == "Terminal" &&
        (this.processJoSearchData.processJoParam.fromLocation == undefined || this.processJoSearchData.processJoParam.fromTerminal == undefined)){
        UIkit.modal('#requiredFields-warning-modal').show();
        return;
      }
      if(this.processJoSearchData.processJoParam.toLocType == "Terminal" &&
        (this.processJoSearchData.processJoParam.toLocation == undefined || this.processJoSearchData.processJoParam.toTerminal == undefined)){
        UIkit.modal('#requiredFields-warning-modal').show();
        return;
      } 
      if(this.processJoSearchData.processJoParam.fromLocType == "Haulage"  &&
        (this.processJoSearchData.processJoParam.fromLocation == undefined || this.processJoSearchData.processJoParam.fromTerminal == undefined)){
        UIkit.modal('#requiredFields-warning-modal').show();
        return;
      }
      if(this.processJoSearchData.processJoParam.toLocType == "Haulage" &&
        (this.processJoSearchData.processJoParam.toLocation == undefined || this.processJoSearchData.processJoParam.toTerminal == undefined)){
        UIkit.modal('#requiredFields-warning-modal').show();
        return;
      }
      if(this.processJoSearchData.processJoParam.fromLocType == "Depot"  &&
        (this.processJoSearchData.processJoParam.fromLocation == undefined || this.processJoSearchData.processJoParam.fromTerminal == undefined)){
        UIkit.modal('#requiredFields-warning-modal').show();
        return;
      }
      if(this.processJoSearchData.processJoParam.toLocType == "Depot" &&
        (this.processJoSearchData.processJoParam.toLocation == undefined || this.processJoSearchData.processJoParam.toTerminal == undefined)){
        UIkit.modal('#requiredFields-warning-modal').show();
        return;
      }
    }

    //to make default ascending on result screen 
    if(this.filterDataSelectedComp.orderBy == undefined){      
       this.filterDataSelectedComp.orderBy = "asnd";   
    } 
    //to make default bkg bl in sort in in result screen
    if(this.filterDataSelectedComp.sortIn == undefined){
      this.filterDataSelectedComp.sortIn = "bkgOrBLNumber";   
    }
    this._joborderService.processJoSearchData = this.processJoSearchData;
    this.getFilterCriteria();//to get filter criteria

    let findButton = document.getElementById('find-button');
    findButton.innerHTML = '<i class="fa fa-search"></i> <span>&nbsp;LOADING...</span>'
    document.getElementById("filterButton").classList.remove("filterButtonClicked");
    this.validationText = this.formValidationSimpleSearch();

    if (this.validationText == "true") {
      this.validationTextFlag = false;
      this.getBackEndData(findButton);
    } else {
      this.validationTextFlag = true;
      findButton.innerHTML = '<i class="fa fa-search"></i> <span>&nbsp;FIND</span>'
    }
  }
  //check form validation
  private formValidationSimpleSearch() {
    let transtype = this.processJoSearchData.processJoParam.transMode;
    let bookingType = this.processJoSearchData.processJoParam.bookingType;
    let joType = this.processJoSearchData.processJoParam.processJoType;
    //Madhuri
    let vendor = this.processJoSearchData.processJoParam.processJoVendorlookup1;
    let bookingBLNo = this.processJoSearchData.processJoParam.processJoBooking;
    let service = this.processJoSearchData.processJoParam.processJoServicelookup1;
    let vessel = this.processJoSearchData.processJoParam.processJoVesselookup1;
    let voyage = this.processJoSearchData.processJoParam.processJoVoyagelookup1;
    let session = this.processJoSearchData.processJoParam.session;
    let portLookup = this.processJoSearchData.processJoParam.processJoPPTDH;

    if (transtype && transtype != "Select Transport" && bookingType && joType) {
      return "true"
    } else {
      return "Please provide Select Transport, Booking/BL #, and Job Order Type";
    }
  }

  //filter data from filter comoponent
  private filterDataSelected(e) {
    this.filterDataSelectedComp = e;
  }
  errormessage:boolean= true;
  serverErrormessage:boolean= true;
  private getBackEndData(findButton) {
    this.errorCodetext="";
    this.serverErrorCodetext="";
    this.spinner.showSpinner();
    let backendData = this._joborderService.getProcessjoSearchData(this.processJoSearchData);
    backendData.subscribe(
      (data) => {
        if(data == "userSessionExpired"){
          this._sessionTimeOutService.checkSessionTimeout(data);
        }
        else if (data.hasOwnProperty("errorCode")) {
          this.errorCodetext = this._serverErrorCode.checkError(data["errorCode"]);
          this.errormessage=false;
         // UIkit.modal('#processjo-error-code-modal').show();
        }
        else {
          this.tableDataProcessJoSearch1 = data;
          let searchInputOutPutData = {
            "filterInputData": this.filterDataSelectedComp,
            "searchInputData": this.processJoSearchData,
            "searchOutputData": this.tableDataProcessJoSearch1,
            "filteredSearchOutputData": this._sortTable.sortTableData(this.tableDataProcessJoSearch1, this.filterDataSelectedComp.sortIn, this.filterDataSelectedComp.orderBy)
          }
          this.errormessage=true;
          this.searchDataProcessJo.emit(searchInputOutPutData);
        }
        this.spinner.hideSpinner();
        if (findButton !== undefined)
          findButton.innerHTML = '<i class="fa fa-search"></i> <span>&nbsp;FIND</span>'
      },
      (err) => {
        this.spinner.hideSpinner();
        // A client-side or network error occurred. Handle it accordingly.
        findButton.innerHTML = '<i class="fa fa-search"></i> <span>&nbsp;FIND</span>'
        this.serverErrormessage = false;
        this.serverErrorCodetext = "There is an issue on DB connection,  Please try again.";
        
        //UIkit.modal('#processjo-error-code-modal').show();
      }
    )
  }


  private resetSearch() {
    this.processJoTypeSelectOptions = this.processJoTypeSelectAllOptions;
    this.errormessage = true;
    this.serverErrormessage=true;
    this.processJoSearchData = {
      "processJoParam": {
        transMode: "Select Transport"
      },
      "action": "joSearch"
    };
    this.bookingTypeText = "Booking/BL #";
    this.validationTextFlag = false;
    this.validationText = ""
    this.processJoSearchData.processJoParam['serviceVal'] = "";
    this.processJoSearchData.processJoParam['vesselVal'] = "";
    this.processJoSearchData.processJoParam['voyageVal'] = "";
  }

  selectLocLookUpValue(e) {
    var isValidated = this.locLookupValidation();
    if (isValidated) {

      if (this.locationCodePickUp && this.locationCodeDropOff) {
        this.processJoSearchData.processJoParam.pptdhVal = this.locationCodePickUp + " / " + this.locationCodeDropOff
      } else if (this.locationCodePickUp) {
        this.processJoSearchData.processJoParam.pptdhVal = this.locationCodePickUp;
      } else if (this.locationCodeDropOff) {
        this.processJoSearchData.processJoParam.pptdhVal = this.locationCodeDropOff;
      }
      UIkit.modal('#ProcessJOPickDropModal').hide();
      //this.locLookUptableData = [];
      // this.lookupErrorCodetext= undefined;
      // this.lookupErrorCodeShow=false;
      // this.showlookuptable = true;
      //this._value = undefined;
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
  equipmentslimit:any;//#NIIT CR3
  addHocTypeCode:any;//#NIIT CR3
  addHocTypeName:any;//#NIIT CR3
  showAddHoc(e) {   

    if (this.processJoSearchData.processJoParam.vendorCode) {

      if (e.target.checked) {
        this.validationTextFlag = false;
        this.validationText = "";
        if (this.processJoSearchData.processJoParam.processJoType == this.processJoTypeSelectAllOptions[4]['value']) {
            this.addHocTypeCode = this.processJoTypeSelectAllOptions[4]['value']
            this.addHocTypeName = this.processJoTypeSelectAllOptions[4]['label']
           } else if (this.processJoSearchData.processJoParam.processJoType == this.processJoTypeSelectAllOptions[5]['value']) {
              this.addHocTypeCode = this.processJoTypeSelectAllOptions[5]['value']
              this.addHocTypeName = this.processJoTypeSelectAllOptions[5]['label']
          }
        this.getEquipmentsLimit(); //#NIIT CR3       
      }
    }  
    else {
      this.validationTextFlag = true;
      this.validationText = "Please provide the vendor code"
      e.target.checked = false;
    }
  }

  getLookupValues(e){    
    this.processJoSearchData.processJoParam['serviceVal'] = e.serviceValue;    
    this.processJoSearchData.processJoParam['vesselVal'] = e.vesselValue;
    this.processJoSearchData.processJoParam['voyageVal'] = e.voyageValue;
  }

  getSVVValue(e){
    if(e.placeholder == "Service"){
      this.processJoSearchData.processJoParam['serviceVal'] = e.serviceValue;
    } else if(e.placeholder == "Vessel"){
      this.processJoSearchData.processJoParam['vesselVal'] = e.vesselValue;
    } else if(e.placeholder == "Voyage"){
      this.processJoSearchData.processJoParam['voyageVal'] = e.voyageValue;
    }     
  }

  getFilterCriteria(){
    if(this.filterDataSelectedComp.orderBy == "asnd"){      
       this.filterDataSelectedComp.orderByName = "Ascending";   
    } 
    if(this.filterDataSelectedComp.orderBy == "dsnd"){      
       this.filterDataSelectedComp.orderByName = "Descending";   
    } 
    if(this.filterDataSelectedComp.sortIn == "bkgOrBLNumber"){      
       this.filterDataSelectedComp.sortInName = "Booking/BL";   
    } 
    if(this.filterDataSelectedComp.sortIn == "transMode"){      
       this.filterDataSelectedComp.sortInName = "Transport Mode";   
    }
    if(this.filterDataSelectedComp.sortIn == "startDate"){      
       this.filterDataSelectedComp.sortInName = "EFFECTIVE DATE";   
    }
    if(this.filterDataSelectedComp.sortIn == "endDate"){      
       this.filterDataSelectedComp.sortInName = "EXPIRY DATE";   
    }
    if(this.filterDataSelectedComp.sortIn == "fromTerminal"){      
       this.filterDataSelectedComp.sortInName = "From";   
    }
    if(this.filterDataSelectedComp.sortIn == "toTerminal"){      
       this.filterDataSelectedComp.sortInName = "To";   
    } 
  }

  //#NIIT CR3 BEGIN	
  //method to get the limit of containers that must be saved to create a jo
  getEquipmentsLimit(){
    let data: any = {    
    "action": "adhocEquipLimit"
    }
    let backendData = this._joborderService.getJoSummaryListLimit(data);
    backendData.subscribe(
      (result) => {
        if(result == "userSessionExpired"){
          this._sessionTimeOutService.checkSessionTimeout(result);
        }  else{
          this.equipmentslimit = result;
          let obj = {
            "vendorCode": this.processJoSearchData.processJoParam.vendorCode,
            "addHocTypeCode": this.addHocTypeCode,
            "addHocTypeName": this.addHocTypeName,
            "joSummaryLimit": this.equipmentslimit
        }
        //emit the object
        this.showAddHocComp.emit(obj);
        }  
      },
      (err) => {
       console.log(err);
      }
    )
  }
  //#NIIT CR3 END	

}
