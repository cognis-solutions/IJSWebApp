import { Component, OnInit, ViewChild, EventEmitter, Output } from '@angular/core';
import { ProcessjoSortSearchTableService } from "./processjo-sort-search-table.service";

@Component({
  selector: 'app-process-jo',
  templateUrl: './process-jo.component.html',
  styleUrls: ['./process-jo.component.scss']
})


export class ProcessJoComponent implements OnInit {

  processJoResultsFlag:boolean = false;
  processJoAdhocFlag:boolean = false;
  processJoSearchData: any;
  tableDataProcessJoSearch: any = [];
  filteredTableDataProcessJoSearch: any = [];
  showHideJoSummeryFlag: boolean = true;
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
          id: "bkgOrBLNumber",
          text: "Booking/BL"
        },
        {
          id: "transMode",
          text: "Transport Mode"
        },
        {
          id: "startDate",
          text: "EFFECTIVE DATE"
        },
        {
          id: "endDate",
          text: "EXPIRY DATE"
        },
        {
          id: "fromTerminal",
          text: "From"
        },
        {
          id: "toTerminal",
          text: "To"
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
  
  @ViewChild('processJoSearch') _processJoSearch: any;
  @ViewChild('processjoAddHoc') _processjoAddHoc: any;
  @ViewChild('processJoTable') _processJoTable: any;
  @ViewChild('joSummary') joSummary: any;
  constructor(private _sortTable: ProcessjoSortSearchTableService) { }
  
  searchProcessJoOrder(e) {
    this.filterDataSelectedComp = e.filterInputData;
    this.processJoSearchData = e.searchInputData;
    this.tableDataProcessJoSearch = e.searchOutputData; 
    this.filteredTableDataProcessJoSearch = e.searchOutputData; 
    this.processJoResultsFlag = true;    
  }
  
  removeFilterBackEnd(e) {
    this.filterDataSelectedComp = e.filterData;
    this.tableDataProcessJoSearch = e.resultTable;
    this.filteredTableDataProcessJoSearch = e.resultTable;
  }
  
  ngOnInit() {
    this.processJoSearchData = this._processJoSearch.getSearchingData();
  }
  
  changeFind() {    
    this.processJoResultsFlag = false;
    this.showHideJoSummeryFlag = true;
    this._processJoTable.showJoSummeryFlag =  false;
  }   

  //changeFind for change vendor
  changeFindforChangeVendor(){
    this.changeFind();
  }       
          
  searchobj: any={};
  addHocTypeName: any;
  addHocTypeCode: any;
  routeListInputValue: any;
  routeListInputDate: any;
  uploadListLimit: any; //#NIIT CR3
  //fromLoc:any;
  //toLoc:any;
  showjoAddHoc(obj){    
    this.searchobj = obj;
    this.uploadListLimit = obj.joSummaryLimit; //#NIIT CR3
    this.addHocTypeName = obj.addHocTypeName;
    this.addHocTypeCode = obj.addHocTypeCode;
    this.routeListInputValue = obj.vendorCode; 
    this.routeListInputDate = obj.endDate; 
   // this.fromLoc=obj.fromLoc;
   // this.toLoc=obj.toLoc;  
    this.processJoAdhocFlag = true;
  }

  hideJoAddHoc(e) {
    this.processJoAdhocFlag = false;
    this._processJoSearch.checkBoxValue = false;
  }

  createNextJobOrder(e)
  {
    //alert(e);
    this.showHideJoSummeryFlag=true;
    this._processJoTable.saveAllSelectNext(e);
  }

  showJoSummeryTable (tempObj) {    
    this.showHideJoSummeryFlag = false;    
    this.joSummary.processJoType = this.processJoSearchData['processJoParam']['processJoType']; 
    this.joSummary.joSummerySaveObj = tempObj;
    this.joSummary.getPrcessJoBackEndData();    
  }
 resetJoSummery($event) {
    this.showHideJoSummeryFlag = true;
    this._processJoTable.showJoSummeryFlag =  false;
    this._processJoTable.desellectSelectedRows();
  }
  
  enableButtonsWhileError($event){
    this._processJoTable.showJoSummeryFlag =  false;
  } 
  
  
  refreshResultTable(obj) {
    this.filteredTableDataProcessJoSearch = this._sortTable.sortTableData(obj['resultTable'], obj['filterData']['sortIn'], obj['filterData']['orderBy']);
    this.tableDataProcessJoSearch = this.filteredTableDataProcessJoSearch;
  }

}
