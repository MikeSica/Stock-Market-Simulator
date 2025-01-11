package controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import model.BaseStock;
import model.BetterStock;
import model.IBetterUser;
import model.IValidate;
import model.User;
import model.Validate;
import view.IView;

/**
 * This class represents the Better Controller of a stock application.
 * This controller offers a simple text interface in which the user can
 * type instructions to manipulate a user class.
 * This controller works with any Readable to read its inputs.
 * The controller directly uses a view.
 * A user is meant to represent a user.
 * The user has a list of portfolios that can be manipulated.
 */
public class BetterController extends BasicController {

  private Readable readable;
  private IBetterUser user;
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
  public BetterController(IBetterUser user, Readable readable, IView view)
          throws IllegalArgumentException {
    super((User) user, readable, view);
    if ((user == null) || (readable == null)) {
      throw new IllegalArgumentException("Readable or user is null");
    }
    this.view = view;
    this.user = user;
    this.readable = readable;
    this.validater = new Validate();

  }

  @Override
  public void goNow() throws IOException {
    Scanner sc = new Scanner(readable);
    boolean quit = false;
    boolean menuPressed = false;

    this.view.welcome();
    this.view.promptUser();

    while (!quit) {
      String userInstruction = sc.next();
      switch (userInstruction) {
        case "stock-chart":
          quit = this.stockChart(sc);
          break;
        case "portfolio-chart":
          quit = this.portfolioChart(sc);
          break;
        case "rebalance":
          quit = this.rebalancing(sc);
          break;
        case "sell":
          quit = this.sellStock(sc);
          break;
        case "buy":
          quit = this.buyStock(sc);
          break;
        case "composition":
          quit = this.composition(sc);
          break;
        case "portfolio-value":
          quit = this.portfolioVal(sc);
          break;
        case "save":
          quit = this.saveHelper(sc);
          break;
        case "retrieve":
          quit = this.retrieveHelper(sc);
          break;
        case "distribution":
          quit = this.distribution(sc);
          break;
        case "menu":
          this.view.promptUser();
          menuPressed = true;
          break;
        case "q":
        case "quit":
          quit = true;
          menuPressed = true;
          break;
        default:
          //goes to other controller
          super.goNowHelp(userInstruction, sc);
          break;
      }
      if (!menuPressed) {
        this.view.displayMessage(System.lineSeparator() + "Type 'menu' for user options" +
                " or 'q'/'quit' to quit." + System.lineSeparator());
      } else {
        menuPressed = false;
      }


    }

  }

  private boolean retrieveHelper(Scanner sc) throws IOException {
    String retrievables = this.user.getSavedPortfolios();
    if (retrievables.isEmpty()) {
      this.view.displayMessage("No Saved Portfolios available :( ");
      return false;
    }
    String[] tempPortfolios = retrievables.split("-");
    List<String> portfolios = new ArrayList<String>();
    for (String portfolio : tempPortfolios) {
      this.view.displayMessage("Portfolio: " + portfolio + System.lineSeparator());
      portfolios.add(portfolio);
    }
    try {
      String portfolioName = getValidRetrievablePortfolioName(sc, portfolios);
      this.checkQ(portfolioName);
      String load = this.user.load(portfolioName);
      this.view.displayMessage(load);
      return false;
    } catch (IllegalStateException e) {
      return true;
    }
  }

  private boolean saveHelper(Scanner sc) throws IOException {
    List<String> portfolios = new ArrayList<>(user.getPortfolios());
    if (portfolios.isEmpty()) {
      this.view.noPortfolios();
      return false;
    }
    for (String currPortfolio : portfolios) {
      this.view.displayPortfolio(currPortfolio);
    }
    try {
      String portfolioName = getValidPortfolioName(sc);
      String message = this.user.save(portfolioName);
      this.view.displayMessage(message + System.lineSeparator());
      return false;
    } catch (IllegalStateException e) {
      return true;
    }
  }

