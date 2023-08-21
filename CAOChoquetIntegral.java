import java.util.TreeSet;

public class CAOChoquetIntegral {

	private AuxiliaryMethods am = new AuxiliaryMethods();
	private CAOperators cao=new CAOperators();

	// the collection must contain both the empty and the whole set
	// numbering of collection from zero!!!
	// measure must be defined on complements of sets
	// thus, if collection={{}, {0}, {1}, {1,2}, {0,1,2}}, then measure on
	// collection={{}, {0}, {0,2}, {1,2}, {0,1,2}} in that order!!!
	
	public CAOChoquetIntegral(double[] vector, String whichAO, int[][] collection, double[] measure, double q, double p) {
		double[] valuesAO = new double[collection.length];
		valuesAO[0] = 0;
		if (whichAO.equals("sum")) {
			// SUM
			for (int i = 1; i < collection.length; i++) {
				valuesAO[i] = cao.sumAO(vector, collection[i]);
			}
		}
		if (whichAO.equals("normSum")) {
			// averaging type of aggregation frac{1}{|E|}\cdot\sum_{i\in E} x_i
			for (int i = 1; i < collection.length; i++) {
				valuesAO[i] = cao.normSumAO(vector, collection[i]);
			}
		}
		if (whichAO.equals("pMean")) {
			// p-MEAN
			for (int i = 1; i < collection.length; i++) {
				valuesAO[i] = cao.pMeanAO(vector, collection[i], p);
			}
		}
		if (whichAO.equals("max")) {
			// MAXIMUM
			for (int i = 1; i < collection.length; i++) {
				valuesAO[i] = cao.maximumAO(vector, collection[i]);
			}
		}
		if (whichAO.equals("min")) {
			// MINIMUM
			for (int i = 1; i < collection.length; i++) {
				valuesAO[i] = cao.minimumAO(vector, collection[i]);
			}
		}
		if (whichAO.equals("maxMin")) {
			// MAXMIN
			for (int i = 1; i < collection.length; i++) {
				valuesAO[i] = cao.maxMinAO(vector, collection[i]);
			}
		}
		if (whichAO.equals("chiUniform")) {
			// CHOQUET INTEGRAL WITH UNIFORM MEASURE
			for (int i = 1; i < collection.length; i++) {
				valuesAO[i] = cao.ChoquetAOUniformMeasure(vector, collection[i], q);
			}
		}
		computingCAOChoquet(valuesAO, measure);
	}

