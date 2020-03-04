import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ProcessJOAdhocComponent } from './process-joadhoc.component';

describe('ProcessJOAdhocComponent', () => {
  let component: ProcessJOAdhocComponent;
  let fixture: ComponentFixture<ProcessJOAdhocComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ProcessJOAdhocComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ProcessJOAdhocComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should be created', () => {
    expect(component).toBeTruthy();
  });
});
