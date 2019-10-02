import {Component, Input, OnInit} from '@angular/core';

@Component({
  selector: 'app-rcl-validation-result',
  templateUrl: './rcl-validation-result.component.html',
  styleUrls: ['./rcl-validation-result.component.scss']
})
export class RclValidationResultComponent implements OnInit {

  @Input() messages: Array<string>;
  @Input() touched: boolean = false;

  constructor() { }

  ngOnInit() {
  }

}