package com.coolworks.util;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.MathContext;

public class MathExtension {

	/**
	 * This is the max value that can be passed to the {@link BigDecimal#pow(int)} method.
	 */
	private static final int BIG_DECIMAL_POW_MAX_EXPONENT = 999999999;
	
	/**
	 * A {@link BigDecimal} instance representing the value <code>2</code>.
	 */
	private static final BigDecimal BIG_DECIMAL_TWO = new BigDecimal(2);
		
	/**
	 * A {@link BigDecimal} instance representing the value <code>2<sup>999999999</sup></code>.
	 */
	private static final BigDecimal TWO_TO_THE_POWER_OF_MAX_EXPONENT = BIG_DECIMAL_TWO.pow(BIG_DECIMAL_POW_MAX_EXPONENT);
	
	
	public static BigDecimal divideByPowerOfTwo(BigDecimal bigDecimal, int exponent) {
		
		int quotient = exponent / BIG_DECIMAL_POW_MAX_EXPONENT;
		int reminder = exponent % BIG_DECIMAL_POW_MAX_EXPONENT;
		
		for (int i = 0; i < quotient; i++) {
			// passing scale and rounding mode instead of the math context makes a **huge** difference (scale and precision are **very** different concepts!)
			bigDecimal = bigDecimal.divide(TWO_TO_THE_POWER_OF_MAX_EXPONENT, MathContext.DECIMAL128.getPrecision(), MathContext.DECIMAL128.getRoundingMode());
		}
		
		BigDecimal twoToThePowerOfReminder = BIG_DECIMAL_TWO.pow(reminder);
		bigDecimal = bigDecimal.divide(twoToThePowerOfReminder, MathContext.DECIMAL128.getPrecision(), MathContext.DECIMAL128.getRoundingMode());
		
		return bigDecimal.stripTrailingZeros();
	}
	
	/**
	 * Custom implementation of the binomial coefficient formula required because neither apache commons nor google guava
	 * provide a version that accept <code>long</code> input parameters. It is useful when calculating the weight for Integer.MAX_VALUE.
	 * 
	 * @param n
	 * @param k
	 * @return
	 */
	public static BigInteger binomialCoefficient(long n, long k) {

		BigInteger res = BigInteger.ONE;

		// Since C(n, k) = C(n, n-k)
		if (k > n - k) {
			k = n - k;
		}
		
		// Calculate value of [n * (n-1) *---* (n-k+1)] / [k * (k-1) *----* 1]
		for (long i = 0; i < k; ++i) {
			res = res.multiply(BigInteger.valueOf(n - i));
			res = res.divide(BigInteger.valueOf(i + 1));
		}

		return res;
	}
	
	private MathExtension() { }
	
}
