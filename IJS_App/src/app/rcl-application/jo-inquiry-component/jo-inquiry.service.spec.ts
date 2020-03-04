import { TestBed, inject } from '@angular/core/testing';

import { JoInquiryService } from './jo-inquiry.service';

describe('JoInquiryService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [JoInquiryService]
    });
  });

  it('should be created', inject([JoInquiryService], (service: JoInquiryService) => {
    expect(service).toBeTruthy();
  }));
});
