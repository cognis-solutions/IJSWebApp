import { TestBed, inject } from '@angular/core/testing';

import { SortSearchTableService } from './sort-search-table.service';

describe('SortSearchTableService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [SortSearchTableService]
    });
  });

  it('should be created', inject([SortSearchTableService], (service: SortSearchTableService) => {
    expect(service).toBeTruthy();
  }));
});
