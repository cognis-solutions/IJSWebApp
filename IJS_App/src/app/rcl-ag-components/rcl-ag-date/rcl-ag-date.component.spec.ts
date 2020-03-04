import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { RclAgDateComponent } from './rcl-ag-date.component';

describe('RclAgDateComponent', () => {
  let component: RclAgDateComponent;
  let fixture: ComponentFixture<RclAgDateComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ RclAgDateComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(RclAgDateComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should be created', () => {
    expect(component).toBeTruthy();
  });
});
