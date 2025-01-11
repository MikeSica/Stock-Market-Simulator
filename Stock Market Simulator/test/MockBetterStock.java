import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;

import model.IBetterStock;

/**
 * A MockBetterStock is an instance of a BetterStock which is
 * used to see if code paths are correct.
 */
public class MockBetterStock extends MockStock implements IBetterStock {
  /**
   * Constructs a new BaseStock which sets the values
   * of financeHistory, and historyData.
   *
   * @param name the name of the stock
   * @throws MalformedURLException if url doesn't work when creating the financeHistory
   * @throws IOException           if there is no financialHistory for that stock
   */
  public MockBetterStock(String name) throws MalformedURLException, IOException {
    super(name);
  }

  @Override
  public String performanceOverTime(String startDate, String endDate) {
    this.log.append("StockPreformanceOverTimeCalled");
    return "";
  }

  @Override
  public float getStockPrice(String date, float quantity) {
    this.log.append("getStockPriceCalledStock");
    return 1;
  }

  @Override
  public float findPriceQuantity(float dollars, String date) {
    this.log.append("findPriceQuantityCalled");
    return 0;
  }

  @Override
  public ArrayList<String> availableDates(String startDate, String endDate) {
    this.log.append("availableDatesCalled");
    return null;
  }
}
