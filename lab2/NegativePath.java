import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Authors: Per Classon, Joakim Uddholm
 */
public class NegativePath {
	long[] dist;
	int[] pre;
	boolean[] inf;
	int source;
	Graph<EdgeWithWeight> g;

	public NegativePath(long[] dist, int[] pre, boolean[] inf,
			Graph<EdgeWithWeight> g, Integer source) {
		this.dist = dist;
		this.source = source;
		this.g = g;
		this.inf = inf;
		this.pre = pre;
	}

	/**
	 * Get shortest distance to node.
	 * 
	 * @param to
	 *            The node.
	 * @return The distance (long)
	 */
	public long getDistance(int to) {
		if (containsCycle(to))
			return Long.MIN_VALUE;
		return dist[to];
	}

	/**
	 * Returns the path from source to the node.
	 * 
	 * @param to
	 *            Node to.
	 * @return Returns list of the path. Null if impossible and empty list if we
	 *         try against the node.
	 */
	public List<Integer> getPath(int to) {
		List<Integer> path = new ArrayList<>();

		System.out.println("path: " + path);
		return path;
	}

	public boolean containsCycle(int to) {
		Set<Integer> v = new HashSet<>();
		v.add(to);
		while (to != source) {
			to = pre[to];
			if (v.contains(to))
				return true;
			v.add(to);
		}
		return false;
	}

}