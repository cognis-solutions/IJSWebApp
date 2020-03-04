import { Component, Optional, Inject, ViewChild, EventEmitter, Input, Output, OnInit   } from '@angular/core';
import {
  NgModel,
  NG_VALUE_ACCESSOR,
  NG_VALIDATORS,
  NG_ASYNC_VALIDATORS,
} from '@angular/forms';
import { noop } from 'rxjs/util/noop';

@Component({
  selector: 'app-jo-maintenance-search-filter',
  templateUrl: './jo-maintenance-search-filter.component.html',
  styleUrls: ['./jo-maintenance-search-filter.component.scss']
})
export class JoMaintenanceSearchFilterComponent implements OnInit {


  @ViewChild(NgModel) model: NgModel;
  @Input() public numberofColumndata: any[];
  @Input() public contractshowfilter: boolean;
  @Output() showhideFilter = new EventEmitter<boolean>();
  @Output() filterDataSelected = new EventEmitter<boolean>();


  filterData: any = {
  };

  constructor() { }

  ngOnInit() {
  }

  // Placeholders for the callbacks
  public _onTouchedCallback: (_: any) => void = noop;

  public _onChangeCallback: (_: any) => void = noop;

  public touchedFlag: boolean = false;



  private registerOnTouched(fn: any) {
    this._onTouchedCallback = fn;
  }

  //Set touched on blur
  private onTouched() {
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

    this.filterDataSelected.emit(this.filterData);
  }

}
