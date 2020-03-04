import { TestBed, inject } from '@angular/core/testing';

import { ImdgClassService } from './imdg-class.service';

describe('ImdgClassService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [ImdgClassService]
    });
  });

  it('should be created', inject([ImdgClassService], (service: ImdgClassService) => {
    expect(service).toBeTruthy();
  }));
});
