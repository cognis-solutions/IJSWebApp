import { Component, OnInit, ViewChild, Input, Output, EventEmitter, ViewContainerRef, ComponentRef } from '@angular/core';
import { NgModel, NG_VALUE_ACCESSOR, NG_VALIDATORS, NG_ASYNC_VALIDATORS } from '@angular/forms';
import { noop } from 'rxjs/util/noop';
import { SpinnerServiceService } from "../../common-services/spinner-service.service";
import { LookUpdataServiceService } from '../../common-services/look-updata-service.service';
import { ServerErrorcodeService } from "../../common-services/server-errorcode.service";
import { SortSearchTableService } from '../../rcl-application//contract-search/sort-search-table.service';
import { ContainerListService } from "../../common-services/container-list.service";
import { Http, Response, Headers, URLSearchParams, RequestOptions } from '@angular/http';
import { ModalDialogModule, IModalDialog, ModalDialogService, IModalDialogOptions, IModalDialogButton } from 'ngx-modal-dialog';
import { Observable } from 'rxjs/Observable';
import 'rxjs/add/operator/map';
import 'rxjs/add/operator/catch';
import * as $ from 'jquery';
import { SessionTimeOutService } from "app/common-services/session-time-out.service";
import { ProcessjoSearchService } from "../../rcl-application/process-jo/processjo-search.service";

declare var UIkit: any;

@Component({
  selector: 'app-rcl-container-modal',
  templateUrl: './rcl-container.component.html',
  styleUrls: ['./rcl-container.component.scss']
})
export class RCLContainerModalComponent implements OnInit,IModalDialog  {

  actionButtons: IModalDialogButton[];
  @Input() private searchCriteria: any;
  @Output() selectUpdateRouteList = new EventEmitter();
  @Output() containerList = new EventEmitter();
  @Output() containerCheckedList = new EventEmitter();
  @Input() private event: any;
  @Output() selectReplaceContainer = new EventEmitter();
  private inputTag: boolean = null;
  private openModal: boolean = false;
  private showlookuptable = false;
  private resultsPerPageArr = [1, 2, 3, 4, 5, 6, 7, 8, 9, 10];
  private resultsPerPage = 5;
  private looUpOrderBy: any;
  private lookupSortIn: any;
  private selectedRow: any = [];
  private lookupErrorCodeShow: boolean = false;
  private lookupErrorCodetext: string;
  private routeListSortData = [{ label: 'Routing #', value: 'routingId' }, { label: 'Priority', value: 'priority', }, { label: 'Currency	', value: 'currency' }, { label: 'Leg Type	', value: 'legType' }];
  private lookUpSortData = [{ label: 'Ascending', value: 'asnd' }, { label: 'Desending', value: 'dsnd' }]
  checkComponent;
  private routeListTableData = [];
  private seachValueList = [];
  private showCheckBox = false;
  private contType: any;
  private searchType: any; //checking whether searching for Total,Available and In Jo
  private bookingNumber: string;
  //for actual container weight
  private actualContainerWeight: string;
  private showContainers = true; //to show only conatiner values in modal
  multipleSelect:boolean = true; //flag to allow user to multiple checkboxes based on user
  

  constructor(private _spinner: SpinnerServiceService, private _lookupData: LookUpdataServiceService, public _serverErrorCode: ServerErrorcodeService, public _sortTable: SortSearchTableService, private _http: Http,private clService:ContainerListService,private _sessionTimeOutService:SessionTimeOutService, private _httpService: ProcessjoSearchService) {
    // this.actionButtons = [     
    //   //{ text: 'Close', onAction: () => true },
    //   { text: 'Select', onAction: () => this.updateRoute() }
    // ];
    
    console.log( this._httpService.processJoSearchData);
   }

  ngOnInit() {
    //this.openLookUpModal();
  }

