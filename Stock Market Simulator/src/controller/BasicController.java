package controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import model.BaseStock;
import model.BasicStock;
import model.FinancialHistory;
import model.IValidate;
import model.User;
import model.Validate;
import view.IView;

/**
 * This class represents the controller of a stock application.
 * This controller offers a simple text interface in which the user can
 * type instructions to manipulate a user class.
 * This controller works with any Readable to read its inputs.
 * The controller directly uses a view.
 * A user is meant to represent a user.
 * The user has a list of portfolios that can be manipulated.
 */
public class BasicController implements IController {
  private Readable readable;
  private User user;
  private IView view;
  private IValidate validater;

  /**
   * MVC constructor for this controller.
   * Takes in a user model, readable, and a view.
   * If the user, readable, or view is null, throws an exception.
   *
   * @param user     the model
   * @param readable the readable
   * @param view     the view
   * @throws IllegalArgumentException if any of the above are null
   */
  public BasicController(User user, Readable readable, IView view)
          throws IllegalArgumentException {
    if ((user == null) || (readable == null)) {
      throw new IllegalArgumentException("Readable or user is null");
    }
    this.view = view;
    this.user = user;
    this.readable = readable;
    this.validater = new Validate();
  }

  /**
   * Gets information from the user, sends it to the model and then sends the models
   * output to the view to display a valid message.
   *
   * @throws IOException when there's an invalid ticker
   */
  public void goNow() throws IOException {
    Scanner sc = new Scanner(readable);
    boolean quit = false;
    boolean menuPressed = false;

    //print the welcome message
    //
    this.view.welcome();
    this.view.promptUser();
    //this.welcomeMessage();

    while (!quit) { //continue until the user quits
      String userInstruction = sc.next();
      //prompt for the instruction name
      quit = this.goNowHelp(userInstruction, sc);
    }
    this.view.farewellMessage();
  }

  /**
   * Changes the quit based on how this method passes.
   * Encapsulates the try catch method for the new-portfolio case.
   * Tries to create a new Portfolio.
   * If it fails it displays error methods.
   * If the user quits, then returns true.
   * Otherwise returns false.
   *
   * @param sc the scanner
   * @return a boolean of the new quit boolean
   */
  private boolean tryCatchNewPortfolio(Scanner sc) throws IOException {
    try {
      this.newPortfolioControlHelp(sc);
      return false;
    } catch (IllegalArgumentException e) {
      this.view.errorMessage(e.getMessage());
      return false;
    } catch (IOException e) {
      throw new RuntimeException(e);
    } catch (IllegalStateException e) {
      return true;
    }
  }

  /**
   * Changes the quit based on how this method passes.
   * Encapsulates the try catch method for the crossover case.
   * Tries to calculate the x-dau crossover with the inputs.
   * If it fails, displays error message.
   * If the user quits, returns true.
   * Otherwise returns false.
   *
   * @param sc the scanner
   * @return a boolean of the new quit boolean
   */
  private boolean tryCatchCrossover(Scanner sc) throws IOException {
    try {
      this.crossoverControlHelp(sc);
      return false;
    } catch (IllegalArgumentException | IOException e) {
      this.view.errorMessage(e.getMessage());
      return false;
    } catch (IllegalStateException e) {
      return true;
    }
  }

  /**
   * Changes the quit based on how this method passes.
   * Encapsulates the try catch method for the average case.
   * Tries to calculate the average for a stock on a day.
   * If it fails, displays error message.
   * If it passes, returns false.
   * The system also displays the answer.
   * If the user quits, returns true.
   *
   * @param sc the scanner
   * @return a boolean of the new quit boolean
   */
  private boolean tryCatchAverage(Scanner sc) throws IOException {
    try {
      this.averageControlHelp(sc);
      return false;
    } catch (IllegalArgumentException e) {
      this.view.errorMessage(e.getMessage());
      return false;
    } catch (IOException e) {
      throw new RuntimeException(e);
    } catch (IllegalStateException e) {
      return true;
    }
  }

  /**
   * Changes the quit based on how this method passes.
   * Encapsulates the try catch method for the change case.
   * Tries to calculate the change for a stock for a range.
   * If it fails, displays error message.
   * If it passes, returns false.
   * The system also displays the answer.
   * If the user quits, returns true.
   *
   * @param sc the scanner
   * @return a boolean of the new quit boolean
   */
  private boolean tryCatchChange(Scanner sc) throws IOException {
    try {
      this.changeControlHelp(sc);
      return false;
    } catch (IllegalArgumentException e) {
      this.view.errorMessage(e.getMessage());
      return false;
    } catch (IOException e) {
      throw new RuntimeException(e);
    } catch (IllegalStateException e) {
      return true;
    }
  }

