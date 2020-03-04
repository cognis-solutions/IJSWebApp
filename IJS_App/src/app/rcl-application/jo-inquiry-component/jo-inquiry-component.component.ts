import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-jo-inquiry-component',
  templateUrl: './jo-inquiry-component.component.html',
  styleUrls: ['./jo-inquiry-component.component.scss']
})
export class JoInquiryComponentComponent implements OnInit {

  maintainJoResultsFlag: boolean = false;
  maintainJoSearchData: any = {
    maintainJoParam: {}
  };
  tableDataMaintainJoSearch: any = [];
  filteredTableDataMaintainJoSearch: any = [];


  //filter data selected
  filterDataSelectedComp: any = {
    "filterDataArr": []
  };
  filterData: any = [
    {
      name: "Port Type",
      type: "radio",
      data: [
        {
          id: "port",
          text: "Port"
        },
        {
          id: "Inland Point",
          text: "inPoint"
        }
      ]
    },
    {
      name: "SOC/COC",
      type: "radio",
      data: [
        {
          id: "SOC",
          text: "SOC"
        },
        {
          id: "COC",
          text: "COC"
        }
      ]
    },
    {
      name: "Sort in",
      type: "radio",
      data: [
        {
          id: "bookingCode",
          text: "Booking"
        },
        {
          id: "blCode",
          text: "BL"
        },
        {
          id: "vendorCode",
          text: "Vendor id"
        },
        {
          id: "vendorName",
          text: "Vendor name"
        },
        {
          id: "jobOrder",
          text: "Job Order"
        },
        {
          id: "routing",
          text: "Routing"
        },
        {
          id: "contract",
          text: "Contract"
        },
        {
          id: "container",
          text: "Container"
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

  constructor() { }

  ngOnInit() {
  }




  private searchJoInquiryOrder(e) {
    this.filterDataSelectedComp = e.filterInputData;
    this.maintainJoSearchData = e.searchInputData;
    this.tableDataMaintainJoSearch = e.searchOutputData;
    this.filteredTableDataMaintainJoSearch = e.searchOutputData;
    this.maintainJoResultsFlag = true;
  }
  
  
  changeFind() {
    this.maintainJoResultsFlag = false;
  }
}
