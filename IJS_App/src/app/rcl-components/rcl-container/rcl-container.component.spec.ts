import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { RclContainerComponent } from './rcl-container.component';

describe('RclContainerComponent', () => {
  let component: RclContainerComponent;
  let fixture: ComponentFixture<RclContainerComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ RclContainerComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(RclContainerComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should be created', () => {
    expect(component).toBeTruthy();
  });
});
