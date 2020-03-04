import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { JoMaintenanceresultTableComponent } from './jo-maintenanceresult-table.component';

describe('JoMaintenanceresultTableComponent', () => {
  let component: JoMaintenanceresultTableComponent;
  let fixture: ComponentFixture<JoMaintenanceresultTableComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ JoMaintenanceresultTableComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(JoMaintenanceresultTableComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should be created', () => {
    expect(component).toBeTruthy();
  });
});
