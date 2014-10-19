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
	boolean[] inf;
	int source;
	Graph<EdgeWithWeight> g;

	public NegativePath(long[] dist, int source, boolean[] inf, Graph<EdgeWithWeight> g) {
		this.dist = dist;
		this.source = source;
		this.g = g;
		this.inf = inf;
	}

	/**
	 * Get shortest distance to node.
	 * 
	 * @param to
	 *            The node.
	 * @return The distance (long)
	 */
	public long getDistance(int to) {
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
		
		long d = dist[to];
		
		
		
		System.out.println("path: " + path);
		return path;
	}

}