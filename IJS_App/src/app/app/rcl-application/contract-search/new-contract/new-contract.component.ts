import { Component, OnInit, ViewChild, Input, AfterViewInit ,ChangeDetectorRef, ChangeDetectionStrategy, EventEmitter, Output } from '@angular/core';
import { ContractSearchService } from "../contract-search.service";
declare var UIkit: any;
declare var jQuery: any;
import { SpinnerServiceService } from "../../../common-services/spinner-service.service";
import { ServerErrorcodeService } from "../../../common-services/server-errorcode.service";
import { ContractSearchComponent } from "../contract-search.component";
import { LookUpdataServiceService } from '../../../common-services/look-updata-service.service';
import * as $ from 'jquery';
import { SessionTimeOutService } from "app/common-services/session-time-out.service";
import { delay } from 'rxjs/operator/delay';
import { delayWhen } from 'rxjs/operator/delayWhen';

@Component({
  selector: 'app-new-contract',
  templateUrl: './new-contract.component.html',
  styleUrls: ['./new-contract.component.scss'],
  changeDetection:ChangeDetectionStrategy.OnPush,
  
})
export class NewContractComponent implements OnInit {

  barge:any;
  constructor(private cd: ChangeDetectorRef,
    public spinner: SpinnerServiceService,
    public _newContractService: ContractSearchService,
    public _serverErrorCode: ServerErrorcodeService,
    public _contractSearchComponent: ContractSearchComponent,
    private _lookupData: LookUpdataServiceService,
    private _spinner: SpinnerServiceService,
    private _sessionTimeOutService:SessionTimeOutService) 
    {  
      // this.barge = {
      // bargeValue: "international"
      //   };
  }

  newContractData = {
    contract: {
      locations: [{
        "fromLocType": '',
        "fromLocation": '',
        "fromTerminal": '',
        "paymentFsc": '',
        "toLocType": '',
        "toLocation": '',
        "toTerminal": '',
        "currency": '',
        "priority": '',
        "fromLocationErr": '',
        "paymentFscErr": '',
        "toLocationErr": '',
        "fromTerminalErr":'',
        "toTerminalErr": '',
        "bargeValue":''
        
      }],
    },
  }
  

  @ViewChild('vendorInput') vendorInput: any;
  @ViewChild('lookupAddContract') _lookupAddContract: any;
  @ViewChild('vendorLookup') _vendorLookup: any;
  @ViewChild('rclNewContractTansport') _rclNewContractTansport: any;
  @ViewChild('inputlookupNewContract') _inputlookupNewContract: any;
  @ViewChild('newContactForm') _newContactForm: any;
  @Input() private userType: string;
  @Input() private action: string;
  @Input() private userData: string;
  @Input() private editNewcontractData: any = this.newContractData;






  contractType: string;
  vendorValue: string;
  startDateValue: string;
  endDateValue: string;
  transportModeValue: string;
  statusValue: string;
  paymentFscValue: string;
  currencyValue: string;
  priorityValue: string;
  fromLocTypeValue: string;
  fromLocationValue: string;
  fromTerminalValue: string;
  toLocTypeValue: string;
  toLocationValue: string;
  toTerminalValue: string;
  daysValue: string;
  hoursValue: string;
  distanceValue: string;
  uomValue: string;
  locselectName: string;
  showlookuptable: boolean = true;
  lookupErrorCodetext: string;
  lookupErrorCodeShow: boolean;
  locLookUptableData: any = [];
  _value: string;
  fromOrTo: string;
  public validationError: string;
  looUpOrderBy: any;
  lookupSortIn: any;
  resultsPerPage = 5;
  resultsPerPageArr = [1, 2, 3, 4, 5, 6, 7, 8, 9, 10];
  selectedRow: any;
  errorTextMsg: string;
  currencyCodeLocation: string;
  paymentFscInput: string;


  //config variable for first select dropdown

  termCodesList: any = [];

  successTextMsg: any;
  successtextFlag: boolean = false;
  changePriorityFlag: boolean = false;
  failureTextFlag: boolean = false;
  okFlag: boolean = false;
  contractID: String = "";

  fscLookUpData: any = [{ "label": "FSC Code", "value": "FSC", "seletcted": "FSCCD", "dropDownData": [{ "label": "FSC Code", "value": "FSCCD" }, { "label": "FSC Description", "value": "FSCDESC" }, { "label": "Company Name", "value": "CMPNM" }, { "label": "City", "value": "FSCCITY" }, { "label": "State", "value": "FSCSTAT" }, { "label": "Country", "value": "FSCCCNTY" }, { "label": "Status", "value": "FSCSTS" }] }, { "label": "FSC Description", "value": "FSC", "seletcted": "FSCDESC" }];

  vesselLookUpData: any = [{ "dropDownData": [{ "label": "Vessel Code", "value": "VSVESS" }, { "label": "Vessel Name", "value": "VSLGNM" }, { "label": "Operator Code", "value": "VSOPCD" }] }];

  currencyLookUpData: any = [{ "dropDownData": [{ "label": "Currency Code", "value": "CURRENCY_CODE" }, { "label": "Currency Name", "value": "CURRENCY_NAME" }] }];

  vendorLookUpData: any = [{ "label": "Vendor", "value": "Vendor", "seletcted": "VCVNCD", "dropDownData": [{ "label": "Vendor", "value": "VCVNCD" }, { "label": "Vendor Name", "value": "VCVDNM" }, { "label": "Vendor Type", "value": "VCVDTY" }, { "label": "City", "value": "VCCITY" }, { "label": "Country", "value": "VCCNTY" }, { "label": "State", "value": "VCSTAT" }] }, { "label": "Vendor Name", "value": "Vendor", "seletcted": "VCVDNM" }]

