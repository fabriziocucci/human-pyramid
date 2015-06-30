package com.coolworks.humanpyramid.algorithm;

import java.math.BigDecimal;
import java.math.BigInteger;

import com.coolworks.util.MathExtension;

/**
 * The most efficient version of the algorithm to calculate the weight over a specific person in the human pyramid.
 * 
 * <p>Formula and explanation can be found <a href="http://math.stackexchange.com/questions/486807/how-much-weight-is-on-each-person-in-a-human-pyramid">here</a>.</p>
 * 
 * @author Fabrizio Cucci
 */
public class FormulaBasedHumanEdgeWeightStrategy implements HumanEdgeWeightStrategy {

	@Override
	public double getHumanEdgeWeight(int level, int index, double weight) {
		
		BigInteger sumAsBigInteger = BigInteger.ZERO;
		for (int k = 0; k <= index; k++) {
			sumAsBigInteger = sumAsBigInteger.add(BigInteger.valueOf(k + 1).multiply(MathExtension.binomialCoefficient(level + 2L, index - k)));
		}
		
		BigDecimal sumAsBigDecimal = new BigDecimal(sumAsBigInteger);
		sumAsBigDecimal = MathExtension.divideByPowerOfTwo(sumAsBigDecimal, level);

		return new BigDecimal(2 * index).add(BigDecimal.ONE).subtract(sumAsBigDecimal).multiply(new BigDecimal(weight)).doubleValue();
	}
	
}
