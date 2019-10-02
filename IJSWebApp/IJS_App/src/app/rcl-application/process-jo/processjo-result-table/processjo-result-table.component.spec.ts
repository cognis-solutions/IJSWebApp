import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ProcessjoResultTableComponent } from './processjo-result-table.component';

describe('ProcessjoResultTableComponent', () => {
  let component: ProcessjoResultTableComponent;
  let fixture: ComponentFixture<ProcessjoResultTableComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ProcessjoResultTableComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ProcessjoResultTableComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should be created', () => {
    expect(component).toBeTruthy();
  });
});
