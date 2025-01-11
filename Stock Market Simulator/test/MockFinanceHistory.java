import java.io.IOException;
import java.util.List;

import model.FinancialHistory;

/**
 * A MockFinanceHistory is an instance of a FinanceHistory which is
 * used to see if code paths are correct.
 */
class MockFinanceHistory extends FinancialHistory {
  StringBuilder log;

  /**
   * Constructor for a MockFinanceHistory object with a log.
   *
   * @param name name of the stock
   * @throws IOException if the name of the stock doesn't have history
   */
  MockFinanceHistory(String name) throws IOException {
    super(name);
    log = new StringBuilder();
  }


  @Override
  public float getPriceAtOpening(String date) {
    log.append("getPriceAtOpening");
    return 0;
  }


  @Override
  public float getPriceAtClosing(String date) {
    log.append("getPriceAtClosing");
    return 0;
  }

  @Override
  public float getHighestPrice(String date) {
    log.append("getHighestPrice");
    return 0;
  }

  @Override
  public float getLowestPrice(String date) {
    log.append("getLowestPrice");
    return 0;
  }

  @Override
  public float volumeofTrade(String date) {
    log.append("volumeOfTrade");
    return 0;
  }

  @Override
  public List<List<String>> getStockHistory() {
    log.append("getStockHistory");
    return null;
  }

  @Override
  public int getDatePlace(String date) {
    log.append("getDatePlace");
    return 0;
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
