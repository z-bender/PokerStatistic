import {PokerStatisticWebPage} from './app.po';

describe('poker-statistic-web App', function() {
  let page: PokerStatisticWebPage;

  beforeEach(() => {
    page = new PokerStatisticWebPage();
  });

  it('should display message saying app works', () => {
    page.navigateTo();
    expect(page.getParagraphText()).toEqual('app works!');
  });
});
