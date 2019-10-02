import { Component, Input, OnInit, ViewChild, Output, EventEmitter,ViewContainerRef } from'@angular/core';
import { GridOptions } from'ag-grid';
declare var UIkit: any;
declare var jQuery: any;
import { JoMaintenanceSortingService } from "../jo-maintenance-sorting.service";
import { SpinnerServiceService } from "../../../common-services/spinner-service.service";
import { ServerErrorcodeService } from "../../../common-services/server-errorcode.service";
import { JoMaintenanceSearchService } from "../jo-maintenance-search.service";
import { RclappUrlService } from "../../../common-services/rclapp-url.service";
import { WindowRefService } from "../../../common-services/window-ref.service";
import * as _ from 'lodash';
import * as FileSaver from 'file-saver';
import { ModalDialogModule,IModalDialog,ModalDialogService } from 'ngx-modal-dialog';//done on 15 jan 2019
import { RCLContainerModalComponent } from "app/rcl-components/rcl-container/rcl-container.component";
import { ContainerListService } from "app/common-services/container-list.service";
declare var jQuery: any;
import * as $ from 'jquery';
import { SessionTimeOutService } from "app/common-services/session-time-out.service";
declare var UIkit: any;

@Component({
  selector: 'app-jo-maintenanceresult-table',
  templateUrl: './jo-maintenanceresult-table.component.html',
  styleUrls: ['./jo-maintenanceresult-table.component.scss']
})
export class JoMaintenanceresultTableComponent implements OnInit {

  private Amount: string; //Remark

  amountUpdData: any = {
    "amountUpdParam": {
      //   transMode: "Select Transport",
      //   bookingType: "Booking/BL #"
      amount: ""
    },
    "action": "joSearch"
  }

  nativeWindow: any

  constructor(private _maintenanceService: JoMaintenanceSearchService, private _serverErrorCode: ServerErrorcodeService, private _spinner: SpinnerServiceService, private _sortTable: JoMaintenanceSortingService, private _rclappUrlService: RclappUrlService, private winRef: WindowRefService
  ,private spinner: SpinnerServiceService,modalService: ModalDialogService, viewRef: ViewContainerRef,private clService:ContainerListService,private _sessionTimeOutService:SessionTimeOutService) {
    this.nativeWindow = winRef.nativeWindow();    
    this.modalService = modalService;
    this.viewRef = viewRef; 
  }

  public checkBoxFilterArray:any = [];
  public isCollapsed: boolean = false;
  private gridOptions: GridOptions;
  private columnDefs2: any[];
  private EqDetailsList: any;
  private showLocErrorText: boolean = false;
  private errorTextLookUp: string;

  private resultsPerPage: number = 10;
  private resultsPerPageArr = [10, 25, 50, 100];
  private refreshTableStatusFilter = true;
  private p = 1;
  private selectAllCheckBox: any;
  jobOrderCancelText = "";
  @Input() private componentType: any;
  @Input() private filterData: any;
  @Input() private filterDataSelectedComp: any;
  @Input() private tableDataMaintainJoSearch: any;
  @Input() private maintainJoSearchData: any;
  @Input() private viewJoUrl: any;
  @Input() private socCocType:any;
  @ViewChild('joInquiryEquipmentBrowser') _joInquiryEquipmentBrowser;
  @ViewChild('rclBookingBL') _rclBookingBL;
  @ViewChild('rclJoLog') _rclJoLog;
  @ViewChild('maintainjoRouteList') _maintainjoRouteList;
  @ViewChild('maintainJoContainer') _maintainJoContainer;
  @Output() refreshBackEndData: EventEmitter<any> = new EventEmitter();
  @Output() refreshData: EventEmitter<any> = new EventEmitter();
  @Output() changeFindforCancelledJo: EventEmitter<any> = new EventEmitter();
  // @Output() private totalRecords :any
  @ViewChild('vehicleTips') vehicleTips;
  @Input() private totalRecords :any
  @Input() private checkJobOrdStsText :any  
  private modalService:any; //done on 15 jan 2019
  private viewRef:any; //done on 15 jan 2019
  tableDataMaintainJoSearch1: any;
  fscLookUpData: any = [{ "label": "FSC", "value": "fsc", "dropDownData": [{ "label": "FSC Code", "value": "FSCCD" }, { "label": "FSC Description", "value": "FSCDESC" }, { "label": "Company Name", "value": "CMPNM" }, { "label": "City", "value": "FSCCITY" }, { "label": "State", "value": "FSCSTAT" }, { "label": "Country", "value": "FSCCCNTY" }, { "label": "Status", "value": "FSCSTS" }] }, { "label": "FSC Name", "value": "FSCNM" }];
  filterOptions:any = [];
  joMaintainenceStatus: any = [];
  joMaintainenceStatusText:any = [];
  initialtableDataMaintenanceJoSearch:any=[];
  colhide: boolean;
  //#NIIT CR1 >>>>BEGIN
  @Output() uploadExcel: EventEmitter<any> = new EventEmitter();
  @Input() private validContainerList :any;
  //#NIIT CR1 >>>>END

