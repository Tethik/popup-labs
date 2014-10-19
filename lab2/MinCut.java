import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.HashSet;
import java.util.List;

/**
 * Solves the minimum cut problem. 
 * 
 * Idea From: Föreläsning + http://stackoverflow.com/questions/4482986/how-can-i-find-the-minimum-cut-on-a-graph-using-a-maximum-flow-algorithm
 * @author Joakim Uddholm, Per Classon
 */
public class MinCut extends MaxFlow {
	
	public List<Integer> markedNodes = new ArrayList<>();
	
	/**
	 * This contains the solution after solve has been called. Not the nicest interface...
	 * @return
	 */
	public List<Integer> getMinimumCutVertices() {
		return markedNodes;
	}

	public MinCut(int nodes) {
		super(nodes);
	}
	
	/**
	 * Take solution from 
	 */
	public void solve(int source, int sink) {
		super.solve(source, sink);
				
		HashSet<Integer> marked = new HashSet<>();
		Deque<Integer> dfs = new ArrayDeque<>();
		dfs.add(source);
		marked.add(source);
		markedNodes.add(source);
		while(dfs.size() > 0) {
			int node = dfs.pollLast();
			
			for(EdgeWithFlow edge : graph.getEdgesFrom(node)) {
				if(edge.flow >= edge.capacity)  
					continue;
				
				if(marked.contains(edge.to)) 
					continue;
				
				
				dfs.add(edge.to);
				marked.add(edge.to);
				markedNodes.add(edge.to);
			}
		}
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Kattio katt = new Kattio(System.in, System.out);
		int n = katt.getInt();
		int m = katt.getInt();
		int source = katt.getInt();
		int sink = katt.getInt();
		
		MinCut cut = new MinCut(n);
		for(int i = 0; i < m; ++i) {			
			cut.addEdge(katt.getInt(), katt.getInt(), katt.getInt());
		}
		
		cut.solve(source, sink);
		
		katt.append(Integer.toString(cut.getMinimumCutVertices().size())).append("\n");;
		for(int i : cut.getMinimumCutVertices()) {
			katt.append(Integer.toString(i)).append("\n");
		}
	
		katt.flush();
		katt.close();
	}

}
