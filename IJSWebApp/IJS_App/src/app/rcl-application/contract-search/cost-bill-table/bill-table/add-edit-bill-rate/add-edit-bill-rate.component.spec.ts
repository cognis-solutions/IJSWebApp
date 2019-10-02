import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AddEditBillRateComponent } from './add-edit-bill-rate.component';

describe('AddEditBillRateComponent', () => {
  let component: AddEditBillRateComponent;
  let fixture: ComponentFixture<AddEditBillRateComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AddEditBillRateComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AddEditBillRateComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should be created', () => {
    expect(component).toBeTruthy();
  });
});
