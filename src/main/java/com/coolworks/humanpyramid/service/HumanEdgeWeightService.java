package com.coolworks.humanpyramid.service;

import com.coolworks.humanpyramid.algorithm.HumanEdgeWeightStrategy;

public class HumanEdgeWeightService {
	
	public static final double PERSON_WEIGHT = 50.0;
	
	private final HumanEdgeWeightStrategy humanEdgeWeightStrategy;
	
	public HumanEdgeWeightService(HumanEdgeWeightStrategy humanEdgeWeightStrategy) {
		this.humanEdgeWeightStrategy = humanEdgeWeightStrategy;
	}

	public double getHumanEdgeWeight(int level, int index) {
		return humanEdgeWeightStrategy.getHumanEdgeWeight(level, index, PERSON_WEIGHT);
	}
	
}
