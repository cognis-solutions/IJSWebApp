import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { RclCustomerLookupComponent } from './rcl-customer-lookup.component';

describe('RclCustomerLookupComponent', () => {
  let component: RclCustomerLookupComponent;
  let fixture: ComponentFixture<RclCustomerLookupComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ RclCustomerLookupComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(RclCustomerLookupComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should be created', () => {
    expect(component).toBeTruthy();
  });
});
