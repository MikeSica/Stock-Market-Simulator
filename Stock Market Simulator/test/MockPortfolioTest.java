import org.junit.Test;

import java.io.IOException;

import static junit.framework.TestCase.assertEquals;

/**
 * A Junit test class for the MockPortfolio class.
 */
public class MockPortfolioTest {

  @Test
  public void testValueOfPortfolio() throws IOException {
    MockPortfolio port = new MockPortfolio("john");
    port.valueOfPortfolio("");
    assertEquals("valueOfPortfoliogetStockPricegetPriceAtClosing",
            port.getLog().toString());
  }

  @Test
  public void testGetName() throws IOException {
    MockPortfolio port = new MockPortfolio("john");
    port.getName();
    assertEquals("getName",
            port.getLog().toString());
  }


  @Test
  public void testAddStock() throws IOException {
    MockPortfolio port = new MockPortfolio("john");
    MockStock stock = new MockStock("AMD");
    port.addStock(stock, 2);
    assertEquals("addStock",
            port.getLog().toString());
  }


}
