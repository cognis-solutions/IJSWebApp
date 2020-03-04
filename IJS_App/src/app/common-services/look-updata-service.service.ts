import { Injectable } from '@angular/core';
import { Http, Response, Headers, URLSearchParams, RequestOptions } from '@angular/http';
import { Observable } from 'rxjs/Observable';
import 'rxjs/add/operator/map';
import 'rxjs/add/operator/catch';
import { RclappUrlService } from "./rclapp-url.service"

export class ContarctJobSerch {
  vendorId: String;
  vendorName: String;
  location: String;
  contractId: String;
  routingId: String;
  startDate: String;
  expireDate: String;
  vechileType: String;
}




@Injectable()
export class LookUpdataServiceService {

  constructor(private _http: Http, private _rclappUrlService: RclappUrlService) { }

  getDataLookupServiceJORouting(actionparam, type, eve, inputValueLoc, wildCard,inputValueTerminal,inputValueLocType,inputSaleDateOrJobOrdDate,vendorCode,transportType,joType,checkComponent): Observable<any> {
    // actionparam,bookingType,inputValue
    let lookUpsearchData;
      
        if(checkComponent=="adHocType" || checkComponent=="jomaintenance"){
            lookUpsearchData = {
            ijsLookupParam: {
              "findIn": type,
              "findForLoc": inputValueLoc,
              "findForTerminal": inputValueTerminal,
              "findForLocType": inputValueLocType,
              "findForSaleDateOrJobOrdDate": inputSaleDateOrJobOrdDate,
              "findForVendorCode": vendorCode,
              "transMode": transportType,
              "joType": joType,
              "sameVendorInSearch": "Y"
            },
            "action": actionparam
          };
        }

        if(checkComponent=="processJo"){
            lookUpsearchData = {
            ijsLookupParam: {
              "findIn": type,
              "findForLoc": inputValueLoc,
              "findForTerminal": inputValueTerminal,
              "findForLocType": inputValueLocType,
              "findForSaleDateOrJobOrdDate": inputSaleDateOrJobOrdDate,
              "findForVendorCode": vendorCode,
              "transMode": transportType,
              "joType": joType,
              "sameVendorInSearch": "N"
            },
            "action": actionparam
          };
        }
       

        if (wildCard == true) {
          lookUpsearchData.ijsLookupParam["wildCard"] = "yes"
        } else {
          lookUpsearchData.ijsLookupParam["wildCard"] = "no"
        }
    
        let seachdata = JSON.stringify(lookUpsearchData);
        let searchParams = new URLSearchParams();
        searchParams.set("data", seachdata);
    
    
    
        return this._http.get(this._rclappUrlService.url + "/IJSWebApp/lookupSearchJOAll.do", { search: searchParams })
          .map(this.extractData)
          .catch(this.handleErrorObservable);
      }

  getDataLookupServiceJOAll(actionparam, type, eve, inputValue, wildCard,contractId,componentType): Observable<any> {
    // actionparam,bookingType,inputValue
    let lookUpsearchData;
      
       if(actionparam=="getJOEquioment"){ 
        lookUpsearchData = {
          ijsLookupParam: {
            "findIn": type,
            "findForList": inputValue,
            "contractId": contractId
          },
          "action": actionparam
        };
       }
       else if(actionparam=="getJOContainer" || actionparam=="getDgImdg" || actionparam=="getBkgBlPopUp" ){ 
        lookUpsearchData = {
          ijsLookupParam: {
            "findIn": type,
            "findList": inputValue,
            "componentType":componentType
          },
          "action": actionparam
        };
       }
       else if(actionparam=="delBkgBl" ){ 
        lookUpsearchData = {
          ijsLookupParam: {
            "findIn": type,
            "deleteFor": inputValue
          },
          "action": actionparam
        };
       }
       else
       { 
        lookUpsearchData = {
          ijsLookupParam: {
            "findIn": type,
            "findFor": inputValue
          },
          "action": actionparam
        };
       }

        if (wildCard == true) {
          lookUpsearchData.ijsLookupParam["wildCard"] = "yes"
        } else {
          lookUpsearchData.ijsLookupParam["wildCard"] = "no"
        }
    
        let seachdata = JSON.stringify(lookUpsearchData);
        let searchParams = new URLSearchParams();
        searchParams.set("data", seachdata);
    
    
    
        return this._http.get(this._rclappUrlService.url + "/IJSWebApp/lookupSearchJOAll.do", { search: searchParams })
          .map(this.extractData)
          .catch(this.handleErrorObservable);
      }
      // getDataLookupServiceJOAllFilter(actionparam, type, eve, inputValue, wildCard): Observable<ContarctJobSerch[]> {
        
      //       let lookUpsearchData = {
      //         ijsLookupParam: {
      //           "findIn": type,
      //           "findForList": inputValue
      //         },
      //         "action": actionparam
      //       };
        
