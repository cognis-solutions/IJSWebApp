import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ExemptedCustomerComponent } from './exempted-customer.component';

describe('ExemptedCustomerComponent', () => {
  let component: ExemptedCustomerComponent;
  let fixture: ComponentFixture<ExemptedCustomerComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ExemptedCustomerComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ExemptedCustomerComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should be created', () => {
    expect(component).toBeTruthy();
  });
});
