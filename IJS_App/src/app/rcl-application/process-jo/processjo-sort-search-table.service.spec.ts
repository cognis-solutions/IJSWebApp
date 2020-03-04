import { TestBed, inject } from '@angular/core/testing';

import { ProcessjoSortSearchTableService } from './processjo-sort-search-table.service';

describe('ProcessjoSortSearchTableService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [ProcessjoSortSearchTableService]
    });
  });

  it('should be created', inject([ProcessjoSortSearchTableService], (service: ProcessjoSortSearchTableService) => {
    expect(service).toBeTruthy();
  }));
});
