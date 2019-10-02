import { Component, ViewChild, OnInit, Input, Output, EventEmitter, ViewContainerRef } from '@angular/core';
import { ProcessjoSortSearchTableService } from "../processjo-sort-search-table.service";
import * as _ from 'lodash';
import { SpinnerServiceService } from "../../../common-services/spinner-service.service";
import { ServerErrorcodeService } from "../../../common-services/server-errorcode.service";
import { ContainerListService } from "../../../common-services/container-list.service";
import { ProcessjoSearchService } from "../processjo-search.service";
import { ModalDialogModule, IModalDialog, ModalDialogService } from 'ngx-modal-dialog';
import { RCLContainerModalComponent } from '../../../rcl-components/rcl-container/rcl-container.component';
import { SessionTimeOutService } from "app/common-services/session-time-out.service";
//import { ProcessjoResultTableService } from "./processjo-result-table-service";
//import { ProcessjoSearchComponent } from "../processjo-search/processjo-search.component";
declare var jQuery: any;
declare var UIkit: any;

@Component({
  selector: 'app-processjo-result-table',
  templateUrl: './processjo-result-table.component.html',
  styleUrls: ['./processjo-result-table.component.scss']
})
export class ProcessjoResultTableComponent implements OnInit {


  resultsPerPage: number = 5;
  resultsPerPageArr = [1, 2, 3, 4, 5, 6, 7, 8, 9, 10];
  vendorCode: string = "";
  routeListInputValue: any = "";
  routeListBookingType: any = "bookingBL";
  showJoSummeryFlag: boolean = false;
  contType: any = "";
  private selectAllCheckBox: any;
  private pc = 1;
  private modalService: any;
  private viewRef: any;
  callingComponent: string
  findButton = document.getElementById('find-button');

  @Input() private processJoSearchData: any;
  @Input() private filteredTableDataProcessJoSearch: any;
  @Input() private filterDataSelectedComp: any;
  @Input() private filterData: any;
  @ViewChild('prosessjoRouteListBLBooking') _prosessjoRouteListBLBooking;
  @Output() showJoSummeryTable: EventEmitter<any> = new EventEmitter();
  @ViewChild('rclDGIMDG') _rclDGIMDG;
  @ViewChild('rclContainer') _rclContainer;
  @Output() refreshData: EventEmitter<any> = new EventEmitter();
  @Output() changeFindforChangeVendor: EventEmitter<any> = new EventEmitter();

  constructor(private _sortTable: ProcessjoSortSearchTableService, private _httpService: ProcessjoSearchService, private _errorCode: ServerErrorcodeService, private _spinner: SpinnerServiceService, private clService: ContainerListService, modalService: ModalDialogService, viewRef: ViewContainerRef, private _sessionTimeOutService: SessionTimeOutService ) {
    this.modalService = modalService;
    this.viewRef = viewRef;
    console.log(this.filteredTableDataProcessJoSearch);
  }

  ngOnInit() {
    console.log(this._httpService.processJoSearchData);
    this._httpService.processJoSearchData;
    //assigning values based on value coming from backend
    this.filteredTableDataProcessJoSearch.forEach(element => {
      if (element.cntSplHandling == "dg") {
        element.cntSplHandling = "DG";
        element.cntSplHandCount = element.dgCount;
      } else if (element.cntSplHandling == "Normal") {
        element.cntSplHandling = "Normal";
        element.cntSplHandCount = element.ladenCntTotal + element.emptyCntTotal;
      }
    });
    this.selectAllCheckBox = jQuery('#selectAllContractinProcessJo input[type="checkbox"]');
    this.clService.ladenSubject.subscribe((val) => {
      if (val.contType == 'L') {
        //exception handled to prevent container modal hanging      
        try {
          this.filteredTableDataProcessJoSearch[val.index]['ladenContainerList'] = val.data;
          this.filteredTableDataProcessJoSearch[val.index]['selectedRowLaden'] = val.selectedRow;
          this.updateContainer(val.data);
        } catch (error) {
          console.log('handling error while updating the containers selected');
        }
      } else {
        console.log('conatiner type should be E <<>> ' + val.contType)
        try {
          this.filteredTableDataProcessJoSearch[val.index]['emptyContainerList'] = val.data;
          this.filteredTableDataProcessJoSearch[val.index]['selectedRowEmpty'] = val.selectedRow;
          this.updateContainer(val.selectedRow);
        } catch (error) {
          console.log('handling error while updating the containers selected');
        }
      }
    });
  }

