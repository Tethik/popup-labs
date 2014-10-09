import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/*
 * Authors: Per Classon, Joakim Uddholm
 */
public class Path {
	long[] dist;
	int[] prev;
	int source;

	public Path(long[] dist, int[] prev, int source) {
		this.dist = dist;
		this.prev = prev;
		this.source = source;
	}

	/**
	 * Get shorest distance to node. 
	 * @param to The node.
	 * @return The distance (long)
	 */
	public long getDistance(int to) {
		return dist[to];
	}

	/**
	 * Returns the path from source to the node.
	 * @param to Node to.
	 * @return Returns list of the path. Null if impossible and empty list if we try against the node.
	 */
	public List<Integer> getPath(int to) {
		List<Integer> path = new ArrayList<>();

		while (to != source) {
			if (prev[to] < 0)
				return null;
			path.add(to);
			to = prev[to];
		}
		if (path.size() > 0)
			path.add(source);

		Collections.reverse(path);

		return path;
	}

}