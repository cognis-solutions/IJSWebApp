import { Injectable } from '@angular/core';

@Injectable()
export class ProcessjoSortSearchTableService {

  constructor() { }
  tableDataContractSearch: any;
  //sort table data
  sortTableData(tableData, sortIn, orderBy) {
    if (orderBy == undefined) {
      orderBy = "asnd";
    }
    if (sortIn == undefined) {
      sortIn = "bkgOrBLNumber";
    }
    tableData.sort((a, b) => {
      if (orderBy == "asnd") {
        if (a[sortIn] < b[sortIn])
          return -1;
        if (a[sortIn] > b[sortIn])
          return 1;
        else
          return 0;
      } else if (orderBy == "dsnd") {
        if (a[sortIn] < b[sortIn])
          return 1;
        if (a[sortIn] > b[sortIn])
          return -1;
        else
          return 0;
      } else if (orderBy == "hp") {
        if (a[sortIn] < b[sortIn]) {
          return -1;
        }
        if (a[sortIn] > b[sortIn]) {
          return 1;
        }
        else {
          if (a["priority"] < b["priority"])
            return -1;
          if (a["priority"] > b["priority"])
            return 1;
          else
            return 0;
        }
      } else if (orderBy == "lp") {
        if (a[sortIn] < b[sortIn]) {
          return 1
        }
        if (a[sortIn] > b[sortIn]) {
          return -1;
        }
        else {
          if (a["priority"] < b["priority"])
            return 1;
          if (a["priority"] > b["priority"])
            return -1;
          else
            return 0
        }
      }
    });
    return tableData;
  }
  
      //code for filter the data od the screen
  addRemoveSortFilter(filterDataSelectedComp, tableDataContractSearch1) {
    this.tableDataContractSearch = tableDataContractSearch1;
    if (filterDataSelectedComp !== undefined) {      
      this.tableDataContractSearch = this.sortTableData(this.tableDataContractSearch, filterDataSelectedComp.sortIn, filterDataSelectedComp.orderBy);
      return this.tableDataContractSearch;
    }
  }
  
}
