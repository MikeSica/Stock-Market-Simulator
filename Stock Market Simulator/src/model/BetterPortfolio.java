package model;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

/**
 * A BetterPortfolio class that is used to store stock
 * data as well as well as stock history(regulated by time) in the portfolio.
 */
public class BetterPortfolio extends PersonalPortfolio implements IBetterPortfolio {
  private List<List<String>> stocks;
  private String name;
  private Validate validater;

  /**
   * Constructor for BetterPortfolio that sets this stocks equal to 0.
   *
   * @param name the name of the portfolio
   */
  public BetterPortfolio(String name) {
    super(name);
    this.name = name;
    this.stocks = new ArrayList<>();
    this.validater = new Validate();
  }

  /**
   * Gets the value of this porfolio at a given date by getting the composition first.
   * Then gets the value of that.
   *
   * @param date the date of the value you want
   * @return a float of the value.
   * @throws IllegalArgumentException if the date is not a valid trading day
   */
  @Override
  public float valueOfPortfolio(String date) throws IllegalArgumentException {
    this.validateDate(date);
    List<List<String>> valueStocks = this.getCompAtDate(date);
    float result = 0;
    for (int i = 0; i < valueStocks.size(); i++) {
      float quantity = Float.parseFloat(valueStocks.get(i).get(1));
      String stockName = valueStocks.get(i).get(0);
      float adder = this.getValueFromName(stockName, date, quantity);
      result += adder;
    }
    return result;
  }

  /**
   * Gets the value of the stock with the quantity at the date.
   *
   * @param stockName the name of the stock
   * @param date      the date at which we are getting the stock price
   * @param quantity  the quantity of stocks
   * @return the value at that date
   */
  private float getValueFromName(String stockName, String date, float quantity) {
    try {
      IBetterStock stick = new BetterStock(stockName);
      return stick.getStockPrice(date, quantity);
    } catch (IOException e) {
      return 0;
    }
  }

  /**
   * Finds the composition of this portfolio at a certain date.
   *
   * @param date the date given
   * @return an arraylist of arraylist string with the stockname and the quantity
   */
  public List<List<String>> getCompAtDate(String date) {
    this.validateDate(date);
    List<List<String>> stocksAtDate;
    stocksAtDate = stocksAtDateHelp(date);
    stocksAtDate = this.compressComposition(stocksAtDate);
    return stocksAtDate;
  }

  /**
   * Helper for getCompAtDate.
   * Gets all the stocks that are active at the date.
   *
   * @param date teh date
   * @return a list of lists of string with stockname, start, end, quantity
   * @throws IllegalArgumentException if date is invalid
   */
  private List<List<String>> stocksAtDateHelp(String date)
          throws IllegalArgumentException {
    List<List<String>> stocksAtDate = new ArrayList<>();
    for (int i = 0; i < stocks.size(); i++) {
      if (this.compareDates(date, stocks.get(i).get(1))
              && this.compareDates(stocks.get(i).get(2), date)) {
        ArrayList<String> adder = new ArrayList<String>();
        adder.add(stocks.get(i).get(0));
        adder.add(stocks.get(i).get(1));
        adder.add(stocks.get(i).get(2));
        adder.add(stocks.get(i).get(3));
        stocksAtDate.add(adder);
      }
    }
    return stocksAtDate;
  }

  /**
   * Adds a stock with the start date to the list of stocks and their data.
   *
   * @param stockName the name of the stock
   * @param quantity  the quantity of the stock
   * @param date      the start date of the stock
   * @throws IllegalArgumentException if the inputs don't work
   */
  @Override
  public void buyStock(String stockName, int quantity, String date)
          throws IllegalArgumentException {
    stockName = stockName.toUpperCase();
    validater.validateDate(date, stockName);
    date = this.findNextDate(stockName, date);
    ArrayList<String> stockData = new ArrayList<String>();
    this.checkWorkingStock(stockName, date);
    stockData.add(stockName);
    this.checkBuyableQuantity(quantity);
    String quan = Float.toString(quantity);
    stockData.add(date);
    stockData.add(null);
    stockData.add(quan);
    this.stocks.add(stockData);

  }

