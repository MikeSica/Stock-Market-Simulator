import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import model.BaseStock;
import model.BasicStock;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * An Abstract JUnit test class for the BasicStock implementation
 * with tests that should on a BaseStock and BetterStock.
 */
public abstract class AbstractStockTest {
  protected BasicStock stock;
  protected BasicStock stock2;
  protected BasicStock stock3;


  @Test
  public void createStockTestStockPrice() {

    float testGoog = stock.getStockPrice("2024-05-30", 50);
    assertEquals(8678.0, testGoog, 0.02);

    float testAmazon = stock2.getStockPrice("2024-05-30", 50);
    assertEquals(8966.0, testAmazon, 0.02);

    float testAmd = stock3.getStockPrice("2024-05-30", 50);
    assertEquals(8337.5, testAmd, 0.02);
  }

  @Test(expected = IllegalArgumentException.class)
  public void getStockPriceInvalidDate() {
    float testAmd = stock3.getStockPrice("2029-15-29", 50);
    assertEquals(8257.0, testAmd, 0.02);
  }

  @Test(expected = IllegalArgumentException.class)
  public void getStockPriceNegativeQuanitty() {
    float testAmd = stock3.getStockPrice("2023-05-29", -50);
    assertEquals(8257.0, testAmd, 0.02);
  }

  @Test
  public void getStockName() {
    assertEquals("AMD", stock3.getName());
    assertEquals("AMZN", stock2.getName());
    assertEquals("GOOG", stock.getName());
  }

  @Test
  public void testStockChange() {
    float googChange = stock.stockChange("2024-01-05", "2024-05-29");
    assertEquals(40.00999450683594, googChange, .2);

    float amznChange = stock2.stockChange("2024-01-05", "2024-05-29");
    assertEquals(36.779998779296875, amznChange, .2);

    float amdChange = stock3.stockChange("2024-03-05", "2024-05-29");
    assertEquals(-39.99000549316406, amdChange, .2);

    float sameDateChangeGoog = stock.stockChange("2024-01-05", "2024-01-05");
    assertEquals(0, sameDateChangeGoog, .2);

  }

  @Test(expected = IllegalArgumentException.class)
  public void stockChangeInvalidDate() {
    float sameDateChangeGoog = stock.stockChange("2024-01-55", "2024-01-05");
    assertEquals(0, sameDateChangeGoog, .2);
  }

  @Test(expected = IllegalArgumentException.class)
  public void stockChangeInvalidSecondDate() {
    float sameDateChangeGoog = stock.stockChange("2024-01-05", "2024-81-05");
    assertEquals(0, sameDateChangeGoog, .2);
  }

  @Test
  public void testMovingAverage() {

    float googMovingAvg = stock.movingAverage("2024-05-30", 6);
    assertEquals(176.395, googMovingAvg, .2);

    float amznMovingAvg = stock2.movingAverage("2024-05-30", 1);
    assertEquals(179.32, amznMovingAvg, .2);

    float amdMovingAvg = stock3.movingAverage("2024-05-30", 1);
    assertEquals(166.75, amdMovingAvg, .2);
  }

  @Test(expected = IllegalArgumentException.class)
  public void movingAvgInvalidDate() {
    float amdMovingAvg = stock3.movingAverage("2024-05-50", 1);
    assertEquals(165.13, amdMovingAvg, .2);
  }

  @Test(expected = IllegalArgumentException.class)
  public void movingAvgQuantityToBig() {
    float amdMovingAvg = stock3.movingAverage("2024-05-29", 100000);
    assertEquals(165.13, amdMovingAvg, .2);
  }

  @Test(expected = IllegalArgumentException.class)
  public void movingAvgNegativeNum() {
    float amdMovingAvg = stock3.movingAverage("2024-05-29", -1);
    assertEquals(165.13, amdMovingAvg, .2);
  }

