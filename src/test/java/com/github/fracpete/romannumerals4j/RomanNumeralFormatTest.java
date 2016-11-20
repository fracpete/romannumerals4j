/*
 * This work is licensed under the Creative Commons Attribution-ShareAlike 3.0
 * Unported License. To view a copy of this license,
 * visit http://creativecommons.org/licenses/by-sa/3.0/ or send a letter to
 * Creative Commons, PO Box 1866, Mountain View, CA 94042, USA.
 */

/**
 * RomanNumeralFormatTest.java
 * Copyright (C) 2016 University of Waikato, Hamilton, NZ
 */

package com.github.fracpete.romannumerals4j;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import junit.textui.TestRunner;

/**
 * Tests the {@link RomanNumeralFormat} class.
 *
 * @author FracPete (fracpete at waikato dot ac dot nz)
 * @version $Revision$
 */
public class RomanNumeralFormatTest
  extends TestCase {

  /**
   * Performs a parse test.
   *
   * @param format	the format object to use
   * @param expected	the expected number
   * @param numeral	the numeral to parse
   * @param fails	whether this is expected to fail
   */
  protected void parse(RomanNumeralFormat format, int expected, String numeral, boolean fails) {
    try {
      assertEquals(expected, format.parse(numeral).intValue());
      if (fails)
	fail("Should have thrown exception!");
    }
    catch (Exception e) {
      if (!fails)
	fail("Parsing failed with: " + e);
    }
  }

  /**
   * Tests the parsing of numeral strings.
   */
  public void testParse() {
    RomanNumeralFormat format = new RomanNumeralFormat();

    parse(format, 1000, "M", false);
    parse(format, 900, "CM", false);
    parse(format, 500, "D", false);
    parse(format, 400, "CD", false);
    parse(format, 100, "C", false);
    parse(format, 90, "XC", false);
    parse(format, 50, "L", false);
    parse(format, 40, "XL", false);
    parse(format, 10, "X", false);
    parse(format, 9, "IX", false);
    parse(format, 5, "V", false);
    parse(format, 4, "IV", false);
    parse(format, 1, "I", false);
    
    parse(format, 1001, "MI", false);
    parse(format, 955, "CMLV", false);
    parse(format, 501, "DI", false);
    parse(format, 422, "CDXXII", false);
    parse(format, 103, "CIII", false);
    parse(format, 57, "LVII", false);
    parse(format, 41, "XLI", false);
    parse(format, 12, "XII", false);
    parse(format, 6, "VI", false);
    parse(format, 3, "III", false);

    parse(format, -1, "IIII", true);
    parse(format, -1, "MMMMM", true);
  }

  /**
   * Performs a format test.
   *
   * @param format	the format object to use
   * @param expected	the expected string
   * @param number	the number for format
   * @param fails	whether this is expected to fail
   */
  protected void format(RomanNumeralFormat format, String expected, int number, boolean fails) {
    try {
      assertEquals(expected, format.format(number));
      if (fails)
	fail("Should have thrown exception!");
    }
    catch (Exception e) {
      if (!fails)
	fail("Formatting failed with: " + e);
    }
  }

  /**
   * Tests the formatting of integers.
   */
  public void testFormat() {
    RomanNumeralFormat format = new RomanNumeralFormat();

    format(format, "M", 1000, false);
    format(format, "CM", 900, false);
    format(format, "D", 500, false);
    format(format, "CD", 400, false);
    format(format, "C", 100, false);
    format(format, "XC", 90, false);
    format(format, "L", 50, false);
    format(format, "XL", 40, false);
    format(format, "X", 10, false);
    format(format, "IX", 9, false);
    format(format, "V", 5, false);
    format(format, "IV", 4, false);
    format(format, "I", 1, false);

    format(format, "MI", 1001, false);
    format(format, "CMLV", 955, false);
    format(format, "DI", 501, false);
    format(format, "CDXXII", 422, false);
    format(format, "CIII", 103, false);
    format(format, "LVII", 57, false);
    format(format, "XLI", 41, false);
    format(format, "XII", 12, false);
    format(format, "VI", 6, false);
    format(format, "III", 3, false);

    format(format, "", 0, true);
    format(format, "", 4000, true);
  }

  /**
   * Returns a test suite.
   *
   * @return		the test suite
   */
  public static Test suite() {
    return new TestSuite(RomanNumeralFormatTest.class);
  }

  /**
   * Runs the test from commandline.
   *
   * @param args	ignored
   */
  public static void main(String[] args) {
    TestRunner.run(suite());
  }
}
