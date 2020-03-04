import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {FormGroup, FormArray, FormBuilder, Validators,ReactiveFormsModule,FormsModule} from "@angular/forms";
import {Routes, RouterModule} from '@angular/router';
import {NgbModule} from "@ng-bootstrap/ng-bootstrap";
import {AngularFontAwesomeModule} from "angular-font-awesome";
import {NgxPaginationModule} from 'ngx-pagination';
import { ModalDialogModule } from 'ngx-modal-dialog';

import {appModuleRouting} from './rcl-application.routing'
import {RclComponentsModule} from "app/rcl-components/rcl-components.module";

// Start - Components
import {ContractSearchComponent} from './contract-search/contract-search.component';
import {ProcessJoComponent} from './process-jo/process-jo.component';
import {JoMaintenanceComponent} from './jo-maintenance/jo-maintenance.component';


import {AgGridModule} from "ag-grid-angular/dist/aggrid.module";
import {RclAgComponentsModule} from "app/rcl-ag-components/rcl-ag-components.module";
import {RclAgHeaderGroupComponent} from "app/rcl-ag-components/rcl-ag-header-group/rcl-ag-header-group.component";
import {RclAgHeaderComponent} from "app/rcl-ag-components/rcl-ag-header/rcl-ag-header.component";
import {RclAgDateComponent} from "app/rcl-ag-components/rcl-ag-date/rcl-ag-date.component";
// End Components

// Start - Providers
//import {ExcemptedCustomerService} from './exempted-customer/excempted-customer.service';
import {ContractSearchService} from './contract-search/contract-search.service';
import {JoMaintenanceSearchService} from './jo-maintenance/jo-maintenance-search.service';
import {JoInquiryService} from './jo-inquiry-component/jo-inquiry.service';

import {SortSearchTableService} from './contract-search/sort-search-table.service';
import {JoInquiryComponentComponent} from './jo-inquiry-component/jo-inquiry-component.component';
// End - Providers

//import {ExemptedCustomerPipePipe} from './exempted-customer/exempted-customer-pipe.pipe';
import {AdvanceSerchTabComponent} from './contract-search/advance-serch-tab/advance-serch-tab.component';
import {NewContractComponent} from './contract-search/new-contract/new-contract.component';
import {CostBillTableComponent} from './contract-search/cost-bill-table/cost-bill-table.component';
import {HeaderGroupComponent} from './contract-search/header-group/header-group.component';
import {ContractHistoryComponent} from './contract-search/contract-history/contract-history.component';
import {VendorDetailsComponent} from './contract-search/vendor-details/vendor-details.component';
import {CompareComponent} from './contract-search/compare/compare.component';
import {AddEditCostRateComponent} from './contract-search/add-edit-cost-rate/add-edit-cost-rate.component';
import {BillTableComponent} from './contract-search/cost-bill-table/bill-table/bill-table.component';
import {AddEditBillRateComponent} from './contract-search/cost-bill-table/bill-table/add-edit-bill-rate/add-edit-bill-rate.component';
import {ServiceLookupComponent} from './contract-search/service-lookup/service-lookup.component';
import {OogDimentionSetupComponent} from './contract-search/oog-dimention-setup/oog-dimention-setup.component';
import {FileUploadComponent} from './contract-search/file-upload/file-upload.component';
import {ProcessjoSearchComponent} from './process-jo/processjo-search/processjo-search.component';
import {ProcessjoSortSearchTableService} from "./process-jo/processjo-sort-search-table.service";

import {JoMaintenanceSortingService} from "./jo-maintenance/jo-maintenance-sorting.service";

