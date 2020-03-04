import { TestBed, inject } from '@angular/core/testing';

import { PortClassService } from './port-class.service';

describe('PortClassService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [PortClassService]
    });
  });

  it('should be created', inject([PortClassService], (service: PortClassService) => {
    expect(service).toBeTruthy();
  }));
});
