/**
 * Created by Roy on 5/28/2017.
 * The program unit for Validation handling functions
 * Each custom componenet should import the required 
 * functions from this program unit
 */
import {
  AbstractControl,
  AsyncValidatorFn,
  Validator,
  Validators,
  ValidatorFn,
} from '@angular/forms';

import { Observable } from 'rxjs/Observable';
import 'rxjs/add/observable/of';

export type ValidationResult = { [validator: string]: string | boolean | any };

export type AsyncValidatorArray = Array<Validator | AsyncValidatorFn>;

export type ValidatorArray = Array<Validator | ValidatorFn>;

const normalizeValidator =
  (validator: Validator | ValidatorFn): ValidatorFn | AsyncValidatorFn => {
    const func = (validator as Validator).validate.bind(validator);
    if (typeof func === 'function') {
      return (c: AbstractControl) => func(c);
    } else {
      return <ValidatorFn | AsyncValidatorFn>validator;
    }
  };

export const composeValidators =
  (validators: ValidatorArray): AsyncValidatorFn | ValidatorFn => {
    if (validators == null || validators.length === 0) {
      return null;
    }
    return Validators.compose(validators.map(normalizeValidator));
  };

export const validate =
  (validators: ValidatorArray, asyncValidators: AsyncValidatorArray) => {
    return (control: AbstractControl) => {
      const synchronousValid = () => composeValidators(validators)(control);

      if (asyncValidators) {
        const asyncValidator = composeValidators(asyncValidators);

        return asyncValidator(control).map(v => {
          const secondary = synchronousValid();
          if (secondary || v) { // compose async and sync validator results
            return Object.assign({}, secondary, v);
          }
        });
      }

      if (validators) {
        return Observable.of(synchronousValid());
      }

      return Observable.of(null);
    };
  };

export const message = (validator: ValidationResult, key: string): string => {
  switch (key) {
    case 'required':
      return 'Please enter a value';
    case 'pattern':
      return 'please input correct value';
    case 'minlength':
      return 'Value must be ' + validator.minlength.requiredLength + ' characters';
    case 'maxlength':
      return 'Value must be a maximum of ' + validator.maxlength.requiredLength + ' characters';
  }

  switch (typeof validator[key]) {
    case 'string':
      return <string>validator[key];
    default:
      return `Validation failed: ${key}`;
  }
};