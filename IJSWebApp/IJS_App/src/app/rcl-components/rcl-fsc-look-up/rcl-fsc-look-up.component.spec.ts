import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { RclInputLookUpComponent } from './rcl-input-look-up.component';

describe('RclInputLookUpComponent', () => {
  let component: RclInputLookUpComponent;
  let fixture: ComponentFixture<RclInputLookUpComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ RclInputLookUpComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(RclInputLookUpComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should be created', () => {
    expect(component).toBeTruthy();
  });
});
