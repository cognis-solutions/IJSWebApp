import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { RclInputSearchComponent } from './rcl-input-search.component';

describe('RclInputSearchComponent', () => {
  let component: RclInputSearchComponent;
  let fixture: ComponentFixture<RclInputSearchComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ RclInputSearchComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(RclInputSearchComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should be created', () => {
    expect(component).toBeTruthy();
  });
});