  onChangeOrderBy(data) {
    this.filterDataSelectedComp.orderBy = data;
    this.getFilterCriteria();
    let tempObj = {
      resultTable: this.filteredTableDataProcessJoSearch,
      filterData: this.filterDataSelectedComp
    }
    this.refreshData.emit(tempObj)
  }

  onChangeSortIn(data) {
    this.filterDataSelectedComp.sortIn = data;
    this.getFilterCriteria();
    let tempObj = {
      resultTable: this.filteredTableDataProcessJoSearch,
      filterData: this.filterDataSelectedComp
    }
    this.refreshData.emit(tempObj)
  }


  chnageVendorSelectedRow: any;
  showRouteListModalFrom(e) {
    if (this.checkedSelectedRows.length == 1) {
      this.showRouteListModal(e, this.checkedSelectedRows[0]);
    }

  }

  saveChangeVendorObj: any = {};

  showRouteListModal(e, row) {
    //delete this.chnageVendorSelectedRow['vendorChanged']; 
    this.chnageVendorSelectedRow = row;
    this._prosessjoRouteListBLBooking.inputValueLoc = row.fromLocation + '/' + row.toLocation;
    this._prosessjoRouteListBLBooking.inputValueTerminal = row.fromTerminal + '/' + row.toTerminal;
    this._prosessjoRouteListBLBooking.inputValueLocType = row.fromLocationTyp + '/' + row.toLocationTyp;

    this.saveChangeVendorObj['vendorIDOLD'] = row.vendorCode;
    this.saveChangeVendorObj['routingIdOLD'] = row.routingNumber;
    this.saveChangeVendorObj['bk_bl_ad'] = row.bkgOrBLType;
    this.saveChangeVendorObj['bkgblNumber'] = row.bkgOrBLNumber;
    //this.saveChangeVendorObj['transMode'] = this.processJoSearchData.processJoParam.transMode;//transport mode
    this._prosessjoRouteListBLBooking.transportType = this.processJoSearchData.processJoParam.transMode;//transport mode
    this._prosessjoRouteListBLBooking.joType = this.processJoSearchData.processJoParam.processJoType;//jo type
    this._prosessjoRouteListBLBooking.vendorCode = row.vendorCode;//vendor code
    this._prosessjoRouteListBLBooking.bookingType = row.bkgOrBLType;
    this._prosessjoRouteListBLBooking.inputSaleDateOrJobOrdDate = row.startDate;
    this.callingComponent = "processJo";
    this._prosessjoRouteListBLBooking.openLookUpModal(this.callingComponent);
  }

  updateRoute(e) {

    this.chnageVendorSelectedRow.vendorID = e.vendorCode;
    this.chnageVendorSelectedRow.vendorCode = e.vendorCode;
    this.chnageVendorSelectedRow.routingNumber = e.routingId;
    this.chnageVendorSelectedRow.fromLocation = e.fromLocation;
    this.chnageVendorSelectedRow.toLocation = e.toLocation;
    this.chnageVendorSelectedRow.fromTerminal = e.fromTerminal;
    this.chnageVendorSelectedRow.toTerminal = e.toTerminal;
    this.chnageVendorSelectedRow.transMode = e.legType;
    this.checkedSelectedRows[0] = this.chnageVendorSelectedRow;
    this.chnageVendorSelectedRow.transportMode = e.legType; //to reflect change transport mode
    this.chnageVendorSelectedRow['vendorChanged'] = 'vendor-changed';
    this.saveChangeVendorObj['contractId'] = e.contractId;
  }

