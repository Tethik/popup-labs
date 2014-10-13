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
			if(o1.x == o2.x)
				return Integer.compare(o1.y, o2.y);
			return Integer.compare(o1.x, o2.x);
		}
	}
	
	/**
	 * Returns a minimum spanning composed as a list of edges in lexical order.
	 * Returns null if no such tree exists (e.g. if the graph's components are not connected).
	 * @return
	 */
	public static Edge[] solve(UndirectedGraph graph) {
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
			
			for(Edge e : graph.getEdgesFrom(u)) {				
				if(visited[e.y])
					continue;
				
				int w = e.weight;
				int y = e.y;
				if(w < key[y]) {
					key[y] = w;	
					Vertex v = new Vertex(y, w);
					vertices.add(v);
					pred[y] = u;
				}
			}
		}
		
		Edge[] solution = new Edge[number_of_nodes-1];
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
			solution[i-1] = new Edge(x, y, key[i]);
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
			
			UndirectedGraph graph = new UndirectedGraph(nodes);
			for(int i = 0; i < edges; ++i) {
				Edge e = new Edge(io.getInt(), io.getInt(), io.getInt());
				graph.addEdge(e);
			}
			
			Edge[] sol = MinSpanTree.solve(graph);
			
			if(sol == null) {
				io.write("Impossible\n");
				continue;
			}
			
			int cost = 0;
			for(Edge e : sol) {
				cost += e.weight;
			}
			io.write(Integer.toString(cost));
			io.write('\n');
			
			for(Edge e : sol) {
				io.write(Integer.toString(e.x));
				io.write(' ');
				io.write(Integer.toString(e.y));
				io.write('\n');
			}
		}		
	}

}
