import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { JoSummaryTableComponent } from './jo-summary-table.component';

describe('JoSummaryTableComponent', () => {
  let component: JoSummaryTableComponent;
  let fixture: ComponentFixture<JoSummaryTableComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ JoSummaryTableComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(JoSummaryTableComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should be created', () => {
    expect(component).toBeTruthy();
  });
});
