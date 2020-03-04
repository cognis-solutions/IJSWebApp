import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { RclRadioFaIconComponent } from './rcl-radio-fa-icon.component';

describe('RclRadioFaIconComponent', () => {
  let component: RclRadioFaIconComponent;
  let fixture: ComponentFixture<RclRadioFaIconComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ RclRadioFaIconComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(RclRadioFaIconComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should be created', () => {
    expect(component).toBeTruthy();
  });
});
