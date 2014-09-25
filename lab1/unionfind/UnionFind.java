import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class UnionFind {
	
	int[] ranks;
	int[] tree;
	
	public UnionFind(int size) {
		if(size < 0)
			throw new IllegalArgumentException("Size must be greater than 0.");
		
		ranks = new int[size];
		tree = new int[size];
		
		for(int i = 0; i < size; ++i) {
			tree[i] = i;
		}
	}
	
	private int Find(int a) {
		int parent = tree[a];
		if(parent != a)
			tree[a] = Find(parent);
		return tree[a];
	}
	
	
	public void Union(int a, int b) {		
		int aRoot = Find(a);
		int bRoot = Find(b);
		
		int aRank = ranks[a];
		int bRank = ranks[b];
		
		if(aRoot == bRoot)
			return;
		
		if(aRank < bRank) {
			tree[aRoot] = bRoot;
		} else if(aRank > bRank) {
			tree[bRoot] = aRoot;
		} else {
			tree[bRoot] = aRoot;
			ranks[bRoot]++;
		}
	}
	
	
	public boolean Same(int a, int b) {
		return Find(a) == Find(b);						
	}	

	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out));
		
		String[] parts = reader.readLine().trim().split(" ");
		UnionFind u = new UnionFind(Integer.parseInt(parts[0]));
		int number_of_cmds = Integer.parseInt(parts[1]);
		
		for(int i = 0; i < number_of_cmds; ++i) {
			parts = reader.readLine().trim().split(" ");
			if(parts[0].equals("?")) {
				if(u.Same(Integer.parseInt(parts[1]), Integer.parseInt(parts[2])))
					writer.write("yes\n");
				else
					writer.write("no\n");
			} else {
				u.Union(Integer.parseInt(parts[1]), Integer.parseInt(parts[2]));
			}						
		}
		writer.flush();
	}

}
