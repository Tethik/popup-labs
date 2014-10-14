
/**
 * Represents an edge in a graph leading from vertice x to vertice y. 
 * By default it will compare against other edges by time.
 * @author Joakim Uddholm, Per Classon
 */
public class EdgeWithTime extends Edge {
	public EdgeWithTime(int x, int y, int t0, int p, int d) {
		super(x, y, t0, p, d);
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