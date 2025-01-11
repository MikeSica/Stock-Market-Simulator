import org.junit.Before;
import org.junit.Test;

import model.IValidate;
import model.Range;
import model.Validate;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * A Junit test class for the Validate class.
 */
public class ValidateTest {

  private IValidate validater;

  @Before
  public void setUp() {
    validater = new Validate();
  }

  @Test
  public void testValidateDateValid() {
    boolean b = this.validateDate("2023-05-26");
    assertTrue(b);
  }

  @Test
  public void testValidateValidTicker() {
    boolean b = validateTicker("AMD");
    assertTrue(b);
    boolean c = validateTicker("GOOG");
    assertTrue(c);
    boolean d = validateTicker("AMZN");
    assertTrue(d);

  }

  @Test(expected = IllegalArgumentException.class)
  public void testValidateDateInValidTicker() {
    validater.validateDate("2023-05-26", "PorscheLambo");
  }

  @Test(expected = IllegalArgumentException.class)
  public void testValidateDateInValidTickerInValidDate() {
    validater.validateDate("2028-05-26", "PorscheLambo");
  }

  @Test(expected = IllegalArgumentException.class)
  public void testValidateDateInValidWeekend() {
    validater.validateDate("2023-05-29", "Amd");
  }

  @Test(expected = IllegalArgumentException.class)
  public void testValidateDateInvalidFormat() {
    validater.validateDate("20230529", "AAPL");
  }

  @Test(expected = IllegalArgumentException.class)
  public void testValidateInvalidTicker() {
    validater.validateTicker("Horsecowpig");
  }

  @Test
  public void testValidateIntegerValid() {
    boolean b = validateInt(10.0f);
    assertTrue(b);
  }

  @Test
  public void testValidateInteger() {
    boolean b = validateInt(10f);
    assertTrue(b);
    boolean c = validateInt(1f);
    assertTrue(c);
    boolean d = validateInt(532f);
    assertTrue(d);
    boolean e = validateInt(102222f);
    assertTrue(e);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testValidateIntegerNegative() {
    validater.validateInteger(-10.0f);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testValidateIntegerNotInt() {
    validater.validateInteger(20.434343f);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testValidateIntegerNull() {
    validater.validateInteger(null);
  }

  @Test
  public void testValidateStartEndValid() {
    boolean b = validateStartEnd("2023-05-03", "2024-05-29");
    assertTrue(b);
    boolean c = validateStartEnd("2024-05-03", "2024-05-29");
    assertTrue(c);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testValidateStartEndValidInValidEnd() {
    validater.validateStartEnd("2023-05-04", "2023-05-03", "AMD");
  }

  @Test(expected = IllegalArgumentException.class)
  public void testValidateStartEndValidInValidFormat() {
    validater.validateStartEnd("2023/05/04", "2023/05/03", "AMD");
  }

  @Test
  public void testValidateStepper() {
    int stepperDay = validater.getStepper(Range.DAY);
    assertEquals(1, stepperDay);

    int stepperWeek = validater.getStepper(Range.WEEK);
    assertEquals(5, stepperWeek);

    int stepperMonth = validater.getStepper(Range.MONTH);
    assertEquals(23, stepperMonth);

    int stepperYear = validater.getStepper(Range.YEAR);
    assertEquals(276, stepperYear);
  }

  private boolean validateDate(String date) {
    try {
      validater.validateDate(date, "AMD");
      return true;
    } catch (IllegalArgumentException e) {
      return false;
    }
  }

  private boolean validateStartEnd(String start, String end) {
    try {
      validater.validateStartEnd(start, end, "AMD");
      return true;
    } catch (IllegalArgumentException e) {
      return false;
    }
  }

  private boolean validateInt(float num) {
    try {
      validater.validateInteger(num);
      return true;
    } catch (IllegalArgumentException e) {
      return false;
    }
  }

  private boolean validateTicker(String ticker) {
    try {
      validater.validateTicker(ticker);
      return true;
    } catch (IllegalArgumentException e) {
      return false;
    }
  }


}