  ngOnInit() {
    //call method to replace old containers with new containers
    this.clService.ladenSubject.subscribe((val)=>{  
      try{
        this.updateEqDetails(val.selectedRow);
      }catch(e){
        console.log("error");
      }                   
    });
    
   

   if(this.maintainJoSearchData.maintainJoParam['jobOrdSts'] != undefined){
    this.allCheckedFlag = false;
   }

  //  if(this.maintainJoSearchData.maintainJoParam['SocOrCoc'] == "S"){
  //     this.socCocOptions = ["S"];
  //  }
  //  if(this.maintainJoSearchData.maintainJoParam['SocOrCoc'] == "C"){
  //     this.socCocOptions = ["C"];
  //  }
  //  if(this.maintainJoSearchData.maintainJoParam['SocOrCoc'] == undefined 
  //  || this.maintainJoSearchData.maintainJoParam['SocOrCoc'] == ""){
  //    this.socCocOptions = ["S","C"];
  //  }

  if(this.maintainJoSearchData.maintainJoParam['SocOrCoc'] == "S"){
    this.socCocOptions = ["S"];
  } else if(this.maintainJoSearchData.maintainJoParam['SocOrCoc'] == "C"){
      this.socCocOptions = ["C"];
  }else{
    this.socCocOptions = ["S","C"];
  }
 

    this.initialtableDataMaintenanceJoSearch = this.tableDataMaintainJoSearch;

    //condition to make contPercent 0 or 100 based on Empty or Laden    
    this.tableDataMaintainJoSearch[0].contDetailJO.forEach(element => {
      if(element.contEmptyOrLaden == "Empty"){
        element.contPercent = "0";
      }else{
        element.contPercent = "100";
      }
    });
    
    //this.filterDataSelectedComp.sortIn = "JoNumber"; //show default sort in sort in
    //this.filterDataSelectedComp.orderBy = "asnd"; //show default order in order by
    if (this.componentType == 'joinquiry') {
      this.colhide = true;
      //new filter functionality <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
      //Filter options for jo Inquiry
      this.filterOptions = [
          { name: 'SOC', selected: (this.socCocType == 'S' || this.socCocType == undefined || this.socCocType.trim().length == 0)  ? true : false  , id: 'SOCorCOC' },
          { name: 'COC', selected: (this.socCocType == 'C' || this.socCocType == undefined || this.socCocType.trim().length == 0)  ? true : false , id: 'SOCorCOC' },
          { name: 'Start', selected: (this.checkJobOrdStsText == 'Start' || this.checkJobOrdStsText == 'Job Order status')  ? true : false , id: 'status'  },
          { name: 'Entry', selected: (this.checkJobOrdStsText == 'Entry' || this.checkJobOrdStsText == 'Job Order status')  ? true : false , id: 'status' },
          { name: 'Issued',selected: (this.checkJobOrdStsText == 'Issued' || this.checkJobOrdStsText == 'Job Order status')  ? true : false , id: 'status'  },
          { name: 'Waitlisted', selected: (this.checkJobOrdStsText == 'Waitlisted' || this.checkJobOrdStsText == 'Job Order status')  ? true : false , id: 'status'  },
          { name: 'Rejected', selected: (this.checkJobOrdStsText == 'Rejected' || this.checkJobOrdStsText == 'Job Order status')  ? true : false , id: 'status'  },
          { name: 'Pending', selected: (this.checkJobOrdStsText == 'Pending' || this.checkJobOrdStsText == 'Job Order status')  ? true : false , id: 'status'  },
          { name: 'Completed', selected: (this.checkJobOrdStsText == 'Completed' || this.checkJobOrdStsText == 'Job Order status')  ? true : false , id: 'status'  },
          { name: 'Cancelled', selected: (this.checkJobOrdStsText == 'Cancelled' || this.checkJobOrdStsText == 'Job Order status')  ? true : false , id: 'status'  }
        ];  
      // if(this.joMaintainenceStatus.length == 0){
      //   this.joMaintainenceStatus = ['S','E','I','D','W','P','M','C']; 
      // }  
        
      //new filter functionality <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
    } else {
      this.colhide = false;     
      //new filter functionality <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
      //Filter options for jo Maintenance
      this.filterOptions = [
          { name: 'SOC', selected: (this.socCocType == 'S' || this.socCocType == undefined || this.socCocType.trim().length == 0)  ? true : false  , id: 'SOCorCOC' },
          { name: 'COC', selected: (this.socCocType == 'C' || this.socCocType == undefined || this.socCocType.trim().length == 0)  ? true : false , id: 'SOCorCOC' },
          { name: 'Start', selected: (this.checkJobOrdStsText == 'Start' || this.checkJobOrdStsText == 'Job Order status')  ? true : false , id: 'status'  },
          { name: 'Entry', selected: (this.checkJobOrdStsText == 'Entry' || this.checkJobOrdStsText == 'Job Order status')  ? true : false , id: 'status' },
          { name: 'Issued',selected: (this.checkJobOrdStsText == 'Issued' || this.checkJobOrdStsText == 'Job Order status')  ? true : false , id: 'status'  },
          { name: 'Waitlisted', selected: (this.checkJobOrdStsText == 'Waitlisted' || this.checkJobOrdStsText == 'Job Order status')  ? true : false , id: 'status'  },
          { name: 'Rejected', selected: (this.checkJobOrdStsText == 'Rejected' || this.checkJobOrdStsText == 'Job Order status')  ? true : false , id: 'status'  },
          { name: 'Pending', selected: (this.checkJobOrdStsText == 'Pending' || this.checkJobOrdStsText == 'Job Order status')  ? true : false , id: 'status'  }
   ];  
      //   if(this.joMaintainenceStatus.length == 0){
      //   this.joMaintainenceStatus = ['S','E','I','D','W','P']; 
      // }  
                
      //new filter functionality <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
    }
    //this.tableDataMaintainJoSearch1 = this.tableDataMaintainJoSearch;
    this.columnDefs2 = [
      {
        headerName: '#', width: 30,checkboxSelection: true, suppressSorting: true,
        suppressMenu: true, hide: this.colhide, cellStyle: {
          'padding-top': '2px',
        }
      },
      {
        headerName: 'Equip. state', field: 'equiptState', width: 100, minWidth: 100, maxWidth: 130, cellStyle: {
          'padding-top': '2px',
        }
      },
      {
        headerName: 'Equip #', field: 'eqNumber', width: 110, minWidth: 110, maxWidth: 130, cellStyle: {
          'padding-top': '2px', 'color': '#0099FF'
        }
      },
      {
        headerName: 'Booking/BL', field: 'BkgOrBLNo', width: 120, minWidth: 120, maxWidth: 150, cellStyle: {
          'padding-top': '2px', 'color': '#0099FF',
          'text-decoration': ' underline'
        }
      },
      {
        headerName: 'Container Detail', field: 'conDtk', width: 200, minWidth: 200, maxWidth: 210, cellStyle: {
          'padding-top': '2px',
        },
        cellRenderer: function (params) {      // Function cell renderer
          return conDtlRender(params);
        }
      },
      {
        headerName: 'SOC/COC', field: 'SOCorCOC', width: 90, cellStyle: {
          'padding-top': '2px',
        },
        cellRenderer: function (params) {      // Function cell renderer
          return stweightRender(params);
        }
      },
      {
        headerName: 'Date', field: 'dt', width: 200, cellStyle: {
          'padding-top': '2px',
        },

        cellRenderer: function (params) {      // Function cell renderer
          return stEndDtRender(params);
        }
      },

      {
        headerName: 'Group Sample',
        children: [
          {
            headerName: "DG/RF/OGG", field: "DGorRForOG", width: 125, cellStyle: { 'padding-top': '2px' }, columnGroupShow: 'close'
          },
          ,
          {
            headerName: "Port class", field: "portClass", width: 75, cellStyle: { 'padding-top': '2px' }, columnGroupShow: 'open'
          },
          {
            headerName: "IMDG Class", field: "imdgClass", width: 75, cellStyle: { 'padding-top': '2px' }, columnGroupShow: 'open'
          },
          {
            headerName: "UNNO", field: "unno", width: 75, cellStyle: { 'padding-top': '2px' }, columnGroupShow: 'open'
          },
          {
            headerName: "Variant", field: "variant", width: 75, cellStyle: { 'padding-top': '2px' }, columnGroupShow: 'open'
          },
          {
            headerName: "Flash Point", field: "flashPoint", width: 75, cellStyle: { 'padding-top': '2px' }, columnGroupShow: 'open'
          },
          {
            headerName: "Temp From", field: "tempFrom", width: 75, cellStyle: { 'padding-top': '2px' }, columnGroupShow: 'open'
          },
          ,
          {
            headerName: "Temp To", field: "tempTo", width: 75, cellStyle: { 'padding-top': '2px' }, columnGroupShow: 'open'
          },
          {
            headerName: "OH", field: "OH", width: 75, cellStyle: { 'padding-top': '2px' }, columnGroupShow: 'open'
          },
          {
            headerName: "OLF", field: "OLF", width: 75, cellStyle: { 'padding-top': '2px' }, columnGroupShow: 'open'
          },
          {
            headerName: "OWL", field: "OWL", width: 75, cellStyle: { 'padding-top': '2px' }, columnGroupShow: 'open'
          },
          {
            headerName: "OWR", field: "OWR", width: 75, cellStyle: { 'padding-top': '2px' }, columnGroupShow: 'open'
          },
          {
            headerName: "OLA", field: "OLA", width: 75, cellStyle: { 'padding-top': '2px' }, columnGroupShow: 'open'
          }
        ]
      },
      // { headerName: 'Currency', field: 'currency', width: 70, minWidth: 70, maxWidth: 150, cellStyle: { 'padding-top': '2px' } },
      
      //#NIIT CR6 >>>>BEGIN
      //adding Size Type for jo miantenance CR 
      { headerName: 'Size Type Based Cost', class:'sizeType', width: 225, minWidth: 70, maxWidth: 300, cellStyle: { 'padding-top': '2px'},
                    headerClass: 'size-type-group',
                    children: [
                        {headerName: 'Amount', field: 'amount', width: 110,minWidth: 75, maxWidth: 150, 
                        cellStyle: { 'padding-top': '2px' },
                        cellRenderer: function(params){
                          return sizeTypeCostRenderer(params);
                        }},
                        {headerName: 'Amount USD', field: 'amountUSD', width: 110, minWidth: 75, maxWidth: 150, cellStyle: { 'padding-top': '2px' }},
                        
                    ]
      },
      //adding BKG/BL for jo miantenance CR 
      {headerName: 'BKG/BL Based Cost', width: 225, minWidth: 70, maxWidth: 300, cellStyle: { 'padding-top': '2px'},
                    headerClass: 'bkg-bl-group',
                    children: [
                        {headerName: 'Amount', field: 'amountBkgBl', width: 110,minWidth: 75, maxWidth: 150,
                         cellStyle: { 'padding-top': '2px' },
                         cellRenderer : function(params){
                           return BkgBlTypeCostRenderer(params);
                         }},
                        {headerName: 'Amount USD', field: 'amountBkgBlUSD', width: 110, minWidth: 75, maxWidth: 150, cellStyle: { 'padding-top': '2px' }},
                        
                    ]
      },
      //adding LumpSum for jo miantenance CR 
      {headerName: 'Lumpsum Based Cost', width: 225, minWidth: 70, maxWidth: 300, cellStyle: { 'padding-top': '2px'},
                    headerClass: 'lump-sum-group',
                    children: [
                        {headerName: 'Amount',field: 'amountLumpsum', width: 110,minWidth: 75, maxWidth: 150, 
                        cellStyle: { 'padding-top': '2px' },
                        cellRenderer  :  function(params){
                          return lumpSumCostRenderer(params);
                        }},
                        {headerName: 'Amount USD', field: 'amountLumpsumUSD', width: 110, minWidth: 75, maxWidth: 150, cellStyle: { 'padding-top': '2px' }},
                        
                    ]
      },
      //#NIIT CR6 >>>>END

      //for delete lump sum column
      // { headerName: 'Delete LumpSum',checkboxSelection: params=>params.data.SOCorCOC =='COC', width: 125, minWidth: 70, maxWidth: 150, cellStyle: { 'padding-top': '2px' } },

      // { headerName: 'Amount', field: 'amount', width: 65, minWidth: 65, maxWidth: 150, cellStyle: { 'padding-top': '2px' } },
      // { headerName: 'Amount USD', field: 'amountUSD', width: 100, minWidth: 100, maxWidth: 150, cellStyle: { 'padding-top': '2px' } },

    ];
    // this.gridOptions = { rowHeight: 60, getRowStyle: getRowStyleScheduled };

    this.selectAllCheckBox = jQuery('#table-header app-rcl-checkbox input[type="checkbox"]')[0];
  }

  
  // Selected option
  selection = [];

  selectedOptions() {
    //return filterFilter(filterOptions, { selected: true });
  };



  agGridCellClicked(e, row) {
    if (this.componentType == 'jomaintenance') {      
      if (e.colDef.field == "BkgOrBLNo") {
        this.insertShowBookingBL(row)
      }
    } else {
      this.insertShowBookingBL(row);
    }
  }

