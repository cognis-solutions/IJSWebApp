import { Component, OnInit, ViewChild, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';


import { JoMaintenanceSortingService } from "./jo-maintenance-sorting.service";
import { SpinnerServiceService } from "../../common-services/spinner-service.service";
import { ServerErrorcodeService } from "../../common-services/server-errorcode.service";
import { JoMaintenanceSearchService } from "./jo-maintenance-search.service";
import { SessionTimeOutService } from "app/common-services/session-time-out.service";


@Component({
  selector: 'app-jo-maintenance',
  templateUrl: './jo-maintenance.component.html',
  styleUrls: ['./jo-maintenance.component.scss']
})
export class JoMaintenanceComponent implements OnInit {

  sub: any;
  componentType: string;
  viewJoUrl: string;
  compName: string;
  maintainJoResultsFlag: boolean = false;
  maintainJoSearchData: any = {
    maintainJoParam: {}
  };
  tableDataMaintainJoSearch: any = [];
  filteredTableDataMaintainJoSearch: any = [];
  @ViewChild('joInquirySearch') _joMaintenanceSearch: any;

  //filter data selected
  filterDataSelectedComp: any = {
    "filterDataArr": []
  };
  filterData: any = [
    {
      name: "Sort in",
      type: "radio",
      data: [
        {
          id: "JoNumber",
          text: "Job Order"
        },
        {
          id: "orderDate",
          text: "Order Date"
        },
        {
          id: "approveDate",
          text: "Approve Date"
        },
        {
          id: "vendorID",
          text: "Vendor Code"
        },
        {
          id: "vendorName",
          text: "Vendor Name"
        },
        {
          id: "amountNum",
          text: "Amount"
        }
      ]
    },
    {
      name: "Order by",
      type: "radio",
      data: [
        {
          id: "asnd",
          text: "Ascending"
        },
        {
          id: "dsnd",
          text: "Descending"
        }
      ]
    }
  ];
  
      jobOrdTypeText: string;
    jobOrdStsText: string

  @ViewChild('maintainResultTable') _maintainResultTable: any;


  constructor(private _sortTable: JoMaintenanceSortingService, private _maintenanceService: JoMaintenanceSearchService, private _spinner: SpinnerServiceService, private route: ActivatedRoute,private _sessionTimeOutService:SessionTimeOutService) { }

  ngOnInit() {
    this.sub = this.route
      .data
      .subscribe(data => {
        this.componentType = data['componentType'];
        
        if(this.componentType == 'joinquiry') {
          this.compName = 'JO Inquiry'
        } else if (this.componentType == 'jomaintenance') {
          this.compName = 'JO Maintenance'
        }        
      });
    this.getViewJoUrl() ;
  }

  ngOnDestroy() {
    this.sub.unsubscribe();
  }

  socCocType;
  totalRecords: number = 0;
  private searchJoInquiryOrder(e) {
    this.filterDataSelectedComp = e.filterInputData;
    this.maintainJoSearchData = e.searchInputData;
    this.tableDataMaintainJoSearch = e.searchOutputData;
    this.jobOrdTypeText = e.jobOrdTypeText;
    this.jobOrdStsText = e.jobOrdStsText;    
    this.maintainJoResultsFlag = true;
    this.totalRecords = e.totalRecords;
    this.socCocType= e.checkSocCocType
  }

  refreshResultTable(obj) {
    this.tableDataMaintainJoSearch = this._sortTable.sortTableData(obj['resultTable'], obj['filterData']['sortIn'], obj['filterData']['orderBy']);
  }

  saveMaintainJo($event) {
    let tempObj = {
      action: 'saveMaintainJO',
      joSaveList: this._maintainResultTable.checkedJoNumber
    }
    let backData = this._maintenanceService.getMaintenanceData(tempObj)
    backData.subscribe(
      (data) => {
        if(data == "userSessionExpired"){
          this._sessionTimeOutService.checkSessionTimeout(data);
        } 
        
      },
      (err) => {
        console.log(err);
      }
    )

  }

  getViewJoUrl() {
    let tempObj = {
      action: 'viewJoUrl',
     // viewJoUrl: this._maintainResultTable.checkedJoNumber
    }
    let backData = this._maintenanceService.getViewJoUrl();
    backData.subscribe(
      (data) => {
        if(data == "userSessionExpired"){
          this._sessionTimeOutService.checkSessionTimeout(data);
        }else{
          this.viewJoUrl=data;
         // console.log(data);
        }        
      },
      (err) => {
        console.log(err);
      }
    )

  }

  changeFind() {
    this.maintainJoResultsFlag = false; 
    this.maintainJoSearchData.maintainJoParam['jobOrdStsText'] = "";
    this.maintainJoSearchData.maintainJoParam['jobOrdSts'] = "";
    this.maintainJoSearchData.maintainJoParam['SocOrCoc'] = "";
    this._joMaintenanceSearch.jobOrdStsText="Job Order status";
  }

  //#NIIT CR1 >>>>BEGIN
  uploadExcelFlag:boolean = true;
  addHocTypeName:any;
  routeContractID:any;
  uploadExcel(e){    
    this.addHocTypeName = e.jobOrderType;
    this.routeContractID = e.contractId;
    this.uploadExcelFlag = e.fileUploadFlag;
  }
  
  closeFileUpload(e){
    this.uploadExcelFlag = true;
  }

  validContainerList:any =[];
  getContainersList(e){
    this.validContainerList = [];
    this.uploadExcelFlag = true;
    this.validContainerList = e;    
  }
  //#NIIT CR1 >>>>END

}
