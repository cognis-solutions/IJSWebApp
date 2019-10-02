import { Component, OnInit, ViewChild, Output, EventEmitter, Input } from '@angular/core';
import * as _ from 'lodash';
declare var UIkit: any;
declare var jQuery: any;
import * as $ from 'jquery';

@Component({
  selector: 'app-process-joadhoc',
  templateUrl: './process-joadhoc.component.html',
  styleUrls: ['./process-joadhoc.component.scss']
})
export class ProcessJOAdhocComponent implements OnInit {



  //vendorCode: string = "";
  selectedRowToUpdateRoute: any;
  EqDetailsList: any = [];
  processJoSearchData: any;
  routeListBookingType: string = "adhoc";
  routeContractID; //variable to hold the selected route contractID

  @ViewChild('joSummary2') joSummary2: any;
  @ViewChild('prosessjoRouteList') _prosessjoRouteList;
  @ViewChild('prosessjoEquipmentBrowser') _prosessjoEquipmentBrowser;
  @Output() closeAddHoc: EventEmitter<any> = new EventEmitter();
  @Input() private routeListInputValue: any;
  @Input() private addHocTypeName: any;
  @Input() private addHocTypeCode: any;
  @Input() private value: any;
  @Input() private routeDetails: any;
  callingComponent : string;
  @Input() private uploadListLimit: any;//#NIIT CR3 
  //@Input() private addHocSearchData: any;
  constructor() { }

  ngOnInit() {
  }

  insertLegshowRouteList(e) {    
    //this._prosessjoRouteList.joType = this.addHocTypeCode;//jo type    
    this.callingComponent = "adHocType"
    this._prosessjoRouteList.openLookUpModal(this.callingComponent);
    this.filteredEquipmentBrowserList = []; //empty the list when insert leg is clicked 
    this.eqListFromUpload = [];//empty the list coming from upload equipment
    this.eqListFromAvailableEquipments = [];//empty the list coming from available equipment
    this._prosessjoEquipmentBrowser.emptyContainerList = []; //empty the container list when insert leg is clicked
  }

  updateRoute(e) {
    this.selectedRowToUpdateRoute = e;
    this.routeDetails = e;
  }

  addEquipmentBrowserModal(e) {
    this._prosessjoEquipmentBrowser.openLookUpModal();
  }

  eqNumberList:any = [];
  eqNumberListDeatils:any=[]; //#NIIT CR3
  equipmentBrowserList:any=[];
  filteredEquipmentBrowserList:any=[];
  eqListFromUpload:any = [];//list coming from upload equipment
  eqListFromAvailableEquipments:any = [];//list coming from available equipment
  eqListFromUnavailableEquipments:any = [];//list coming from unavailable equipment
  updateEqDetails(e) {
    
    this.EqDetailsList = e;

    //maintain list coming from excel upload
    if(this.EqDetailsList.eqListSource == "uploadEquipment"){
      if(this.eqListFromUpload.length == 0){
        this.eqListFromUpload = this.EqDetailsList;
      }else{
        this.eqListFromUpload = this.eqListFromUpload.concat(this.EqDetailsList);
        this.eqListFromUpload = this.getUniqueEquipmentList(this.eqListFromUpload);
      }      
    }
    //maintain list coming from available equipments
    if(this.EqDetailsList.eqListSource == "availableEquipment"){
      if(this.eqListFromAvailableEquipments.length == 0){
        this.eqListFromAvailableEquipments = this.EqDetailsList;
      }else{
        this.eqListFromAvailableEquipments = this.eqListFromAvailableEquipments.concat(this.EqDetailsList);
        this.eqListFromAvailableEquipments = this.getUniqueEquipmentList(this.eqListFromAvailableEquipments);
      }        
    }

    //maintain list coming from unAvailable equipments
    if(this.EqDetailsList.eqListSource == "unavailableEquipment"){      
        this.eqListFromUnavailableEquipments = this.EqDetailsList;          
    }

    this.fileUploadFlag = true;

    this.filteredEquipment();   

    //to scroll to the bottom of the page
    //#NIIT CR3 BEGIN
    setTimeout(function() {
    var scrollValue= $('.TableHeightCalculate').height();
    $('#add-empty-equipment-setup-modal').parent().parent().parent().scrollTop(scrollValue);
    }, 0);
    //#NIIT CR3 END

}
  
  
  //show file upload component
  fileUploadFlag: boolean = true;
  uploadExcelFile(e) {    
    this.routeContractID = this.selectedRowToUpdateRoute.contractId
    this.fileUploadFlag = false;
  }