  dialogInit(reference: ComponentRef<IModalDialog>, options: Partial<IModalDialogOptions<any>>) {
    // no processing needed
    //console.log("dialog oninit called"+ JSON.stringify(options.data));
    this.openLookUpModal(options.data.event, options.data.contType, options.data.searchType, options.data.bkgBlNumber, options.data.cntSize, options.data.cntSplHandling, options.data.bookType, options.data.jobType, options.data.row , options.data.emptyContainer,options.data.ladenContainer,options.data.index,options.data.selectedRowLaden,options.data.selectedRowEmpty)
    if(options.data.searchType == 'AV'){ //Select button for available
        this.actionButtons = [   
        { text: 'Select', onAction: () => this.updateRoute() }
      ];
    } else{ //close button for Total and In Jo
        this.actionButtons = [     
        { text: 'Close', onAction: () => true },      
      ];
    }    
  }
  joContainerDetails = [];
  ladenContainerList = [];
  rowIndex;
  openLookUpModal(event, contType, searchType, bkgBlNumber, cntSize, cntSplHandling, bookType, jobType, row , emptyContainer,ladenContainer,index,selectedRowLaden,selectedRowEmpty) {
   // console.log('open lookup'+ JSON.stringify(ladenContainer));
   // console.log("seleceted row for Laden"+ JSON.stringify(selectedRowLaden));
   // console.log("seleceted row for empty "+ JSON.stringify(selectedRowEmpty));
    this.contType = contType;
    this.rowIndex = index;
    this.searchType = searchType;
    this.bookingNumber = bkgBlNumber;
    if(selectedRowLaden != undefined && contType== "L"){
      this.selectedRow = selectedRowLaden;
    } else if(selectedRowEmpty != undefined && contType== "E"){
      this.selectedRow = selectedRowEmpty;
    }    
    this.showCheckBox = false ;
    if(searchType != "AV"){
      this.showCheckBox = true;
      this.showContainers = false;
    }
    
    if(row.callingComponent == "joMaintainenance"){
      this.checkComponent = row.callingComponent;
      this.multipleSelect = false;
      this.joContainerDetails = row;
    } else{
      this.multipleSelect = true;
    }
    
   
    //$("#rcl-container-lookup-modal-center").detach();

    this.seachValueList  =[];
    let obj = { "key": "contType", "value": contType};
    this.seachValueList.push(obj);
    
    obj = { "key": "searchType", "value": searchType};
    this.seachValueList.push(obj);
    
    obj = { "key": "bookType", "value": bookType};
    this.seachValueList.push(obj);

    obj = { "key": "jobType","value": jobType};
    this.seachValueList.push(obj);

    //assigning bkgblnumber based on component from where it is called
    if(row.callingComponent == "joMaintainenance"){
      obj = { "key": "bkgBlNumber","value": row.bkgBlNumber};
      this.seachValueList.push(obj);
    }else{
    obj = { "key": "bkgBlNumber","value": bkgBlNumber};
    this.seachValueList.push(obj);
    }

    obj = { "key": "cntSize","value": cntSize};
    this.seachValueList.push(obj);

    obj = { "key": "cntSplHandling","value": cntSplHandling};
    this.seachValueList.push(obj);

    obj = { "key": "fromLocation","value": row.fromLocation};
    this.seachValueList.push(obj);

    obj = { "key": "toLocation","value": row.toLocation};
    this.seachValueList.push(obj);

    obj = { "key": "fromTerminal","value": row.fromTerminal};
    this.seachValueList.push(obj);

    obj = { "key": "toTerminal","value": row.toTerminal};
    this.seachValueList.push(obj);

    obj = { "key": "fromMode","value": row.fromLocationTyp};
    this.seachValueList.push(obj);

    obj = { "key": "toMode","value": row.toLocationTyp};
    this.seachValueList.push(obj);

    obj = { "key": "cntType","value": row.cntType};
    this.seachValueList.push(obj);

    if(this._httpService.processJoSearchData.processJoParam.vendorCode == null || this._httpService.processJoSearchData.processJoParam.fromLocation == null || this._httpService.processJoSearchData.processJoParam.toLocation == null ||this._httpService.processJoSearchData.processJoParam.fromTerminal == null ||this._httpService.processJoSearchData.processJoParam.toTerminal == null) {
      this.inputTag = true;
    } 

    obj = { "key": "searchvendorCode","value": this._httpService.processJoSearchData.processJoParam.vendorCode};
    this.seachValueList.push(obj);

    
    obj = { "key": "searchfromLocation","value": this._httpService.processJoSearchData.processJoParam.fromLocation};
    this.seachValueList.push(obj);

    obj = { "key": "searchtoLocation","value": this._httpService.processJoSearchData.processJoParam.toLocation};
    this.seachValueList.push(obj);

    obj = { "key": "searchfromTerminal","value": this._httpService.processJoSearchData.processJoParam.fromTerminal};
    this.seachValueList.push(obj);

    obj = { "key": "searchtoTerminal","value": this._httpService.processJoSearchData.processJoParam.toTerminal};
    this.seachValueList.push(obj);
    obj = { "key": "routingNumber","value": row.routingNumber};
    this.seachValueList.push(obj);


    this._httpService.processJoSearchData
    this.openModal = true;
    this.showlookuptable = true;
    this.lookupErrorCodeShow = false;
    this._spinner.showSpinner();
    // if(contType == "L"){
    //   if(ladenContainer.length == 0){
    //   this.getLocLookUpData('getJOContainer','','',this.seachValueList,'');
    //   }
    // }else if(contType == "E"){
    //   if(emptyContainer.length == 0){
    //   this.getLocLookUpData('getJOContainer','','',this.seachValueList,'');
    //   }
    // }
    if((contType== "L" && ladenContainer == undefined && searchType == "AV")){
        this.getLocLookUpData('getJOContainer','','',this.seachValueList,'',ladenContainer,''); //getting containers for Available when contType = L    
    }
    else if((contType== "L" && ladenContainer == "" && row.callingComponent == "joMaintainenance" && searchType == "AV")){
        this.getLocLookUpData('getJOContainer','','',this.seachValueList,'',ladenContainer,"jOMaintenance"); //getting containers when called from jo maintenance    
    } else if((contType== "L" && searchType == "T" )){
        this.getLocLookUpData('getJOContainer','','',this.seachValueList,'',ladenContainer,''); //getting containers for Total when contType = L
    } else if((contType== "L" && searchType == "IN" )){
        this.getLocLookUpData('getJOContainer','','',this.seachValueList,'',ladenContainer,''); //getting containers in In JO when contType = L
    } else if(contType== "E" && emptyContainer == undefined && searchType == "AV"){
        this.getLocLookUpData('getJOContainer','','',this.seachValueList,'',emptyContainer,'');  //getting containers for Available when contType = E
    } else if(contType== "E" && emptyContainer == "" && row.callingComponent == "joMaintainenance" && searchType == "AV"){
        this.getLocLookUpData('getJOContainer','','',this.seachValueList,'',emptyContainer,"jOMaintenance");  //getting containers when called from jo maintenance
    } else if((contType== "E" && searchType == "T" )){
        this.getLocLookUpData('getJOContainer','','',this.seachValueList,'',ladenContainer,''); //getting containers for Total when contType = E
    } else if((contType== "E" && searchType == "IN" )){
        this.getLocLookUpData('getJOContainer','','',this.seachValueList,'',ladenContainer,''); //getting containers in In JO when contType = E
    } else {
        this.showlookuptable = false;
        this.lookupErrorCodetext = undefined;
        this.lookupErrorCodeShow = false;
          if(contType== "L"){
            this.clService.cntrlistData = ladenContainer;
          } else{
            this.clService.cntrlistData = emptyContainer;
          } 
      
      this._spinner.hideSpinner();
     // UIkit.modal('#rcl-container-lookup-modal-center').show();
    }
   // this.getLocLookUpData('getJOContainer','','',this.seachValueList,'');

    // setTimeout(function () {
    //   UIkit.modal('#rcl-container-lookup-modal-center').show();
    // }, 100);
  }

