import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import model.IBetterUser;

/**
 * A MockBetterUser is an instance of a IBetterUser which is
 * used to see if code paths are correct.
 */
public class MockBetterUser extends MockUser implements IBetterUser {
  MockBetterPortfolio mPortfolio;

  /**
   * Constructor for a MockUser object with a log and a MockPortfolio.
   *
   * @throws IOException if a stock doesn't exist in the MockPortfolio
   */
  MockBetterUser() throws IOException {
    mPortfolio = new MockBetterPortfolio("greg");
  }

  @Override
  public String buy(String portfolioName, String stockName, int quantity, String date)
          throws IllegalArgumentException {
    this.mPortfolio.buyStock(stockName, quantity, date);
    this.log.append(mPortfolio.getLog());
    return "";
  }

  @Override
  public String sell(String portfolioName, String stockName, float quantity, String date)
          throws IllegalArgumentException {
    this.mPortfolio.sellStock(stockName, quantity, date);
    this.log.append(mPortfolio.getLog());
    return "";
  }

  @Override
  public String rebalance(String portfolioName, ArrayList<Float> parameters, String date)
          throws IllegalArgumentException {
    try {
      this.mPortfolio.rebalance(parameters, date);
      this.log.append(mPortfolio.getLog());
    } catch (IOException e) {
      this.log.append("rebalanceFailed");
    }
    return "";
  }

  @Override
  public String getCompAtDate(String portfolioName, String date) {
    this.mPortfolio.getCompAtDate(date);
    this.log.append(mPortfolio.getLog());
    return "";
  }

  @Override
  public String save(String portfolioName) throws IllegalArgumentException {
    try {
      this.mPortfolio.save();
      this.log.append(mPortfolio.getLog());

    } catch (IOException e) {
      this.log.append("Save Failed");
    }
    return "";
  }


  @Override
  public String load(String portfolioName) throws IllegalArgumentException {

    try {
      this.mPortfolio.load();
      this.log.append(mPortfolio.getLog());
    } catch (FileNotFoundException e) {
      this.log.append("Load Failed");
    }


    return "";
  }

  @Override
  public String getSavedPortfolios() {
    this.log.append("GetSavedPortfoliosCalled");
    return "";
  }

  @Override
  public String getPortfolioPerformance(String portfolioName, String startDate, String endDate)
          throws IllegalArgumentException {
    try {
      this.mPortfolio.performanceOverTime(startDate, endDate);
      this.log.append(mPortfolio.getLog());
      this.log.append("GetPortfolioPeformance");
    } catch (IOException e) {
      this.log.append("getPortfolioPreformanceFailed");
    }
    return "";
  }

  @Override
  public String getDistribution(String portfolioName, String date) throws IOException {
    try {
      this.mPortfolio.distribution(date);
      this.log.append(mPortfolio.getLog());
      this.log.append("getDistribution");

    } catch (IOException e) {
      this.log.append("getDisributionFailed");
    }
    return "";
  }

  @Override
  public String getSellableAtDate(String portfolioName, String date) {
    this.log.append("SellableCalled");
    return "";
  }


}
