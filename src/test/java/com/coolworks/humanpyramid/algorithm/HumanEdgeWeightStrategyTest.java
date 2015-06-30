package com.coolworks.humanpyramid.algorithm;

import java.util.Arrays;
import java.util.Collection;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameter;
import org.junit.runners.Parameterized.Parameters;

import com.coolworks.humanpyramid.algorithm.HumanEdgeWeightStrategy;

@RunWith(Parameterized.class)
public abstract class HumanEdgeWeightStrategyTest<T extends HumanEdgeWeightStrategy> {

	private final static double PERSON_WEIGHT = 50.0;
	
	private T instance;
	
	protected abstract T newInstance();
	
	@Parameters
	public static Collection<Object[]> data() {
		return Arrays.asList(new Object[][] { 
				{ 0, new double[] { 0.0 } },
				{ 1, new double[] { 25.0, 25.0 } },
				{ 2, new double[] { 37.5, 75.0, 37.5 } },
				{ 3, new double[] { 43.75, 106.25, 106.25, 43.75 } },
				{ 4, new double[] { 46.875, 125.0, 156.25, 125.0, 46.875 } },
				{ 5, new double[] { 48.4375, 135.9375, 190.625, 190.625, 135.9375, 48.4375 } },
				{ 6, new double[] { 49.21875, 142.1875, 213.28125, 240.625, 213.28125, 142.1875, 49.21875 } },
				{ 7, new double[] { 49.609375, 145.703125, 227.734375, 276.953125, 276.953125, 227.734375, 145.703125, 49.609375} },
				{ 8, new double[] { 49.8046875, 147.65625, 236.71875, 302.34375, 326.953125, 302.34375, 236.71875, 147.65625, 49.8046875} },
				{ 9, new double[] { 49.90234375, 148.73046875, 242.1875, 319.53125, 364.6484375, 364.6484375, 319.53125, 242.1875, 148.73046875, 49.90234375 } }
		});
	}
	
    @Parameter(value = 0)
    public int level;
    
    @Parameter(value = 1)
    public double[] expectedWeights;

	@Before 
    public void setUp() {
        instance = newInstance();
    }
	
	@Test
	public void testByLevel() {

		double[] actualWeights = new double[level + 1];
		for (int i = 0; i < level + 1; i++) {
			actualWeights[i] = instance.getHumanEdgeWeight(level, i, PERSON_WEIGHT);
		}
		
		Assert.assertArrayEquals(expectedWeights, actualWeights, Double.MIN_VALUE);
	}

}