	public void computingCAOChoquet(double[] A, double[] measureOnComplementsOfSetsOfCollection) {
		// with the following indexing, I want to connect the collections - the set with
		// its complement, thus maps () and <>
		double[][] AWithIndices = new double[A.length][2];
		double[][] measureWithIndices = new double[A.length][2];
		TreeSet<Double> ao = new TreeSet();
		TreeSet<Double> mu = new TreeSet<>();
		for (int i = 0; i < AWithIndices.length; i++) {
			AWithIndices[i][0] = A[i];
			AWithIndices[i][1] = i;
			ao.add(A[i]);
			measureWithIndices[i][0] = measureOnComplementsOfSetsOfCollection[measureOnComplementsOfSetsOfCollection.length
					- 1 - i];
			measureWithIndices[i][1] = i;
			mu.add(measureWithIndices[i][0]);
		}
		double[][] AWithIndicesIncreasing = am.increasingArrangeByFirstElement(AWithIndices);
		double[][] measureWithIndicesIncreasing = am.increasingArrangeByFirstElement(measureWithIndices);
		// lines for visualization
		int[][] ijLines = new int[A.length][2];// fisrt index i, second j
		int[][] jiLines = new int[A.length][2];// first index j, second i
		int count = 0;
		for (int i = 0; i < AWithIndicesIncreasing.length; i++) {
			for (int j = 0; j < measureWithIndicesIncreasing.length; j++) {
				if (AWithIndicesIncreasing[i][1] == measureWithIndicesIncreasing[j][1]) {
					ijLines[count][0] = i;
					ijLines[count][1] = j;
					jiLines[count][0] = j;
					jiLines[count][1] = i;
					count++;
				}
			}
		}
		jiLines = am.increasingArrangeByFirstElement(jiLines);
		int[] boldI = makeBoldI(ijLines);
		int[] boldJ = makeBoldJ(jiLines);
		int[] varphi = varphiUpperStar(measureWithIndicesIncreasing); // varphi^*
		int[] psi = psiLowerStar(measureWithIndicesIncreasing, boldI); // psi_*
		StringBuilder gsf=new StringBuilder();
		double result = 0;
		if (ao.size() <= mu.size()) {
			// calculation through AO
			for (int i = 0; i < AWithIndicesIncreasing.length - 1; i++) {
				if (AWithIndicesIncreasing[psi[i + 1]][0] != AWithIndicesIncreasing[psi[i]][0]) {
					gsf.append(measureWithIndicesIncreasing[boldI[i]][0]+"*1_["+AWithIndicesIncreasing[psi[i]][0]+","+AWithIndicesIncreasing[psi[i + 1]][0]+")+");
					result = result + ((AWithIndicesIncreasing[psi[i + 1]][0] - AWithIndicesIncreasing[psi[i]][0])
							* measureWithIndicesIncreasing[boldI[i]][0]);
				}
			}
		} else {
			// calculation through measure mu
			for (int i = measureWithIndicesIncreasing.length - 1; i >= 1; i--) {
				if (AWithIndicesIncreasing[boldJ[varphi[i - 1]]][0] != AWithIndicesIncreasing[boldJ[varphi[i]]][0]) {
					gsf.append(measureWithIndicesIncreasing[i][0]+"*1_["+AWithIndicesIncreasing[boldJ[varphi[i]]][0]+","+AWithIndicesIncreasing[boldJ[varphi[i - 1]]][0]+")+");
					result = result + ((AWithIndicesIncreasing[boldJ[varphi[i - 1]]][0]
							- AWithIndicesIncreasing[boldJ[varphi[i]]][0]) * measureWithIndicesIncreasing[i][0]);
				}
			}
		}
		System.out.println("generalized survival function: "+gsf.toString().substring(0,gsf.length()-1));
		System.out.println("conditional aggregation-based Choquet integral: "+result);
	}

	private int[] makeBoldI(int[][] ijLines) {
		int[] result = new int[ijLines.length];
		int min = Integer.MAX_VALUE;
		for (int i = 0; i < result.length; i++) {
			if (min > ijLines[i][1]) {
				min = ijLines[i][1];
			}
			result[i] = min;
		}
		return result;
	}

	private int[] makeBoldJ(int[][] jiLines) {
		int[] result = new int[jiLines.length];
		int min = Integer.MAX_VALUE;
		for (int i = 0; i < result.length; i++) {
			if (min > jiLines[i][1]) {
				min = jiLines[i][1];
			}
			result[i] = min;
		}
		return result;
	}

	private int[] varphiUpperStar(double[][] measureWithIndicesIncreasing) {
		int[] result = new int[measureWithIndicesIncreasing.length];
		int max = 0;
		for (int i = 0; i < result.length; i++) {
			max = i;
			for (int j = i; j < result.length; j++) {
				if (measureWithIndicesIncreasing[max][0] == measureWithIndicesIncreasing[j][0]) {
					max = j;
				} else {
					break;
				}
			}
			result[i] = max;
		}
		return result;
	}

	private int[] psiLowerStar(double[][] measureWithIndicesIncreasing, int[] boldI) {
		int[] result = new int[measureWithIndicesIncreasing.length];
		int min = Integer.MAX_VALUE;
		for (int i = result.length - 1; i >= 0; i--) {
			min = i;
			for (int j = i - 1; j >= 0; j--) {
				if (measureWithIndicesIncreasing[boldI[j]][0] == measureWithIndicesIncreasing[boldI[i]][0]) {
					min = j;
				} else {
					break;
				}
			}
			result[i] = min;
		}
		return result;
	}
}
