import { Injectable } from '@angular/core';
import { Subject } from 'rxjs/Subject';


@Injectable()
export class SpecialHandlingService {

  oogList: Array<Object>;  
  

  currentNameSubject : Subject<any>;
  currentNameSubjectforAddEdit : Subject<any>;
  refreshOogSubject: Subject<any>;//#NIIT CR4 >>>>

  constructor() {
    this.oogList = new Array<Object>();
    this.currentNameSubject = new Subject<any>();
    this.currentNameSubjectforAddEdit = new Subject<any>();
    this.refreshOogSubject = new Subject<any>();//#NIIT CR4 >>>>
   } 
  

  public setOogList(rowObj){
   var sNO = 0;
   if(this.oogList!=undefined && this.oogList.length !=0){
    let oogClassList = [...this.oogList];     
    oogClassList.sort((a,b) => (a['seqNo'] > b['seqNo']) ? -1 : ((b['seqNo'] > a['seqNo']) ? 1 : 0)); //to sort the term code list
    sNO = oogClassList[0]['seqNo']; 
   }
   rowObj['seqNo'] = sNO + 1;   
   this.oogList.push(rowObj);
   this.oogList.sort((a,b) => (a['oogSetupCode'] < b['oogSetupCode']) ? -1 : ((b['oogSetupCode'] < a['oogSetupCode']) ? 1 : 0)); //to sort the oog class list in sorted order    
  }

}
