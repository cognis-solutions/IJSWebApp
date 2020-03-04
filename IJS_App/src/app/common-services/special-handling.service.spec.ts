import { TestBed, inject } from '@angular/core/testing';

import { SpecialHandlingService } from './special-handling.service';

describe('SpecialHandlingService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [SpecialHandlingService]
    });
  });

  it('should be created', inject([SpecialHandlingService], (service: SpecialHandlingService) => {
    expect(service).toBeTruthy();
  }));
});