  private boolean portfolioVal(Scanner sc) throws IOException {

    List<String> portfolios = new ArrayList<>(user.getPortfolios());
    if (portfolios.isEmpty()) {
      this.view.noPortfolios();
      return false;
    }

    for (String currPortfolio : portfolios) {
      this.view.displayPortfolio(currPortfolio);
    }
    try {
      String portfolioName = getValidPortfolioName(sc);
      BetterStock amdHelper = new BetterStock("AMD");
      String date = dateValidater(sc, amdHelper, "", null);
      float val = user.getValueOfPortfolio(portfolioName, date);
      this.view.displayMessage("The value of " + portfolioName + " on " + date + " is $" + val
              + System.lineSeparator());
      return false;
    } catch (IllegalStateException e) {
      return true;
    }
  }


  private boolean composition(Scanner sc) throws IOException {

    List<String> portfolios = new ArrayList<>(user.getPortfolios());
    if (portfolios.isEmpty()) {
      this.view.noPortfolios();
      return false;
    }
    for (String currPortfolio : portfolios) {
      this.view.displayPortfolio(currPortfolio);
    }
    try {
      String portfolioName = getValidPortfolioName(sc);
      BetterStock amdHelper = new BetterStock("AMD");
      String date = dateValidater(sc, amdHelper, "", null);
      String distributionMessage = user.getCompAtDate(portfolioName, date);
      if (distributionMessage.isEmpty()) {
        distributionMessage = "Not Composed of anything: ";
      }
      this.view.displayMessage(distributionMessage);
      return false;
    } catch (IllegalStateException e) {
      return true;
    }
  }

  private boolean distribution(Scanner sc) throws IOException {

    List<String> portfolios = new ArrayList<>(user.getPortfolios());
    if (portfolios.isEmpty()) {
      this.view.noPortfolios();
      return false;
    }
    for (String currPortfolio : portfolios) {
      this.view.displayPortfolio(currPortfolio);
    }
    try {
      String portfolioName = getValidPortfolioName(sc);
      BetterStock amdHelper = new BetterStock("AMD");
      String date = dateValidater(sc, amdHelper, "", null);
      String distributionMessage = user.getDistribution(portfolioName, date);
      this.view.displayMessage(distributionMessage);
      return false;
    } catch (IllegalStateException e) {
      return true;
    }
  }

  private boolean sellStock(Scanner sc) throws IOException {

    List<String> portfolios = new ArrayList<>(user.getPortfolios());
    if (portfolios.isEmpty()) {
      this.view.noPortfolios();
      return false;
    }

    for (String currPortfolio : portfolios) {
      this.view.displayPortfolio(currPortfolio);
    }
    String portfolioName = getValidPortfolioName(sc);
    while (true) {
      try {
        BetterStock amdHelper = new BetterStock("AMD");
        String date = dateValidater(sc, amdHelper, "", null);
        String composition = this.user.getSellableAtDate(portfolioName, date);
        if (composition.isEmpty()) {
          this.view.displayMessage("No stocks available to sell :(");
          return false;
        }
        this.view.displayMessage("On " + date + " Your Portfolio " + portfolioName
                + " is composed of the following: " + composition);
        this.view.displayMessage("Input stock to sell: ");
        String stockTicker = getValidTicker(sc);
        this.view.displayMessage("Input quantity to sell(Can sell Partial Shares): ");
        float quantity = getFloatQuantity(sc);
        user.sell(portfolioName, stockTicker, quantity, date);
        this.view.displayMessage("Selling of " + quantity + " " + stockTicker + " in "
                + portfolioName + " on " + date + " was successful!");
        return false;
      } catch (IllegalStateException e) {
        return true;
      } catch (IllegalArgumentException e) {
        this.view.displayMessage("Could not sell, please input a different stock or quantity");
      }
    }
  }

  private boolean buyStock(Scanner sc) throws IOException {

    List<String> portfolios = new ArrayList<>(user.getPortfolios());
    if (portfolios.isEmpty()) {
      this.view.noPortfolios();
      return false;
    }
    for (String currPortfolio : portfolios) {
      this.view.displayPortfolio(currPortfolio);
    }
    try {
      String portfolioName = getValidPortfolioName(sc);
      this.view.displayMessage("Input Stock to Buy:");
      String stockTicker = getValidTicker(sc);
      BetterStock currStock = new BetterStock(stockTicker);
      String date = dateValidater(sc, currStock, "", null);
      this.view.displayMessage("Input a Quantity(Whole Number) to Purchase: ");
      int quantity = getIntQuantity(sc);
      user.buy(portfolioName, stockTicker, quantity, date);
      this.view.displayMessage("Purchase of " + quantity + " " + stockTicker + " in "
              + portfolioName + " on " + date + " was successful!");
      return false;
    } catch (IllegalStateException e) {
      return true;
    }
  }