  saveChangeVendor(e, row) {
    this.saveChangeVendorObj['vendorID'] = row.vendorID;
    this.saveChangeVendorObj['routingId'] = row.routingNumber;
    this.saveChangeVendorObj['contractId'] = row.routingId;
    this.saveChangeVendorObj['transMode'] = row.transMode;
    this.saveChangeVendorObj['action'] = "saveChangeVendor"
    this.chnageVendorSelectedRow['saveClicked'] = true;
    this._spinner.showSpinner();
    let backendData = this._httpService.getProcessjoSearchData(this.saveChangeVendorObj)
    backendData.subscribe(
      (data) => {
        if (data == "userSessionExpired") {
          this._sessionTimeOutService.checkSessionTimeout(data);
        }
        else if (!data['errorCode'].includes("MSG")) {
          this.getBackEndRefreshedTableData();
          this.checkedSelectedRows = [];
        }
        // this._spinner.hideSpinner();
      },
      (err) => {
        this._spinner.hideSpinner();

      }
    )
  }


  changeFind(e) {
    this.changeFindforChangeVendor.emit(e);
  }


  errorCodetext: string;
  getBackEndRefreshedTableData() {
    //this.refreshTableStatusFilter = false
    this._spinner.showSpinner();
    this.processJoSearchData['action'] = 'joSearch';
    let backendData = this._httpService.getProcessjoSearchData(this.processJoSearchData);
    backendData.subscribe(
      (data) => {
        if (data == "userSessionExpired") {
          this._sessionTimeOutService.checkSessionTimeout(data);
        }
        else if (data.hasOwnProperty("errorCode")) {
          this.errorCodetext = this._errorCode.checkError(data["errorCode"]);
          this._spinner.hideSpinner();
          UIkit.modal('#create-jo-summery-error-modal').show();
        } else {
          this.filteredTableDataProcessJoSearch = data;
          this.saveChangeVendorObj = {};
          this.chnageVendorSelectedRow['vendorChanged'] = 'vendor-changed';
          this._spinner.hideSpinner();
        }
      },
      (err) => {
        this._spinner.hideSpinner();
        console.log(err);
        this.errorCodetext = this._errorCode.checkError(err);
      }
    )
  }

  insertShowDGIMDG(e, row) {
    if (row.specialHandlingCode == 'O0' || row.specialHandlingCode == 'D1') {
      this._rclDGIMDG.openLookUpModal(row);
    }
  }

  containerClickedRow: any;
  insertshowContList(e, contType, searchType, bookingBlNum, cntSize, cntSplHandling, count, row, i) {
    if (count > 0) {
      console.log(this._httpService.processJoSearchData);
      this.containerClickedRow = this.filteredTableDataProcessJoSearch[i];
      this.containerClickedRow.selectedRowEmpty
      this.contbookingValue = '';
      //this.processJOSearch.processJoSearchData.processJoParam;
      this.openNewDialog(event, contType, searchType, bookingBlNum, cntSize, cntSplHandling, this.processJoSearchData.processJoParam.bookingType, this.processJoSearchData.processJoParam.processJoType, row, this.containerClickedRow['emptyContainerList'], this.containerClickedRow['ladenContainerList'], i, this.containerClickedRow.selectedRowLaden, this.containerClickedRow.selectedRowEmpty);
    }
  }

