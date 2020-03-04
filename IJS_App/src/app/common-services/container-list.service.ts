import { Injectable } from '@angular/core';
import { Subject } from 'rxjs/Subject';

@Injectable()
export class ContainerListService {
 
  ladenSubject : Subject<any>;
  cntrlistData:any;
  
  constructor() {    
    this.ladenSubject = new Subject<any>();
   } 

}
