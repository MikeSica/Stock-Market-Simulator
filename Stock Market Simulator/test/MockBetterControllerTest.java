import org.junit.Test;

import java.io.IOException;
import java.io.StringReader;

import view.BasicView;
import view.IView;

import static junit.framework.TestCase.assertEquals;

/**
 * A Junit test class for the Mock Better Controller and Mock Model.
 */
public class MockBetterControllerTest {

  @Test
  public void testSave() throws IOException {
    Readable in = new StringReader("save\nq");
    Appendable ap = System.out;
    MockBetterUser user = new MockBetterUser();
    IView view = new BasicView(ap);
    MockBetterController test = new MockBetterController(user, in, view);
    test.goNow();
    String log = test.getLog();
    assertEquals("saveCalledController-SaveCalled-quitChanged", log);
  }

  @Test
  public void testRetrieve() throws IOException {
    Readable in = new StringReader("retrieve\nq");
    Appendable ap = System.out;
    MockBetterUser user = new MockBetterUser();
    IView view = new BasicView(ap);
    MockBetterController test = new MockBetterController(user, in, view);
    test.goNow();
    String log = test.getLog();
    assertEquals("retrieveCalledController-LoadCalled-quitChanged", log);
  }

  @Test
  public void testBuyStock() throws IOException {
    Readable in = new StringReader("buy\nq");
    Appendable ap = System.out;
    MockBetterUser user = new MockBetterUser();
    IView view = new BasicView(ap);
    MockBetterController test = new MockBetterController(user, in, view);
    test.goNow();
    String log = test.getLog();
    assertEquals("buyCalledController-getStockPricegetPriceAtClosingBuyStockCalled"
            + "-quitChanged", log);
  }

  @Test
  public void testStockChart() throws IOException {
    Readable in = new StringReader("stock-chart\nq");
    Appendable ap = System.out;
    MockBetterUser user = new MockBetterUser();
    IView view = new BasicView(ap);
    MockBetterController test = new MockBetterController(user, in, view);
    test.goNow();
    String log = test.getLog();
    assertEquals("stockChartController-StockPreformanceOverTimeCalled-quitChanged", log);
  }

  @Test
  public void testPortfolioChart() throws IOException {
    Readable in = new StringReader("portfolio-chart\nq");
    Appendable ap = System.out;
    MockBetterUser user = new MockBetterUser();
    IView view = new BasicView(ap);
    MockBetterController test = new MockBetterController(user, in, view);
    test.goNow();
    String log = test.getLog();
    assertEquals("portfolioChartController-GetCompAtDateCalledpreformanceOverTi"
            + "meCalledGetPortfolioPeformance-quitChanged", log);
  }

  @Test
  public void testRebalance() throws IOException {
    Readable in = new StringReader("rebalance\nq");
    Appendable ap = System.out;
    MockBetterUser user = new MockBetterUser();
    IView view = new BasicView(ap);
    MockBetterController test = new MockBetterController(user, in, view);
    test.goNow();
    String log = test.getLog();
    assertEquals("rebalanceCalledController-getStockPricegetPriceAtClosingf"
            + "indPriceQuantityCalledgetStockPriceCalledStockSellStockCalledgetStockPrice"
            + "getPriceAtClosingBuyStockCalledRebalanceCalled-quitChanged", log);
  }

  @Test
  public void testBuy() throws IOException {
    Readable in = new StringReader("buy\nq");
    Appendable ap = System.out;
    MockBetterUser user = new MockBetterUser();
    IView view = new BasicView(ap);
    MockBetterController test = new MockBetterController(user, in, view);
    test.goNow();
    String log = test.getLog();
    assertEquals("buyCalledController-getStockPricegetPriceAtClosingBuyS"
            + "tockCalled-quitChanged", log);
  }

