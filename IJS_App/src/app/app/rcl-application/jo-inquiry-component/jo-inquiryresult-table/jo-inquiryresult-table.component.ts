import { Component, Input, OnInit, ViewChild } from'@angular/core';
import { GridOptions } from'ag-grid';
declare var UIkit: any;
declare var jQuery: any;

@Component({
  selector: 'app-jo-inquiryresult-table',
  templateUrl: './jo-inquiryresult-table.component.html',
  styleUrls: ['./jo-inquiryresult-table.component.scss']
})
export class JoInquiryresultTableComponent implements OnInit {

  private Amount: string; //Remark

  amountUpdData: any = {
    "amountUpdParam": {
      //   transMode: "Select Transport",
      //   bookingType: "Booking/BL #"
      amount: "120000"
    },
    "action": "joSearch"
  }

  constructor() { }
  public isCollapsed: boolean = false;
  private gridOptions2: GridOptions;
  private columnDefs2: any[];
  private EqDetailsList: any;
  private showLocErrorText: boolean = false;
  private errorTextLookUp: string;

  @Input() private filterData: any;
  @Input() private filterDataSelectedComp: any;
  @Input() private tableDataMaintainJoSearch: any;
  @ViewChild('agGrid') private _agGrid
  @ViewChild('joInquiryEquipmentBrowser') _joInquiryEquipmentBrowser;
  @ViewChild('rclBookingBL') _rclBookingBL;
  @ViewChild('rclJoLog') _rclJoLog;

  ngOnInit() {
    this.columnDefs2 = [
      {
        headerName: '#', width: 30, checkboxSelection: true, suppressSorting: true,
        suppressMenu: true, cellStyle: {
          'padding-top': '2px',
          'text-align': 'center'
          //'font-size':'20px',
        }
      },
      {
        headerName: 'Equipt State', field: 'equiptState', width: 200, cellStyle: {
          'white-space': 'normal',
          'overflow': 'initial',
          'padding-top': '2px',
          'padding-left': '5px',
          //'font-size':'20px',

        }
      },
      {
        headerName: 'Equip #', field: 'eqNumber', width: 200, cellStyle: {
          'padding-top': '2px', 'padding-left': '5px', 'color': '#0099FF',
          //'font-size':'20px',
          'text-decoration': ' underline', 'font-weight': 'bold'
        }
      },
      {
        headerName: 'Booking/BL', field: 'BkgOrBLNo', width: 200, cellStyle: {
          'padding-top': '2px', 'padding-left': '5px', 'color': '#0099FF',
          //'font-size':'20px',
          'text-decoration': ' underline'
        }
      },
      {
        headerName: 'Container Detail', field: 'conDtk', width: 200, cellStyle: {
          'padding-top': '2px', 'padding-left': '5px',
          //'font-size':'20px'
        },
        cellRenderer: function (params) {      // Function cell renderer
          return conDtlRender(params);
        }
      },
      {
        headerName: 'SOC/COC', field: 'SOCorCOC', width: 200, cellStyle: {
          //'font-size':'  20px',
          'padding-top': '2px', 'padding-left': '5px'
        }
      },
      {
        headerName: 'Date', field: 'dt', width: 200, cellStyle: {
          'padding-top': '2px', 'padding-left': '5px'
        },

        cellRenderer: function (params) {      // Function cell renderer
          return stEndDtRender(params);
        }
      },

      {
        headerName: 'Group Sample',
        children: [
          {
            headerName: "DG/RF/OGG", field: "DGorRForOG", width: 150, cellStyle: { 'padding-top': '2px', 'padding-left': '5px' }, columnGroupShow: 'close'
          },
          ,
          {
            headerName: "Port class", field: "portClass", width: 150, cellStyle: { 'padding-top': '2px', 'padding-left': '5px' }, columnGroupShow: 'open'
          },
          {
            headerName: "IMDG Class", field: "IMDGDetail", width: 150, cellStyle: { 'padding-top': '2px', 'padding-left': '5px' }, columnGroupShow: 'open'
          },
          {
            headerName: "UNNO", field: "UNNO", width: 150, cellStyle: { 'padding-top': '2px', 'padding-left': '5px' }, columnGroupShow: 'open'
          },
          {
            headerName: "Variant", field: "variant", width: 150, cellStyle: { 'padding-top': '2px', 'padding-left': '5px' }, columnGroupShow: 'open'
          },
          {
            headerName: "Flash Point", field: "flashPoint", width: 150, cellStyle: { 'padding-top': '2px', 'padding-left': '5px' }, columnGroupShow: 'open'
          },
          {
            headerName: "Temp From", field: "tempFrom", width: 150, cellStyle: { 'padding-top': '2px', 'padding-left': '5px' }, columnGroupShow: 'open'
          },
          ,
          {
            headerName: "Temp To", field: "tempTo", width: 150, cellStyle: { 'padding-top': '2px', 'padding-left': '5px' }, columnGroupShow: 'open'
          },
          {
            headerName: "OH", field: "OH", width: 150, cellStyle: { 'padding-top': '2px', 'padding-left': '5px' }, columnGroupShow: 'open'
          },
          {
            headerName: "OLF", field: "OLF", width: 150, cellStyle: { 'padding-top': '2px', 'padding-left': '5px' }, columnGroupShow: 'open'
          },
          {
            headerName: "OWL", field: "OWL", width: 150, cellStyle: { 'padding-top': '2px', 'padding-left': '5px' }, columnGroupShow: 'open'
          },
          {
            headerName: "OWR", field: "OWR", width: 150, cellStyle: { 'padding-top': '2px', 'padding-left': '5px' }, columnGroupShow: 'open'
          },
          {
            headerName: "OLA", field: "OLA", width: 150, cellStyle: { 'padding-top': '2px', 'padding-left': '5px' }, columnGroupShow: 'open'
          }
        ]
      },
      { headerName: 'Currency', field: 'currency', width: 200, cellStyle: { 'padding-top': '2px', 'padding-left': '5px' } },
      { headerName: 'Amount', field: 'amount', width: 200, cellStyle: { 'padding-top': '2px', 'padding-left': '5px' } },
      { headerName: 'Amount USD', field: 'amountUSD', width: 200, cellStyle: { 'padding-top': '2px', 'padding-left': '5px' } },

    ];
    this.gridOptions2 = { rowHeight: 60, getRowStyle: getRowStyleScheduled };
  }


