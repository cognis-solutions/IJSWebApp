import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { JoMaintenanceComponent } from './jo-maintenance.component';

describe('JoMintenanceComponent', () => {
  let component: JoMaintenanceComponent;
  let fixture: ComponentFixture<JoMaintenanceComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ JoMaintenanceComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(JoMaintenanceComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should be created', () => {
    expect(component).toBeTruthy();
  });
});
