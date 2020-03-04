import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ServiceVesselVoyageLookupComponent } from './service-vessel-voyage-lookup.component';

describe('ServiceVesselVoyageLookupComponent', () => {
  let component: ServiceVesselVoyageLookupComponent;
  let fixture: ComponentFixture<ServiceVesselVoyageLookupComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ServiceVesselVoyageLookupComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ServiceVesselVoyageLookupComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should be created', () => {
    expect(component).toBeTruthy();
  });
});