  openNewDialog(event, contType, searchType, bookingBlNum, cntSize, cntSplHandling, bookingType, processJoType, row, emptyContainerList, ladenContainerList, i, selectedRowLaden, selectedRowEmpty) {
    //to open modal for available empty containers
    if (contType == "E" && searchType == "AV") {
      this.modalService.openDialog(this.viewRef, {
        title: 'Please select available "Empty Container"',
        data: { event: event, contType: contType, searchType: searchType, bkgBlNumber: bookingBlNum, cntSize: cntSize, cntSplHandling: cntSplHandling, bookType: bookingType, jobType: processJoType, row: row, emptyContainer: emptyContainerList, ladenContainer: ladenContainerList, index: i, selectedRowLaden: selectedRowLaden, selectedRowEmpty: selectedRowEmpty },
        childComponent: RCLContainerModalComponent
      });
    } else if (contType == "E" && searchType == "T") { //to open total empty containers
      this.modalService.openDialog(this.viewRef, {
        title: 'Total Empty Container',
        data: { event: event, contType: contType, searchType: searchType, bkgBlNumber: bookingBlNum, cntSize: cntSize, cntSplHandling: cntSplHandling, bookType: bookingType, jobType: processJoType, row: row, emptyContainer: emptyContainerList, ladenContainer: ladenContainerList, index: i, selectedRowLaden: selectedRowLaden, selectedRowEmpty: selectedRowEmpty },
        childComponent: RCLContainerModalComponent
      });
    } else if (contType == "E" && searchType == "IN") { //to open in jo empty conatiners
      this.modalService.openDialog(this.viewRef, {
        title: 'In Jo Empty Container',
        data: { event: event, contType: contType, searchType: searchType, bkgBlNumber: bookingBlNum, cntSize: cntSize, cntSplHandling: cntSplHandling, bookType: bookingType, jobType: processJoType, row: row, emptyContainer: emptyContainerList, ladenContainer: ladenContainerList, index: i, selectedRowLaden: selectedRowLaden, selectedRowEmpty: selectedRowEmpty },
        childComponent: RCLContainerModalComponent
      });
    }
    if (contType == "L" && searchType == "AV") { //to open available laden containers
      this.modalService.openDialog(this.viewRef, {
        title: 'Please select available "Laden Container"',
        data: { event: event, contType: contType, searchType: searchType, bkgBlNumber: bookingBlNum, cntSize: cntSize, cntSplHandling: cntSplHandling, bookType: bookingType, jobType: processJoType, row: row, emptyContainer: emptyContainerList, ladenContainer: ladenContainerList, index: i, selectedRowLaden: selectedRowLaden, selectedRowEmpty: selectedRowEmpty },
        childComponent: RCLContainerModalComponent
      });
    } else if (contType == "L" && searchType == "T") { //to open total laden containers
      this.modalService.openDialog(this.viewRef, {
        title: 'Total Laden Container',
        data: { event: event, contType: contType, searchType: searchType, bkgBlNumber: bookingBlNum, cntSize: cntSize, cntSplHandling: cntSplHandling, bookType: bookingType, jobType: processJoType, row: row, emptyContainer: emptyContainerList, ladenContainer: ladenContainerList, index: i, selectedRowLaden: selectedRowLaden, selectedRowEmpty: selectedRowEmpty },
        childComponent: RCLContainerModalComponent
      });
    } else if (contType == "L" && searchType == "IN") {
      this.modalService.openDialog(this.viewRef, { //to open in jo laden containers
        title: 'In Jo Laden Container',
        data: { event: event, contType: contType, searchType: searchType, bkgBlNumber: bookingBlNum, cntSize: cntSize, cntSplHandling: cntSplHandling, bookType: bookingType, jobType: processJoType, row: row, emptyContainer: emptyContainerList, ladenContainer: ladenContainerList, index: i, selectedRowLaden: selectedRowLaden, selectedRowEmpty: selectedRowEmpty },
        childComponent: RCLContainerModalComponent
      });
    }
  }

  containerLists(e) {

    this.filteredTableDataProcessJoSearch[e.index]['ladenContainerList'] = e.data;
    console.log(this.filteredTableDataProcessJoSearch);
  }

  //select check boxes
  private checkedSelectedRows: any = [];
  private processJoSummeryFields: any = [];
  private processJoSummeryFields2: any = [];
  selectedRowContainer: any;

  processjoselectTableRowCheckBoxes(e, rowObj) {
    this.processJoSummeryFields = [];
    this.selectedRowContainer = rowObj
    if (e.target.checked) {
      this.checkedSelectedRows.push(rowObj);
    } else {
      this.checkedSelectedRows = this.deleteObjByJoNumber(this.checkedSelectedRows, rowObj);
      //to make checkboxes unchecked if one of the checkbox in the result table is unchecked
      if (this.selectAllCheckBox.checked == true) {
        this.selectAllCheckBox.checked = false;
      }
    }
    this.createProceeJoObjectForActions();
  }

  processjoselectTableRowCheckBoxAll(e) {
    this.selectAllCheckBox = e.target;
    if (e.target.checked) {
      this.processJoSummeryFields = [];
      if (jQuery('#processjo-table-container app-rcl-checkbox input[type="checkbox"]').length == this.resultsPerPage) {
        this.checkedSelectedRows = this.filteredTableDataProcessJoSearch.slice(((this.pc * this.resultsPerPage) - this.resultsPerPage), this.pc * this.resultsPerPage);
      } else {
        this.checkedSelectedRows = this.filteredTableDataProcessJoSearch.slice(((this.pc * this.resultsPerPage) - this.resultsPerPage), this.filteredTableDataProcessJoSearch.length);
      }
      this.checkedSelectedRows.forEach(element => {
        element['checked'] = true;
      });
    } else {
      this.filteredTableDataProcessJoSearch.forEach(element => {
        element['checked'] = false;
      });
      this.processJoSummeryFields = [];
      this.checkedSelectedRows = [];
    }
    this.createProceeJoObjectForActions();
  }

