import { TestBed, inject } from '@angular/core/testing';

import { LookUpdataServiceService } from './look-updata-service.service';

describe('LookUpdataServiceService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [LookUpdataServiceService]
    });
  });

  it('should be created', inject([LookUpdataServiceService], (service: LookUpdataServiceService) => {
    expect(service).toBeTruthy();
  }));
});
