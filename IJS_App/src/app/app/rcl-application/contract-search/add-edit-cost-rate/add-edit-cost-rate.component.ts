import { Component, OnInit, ViewChild, Input, Output, EventEmitter, ViewContainerRef } from '@angular/core';
import { ContractSearchService } from "../contract-search.service";
declare var jQuery: any;
import * as $ from 'jquery';
declare var UIkit: any;
import { SpinnerServiceService } from "../../../common-services/spinner-service.service";
import { ServerErrorcodeService } from "../../../common-services/server-errorcode.service";
import { ContractSearchComponent } from "../contract-search.component";
import { LookUpdataServiceService } from '../../../common-services/look-updata-service.service';
import { SortSearchTableService } from '../sort-search-table.service';
import { MappingData } from './add-edit-mapping';
import { SpecialHandlingService } from "../../../common-services/special-handling.service";
import {PortClassService } from '../../../common-services/port-class.service';
import {ImdgClassService } from '../../../common-services/imdg-class.service';
import { ModalDialogModule,IModalDialog,ModalDialogService } from 'ngx-modal-dialog';
import { PortClassSetupComponent } from '../port-class-setup/port-class-setup.component';
import { ImdgClassSetupComponent } from '../imdg-class-setup/imdg-class-setup.component';
import { OogDimentionSetupComponent } from '../oog-dimention-setup/oog-dimention-setup.component';
import { SessionTimeOutService } from "app/common-services/session-time-out.service";
@Component({
  selector: 'app-add-edit-cost-rate',
  templateUrl: './add-edit-cost-rate.component.html',
  styleUrls: ['./add-edit-cost-rate.component.scss']
})
export class AddEditCostRateComponent implements OnInit {

  addEditCostRateObj = {
    ijsRateVO: {
      mtOrLaden: "LADEN",
      rateBasis: "S",
      eqCatq: "B",
      rateStatus: "O",
      impOrExp: "ALL",
      splHandling: "N",
      eqType: "**",
      oogCode: "", //#NIIT CR4 >>>>BEGIN
      service: "****",
      uom: 'K'
    }
  };

  costServiceLookupFlag: boolean = false;
  @Output() public hideAddNewCostRate = new EventEmitter();
  @ViewChild('CostServiceLookup') _costServiceLookup: any;
  @ViewChild('OogDimentionSetup') _oogDimentionSetup: any;
  @ViewChild('spclCostBlBooking') _spclCostBlBooking: any;
  @ViewChild('imDgClassSetup') _imDgClassSetup: any;
  @ViewChild('portClassSetup') _portClassSetup: any;
  @Input() private userType: any;
  @Output() public successMessage = new EventEmitter();
  @Output() public successFlag = new EventEmitter();
  @Output() public getData = new EventEmitter();
  @Input() eqTypeListforCostTable: any;
  @Input() userTypeforAddEdit: any; //for getting the user type
  @Output() public editCostMessage = new EventEmitter(); //event containing the editCostRate object
  private modalService:any;
  private viewRef:any;

  //#NIIT CR4 >>>>BEGIN
  @Input() portOptionsForCostPopup:any; 
  @Input() imdgOptionsForCostPopup:any; 
  @Input() oogOptionsForCostPopup:any;
  @Input() selectedContarctRow:any;
  //#NIIT CR4 >>>>END



  public _mappingData: MappingData;
  eqTypeList: any;
  termCodesList: any;
  addEditLabel: any;

   //rate options for location user
  private rateStatusOptionsforLocationUser: any = [
    {
      label: 'Open',
      value: 'O',
    }, {
      label: 'Pending',
      value: 'P',
    }
  ]


  rateStatusOptions: any = [
        {
            label: 'Open',
            value: 'O',
        }, {
            label: 'Pending',
            value: 'P',
        },
        {
            label: 'Approved',
            value: 'A',
        },
        {
            label: 'Rejected',
            value: 'R',
        }
    ]

