package model;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

/**
 * The Better Stock with functions for performance over time, available dates, and price.
 * Also includes functions implemented in BaseStock.
 */
public class BetterStock extends BaseStock implements IBetterStock {
  private String ticker;
  private FinancialHistory financeHistory;
  private List<List<String>> historyData;
  private IValidate validater;

  /**
   * Constructs a new BaseStock which sets the values
   * of financeHistory, and historyData.
   *
   * @param name the name of the stock
   * @throws MalformedURLException if url doesn't work when creating the financeHistory
   * @throws IOException           if there is no financialHistory for that stock
   */
  public BetterStock(String name) throws MalformedURLException, IOException {
    super(name);
    this.financeHistory = new FinancialHistory(name);
    this.historyData = this.financeHistory.getStockHistory();
    this.ticker = name;
    this.validater = new Validate();
  }

  /**
   * Gives a String representation of a stocks performance over an interval performance.
   *
   * @param startDate the start date of the interval
   * @param endDate   the end date of the interval
   * @return the data in the format of a chart
   * @throws IllegalArgumentException if the start date comes after or is the same as the end date
   */
  @Override
  public String performanceOverTime(String startDate, String endDate)
          throws IllegalArgumentException {
    this.validateDates(startDate, endDate);

    int startInt = this.financeHistory.getDatePlace(startDate);
    int endInt = this.financeHistory.getDatePlace(endDate);
    float starVal = this.getStarValue(startInt, endInt);
    Range range = getRange(startDate, endDate);
    List<List<String>> starQuantity = getStars(startInt, endInt, range, starVal);
    String output = graphHelper(starQuantity, starVal, startDate, endDate);
    return output;
  }

  @Override
  public float findPriceQuantity(float dollars, String date)
          throws IllegalStateException, IllegalArgumentException {
    if (dollars < 0) {
      throw new IllegalStateException("Can't have negative dollar amount");
    }
    this.validater.validateDate(date, this.getName());
    return (dollars / this.financeHistory.getPriceAtClosing(date));
  }

  @Override
  public List<String> availableDates(String startDate, String endDate)
          throws IllegalArgumentException {
    this.validateDates(startDate, endDate);
    int indexStart = this.financeHistory.getDatePlace(startDate);
    int indexEnd = this.financeHistory.getDatePlace(endDate);
    List<String> dates = new ArrayList<String>();
    for (int i = indexEnd; i <= indexStart; i++) {
      String date = this.historyData.get(i).get(0);
      dates.add(date);
    }
    return dates;
  }

  /**
   * Gets the value of a star based on the start and end integers.
   * Gets the average and then gets that divided by 10 and rounded.
   * Returns the value
   *
   * @param start the start int(dateplace of the start date)
   * @param end   the end int(dateplace of the end date)
   * @return a flaot of the star value
   */
  private float getStarValue(int start, int end) {
    float price = getAverage(start, end);
    float value = Math.abs(price / 10);
    String val = "" + Math.round(value);
    String newVal = "" + val.charAt(0);
    for (int i = 1; i < val.length(); i++) {
      newVal += 0;
    }
    return Float.parseFloat(newVal);
  }


  /**
   * Changes an List of the stocks and their stars at dates to a readable chart.
   *
   * @param starQuantity a list of the stocks and their stars at certain dates
   * @param starVal      the value of the star
   * @param start        the start date
   * @param end          the end date
   * @return a string that ouputs as a readable chart.
   */
  private String graphHelper(List<List<String>> starQuantity, float starVal, String start,
                             String end) {
    StringBuilder output = new StringBuilder("Performance of Stock " + ticker + " from " + start
            + " to " + end + "\n\n");
    for (int i = starQuantity.size() - 1; i >= 0; i--) {
      List<String> strings = starQuantity.get(i);
      output.append(strings.get(0)).append(": ");
      int stars = Integer.parseInt(strings.get(1));
      for (int j = 0; j < stars; j++) {
        output.append("*");
      }
      output.append("\n");
    }
    output.append("\nScale: * = ").append(starVal);
    return output.toString();
  }

  /**
   * Gets the range as day, week, month, etc.
   * Gets how many days are within the dates and from that returns a range.
   *
   * @param startDate the start date
   * @param endDate   the end date
   * @return a Range of the range
   */
  private Range getRange(String startDate, String endDate) {

    Range range = Range.DAY;
    int indexStart = this.financeHistory.getDatePlace(startDate);
    int indexEnd = this.financeHistory.getDatePlace(endDate);
    if ((indexStart - indexEnd) >= 30) {
      range = Range.WEEK;
    }
    if ((indexStart - indexEnd) >= 150) {
      range = Range.MONTH;
    }
    if ((indexStart - indexEnd) >= 1800) {
      range = Range.YEAR;
    }
    return range;
  }

  /**
   * Gets a string of the amount of stars for specific dates for the graph.
   *
   * @param startInt the place of the date in the data of the start date
   * @param endInt   the place of the date in the data of the end date
   * @param range    the range we are working with (day, week, month)
   * @param starVal  the value of the stars
   * @return a list that has the amount of stars for a given period of time
   */
  private List<List<String>> getStars(int startInt, int endInt, Range range, float starVal) {
    int stepper = validater.getStepper(range);
    List<List<String>> result = new ArrayList<>();
    for (int i = endInt; i <= startInt; i += stepper) {
      List<String> tempAdder = new ArrayList<String>();
      String starDate = this.historyData.get(i).get(0);
      tempAdder.add(starDate);
      float star = this.financeHistory.getPriceAtClosing(starDate);
      int stars = 0;
      while (starVal < star && stars < 50) {
        stars++;
        star -= starVal;
      }
      if (stars == 0 && star != 0) {
        stars = 1;
      }
      tempAdder.add("" + stars);
      result.add(tempAdder);
      if (i == startInt) {
        break;
      }
      if (i + stepper > startInt) {
        i = startInt - stepper;
      }
    }
    return result;
  }

  /**
   * Gets the average closing price between two indicies.
   *
   * @param startInt the starting index
   * @param endInt   the ending index
   * @return the average closing price between the two
   */
  private float getAverage(int startInt, int endInt) {
    float total = 0;
    for (int i = endInt; i <= startInt; i++) {
      total += this.getClosingBasedOnIndex(i);
    }
    if (startInt == endInt) {
      return total;
    }
    return total / (startInt - endInt);
  }

  /**
   * Gets the closing price based on the index.
   *
   * @param i the index
   * @return the closing price for that index
   */
  private float getClosingBasedOnIndex(int i) {
    return Float.parseFloat(this.historyData.get(i).get(4));
  }

  /**
   * Makes sure the dates are valid and work for this stock.
   * Meaening the start date comes before the end date.
   * And there is data for both dates.
   *
   * @param startDate the start date
   * @param endDate   the end date
   */
  private void validateDates(String startDate, String endDate) {
    if (startDate.compareTo(endDate) > 0) {
      throw new IllegalArgumentException("Start date can't be after end date");
    }
    this.validater.validateDate(startDate, this.getName());
    this.validater.validateDate(endDate, this.getName());
  }
}
