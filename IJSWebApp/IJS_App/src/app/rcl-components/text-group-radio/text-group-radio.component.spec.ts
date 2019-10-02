import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { TextGroupRadioComponent } from './text-group-radio.component';

describe('TextGroupRadioComponent', () => {
  let component: TextGroupRadioComponent;
  let fixture: ComponentFixture<TextGroupRadioComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ TextGroupRadioComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(TextGroupRadioComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should be created', () => {
    expect(component).toBeTruthy();
  });
});
