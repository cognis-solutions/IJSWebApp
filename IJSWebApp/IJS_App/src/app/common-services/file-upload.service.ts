import { Injectable } from '@angular/core';
import { Http, Response, Headers, URLSearchParams, RequestOptions } from '@angular/http';
import { Observable } from 'rxjs/Observable';;
import { RclappUrlService } from "./rclapp-url.service"


@Injectable()
export class FileUploadService {
  progress$;
  progress;
  progressObserver;
  constructor(private _rclappUrlService: RclappUrlService, private _http: Http) {
    this.progress$ = Observable.create(observer => {
      this.progressObserver = observer
    });    
  }

  public makeFileRequest(formData): Observable<any> {
    return Observable.create(observer => {
      let xhr: XMLHttpRequest = new XMLHttpRequest();

      xhr.onreadystatechange = () => {
        if (xhr.readyState === 4) {
          if (xhr.status === 200) {
            observer.next(JSON.parse(xhr.response));
            observer.complete();
          } else {
            observer.error(xhr.response);
          }
        }
      };

      xhr.upload.onprogress = (event) => {
        this.progress = Math.round(event.loaded / event.total * 100);
        this.progressObserver.next(this.progress);
      };

      xhr.open('POST', this._rclappUrlService.url + '/IJSWebApp/uploadExcel.do', true);
      xhr.send(formData);
    });
  }
  
  saveXlsFile(obj) {    
    let seachdata = JSON.stringify(obj);
    let searchParams = new URLSearchParams();
    searchParams.set("data", seachdata);   
    
    return this._http.get(this._rclappUrlService.url + "/IJSWebApp/contractSearch.do" , { search: searchParams })
      .map(this.extractData)
      .catch(this.handleErrorObservable);
  }
  
 private extractData(res: Response) {
    let body = res.json();
    return body;
  }
  private handleErrorObservable(error: Response | any) {
    console.log(error.message || error.status);
    return Observable.throw(error.status);
  }
  
  saveContainerXlsFile(obj) {    
    let seachdata = JSON.stringify(obj);
    let searchParams = new URLSearchParams();
    searchParams.set("data", seachdata);   
    
    return this._http.get(this._rclappUrlService.url + "/IJSWebApp/processJOSearchBookingBL.do" , { search: searchParams })
      .map(this.extractData)
      .catch(this.handleErrorObservable);
  }
  
  
}