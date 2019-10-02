import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { SpinnerServiceService } from "app/common-services/spinner-service.service";

@Injectable()
export class SessionTimeOutService {

  constructor(public spinner: SpinnerServiceService,private router: Router) { }
  //session-time-out
  checkSessionTimeout(timeOutError){
    //check if session is expired or not
    if(timeOutError == "userSessionExpired"){          
          this.spinner.hideSpinner();
          this.router.navigate(['/session-time-out']);
        }
  }

}
