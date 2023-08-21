public class Launcher{
	
	public static void main(String[] args) {
		double[] vector = { 2, 3, 4 };
		int[][] collection = { {}, { 0 }, { 1 }, { 2 }, { 0, 1 }, { 0, 2 }, { 1, 2 }, { 0, 1, 2 } };
		double[] measure = { 0, 0.25, 0.25, 0.4, 0.75, 0.75, 0.75, 1 };
		
		CAOChoquetIntegral caoChoquetIntegral=new CAOChoquetIntegral(
				vector, 		// vector must contain only nonnegative components
				"sum", 			// conditional aggregation operators are: "sum", "normSum", "pMean", "max", "min", "maxMin", "chiUniform"
				collection, 
				measure, 
				1, 				// q - parameter for uniform measure - only if "chiUniform" is selected
				2				// p - parameter for p-mean - only if "pMean" is selected
		);

	}
	
}