  addEditSelectConfig: any = {
    highlight: false,
    create: false,
    persist: true,
    plugins: ['dropdown_direction', 'remove_button'],
    dropdownDirection: 'down',
    labelField: 'label',
    valueField: 'value',
    searchField: ['label'],
    maxItems: 1
  };
  addEditMultiSelectConfig: any = {
    highlight: true,
    create: false,
    persist: true,
    plugins: ['dropdown_direction', 'remove_button'],
    dropdownDirection: 'down',
    labelField: 'label',
    valueField: 'value',
    searchField: ['label'],
    maxItems: 6
  };
  failureTextMsg: string;
  successTextMsg: string;
  successtextFlag: boolean;
  splCostByBlOrBookingFlag: boolean;

  vesselLookUpData: any = [{ "dropDownData": [{ "label": "Vessel Code", "value": "VSVESS" }, { "label": "Vessel Name", "value": "VSLGNM" }, { "label": "Operator Code", "value": "VSOPCD" }] }];
  currencyLookUpData: any = [{ "dropDownData": [{ "label": "Currency Code", "value": "CURRENCY_CODE" }, { "label": "Currency Name", "value": "CURRENCY_NAME" }] }];

  constructor(public _joborderService: ContractSearchService, private _spinner: SpinnerServiceService, public _serverErrorCode: ServerErrorcodeService, public _sortTable: SortSearchTableService,private shService:SpecialHandlingService,private portService:PortClassService,private imdgService:ImdgClassService,modalService: ModalDialogService, viewRef: ViewContainerRef,private _sessionTimeOutService:SessionTimeOutService) {
    this.modalService = modalService;
    this.viewRef = viewRef;
    this._mappingData = new MappingData();
    this.addEditCostRateObj.ijsRateVO['manualRate'] = true;   

    jQuery(document).ready(function () {

      jQuery('.lookup-wrapper').on('click', function (event) {
        event.stopImmediatePropagation();
        jQuery("html, body").animate({ scrollTop: 0 }, 1000);
        return false;

      });

    })    
  }

  ngOnInit() {    
    if (this.userTypeforAddEdit != "Global") {
      this._mappingData.rateStatusOptions = this.rateStatusOptionsforLocationUser;
    } else{
       this._mappingData.rateStatusOptions = this.rateStatusOptions;
    }

    this._mappingData.eQTypeOptions = this.eqTypeListforCostTable;

    this.shService.currentNameSubjectforAddEdit.subscribe((val)=>{ 
        try{
          this.getValueOogSetup(val);
        } catch(error){
          console.log("error in currentNameSubjectforAddEdit");
        }        
      });

    this.portService.portClassSubjectforAddEdit.subscribe((val)=>{      
       try{
         this.getValuePortClassSetup(val);
       } catch(error){
         console.log("error in portClassSubjectforAddEdit"); 
       }
    });  


    this.imdgService.imdgClassSubjectforAddEdit.subscribe((val)=>{     
       try{
         this.getValueimDgClassSetup(val);
       } catch(error){
         console.log("error in imdgClassSubjectforAddEdit");
       }       
    });

  }

  //#NIIT CR4 >>>>BEGIN
  clearField(e) {
    if (e == "N" || e == "RF" || e == "DA" || e == "OD" || e == "BBK" || e == "ODG") {
		this.addEditCostRateObj.ijsRateVO['portCode'] = null;
		this.addEditCostRateObj.ijsRateVO['imdgCode'] = null;
		this.addEditCostRateObj.ijsRateVO['oogCode'] = null;               
      }
      if (e == "RDG" || e == "D1") {
        this.addEditCostRateObj.ijsRateVO['oogCode'] = null;          
      }
      if (e == "0G") {
        this.addEditCostRateObj.ijsRateVO['portCode'] = null;
		    this.addEditCostRateObj.ijsRateVO['imdgCode'] = null;
      }  
  }
  //#NIIT CR4 >>>>END

  RateBasisChanged(e) {   
    let rateBasis = e;
    if (rateBasis == "S") {
      this.addEditCostRateObj.ijsRateVO['lumpSum'] = null;
      this.addEditCostRateObj.ijsRateVO['splHandling'] = null;

    }
    else if (rateBasis == "B" || rateBasis == "L") {
      this.addEditCostRateObj.ijsRateVO['rate20'] = null;
      this.addEditCostRateObj.ijsRateVO['rate40'] = null;
      this.addEditCostRateObj.ijsRateVO['rate45'] = null; 
      this.addEditCostRateObj.ijsRateVO['splHandling'] = null;
      this.addEditCostRateObj.ijsRateVO['mtOrLaden'] = null;
      this.addEditCostRateObj.ijsRateVO['eqCatq'] = null;
      this.addEditCostRateObj.ijsRateVO['upto'] = null;
      this.addEditCostRateObj.ijsRateVO['eqType'] = null; 
      this.addEditCostRateObj.ijsRateVO['service'] = "***";
      this.addEditCostRateObj.ijsRateVO['vesselCodes'] = "***";          
    }
    rateBasis = null;
  }

