import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ImdgClassSetupComponent } from './imdg-class-setup.component';

describe('ImdgClassSetupComponent', () => {
  let component: ImdgClassSetupComponent;
  let fixture: ComponentFixture<ImdgClassSetupComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ImdgClassSetupComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ImdgClassSetupComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should be created', () => {
    expect(component).toBeTruthy();
  });
});
