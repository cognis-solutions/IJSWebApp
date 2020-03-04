import { Component, EventEmitter, Output, OnInit, Input } from '@angular/core';
declare var UIkit: any;
declare var jQuery: any;
import { LookUpdataServiceService } from '../../common-services/look-updata-service.service';
import { ServerErrorcodeService } from "../../common-services/server-errorcode.service";
import { SpinnerServiceService } from "../../common-services/spinner-service.service";
;
import { SortSearchTableService } from "../../rcl-application/contract-search/sort-search-table.service";
import { SessionTimeOutService } from "app/common-services/session-time-out.service";

@Component({
  selector: 'app-door-lookup',
  templateUrl: './door-lookup.component.html',
  styleUrls: ['./door-lookup.component.scss']
})
export class DoorLookupComponent implements OnInit {

  showlookuptable: boolean = true;
  locLookUptableData: any = []
  advanceLocPickupTpye: string;
  advanceLocDropOffType: string;
  showLocErrorText: any;
  errorTextLookUp: any;
  haulageLookUpSelected: any;
  terminalLookUpSelected:any
  depotLookUpSelected:any;
  portLookUpSelected:any;
  locselectName: string;
  lookupErrorCodeShow: any;
  lookupErrorCodetext: any;
  lookupRow: any = [];
  resultsPerPage = 5;
  resultsPerPageArr = [1, 2, 3, 4, 5, 6, 7, 8, 9, 10];
  lookupSortIn: any;
  looUpOrderBy: any;
  lookupWildCard: boolean;
  _value: string;
  openModal: boolean = false;
  doorLookUpSelected: any;
  p1:number = 1;p2:number = 1;p3:number = 1;p4:number = 1;p5:number = 1

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

  portLookUpOption = [
    {
      label: 'Port Code',
      value: 'PICODE'
    },
    {
      label: 'Port Name',
      value: 'PINAME'
    },
    {
      label: 'Country Code',
      value: 'PICNCD'
    },
    {
      label: 'Zone Code',
      value: 'PIZONE'
    },
    {
      label: 'State Code',
      value: 'PIST'
    },
    {
      label: 'Status',
      value: 'PIRCST'
    }
  ]

  //LookUp sort options
  doorLookUpSortData = [{ label: 'Point Code', value: 'pointCode' }, { label: 'Point Name', value: 'poingName' }, { label: 'Country Code', value: 'countryCode' }, { label: 'Zone Code', value: 'zoneCode' }, { label: 'State Code', value: 'stateCode' }];

  terminalLookUpSortData = [{ label: 'Terminal', value: 'terminal' }, { label: 'Terminal Name', value: 'terminalName' }, { label: 'Port', value: 'port' }, { label: 'FSC', value: 'fsc' }];

  depotLookUpSortData = [{ label: 'Depot Code', value: 'depot' }, { label: 'Depot Name', value: 'depotName' }, { label: 'Depot Port', value: 'depotPort' }, { label: 'Point Code', value: 'fsc' }]

  haulageLookUpSortData = [{ label: 'Haulage Location Code', value: 'haulageLocationCode' }, { label: 'Inland Point', value: 'inlandPoint', }, { label: 'Haulage Location Name', value: 'hulageLocationName' }];



  portLookUpSortData = [{ label: 'Port Code', value: 'portCode' }, { label: 'Port Name', value: 'portName' }, { label: 'Country Code', value: 'country' }, { label: 'Zone Code', value: 'zone' }, { label: 'State Code', value: 'state' }];

  lookUpSortData = [{ label: 'Ascending', value: 'asnd' }, { label: 'Descending', value: 'dsnd' }];

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


  @Output() rowSelected = new EventEmitter<boolean>();

  constructor(private _lookupData: LookUpdataServiceService, public _spinner: SpinnerServiceService, private _serverErrorCode: ServerErrorcodeService, public _sortTable: SortSearchTableService,private _sessionTimeOutService:SessionTimeOutService) { }

  ngOnInit() {

  }


  openFromLookup(type, pickDropLoc) {
    jQuery('#PickDropModalLookUp1').detach();
    this.doorLookUpSelected = this.doorLookUpOption[0]['value'];//to have dafault value in point lookup
    this.terminalLookUpSelected = this.terminalLookUpOption[0]['value'];
    this.depotLookUpSelected = this.depotLookUpOption[0]['value'];
    this.haulageLookUpSelected = this.haulageLookUpOption[0]['value'];
    this.portLookUpSelected = this.portLookUpOption[0]['value'];
    this.lookupWildCard = true; // to have wild card checked by default
    this.advanceLocDropOffType = undefined;
    this.advanceLocPickupTpye = type;
    this.locselectName = pickDropLoc;

    if ((this.advanceLocPickupTpye && this.locselectName == "from")) {
      this.openModal = true;
      setTimeout(function () {
        UIkit.modal('#PickDropModalLookUp1').show();
      }, 100)
      this.showlookuptable = true;
    }

  }

