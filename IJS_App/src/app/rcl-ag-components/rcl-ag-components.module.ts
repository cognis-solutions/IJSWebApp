import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RclAgDateComponent } from './rcl-ag-date/rcl-ag-date.component';
import { RclAgHeaderComponent } from './rcl-ag-header/rcl-ag-header.component';
import { RclAgHeaderGroupComponent } from './rcl-ag-header-group/rcl-ag-header-group.component';
import { FormsModule } from "@angular/forms";

@NgModule({
  imports: [
    CommonModule,
    FormsModule,
  ],
  declarations: [RclAgDateComponent, RclAgHeaderComponent, RclAgHeaderGroupComponent],
  exports: [
    RclAgDateComponent, RclAgHeaderComponent, RclAgHeaderGroupComponent
  ]
})
export class RclAgComponentsModule { }
