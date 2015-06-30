package com.coolworks.humanpyramid.algorithm;

import com.coolworks.util.SymmetricDoubleArray;

/**
 * Better version of the {@link BetterIterativeHumanEdgeWeightStrategy} algorithm. Exploit the fact that each level is symmetric.
 * 
 * @author Fabrizio Cucci
 */
public class YetAnotherBetterIterativeHumanEdgeWeightStrategy implements HumanEdgeWeightStrategy {

	@Override
	public double getHumanEdgeWeight(int level, int index, double weight) {
		
		SymmetricDoubleArray previousLevel = new SymmetricDoubleArray(1);
		
		for (int l = 1; l <= level; l++) {
			
			SymmetricDoubleArray currentLevel = new SymmetricDoubleArray(l + 1);
			for (int i = 0; i < previousLevel.getPhysicalLength(); i++) {
				currentLevel.set(i, currentLevel.get(i) + ((previousLevel.get(i) + weight) / 2));
				currentLevel.set(i + 1, currentLevel.get(i + 1) + ((previousLevel.get(i) + weight) / 2));
			}
			
			if (l % 2 != 0) {
				currentLevel.set(currentLevel.getPhysicalLength() - 1, currentLevel.get(currentLevel.getPhysicalLength() - 1) - ((previousLevel.get(previousLevel.getPhysicalLength() - 1) + weight) / 2));
			} else {
				currentLevel.set(currentLevel.getPhysicalLength() - 1, currentLevel.get(currentLevel.getPhysicalLength() - 1) + ((previousLevel.get(previousLevel.getPhysicalLength() - 1) + weight) / 2));
			}
			
			previousLevel = currentLevel;
		}
		
		return previousLevel.get(index);
	}
	
}
