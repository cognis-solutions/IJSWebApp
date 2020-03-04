import { Component, OnInit, Input, Output, EventEmitter } from '@angular/core';
import { ProcessjoSortSearchTableService } from "../processjo-sort-search-table.service";
import { ProcessjoSearchService } from "../processjo-search.service";
import { UserTypeService } from "../../../user/user-type.service";
import { SpinnerServiceService } from "../../../common-services/spinner-service.service";
import { ServerErrorcodeService } from "../../../common-services/server-errorcode.service";
import { SessionTimeOutService } from "app/common-services/session-time-out.service";

@Component({
  selector: 'app-processjo-result-filter',
  templateUrl: './processjo-result-filter.component.html',
  styleUrls: ['./processjo-result-filter.component.scss']
})
export class ProcessjoResultFilterComponent implements OnInit {

  constructor(private _spinner: SpinnerServiceService, private _serverErrorCode: ServerErrorcodeService, private _sortTable: ProcessjoSortSearchTableService, private _userTypeService: UserTypeService, public _joborderService: ProcessjoSearchService,private _sessionTimeOutService:SessionTimeOutService) { }

  @Input() private processJoSearchDataInput: any;
  @Input() private tableDataProcessJoSearch: any;
  @Input() private filterDataSelectedComp: any;
  @Output() changeFind: EventEmitter<any> = new EventEmitter();
  @Output() refreshData: EventEmitter<any> = new EventEmitter();


  ngOnInit() {
  }
  proceesjochangeFind(e) {
    this.changeFind.emit(e);
  }
  //remove filter criteriea using bubbles
  proceesjoRemoveFilter(filterObj) {
    if (typeof filterObj === "string") {
      if (filterObj === this.filterDataSelectedComp.sortIn) {
        delete this.filterDataSelectedComp.sortIn;
      }
      if (filterObj === this.filterDataSelectedComp.orderBy) {
        delete this.filterDataSelectedComp.orderBy;
      }
    }

    let filteredData = this._sortTable.addRemoveSortFilter(this.filterDataSelectedComp, this.tableDataProcessJoSearch);
  }

  //cross button click in filter to get data from backend
  searchChnageJobData(propName) {
    if (this.processJoSearchDataInput.processJoParam.hasOwnProperty(propName)) {
      if (this.processJoSearchDataInput.processJoParam[propName] == 'fromLocType') {
        delete this.processJoSearchDataInput.processJoParam.fromTerminal;
        delete this.processJoSearchDataInput.processJoParam.fromLocation;
      } else if (this.processJoSearchDataInput.processJoParam[propName] == 'toLocType') {
        delete this.processJoSearchDataInput.processJoParam.toTerminal;
        delete this.processJoSearchDataInput.processJoParam.toLocation;
      } else {
        delete this.processJoSearchDataInput.processJoParam[propName];
      }
    }
    this.getBackEndRefreshedTableData();
  }

  private getBackEndRefreshedTableData() {
    this._spinner.showSpinner();
    this.processJoSearchDataInput['action'] = 'joSearch';
    let backendData = this._joborderService.getProcessjoSearchData(this.processJoSearchDataInput);
    backendData.subscribe(
      (data) => {
        if(data == "userSessionExpired"){
          this._sessionTimeOutService.checkSessionTimeout(data);
        } else {
          this.tableDataProcessJoSearch = data;
          let tempObj = {
            resultTable: this.tableDataProcessJoSearch,
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
