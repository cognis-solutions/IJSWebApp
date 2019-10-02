import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { OogDimentionSetupComponent } from './oog-dimention-setup.component';

describe('OogDimentionSetupComponent', () => {
  let component: OogDimentionSetupComponent;
  let fixture: ComponentFixture<OogDimentionSetupComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ OogDimentionSetupComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(OogDimentionSetupComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should be created', () => {
    expect(component).toBeTruthy();
  });
});
