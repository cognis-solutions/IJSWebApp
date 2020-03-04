import { Component, OnInit, Input } from '@angular/core';
import { FormGroup, FormArray, FormBuilder, Validators, FormControl } from '@angular/forms';

@Component({
  selector: 'app-to-form-loaction-form',
  templateUrl: './to-form-loaction-form.component.html',
  styleUrls: ['./to-form-loaction-form.component.scss']
})
export class ToFormLoactionFormComponent implements OnInit {
  
      public adressForm: FormGroup;
      @Input('group') group;
    

  constructor(private _fb: FormBuilder) { 
    
  }

  ngOnInit() {
    this.adressForm = new FormGroup({
       street: new FormControl(),
       postcode: new FormControl(),
       
    });
  }

}