  openToLookup(type, pickDropLoc) {
    jQuery('#PickDropModalLookUp1').detach();
    this.doorLookUpSelected = this.doorLookUpOption[0]['value'];//to have dafault value in point lookup
    this.terminalLookUpSelected = this.terminalLookUpOption[0]['value'];
    this.depotLookUpSelected = this.depotLookUpOption[0]['value'];
    this.haulageLookUpSelected = this.haulageLookUpOption[0]['value'];
    this.portLookUpSelected = this.portLookUpOption[0]['value'];
    this.lookupWildCard = true; // to have wild card checked by default
    this.advanceLocPickupTpye = undefined;
    this.advanceLocDropOffType = type;
    this.looUpOrderBy = "asnd";
    this.locselectName = pickDropLoc;
    if ((this.advanceLocDropOffType && this.locselectName == "to")) {
      this.openModal = true;
      setTimeout(function () {
        UIkit.modal('#PickDropModalLookUp1').show();
      }, 100)
      this.showlookuptable = true;
    }

  }
  resetPickDropModal(e) {
    this.openModal = false;
    this.resultsPerPage = 5;
    this.lookupSortIn = undefined;
    this.looUpOrderBy = undefined;
    this.showlookuptable = true;
    this.locLookUptableData = [];
    this.haulageLookUpSelected = undefined;
    this.advanceLocPickupTpye = undefined;
    this.advanceLocDropOffType = undefined;
    this.lookupWildCard = false;
    this.lookupErrorCodetext = undefined;
    this.lookupErrorCodeShow = false;
    this._value = "";
    this.doorLookUpSelected = undefined;
   

    //jQuery('#PickDropModalLookUp1').remove();
  }
  getLocLookUpData(advanceLocPickupTpye, type, eve, inpuvaluevalue, wildCard) {
    this.looUpOrderBy = "asnd";
    this.lookupSortIn = "portCode";
    if(advanceLocPickupTpye == "Port") {
      this.lookupSortIn = "portCode";
     }
    if(advanceLocPickupTpye == "Terminal") {
      this.lookupSortIn = "terminal";
     }
     if(advanceLocPickupTpye == "Door") {
      this.lookupSortIn = "pointCode";
     }

     if(advanceLocPickupTpye == "Depot") {
      this.lookupSortIn = "depot";
     }

     if(advanceLocPickupTpye == "Haulage") {
      this.lookupSortIn = "haulageLocationCode";
     }
     
     
    this._spinner.showSpinner();
    eve.stopPropagation();
    eve.preventDefault();
    eve.stopImmediatePropagation();

    var backendData = this._lookupData.getDataLookupService(advanceLocPickupTpye, type, eve, inpuvaluevalue, wildCard);
    backendData.subscribe(
      (data) => {
        if(data == "userSessionExpired"){
          UIkit.modal('#PickDropModalLookUp1').hide();
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
        this.p1 = 1;
        this.p2 = 1;
        this.p3 = 1;
        this.p4 = 1;
        this.p5 = 1;
        this._spinner.hideSpinner();
      },
      (err) => {
        this._spinner.hideSpinner();
        // A client-side or network error occurred. Handle it accordingly.
        this.lookupErrorCodetext = "Something Went wrong!! Please Try Again"
        this.lookupErrorCodeShow = true;
      }
    )
  }
  selectRowData(e, type) {
    this.doorLookUpSelected = undefined;  
    this.haulageLookUpSelected = undefined;
    this.resultsPerPage = 5;
    this.lookupSortIn = undefined;
    this.looUpOrderBy = undefined;

    this.openModal = false;
    UIkit.modal('#PickDropModalLookUp1').hide();
    this.advanceLocDropOffType = undefined;
    this.advanceLocPickupTpye = undefined;
    this.locselectName = undefined;
    this.locLookUptableData = [];
    this.lookupErrorCodetext = "";
    this.lookupErrorCodeShow = false;
    this.lookupWildCard = false;
    this._value = "";
    this.lookupRow[0] = e;
    this.lookupRow[1] = type;

    this.rowSelected.emit(this.lookupRow);
    //return this.lookupRow;
    //jQuery('#PickDropModalLookUp1').remove();

  }
  sortLookUpDataByColumn(e) {
    this.lookupSortIn = e.target.value;
    this._sortTable.sortTableData(this.locLookUptableData, this.lookupSortIn, this.looUpOrderBy);
  }
  sortLookUpDataByOrder(e) {
    this._sortTable.sortTableData(this.locLookUptableData, this.lookupSortIn, this.looUpOrderBy);
  }

}