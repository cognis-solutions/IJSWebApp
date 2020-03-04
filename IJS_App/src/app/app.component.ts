import { Component, OnInit } from '@angular/core';
import { SpinnerServiceService } from './common-services/spinner-service.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent implements OnInit{
  showSpinner: boolean = false;
  constructor(public spinner : SpinnerServiceService){ }
  ngOnInit() {
    this.spinner.isSpinning.subscribe(isVisible=>{
      this.showSpinner = isVisible;
    })
  }
 //Set the width of the side navigation to 250px */
   openNav() {
      document.getElementById("mySidenav").style.width = "250px";
  }

 //Set the width of the side navigation to 0 
  closeNav() {
      document.getElementById("mySidenav").style.width = "0";
  }
  
  // onClickOutside(event) {
  //     if(event.target.classList.value !== "fa fa-bars") {
  //       document.getElementById("mySidenav").style.width = "0";
  //     }
  // }

  
}
