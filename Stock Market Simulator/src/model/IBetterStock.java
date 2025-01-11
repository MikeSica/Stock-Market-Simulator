package model;

import java.util.List;

/**
 * A stock interface with added functionality than BasicStock.
 * Adds a performance over time functionality.
 * Adds functionality to find the stock quantity of a price.
 * Can get the trading days between two days.
 * As well as all functionality in BasicStock.
 */
public interface IBetterStock extends BasicStock {

  /**
   * Gives a String representation of a stocks performance over an interval performance.
   *
   * @param startDate the start date of the interval
   * @param endDate   the end date of the interval
   * @return the data in the format of a chart
   */
  String performanceOverTime(String startDate, String endDate);

  /**
   * Finds the amount a certain amount of dollars can buy.
   *
   * @param dollars the dollars you are using to buy the stock
   * @param date    the date of the stock
   * @return the amount of shares the dollars is
   */
  float findPriceQuantity(float dollars, String date);

  /**
   * Gets all the available trading days within a specified range.
   *
   * @param startDate the start of the range
   * @param endDate   the end of the range
   * @return the available dates as an arrayList of String
   */
  List<String> availableDates(String startDate, String endDate);
}
