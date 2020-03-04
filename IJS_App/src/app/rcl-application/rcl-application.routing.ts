import { Routes, RouterModule } from '@angular/router';
import { ContractSearchComponent } from "./contract-search/contract-search.component";
import { ProcessJoComponent } from "./process-jo/process-jo.component";
import { JoMaintenanceComponent } from './jo-maintenance/jo-maintenance.component';
import { PageNotFoundComponent } from './page-not-found/page-not-found.component';
import { JoInquiryComponentComponent } from "./jo-inquiry-component/jo-inquiry-component.component";
import { TestTableComponent } from './test-table/test-table.component';
import { SessionTimeOutComponent } from './session-time-out/session-time-out.component';

const appModuleRoutes: Routes = [
    {
        path: '',
        pathMatch: "full",
        redirectTo: 'contractsearch'
    },
    /**{
        path: 'login', component: ContractSearchComponent
    },**/ /** by RCL **/
    {
        path: 'contractsearch', component: ContractSearchComponent
    },
    {
        path: 'processjo', component: ProcessJoComponent,
    },
    {
        path: 'jomaintenance', component: JoMaintenanceComponent, data: { componentType: 'jomaintenance' }
    },
    {
        path: 'joinquiry', component: JoMaintenanceComponent, data: { componentType: 'joinquiry' }
    },
    {
        path: 'table', component: TestTableComponent
    },
    {
        path: 'session-time-out',
        component: SessionTimeOutComponent
    },
    {
        path: 'error',
        redirectTo: 'PageNotFoundComponent'
    },
    {
        path: '**',
        redirectTo: 'PageNotFoundComponent'
    },
]

export const appModuleRouting = RouterModule.forChild(appModuleRoutes);