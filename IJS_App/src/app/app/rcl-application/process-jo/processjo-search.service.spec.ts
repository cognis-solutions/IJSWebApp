import { TestBed, inject } from '@angular/core/testing';

import { ProcessjoSearchService } from './processjo-search.service';

describe('ProcessjoSearchService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [ProcessjoSearchService]
    });
  });

  it('should be created', inject([ProcessjoSearchService], (service: ProcessjoSearchService) => {
    expect(service).toBeTruthy();
  }));
});