  singleSelectConfig: any = {
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

  multiSelectConfig: any = {
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
 
  selectOptionsForTransportDefault: any =[
    {
      label: 'Truck',
      value: 'Truck',
      code: '01'
    }, {
      label: 'Barge',
      value: 'Barge',
      code: '02'
    }, {
      label: 'Rail',
      value: 'Rail',
      code: '03'
    }, {
      label: 'Feeder',
      value: 'Feeder',
      code: '04'
    }
  ];
  
  selectOptionsForTransport: any = [
    {
      label: 'Truck',
      value: 'Truck',
      code: '01'
    }, {
      label: 'Barge',
      value: 'Barge',
      code: '02'
    }, {
      label: 'Rail',
      value: 'Rail',
      code: '03'
    }, {
      label: 'Feeder',
      value: 'Feeder',
      code: '04'
    },
  ];
  selectOptionsForStatus: any = [];

  selectOptionsForFromLocType: any = [
    {
      label: 'Door',
      value: 'Door',
    }, {
      label: 'Terminal',
      value: 'Terminal',
    }, {
      label: 'Haulage Location',
      value: 'Haulage',
    }, {
      label: 'Depot',
      value: 'Depot',
    },
  ];

  selectOptionsForToLocType: any = [
    {
      label: 'Door',
      value: 'Door',
    }, {
      label: 'Terminal',
      value: 'Terminal',
    }, {
      label: 'Haulage Location',
      value: 'Haulage',
    }, {
      label: 'Depot',
      value: 'Depot',
    },
  ];





  contractId:boolean = false;
  ngOnInit() {  
    
 setInterval(()=>{
        this.cd.markForCheck();
      },200)  
    this.selectOptionsForTransport = [];   
    //this.getTermData(); 
    if (this.action == "New") {  
      this.getTermData();
      // this.newContractData.contract['transMode'] = 'Truck';
      // this.newContractData.contract.locations[0].fromLocType = 'Terminal';
      // this.newContractData.contract.locations[0].toLocType = 'Terminal';     
      this.newContractData.contract['startDate'] = this.getTodaytDate();
      this.newContractData.contract['endDate'] = this.getDateAfter3Months(90);
      //this.newContractData.contract['endDate'] = this.getYearLastDate();
      if (this.userType == 'Global') {
        this.newContractData.contract['status'] = 'Active'
      } else {
        this.newContractData.contract['status'] = 'Entry'
        this.newContractData.contract.locations[0]['paymentFsc'] = this.userData['fscCode'];
      }
      if (this.userData['userAuthType'] != 'HQ') {
        this.newContractData.contract.locations[0]['currency'] = this.userData['fscCurr'];
        this.selectOptionsForStatus = [{ label: 'Entry', value: 'Entry' }, { label: 'Pending', value: 'Pending' }];
      } else {
        this.selectOptionsForStatus = [{ label: 'Active', value: 'Active' }, { label: 'Suspend', value: 'Suspend' }, { label: 'Pending', value: 'Pending' }, { label: 'Entry', value: 'Entry' }
      ,{ label: 'Expired', value: 'Expired' }];
      }
      this.contractType = "New";
      this.newContractData.contract['uom'] = "KM";
    } else if (this.action == "Edit") {
      this.newContractData.contract["vendorName"]= this.editNewcontractData.vendorName;
      this.contractId = true;
      if (this.userData['userAuthType'] != 'HQ') {
        this.selectOptionsForStatus = [{ label: 'Entry', value: 'Entry' }, { label: 'Pending', value: 'Pending' }];
      }
      else if (this.userData['userAuthType'] == 'HQ') {
        this.selectOptionsForStatus = [{ label: 'Active', value: 'Active' }, { label: 'Suspend', value: 'Suspend' }, { label: 'Pending', value: 'Pending' }, { label: 'Entry', value: 'Entry' }
      ,{ label: 'Expired', value: 'Expired' }];
      }
      this.assignEditCopyData();
    } else if (this.action == "Copy") {
      this.newContractData.contract["vendorName"]= this.editNewcontractData.vendorName;
      if (this.userData['userAuthType'] != 'HQ') {
        this.newContractData.contract['currency'] = this.userData['fscCurr'];
        this.selectOptionsForStatus = [{ label: 'Active', value: 'Active' }, { label: 'Pending', value: 'Pending' }];
      } else {
        this.selectOptionsForStatus = [{ label: 'Active', value: 'Active' }, { label: 'Suspend', value: 'Suspend' }, { label: 'Pending', value: 'Pending' }, { label: 'Entry', value: 'Entry' }
      ,{ label: 'Expired', value: 'Expired' }];
      }
      this.assignEditCopyData();
      this.contractType = "New";
    }

    //this.addLocations()
  }

  // to assign the data from copy and Edit functions
  assignEditCopyData() {
    //debugger;
    this.newContractData.contract['vendorCode'] = this.editNewcontractData['vendorCode'];
    if (this.action == 'Edit' || this.action == 'edit' || this.action == "Copy") {
      //this.getTransportMode();
      this.newContractData.contract['contractId'] = this.editNewcontractData['contractId'];
      this.newContractData.contract["EditOnOff"] = true;
      this.spinner.hideSpinner();
    }
    this.newContractData.contract['startDate'] = this.editNewcontractData['startDate'];
    this.newContractData.contract['endDate'] = this.editNewcontractData['endDate'];
    this.newContractData.contract['transMode'] = this.editNewcontractData['transMode'];
    this.newContractData.contract['status'] = this.editNewcontractData['status'];
    this.newContractData.contract['term'] = this.editNewcontractData['term'];
    //this.newContractData.contract['bargeValue'] = this.editNewcontractData["domInn"];
    this.newContractData.contract['costPriority'] = this.editNewcontractData["costPriority"];
    
    //this.newContractData.contract['international'] = this.editNewcontractData['international'];
    // var termString = this.newContractData.contract['term'];
    // this.newContractData.contract['term'] = [];
    // this.newContractData.contract['term'].push(termString);
    this.newContractData.contract.locations[0]['fromLocType'] = this.editNewcontractData['fromLocType'];
    this.newContractData.contract.locations[0]['fromLocation'] = this.editNewcontractData['fromLocation'];
    this.newContractData.contract.locations[0]['fromTerminal'] = this.editNewcontractData['fromTerminal'];
    this.newContractData.contract.locations[0]['paymentFsc'] = this.editNewcontractData['paymentFsc'];
    this.newContractData.contract.locations[0]['toLocType'] = this.editNewcontractData['toLocType'];
    this.newContractData.contract.locations[0]['toLocation'] = this.editNewcontractData['toLocation'];
    this.newContractData.contract.locations[0]['toTerminal'] = this.editNewcontractData['toTerminal'];
    this.newContractData.contract.locations[0]['currency'] = this.editNewcontractData['currency'];
    this.newContractData.contract.locations[0]['priority'] = this.editNewcontractData['priority'];
    this.newContractData.contract.locations[0]['bargeValue'] = this.editNewcontractData["domInn"];
    this.newContractData.contract.locations[0]['days'] = this.editNewcontractData['days'];
    this.newContractData.contract['hours'] = this.editNewcontractData['hours'];
    this.newContractData.contract['distance'] = this.editNewcontractData['distance'];
    this.newContractData.contract['uom'] = this.editNewcontractData['uom'];
    this.newContractData.contract['remarks'] = this.editNewcontractData['remarks'];
    this.newContractData.contract['exempted'] = this.editNewcontractData['exempted'];
    this.newContractData.contract['transModeforEdit'] = this.editNewcontractData['transMode'];
    this.getSelectedContractData(0);//changes for getting selectedContractData
    //this.getfscCodeToLoc(0);//#NIIT CR5 
    
} 
  //changes for getting selectedContractData
  getSelectedContractData(index){
    /*
     *
     * logic for getting transport mode, terms and fsc code in single request
     * plus all the data related to the selected contract
     * 
     */   
     
    //this._spinner.showSpinner();  

    var backendResult = this._newContractService.getData({ action: 'getSelectedContractData', 
              vendorCode: this.newContractData.contract["vendorCode"],
              contract: {
                        locType: "TO_LOC_TYPE",                        
                        fromLocType: this.newContractData.contract.locations[index]['fromLocType'],
                        fromLocation: this.newContractData.contract.locations[index]['fromLocation'],
                        toLocType: this.newContractData.contract.locations[index]['toLocType'],
                        toLocation: this.newContractData.contract.locations[index]['toLocation'],
                        transMode: this.newContractData.contract['transMode']  
      }});
      
      backendResult.subscribe(
        (data) => {
          if(data == "userSessionExpired"){
            this._sessionTimeOutService.checkSessionTimeout(data);
          }
          else if (data.hasOwnProperty("errorCode")) {
            this.spinner.hideSpinner();

            if(data["errorCode"] == "IJS_COMM_SETUP_EX_1001"){
              this.toLocationErrFlag = true;//#NIIT CR5 
              this.newContractData.contract.locations[index]['toLocationErr'] = "false";
              this.newContractData.contract.locations[index]['toLocationErrorTextMsg'] = this._serverErrorCode.checkError("IJS_COMM_SETUP_EX_1001");
              
              //getting the transport mode
              if(data["transportModeList"] !=undefined && data["transportModeList"].length > 0 ){
                for (let i = 0; i < data["transportModeList"].length; i++) {
                this.selectOptionsForTransport.push({ label: data["transportModeList"][i]["label"], value: data["transportModeList"][i]["value"], code: data["transportModeList"][i]["code"] });
                }
              }
              this.newContractData.contract['transMode'] = this.newContractData.contract['transModeforEdit'];
               //getting the term list
              if (data["termList"]) {
              for (let i = 0; i < data["termList"].length; i++) {
                this.termCodesList.push({ label: data["termList"][i]["termCode"], value: data["termList"][i]["termCode"] });
                this.termCodesList.sort((a,b) => (a.value > b.value) ? 1 : ((b.value > a.value) ? -1 : 0)); //to sort the term code list
                }
              }    

           }
             
            else if(data["errorCode"] == "IJS_EX_10047"){
              this.errorTextMsg =  this._serverErrorCode.checkError("IJS_EX_10047");          
              this.errorField = false; //to show error below vendor         
              this.newContractData.contract["vendorName"]=""; 

               //getting the fsc currency
              if (data["fscCurrencyList"]) {               
                this.newContractData.contract.locations[index]['currency'] = data["fscCurrencyList"][0]["currencyCode"];
              } 

              //getting the term list
              if (data["termList"]) {
              for (let i = 0; i < data["termList"].length; i++) {
                this.termCodesList.push({ label: data["termList"][i]["termCode"], value: data["termList"][i]["termCode"] });
                this.termCodesList.sort((a,b) => (a.value > b.value) ? 1 : ((b.value > a.value) ? -1 : 0)); //to sort the term code list
                }
              }  

            } 
            else if(data["errorCode"] == "IJS_TRANS_CURR_ERROR"){
              this.toLocationErrFlag = true;//#NIIT CR5 
              this.newContractData.contract.locations[index]['toLocationErr'] = "false";
              this.newContractData.contract.locations[index]['toLocationErrorTextMsg'] = this._serverErrorCode.checkError("IJS_COMM_SETUP_EX_1001");
              this.errorTextMsg =  this._serverErrorCode.checkError("IJS_EX_10047");          
              this.errorField = false; //to show error below vendor         
              this.newContractData.contract["vendorName"]="";
              
              //getting the term list
              if (data["termList"]) {
              for (let i = 0; i < data["termList"].length; i++) {
                this.termCodesList.push({ label: data["termList"][i]["termCode"], value: data["termList"][i]["termCode"] });
                this.termCodesList.sort((a,b) => (a.value > b.value) ? 1 : ((b.value > a.value) ? -1 : 0)); //to sort the term code list
                }
              }   
          }            
            
          } else {
            
            this.errorField = true;

            //getting the vendor name
            if(data["vendorName"]){          
             this.newContractData.contract["vendorName"]=data["vendorName"];
            }
            //getting the fsc currency
            if (data["fscCurrencyList"]) {             
              this.newContractData.contract.locations[index]['currency'] = data["fscCurrencyList"][0]["currencyCode"];
            }
            //getting the transport mode
            if(data["transportModeList"] !=undefined && data["transportModeList"].length > 0 ){
              for (let i = 0; i < data["transportModeList"].length; i++) {
              this.selectOptionsForTransport.push({ label: data["transportModeList"][i]["label"], value: data["transportModeList"][i]["value"], code: data["transportModeList"][i]["code"] });
              }
            }
            this.newContractData.contract['transMode'] = this.newContractData.contract['transModeforEdit'];
            //  this.newContractData.contract["EditOnOff"]=true;

            //getting the term list
            if (data["termList"]) {
            for (let i = 0; i < data["termList"].length; i++) {
              this.termCodesList.push({ label: data["termList"][i]["termCode"], value: data["termList"][i]["termCode"] });
              this.termCodesList.sort((a,b) => (a.value > b.value) ? 1 : ((b.value > a.value) ? -1 : 0)); //to sort the term code list
              }
			      }            
            this._spinner.hideSpinner();
          }          
        }
      )  
  }



  addLocations() {
    this.newContractData.contract.locations.push(
      {
        "fromLocType": '',
        "fromLocation": '',
        "fromTerminal": '',
        "paymentFsc": '',
        "toLocType": '',
        "toLocation": '',
        "toTerminal": '',
        "currency": '',
        "priority": '',
        "fromLocationErr": '',
        "paymentFscErr": '',
        "toLocationErr": '',
        "fromTerminalErr":'',
        "toTerminalErr": '',
        "bargeValue":''
        
       
      }
    );
   
 

  }
  removeLocations(i) {
    this.newContractData.contract.locations.splice(i, 1);
  }

  
  getTermData() {
    //this.spinner.showSpinner();
    var backendResult = this._newContractService.getData({ "action": "getTermData" });
    backendResult.subscribe(
      (data) => {
        if(data == "userSessionExpired"){
            this._sessionTimeOutService.checkSessionTimeout(data);
        }
        else if (data.hasOwnProperty("errorCode")) {

        } else {
          if (data["termList"]) {
            for (let i = 0; i < data["termList"].length; i++) {
              this.termCodesList.push({ label: data["termList"][i]["termCode"], value: data["termList"][i]["termCode"] });
              this.termCodesList.sort((a,b) => (a.value > b.value) ? 1 : ((b.value > a.value) ? -1 : 0)); //to sort the term code list
            }
          }
        }        
      }
    )
  }

  errorField:boolean= true;
  vendorCodename:boolean= false;
  getTransportMode() {
    this.errorTextMsg=undefined;
    this.spinner.showSpinner();
    let selectOptionsForTransportForEdit = this.selectOptionsForTransport;
   // this.selectOptionsForTransport = [];
    this.newContractData.contract["vendorCode"];
    if(this.selectOptionsForTransport.length > 0 && (this.action == "Edit")){
      
      if(this.newContractData.contract['transModeforEdit'] == this.newContractData.contract['transMode']){
        this.newContractData.contract["EditOnOff"]=false;
      }else{
        this.newContractData.contract["EditOnOff"]=true;
      }
      this.changeTransmode(this);
    } else {
      this.selectOptionsForTransport = [];
      var backendResult = this._newContractService.getData({ action: 'getTransportMode', vendorCode: this.newContractData.contract["vendorCode"] });
    backendResult.subscribe(
      (data) => {
        if(data == "userSessionExpired"){
            this._sessionTimeOutService.checkSessionTimeout(data);
        }
        else if (data.hasOwnProperty("errorCode")) {
          this.spinner.hideSpinner();
          this.errorTextMsg =  this._serverErrorCode.checkError(data["errorCode"]);
          //UIkit.modal('#error-warning-modal').show();
          this.errorField = false;
          //this.vendorCodename= true;
          this.newContractData.contract["vendorName"]="";
          //this.selectOptionsForTransport.push(this.selectOptionsForTransportDefault);
          //this.newContractData.contract['transMode'] = 'Truck';
        } else {
         // alert("Database Call");
          this.errorField = true;
          
          if(data["vendorName"]){          
            this.newContractData.contract["vendorName"]=data["vendorName"];
           }
          if (data["transportModeList"] !=undefined && data["transportModeList"].length > 0 ) {          
            for (let i = 0; i < data["transportModeList"].length; i++) {
              this.selectOptionsForTransport.push({ label: data["transportModeList"][i]["label"], value: data["transportModeList"][i]["value"], code: data["transportModeList"][i]["code"] });
            
            }            
              if(this.action == "New"){
                this.newContractData.contract['transMode'] =  data["transportModeList"][0]["value"];
               this.newContractData.contract["EditOnOff"]=true;
                this.changeTransmode(this);
            } else{
               this.newContractData.contract['transMode'] = this.newContractData.contract['transModeforEdit'];
               this.newContractData.contract["EditOnOff"]=true;
            }
            
           }          
        }

      }, (err) => {
        this.spinner.hideSpinner();
        this.newContractData.contract["vendorName"]="";
      }
     )
    }
  }

  fscErrorTextMsg = "";
  getCurrency(index) {
    if (this.newContractData.contract.locations[index]['paymentFsc']) {
      this._spinner.showSpinner();
      this.newContractData.contract.locations[index]['paymentFscErr'] = "";
      var backendResult = this._newContractService.getData
        ({ action: 'getContractCurrency', paymentFscCode: this.newContractData.contract.locations[index]['paymentFsc'] });
      backendResult.subscribe(
        (data) => {
          if(data == "userSessionExpired"){
            this._sessionTimeOutService.checkSessionTimeout(data);
          }
          else if (data.hasOwnProperty("errorCode")) {
            this.spinner.hideSpinner();
             this.fscErrorTextMsg = this._serverErrorCode.checkError(data["errorCode"]);
             this.newContractData.contract.locations[index]['paymentFscErr'] = "false";            
          } else {        
            if (data["fscCurrencyList"]) {              
              this.newContractData.contract.locations[index]['currency'] = data["fscCurrencyList"][0]["currencyCode"];
            }
            this._spinner.hideSpinner();
          }
          
        }

      )
    }
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


  getYearLastDate() {
    var currYear = new Date().getFullYear();
    return '31/' + '12/' + currYear;
  }

  //to get end date default 3 months after todays start date when new contract screen is opened
  getDateAfter3Months(n){
    var t = new Date();
    t.setDate(t.getDate() + n); 
    var month = "0"+(t.getMonth()+1);
    var date = "0"+t.getDate();
    month = month.slice(-2);
    date = date.slice(-2);
     var date = date +"/"+month +"/"+t.getFullYear();
    return date; 
  }

  

  changeTransmode(e) {
    if(this.newContractData.contract["EditOnOff"] == true){
        if (this.newContractData.contract['transMode'] == 'Barge' || this.newContractData.contract['transMode'] == 'Feeder') {
      this.selectOptionsForFromLocType = [{ label: 'Terminal', value: 'Terminal' }];
      this.selectOptionsForToLocType = [{ label: 'Terminal', value: 'Terminal' }];
       
       for (let i = 0; i < this.newContractData.contract.locations.length; i++)
       {
        this.newContractData.contract.locations[i]['fromLocType'] = 'Terminal';
        this.newContractData.contract.locations[i]['toLocType'] = 'Terminal';
        this.newContractData.contract.locations[i]['fromLocation'] = '';
        this.newContractData.contract.locations[i]['fromTerminal'] = '';

       }      
    } else {
      this.selectOptionsForFromLocType = [{ label: 'Door', value: 'Door', }, { label: 'Terminal', value: 'Terminal' }, { label: 'Haulage Location', value: 'Haulage' }, { label: 'Depot', value: 'Depot' },];
      this.selectOptionsForToLocType = [{ label: 'Door', value: 'Door', }, { label: 'Terminal', value: 'Terminal' }, { label: 'Haulage Location', value: 'Haulage' }, { label: 'Depot', value: 'Depot' },];
      for (let i = 0; i < this.newContractData.contract.locations.length; i++)
       {
         this.newContractData.contract.locations[i]['fromLocType'] = 'Terminal';
         this.newContractData.contract.locations[i]['toLocType'] = 'Terminal';
         this.newContractData.contract.locations[i]['fromLocation'] = '';
         this.newContractData.contract.locations[i]['fromTerminal'] = '';
       }      
     }
    } 
  }

  setVendorName(e) {
    this.newContractData.contract['vendorName'] = e;
    this.errorField = true; 
    this.setTransportMode(e , 'newContract');
  }

  startDate;
  setExpiryDate(e){
    this.startDate = e;
    this.newContractData.contract['endDate'] = this.changedExpiryDate(this.startDate);
  }

   changedExpiryDate(sDate){     
    var newDate = sDate.split('/').reverse().join('-');  
    var dsplit = newDate.split("-");
    var t = new Date(dsplit[0],dsplit[1]-1,dsplit[2]);
    t.setDate(t.getDate() + 90); 
    var month = "0"+(t.getMonth()+1);
    var date = "0"+t.getDate();
    month = month.slice(-2);
    date = date.slice(-2);
     var date = date +"/"+month +"/"+t.getFullYear();
    return date; 
  }

  setTransportMode(e, newContract: string) {   
    if(this.newContractData.contract["vendorCode"] ==''){
      this.newContractData.contract["vendorName"]="";
      this.selectOptionsForTransport={};     
    }    
    if (newContract == 'newContract' && (this.newContractData.contract["vendorCode"]!=undefined && this.newContractData.contract["vendorCode"]!='')){
        this.getTransportMode();    
    }   
      this.spinner.hideSpinner();
  }

  showTransTimeFlag: boolean = false
  showHideTransport(e) {
    if (this.showTransTimeFlag == false) {
      this.showTransTimeFlag = true;
    } else {
      this.showTransTimeFlag = false;
    }
  }

  closeNewContract() {
    this._contractSearchComponent.numberOFRowSelected = 0;  
    if (this.action == 'Edit' || this.action == 'edit' || this.action == 'Copy' || this.action == 'copy') {
      this._contractSearchComponent.refreshTableData();
      this._contractSearchComponent.showNewContractPage = false;
    }
    else {
      this._contractSearchComponent.showNewContractPage = false;
    }
  }
  closeModal() {
    $('html').removeAttr('class');
    $('#success-modal').remove();
   
  }
  saveNewContract(event, action) {
   // console.log(this.newContractData.contract['term']);
   // console.log(this.newContractData.contract['domestic']);
    
   // console.log(this.newContractData.contract['bargeValue']);
    // if(this.newContractData.contract['term']!=undefined){

    // var termString = this.newContractData.contract['term'];
    // this.newContractData.contract['term'] = [];
    // this.newContractData.contract['term'].push(termString);
    // }
   // this.newContractData.contract['bargeValue'];
    this.successTextMsg = undefined;
    var submitFlag: boolean = false;
    //alert(action);
    //alert( this.contractType);
    if (action == 'changepriority') {
      action = this.contractType + action;
      this.newContractData.contract.locations[0]['priority']=this.newContractData.contract.locations[0]['priority']+1;
      $('#success-modal').addClass('uk-open').hide();
      //  alert("Change priority    "+action);
    }
    if (this.validateData(event, submitFlag)) {
      action = action.toLowerCase()
      this.contractType = action;
      if (action == 'copy') {
       // this.newContractData["action"] = 'new';
        this.newContractData["action"] = 'copy';
        //delete this.newContractData.contract['contractId'];
      } else {
        this.newContractData["action"] = action;
      }
        //alert("Save Called");
        this.saveContractData(event);
      }
   else{
     this.dataValidation();
   }
}

  // closeWarning() {
  //  // $('html').removeAttr('class');
  //   //$('#priority-warning-modal').remove();
  //   $('#priority-warning-modal').addClass('uk-open').hide();
  //  // UIkit.modal('#priority-warning-modal').hide();

  // }

  closeValidationWarning(){
    $('#validation-warning-modal').addClass('uk-open').hide();
   // UIkit.modal('#validation-warning-modal').hide();
  }
  public priorityTextMsg: string;
  public errorMessage: string;
  dataValidation() {
    // if(this.validationError == 'PriorityNegative' || this.validationError == 'DaysNegative'){
    //     this.priorityTextMsg = "Priority value/Days/Distance/Hours cannot be 0 or negative and must be numeric.";
    // }
    // else{
    //   this.priorityTextMsg = "Please Enter Correct Data";
    // }
    if(this.validationError != ""){
      this.errorMessage = this.validationError;
      $('#validation-warning-modal').addClass('uk-open').show();
     // UIkit.modal('#validation-warning-modal').show();
    }
    //UIkit.modal('#priority-warning-modal').show();
  }

  validateData(event: any, submitFlag: boolean) {
    this.validationError="";
    if(this.errorTextMsg!=undefined || this.errorTextMsg!=null){
      return false;
    }
    if (this.newContractData.contract["vendorCode"] == undefined) {
      this.validationError = 'Please enter valid Vendor';
      return false;
    }
    else {
      submitFlag = true;
    }
    if (this.newContractData.contract["startDate"] == undefined) {
      this.validationError = 'Please enter the Start Date';
      return false;
    }
    else {
      submitFlag = true;
    }
    if (this.newContractData.contract["endDate"] == undefined) {
      this.validationError = 'Please enter the End Date';
      return false;
    }
    else {
      submitFlag = true;
    }
    if (this.newContractData.contract["transMode"] == undefined) {
      this.validationError = 'Please Enter the Transport Mode';
      return false;
    }
    else {
      submitFlag = true;
    }
    // not required to validate since it is not a mandatory field
    // if (this.newContractData.contract["status"] == undefined) {
    //   this.validationError = 'status';
    //   return false;
    // }
    // else {
    //   submitFlag = true;
    // }
    // if (this.newContractData.contract["term"] == undefined) {
    //   this.validationError = 'term';
    //   return false;
    // }
    // else {
    //   submitFlag = true;
    // }
    for (let i = 0; i < this.newContractData.contract.locations.length; i++){
      if (this.newContractData.contract.locations[i]["fromLocType"] == undefined) {
      this.validationError = 'Please enter the From Location Type';
      return false;
    }
    else {
      submitFlag = true;
    }
    if (this.newContractData.contract.locations[i]["fromLocation"] == undefined ||
        this.newContractData.contract.locations[i]['fromLocationErr'] == "false") {
        this.validationError = 'Please enter the From Location';
        return false;
    }
    else {
      submitFlag = true;
    }
    if (this.newContractData.contract.locations[i]["fromLocation"] != undefined
      && this.newContractData.contract.locations[i]["fromTerminal"] == undefined
      && this.newContractData.contract.locations[i]["fromLocType"] != 'Door') {
      this.validationError = 'Please enter the From Terminal>>>>';
      return false;
    }
    else {
      submitFlag = true;
    }
    if (this.newContractData.contract.locations[i]["paymentFsc"] == undefined) {
      this.validationError = 'Please enter the Payment FSC';
      return false;
    }
    else {
      submitFlag = true;
    }
    if (this.newContractData.contract.locations[i]["currency"] == undefined) {
      this.validationError = 'Please enter the Currency';
      return false;
    }
    else {
      submitFlag = true;
    }
    if (this.newContractData.contract.locations[i]["priority"] == '') {
      this.newContractData.contract.locations[i]["priority"] ='0';
       submitFlag = true;
    }
    //debugger;
    if ((this.newContractData.contract.locations[i]['bargeValue']==undefined||this.newContractData.contract.locations[i]['bargeValue']=="")&& (this.newContractData.contract['transMode']== 'Barge')) {
      this.validationError = 'Please Select One Barge Value';
      return false;
    }
    else {
      submitFlag = true;
    }
     
    /*if((this.newContractData.contract.locations[i]['priority'] <= '0') ||
      (this.newContractData.contract.locations[i]['priority'] == '00') ||
      (this.newContractData.contract.locations[i]['priority'] == '0-')) {
       this.validationError = 'Please enter a positive Priority';
       return false;
      }
      else {
        submitFlag = true;
      } */

    if (this.newContractData.contract.locations[i]["toLocType"] == undefined) {
      this.validationError = 'Please enter the To Location Type';
      return false;
    }
    else {
      submitFlag = true;
    }
    if (this.newContractData.contract.locations[i]["toLocation"] == undefined) {
      this.validationError = 'Please enter the To Location';
      return false;
    }
    else {
      submitFlag = true;
    }
    if (this.newContractData.contract.locations[i]["toLocation"] != undefined
      && this.newContractData.contract.locations[i]["toLocType"] != 'Door'
      && this.newContractData.contract.locations[i]["toTerminal"] == undefined) {
      this.validationError = 'Please enter the To Terminal>>>>>>>';
      return false;
    }
    else {
      submitFlag = true;
    }
    if (this.newContractData.contract.locations[i]["fromLocationErr"] == "false") {
      this.validationError = 'Please enter the From TRM/DPT/H.LOC';
      return false;
    }
    else {
      submitFlag = true;
    }

    if (this.newContractData.contract.locations[i]["paymentFscErr"] == "false") {
      this.validationError = 'Please enter From Payment FSC';
      return false;
    }
    else {
      submitFlag = true;
    }
    if (this.newContractData.contract.locations[i]["fromTerminalErr"] == "false") {
      this.validationError = 'Please enter the From TRM/DPT/H.LOC';
      return false;
    }
    else {
      submitFlag = true;
    }

    if (this.newContractData.contract.locations[i]["toTerminalErr"] == "false") {
      this.validationError = 'Please enter the To TRM/DPT/H.LOC';
      return false;
    }
    else {
      submitFlag = true;
    }

    if (this.newContractData.contract.locations[i]["toLocationErr"] == "false") {
      this.validationError = 'Please Enter the To Location';
      return false;
    }
    else {
      submitFlag = true;
    }

   } 
  //  if((this.newContractData.contract['days'] < '0') ||
  //     (this.newContractData.contract['days'] == '00') ||
  //     (this.newContractData.contract['days'] == '0-') ||
  //     (this.newContractData.contract['distance'] < '0') ||
  //     (this.newContractData.contract['distance'] == '00') ||
  //     (this.newContractData.contract['distance'] == '0-') ||
  //     (this.newContractData.contract['hours'] < '0') ||
  //     (this.newContractData.contract['hours'] == '00') ||
  //     (this.newContractData.contract['hours'] == '0-')) {
        
        
  //       this.validationError = 'Transport Time Negative';
  //       return false;
  //       //  this.priorityValidate(this, event);
  //  } 
  if((this.newContractData.contract['days'] < '0') ||
      (this.newContractData.contract['days'] == '0-') ||
      (this.newContractData.contract['distance'] < '0') ||
      (this.newContractData.contract['distance'] == '0-') ||
      (this.newContractData.contract['hours'] < '0') ||
      (this.newContractData.contract['hours'] == '0-')) {        
        this.validationError = 'Please Enter a positive Transport Time';
        return false;
        //  this.priorityValidate(this, event);
   }
   else {
     submitFlag = true;
     return submitFlag; 
   }  
}


  //Change this method with addRecord to DB
  getBackEndData() {
    this.spinner.showSpinner();
    delay;
    var backendData = this._newContractService.getData(this.newContractData);
    backendData.subscribe(
      (data) => {
        if(data == "userSessionExpired"){
            this._sessionTimeOutService.checkSessionTimeout(data);
        }
        else if (data.hasOwnProperty("errorCode")) {
          if (data["errorCode"] == 'IJS_MSG_1002') {
            window.dispatchEvent(new Event('resize')); 
            this.successTextMsg = "Contract Successfully Updated"
            this.successtextFlag = true;
          } else {
            this.successTextMsg = this._serverErrorCode.checkError(data["errorCode"]);
            this.successtextFlag = false;
          }
          this.lookupErrorCodeShow = true;
          this._contractSearchComponent.showNewContractPage = true;
        }
        this.spinner.hideSpinner();
        window.dispatchEvent(new Event('resize')); 
        $('#success-modal').addClass('uk-open').show();
       // UIkit.modal('#success-modal').show();
      }
    )
  }
  contractSuccessObj: any;
  messageList: any = [];
  contractIds: string;
  saveContractData(event: any) {    
    this.successTextMsg = undefined;
     this.contractIds =undefined;
    this.spinner.showSpinner();
    //condition to disable save when location user edit a contract with pending status
    // if(this.editNewcontractData.status=="Entry" && this.userType == "modifyOnly"){
    //   this.newContractData.contract['locationUserSave']="canSave";
    // }
    // if(this.editNewcontractData.status=="Pending" && this.userType == "modifyOnly"){
    //   this.newContractData.contract['locationUserSave']="canNotSave";
    // }
    // debugger;
    //to change status to Entry when logged in via location user and status is Active or Pending
    if(this.userType == "modifyOnly" && (this.newContractData.contract['status']=="Active"|| this.newContractData.contract['status']=="Suspend")){
      this.newContractData.contract['status']="Entry";
    }if(this.joDate!=null || this.joDate!=undefined){
    this.newContractData.contract['startDate']=this.joDate;
    this.newContractData.contract['joDate']=this.joDate;
    }else{
      this.newContractData.contract['joDate']="";
    }
   // debugger;
 
    var backendResult = this._newContractService.saveContractData(this.newContractData);
    if(this.newContractData["action"] == 'copy'){
      delete this.newContractData.contract['contractId'];
    }
    delay;
    backendResult.subscribe(
      (data) => {
        
        this.successtextFlag = true;
        this.failureTextFlag = true;
        this.changePriorityFlag = true;
        this.okFlag = true;
        if(data == "userSessionExpired"){
            this._sessionTimeOutService.checkSessionTimeout(data);
        }
        else if (data.hasOwnProperty("errorCode")) {
          if (data["errorCode"] == 'IJS_MSG_1002') {
            this.successTextMsg = "Contract Successfully Updated"
            this.successtextFlag = false;
            this.contractID = data["contract"].contractId;
            this.cd.markForCheck(); // marks path
          }
          else if (data["errorCode"] == 'IJS_EX_10007') {
            this.successTextMsg = this._serverErrorCode.checkError(data["errorCode"]);
            this.okFlag = false;
            this.changePriorityFlag = false;
            this.cd.markForCheck(); // marks path
          }
          else if (data["errorCode"] == 'IJS_EX_10006' || data["errorCode"] == 'IJS_EX_10008'
            || data["errorCode"] == 'IJS_EX_10044' || data["errorCode"] == 'IJS_EX_10026'
            || data["errorCode"] == 'IJS_EX_10045' || data["errorCode"] == 'IJS_EX_10046'
            || data["errorCode"] == 'IJS_CNTR_EX_10031'
            || data["errorCode"] == 'IJS_CNTR_EX_10032'
            || data["errorCode"] == 'IJS_CNTR_JO_EX_1001'
            || data["errorCode"] == 'IJS_CNTR_EX_99999') {
            this.successTextMsg = this._serverErrorCode.checkError(data["errorCode"]);
            this.okFlag = false;
            this.cd.markForCheck(); // marks path
          }          
          this._contractSearchComponent.showNewContractPage = true;
        }

        else if (data.hasOwnProperty("msgSuccessVO") || data.hasOwnProperty("msgDupRoute")) {

          if (data.hasOwnProperty("msgSuccessVO")) {
            this.successTextMsg = this._serverErrorCode.checkError("IJS_MSG_1001");
            this.successtextFlag = false;
            this.contractSuccessObj = data["msgSuccessVO"];
            this.messageList = this.contractSuccessObj.messageList;
            for (var i = 0; i < this.messageList.length; i++) {
              if (this.contractIds == undefined) {
                this.contractIds = this.messageList[i].contractId;
              }
              else
                this.contractIds = this.contractIds + ", " + this.messageList[i].contractId;
            }
            this.successTextMsg = this.successTextMsg + " :  " + this.contractIds;
            this.cd.markForCheck(); // marks path
          }
          if (data.hasOwnProperty("msgDupRoute")) {
            this.successTextMsg = this._serverErrorCode.checkError("IJS_EX_10006");
            this.cd.markForCheck(); // marks path
            //this.successtextFlag = false;
            if (this.successtextFlag) {
              this.okFlag = false;
            }
          }
          this.lookupErrorCodeShow = true;
          this._contractSearchComponent.showNewContractPage = true;
        }
        this.spinner.hideSpinner();
        this.cd.markForCheck(); // marks path
        //debugger;
        $('#success-modal').addClass('uk-open').show();
       // UIkit.modal('#success-modal').show();
        
      }
    )
  }

  cuurentLookupIndex: number
  openFromLookup(selectedValue, fromOrTo, i) {
    this.fromOrTo = fromOrTo;
    this.cuurentLookupIndex = i;
    this._lookupAddContract.openFromLookup(selectedValue, fromOrTo);
  }

  openToLookup(selectedValue, fromOrTo, i) {
    this.fromOrTo = fromOrTo;
    this.cuurentLookupIndex = i;
    this._lookupAddContract.openToLookup(selectedValue, fromOrTo);


  }


  //fill the data of code when row selected
  rowSelected(e: any) {
    //to reset save button disable flag
    this.toLocationErrFlag = false;//#NIIT CR5 
    var type: string = e[1];
    var event: any = e[0];
    if (type == "Door") {
      if (this.fromOrTo == "to") {
        this.newContractData.contract.locations[this.cuurentLookupIndex]["toLocation"] = event.target.parentElement.children[1].textContent;
        if(event.target.parentElement.children[1].textContent){
          this.newContractData.contract.locations[this.cuurentLookupIndex]["toLocationErr"]="";
        }
        //#NIIT CR5
        this.getfscCodeToLoc(this.cuurentLookupIndex); 
      }
      else {
        this.newContractData.contract.locations[this.cuurentLookupIndex]["fromLocation"] = event.target.parentElement.children[1].textContent;
        this.newContractData.contract.locations[this.cuurentLookupIndex]['paymentFsc'] = event.target.parentElement.children[8].textContent;
        this.newContractData.contract.locations[this.cuurentLookupIndex]['currency'] = event.target.parentElement.children[9].textContent;
        if(event.target.parentElement.children[1].textContent){
          this.newContractData.contract.locations[this.cuurentLookupIndex]["fromLocationErr"]="";
        }
        if(event.target.parentElement.children[8].textContent){
          this.newContractData.contract.locations[this.cuurentLookupIndex]["paymentFscErr"]="";
        }
        //#NIIT CR5
        this.getfscCodeToLoc(this.cuurentLookupIndex);
      }
    }
    else if (type == "Terminal") {
      if (this.fromOrTo == "to") {
        this.newContractData.contract.locations[this.cuurentLookupIndex]["toLocation"] = event.target.parentElement.children[3].textContent;
        this.newContractData.contract.locations[this.cuurentLookupIndex]["toTerminal"] = event.target.parentElement.children[1].textContent;
        if(event.target.parentElement.children[3].textContent){
          this.newContractData.contract.locations[this.cuurentLookupIndex]["toLocationErr"]="";
        }
        if(event.target.parentElement.children[1].textContent){
          this.newContractData.contract.locations[this.cuurentLookupIndex]["toTerminalErr"]="";
        }
        //#NIIT CR5
        this.getfscCodeToLoc(this.cuurentLookupIndex); 
      }
      else {
        this.newContractData.contract.locations[this.cuurentLookupIndex]["fromLocation"] = event.target.parentElement.children[3].textContent;
        this.newContractData.contract.locations[this.cuurentLookupIndex]["fromTerminal"] = event.target.parentElement.children[1].textContent;
        this.newContractData.contract.locations[this.cuurentLookupIndex]['paymentFsc'] = event.target.parentElement.children[4].textContent;
        this.newContractData.contract.locations[this.cuurentLookupIndex]['currency'] = event.target.parentElement.children[7].textContent;
        if(event.target.parentElement.children[3].textContent){
          this.newContractData.contract.locations[this.cuurentLookupIndex]["fromLocationErr"]="";
        }
        if(event.target.parentElement.children[1].textContent){
          this.newContractData.contract.locations[this.cuurentLookupIndex]["fromTerminalErr"]="";
        }
        if(event.target.parentElement.children[2].textContent){
          this.newContractData.contract.locations[this.cuurentLookupIndex]["paymentFscErr"]="";
        }
        //#NIIT CR5
        this.getfscCodeToLoc(this.cuurentLookupIndex);
      }
    }
    else if (type == "Depot") {
      if (this.fromOrTo == "to") {
        this.newContractData.contract.locations[this.cuurentLookupIndex]["toTerminal"] = event.target.parentElement.children[1].textContent;
        this.newContractData.contract.locations[this.cuurentLookupIndex]["toLocation"] = event.target.parentElement.children[5].textContent;
        if(event.target.parentElement.children[1].textContent){
          this.newContractData.contract.locations[this.cuurentLookupIndex]["toTerminalErr"]="";
        }
        if(event.target.parentElement.children[5].textContent){
          this.newContractData.contract.locations[this.cuurentLookupIndex]["toLocationErr"]="";
        }
        //#NIIT CR5
        this.getfscCodeToLoc(this.cuurentLookupIndex); 
      }
      else {
        this.newContractData.contract.locations[this.cuurentLookupIndex]["fromTerminal"] = event.target.parentElement.children[1].textContent;
        this.newContractData.contract.locations[this.cuurentLookupIndex]["fromLocation"] = event.target.parentElement.children[5].textContent;
        this.newContractData.contract.locations[this.cuurentLookupIndex]['paymentFsc'] = event.target.parentElement.children[4].textContent;
        this.newContractData.contract.locations[this.cuurentLookupIndex]['currency'] = event.target.parentElement.children[8].textContent;
        if(event.target.parentElement.children[1].textContent){
          this.newContractData.contract.locations[this.cuurentLookupIndex]["fromTerminalErr"]="";          
        }
        if(event.target.parentElement.children[5].textContent){
          this.newContractData.contract.locations[this.cuurentLookupIndex]["fromLocationErr"]="";
        }
        if(event.target.parentElement.children[4].textContent){
          this.newContractData.contract.locations[this.cuurentLookupIndex]["paymentFscErr"]="";
        }
        //#NIIT CR5
        this.getfscCodeToLoc(this.cuurentLookupIndex);
      }
    }
    else if (type == "Haulage") {
      if (this.fromOrTo == "to") {
        this.newContractData.contract.locations[this.cuurentLookupIndex]["toTerminal"] = event.target.parentElement.children[1].textContent;
        this.newContractData.contract.locations[this.cuurentLookupIndex]["toLocation"] = event.target.parentElement.children[2].textContent;
        if(event.target.parentElement.children[1].textContent){
          this.newContractData.contract.locations[this.cuurentLookupIndex]["toTerminalErr"]="";
        }
        if(event.target.parentElement.children[2].textContent){
          this.newContractData.contract.locations[this.cuurentLookupIndex]["toLocationErr"]="";
        }
        //#NIIT CR5
        this.getfscCodeToLoc(this.cuurentLookupIndex); 
      }
      else {
        this.newContractData.contract.locations[this.cuurentLookupIndex]["fromTerminal"] = event.target.parentElement.children[1].textContent;
        this.newContractData.contract.locations[this.cuurentLookupIndex]["fromLocation"] = event.target.parentElement.children[2].textContent;
        this.newContractData.contract.locations[this.cuurentLookupIndex]['paymentFsc'] = event.target.parentElement.children[6].textContent;
        this.newContractData.contract.locations[this.cuurentLookupIndex]['currency'] = event.target.parentElement.children[7].textContent;
        if(event.target.parentElement.children[1].textContent){
          this.newContractData.contract.locations[this.cuurentLookupIndex]["fromTerminalErr"]="";          
        }
        if(event.target.parentElement.children[2].textContent){
          this.newContractData.contract.locations[this.cuurentLookupIndex]["fromLocationErr"]="";
        }
        if(event.target.parentElement.children[6].textContent){
          this.newContractData.contract.locations[this.cuurentLookupIndex]["paymentFscErr"]="";
        }
        //#NIIT CR5
        this.getfscCodeToLoc(this.cuurentLookupIndex);
      }
    }

    //jQuery("#contractfsclookup").focus();
  }

  openVendorLookUp() {
    this._vendorLookup.openLookUpModal();
  }

  searchContract(event) {
    //debugger;
    if (this.contractType == 'Edit' || this.contractType == 'edit' ||
      this.contractType == 'editchangepriority') {
      this._contractSearchComponent.refreshTableData();
      this._contractSearchComponent.showNewContractPage = false;
      this._contractSearchComponent.searchJobOrder(event);
    }
    else {
      this._contractSearchComponent.simpleContSearchData = {
        contractParam: {
          searchScreenParam: {}
        },
        contract: {},
        action: "search"
      };
      // this._contractSearchComponent.simpleContSearchData.contractParam.searchScreenParam.contractNumber = this.contractID;
      this._contractSearchComponent.simpleContSearchData.contractParam.searchScreenParam.contractNumber = this.contractIds;
      //this._contractSearchComponent.simpleContSearchData.contractParam.searchScreenParam['status']="All";
      //alert(this.contractIds);
      
      $('html').removeAttr('class');
      $('#success-modal').remove();
      this._contractSearchComponent.showNewContractPage = false;
      this._contractSearchComponent.searchJobOrder(event);
    }
  }



 
  locationErrorTextMsg: string;
  getfscCode(index){
    if(!this.newContractData.contract.locations[index]['fromLocation']){
      this.newContractData.contract.locations[index]['paymentFsc']=undefined;
      this.newContractData.contract.locations[index]['currency']=undefined;
    }
    if (this.newContractData.contract.locations[index]['fromLocation'] &&
      this.newContractData.contract.locations[index]['fromLocType'])
    {
      this.newContractData.contract.locations[index]['fromLocationErr'] = "";
      this._spinner.showSpinner();
      var backendResult = this._newContractService.getData
      ({ action: 'getfscCurrencyCode', contract: {locType: "FROM_LOC_TYPE",
                                       fromLocType: this.newContractData.contract.locations[index]['fromLocType'],
                                       fromLocation: this.newContractData.contract.locations[index]['fromLocation'],
                                       toLocType: this.newContractData.contract.locations[index]['toLocType'],
                                       toLocation: this.newContractData.contract.locations[index]['toLocation'],
                                       transMode: this.newContractData.contract['transMode']  }});
      backendResult.subscribe(
               (data) => {
                 if(data == "userSessionExpired"){
                    this._sessionTimeOutService.checkSessionTimeout(data);
                 }
                 else if (data.hasOwnProperty("errorCode")) {
                   
                 } else {
                   if (data["fscCurrencyCode"]) {
                     this.newContractData.contract.locations[index]["paymentFsc"] = data["fscCode"];
                     }
                    if (data["fscCode"]) {
                     this.newContractData.contract.locations[index]['currency'] = data["fscCurrencyCode"];
                    } 
                    
                    if (!data["fscCurrencyCode"] && !data["fscCode"]) { 
                      this.newContractData.contract.locations[index]['fromLocationErr'] = "false";
                      //#NIIT CR5
                      this.locationErrorTextMsg = "Invalid Location";
                      this.newContractData.contract.locations[index]['paymentFsc']=undefined;
                      this.newContractData.contract.locations[index]['currency']=undefined;
                      
                    }
                 }
                 this._spinner.hideSpinner();
     })
     
  }
}

toLocationErrorTextMsg: string;
toLocationErrFlag:boolean = false;//#NIIT CR5 
getfscCodeToLoc(index){
    this.toLocationErrFlag = false;//#NIIT CR5 
    if (this.newContractData.contract.locations[index]['toLocation'] &&
      this.newContractData.contract.locations[index]['toLocType'])
    {
      this.newContractData.contract.locations[index]['toLocationErr'] = "";
      this.newContractData.contract.locations[index]['toLocationErrorTextMsg']="";
      this._spinner.showSpinner();
      var backendResult = this._newContractService.getData
      ({ action: 'getfscCurrencyCode', contract: {locType: "TO_LOC_TYPE",
                                       fromLocType: this.newContractData.contract.locations[index]['fromLocType'],
                                       fromLocation: this.newContractData.contract.locations[index]['fromLocation'],
                                       toLocType: this.newContractData.contract.locations[index]['toLocType'],
                                       toLocation: this.newContractData.contract.locations[index]['toLocation'],
                                       transMode: this.newContractData.contract['transMode']  }});
      backendResult.subscribe(
               (data) => {
                 if(data == "userSessionExpired"){
                      this._sessionTimeOutService.checkSessionTimeout(data);
                  }
                 else if (data.hasOwnProperty("errorCode")) {
                    this.toLocationErrFlag = true;//#NIIT CR5 
                    this.newContractData.contract.locations[index]['toLocationErr'] = "false";
                    this.newContractData.contract.locations[index]['toLocationErrorTextMsg'] = this._serverErrorCode.checkError("IJS_COMM_SETUP_EX_1001"); 
                   
                 } else {
                    if (!data["fscCode"]) { 
                      this.newContractData.contract.locations[index]['toLocationErr'] = "false";
                      //#NIIT CR5
                      this.newContractData.contract.locations[index]['toLocationErrorTextMsg'] = "Invalid Location";
                      }
                 }
                 this._spinner.hideSpinner();
              })     
            }
}

getCurrencyCode() {
  if (this.paymentFscInput) {
    this._spinner.showSpinner();
    this.currencyCodeLocation="";
    var backendResult = this._newContractService.getData
      ({ action: 'getContractCurrency', paymentFscCode: this.paymentFscInput });
    backendResult.subscribe(
      (data) => {
        if(data == "userSessionExpired"){
            this._sessionTimeOutService.checkSessionTimeout(data);
        }
        else if (data.hasOwnProperty("errorCode")) {
          
        } else {
          if (data["fscCurrencyList"]) {            
            this.currencyCodeLocation = data["fscCurrencyList"][0]["currencyCode"];
          }         
        }
        this.paymentFscInput="";
      }
    )
    this._spinner.hideSpinner();
  }
}
changepriority(priority)
{
  //this.editNewcontractData.priority
 // alert(this.editNewcontractData.priority);
if(this.newContractData.contract.locations[0].priority >this.editNewcontractData.priority+1)
{
  this.newContractData.contract.locations[0].priority=this.newContractData.contract.locations[0].priority+1;
}else {
  this.newContractData.contract.locations[0].priority=this.editNewcontractData.priority+1;
}


}

pickUpDropDownChange(e, index, obj) {
   this.newContractData.contract.locations[index]['fromLocation'] = undefined;
   this.newContractData.contract.locations[index]['fromTerminal'] = undefined;  
   this.newContractData.contract.locations[index]['paymentFsc'] = undefined;
   this.newContractData.contract.locations[index]['currency'] = undefined; 
}

dropOffDropDownChange(e, index) {
 
   this.newContractData.contract.locations[index]['toLocation'] = undefined;
   this.newContractData.contract.locations[index]['toTerminal'] = undefined;  
  }

  fromTerminalErrorTextMsg: string;

  validateFromTerminal(index){
    if (this.newContractData.contract.locations[index]['fromLocation'] &&
      this.newContractData.contract.locations[index]['fromLocType'])
    {
      this.newContractData.contract.locations[index]['fromTerminalErr'] = "";
      this.fromTerminalErrorTextMsg="";
      this._spinner.showSpinner();
      var backendResult = this._newContractService.getData
      ({ action: 'validateTerminal', location: this.newContractData.contract.locations[index]['fromLocation'],
                                     locationType: this.newContractData.contract.locations[index]['fromLocType'],
                                     terminal: this.newContractData.contract.locations[index]['fromTerminal']});
      backendResult.subscribe(
               (data) => {
                 if(data == "userSessionExpired"){
                    this._sessionTimeOutService.checkSessionTimeout(data);
                  }
                 else if (data.hasOwnProperty("errorCode")) {
                  this.newContractData.contract.locations[index]['fromTerminalErr'] = "false";
                  this.fromTerminalErrorTextMsg = this._serverErrorCode.checkError("IJS_EX_10049");
                 } else {
                    if (data["terminalValid"]) { 
                      
                      }
                 }
                 this._spinner.hideSpinner();
     })
     
  }
  }
  dateValidate(e)
  {
        
    var backendData = this._newContractService.validateDate(this.newContractData.contract['contractId']);
    backendData.subscribe(
      (data) => {
  if(data.length!=0){
        this.joDate = data[0].endDate;
  }
    })
  }
joDate:any;
  toTerminalErrorTextMsg: string;
  validateToTerminal(index){
    if (this.newContractData.contract.locations[index]['toLocation'] &&
      this.newContractData.contract.locations[index]['toLocType'])
    {
      this.newContractData.contract.locations[index]['toTerminalErr'] = "";
      this.toTerminalErrorTextMsg="";
      this._spinner.showSpinner();
      var backendResult = this._newContractService.getData
      ({ action: 'validateTerminal', location: this.newContractData.contract.locations[index]['toLocation'],
                                     locationType: this.newContractData.contract.locations[index]['toLocType'],
                                     terminal: this.newContractData.contract.locations[index]['toTerminal']});
      backendResult.subscribe(
               (data) => {
                 if(data == "userSessionExpired"){
                      this._sessionTimeOutService.checkSessionTimeout(data);
                  }
                 else if (data.hasOwnProperty("errorCode")) {
                  this.newContractData.contract.locations[index]['toTerminalErr'] = "false";
                  this.toTerminalErrorTextMsg = this._serverErrorCode.checkError("IJS_EX_10049");;
                 } else {
                    if (data["terminalValid"]) { 
                      
                      }
                 }
                 this._spinner.hideSpinner();
     })
  }
 }
}
