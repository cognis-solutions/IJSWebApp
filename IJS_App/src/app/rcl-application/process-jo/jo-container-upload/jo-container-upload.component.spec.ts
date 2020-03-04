import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { JoContainerUploadComponent } from './jo-container-upload.component';

describe('JoContainerUploadComponent', () => {
  let component: JoContainerUploadComponent;
  let fixture: ComponentFixture<JoContainerUploadComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ JoContainerUploadComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(JoContainerUploadComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should be created', () => {
    expect(component).toBeTruthy();
  });
});
