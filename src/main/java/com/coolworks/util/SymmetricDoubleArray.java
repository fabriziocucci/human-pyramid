package com.coolworks.util;

public class SymmetricDoubleArray {

	private final int virtualLength;
	private final int physicalLength;
	private final double[] array;

	public SymmetricDoubleArray(int length) {
		this.virtualLength = length;
		this.physicalLength = (length + 1) / 2;
		this.array = new double[physicalLength];
	}

	public void set(int index, double value) {
		if (index < this.array.length) {
			this.array[index] = value;
		} else {
			this.array[this.virtualLength - index - 1] = value;
		}
	}

	public double get(int index) {
		if (index < this.array.length) {
			return this.array[index];
		} else {
			return this.array[this.virtualLength - index - 1];
		}
	}
	
	public int getPhysicalLength() {
		return physicalLength;
	}
	
}
