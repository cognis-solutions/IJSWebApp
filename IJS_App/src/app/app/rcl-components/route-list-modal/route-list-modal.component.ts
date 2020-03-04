import { Component, OnInit, ViewChild, Input, Output, EventEmitter } from '@angular/core';
import { NgModel, NG_VALUE_ACCESSOR, NG_VALIDATORS, NG_ASYNC_VALIDATORS } from '@angular/forms';
import { noop } from 'rxjs/util/noop';
import { SpinnerServiceService } from "../../common-services/spinner-service.service";
import { LookUpdataServiceService } from '../../common-services/look-updata-service.service';
import { ServerErrorcodeService } from "../../common-services/server-errorcode.service";
import { SortSearchTableService } from '../../rcl-application//contract-search/sort-search-table.service';
//import {ProcessjoSearchComponent} from "app/rcl-application/process-jo/processjo-search/processjo-search.component"
import { Http, Response, Headers, URLSearchParams, RequestOptions } from '@angular/http';
import { Observable } from 'rxjs/Observable';
import 'rxjs/add/operator/map';
import 'rxjs/add/operator/catch';
import { SessionTimeOutService } from "app/common-services/session-time-out.service";
import { TestBed } from '@angular/core/testing';
import { ProcessjoSearchService } from 'app/rcl-application/process-jo/processjo-search.service';
//import { ProcessjoSearchService } from "app/rcl-application/process-jo/processjo-search.service";

declare var UIkit: any;
declare var jQuery: any;


@Component({
  selector: 'app-route-list-modal',
  templateUrl: './route-list-modal.component.html',
  styleUrls: ['./route-list-modal.component.scss']
})
export class RouteListModalComponent implements OnInit {


  @Input() private cntSplHandling: any;
  @Input() private cntSize :any;
  @Input() private cntType: any;
  @Input() private bkgOrBLNumber:any;
  @Input() private bookingType: any;
  @Input() private inputValueLoc: any;
  @Input() private inputValueTerminal: any;
  @Input() private inputValueLocType: any;
  @Input() private inputSaleDateOrJobOrdDate: any;
  @Input() private vendorCode: any;
  @Input() private transportType: any;
  @Input() private joType: any;
  @Input() private bargeValue: any;
  @Input() private fromLocation:any;
  @Input() private toLocation:any;
  // @Input() private fromLoc:any;
  // @Input() private toLoc:any;
  // @Input() private transMode:any;
  // @Input() private vendorCd:any;
 
  actionparam = "lookupRouteList";
  @Output() selectUpdateRouteList = new EventEmitter();
  //@Output() processJoSearchVendors= new EventEmitter();
 
  //@Output() searchRouteList:EventEmitter<any> = new EventEmitter();

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
 // public _sortTable: SortSearchTableService
  constructor(public _joborderService: ProcessjoSearchService,public _sortTable: SortSearchTableService,private _spinner: SpinnerServiceService, private _lookupData: LookUpdataServiceService, public _serverErrorCode: ServerErrorcodeService, private _http: Http,private _sessionTimeOutService:SessionTimeOutService) { }

