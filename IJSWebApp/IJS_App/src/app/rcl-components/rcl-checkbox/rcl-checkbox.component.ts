import { Component, Optional, Inject, ViewChild, Input, Output, EventEmitter } from '@angular/core';
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
  selector: 'app-rcl-checkbox',
  templateUrl: './rcl-checkbox.component.html',
  styleUrls: ['./rcl-checkbox.component.scss'],
  providers: [
    {
      provide: NG_VALUE_ACCESSOR,
      useExisting: RclCheckboxComponent,
      multi: true
    }
  ]
})
export class RclCheckboxComponent extends ElementBase<string> {
  @ViewChild(NgModel) model: NgModel;
  @Input() public label: string;
  @Input() public klass: string;
  @Input() public compid: String;
  @Input() public disabled: String;
  @Input() public checkValue: String;
  @Input() public value;  
  @Input() public name;  
  @Input() public checked;           
  


  
  
  @Output() public rclCheckChanged = new EventEmitter();

  public _onTouchedCallback: (_: any) => void = noop;
  public _onChangeCallback: (_: any) => void = noop;

  public identifier = `rcl-checkbox-${identifier++}`;

  constructor( @Optional() @Inject(NG_VALIDATORS) validators: Array<any>,
    @Optional() @Inject(NG_ASYNC_VALIDATORS) asyncValidators: Array<any>) {
    super(validators, asyncValidators);
  }

  // From ControlValueAccessor interface
  registerOnTouched(fn: any) {
    this._onTouchedCallback = fn;
  }

  onChangeState(e){
    this.rclCheckChanged.emit(e);
  }

  //Set touched on blur
  onTouched(){
    this._onTouchedCallback(null);
  }

  ngOnInit() {
  }

}