  setRateStatus() {
    if (this.userTypeforAddEdit != "Global") {
      this.addEditCostRateObj.ijsRateVO['rateStatus'] = "O";
    } else {
      this.addEditCostRateObj.ijsRateVO['rateStatus'] = "A";
    }
  }

  showExemptedCustomer: boolean;
  currentComponent:string;//#NIIT CR6 >>>>
  currentClickedRow = {};
  showExempted(e, data) {
    if (this.splCostByBlOrBookingFlag) {
      this.showExemptedCustomer = true;
      this.currentComponent = 'addEditCostRate'; //#NIIT CR6 >>>>
      this.currentClickedRow = data;
      jQuery("html, body").animate({ scrollTop: 0 }, 900); //to scroll to the top of page
    }
  }

  disableClose:boolean = false;//#NIIT CR4 >>>>BEGIN
  hideExemptedCustomer(e) {
    this.showExemptedCustomer = false;
    this.splCostByBlOrBookingFlag = false;
    this.disableClose = e.exemptedDataFlag;
    this.addEditCostRateObj.ijsRateVO['splCostByBlOrBooking'] = e.tempString;//#NIIT CR4 >>>>BEGIN
  }

  closeAddEditCost(e) {
    this.hideAddNewCostRate.emit(e);
  }

  showServiceLookUp(e) {
    this._costServiceLookup.showModal();
  }

  getTodaytDate() {
    var today = new Date();
    var dd = today.getDate();
    var mm = today.getMonth() + 1; //January is 0!
    var yyyy = today.getFullYear();
    var dd1;
    var mm1;
    var today1;

    if (dd < 10) {
      dd1 = '0' + dd;     
    } else {
      dd1 = '' + dd;
    }
    if (mm < 10) {
      mm1 = '0' + mm;
    } else {
      mm1 = '' + mm;
    }
    today1 = dd1 + '/' + mm1 + '/' + yyyy;
    return today1;
  }


  closeWarning() {  
    //#NIIT CR4 >>>>  
    $('#special-handling-validation-modal').addClass('uk-open').hide();
  }

  expiryDate: Date;
  today: Date;
  action: String;
  warningTextMsg: String;
  process(date) {
    var parts = date.split("/");
    var date: any = new Date(parts[1] + "/" + parts[0] + "/" + parts[2]);
    return date.getTime();
  }

  //object containing message and flag
  costRateDataObject = {   
      costOperationMessage: "",
      costOperationFlag: true,        
  };

