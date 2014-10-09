import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.PriorityQueue;


public class MinSpanTree {
	
//	private static class VerticeComparator implements Comparator<Integer> {
//		private int[] key;
//		public VerticeComparator(int[] key) {
//			this.key = key;
//		}
//		
//		@Override
//		public int compare(Integer u, Integer v) {				
//			return Integer.compare(key[u], key[v]);
//		}
//	}
//	
	private static class EdgeComparator implements Comparator<Edge> {
		@Override
		public int compare(Edge o1, Edge o2) {
			if(o1.x == o2.x)
				return Integer.compare(o1.y, o2.y);
			return Integer.compare(o1.x, o2.x);
		}
	}
	
	public static class Edge implements Comparable<Edge> {
		public Edge(int x, int y, int w) {
			this.x = x;
			this.y = y;
			this.w = w;
		}
		
		public int x;
		public int y;
		public int w;
	
		@Override
		public int compareTo(Edge o) {			
			return Integer.compare(w, o.w);
		}
		
		@Override
		public boolean equals(Object obj) {
			if(!(obj instanceof Edge)) {
				return false;
			}
				
			Edge o = (Edge) obj;
			return o.x == x && o.y == y && o.w == w;
		}
		
		@Override
		public String toString() {
			return "("+x+", "+y+", "+w+")";
		};
		
		public Edge flipped() {
			Edge e = new MinSpanTree.Edge(y, x, w);
			return e;			
		}
	}
	
	private static class Vertex implements Comparable<Vertex> {

		public int node;
		public int weight;
		
		public Vertex(int y, int w) {
			this.node = y;
			this.weight = w;
		}

		@Override
		public int compareTo(Vertex o) {
			return Integer.compare(weight, o.weight);
		}
		
	}
	
	
	
	private int number_of_nodes;
	private ArrayList<ArrayList<Edge>> adjacencyList = new ArrayList<ArrayList<Edge>>();
	
	public MinSpanTree(int n) {
		number_of_nodes = n;
		for(int i = 0; i < n; ++i)
			adjacencyList.add(new ArrayList<MinSpanTree.Edge>());
	}
	
	public void add(Edge e) {
		assert(e.x < e.y);
		adjacencyList.get(e.x).add(e);
		adjacencyList.get(e.y).add(e.flipped());
	}
	
	public Edge[] solve() {
		final int[] key = new int[number_of_nodes];
		boolean[] visited = new boolean[number_of_nodes];
		int[] pred = new int[number_of_nodes];
		
		Arrays.fill(key, Integer.MAX_VALUE);
		Arrays.fill(pred, -1);	
		key[0] = 0;		
		
		PriorityQueue<Vertex> vertices = new PriorityQueue<>(); 
		vertices.add(new Vertex(0,0));
		
		while(!vertices.isEmpty()) {
			int u = vertices.poll().node;
			if(visited[u])
				continue;
			
			for(Edge e : adjacencyList.get(u)) {
				int w = e.w;
				int y = e.y;
				if(!visited[y] && w < key[y]) {
					key[y] = w;	
					Vertex v = new Vertex(y, w);
					vertices.add(v);
					pred[y] = u;
				}
			}			
			visited[u] = true;
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
			
			MinSpanTree tree = new MinSpanTree(nodes);
			for(int i = 0; i < edges; ++i) {
				Edge e = new Edge(io.getInt(), io.getInt(), io.getInt());
				tree.add(e);
			}
			
			Edge[] sol = tree.solve();
			
			if(sol == null) {
				io.write("Impossible\n");
				continue;
			}
			
			int cost = 0;
			for(Edge e : sol) {
				cost += e.w;
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