  private int getIntQuantity(Scanner sc) throws IOException, IllegalStateException {
    int num;
    while (true) {
      String input = sc.next();
      this.checkQ(input);
      try {
        num = Integer.parseInt(input);
        if (num > 0) {
          break;
        } else {
          this.view.displayMessage("Invalid input. Please type a positive integer.");
        }
      } catch (NumberFormatException | IOException e) {
        this.view.displayMessage("Invalid input. Please type a positive integer.");
      }
    }
    return num;
  }

  private float getFloatQuantity(Scanner sc) throws IOException, IllegalStateException {
    float num;
    while (true) {
      String input = sc.next();
      this.checkQ(input);
      try {
        num = Float.parseFloat(input);
        if (num > 0) {
          break;
        } else {
          this.view.displayMessage("Invalid input. Please type a positive number.");
        }
      } catch (NumberFormatException | IOException e) {
        this.view.displayMessage("Invalid input. Please type a positive number.");
      }
    }
    return num;
  }

  private boolean rebalancing(Scanner sc) throws IOException {
    List<String> portfolios = new ArrayList<>(user.getPortfolios());

    if (portfolios.isEmpty()) {
      this.view.noPortfolios();
      return false;
    }

    for (String currPortfolio : portfolios) {
      this.view.displayPortfolio(currPortfolio);
    }
    try {

      String portfolioName = getValidPortfolioName(sc);
      BaseStock amdHelper = new BaseStock("AMD");
      String date = dateValidater(sc, amdHelper, "", null);
      String currDistribution = this.user.getDistribution(portfolioName, date);
      ArrayList<Float> parameters = (ArrayList<Float>) rebalanceHelperMessage(sc, currDistribution);
      String message = this.user.rebalance(portfolioName, parameters, date);
      this.view.displayMessage(message);
      return false;
    } catch (IllegalStateException e) {
      return true;
    }
  }

  private List<Float> rebalanceHelperMessage(Scanner sc, String currDistribution)
          throws IOException {
    ArrayList<Float> newPercentages = new ArrayList<>();
    ArrayList<String> stockNames = new ArrayList<>();
    String[] lines = currDistribution.split(System.lineSeparator());
    float totalPercent = 0;

    for (int i = 0; i < lines.length; i++) {
      if (lines[i].startsWith("Stock:")) {
        String stockName = lines[i].split(":")[1].trim();
        stockNames.add(stockName);
        String quantityLine = lines[i + 1];
        String percentLine = lines[i + 4];
        float currentPercent = Float.parseFloat(percentLine.split(":")[1].trim().replace("%", ""));
        float quantity = Float.parseFloat(quantityLine.split(":")[1].trim());
        this.view.displayMessage("Current portfolio share of " + quantity + " of " + stockName
                + ": " + currentPercent + "%" + System.lineSeparator());
        i += 4;
      }
    }

    for (int j = 0; j < stockNames.size(); j++) {
      boolean isEnd = false;
      if (j == stockNames.size() - 1) {
        isEnd = true;
      }
      String stockName = stockNames.get(j);
      float newPercent = getValidPercentage(sc, stockName, totalPercent, isEnd);
      totalPercent += newPercent;
      newPercentages.add(newPercent);
      if (totalPercent == 100 && !isEnd) {
        this.view.displayMessage("The total percentage is now 100%. Any remaining stocks "
                + "will be set to 0%.\n");
        for (int i = newPercentages.size(); i < stockNames.size(); i++) {
          newPercentages.add(0f);
        }

      }
    }

    return newPercentages;
  }


