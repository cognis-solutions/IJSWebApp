import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ServiceLookupComponent } from './service-lookup.component';

describe('ServiceLookupComponent', () => {
  let component: ServiceLookupComponent;
  let fixture: ComponentFixture<ServiceLookupComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ServiceLookupComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ServiceLookupComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should be created', () => {
    expect(component).toBeTruthy();
  });
});
