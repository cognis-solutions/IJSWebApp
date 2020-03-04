import { Component, OnInit, Output, EventEmitter } from '@angular/core';
declare var UIkit: any;
import * as _ from 'lodash';

@Component({
  selector: 'app-spcl-cost-bl-booking',
  templateUrl: './spcl-cost-bl-booking.component.html',
  styleUrls: ['./spcl-cost-bl-booking.component.scss']
})
export class SpclCostBlBookingComponent implements OnInit {

  constructor() { }
  blBookingOptions = [
    {
      label: 'Booking',
      value: 'BK'
    }, {
      label: 'BL',
      value: 'BL'
    }
  ];
  lookUpConfig: any = {
    highlight: false,
    create: false,
    persist: true,
    plugins: ['dropdown_direction', 'remove_button'],
    dropdownDirection: 'down',
    labelField: 'label',
    valueField: 'value',
    searchField: ['label'],
    maxItems: 1
  };
  spclBlBookingList: any = [];
  elementsToDel: any = [];

  blBookingValue: string;
  spclChargeCode: string;
  
  chargeCodeLookUpData: any = [{ "label": "Charge Code", "value": "chargeCodeLookup", "dropDownData": [{ "label": "Charge Code", "value": "chargeCode" }, { "label": "Description", "value": "description" }, { "label": "Status", "value": "status" }] }]

  @Output() public splBlBookingDataList = new EventEmitter();


  showModal(val) {    
    if (!val) {
      this.spclBlBookingList = [];
    } else {
      this.spclBlBookingList = val;
    }
    this.elementsToDel = [];
    UIkit.modal('#spcl-cost-bl-booking').show();
  }

  lookupErrorCodeShow: any = false;
  //add row in table
  addRow() {
    this.lookupErrorCodeShow = false;
    let rowObj = {
      "bkgBlNum": this.blBookingValue,
      "charge": this.spclChargeCode
    }
    
    if(this.spclBlBookingList.length < 1) {
       this.spclBlBookingList.push(rowObj);
    } else {
      this.spclBlBookingList.forEach(element => {      
      if(_.isEqual(element, rowObj)){              
        this.lookupErrorCodeShow = true;               
      }     
    });
    if(!this.lookupErrorCodeShow) {
      this.lookupErrorCodeShow = false;
      this.spclBlBookingList.push(rowObj);
    }
    }    
  }
  //delete row
  deleteRow() {
    let x = this.elementsToDel.length;
    while (x--) {
      let y = this.spclBlBookingList.length;
      while (y--) {
        if (this.elementsToDel[x] == y) {
          this.spclBlBookingList.splice(y, 1);
        }
      }
    }
    this.elementsToDel = [];
  }

  //select Check box
  checkSelectedRow(e, rowData, i) {
    this.elementsToDel.push(i);
    this.deleteRow()    
  }

  ngOnInit() {
   
  }

  saveRow() {
    this.splBlBookingDataList.emit(this.spclBlBookingList);
    this.spclBlBookingList = [];
    this.blBookingValue = undefined;
    this.spclChargeCode = undefined;
    UIkit.modal('#spcl-cost-bl-booking').hide();
  }
  
  resetSplbooking() {
    this.spclBlBookingList = [];
    this.blBookingValue = undefined;
    this.spclChargeCode = undefined;
    UIkit.modal('#spcl-cost-bl-booking').hide();
  }
}
