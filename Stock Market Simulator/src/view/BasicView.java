package view;

import java.io.IOException;

/**
 * Basic implementation of View containing all it's methods.
 * It's meant to print the messages that the user sees.
 */
public class BasicView implements IView {
  private Appendable appendable;

  /**
   * Constructor that creates a BasicView object and sets the appendable field.
   *
   * @param ap the appendable
   */
  public BasicView(Appendable ap) {
    this.appendable = ap;
  }

  @Override
  public void welcome() throws IOException {
    welcomeMessage();
  }

  @Override
  public void farewellMessage() throws IOException {
    appendable.append("Thank you for using this program!");
  }


  @Override
  public void promptUser() throws IOException {
    prompt();
  }

  @Override
  public void displayMessage(String message) throws IOException {
    appendable.append(message);
  }

  @Override
  public void askTicker() throws IOException {
    tickerPrompt();
  }

  @Override
  public void askStartDate() throws IOException {
    startPrompt();
  }

  @Override
  public void askDate() throws IOException {
    datePrompt();
  }


  @Override
  public void askEndDate() throws IOException {
    endPrompt();
  }


  @Override
  public void movingAverage() throws IOException {
    movingAverageprompt();
  }


  @Override
  public void newPortfolioMessage() throws IOException {
    appendable.append("Type the name of your new Portfolio: ");
  }

  @Override
  public void addingMessage() throws IOException {
    appendable.append("Input a ticker symbol to add a stock or done if you"
            + " want to build the portfolio: ");
  }


  @Override
  public void displayPortfolio(String portfolio) throws IOException {
    appendable.append("Portfolio - " + portfolio);
  }

  @Override
  public void noPortfolios() throws IOException {
    appendable.append("No portfolios avaliable to operate on" + System.lineSeparator()
            + "either create a new portfolio or select a different menu option");
  }

  @Override
  public void portfolioSelect() throws IOException {
    appendable.append("Choose one of the above portfolios: ");
  }


  @Override
  public void errorMessage(String e) throws IOException {
    writeMessage("Error: " + e + System.lineSeparator());
  }

  /**
   * Helper to add a string to the message so it writes and displays it.
   *
   * @param message the inputted string
   * @throws IllegalStateException if the append fails
   */
  private void writeMessage(String message) throws IOException {
    appendable.append(message);
  }

  /**
   * Displays the general menu with the things the user can do.
   */
  private void printMenu() throws IOException {
    writeMessage("Supported user instructions are: " + System.lineSeparator());
    writeMessage("'change' to Examine the gain/loss of stocks over a specified date"
            + System.lineSeparator());
    writeMessage("'average' to examine the x-day moving average of a stock"
            + System.lineSeparator());
    writeMessage("'crossover' to determine the x-day crossovers for a stock"
            + System.lineSeparator());
    writeMessage("'portfolio-value' to determine the value of an existing portfolio"
            + System.lineSeparator());
    writeMessage("'new-portfolio' to create a new Portfolio"
            + System.lineSeparator());
    writeMessage("'add-portfolio' to add to an existing portfolio"
            + System.lineSeparator());
    writeMessage("'menu' to view the menu"
            + System.lineSeparator());
    writeMessage("Press q or quit to quit the program" + System.lineSeparator());
  }

  /**
   * Adds the welcome to the stock program message to the string builder.
   * Also prints the menu.
   * This is meant to be a helper for the welcome message function to display the welcome message.
   */
  private void welcomeMessage() throws IOException {
    writeMessage("Welcome to the Stock Program!");
  }

  /**
   * Displays the general prompt that the user will see when they need to type things.
   * Along with the menu.
   */
  private void prompt() throws IOException {
    printMenu();
    writeMessage("Type instruction: ");

  }

  /**
   * Prompt that shows when the user needs to input a starting date for an action.
   * This works for setting the start date when you want to define the date range.
   */
  private void startPrompt() throws IOException {
    writeMessage("Input StartDate (YYYY-MM-DD): ");
  }

  /**
   * Prompt that shows when the user needs to input a date for an action.
   * Works for when the user needs to get things at a certain date.
   */
  private void datePrompt() throws IOException {
    writeMessage("Input Date (YYYY-MM-DD): ");
  }

  /**
   * Prompt that shows when the user needs to input an ending date for an action.
   * This works for setting the end date when you want to define the date range.
   */
  private void endPrompt() throws IOException {
    writeMessage("Input EndDate (YYYY-MM-DD): ");
  }

  /**
   * Prompt that the user sees when inputting a ticker symbol for a stock.
   */
  private void tickerPrompt() throws IOException {
    writeMessage("Input Stock Ticker Symbol: ");
  }

  /**
   * Prompt that the user sees when they need to input the x for the x-day moving average.
   */
  private void movingAverageprompt() throws IOException {
    writeMessage("Input the day moving average: ");
  }

}

