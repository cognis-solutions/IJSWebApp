import { NgModel } from '@angular/forms';
import { Observable } from 'rxjs/Observable';
import 'rxjs/add/operator/map';

import { ValueAccessorBase } from './value-accessor-base';
import {
  AsyncValidatorArray,
  ValidatorArray,
  ValidationResult,
  message,
  validate,
} from './element-base-validate';


export abstract class ElementBase<T> extends ValueAccessorBase<T> {
  public abstract model: NgModel;
  // we will ultimately get these arguments from @Inject on the derived class
  constructor(private validators: ValidatorArray,
              private asyncValidators: AsyncValidatorArray,
  ) {
    super();
  }

  public validate(): Observable<ValidationResult> {
    return validate
    (this.validators, this.asyncValidators)
    (this.model.control);
  }

  public get invalid(): Observable<boolean> {
    return this.validate().map(v => Object.keys(v || {}).length > 0);
  }

  public get failures(): Observable<Array<string>> {
    return this.validate().map(v => Object.keys(v).map(k => message(v, k)));
  }
}
