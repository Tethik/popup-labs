/**
 * @author Joakim Uddholm, Per Classon 
 */
public class UndirectedGraph extends AdjacencyListGraph {

	public UndirectedGraph(int v) {
		super(v);
	}
	
	@Override	
	public void addEdge(Edge edge) {	
		adj[edge.x].add(edge);
		adj[edge.y].add(edge.flipped());
	}
}