  /**
   * Helper for when the user wants to calculate the change in stock for a specific range.
   * Displays what the user needs to input.
   * Uses those inputs and functions for stockChange to calculate the stock.
   * Displays the change in stock.
   *
   * @param sc the scanner
   * @throws IOException              if the stock doesn't work
   * @throws IllegalArgumentException if the user inputs something that doesn't work
   * @throws IllegalStateException    if the user quits in the middle of the function.
   */
  private void changeControlHelp(Scanner sc)
          throws IOException, IllegalArgumentException, IllegalStateException {
    String stockTicker = getValidTicker(sc);
    BaseStock currStock = new BaseStock(stockTicker);
    String startDate = dateValidater(sc, currStock, "start", null);
    String endDate = dateValidater(sc, currStock, "end", startDate);
    float totalChange = currStock.stockChange(startDate, endDate);
    String message = "";
    if (totalChange < 0) {
      float absoluteValue = 0 - totalChange;
      message = "lost" + " " + absoluteValue + " Over the time frame";
    } else if (totalChange > 0) {
      message = "Gained" + " $" + totalChange + " Over the time Frame";
    } else {
      message = "didn't change in price";
    }
    view.displayMessage(message);
  }

  /**
   * Helper for whemn the user wants to find the average stock for a given day.
   * Displays what the user sees and uses their inputs to get the average value.
   *
   * @param sc the scanner
   * @throws IOException              if the stock doesn't work
   * @throws IllegalArgumentException if they input an argument that doesn't work
   * @throws IllegalStateException    if the user wants to quit in the middle of the function.
   */
  private void averageControlHelp(Scanner sc)
          throws IOException, IllegalArgumentException, IllegalStateException {
    String stockTicker = getValidTicker(sc);
    BaseStock currStock = new BaseStock(stockTicker);
    String date = dateValidater(sc, currStock, "norm", null);
    this.view.movingAverage();
    int days = dayMovingAverageValidater(sc, date, stockTicker);
    float movingAve = currStock.movingAverage(date, days);
    String message = "The " + days + "-day moving average of " + stockTicker + " on "
            + date + " is $" + movingAve;
    this.view.displayMessage(message);
  }

  /**
   * Function to help the controller when the user wants to add stock to a portfolio.
   *
   * @param sc the scanner
   * @throws IOException              if the stock doesn't work
   * @throws IllegalArgumentException if the user does an invalid input
   * @throws IllegalStateException    if the user wants to quit in the middle of this
   */
  private boolean addPortfolioControlHelp(Scanner sc)
          throws IOException, IllegalArgumentException, IllegalStateException {
    ArrayList<String> portfolios = new ArrayList<>(user.getPortfolios());
    if (portfolios.size() == 0) {
      this.view.noPortfolios();
      return false;
    }
    for (int i = 0; i < portfolios.size(); i++) {
      String currPortfolio = portfolios.get(i);
      this.view.displayPortfolio(currPortfolio);
    }
    try {
      StringBuilder log = new StringBuilder();
      String portfolioName = getValidPortfolioName(sc);

      boolean adding = true;
      while (adding) {
        this.view.addingMessage();
        String ticker = getValidTickerOrBuild(sc);
        if (ticker.equals("done")) {
          adding = false;
          continue;
        }
        this.view.displayMessage("Input Quantity: ");
        int quantity = intValidater(sc);
        log.append(System.lineSeparator() + quantity + "-");
        log.append(ticker);
        user.updatePortfolio(portfolioName, ticker, quantity);

      }
      String message = "Your Portfolio " + portfolioName + " has been updated with the "
              + "following additions " + log;
      this.view.displayPortfolio(message);
      return false;
    } catch (IllegalArgumentException e) {
      return true;
    }
  }

