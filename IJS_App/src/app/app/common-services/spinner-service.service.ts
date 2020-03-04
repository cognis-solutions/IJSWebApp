import { Injectable } from '@angular/core';
import { Observable } from 'rxjs/Observable';
import { BehaviorSubject } from 'rxjs/BehaviorSubject'

@Injectable()
export class SpinnerServiceService {
  public spinnerStatus: BehaviorSubject<boolean> = new BehaviorSubject<boolean>(false);
  public isSpinning: Observable<boolean> = this.spinnerStatus.asObservable();

  constructor() { }

  public getSpinnerStatus(): Observable<boolean>{
    return this.isSpinning;
  }

  public showSpinner(){
    this.spinnerStatus.next(true);
  }

  public hideSpinner(){
    this.spinnerStatus.next(false);
  }

}
