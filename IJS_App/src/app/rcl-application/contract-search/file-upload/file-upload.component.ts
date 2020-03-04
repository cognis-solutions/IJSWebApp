import { Component, OnInit, Output, EventEmitter  } from '@angular/core';
import { DomSanitizer, SafeUrl } from '@angular/platform-browser';
import { FileUploadService } from "../../../common-services/file-upload.service";
import { DownloadFileService } from "../../../common-services/download-file.service";
import { RclappUrlService } from "../../../common-services/rclapp-url.service";
import { ServerErrorcodeService } from "../../../common-services/server-errorcode.service";
import { SpinnerServiceService } from "../../../common-services/spinner-service.service";
import * as FileSaver from 'file-saver';
declare var UIkit: any;
import * as uikit from 'uikit';
import * as $ from 'jquery';
declare var jQuery: any;

@Component({
  selector: 'app-file-upload',
  templateUrl: './file-upload.component.html',
  styleUrls: ['./file-upload.component.scss']
})
export class FileUploadComponent implements OnInit {

  fileSelected: File = new File([], "");
  progressValue: number = 0;
  errorMsg: string;
  successMsg: string;
  dataFilePath: string;
  uploadResultMsg: string;
  uploadErrResultMsg: string;

  @Output() public closeFileUploader = new EventEmitter();

  constructor(private _fileUploadService: FileUploadService, private _downloadFileService: DownloadFileService, private _sanitizer: DomSanitizer, private _rclappUrlService: RclappUrlService,
              public _serverErrorCode: ServerErrorcodeService, public spinner: SpinnerServiceService ) {
    this._fileUploadService.progress$.subscribe(
      data => {
        this.progressValue = data;       
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
          if(data.uploadVO.isFailed == false){            
            this.successMsg = "Uploaded successfully"; 
            this.dataFilePath = data.uploadVO.fileName;
           }
          else {
            this.errorMsg = "Uploading Failed";
          }      
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
  public updatedContracts =[];
  public failedContracts =[];
  public partialSuccessful = [];
  
  saveUploadedFile(e) {
    let saveobj = {
      uploadVO: {
        action: 'upload',
        fileName: this.dataFilePath
      }      
    }
    this.spinner.showSpinner();
    this._fileUploadService.saveXlsFile(saveobj).subscribe(
        (data) => {        
          this.spinner.hideSpinner();
          if(data.uploadVO !=undefined){
            this.updatedContracts=data.uploadVO.updatedContracts;
            this.failedContracts=data.uploadVO.failedContracts;
            this.partialSuccessful=data.uploadVO.partialSuccessful; //to show partial successful
            this.updatedContracts.sort((a,b) => (a < b) ? -1 : ((b < a) ? 1 : 0)); 
            this.failedContracts.sort((a,b) => (a < b) ? -1 : ((b < a) ? 1 : 0)); 
            this.partialSuccessful.sort((a,b) => (a < b) ? -1 : ((b < a) ? 1 : 0)); 
          }                  
          $('#modal-group-1').addClass('uk-open').show();
          jQuery("html, body").animate({ scrollTop: 0 }, 900); //to scroll to the top of page on clicking save
        },
        (err) =>{
          this.spinner.hideSpinner();
          this.uploadErrResultMsg=this._serverErrorCode.checkError("GBL_IJS_EX_10001");
        }
      )
        
  }
  
  downloadTemplateXls(e) {
	
    this._downloadFileService.downloadTemplateExcel("contractTemplateDownload","contractSearch")
    .subscribe(
      data => {
        FileSaver.saveAs(data, "IJS_CONTRACT_TEMPLATE_DOWNLOAD");
      }
    );
  }
  
  closeFileUpload (e) {
    this.closeFileUploader.emit(e);
  }
  
  
  
  ngOnInit() {
  }

  closeUploadExcelDialog(){
    $('#modal-group-1').addClass('uk-open').hide();
  }

  openSuccessContractsDiaolg(){
    $('#modal-group-2').addClass('uk-open').hide();
    $('#modal-group-3').addClass('uk-open').hide();
    $('#modal-group-1').addClass('uk-open').show();    
  }

  openFailedContractsDiaolg(){
    $('#modal-group-1').addClass('uk-open').hide();
    $('#modal-group-3').addClass('uk-open').hide();
    $('#modal-group-2').addClass('uk-open').show();
  }

  openPartialSuccessContractsDiaolg(){
    $('#modal-group-1').addClass('uk-open').hide();
    $('#modal-group-2').addClass('uk-open').hide();
    $('#modal-group-3').addClass('uk-open').show();
  }

}
