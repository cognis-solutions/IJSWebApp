import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { RclCheckboxComponent } from './rcl-checkbox.component';

describe('RclCheckboxComponent', () => {
  let component: RclCheckboxComponent;
  let fixture: ComponentFixture<RclCheckboxComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ RclCheckboxComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(RclCheckboxComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should be created', () => {
    expect(component).toBeTruthy();
  });
});