  @Test
  public void testCrossOverDays() {
    String googCrossOver = stock.crossOverDays("2023-01-05", "2023-05-25", 50);
    String expected = "2023-01-19" + System.lineSeparator() +
            "2023-01-20" + System.lineSeparator() +
            "2023-01-23" + System.lineSeparator() +
            "2023-01-24" + System.lineSeparator() +
            "2023-01-25" + System.lineSeparator() +
            "2023-01-26" + System.lineSeparator() +
            "2023-01-27" + System.lineSeparator() +
            "2023-01-30" + System.lineSeparator() +
            "2023-01-31" + System.lineSeparator() +
            "2023-02-01" + System.lineSeparator() +
            "2023-02-02" + System.lineSeparator() +
            "2023-02-03" + System.lineSeparator() +
            "2023-02-06" + System.lineSeparator() +
            "2023-02-07" + System.lineSeparator() +
            "2023-02-08" + System.lineSeparator() +
            "2023-02-09" + System.lineSeparator() +
            "2023-02-13" + System.lineSeparator() +
            "2023-02-14" + System.lineSeparator() +
            "2023-02-15" + System.lineSeparator() +
            "2023-02-16" + System.lineSeparator() +
            "2023-02-17" + System.lineSeparator() +
            "2023-03-06" + System.lineSeparator() +
            "2023-03-08" + System.lineSeparator() +
            "2023-03-15" + System.lineSeparator() +
            "2023-03-16" + System.lineSeparator() +
            "2023-03-17" + System.lineSeparator() +
            "2023-03-20" + System.lineSeparator() +
            "2023-03-21" + System.lineSeparator() +
            "2023-03-22" + System.lineSeparator() +
            "2023-03-23" + System.lineSeparator() +
            "2023-03-24" + System.lineSeparator() +
            "2023-03-27" + System.lineSeparator() +
            "2023-03-28" + System.lineSeparator() +
            "2023-03-29" + System.lineSeparator() +
            "2023-03-30" + System.lineSeparator() +
            "2023-03-31" + System.lineSeparator() +
            "2023-04-03" + System.lineSeparator() +
            "2023-04-04" + System.lineSeparator() +
            "2023-04-05" + System.lineSeparator() +
            "2023-04-06" + System.lineSeparator() +
            "2023-04-10" + System.lineSeparator() +
            "2023-04-11" + System.lineSeparator() +
            "2023-04-12" + System.lineSeparator() +
            "2023-04-13" + System.lineSeparator() +
            "2023-04-14" + System.lineSeparator() +
            "2023-04-17" + System.lineSeparator() +
            "2023-04-18" + System.lineSeparator() +
            "2023-04-19" + System.lineSeparator() +
            "2023-04-20" + System.lineSeparator() +
            "2023-04-21" + System.lineSeparator() +
            "2023-04-24" + System.lineSeparator() +
            "2023-04-25" + System.lineSeparator() +
            "2023-04-26" + System.lineSeparator() +
            "2023-04-27" + System.lineSeparator() +
            "2023-04-28" + System.lineSeparator() +
            "2023-05-01" + System.lineSeparator() +
            "2023-05-02" + System.lineSeparator() +
            "2023-05-03" + System.lineSeparator() +
            "2023-05-04" + System.lineSeparator() +
            "2023-05-05" + System.lineSeparator() +
            "2023-05-08" + System.lineSeparator() +
            "2023-05-09" + System.lineSeparator() +
            "2023-05-10" + System.lineSeparator() +
            "2023-05-11" + System.lineSeparator() +
            "2023-05-12" + System.lineSeparator() +
            "2023-05-15" + System.lineSeparator() +
            "2023-05-16" + System.lineSeparator() +
            "2023-05-17" + System.lineSeparator() +
            "2023-05-18" + System.lineSeparator() +
            "2023-05-19" + System.lineSeparator() +
            "2023-05-22" + System.lineSeparator() +
            "2023-05-23" + System.lineSeparator() +
            "2023-05-24" + System.lineSeparator();
    assertEquals(expected, googCrossOver);
  }


  @Test(expected = IllegalArgumentException.class)
  public void crossoverInvalidFirstDate() {
    String googCrossOver = stock.crossOverDays("2033-01-05", "2023-05-25", 50);
    assertTrue(googCrossOver.length() > 9);
  }

  @Test(expected = IllegalArgumentException.class)
  public void crossoverInvalidSecondDate() {
    String googCrossOver = stock.crossOverDays("2023-01-05", "2023-43-25", 50);
    assertTrue(googCrossOver.length() > 9);
  }

  @Test(expected = IllegalArgumentException.class)
  public void crossoverInvalidNegativeNum() {
    String googCrossOver = stock.crossOverDays("2023-01-05", "2023-05-25", -50);
    assertTrue(googCrossOver.length() > 9);
  }

  @Test(expected = IllegalArgumentException.class)
  public void crossoverInvalidBigNum() {
    String googCrossOver = stock.crossOverDays("2023-01-05", "2023-05-25", 100000);
    assertTrue(googCrossOver.length() > 9);
  }

  @Test(expected = IllegalArgumentException.class)
  public void crossoverZero() {
    String googCrossOver = stock.crossOverDays("2023-01-05", "2023-05-25", 0);
    assertTrue(googCrossOver.length() > 9);
  }


  @Test
  public void getHistoryDataTest() throws IOException {
    List<List<String>> googHistory = new ArrayList<>(stock.getHistoryData());
    assertTrue(googHistory.size() > 43);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testInvalidStock() throws IOException {
    BasicStock test = new BaseStock("fubuebg");
  }

}
