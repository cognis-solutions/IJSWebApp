import { Component, OnInit } from '@angular/core';
import { UserService } from "./user.service";
import { UserTypeService } from "./user-type.service";
import { ServerErrorcodeService } from "../common-services/server-errorcode.service";
@Component({
  selector: 'app-user',
  templateUrl: './user.component.html',
  styleUrls: ['./user.component.scss']
})
export class UserComponent implements OnInit {
  errorCodetext: string;
  errorCodeShow: boolean = false;
  userInfo:any;
  constructor(public _userService: UserService, private _userTypeService: UserTypeService, public _serverErrorCode: ServerErrorcodeService) { }

  ngOnInit() {
    this.getUserData();
  }

  getUserData(){
    var userData = this._userService.startupData();
   
        if (userData.hasOwnProperty("errorCode")) {
          this.errorCodetext = this._serverErrorCode.checkError(userData["errorCode"]);
          this.errorCodeShow = true;
        }
        else {          
          this.userInfo=userData;           
        } 
  }

}
