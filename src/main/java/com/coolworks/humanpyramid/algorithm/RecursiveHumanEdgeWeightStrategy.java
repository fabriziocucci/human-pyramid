package com.coolworks.humanpyramid.algorithm;

/**
 * Naive recursive implementation of the algorithm to calculate the weight over a specific person in the human pyramid.
 * 
 * @author Fabrizio Cucci
 */
public class RecursiveHumanEdgeWeightStrategy implements HumanEdgeWeightStrategy {

	@Override
	public double getHumanEdgeWeight(int level, int index, double weight) {
		if (level == 0) {
			return 0.0;
		} else if (index == 0) {
			return (getHumanEdgeWeight(level - 1, index, weight) + weight) / 2;
		} else if (index == level) {
			return (getHumanEdgeWeight(level - 1, index - 1, weight) + weight) / 2;
		} else {
			return weight + (getHumanEdgeWeight(level - 1, index - 1, weight) / 2) + (getHumanEdgeWeight(level - 1, index, weight) / 2);
		}
	}

}
