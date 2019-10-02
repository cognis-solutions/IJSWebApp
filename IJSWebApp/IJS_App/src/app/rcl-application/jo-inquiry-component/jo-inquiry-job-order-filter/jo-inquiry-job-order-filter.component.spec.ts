import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { JoMaintainenceJobOrderFilterComponent } from './jo-maintainence-job-order-filter.component';

describe('JoMaintainenceJobOrderFilterComponent', () => {
  let component: JoMaintainenceJobOrderFilterComponent;
  let fixture: ComponentFixture<JoMaintainenceJobOrderFilterComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ JoMaintainenceJobOrderFilterComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(JoMaintainenceJobOrderFilterComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should be created', () => {
    expect(component).toBeTruthy();
  });
});
