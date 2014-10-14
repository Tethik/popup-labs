
/*
 * Authors: Per Classon, Joakim Uddholm
 */
public class ShortestPath2 extends ShortestPath {
	public static void main(String[] args) {
		Kattio kattio = new Kattio(System.in);

		while (true) {
			int v = kattio.getInt();
			int e = kattio.getInt();
			int q = kattio.getInt();
			int s = kattio.getInt();

			if (v == 0 && e == 0 && q == 0 && s == 0)
				break;

			AdjacencyListGraph g = new AdjacencyListGraph(v);

			for (int i = 0; i < e; ++i)
				g.addEdge(new EdgeWithTime(kattio.getInt(), kattio.getInt(), kattio.getInt(), kattio.getInt(), kattio.getInt()));

			Path p = dijkstra(g, s);
			
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
}
