import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import model.IBetterPortfolio;

/**
 * A MockBetterPortfolio is an instance of a BetterPortfolio which is
 * used to see if code paths are correct.
 */
class MockBetterPortfolio extends MockPortfolio implements IBetterPortfolio {
  MockBetterStock mStock;

  /**
   * Constructor for a MockPortfolio object with a log and a MockStock.
   *
   * @param name name of the stock
   * @throws IOException if the name of the stock doesn't have history
   */
  MockBetterPortfolio(String name) throws IOException {
    super(name);
    mStock = new MockBetterStock("goog");
  }

  @Override
  public List<List<String>> getCompAtDate(String date) {
    this.log.append("GetCompAtDateCalled");
    return null;
  }

  @Override
  public void buyStock(String stockName, int quantity, String date) {
    mStock.getStockPrice(date, quantity);
    this.log.append(mStock.getLog());
    this.log.append("BuyStockCalled");
  }

  @Override
  public void sellStock(String stockName, float quantity, String date) {
    mStock.getStockPrice(date, quantity);
    this.log.append(mStock.getLog());
    this.log.append("SellStockCalled");
  }

  @Override
  public void save() throws IOException {
    this.log.append("SaveCalled");
  }

  @Override
  public void load() throws FileNotFoundException {
    this.log.append("LoadCalled");
  }

  @Override
  public void rebalance(List<Float> parameters, String date) throws IOException {
    mStock.getStockPrice(date, 1);
    mStock.findPriceQuantity(1, date);
    this.sellStock("goog", 1, date);
    this.buyStock("goog", 1, date);
    this.log.append(mStock.getLog());
    this.log.append("RebalanceCalled");
  }

  @Override
  public String performanceOverTime(String startDate, String endDate) throws IOException {
    this.getCompAtDate(startDate);
    mStock.availableDates(startDate, endDate);
    this.log.append("preformanceOverTimeCalled");
    return "";
  }

  @Override
  public String distribution(String date) throws IOException {
    this.log.append("Distribution Called");
    return "";
  }

  @Override
  public List<List<String>> getSellable(String date) {
    this.log.append("Sellable Called");
    return List.of();
  }


}
