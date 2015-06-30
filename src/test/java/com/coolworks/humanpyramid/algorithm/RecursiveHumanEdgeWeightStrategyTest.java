package com.coolworks.humanpyramid.algorithm;

import com.coolworks.humanpyramid.algorithm.RecursiveHumanEdgeWeightStrategy;

public class RecursiveHumanEdgeWeightStrategyTest extends HumanEdgeWeightStrategyTest<RecursiveHumanEdgeWeightStrategy> {

	@Override
	protected RecursiveHumanEdgeWeightStrategy newInstance() {
		return new RecursiveHumanEdgeWeightStrategy();
	}

}
