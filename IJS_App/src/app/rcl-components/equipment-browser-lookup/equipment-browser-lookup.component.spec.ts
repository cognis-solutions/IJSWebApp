import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { EquipmentBrowserLookupComponent } from './equipment-browser-lookup.component';

describe('EquipmentBrowserLookupComponent', () => {
  let component: EquipmentBrowserLookupComponent;
  let fixture: ComponentFixture<EquipmentBrowserLookupComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ EquipmentBrowserLookupComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(EquipmentBrowserLookupComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should be created', () => {
    expect(component).toBeTruthy();
  });
});
