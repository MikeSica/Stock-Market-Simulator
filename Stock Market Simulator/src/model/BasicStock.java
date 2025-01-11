package model;

import java.util.List;

/**
 * The BaseStock interface contains methods that must
 * be contained in classes that implement the interface ensuring
 * that operations such as calculating the stock change, calculating a stocks x-day
 * moving average, returning the crossover days for a range of days, getting the stock price
 * are available across different model.BaseStock implementations.
 */
public interface BasicStock {

  /**
   * Calculates the difference between the value of a stock price between 2 dates(YYYY-MM-DD).
   *
   * @param startDate the date to get the initial value of the stock on
   * @param endDate   the date to get the ending value of the stock on
   * @return the value of the stock on the endDate minus that of the stock value on the startDate
   */
  float stockChange(String startDate, String endDate);

  /**
   * Calculates the x-day moving average of a stock given a date and x.
   *
   * @param givenDate the date to find the moving average of the stock on
   * @param num       the amount of days to find the moving average of
   * @return the x-day moving average of the stock on that day
   */
  float movingAverage(String givenDate, int num);

  /**
   * Gets the dates that are crossover days over a specified date range by finding
   * the value of the stock on each day in the date range and comparing it to
   * the value of its x-day moving average.
   *
   * @param startDate the beginning date of the range
   * @param endDate   the ending date of the range
   * @param x         the amount of days to find the moving average of for each day in the interval
   * @return the days that are crossover days.
   */
  String crossOverDays(String startDate, String endDate, int x);

  /**
   * Gets the value of a certain amount of stocks on a given date.
   *
   * @param date     the date to find the value of the stocks on
   * @param quantity the quantity of stocks
   * @return the value of stock price on the given date multiplied by the quantity of stocks
   */
  float getStockPrice(String date, float quantity);

  /**
   * Gets the historical data as an arraylist of arraylist string for this stock.
   *
   * @return an arraylist of arraylist string of the data
   */
  List<List<String>> getHistoryData();

  /**
   * Gets the name of the stock ticker as a string.
   * @return the name of the stock
   */
  String getName();
}