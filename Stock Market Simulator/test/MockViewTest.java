import org.junit.Test;

import java.io.IOException;

import view.IView;

import static junit.framework.TestCase.assertEquals;

/**
 * A Junit test class for the MockView class.
 */
public class MockViewTest {

  private Appendable ap;

  @Test
  public void testWelcome() throws IOException {
    ap = new StringBuilder();
    IView view = new MockView(ap);
    view.welcome();
    assertEquals("Welcome Message", ap.toString());
  }

  @Test
  public void testPromptUser() throws IOException {
    ap = new StringBuilder();
    IView view = new MockView(ap);
    view.promptUser();
    assertEquals("Prompt user menu", ap.toString());
  }

  @Test
  public void testDisplayMessage() throws IOException {
    ap = new StringBuilder();
    IView view = new MockView(ap);
    view.displayMessage("random");
    assertEquals("Display a message", ap.toString());
  }

  @Test
  public void testAskTicker() throws IOException {
    ap = new StringBuilder();
    IView view = new MockView(ap);
    view.askTicker();
    assertEquals("Asks for ticker", ap.toString());
  }

  @Test
  public void testAskStartDate() throws IOException {
    ap = new StringBuilder();
    IView view = new MockView(ap);
    view.askStartDate();
    assertEquals("Asks for start date", ap.toString());
  }

  @Test
  public void testAskDate() throws IOException {
    ap = new StringBuilder();
    IView view = new MockView(ap);
    view.askDate();
    assertEquals("Asks for date", ap.toString());
  }

  @Test
  public void testAskEndDate() throws IOException {
    ap = new StringBuilder();
    IView view = new MockView(ap);
    view.askEndDate();
    assertEquals("Asks for end date", ap.toString());
  }

  @Test
  public void testMovingAverage() throws IOException {
    ap = new StringBuilder();
    IView view = new MockView(ap);
    view.movingAverage();
    assertEquals("Asks user for int for days", ap.toString());
  }

  @Test
  public void testNewPortfolioMessage() throws IOException {
    ap = new StringBuilder();
    IView view = new MockView(ap);
    view.newPortfolioMessage();
    assertEquals("Asks user to enter new portfolio name", ap.toString());
  }

  @Test
  public void testAddingMessage() throws IOException {
    ap = new StringBuilder();
    IView view = new MockView(ap);
    view.addingMessage();
    assertEquals("Message when adding a portfolio", ap.toString());
  }

  @Test
  public void testDisplayPortfolio() throws IOException {
    ap = new StringBuilder();
    IView view = new MockView(ap);
    view.displayPortfolio("portfolio");
    assertEquals("Displays portfolio name", ap.toString());
  }

  @Test
  public void testNoPortfolios() throws IOException {
    ap = new StringBuilder();
    IView view = new MockView(ap);
    view.noPortfolios();
    assertEquals("message when no portfolios exists", ap.toString());
  }

  @Test
  public void testPortfolioSelect() throws IOException {
    ap = new StringBuilder();
    IView view = new MockView(ap);
    view.portfolioSelect();
    assertEquals("instructs user to select portfolio", ap.toString());
  }

  @Test
  public void testErrorMessage() throws IOException {
    ap = new StringBuilder();
    IView view = new MockView(ap);
    view.errorMessage("error");
    assertEquals("Error Message", ap.toString());
  }

  @Test
  public void testFarewellMessage() throws IOException {
    ap = new StringBuilder();
    IView view = new MockView(ap);
    view.farewellMessage();
    assertEquals("Farewell Message", ap.toString());
  }


}
