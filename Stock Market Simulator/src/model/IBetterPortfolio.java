package model;


import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

/**
 * A portfolio with more than basic functionality.
 * Contains functions for getting the composition.
 * Can buy and sell stock.
 * Can load and save portfolios.
 * As well as performance over tiem and distribution.
 */
public interface IBetterPortfolio extends IPortfolio {
  /**
   * Gets the composition at the date of the portfolio.
   *
   * @param date the date
   * @return an arraylist of the list of the stock name and quantity
   */
  public List<List<String>> getCompAtDate(String date);

  /**
   * Buys a stock at the end of the day.
   * Adds it to stocks with a null end date.
   *
   * @param stockName the name of the stock
   * @param quantity  the quantity
   * @param date      the start date of the buy
   */
  void buyStock(String stockName, int quantity, String date);

  /**
   * Sells teh stock at the end of the day.
   *
   * @param stockName the name of the stock
   * @param quantity  the quantity to be sold
   * @param date      the date we are selling
   */
  void sellStock(String stockName, float quantity, String date);

  /**
   * Saves the portfolio to savedPortfolios as the inputted name.
   *
   * @throws IOException if the FileWriter fails
   */
  void save() throws IOException;

  /**
   * Changes the data in the portfolio to match the file you loaded.
   *
   * @throws IllegalArgumentException if the file does not exist
   */
  void load() throws IllegalArgumentException, FileNotFoundException;

  /**
   * Rebalances the portfolio in accordance with the parameters.
   *
   * @param parameters a list of percentages that correspond to the portfolio stocks.
   * @param date       the date we are rebalancing at
   * @throws IOException if the program cannot find the total value of a stock at the date
   */
  void rebalance(List<Float> parameters, String date) throws IOException;

  /**
   * Gives a String representation of a stocks performance over an interval performance.
   *
   * @param startDate the start date of the interval
   * @param endDate   the end date of the interval
   * @return the data in the format of a chart
   */
  String performanceOverTime(String startDate, String endDate) throws IOException;

  /**
   * Displays the distribution of the portfolio (percentage composition of it).
   * As a string.
   *
   * @param date the date we want the distribution
   * @return a readable string of the distribution
   * @throws IOException if we cannot find the data for a stock
   */
  String distribution(String date) throws IOException;

  /**
   * Gets the sellable stocks by checking that the end date is null and it is after the date.
   *
   * @param date the date in question
   * @return a list of sellable stocks
   * @throws IllegalArgumentException if the date is invalid
   */
  List<List<String>> getSellable(String date) throws IllegalArgumentException;
}
