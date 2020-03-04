import { Component, OnInit, Input, Output, EventEmitter } from '@angular/core';
import { WindowRefService } from "../../../common-services/window-ref.service";
import { UserTypeService } from "../../../user/user-type.service";
import { SpinnerServiceService } from "../../../common-services/spinner-service.service";
import { ServerErrorcodeService } from "../../../common-services/server-errorcode.service";
import { JoMaintenanceSearchService } from "../jo-maintenance-search.service";
import { JoMaintenanceSortingService } from "../jo-maintenance-sorting.service";
import { SessionTimeOutService } from "app/common-services/session-time-out.service";

@Component({
  selector: 'app-jo-maintainence-job-order-filter',
  templateUrl: './jo-maintainence-job-order-filter.component.html',
  styleUrls: ['./jo-maintainence-job-order-filter.component.scss']
})
export class JoMaintainenceJobOrderFilterComponent implements OnInit {

  @Input() private filterData: any;
  @Input() private maintainJoSearchData: any;
  @Input() private tableDataMaintainJoSearch: any;
  @Input() private filterDataSelectedComp: any;
  @Input() private jobOrdTypeText: string;
  @Input() private jobOrdStsText: string;
  @Output() changeFind: EventEmitter<any> = new EventEmitter();
  @Output() refreshData: EventEmitter<any> = new EventEmitter();


  constructor(private _spinner: SpinnerServiceService, private _maintenanceService: JoMaintenanceSearchService, private _sortTable: JoMaintenanceSortingService,private _sessionTimeOutService:SessionTimeOutService) {
    
  }

  ngOnInit() {
  }

  maintainjochangeFind(e) {
    this.changeFind.emit(e);
  }

  maintainancejoRemoveFilter(filterObj) {
    if (typeof filterObj === "string") {
      if (filterObj === this.filterDataSelectedComp.sortIn) {
        delete this.filterDataSelectedComp.sortIn;
      }
      if (filterObj === this.filterDataSelectedComp.orderBy) {
        delete this.filterDataSelectedComp.orderBy;
      }
    }
    let tempObj = {
      resultTable: this.tableDataMaintainJoSearch,
      filterData: this.filterDataSelectedComp
    }
    this.refreshData.emit(tempObj)

  }

  //cross button click in filter to get data from backend
  searchChnageJobData(propName) {
    if (this.maintainJoSearchData.maintainJoParam.hasOwnProperty(propName)) {
      if (this.maintainJoSearchData.maintainJoParam[propName] == 'bookingOrBlValue') {
        delete this.maintainJoSearchData.maintainJoParam['bookingOrBlType']
      } else if (this.maintainJoSearchData.maintainJoParam[propName] == 'routContractOrContValue') {
        delete this.maintainJoSearchData.maintainJoParam['routContractOrContType']
      } else if (this.maintainJoSearchData.maintainJoParam[propName] == 'joCostValue') {
        delete this.maintainJoSearchData.maintainJoParam.joCostTyp;
      } else if (this.maintainJoSearchData.maintainJoParam[propName] == 'fromLocType') {
        delete this.maintainJoSearchData.maintainJoParam.fromTerminal;
        delete this.maintainJoSearchData.maintainJoParam.fromLocation;
      } else if (this.maintainJoSearchData.maintainJoParam[propName] == 'toLocType') {
        delete this.maintainJoSearchData.maintainJoParam.toTerminal;
        delete this.maintainJoSearchData.maintainJoParam.toLocation;
      } else {
        delete this.maintainJoSearchData.maintainJoParam[propName];
      }
      this.getBackEndRefreshedTableData();
    }
  }


  private getBackEndRefreshedTableData() {
    this._spinner.showSpinner();
    this.maintainJoSearchData['action'] = 'maintainJoSearch';
    let backendData = this._maintenanceService.getMaintenanceData(this.maintainJoSearchData);
    backendData.subscribe(
      (data) => {
        if(data == "userSessionExpired"){
          this._sessionTimeOutService.checkSessionTimeout(data);
        } else{
          this.tableDataMaintainJoSearch = data;
          let tempObj = {
            resultTable: this.tableDataMaintainJoSearch,
            filterData: this.filterDataSelectedComp
          }
          this.refreshData.emit(tempObj)
          this._spinner.hideSpinner();
        } 
      },
      (err) => {
        this._spinner.hideSpinner();
        console.log(err);
      }
    )
  }

}
