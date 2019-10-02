import { Component, Optional, Inject, ViewChild, Input, Output, EventEmitter } from '@angular/core';
import {
  NgModel,
  NG_VALUE_ACCESSOR,
  NG_VALIDATORS,
  NG_ASYNC_VALIDATORS,
} from '@angular/forms';
import { noop } from 'rxjs/util/noop';
import { ElementBase } from '../../rcl-components/rcl-base/element-base';

@Component({
  selector: 'app-text-group-radio',
  templateUrl: './text-group-radio.component.html',
  styleUrls: ['./text-group-radio.component.scss'],
  providers: [
    {
      provide: NG_VALUE_ACCESSOR,
      useExisting: TextGroupRadioComponent,
      multi: true
    }
  ]
})
export class TextGroupRadioComponent extends ElementBase<string> {

  @ViewChild(NgModel) model: NgModel;
  @Input() public label: string;
  @Input() public labelclass: string;
  @Input() public klass: string;
  @Input() public compid: string;
  @Input() public data: { id: string, text: string }[];
  @Output() onChangeValue = new EventEmitter();

  public _onTouchedCallback: (_: any) => void = noop;
  public _onChangeCallback: (_: any) => void = noop;

  constructor( @Optional() @Inject(NG_VALIDATORS) validators: Array<any>,
    @Optional() @Inject(NG_ASYNC_VALIDATORS) asyncValidators: Array<any>) {
    super(validators, asyncValidators);
  }

  registerOnChange(fn: (value: string) => void): void {
    this._onChangeCallback = fn;
  }
  registerOnTouched(fn: () => void): void {
    this._onTouchedCallback(null);
  }

  // Set touched on blur
  onChange(val) {
    this.onChangeValue.emit(val);
    this._onChangeCallback(val);
  }

}
