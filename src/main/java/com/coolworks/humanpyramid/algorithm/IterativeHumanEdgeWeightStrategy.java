package com.coolworks.humanpyramid.algorithm;

/**
 * Naive iterative implementation of the algorithm to calculate the weight over a specific person in the human pyramid.
 * 
 * @author Fabrizio Cucci
 */
public class IterativeHumanEdgeWeightStrategy implements HumanEdgeWeightStrategy {

	@Override
	public double getHumanEdgeWeight(int level, int index, double weight) {
		
		double[][] pyramid = new double[level + 1][];
		pyramid[0] = new double[1];
		
		for (int l = 1; l <= level; l++) {
			pyramid[l] = new double[l + 1];
			for (int i = 0; i < pyramid[l - 1].length; i++) {
				pyramid[l][i] += (pyramid[l - 1][i] + weight) / 2;
				pyramid[l][i + 1] += (pyramid[l - 1][i] + weight) / 2;
			}
		}
		
		return pyramid[level][index];
	}

}
