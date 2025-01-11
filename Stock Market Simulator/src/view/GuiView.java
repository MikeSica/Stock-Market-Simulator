package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.BoxLayout;
import javax.swing.BorderFactory;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JFrame;
import javax.swing.border.LineBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import controller.BetterController;
import controller.IController;
import model.BetterStock;
import model.IBetterStock;
import model.IBetterUser;

/**
 * An implementation of IView containing all it's methods which extends JFrame.
 * It's meant to display a graphical user interface to the user to allow for easy interaction with
 * the program, and it overrides the promptUser and interacts with the controller to achieve this.
 */
public class GuiView extends JFrame
        implements IView, ActionListener, ItemListener, ListSelectionListener {


  private Readable readable;
  private IBetterUser user;
  private IController controller;
  private boolean validTicker;
  private boolean validQuantity;

  /**
   * The View in charge of giving the user a GUI to work with.
   *
   * @param newUser the user that is using this program
   */
  public GuiView(IBetterUser newUser) {
    super();
    readable = new StringReader("");
    this.controller = new BetterController(newUser, readable, this);
    this.setHeading();
    JPanel mainPanel = new JPanel();
    mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.PAGE_AXIS));
    JScrollPane mainScrollPane = new JScrollPane(mainPanel);
    add(mainScrollPane);
    this.user = newUser;
    JPanel buttonsPanel = new JPanel(new GridLayout(2, 3, 20, 20));
    buttonsPanel = this.setButtons(buttonsPanel);
    mainPanel.add(buttonsPanel, BorderLayout.CENTER);
    JButton exitButton = new JButton("Exit");
    exitButton = this.exitFormat(exitButton);
    JPanel exitPanel = new JPanel(new FlowLayout(FlowLayout.LEADING));
    exitPanel.add(exitButton);
    this.add(exitPanel, BorderLayout.SOUTH);
    JPanel stuffPanel = new JPanel();
    stuffPanel.setBorder(BorderFactory.createTitledBorder("Stock Program"));
    stuffPanel.setLayout(new BoxLayout(stuffPanel, BoxLayout.PAGE_AXIS));
    stuffPanel.add(buttonsPanel);
    mainPanel.add(stuffPanel);

  }

  /**
   * Sets the format of the exit button to be red.
   *
   * @param exitButton the button we are formatting
   * @return the button with the red.
   */
  private JButton exitFormat(JButton exitButton) {
    exitButton.setBackground(Color.RED);
    exitButton.setOpaque(true);
    exitButton.setBorderPainted(false);
    exitButton.setPreferredSize(new Dimension(100, 50));
    exitButton.addActionListener(e -> System.exit(0));
    return exitButton;
  }

  /**
   * Creates the main button panel that the user will have.
   * Contains all buttons except the exit button.
   *
   * @param buttonsPanel the button panel
   * @return the button panel with the buttons
   */
  private JPanel setButtons(JPanel buttonsPanel) {
    JButton button1 = createButton("Create New Portfolio", Color.LIGHT_GRAY,
            new LineBorder(Color.CYAN, 2));
    JButton button2 = createButton("Buy Stock", Color.LIGHT_GRAY,
            new LineBorder(Color.MAGENTA, 2));
    JButton button3 = createButton("Sell Stock", Color.LIGHT_GRAY,
            new LineBorder(Color.RED, 2));
    JButton button4 = createButton("Get Portfolio Composition", Color.LIGHT_GRAY,
            new LineBorder(Color.LIGHT_GRAY, 2));
    JButton button5 = createButton("Save Portfolio", Color.LIGHT_GRAY,
            new LineBorder(Color.GREEN, 2));
    JButton button6 = createButton("Load Portfolio", Color.LIGHT_GRAY,
            new LineBorder(Color.ORANGE, 2));
    button1.setActionCommand("New Portfolio");
    button1.addActionListener(this);
    button2.setActionCommand("Buy");
    button2.addActionListener(this);
    button3.setActionCommand("sell");
    button3.addActionListener(this);
    button4.setActionCommand("Comp");
    button4.addActionListener(this);
    button5.setActionCommand("Save");
    button5.addActionListener(this);
    button6.setActionCommand("Retrieve");
    button6.addActionListener(this);
    buttonsPanel.add(button1);
    buttonsPanel.add(button2);
    buttonsPanel.add(button3);
    buttonsPanel.add(button4);
    buttonsPanel.add(button5);
    buttonsPanel.add(button6);
    return buttonsPanel;

  }

  /**
   * Sets the heading of the Main Screen to have a title and then a header.
   */
  private void setHeading() {
    setTitle("The Solid Stock Brokerz");
    setSize(1000, 400);
    JPanel headerPanel = new JPanel(new BorderLayout());
    JLabel heading = new JLabel("Welcome to the Stock Program!", JLabel.CENTER);
    heading.setFont(new Font("Serif", Font.BOLD, 24));
    headerPanel.add(heading, BorderLayout.NORTH);
    this.add(headerPanel, BorderLayout.NORTH);
  }

  /**
   * Loads the portfolio chosen.
   */
  private void retrieve() {
    String portfolioName;
    try {
      portfolioName = portfolioDropper(true);
    } catch (IllegalArgumentException e) {
      return;
    }
    String message = "retrieve\n" + portfolioName + "\n" + "\nq";
    readable = updateRead(message);
    controller = new BetterController(user, readable, this);
    try {
      controller.goNow();
    } catch (IOException e) {
      //this will never happen.
    }
  }

  /**
   * saves the portfolio to the file savedPortfolios in the res.
   */
  private void save() {
    String portfolioName;
    try {
      portfolioName = portfolioDropper(false);
    } catch (IllegalArgumentException e) {
      return;
    }
    String message = "save\n" + portfolioName + "\n" + "\nq";
    readable = updateRead(message);
    controller = new BetterController(user, readable, this);
    try {
      controller.goNow();
    } catch (IOException e) {
      //this will never happen.
    }

  }

  /**
   * Gives a series of drop downs to sell a stock in the portfolio.
   */
  private void sell() {
    String portfolioName;
    try {
      portfolioName = portfolioDropper(false);
    } catch (IllegalArgumentException e) {
      return;
    }
    List<String> availableDates = this.getSellDates(portfolioName);
    if (availableDates.isEmpty()) {
      JOptionPane.showMessageDialog(null, "You have no stocks to sell.");
      return;
    }
    String[] years = this.getYears(availableDates);
    String[] reverse = new String[years.length];
    for(int i = years.length - 1; i >= 0; i--) {
      reverse[i] = years[years.length - 1 - i];
    }
    JComboBox<String> yearComboBox = new JComboBox<>(reverse);
    int yearResult = JOptionPane.showConfirmDialog(null, yearComboBox,
            "Choose the Year to operate on", JOptionPane.OK_CANCEL_OPTION,
            JOptionPane.QUESTION_MESSAGE);
    if (this.checkResult(yearResult)) {
      return;
    }
    String year = (String) yearComboBox.getSelectedItem();
    String[] months = this.getMonths(availableDates, year);
    JComboBox<String> monthComboBox = new JComboBox<>(months);
    int monthResult = JOptionPane.showConfirmDialog(null, monthComboBox,
            "Choose the Month to operate on", JOptionPane.OK_CANCEL_OPTION,
            JOptionPane.QUESTION_MESSAGE);
    if (this.checkResult(monthResult)) {
      return;
    }
    String month = (String) monthComboBox.getSelectedItem();
    String[] days = this.getDays(availableDates, month, year);
    JComboBox<String> dayComboBox = new JComboBox<>(days);
    int dayResult = JOptionPane.showConfirmDialog(null, dayComboBox,
            "Choose the Day to operate on", JOptionPane.OK_CANCEL_OPTION,
            JOptionPane.QUESTION_MESSAGE);
    if (this.checkResult(dayResult)) {
      return;
    }
    String day = (String) monthComboBox.getSelectedItem();
    String currDate = year + "-" + month + "-" + day;
    String tempStockName;
    try {
      tempStockName = sellStockDropper(portfolioName, currDate);
    } catch (IllegalArgumentException e) {
      return;
    }
    this.sellFinalize(portfolioName, year, month, day, tempStockName);
  }

  /**
   * With all the information needed, gets the usable sellable quantity and passes it
   * into the controller.
   *
   * @param portfolioName the name of the portoflio
   * @param year          the year we are selling at
   * @param month         the month we are selling at
   * @param day           the day we are selling at
   * @param tempStockName the temporary stock name to validate dates.
   */
  private void sellFinalize(String portfolioName, String year, String month, String day,
                            String tempStockName) {

    String[] stockInfo = tempStockName.split("-");
    String stockName = stockInfo[0];
    System.out.println(stockInfo[1]);
    float totalStock = Float.parseFloat(stockInfo[1]);

    String quantityBlock = JOptionPane.showInputDialog("Quantity of selling\n must be less than "
            + totalStock);
    float quantity = 0;
    while (!validQuantity) {
      try {
        if (quantityBlock == null) {
          return;
        }
        quantity = Float.parseFloat(quantityBlock);
        if (quantity > totalStock || quantity <= 0) {
          throw new NumberFormatException();
        }
        validQuantity = true;
        String quantMessage = "sell\n" + portfolioName + "\n" + year + "\n" + month
                + "\n" + day + "\n" + stockName + "\n" + quantity + "\nq";
        readable = updateRead(quantMessage);
        controller = new BetterController(user, readable, this);
        controller.goNow();
      } catch (NumberFormatException e) {
        quantityBlock = JOptionPane.showInputDialog("That was an invalid quantity. \n" +
                "Please input a valid quantity in the form of a \npositive number less than "
                + totalStock + ":");
      } catch (IOException e) {
        //will never happen
        throw new RuntimeException(e);
      }
    }
  }

  /**
   * Checks the result of the user getting a value is the OK_Option or not.
   *
   * @param result the result
   * @return a boolean to see if the user clicked the OK_Option
   */
  private boolean checkResult(int result) {
    return result != JOptionPane.OK_OPTION;
  }

  /**
   * Creates the array for a combo box for the view to give to the user of available days.
   *
   * @param availableDates available days.
   * @param month          the chosen month
   * @param year           the chosen year
   * @return an arrays of string of the days
   */
  private String[] getDays(List<String> availableDates, String month, String year) {
    List<String> tempDays = new ArrayList<String>();
    for (int i = 0; i < availableDates.size(); i++) {
      String currDay = availableDates.get(i).substring(8, 10);
      if (!tempDays.contains(currDay) && month.equals(availableDates.get(i).substring(5, 7)) &&
              year.equals(availableDates.get(i).substring(0, 4))) {
        tempDays.add(currDay);
      }
    }

    String[] days = new String[tempDays.size()];

    for (int i = 0; i < tempDays.size(); i++) {
      days[i] = tempDays.get(i);
    }
    return days;
  }

  /**
   * Creates the array for a combo box for the view to give to the user of available months.
   *
   * @param availableDates available days.
   * @param year           the chosen year
   * @return an arrays of string of the months
   */
  private String[] getMonths(List<String> availableDates, String year) {
    List<String> tempMonths = new ArrayList<String>();
    for (int i = 0; i < availableDates.size(); i++) {
      String currMonth = availableDates.get(i).substring(5, 7);
      if (!tempMonths.contains(currMonth) && year.equals(availableDates.get(i).substring(0, 4))) {
        tempMonths.add(currMonth);
      }
    }
    String[] months = new String[tempMonths.size()];
    for (int i = 0; i < tempMonths.size(); i++) {
      months[i] = tempMonths.get(i);
    }
    return months;
  }

  /**
   * gets an array of years that the view can put into a comboBox.
   *
   * @param availableDates the dates available to the user
   * @return the Array of years.
   */
  private String[] getYears(List<String> availableDates) {
    List<String> tempYears = new ArrayList<String>();
    for (int i = 0; i < availableDates.size(); i++) {
      String currYear = availableDates.get(i).substring(0, 4);
      if (!tempYears.contains(currYear)) {
        tempYears.add(currYear);
      }
    }
    String[] years = new String[tempYears.size()];

    for (int i = 0; i < tempYears.size(); i++) {
      years[i] = tempYears.get(i);
    }
    return years;
  }

  /**
   * Gets the days you can sell a stock at.
   *
   * @param portfolioName the portfolio we are selling from
   * @return a list of days to sell a stock.
   */
  private List<String> getSellDates(String portfolioName) {
    List<String> availableDates = new ArrayList<String>();
    IBetterStock stocky = null;
    try {
      stocky = new BetterStock("AMD");
    } catch (IOException e) {
      //will never happen
    }
    List<List<String>> stockData = stocky.getHistoryData();
    String endDate = stockData.get(0).get(0);
    int size1 = stockData.size() - 1;
    String startDate = stockData.get(size1).get(0);
    List<String> dates = stocky.availableDates(startDate, endDate);

    while (!dates.isEmpty()) {
      int size = dates.size() - 1;
      try {
        String selly = this.user.getSellableAtDate(portfolioName, dates.get(size));
        if (!selly.isEmpty()) {
          availableDates.addAll(dates);
          break;
        }
      } catch (IllegalArgumentException e) {
        //does nothing and continues the program
      }
      dates.remove(size);
    }
    return availableDates;
  }

  /**
   * Gets the composition at a certain day by prompting the user for the portfolio and date.
   */
  private void getComp() {
    String portfolioName;
    try {
      portfolioName = portfolioDropper(false);
    } catch (IllegalArgumentException e) {
      return;
    }
    IBetterStock dateHelp = null;
    try {
      dateHelp = new BetterStock("AMD");
    } catch (IOException e) {
      //will never happen
    }
    List<String> dates = dateHelp.availableDates("1999-11-01", "2024-06-18");
    String[] years = this.getYears(dates);
    String[] reverseYears = new String[years.length];
    for(int i = 0; i < years.length; i++) {
      reverseYears[years.length - i - 1] = years[i];
    }
    JComboBox<String> yearComboBox = new JComboBox<>(reverseYears);
    int yearResult = JOptionPane.showConfirmDialog(null, yearComboBox,
            "Choose the Year: ", JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
    if (this.checkResult(yearResult)) {
      return;
    }
    String year = (String) yearComboBox.getSelectedItem();
    String[] months = this.getMonths(dates, year);
    JComboBox<String> monthComboBox = new JComboBox<>(months);
    int monthResult = JOptionPane.showConfirmDialog(null, monthComboBox,
            "Choose the Month: ", JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
    if (this.checkResult(monthResult)) {
      return;
    }
    String month = (String) monthComboBox.getSelectedItem();
    String[] days = this.getDays(dates, month, year);
    JComboBox<String> dayComboBox = new JComboBox<>(days);
    int dayResult = JOptionPane.showConfirmDialog(null, dayComboBox,
            "Choose the Day: ", JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
    if (dayResult != JOptionPane.OK_OPTION) {
      return;
    }
    String day = (String) dayComboBox.getSelectedItem();
    String inserter = "distribution\n" + portfolioName + "\n" + year + "\n" + month + "\n" + day
            + "\n" + "q";
    readable = updateRead(inserter);
    controller = new BetterController(user, readable, this);
    try {
      controller.goNow();
    } catch (IOException e) {
      //this will never happen.
    }
  }

  /**
   * Prompts the user for valid inputs and puts them in the controller to buy a certain stock.
   * To a certain portoflio.
   */
  private void buy() {
    String portfolioName;
    try {
      portfolioName = portfolioDropper(false);
    } catch (IllegalArgumentException e) {
      return;
    }
    String stockName = JOptionPane.showInputDialog("Stock Name");
    String stockMessage = "buy\n" + portfolioName + "\n" + stockName + "\n" + "q";
    readable = updateRead(stockMessage);
    controller = new BetterController(user, readable, this);
    try {
      controller.goNow();
    } catch (IOException e) {
      //this will never happen.
    }
    while (!validTicker) {
      stockName = this.buyValidTicker(portfolioName);
      if (stockName == null) {
        return;
      }
    }
    IBetterStock currStock = null;
    try {
      currStock = new BetterStock("AMD");
    } catch (IOException e) {
      //will never happen
    }
    List<List<String>> stockData = currStock.getHistoryData();
    String endDate = stockData.get(0).get(0);
    int size1 = stockData.size() - 1;
    String startDate = stockData.get(size1).get(0);
    List<String> dates = currStock.availableDates(startDate, endDate);
    List<String> reverse = new ArrayList<>();
    for(int i = dates.size() - 1; i >= 0; i--) {
      reverse.add(dates.get(i));
    }
    String[] years = this.getYears(reverse);
    JComboBox<String> yearComboBox = new JComboBox<>(years);
    int yearResult = JOptionPane.showConfirmDialog(null, yearComboBox,
            "Choose the Year: ", JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
    if (this.checkResult(yearResult)) {
      return;
    }
    String year = (String) yearComboBox.getSelectedItem();
    String[] months = this.getMonths(dates, year);
    JComboBox<String> monthComboBox = new JComboBox<>(months);
    int monthResult = JOptionPane.showConfirmDialog(null, monthComboBox,
            "Choose the Month: ", JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
    if (this.checkResult(monthResult)) {
      return;
    }
    String month = (String) monthComboBox.getSelectedItem();
    String[] days = this.getDays(dates, month, year);
    JComboBox<String> dayComboBox = new JComboBox<>(days);
    int dayResult = JOptionPane.showConfirmDialog(null, dayComboBox,
            "Choose the Day: ", JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
    if (dayResult != JOptionPane.OK_OPTION) {
      return;
    }
    String day = (String) dayComboBox.getSelectedItem();
    this.finalizeBuy(portfolioName, stockName, year, month, day);
    validQuantity = false;
  }

  /**
   * Finalizes the buy to buy the stock.
   *
   * @param portfolioName the name of the portfolio
   * @param stockName     the stock we are buying
   * @param year          the year we are buying it at
   * @param month         the month we are buying it at
   * @param day           the day we are buying from
   */
  private void finalizeBuy(String portfolioName, String stockName, String year,
                           String month, String day) {
    String quantityBlock = JOptionPane.showInputDialog("Quantity of purchase");
    int quantity = 0;
    while (!validQuantity) {
      try {
        if (quantityBlock == null) {
          return;
        }
        quantity = Integer.parseInt(quantityBlock);
        if (quantity <= 0) {
          throw new NumberFormatException("Negative buy");
        }
        validQuantity = true;
        String quantMessage = "buy\n" + portfolioName + "\n" + stockName + "\n" + year + "\n"
                + month + "\n" + day + "\n" + quantity + "\nq";
        readable = updateRead(quantMessage);
        controller = new BetterController(user, readable, this);
        try {
          controller.goNow();
        } catch (IOException e) {
          //this will never happen.1
        }
      } catch (NumberFormatException e) {
        quantityBlock = JOptionPane.showInputDialog("That was an invalid quantity. \n" +
                "Please input a valid quantity in the form of an Integer:");
      }
    }
  }

  /**
   * Checks that the user put a valid tickerSymbol and prompts them again based on the controller.
   *
   * @param portfolioName the name of the portfolio
   * @return a string of the ticekr
   */
  private String buyValidTicker(String portfolioName) {
    String stockName = JOptionPane.showInputDialog("That stock doesn't exist.\n" +
            "Please input a valid stock name:");
    String stockMessage = "buy\n" + portfolioName + "\n" + stockName + "\n" + "q";
    readable = updateRead(stockMessage);
    controller = new BetterController(user, readable, this);
    try {
      controller.goNow();
    } catch (IOException e) {
      //this will never happen.
    }
    return stockName;
  }

  /**
   * Prompts the user for a name and creates the new portfolio.
   */
  private void newPortfolio() {
    String portfolio = JOptionPane.showInputDialog("Please enter the name of the new Portfolio");
    if (user.getPortfolios().contains(portfolio)) {
      portfolio = JOptionPane.showInputDialog("That Portfolio already exists.\n" +
              "Please enter the name of the new Portfolio");
    }
    if (portfolio != null && !portfolio.isEmpty()) {
      String newPortfolio = "new-portfolio\n" + portfolio + "\nq";
      readable = updateRead(newPortfolio);
      controller = new BetterController(user, readable, this);
      try {
        controller.goNow();
      } catch (IOException e) {
        //this will never happen.
      }
    }
  }

  /**
   * Creates a dropper for the user to pick stocks they can sell.
   *
   * @param portfolioName the name of the portfolio we are selling from
   * @param date          the date we are selling from
   * @return a string of the stock we are selling
   * @throws IllegalArgumentException if they exit.
   */
  private String sellStockDropper(String portfolioName, String date)
          throws IllegalArgumentException {
    String[] sellables = user.getSellableAtDate(portfolioName, date).split("\n");

    String[] tickers = new String[sellables.length];
    String[] quantity = new String[sellables.length];
    for (int i = 0; i < sellables.length; i++) {
      tickers[i] = sellables[i].split(" ", 2)[0];
      quantity[i] = sellables[i].split(" ", 2)[1];
    }

    JComboBox<String> sellableComboBox = new JComboBox<>(tickers);

    int stockResult = JOptionPane.showConfirmDialog(null, sellableComboBox,
            "Choose stock to sell", JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
    if (this.checkResult(stockResult)) {
      throw new IllegalArgumentException("Exit");
    }
    String quant = "";
    String stockName = (String) sellableComboBox.getSelectedItem();
    for (int i = 0; i < tickers.length; i++) {
      if (tickers[i].equals(stockName)) {
        quant = quantity[i];
      }
    }

    return stockName + "-" + quant;

  }

  /**
   * Creates a drop down menu for the portfolios.
   *
   * @param saved if this is taken from the saved portfolios or not
   * @return a string of the portfolio chosen.
   * @throws IllegalArgumentException if no portfolios are available.
   */
  private String portfolioDropper(boolean saved) throws IllegalArgumentException {
    List<String> tempPortfolios = new ArrayList<>();
    if (!saved) {
      tempPortfolios.addAll(user.getPortfolios());
    } else {
      String savedP = user.getSavedPortfolios();
      String[] beginSavedList = savedP.split("\n");
      for (int i = 0; i < beginSavedList.length; i++) {
        tempPortfolios.add(beginSavedList[i]);
      }
    }
    if (tempPortfolios.isEmpty() || tempPortfolios.get(0).isEmpty()) {
      try {
        this.noPortfolios();
      } catch (IOException e) {
        //this will never happen
      }
      throw new IllegalArgumentException("No portfolios to work on");
    }
    String[] portfolios = new String[tempPortfolios.size()];
    for (int i = 0; i < tempPortfolios.size(); i++) {
      portfolios[i] = tempPortfolios.get(i);
    }
    JComboBox<String> portfolioComboBox = new JComboBox<>(portfolios);
    int portfolioResult = JOptionPane.showConfirmDialog(null, portfolioComboBox,
            "Choose a Portfolio", JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
    if (portfolioResult != JOptionPane.OK_OPTION) {
      throw new IllegalArgumentException("Exit");
    }
    String portfolioName = (String) portfolioComboBox.getSelectedItem();
    return portfolioName;

  }

  /**
   * Creates a readable with the string.
   *
   * @param s the message for the readable
   * @return the new readable
   */
  private Readable updateRead(String s) {
    return new StringReader(s);
  }

  @Override
  public void welcome() throws IOException {
  //Not used in this iteration.
  }

  @Override
  public void farewellMessage() throws IOException {
  //Not used in this iteration.
  }

  @Override
  public void promptUser() throws IOException {
  //Not used in this iteration.
  }

  @Override
  public void displayMessage(String message) throws IOException {
    this.compositionMessage(message);
    this.invalidTickerMessage(message);
    this.dateMessage(message);
    this.positiveMessage(message);
    this.successfulMessage(message);
    this.notComposedMessage(message);
    this.purchaseOfMessage(message);
    this.sellingOfMessage(message);
  }

  /**
   * Shows the message dialog if the user sold successfully.
   *
   * @param message the message from the controller
   */
  private void sellingOfMessage(String message) {
    if (message.contains("Selling of")) {
      JOptionPane.showMessageDialog(null, message);
    }
  }

  /**
   * Shows the message dialog if the user bought successfully.
   *
   * @param message the message from the controller
   */
  private void purchaseOfMessage(String message) {
    if (message.contains("Purchase of")) {
      JOptionPane.showMessageDialog(null, message);
    }
  }

  /**
   * Shows the message dialog if the portoflio is not composed of anything.
   *
   * @param message the message from the controller
   */
  private void notComposedMessage(String message) {
    if (message.contains("Not Composed of anything:")) {
      JOptionPane.showMessageDialog(null, message);
    }
  }

  /**
   * Shows the message dialog if the inputted a valid quantity to buy/sell.
   *
   * @param message the message from the controller
   */
  private void successfulMessage(String message) {
    if (message.contains("was successful!")) {
      validQuantity = true;
    }
  }

  /**
   * Shows the message dialog if the user inputted an invalid quantity to buy/sell.
   *
   * @param message the message from the controller
   */
  private void positiveMessage(String message) {
    if (message.contains("positive")) {
      validQuantity = false;
    }
  }

  /**
   * Shows the message dialog if the user inputted a date correctly.
   *
   * @param message the message from the controller
   */
  private void dateMessage(String message) {
    if (message.contains("Date")) {
      validTicker = true;
    }
  }

  /**
   * Shows the message dialog if the user inputted an invalid ticker.
   *
   * @param message the message from the controller
   */
  private void invalidTickerMessage(String message) {
    if (message.equals("Invalid stock ticker. Please input a valid ticker symbol -")) {
      validTicker = false;
    }
  }

  /**
   * Shows the message dialog if the user has a portfolio of nothing.
   *
   * @param message the message from the controller
   */
  private void compositionMessage(String message) {
    String dataMessage;
    if (message.contains("is: 0.0\n") ||
            message.contains("No Stocks Are Owned At This")) {
      dataMessage = "You have no stocks available at this date \n" +
              "If you bought stocks today, they will appear " +
              "tomorrow as they are bought at the end of this day.";
      JOptionPane.showMessageDialog(null, dataMessage);
    } else if (message.contains("The value of the portfolio")) {

      JOptionPane.showMessageDialog(null, message);
    }
  }

  @Override
  public void askTicker() throws IOException {
  //Not used in this iteration of View.
  }

  @Override
  public void askStartDate() throws IOException {
  //Not used in this iteration of View.
  }

  @Override
  public void askDate() throws IOException {
  //Not used in this iteration of View.
  }

  @Override
  public void askEndDate() throws IOException {
  //Not used in this iteration of View.
  }

  @Override
  public void movingAverage() throws IOException {
  //Not used in this iteration of View.
  }

  @Override
  public void portfolioSelect() throws IOException {
  //Not used in this iteration of View.
  }

  @Override
  public void displayPortfolio(String portfolio) throws IOException {
  //Not used in this iteration of View.
  }

  @Override
  public void noPortfolios() throws IOException {
    String noPortfolioMessage = "Sorry, you seem to have no portoflios. \n Please create one first";
    JOptionPane.showMessageDialog(null, noPortfolioMessage);
  }

  @Override
  public void newPortfolioMessage() throws IOException {
    String newPortMessage = "Your portfolio has been created!";
    JOptionPane.showMessageDialog(null, newPortMessage);
  }

  @Override
  public void addingMessage() throws IOException {
  //Not used in this iteration of View.
  }

  @Override
  public void errorMessage(String e) throws IOException {
  //Not used in this iteration of View.
  }

  @Override
  public void itemStateChanged(ItemEvent e) {
  //Not used in this iteration of View.
  }

  @Override
  public void valueChanged(ListSelectionEvent e) {
  //Not used in this iteration of View.
  }

  /**
   * Helper to make a button.
   *
   * @param text       the text of the button
   * @param background background color
   * @param border     line border
   * @return a created button.
   */
  private JButton createButton(String text, Color background, LineBorder border) {
    JButton button = new JButton(text);
    button.setPreferredSize(new Dimension(100, 100));
    button.setBackground(background);
    button.setOpaque(true);
    button.setBorder(border);
    return button;
  }

  @Override
  public void actionPerformed(ActionEvent argument) {
    switch (argument.getActionCommand()) {
      case "New Portfolio":
        newPortfolio();
        break;
      case "Buy":
        buy();
        break;
      case "sell":
        sell();
        break;
      case "Comp":
        getComp();
        break;
      case "Save":
        save();
        break;
      case "Retrieve":
        retrieve();
        break;
      default:
        //User did nothing.
        break;
    }
  }
}
