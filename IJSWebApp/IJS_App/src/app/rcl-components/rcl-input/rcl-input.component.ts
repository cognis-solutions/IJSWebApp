import { Component, Optional, Inject, ViewChild, Input } from '@angular/core';
import {
  NgModel,
  NG_VALUE_ACCESSOR,
  NG_VALIDATORS,
  NG_ASYNC_VALIDATORS,
} from '@angular/forms';
import { noop } from 'rxjs/util/noop';
import { ElementBase } from '../rcl-base/element-base';

let identifier = 0;

@Component({
  selector: 'app-rcl-input',
  templateUrl: './rcl-input.component.html',
  styleUrls: ['./rcl-input.component.scss'],
  providers: [
    {
      provide: NG_VALUE_ACCESSOR,
      useExisting: RclInputComponent,
      multi: true
    }
  ]
})

export class RclInputComponent extends ElementBase<string> {
  @ViewChild(NgModel) model: NgModel;
  @Input() public label: string;
  @Input() public placeholder: string = "";
  @Input() public klass: string;
  @Input() public compid: String;
  @Input() public disabled: boolean;
  @Input() public maxlength: number;
  @Input() public type:string = "search"
  @Input() public min:number;
  @Input() public max:number;
  @Input() public readOnly: boolean;
  @Input() public pattern;

  // Placeholders for the callbacks
  public _onTouchedCallback: (_: any) => void = noop;

  public _onChangeCallback: (_: any) => void = noop;

  public identifier = `rcl-input-${identifier++}`;
  
  public touchedFlag: boolean = false;

  constructor( @Optional() @Inject(NG_VALIDATORS) validators: Array<any>,
    @Optional() @Inject(NG_ASYNC_VALIDATORS) asyncValidators: Array<any>) {
    super(validators, asyncValidators);
  }

  // From ControlValueAccessor interface
  registerOnTouched(fn: any) {
    this._onTouchedCallback = fn;
  }

  //Set touched on blur
  onTouched() {
    this._onTouchedCallback(null);
    this.touchedFlag=true;
  }
}
