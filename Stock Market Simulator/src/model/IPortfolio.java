package model;

import java.io.IOException;

/**
 * The interface IPortfolio defines methods that must be implemented
 * in classes which require a portfolio. These methods include retrieving
 * the value of a portfolio, getting the name of a portfolio
 * and adding stocks to a portfolio.
 */
public interface IPortfolio {

  /**
   * Gets the value of the portfolio by adding up all stocks and quantities.
   *
   * @param date the date of the value you want
   * @return a float of the value
   * @throws IllegalArgumentException if the date is not a valid trading day for the portfolio
   */
  float valueOfPortfolio(String date) throws IllegalArgumentException;

  /**
   * Gets the name of this portfolio.
   *
   * @return a string of the name
   */
  String getName();

  /**
   * Adds a stock to the stock list in the portfolio.
   *
   * @param stockName the name of the stock
   * @param quantity  the amount of stocks bought
   * @throws IOException if the stock doesn't exist or the quantity is negative
   */
  void addStock(BasicStock stockName, int quantity) throws IOException;

}
