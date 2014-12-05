import java.util.Comparator;
import java.util.HashSet;
import java.util.PriorityQueue;

/**
 * Authors: Per Classon, Joakim Uddholm
 */
public class ShortestPath {

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
	
	private static final class DistanceComparator implements Comparator<Integer> {
		private final long[] dist;
		
		private DistanceComparator(long[] dist) {
			this.dist = dist;
		}
		
		@Override
		public int compare(Integer o1, Integer o2) {
			return Long.compare(dist[o1], dist[o2]);
		}
	}
	
	/**
	 * Produces shortest path from source to all reachable nodes in graph.
	 * 
	 * @param g
	 *            Graph object.
	 * @param source
	 *            Source integer.
	 * @return Path object with both distance and path to all nodes
	 */
	public static Path dijkstra(AdjacencyListGraph<EdgeWithWeight> g, int source) {
		int length = g.getV();
		
		PriorityQueue<Vertex> q = new PriorityQueue<Vertex>();
		
		int[] prev = new int[length];
		boolean[] visited = new boolean[length];
		long[] weight = new long[length];
		
		for(int i = 0; i < length; ++i) {
			prev[i] = -1;
			weight[i] = Long.MAX_VALUE;
		}
		
		prev[source] = source;
		weight[source] = 0;
		q.add(new Vertex(source, 0));
		
		// get all vertex: from graph		
		while (!q.isEmpty()) {
			Vertex v = q.poll();
			int node = v.index;

			if (visited[node])
				continue;
			
			visited[node] = true;
			
			for (EdgeWithWeight e : g.getEdgesFrom(node)) {
				if (visited[e.to])
					continue;
				
				int alt = v.weight + e.weight;
				
				if (alt < weight[e.to]) {
					weight[e.to] = alt;
					Vertex to = new Vertex(e.to, alt);
					q.add(to);
					prev[e.to] = node;
				}
				
			}
		}
		
		return new Path(weight, prev, source);
	}
}
