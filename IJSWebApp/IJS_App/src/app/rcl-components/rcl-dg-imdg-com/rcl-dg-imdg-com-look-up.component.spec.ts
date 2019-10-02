import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { RclJOLogLookUpComponent } from './rcl-jo-log-look-up.component';

describe('RclInputLookUpComponent', () => {
  let component: RclJOLogLookUpComponent;
  let fixture: ComponentFixture<RclJOLogLookUpComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ RclJOLogLookUpComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(RclJOLogLookUpComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should be created', () => {
    expect(component).toBeTruthy();
  });
});
