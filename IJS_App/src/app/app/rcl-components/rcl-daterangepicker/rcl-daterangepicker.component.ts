import { Component, OnInit, AfterViewInit, Optional, Inject, ViewChild, Input,Output,EventEmitter, ElementRef, forwardRef } from '@angular/core';
import {
  NgModel,
  NG_VALUE_ACCESSOR,
  NG_VALIDATORS,
  NG_ASYNC_VALIDATORS,
} from '@angular/forms';
import {noop} from 'rxjs/util/noop';
import {ElementBase} from '../rcl-base/element-base';

declare var jQuery: any;
declare var moment: any;
let identifier = 0;

@Component({
  selector: 'app-rcl-daterangepicker',
  templateUrl: './rcl-daterangepicker.component.html',
  styleUrls: ['./rcl-daterangepicker.component.scss'],
  providers: [
    {
      provide: NG_VALUE_ACCESSOR,
      useExisting: RclDaterangepickerComponent,
      multi: true
    }
  ]
})
export class RclDaterangepickerComponent extends ElementBase<string> implements OnInit, AfterViewInit {
  @ViewChild(NgModel) model: NgModel;
  @ViewChild('input') input: ElementRef;
  @Input() klass: string;
  @Input() label: string;
  @Input() compid: string;
  @Input() placeholder: string;
  @Input() single: boolean;
  @Input() time: boolean;
  @Input() time24H: boolean;
  @Input() disabled: boolean;
  @Input() readOnly: boolean;
  @Output() effectiveDate = new EventEmitter();

  public startDate: string;
  public endDate: string;

  public input_element: any;

  public identifier = `rcl-daterangepicker-${identifier++}`;
  public touchedFlag: boolean = false;

  // Placeholders for the callbacks
  public _onTouchedCallback: (_: any) => void = noop;

  public _onChangeCallback: (_: any) => void = noop;

  constructor( @Optional() @Inject(NG_VALIDATORS) validators: Array<any>,
    @Optional() @Inject(NG_ASYNC_VALIDATORS) asyncValidators: Array<any>) {
    super(validators, asyncValidators);
  }

  ngAfterViewInit(): void {
    this.input_element = jQuery(this.input.nativeElement);

    this.input_element.daterangepicker({
      autoUpdateInput: false,
      startDate: this.startDate,
      endDate: this.endDate,
      singleDatePicker: this.single,
      timePicker: this.time,
      timePicker24Hour: this.time24H,
      showDropdowns: true,
      drops: 'down',
      locale: {
        cancelLabel: 'Clear',
        format: 'DD/MM/YYYY'
      }
    });

    this.input_element.on('apply.daterangepicker', (ev, picker) => {

      if (!this.disabled) {
        let formatStr: String = 'DD/MM/YYYY';
        if (this.time) {
          if (this.time24H) {
            formatStr = 'DD/MM/YYYY HH:mm:ss';
          } else {
            formatStr = 'DD/MM/YYYY hh:mm:ss A';
          }
        }
        if (this.single) {
          this.input_element.val(picker.startDate.format(formatStr));
          this.value = ev.target.value;        
          this.effectiveDate.emit(this.value);
        } else {
          this.input_element.val(picker.startDate.format(formatStr) + '-' + picker.endDate.format(formatStr));
          this.value = ev.target.value;
        }
      }
    });

    this.input_element.on('show.daterangepicker', (ev, picker) => {
      if (!this.disabled) {
        this._onTouchedCallback(null);
        this.touchedFlag = true;
      }
    });

    this.input_element.on('cancel.daterangepicker', (ev, picker) => {
      this.value = null;
      this.input_element.val(null);
    });
  }

  // From ControlValueAccessor interface
  registerOnTouched(fn: any) {
    this._onTouchedCallback = fn;
  }

  ngOnInit() {
  }

 
  formatAMPM(date) {
    let hours = date.getHours();
    let minutes = date.getMinutes();
    let ampm = hours >= 12 ? 'PM' : 'AM';
    hours = hours % 12;
    hours = hours ? hours : 12; // the hour '0' should be '12'
    minutes = minutes < 10 ? '0' + minutes : minutes;
    return hours + ':' + minutes + ' ' + ampm;
  }

  showFormatError: boolean = false;
  changeDate(e) {
    this.showFormatError = false;
    var patt = new RegExp(e.target.pattern);
    if (e.target.value && this.single == true) {
      if (!patt.test(e.target.value)) {
        this.showFormatError = true;
      } else {
        this.showFormatError = false;
      }
    } else {
      this.showFormatError = false;
    }

  }  
}
