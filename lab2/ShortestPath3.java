/*
 * Authors: Per Classon, Joakim Uddholm
 */
public class ShortestPath3 {
	public static void main(String[] args) {
		Kattio kattio = new Kattio(System.in);

		while (true) {
			int v = kattio.getInt();
			int e = kattio.getInt();
			int q = kattio.getInt();
			int s = kattio.getInt();

			if (v == 0 && e == 0 && q == 0 && s == 0)
				break;

			AdjacencyListGraph<EdgeWithWeight> g = new AdjacencyListGraph<>(v);

			for (int i = 0; i < e; ++i)
				g.addEdge(new EdgeWithWeight(kattio.getInt(), kattio.getInt(), kattio.getInt()));

			NegativePath p = bellmanFords(g, s);

			for (int i = 0; i < q; ++i) {
				long d = p.getDistance(kattio.getInt());
				if (d == Long.MAX_VALUE) {
					kattio.print("Impossible\n");
				} else {
					kattio.print(Long.toString(d));
					kattio.print('\n');
				}
			}
			kattio.print('\n');
		}
		kattio.flush();
		kattio.close();
	}

	/**
	 * 
	 * @param g
	 * @param source
	 * @return
	 */
	private static NegativePath bellmanFords(Graph<EdgeWithWeight> g, Integer source) {
		int length = g.getV();
		long[] weight = new long[length];
		boolean[] inf = new boolean[length];
		int[] path = new int[length];
		for (int i = 1; i < length; i++) {
			weight[i] = Long.MAX_VALUE;
		}
		weight[0] = 0;
		
		for (int i = 0; i < length; i++) {
			for (int j = 0; j < length; j++) {
				for (EdgeWithWeight e : g.getEdgesFrom(j)) {
					if (weight[e.to] > weight[e.from] + e.weight) {
						weight[e.to] = weight[e.from] + e.weight;
						path[e.to] = e.from;
					}
				}
			}
		}
		
		for (int i = 0; i < length; i++) {
			for (EdgeWithWeight e : g.getEdgesFrom(i)) {
				if (weight[e.from] + e.weight < weight[e.to]) {
					inf[e.to] = true;
				}
			}
		}
		
		return new NegativePath(weight, source, inf, g);
	}
}
