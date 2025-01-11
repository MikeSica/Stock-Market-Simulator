package model;

import java.io.IOException;
import java.util.ArrayList;

/**
 * A user class that has extended functionality than IUser.
 * Contains functions to buy and sell stock.
 * Contains functions to rebalance a portfolio.
 * Contains functionality to get the composition of a portfolio at a date.
 * Can get a chart of a portfolio's performance.
 * Can get a distribution of a portfolio.
 * Can return a list of portfolios the user has saved.
 * AS well as all functionality in IUser.
 */
public interface IBetterUser extends IUser {
  /**
   * Buys the stock for the given portfolio.
   *
   * @param portfolioName the name of the portfolio
   * @param stockName     the name of the stock
   * @param quantity      the quantity bought
   * @param date          the date
   * @return a string of if it worked
   * @throws IllegalArgumentException if the portfolio.buy fails for any reason
   */
  String buy(String portfolioName, String stockName, int quantity, String date)
          throws IllegalArgumentException;

  /**
   * Gets the portfolio and sells the stock from it if it can.
   *
   * @param portfolioName the name of the portfolio
   * @param stockName     the name of the stock
   * @param quantity      the quantity
   * @param date          the date when you are selling
   * @return a string of the stock being sold
   * @throws IllegalArgumentException if the portfolio.sell fails for any reason
   */
  String sell(String portfolioName, String stockName, float quantity, String date)
          throws IllegalArgumentException;

  /**
   * Rebalances the portfolio in accordance with the paramteters.
   *
   * @param portfolioName the name of the portfolio
   * @param parameters    the parameters in numbers of the percentages
   * @param date          the date when we are doing the rebalancing
   * @return a string of the data of the reabalanced portfoliio
   * @throws IllegalArgumentException if the portfolio.rebalancing fails for any reason
   */
  String rebalance(String portfolioName, ArrayList<Float> parameters, String date)
          throws IllegalArgumentException;


  /**
   * Gets the composition of the portfolio at the date.
   * Gets the portfolio and calls the getCompAtDate function on it.
   *
   * @param portfolioName the name of the portfolio
   * @param date          the date of the portfolio
   * @return the composition of the portfolio
   * @throws IllegalArgumentException if the date or portfolio is invalid.
   */
  String getCompAtDate(String portfolioName, String date) throws IllegalArgumentException;

  /**
   * Finds the portfolio and calls the save function on it.
   *
   * @param portfolioName the name of the portfolio
   * @return a string that the save function passed
   * @throws IllegalArgumentException if the portfolio.save function fails for any reason.
   */
  String save(String portfolioName)
          throws IllegalArgumentException;

  /**
   * Loads a saved portfolio.
   * Gets the portfolio that has that name and calls the load function on it to retrieve it.
   *
   * @param portfolioName the name of the portfolio
   * @return a string of if it passed
   * @throws IllegalArgumentException if the load function
   *                                  fails due that portfolio not existing
   *                                  in the folder of saved portfolios
   */
  String load(String portfolioName)
          throws IllegalArgumentException;

  /**
   * Returns a long String of the list of the saved portfolios.
   *
   * @return a list of the saved portfolios
   */
  String getSavedPortfolios();

  /**
   * Gets the portfolio from the portfolioName.
   * Does the preformance over time method to get a string of the chart.
   *
   * @param portfolioName the name of the portfolio
   * @param startDate     the start date
   * @param endDate       the end date
   * @return a string of the chart
   * @throws IllegalArgumentException if the method finds invalid start/end dates
   */
  String getPortfolioPerformance(String portfolioName, String startDate, String endDate)
          throws IllegalArgumentException;

  /**
   * Gets the distribution of the portfolio with the portfolioName.
   * calls the distribution function on the portfolio.
   *
   * @param portfolioName the name of the portfolio
   * @param date          the date we are getting the distribution at
   * @return a string of the portfolio distribution
   * @throws IOException if the portfolio has a stock that doesn't exist
   */
  String getDistribution(String portfolioName, String date) throws IOException;

  /**
   * Gets all the stocks you can sell at a certain date.
   *
   * @param portfolioName the name of the portfolio
   * @param date          the date we want to sell
   * @return the composition of the stocks you can sell in a readable string
   * @throws IllegalArgumentException if a portfolio with that name doesn't exist or the date is an
   *                                  invalid trading day
   */
  String getSellableAtDate(String portfolioName, String date) throws IllegalArgumentException;

}
