import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import model.BetterPortfolio;
import model.IBetterPortfolio;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertTrue;

/**
 * A Junit test class for the BetterPortfolio class.
 */

public class BetterPortfolioTest extends AbstractPortfolioTest {

  private IBetterPortfolio test;

  @Before
  public void setUp() throws Exception {
    normPortfolioTester = new BetterPortfolio("norm");
    test = new BetterPortfolio("greg");
  }

  @Test
  public void testBuyStock() {
    test.buyStock("Goog", 142, "2023-05-26");
    float tester = test.valueOfPortfolio("2023-05-30");
    assertEquals(17698.88, tester, 0.02);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testBuyStockZero() {
    test.buyStock("Goog", 0, "2023-05-26");
    float tester = test.valueOfPortfolio("2023-05-26");
    assertEquals(0, tester, 0.02);
  }

  @Test
  public void testValueBeforeAndAfterStock() {
    test.buyStock("Goog", 142, "2023-05-24");
    test.buyStock("AMZN", 140, "2024-05-30");
    float tester = test.valueOfPortfolio("2023-05-25");
    assertEquals(17657.69921875, tester, 0.02);
    tester = test.valueOfPortfolio("2022-05-26");
    assertEquals(0, tester, 0.01);
    tester = test.valueOfPortfolio("2024-05-31");
    assertEquals(49403.92, tester, 0.01);
  }

  @Test
  public void testBuyStockBeforeValue() {
    test.buyStock("Goog", 142, "2023-05-26");
    float tester = test.valueOfPortfolio("2021-05-26");
    assertEquals(0, tester, 0.02);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testBuyStockBadDate() {
    test.buyStock("Goog", 142, "2023-fwe26");
    float tester = test.valueOfPortfolio("2021-05-26");
    assertEquals(0, tester, 0.02);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testBuyStockNegativeQuantity() {
    test.buyStock("Goog", -142, "2023-05-26");
    float tester = test.valueOfPortfolio("2021-05-26");
    assertEquals(0, tester, 0.02);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testBuyStockBadTicker() {
    test.buyStock("Gorg", 142, "2023-05-26");
    float tester = test.valueOfPortfolio("2021-05-26");
    assertEquals(0, tester, 0.02);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testSellablesBadDate() {
    test.buyStock("Goog", 142, "2023-05-26");
    List<List<String>> tester = test.getSellable("2025-05-26");
  }

  @Test
  public void testSellablesNoStock() {
    test.buyStock("Goog", 142, "2023-05-26");
    List<List<String>> tester = test.getSellable("2023-05-03");
    List<List<String>> expected = new ArrayList<>();
    assertEquals(expected, tester);
  }

  @Test
  public void testSellables() {
    test.buyStock("Goog", 142, "2023-05-26");
    List<List<String>> tester = test.getSellable("2024-05-31");
    List<List<String>> expected = new ArrayList<>();
    List<String> adder = new ArrayList<String>();
    adder.add("GOOG");
    adder.add("142.0");
    expected.add(adder);
    assertEquals(expected, tester);
  }

  @Test
  public void testSellStock() {
    test.buyStock("Goog", 142, "2023-05-26");
    test.sellStock("Goog", 100, "2024-05-30");
    float tester = test.valueOfPortfolio("2023-07-21");
    assertEquals(17084.01, tester, 0.01);
    tester = test.valueOfPortfolio("2024-05-31");
    assertEquals(7306.32, tester, 0.01);
  }

  @Test
  public void testSellStockDifferentCapitals() {
    test.buyStock("Goog", 142, "2023-05-26");
    test.sellStock("GoOg", 100, "2024-05-30");
    float tester = test.valueOfPortfolio("2023-07-21");
    assertEquals(17084.019, tester, 0.01);
    tester = test.valueOfPortfolio("2024-05-31");
    assertEquals(7306.32, tester, 0.01);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testSellStockBadTicker() {
    test.buyStock("Goog", 142, "2023-05-26");
    test.sellStock("GrOg", 100, "2024-05-30");
    float tester = test.valueOfPortfolio("2024-07-21");
    assertEquals(243, tester, 0.01);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testSellStockTooMuch() {
    test.buyStock("Goog", 142, "2023-05-26");
    test.sellStock("GrOg", 200, "2024-05-30");
    float tester = test.valueOfPortfolio("2024-07-21");
    assertEquals(243, tester, 0.01);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testSellStockNegative() {
    test.buyStock("Goog", 142, "2023-05-26");
    test.sellStock("GrOg", -100, "2024-05-30");
    float tester = test.valueOfPortfolio("2024-07-21");
    assertEquals(243, tester, 0.01);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testSellStockZero() {
    test.buyStock("Goog", 142, "2023-05-26");
    test.sellStock("GoOg", 0, "2023-05-31");
    float tester = test.valueOfPortfolio("2024-05-31");
    assertEquals(24702.32, tester, 0.01);
    tester = test.valueOfPortfolio("2023-05-30");
    assertEquals(17698.88, tester, 0.01);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testSellStockBadDate() {
    test.buyStock("Goog", 142, "2023-05ref6");
    test.sellStock("GrOg", 0, "2024-05-30");
    float tester = test.valueOfPortfolio("2024-07-21");
    assertEquals(243, tester, 0.01);
  }

  @Test
  public void testSaveEmpty() throws IOException {
    test.save();
    File tester = new File("./res/savedPortfolios/greg.csv");
    assertTrue(tester.exists());
  }

  @Test
  public void testSaveNotEmpty() throws IOException {
    test.buyStock("Goog", 142, "2023-05-26");
    test.buyStock("AMZN", 121, "2023-05-26");
    test.buyStock("AAPL", 13, "2023-05-26");
    test.save();
    File tester = new File("./res/savedPortfolios/greg.csv");
    assertTrue(tester.exists());
  }

  @Test
  public void testLoad() throws IOException {
    test.save();
    test.buyStock("Goog", 142, "2023-05-25");
    Float tester = test.valueOfPortfolio("2023-05-26");
    assertEquals(17811.06, tester, 0.02);
    test.load();
    tester = test.valueOfPortfolio("2023-05-30");
    assertEquals(0, tester, 0.02);
  }

  @Test
  public void testLoadNotEmpty() throws IOException {
    test.buyStock("Goog", 142, "2023-05-25");
    test.buyStock("AMZN", 36, "2023-05-25");
    test.save();
    Float tester = test.valueOfPortfolio("2023-05-26");
    assertEquals(22135.019, tester, 0.02);
    test.buyStock("AAPL", 231, "2023-05-25");
    tester = test.valueOfPortfolio("2023-05-26");
    assertEquals(62659.347, tester, 0.02);
    test.load();
    tester = test.valueOfPortfolio("2023-05-26");
    assertEquals(22135.019, tester, 0.02);
  }

  @Test
  public void testRebalance() throws IOException {
    test.buyStock("Goog", 142, "2023-05-26");
    test.buyStock("AMZN", 121, "2023-05-26");
    test.buyStock("AAPL", 13, "2023-05-26");
    List<Float> parameters = new ArrayList<Float>();
    parameters.add(60F);
    parameters.add(20F);
    parameters.add(20F);
    test.rebalance(parameters, "2023-07-03");
    List<List<String>> tester1 = test.getCompAtDate("2023-05-30");
    List<List<String>> tester2 = test.getCompAtDate("2023-07-07");
    List<List<String>> amsw1 = new ArrayList<>();
    List<String> adder1 = new ArrayList<String>();
    List<String> adder2 = new ArrayList<String>();
    List<String> adder3 = new ArrayList<String>();
    adder1.add("GOOG");
    adder1.add("142.0");
    amsw1.add(adder1);
    adder2.add("AMZN");
    adder2.add("121.0");
    adder3.add("AAPL");
    adder3.add("13.0");
    amsw1.add(adder2);
    amsw1.add(adder3);
    List<List<String>> amsw2 = new ArrayList<>();
    assertEquals(amsw1, tester1);
    adder1.clear();
    adder2.clear();
    adder3.clear();
    adder1.add("GOOG");
    adder1.add("176.992");
    amsw2.add(adder1);
    adder2.add("AMZN");
    adder2.add("54.336");
    adder3.add("AAPL");
    adder3.add("36.999");
    amsw2.add(adder2);
    amsw2.add(adder3);
    assertEquals(amsw2, tester2);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testRebalanceBeforeBuy() throws IOException {
    test.buyStock("Goog", 142, "2023-05-26");
    test.buyStock("AMZN", 121, "2023-05-26");
    test.buyStock("AAPL", 13, "2023-05-26");
    List<Float> parameters = new ArrayList<Float>(60);
    parameters.add(20F);
    parameters.add(20F);
    test.rebalance(parameters, "2021-07-03");
    List<List<String>> tester1 = test.getCompAtDate("2023-05-30");
    List<List<String>> tester2 = test.getCompAtDate("2024-05-30");
    List<List<String>> amsw1 = new ArrayList<>();
    List<List<String>> amsw2 = new ArrayList<>();
    assertEquals(amsw1, tester1);
    assertEquals(amsw2, tester2);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testRebalanceTooManyParameters() throws IOException {
    test.buyStock("Goog", 142, "2023-05-26");
    test.buyStock("AMZN", 121, "2023-05-26");
    test.buyStock("AAPL", 13, "2023-05-26");
    List<Float> parameters = new ArrayList<Float>(60);
    parameters.add(20F);
    parameters.add(20F);
    parameters.add(20F);
    test.rebalance(parameters, "2021-07-03");
    List<List<String>> tester1 = test.getCompAtDate("2023-05-26");
    List<List<String>> tester2 = test.getCompAtDate("2024-05-30");
    List<List<String>> amsw1 = new ArrayList<>();
    List<List<String>> amsw2 = new ArrayList<>();
    assertEquals(amsw1, tester1);
    assertEquals(amsw2, tester2);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testRebalanceTooFewParameters() throws IOException {
    test.buyStock("Goog", 142, "2023-05-26");
    test.buyStock("AMZN", 121, "2023-05-26");
    test.buyStock("APPL", 13, "2023-05-26");
    List<Float> parameters = new ArrayList<Float>(60);
    parameters.add(20F);
    test.rebalance(parameters, "2021-07-03");
    List<List<String>> tester1 = test.getCompAtDate("2023-05-26");
    List<List<String>> tester2 = test.getCompAtDate("2024-05-30");
    List<List<String>> amsw1 = new ArrayList<>();
    List<List<String>> amsw2 = new ArrayList<>();
    assertEquals(amsw1, tester1);
    assertEquals(amsw2, tester2);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testPreformanceOverTimeMultipleStocksBeforeBuy() throws IOException {
    test.buyStock("Goog", 142, "2023-05-26");
    test.buyStock("AMZN", 121, "2023-05-26");
    test.buyStock("AAPL", 13, "2023-05-26");
    String tester = test.performanceOverTime("2021-05-26", "2025-05-26");
    String amsw = "";
    assertEquals(amsw, tester);
  }

  @Test
  public void testPreformanceOverTimeMultipleStocksMonth() throws IOException {
    test.buyStock("Goog", 142, "2023-04-26");
    test.buyStock("AMZN", 121, "2023-04-26");
    test.buyStock("AAPL", 13, "2023-04-26");
    String tester = test.performanceOverTime("2023-04-27", "2023-05-26");
    String amsw = "Performance of Portfolio greg from 2023-04-27 to 2023-05-26\n" +
            "\n" +
            "2023-04-27: **********\n" +
            "2023-04-28: **********\n" +
            "2023-05-01: *********\n" +
            "2023-05-02: *********\n" +
            "2023-05-03: *********\n" +
            "2023-05-04: *********\n" +
            "2023-05-05: **********\n" +
            "2023-05-08: **********\n" +
            "2023-05-09: **********\n" +
            "2023-05-10: **********\n" +
            "2023-05-11: **********\n" +
            "2023-05-12: **********\n" +
            "2023-05-15: **********\n" +
            "2023-05-16: ***********\n" +
            "2023-05-17: ***********\n" +
            "2023-05-18: ***********\n" +
            "2023-05-19: ***********\n" +
            "2023-05-22: ***********\n" +
            "2023-05-23: ***********\n" +
            "2023-05-24: ***********\n" +
            "2023-05-25: ***********\n" +
            "2023-05-26: ***********\n" +
            "\n" +
            "Scale: * = 3000.0";
    assertEquals(amsw, tester);
  }

  //Sudden drop off is due to stock split
  @Test
  public void testPreformanceOverTimeMultipleStocksYear() throws IOException {
    test.buyStock("Goog", 142, "2021-05-25");
    test.buyStock("AMZN", 121, "2021-05-25");
    test.buyStock("AAPL", 13, "2021-05-25");
    String tester = test.performanceOverTime("2021-05-26", "2023-05-30");
    String amsw = "Performance of Portfolio greg from 2021-05-26 to 2023-05-30\n" +
            "\n" +
            "2021-06-28: *******************\n" +
            "2021-07-30: *******************\n" +
            "2021-09-01: ********************\n" +
            "2021-10-05: *******************\n" +
            "2021-11-05: *********************\n" +
            "2021-12-09: *********************\n" +
            "2022-01-12: ********************\n" +
            "2022-02-15: *******************\n" +
            "2022-03-21: *******************\n" +
            "2022-04-22: *****************\n" +
            "2022-05-25: **************\n" +
            "2022-06-29: ********\n" +
            "2022-08-02: *\n" +
            "2022-09-02: *\n" +
            "2022-10-06: *\n" +
            "2022-11-08: *\n" +
            "2022-12-12: *\n" +
            "2023-01-17: *\n" +
            "2023-02-17: *\n" +
            "2023-03-23: *\n" +
            "2023-04-26: *\n" +
            "2023-05-30: *\n" +
            "\n" +
            "Scale: * = 40000.0";
    assertEquals(amsw, tester);
  }

  @Test
  public void testPreformanceOverTimeMultipleStocksweek() throws IOException {
    test.buyStock("Goog", 142, "2022-05-25");
    test.buyStock("AMZN", 121, "2022-05-25");
    test.buyStock("AAPL", 13, "2022-05-25");
    String tester = test.performanceOverTime("2024-05-13", "2024-06-04");
    String amsw = "Performance of Portfolio greg from 2024-05-13 to 2024-06-04\n" +
            "\n" +
            "2024-05-13: ************\n" +
            "2024-05-14: ************\n" +
            "2024-05-15: ************\n" +
            "2024-05-16: ************\n" +
            "2024-05-17: ************\n" +
            "2024-05-20: ************\n" +
            "2024-05-21: ************\n" +
            "2024-05-22: ************\n" +
            "2024-05-23: ************\n" +
            "2024-05-24: ************\n" +
            "2024-05-28: ************\n" +
            "2024-05-29: ************\n" +
            "2024-05-30: ************\n" +
            "2024-05-31: ************\n" +
            "2024-06-03: ************\n" +
            "2024-06-04: ************\n" +
            "\n" +
            "Scale: * = 4000.0";
    assertEquals(amsw, tester);
  }

  @Test
  public void testPreformanceOverTimeMultipleStocksLessThanFiveDays() throws IOException {
    test.buyStock("Goog", 142, "2023-05-26");
    test.buyStock("AMZN", 121, "2023-05-26");
    test.buyStock("AAPL", 13, "2023-05-26");
    String tester = test.performanceOverTime("2024-05-28", "2024-05-30");
    String amsw = "Performance of Portfolio greg from 2024-05-28 to 2024-05-30\n" +
            "\n" +
            "2024-05-28: ************\n" +
            "2024-05-29: ************\n" +
            "2024-05-30: ************\n" +
            "\n" +
            "Scale: * = 4000.0";
    assertEquals(amsw, tester);
  }

  @Test
  public void testPreformanceOverTimeMultipleStocksSameDay() throws IOException {
    test.buyStock("Goog", 142, "2023-05-26");
    test.buyStock("AMZN", 121, "2023-05-26");
    test.buyStock("AAPL", 13, "2023-05-26");
    String tester = test.performanceOverTime("2024-05-30", "2024-05-30");
    String amsw = "Performance of Portfolio greg from 2024-05-30 to 2024-05-30\n" +
            "\n" +
            "2024-05-30: ************\n" +
            "\n" +
            "Scale: * = 4000.0";
    assertEquals(amsw, tester);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testPreformanceOverTimeMultipleStocksInvalidStartDate() throws IOException {
    test.buyStock("Goog", 142, "2023-05-26");
    test.buyStock("AMZN", 121, "2023-05-26");
    test.buyStock("AAPL", 13, "2023-05-26");
    String tester = test.performanceOverTime("2024-05fw421-26", "2024-05-30");
    String amsw = "";
    assertEquals(amsw, tester);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testPreformanceOverTimeMultipleStocksInvalidEndDate() throws IOException {
    test.buyStock("Goog", 142, "2023-05-26");
    test.buyStock("AMZN", 121, "2023-05-26");
    test.buyStock("AAPL", 13, "2023-05-26");
    String tester = test.performanceOverTime("2024-05-30", "202442fe5-26");
    String amsw = "";
    assertEquals(amsw, tester);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testPreformanceOverTimeMultipleStocksWrongOrderDates() throws IOException {
    test.buyStock("Goog", 142, "2023-05-26");
    test.buyStock("AMZN", 121, "2023-05-26");
    test.buyStock("AAPL", 13, "2023-05-26");
    String tester = test.performanceOverTime("2025-05-26", "2024-05-30");
    String amsw = "";
    assertEquals(amsw, tester);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testPreformanceOverTimeMultipleStocksFuture() throws IOException {
    test.buyStock("Goog", 142, "2023-05-26");
    test.buyStock("AMZN", 121, "2023-05-26");
    test.buyStock("AAPL", 13, "2023-05-26");
    String tester = test.performanceOverTime("2024-05-30", "2030-05-26");
    String amsw = "";
    assertEquals(amsw, tester);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testPreformanceOverTimeMultipleStocksBeforeData() throws IOException {
    test.buyStock("Goog", 142, "2023-05-26");
    test.buyStock("AMZN", 121, "2023-05-26");
    test.buyStock("AAPL", 13, "2023-05-26");
    String tester = test.performanceOverTime("1982-05-26", "2024-05-30");
    String amsw = "";
    assertEquals(amsw, tester);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testPreformanceOverTimeSingleStockBeforeBuy() throws IOException {
    test.buyStock("Goog", 142, "2023-05-26");
    String tester = test.performanceOverTime("2021-05-26", "2025-05-26");
    String amsw = "";
    assertEquals(amsw, tester);
  }

  @Test
  public void testPreformanceOverTimeSingleStockMonth() throws IOException {
    test.buyStock("Goog", 142, "2023-05-26");
    String tester = test.performanceOverTime("2023-05-30", "2024-05-30");
    String amsw = "Performance of Portfolio greg from 2023-05-30 to 2024-05-30\n" +
            "\n" +
            "2023-06-30: *****************\n" +
            "2023-08-03: ******************\n" +
            "2023-09-06: *******************\n" +
            "2023-10-09: *******************\n" +
            "2023-11-09: ******************\n" +
            "2023-12-13: *******************\n" +
            "2024-01-18: ********************\n" +
            "2024-02-21: ********************\n" +
            "2024-03-25: *********************\n" +
            "2024-04-26: ************************\n" +
            "2024-05-30: ************************\n" +
            "\n" +
            "Scale: * = 1000.0";
    assertEquals(amsw, tester);
  }

  @Test
  public void testPreformanceOverTimeSingleStockLessThanFiveDays() throws IOException {
    test.buyStock("Goog", 142, "2023-05-26");
    String tester = test.performanceOverTime("2024-05-28", "2024-05-30");
    String amsw = "Performance of Portfolio greg from 2024-05-28 to 2024-05-30\n" +
            "\n" +
            "2024-05-28: ************\n" +
            "2024-05-29: ************\n" +
            "2024-05-30: ************\n" +
            "\n" +
            "Scale: * = 2000.0";
    assertEquals(amsw, tester);
  }

  @Test
  public void testPreformanceOverTimeSingleStockSameDay() throws IOException {
    test.buyStock("Goog", 142, "2023-05-26");
    String tester = test.performanceOverTime("2024-05-30", "2024-05-30");
    String amsw = "Performance of Portfolio greg from 2024-05-30 to 2024-05-30\n" +
            "\n" +
            "2024-05-30: ************\n" +
            "\n" +
            "Scale: * = 2000.0";
    assertEquals(amsw, tester);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testPreformanceOverTimeSingleStockInvalidStartDate() throws IOException {
    test.buyStock("Goog", 142, "2023-05-26");
    String tester = test.performanceOverTime("2024-05fw421-26", "2024-05-30");
    String amsw = "";
    assertEquals(amsw, tester);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testPreformanceOverTimeSingleStockInvalidEndDate() throws IOException {
    test.buyStock("Goog", 142, "2023-05-26");
    String tester = test.performanceOverTime("2024-05-30", "202442fe5-26");
    String amsw = "";
    assertEquals(amsw, tester);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testPreformanceOverTimeSingleStockWrongOrderDates() throws IOException {
    test.buyStock("Goog", 142, "2023-05-26");
    String tester = test.performanceOverTime("2025-05-26", "2024-05-30");
    String amsw = "";
    assertEquals(amsw, tester);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testPreformanceOverTimeSingleStockFuture() throws IOException {
    test.buyStock("Goog", 142, "2023-05-26");
    String tester = test.performanceOverTime("2024-05-30", "2030-05-26");
    String amsw = "";
    assertEquals(amsw, tester);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testPreformanceOverTimeSingleStockBeforeData() throws IOException {
    test.buyStock("Goog", 142, "2023-05-26");
    String tester = test.performanceOverTime("1982-05-26", "2024-05-30");
    String amsw = "";
    assertEquals(amsw, tester);
  }

  @Test
  public void testPreformanceOverTimeSingleStockYear() throws IOException {
    test.buyStock("Goog", 142, "2022-05-26");
    String tester = test.performanceOverTime("2022-05-26", "2024-05-30");
    String amsw = "Performance of Portfolio greg from 2022-05-26 to 2024-05-30\n\n" +
            "2022-06-28: **************************************************\n" +
            "2022-08-01: *****\n" +
            "2022-09-01: *****\n" +
            "2022-10-05: ****\n" +
            "2022-11-07: ****\n" +
            "2022-12-09: ****\n" +
            "2023-01-13: ****\n" +
            "2023-02-16: ****\n" +
            "2023-03-22: ****\n" +
            "2023-04-25: ****\n" +
            "2023-05-26: *****\n" +
            "2023-06-30: *****\n" +
            "2023-08-03: ******\n" +
            "2023-09-06: ******\n" +
            "2023-10-09: ******\n" +
            "2023-11-09: ******\n" +
            "2023-12-13: ******\n" +
            "2024-01-18: ******\n" +
            "2024-02-21: ******\n" +
            "2024-03-25: *******\n" +
            "2024-04-26: ********\n" +
            "2024-05-30: ********\n" +
            "\n" +
            "Scale: * = 3000.0";
    assertEquals(amsw, tester);
  }

  @Test
  public void testPreformanceOverTimeSingleStocksweek() throws IOException {
    test.buyStock("Goog", 142, "2022-05-26");
    String tester = test.performanceOverTime("2024-01-30", "2024-02-15");
    String amsw = "Performance of Portfolio greg from 2024-01-30 to 2024-02-15\n" +
            "\n" +
            "2024-01-30: **********\n" +
            "2024-01-31: **********\n" +
            "2024-02-01: **********\n" +
            "2024-02-02: **********\n" +
            "2024-02-05: **********\n" +
            "2024-02-06: **********\n" +
            "2024-02-07: **********\n" +
            "2024-02-08: **********\n" +
            "2024-02-09: **********\n" +
            "2024-02-12: **********\n" +
            "2024-02-13: **********\n" +
            "2024-02-14: **********\n" +
            "2024-02-15: **********\n" +
            "\n" +
            "Scale: * = 2000.0";
    assertEquals(amsw, tester);
  }

  @Test
  public void testDistributionBeforeBuyingStocks() throws IOException {
    String tester = test.distribution("2023-05-29");
    String amsw = "No Stocks Are Owned At This Date";
    assertEquals(amsw, tester);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testDistributionInvalidDate() throws IOException {
    test.buyStock("Goog", 142, "2023-05-28");
    String tester = test.distribution("2024-05-30");
    String amsw = "";
    assertEquals(amsw, tester);
  }

  @Test
  public void testDistributionAfterBuyingStocks() throws IOException {
    test.buyStock("Goog", 142, "2024-05-28");
    test.buyStock("AMZN", 121, "2024-05-28");
    test.buyStock("AAPL", 13, "2024-05-28");
    String tester = test.distribution("2024-05-29");
    String amsw = "The value of the portfolio on 2024-05-29 is: 49688.99\n" +
            "and is composed of the following: \n" +
            "Stock: GOOG\n" +
            "Quantity: 142.0\n" +
            "Current Price of 1 share: 177.4\n" +
            "Current Value of that stocks holdings: 25190.799\n" +
            "Percent of Total Portfolio Value: 50.696945%\n" +
            "Stock: AMZN\n" +
            "Quantity: 121.0\n" +
            "Current Price of 1 share: 182.02\n" +
            "Current Value of that stocks holdings: 22024.42\n" +
            "Percent of Total Portfolio Value: 44.32455%\n" +
            "Stock: AAPL\n" +
            "Quantity: 13.0\n" +
            "Current Price of 1 share: 190.29\n" +
            "Current Value of that stocks holdings: 2473.77\n" +
            "Percent of Total Portfolio Value: 4.9785075%\n";
    assertEquals(amsw, tester);
  }

  @Test
  public void testDistributionAfterBuyingAndSellingStocks() throws IOException {
    test.buyStock("Goog", 142, "2024-05-28");
    test.buyStock("AMZN", 121, "2024-05-28");
    test.buyStock("AAPL", 13, "2024-05-28");
    test.sellStock("AAPL", 13, "2024-05-29");
    String tester = test.distribution("2024-05-30");
    String amsw = "The value of the portfolio on 2024-05-30 is: 46343.242\n" +
            "and is composed of the following: \n" +
            "Stock: GOOG\n" +
            "Quantity: 142.0\n" +
            "Current Price of 1 share: 173.56\n" +
            "Current Value of that stocks holdings: 24645.52\n" +
            "Percent of Total Portfolio Value: 53.180397%\n" +
            "Stock: AMZN\n" +
            "Quantity: 121.0\n" +
            "Current Price of 1 share: 179.32\n" +
            "Current Value of that stocks holdings: 21697.72\n" +
            "Percent of Total Portfolio Value: 46.8196%\n" +
            "Stock: AAPL\n" +
            "Quantity: 13.0\n" +
            "Current Price of 1 share: 191.29\n" +
            "Current Value of that stocks holdings: 2486.77\n" +
            "Percent of Total Portfolio Value: 5.365982%\n";
    assertEquals(amsw, tester);
  }



}
