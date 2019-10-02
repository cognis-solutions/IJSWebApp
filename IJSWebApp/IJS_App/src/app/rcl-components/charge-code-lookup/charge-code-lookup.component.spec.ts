import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ChargeCodeLookupComponent } from './charge-code-lookup.component';

describe('ChargeCodeLookupComponent', () => {
  let component: ChargeCodeLookupComponent;
  let fixture: ComponentFixture<ChargeCodeLookupComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ChargeCodeLookupComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ChargeCodeLookupComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should be created', () => {
    expect(component).toBeTruthy();
  });
});
