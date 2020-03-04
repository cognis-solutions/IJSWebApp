import { TestBed, inject } from '@angular/core/testing';

import { SpinnerServiceService } from './spinner-service.service';

describe('SpinnerServiceService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [SpinnerServiceService]
    });
  });

  it('should be created', inject([SpinnerServiceService], (service: SpinnerServiceService) => {
    expect(service).toBeTruthy();
  }));
});