  /**
   * Helps the user create a portfolio with stocks.
   * Displays what the user needs to see to create the new portoflio.
   *
   * @param sc the scanner
   * @throws IOException              if the stock doesn't work
   * @throws IllegalArgumentException if they input an invalid argument
   * @throws IllegalStateException    if they quit in the middle of this
   */
  private void newPortfolioControlHelp(Scanner sc)
          throws IOException, IllegalArgumentException, IllegalStateException {
    this.view.newPortfolioMessage();
    String portfolioName = sc.next();
    this.checkQ(portfolioName);
    this.user.addPortfolio(portfolioName);
    String message = "Your Portfolio " + portfolioName + " has been created! ";
    this.view.displayPortfolio(message);
  }

  /**
   * Displays the views and uses the inputs to get the value of a portfolio.
   * Once it gets the portfolio name, it gets the portfolio value.
   *
   * @param sc the scanner
   * @throws IOException              if the stock doesn't work
   * @throws IllegalArgumentException if the user inputs something incorrectly
   */
  private boolean portfolioValueHelpControl(Scanner sc)
          throws IOException, IllegalArgumentException {
    ArrayList<String> portfolios = new ArrayList<>(user.getPortfolios());
    if (portfolios.size() == 0) {
      this.view.noPortfolios();
      return false;
    }

    for (int i = 0; i < portfolios.size(); i++) {
      String currPortfolio = portfolios.get(i);
      this.view.displayPortfolio(currPortfolio);
    }
    try {
      String portfolioName = getValidPortfolioName(sc);
      this.checkQ(portfolioName);
      BaseStock currStock = new BaseStock("AMD");
      String date = dateValidater(sc, currStock, "norm", null);
      this.checkQ(date);

      float portfolioValue = user.getValueOfPortfolio(portfolioName, date);
      String message = "The value of portfolio " + portfolioName + " on " + date + " is $"
              + portfolioValue;
      this.view.displayMessage(message);
      return false;
    } catch (IllegalStateException e) {
      return true;
    }
  }

  /**
   * Helper for when the user wants to do a crossover.
   * Displays the messages and takes in the inputs to display crossover days.
   *
   * @param sc the sanner
   * @throws IOException              if the stock doesn't work
   * @throws IllegalArgumentException if the user inputs something incorrectly
   * @throws IllegalStateException    if the user quits in the middle of this
   */
  private void crossoverControlHelp(Scanner sc)
          throws IOException, IllegalArgumentException, IllegalStateException {
    String stockTicker = getValidTicker(sc);
    BaseStock currStock = new BaseStock(stockTicker);
    String startDate = dateValidater(sc, currStock, "start", null);
    String endDate = dateValidater(sc, currStock, "end", startDate);
    this.view.movingAverage();
    int days = dayMovingAverageValidater(sc, startDate, stockTicker);
    String crossoverDays = currStock.crossOverDays(startDate, endDate, days);
    if (crossoverDays.isEmpty()) {
      this.view.displayMessage("None of the days within the range are crossover days");
    } else {
      this.view.displayMessage("The following are crossoverDays: " + crossoverDays);
    }
  }

  /**
   * Checks that the inputted date is valid using the helper checkMonth.
   *
   * @param date  the inputted date string
   * @param stock the stock for the stock history
   * @throws IllegalArgumentException if the year doesn't exist in the stockHistory or
   *                                  the date isn't in the correct format.
   */
  private void validDate(String date, BasicStock stock)
          throws IllegalArgumentException, IOException {
    List<List<String>> stockData = stock.getHistoryData();
    this.checkDateFormat(date);
    int year = this.getYear(date);
    int month = this.getMonth(date);
    int day = this.getDay(date);
    for (int i = 0; i < stockData.size(); i++) {
      String tryDate = stockData.get(i).get(0);
      int newYear = this.getYear(tryDate);
      int newMonth = this.getMonth(tryDate);
      int newDay = this.getDay(tryDate);
      if (year == newYear && month == newMonth && day == newDay) {
        this.checkMonth(stockData, month, newMonth, day, newDay, i);
        return;
      }
    }
    throw new IllegalArgumentException("This date doesn't exist");
  }

  /**
   * Checks that there exists a month that is equal to the inputted month.
   * If not, throws an exception.
   *
   * @param stockData an arraylist of arraylist string of the stock history
   * @param month     the string month we are looking for
   * @param newMonth  the string month in the stock history
   * @param day       the string day we are looking for
   * @param newDay    the string day in the stock history
   * @param i         where we are starting searching because the year should start the same
   * @throws IllegalArgumentException if the month is above of below 12.
   */
  private void checkMonth(List<List<String>> stockData,
                          int month, int newMonth, int day, int newDay, int i)
          throws IllegalArgumentException, IOException {
    for (int j = i; j < stockData.size(); j++) {
      String tryDate = stockData.get(j).get(0);
      newMonth = this.getMonth(tryDate);
      newDay = this.getDay(tryDate);
      if (month == newMonth) {
        this.getMostRecentDay(stockData, day, newDay, j);
        return;
      }
    }
    throw new IllegalArgumentException("This date doesn't exist");
  }

