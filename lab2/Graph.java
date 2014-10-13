/**
 * @author Joakim Uddholm, Per Classon
 */
public interface Graph {

	public void addEdge(Edge edge);	
	public Iterable<Edge> getEdgesFrom(int n);
	public boolean isNeighbour(int u, int v);
	public int getV();

}