  createProceeJoObjectForActions() {
    for (let i = 0; i < this.checkedSelectedRows.length; i++) {
      let tempObj = {
        'bkgOrBLNumber': this.checkedSelectedRows[i].bkgOrBLNumber,
        'bkgOrBLType': this.checkedSelectedRows[i].bkgOrBLType,
        'fromTerminal': this.checkedSelectedRows[i].fromTerminal,
        'toTerminal': this.checkedSelectedRows[i].toTerminal,
        'fromLocation': this.checkedSelectedRows[i].fromLocation,
        'toLocation': this.checkedSelectedRows[i].toLocation,
        'routingNumber': this.checkedSelectedRows[i].routingNumber,
        'vendorCode': this.checkedSelectedRows[i].vendorCode,
        'cntSize': this.checkedSelectedRows[i].cntSize,
        'cntType': this.checkedSelectedRows[i].cntType,
        'cntSplHandling': this.checkedSelectedRows[i].cntSplHandling,
        'transportMode': this.checkedSelectedRows[i].transportMode,
        'fromLocationTyp': this.checkedSelectedRows[i].fromLocationTyp,
        'toLocationTyp': this.checkedSelectedRows[i].toLocationTyp,
        'lstContainerl': this.checkedSelectedRows[i]['lstContainerl'],
        'lstContainere': this.checkedSelectedRows[i]['lstContainere']
      }
      this.processJoSummeryFields.push(tempObj);
    }
  }
  //sai to send actual container weight to backend

  createProceeJoObjectForActionsToBackend(value) {
    this.processJoSummeryFields2 = [];
    for (let i = 0; i < this.containerClickedRow.ladenContainerList.length; i++) {
      let tempObj = {
        'container': this.containerClickedRow.ladenContainerList[i]['container'],
        'actualContainerWeight': this.containerClickedRow.ladenContainerList[i]['actualContainerWeight'],
        'oldContainerWeight': this.containerClickedRow.ladenContainerList[i]['containerWeight'],
        'containerSize': this.containerClickedRow['cntSize'],
        'containerType': this.containerClickedRow['cntType'],
        'specialHandling': this.containerClickedRow['cntSplHandling'],
        'bookingOrBLNumber': this.containerClickedRow['bkgOrBLNumber'],
        'bookingOrBLType': this.containerClickedRow['bkgOrBLType'],
        'fromLocation': this.containerClickedRow['fromLocation'],
        'fromTerminal': this.containerClickedRow['fromTerminal'],
        'toLocation': this.containerClickedRow['toLocation'],
        'toTerminal': this.containerClickedRow['toTerminal'],
        'vendorCode': this.containerClickedRow['vendorCode'],
      }
      if(tempObj.actualContainerWeight != null) {
        this.processJoSummeryFields2.push(tempObj);
      }
      
    }
    this.getBackEndDataForActContWeight(this.findButton);
  }
  private getBackEndDataForActContWeight(findButton) {
    this.errorCodetext = "";
    let backendData = this._httpService.getProcessTableResultSearchData(this.processJoSummeryFields2);
    backendData.subscribe(
      (data) => {

      }
    )
  }

  processJoPageChange(e) {
    this.pc = e;
    if (this.selectAllCheckBox['checked']) {
      this.filteredTableDataProcessJoSearch.forEach(element => {
        element['checked'] = true;
        this.selectAllCheckBox['checked'] = true;
      });
      this.processJoSummeryFields = [];
      this.checkedSelectedRows = [];
      //this.selectAllCheckBox['checked'] = false;
    }

  }

