import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AddEditCostRateComponent } from './add-edit-cost-rate.component';

describe('AddEditCostRateComponent', () => {
  let component: AddEditCostRateComponent;
  let fixture: ComponentFixture<AddEditCostRateComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AddEditCostRateComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AddEditCostRateComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should be created', () => {
    expect(component).toBeTruthy();
  });
});
