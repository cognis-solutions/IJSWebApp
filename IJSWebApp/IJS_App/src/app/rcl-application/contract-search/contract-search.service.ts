import { Injectable } from '@angular/core';
//import { Http, Response, Headers, URLSearchParams, RequestOptions } from '@angular/http';
import { Http, Response, Headers, URLSearchParams, RequestOptions, ResponseContentType } from '@angular/http';
import { Observable } from 'rxjs/Observable';
import 'rxjs/add/operator/map';
import 'rxjs/add/operator/catch';
import  { RclappUrlService } from "../../common-services/rclapp-url.service"




@Injectable()
export class ContractSearchService {

    constructor(private _http: Http, private _rclappUrlService: RclappUrlService) { }

    getData(searchDataObj: any): Observable<any> {
        let seachdata = JSON.stringify(searchDataObj);
        let searchParams = new URLSearchParams();
        searchParams.set("data", seachdata);
        
        return this._http.get(this._rclappUrlService.url + '/IJSWebApp/contractSearch.do', { search: searchParams })
            .map(this.extractData)
            .catch(this.handleErrorObservable);
    }

    //Create new contract data
    saveContractData(contractForSaveObj: any): Observable<any> {
        let contractForSave = JSON.stringify(contractForSaveObj);
        return this._http.post( this._rclappUrlService.url + '/IJSWebApp/contractSearch.do',contractForSave)
            .map(this.extractData)
            .catch(this.handleErrorObservable);
    }
    costRateGetData(searchDataObj: any): Observable<any> {
        let seachdata = JSON.stringify(searchDataObj);
        let searchParams = new URLSearchParams();
        searchParams.set("data", seachdata);
        return this._http.get(this._rclappUrlService.url +  '/IJSWebApp/rateSearch.do', { search: searchParams })
            .map(this.extractData)
            .catch(this.handleErrorObservable);
    }

    saveCostRateData(rateDataForSearchObj: any): Observable<any> {
        let rateDataForSearch = JSON.stringify(rateDataForSearchObj);
        return this._http.post(this._rclappUrlService.url + '/IJSWebApp/rateSearch.do', rateDataForSearch)
            .map(this.extractData)
            .catch(this.handleErrorObservable);
    }

    saveBillRateData(rateDataForSearchObj: any): Observable<any> {
        let rateDataForSearch = JSON.stringify(rateDataForSearchObj);
        return this._http.post(this._rclappUrlService.url + '/IJSWebApp/rateSearch.do', rateDataForSearch)
            .map(this.extractData)
            .catch(this.handleErrorObservable);
    }
    getCostRateTableData(searchData: any): Observable<any> {
        var searchDataObj = {
            routingNumber: searchData.routingID,
            paymentFSC: searchData.fsc,
            //#NIIT CR4 >>>>BEGIN
            rateType: searchData.rateType,
            costRateSetup:{"terminalDepotCode":searchData.terminalDepotCode},
            //#NIIT CR4 >>>>END
            action: 'rateSearch'
        }
        let seachdata = JSON.stringify(searchDataObj);
        let searchParams = new URLSearchParams();
        searchParams.set("data", seachdata);
        return this._http.get( this._rclappUrlService.url + '/IJSWebApp/rateSearch.do', { search: searchParams })
            .map(this.extractData)
            .catch(this.handleErrorObservable);
    }  

    //#NIIT CR4 >>>>BEGIN
    //method to get the ooglist for the setup
    getOOgList(searchData: any): Observable<any> {
        var searchDataObj = {
                costRateSetup:{
                    terminalDepotCode: searchData.terminalDepotCode
                    },           
                action: searchData.action
            }
        let seachdata = JSON.stringify(searchDataObj);
        let searchParams = new URLSearchParams();
        searchParams.set("data", seachdata);
        return this._http.get( this._rclappUrlService.url + '/IJSWebApp/rateSearch.do', { search: searchParams })
            .map(this.extractData)
            .catch(this.handleErrorObservable);
    }  
    
    //method to get the portlist and Imdg for the setup
    getPortImdgList(searchData: any): Observable<any> {
        var searchDataObj = {
                costRateSetup:{
                    terminalDepotCode: searchData.terminalDepotCode,
                    portImdgType: searchData.portImdgType
                    },           
                action: searchData.action
            }
        let seachdata = JSON.stringify(searchDataObj);
        let searchParams = new URLSearchParams();
        searchParams.set("data", seachdata);
        return this._http.get( this._rclappUrlService.url + '/IJSWebApp/rateSearch.do', { search: searchParams })
            .map(this.extractData)
            .catch(this.handleErrorObservable);
    }  
   
