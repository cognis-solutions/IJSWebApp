import { Injectable } from '@angular/core';
import { Subject } from 'rxjs/Subject';

@Injectable()
export class PortClassService {

  portClassList: Array<Object>;
  portClassSubject : Subject<any>;
  portClassSubjectforAddEdit : Subject<any>;
  refreshSubject: Subject<any>;//#NIIT CR4 >>>>


  constructor() {
    this.portClassList = new Array<Object>();
    this.portClassSubject = new Subject<any>();
    this.portClassSubjectforAddEdit = new Subject<any>();
    this.refreshSubject = new Subject<any>();//#NIIT CR4 >>>>
   }

  public setPortClassList(rowObj){

   var sNO = 0;
   if(this.portClassList!=undefined && this.portClassList.length !=0){
    let portList = [...this.portClassList];     
    portList.sort((a,b) => (a['seqNo'] > b['seqNo']) ? -1 : ((b['seqNo'] > a['seqNo']) ? 1 : 0)); //to sort the term code list
    sNO = portList[0]['seqNo']; 
   }

   rowObj['seqNo'] = sNO + 1;   
   this.portClassList.push(rowObj);    
  }

}
