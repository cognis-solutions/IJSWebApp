import { Injectable } from '@angular/core';

@Injectable()
export class JoMaintenanceSortingService {

  constructor() { }
  tableDataContractSearch: any;

  formattedDate;
  getFormattedDate(date){
    this.formattedDate = date.split(/\//);    
    this.formattedDate = date.split(/\//).reverse().join('/');
    return new Date(this.formattedDate);    
  }

  //sort table data
  sortTableData(tableData, sortIn, orderBy) {
    if (orderBy == undefined) {
      orderBy = "asnd";
    }
    if (sortIn == undefined) {
      sortIn = "JoNumber";
    }
    tableData.sort((a, b) => {
      if (orderBy == "asnd") {        
        if (sortIn == 'orderDate' || sortIn == 'approveDate' || 
          sortIn == 'startDate' || sortIn == 'endDate') {
         if(a[sortIn] === "" || a[sortIn] === undefined) 
            return 1;
         if(b[sortIn] === "" || b[sortIn] === undefined) 
            return -1;
         
          var date1 = this.getFormattedDate(a[sortIn]);
          var date2 = this.getFormattedDate(b[sortIn]); 
          if (date1 < date2)
            return -1;
          else if (date1 > date2)
            return 1;
          else
            return 0;
        } else {
          // if (sortIn == 'amount' ){
          //   sortIn='amountNum' 
          // }
          if (a[sortIn] < b[sortIn])
            return -1;
          else if (a[sortIn] > b[sortIn])
            return 1;
          else
            return 0;
        }
      } else if (orderBy == "dsnd") {
        if (sortIn == 'orderDate' || sortIn == 'approveDate' || sortIn == 'startDate' || sortIn == 'endDate') {
          if(a[sortIn] === "" || a[sortIn] === undefined) 
          return 1;
          if(b[sortIn] === "" || b[sortIn] === undefined) 
          return -1;
         
          var date1 = this.getFormattedDate(a[sortIn]);
          var date2 = this.getFormattedDate(b[sortIn]); 
          if (date1 < date2)
            return 1;
          else if (date1 > date2)
            return -1;
          else
            return 0;
        }else {
          // if (sortIn == 'amount' ){
          //   sortIn='amountNum' 
          // }
          if (a[sortIn] < b[sortIn])
            return 1;
          else if (a[sortIn] > b[sortIn])
            return -1;
          else
            return 0;
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
