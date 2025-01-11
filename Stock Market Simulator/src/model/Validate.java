package model;

import java.io.IOException;

/**
 * Helper class that contains methods to help validate inputs(Dates, steppers, Integers)
 * and implements IValidate.
 */
public class Validate implements IValidate {

  @Override
  public void validateDate(String date, String tickerSymbol) throws IllegalArgumentException {
    if (date.length() != 10 || !date.substring(4, 5).equals("-")
            || !date.substring(7, 8).equals("-")) {
      throw new IllegalArgumentException("Invalid date");
    }
    try {
      BasicStock test = new BaseStock(tickerSymbol);
      test.getStockPrice(date, 1);
    } catch (IOException e) {
      throw new IllegalArgumentException("Invalid date");
    }
  }

  @Override
  public void validateTicker(String tickerSymbol) throws IllegalArgumentException {
    try {
      BasicStock test = new BaseStock(tickerSymbol);
    } catch (IOException e) {
      throw new IllegalArgumentException("Invalid date");
    }
  }

  @Override
  public void validateInteger(Float i) throws IllegalArgumentException {
    if (i == null || i <= 0 || i % 1 != 0) {
      throw new IllegalArgumentException("Invalid number");
    }
  }

  @Override
  public void validateStartEnd(String startDate,
                               String endDate, String tickerSymbol)
          throws IllegalArgumentException {
    this.validateDate(startDate, tickerSymbol);
    this.validateDate(endDate, tickerSymbol);
    try {
      IHistory test = new FinancialHistory(tickerSymbol);
      int date1 = test.getDatePlace(startDate);
      int date2 = test.getDatePlace(endDate);
      if (date2 > date1) {
        throw new IOException("dates don't work");
      }
    } catch (IOException e) {
      throw new IllegalArgumentException("Invalid start/end dates");
    }
  }

  /**
   * From the range, gets the stepper which is how we are
   * going to iterate through the list in getStars.
   *
   * @param range a string of the range
   * @return the stepper
   */
  public int getStepper(Range range) {
    int stepper = 1;
    String rangeHelp = range.toString();
    switch (rangeHelp) {
      case ("WEEK"):
        stepper = 5;
        break;
      case ("MONTH"):
        stepper = 23;
        break;
      case ("YEAR"):
        stepper = 276;
        break;
      default:
        break;
    }
    return stepper;
  }
}
