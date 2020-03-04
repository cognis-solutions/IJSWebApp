import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { RclSearchfilterComponent } from './rcl-searchfilter.component';

describe('RclSearchfilterComponent', () => {
  let component: RclSearchfilterComponent;
  let fixture: ComponentFixture<RclSearchfilterComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ RclSearchfilterComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(RclSearchfilterComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should be created', () => {
    expect(component).toBeTruthy();
  });
});
