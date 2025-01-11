package model;

/**
 * Helper class to contains functions for validating inputs.
 * Validate dates, tickers, flaots, ints, and steppers.
 */
public interface IValidate {
  /**
   * Checks that the date is in proper format and there is data for it.
   *
   * @param date         the date we are validating
   * @param tickerSymbol the ticker symbol for the stock history
   * @throws IllegalArgumentException when date is invalid
   */
  public void validateDate(String date, String tickerSymbol) throws IllegalArgumentException;

  /**
   * Checks that the ticker is valid and has data.
   *
   * @param tickerSymbol the tickerSymbol
   * @throws IllegalArgumentException if the tickerSymbol doesn't work
   */
  public void validateTicker(String tickerSymbol) throws IllegalArgumentException;

  /**
   * Checks that float isn't negative and is an integer.
   *
   * @param i the float
   * @throws IllegalArgumentException if the float isn't an valid integer or is negative;
   */
  public void validateInteger(Float i) throws IllegalArgumentException;

  /**
   * Checks that the start date is before the end date.
   * Also checks that they work with the given ticker symbol and history.
   *
   * @param startDate    the start date
   * @param endDate      the end date
   * @param tickerSymbol the ticker symbol
   * @throws IllegalArgumentException if theygit p do not pass the requirements for start/end dates
   */
  public void validateStartEnd(String startDate,
                               String endDate, String tickerSymbol)
          throws IllegalArgumentException;

  /**
   * Gets the stepper value based on the range.
   * Signifies how fast we sort through a list of days.
   *
   * @param range the range in WEEK, MONTh, YEAR, DAT
   * @return an int of how fast we sort through a list
   */
  public int getStepper(Range range);

}
