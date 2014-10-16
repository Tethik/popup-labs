/**
 * 
 * @author Joakim Uddholm, Per Classon
 */
public class EdgeWithWeight extends Edge implements Comparable<EdgeWithWeight> {
	
	public int weight = 0;
	
	public EdgeWithWeight(int x, int y, int weight) {
		super(x, y);		
		this.weight = weight;
	}

	public EdgeWithWeight(int x, int y) {
		super(x, y);
	}

	@Override
	public int compareTo(EdgeWithWeight o) {
		return Integer.compare(weight, o.weight);
	}
	
	@Override
	public boolean equals(Object obj) {
		if(!(obj instanceof EdgeWithWeight)) {
			return false;
		}
			
		EdgeWithWeight o = (EdgeWithWeight) obj;
		return o.x == x && o.y == y && o.weight == weight;
	}
	
	@Override	
	public Edge flipped() {
		return new EdgeWithWeight(y, x, weight);
	}

	public long getWeight(long l) {		
		return l + weight;
	}
	
}
