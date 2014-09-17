import java.util.ArrayList;

public class Knapsack {
	public int[] solve(Double capacity, int[] values, int[] weights) {
		int maxItems = values.length;
		int maxWeight = capacity.intValue();
		int[][] resultValue = new int[maxItems + 1][maxWeight + 1];
		boolean[][] bestItems = new boolean[maxItems + 1][maxWeight + 1];

		for (int i = 1; i <= maxItems; i++) {
			for (int w = 0; w <= maxWeight; w++) {
				int prevValue = resultValue[i - 1][w];
				int value = 0;
				if (w - weights[i - 1] >= 0)
					value = values[i - 1]
							+ resultValue[i - 1][w - weights[i - 1]];

				if (value > prevValue) {
					resultValue[i][w] = value;
					bestItems[i][w] = true;
				} else {
					resultValue[i][w] = prevValue;
					bestItems[i][w] = false;
				}
			}
		}

		ArrayList<Integer> res = new ArrayList<>();
		int weightLeft = maxWeight;
		for (int i = maxItems; i > 0; i--) {
			if (bestItems[i][weightLeft]) {
				res.add(i - 1);
				weightLeft -= weights[i - 1];
			}
		}

		int[] ret = new int[res.size()];
		for (int i = 0; i < ret.length; i++) {
			ret[i] = res.get(i).intValue();
		}

		return ret;
	}

	public static void main(String[] args) {
		Kattio kattio = new Kattio(System.in);
		Knapsack knapsack = new Knapsack();
		
		do {
			double capacity = kattio.getDouble();
			int n = kattio.getInt();
			int[] values = new int[n];
			int[] weights = new int[n];
			
			for (int i = 0; i < n; i++) {
				values[i] = kattio.getInt();
				weights[i] = kattio.getInt();
			}
			
			int[] res = knapsack.solve(capacity, values, weights);

			StringBuilder sb = new StringBuilder();
			sb.append(res.length).append("\n");
			
			for (int i = 0; i < res.length; i++) {
				sb.append(res[i]).append(" ");
			}
			
			System.out.println(sb.toString().trim());
		} while (kattio.hasMoreTokens());

	}
}
