import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { JoMaintenanceSearchComponent } from './jo-maintenance-search.component';

describe('JoMaintenanceSearchComponent', () => {
  let component: JoMaintenanceSearchComponent;
  let fixture: ComponentFixture<JoMaintenanceSearchComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ JoMaintenanceSearchComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(JoMaintenanceSearchComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should be created', () => {
    expect(component).toBeTruthy();
  });
});