  callingComponent : string = "joMaintainenance";
  replaceEquipmentData = {};
  //open Eq browser modal lookup
  replaceEqRowJoNumber: string;
  replaceObject;
  addEquipmentBrowserModal(e, row) {
    if (this.selectedRowData) {      
      this.replaceEquipmentData = this.selectedRowData.data;
      this.replaceEquipmentData = Object.assign(row ,this.replaceEquipmentData );      
      if (row.bk_bl_ad == 'AD') {
        this._joInquiryEquipmentBrowser.sendingReplaceEquipmentData(this.replaceEquipmentData,this.callingComponent);
        this._joInquiryEquipmentBrowser.openLookUpModal();
      }

      //done on 15 jan 2019
      //for booking bl
        if(row.bk_bl_ad == 'BL' || row.bk_bl_ad == 'BK'){
          if(row.bk_bl_ad == 'BL'){
            var checkBkgBl = "BL"
          }
          if(row.bk_bl_ad == 'BK'){
            var checkBkgBl="Booking";
          }
          if(row.contEmptyOrLaden == "Empty"){
            var checkContType = "E";
          } 
          if(row.contEmptyOrLaden == "Laden"){
            var checkContType = "L";
          } 
          if(row.detailType == "Sea Leg"){
            var joTypeforBkgBl = "SEALEG";
          }
          if(row.jobOrdType == "Export"){
            var joTypeforBkgBl = "ETR";
          }
          if(row.detailType == "Import"){
            var joTypeforBkgBl ="ITR"
          }
          if(row.detailType == "Inter Terminal"){
            var joTypeforBkgBl ="IT";
          }
          var contSize = row.contSize;
            
          this.replaceObject = {
          'contType': checkContType,
          'searchType': "AV",
          'bookType': checkBkgBl,
          'jobType': joTypeforBkgBl,
          'bkgBlNumber': row.BkgOrBLNo,
          'cntSize': contSize.toString(),
          'cntSplHandling': "N",
          'fromLocation': row.fromLoaction,
          'toLocation': row.toLocation,
          'fromTerminal': row.fromTerminal,
          'toTerminal': row.toTerminal,
          'fromLocationTyp': row.fromLocType,
          'toLocationTyp': row.toLocType,
          'cntType': row.contType,
          'callingComponent': this.callingComponent
        }
        this.openNewDialog(event, this.replaceObject.contType, this.replaceObject.searchType, this.replaceObject.bookType,this.replaceObject.cntSize, this.replaceObject.cntSplHandling, this.replaceObject.bookType,this.replaceObject.jobType, this.replaceObject,'','','','','');
      } //done on 15 jan 2019
    }    
    this.replaceEqRowJoNumber = row.JoNumber;    
  }

  //done on 15 jan 2019
  openNewDialog(event, contType, searchType, bookingBlNum, cntSize, cntSplHandling, bookingType, processJoType, row,emptyContainerList,ladenContainerList,i,selectedRowLaden,selectedRowEmpty) {
    if(contType=="E" && searchType =="AV"){ //to open available empty containers
      this.modalService.openDialog(this.viewRef, {
      title: 'Please select available "Empty Container"',
      data: { event : event, contType : contType, searchType : searchType, bkgBlNumber : bookingBlNum, cntSize : cntSize, cntSplHandling : cntSplHandling, bookType : bookingType, jobType : processJoType, row : row, emptyContainer : emptyContainerList, ladenContainer : ladenContainerList, index: i , selectedRowLaden: selectedRowLaden,selectedRowEmpty:selectedRowEmpty},
      childComponent: RCLContainerModalComponent
      });
    } else if(contType=="L" && searchType =="AV"){ //to open available laden containers
      this.modalService.openDialog(this.viewRef, {
      title: 'Please select available "Laden Container"',
      data: { event : event, contType : contType, searchType : searchType, bkgBlNumber : bookingBlNum, cntSize : cntSize, cntSplHandling : cntSplHandling, bookType : bookingType, jobType : processJoType, row : row, emptyContainer : emptyContainerList, ladenContainer : ladenContainerList, index: i , selectedRowLaden: selectedRowLaden,selectedRowEmpty:selectedRowEmpty},
      childComponent: RCLContainerModalComponent
      });
    } //done on 15 jan 2019
  }//done on 15 jan 2019

  insertshowContList(e, row) {

    let tempObj = {
      'fromLocation': row.fromLoaction,
      'toLocation': row.toLocation,
      'fromTerminal': row.fromTerminal,
      'toTerminal': row.toTerminal,
      'fromMode': row.transMode,
      'toMode': row.transMode,
    }
    let containerType;
    if (this.selectedRowData.data.contEmptyOrLaden == 'MT') {
      containerType = 'E'
    } else if (this.selectedRowData.data.contEmptyOrLaden == 'Laden') {
      containerType = 'L'
    }
    this._maintainJoContainer.openLookUpModal(e, containerType, 'T', this.selectedRowData.data.BkgOrBLNo, this.selectedRowData.data.contSize, this.selectedRowData.data.DGorRForOG, row.adhoc_yn, row.jobOrdType, tempObj);

    // this.processJoSearchData.processJoParam.bookingType, this.processJoSearchData.processJoParam.processJoType, row
  }




  //For Opening log lookup
  insertShowRclJoLog(e, row) {
    this._rclJoLog.openLookUpModal(row);
  }


  //open BL/Booking modal lookup
  insertShowBookingBL(e) {
    this._rclBookingBL.openLookUpModal(e);
  }


  private openFSCPopUp(e) {
    if (e.target.disabled) {
      e.stopPropogation();
    } else {
      UIkit.modal('#summaryAmountModal').show();
    }
  }

  private hideAmountPopUp(e) {
    if (e.target.disabled) {
      e.stopPropogation();
    } else {
      UIkit.modal('#summaryAmountModal').hide();
    }
  }

  amountLookupValidation() {
    if (this.Amount) {
      this.showLocErrorText = false;
      return true;
    }
    else {
      if (this.Amount == undefined || this.Amount == "") {
        this.errorTextLookUp = "Please provide Amount field."
        this.showLocErrorText = true;
        return false;
      }
    }
  }

  // method  to show the table (Collps Table) at click of down arrow
  showCollapsedTable(e,index) {    
    //condition to make contPercent 0 or 100 based on Empty or Laden    
    this.tableDataMaintainJoSearch[index].contDetailJO.forEach(element => {
      if(element.contEmptyOrLaden == "Empty"){
        element.contPercent = "0";
      }else{
        element.contPercent = "100";
      }
    });
    let clpsTable = jQuery(e.target).closest('.collapse-button-row').next('.collapse-table')
    if (clpsTable) {
      if (clpsTable.hasClass('collapse')) {
        clpsTable.addClass('active').removeClass('collapse');
        jQuery(e.target).closest('.collapse-button-row').find('.fa-angle-down').addClass('fa-angle-up').removeClass('fa-angle-down');
      } else {
        clpsTable.addClass('collapse').removeClass('active');
        jQuery(e.target).closest('.collapse-button-row').find('.fa-angle-up').addClass('fa-angle-down').removeClass('fa-angle-up')
      }
    }
  }



  // method  to show All the tables (Collps Tables) at click of button
  showCollapsedTableAll(e) {
    let clpsTables = jQuery('.collapse-button-row').next('.collapse-table');
    if (clpsTables) {
      for (let i = 0; i < clpsTables.length; i++) {
        if (jQuery(e.target).hasClass('expend-all')) {
          jQuery(clpsTables[i]).addClass('active').removeClass('collapse');
          jQuery('.fa-angle-down').addClass('fa-angle-up').removeClass('fa-angle-down')
        } else if (jQuery(e.target).hasClass('collapse-all')) {
          jQuery(clpsTables[i]).addClass('collapse').removeClass('active');
          jQuery('.fa-angle-down').addClass('fa-angle-down').removeClass('fa-angle-up')
        }
      }
    }
  }


  private checkedSelectedRows: any = [];
  private checkedJoNumber = [];
  containsLumpSum:boolean=false;
  private maintainjoselectTableRowCheckBox(e, rowObj) {
    this.containsLumpSum = false;
    if (e.target.checked) {
      this.checkedSelectedRows.push(rowObj);      
    } else {
      this.checkedSelectedRows = this.deleteObjByJoNumber(this.checkedSelectedRows, 'JoNumber', rowObj.JoNumber);
    }

    //checking if the selected rows contains lumSum or not
    for (var i = 0; i < this.checkedSelectedRows.length; i++) {
      for (var j = 0; j < this.checkedSelectedRows[i].contDetailJO.length; j++) {
        if(this.checkedSelectedRows[i].contDetailJO[j].amountLumpsum != undefined && this.checkedSelectedRows[i].contDetailJO[j].amountLumpsum != ""){
          this.containsLumpSum = true;
        }
      }  
    }
    this.createJoNumberObjectForActions();
  }


  createJoNumberObjectForActions() {
    this.checkedJoNumber = [];
    for (let i = 0; i < this.checkedSelectedRows.length; i++) {
      let tempObj = {
        'adhoc_yn': this.checkedSelectedRows[i].adhoc_yn,
        'routingId': this.checkedSelectedRows[i].routingId,
        'vendorID': this.checkedSelectedRows[i].vendorID,
        'JoNumber': this.checkedSelectedRows[i].JoNumber,
        'fromLoaction': this.checkedSelectedRows[i].fromLoaction,
        'toLocation': this.checkedSelectedRows[i].toLocation,
        'fromTerminal': this.checkedSelectedRows[i].fromTerminal,
        'toTerminal': this.checkedSelectedRows[i].toTerminal,
        'fromLocType': this.checkedSelectedRows[i].fromLocType,
        'toLocType': this.checkedSelectedRows[i].toLocType,
        'vendorName': this.checkedSelectedRows[i].vendorName,
        'reasonCode': this.checkedSelectedRows[i].reasonCode
      }
      this.checkedJoNumber.push(tempObj);
    }

    
  }

  openPdfLink($event) {
    let pdfUrl;
    if (this.checkedSelectedRows.length == 1) { 
      pdfUrl = this.viewJoUrl + "&P_JOB_ORDER='" + this.checkedSelectedRows[0].JoNumber + "'";
      this.nativeWindow.open(pdfUrl);
    }    
  }

  maintainjoselectTableRowCheckBoxAll(e) {
    this.selectAllCheckBox = e.target;
    //jQuery('.maintain-table-container app-rcl-checkbox input[type="checkbox"]').prop('checked', e.target.checked);
    if (e.target.checked) {
      this.checkedJoNumber = [];
      if (jQuery('.maintain-table-container app-rcl-checkbox input[type="checkbox"]').length == this.resultsPerPage) {
        this.checkedSelectedRows = this.tableDataMaintainJoSearch.slice(((this.p * this.resultsPerPage) - this.resultsPerPage), this.p * this.resultsPerPage);
      } else {
        this.checkedSelectedRows = this.tableDataMaintainJoSearch.slice(((this.p * this.resultsPerPage) - this.resultsPerPage), this.tableDataMaintainJoSearch.length);
      }
      this.checkedSelectedRows.forEach(element => {
        element['checked'] = true;
      });

    } else {
      this.checkedSelectedRows.forEach(element => {
        element['checked'] = false;
      });
      this.checkedJoNumber = [];
      this.checkedSelectedRows = [];
    }
    this.createJoNumberObjectForActions();

  }