  closeFileUpload(e) {
    this.fileUploadFlag = true;
  }

  closeAddHocSideBar(e) {
    this.closeAddHoc.emit(e);
  }

  private showHideflag = false;
  errorCodetext:string; //#NIIT CR3
  private saveAdhocJoSummery(e) {
    this.filteredEquipmentBrowserList.forEach(row => {
      this.eqNumberList.push(row.eqpNum); 
      this.eqNumberListDeatils.push({"eqType":row.type,"eqSize":row.size,"eqNumber":row.eqpNum}); //#NIIT CR3
    }); 
    
    this.joSummary2.processJoType = this.addHocTypeCode;
    this.joSummary2.joSummerySaveObj = {
      'routingList': this.selectedRowToUpdateRoute,
      'eqList': this.eqNumberList,
      'eqDetails': this.eqNumberListDeatils, //#NIIT CR3
      'processJoType': this.addHocTypeCode,
      'vendorCode': this.routeListInputValue,
      'action': 'joSummeryAdhoc'
    }
    //#NIIT CR3 BEGIN
    let limit = this.uploadListLimit;
    if(this.eqNumberListDeatils.length > limit){//#NIIT CR3
      this.errorCodetext = "JO can be created for upto "+ this.uploadListLimit +" containers." //#NIIT CR3 
      UIkit.modal('#equipmentLimitExceedModal').show();  //#NIIT CR3    
    } else{
      this.showHideflag = true;
      this.joSummary2.getPrcessJoBackEndData();
    }
    //#NIIT CR3 END 
    this.eqNumberList = [];
    this.eqNumberListDeatils = []; //#NIIT CR3
  }
  
  hideJoSummery($event) {
    this.showHideflag = false;
  }
  
  //#NIIT CR3 BEGIN	
  //method to open the empty equipment containers modal 		
  openAddEmptyEquipmentsModal(e){ 
    this._prosessjoEquipmentBrowser.openEmptyEquipmentsModal();
  }
  //#NIIT CR3 END	

  //#NIIT CR3 BEGIN	
  //method to remove objects based on the delete criteria
  removeFromfilteredEquipmentBrowserList(e){
    var deleteCriteria = e;    
    if(deleteCriteria.length != 0){      
      this.filteredEquipmentBrowserList = [];
    }
  }
  //#NIIT CR3 END	

  //adhoc list edited therefore empty the master list
  adhocListEdited(e){
    this.filteredEquipmentBrowserList = [];
  }
  
  //#NIIT CR3 END

  //#NIIT CR3 BEGIN
  //method to remove duplicate elements from list
  getUniqueEquipmentList(listParam){    
    const list = listParam.filter(function({eqpNum}) {
      const key =`${eqpNum}`;
      return !this.has(key) && this.add(key);
    }, new Set);

    return list;
  }
  //#NIIT CR3 END

  //#NIIT CR3 BEGIN
  //method to get final equipment browser list
  filteredEquipment(){

    //checking list coming from excel upload
    if(this.eqListFromUpload.length != 0){
      this.filteredEquipmentBrowserList = this.filteredEquipmentBrowserList.concat(this.eqListFromUpload);
    }

    //checking list coming from equipment browser
    if(this.eqListFromAvailableEquipments.length !=0){
      this.filteredEquipmentBrowserList = this.filteredEquipmentBrowserList.concat(this.eqListFromAvailableEquipments);
    }

    //checking list coming from unavailable equipments
    if(this.eqListFromUnavailableEquipments.length !=0){
      this.filteredEquipmentBrowserList = this.filteredEquipmentBrowserList.concat(this.eqListFromUnavailableEquipments);
    }

    //call method and get final list of unique eqlist
    this.filteredEquipmentBrowserList = this.getUniqueEquipmentList(this.filteredEquipmentBrowserList);
    
  }
  //#NIIT CR3 END
  
}
