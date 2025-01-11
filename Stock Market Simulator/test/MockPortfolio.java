import java.io.IOException;

import model.BasicStock;
import model.PersonalPortfolio;

/**
 * A MockPortfolio is an instance of a portfolio which is
 * used to see if code paths are correct.
 */
class MockPortfolio extends PersonalPortfolio {
  StringBuilder log;
  MockStock stock;

  /**
   * Constructor for a MockPortfolio object with a log and a MockStock.
   *
   * @param name name of the stock
   * @throws IOException if the name of the stock doesn't have history
   */
  MockPortfolio(String name) throws IOException {
    super(name);
    this.log = new StringBuilder();
    stock = new MockStock("GOOG");
  }

  @Override
  public float valueOfPortfolio(String date) {
    this.log.append("valueOfPortfolio");
    stock.getStockPrice("", 1);
    this.log.append(stock.getLog());
    return 0;
  }

  @Override
  public String getName() {
    this.log.append("getName");
    return "";
  }

  @Override
  public void addStock(BasicStock stockName, int quantity) {
    this.log.append("addStock");
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
