import { TestBed, inject } from '@angular/core/testing';

import { RclappUrlService } from './rclapp-url.service';

describe('RclappUrlService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [RclappUrlService]
    });
  });

  it('should be created', inject([RclappUrlService], (service: RclappUrlService) => {
    expect(service).toBeTruthy();
  }));
});
