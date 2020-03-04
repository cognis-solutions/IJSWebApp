import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { RclAgHeaderComponent } from './rcl-ag-header.component';

describe('RclAgHeaderComponent', () => {
  let component: RclAgHeaderComponent;
  let fixture: ComponentFixture<RclAgHeaderComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ RclAgHeaderComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(RclAgHeaderComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should be created', () => {
    expect(component).toBeTruthy();
  });
});
