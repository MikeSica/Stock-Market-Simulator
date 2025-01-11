import org.junit.Test;

import java.io.IOException;

import static junit.framework.TestCase.assertEquals;

/**
 * A Junit test class for the MockFinanceHistory class.
 */
public class MockFinanceHistoryTest {

  @Test
  public void testPriceAtOpening() throws IOException {
    MockFinanceHistory mockFinance = new MockFinanceHistory("AMD");
    mockFinance.getPriceAtOpening("");
    assertEquals("getPriceAtOpening", mockFinance.getLog().toString());
  }

  @Test
  public void testPriceAtClosing() throws IOException {
    MockFinanceHistory mockFinance = new MockFinanceHistory("AMD");
    mockFinance.getPriceAtClosing("");
    assertEquals("getPriceAtClosing", mockFinance.getLog().toString());
  }

  @Test
  public void testPriceAtHigh() throws IOException {
    MockFinanceHistory mockFinance = new MockFinanceHistory("AMD");
    mockFinance.getHighestPrice("");
    assertEquals("getHighestPrice", mockFinance.getLog().toString());
  }

  @Test
  public void testPriceAtLow() throws IOException {
    MockFinanceHistory mockFinance = new MockFinanceHistory("AMD");
    mockFinance.getLowestPrice("");
    assertEquals("getLowestPrice", mockFinance.getLog().toString());
  }

  @Test
  public void testVolumeOfTrade() throws IOException {
    MockFinanceHistory mockFinance = new MockFinanceHistory("AMD");
    mockFinance.volumeofTrade("");
    assertEquals("volumeOfTrade", mockFinance.getLog().toString());
  }

  @Test
  public void testGetHistory() throws IOException {
    MockFinanceHistory mockFinance = new MockFinanceHistory("AMD");
    mockFinance.getStockHistory();
    assertEquals("getStockHistory", mockFinance.getLog().toString());
  }

  @Test
  public void testGetDatePlace() throws IOException {
    MockFinanceHistory mockFinance = new MockFinanceHistory("AMD");
    mockFinance.getDatePlace("");
    assertEquals("getDatePlace", mockFinance.getLog().toString());
  }

}
