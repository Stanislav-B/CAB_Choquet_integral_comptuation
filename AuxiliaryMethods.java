
public class AuxiliaryMethods {
	
	public double[] increasingArrange(double[] array) {
		double[] result = new double[array.length];
		for (int i = 0; i < result.length; i++) {
			result[i] = array[i];
		}
		boolean itIs = true;
		double auxiliary;
		while (itIs) {
			itIs = false;
			for (int i = 1; i < result.length; i++) {
				if (result[i - 1] > result[i]) {
					itIs = true;
					auxiliary = result[i - 1];
					result[i - 1] = result[i];
					result[i] = auxiliary;
				}
			}
		}
		return result;
	}
	
	public double[][] increasingArrangeByFirstElement(double[][] array) {
		double[][] result = new double[array.length][array[0].length];
		for (int i = 0; i < result.length; i++) {
			for (int j = 0; j < result[0].length; j++) {
				result[i][j] = array[i][j];
			}
		}
		boolean itIs = true;
		double auxiliary;
		double auxiliary2;
		while (itIs) {
			itIs = false;
			for (int i = 1; i < result.length; i++) {
				if (result[i - 1][0] > result[i][0]) {
					itIs = true;
					auxiliary = result[i - 1][0];
					auxiliary2 = result[i - 1][1];
					result[i - 1][0] = result[i][0];
					result[i - 1][1] = result[i][1];
					result[i][0] = auxiliary;
					result[i][1] = auxiliary2;
				}
			}
		}
		return result;
	}

	public int[][] increasingArrangeByFirstElement(int[][] array) {
		int[][] result = new int[array.length][array[0].length];
		for (int i = 0; i < result.length; i++) {
			for (int j = 0; j < result[0].length; j++) {
				result[i][j] = array[i][j];
			}
		}
		boolean itIs = true;
		int auxiliary;
		int auxiliary2;
		while (itIs) {
			itIs = false;
			for (int i = 1; i < result.length; i++) {
				if (result[i - 1][0] > result[i][0]) {
					itIs = true;
					auxiliary = result[i - 1][0];
					auxiliary2 = result[i - 1][1];
					result[i - 1][0] = result[i][0];
					result[i - 1][1] = result[i][1];
					result[i][0] = auxiliary;
					result[i][1] = auxiliary2;
				}
			}
		}
		return result;
	}
}
