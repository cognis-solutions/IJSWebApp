import { Injectable } from '@angular/core';
import { Subject } from 'rxjs/Subject';

@Injectable()
export class ImdgClassService {

  imdgList: Array<Object>; 
  imdgClassSubject : Subject<any>;
  imdgClassSubjectforAddEdit : Subject<any>;
  refreshImdgSubject: Subject<any>;//#NIIT CR4 >>>>

  constructor() {
    this.imdgList = new Array<Object>();
    this.imdgClassSubject = new Subject<any>();
    this.imdgClassSubjectforAddEdit = new Subject<any>();
    this.refreshImdgSubject = new Subject<any>();//#NIIT CR4 >>>>
   }

  public setImdgList(rowObj){

   var sNO = 0;
   if(this.imdgList!=undefined && this.imdgList.length !=0){
    let imdgClassList = [...this.imdgList];     
    imdgClassList.sort((a,b) => (a['seqNo'] > b['seqNo']) ? -1 : ((b['seqNo'] > a['seqNo']) ? 1 : 0)); //to sort the term code list
    sNO = imdgClassList[0]['seqNo']; 
   }

   rowObj['seqNo'] = sNO + 1;  
   this.imdgList.push(rowObj);    
  }

}
