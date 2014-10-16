/**
 * @author Joakim Uddholm, Per Classon 
 */
public class UndirectedGraph<T extends Edge> extends AdjacencyListGraph<T> {

	public UndirectedGraph(int v) {
		super(v);
	}
	
	@Override	
	public void addEdge(T edge) {			
		adj[edge.from].add(edge);
		T flipped = (T) edge.flipped();
		adj[edge.to].add(flipped);
	}
}
