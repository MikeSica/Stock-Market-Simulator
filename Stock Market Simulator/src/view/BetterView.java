package view;

import java.io.IOException;

/**
 * An implementation of IView containing all it's methods which extends BasicView.
 * It's meant to print the messages that the user sees, and it overrides the promptUser and
 * noPortfolios messages to account for additional functionality.
 */
public class BetterView extends BasicView {
  private Appendable appendable;

  /**
   * Constructor that creates a BetterView object and sets the appendable field.
   *
   * @param ap the appendable
   */
  public BetterView(Appendable ap) {
    super(ap);
    this.appendable = ap;
  }

  @Override
  public void noPortfolios() throws IOException {
    writeMessage("No portfolios available, create a new one or retrieve a saved one!"
            + System.lineSeparator());
  }

  @Override
  public void promptUser() throws IOException {
    printMenu();
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
   * Displays the new general menu with the things the user can do.
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
    writeMessage("'stock-chart' to display a stock chart" + System.lineSeparator());
    writeMessage("'portfolio-chart' to display a portfolio chart" + System.lineSeparator());
    writeMessage("'rebalance' to rebalance the portfolio" + System.lineSeparator());
    writeMessage("'sell' to sell stocks from a portfolio" + System.lineSeparator());
    writeMessage("'buy' to buy stocks for a portfolio" + System.lineSeparator());
    writeMessage("'composition' to display portfolio composition" + System.lineSeparator());
    writeMessage("'save' to save a portfolio" + System.lineSeparator());
    writeMessage("'retrieve' to retrieve a saved portfolio" + System.lineSeparator());
    writeMessage("'distribution' to display portfolio distribution" + System.lineSeparator());
    writeMessage("'menu' to view the menu"
            + System.lineSeparator());
    writeMessage("Press q or quit to quit the program" + System.lineSeparator());
  }
}
