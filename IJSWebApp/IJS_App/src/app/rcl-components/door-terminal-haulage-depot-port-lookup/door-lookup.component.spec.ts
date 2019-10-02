import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { DoorLookupComponent } from './door-lookup.component';

describe('DoorLookupComponent', () => {
  let component: DoorLookupComponent;
  let fixture: ComponentFixture<DoorLookupComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ DoorLookupComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(DoorLookupComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should be created', () => {
    expect(component).toBeTruthy();
  });
});
