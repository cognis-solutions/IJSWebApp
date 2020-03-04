import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { RclValidationResultComponent } from './rcl-validation-result.component';

describe('RclValidationResultComponent', () => {
  let component: RclValidationResultComponent;
  let fixture: ComponentFixture<RclValidationResultComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ RclValidationResultComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(RclValidationResultComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should be created', () => {
    expect(component).toBeTruthy();
  });
});