  noOfRecordsPerPage; //variable to show no of records in current page
  onChangeNoOfRecordsPerPage(e){
    this.noOfRecordsPerPage = e.target.value;
    this.resultsPerPage = this.noOfRecordsPerPage;
    this.maintainJoPageChange(this.p);
  }

  
  maintainJoPageChange(e) {
    this.p = e;
    if (this.selectAllCheckBox['checked']) {
      this.tableDataMaintainJoSearch.forEach(element => {
        element['checked'] = false;
      });
      this.checkedJoNumber = [];
      this.checkedSelectedRows = [];
      this.selectAllCheckBox['checked'] = false;
    }
    this.getBackEndData(e);
    this.checkBoxFilterArray = jQuery('.check-container1 input[type="checkbox"]');
  }

  private jobOrdTypeText = "Job Order Type";
  private jobOrdStsText = "Job Order status";
  tableDataMaintenanceJoSearch1: any = [];
 @Output() searchDataJoInquiry: EventEmitter<any> = new EventEmitter();
  private getBackEndData(findButton) {
    this.spinner.showSpinner();
    if(this.socCocOptions.length == 2){
      this.maintainJoSearchData.maintainJoParam['SocOrCoc'] = "";
      //this.socCocStateTicked.length = 0;
    }
    this.maintainJoSearchData['action'] = 'maintainJoSearch';
    this.maintainJoSearchData.maintainJoParam['pageNo'] = findButton;
    this.maintainJoSearchData.maintainJoParam['requestChanged'] = true;
    this.maintainJoSearchData.maintainJoParam['noOfRecPerPage'] = this.resultsPerPage;

    let searchObj = this.maintainJoSearchData;
    let backendData = this._maintenanceService.getMaintenanceData(searchObj);
    backendData.subscribe(
      (data) => {
        if(data == "userSessionExpired"){
          this._sessionTimeOutService.checkSessionTimeout(data);
        }
        else if (data.hasOwnProperty("errorCode")) {
          if(data["errorCode"] !="IJS_MAINTAIN_JO_PAGINATION_ERROR"){
            this.spinner.hideSpinner();
            this.tableDataMaintenanceJoSearch1 = [];
            this.tableDataMaintainJoSearch =  [];
            this.totalRecords =0; 
            this.errorCodetext = this._serverErrorCode.checkError(data["errorCode"]);
           // $('#maintainjo-error-code-modal-search').addClass('uk-open').show(); 
            // UIkit.modal('#maintainjo-error-code-modal-search').show();
         }         
        }
        else {
           this.spinner.hideSpinner();
          this.tableDataMaintenanceJoSearch1 = data;
          this.tableDataMaintainJoSearch =  this.tableDataMaintenanceJoSearch1.searchResult.result;
          this.totalRecords = this.tableDataMaintenanceJoSearch1.totalRecords;
         
           let tempObj = {
              resultTable: this.tableDataMaintainJoSearch,
              filterData: this.filterDataSelectedComp
            }
          this.refreshData.emit(tempObj);

          
          //new filter functionality <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
		      //this.filterDataOnPageChange(this.tableDataMaintainJoSearch); 
          //new filter functionality <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
          // let searchInputOutPutData = {
          //   "filterInputData": this.filterDataSelectedComp,
          //   "searchInputData": this.maintainJoSearchData,
          //   //"searchOutputData": this._sortTable.sortTableData(this.tableDataMaintenanceJoSearch1, this.filterDataSelectedComp.sortIn, this.filterDataSelectedComp.orderBy),
          //   "searchOutputData": this._sortTable.sortTableData(this.tableDataMaintainJoSearch, this.filterDataSelectedComp.sortIn, this.filterDataSelectedComp.orderBy),
          //   "jobOrdTypeText": this.jobOrdTypeText,
          //   "jobOrdStsText": this.jobOrdStsText,
          //   "totalRecords": this.tableDataMaintainJoSearch.totalRecords
          // }
          // this.searchDataJoInquiry.emit(searchInputOutPutData);
        }
               
        
      },
      (err) => {
        this.spinner.hideSpinner();
        // A client-side or network error occurred. Handle it accordingly.
        findButton.innerHTML = '<i class="fa fa-search"></i> <span>&nbsp;FIND</span>'
        this.errorCodetext = "Something Went wrong,  Please try again.";
        UIkit.modal('#maintainjo-error-code-modal').show();
      }
    )
  }

  closeJoMaintainWarning(e){
    UIkit.modal('#maintainjo-error-code-modal-search').hide();    
  }
	
  //new filter functionality
  //filter data on page change  <<<<<<<<<<<<<<<<<<<<<<<<<<<<
  filterDataOnPageChange(records){    
    this.checkBoxFilterArray = this.filterOptions;
    let newcbArray=this.checkBoxFilterArray;    
    let tableData = records;
    
    newcbArray.forEach(element => {
      if(!element.selected){       
       if (element.id == 'SOCorCOC') {
            tableData = tableData.filter((obj) => {
            return (element.name !== obj.SOCorCOC);
          });
       } else if (element.id == 'status') {
            tableData = tableData.filter((obj) => {
            return (element.name !== obj.status);
        });
       }
      }
    });    
    this.tableDataMaintainJoSearch = tableData;
  }
  // new filter functionality <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
  


