/**
 * @author Joakim Uddholm, Per Classon
 */
public interface Graph <T extends Edge>  {

	public void addEdge(T edge);	
	public Iterable<T> getEdgesFrom(int n);
	public boolean isNeighbour(int u, int v);
	public int getV();

}
