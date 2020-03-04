import { Injectable } from '@angular/core';

@Injectable()
export class SortSearchTableService {

  constructor() { }

  //sort table data
  sortTableData(tableData, sortIn, orderBy) {
   // debugger;
   


    if (sortIn == undefined) {
     // sortIn = "vendorCode";
    } else if (sortIn == 'startDate') {
      tableData.sort(function (a, b) {
        var aa = a[sortIn].split('/').reverse().join(),
          bb = b[sortIn].split('/').reverse().join();
        return aa < bb ? -1 : (aa > bb ? 1 : 0);
      });
    }

    tableData.sort((a, b) => {
      if (orderBy == "asnd") {        
        if(a[sortIn] === "" || a[sortIn] === undefined) 
           return 1;
        if(b[sortIn] === "" || b[sortIn] === undefined) 
           return -1;
        if(a[sortIn] === b[sortIn]) 
           return 0;
        return a[sortIn] < b[sortIn] ? -1 : 1;
      } else if (orderBy == "dsnd") {        
        if(a[sortIn] === "" || a[sortIn] === undefined) 
           return 1;
        if(b[sortIn] === "" || b[sortIn] === undefined) 
           return -1;
        if(a[sortIn] === b[sortIn]) 
           return 0;
        return a[sortIn] < b[sortIn] ? 1 : -1;
      } 
        
    
        // else {
        //   if (a["priority"] < b["priority"])
        //     return -1;
        //   if (a["priority"] > b["priority"])
        //     return 1;
        //   else
        //     return 0;
        // }
      // } else if (orderBy == "lp") {
      //   sortIn = "priority"
      //   if (a[sortIn] < b[sortIn]) {
      //     return 1
      //   }
      //   if (a[sortIn] > b[sortIn]) {
      //     return -1;
      //   }
      //   else
      //     return 0

        // else {
        //   if (a["priority"] < b["priority"])
        //     return 1;
        //   if (a["priority"] > b["priority"])
        //     return -1;
        //   else
        //     return 0
        // }
      
    });


    return tableData;
  }



}