  //delete element from array
  deleteObjByJoNumber(arr, attr, value) {
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

  //complete job order
  completeJobOrder(e) {
    let completeJobOrderSearchData = { contractsList: this.checkedJoNumber, action: "completeJo" };
    this._spinner.showSpinner();
    let backendData = this._maintenanceService.getMaintenanceData(completeJobOrderSearchData);
    backendData.subscribe(
      (data) => {
        if(data == "userSessionExpired"){
          this._sessionTimeOutService.checkSessionTimeout(data);
        }
        else if (!data['errorCode'].includes("MSG")) {
          this._spinner.hideSpinner();
          this.errorCodetext = this._serverErrorCode.checkError(data["errorCode"]);
          UIkit.modal('#maintainjo-error-code-modal-search').show();
        }
        else {
          this.getBackEndRefreshedTableData();
        }
        //this._spinner.hideSpinner();
        this.checkedSelectedRows = [];
      }
    )
  }

  //#NIIT CR6 >>>>BEGIN
  approveJobOrderList(e){

    //if selected rows conatins lumpsum the ask for confirmation 
    if(this.containsLumpSum){
       $('#approve-job-order').addClass('uk-open').show();
    }
    //if selected rows doesn't contains lumpsum then approve job orders directly     
    else{
      this.approveJobOrder('');
    }
  }
  //#NIIT CR6 >>>>END

  //approve job order
  approveJobOrder(e) {
    let approveJobOrderSearchData = { contractsList: this.checkedJoNumber, action: "maintainJoApprove" };
    this._spinner.showSpinner();
    let backendData = this._maintenanceService.getMaintenanceData(approveJobOrderSearchData);
    backendData.subscribe(
      (data) => {
        if(data == "userSessionExpired"){
          this._sessionTimeOutService.checkSessionTimeout(data);
        }
        else if (!data['errorCode'].includes("MSG")) {
          this._spinner.hideSpinner();
          this.errorCodetext = this._serverErrorCode.checkError(data["errorCode"]);
          UIkit.modal('#maintainjo-error-code-modal-search').show();
        }
        else {
          this.getBackEndRefreshedTableData();
        }
        //this._spinner.hideSpinner();
        this.checkedSelectedRows = [];
      }
    )
  }
  //cancel job order
  cancleJobOrder(e) {
    this._spinner.showSpinner();
    let cancleJobOrderSearchData = { contractsList: this.checkedJoNumber, action: "maintainJoCancel" };
    this._spinner.showSpinner();
    let backendData = this._maintenanceService.getMaintenanceData(cancleJobOrderSearchData);
    backendData.subscribe(
      (data) => {
        if(data == "userSessionExpired"){
          this._sessionTimeOutService.checkSessionTimeout(data);
        }
        else if (!data['errorCode'].includes("MSG")) {
          this._spinner.hideSpinner();
          this.errorCodetext = this._serverErrorCode.checkError(data["errorCode"]);
          UIkit.modal('#maintainjo-error-code-modal-search').show();
        }
        else {
          // let tempObj = {
          //   searchObj: this.tableDataMaintainJoSearch,
          //   filterData: this.filterDataSelectedComp
          // }
          // this.refreshBackEndData.emit(tempObj)
          this.jobOrderCancelText = this._serverErrorCode.checkError(data["errorCode"]);
          this._spinner.hideSpinner();
          this.getBackEndRefreshedTableData();
        }
        //this._spinner.hideSpinner();
        this.checkedSelectedRows = [];
      }
    )
  }
  //reject job order
  rejectJobOrder(rowObj, e) {
    let rejectJobOrderSearchData = { contractsList: this.checkedJoNumber, action: "maintainJoReject" };
    this._spinner.showSpinner();
    let backendData = this._maintenanceService.getMaintenanceData(rejectJobOrderSearchData);
    backendData.subscribe(
      (data) => {
        if(data == "userSessionExpired"){
          this._sessionTimeOutService.checkSessionTimeout(data);
        }
        else if (!data['errorCode'].includes("MSG")) {
          this._spinner.hideSpinner();
          this.errorCodetext = this._serverErrorCode.checkError(data["errorCode"]);
          UIkit.modal('#maintainjo-error-code-modal-search').show();
        }
        else {
          this.getBackEndRefreshedTableData();
        }

        this.checkedSelectedRows = [];
      }
    )
  }


  saveJobOrder(e, rowObj) {
    let saveJobOrderSearchData = {
      joSaveListAll: {
        "joRemoveCntrList": this.joRemoveCntrList,
        "joChangeVendorList": this.joChangeVendorList,
        "joSaveFscList": this.joSaveFscList,
        "joReplaceCntrList": this.joReplaceCntrList
      },
      "action": "maintainJoSave"
    }

    this._spinner.showSpinner();
    let backendData = this._maintenanceService.getMaintenanceData(saveJobOrderSearchData);
    backendData.subscribe(
      (data) => {
        if(data == "userSessionExpired"){
          this._sessionTimeOutService.checkSessionTimeout(data);
        }
        else if(data['errorCode'].includes("IJS_EX_10035")){
          this._spinner.hideSpinner();
          this.errorCodetext = this._serverErrorCode.checkError(data["errorCode"]);
          UIkit.modal('#maintainjo-success-code-modal-search').show();
        }
        else if (!data['errorCode'].includes("MSG")) {
          this._spinner.hideSpinner();
          this.errorCodetext = this._serverErrorCode.checkError(data["errorCode"]);
          UIkit.modal('#maintainjo-error-code-modal-search').show();
        }
        else {
          this.getBackEndRefreshedTableData();
        }
        this.checkedSelectedRows = [];
      },
      (err) => {
        this._spinner.hideSpinner();
      }
    )
  }

  errorCodetext: string;
  getBackEndRefreshedTableData() {
    this.refreshTableStatusFilter = false
    this._spinner.showSpinner();
    this.maintainJoSearchData['action'] = 'maintainJoSearch';
    let backendData = this._maintenanceService.getMaintenanceData(this.maintainJoSearchData);
    backendData.subscribe(
      (data) => {
        if(data == "userSessionExpired"){
          this._sessionTimeOutService.checkSessionTimeout(data);
        }
        else if (data.hasOwnProperty("errorCode")) {
          this._spinner.hideSpinner();
          if(this.jobOrderCancelText != ""){
            this.jobOrderCancelText;
            UIkit.modal('#jo-cancel-modal').show();            
          }else{
            this.errorCodetext = this._serverErrorCode.checkError(data["errorCode"]);
            UIkit.modal('#maintainjo-error-code-modal-search').show();
          }          
        } else {
          this.tableDataMaintenanceJoSearch1 = data;
          this.tableDataMaintainJoSearch = this.tableDataMaintenanceJoSearch1.searchResult.result;
          this.tableDataMaintainJoSearch = this._sortTable.sortTableData(this.tableDataMaintainJoSearch, this.filterDataSelectedComp['sortIn'], this.filterDataSelectedComp['orderBy']);

          this.selectAllCheckBox.checked = false;
          this.joChangeVendorList = [];
          this.joSaveFscList = [];
          this.joRemoveCntrList = [];
          this.joReplaceCntrList = [];
        // this.totalRecords = data['totalRecords'];
          this.refreshTableStatusFilter = true;
          this._spinner.hideSpinner();
        }
      },
      (err) => {
        this._spinner.hideSpinner();
        this.errorCodetext = this._serverErrorCode.checkError(err);
        UIkit.modal('#maintainjo-error-code-modal-search').show();
      }
    )
  }



  onChangeOrderBy(data) {
    this.filterDataSelectedComp.orderBy = data;
    this.getFilterCriteria();    
    //this.getBackEndData(this.p);

    //object to emmit the records of current page only to be sorted
    let tempObj = {
      resultTable: this.tableDataMaintainJoSearch,
      filterData: this.filterDataSelectedComp
    }
    this.refreshData.emit(tempObj)
  }

  onChangeSortIn(data) {
    this.filterDataSelectedComp.sortIn = data;
    this.getFilterCriteria();
    //this.getBackEndData(this.p);
    
    //object to emmit the records of current page only to be sorted
    let tempObj = {
      resultTable: this.tableDataMaintainJoSearch,
      filterData: this.filterDataSelectedComp
    }
    this.refreshData.emit(tempObj)
  }

  routeListInputValueLoc: string = "";
  routeListInputValueTerminal: string = "";
  routeListInputValueLocType: string = "";
  routeListInputDate: string = "";
  routeListVendorCode: string = "";
  routeListBookingType: string = "";
  chnageVendorSelectedRow: any;

  private insertLegshowRouteList(e) {
    if (this.checkedSelectedRows.length == 1) {
      this.chnageVendorSelectedRow = this.checkedSelectedRows[0];
      this.routeListInputValueLoc = this.checkedSelectedRows[0].fromLoaction + '/' + this.checkedSelectedRows[0].toLocation;
      this.routeListInputValueTerminal = this.checkedSelectedRows[0].fromTerminal + '/' + this.checkedSelectedRows[0].toTerminal;
      this.routeListInputValueLocType = this.checkedSelectedRows[0].fromLocType + '/' + this.checkedSelectedRows[0].toLocType;
      this.routeListBookingType = this.checkedSelectedRows[0].bk_bl_ad;
      this.routeListInputDate = this.checkedSelectedRows[0].orderDate;
      this.routeListVendorCode = this.checkedSelectedRows[0].vendorID;

      this._maintainjoRouteList.inputValueLoc = this.routeListInputValueLoc;
      this._maintainjoRouteList.inputValueTerminal = this.routeListInputValueTerminal;
      this._maintainjoRouteList.inputValueLocType = this.routeListInputValueLocType;
      this._maintainjoRouteList.inputSaleDateOrJobOrdDate = this.routeListInputDate;
      this._maintainjoRouteList.bookingType = this.routeListBookingType;
      this._maintainjoRouteList.vendorCode = this.routeListVendorCode;

      //job order type for change vendor functionality
      this._maintainjoRouteList.transportType = this.chnageVendorSelectedRow.transMode; //transport mode

      //job order type
      if(this.chnageVendorSelectedRow.jobOrdType=="Ad-Hoc Empty"){
         this._maintainjoRouteList.joType = "ER";
      }
      if(this.chnageVendorSelectedRow.jobOrdType=="Ad-Hoc Laden"){
         this._maintainjoRouteList.joType = "LAH";
      }
      if(this.chnageVendorSelectedRow.jobOrdType=="Sea Leg"){
         this._maintainjoRouteList.joType = "SEALEG";
      }
      if(this.chnageVendorSelectedRow.jobOrdType=="Export"){
         this._maintainjoRouteList.joType = "ETR";
      }
      if(this.chnageVendorSelectedRow.jobOrdType=="Import"){
         this._maintainjoRouteList.joType = "ITR";
      }
      if(this.chnageVendorSelectedRow.jobOrdType=="Inter Terminal"){
         this._maintainjoRouteList.joType = "IT";
      }     
      this.callingComponent = this.componentType; //checking the component from where modal is opened
      this._maintainjoRouteList.openLookUpModal(this.callingComponent);
    }
  }

  joChangeVendorList: any = []
  updateRoute(e) {
    let tempObj = {
      "bk_bl_ad": this.checkedSelectedRows[0].bk_bl_ad,
      "JoNumber": this.checkedSelectedRows[0].JoNumber,
      "jobOrdType": this.checkedSelectedRows[0].jobOrdType,
      "orderDate": this.checkedSelectedRows[0].orderDate,
      "routingId": e.routingId,
      "vendorID": e.vendorCode,
      "contractId": e.contractId,
      "SOCorCOC": this.checkedSelectedRows[0].SOCorCOC,
      "transMode":e.transMode
    }
    this.chnageVendorSelectedRow.contractId = e.contractId
    this.chnageVendorSelectedRow.vendorID = e.vendorCode
    this.chnageVendorSelectedRow['vendorChanged'] = 'vendor-changed'
    this.joChangeVendorList.push(tempObj);
  }

  joSaveFscList: any = []

  updateFsc(fsc, row) {
    let tempObj = {
      "fsc": fsc,
      "JoNumber": row.JoNumber
    }
    if (this.joSaveFscList.length == 0) {
      this.joSaveFscList.push(tempObj);
    } else {
      this.joSaveFscList.forEach(element => {
        if (element.JoNumber == row.JoNumber) {
          element.fsc = fsc;
        } else {
          this.joSaveFscList.push(tempObj);
        }
      });
      this.joSaveFscList = this.removeEleUsingProp(this.joSaveFscList, row.JoNumber)
    }
  }
  removeEleUsingProp(arr, prop) {
    return arr.map(function (e) { return e[prop]; }).filter(function (e, i, a) {
      return i === a.indexOf(e);
    });
  }


  rowSelectedFlag: boolean = false;
  selectedRowId: number;
  selectedRowData: any;
  joDetails:any = [];//#NIIT CR1 >>>>
  adHocFlag:boolean = false;
  private onRowSelected(e, row) {
    this.joDetails = [];//#NIIT CR1 >>>>
    if (e.node.selected == false) {
      if (this.selectedRowId == e.node.id) {
        this.rowSelectedFlag = false;
        this.selectedRowData = undefined;
        //#NIIT CR1 >>>>BEGIN
        this.joDetails = []; 
        this.adHocFlag = false; 
        //#NIIT CR1 >>>>END     
      }
    } else {
      this.rowSelectedFlag = true;
      this.selectedRowData = e.node;
      this.selectedRowId = e.node.id;
      //#NIIT CR1 >>>>BEGIN
      this.joDetails.push(row); 
      if(row.detailType == "Ad-Hoc Laden" || row.detailType == "Ad-Hoc Empty"){
        this.adHocFlag = true;
      } else{
        this.adHocFlag = false;
      }
      //#NIIT CR1 >>>>END
    }
  }

  joRemoveCntrList: any = [];
  duplicateFound:boolean = false;
  removeEquipment(e, row) {
    if (this.selectedRowData) {
      if(this.selectedRowData.gridOptionsWrapper.gridOptions.rowData.length > 1){
        this.selectedRowData.gridOptionsWrapper.gridOptions.rowData.splice(this.selectedRowData.id, 1);
        this.selectedRowData.gridOptionsWrapper.gridOptions.api.setRowData(this.selectedRowData.gridOptionsWrapper.gridOptions.rowData);
        this.selectedRowData.gridOptionsWrapper.gridOptions.api.refreshView()
        this.joRemoveCntrList.push({ "contNoToDelete": this.selectedRowData.data['eqNumber'], "JoNumber": row['JoNumber'] });
        this.selectedRowData = undefined;
      } else {
        $('#remove-equipment-error-modal').addClass('uk-open').show();
      }      
    }
  }

  closeRemoveEquipmentWarning(e){
    $('#remove-equipment-error-modal').addClass('uk-open').hide();
  }

  joReplaceCntrList: any = [];
  updateEqDetails(e) {

    if (this.selectedRowData) {
      if(this.replaceObject != undefined){
        //object for Booking/BL
        let tempObj = {
        "JoNumber": this.replaceEqRowJoNumber,
        "oldContainerNo": this.selectedRowData.data['oldContainerNo'],
        "oldContNoReplace": this.selectedRowData.data['eqNumber'],
        "newContNoReplace": e[0].container,       
        "contSize": this.replaceObject.cntSize,
        "contType": this.selectedRowData.data['contType']
      }
       if(this.joReplaceCntrList.length ==0){
         this.joReplaceCntrList.push(tempObj);
         this.selectedRowData.data['eqNumber'] = e[0].container;
       }else{
        this.joReplaceCntrList.forEach(element => {
          if(element.newContNoReplace == tempObj.newContNoReplace){
            if(element.oldContainerNo != tempObj.oldContainerNo){
              this.duplicateFound = true;
              UIkit.modal('#maintainjo-replace-container').show();
            }                        
          } 
          // else{
          //   this.joReplaceCntrList.push(tempObj);
          //   this.selectedRowData.data['eqNumber'] = e[0].container;
          // }
        });
        
        if(this.duplicateFound == false){        
          
          const index = this.joReplaceCntrList.findIndex((replaceListData) => replaceListData.oldContainerNo == tempObj.oldContainerNo);
          if (index === -1) {
              this.joReplaceCntrList.push(tempObj);
          } else {
              this.joReplaceCntrList[index] = tempObj;
          }                  
          this.selectedRowData.data['eqNumber'] = e[0].container;
        }else{
          this.duplicateFound = false;
        }

       }
       
      }else{
        //object for Laden AdHoc
        let tempObj = {
        "JoNumber": this.replaceEqRowJoNumber,
        "oldContainerNo": this.selectedRowData.data['oldContainerNo'],
        "oldContNoReplace": this.selectedRowData.data['eqNumber'],
        "newContNoReplace": e[0].eqpNum,
        "contEmptyOrLaden": this.selectedRowData.data['contEmptyOrLaden'],
        "contSize": this.selectedRowData.data['contSize'],
        "contType": this.selectedRowData.data['contType']
      }
       this.joReplaceCntrList.push(tempObj);
       this.selectedRowData.data['eqNumber'] = e[0].eqpNum;
      } 
      this.selectedRowData.gridOptionsWrapper.gridOptions.api.setRowData(this.selectedRowData.gridOptionsWrapper.gridOptions.rowData);

      this.selectedRowData = undefined;
    }   
  }

  downloadErrorText:String='';
  downloadedContainer:String = '';
  downoadResultsExcelFile(e) {
    this._spinner.showSpinner();
    let downloadSearchData = this.maintainJoSearchData;
    downloadSearchData["action"] = "joDownloadLimit";

    this._maintenanceService.getDownloadDataLimit(downloadSearchData)
      .subscribe((data)=>{
        if(data == "userSessionExpired"){
          this._sessionTimeOutService.checkSessionTimeout(data);
        }
        else if(data['errorCode'] == "IJS_EX_10036"){
          var downloadContainer = data['downloadContainer'];
          var downloadLimit = data['downloadLimit'];
          this._spinner.hideSpinner();
          this.downloadErrorText = "Only less than "+ downloadLimit+ " containers can be downloaded";
          this.downloadedContainer = "Total downloaded containers in request are "+ downloadContainer;
          $('#download-error-modal').addClass('uk-open').show(); 
        }else{
          downloadSearchData["action"] = "maintainDownload";
          this._maintenanceService.downloadJobOrders(downloadSearchData)
            .subscribe((data)=>{
              FileSaver.saveAs(data, "IJS_JOB_ORDER_DOWNLOAD");
              this._spinner.hideSpinner();
            },(err)=>{
              this._spinner.hideSpinner();        
              console.log(err);
              this.errorCodetext = this._serverErrorCode.checkError(err);
            });            
        }
      }); 
  }

  //#NIIT CR6 >>>>BEGIN
  //method to close download warning error
  closeDownloadWarning(e){
    $('#download-error-modal').addClass('uk-open').hide();
  }
  //#NIIT CR6 >>>>END
  
  //old filter functionality
  // filterTableDataFromFilter(e) {
  //   console.log('checking the event :'+ JSON.stringify(e));
  //   let tableData = this.tableDataMaintainJoSearch1;
  //   let checkBoxFilterArray = jQuery('.check-container1 input[type="checkbox"]');
  //   checkBoxFilterArray.each(function (e) {
  //     if (checkBoxFilterArray[e].name == 'SOCorCOC') {
  //       if (!checkBoxFilterArray[e].checked) {
  //         tableData = tableData.filter((obj) => {
  //           return (checkBoxFilterArray[e].value !== obj.SOCorCOC);
  //         });
  //       }
  //     } else if (checkBoxFilterArray[e].name == 'status') {
  //       if (!checkBoxFilterArray[e].checked) {
  //         tableData = tableData.filter((obj) => {
  //           return (checkBoxFilterArray[e].value !== obj.status);
  //         });
  //       }
  //     }
  //   });
  //   this.tableDataMaintainJoSearch = tableData;
  // }
  
  
  //joMaintainenceStatus = ['S','E','I','D','W','P'];
  statusOptionsText = [];
  statusOptions = [];
  socCocOptions = [];
  socCocStateUnticked = [];
  socCocStateTicked = [];
  allCheckedFlag: boolean = true;
  requestChangedFlag:boolean = false;
  //new filter functionality <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
  filterTableDataFromFilter(e) { 
    this.requestChangedFlag = true;
    if (this.componentType == 'joinquiry') {   
      if(this.statusOptions.length == 0 && this.allCheckedFlag == true){
          this.joMaintainenceStatus = ['S','E','I','D','W','P','M','C'];
          this.joMaintainenceStatusText= ['Start','Entry','Issued','Waitlisted','Rejected','Pending','Completed','Cancelled']; 
      } else{
          this.joMaintainenceStatus = [];
          this.joMaintainenceStatusText=[];
      }
    } else { 
      if(this.statusOptions.length == 0 && this.allCheckedFlag == true){
          this.joMaintainenceStatus = ['S','E','I','D','W','P'];
          this.joMaintainenceStatusText= ['Start','Entry','Issued','Waitlisted','Rejected','Pending'];  
      } else{
          this.joMaintainenceStatus = [];
          this.joMaintainenceStatusText=[];
      }
    }
    var filteredStatus =  this.statusOptions;
    var filteredStatusText = this.statusOptionsText;
    this.checkBoxFilterArray = this.filterOptions;
    let cbArray=this.checkBoxFilterArray;
    cbArray.forEach(element => {
      if(element.selected){
           if(element.name == "Start"){  
             //filteredStatus.indexOf("S") === -1 ? filteredStatus.push("S") : "";
             if(filteredStatus.indexOf("S") === -1){
                filteredStatus.push("S");
                filteredStatusText.push("Start");
             }                                 
           } 
           if(element.name == "Entry"){   
             //filteredStatus.indexOf("E") === -1 ? filteredStatus.push("E") : "";
             if(filteredStatus.indexOf("E") === -1){
                filteredStatus.push("E");
                filteredStatusText.push("Entry");
             }                          
           }  
           if(element.name == "Issued"){  
             //filteredStatus.indexOf("I") === -1 ? filteredStatus.push("I") : "";
             if(filteredStatus.indexOf("I") === -1){
                filteredStatus.push("I");
                filteredStatusText.push("Issued");
             }                                           
           }  
           if(element.name == "Waitlisted"){
             //filteredStatus.indexOf("D") === -1 ? filteredStatus.push("D") : "";
             if(filteredStatus.indexOf("D") === -1){
                filteredStatus.push("D");
                filteredStatusText.push("Waitlisted");
             }                                          
           }  
           if(element.name == "Rejected"){    
             //filteredStatus.indexOf("W") === -1 ? filteredStatus.push("W") : "";
             if(filteredStatus.indexOf("W") === -1){
                filteredStatus.push("W");
                filteredStatusText.push("Rejected");
             }                                    
           }  
           if(element.name == "Pending"){ 
             //filteredStatus.indexOf("P") === -1 ? filteredStatus.push("P") : "";
             if(filteredStatus.indexOf("P") === -1){
                filteredStatus.push("P");
                filteredStatusText.push("Pending");
             }                                           
           }
           if(element.name == "Completed"){ 
             //filteredStatus.indexOf("M") === -1 ? filteredStatus.push("M") : "";
             if(filteredStatus.indexOf("M") === -1){
                filteredStatus.push("M");
                filteredStatusText.push("Completed");
             }                                           
           }
           if(element.name == "Cancelled"){ 
             //filteredStatus.indexOf("C") === -1 ? filteredStatus.push("C") : "";
             if(filteredStatus.indexOf("C") === -1){
                filteredStatus.push("C");
                filteredStatusText.push("Cancelled");
             }                                           
           }
           if(element.name == "SOC"){
              this.maintainJoSearchData.maintainJoParam['SocOrCoc'] = "S";
              this.socCocOptions.indexOf("S") === -1 ? this.socCocOptions.push("S") : "";                                     
              this.socCocStateTicked.indexOf("S") === -1 ? this.socCocStateTicked.push("S") : "";                                     
           } 
           if(element.name == "COC"){
             this.maintainJoSearchData.maintainJoParam['SocOrCoc'] = "C";
             this.socCocOptions.indexOf("C") === -1 ? this.socCocOptions.push("C") : "";                                     
             this.socCocStateTicked.indexOf("C") === -1 ? this.socCocStateTicked.push("C") : "";                                     
           }              
      }
      if(!element.selected){       
       
       if (element.id == 'SOCorCOC') {
           if(element.name == "SOC"){
            this.maintainJoSearchData.maintainJoParam['SocOrCoc'] = "C";
            this.socCocOptions = this.socCocOptions.filter((element)=> element != "S" );
            this.socCocStateUnticked.indexOf("C") === -1 ? this.socCocStateUnticked.push("C") : "" ;                                    
           }
           if(element.name == "COC"){
             this.maintainJoSearchData.maintainJoParam['SocOrCoc'] = "S";
             this.socCocOptions = this.socCocOptions.filter((element)=> element != "C" );
             this.socCocStateUnticked.indexOf("S") === -1 ? this.socCocStateUnticked.push("S") : "";                                    
           }
           this.allCheckedFlag = false;          
       } else if (element.id == 'status') {
          
           if(this.statusOptions.length != 0){

              if(element.name == "Start"){                         
                  filteredStatus = filteredStatus.filter(word => word !== "S");
                  filteredStatusText = filteredStatusText.filter(word => word !== "Start");                            
              }  if(element.name == "Entry"){         
                  filteredStatus = filteredStatus.filter(word => word !== "E"); 
                  filteredStatusText = filteredStatusText.filter(word => word !== "Entry");                                      
              }  if(element.name == "Issued"){                         
                  filteredStatus = filteredStatus.filter(word => word !== "I");  
                  filteredStatusText = filteredStatusText.filter(word => word !== "Issued");                                       
              }  if(element.name == "Waitlisted"){                      
                  filteredStatus = filteredStatus.filter(word => word !== "D"); 
                  filteredStatusText = filteredStatusText.filter(word => word !== "Waitlisted");                                         
              }  if(element.name == "Rejected"){                    
                  filteredStatus = filteredStatus.filter(word => word !== "W"); 
                  filteredStatusText = filteredStatusText.filter(word => word !== "Rejected");                                         
              }  if(element.name == "Pending"){                        
                  filteredStatus = filteredStatus.filter(word => word !== "P"); 
                  filteredStatusText = filteredStatusText.filter(word => word !== "Pending");                                        
              }  if(element.name == "Completed"){
                  filteredStatus = filteredStatus.filter(word => word !== "M");
                  filteredStatusText = filteredStatusText.filter(word => word !== "Completed");                            
              }  if(element.name == "Cancelled"){
                  filteredStatus = filteredStatus.filter(word => word !== "C");
                  filteredStatusText = filteredStatusText.filter(word => word !== "Cancelled");                            
              }  
          } else{
               if(element.name == "Start" && this.allCheckedFlag){                         
                  filteredStatus = this.joMaintainenceStatus.filter(word => word !== "S");
                  filteredStatusText = this.joMaintainenceStatusText.filter(word => word !== "Start");              
              }  if(element.name == "Entry" && this.allCheckedFlag){         
                  filteredStatus = this.joMaintainenceStatus.filter(word => word !== "E");
                  filteredStatusText = this.joMaintainenceStatusText.filter(word => word !== "Entry");                           
              }  if(element.name == "Issued" && this.allCheckedFlag ){                         
                  filteredStatus = this.joMaintainenceStatus.filter(word => word !== "I");  
                  filteredStatusText = this.joMaintainenceStatusText.filter(word => word !== "Issued");                         
              }  if(element.name == "Waitlisted" && this.allCheckedFlag){                      
                  filteredStatus = this.joMaintainenceStatus.filter(word => word !== "D");
                  filteredStatusText = this.joMaintainenceStatusText.filter(word => word !== "Waitlisted");                            
              }  if(element.name == "Rejected" && this.allCheckedFlag ){                    
                  filteredStatus = this.joMaintainenceStatus.filter(word => word !== "W");
                  filteredStatusText = this.joMaintainenceStatusText.filter(word => word !== "Rejected");                            
              }  if(element.name == "Pending" && this.allCheckedFlag){                        
                  filteredStatus = this.joMaintainenceStatus.filter(word => word !== "P");
                  filteredStatusText = this.joMaintainenceStatusText.filter(word => word !== "Pending");                           
              }  if(element.name == "Completed" && this.allCheckedFlag){                        
                  filteredStatus = this.joMaintainenceStatus.filter(word => word !== "M"); 
                  filteredStatusText = this.joMaintainenceStatusText.filter(word => word !== "Completed");                          
              }  if(element.name == "Cancelled" && this.allCheckedFlag){                        
                  filteredStatus = this.joMaintainenceStatus.filter(word => word !== "C");  
                  filteredStatusText = this.joMaintainenceStatusText.filter(word => word !== "Cancelled");                         
              }   
              this.allCheckedFlag = false;   
          }                             
        }
      }
    }); 
    this.statusOptions =  filteredStatus; 
    this.statusOptionsText  =  filteredStatusText;   
    this.maintainJoSearchData.maintainJoParam['jobOrdSts'] = filteredStatus.toString();
    this.maintainJoSearchData.maintainJoParam['jobOrdStsText'] = filteredStatusText.toString().replace(/,/g,', ');    
    
    
  } 
  //new filter functionality <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

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
      //this.maintainJoSearchData.maintainJoParam['orderType'] = "ASC";
    } 
    if(this.filterDataSelectedComp.orderBy=="dsnd"){
      this.filterDataSelectedComp.orderByText="Descending";
      //this.maintainJoSearchData.maintainJoParam['orderType'] = "DESC";
    } 
    
  }

  //change find called when job order cancelled successfully
  changeFind(e){
    this.changeFindforCancelledJo.emit(e);
  }

  showHidePopOver(popover) {    
    if (popover.isOpen()) {
      popover.close();     
      if(this.socCocOptions.length == 0){       
          this.tableDataMaintenanceJoSearch1 = [];
          this.tableDataMaintainJoSearch =  [];
          this.totalRecords =0;     
      }else{
        if(this.requestChangedFlag){
          
          this.getBackEndData(1);
          this.requestChangedFlag = false;
        }         
      }
      
    } else {
      popover.open();
    }
  }

  //#NIIT CR6 >>>> BEGIN
  deleteLumSumJobOrders:any;
  //popup to ask user whether to delete the records or not
  deleteLumpSum(e){    
     $('#delete-maintenance-lumpsum-modal').addClass('uk-open').show(); 
  }

  jobOrderDeleteText:any = '';
  //method to delete the lump sum records after confirmation from the user
  jobOrderWithoutLumpSum:any = [];
  deleteJoMaintenanceLumpSumConfirm(){
    var lumsumCount=0;
    this.jobOrderWithoutLumpSum = [];

    const joWithoutLumpSum = new Set();
    for (var i = 0; i < this.checkedSelectedRows.length; i++) {
      for (var j = 0; j < this.checkedSelectedRows[i].contDetailJO.length; j++) {
        if(this.checkedSelectedRows[i].contDetailJO[j].amountLumpsum == undefined || this.checkedSelectedRows[i].contDetailJO[j].amountLumpsum == ""){         
         lumsumCount++;
        }
      }  
      if(lumsumCount==this.checkedSelectedRows[i].contDetailJO.length){
        joWithoutLumpSum.add(this.checkedSelectedRows[i].JoNumber);
      }
      lumsumCount=0;
    }
    
    this.jobOrderWithoutLumpSum = Array.from(joWithoutLumpSum);
    

    const filteredJoSet = new Set();
    //check if jo contains lump sum
    for (var i = 0; i < this.checkedSelectedRows.length; i++) {
      for (var j = 0; j < this.checkedSelectedRows[i].contDetailJO.length; j++) {
        if(this.checkedSelectedRows[i].contDetailJO[j].amountLumpsum != undefined && this.checkedSelectedRows[i].contDetailJO[j].amountLumpsum != ""){
          filteredJoSet.add(this.checkedSelectedRows[i].JoNumber)
        }
      }  
    }
    this.deleteLumSumJobOrders = Array.from(filteredJoSet);

    
    if(this.jobOrderWithoutLumpSum.length == 0){
      let deleteLumpSumObject = { joDeleteLumpsumList: this.deleteLumSumJobOrders, action: "deleteLumpsum" };
      this._spinner.showSpinner();
      let backendData = this._maintenanceService.getMaintenanceData(deleteLumpSumObject);
      backendData.subscribe(
        (data) => {
          if(data == "userSessionExpired"){
            this._sessionTimeOutService.checkSessionTimeout(data);
          }
          else if (!data['errorCode'].includes("MSG")) {
            this._spinner.hideSpinner();
          }
          else {
            this.jobOrderDeleteText = this._serverErrorCode.checkError(data["errorCode"]);
            this._spinner.hideSpinner();
            this.getSuccessMsg();          
          }
          //this._spinner.hideSpinner();
          this.checkedSelectedRows = [];
        }
      )
    } else{      
      $('#jo-without-lumpsum').addClass('uk-open').show(); 
    }
    
  }

  closeJoWarning(e){
    $('#jo-without-lumpsum').addClass('uk-open').hide(); 
  }

  //method to show user that the records have been successfully deleted
  getSuccessMsg(){
    $('#delete-lumpsum-jo-success-modal').addClass('uk-open').show(); //show success
  }

  //method to refreh the table after the records have been successfully deleted
  refreshJoResultTable(){
    $('#delete-lumpsum-jo-success-modal').addClass('uk-open').hide(); 
    this.getBackEndRefreshedTableData();
  }

  //method to refresh table when when lump sum is deleted from booking bl popup
  refreshJoMaintenanceTable(e){
    this.getBackEndRefreshedTableData();
  }
  //#NIIT CR6 >>>> END

  //#NIIT CR1 >>>>BEGIN
  ngOnChanges(){    
    if(this.selectedRowData){
       const usedJoSet = new Set();
       for (var i = 0; i < this.selectedRowData.gridOptionsWrapper.gridOptions.rowData.length; i++) {
          for (var j = 0; j < this.validContainerList.length; j++) {
            if(((this.selectedRowData.gridOptionsWrapper.gridOptions.rowData[i].contType == "**" 
               || this.selectedRowData.gridOptionsWrapper.gridOptions.rowData[i].contType == this.validContainerList[j].type) 
               && (/^\d/.test(this.selectedRowData.gridOptionsWrapper.gridOptions.rowData[i].eqNumber))               
               && (this.selectedRowData.gridOptionsWrapper.gridOptions.rowData[i].contSize == parseInt(this.validContainerList[j].size))
               && !(usedJoSet.has(this.validContainerList[j].eqpNum)))){
              this.replaceEqRowJoNumber = this.joDetails[0].JoNumber;
              let tempObj = {
                "JoNumber": this.replaceEqRowJoNumber,
                "oldContainerNo": this.selectedRowData.gridOptionsWrapper.gridOptions.rowData[i].oldContainerNo,
                "oldContNoReplace": this.selectedRowData.gridOptionsWrapper.gridOptions.rowData[i].eqNumber, 
                "newContNoReplace": this.validContainerList[j].eqpNum,
                "contEmptyOrLaden": this.selectedRowData.gridOptionsWrapper.gridOptions.rowData[i].contEmptyOrLaden, 
                "contSize": this.selectedRowData.gridOptionsWrapper.gridOptions.rowData[i].contSize,
                "contType": this.selectedRowData.gridOptionsWrapper.gridOptions.rowData[i].contType,
              }
              this.joReplaceCntrList.push(tempObj); 
              usedJoSet.add(this.validContainerList[j].eqpNum)             
              this.selectedRowData.gridOptionsWrapper.gridOptions.rowData[i].eqNumber = this.validContainerList[j].eqpNum;
              this.selectedRowData.gridOptionsWrapper.gridOptions.api.setRowData(this.selectedRowData.gridOptionsWrapper.gridOptions.rowData);              
              break;
            }               
          }            
        }
        this.selectedRowData = undefined;
     } 
   }

  //show file upload component
  fileUploadFlag: boolean = true;
  uploadExcelSelectedRow:any =[];
  contractId:any;
  jobOrderType:any;
  uploadExcelFile(e) {
    if (this.joDetails.length == 1) {
      //this.uploadExcelSelectedRow = this.checkedSelectedRows[0];
      this.contractId = this.joDetails[0].contractId;
      this.jobOrderType =  this.joDetails[0].jobOrdType;
    }
    this.fileUploadFlag = false;    
    this.uploadExcel.emit({"fileUploadFlag": this.fileUploadFlag ,"contractId": this.contractId,"jobOrderType":this.jobOrderType});
  }  
  //#NIIT CR1 >>>>END

}



