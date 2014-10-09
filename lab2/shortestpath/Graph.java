import java.util.ArrayList;
import java.util.List;

/*
 * Authors: Per Classon, Joakim Uddholm
 */
public class Graph {
	private List<Vertex>[] adj;
	private int v;

	/**
	 * The constructor for our graph.
	 * 
	 * @param v
	 *            The number of vertices in the graph
	 * @param e
	 *            The number of edges in the graph
	 */
	@SuppressWarnings("unchecked")
	public Graph(int v) {
		this.v = v;
		adj = new List[v];
		for (int i = 0; i < v; i++) {
			adj[i] = new ArrayList<>();
		}
	}

	/**
	 * Add edge to the graph.
	 * 
	 * @param n
	 *            Edge from.
	 * @param m
	 *            Edge to.
	 * @param w
	 *            Weight of weight.
	 */
	public void addEdge(int n, int m, int w) {
		adj[n].add(new Vertex(m, w));
	}

	public List<Vertex> getNeighbours(int n) {
		return adj[n];
	}

	public int getV() {
		return v;
	}
}
