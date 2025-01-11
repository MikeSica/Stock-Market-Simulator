import java.io.IOException;
import java.util.ArrayList;

import model.User;

/**
 * A MockUser is an instance of a User which is
 * used to see if code paths are correct.
 */
class MockUser extends User {
  MockPortfolio portfolio;
  StringBuilder log;

  /**
   * Constructor for a MockUser object with a log and a MockPortfolio.
   *
   * @throws IOException if a stock doesn't exist in the MockPortfolio
   */
  MockUser() throws IOException {
    this.log = new StringBuilder();
    portfolio = new MockPortfolio("pol");
  }

  @Override
  public void addPortfolio(String name) {
    this.log.append("addPortfolio");
  }

  @Override
  public void updatePortfolio(String name, String tickerSymbol, int quantity) throws IOException {
    this.log.append("updatePortfolio");
  }

  @Override
  public ArrayList<String> getPortfolios() {
    this.log.append("getPortfolios");
    return null;
  }

  @Override
  public float getValueOfPortfolio(String name, String date) {
    this.log.append("getValueOfPortfolio");
    portfolio.valueOfPortfolio("");
    this.log.append(portfolio.getLog());
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