  /**
   * Checks that there exists a day that is equal to the day inputted.
   * If not, gets the closest available date by getting the previous date in the stockData.
   * If there is nothing close to that, then it is an invalid day
   *
   * @param stockData an arraylist of arraylist string of the stock history
   * @param day       the string day we are looking for
   * @param newDay    the string day of the stock history
   * @param i         where we start checking the list where the month should work
   * @throws IllegalArgumentException if the day is less than 1.
   */
  private void getMostRecentDay(List<List<String>> stockData, int day, int newDay, int i)
          throws IllegalArgumentException, IOException {
    for (int j = i; j < stockData.size(); j++) {
      String tryDate = stockData.get(j).get(0);
      newDay = this.getDay(tryDate);
      if (day > newDay) {
        this.view.displayMessage("This date does not work, a close available date is "
                + stockData.get(j).get(0));
        return;
      }
      if (day == newDay) {
        return;
      }
    }
    throw new IllegalArgumentException("This date doesn't exist");
  }

  /**
   * Helper for validDate where it gets the year of a correctly formatted string.
   *
   * @param date the string date in question
   * @return the integer of the day
   * @throws IllegalArgumentException if you cannot get the day from the string
   */
  private int getYear(String date) {
    try {
      return Integer.parseInt(date.substring(0, 4));
    } catch (StringIndexOutOfBoundsException | NumberFormatException e) {
      throw new IllegalArgumentException("Invalid date format. Please enter a "
              + "date in the format YYYY-MM-DD.");
    }
  }

  /**
   * Helper for validDate where it gets the month of a correctly formatted string.
   *
   * @param date the string date in question
   * @return the integer of the month
   * @throws IllegalArgumentException if you cannot get the month from the string
   */
  private int getMonth(String date) throws IllegalArgumentException {
    try {
      return Integer.parseInt(date.substring(5, 7));
    } catch (StringIndexOutOfBoundsException | NumberFormatException e) {
      throw new IllegalArgumentException("Invalid date format. Please enter a "
              + "date in the format YYYY-MM-DD.");
    }
  }

  /**
   * Helper for validDate where it gets the day of a correctly formatted string.
   *
   * @param date the string date in question
   * @return the integer of the day
   * @throws IllegalArgumentException if you cannot get the day from the string
   */
  private int getDay(String date) throws IllegalArgumentException {
    try {
      return Integer.parseInt(date.substring(8, 10));
    } catch (StringIndexOutOfBoundsException | NumberFormatException e) {
      throw new IllegalArgumentException("Invalid date format. Please enter"
              + " a date in the format YYYY-MM-DD.");
    }
  }

  /**
   * Checks that the length of the date is good and that there are hashes.
   *
   * @param date a string of the date
   * @throws IllegalArgumentException if there aren't hashes or the date is too long
   */
  private void checkDateFormat(String date) throws IllegalArgumentException {
    if (date.length() != 10
            || date.charAt(4) != '-'
            || date.charAt(7) != '-') {
      throw new IllegalArgumentException("invalid format");
    }
  }

  /**
   * Checks that the input string is quit or not.
   *
   * @param q the string that may or may not be quit
   * @throws IllegalStateException if the user is quitting
   */
  private void checkQ(String q) throws IllegalStateException {
    if (q.equals("q") || q.equals("quit")) {
      throw new IllegalStateException("ur quitting :(");
    }
  }

  /**
   * Helper for the portfolio create actions.
   * Checks if the user is done inputting stocks or not.
   * If not, check that the stock ticker is valid.
   * If so, returns the stock ticker to build it.
   *
   * @param sc the scanner
   * @return the valid stock ticker
   */
  private String getValidTickerOrBuild(Scanner sc) throws IOException {
    String stockTicker;
    while (true) {
      stockTicker = sc.next();
      if (stockTicker.equalsIgnoreCase("done")) {
        return stockTicker;
      }
      this.checkQ(stockTicker);
      try {
        new BaseStock(stockTicker);
        break;
      } catch (IllegalArgumentException | IOException e) {
        this.view.displayMessage("Invalid stock ticker. Please input a valid ticker symbol.");
      }
    }
    return stockTicker;
  }

