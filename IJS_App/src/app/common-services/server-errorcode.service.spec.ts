import { TestBed, inject } from '@angular/core/testing';

import { ServerErrorcodeService } from './server-errorcode.service';

describe('ServerErrorcodeService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [ServerErrorcodeService]
    });
  });

  it('should be created', inject([ServerErrorcodeService], (service: ServerErrorcodeService) => {
    expect(service).toBeTruthy();
  }));
});
