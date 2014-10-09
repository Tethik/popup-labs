import java.util.Arrays;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;

/*
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

			Graph g = new Graph(v);

			for (int i = 0; i < e; i++)
				g.addEdge(kattio.getInt(), kattio.getInt(), kattio.getInt());

			Path p = dijkstra(g, s);
			
			for (int i = 0; i < q; i++) {
				long d = p.getDistance(kattio.getInt());
				if (d == Long.MAX_VALUE)
					System.out.println("Impossible");
				else
					System.out.println(d);
			}
			System.out.println();
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
	public static Path dijkstra(Graph g, int source) {
		int length = g.getV();
		PriorityQueue<Vertex> q = new PriorityQueue<Vertex>(length);
		Vertex[] prev = new Vertex[length];

		// get all vertex: from graph
		
		while (!q.isEmpty()) {
			Vertex u = q.poll();
			if (u.dist == Long.MAX_VALUE) break;
			for (Vertex v : g.getNeighbours(u.index)) {
				if (v.equals(u))
					continue;
				long alt = u.dist + v.weight;
				if (alt < u.dist) {
					u.dist = alt;
					v.prev = u;
				}
			}
		}
		
		return new Path(dist, prev, source);
	}
}