// functin to rendor AgGrid 
function conDtlRender(params) {

  //#NIIT CR6 >>>>BEGIN
  //handling undefined contsize
  if(params.data.contSize == undefined){
    params.data.contSize = "";
  }else{
    params.data.contSize = params.data.contSize;
  }
  //handling undefined contType
  if(params.data.contType == undefined){
    params.data.contType = "";
  }else{
    params.data.contType = params.data.contType;
  }
  //handling undefined contWeight
  if(params.data.contWeight == undefined){
    params.data.contWeight = "";
  }else{
    params.data.contWeight = params.data.contWeight;
  }
  //handling undefined contEmptyOrLaden
  if(params.data.contEmptyOrLaden == undefined){
    params.data.contEmptyOrLaden = "";
  }else{
    params.data.contEmptyOrLaden = params.data.contEmptyOrLaden;
  }
  //#NIIT CR6 >>>>END

  let template = document.createElement('template');
  template.innerHTML = ' <div>'
    + '<div class=\'row\'>'
    + '<div class=\'col-md-12 text-warning \'>Size</div>'
    + '<div class=\'col-md-15\'>' + params.data.contSize + '</div>'
    + '<div class=\'col-md-20 text-warning \'>Weight</div>'
    + '<div class=\'col-md-20\'><i class="fa fa-cube" aria-hidden="true"></i><sup style="font-size:10px;background: #FFF; color: #000;">' + params.data.contPercent + '%</sup></div>'
    + '</div>'
    + '<div class=\'row\'>'
    + '<div class=\'col-md-12 text-warning\'>Type</div>'
    + '<div class=\'col-md-15\'>' + params.data.contType + '</div>'
    + '<div class=\'col-md-20\'>' + params.data.contWeight + '</div>'
    + '<div class=\'col-md-20\'>' + params.data.contEmptyOrLaden + '</div>'
    + '</div>'
    + '</div>';  
  return template.innerHTML;
}

