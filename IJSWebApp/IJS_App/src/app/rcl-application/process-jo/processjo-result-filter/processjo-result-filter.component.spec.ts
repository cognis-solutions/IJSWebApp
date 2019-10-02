import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ProcessjoResultFilterComponent } from './processjo-result-filter.component';

describe('ProcessjoResultFilterComponent', () => {
  let component: ProcessjoResultFilterComponent;
  let fixture: ComponentFixture<ProcessjoResultFilterComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ProcessjoResultFilterComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ProcessjoResultFilterComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should be created', () => {
    expect(component).toBeTruthy();
  });
});
