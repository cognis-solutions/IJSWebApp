import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { RclListNoLookUpComponent } from './rcl-list-no-look-up.component';

describe('RclListNoLookUpComponent', () => {
  let component: RclListNoLookUpComponent;
  let fixture: ComponentFixture<RclListNoLookUpComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ RclListNoLookUpComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(RclListNoLookUpComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should be created', () => {
    expect(component).toBeTruthy();
  });
});