// functin to rendor AgGrid 
function stEndDtRender(params) {
  //to show empty instead of undefined
  if(params.data.startDate == undefined){
    params.data.startDate = "";
  } else {
    params.data.startDate = params.data.startDate;
  }
  
  //to show empty instead of undefined
  if(params.data.endDate == undefined){
    params.data.endDate = "";
  } else {
    params.data.endDate = params.data.endDate;
  }
  
  let template = document.createElement('template');
  template.innerHTML = " <div> "
    + "<div class='row'>"
    + "<div class='col-md-35 text-warning'>Start Date </div>"
    + "<div class='col-md-35'>" + params.data.startDate  + "</div>"
    + "</div>"
    + "<div class='row'>"
    + "<div class='col-md-35 text-warning'>End Date </div>"
    + "<div class='col-md-35'>" + params.data.endDate + "</div>"
    + "</div>"
    + "</div>";  
  return template.innerHTML;
}

// functin to rendor AgGrid 
function stweightRender(params) {
  //to show empty instead of undefined
  if(params.data.SOCorCOC == undefined){
    params.data.SOCorCOC = "";
  } else {
    params.data.SOCorCOC = params.data.SOCorCOC;
  }
  
  //to show empty instead of undefined
  if(params.data.contWeight == undefined){
    params.data.contWeight = "";
  } else {
    params.data.contWeight = params.data.contWeight;
  }

  
  let template = document.createElement('template');
  template.innerHTML = " <div> "
    + "<div class='row'>"    
    + "<div class='col-md-35'>" + params.data.SOCorCOC  + "</div>"
    + "</div>"
    + "<div class='row'>"
    // + "<div class='col-md-35 text-warning'>Weight </div>"
    // + "<div class='col-md-35'>" + params.data.contWeight + "</div>"
    + "</div>"
    + "</div>";
  
  return template.innerHTML;
}


