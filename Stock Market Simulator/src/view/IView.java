package view;

import java.io.IOException;

/**
 * The IView interface represents a view for the controller to call to.
 * It provides methods to display various types of information to the user.
 */
public interface IView {

  /**
   * Displays a welcome message to the user when they start the program.
   */
  public void welcome() throws IOException;

  /**
   * Displays a farewell message to the user when they quit the program.
   */
  public void farewellMessage() throws IOException;

  /**
   * Asks the user to type a message.
   */
  public void promptUser() throws IOException;

  /**
   * Displays a message to the user.
   *
   * @param message message to display
   */
  public void displayMessage(String message) throws IOException;

  /**
   * Asks the user for a tickerSymbol.
   */
  public void askTicker() throws IOException;

  /**
   * Asks the user to input a beginning date.
   */
  public void askStartDate() throws IOException;

  /**
   * Asks the user to input a date.
   */
  public void askDate() throws IOException;

  /**
   * Asks the user to input an ending date.
   */
  public void askEndDate() throws IOException;

  /**
   * Prompts the user to examine the moving average of a stock.
   */
  public void movingAverage() throws IOException;


  /**
   * Displays message telling user to select a portfolio.
   */
  public void portfolioSelect() throws IOException;

  /**
   * Displays the name of a portfolio in a statement.
   *
   * @param portfolio name of the portfolio
   */
  public void displayPortfolio(String portfolio) throws IOException;

  /**
   * Displays a message when there are no portfolios available to operate on.
   */
  public void noPortfolios() throws IOException;

  /**
   * Displays a message to user when they try to make a new portfolio.
   */
  public void newPortfolioMessage() throws IOException;

  /**
   * Message displayed to user when asking them if they want to continue adding stock
   * to a portfolio or finish constructing the portfolio.
   */
  public void addingMessage() throws IOException;


  /**
   * Displays error message to the user.
   *
   * @param e error message to display
   */
  public void errorMessage(String e) throws IOException;
}