  sendDataToBackEnd() {
    alert('sendDataToBackEnd');
    this.successTextMsg = "";
    this.failureTextMsg = "";
    this.expiryDate = this.addEditCostRateObj.ijsRateVO['endDate'];
    this.today = this.getTodaytDate();
    this.action = this.addEditCostRateObj['action'];
    // if (this.action == "editCostRate" && this.process(this.today) > this.process(this.expiryDate)) {
    //   this.warningTextMsg = "Expiry Date Cannot be less than Current Date";
    //   UIkit.modal('#date-warning-modal').show();
    // } 
    //#NIIT CR4 >>>>BEGIN
    if ((this.addEditCostRateObj.ijsRateVO['splHandling'] == 'D1' || this.addEditCostRateObj.ijsRateVO['splHandling'] == 'RDG') && (this.addEditCostRateObj.ijsRateVO['portCode'] == undefined && this.addEditCostRateObj.ijsRateVO['imdgCode'] == undefined)) {
      this.warningTextMsg = "Enter either Port Class Code or IMDG Details.";     
      $('#special-handling-validation-modal').addClass('uk-open').show();
    }
    else if (this.addEditCostRateObj.ijsRateVO['splHandling'] == '0G' && this.addEditCostRateObj.ijsRateVO['oogCode'] == undefined) {
      this.warningTextMsg = "Enter OOG Details.";      
      $('#special-handling-validation-modal').addClass('uk-open').show();
    }
    else if (this.addEditCostRateObj.ijsRateVO['splHandling'] == 'ODG' && ((this.addEditCostRateObj.ijsRateVO['portCode'] == undefined || this.addEditCostRateObj.ijsRateVO['imdgCode'] == undefined) && this.addEditCostRateObj.ijsRateVO['oogCode'] == undefined)) {
      this.warningTextMsg = "Enter either Port Class Code or IMDG Details and OOG Details.";     
      $('#special-handling-validation-modal').addClass('uk-open').show();
    }//#NIIT CR4 >>>>END
    else {
      this._spinner.showSpinner();
      if (this.addEditCostRateObj.ijsRateVO.eqType) {
        this.addEditCostRateObj.ijsRateVO.eqType = this.addEditCostRateObj.ijsRateVO.eqType.toString();
      }
      if (this.addEditCostRateObj.ijsRateVO['term']) {
        this.addEditCostRateObj.ijsRateVO['term'] = this.addEditCostRateObj.ijsRateVO['term'].toString();
      }    
      var backendData = this._joborderService.saveCostRateData(this.addEditCostRateObj);
      backendData.subscribe(
        (data) => {
          this.successTextMsg = "";
          if(data == "userSessionExpired"){
            this._sessionTimeOutService.checkSessionTimeout(data);
          }
          else if (data.hasOwnProperty("errorCode")) {
            this.successTextMsg = "";
            if (data["errorCode"].includes("IJS_EX_") || data["errorCode"].includes("IJS_RATE_EX_") || data["errorCode"].includes("IJS_CNTR_EX")) {
              this.failureTextMsg = this._serverErrorCode.checkError(data["errorCode"]);
              this.successtextFlag = false;
              this.costRateDataObject.costOperationMessage = this.failureTextMsg; //assigning message value to object field
              this.costRateDataObject.costOperationFlag = this.successtextFlag; //assigning flag value to object field 
              this.editCostMessage.emit(this.costRateDataObject); //emitting the object to parent component
              //UIkit.modal('#add-edit-cost-failure').show();
            } else {
              this.successTextMsg = this._serverErrorCode.checkError(data["errorCode"]);
              this.successtextFlag = true;
              this.costRateDataObject.costOperationMessage = this.successTextMsg;
              this.costRateDataObject.costOperationFlag = this.successtextFlag;
              this.editCostMessage.emit(this.costRateDataObject);              
            }
          }         
          this._spinner.hideSpinner();
        }
      )
    }
  }



  serviceRowSelected(e) {
    this.addEditCostRateObj.ijsRateVO['service'] = e;
  }
  closeAddEditCostRate(e) {

    UIkit.modal('#add-edit-cost-success').hide();
    this.closeAddEditCost('');
  }

  showOOGModal(e, val) {
    if (e.target.disabled == true) {
      e.preventDefault();
    } else {
      //val = this.convertOOgValue({}, val);
      if(val == undefined){
          val = new Array<Object>();
      }            
      this.shService.oogList = [...val]; 
      this.openOogNewDialog(this.shService.oogList,this.checkComponentName);       
      //this._oogDimentionSetup.showModal(this.shService.oogList, this.checkComponentName);
    }
  }

   openOogNewDialog(oogList,checkComponent) {
    this.modalService.openDialog(this.viewRef, {
    title: 'OOG Dimention Setup',
    data: { oogList : oogList, checkComponent : checkComponent},
    childComponent: OogDimentionSetupComponent
    });
  }

  showSpclCostbyBlBookingModal(e, val) {
    if (e.target.disabled == true) {
      e.preventDefault();
    } else {
      //val = this.convertOOgValue({}, val)
      this._spclCostBlBooking.showModal(this.addEditCostRateObj.ijsRateVO['splCostByBlOrBookingList']);
    }
  }

  convertOOgValue(obj, val) {
    return;
  }

