import java.io.IOException;

/**
 * A MockView is an instance of a view which is
 * used to see if code paths are correct.
 */
class MockBetterView extends MockView {


  private Appendable ap;

  /**
   * Constructor that creates a BasicView object and sets message to a new StringBuilder.
   *
   * @param ap the appendable
   */
  public MockBetterView(Appendable ap) {
    super(ap);
    this.ap = ap;
  }

  @Override
  public void welcome() throws IOException {
    ap.append("Welcome Message");

  }

  @Override
  public void farewellMessage() throws IOException {
    ap.append("Farewell Message");
  }


  @Override
  public void promptUser() throws IOException {
    ap.append("Prompt user menu");
  }

  @Override
  public void displayMessage(String message) throws IOException {
    ap.append("Display a message");
  }

  @Override
  public void askTicker() throws IOException {
    ap.append("Asks for ticker");
  }

  @Override
  public void askStartDate() throws IOException {
    ap.append("Asks for start date");
  }

  @Override
  public void askDate() throws IOException {
    ap.append("Asks for date");
  }


  @Override
  public void askEndDate() throws IOException {
    ap.append("Asks for end date");
  }


  @Override
  public void movingAverage() throws IOException {
    ap.append("Asks user for int for days");
  }


  @Override
  public void newPortfolioMessage() throws IOException {
    ap.append("Asks user to enter new portfolio name");
  }

  @Override
  public void addingMessage() throws IOException {
    ap.append("Message when adding a portfolio");
  }


  @Override
  public void displayPortfolio(String portfolio) throws IOException {
    ap.append("Displays portfolio name");
  }

  @Override
  public void noPortfolios() throws IOException {
    ap.append("message when no portfolios exists");
  }

  @Override
  public void portfolioSelect() throws IOException {
    ap.append("instructs user to select portfolio");
  }


  @Override
  public void errorMessage(String e) throws IOException {
    ap.append("Error Message");
  }

}


