package model;

import java.util.HashMap;
import java.util.Map;

/**
 * This class represents a PersonalPortfolio that implements IPortfolio.
 * It has a name and efficiently represents owned stocks with a HashMap.
 */
public class PersonalPortfolio implements IPortfolio {
  private Map<BasicStock, Float> ownedStocks;

  private String portfolioName;

  /**
   * Constructor for personal portfolio: takes in the name and sets an empty list of owned stocks.
   *
   * @param portfolioName the name of this portfolio
   */
  public PersonalPortfolio(String portfolioName) {
    this.portfolioName = portfolioName;
    this.ownedStocks = new HashMap<BasicStock, Float>();
  }

  /**
   * Gets the value of the portfolio by adding up all stocks and quantities.
   *
   * @param date the date of the value you want
   * @return a float of the value
   */
  @Override
  public float valueOfPortfolio(String date) {
    float totalPrice = 0;
    for (BasicStock currentStock : ownedStocks.keySet()) {
      totalPrice += currentStock.getStockPrice(date, ownedStocks.get(currentStock));
    }
    return totalPrice;
  }

  /**
   * Gets the name of this portfolio.
   *
   * @return a string of the name
   */
  @Override
  public String getName() {
    return this.portfolioName;
  }

  @Override
  public void addStock(BasicStock stockName, int quantity) {
    this.ownedStocks.put(stockName, (float) quantity);
  }


}
