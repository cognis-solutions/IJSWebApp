import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { RclInputComponent } from './rcl-input.component';

describe('RclInputComponent', () => {
  let component: RclInputComponent;
  let fixture: ComponentFixture<RclInputComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ RclInputComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(RclInputComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should be created', () => {
    expect(component).toBeTruthy();
  });
});