  /**
   * Finds the next available date for the stock.
   * Creates an IHistory, gets the date place, adds 1 to it, gets that date.
   * Works for everything except the current day.
   *
   * @param stockName the name of the stock
   * @param date      the date
   * @return the next working day
   */
  private String findNextDate(String stockName, String date) {
    try {
      IHistory his = new FinancialHistory(stockName);
      int newDate = his.getDatePlace(date);
      return his.getStockHistory().get(newDate).get(0);
    } catch (MalformedURLException e) {
      throw new RuntimeException(e);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public String distribution(String date) throws IOException {
    if (stocks.size() == 0) {
      return "No Stocks Are Owned At This Date";
    }
    validateDate(date);
    return distributionMessage(date).toString();
  }

  /**
   * Makes a stringbuilder to tell the user the information of the price at the date.
   *
   * @param date the date
   * @return a string builder with the information
   * @throws IOException if the date does not work
   */
  private StringBuilder distributionMessage(String date) throws IOException {
    StringBuilder message = new StringBuilder();
    float currVal = valueOfPortfolio(date);
    message.append("The value of the portfolio on " + date + " is: " + currVal
            + "\nand is composed of the following: " + System.lineSeparator());
    message.append(stockDistributionMessageHelper(date, currVal));
    return message;

  }

  /**
   * Creates a stringbuilder with all the data for the user.
   * This inclides the quantity you have, the price of 1 stock, the price of ur holdings.
   * As well as that stock's percentage of total value.
   *
   * @param date    the date
   * @param currVal the value of the portfolio at the date
   * @return a string with readable data of the stock for the user
   * @throws IOException if the string builder cannot append the message
   */
  private StringBuilder stockDistributionMessageHelper(String date, float currVal)
          throws IOException {
    StringBuilder message = new StringBuilder();
    Map<String, Float> stockQuantities = new HashMap<>();
    for (int i = 0; i < stocks.size(); i++) {
      String currName = stocks.get(i).get(0);
      Float currQuantity = Float.parseFloat(stocks.get(i).get(3));
      if (stockQuantities.containsKey(currName)) {
        stockQuantities.put(currName, stockQuantities.get(currName) + currQuantity);
      } else {
        stockQuantities.put(currName, currQuantity);
      }
    }

    for (String currName : stockQuantities.keySet()) {
      Float totalQuantity = stockQuantities.get(currName);
      BetterStock currStock = new BetterStock(currName);
      if (totalQuantity > 0) {
        float currStockPrice = currStock.getStockPrice(date, 1);
        float currStockValue = currStock.getStockPrice(date, totalQuantity);
        float totalShare = (currStockValue / currVal) * 100;
        if (totalShare > 100) {
          totalShare = 100;
        }
        message.append("Stock: ").append(currName)
                .append("\nQuantity: ").append(totalQuantity)
                .append("\nCurrent Price of 1 share: $").append(currStockPrice)
                .append("\nCurrent Value of that stock's holdings: $").append(currStockValue)
                .append("\nPercent of Total Portfolio Value: ").append(totalShare).append("%\n");
      }
    }
    return message;
  }

  /**
   * Makes sure the date is valid and usable.
   * Also checks that there is data available at that date for a stock
   *
   * @param date the date
   * @throws IllegalArgumentException if the date isn't valid
   */
  private void validateDate(String date)
          throws IllegalArgumentException {
    boolean isValid = false;
    for (int i = 0; i < stocks.size(); i++) {
      try {
        if (isValid) {
          break;
        }
        validater.validateDate(date, stocks.get(i).get(0));
        isValid = true;
      } catch (IllegalArgumentException ignored) {
      }
    }
    if (stocks.size() == 0) {
      validater.validateDate(date, "AMZN");
      isValid = true;
    }
    if (!isValid) {
      throw new IllegalArgumentException("InValid Date");
    }

  }


  /**
   * checks if the stock works by creating one and catching an error.
   *
   * @param stockName the name of the stock
   * @throws IllegalArgumentException if the creation of the stock throws an error.
   */
  private void checkWorkingStock(String stockName, String date)
          throws IllegalArgumentException {
    try {
      BasicStock test = new BaseStock(stockName);
      test.getStockPrice(date, 1);
    } catch (IOException e) {
      throw new IllegalArgumentException("Invalid Stock");
    }
  }

  /**
   * Checks if the amount of buy-able stock is 0 or less.
   *
   * @param quantity the money we need to find for
   * @throws IllegalArgumentException if the quantity is negative
   */
  private void checkBuyableQuantity(float quantity)
          throws IllegalArgumentException {
    if (quantity <= 0) {
      throw new IllegalArgumentException("Invalid quantity");
    }
  }


  @Override
  public void sellStock(String stockName, float quantity, String date)
          throws IllegalArgumentException {
    stockName = stockName.toUpperCase();
    this.validateDate(date);
    this.checkWorkingStock(stockName, date);
    List<List<String>> sellableStocks = this.getAllStocksThatCanBeSold(stockName, date);
    float allowableQuantity = this.getAllowableQuantity(sellableStocks);
    this.checkQuantities(allowableQuantity, quantity);
    this.updateSoldStocks(quantity, sellableStocks, date);
  }

  /**
   * Removes the current stock.
   * Adds one with an end date.
   * Another with the adjusted quantities in stocks.
   *
   * @param quantity       the quantity you want to sell
   * @param sellableStocks a list of the stocks you can sell
   * @param date           the date you want to sell them at
   */
  private void updateSoldStocks(float quantity,
                                List<List<String>> sellableStocks,
                                String date) {
    quantity = (float) Math.round(quantity * 1000) / 1000;
    for (int i = 0; i < sellableStocks.size(); i++) {
      this.stocks.remove(sellableStocks.get(i));
      Float stockQuan = Float.parseFloat(sellableStocks.get(i).get(3));
      quantity = this.finishSellingIfCan(quantity, stockQuan, sellableStocks, date, i);
      quantity = this.sellAllIfCan(quantity, stockQuan, sellableStocks, date, i);
      if (quantity <= 0) {
        return;
      }
    }
  }

  /**
   * Helper for updateSold stocks.
   * Adds the stock with its end date to the list of stocks.
   * Only does so if you are selling the rest of the stock
   *
   * @param quantity       the quantity you want to sell
   * @param priceOfStock   the quantity you currently have
   * @param sellableStocks a list of stocks you can sell
   * @param date           the date you are selling at
   * @param i              its place in sellableStocks
   * @return the new quantity you want to sell
   */
  private float sellAllIfCan(float quantity,
                             float priceOfStock,
                             List<List<String>> sellableStocks,
                             String date,
                             int i) {
    if (quantity >= priceOfStock) {
      this.addManipulatedStock(priceOfStock, sellableStocks, i, date);
      return 0;
    }
    return quantity;
  }

  /**
   * Helper for updateSoldStocks.
   * Makes a previous stock and next stock to account for how much was sold.
   * If all was sold, just adds the previous stock.
   *
   * @param quantity       the quantity you want to sell
   * @param priceOfStock   the price of the current stock
   * @param sellableStocks a list of the stocks you can sell
   * @param date           the date you are selling
   * @param i              the idnex of the stock in sellableStocks
   * @return the new quantity, or -1 to signify that the function is done.
   */
  private float finishSellingIfCan(float quantity,
                                   float priceOfStock,
                                   List<List<String>> sellableStocks,
                                   String date, int i) {
    if (quantity < priceOfStock) {
      this.addManipulatedStock(quantity, sellableStocks, i, date);
      quantity = Float.parseFloat(sellableStocks.get(i).get(3)) - quantity;
      this.addManipulatedStock(quantity, sellableStocks, i, null);
      return -1;
    }
    return quantity;
  }

  /**
   * Adds a stock with the parameters to the stockList.
   *
   * @param priceOfStock   the price of the stock
   * @param sellableStocks the sellable stocks list
   * @param i              the place where we are getting the manipulated stock
   * @param date           the date that can be null
   */
  private void addManipulatedStock(float priceOfStock,
                                   List<List<String>> sellableStocks,
                                   int i,
                                   String date) {
    ArrayList<String> nextStock = new ArrayList<String>();
    nextStock.add(sellableStocks.get(i).get(0));
    nextStock.add(sellableStocks.get(i).get(1));
    nextStock.add(date);
    nextStock.add(Float.toString(priceOfStock));
    this.stocks.add(nextStock);
  }

  /**
   * Makes sure the quantity is greater than or equal to the allowable quantity.
   * Helper for the sell stock function to make sure you aren't selling  more than what you can.
   *
   * @param allowableQuantity the total quantity you have
   * @param quantity          the quantity you want to sell
   * @throws IllegalArgumentException if the quantity you want to sell is more than what you have
   */
  private void checkQuantities(float allowableQuantity, float quantity)
          throws IllegalArgumentException {
    if (allowableQuantity < quantity || quantity <= 0) {
      throw new IllegalArgumentException("You can't sell that many stocks");
    }
  }

  /**
   * Gets the total amount of the stocks in the stock list.
   *
   * @param sellableStocks the stock list of the stock
   * @return the total added quantity
   */
  private float getAllowableQuantity(List<List<String>> sellableStocks) {
    float result = 0;
    for (int i = 0; i < sellableStocks.size(); i++) {
      float adder = Float.parseFloat(sellableStocks.get(i).get(3));
      result += adder;
    }
    return result;
  }

  @Override
  public void save() throws IOException {
    String path = "./res/savedPortfolios/";
    Path pathy = Paths.get(path);
    String header = "Portfolio: " + this.getName()
            + "\nStart Date, End Date, Stock, Amount\n";
    try {
      File theDir = new File(path);
      if (!theDir.exists()) {
        theDir.mkdirs();
      }
      File filePath = Paths.get("./res/savedPortfolios/" + this.getName() + ".csv").toFile();
      if (!Files.exists(filePath.toPath())) {
        try {
          Files.createFile(filePath.toPath());
        } catch (IOException e) {
          throw new IOException(e);
        }
      }
      FileWriter writer = new FileWriter(filePath);
      writer.write(header);
      String line = this.getLine();
      writer.write(line);
      writer.close();
    } catch (IOException e) {
      throw new IllegalArgumentException("Portfolio cannot be saved");
    }
  }

  /**
   * Gets the data of the stocks in a line format for the file.
   *
   * @return a string with the data for this stock
   */
  private String getLine() {
    StringBuilder line = new StringBuilder("\n");
    for (int i = 0; i < this.stocks.size(); i++) {
      String stockName = this.stocks.get(i).get(0);
      String stockStartDate = this.stocks.get(i).get(1);
      String stockEndDate = this.stocks.get(i).get(2);
      String stockQuantity = this.stocks.get(i).get(3);
      line.append(stockName).append(", ").
              append(stockStartDate).append(", ").
              append(stockEndDate).append(", ").
              append(stockQuantity).
              append("\n");
    }
    return line.toString();
  }

  @Override
  public void load() throws IllegalArgumentException {
    List<String> data = new ArrayList<String>();
    String path = "./res/savedPortfolios/" + this.getName() + ".csv";
    File file = new File(path);
    try {
      Scanner myReader = new Scanner(file);
      myReader.nextLine();
      myReader.nextLine();
      myReader.nextLine();
      while (myReader.hasNextLine()) {
        data.add(myReader.nextLine());
      }
      this.stocks = this.readData(data);
    } catch (FileNotFoundException e) {
      throw new IllegalArgumentException("You don't have this portfolio saved");
    }
  }

  /**
   * Reads the data that was found from the file by getting a line and splitting it by commas.
   *
   * @param data the list of data we got from the file.
   * @return the list of the data in the format of stocks
   */
  private List<List<String>> readData(List<String> data) {
    List<List<String>> finalData = new ArrayList<>();
    for (int i = 0; i < data.size(); i++) {
      String help = data.get(i);
      String[] arr = help.split(", ");
      List<String> list = new ArrayList<>(Arrays.asList(arr));
      finalData.add(list);
    }
    return finalData;
  }

  /**
   * Rebalances the stocks in the portfolio at this date acording to the parameters.
   * For each parameter, gets the corresponding stock, and does the math to buy or sell that stock.
   * throws an exception if the parameters don't match.
   * Assumes a valid date, and the parameters are in the order of the stock composition at the date.
   *
   * @param parameters the percentages we want to rewrite
   * @param date       the date of when we are doing this
   * @throws IllegalArgumentException if the parameters don't match.
   * @throws IOException              if there's no stock data for that date
   */
  @Override
  public void rebalance(List<Float> parameters, String date) throws IOException {
    this.validateDate(date);
    float totalValue = this.findTotalValue(date);
    List<List<String>> sellableStocks = this.getSellable(date);
    this.validatePercentage(parameters);
    this.validateAmount(parameters, sellableStocks);
    for (int i = 0; i < parameters.size(); i++) {
      String tempName = sellableStocks.get(i).get(0);
      float amtWanted = totalValue * (parameters.get(i) / 100);
      float tempQuantity = Float.parseFloat(sellableStocks.get(i).get(1));
      float amtNeededToBeSold =
              this.getValueFromName(tempName, date, tempQuantity) - amtWanted;
      float residue = this.sellOrBuy(amtNeededToBeSold, tempName, date);
      if (residue != 0) {
        this.sellOrBuy(residue, tempName, date);
      }
    }
  }


  /**
   * Gives a String representation of a stocks performance over an interval performance.
   *
   * @param startDate the start date of the interval
   * @param endDate   the end date of the interval
   * @return the data in the format of a chart
   * @throws IOException              if the stock ticker doesn't exist
   * @throws IllegalArgumentException if the start date comes after or is the same as the end date
   */
  @Override
  public String performanceOverTime(String startDate,
                                    String endDate)
          throws IOException, IllegalArgumentException {
    this.validateDate(startDate);
    this.validateDate(endDate);
    if (startDate.compareTo(endDate) > 0) {
      throw new IllegalArgumentException("End date must be after Start date or be the same");
    }
    List<String> dates = new ArrayList<String>();
    dates = this.getDates(startDate, endDate);
    float starVal = this.getStarValue(startDate, endDate, dates);
    Range range = getRange(dates);
    List<List<String>> starQuantity = getStars(dates, range, starVal);
    String output = graphHelper(starQuantity, starVal, startDate, endDate);
    return output;
  }

  /**
   * Gets all the available dates between the startDate and endDate.
   * .These dates have data for each stock.
   *
   * @param startDate the start date
   * @param endDate   the end date
   * @return a list of dates that are trading days
   * @throws IOException if the start or end date don't work
   */
  private List<String> getDates(String startDate, String endDate) throws IOException {
    List<String> dates = new ArrayList<String>();
    for (int i = 0; i < stocks.size(); i++) {
      BetterStock stock = new BetterStock(stocks.get(i).get(0));
      for (String date : stock.availableDates(startDate, endDate)) {
        if (!dates.contains(date)) {
          dates.add(date);
        }
      }
    }
    return dates;
  }

  /**
   * Gets the graph as a string from the data.
   *
   * @param starQuantity a list of the quantity of stars
   * @param starVal      the value of the stars
   * @param start        the start date
   * @param end          the end date
   * @return the string of the graph
   */
  private String graphHelper(List<List<String>> starQuantity, float starVal, String start,
                             String end) {
    StringBuilder output = new StringBuilder("Performance of Portfolio " + this.name + " from "
            + start + " to " + end + "\n\n");

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
   * Gets a list of stars for the dats from the list of dates in that order.
   *
   * @param dates   the list of dates
   * @param range   the range we are working with(week, month, etc)
   * @param starVal the value of a star
   * @return a list of stars with their dates
   */
  private List<List<String>> getStars(List<String> dates, Range range, float starVal) {
    int stepper = validater.getStepper(range);
    List<List<String>> result = new ArrayList<>();
    for (int i = 0; i < dates.size(); i += stepper) {
      List<String> tempAdder = new ArrayList<String>();
      String starDate = dates.get(i);
      tempAdder.add(starDate);
      float star = valueOfPortfolio(starDate);
      int stars = 0;
      while (starVal < star && stars < 50) {
        stars++;
        star -= starVal;
      }
      if (stars >= (starVal) / 2) {
        stars++;
      }
      if (stars == 0 && star != 0) {
        stars = 1;
      }
      tempAdder.add("" + stars);
      result.add(tempAdder);
    }
    return result;
  }

  /**
   * Gets the smallest value of the portfolio from the dates to be used as a base for the chart.
   *
   * @param dates list of dates we are looking at
   * @return the smallest portfolio value
   */
  private float getAverage(List<String> dates) {
    float total = 0;
    for (int i = 0; i < dates.size(); i++) {
      float tempResult = this.valueOfPortfolio(dates.get(i));
      total += tempResult;
    }
    return total / (dates.size());
  }

  /**
   * Gets the range based on how many dates we are looking at.
   *
   * @param dates the list of dates we are looking at
   * @return a range of day, week, month, or year depending on amount of days
   */
  private Range getRange(List<String> dates) {
    int size = dates.size();
    Range range = Range.DAY;
    if (size >= 30) {
      range = Range.WEEK;
    }
    if (size >= 150) {
      range = Range.MONTH;
    }
    if (size >= 1800) {
      range = Range.YEAR;
    }
    return range;
  }

  /**
   * Gets the star value based on the initial date we are working with.
   * The star value should the 10% of the initial date rounded for simplicity.
   *
   * @param initialDate the date we are looking at
   * @return the value of the stars
   */
  private float getStarValue(String initialDate, String endDate, List<String> dates) {
    float price = getAverage(dates);

    float value = Math.abs(price / 10);
    String val = "" + Math.round(value);
    String newVal = "" + val.charAt(0);
    for (int i = 1; i < val.length(); i++) {
      newVal += 0;
    }
    return Float.parseFloat(newVal);
  }


  /**
   * Gets the sellable stocks by checking that the end date is null and it is after the date.
   *
   * @param date the date in question
   * @return a list of sellable stocks
   * @throws IllegalArgumentException if the date is invalid or there are no stocks at the date
   */
  public List<List<String>> getSellable(String date) throws IllegalArgumentException {
    List<List<String>> stocksAtDate = new ArrayList<>();
    for (int i = 0; i < stocks.size(); i++) {
      if (this.compareDates(date, stocks.get(i).get(1))
              && stocks.get(i).get(2) == null) {
        List<String> adder = new ArrayList<String>();
        adder.add(stocks.get(i).get(0));
        adder.add(stocks.get(i).get(1));
        adder.add(stocks.get(i).get(2));
        adder.add(stocks.get(i).get(3));
        stocksAtDate.add(adder);
      }
    }
    stocksAtDate = this.compressComposition(stocksAtDate);
    if (stocksAtDate.isEmpty()) {
      throw new IllegalArgumentException("Noting to sell");
    }
    return stocksAtDate;
  }

  /**
   * Helper for rebalancing the portfolio.
   * Uses the amount needed to be sold and either buys or sells the stock that amount.
   *
   * @param amtNeededToBeSold amount to be sold
   * @param tempName          the name of the stock
   * @param date              the date when we are buying or selling the stock
   */
  private float sellOrBuy(float amtNeededToBeSold, String tempName, String date) {
    float residue = 0;
    if (amtNeededToBeSold < 0) {
      amtNeededToBeSold = this.changeFromMoneyToQuantity(-amtNeededToBeSold, tempName, date);
      int buy = (int) Math.ceil(amtNeededToBeSold);
      residue = buy - amtNeededToBeSold;
      this.buyStock(tempName, buy, date);
    } else {
      amtNeededToBeSold = this.changeFromMoneyToQuantity(amtNeededToBeSold, tempName, date);
      amtNeededToBeSold = (float) Math.round(amtNeededToBeSold * 1000) / 1000;
      this.sellStock(tempName, amtNeededToBeSold, date);
    }
    return residue;
  }

  /**
   * Changes from the money imputted to the amount of stocks it is.
   *
   * @param amtNeededToBeSold the money of the stocks
   * @param tempName          the name of the stock
   * @param date              the date
   * @return the amount of stocks the money is worth
   */
  private float changeFromMoneyToQuantity(float amtNeededToBeSold, String tempName, String date) {
    try {
      IBetterStock help = new BetterStock(tempName);
      return help.findPriceQuantity(amtNeededToBeSold, date);
    } catch (IOException e) {
      return 0;
    }
  }

  /**
   * Checks that the percentages in the parameters add up to 1.
   *
   * @param parameters the percentages
   * @throws IllegalArgumentException if they do not add up to 1
   */
  private void validatePercentage(List<Float> parameters)
          throws IllegalArgumentException {
    float total = 0;
    for (int i = 0; i < parameters.size(); i++) {
      total += parameters.get(i);
    }
    if (total != 100) {
      throw new IllegalArgumentException("Parameters must add to 100");
    }
  }

  /**
   * Checks that the list of percentages matches the list of available stocks to sell for.
   * the rebalance.
   *
   * @param a1 the list of percentages
   * @param a2 the list of available stocks to rebalance
   * @throws IllegalArgumentException if the list sizes do not equal eachother
   */
  private void validateAmount(List<Float> a1, List<List<String>> a2)
          throws IllegalArgumentException {
    if (a1.size() != a2.size()) {
      throw new IllegalArgumentException("You need to input as many parameters as there are"
              + " stocks");
    }
  }


  /**
   * Helper for the rebalance method.
   * Finds the total value of all sellable stocks by creating a stock and finding that value.
   *
   * @param date the date we are finding the value at
   * @return the totoal value of the protfolio
   * @throws IOException Invalid Date
   */
  private float findTotalValue(String date) throws IOException {
    float result = 0;
    List<List<String>> stockAvailable = this.getCompAtDate(date);
    for (int i = 0; i < stockAvailable.size(); i++) {
      String stockName = stockAvailable.get(i).get(0);
      float stockQuan = Float.parseFloat(stockAvailable.get(i).get(1));
      result += this.getValueFromName(stockName, date, stockQuan);
    }
    return result;
  }

  /**
   * Checks that the second date either is the same as the first or is before it.
   *
   * @param date1 the first date in YYYY-MM-DD
   * @param date2 the second date in YYYY-MM-DD
   * @return true if the second date is before or equal to the first
   * @throws IllegalArgumentException if the date is invalid
   */
  private boolean compareDates(String date1, String date2)
          throws IllegalArgumentException {
    if (date2 == null || date1 == null
            || date1.equals("null")
            || date2.equals("null")) {
      return true;
    }
    try {
      int year1 = Integer.parseInt(date1.substring(0, 4));
      int month1 = Integer.parseInt(date1.substring(5, 7));
      int day1 = Integer.parseInt(date1.substring(8, 10));
      int year2 = Integer.parseInt(date2.substring(0, 4));
      int month2 = Integer.parseInt(date2.substring(5, 7));
      int day2 = Integer.parseInt(date2.substring(8, 10));
      return ((year2 < year1) || (year2 == year1 && month2 < month1)
              || (year2 == year1 && month2 == month1 && day2 < day1));
    } catch (NumberFormatException e) {
      throw new IllegalArgumentException("Invalid Date");
    }
  }

  /**
   * Helper for the getCompositionAtDate function.
   * Filters out duplicates and adds their quantities.
   *
   * @param stockComp the composition for the date with duplicates
   * @return the composition without duplicates
   */
  private List<List<String>> compressComposition(List<List<String>> stockComp) {
    List<List<String>> result;
    result = this.simplify(stockComp);
    for (int i = 0; i < result.size(); i++) {
      String name = result.get(i).get(0);
      String quantity = result.get(i).get(1);
      result = this.compressCompositionHelp(result, name, quantity, i);
    }
    return result;
  }

  /**
   * Takes in an arraylist of the full stocks and outputs a list of just the name and quantity.
   *
   * @param stockComp the arraylist of sock portfolio data
   * @return an arraylist of the stock names and their quantities
   */
  private List<List<String>> simplify(List<List<String>> stockComp) {
    List<List<String>> result = new ArrayList<>();
    for (int i = 0; i < stockComp.size(); i++) {
      List<String> adder = new ArrayList<String>();
      adder.add(stockComp.get(i).get(0));
      adder.add(stockComp.get(i).get(3));
      result.add(adder);
    }
    return result;
  }


  /**
   * Helper for compressComposition.
   * Compares each name in a stock composition to a given name.
   * Takes both of those stocks out.
   * Adds a new one with the improved quantity.
   *
   * @param stockComp the current composition
   * @param name      the String that is being compared to
   * @param quantity  the quantity of the original stock
   * @param i         where on the list the original stock is
   * @return the new composition with fewer duplicates
   */
  private List<List<String>> compressCompositionHelp(List<List<String>> stockComp, String name,
                                                     String quantity, int i) {
    for (int j = i + 1; j < stockComp.size(); j++) {
      if (name.equals(stockComp.get(j).get(0))) {
        float quant1 = Float.parseFloat(quantity);
        float quant2 = Float.parseFloat(stockComp.get(j).get(1));
        quant1 += quant2;
        String trueQuant = Float.toString(quant1);
        stockComp.remove(j);
        stockComp.remove(i);
        List<String> adder = new ArrayList<String>();
        adder.add(name);
        adder.add(trueQuant);
        stockComp.add(i, adder);
        j--;
      }
    }
    return stockComp;
  }

  /**
   * Gets all sellable stocks by checking the name is what we want.
   * Also checks the starting date is valid, and the ending date is null.
   *
   * @param name the name of the stock
   * @param date the date we want to sell it
   * @return a list of a list of stocks with their name, quantity, and starting date
   * @throws IllegalArgumentException if date is invalid
   */
  private List<List<String>> getAllStocksThatCanBeSold(String name, String date)
          throws IllegalArgumentException {
    List<List<String>> result = new ArrayList<>();
    name = name.toUpperCase();
    for (int i = 0; i < this.stocks.size(); i++) {
      if (this.stocks.get(i).get(0).equals(name)
              && this.stocks.get(i).get(2) == null
              && this.compareDates(date, this.stocks.get(i).get(1))) {
        result.add(this.stocks.get(i));
      }
    }
    return result;
  }
}