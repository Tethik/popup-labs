import java.util.Comparator;
import java.util.HashSet;
import java.util.PriorityQueue;


public class ShortestPath2 {
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
				g.addEdge(new Edge(kattio.getInt(), kattio.getInt(), kattio.getInt(), kattio.getInt(), kattio.getInt()));

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
	public static Path dijkstra(AdjacencyListGraph g, int source) {
		int length = g.getV();
		
		final long[] time = new long[length];		
		PriorityQueue<Integer> q = new PriorityQueue<Integer>(length, new DistanceComparator(time));
		
		int[] prev = new int[length];
		HashSet<Integer> visited = new HashSet<>();
		
		for(int i = 0; i < length; ++i) {
			time[i] = Long.MAX_VALUE;
			prev[i] = -1;
		}
		time[source] = 0;
		prev[source] = source;
		q.add(source);
		
		// get all vertex: from graph		
		while (!q.isEmpty()) {
			Integer node = q.poll();
				
			visited.add(node);
			for (Edge e : g.getEdgesFrom(node)) {
				if(visited.contains(e.y))
					continue;

				long t = time[node];
				long newTime = 0;
				
				if (e.p == 0 && t > e.t0)
					continue;
				
				if (e.t0 > t) {
					newTime = e.t0;
				}
				else if (e.p == 0 || ((t - e.t0) % e.p) == 0) {
					newTime = t;
				}
				else {
					newTime = t + e.p - ((t - e.t0) % e.p);
				}
					
				long alt = newTime + e.weight;
				if (alt < time[e.y]) {
					time[e.y] = alt;
					prev[e.y] = node;
					q.remove(e.y);
					q.add(e.y);
				}
				
			}
		}
		
		return new Path(time, prev, source);
	}
}
