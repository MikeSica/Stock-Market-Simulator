import java.io.IOException;
import java.util.List;

import model.BaseStock;

/**
 * A MockStock is an instance of a BaseStock which is
 * used to see if code paths are correct.
 */
class MockStock extends BaseStock {
  StringBuilder log;
  MockFinanceHistory historyMock;

  /**
   * Constructor for a MockStock object with a log and a MockFinanceHistory.
   *
   * @param name name of the stock
   * @throws IOException if the name of the stock doesn't have history
   */
  MockStock(String name) throws IOException {
    super(name);
    this.log = new StringBuilder();
    historyMock = new MockFinanceHistory("GOOG");
  }

  @Override
  public float stockChange(String startDate, String endDate) {
    log.append("StockChange");
    historyMock.getPriceAtClosing(startDate);
    historyMock.getPriceAtClosing(endDate);
    log.append(historyMock.getLog());
    return 0;
  }

  @Override
  public float movingAverage(String givenDate, int num) {
    log.append("movingAverage");
    historyMock.getDatePlace(givenDate);
    historyMock.getPriceAtClosing(givenDate);
    log.append(historyMock.getLog());
    return 0;
  }

  @Override
  public String crossOverDays(String startDate, String endDate, int x) {
    log.append("crossOverDays");
    historyMock.getDatePlace(startDate);
    historyMock.getDatePlace(startDate);
    historyMock.getDatePlace(startDate);
    historyMock.getPriceAtClosing(startDate);
    log.append(historyMock.getLog());
    return "";
  }


  public float getStockPrice(String date, int quantity) {
    log.append("getStockPrice");
    historyMock.getPriceAtClosing(date);
    log.append(historyMock.getLog());
    return 0;
  }

  @Override
  public List<List<String>> getHistoryData() {
    log.append("getHistoryData");
    return null;
  }

  /**
   * Returns the current log as a StringBuilder.
   *
   * @return the log
   */
  public StringBuilder getLog() {
    StringBuilder temp = new StringBuilder(this.log);
    this.log.setLength(0);
    return temp;
  }

}
