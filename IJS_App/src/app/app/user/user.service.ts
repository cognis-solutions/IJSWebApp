import { Injectable } from '@angular/core';
import { Http, Response, Headers, URLSearchParams, RequestOptions } from '@angular/http';
import { Observable } from 'rxjs/Observable';
import 'rxjs/add/operator/map';
import 'rxjs/add/operator/toPromise';
import 'rxjs/add/operator/catch';
import { RclappUrlService } from "../common-services/rclapp-url.service"
import { UserTypeService } from "./user-type.service";


export class UserInfo {
    userId: String;
    personCd: String;
    fscCode: String;
    fscName: String;
    deptCode: String;
    titleCode: String;
    isGroupAuth: String;
    emailId1: String;
    fscCurr: String;
    userAuthType: String;
    
}
@Injectable()
export class UserService {

    private _startupData: any;
    constructor(private _http: Http, private _rclappUrlService: RclappUrlService, private _userTypeService: UserTypeService) { }


    getData(): Promise<any[]> {
        this._startupData = null;
        
        return this._http.get(this._rclappUrlService.url + '/IJSWebApp/loadUser.do')
            .map(this.extractData)
            .toPromise()
            .then((data: any) => this._startupData = data)
            .catch((err: any) => Promise.resolve());
    }

   startupData(): any {  
        this._userTypeService.setValue(this._startupData.userType)     
        return this._startupData;
    }

    private extractData(res: Response) {
        let body = res.json();        
        return body;
    }
    private handleErrorObservable(error: Response | any) {        
        return Observable.throw(error);
    }
}
