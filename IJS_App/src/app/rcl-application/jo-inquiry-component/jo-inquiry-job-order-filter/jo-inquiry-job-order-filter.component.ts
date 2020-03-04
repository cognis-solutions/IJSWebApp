import { Component, OnInit, Input, Output, EventEmitter } from '@angular/core';

@Component({
  selector: 'app-jo-inquiry-job-order-filter',
  templateUrl: './jo-inquiry-job-order-filter.component.html',
  styleUrls: ['./jo-inquiry-job-order-filter.component.scss']
})
export class JoInquiryJobOrderFilterComponent implements OnInit {
  
  @Input() private filterData: any;
  @Input() private maintainJoSearchData: any;
  @Input() private filterDataSelectedComp: any;
  @Output() changeFind: EventEmitter<any> = new EventEmitter();
  
  
  constructor() { 
    
  }

  ngOnInit() {
  }
  
 maintainjochangeFind(e) {
    this.changeFind.emit(e);
  }

}
