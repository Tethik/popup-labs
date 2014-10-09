/*
 * Authors: Per Classon, Joakim Uddholm
 */
public class Vertex implements Comparable<Vertex> {
	public long dist;
	public int index, weight;
	public Vertex prev;

	public Vertex(int index, int weight) {
		this.index = index;
		this.weight = weight;
	}

	@Override
	public int compareTo(Vertex o) {
		return Long.compare(dist, o.dist);
	}
}
