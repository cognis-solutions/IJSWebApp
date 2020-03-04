import { TestBed, inject } from '@angular/core/testing';

import { JoMaintenanceSortingService } from './jo-maintenance-sorting.service';

describe('JoMaintenanceSortingService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [JoMaintenanceSortingService]
    });
  });

  it('should be created', inject([JoMaintenanceSortingService], (service: JoMaintenanceSortingService) => {
    expect(service).toBeTruthy();
  }));
});
