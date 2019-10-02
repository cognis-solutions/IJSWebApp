import { Injectable } from '@angular/core';
import { Http, Response, Headers, URLSearchParams, RequestOptions } from '@angular/http';
import { Observable } from 'rxjs/Observable';
import 'rxjs/add/operator/map';
import 'rxjs/add/operator/catch';
import { RclappUrlService } from "../../common-services/rclapp-url.service"

export class ProcessJoSearch {
  // vendorId: String;
  // vendorName: String;
  // location: String;
  // contractId: String;
  // routingId: String;
  // startDate: String;
  // expireDate: String;
  // vechileType: String;
  transMode : String;
  bookingType : String;
  processJoType : String;
  //Madhuri
  vendor : String;
  bookingBLNo : String;
  service : String;
  vessel : String;
  voyage : String;
  session : String;
  portLookup : String;
}


@Injectable()
export class ProcessjoSearchService {


  public processJoSearchData:any;
  constructor(private _http: Http, private _rclappUrlService: RclappUrlService) { }

  //#NIIT CR3 >>>> BEGIN
  getProcessjoSearchData(searchDataObj): Observable<any> {
    let seachdata = JSON.stringify(searchDataObj);
    // let searchParams = new URLSearchParams();
    // searchParams.set("data", seachdata);
  return this._http.post(this._rclappUrlService.url + '/IJSWebApp/processJOSearchBookingBL.do', seachdata) 
    // return this._http.get("http://localhost:4200/assets/jsons/processJosearchresult.json", { search: searchParams })
      .map(this.extractData)
      .catch(this.handleErrorObservable);
  }

  getProcessTableResultSearchData(searchDataObj): Observable<any> {
    let seachdata = JSON.stringify(searchDataObj);
    // let searchParams = new URLSearchParams();
    // searchParams.set("data", seachdata);
  return this._http.post(this._rclappUrlService.url + '/IJSWebApp/resultTableSearchBL.do', seachdata) 
    // return this._http.get("http://localhost:4200/assets/jsons/processJosearchresult.json", { search: searchParams })
      .map(this.extractData)
      .catch(this.handleErrorObservable);
  }
  //#NIIT CR3  <<<< END

  //#NIIT CR3 >>>> BEGIN
  getJoSummaryListLimit(searchDataObj): Observable<any> {
    let seachdata = JSON.stringify(searchDataObj);    
    return this._http.post(this._rclappUrlService.url + '/IJSWebApp/processJOSearchBookingBL.do', seachdata)
     .map(this.extractData)
     .catch(this.handleErrorObservable);
  }
  //#NIIT CR3  <<<< END

  //#NIIT CR6 >>>> BEGIN
  //method to delete the jo summary lumpsum
  deleteProcessJoLumpSum(searchDataObj: any): Observable<any> {
    let seachdata = JSON.stringify(searchDataObj);    
    return this._http.post(this._rclappUrlService.url + '/IJSWebApp/processJOSearchBookingBL.do', seachdata)
     .map(this.extractData)
     .catch(this.handleErrorObservable);
    }  
  //#NIIT CR6 >>>>END

  private extractData(res: Response) {
    let body = res.json();
    return body;
  }
  private handleErrorObservable(error: Response | any) {
    return Observable.throw(error);
  }
}





