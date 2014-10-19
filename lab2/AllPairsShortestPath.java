import java.util.ArrayList;


public class AllPairsShortestPath {
	
	private int nodes;
	private int[][] dist;
	private int[][] next;
	private ArrayList<EdgeWithWeight> edges = new ArrayList<EdgeWithWeight>();
	
	public AllPairsShortestPath(int nodes) {
		this.nodes = nodes;
		this.dist = new int[nodes][nodes];
		this.next = new int[nodes][nodes];
	}
	
	public void addEdge(int u, int v, int w) {
		edges.add(new EdgeWithWeight(u, v, w));
	}
	
	public void populate() {
		for(int i = 0; i < nodes; ++i) {
			for(int j = 0; j < nodes; ++j) {
				next[i][j] = -1;
				if(i == j) continue;
				dist[i][j] = Integer.MAX_VALUE;				
			}
		}
		
		for(EdgeWithWeight edge : edges) {			
			if(edge.weight > 0 && edge.from == edge.to)
				continue;
			dist[edge.from][edge.to] = edge.weight;
			next[edge.from][edge.to] = edge.to;
		}
		
		// Trippellooooooop
		for(int k = 0; k < nodes; ++k) {
			for(int i = 0; i < nodes; ++i) {
				for(int j = 0; j < nodes; ++j) {
					int sum = (dist[i][k] == Integer.MAX_VALUE || dist[k][j] == Integer.MAX_VALUE) ?
							Integer.MAX_VALUE :
							dist[i][k] + dist[k][j];
//					if(i == 3 && j == 3) {
//						System.out.println(sum);
//						System.out.println(dist[i][j]);
//					}
					if(sum < dist[i][j]) {
		               dist[i][j] = sum;
	               	   next[i][j] = next[i][k];
					}
				}
			}
		}
				
		
	}
	
	public int query(int u, int v) {
		if(dist[u][v] == Integer.MAX_VALUE)
			return Integer.MAX_VALUE;
		
		
		int n = u;
		while(n != -1) {
			if(dist[n][n] < 0)
				return Integer.MIN_VALUE;
			n = next[n][v];
		}
		
		return dist[u][v];
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Kattio katt = new Kattio(System.in, System.out);
		while(true) {
			int n = katt.getInt(); // vertices
			int m = katt.getInt(); // edges
			int q = katt.getInt(); // queries
			
			if(n == 0 && m == 0 && q == 0)
				break;
			
			AllPairsShortestPath path = new AllPairsShortestPath(n);
			
			for(int i = 0; i < m; ++i) {
				path.addEdge(katt.getInt(), katt.getInt(), katt.getInt());
			}
			
			
			path.populate();
			
			// query
			for(int i = 0; i < q; ++i) {
				
				int u = katt.getInt();
				int v = katt.getInt();
				
				int w = path.query(u, v);
				if(w == Integer.MAX_VALUE) {
					katt.append("Impossible\n");
				} else if(w == Integer.MIN_VALUE) {
					katt.append("-Infinity\n");
				} else {
					katt.append(Integer.toString(w)).append("\n");
				}
			}
			katt.append("\n");
			katt.flush();
		}
		katt.close();

	}

}
