import java.util.ArrayList;
import java.util.List;

/**
 * Authors: Per Classon, Joakim Uddholm
 */
public class AdjacencyListGraph<T extends Edge> implements Graph<T>  {
	protected List<T>[] adj;
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
	@Override
	public void addEdge(T edge) {
		adj[edge.x].add(edge);
	}

	public Iterable<T> getEdgesFrom(int n) {
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
	
	@Override	
	public String toString() {
		StringBuilder builder = new StringBuilder();
		for(int i = 0; i < v; ++i) {
			builder.append(i).append(": \n");
			if(adj[i].size() == 0)
				builder.append("None");
			
			for(T e : adj[i])				
				builder.append(e).append("\n");
			builder.append("\n");
		}
		
		return builder.toString();
	}
}