  getValueOogSetup(e) {
    let tempStr = ""
    for (let i = 0; i < e.length; i++) {
      if (i == e.length - 1) {
        tempStr = tempStr.concat(e[i].oogSetupCode + ":" + e[i].minOverLength + ":" + e[i].maxOverLength + ":" + e[i].minOverWidth + ":" + e[i].maxOverWidth + ":" + e[i].minOverHeight + ":" + e[i].maxOverHeight);
     
      } else {
        tempStr = tempStr.concat(e[i].oogSetupCode + ":" + e[i].minOverLength + ":" + e[i].maxOverLength + ":" + e[i].minOverWidth + ":" + e[i].maxOverWidth + ":" + e[i].minOverHeight + ":" + e[i].maxOverHeight + "::");
       
      }
    }
    //#NIIT CR4 >>>>
    this.addEditCostRateObj.ijsRateVO['oogCode'] = tempStr;
    this.addEditCostRateObj.ijsRateVO['oogSetUpList'] = e;
  }

  getValueSpclBlBooking(e) {
    this.addEditCostRateObj.ijsRateVO['splCostByBlOrBookingList'] = e;    
    this.addEditCostRateObj.ijsRateVO['splCostByBlOrBooking'] = e;
    
  }

  showimDgModal(e, val) {
    if (e.target.disabled == true) {
      e.preventDefault();
    } else {      
      if(val == undefined){
          val = new Array<Object>();
      }            
      this.imdgService.imdgList = [...val]; 
      this.openImdgNewDialog(this.imdgService.imdgList,this.checkComponentName);     
      
    }
  }

  openImdgNewDialog(imdgList,checkComponent) {
  this.modalService.openDialog(this.viewRef, {
    title: 'IMDG Class',
    data: { imdgList : imdgList, checkComponent : checkComponent},
    childComponent: ImdgClassSetupComponent
    });
  }

  getValueimDgClassSetup(e) {
    let tempStr = "";    
    
    if (e.length > 0) {
      for (let i = 0; i < e.length; i++) {
        if (i == e.length - 1) {
          tempStr = tempStr.concat(e[i].imdgClass + ":" + e[i].includeUnno + ":" + e[i].excludeUnno);
        } else {
          tempStr = tempStr.concat(e[i].imdgClass + ":" + e[i].includeUnno + ":" + e[i].excludeUnno + "::");
        }
      }
      //#NIIT CR4 >>>>BEGIN
      this.addEditCostRateObj.ijsRateVO['imdgCode'] = tempStr;
      this.addEditCostRateObj.ijsRateVO['imDgList'] = e;
    } else {
      this.addEditCostRateObj.ijsRateVO['portCode'] = undefined;
      this.addEditCostRateObj.ijsRateVO['imdgCode'] = undefined;
      this.addEditCostRateObj.ijsRateVO['imDgList'] = e;
      //#NIIT CR4 >>>>END
    }
    
  }

  checkComponentName: string = "addEditCostRate";
  showPortClassModal(e, val) {
    if (e.target.disabled == true) {
      e.preventDefault();
    } else {      
      if(val == undefined){
          val = new Array<Object>();
      }            
      this.portService.portClassList = [...val];
      this.openNewDialog(this.portService.portClassList,this.checkComponentName);  
    }
  }

  openNewDialog(portClassList,checkComponent) {
  this.modalService.openDialog(this.viewRef, {
    title: 'Port Class',
    data: { portClassList : portClassList, checkComponent : checkComponent},
    childComponent: PortClassSetupComponent
    });
  }

  getValuePortClassSetup(e) {
    let tempStr = "";     
    
    if (e.length > 0) {
      for (let i = 0; i < e.length; i++) {
        if (i == e.length - 1) {
          tempStr = tempStr.concat(e[i].portClass + ":" + e[i].includeUnno + ":" + e[i].excludeUnno);
        } else {
          tempStr = tempStr.concat(e[i].portClass + ":" + e[i].includeUnno + ":" + e[i].excludeUnno + "::");
        }
      }
      //#NIIT CR4 >>>>BEGIN
      this.addEditCostRateObj.ijsRateVO['portCode'] = tempStr;
      this.addEditCostRateObj.ijsRateVO['portClassList'] = e;
    } else {
      this.addEditCostRateObj.ijsRateVO['portCode'] = undefined;
      this.addEditCostRateObj.ijsRateVO['imdgCode'] = undefined;
      this.addEditCostRateObj.ijsRateVO['portClassList'] = e;
      //#NIIT CR4 >>>>END
    }
  }  

}
