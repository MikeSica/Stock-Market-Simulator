import org.junit.Test;

import java.io.IOException;
import java.io.StringReader;

import view.BasicView;
import view.IView;

import static junit.framework.TestCase.assertEquals;

/**
 * A Junit test class for the Mock Controller and Mock Model.
 */
public class MockControllerAndModelTest {


  @Test
  public void testChange() throws IOException {
    Readable in = new StringReader("change\nq");
    Appendable ap = System.out;
    MockUser user = new MockUser();
    IView view = new BasicView(ap);
    MockController test = new MockController(user, in, view);
    test.goNow();
    String log = test.getLog();
    assertEquals("changeCalled-StockChangegetPriceAtClosingg"
            + "etPriceAtClosingquitChanged-quit", log);
  }

  @Test
  public void testAverage() throws IOException {
    Readable in = new StringReader("average\nq");
    Appendable ap = System.out;
    MockUser user = new MockUser();
    IView view = new BasicView(ap);
    MockController test = new MockController(user, in, view);
    test.goNow();
    String log = test.getLog();
    assertEquals("averageCalled-movingAveragegetDatePlace"
            + "getPriceAtClosingquitChanged-quit", log);
  }

  @Test
  public void testNewPortfolio() throws IOException {
    Readable in = new StringReader("new-portfolio\nq");
    Appendable ap = System.out;
    MockUser user = new MockUser();
    IView view = new BasicView(ap);
    MockController test = new MockController(user, in, view);
    test.goNow();
    String log = test.getLog();
    assertEquals("new-portfolioCalled-addPortfolioquitChanged-quit", log);
  }

  @Test
  public void testPortfolioValue() throws IOException {
    Readable in = new StringReader("portfolio-value\nq");
    Appendable ap = System.out;
    MockUser user = new MockUser();
    IView view = new BasicView(ap);
    MockController test = new MockController(user, in, view);
    test.goNow();
    String log = test.getLog();
    assertEquals("portfolio-valueCalled-getValueOfPortfolio"
            + "valueOfPortfoliogetStockPricegetPriceAtClosingquitChanged-quit", log);
  }

  @Test
  public void testAddPortfolio() throws IOException {
    Readable in = new StringReader("add-portfolio\nq");
    Appendable ap = System.out;
    MockUser user = new MockUser();
    IView view = new BasicView(ap);
    MockController test = new MockController(user, in, view);
    test.goNow();
    String log = test.getLog();
    assertEquals("add-portfolioCalled-updatePortfolioquitChanged-quit", log);
  }

  @Test
  public void testMenu() throws IOException {
    Readable in = new StringReader("menu\nq");
    Appendable ap = System.out;
    MockUser user = new MockUser();
    IView view = new BasicView(ap);
    MockController test = new MockController(user, in, view);
    test.goNow();
    String log = test.getLog();
    assertEquals("menuCalled-quitChanged-quit", log);
  }

  @Test
  public void testCrossover() throws IOException {
    Readable in = new StringReader("crossover\nq");
    Appendable ap = System.out;
    MockUser user = new MockUser();
    IView view = new BasicView(ap);
    MockController test = new MockController(user, in, view);
    test.goNow();
    String log = test.getLog();
    assertEquals("crossedCalled-crossOverDaysgetDatePlacegetDatePla"
            + "cegetDatePlacegetPriceAtClosingquitChanged-quit", log);
  }

  @Test
  public void testQuit() throws IOException {
    Readable in = new StringReader("q");
    Appendable ap = System.out;
    MockUser user = new MockUser();
    IView view = new BasicView(ap);
    MockController test = new MockController(user, in, view);
    test.goNow();
    String log = test.getLog();
    assertEquals("quitChanged-quit", log);
  }

  @Test
  public void testInvalid() throws IOException {
    Readable in = new StringReader("averafehf\nq");
    Appendable ap = System.out;
    MockUser user = new MockUser();
    IView view = new BasicView(ap);
    MockController test = new MockController(user, in, view);
    test.goNow();
    String log = test.getLog();
    assertEquals("badInput-quitChanged-quit", log);
  }

  @Test
  public void testAllInputs() throws IOException {
    Readable in = new StringReader("average\ncrossover"
            + "\nnew-portfolio\nchange\n"
            + "add-portfolio\nportfolio-value\nmenu\nvbdhsb\nq");
    Appendable ap = System.out;
    MockUser user = new MockUser();
    IView view = new BasicView(ap);
    MockController test = new MockController(user, in, view);
    test.goNow();
    String log = test.getLog();
    assertEquals("averageCalled-movingAveragegetDatePl"
            + "acegetPriceAtClosingcrossedCalled-crossOverDaysget"
            + "DatePlacegetDatePlacegetDatePlacegetPriceAtClosing"
            + "new-portfolioCalled-addPortfoliochangeCalled-Stock"
            + "ChangegetPriceAtClosinggetPriceAtClosingadd-portfo"
            + "lioCalled-updatePortfolioportfolio-valueCalled-get"
            + "ValueOfPortfoliovalueOfPortfoliogetStockPricegetPr"
            + "iceAtClosingmenuCalled-badInput-quitChanged-quit", log);
  }


}

