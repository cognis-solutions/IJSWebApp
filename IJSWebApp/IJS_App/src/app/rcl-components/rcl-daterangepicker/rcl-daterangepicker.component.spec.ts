import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { RclDaterangepickerComponent } from './rcl-daterangepicker.component';

describe('RclDaterangepickerComponent', () => {
  let component: RclDaterangepickerComponent;
  let fixture: ComponentFixture<RclDaterangepickerComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ RclDaterangepickerComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(RclDaterangepickerComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should be created', () => {
    expect(component).toBeTruthy();
  });
});
