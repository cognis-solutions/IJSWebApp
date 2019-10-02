import { Component, OnInit, Output, Input, EventEmitter  } from '@angular/core';
import { DomSanitizer, SafeUrl } from '@angular/platform-browser';
import * as FileSaver from 'file-saver';
import * as $ from 'jquery';
import { SpinnerServiceService } from "app/common-services/spinner-service.service";
import { FileUploadService } from "app/common-services/file-upload.service";
import { DownloadFileService } from "app/common-services/download-file.service";
import { RclappUrlService } from "app/common-services/rclapp-url.service";
import { ServerErrorcodeService } from "app/common-services/server-errorcode.service";
import { SessionTimeOutService } from "app/common-services/session-time-out.service";
declare var jQuery: any;

@Component({
  selector: 'app-jo-maintenance-excel-upload',
  templateUrl: './jo-maintenance-excel-upload.component.html',
  styleUrls: ['./jo-maintenance-excel-upload.component.scss']
})
export class JoMaintenanceExcelUploadComponent implements OnInit {

  fileSelected: File = new File([], "");
    progressValue: number = 0;
    errorMsg: string;
    successMsg: string;
    dataFilePath: string;
    @Output() public closeFileUploader = new EventEmitter();
    @Input() private addHocType: any;
    @Input() private selectedRouteContract: any; //contract id 
    


    constructor(private _fileUploadService: FileUploadService, private _downloadFileService: DownloadFileService, private _sanitizer: DomSanitizer, private _rclappUrlService: RclappUrlService,  public _serverErrorCode: ServerErrorcodeService,private _spinner: SpinnerServiceService,private _sessionTimeOutService:SessionTimeOutService ) {
      this._fileUploadService.progress$.subscribe(
        data => {
          this.progressValue = data;
          console.log('progress = ' + data);
        });
    }
  
  
  
    //When file enter the dragzone
    fileOnDragOver(e) {
      e.stopPropagation();
      e.preventDefault();
      if (e.target.id == 'dropZone') {
        e.target.classList.add("ondragover");
      } else {
        e.target.closest('#dropZone').classList.add("ondragover");
      }
      console.log("dragover", e);
    }
  
    //When file is dragging on dragzone
    fileOnDrag(e) {
      e.stopPropagation();
      e.preventDefault();
      if (e.target.id == 'dropZone') {
        e.target.classList.add("ondragover");
      } else {
        e.target.closest('#dropZone').classList.add("ondragover");
      }
      console.log("dragover", e);
    }
  
    //When file leave the dragzone
    fileOndragLeave(e) {
      e.stopPropagation();
      e.preventDefault();
  
      if (e.target.id == 'dropZone') {
        e.target.classList.remove("ondragover");
      } else {
        e.target.closest('#dropZone').classList.remove("ondragover");
      }
      console.log("drag leave", e);
    }
  
    fileOnDrop(e) {
      e.stopPropagation();
      e.preventDefault();
      if (e.target.id == 'dropZone') {
        e.target.closest('#dropZone').classList.remove("ondragover");
        e.target.classList.add("ondrop");
      } else {
        e.target.closest('#dropZone').classList.remove("ondragover");
        e.target.closest('#dropZone').classList.add("ondrop");
      }
      
      this.fileSelected = e.dataTransfer.files[0];
      this.progressValue = 0;
      this.successMsg = "";
      this.errorMsg = "";
      this.dataFilePath = "";
      
      setTimeout(function () {
        e.target.closest('#dropZone').classList.remove("ondrop");
      }, 300);
  
    }
  
    fileOnSelect(e) {
      this.progressValue = 0;
      this.successMsg = "";
      this.errorMsg = "";
      this.dataFilePath = "";
      this.fileSelected = e.target.files[0];
    }
  
    uploadFile(e) {
      if (this.validateFile()) {
        this.errorMsg = "";
        let formData: FormData = new FormData();
        formData.append('file', this.fileSelected, this.fileSelected.name);
        let backresp = this._fileUploadService.makeFileRequest(formData);
        backresp.subscribe(
          (data) => {
            if(data == "userSessionExpired"){
              this._sessionTimeOutService.checkSessionTimeout(data);
            }
            else if(data.uploadVO.isFailed == false){            
              this.successMsg = "Uploaded successfully"; 
              this.dataFilePath = data.uploadVO.fileName;
             }
            else {
              this.errorMsg = "Uploading Failed";
            }
            console.log(data);          
          }
        )
      } else {
        this.errorMsg = "Please select a valid file"
      }
    }
  
