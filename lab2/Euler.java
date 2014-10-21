import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;


/**
 * @author Joakimg Uddholm, Per Classon
 */
public class Euler {
	
	/**
	 * Double linked list. I need this custom to be able to have more than one iterator...
	 * @author 
	 */
	private static class Node {
		public Node next;
		public Node previous;
		
		public int value;		
		public Node(int value) {
			this.value = value;
		}
		public Node(int value, Node previous) {
			this(value);	
			this.previous = previous;
		}
		
		public Node(int value, Node previous, Node next) {
			this(value, previous);
			this.next = next;
		}
	}
	
	private static class NodeList {
		public Node start = null;
		public Node end = null;
		public int size = 0;
		
		public void add(int value) {
			if(end == null) { // empty
				end = start = new Node(value);
			} else {
				end = new Node(value, end);
			}
			size++;
		}
		
		public Node insert(Node before, int value) {
			Node _new = new Node(value, before, before.next);
			if(before.next != null) {
				before.next.previous = _new;
			} else {
				end = _new;
			}
			before.next = _new;
			size++;
			return _new;
		}
		
		public Node iterator() {
			return start;
		}
		
		public int[] toList() {
			int[] values = new int[size];
			
			Node it = start;
			int i = 0;
			while(it != null) {
				values[i++] = it.value;
				it = it.next;
			}
			
			return values;
		}
		
		@Override		
		public String toString() {
			StringBuilder builder = new StringBuilder();
			builder.append("[");
			Node it = start;
			int i = 0;
			while(it != null) {
				builder.append(it.value);
				builder.append(" ");
				it = it.next;
			}
			builder.append("]");
			return builder.toString();
		}
	}
	
	private Deque<Integer>[] adjacencyLists;	
	private int[] in;
	
	private int numberOfEdges;
	private int numberOfNodes;
	private int start;
	private int end;
	private int nonzero;
	
	@SuppressWarnings("unchecked")
	public Euler(int nodes) {
		this.numberOfNodes = nodes;
		this.in = new int[nodes];		
		
		adjacencyLists = new LinkedList[nodes];		
		for(int i = 0; i < nodes; ++i) {
			adjacencyLists[i] = new LinkedList<Integer>();
		}
	}
	
	public void addEdge(int from, int to) {
		adjacencyLists[from].add(to);		
		in[to]++;
		numberOfEdges++;
	}
	
	
	
	private boolean precondition() {
		// Precon: (from wiki)
		// A directed graph has an Eulerian trail if and only if at most one vertex
		// has (out-degree) − (in-degree) = 1, at most one vertex has (in-degree) − (out-degree) = 1,
		// every other vertex has equal in-degree and out-degree, 
		// and all of its vertices with nonzero degree belong to a single connected component of the underlying undirected graph.
		start = -1;
		end = -1;
		nonzero = -1;
		for(int i = 0; i < numberOfNodes; ++i) {
			int sum = adjacencyLists[i].size() - in[i];
			if(sum == 1) {
				if(start > -1)
					return false; 
				
				start = i;		
				continue;
			} else if(sum == -1) {
				if(end > -1)
					return false;
				
				end = i;
				continue;
			}
			
			if(Math.abs(sum) > 1) {				
				return false;
			}
			
			if(nonzero == -1 && adjacencyLists[i].size() > 0)
				nonzero = i;
			
		}		
		
		return (start == -1 && end == -1) || (start > -1 && end > -1);
	}
	
	/**
	 * If possible, returns a list containing a path of nodes visited. Otherwise an empty list.
	 * Each edge will be visited exactly once.
	 * @return
	 */
	public List<Integer> path() {
		NodeList path = new NodeList();
		LinkedList<Integer> empty = new LinkedList<Integer>();
				
		if(!precondition())
			return empty;
		
		start = start > -1 ? start : nonzero; 
				
		path.add(start);
		Node iterator = path.iterator();				
		
		while(iterator != null) {
			int current = iterator.value;	
			
			// Find cycle for current, append to iterator...
			Node insert = iterator;
			while(adjacencyLists[current].size() > 0) {
				int next = current;
				
				do {
					next = adjacencyLists[next].pollFirst();					
					insert = path.insert(insert, next);
				} while(next != current && adjacencyLists[next].size() > 0);
				
				if(next != current // found the end
						&& adjacencyLists[current].size() > 0) // but there are still edges from this node..
					insert = iterator;
			}	
			
			// All cycles handled, continue.
			iterator = iterator.next;
		}
		
		if(path.size <= numberOfEdges) // composites..
			return empty;
		
		List<Integer> ret = new ArrayList<Integer>(path.size);
			
		for(int v : path.toList())
			ret.add(v);
		
		return ret;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Kattio katt = new Kattio(System.in, System.out);
		while(true) {
			int n = katt.getInt();
			int m = katt.getInt();
			
			if(n == 0 && m == 0)
				break;
			
			Euler euler = new Euler(n);
			for(int i = 0; i < m; ++i) {
				euler.addEdge(katt.getInt(), katt.getInt());
			}
			
			List<Integer> path = euler.path();
			if(path.size() == 0) { 
				katt.append("Impossible\n");
			} else {
				for(int i = 0; i < path.size(); ++i) {
					katt.append(Integer.toString(path.get(i))).append(" ");
				}
				katt.append("\n");
			}
			katt.flush();			
		}
		katt.close();
	}

}
