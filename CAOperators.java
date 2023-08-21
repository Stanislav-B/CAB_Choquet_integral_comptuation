
public class CAOperators {

	private AuxiliaryMethods am = new AuxiliaryMethods();

	public double sumAO(double[] vector, int[] set) {
		double result = 0;
		for (int i = 0; i < set.length; i++) {
			result = result + vector[set[i]];
		}
		return result;
	}

	// normed sum by conditional set: frac{1}{|E|}\cdot\sum_{i\in E} x_i
	public double normSumAO(double[] vector, int[] set) {
		if (set.length == 0) {
			return 0;
		}
		double result = 0;
		for (int i = 0; i < set.length; i++) {
			result = (1.0 / (double) set.length) * (sumAO(vector, set));
		}
		return result;
	}

	public double pMeanAO(double[] vector, int[] set, double p) {
		if (set.length == 0) {
			return 0;
		}
		double result = 0;
		for (int i = 0; i < set.length; i++) {
			result = result + Math.pow(vector[set[i]], p);
		}
		result = result / ((double) set.length);
		result = Math.pow(result, 1.0 / p);
		return result;
	}

	public double maximumAO(double[] vector, int[] set) {
		double result = 0;
		for (int i = 0; i < set.length; i++) {
			if (result < vector[set[i]]) {
				result = vector[set[i]];
			}
		}
		return result;
	}

	public double minimumAO(double[] vector, int[] set) {
		double result = Double.MAX_VALUE;
		for (int i = 0; i < set.length; i++) {
			if (result > vector[set[i]]) {
				result = vector[set[i]];
			}
		}
		return result;
	}

	// (max+min)/2
	public double maxMinAO(double[] vector, int[] set) {
		double max = 0;
		double min = Double.MAX_VALUE;
		for (int i = 0; i < set.length; i++) {
			if (max < vector[set[i]]) {
				max = vector[set[i]];
			}
			if (min > vector[set[i]]) {
				min = vector[set[i]];
			}
		}
		return ((max + min) / 2);
	}

	public double ChoquetAOUniformMeasure(double[] vector, int[] set, double q) {
		double[] newVector = new double[vector.length];
		for (int i = 0; i < set.length; i++) {
			newVector[set[i]] = vector[set[i]];
		}
		double[] newVectorIncreasing = am.increasingArrange(newVector);
		double result = newVectorIncreasing[0];
		double n = newVectorIncreasing.length;
		for (int i = 1; i < n; i++) {
			result = result + ((newVectorIncreasing[i] - newVectorIncreasing[i - 1]) * (Math.pow((n - i) / n, q)));
		}
		return result;
	}
}