  cntrlistData = [];
  noContainerFlag:boolean = false;
  getLocLookUpData(lookupTpye, type, eve, inpuvaluevalue, wildCard ,containerList,componentType) {     
   //this.clService.cntrlistData = containerList; 
   this._spinner.showSpinner();    
   var backendData = this._lookupData.getDataLookupServiceJOAll(lookupTpye, type, eve, inpuvaluevalue, wildCard,'',componentType);
   backendData.subscribe(
     (data) => {    
       if(data == "userSessionExpired"){
          this._sessionTimeOutService.checkSessionTimeout(data);
       }    
       else if (data.hasOwnProperty("errorCode")) {
           this.lookupErrorCodetext = this._serverErrorCode.checkError(data["errorCode"]);
            this.noContainerFlag = true;
           this.lookupErrorCodeShow = false;
           this.showlookuptable = false;
           var containerError = [];
           containerError.push({container:"There are no containers available for this Booking."});
           this.clService.cntrlistData=containerError;          
         }
         else {        
           this.showlookuptable = false;
           this.lookupErrorCodetext = undefined;
           this.lookupErrorCodeShow = false;
           //this.routeListTableData = data;
           this.clService.cntrlistData = data;
         }
         this._spinner.hideSpinner();
         //UIkit.modal('#rcl-container-lookup-modal-center').show();
     },
     (err) => {      
       this._spinner.hideSpinner();
       // A client-side or network error occurred. Handle it accordingly.
       this.lookupErrorCodetext = "Something Went wrong!! Please Try Again"  
       this.lookupErrorCodeShow = true;       
     }
   )
  }

