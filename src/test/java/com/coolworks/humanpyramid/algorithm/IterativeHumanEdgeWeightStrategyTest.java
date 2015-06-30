package com.coolworks.humanpyramid.algorithm;

import com.coolworks.humanpyramid.algorithm.IterativeHumanEdgeWeightStrategy;

public class IterativeHumanEdgeWeightStrategyTest extends HumanEdgeWeightStrategyTest<IterativeHumanEdgeWeightStrategy> {

	@Override
	protected IterativeHumanEdgeWeightStrategy newInstance() {
		return new IterativeHumanEdgeWeightStrategy();
	}

}
