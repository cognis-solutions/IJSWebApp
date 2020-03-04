import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { RclSelectizeComponent } from './rcl-selectize.component';

describe('RclSelectizeComponent', () => {
  let component: RclSelectizeComponent;
  let fixture: ComponentFixture<RclSelectizeComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ RclSelectizeComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(RclSelectizeComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should be created', () => {
    expect(component).toBeTruthy();
  });
});
