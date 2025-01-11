import org.junit.Test;

import java.io.IOException;

import static junit.framework.TestCase.assertEquals;

/**
 * A Junit test class for the MockStock class.
 */
public class MockStockTest {

  @Test
  public void testStockChange() throws IOException {
    MockStock stock = new MockStock("AMD");
    stock.stockChange("", "");
    assertEquals("StockChangegetPriceAtClosinggetPriceAtClosing",
            stock.getLog().toString());
  }

  @Test
  public void testMovingAverage() throws IOException {
    MockStock stock = new MockStock("AMD");
    stock.movingAverage("", 2);
    assertEquals("movingAveragegetDatePlacegetPriceAtClosing",
            stock.getLog().toString());
  }

  @Test
  public void testCrossOverDays() throws IOException {
    MockStock stock = new MockStock("AMD");
    stock.crossOverDays("", "", 2);
    assertEquals("crossOverDaysgetDatePlacegetDatePlacegetDatePlacegetPriceAtClosing",
            stock.getLog().toString());
  }

  @Test
  public void testGetPrice() throws IOException {
    MockStock stock = new MockStock("AMD");
    stock.getStockPrice("", 2);
    assertEquals("getStockPricegetPriceAtClosing", stock.getLog().toString());
  }

  @Test
  public void testGetHistory() throws IOException {
    MockStock stock = new MockStock("AMD");
    stock.getHistoryData();
    assertEquals("getHistoryData",
            stock.getLog().toString());
  }
}
