package com.coolworks.humanpyramid.algorithm;

import com.coolworks.humanpyramid.algorithm.FormulaBasedHumanEdgeWeightStrategy;

public class FormulaBasedHumanEdgeWeightStrategyTest extends HumanEdgeWeightStrategyTest<FormulaBasedHumanEdgeWeightStrategy> {

	@Override
	protected FormulaBasedHumanEdgeWeightStrategy newInstance() {
		return new FormulaBasedHumanEdgeWeightStrategy();
	}

}
