import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { JoMaintenanceSearchFilterComponent } from './jo-maintenance-search-filter.component';

describe('JoMaintenanceSearchFilterComponent', () => {
  let component: JoMaintenanceSearchFilterComponent;
  let fixture: ComponentFixture<JoMaintenanceSearchFilterComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ JoMaintenanceSearchFilterComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(JoMaintenanceSearchFilterComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should be created', () => {
    expect(component).toBeTruthy();
  });
});
