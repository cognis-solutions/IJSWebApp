import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ProcessJoComponent } from './process-jo.component';

describe('ProcessJoComponent', () => {
  let component: ProcessJoComponent;
  let fixture: ComponentFixture<ProcessJoComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ProcessJoComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ProcessJoComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should be created', () => {
    expect(component).toBeTruthy();
  });
});
