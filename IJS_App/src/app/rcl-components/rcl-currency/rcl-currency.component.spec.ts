import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { RclCurrencyComponent } from './rcl-currency.component';

describe('RclCurrencyComponent', () => {
  let component: RclCurrencyComponent;
  let fixture: ComponentFixture<RclCurrencyComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ RclCurrencyComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(RclCurrencyComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should be created', () => {
    expect(component).toBeTruthy();
  });
});
