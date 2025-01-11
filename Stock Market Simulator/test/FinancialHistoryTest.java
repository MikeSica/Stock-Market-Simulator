import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.List;

import model.FinancialHistory;
import model.IHistory;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;

/**
 * A Junit test class for the Financial history class.
 */
public class FinancialHistoryTest {

  private IHistory financeHistoryGoog;
  private IHistory financeHistoryAmd;

  @Before
  public void setUp() throws IOException {
    financeHistoryGoog = new FinancialHistory("GOOG");
    financeHistoryAmd = new FinancialHistory("AMD");
  }

  @Test
  public void testLowerCaseWorks() throws IOException {
    IHistory test = new FinancialHistory("amzn");
    List<List<String>> result = test.getStockHistory();
    assertTrue(result.size() > 10);
  }


  @Test
  public void getPriceAtOpeningAmd() {
    float amdOpen = financeHistoryAmd.getPriceAtOpening("2023-05-26");
    assertEquals(122.45, amdOpen, .02);

  }

  @Test
  public void getPriceAtOpeningGoog() {
    float googOpen = financeHistoryGoog.getPriceAtOpening("2023-05-26");
    assertEquals(124.06, googOpen, .02);

  }

  @Test
  public void getPriceAtHighAmd() {
    float amdHigh = financeHistoryAmd.getHighestPrice("2023-05-26");
    assertEquals(127.43, amdHigh, .02);

  }

  @Test
  public void getPriceAtHighGoog() {
    float googHigh = financeHistoryGoog.getHighestPrice("2023-05-26");
    assertEquals(126.0, googHigh, .02);

  }


  @Test
  public void getPriceLowAmd() {
    float result = financeHistoryAmd.getLowestPrice("2023-05-26");
    assertEquals(120.89, result, .02);

  }

  @Test
  public void getPriceAtLowGoog() {
    float result = financeHistoryGoog.getLowestPrice("2023-05-26");
    assertEquals(123.29, result, .03);
  }

  @Test
  public void getPriceCloseAmd() {
    float result = financeHistoryAmd.getPriceAtClosing("2023-05-26");
    assertEquals(127.0299, result, .02);

  }

  @Test
  public void getPriceAtCloseGoog() {
    float result = financeHistoryGoog.getPriceAtClosing("2023-05-26");
    assertEquals(125.43, result, .03);
  }

  @Test
  public void getVolumeAmd() {
    float result = financeHistoryAmd.volumeofTrade("2023-05-26");
    assertEquals(9.2922752E7, result, .02);

  }

  @Test
  public void getVolumeGoog() {
    float result = financeHistoryGoog.volumeofTrade("2023-05-26");
    assertEquals(2.5169036E7, result, .03);
  }

  @Test
  public void getHistoryAmd() {
    List<List<String>> result = financeHistoryAmd.getStockHistory();
    assertTrue(result.size() > 5);
  }

  @Test
  public void getHistoryGoog() {
    List<List<String>> result = financeHistoryGoog.getStockHistory();
    assertTrue(result.size() > 5);
  }

  @Test
  public void getDatePlaceAmd() {
    int result = financeHistoryAmd.getDatePlace("2023-05-26");
    assertEquals(263, result);

  }

  @Test
  public void getDatePlaceGoog() {
    int result = financeHistoryAmd.getDatePlace("2023-05-26");
    assertEquals(256, result);
  }

  @Test(expected = IllegalArgumentException.class)
  public void throwsErrorIfNotExist() throws IOException {
    IHistory test = new FinancialHistory("euhgeuv2");
  }


  @Test(expected = IllegalArgumentException.class)
  public void getDatePlaceGoogNotExist() {
    int result = financeHistoryAmd.getDatePlace("2223-05-26");
    assertEquals(0, result);
  }

  @Test(expected = IllegalArgumentException.class)
  public void getVolumeGoogNoExist() {
    float result = financeHistoryGoog.volumeofTrade("2223-05-26");
    assertEquals(0, result, .03);
  }

  @Test(expected = IllegalArgumentException.class)
  public void getPriceAtOpeningAmdNoExist() {
    float amdOpen = financeHistoryAmd.getPriceAtOpening("2223-05-26");
    assertEquals(122.45, amdOpen, .02);

  }


  @Test(expected = IllegalArgumentException.class)
  public void getPriceAtHighAmdNoExist() {
    float amdHigh = financeHistoryAmd.getHighestPrice("2023-15-26");
    assertEquals(127.43, amdHigh, .02);
  }


  @Test(expected = IllegalArgumentException.class)
  public void getPriceAtLowGoogNoExist() {
    float result = financeHistoryGoog.getLowestPrice("2223-05-35");
    assertEquals(123.29, result, .03);
  }


  @Test(expected = IllegalArgumentException.class)
  public void getPriceAtCloseGoogNoExist() {
    float result = financeHistoryGoog.getPriceAtClosing("2223-05-26");
    assertEquals(125.43, result, .03);
  }


}
