import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ProcessjoSearchComponent } from './processjo-search.component';

describe('ProcessjoSearchComponent', () => {
  let component: ProcessjoSearchComponent;
  let fixture: ComponentFixture<ProcessjoSearchComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ProcessjoSearchComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ProcessjoSearchComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should be created', () => {
    expect(component).toBeTruthy();
  });
});
