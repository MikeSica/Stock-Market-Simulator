package model;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

/**
 * This class represents a BasicStock object.
 * The BaseStock implements the BasicStock interface and
 * has a financialHistory and history of the stock's data.
 */
public class BaseStock implements BasicStock {
  private String ticker;
  private FinancialHistory financeHistory;
  private List<List<String>> historyData;


  /**
   * Constructs a new BaseStock which sets the values
   * of financeHistory, and historyData.
   *
   * @param name the name of the stock
   * @throws MalformedURLException if url doesn't work when creating the financeHistory
   * @throws IOException           if there is no financialHistory for that stock
   */
  public BaseStock(String name) throws MalformedURLException, IOException {
    this.financeHistory = new FinancialHistory(name);
    this.historyData = this.financeHistory.getStockHistory();
    this.ticker = name;

  }

  /**
   * Calculates the change in stock price between the start date and end date and returns the
   * difference as a float.
   *
   * @param startDate the start date for the calculation
   * @param endDate   the end date for the calculation
   * @return the change in stock price between the start date and end date
   */
  @Override
  public float stockChange(String startDate, String endDate) {
    float startPrice = this.financeHistory.getPriceAtClosing(startDate);
    float endPrice = this.financeHistory.getPriceAtClosing(endDate);
    return endPrice - startPrice;
  }

  /**
   * Calculates the moving average of the stock price for the specified number of days
   * ending on the given date.
   *
   * @param givenDate the end date for the moving average calculation
   * @param num       the number of days over which to calculate the moving average
   * @return the moving average of the stock price
   * @throws IllegalArgumentException if the date does not have stock information
   *                                  or the quantity is negative or too large for
   *                                  the amount of available days.
   */
  @Override
  public float movingAverage(String givenDate, int num)
          throws IllegalArgumentException {
    float totalPrices = 0;
    int datePlace = this.financeHistory.getDatePlace(givenDate);
    List<String> dates = new ArrayList<>();
    String helpDate;
    for (int i = 0; i < num; i++) {
      if ((i + datePlace) >= this.historyData.size()) {
        throw new IllegalArgumentException("Input a Smaller Quantity");
      }
      helpDate = (this.historyData.get(datePlace + i).get(0));
      totalPrices += this.financeHistory.getPriceAtClosing(helpDate);
    }
    if (num <= 0) {
      throw new IllegalArgumentException("Input a valid positive number");
    }
    return totalPrices / num;
  }

  /**
   * Identifies the days within the specified date range where the moving average
   * of the stock price for the specified number of days is greater than the
   * closing price on that day.
   *
   * @param startDate the start date of the range
   * @param endDate   the end date of the range
   * @param x         the number of days over which to calculate the moving average
   * @return a string containing the crossover days
   */
  @Override
  public String crossOverDays(String startDate, String endDate, int x)
          throws IllegalArgumentException {
    StringBuilder crossoverDays = new StringBuilder();
    int initialDate = this.financeHistory.getDatePlace(startDate);
    int endingDate = this.financeHistory.getDatePlace(endDate);
    int dateRange = initialDate - endingDate;
    int datePlace = this.financeHistory.getDatePlace(endDate);
    String helpDate;
    for (int i = dateRange; i > 0; i--) {
      helpDate = (this.historyData.get(datePlace + i).get(0));
      try {
        if (movingAverage(helpDate, x) < this.financeHistory.getPriceAtClosing(helpDate)) {
          crossoverDays.append(helpDate).append(System.lineSeparator());
        }
      } catch (IllegalArgumentException e) {
        throw new IllegalArgumentException("Quantity is too big please input a smaller quantity");
      }
    }

    return crossoverDays.toString();
  }

  /**
   * Calculates the total stock price for the given date and quantity of stocks.
   * Gets the price at closing from financeHistory and multiplies it by quantity.
   *
   * @param date     the date for which to retrieve the stock price
   * @param quantity the quantity of stocks
   * @return the total stock price for the given date and quantity
   */
  @Override
  public float getStockPrice(String date, float quantity) {
    return this.financeHistory.getPriceAtClosing(date) * quantity;
  }

  /**
   * Retrieves the historical stock data.
   * Returns a copy of the history data in this class.
   *
   * @return an List of List of Strings containing the historical stock data
   */
  @Override
  public List<List<String>> getHistoryData() {
    List<List<String>> result = new ArrayList<>();
    result.addAll(this.historyData);
    return result;
  }

  /**
   * Gets the name of the stock ticker as a string.
   *
   * @return the name of the stock
   */
  @Override
  public String getName() {
    return ticker;
  }
}
