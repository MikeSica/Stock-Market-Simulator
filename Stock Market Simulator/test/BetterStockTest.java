
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;


import model.BetterStock;
import model.IBetterStock;

import static org.junit.Assert.assertEquals;

/**
 * A Junit test for the BetterStock class.
 */
public class BetterStockTest extends AbstractStockTest {

  private IBetterStock betterStock1;
  private IBetterStock betterStock2;
  private IBetterStock betterStock3;

  @Before
  public void setUp() throws Exception {

    stock = new BetterStock("GOOG");
    stock2 = new BetterStock("AMZN");
    stock3 = new BetterStock("AMD");
    betterStock1 = new BetterStock("GOOG");
    betterStock2 = new BetterStock("AMZN");

    betterStock3 = new BetterStock("AMD");
  }

  @Test
  public void performanceOverTimeDay() {

    String tester = betterStock3.performanceOverTime("2022-04-26", "2022-05-27");
    String answer = "Performance of Stock AMD from 2022-04-26 to 2022-05-27\n" +
            "\n" +
            "2022-04-26: ********\n" +
            "2022-04-27: ********\n" +
            "2022-04-28: ********\n" +
            "2022-04-29: ********\n" +
            "2022-05-02: ********\n" +
            "2022-05-03: *********\n" +
            "2022-05-04: *********\n" +
            "2022-05-05: *********\n" +
            "2022-05-06: *********\n" +
            "2022-05-09: ********\n" +
            "2022-05-10: ********\n" +
            "2022-05-11: ********\n" +
            "2022-05-12: ********\n" +
            "2022-05-13: *********\n" +
            "2022-05-16: *********\n" +
            "2022-05-17: **********\n" +
            "2022-05-18: *********\n" +
            "2022-05-19: *********\n" +
            "2022-05-20: *********\n" +
            "2022-05-23: *********\n" +
            "2022-05-24: *********\n" +
            "2022-05-25: *********\n" +
            "2022-05-26: *********\n" +
            "2022-05-27: **********\n" +
            "\n" +
            "Scale: * = 10.0";
    assertEquals(answer, tester);
  }

  @Test
  public void performanceOverTimeMonth() {

    String tester = betterStock3.performanceOverTime("2022-04-26", "2024-05-29");
    String answer = "Performance of Stock AMD from 2022-04-26 to 2024-05-29\n" +
            "\n" +
            "2022-04-26: ********\n" +
            "2022-05-23: *********\n" +
            "2022-06-27: ********\n" +
            "2022-07-29: *********\n" +
            "2022-08-31: ********\n" +
            "2022-10-04: ******\n" +
            "2022-11-04: ******\n" +
            "2022-12-08: *******\n" +
            "2023-01-12: *******\n" +
            "2023-02-15: ********\n" +
            "2023-03-21: *********\n" +
            "2023-04-24: ********\n" +
            "2023-05-25: ************\n" +
            "2023-06-29: ***********\n" +
            "2023-08-02: **********\n" +
            "2023-09-05: ***********\n" +
            "2023-10-06: **********\n" +
            "2023-11-08: ***********\n" +
            "2023-12-12: *************\n" +
            "2024-01-17: ****************\n" +
            "2024-02-20: ****************\n" +
            "2024-03-22: *****************\n" +
            "2024-04-25: ***************\n" +
            "2024-05-29: ****************\n" +
            "\n" +
            "Scale: * = 10.0";
    assertEquals(answer, tester);
  }

  @Test
  public void performanceOverTimeYear() {

    String tester = betterStock3.performanceOverTime("2010-04-26", "2024-05-29");
    String answer = "Performance of Stock AMD from 2010-04-26 to 2024-05-29\n" +
            "\n" +
            "2010-04-26: **\n" +
            "2011-03-30: **\n" +
            "2012-05-03: *\n" +
            "2013-06-11: *\n" +
            "2014-07-16: *\n" +
            "2015-08-19: *\n" +
            "2016-09-22: *\n" +
            "2017-10-26: ***\n" +
            "2018-11-30: *****\n" +
            "2020-01-08: ***********\n" +
            "2021-02-11: ***********************\n" +
            "2022-03-17: ***************************\n" +
            "2023-04-24: *********************\n" +
            "2024-05-29: *****************************************\n" +
            "\n" +
            "Scale: * = 4.0";
    assertEquals(answer, tester);
  }

  @Test
  public void performanceOverTimeZeroDays() {

    String tester = betterStock3.performanceOverTime("2022-04-26", "2022-04-26");
    String answer = "Performance of Stock AMD from 2022-04-26 to 2022-04-26\n" +
            "\n" +
            "2022-04-26: *********\n" +
            "\n" +
            "Scale: * = 9.0";
    assertEquals(answer, tester);
  }

