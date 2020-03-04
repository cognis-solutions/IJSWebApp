import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ToFormLoactionFormComponent } from './to-form-loaction-form.component';

describe('ToFormLoactionFormComponent', () => {
  let component: ToFormLoactionFormComponent;
  let fixture: ComponentFixture<ToFormLoactionFormComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ToFormLoactionFormComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ToFormLoactionFormComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should be created', () => {
    expect(component).toBeTruthy();
  });
});