  private agGridCellClicked(e) {    
    if (e.colDef.field == "eqNumber") {
      this.addEquipmentBrowserModal(e)
    } else if (e.colDef.field == "bkgBl") {
      this.insertShowBookingBL(e)
    }
  }

  //open Eq browser modal lookup
  private addEquipmentBrowserModal(e) {
    this._joInquiryEquipmentBrowser.openLookUpModal();
  }

  //For Opening log lookup
  insertShowRclJoLog(e) {
    this._rclJoLog.openLookUpModal();
  }
  updateEqDetails(e) {
    this.EqDetailsList = e;
  }

  //open BL/Booking modal lookup
  insertShowBookingBL(e) {
    this._rclBookingBL.openLookUpModal();
  }


  private openAmountPopUp(e) {
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

  setAmountValue(e) {
    var isValidated = this.amountLookupValidation();
    if (isValidated) {

      if (this.Amount) {
        this.amountUpdData.amountUpdParam.amount = this.Amount;
      }
      UIkit.modal('#summaryAmountModal').hide();
    }
  }

  //validation 
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
  showCollapsedTable(e) {
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

  rowSelected1(e) {
   
  }

}

// functin to rendor AgGrid 
function conDtlRender(params) {  
  let template = document.createElement('template');
  template.innerHTML = ' <div>'
    + '<div class=\'row\'>'
    + '<div class=\'col-md-30 text-warning \'>Size</div>'
    + '<div class=\'col-md-20\'>' + params.data.contSize + '</div>'
    + '<div class=\'col-md-20\'>'+ params.data.contPercent +'</div>'
    + '</div>'
    + '<div class=\'row\'>'
    + '<div class=\'col-md-30 text-warning\'>Type</div>'
    + '<div class=\'col-md-20\'>' + params.data.contType + '</div>'
    + '<div class=\'col-md-20\'>' + params.data.contEmptyOrLaden + '</div>'
    + '</div>'
    + '</div>';  
  return template.innerHTML;
}

// functin to rendor AgGrid 
function stEndDtRender(params) { 
  let template = document.createElement('template');
  template.innerHTML = " <div> "
    + "<div class='row'>"
    + "<div class='col-md-35 text-warning'>Start Date </div>"
    + "<div class='col-md-35'>" + params.data.startDate + "</div>"
    + "</div>"
    + "<div class='row'>"
    + "<div class='col-md-35 text-warning'>End Date </div>"
    + "<div class='col-md-35'>" + params.data.endDate + "</div>"
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