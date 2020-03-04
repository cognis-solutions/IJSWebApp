import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { SpclCostBlBookingComponent } from './spcl-cost-bl-booking.component';

describe('SpclCostBlBookingComponent', () => {
  let component: SpclCostBlBookingComponent;
  let fixture: ComponentFixture<SpclCostBlBookingComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ SpclCostBlBookingComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(SpclCostBlBookingComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should be created', () => {
    expect(component).toBeTruthy();
  });
});
