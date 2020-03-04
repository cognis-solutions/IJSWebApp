import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { VesselLookupComponent } from './vessel-lookup.component';

describe('VesselLookupComponent', () => {
  let component: VesselLookupComponent;
  let fixture: ComponentFixture<VesselLookupComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ VesselLookupComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(VesselLookupComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should be created', () => {
    expect(component).toBeTruthy();
  });
});