  /**
   * Gets a valid StockTicker based on the user inputs.
   * If the user didn't input a valid ticker, prompts the user to try again.
   *
   * @param sc the scanner
   * @return a string of the stockticker
   */
  private String getValidTicker(Scanner sc) throws IOException {
    String stockTicker;
    this.view.askTicker();
    while (true) {
      stockTicker = sc.next();
      this.checkQ(stockTicker);
      try {
        new BaseStock(stockTicker);
        break;
      } catch (IllegalArgumentException | IOException e) {
        this.view.displayMessage("Invalid stock ticker. Please input a valid ticker symbol.");
      }
    }
    return stockTicker;
  }

  /**
   * Makes sure that date is valid with a start date that might be null depending
   * on the type of date needed.
   *
   * @param sc        the scanner
   * @param stock     the stock
   * @param dateType  the type of the date
   * @param startDate the start date
   * @return the date
   */
  private String dateValidater(Scanner sc, BaseStock stock, String dateType, String startDate)
          throws IOException, IllegalStateException {
    String date;
    int startYear = 0;
    int startMonth = 0;
    int startDay = 0;
    while (true) {
      this.askDateForValidator(dateType);
      String[] startDateParts = new String[2];
      if (startDate != null) {
        startDateParts = startDate.split("-");
        startYear = Integer.parseInt(startDateParts[0]);

      }
      String year = promptForYear(sc, startYear);
      if (Integer.parseInt(year) == startYear) {
        startMonth = Integer.parseInt(startDateParts[1]);
      }
      String month = promptForMonth(sc, startMonth);
      if ((Integer.parseInt(month) == startMonth) && Integer.parseInt(year) == startYear) {
        startDay = Integer.parseInt(startDateParts[2]);

      }
      String day = promptForDay(sc, startDay);
      date = year + "-" + month + "-" + day;
      try {
        validater.validateDate(date, stock.getName());
        if (startDate != null && dateType.equals("end")) {
          validater.validateStartEnd(startDate, date, stock.getName());
        }
        break;
      } catch (IllegalArgumentException e) {
        this.view.errorMessage("Invalid date. Please input a valid date.");
      }
    }
    return date;
  }

  /**
   * Helper for the dateValidator that asks the user for the date depending on the type of date.
   *
   * @param dateType the type of date
   */
  private void askDateForValidator(String dateType) throws IOException, IllegalStateException {
    if (dateType.equals("start")) {
      this.view.displayMessage("Start Date in format (YYYY-MM-DD)" + System.lineSeparator());
    } else if (dateType.equals("end")) {
      this.view.displayMessage("End Date in format (YYYY-MM-DD)" + System.lineSeparator());
    } else {
      this.view.displayMessage("Date in format (YYYY-MM-DD)" + System.lineSeparator());
    }
  }

  private String promptForYear(Scanner sc, int startYear)
          throws IOException, IllegalStateException {
    String year;
    while (true) {
      this.view.displayMessage("Input year (YYYY) - ");
      year = sc.next();
      this.checkQ(year);
      if (year.matches("\\d{4}") && (Integer.parseInt(year) < 2025) &&
              (Integer.parseInt(year) > 1998) && (Integer.parseInt(year) >= startYear)) {
        break;
      } else if (startYear > Integer.parseInt(year)) {
        this.view.displayMessage("Ending dates year can't come before starting dates year"
                + System.lineSeparator());
      } else {
        this.view.errorMessage("Invalid year. Please input a valid year (YYYY).");
      }
    }
    return year;
  }

  private String promptForMonth(Scanner sc, int startMonth)
          throws IOException, IllegalStateException {
    String month;
    while (true) {
      this.view.displayMessage("Input Month (MM) - ");
      month = sc.next();
      this.checkQ(month);
      if (month.matches("\\d{1,2}")) {
        int monthInt = Integer.parseInt(month);
        if ((monthInt >= 1 && monthInt <= 12) && (monthInt >= startMonth)) {
          month = String.format("%02d", monthInt);
          break;
        } else if (monthInt < startMonth) {
          this.view.displayMessage("Since years are the same end month can't be before "
                  + "starting dates month" + System.lineSeparator());
        }
      } else {
        this.view.errorMessage("Invalid month. Please input a valid month (MM)."
                + System.lineSeparator());
      }
    }
    return month;
  }

