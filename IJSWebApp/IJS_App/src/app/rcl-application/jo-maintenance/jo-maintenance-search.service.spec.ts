import { TestBed, inject } from '@angular/core/testing';

import { JoMaintenanceSearchService } from './jo-maintenance-search.service';

describe('JoMaintenanceSearchService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [JoMaintenanceSearchService]
    });
  });

  it('should be created', inject([JoMaintenanceSearchService], (service: JoMaintenanceSearchService) => {
    expect(service).toBeTruthy();
  }));
});
