
/**
 * Represents an edge in a graph leading from vertice x to vertice y. 
 * By default it will compare against other edges by time.
 * @author Joakim Uddholm, Per Classon
 */
public class EdgeWithTime extends EdgeWithWeight {
	
	public int weight;
	public int t0;
	public int p;
	
	public EdgeWithTime(int x, int y, int t0, int p, int d) {
		super(x, y, d);
		this.t0 = t0;
		this.p = p;
//		this.weight = d;
	}
		
	@Override
	public long getWeight(long t) {
		long newTime = 0;
		
		if (p == 0 && t > t0)
			return -1l;
		
		if (t0 > t) {
			newTime = t0;
		}
		else if (p == 0 || ((t - t0) % p) == 0) {
			newTime = t;
		}
		else {
			newTime = t + p - ((t - t0) % p);
		}
		return newTime + weight;
	}
}