      //       if (wildCard == true) {
      //         lookUpsearchData.ijsLookupParam["wildCard"] = "yes"
      //       } else {
      //         lookUpsearchData.ijsLookupParam["wildCard"] = "no"
      //       }
        
      //       let seachdata = JSON.stringify(lookUpsearchData);
      //       let searchParams = new URLSearchParams();
      //       searchParams.set("data", seachdata);
        
        
        
      //       return this._http.get(this._rclappUrlService.url + "/IJSWebApp/lookupSearchJOAll.do", { search: searchParams })
      //         .map(this.extractData)
      //         .catch(this.handleErrorObservable);
      //     }    

   

  getDataLookupService(actionparam, type, eve, inputValue, wildCard): Observable<any> {

    let lookUpsearchData = {
      ijsLookupParam: {
        "findIn": type,
        "findFor": inputValue
      },
      "action": actionparam
    };

    if (wildCard == true) {
      lookUpsearchData.ijsLookupParam["wildCard"] = "yes"
    } else {
      lookUpsearchData.ijsLookupParam["wildCard"] = "no"
    }

    let seachdata = JSON.stringify(lookUpsearchData);
    let searchParams = new URLSearchParams();
    searchParams.set("data", seachdata);



    return this._http.get(this._rclappUrlService.url + "/IJSWebApp/lookupSearch.do", { search: searchParams })
      .map(this.extractData)
      .catch(this.handleErrorObservable);
  }


  getPagingDataLookupService(actionparam, type, eve, inputValue, wildCard, pageNo, requestChanged, lookupSortIn, looUpOrderBy,recordsPerPage) {

    let lookUpsearchData = {
      ijsLookupParam: {
        "findIn": type,
        "findFor": inputValue,
        "pageNo": pageNo,
        "requestChanged": requestChanged,
        "sortBy": lookupSortIn,
        "orderBy": looUpOrderBy,
        "noOfRecPerPage": recordsPerPage
      },
      "action": actionparam
    };



    if (wildCard == true) {
      lookUpsearchData.ijsLookupParam["wildCard"] = "yes"
    } else {
      lookUpsearchData.ijsLookupParam["wildCard"] = "no"
    }
    
    let seachdata = JSON.stringify(lookUpsearchData);
    let searchParams = new URLSearchParams();
    searchParams.set("data", seachdata);
    
    return this._http.get(this._rclappUrlService.url + "/IJSWebApp/lookupSearch.do", { search: searchParams })
      .map(this.extractData)
      .catch(this.handleErrorObservable);

  }

  //TODO:Remove later as this is direct JSON CAlling
  getPagingDataLookupServiceSVV(actionparam, type, eve, inputValue, wildCard) {
    
        let lookUpsearchData = {
          ijsLookupParam: {
            "findIn": type,
            "findFor": inputValue
          },
          "action": actionparam
        };
    
    
    
        if (wildCard == true) {
          lookUpsearchData.ijsLookupParam["wildCard"] = "yes"
        } else {
          lookUpsearchData.ijsLookupParam["wildCard"] = "no"
        }
        
        let seachdata = JSON.stringify(lookUpsearchData);
        let searchParams = new URLSearchParams();
        searchParams.set("data", seachdata);
        
        return this._http.get("/IJSWebApp/assets/ServiceVesselVoyage.json", { search: searchParams })
          .map(this.extractData)
          .catch(this.handleErrorObservable);
    
      }
      
      // #NIIT CR3 BEGIN
      getUnavailableEquipmentsType(actionparam): Observable<any>{

        let unavailableEquipmentsSearchData = {          
          "action": actionparam
        };

        let seachdata = JSON.stringify(unavailableEquipmentsSearchData);
        let searchParams = new URLSearchParams();
        searchParams.set("data", seachdata);
        
        return this._http.get(this._rclappUrlService.url + "/IJSWebApp/processJOSearchBookingBL.do", { search: searchParams })
          .map(this.extractData)
          .catch(this.handleErrorObservable);

      }
      // #NIIT CR3 END

      //#NIIT CR6 >>>>BEGIN
      deleteBookingBlLumpSum(actionparam): Observable<any>{

        let lookUpsearchData = {
          ijsLookupParam: {
            "joLumpsumIds": actionparam.joLumpsumIds,
            
          },
          "action": actionparam.action
        };

        let seachdata = JSON.stringify(lookUpsearchData);
        let searchParams = new URLSearchParams();
        searchParams.set("data", seachdata);
        
        return this._http.get(this._rclappUrlService.url + "/IJSWebApp/lookupSearchJOAll.do", { search: searchParams })
          .map(this.extractData)
          .catch(this.handleErrorObservable);
      }
      //#NIIT CR6 >>>>END

  private extractData(res: Response) {
    let body = res.json();
    //console.log(body);
    return body;
  }
  private handleErrorObservable(error: Response | any) {
    console.log(error.message || error.status);
    return Observable.throw(error.status);
  }

}