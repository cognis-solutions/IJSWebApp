import { Injectable } from '@angular/core';
import { Http, Response, Headers, URLSearchParams, RequestOptions, ResponseContentType } from '@angular/http';
import { Observable } from 'rxjs/Observable';
import 'rxjs/add/operator/map';
import { RclappUrlService } from "./rclapp-url.service"

@Injectable()
export class DownloadFileService {

  constructor(private _rclappUrlService: RclappUrlService, private _http: Http) { }

  downloadTemplateExcel(action,subUrl): Observable<any> {

    console.log("in download")
    
    
         let dowloadtempData = {
            "action": action
          };
        let seachdata = JSON.stringify(dowloadtempData);
        let searchParams = new URLSearchParams();
        searchParams.set("data", seachdata);
    
    return this._http.get
    (
     
      this._rclappUrlService.url + "/IJSWebApp/"+subUrl+".do", 
    {search: searchParams, responseType: ResponseContentType.Blob })
      .map(res => res.blob())
      .catch(this.handleError)
  }


  private handleError(error: Response | any) {
    console.log(error.message || error.status);
    return Observable.throw(error.status);
  }
  
}
