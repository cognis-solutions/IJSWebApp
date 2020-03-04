import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { CostBillTableComponent } from './cost-bill-table.component';

describe('CostBillTableComponent', () => {
  let component: CostBillTableComponent;
  let fixture: ComponentFixture<CostBillTableComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ CostBillTableComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CostBillTableComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should be created', () => {
    expect(component).toBeTruthy();
  });
});