  private float getValidPercentage(Scanner sc, String stockName, float totalPercent,
                                   boolean atEnd) throws IOException {
    float newPercent = 0;
    boolean validInput = false;

    while (!validInput) {
      this.view.displayMessage("Enter new percentage for Stock: " + stockName + ": ");
      String input = sc.next();
      checkQ(input);

      while (!isFloat(input)) {
        this.view.displayMessage("Invalid input. Enter a valid percentage for Stock: "
                + stockName + ": ");
        input = sc.next();
        checkQ(input);
      }
      newPercent = Float.parseFloat(input);
      if (newPercent < 0 || newPercent > 100) {
        this.view.displayMessage("Invalid input. Percentage must be between 0 and 100.\n");
      } else if (totalPercent + newPercent > 100) {
        float percentLeft = 100 - totalPercent;
        this.view.displayMessage("This percentage would make the total exceed 100%.\n");
        this.view.displayMessage("Input a percentage up to " + percentLeft + "%\n");
      } else if ((totalPercent + newPercent != 100) && atEnd) {
        float percentLeft = 100 - totalPercent;
        this.view.displayMessage("The last stock must have a percent of " + percentLeft + "%\n");
      } else {
        validInput = true;
      }
    }
    return newPercent;
  }


  private boolean isFloat(String input) {
    try {
      Float.parseFloat(input);
      return true;
    } catch (NumberFormatException e) {
      return false;
    }
  }


  private boolean portfolioChart(Scanner sc) throws IOException {
    List<String> portfolios = new ArrayList<String>(user.getPortfolios());
    if (portfolios.isEmpty()) {
      this.view.noPortfolios();
      return false;
    }
    for (String currPortfolio : portfolios) {
      this.view.displayPortfolio(currPortfolio);
      this.view.displayMessage(System.lineSeparator());
    }
    try {
      String portfolioName = getValidPortfolioName(sc);
      this.checkQ(portfolioName);
      BaseStock amdHelper = new BaseStock("AMD");
      String startDate = dateValidater(sc, amdHelper, "start", null);
      String endDate = dateValidater(sc, amdHelper, "end", startDate);

      String message = user.getPortfolioPerformance(portfolioName, startDate, endDate);
      this.view.displayMessage(message);
      return false;
    } catch (IllegalStateException e) {
      return true;
    }
  }


  private boolean stockChart(Scanner sc) throws IOException {
    this.view.displayMessage("Input the stock: ");
    String stockTicker = getValidTicker(sc);
    try {
      BetterStock currStock = new BetterStock(stockTicker);
      String startDate = dateValidater(sc, currStock, "start", null);
      String endDate = dateValidater(sc, currStock, "end", startDate);
      String message = currStock.performanceOverTime(startDate, endDate);
      this.view.displayMessage(message);
      return false;
    } catch (IllegalStateException e) {
      return true;
    }
  }


  /**
   * Gets a valid StockTicker based on the user inputs.
   * If the user didn't input a valid ticker, prompts the user to try again.
   *
   * @param sc the scanner
   * @return a string of the stockTicker
   */
  private String getValidTicker(Scanner sc) throws IOException, IllegalStateException {
    String stockTicker;
    while (true) {
      stockTicker = sc.next();
      this.checkQ(stockTicker);
      try {
        validater.validateTicker(stockTicker);
        break;
      } catch (IllegalArgumentException e) {
        this.view.displayMessage("Invalid stock ticker. Please input a valid ticker symbol -");
      }
    }
    return stockTicker;
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
   * Checks that the user inputs one of the names of the portfolios the user has.
   * If not, prompts the user to input a valid name.
   *
   * @param sc the scanner
   * @return a string of the name of the portfolio
   */
  private String getValidPortfolioName(Scanner sc) throws IOException, IllegalStateException {
    String portfolioName;
    this.view.displayMessage(System.lineSeparator() + "Type one of the above portfolios: ");
    while (true) {
      portfolioName = sc.next();
      this.checkQ(portfolioName);
      if (user.getPortfolios().contains(portfolioName)) {
        break;
      } else {
        this.view.displayMessage("Invalid name. Input one of the portfolios listed above: ");
      }
    }
    return portfolioName;
  }

  /**
   * Checks that the user inputs one of the names of the retrievable portfolios the user has.
   * If not, prompts the user to input a valid retrievable portfolio.
   *
   * @param sc the scanner
   * @return a string of the name of the portfolio
   */
  private String getValidRetrievablePortfolioName(Scanner sc, List<String> files)
          throws IOException, IllegalStateException {
    String fileName;
    this.view.displayMessage(System.lineSeparator() + "Choose one of the above portfolios: ");
    while (true) {
      fileName = sc.next();
      this.checkQ(fileName);
      if (files.contains(fileName)) {
        break;
      } else {
        this.view.displayMessage("Invalid name. Input one of the files listed above");
      }
    }
    return fileName;
  }

}

