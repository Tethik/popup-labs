import java.util.ArrayList;
import java.util.List;

/**
 * Authors: Per Classon, Joakim Uddholm
 */
public class AdjacencyListGraph implements Graph {
	protected List<Edge>[] adj;
	protected int v;

	/**
	 * The constructor for our graph.
	 * 
	 * @param v
	 *            The number of vertices in the graph
	 * @param e
	 *            The number of edges in the graph
	 */
	@SuppressWarnings("unchecked")
	public AdjacencyListGraph(int v) {
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
	public void addEdge(Edge edge) {
		adj[edge.x].add(edge);
	}

	public Iterable<Edge> getEdgesFrom(int n) {
		return adj[n];
	}
	
//	public Iterable<Vertex> getNeighbours(int n) {
//		return adj[n];
//	}

	public int getV() {
		return v;
	}

	@Override
	public boolean isNeighbour(int u, int v) {
		return adj[u].contains(v);
	}
}
