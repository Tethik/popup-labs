import java.io.IOException;
import java.util.Arrays;
import java.util.Comparator;
import java.util.PriorityQueue;

/**
 * Solves the minimum spanning tree problem. Requires UndirectedGraph as input.
 * Main-function works as standalone. For a more general approach see solve()
 * @author Joakim Uddholm, Per Classon
 */
public class MinSpanTree {
	
	private static class EdgeComparator implements Comparator<Edge> {
		@Override
		public int compare(Edge o1, Edge o2) {
			if(o1.from == o2.from)
				return Integer.compare(o1.to, o2.to);
			return Integer.compare(o1.from, o2.from);
		}
	}
	
	/**
	 * Returns a minimum spanning composed as a list of edges in lexical order.
	 * Returns null if no such tree exists (e.g. if the graph's components are not connected).
	 * @return
	 */
	public static EdgeWithWeight[] solve(UndirectedGraph<EdgeWithWeight> graph) {
		int number_of_nodes = graph.getV();
		final int[] key = new int[number_of_nodes];
		boolean[] visited = new boolean[number_of_nodes];
		int[] pred = new int[number_of_nodes];
		
		Arrays.fill(key, Integer.MAX_VALUE);
		Arrays.fill(pred, -1);	
		key[0] = 0;		
		
		PriorityQueue<Vertex> vertices = new PriorityQueue<>(); 
		vertices.add(new Vertex(0,0));
		
		while(!vertices.isEmpty()) {
			int u = vertices.poll().index;
			
			visited[u] = true;
			
			for(EdgeWithWeight e : graph.getEdgesFrom(u)) {				
				if(visited[e.to])
					continue;
				
				int w = e.weight;
				int y = e.to;
				if(w < key[y]) {
					key[y] = w;	
					Vertex v = new Vertex(y, w);
					vertices.add(v);
					pred[y] = u;
				}
			}
		}
		
		EdgeWithWeight[] solution = new EdgeWithWeight[number_of_nodes-1];
		for(int i = 1; i < number_of_nodes; ++i) {
			if(pred[i] < 0)
				return null;
			
			int x;
			int y;
			if (i < pred[i]) {
				x = i;
				y = pred[i];
			} else {
				x = pred[i];
				y = i;
			}
			solution[i-1] = new EdgeWithWeight(x, y, key[i]);
		}
		
		Arrays.sort(solution, new EdgeComparator());
		return solution;
	}

	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		Kattio io = new Kattio(System.in, System.out);
		
		while(true) {
			int nodes = io.getInt();
			int edges = io.getInt();
			if(nodes == 0 && edges == 0) {
				io.flush();
				io.close();
				return;
			}
			
			UndirectedGraph<EdgeWithWeight> graph = new UndirectedGraph<EdgeWithWeight>(nodes);
			for(int i = 0; i < edges; ++i) {
				EdgeWithWeight e = new EdgeWithWeight(io.getInt(), io.getInt(), io.getInt());
				graph.addEdge(e);
			}
			
			EdgeWithWeight[] sol = MinSpanTree.solve(graph);
			
			if(sol == null) {
				io.write("Impossible\n");
				continue;
			}
			
			int cost = 0;
			for(EdgeWithWeight e : sol) {
				cost += e.weight;
			}
			io.write(Integer.toString(cost));
			io.write('\n');
			
			for(Edge e : sol) {
				io.write(Integer.toString(e.from));
				io.write(' ');
				io.write(Integer.toString(e.to));
				io.write('\n');
			}
		}		
	}

}
