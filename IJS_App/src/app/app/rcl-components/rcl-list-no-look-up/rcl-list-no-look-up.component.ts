import { Component, Optional, Inject, ViewChild, Input, Output, EventEmitter } from '@angular/core';
import { NgModel, NG_VALUE_ACCESSOR, NG_VALIDATORS, NG_ASYNC_VALIDATORS } from '@angular/forms';
import { noop } from 'rxjs/util/noop';

@Component({
  selector: 'app-rcl-list-no-look-up',
  templateUrl: './rcl-list-no-look-up.component.html',
  styleUrls: ['./rcl-list-no-look-up.component.scss']
})
export class RclListNoLookUpComponent {

  @Input() public data: any[];
  @Input() public klass: string;
  @Input() public label: string;
  @Input() public compid: string;
  @Input() public placeholder: string;
  @Output() changeValueObj = new EventEmitter();
  @Input() public _value: String;
  @Input() public readOnly: boolean;

  private previouselement: any;
  public active: boolean = false;
  //private _value: string;
  private _type: string;
  private
  constructor() { }

  ngOnInit() {
  }


  changeInputValue(newValue) {
    if (newValue) {
      let lookUpObj = {
        type: this._type,
        value: newValue
      };
      this.changeValueObj.emit(lookUpObj);
    }
    this._type = undefined;
  }

  //Dropdown select value
  selectedDropDown: string;
  // Placeholders for the callbacks
  public _onTouchedCallback: (_: any) => void = noop;

  public _onChangeCallback: (_: any) => void = noop;

  public touchedFlag: boolean = false;

  // From ControlValueAccessor interface
  registerOnTouched(fn: any) {
    this._onTouchedCallback = fn;
  }

  //Set touched on blur
  onTouched() {
    this._onTouchedCallback(null);
    this.touchedFlag = true;
  }


  //lookup inupts show hide
  onLookupShowHide(e, selectedvalue) {
    this._value = ""
    this._type = selectedvalue;
    if (this.previouselement != undefined && this.previouselement != e.target.previousElementSibling) {
      this.previouselement.previousElementSibling.setAttribute("hidden", "hidden");
      this.previouselement.removeAttribute("hidden");
    }
    this.previouselement = e.target;
    e.target.setAttribute("hidden", "hidden");
    e.target.previousElementSibling.removeAttribute("hidden");
  }




  //lookup hide
  onClickOutside(event) {
    if (event && event['value'] === true) {
      this.active = false;
    }
  }
}
