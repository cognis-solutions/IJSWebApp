import { Component, Optional, Inject, ViewChild, EventEmitter, Input, Output   } from '@angular/core';
import {
  NgModel,
  NG_VALUE_ACCESSOR,
  NG_VALIDATORS,
  NG_ASYNC_VALIDATORS,
} from '@angular/forms';
import { noop } from 'rxjs/util/noop';
import { ElementBase } from '../rcl-base/element-base';

@Component({
  selector: 'app-rcl-searchfilter',
  templateUrl: './rcl-searchfilter.component.html',
  styleUrls: ['./rcl-searchfilter.component.scss'],
  providers: [
    {
      provide: NG_VALUE_ACCESSOR,
      useExisting: RclSearchfilterComponent,
      multi: true
    }
  ]
})
export class RclSearchfilterComponent extends ElementBase<string> {

  @ViewChild(NgModel) model: NgModel;
  @Input() public numberofColumndata: any[];
  @Input() public contractshowfilter: boolean;
  @Output() showhideFilter = new EventEmitter<boolean>();
  @Output() filterDataSelected = new EventEmitter<boolean>();

  filterData: any = {
    "filterDataArr": []
  };

  // Placeholders for the callbacks
  public _onTouchedCallback: (_: any) => void = noop;

  public _onChangeCallback: (_: any) => void = noop;

  public touchedFlag: boolean = false;

  constructor( @Optional() @Inject(NG_VALIDATORS) validators: Array<any>,
    @Optional() @Inject(NG_ASYNC_VALIDATORS) asyncValidators: Array<any>) {
    super(validators, asyncValidators);
  }

  registerOnTouched(fn: any) {
    this._onTouchedCallback = fn;
  }

  //Set touched on blur
  onTouched() {
    this._onTouchedCallback(null);
    this.touchedFlag = true;
  }
  
  hideFilter(e) {
    this.showhideFilter.emit(e);
  }

  
  private filterResults(e) {
    if (e.target.getAttribute("ng-reflect-name") == "Sort in") {
      this.filterData['sortIn'] = e.target.getAttribute("ng-reflect-value");
    }
    if (e.target.getAttribute("ng-reflect-name") == "Order by") {
      this.filterData['orderBy'] = e.target.getAttribute("ng-reflect-value");
    }
    //filter by
    if (e.target.getAttribute("ng-reflect-name") == "Filter by") {
      this.filterData['filterBy'] = e.target.getAttribute("ng-reflect-value");
    }
    
    
    this.filterDataSelected.emit(this.filterData);
  }
  
  

  //filter selected values and emit the data to parent component
  // filterResults(e) {
  //   var datapushfilter = {};
  //   if (e.target.getAttribute("ng-reflect-name") == "Sort in") {
  //     this.filterData['sortIn'] = e.target.getAttribute("ng-reflect-value");
  //   }
  //   if (e.target.getAttribute("ng-reflect-name") == "Order by") {
  //     this.filterData['orderBy'] = e.target.getAttribute("ng-reflect-value");
  //   }
  //   if (this.filterData['filterDataArr'] != undefined) {

  //     if (e.target.getAttribute("ng-reflect-name") != 'Sort in' && e.target.getAttribute("ng-reflect-name") != 'Order by') {
  //       if (this.filterData['filterDataArr'].length > 0) {
  //         for (var i = 0; i < this.filterData['filterDataArr'].length; i++) {

  //           if (this.filterData.filterDataArr[i].hasOwnProperty(e.target.getAttribute("ng-reflect-name"))) {
  //             this.filterData['filterDataArr'][i][e.target.getAttribute("ng-reflect-name")] = e.target.getAttribute("ng-reflect-value");
             
  //           }
  //         }
  //         datapushfilter[e.target.getAttribute("ng-reflect-name")] = e.target.getAttribute("ng-reflect-value");
  //         this.filterData['filterDataArr'].push(datapushfilter);
  //         return;

  //       } else {
  //         datapushfilter[e.target.getAttribute("ng-reflect-name")] = e.target.getAttribute("ng-reflect-value");
  //         this.filterData['filterDataArr'].push(datapushfilter);
  //        // return;
  //       }
  //     }
  //   } else {
  //     datapushfilter[e.target.getAttribute("ng-reflect-name")] = e.target.getAttribute("ng-reflect-value");

  //     if (e.target.getAttribute("ng-reflect-name") != 'Sort in' && e.target.getAttribute("ng-reflect-name") != 'Order by') {
  //       this.filterData['filterDataArr'].push(datapushfilter);
  //       //this.filterDataSelected.emit(this.filterData);
  //      // return;
  //     }
  //   }

  // }

  ngOnInit() {
  }

}
