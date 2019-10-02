import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { RclRadioComponent } from './rcl-radio.component';

describe('RclRadioComponent', () => {
  let component: RclRadioComponent;
  let fixture: ComponentFixture<RclRadioComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ RclRadioComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(RclRadioComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should be created', () => {
    expect(component).toBeTruthy();
  });
});