// functin to rendor AgGrid 
function getRowStyleScheduled(params) {
  return {
    'background-color': '#516066',
    'color': 'White'
  };
};

//#NIIT CR6 >>>>BEGIN
function sizeTypeCostRenderer(params){

  // //to show empty instead of undefined
  if(params.data.amount == undefined){
    params.data.amount = "";
  } else {
    params.data.amount = params.data.amount;
  }
  
  //to show empty instead of undefined
  if(params.data.currency == undefined){
    params.data.currency = "";
  } else {
    params.data.currency = params.data.currency;
  }

  if(params.data.amount ==""){
    return null;
  } 
  else{
    
    let template = document.createElement('template');
    template.innerHTML = " <div> "
      + "<div class='row'>"    
      + "<div class='col-md-35'>" + params.data.amount+" ("+params.data.currency+")"+ "</div>"
      + "</div>"
      + "<div class='row'>"    
      + "</div>"
      + "</div>";
   
    return template.innerHTML;
  } 
}

function BkgBlTypeCostRenderer(params){

  // //to show empty instead of undefined
  if(params.data.amountBkgBl == undefined){
    params.data.amountBkgBl = "";
  } else {
    params.data.amountBkgBl = params.data.amountBkgBl;
  }

  if(params.data.amountBkgBl == ""){
    return null;
  }
  else{  
    let template = document.createElement('template');
    template.innerHTML = " <div> "
      + "<div class='row'>"    
      + "<div class='col-md-35'>" + params.data.amountBkgBl+" ("+params.data.currency+")"+ "</div>"
      + "</div>"
      + "<div class='row'>"    
      + "</div>"
      + "</div>";    
    return template.innerHTML;
  }
}


function lumpSumCostRenderer(params){

  //to show empty instead of undefined
  if(params.data.amountLumpsum == undefined){
    params.data.amountLumpsum = "";
  } else {
    params.data.amountLumpsum = params.data.amountLumpsum;
  }

  if(params.data.amountLumpsum == ""){
    return null;
  }
  else{    
    let template = document.createElement('template');
    template.innerHTML = " <div> "
      + "<div class='row'>"    
      + "<div class='col-md-35'>" + params.data.amountLumpsum+" ("+params.data.currency+")"+ "</div>"
      + "</div>"
      + "<div class='row'>"    
      + "</div>"
      + "</div>";    
    return template.innerHTML;
  }  
}

//#NIIT CR6 >>>>END