import {ProcessjoSearchService} from "./process-jo/processjo-search.service";
import {ProcessjoResultFilterComponent} from './process-jo/processjo-result-filter/processjo-result-filter.component';
import {ProcessjoResultTableComponent} from './process-jo/processjo-result-table/processjo-result-table.component';
import {ProcessJOAdhocComponent} from './process-jo/process-joadhoc/process-joadhoc.component';
import {JoMaintenanceSearchComponent} from './jo-maintenance/jo-maintenance-search/jo-maintenance-search.component';
import {JoSummaryTableComponent} from './process-jo/jo-summary-table/jo-summary-table.component';
import {JoMaintenanceresultTableComponent} from './jo-maintenance/jo-maintenanceresult-table/jo-maintenanceresult-table.component';
import {JoInquirySearchComponent} from './jo-inquiry-component/jo-inquiry-search/jo-inquiry-search.component';
import {JoMaintainenceJobOrderFilterComponent} from './jo-maintenance/jo-maintainence-job-order-filter/jo-maintainence-job-order-filter.component';
import {JoInquiryresultTableComponent} from './jo-inquiry-component/jo-inquiryresult-table/jo-inquiryresult-table.component';
import {JoInquiryJobOrderFilterComponent} from './jo-inquiry-component/jo-inquiry-job-order-filter/jo-inquiry-job-order-filter.component';
import {ProcessjoSearchFilterComponent} from './process-jo/processjo-search-filter/processjo-search-filter.component';
import {JoMaintenanceSearchFilterComponent} from './jo-maintenance/jo-maintenance-search-filter/jo-maintenance-search-filter.component';
import {JoContainerUploadComponent} from './process-jo/jo-container-upload/jo-container-upload.component';
import {SpclCostBlBookingComponent} from './contract-search/spcl-cost-bl-booking/spcl-cost-bl-booking.component'

import {ExemptedCustomerComponent} from './contract-search/exempted-customer/exempted-customer.component';
import { PageNotFoundComponent } from './page-not-found/page-not-found.component';
import { ImdgClassSetupComponent } from './contract-search/imdg-class-setup/imdg-class-setup.component';
import { PortClassSetupComponent } from './contract-search/port-class-setup/port-class-setup.component';
import { TestTableComponent } from './test-table/test-table.component';
import { BillingTableComponent } from './billing-table/billing-table.component';
import { SpecialHandlingService } from '../common-services/special-handling.service'
import { PortClassService } from '../common-services/port-class.service';
import { ImdgClassService } from '../common-services/imdg-class.service'; 
import { ContainerListService } from '../common-services/container-list.service'
import { RCLContainerModalComponent } from '../rcl-components/rcl-container/rcl-container.component';
import { SessionTimeOutComponent } from './session-time-out/session-time-out.component';
import { JoMaintenanceExcelUploadComponent } from './jo-maintenance/jo-maintenance-excel-upload/jo-maintenance-excel-upload.component';

   
@NgModule({
  imports: [   
    CommonModule,
    FormsModule,
    ReactiveFormsModule,
    appModuleRouting,
    RclComponentsModule,
    NgbModule.forRoot(),
    AngularFontAwesomeModule,
    NgxPaginationModule,
    ModalDialogModule.forRoot(),
    RclAgComponentsModule,
    AgGridModule
    .withComponents(
      [
        HeaderGroupComponent
      ]
    ),
  ],
  declarations: [
    ContractSearchComponent, 
    ProcessJoComponent, 
    JoMaintenanceComponent,
    ExemptedCustomerComponent,
    JoInquiryComponentComponent,
    AdvanceSerchTabComponent,
    NewContractComponent,
    CostBillTableComponent,
    HeaderGroupComponent,
    ContractHistoryComponent,
    VendorDetailsComponent,
    CompareComponent,
    AddEditCostRateComponent,
    BillTableComponent,
    AddEditBillRateComponent,
    ServiceLookupComponent,
    OogDimentionSetupComponent,
    FileUploadComponent,
    ProcessjoSearchComponent,
    ProcessjoResultFilterComponent,
    ProcessjoResultTableComponent,
    ProcessJOAdhocComponent,
    JoMaintenanceSearchComponent,
    JoSummaryTableComponent,
    JoMaintenanceresultTableComponent,

    JoInquirySearchComponent,
    JoMaintainenceJobOrderFilterComponent,
    JoInquiryresultTableComponent,
    JoInquiryJobOrderFilterComponent,
    ProcessjoSearchFilterComponent,
    JoMaintenanceSearchFilterComponent,
    JoContainerUploadComponent,
    SpclCostBlBookingComponent,
    PageNotFoundComponent,
    ImdgClassSetupComponent,
    PortClassSetupComponent,
    TestTableComponent,
    BillingTableComponent,
    SessionTimeOutComponent,
    JoMaintenanceExcelUploadComponent
   
  ],
  providers: [ ContractSearchService, SortSearchTableService, HeaderGroupComponent, CostBillTableComponent, ProcessjoSortSearchTableService, ProcessjoSearchService, JoMaintenanceSearchService, JoInquiryService, JoMaintenanceSortingService,SpecialHandlingService,PortClassService,ImdgClassService,ContainerListService],
  entryComponents: [ RCLContainerModalComponent,PortClassSetupComponent,ImdgClassSetupComponent,OogDimentionSetupComponent]
})
export class RclApplicationModule {}