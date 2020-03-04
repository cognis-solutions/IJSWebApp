import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { PortClassSetupComponent } from './port-class-setup.component';

describe('PortClassSetupComponent', () => {
  let component: PortClassSetupComponent;
  let fixture: ComponentFixture<PortClassSetupComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ PortClassSetupComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(PortClassSetupComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should be created', () => {
    expect(component).toBeTruthy();
  });
});
