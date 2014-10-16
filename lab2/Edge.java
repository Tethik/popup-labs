/**
 * Represents an edge in a graph leading from vertice x to vertice y. 
 * Since most algorithms to be implemented has some sort of weight/distance thing going on, we include it here.
 * By default it will compare against other edges by weight.
 * @author Joakim Uddholm, Per Classon
 */
public class Edge {
	
	public int from;
	public int to;
	
	public Edge(int from, int to) {
		this.from = from;
		this.to = to;		
	}
	
	@Override
	public boolean equals(Object obj) {
		if(!(obj instanceof Edge)) {
			return false;
		}
			
		Edge o = (Edge) obj;
		return o.from == from && o.to == to; // && o.weight == weight;
	}
	
	@Override
	public String toString() {
		return "("+from+", "+to+")";
	};
	
	/**
	 * Returns an edge with flipped vertices. I.e. a -> b becomes b -> a.
	 * Note for subclasses this will have to be overloaded!
	 * @return
	 */
	public Edge flipped() {
		Edge e = new Edge(to, from);
		return e;			
	}
}