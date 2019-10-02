import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ProcessjoSearchFilterComponent } from './processjo-search-filter.component';

describe('ProcessjoSearchFilterComponent', () => {
  let component: ProcessjoSearchFilterComponent;
  let fixture: ComponentFixture<ProcessjoSearchFilterComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ProcessjoSearchFilterComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ProcessjoSearchFilterComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should be created', () => {
    expect(component).toBeTruthy();
  });
});