  @Test
  public void testSell() throws IOException {
    Readable in = new StringReader("sell\nq");
    Appendable ap = System.out;
    MockBetterUser user = new MockBetterUser();
    IView view = new BasicView(ap);
    MockBetterController test = new MockBetterController(user, in, view);
    test.goNow();
    String log = test.getLog();
    assertEquals("sellCalledController-getStockPriceCalledSto"
            + "ckSellStockCalled-quitChanged", log);
  }

  @Test
  public void testMenu() throws IOException {
    Readable in = new StringReader("menu\nq");
    Appendable ap = System.out;
    MockBetterUser user = new MockBetterUser();
    IView view = new BasicView(ap);
    MockBetterController test = new MockBetterController(user, in, view);
    test.goNow();
    String log = test.getLog();
    assertEquals("menuCalled-quitChanged", log);
  }

  @Test
  public void testInstantQuit() throws IOException {
    Readable in = new StringReader("q");
    Appendable ap = System.out;
    MockBetterUser user = new MockBetterUser();
    IView view = new BasicView(ap);
    MockBetterController test = new MockBetterController(user, in, view);
    test.goNow();
    String log = test.getLog();
    assertEquals("quitChanged", log);
  }

  @Test
  public void testInvalidInput() throws IOException {
    Readable in = new StringReader("Megatron\nq");
    Appendable ap = System.out;
    MockBetterUser user = new MockBetterUser();
    IView view = new BasicView(ap);
    MockBetterController test = new MockBetterController(user, in, view);
    test.goNow();
    String log = test.getLog();
    assertEquals("badInput-quitChanged", log);
  }

  @Test
  public void testComposition() throws IOException {
    Readable in = new StringReader("composition\nq");
    Appendable ap = System.out;
    MockBetterUser user = new MockBetterUser();
    IView view = new BasicView(ap);
    MockBetterController test = new MockBetterController(user, in, view);
    test.goNow();
    String log = test.getLog();
    assertEquals("compositionCalledController-getValueOfPortfoliovalueOfPo"
            + "rtfoliogetStockPricegetPriceAtClosingGetCompAtDateCalled-quitChanged", log);
  }

  @Test
  public void testDistribution() throws IOException {
    Readable in = new StringReader("distribution\nq");
    Appendable ap = System.out;
    MockBetterUser user = new MockBetterUser();
    IView view = new BasicView(ap);
    MockBetterController test = new MockBetterController(user, in, view);
    test.goNow();
    String log = test.getLog();
    assertEquals("distributionCalledController-Distribution CalledgetDistribu" +
            "tion-quitChanged", log);
  }

  @Test
  public void testAllInputs() throws IOException {
    Readable in = new StringReader("distribution\nbuy\nsell\ncomposition\nMegatron\nsave\nme"
            + "nu\nrebalance\nstock-chart\nportfolio-chart\nretrieve\nq");
    Appendable ap = System.out;
    MockBetterUser user = new MockBetterUser();
    IView view = new BasicView(ap);
    MockBetterController test = new MockBetterController(user, in, view);
    test.goNow();
    String log = test.getLog();
    assertEquals("distributionCalledController-Distribution CalledgetDistribution-"
            + "buyCalledController-getStockPricegetPriceAtClosingBuyStockCalled-sellCalledContro"
            + "ller-getStockPriceCalledStockSellStockCalled-compositionCalledController-getValueO"
            + "fPortfoliovalueOfPortfoliogetStockPricegetPriceAtClosingGetCompAtDateCalled-badInpu"
            + "t-saveCalledController-SaveCalled-menuCalled-rebalanceCalledController-getStockPric"
            + "egetPriceAtClosingfindPriceQuantityCalledgetStockPriceCalledStockSellStockCalledget"
            + "StockPricegetPriceAtClosingBuyStockCalledRebalanceCalled-stockChartController-Stoc"
            + "kPreformanceOverTimeCalled-portfolioChartController-GetCompAtDateCalledpreformanceO"
            + "verTimeCalledGetPortfolioPeformance-retrieveCalledController-LoadCal"
            + "led-quitChanged", log);
  }




}
