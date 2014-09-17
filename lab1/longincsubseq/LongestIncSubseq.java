import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;


public class LongestIncSubseq {
	
//	public static <T extends Comparable<? super T> > int[] solve(T objects[]) {
//		int[] indices = new int[objects.length];
//		Arrays.fill(indices, -1);
//		ArrayList<T> cmps = new ArrayList<T>();
//		
//		
////		for(int i = 0; i < objects.length; ++i)
////			last.add(null); // se till att det finns för varje index
//		int maxlen = 1;
//		
//		for(T a : objects) {
//			int insertionPoint = Arrays.binarySearch(cmps.To, 0, maxlen, a);
//		}
//		
//		return Arrays.copyOf(indices, maxlen);
//	}
	
	private static class IndexedItem implements Comparable<IndexedItem> {
		public int index;
		public int item;
		public IndexedItem previous;
		
		public IndexedItem(int index, int item) {
			this.index = index;
			this.item = item;
		}

		@Override
		public int compareTo(IndexedItem paramT) {
			return (this.item < paramT.item) ? -1 : (this.item > paramT.item) ? 1 : 0;
		}
				
		@Override
		public String toString() {
			StringBuilder builder = new StringBuilder();
			builder.append("(").append(item).append(",").append(item).append(")");
			return builder.toString();
		}
		
		
	}
	
	private static <T> String ListToString(List<T> objects) {
		StringBuilder builder = new StringBuilder();
		for(T o : objects)
			builder.append(o).append(" ");
		return builder.toString();
	}
	
	public static Integer[] solve(Integer objects[]) {
		if(objects.length == 0)
			return new Integer[] {};
		
		ArrayList<IndexedItem> last = new ArrayList<IndexedItem>();
		
		int maxlen = 0;
		int index = 0;
		for(Integer a : objects) {	
			IndexedItem v = new IndexedItem(index, a);
			index++;
			int insertionPoint = Collections.binarySearch(last, v);
			if(insertionPoint > -1)
				continue;
			
			
			insertionPoint *= -1;
			insertionPoint--;
//			System.out.println(insertionPoint + " " + a);
//			System.out.println(ListToString(last));
//			System.out.println();
			
			// -(insertion point - 1)			
			if(insertionPoint >= last.size())
			{
				if(last.size() > 0)
					v.previous = last.get(last.size() - 1);				
				last.add(v);							
			} else {
				if(v.compareTo(last.get(insertionPoint)) == -1) {
					if(insertionPoint > 0)
						v.previous = last.get(insertionPoint - 1);
					last.set(insertionPoint, v);
				}
			}
		}
		
		Integer[] answer = new Integer[last.size()];
		int i = answer.length - 1;
		for(IndexedItem item = last.get(last.size() - 1); item != null; item = item.previous)
			answer[i--] = item.index;
		
		return answer;
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		
		String line = reader.readLine();
		while(line != null && line.trim() != "") {
			int size = Integer.parseInt(line);
			Integer[] objects = new Integer[size];
			line = reader.readLine().trim();
			String[] parts = line.split(" ");
			for(int i = 0; i < size; ++i) 
			{	
				objects[i] = Integer.parseInt(parts[i]);
			}
			
			Integer[] answer = solve(objects);
			System.out.println(answer.length);
			if(answer.length > 0) {
				for(int i : answer)
					System.out.print(i + " ");
				System.out.println();
			}
			line = reader.readLine();
		}
	}

}
