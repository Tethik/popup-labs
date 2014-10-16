import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Queue;

/**
 * MaxFlow solver. Usage: Create, add edges. Then solve(). 
 *  Edges/Graph is provided in graph object via getGraph().
 *  Total flow can be gotten by getTotalFlow().
 * @author Joakim Uddholm, Per Classon
 */
public class MaxFlow {
	
	private AdjacencyListGraph<EdgeWithFlow> graph;
	private EdgeWithFlow[] path;
	private int[] flowMap;
	private int totalFlow = 0;
	private int nodes;
	
	public MaxFlow(int nodes) {
		this.nodes = nodes;
		this.graph = new AdjacencyListGraph<EdgeWithFlow>(nodes);
	}
	
	public AdjacencyListGraph<EdgeWithFlow> getGraph() {
		return graph;
	}
	
	public int getTotalFlow() {
		return totalFlow;
	}
	
	public void addEdge(int x, int y, int capacity) {
		EdgeWithFlow edge = new EdgeWithFlow(x, y, capacity);
		EdgeWithFlow reverse = new EdgeWithFlow(y, x, 0);
		edge.reverse = reverse;
		reverse.reverse = edge;
		graph.addEdge(edge);			
		graph.addEdge(reverse);
	}
	
	public void solve(int source, int sink) {
		// Map from Node to amount of input flow
		this.flowMap = new int[nodes];
		// Map from Node to Edge which lead to that node.
		this.path = new EdgeWithFlow[nodes];
		
		while(true) {
			// Greedy search for any path.
			findpath(source, sink);	
			
			int flow = this.flowMap[sink];			
			if(flow == Integer.MAX_VALUE)
				break;
			
			// Step back and put in flows from path
			int next = sink;			
			totalFlow += flow;
			while(next != source) {
				EdgeWithFlow edge = path[next];
				edge.flow += flow;
				edge.reverse.flow -= flow;
				next = edge.x;
			}
		}
	}

	private void findpath(int source, int sink) {
		Queue<Integer> queue = new ArrayDeque<>();
		queue.add(source);		
	
		for(int i = 0; i < nodes; ++i) {
			flowMap[i] = Integer.MAX_VALUE;			
		}
		
		while(queue.size() > 0) {
			int current = queue.poll();
			
			for(EdgeWithFlow edge : graph.getEdgesFrom(current)) {	
				int residual = edge.residual();		
				if(residual > 0 && flowMap[edge.y] == Integer.MAX_VALUE && edge.y != source) {
					path[edge.y] = edge;	
					flowMap[edge.y] = Math.min(residual, flowMap[edge.x]);
					
					if(edge.y == sink)
						return;
					
					queue.add(edge.y);
				}	
			}
		}
		return;
	}

	
	/**
	 * Kattis main funktion.
	 * @param args
	 */
	public static void main(String[] args) {
		Kattio katt = new Kattio(System.in, System.out);
		int n = katt.getInt();
		int m = katt.getInt();
		int source = katt.getInt();
		int sink = katt.getInt();
		
		MaxFlow flow = new MaxFlow(n);
		for(int i = 0; i < m; ++i) {			
			flow.addEdge(katt.getInt(), katt.getInt(), katt.getInt());
		}
		
		flow.solve(source, sink);
		
		AdjacencyListGraph<EdgeWithFlow> graph = flow.getGraph();
		
		ArrayList<EdgeWithFlow> edges = new ArrayList<>();		
		for(int i = 0; i < n; ++i) {
			for(EdgeWithFlow edge : graph.getEdgesFrom(i)) {				
				if(edge.capacity > 0 && edge.flow > 0) {
					assert(edge.flow <= edge.capacity);
					edges.add(edge);
				}					
			}
		}
		
		katt.append(Integer.toString(n)).append(" ")
		.append(Integer.toString(flow.getTotalFlow()))
		.append(" ").append(Integer.toString(edges.size()));
		katt.append("\n");
		
		for(EdgeWithFlow edge : edges)
			katt.append(edge.toString()).append("\n");
		katt.flush();
		katt.close();

	}

}
