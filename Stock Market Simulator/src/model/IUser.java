package model;

import java.io.IOException;
import java.util.List;

/**
 * Represents an IUser interface so that types of users may be updated later.
 */
public interface IUser {
  /**
   * Adds a blank portfolio to the users portfolios.
   *
   * @param name the name of the portfolio
   */
  public void addPortfolio(String name);

  /**
   * Updates a portfolio by adding a certain quantity of a given stock to the portfolio.
   *
   * @param name         the name of the portfolio
   * @param tickerSymbol the name of the stock ticker
   * @param quantity     amount of stock to add
   */
  public void updatePortfolio(String name, String tickerSymbol, int quantity) throws IOException;

  /**
   * returns an Array List containing all the users portfolio names.
   *
   * @return the users portfolios names
   */
  public List<String> getPortfolios();



  /**
   * Gets the value of a user's portfolio at a date.
   *
   * @param name of the users portfolio to get the value of
   * @param date the date to get the value of the portfolio on
   * @return the value of the that portfolio on a specific date
   * @throws IllegalArgumentException if the date is not a trading day or the portfolio name
   *                                  is invalid.
   */
  public float getValueOfPortfolio(String name, String date) throws IllegalArgumentException;
}