  saveErrorText: string;
  showJoSummery(e) {
    this.showJoSummeryFlag = true;

    //checking whether vendor is changed
    if (this.chnageVendorSelectedRow != undefined) {

      //if save button clicked after vendor change
      if (this.chnageVendorSelectedRow.saveClicked) {
        this.showJoSummeryFlag = false;
        this.saveAllSelect();

      } else { //if save button not clicked after vendor change
        this.showJoSummeryFlag = false;
        this.saveErrorText = "Please save the changed vendor first"
        UIkit.modal('#create-jo-summery-save-error').show();
        //alert('first save then click save all select');
      }
    } else {//when vendor is not chnaged then save all directly
      this.saveAllSelect();
    }
  }

  //method to save jo summary when different validations are satisfied
  saveAllSelect() {
    if (this.processJoSummeryFields.length > 0) {
      let tempObj = {
        'lstJOSummaryParam': this.processJoSummeryFields,
        'action': 'joSummary',
        'processJoType': this.processJoSearchData.processJoParam.processJoType,
        'transMode': this.processJoSearchData.processJoParam.transMode
      }
      this.showJoSummeryTable.emit(tempObj);
    }
  }

  //method to close click save first validation error
  clickSave() {
    UIkit.modal('#create-jo-summery-save-error').hide();
  }


  private deleteObjByJoNumber(arr, rowObj) {
    let filterBo = [];
    arr.forEach(element => {
      if (element.bkgOrBLNumber == rowObj.bkgOrBLNumber) {
        filterBo = arr.filter(arr => arr.bkgOrBLNumber != rowObj.bkgOrBLNumber);
      }
    });

    return filterBo;
  }

  private lstContainerl: any = [];
  private lstContainere: any = [];
  containerList: any;
  contbookingValue = '';
  updateContainer(e) {
    this.lstContainerl = [];
    this.lstContainere = [];
    e.forEach(element => {
      if (element['bookingNumber'] == this.contbookingValue) {
        if (element['contType'] == 'L') {

          this.lstContainerl.push(element['container']);
          this.lstContainerl.push(element['actualContainerWeight']);
        } else if (element['contType'] == 'E') {
          this.lstContainere.push(element['container']);
        }
      } else {
      //  this.lstContainerl = [];
       // this.lstContainere = [];
        if (element['contType'] == 'L') {
          this.lstContainerl.push(element['container']);
          this.lstContainerl.push(element['actualContainerWeight']);
        } else if (element['contType'] == 'E') {
          this.lstContainere.push(element['container']);
        }
      }
      this.contbookingValue = element['bookingNumber'];
    });
    this.containerClickedRow['lstContainere'] = this.lstContainere;
    this.containerClickedRow['lstContainerl'] = this.lstContainerl;
    //this.containerClickedRow['selectedRow'] = e;
    this.processJoSummeryFields = []; //empty the object containing the selected rows
    this.createProceeJoObjectForActions();//to create object after the record is already checked  
    //to send data to backend
    this.createProceeJoObjectForActionsToBackend(this.containerClickedRow);
  }

  desellectSelectedRows() {
    this.filteredTableDataProcessJoSearch.forEach(element => {
      element['checked'] = false;
    });

    this.checkedSelectedRows = [];

  }

  getFilterCriteria() {
    if (this.filterDataSelectedComp.orderBy == "asnd") {
      this.filterDataSelectedComp.orderByName = "Ascending";
    }
    if (this.filterDataSelectedComp.orderBy == "dsnd") {
      this.filterDataSelectedComp.orderByName = "Descending";
    }
    if (this.filterDataSelectedComp.sortIn == "bkgOrBLNumber") {
      this.filterDataSelectedComp.sortInName = "Booking/BL";
    }
    if (this.filterDataSelectedComp.sortIn == "transMode") {
      this.filterDataSelectedComp.sortInName = "Transport Mode";
    }
    if (this.filterDataSelectedComp.sortIn == "startDate") {
      this.filterDataSelectedComp.sortInName = "EFFECTIVE DATE";
    }
    if (this.filterDataSelectedComp.sortIn == "endDate") {
      this.filterDataSelectedComp.sortInName = "EXPIRY DATE";
    }
    if (this.filterDataSelectedComp.sortIn == "fromTerminal") {
      this.filterDataSelectedComp.sortInName = "From";
    }
    if (this.filterDataSelectedComp.sortIn == "toTerminal") {
      this.filterDataSelectedComp.sortInName = "To";
    }
  }
}