    validateFile() {
      var ext = this.fileSelected.name.split('.');
      if (ext[1] == "xls") {
        return true;
      } else {      
        return false;
      }
    }
    validateSuccessMsg: boolean;
    validateErrorMsg: boolean;
    errorCode: String;
    validateErrorMsgText: String;
    validContainerResult: any;
    failedContainerResult:any = [];
    saveUploadedFile(e) {      
      let addhoc ;
      if(this.addHocType == 'Ad-Hoc Empty')
        {
          addhoc = 'E'
        } else if (this.addHocType == 'Ad-Hoc Laden') {
          addhoc = 'L'
        }
        //addhoc = 'L';  

      let saveobj = {
        uploadVO: {
          action: 'upload',
          fileName: this.dataFilePath,
          addhocType: addhoc,
          contractId: this.selectedRouteContract
        }      
      }
      this._spinner.showSpinner();
      this._fileUploadService.saveContainerXlsFile(saveobj).subscribe(
          (data) => {
            console.log(data);
            this.errorCode=data.errorCode;
            if(data == "userSessionExpired"){
              this._sessionTimeOutService.checkSessionTimeout(data);
            }
            if(this.errorCode=="IJS_EX_10027"){
            this.validContainerResult=data.searchResult.result;
            this.validContainerResult.sort((a,b) => (a['eqpNum'] < b['eqpNum']) ? -1 : ((b['eqpNum'] < a['eqpNum']) ? 1 : 0)); //to sort the successful containers list in sorted order
            this.validContainerResult['eqListSource'] = "uploadEquipment";
            
            this.validateSuccessMsg=true;
          
          }
          if(data.msgVOs != undefined || data.msgVOs!= null){
            this.failedContainerResult = data.msgVOs; //#NIIT CR3
            if(this.failedContainerResult.length != 0){
              this.failedContainerList();//#NIIT CR3
            }
          }
          this._spinner.hideSpinner();
          $('#modal-group-1').addClass('uk-open').show();
          //to scroll to the top of page on clicking save
          $('#modal-group-1').parent().parent().scrollTop(0);
          });
    }

    //#NIIT CR3  <<<<<< BEGIN
    failedList:any = [];
    //method to give a list of failed uploaded equipments with the error message
    failedContainerList(){
      for (var i = 0; i < this.failedContainerResult.length; i++) {
          for (var j = 0; j < this.failedContainerResult[i].messageList.length; j++) {
            //when Equipment doesn't exist
            if(this.failedContainerResult[i].errorCode == "IJS_PRJ_EX_10003"){
              let failedEqListObj = {
                "eqpNum": this.failedContainerResult[i].messageList[j],
                "errorMsg": this._serverErrorCode.checkError("IJS_PRJ_EX_10003")        
               }
               this.failedList.push(failedEqListObj);
            }
            //when Equipment location doesn't match with the From location of contract
            if(this.failedContainerResult[i].errorCode == "IJS_PRJ_EX_10004"){
              let failedEqListObj = {
                "eqpNum": this.failedContainerResult[i].messageList[j],
                "errorMsg": this._serverErrorCode.checkError("IJS_PRJ_EX_10004")        
               }
               this.failedList.push(failedEqListObj);
            }
            //when JO is already created and Equipment can not be used to move back to the source loacation
            if(this.failedContainerResult[i].errorCode == "IJS_PRJ_EX_10005"){
                let failedEqListObj = {
                  "eqpNum": this.failedContainerResult[i].messageList[j],
                  "errorMsg": this._serverErrorCode.checkError("IJS_PRJ_EX_10005")        
                }
                this.failedList.push(failedEqListObj);
            }  
            //when JO is already created with this equipment
            if(this.failedContainerResult[i].errorCode == "IJS_PRJ_EX_10006"){
              let failedEqListObj = {
                "eqpNum": this.failedContainerResult[i].messageList[j],
                "errorMsg": this._serverErrorCode.checkError("IJS_PRJ_EX_10006")        
               }
               this.failedList.push(failedEqListObj);
            }    
        }
      }
        this.failedContainerResult = this.failedList;
        this.failedContainerResult.sort((a,b) => (a['eqpNum'] < b['eqpNum']) ? -1 : ((b['eqpNum'] < a['eqpNum']) ? 1 : 0)); //to sort the failed containers list in sorted order 
        this.failedList = [];
    }
    //#NIIT CR3  <<<<<< END
    
    downloadTemplateXls(e) {
     
      this._downloadFileService.downloadTemplateExcel("containerTemplateDownload","processJOSearchBookingBL")
      .subscribe(
        data => {
         
          FileSaver.saveAs(data, "IJS_CONTAINER_DOWNLOAD_TEMPLATE");
        }
      );
    }
    
    closeFileUpload (e) {
      this.closeFileUploader.emit(e);
    }
    @Output() containerUpdateList = new EventEmitter();
    confirmContainer(e){     
      this.containerUpdateList.emit(this.validContainerResult);  
    }
    
    ngOnInit() {
    }

    openFailedUploadedEquipments(){
     $('#modal-group-1').addClass('uk-open').hide();
     $('#modal-group-2').addClass('uk-open').show();   
   }

   openSuccessfulUploadedEquipments(){
     $('#modal-group-2').addClass('uk-open').hide();
     $('#modal-group-1').addClass('uk-open').show();
   }


}