  private String promptForDay(Scanner sc, int startingDay)
          throws IOException, IllegalStateException {
    String day;
    while (true) {
      this.view.displayMessage("Input Day (DD) - ");
      day = sc.next();
      this.checkQ(day);
      if (day.matches("\\d{1,2}")) {
        int dayInt = Integer.parseInt(day);
        if ((dayInt >= 1 && dayInt <= 31) && dayInt >= startingDay) {
          day = String.format("%02d", dayInt);
          break;
        } else if (dayInt < startingDay) {
          this.view.displayMessage("Ending day can't come before Starting day");
        }
      } else {
        this.view.errorMessage("Invalid day. Please input a valid day (DD).");
      }
    }
    return day;
  }

  /**
   * Checks that the user inputted a valid integer.
   * If not, prompts the user to input a valid integer.
   *
   * @param sc the scanner
   * @return the valid int
   */
  private int intValidater(Scanner sc) throws IOException {
    int num;
    while (true) {
      String input = sc.next();
      this.checkQ(input);
      try {
        num = Integer.parseInt(input);
        if (num > 0) {
          break; // If input is a valid non-negative integer, break the loop
        } else {
          this.view.displayMessage("Invalid input. Please type a positive integer.");
        }
      } catch (NumberFormatException | IOException e) {
        this.view.displayMessage("Invalid input. Please type a positive integer.");
      }
    }
    return num;
  }

  /**
   * Checks that the user inputs one of the names of the portfolios the user has.
   * If not, prompts the user to input a valid name.
   *
   * @param sc the scanner
   * @return a string of the name of the portfolio
   */
  private String getValidPortfolioName(Scanner sc) throws IOException {
    String portfolioName;
    this.view.portfolioSelect();
    while (true) {
      portfolioName = sc.next();
      this.checkQ(portfolioName);
      if (user.getPortfolios().contains(portfolioName)) {
        break;
      } else {
        this.view.displayMessage("Invalid name. Input one of the portfolios listed above");
      }
    }
    return portfolioName;
  }

  /**
   * Checks that the user inputted a valid x-day moving average quantity that
   * isn't negative and isn't too large. If not, prompts the user to input a valid Quantity.
   *
   * @param sc the scanner
   * @return the valid int
   */
  private int dayMovingAverageValidater(Scanner sc, String date, String ticker) throws IOException {
    int num;
    while (true) {
      String input = sc.next();
      this.checkQ(input);
      FinancialHistory finance = new FinancialHistory(ticker);
      int datePlace = finance.getDatePlace(date);
      try {
        num = Integer.parseInt(input);
        if ((num > 0) && (num + datePlace) < finance.getStockHistory().size()) {
          break; // If input is a valid non-negative integer, break the loop
        } else if (num < 0) {
          this.view.displayMessage("Invalid input. Please type a positive integer.");
        } else {
          this.view.displayMessage("Invalid input. Please type a smaller positive integer");
        }
      } catch (NumberFormatException e) {
        this.view.displayMessage("Invalid input. Please type a positive integer.");
      } catch (IllegalArgumentException e) {
        this.view.displayMessage("Invalid input. Please type a smaller positive integer");
      }
    }
    return num;
  }

  /**
   * Helper for the controller.
   *
   * @param userInstruction the instruction of the user
   * @param sc              the scanner
   * @return the quit
   * @throws IOException if dates invalid
   */
  public boolean goNowHelp(String userInstruction, Scanner sc) throws IOException {
    //take an instruction name
    boolean quit = false;
    boolean menuPressed = true;
    switch (userInstruction) {
      case "change":
        quit = this.tryCatchChange(sc);
        break;
      case "average":
        quit = this.tryCatchAverage(sc);
        break;
      case "crossover":
        quit = this.tryCatchCrossover(sc);
        break;
      case "portfolio-value":
        quit = this.portfolioValueHelpControl(sc);
        break;
      case "menu":
        this.view.promptUser();
        break;
      case "new-portfolio":
        quit = this.tryCatchNewPortfolio(sc);
        break;
      case "add-portfolio":
        quit = this.addPortfolioControlHelp(sc);
        break;
      case "q":
      case "quit":
        quit = true;
        break;
      default:
        this.view.displayMessage("Undefined instruction: " + userInstruction
                + System.lineSeparator());
    }

    return quit;
  }
}
