/**
 * Represents an edge in a graph leading from vertice x to vertice y. 
 * Since most algorithms to be implemented has some sort of weight/distance thing going on, we include it here.
 * By default it will compare against other edges by weight.
 * @author Joakim Uddholm, Per Classon
 */
public class Edge implements Comparable<Edge> {
	public Edge(int x, int y) {
		this.x = x;
		this.y = y;		
	}
	
	public Edge(int x, int y, int weight) {
		this.x = x;
		this.y = y;		
		this.weight = weight;
	}
	
	public Edge(int x, int y, int t0, int p, int d) {
		this.x = x;
		this.y = y;		
		this.weight = d;
		this.t0 = t0;
		this.p = p;
	}
	
	public int x;
	public int y;
	public int weight;
	public int t0;
	public int p;
	
	@Override
	public boolean equals(Object obj) {
		if(!(obj instanceof Edge)) {
			return false;
		}
			
		Edge o = (Edge) obj;
		return o.x == x && o.y == y && o.weight == weight;
	}
	
	@Override
	public String toString() {
		return "("+x+", "+y+", "+weight+")";
	};
	
	/**
	 * Returns an edge with flipped vertices. I.e. a -> b becomes b -> a.
	 * Note for subclasses this will have to be overloaded!
	 * @return
	 */
	public Edge flipped() {
		Edge e = new Edge(y, x, weight);
		return e;			
	}

	@Override
	public int compareTo(Edge o) {
		return Integer.compare(weight, o.weight);
	}
}