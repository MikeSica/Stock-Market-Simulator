package model;

import java.util.List;

/**
 * The interface IHistory defines methods that must be implemented
 * in classes which retrieve stock data. These methods include retrieving
 * prices at different times, trade volumes, and the entire stock history.
 */
public interface IHistory {

  /**
   * Retrieves the opening price of the stock for the given date.
   *
   * @param date the date for which to retrieve the opening price.
   * @return the opening price of the stock on the given date.
   */
  public float getPriceAtOpening(String date);

  /**
   * Retrieves the closing price of the stock for the given date.
   *
   * @param date the date for which to retrieve the closing price.
   * @return the closing price of the stock on the given date.
   */
  public float getPriceAtClosing(String date);

  /**
   * Retrieves the highest price of the stock for the given date.
   *
   * @param date the date for which to retrieve the highest price.
   * @return the highest price of the stock on the given date.
   */
  public float getHighestPrice(String date);

  /**
   * Retrieves the lowest price of the stock for the given date.
   *
   * @param date the date for which to retrieve the lowest price.
   * @return the lowest price of the stock on the given date.
   */
  public float getLowestPrice(String date);

  /**
   * Retrieves the volume of trades for the stock on the given date.
   *
   * @param date the date for which to retrieve the trade volume.
   * @return the volume of trades for the stock on the given date.
   */
  public float volumeofTrade(String date);

  /**
   * Retrieves the entire stock history as an ArrayList of ArrayList of Strings.
   * Each inner ArrayList represents a row of stock data.
   *
   * @return an ArrayList of ArrayList of Strings containing the stock history.
   */
  public List<List<String>> getStockHistory();

  /**
   * Retrieves the index of the date in the stock data.
   *
   * @param date the date for which to retrieve the index.
   * @return the index of the date in the stock data.
   */
  public int getDatePlace(String date);
}
