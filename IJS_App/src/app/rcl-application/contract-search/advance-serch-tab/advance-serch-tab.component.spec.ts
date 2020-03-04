import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AdvanceSerchTabComponent } from './advance-serch-tab.component';

describe('AdvanceSerchTabComponent', () => {
  let component: AdvanceSerchTabComponent;
  let fixture: ComponentFixture<AdvanceSerchTabComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AdvanceSerchTabComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AdvanceSerchTabComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should be created', () => {
    expect(component).toBeTruthy();
  });
});