    //method to save the ooglist for the setup
    saveAndGetOogList(searchData: any): Observable<any> {
        var searchDataObj = {
                costRateSetup:{
                    terminalDepotCode: searchData.terminalDepotCode,
                    oogSetUpList: searchData.oogSetUpList                    
                    },           
                action: "saveOOGSetup"
            }
        let seachdata = JSON.stringify(searchDataObj);
        let searchParams = new URLSearchParams();
        searchParams.set("data", seachdata);
        return this._http.get( this._rclappUrlService.url + '/IJSWebApp/rateSearch.do', { search: searchParams })
            .map(this.extractData)
            .catch(this.handleErrorObservable);
    }  

    //method to save the port and imdglist for the setup
    saveAndGetPortImdgList(searchData: any): Observable<any> {

        if(searchData.portImdgType == "PORT"){
            var searchDataObj = {
                costRateSetup:{
                    terminalDepotCode: searchData.terminalDepotCode,
                    portList: searchData.portImdgList ,
                    portImdgType:searchData.portImdgType                   
                    },           
                action: searchData.action
            }
          var seachdata = JSON.stringify(searchDataObj);  
        }
        else if(searchData.portImdgType == "IMDG"){
            var Obj = {
                costRateSetup:{
                    terminalDepotCode: searchData.terminalDepotCode,
                    imdgList: searchData.portImdgList ,
                    portImdgType:searchData.portImdgType                   
                    },           
                action: searchData.action
            }
          var seachdata = JSON.stringify(Obj);  
        }
        
        
        let searchParams = new URLSearchParams();
        searchParams.set("data", seachdata);
        return this._http.get( this._rclappUrlService.url + '/IJSWebApp/rateSearch.do', { search: searchParams })
            .map(this.extractData)
            .catch(this.handleErrorObservable);
    }  
    
    //method to delete the ooglist for the setup
    deleteOogSetupFromList(searchData: any): Observable<any> {
        var searchDataObj = {
                costRateSetup:{
                    terminalDepotCode: searchData.terminalDepotCode,
                    oogSetUpList: searchData.oogSetUpList                    
                    },           
                action: "saveOOGSetup"
            }
        let seachdata = JSON.stringify(searchDataObj);
        let searchParams = new URLSearchParams();
        searchParams.set("data", seachdata);
        return this._http.get( this._rclappUrlService.url + '/IJSWebApp/rateSearch.do', { search: searchParams })
            .map(this.extractData)
            .catch(this.handleErrorObservable);
    }  

    //method to delete the port or imdg for the setup
    deletePortImdgSetupFromList(searchData: any): Observable<any> {

        if(searchData.portImdgType == "PORT"){
            var searchDataObj = {
                costRateSetup:{
                    terminalDepotCode: searchData.terminalDepotCode,
                    portList: searchData.portList,   
                    portImdgType: searchData.portImdgType                                   
                    },           
                action: searchData.action
            }
          var seachdata = JSON.stringify(searchDataObj);  
        }   
        else if(searchData.portImdgType == "IMDG"){
            var Obj = {
                costRateSetup:{
                    terminalDepotCode: searchData.terminalDepotCode,
                    imdgList: searchData.imdgList ,
                    portImdgType:searchData.portImdgType                   
                    },           
                action: searchData.action
            }
          var seachdata = JSON.stringify(Obj);  
        }    
       
        let searchParams = new URLSearchParams();
        searchParams.set("data", seachdata);
        return this._http.get( this._rclappUrlService.url + '/IJSWebApp/rateSearch.do', { search: searchParams })
            .map(this.extractData)
            .catch(this.handleErrorObservable);
    }  
    //#NIIT CR4 >>>>END
    
   downloadContract(downloadSearchData:string): Observable<Blob> {
    let searchParams = new URLSearchParams();
    searchParams.set("data", JSON.stringify(downloadSearchData));
    let searchResultDownloadUrl = this._rclappUrlService.url + "/IJSWebApp/contractSearch.do?" ;
    
    return this._http.get(searchResultDownloadUrl, {search: searchParams, responseType: ResponseContentType.Blob })
    .map(res => res.blob())
    .catch(this.handleErrorObservable);
  }
  doGeneratePortPair(): Observable<any> {
    var searchDataObj = {
          action: 'genPortPair'
    }
    let seachdata = JSON.stringify(searchDataObj);
    let searchParams = new URLSearchParams();
    searchParams.set("data", seachdata);
    let searchResultDownloadUrl = this._rclappUrlService.url + "/IJSWebApp/contractSearch.do?" ;
    
    return this._http.get(searchResultDownloadUrl,{ search: searchParams })
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


//>>>>>>>>>>>>>>>>>>>>>>>>>>>>><<<<<<<<<<<<<<<<<<<<<<<<>>>>>>>>>>>>>>>>>>>>>>>//
//-----------------------Merging Changes-------------------------------------//
//>>>>>>>>>>>>>>>>>>>>>>>>>>>>><<<<<<<<<<<<<<<<<<<<<<<<>>>>>>>>>>>>>>>>>>>>>//




}