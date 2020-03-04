import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { RclAgHeaderGroupComponent } from './rcl-ag-header-group.component';

describe('RclAgHeaderGroupComponent', () => {
  let component: RclAgHeaderGroupComponent;
  let fixture: ComponentFixture<RclAgHeaderGroupComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ RclAgHeaderGroupComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(RclAgHeaderGroupComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should be created', () => {
    expect(component).toBeTruthy();
  });
});
