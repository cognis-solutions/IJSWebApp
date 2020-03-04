import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { RouteListModalComponent } from './route-list-modal.component';

describe('RouteListModalComponent', () => {
  let component: RouteListModalComponent;
  let fixture: ComponentFixture<RouteListModalComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ RouteListModalComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(RouteListModalComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should be created', () => {
    expect(component).toBeTruthy();
  });
});
