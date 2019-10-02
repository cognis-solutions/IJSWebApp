import { RclIjsApplicationPage } from './app.po';

describe('rcl-ijs-application App', () => {
  let page: RclIjsApplicationPage;

  beforeEach(() => {
    page = new RclIjsApplicationPage();
  });

  it('should display welcome message', () => {
    page.navigateTo();
    expect(page.getParagraphText()).toEqual('Welcome to app!!');
  });
});