  ngOnInit() {
    //this.openLookUpModal();
    // console.log(this._joborderService.processJoSearchData)
    // alert(this._joborderService.processJoSearchData)
    // this.fromLoc=this._joborderService.processJoSearchData.processJoParam.fromLocation;
    // this.toLoc=this._joborderService.processJoSearchData.processJoParam.toLocation;
    // alert(this.fromLoc + this.toLoc )
    
    
  }

  
//    processJoSearchVendors: any = {
//      "processJoSearchVendors": {
      
//     },
  
//    }
  
  
// //nikash
// openLookUpModalNew(callingComponent){
//   alert("openLookUpModalNew");

//   // jQuery("#lookup-popup-input-vendor").detach();
//    jQuery("#lookup-popup-input-vendor").detach();
// //this.getLocLookUpDataRouteListNew(this.actionparam,this.fromLocation,this.toLocation);
// this.openModal = true;
// this.showlookuptable = true;
// this.lookupErrorCodeShow = false;
// this.looUpOrderBy="asnd";
// this.lookupSortIn = "contractId";
// this.selectedRow = [];
// this.checkComponent = callingComponent;
// this._spinner.showSpinner();
// this.getLocLookUpDataRouteListNew(this.actionparam, this.bookingType, this.inputValueLoc,
//   this.inputValueTerminal,this.inputValueLocType,this.inputSaleDateOrJobOrdDate,
//   this.vendorCode,this.transportType,this.joType,this.checkComponent,
//   this.bargeValue,this.cntSize,this.cntType,this.bkgOrBLNumber,this.cntSplHandling);
// setTimeout(function () {
//   UIkit.modal('#lookup-popup-input-vendor').show();
// }, 100);

//    }
// getLocLookUpDataRouteListNew(actionparam, bookingType, inputValueLoc,
//   inputValueTerminal,inputValueLocType,inputSaleDateOrJobOrdDate,
//   vendorCode,transportType,joType,checkComponent,bargeValue,cntSize,cntType,bkgOrBLNumber,cntSplHandling)
//  {
  
//   this._spinner.showSpinner();
//    this._lookupData.getDataLookupServiceJORoutingNew(actionparam, bookingType, inputValueLoc,
//     inputValueTerminal,inputValueLocType,inputSaleDateOrJobOrdDate,
//     vendorCode,transportType,joType,checkComponent,bargeValue,cntSize,cntType,bkgOrBLNumber,cntSplHandling).subscribe(
//      data => {
       
//        if(data == "userSessionExpired"){
//        UIkit.modal('#lookup-popup-input-vendor').hide();
//           this._sessionTimeOutService.checkSessionTimeout(data);
//      }
//       else if (data.hasOwnProperty("errorCode")) {
//         this.lookupErrorCodetext = this._serverErrorCode.checkError(data["errorCode"]);
//         this.lookupErrorCodeShow = true;
//         this.showlookuptable = true;
//      }
//          else {
//            this.showlookuptable = false;
//            this.lookupErrorCodetext = undefined;
//            this.lookupErrorCodeShow = false;
//            this.routeListTableData = data; 
//            alert('routeListTableData');
          
          
//           // this._lookupData.processJoSearchVendors= this.routeListTableData;
//          // this.processJoSearchVendors.emit(this.routeListTableData);
//         }
//          this.pc = 1;
//          this._spinner.hideSpinner();
//        //return data;
//       },
//        (err) => {
//          this.lookupErrorCodetext = "Something went wrong please try again";
//        this.lookupErrorCodeShow = true;
//         this._spinner.hideSpinner();
//       }
//    )
  
//    }
  

multiple:boolean=false;

openLookUpModalMultiVendor(row,joType,singleloc,callingComponent)
{
  this.multiple=true;
  this.openModal=true;
  
  setTimeout(function () {
  UIkit.modal('#vendor-costCalc-popup').show();
}, 100);

  var backendData = this._lookupData.getDataServiceForMultiple(row,joType,singleloc,callingComponent);
    backendData.subscribe(
      (data) => {
        
        if(data == "userSessionExpired"){
          UIkit.modal('#vendor-costCalc-popup').hide();
          this._sessionTimeOutService.checkSessionTimeout(data);
        }
       else if(data==null || data==undefined)
        {
          this.lookupErrorCodetext = this._serverErrorCode.checkError("IJS_EX_10001");
          this.lookupErrorCodeShow = true;
          this.showlookuptable = true;
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







  openLookUpModal(callingComponent) {
    //debugger;
    jQuery("#route-list-modal-center").detach();

//debugger;
if(callingComponent!="jomaintenance"){
    if(this._joborderService.processJoSearchData.processJoParam.processJoType=="ER"||this._joborderService.processJoSearchData.processJoParam.processJoType=="LAH")
{
    // this.fromLoc=this._joborderService.processJoSearchData.processJoParam.fromLocation;
    // this.toLoc=this._joborderService.processJoSearchData.processJoParam.toLocation;
    // this.transMode=this._joborderService.processJoSearchData.processJoParam.transMode;
    // this.vendorCd = this._joborderService.processJoSearchData.processJoParam.vendorCode;

    this.inputValueLoc = this._joborderService.processJoSearchData.processJoParam.fromLocation
                   + '/' + this._joborderService.processJoSearchData.processJoParam.toLocation;

     //console.log(this.inputValueLoc);
    this.inputValueTerminal = this._joborderService.processJoSearchData.processJoParam.fromTerminal
                   + '/' + this._joborderService.processJoSearchData.processJoParam.toTerminal;

                 //console.log(this.inputValueTerminal);
    this.inputValueLocType = this._joborderService.processJoSearchData.processJoParam.fromLocType
                   + '/' + this._joborderService.processJoSearchData.processJoParam.fromLocType;
    
                  // console.log(this.inputValueLocType);
    this.transportType = this._joborderService.processJoSearchData.processJoParam.transMode;

    this.vendorCode = this._joborderService.processJoSearchData.processJoParam.vendorCode;
    this.bookingType= "adhoc"; 
}
}
    this.openModal = true;
    this.showlookuptable = true;
    this.lookupErrorCodeShow = false;
    this.looUpOrderBy="asnd";
    this.lookupSortIn = "contractId";
    this.selectedRow = [];
    this.checkComponent = callingComponent;
    this._spinner.showSpinner();
    this.getLocLookUpDataRouteList(this.actionparam, this.bookingType, this.inputValueLoc,this.inputValueTerminal,this.inputValueLocType,this.inputSaleDateOrJobOrdDate,this.vendorCode,this.transportType,this.joType,this.checkComponent,this.bargeValue);
    setTimeout(function () {
      UIkit.modal('#route-list-modal-center').show();
    }, 100);
  }


  getLocLookUpDataRouteList(actionparam, bookingType, inputValueLoc,inputValueTerminal,inputValueLocType,inputSaleDateOrJobOrdDate,vendorCode,transportType,joType,checkComponent,bargeValue) {
    this._spinner.showSpinner();
    var backendData = this._lookupData.getDataLookupServiceJORouting(actionparam, bookingType, '', inputValueLoc, '',inputValueTerminal,inputValueLocType,inputSaleDateOrJobOrdDate,vendorCode,transportType,joType,checkComponent,bargeValue);
    backendData.subscribe(
      (data) => {
        
        if(data == "userSessionExpired"){
          UIkit.modal('#route-list-modal-center').hide();
          this._sessionTimeOutService.checkSessionTimeout(data);
        }
       else if(data==null || data==undefined)
        {
          this.lookupErrorCodetext = this._serverErrorCode.checkError("IJS_EX_10001");
          this.lookupErrorCodeShow = true;
          this.showlookuptable = true;
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
  private showLocErrorText: boolean = false;
  private errorTextLookUp: string   
  //Nikash
  private getRowData(e, rowData, i) 
  {
  this.selectedRow = [];
  let selected = rowData.selected;
  this.deselectAll(this.routeListTableData);
  rowData.selected = !selected;
  rowData['checked'] = rowData.selected;
 
  for(i=0;i<this.routeListTableData.length;i++)
  {
  if(rowData['priority']>this.routeListTableData[i]['priority'])
  {
    
    alert("The Selected Row Has Less Priority!!");
  //UIkit.modal('#priorityExceedModal').show();
  
    break;
  }
  }
  this.selectedRow.push(rowData);
    // if (e.target.checked == true) {
    // rowData['selected'] = true;
    // this.selectedRow.push(rowData);
    // } else {
    // this.selectedRow = this.deleteObjByRoutingId(this.selectedRow, 'routingId', rowData.routingId);
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
    //alert("update.....");  
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