  sortLookUpDataByColumn() {
    this._sortTable.sortTableData(this.routeListTableData, this.lookupSortIn, this.looUpOrderBy);
  }
  sortLookUpDataByOrder() {
    this._sortTable.sortTableData(this.routeListTableData, this.lookupSortIn, this.looUpOrderBy);
  }

  resetRouteListModal(e) {
    this.routeListTableData = [];
    this.openModal = false;
    UIkit.modal('#rcl-container-lookup-modal-center').hide();
  }

  
    //select Check box
   selectedFlag:boolean = true; 

  private getRowData(e, rowData, i) {        
    if (e.target.checked == true) {
      this.selectedFlag = true;
      this.clService.cntrlistData[i]['selectedFlag'] = true;
      let tempObj = {
        "container": rowData.container,
        "contType": this.contType,
        'bookingNumber': this.bookingNumber,
        'actualContainerWeight': rowData.actualContainerWeight
      }      
      this.selectedRow.push(tempObj);
     // console.log("getRowData <<>>"+ JSON.stringify(this.selectedRow));
    } else {
     this.selectedFlag = false; 
     this.clService.cntrlistData[i]['selectedFlag'] = false;
     this.selectedRow = this.deleteObjByRoutingId(this.selectedRow, 'container', rowData.container);
    }    
  }
  
    //delete element from array
  private deleteObjByRoutingId(arr, attr, value) {
    var i = arr.length;
    while (i--) {
      if (arr[i]
        && arr[i].hasOwnProperty(attr)
        && (arguments.length > 2 && arr[i][attr] === value)) {
        arr.splice(i, 1);
      }
    }
    return arr;
  }

  updateRoute() : boolean {
     // console.log('clicked'+ this.contType);
      UIkit.modal('#rcl-container-lookup-modal-center').hide();
      //for process jo screen  
      if((this.searchType == "AV" && this.checkComponent == undefined)){
      //  console.log('update Route :'+ {data: this.clService.cntrlistData,index: this.rowIndex});    
        this.clService.ladenSubject.next({data: this.clService.cntrlistData,index: this.rowIndex , contType: this.contType, selectedRow : this.selectedRow });
        //this.containerCheckedList.emit({data: this.cntrlistData,index: this.rowIndex});  
     //   console.log("selected rows <<>>"+ JSON.stringify(this.selectedRow));  
        //this.containerList.emit(this.selectedRow);
        //console.log('emit called??');        
      }     
      //new container emitted to jo maintainenance screen to replace with old containers
      if(this.checkComponent == "joMaintainenance" && this.selectedRow !=""){
        //event emmited to replace containers
        this.clService.ladenSubject.next({selectedRow : this.selectedRow });          
      } 
      this.lookupErrorCodeShow = false;
      this.lookupErrorCodetext = '';
      this.selectedRow = [];     
      this.openModal = false;   
      this.clService.cntrlistData = []; 
      return true;   
  }

  getCntrListData(){
    return this.clService.cntrlistData;
  }

  getSelectedData(e, rowData, i){
    this.selectedRow = [];
    let selected = rowData.selected;
    this.deselectAll(this.clService.cntrlistData);
    rowData.selected = !selected;
    rowData['checked'] = rowData.selected;
    //console.log(this.joContainerDetails);
    this.selectedRow.push(rowData);
  }

  deselectAll(arr: any[]){
    arr.forEach(val =>{
    if(val.selected){
        val.selected = false;
        val.checked = false;
    }})
  }

}