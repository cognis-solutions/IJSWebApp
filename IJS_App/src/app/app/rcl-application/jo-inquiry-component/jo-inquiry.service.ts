import { Injectable } from '@angular/core';
import { Http, Response, Headers, URLSearchParams, RequestOptions } from '@angular/http';
import { Observable } from 'rxjs/Observable';
import 'rxjs/add/operator/map';
import 'rxjs/add/operator/catch';
import  { RclappUrlService } from "../../common-services/rclapp-url.service"


@Injectable()
export class JoInquiryService {
  
  constructor(private _http: Http, private _rclappUrlService: RclappUrlService) { }

    getJoInquiryData(searchDataObj: any): Observable<any> {
        let seachdata = JSON.stringify(searchDataObj);
        let searchParams = new URLSearchParams();
        searchParams.set("data", seachdata);
        
        return this._http.get('http://localhost:4200/assets/jsons/maintinaceResult.json', { search: searchParams })
            .map(this.extractData)
            .catch(this.handleErrorObservable);
    }    
    

    private extractData(res: Response) {
        let body = res.json();
        return body;
    }
    private handleErrorObservable(error: Response | any) {
        return Observable.throw(error);
    }

}


export class MaintainSerch {

}