  @Test
  public void performanceOverTimeLessThanFiveDays() {

    String tester = betterStock1.performanceOverTime("2022-04-26", "2022-04-27");
    String answer = "Performance of Stock GOOG from 2022-04-26 to 2022-04-27\n" +
            "\n" +
            "2022-04-26: *****\n" +
            "2022-04-27: *****\n" +
            "\n" +
            "Scale: * = 400.0";
    assertEquals(answer, tester);

    String tester2 = betterStock2.performanceOverTime("2022-04-26", "2022-04-29");
    String answer2 = "Performance of Stock AMZN from 2022-04-26 to 2022-04-29\n" +
            "\n" +
            "2022-04-26: *********\n" +
            "2022-04-27: *********\n" +
            "2022-04-28: *********\n" +
            "2022-04-29: ********\n" +
            "\n" +
            "Scale: * = 300.0";
    assertEquals(answer2, tester2);
  }

  @Test(expected = IllegalArgumentException.class)
  public void performanceOverTimeIllegalEnding() {

    String tester = betterStock1.performanceOverTime("2022-04-26", "2022-04-25");

  }

  @Test(expected = IllegalArgumentException.class)
  public void performanceOverTimeIllegalStarting() {

    String tester = betterStock3.performanceOverTime("2022-04-24", "2022-05-29");

  }

  @Test
  public void findPriceQuantityOneShare() {
    float googPrice = betterStock1.getStockPrice("2024-05-29", 1);
    assertEquals(177.39999389648438F, googPrice, 0.02);

    float quantity = betterStock1.findPriceQuantity(177.39999389648438F, "2024-05-29");
    float expectedAmount = 1;
    assertEquals(expectedAmount, quantity,.02);
  }

  @Test
  public void findPriceQuantityRandomShareAmount() {
    float amznPrice = betterStock2.getStockPrice("2024-05-29", 1);
    assertEquals(182.02000427246094, amznPrice, 0.02);

    float quantity1 = betterStock2.findPriceQuantity(182.02000427246094F, "2024-05-29");
    float expectedAmount1 = 1;
    assertEquals(expectedAmount1, quantity1,.02);

  }

  @Test
  public void findPriceQuantityZeroDollar() {
    float amznPrice = betterStock2.getStockPrice("2024-05-29", 1);
    assertEquals(182.02000427246094, amznPrice, 0.02);

    float quantity1 = betterStock2.findPriceQuantity(0F, "2024-05-29");
    float expectedAmount1 = 0;
    assertEquals(expectedAmount1, quantity1, .02);

  }

  @Test
  public void findPriceQuantityPartialShares() {
    float amznPrice = betterStock2.getStockPrice("2024-05-29", 1);
    assertEquals(182.02000427246094, amznPrice, 0.02);

    float amount1 = 0.23F * 182.02000427246094F;
    float quantity1 = betterStock2.findPriceQuantity(amount1, "2024-05-29");
    float expectedAmount1 = .23F;
    assertEquals(expectedAmount1, quantity1, .02);

  }

  @Test(expected = IllegalArgumentException.class)
  public void findPriceQuantityInValidDate() {
    float amount1 = 182.02000427246094F;
    float quantity = betterStock2.findPriceQuantity(amount1, "2029-05-29");
  }

  @Test(expected = IllegalStateException.class)
  public void findPriceQuantityNegativeDollar() {
    float amdPrice = betterStock3.getStockPrice("2024-05-29", 1);
    assertEquals(165.13999938964844, amdPrice, 0.02);

    float amount1 = -(0.23F * 182.02000427246094F);
    float quantity = betterStock2.findPriceQuantity(amount1, "2024-05-29");
  }


  @Test
  public void availableDates() {
    List<String> dates = betterStock1.availableDates("2024-05-13", "2024-05-28");
    ArrayList<String> expected = new ArrayList<String>();
    expected.add("2024-05-28");
    expected.add("2024-05-24");
    expected.add("2024-05-23");
    expected.add("2024-05-22");
    expected.add("2024-05-21");
    expected.add("2024-05-20");
    expected.add("2024-05-17");
    expected.add("2024-05-16");
    expected.add("2024-05-15");
    expected.add("2024-05-14");
    expected.add("2024-05-13");
    assertEquals(expected, dates);

  }

  @Test
  public void availableDatesSameDate() {
    List<String> dates = betterStock1.availableDates("2024-05-13", "2024-05-13");
    ArrayList<String> expected = new ArrayList<String>();
    expected.add("2024-05-13");
    assertEquals(expected, dates);

  }


  @Test(expected = IllegalArgumentException.class)
  public void availableDatesInvalidEnd() {
    List<String> dates = betterStock1.availableDates("2024-05-13", "2023-05-28");
  }

  @Test(expected = IllegalArgumentException.class)
  public void availableDatesInvalidStart() {
    List<String> dates = betterStock1.availableDates("2029-05-13", "2034-05-28");
  }


}