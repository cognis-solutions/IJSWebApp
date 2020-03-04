import { BrowserModule } from '@angular/platform-browser';
import { NgModule, APP_INITIALIZER } from '@angular/core';
import { FormsModule } from '@angular/forms'
import { HttpModule } from '@angular/http';
import { RouterModule, Routes } from '@angular/router';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { DatePipe } from '@angular/common'; 
// Start - Installed Components Application Modules or Components
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { AngularFontAwesomeModule } from 'angular-font-awesome/angular-font-awesome';
// End - Installed Components Application Modules or Components

// Start - Application Modules or Components
import { AppComponent } from './app.component';
import { RclComponentsModule } from './rcl-components/rcl-components.module';
import { RclApplicationModule } from './rcl-application/rcl-application.module';
// End - Application Modules or Components

// Start - Services 
import { SpinnerServiceService } from './common-services/spinner-service.service';
import { ServerErrorcodeService } from './common-services/server-errorcode.service';
import { FileUploadService } from './common-services/file-upload.service';
import { ClickOutside } from './common-directives/click-outside.directive';
import { SpecialHandlingService } from './common-services/special-handling.service';
import { LookUpdataServiceService } from './common-services/look-updata-service.service';
import { UserComponent } from './user/user.component';
import { UserTypeService } from './user/user-type.service';
import { UserService } from './user/user.service';
import { WindowRefService } from './common-services/window-ref.service';
import { RclappUrlService } from './common-services/rclapp-url.service';
import { DownloadFileService } from './common-services/download-file.service';
import { PortClassService } from './common-services/port-class.service';
import { ImdgClassService } from './common-services/imdg-class.service';
import { ContainerListService } from './common-services/container-list.service';
import { SessionTimeOutService } from "app/common-services/session-time-out.service";
//import { ProcessJOAdhocComponent } from "app/rcl-application/process-jo/process-joadhoc/process-joadhoc.component";
//import { RouteListModalComponent } from "app/rcl-components/route-list-modal/route-list-modal.component";
import { SortSearchTableService } from "app/rcl-application/contract-search/sort-search-table.service";
import { ProcessjoSearchComponent } from "app/rcl-application/process-jo/processjo-search/processjo-search.component";
import { ProcessjoSortSearchTableService } from "app/rcl-application/process-jo/processjo-sort-search-table.service";
//import { ProcessjoSearchService } from "app/rcl-application/process-jo/processjo-search.service";
@NgModule({
  declarations: [
    AppComponent,
    UserComponent,
   

  ],
  imports: [
    BrowserModule,
    BrowserAnimationsModule,
    FormsModule,
    HttpModule,
    RouterModule.forRoot([
      {
        path: '',
        loadChildren: './rcl-application/rcl-application.module.ts#RclApplicationModule'
      },
      {
        path: '**',
        loadChildren: './rcl-application/rcl-application.module.ts#RclApplicationModule'
      }
    ]),
    NgbModule.forRoot(),
    AngularFontAwesomeModule,
    RclComponentsModule
  ],
  providers: [ProcessjoSortSearchTableService,SortSearchTableService,ProcessjoSearchComponent,RclComponentsModule, DatePipe, SpinnerServiceService, ServerErrorcodeService, LookUpdataServiceService, UserService, WindowRefService, FileUploadService, RclappUrlService, DownloadFileService, UserTypeService,SpecialHandlingService,PortClassService,ImdgClassService,SessionTimeOutService,ContainerListService, 
  {
      provide: APP_INITIALIZER,
      useFactory: userServiceFactory,
      multi: true,
      deps: [UserService]
    }  
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }

export function userServiceFactory(userService: UserService): Function {
    return () => userService.getData()
  }
