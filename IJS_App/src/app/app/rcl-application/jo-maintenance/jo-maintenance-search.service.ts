import { Injectable } from '@angular/core';
import { Http, Response, Headers, URLSearchParams, RequestOptions,ResponseContentType } from '@angular/http';
import { Observable } from 'rxjs/Observable';
import 'rxjs/add/operator/map';
import 'rxjs/add/operator/catch';

import { RclappUrlService } from "../../common-services/rclapp-url.service"

@Injectable()
export class JoMaintenanceSearchService {

    constructor(private _http: Http, private _rclappUrlService: RclappUrlService) { }



    getMaintenanceDataSummary(summaryObj:any):Observable<any>
    {

        let searchObj = summaryObj;
        let seachdata = JSON.stringify(searchObj);
        let searchParams = new URLSearchParams();
        searchParams.set("data", seachdata);
        return this._http.get(this._rclappUrlService.url + '/IJSWebApp/maintainJOSumarry.do', { search: searchParams })
        //return this._http.get('http://localhost:4200/assets/jsons/maintinaceResult.json', { search: searchParams })
            .map(this.extractData)
            .catch(this.handleErrorObservable);

    }

    getMaintenanceData(searchDataObj: any): Observable<any> {

        let searchObj = searchDataObj;
        let seachdata = JSON.stringify(searchObj);
        let searchParams = new URLSearchParams();
        searchParams.set("data", seachdata);

        return this._http.get(this._rclappUrlService.url + '/IJSWebApp/maintainJOSearch.do', { search: searchParams })
        //return this._http.get('http://localhost:4200/assets/jsons/maintinaceResult.json', { search: searchParams })
            .map(this.extractData)
            .catch(this.handleErrorObservable);
    }

    getViewJoUrl(): Observable<string> {
        return this._http.get(this._rclappUrlService.url + '/IJSWebApp/viewJoUrl.do')
        //return this._http.get('http://localhost:4200/assets/jsons/maintinaceResult.json', { search: searchParams })
            .map(this.extractData)
            .catch(this.handleErrorObservable);
    }
    downloadJobOrders(downloadSearchData:string): Observable<Blob> {
        let searchParams = new URLSearchParams();
        searchParams.set("data", JSON.stringify(downloadSearchData));
        let searchResultDownloadUrl = this._rclappUrlService.url + "/IJSWebApp/maintainJOSearch.do?" ;
        return this._http.get(searchResultDownloadUrl, {search: searchParams, responseType: ResponseContentType.Blob })
        .map(res => res.blob())
        .catch(this.handleErrorObservable);
      }  

    getDownloadDataLimit(searchDataObj: any): Observable<any> {

        let searchObj = searchDataObj;
        let seachdata = JSON.stringify(searchObj);
        let searchParams = new URLSearchParams();
        searchParams.set("data", seachdata);

        return this._http.get(this._rclappUrlService.url + '/IJSWebApp/maintainJOSearch.do', { search: searchParams })
        //return this._http.get('http://localhost:4200/assets/jsons/maintinaceResult.json', { search: searchParams })
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