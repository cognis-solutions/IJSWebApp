import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { JoMaintenanceExcelUploadComponent } from './jo-maintenance-excel-upload.component';

describe('JoMaintenanceExcelUploadComponent', () => {
  let component: JoMaintenanceExcelUploadComponent;
  let fixture: ComponentFixture<JoMaintenanceExcelUploadComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ JoMaintenanceExcelUploadComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(JoMaintenanceExcelUploadComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should be created', () => {
    expect(component).toBeTruthy();
  });
});
