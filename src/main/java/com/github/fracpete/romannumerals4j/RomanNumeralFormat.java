/*
 * This work is licensed under the Creative Commons Attribution-ShareAlike 3.0
 * Unported License. To view a copy of this license,
 * visit http://creativecommons.org/licenses/by-sa/3.0/ or send a letter to
 * Creative Commons, PO Box 1866, Mountain View, CA 94042, USA.
 */

/**
 * RomanNumeralFormat.java
 * Copyright (C) 2016 University of Waikato, Hamilton, NZ
 * Copyright (C) paxdiablo
 * Copyright (C) Ravindra Gullapalli
 * Copyright (C) chepe lucho
 */

package com.github.fracpete.romannumerals4j;

import java.text.FieldPosition;
import java.text.NumberFormat;
import java.text.ParsePosition;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Simple format/parse class for Roman Numerals, sourced from several
 * StackOverflow posts. Roman numerals only cover 1 to 3999.
 *
 * @author FracPete (fracpete at waikato dot ac dot nz)
 * @author paxdiablo (regexp - http://stackoverflow.com/a/267405)
 * @author Ravindra Gullapalli (roman to int - http://stackoverflow.com/a/9073310)
 * @author chepe lucho (int to roman - http://stackoverflow.com/a/17376764)
 * @version $Revision$
 */
public class RomanNumeralFormat
  extends NumberFormat {

  /** expression for checking roman numeral. */
  public final static String CHECK_REGEXP = "^M{0,3}(CM|CD|D?C{0,3})(XC|XL|L?X{0,3})(IX|IV|V?I{0,3})$";

  /** the hashmap for the numerals. */
  protected static LinkedHashMap<String, Integer> m_RomanNumerals;
  static {
    m_RomanNumerals = new LinkedHashMap<>();
    m_RomanNumerals.put("M", 1000);
    m_RomanNumerals.put("CM", 900);
    m_RomanNumerals.put("D", 500);
    m_RomanNumerals.put("CD", 400);
    m_RomanNumerals.put("C", 100);
    m_RomanNumerals.put("XC", 90);
    m_RomanNumerals.put("L", 50);
    m_RomanNumerals.put("XL", 40);
    m_RomanNumerals.put("X", 10);
    m_RomanNumerals.put("IX", 9);
    m_RomanNumerals.put("V", 5);
    m_RomanNumerals.put("IV", 4);
    m_RomanNumerals.put("I", 1);
  }

  /**
   * Returns whether grouping is used.
   *
   * @return		always false
   */
  @Override
  public boolean isGroupingUsed() {
    return false;
  }

  /**
   * Returns whether numbers are parsed as integers.
   *
   * @return		always true
   */
  @Override
  public boolean isParseIntegerOnly() {
    return true;
  }

  /**
   * Specialization of format.
   *
   * @param number     the double number to format
   * @param toAppendTo the StringBuffer to which the formatted text is to be
   *                   appended
   * @param pos        the field position
   * @return the formatted StringBuffer
   * @exception        ArithmeticException if rounding is needed with rounding
   *                   mode being set to RoundingMode.UNNECESSARY
   * @see java.text.Format#format
   */
  @Override
  public StringBuffer format(double number, StringBuffer toAppendTo, FieldPosition pos) {
    return format(new Double(number).longValue(), toAppendTo, pos);
  }

  /**
   * Repeats the format string a number of times.
   *
   * @param s		the format to repeat
   * @param n		the number of times to repeat
   * @return		the generated string
   */
  protected String repeatFormat(String s, int n) {
    if (s == null)
      return null;

    StringBuilder sb = new StringBuilder();
    for (int i = 0; i < n; i++)
      sb.append(s);

    return sb.toString();
  }

  /**
   * Specialization of format.
   *
   * @param number     the long number to format
   * @param toAppendTo the StringBuffer to which the formatted text is to be
   *                   appended
   * @param pos        the field position
   * @return the formatted StringBuffer
   * @exception        ArithmeticException if rounding is needed with rounding
   *                   mode being set to RoundingMode.UNNECESSARY
   * @see java.text.Format#format
   */
  @Override
  public StringBuffer format(long number, StringBuffer toAppendTo, FieldPosition pos) {
    if ((number < 1) || (number > 3999))
      throw new IllegalArgumentException("Roman numbers can only be 1 - 3999, provided: " + number);

    StringBuffer result = new StringBuffer();
    int intNum = (int) number;
    for(Map.Entry<String, Integer> entry : m_RomanNumerals.entrySet()){
      int matches = intNum /entry.getValue();
      result.append(repeatFormat(entry.getKey(), matches));
      intNum = intNum % entry.getValue();
    }

    return result;
  }

  /**
   * Adds/subtracts the decimal from the lastDecimal based on lastNumber.
   *
   * @param decimal	the decimal to add/subtract
   * @param lastNumber	the last number encountered
   * @param lastDecimal	the tally so far
   * @return		the updated value
   */
  protected int processDecimal(int decimal, int lastNumber, int lastDecimal) {
    if (lastNumber > decimal)
      return lastDecimal - decimal;
    else
      return lastDecimal + decimal;
  }

  /**
   * Returns a Long if possible (e.g., within the range [Long.MIN_VALUE,
   * Long.MAX_VALUE] and with no decimals), otherwise a Double.
   * If IntegerOnly is set, will stop at a decimal
   * point (or equivalent; e.g., for rational numbers "1 2/3", will stop
   * after the 1).
   * Does not throw an exception; if no object can be parsed, index is
   * unchanged!
   *
   * @param source the String to parse
   * @param parsePosition the parse position
   * @return the parsed value
   * @see java.text.NumberFormat#isParseIntegerOnly
   * @see java.text.Format#parseObject
   */
  @Override
  public Number parse(String source, ParsePosition parsePosition) {
    if (!source.matches(CHECK_REGEXP))
      return null;

    int decimal = 0;
    int lastNumber = 0;
    String romanNumeral = source.toUpperCase();
    for (int x = romanNumeral.length() - 1; x >= 0 ; x--) {
      char convertToDecimal = romanNumeral.charAt(x);

      switch (convertToDecimal) {
	case 'M':
	  decimal = processDecimal(1000, lastNumber, decimal);
	  lastNumber = 1000;
	  break;

	case 'D':
	  decimal = processDecimal(500, lastNumber, decimal);
	  lastNumber = 500;
	  break;

	case 'C':
	  decimal = processDecimal(100, lastNumber, decimal);
	  lastNumber = 100;
	  break;

	case 'L':
	  decimal = processDecimal(50, lastNumber, decimal);
	  lastNumber = 50;
	  break;

	case 'X':
	  decimal = processDecimal(10, lastNumber, decimal);
	  lastNumber = 10;
	  break;

	case 'V':
	  decimal = processDecimal(5, lastNumber, decimal);
	  lastNumber = 5;
	  break;

	case 'I':
	  decimal = processDecimal(1, lastNumber, decimal);
	  lastNumber = 1;
	  break;

	default:
	  // unhandled
	  return null;
      }

      // increment position
      parsePosition.setIndex(parsePosition.getIndex() + 1);
    }

    return decimal;
  }
}
