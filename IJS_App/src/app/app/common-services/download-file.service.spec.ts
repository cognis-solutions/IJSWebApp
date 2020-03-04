import { TestBed, inject } from '@angular/core/testing';

import { DownloadFileService } from './download-file.service';

describe('DownloadFileService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [DownloadFileService]
    });
  });

  it('should be created', inject([DownloadFileService], (service: DownloadFileService) => {
    expect(service).toBeTruthy();
  }));
});
