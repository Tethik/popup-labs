/**
 * Authors: Per Classon, Joakim Uddholm
 */
public class Vertex implements Comparable<Vertex> {
	public int index, weight;
	public Vertex prev;

	public Vertex(int index, int weight) {
		this.index = index;
		this.weight = weight;
	}

	@Override
	public int compareTo(Vertex o) {
		return Integer.compare(weight, o.weight);
	}
}
