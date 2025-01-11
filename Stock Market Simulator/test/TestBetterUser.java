import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

import model.BetterUser;
import model.IBetterUser;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertTrue;

/**
 * A Junit test class for the Better User class.
 */
public class TestBetterUser extends AbstractUserTests {
  private IBetterUser u;

  @Before
  public void setUp() {
    u1 = new BetterUser();
    u = new BetterUser();
    u.addPortfolio("gorg");
    u.addPortfolio("geoff");
  }


  @Test
  public void testBuy() {
    float result = u.getValueOfPortfolio("gorg", "2023-05-26");
    assertEquals(result, 0, 0.02);
    u.buy("gorg", "GOOG", 3, "2023-05-26");
    result = u.getValueOfPortfolio("gorg", "2023-05-30");
    assertEquals(result, 373.919, 0.02);
  }

  @Test
  public void testBuyOtherStaysTheSame() {
    float result = u.getValueOfPortfolio("gorg", "2023-05-26");
    assertEquals(result, 0, 0.02);
    u.buy("gorg", "goog", 3, "2023-05-26");
    result = u.getValueOfPortfolio("geoff", "2023-05-26");
    assertEquals(0, result, 0.02);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testBuyInvalidDate() {
    float result = u.getValueOfPortfolio("gorg", "2023-05-26");
    assertEquals(result, 0, 0.02);
    u.buy("gorg", "goog", 3, "2024-05-26");
    result = u.getValueOfPortfolio("geoff", "2023-05-26");
    assertEquals(0, result, 0.02);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testBuyInvalidPortfolioName() {
    float result = u.getValueOfPortfolio("gorg", "2023-05-26");
    assertEquals(result, 0, 0.02);
    u.buy("geeg", "goog", 3, "2023-05-26");
    result = u.getValueOfPortfolio("geoff", "2023-05-26");
    assertEquals(0, result, 0.02);
  }

  @Test
  public void testBuyTwice() {
    float result = u.getValueOfPortfolio("gorg", "2023-05-25");
    assertEquals(result, 0, 0.02);
    u.buy("gorg", "goog", 3, "2023-05-25");
    u.buy("gorg", "amzn", 3, "2024-05-24");
    result = u.getValueOfPortfolio("gorg", "2023-05-26");
    assertEquals(376.290, result, 0.02);
    result = u.getValueOfPortfolio("gorg", "2024-05-31");
    assertEquals(1051.19, result, 0.02);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testBuyInvalidStockName() {
    float result = u.getValueOfPortfolio("gorg", "2023-05-26");
    assertEquals(result, 0, 0.02);
    u.buy("gorg", "gorg", 3, "2023-05-26");
    result = u.getValueOfPortfolio("geoff", "2023-05-26");
    assertEquals(0, result, 0.02);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testBuyInvalidQuantity() {
    float result = u.getValueOfPortfolio("gorg", "2023-05-26");
    assertEquals(result, 0, 0.02);
    u.buy("gorg", "goog", -3, "2023-05-26");
    result = u.getValueOfPortfolio("geoff", "2023-05-26");
    assertEquals(0, result, 0.02);
  }

  @Test
  public void testSell() {
    float result = u.getValueOfPortfolio("gorg", "2023-05-25");
    assertEquals(result, 0, 0.02);
    u.buy("gorg", "goog", 3, "2023-05-25");
    result = u.getValueOfPortfolio("gorg", "2023-05-26");
    assertEquals(result, 376.29, 0.02);
    u.sell("gorg", "goog", 1, "2023-05-26");
    result = u.getValueOfPortfolio("gorg", "2023-05-30");
    assertEquals(result, 249.279, 0.02);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testSellButNotHave() {
    float result = u.getValueOfPortfolio("gorg", "2023-05-25");
    assertEquals(result, 0, 0.02);
    u.buy("gorg", "goog", 3, "2023-05-25");
    result = u.getValueOfPortfolio("gorg", "2023-05-26");
    assertEquals(result, 376.29, 0.02);
    u.sell("gorg", "amzn", 1, "2023-05-26");
    result = u.getValueOfPortfolio("gorg", "2023-05-26");
    assertEquals(result, 376.29, 0.02);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testSellBadPortfolioName() {
    float result = u.getValueOfPortfolio("gorg", "2023-05-25");
    assertEquals(result, 0, 0.02);
    u.buy("gorg", "goog", 3, "2023-05-25");
    result = u.getValueOfPortfolio("gorg", "2023-05-26");
    assertEquals(result, 376.29, 0.02);
    u.sell("grrg", "goog", 1, "2023-05-26");
    result = u.getValueOfPortfolio("gorg", "2023-05-26");
    assertEquals(result, 376.29, 0.02);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testOtherSellButNotHave() {
    float result = u.getValueOfPortfolio("gorg", "2023-05-25");
    assertEquals(result, 0, 0.02);
    u.buy("gorg", "goog", 3, "2023-05-25");
    result = u.getValueOfPortfolio("gorg", "2023-05-26");
    assertEquals(result, 376.29, 0.02);
    u.sell("geoff", "goog", 1, "2023-05-26");
    result = u.getValueOfPortfolio("gorg", "2023-05-26");
    assertEquals(result, 376.29, 0.02);
  }

  @Test
  public void testSellOtherPortfolioOriginalStaysTheSame() {
    float result = u.getValueOfPortfolio("gorg", "2023-05-25");
    assertEquals(result, 0, 0.02);
    u.buy("gorg", "goog", 3, "2023-05-25");
    u.buy("geoff", "goog", 3, "2023-05-25");
    result = u.getValueOfPortfolio("geoff", "2023-05-26");
    assertEquals(result, 376.29, 0.02);
    u.sell("gorg", "goog", 1, "2023-05-26");
    assertEquals(result, 376.29, 0.02);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testSellInvalidName() {
    float result = u.getValueOfPortfolio("gorg", "2023-05-25");
    assertEquals(result, 0, 0.02);
    u.buy("gorg", "goog", 3, "2023-05-25");
    u.buy("geoff", "goog", 3, "2023-05-25");
    result = u.getValueOfPortfolio("geoff", "2023-05-26");
    assertEquals(result, 376.29, 0.02);
    u.sell("greeg", "goog", 1, "2023-05-26");
    assertEquals(result, 376.29, 0.02);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testSellInvalidStockName() {
    float result = u.getValueOfPortfolio("gorg", "2023-05-25");
    assertEquals(result, 0, 0.02);
    u.buy("gorg", "goog", 3, "2023-05-25");
    u.buy("geoff", "goog", 3, "2023-05-25");
    result = u.getValueOfPortfolio("geoff", "2023-05-26");
    assertEquals(result, 376.29, 0.02);
    u.sell("gorg", "gofg", 1, "2023-05-26");
    assertEquals(result, 376.29, 0.02);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testSellInvalidQuantity() {
    float result = u.getValueOfPortfolio("gorg", "2023-05-25");
    assertEquals(result, 0, 0.02);
    u.buy("gorg", "goog", 3, "2023-05-25");
    u.buy("geoff", "goog", 3, "2023-05-25");
    result = u.getValueOfPortfolio("geoff", "2023-05-26");
    assertEquals(result, 376.29, 0.02);
    u.sell("gorg", "goog", -1, "2023-05-26");
    assertEquals(result, 376.29, 0.02);
  }

  @Test
  public void testSellFloat() {
    float result = u.getValueOfPortfolio("gorg", "2023-05-25");
    assertEquals(result, 0, 0.02);
    u.buy("gorg", "goog", 3, "2023-05-25");
    u.buy("geoff", "goog", 3, "2023-05-25");
    result = u.getValueOfPortfolio("geoff", "2023-05-26");
    assertEquals(result, 376.29, 0.02);
    u.sell("gorg", "goog", (float) 1.03223, "2023-05-26");
    assertEquals(result, 376.29, 0.02);
  }

  @Test
  public void testSellAll() {
    float result = u.getValueOfPortfolio("gorg", "2023-05-25");
    assertEquals(result, 0, 0.02);
    u.buy("gorg", "goog", 3, "2023-05-25");
    u.buy("geoff", "goog", 3, "2023-05-25");
    result = u.getValueOfPortfolio("geoff", "2023-05-26");
    assertEquals(376.29, result, 0.02);
    u.sell("gorg", "goog", 3, "2023-05-26");
    result = u.getValueOfPortfolio("gorg", "2023-05-30");
    assertEquals(0, result, 0.02);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testSellInvalidDate() {
    float result = u.getValueOfPortfolio("gorg", "2023-05-25");
    assertEquals(result, 0, 0.02);
    u.buy("gorg", "goog", 3, "2023-05-25");
    u.buy("geoff", "goog", 3, "2023-05-25");
    result = u.getValueOfPortfolio("geoff", "2023-05-26");
    assertEquals(result, 376.29, 0.02);
    u.sell("gorg", "goog", 1, "2023-55-26");
    assertEquals(result, 376.29, 0.02);
  }

  @Test
  public void testGetCompAtDateWhileNothingIsThere() {
    String result = u.getCompAtDate("gorg", "2023-05-26");
    assertEquals("", result);
  }

  @Test
  public void testGetCompAtDateBeforeAndAfterBuy() {
    String result = u.getCompAtDate("gorg", "2023-05-25");
    assertEquals("", result);
    u.buy("gorg", "goog", 3, "2023-05-25");
    result = u.getCompAtDate("gorg", "2023-05-26");
    assertEquals("GOOG 3.0\n", result);
  }

  @Test
  public void testGetCompAtDateBeforeAndAfterSell() {
    String result = u.getCompAtDate("gorg", "2023-05-26");
    assertEquals("", result);
    u.buy("gorg", "goog", 3, "2023-05-26");
    result = u.getCompAtDate("gorg", "2023-05-30");
    assertEquals("GOOG 3.0\n", result);
    u.sell("gorg", "goog", 1, "2023-05-31");
    result = u.getCompAtDate("gorg", "2023-05-31");
    assertEquals("GOOG 2.0\n", result);
  }

  @Test
  public void testGetCompAtDateOtherPortfolio() {
    String result = u.getCompAtDate("gorg", "2023-05-26");
    assertEquals("", result);
    u.buy("gorg", "goog", 3, "2023-05-26");
    result = u.getCompAtDate("geoff", "2023-05-26");
    assertEquals("", result);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testGetCompAtDateInvalidPortfolioName() {
    String result = u.getCompAtDate("gre3", "2023-05-26");
    assertEquals("", result);
    u.buy("gorg", "goog", 3, "2023-05-26");
    result = u.getCompAtDate("gorg", "2023-05-26");
    assertEquals("", result);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testGetCompAtDateInvalidDate() {
    String result = u.getCompAtDate("gre3", "1992-05-26");
    assertEquals("", result);
    u.buy("gorg", "goog", 3, "2023-05-26");
    result = u.getCompAtDate("gorg", "2023-05-26");
    assertEquals("", result);
  }

  @Test
  public void testAddPortfolio() {
    u.addPortfolio("ger");
    String result = u.getCompAtDate("ger", "2023-05-26");
    assertEquals("", result);
  }

  @Test
  public void testSave() {
    u.save("gorg");
    File result = new File("./res/savedPortfolios/gorg.csv");
    assertTrue(result.exists());
  }

  @Test
  public void testSaveNotEmpty() {
    u.buy("gorg", "goog", 30, "2023-05-25");
    u.sell("gorg", "goog", 3, "2023-05-26");
    u.save("gorg");
    File result = new File("./res/savedPortfolios/gorg.csv");
    assertTrue(result.exists());
  }


  @Test(expected = IllegalArgumentException.class)
  public void testSaveInvalidName() {
    u.save("greeg");
    File result = new File("./res/savedPortfolios/gorg.csv");
    assertTrue(result.exists());
  }

  @Test
  public void testLoadEmpty() {
    IBetterUser u2 = new BetterUser();
    u2.addPortfolio("geeg");
    u2.save("geeg");
    String answer = u2.getCompAtDate("geeg", "2023-05-26");
    assertEquals("", answer);
    File result = new File("./res/savedPortfolios/geeg.csv");
    assertTrue(result.exists());
    u2.buy("geeg", "goog", 3, "2023-05-25");
    answer = u2.getCompAtDate("geeg", "2023-05-26");
    assertEquals("GOOG 3.0\n", answer);
    u2.load("geeg");
    answer = u2.getCompAtDate("geeg", "2023-05-30");
    assertEquals("", answer);
  }

  @Test
  public void testLoad() {
    u.buy("gorg", "goog", 3, "2023-05-26");
    u.save("gorg");
    String answer = u.getCompAtDate("gorg", "2023-05-31");
    assertEquals("GOOG 3.0\n", answer);
    File result = new File("./res/savedPortfolios/gorg.csv");
    assertTrue(result.exists());
    u.sell("gorg", "goog", 1, "2023-05-30");
    answer = u.getCompAtDate("gorg", "2023-05-31");
    assertEquals("GOOG 2.0\n", answer);
    u.load("gorg");
    answer = u.getCompAtDate("gorg", "2023-05-31");
    assertEquals("GOOG 3.0\n", answer);
  }


  @Test(expected = IllegalArgumentException.class)
  public void testLoadInvalidName() {
    u.buy("gorg", "goog", 3, "2023-05-26");
    u.save("gorg");
    String answer;
    File result = new File("./res/savedPortfolios/gorg.csv");
    assertTrue(result.exists());
    u.sell("gorg", "goog", 1, "2023-05-26");
    u.load("greeg");
    answer = u.getCompAtDate("gorg", "2023-05-26");
    assertEquals("", answer);
  }

  @Test
  public void testRebalance() {
    u.buy("gorg", "goog", 3, "2023-05-25");
    u.buy("gorg", "amzn", 3, "2023-05-25");
    u.buy("gorg", "aapl", 3, "2023-05-25");
    ArrayList<Float> parameters = new ArrayList<Float>(Arrays.asList(60F, 20F, 20F));
    String result = u.getCompAtDate("gorg", "2023-05-26");
    assertEquals("GOOG 3.0\n" +
            "AMZN 3.0\n" +
            "AAPL 3.0\n", result);
    u.rebalance("gorg", parameters, "2024-05-30");
    result = u.getCompAtDate("gorg", "2024-05-31");
    assertEquals("GOOG 5.998\n" +
            "AMZN 1.821\n" +
            "AAPL 1.707\n", result);
  }

  @Test
  public void testRebalanceWithZeros() {
    u.buy("gorg", "goog", 3, "2023-05-25");
    u.buy("gorg", "amzn", 3, "2023-05-25");
    u.buy("gorg", "aapl", 3, "2023-05-25");
    ArrayList<Float> parameters = new ArrayList<Float>(Arrays.asList(60F, 0F, 40F));
    String result = u.getCompAtDate("gorg", "2023-05-26");
    assertEquals("GOOG 3.0\n" +
            "AMZN 3.0\n" +
            "AAPL 3.0\n", result);
    u.rebalance("gorg", parameters, "2023-05-26");
    result = u.getCompAtDate("gorg", "2023-05-30");
    assertEquals("GOOG 6.992\n" +
            "AAPL 2.88\n", result);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testRebalanceTooManyParameters() {
    u.buy("gorg", "goog", 3, "2023-05-25");
    u.buy("gorg", "amzn", 3, "2023-05-25");
    u.buy("gorg", "aapl", 3, "2023-05-25");
    ArrayList<Float> parameters = new ArrayList<Float>(Arrays.asList(60F, 20F, 20F, 20F));
    String result = u.getCompAtDate("gorg", "2023-05-26");
    assertEquals("GOOG 3.0\n" +
            "AMZN 3.0\n" +
            "AAPL 3.0\n", result);
    u.rebalance("gorg", parameters, "2023-05-26");
    result = u.getCompAtDate("gorg", "2023-05-26");
    assertEquals("", result);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testRebalanceTooFewParameters() {
    u.buy("gorg", "goog", 3, "2023-05-26");
    u.buy("gorg", "amzn", 3, "2023-05-26");
    u.buy("gorg", "aapl", 3, "2023-05-26");
    ArrayList<Float> parameters = new ArrayList<Float>(Arrays.asList(60F, 40F));
    String result;
    u.rebalance("gorg", parameters, "2023-05-26");
    result = u.getCompAtDate("gorg", "2023-05-26");
    assertEquals("", result);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testRebalanceParametersDontEqual100() {
    u.buy("gorg", "goog", 3, "2023-05-26");
    u.buy("gorg", "amzn", 3, "2023-05-26");
    u.buy("gorg", "aapl", 3, "2023-05-26");
    ArrayList<Float> parameters = new ArrayList<Float>(Arrays.asList(60F, 10F, 10F));
    String result;
    u.rebalance("gorg", parameters, "2023-05-26");
    result = u.getCompAtDate("gorg", "2023-05-26");
    assertEquals("", result);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testRebalanceParametersGreaterThan() {
    u.buy("gorg", "goog", 3, "2023-05-26");
    u.buy("gorg", "amzn", 3, "2023-05-26");
    u.buy("gorg", "aapl", 3, "2023-05-26");
    ArrayList<Float> parameters = new ArrayList<Float>(Arrays.asList(60F, 100F, 10F));
    String result;
    u.rebalance("gorg", parameters, "2023-05-26");
    result = u.getCompAtDate("gorg", "2023-05-26");
    assertEquals("", result);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testRebalanceInvalidPortfolioName() {
    u.buy("gorg", "goog", 3, "2023-05-26");
    u.buy("gorg", "amzn", 3, "2023-05-26");
    u.buy("gorg", "aapl", 3, "2023-05-26");
    ArrayList<Float> parameters = new ArrayList<Float>(Arrays.asList(60F, 20F, 20F));
    String result;
    u.rebalance("grfeg", parameters, "2023-05-26");
    result = u.getCompAtDate("gorg", "2023-05-26");
    assertEquals("", result);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testRebalanceInvalidDate() {
    u.buy("gorg", "goog", 3, "2023-05-26");
    u.buy("gorg", "amzn", 3, "2023-05-26");
    u.buy("gorg", "aapl", 3, "2023-05-26");
    ArrayList<Float> parameters = new ArrayList<Float>(Arrays.asList(60F, 20F, 20F));
    String result;
    u.rebalance("gorg", parameters, "20253-05-26");
    result = u.getCompAtDate("gorg", "2023-05-26");
    assertEquals("", result);
  }

  @Test
  public void testPreformanceOverTimeEmpty() {
    String result = u.getPortfolioPerformance("gorg", "2023-05-26", "2024-05-30");
    assertEquals("Performance of Portfolio gorg from 2023-05-26 to 2024-05-30\n" +
            "\n" +
            "\n" +
            "Scale: * = 0.0", result);
  }

  @Test
  public void testPreformanceOverTimeYear() {
    u.buy("gorg", "goog", 133, "2022-01-05");
    String result = u.getPortfolioPerformance("gorg", "2022-01-05", "2024-05-30");
    assertEquals("Performance of Portfolio gorg from 2022-01-05 to 2024-05-30\n" +
            "\n" +
            "2022-01-11: **********************************************\n" +
            "2022-02-14: ********************************************\n" +
            "2022-03-18: *********************************************\n" +
            "2022-04-21: *****************************************\n" +
            "2022-05-24: ***********************************\n" +
            "2022-06-28: *************************************\n" +
            "2022-08-01: *\n" +
            "2022-09-01: *\n" +
            "2022-10-05: *\n" +
            "2022-11-07: *\n" +
            "2022-12-09: *\n" +
            "2023-01-13: *\n" +
            "2023-02-16: *\n" +
            "2023-03-22: *\n" +
            "2023-04-25: *\n" +
            "2023-05-26: **\n" +
            "2023-06-30: **\n" +
            "2023-08-03: **\n" +
            "2023-09-06: **\n" +
            "2023-10-09: **\n" +
            "2023-11-09: **\n" +
            "2023-12-13: **\n" +
            "2024-01-18: **\n" +
            "2024-02-21: **\n" +
            "2024-03-25: **\n" +
            "2024-04-26: **\n" +
            "2024-05-30: **\n" +
            "\n" +
            "Scale: * = 8000.0", result);
  }

  @Test
  public void testPreformanceOverTimeYearSellInTheMiddle() {
    u.buy("gorg", "goog", 133, "2022-01-05");
    u.sell("gorg", "goog", 100, "2023-05-26");
    String result = u.getPortfolioPerformance("gorg", "2022-08-01", "2024-05-30");
    assertEquals("Performance of Portfolio gorg from 2022-08-01 to 2024-05-30\n" +
            "\n" +
            "2022-08-01: *******************\n" +
            "2022-09-01: ******************\n" +
            "2022-10-05: ****************\n" +
            "2022-11-07: **************\n" +
            "2022-12-09: ***************\n" +
            "2023-01-13: ***************\n" +
            "2023-02-16: ***************\n" +
            "2023-03-22: *****************\n" +
            "2023-04-25: *****************\n" +
            "2023-05-26: *****\n" +
            "2023-06-30: ****\n" +
            "2023-08-03: *****\n" +
            "2023-09-06: *****\n" +
            "2023-10-09: *****\n" +
            "2023-11-09: *****\n" +
            "2023-12-13: *****\n" +
            "2024-01-18: *****\n" +
            "2024-02-21: *****\n" +
            "2024-03-25: ******\n" +
            "2024-04-26: *******\n" +
            "2024-05-30: *******\n" +
            "\n" +
            "Scale: * = 800.0", result);
  }

  @Test
  public void testPreformanceOverTimeEmptyAtFirst() {
    u.buy("gorg", "goog", 133, "2023-05-26");
    String result = u.getPortfolioPerformance("gorg", "2022-01-05", "2024-05-30");
    assertEquals("Performance of Portfolio gorg from 2022-01-05 to 2024-05-30\n" +
            "\n" +
            "2022-01-11: \n" +
            "2022-02-14: \n" +
            "2022-03-18: \n" +
            "2022-04-21: \n" +
            "2022-05-24: \n" +
            "2022-06-28: \n" +
            "2022-08-01: \n" +
            "2022-09-01: \n" +
            "2022-10-05: \n" +
            "2022-11-07: \n" +
            "2022-12-09: \n" +
            "2023-01-13: \n" +
            "2023-02-16: \n" +
            "2023-03-22: \n" +
            "2023-04-25: \n" +
            "2023-05-26: \n" +
            "2023-06-30: **********************\n" +
            "2023-08-03: ************************\n" +
            "2023-09-06: *************************\n" +
            "2023-10-09: **************************\n" +
            "2023-11-09: *************************\n" +
            "2023-12-13: *************************\n" +
            "2024-01-18: ***************************\n" +
            "2024-02-21: ***************************\n" +
            "2024-03-25: ****************************\n" +
            "2024-04-26: *********************************\n" +
            "2024-05-30: ********************************\n" +
            "\n" +
            "Scale: * = 700.0", result);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testPreformanceOverALotOfInvalidTime() {
    u.buy("gorg", "goog", 133, "2023-05-26");
    String result = u.getPortfolioPerformance("gorg", "1000-01-02", "2024-05-30");
    assertEquals("", result);
  }

  @Test
  public void testPreformanceOverMonths() {
    u.buy("gorg", "goog", 133, "2023-05-26");
    String result = u.getPortfolioPerformance("gorg", "2023-05-26", "2024-05-30");
    assertEquals("Performance of Portfolio gorg from 2023-05-26 to 2024-05-30\n" +
            "\n" +
            "2023-05-26: \n" +
            "2023-06-30: ****************\n" +
            "2023-08-03: *****************\n" +
            "2023-09-06: ******************\n" +
            "2023-10-09: ******************\n" +
            "2023-11-09: *****************\n" +
            "2023-12-13: *****************\n" +
            "2024-01-18: *******************\n" +
            "2024-02-21: *******************\n" +
            "2024-03-25: ********************\n" +
            "2024-04-26: ***********************\n" +
            "2024-05-30: ***********************\n" +
            "\n" +
            "Scale: * = 1000.0", result);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testPreformanceWhenStartANdEndAreReversed() {
    u.buy("gorg", "goog", 133, "2023-05-26");
    String result = u.getPortfolioPerformance("gorg", "2024-05-30", "2020-01-02");
    assertEquals("", result);
  }

  @Test
  public void testPreformanceOverTimeOneDay() {
    u.buy("gorg", "goog", 133, "2023-05-26");
    String result = u.getPortfolioPerformance("gorg", "2023-05-25", "2023-05-26");
    assertEquals("Performance of Portfolio gorg from 2023-05-25 to 2023-05-26\n" +
            "\n" +
            "2023-05-25: *\n" +
            "2023-05-26: *\n" +
            "\n" +
            "Scale: * = 0.0", result);
  }

  @Test
  public void testPreformanceOverTimeWeeks() {
    u.buy("gorg", "goog", 133, "2023-05-25");
    String result = u.getPortfolioPerformance("gorg", "2023-05-26", "2023-08-23");
    assertEquals("Performance of Portfolio gorg from 2023-05-26 to 2023-08-23\n" +
            "\n" +
            "2023-05-26: ****************\n" +
            "2023-06-05: ****************\n" +
            "2023-06-12: ****************\n" +
            "2023-06-20: ****************\n" +
            "2023-06-27: ***************\n" +
            "2023-07-05: ****************\n" +
            "2023-07-12: ***************\n" +
            "2023-07-19: ****************\n" +
            "2023-07-26: *****************\n" +
            "2023-08-02: *****************\n" +
            "2023-08-09: *****************\n" +
            "2023-08-16: *****************\n" +
            "2023-08-23: *****************\n" +
            "\n" +
            "Scale: * = 1000.0", result);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testPreformanceOverTimeInvalidPortfolioName() {
    u.buy("gorg", "goog", 133, "2023-05-26");
    String result = u.getPortfolioPerformance("gorg2", "2023-05-26", "2024-05-30");
    assertEquals("", result);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testPreformanceOverTimeInvalidStartDate() {
    u.buy("gorg", "goog", 133, "2023-05-26");
    String result = u.getPortfolioPerformance("gorg", "2023-2205-26", "2024-05-30");
    assertEquals("", result);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testPreformanceOverTimeInvalidEndDate() {
    u.buy("gorg", "goog", 133, "2023-05-26");
    String result = u.getPortfolioPerformance("gorg", "2023-05-26", "32024-05-30");
    assertEquals("", result);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testGetSellablesInValidDate() {
    u.buy("gorg", "goog", 133, "2023-05-26");
    u.getSellableAtDate("gorg", "2025-03-02");
  }

  @Test
  public void testGetSellables() {
    u.buy("gorg", "goog", 133, "2024-05-29");
    assertEquals("GOOG 133.0\n", u.getSellableAtDate("gorg", "2024-06-03"));

  }


}
