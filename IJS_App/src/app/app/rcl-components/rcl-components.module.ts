//Pre defined angular component
import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {FormsModule} from "@angular/forms";
import {NgxPaginationModule} from 'ngx-pagination';
import {NgbModule} from "@ng-bootstrap/ng-bootstrap";

//component defined by developers for app
import {RclInputComponent} from './rcl-input/rcl-input.component';
import {RclValidationResultComponent} from './rcl-validation-result/rcl-validation-result.component';
import {RclSelectizeComponent} from './rcl-selectize/rcl-selectize.component';
import {RclCheckboxComponent} from './rcl-checkbox/rcl-checkbox.component';
import {RclDaterangepickerComponent} from './rcl-daterangepicker/rcl-daterangepicker.component';
import {RclRadioComponent} from './rcl-radio/rcl-radio.component';
import {RclRadioFaIconComponent} from './rcl-radio-fa-icon/rcl-radio-fa-icon.component';
import {RclInputLookUpComponent} from './rcl-vendor-country-look-up/rcl-input-look-up.component';

import {ClickOutside} from '../common-directives/click-outside.directive';
import {RclSearchfilterComponent} from './rcl-searchfilter/rcl-searchfilter.component';
import {DoorLookupComponent} from './door-terminal-haulage-depot-port-lookup/door-lookup.component';
import {ServiceVesselVoyageLookupComponent} from './service-vessel-voyage-lookup/service-vessel-voyage-lookup.component';
import {RouteListModalComponent} from './route-list-modal/route-list-modal.component';
import {EquipmentBrowserLookupComponent} from './equipment-browser-lookup/equipment-browser-lookup.component';
import {TextGroupRadioComponent} from './text-group-radio/text-group-radio.component';
import {RclListNoLookUpComponent} from './rcl-list-no-look-up/rcl-list-no-look-up.component';
import {RCLContainerModalComponent} from './rcl-container/rcl-container.component';
import {RclJOLogLookUpComponent} from './rcl-jo-log/rcl-jo-log-look-up.component';
import {RclRsnCdLookUpComponent} from './rcl-reason-code/rcl-reason-code-look-up.component';
import {RclFSCLookUpComponent} from './rcl-fsc-look-up/rcl-fsc-look-up.component';
import {RclBookingBLLookUpComponent} from './rcl-booking-bl/rcl-booking-bl-look-up.component';
import {RclDGIMDGLookUpComponent} from './rcl-dg-imdg-com/rcl-dg-imdg-com-look-up.component';
import {ChargeCodeLookupComponent} from './charge-code-lookup/charge-code-lookup.component';
import {RclCustomerLookupComponent} from './rcl-customer-lookup/rcl-customer-lookup.component';
import {RclInputSearchComponent} from './rcl-input-search/rcl-input-search.component';
import { IjsAutoScrollDirective } from '../common-directives/ijs-auto-scroll.directive';
import { VesselLookupComponent } from './vessel-lookup/vessel-lookup.component';
import { RclCurrencyComponent } from './rcl-currency/rcl-currency.component';

@NgModule({
  imports: [
    CommonModule,
    FormsModule,
	  NgxPaginationModule,
    NgbModule.forRoot()    
  ],
  declarations: [RclInputComponent, RclValidationResultComponent, RclSelectizeComponent, RclCheckboxComponent, RclDaterangepickerComponent, RclRadioComponent, RclRadioFaIconComponent, RclInputLookUpComponent,ClickOutside, RclSearchfilterComponent, DoorLookupComponent, ServiceVesselVoyageLookupComponent, RouteListModalComponent, EquipmentBrowserLookupComponent, TextGroupRadioComponent ,RclListNoLookUpComponent, RCLContainerModalComponent, RclJOLogLookUpComponent, RclRsnCdLookUpComponent, RclFSCLookUpComponent, RclBookingBLLookUpComponent, RclDGIMDGLookUpComponent, ChargeCodeLookupComponent, RclCustomerLookupComponent, RclInputSearchComponent, IjsAutoScrollDirective, VesselLookupComponent, RclCurrencyComponent  ],
  
  exports: [ RclInputComponent, RclValidationResultComponent, RclSelectizeComponent, RclCheckboxComponent, RclDaterangepickerComponent, RclRadioComponent, RclRadioFaIconComponent, RclInputLookUpComponent,ClickOutside, RclSearchfilterComponent, DoorLookupComponent, ServiceVesselVoyageLookupComponent, RouteListModalComponent, TextGroupRadioComponent, EquipmentBrowserLookupComponent ,RclListNoLookUpComponent, RCLContainerModalComponent, RclJOLogLookUpComponent, RclRsnCdLookUpComponent, RclFSCLookUpComponent, RclBookingBLLookUpComponent, RclDGIMDGLookUpComponent, ChargeCodeLookupComponent, RclCustomerLookupComponent, RclInputSearchComponent, IjsAutoScrollDirective, VesselLookupComponent, RclCurrencyComponent ]
})
export class RclComponentsModule {}
