import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Solves the Longest Incremenet Subsequence problem for any list of comparables.
 * @author Joakim Uddholm, Per Classon
 */
public class LongestIncSubseq {
	
	private static class IndexedItem<T extends Comparable<T>> implements Comparable<IndexedItem<T>> {
		public int index;
		public T item;
		public IndexedItem<T> previous;
		
		public IndexedItem(int index, T item) {
			this.index = index;
			this.item = item;
		}

		@Override
		public int compareTo(IndexedItem<T> paramT) {
			return this.item.compareTo(paramT.item);
		}
				
		@Override
		public String toString() {
			StringBuilder builder = new StringBuilder();
			builder.append("(").append(item).append(",").append(item).append(")");
			return builder.toString();
		}
	}
	
	@SuppressWarnings("unused")
	private static <T> String ListToString(List<T> objects) {
		StringBuilder builder = new StringBuilder();
		for(T o : objects)
			builder.append(o).append(" ");
		return builder.toString();
	}
	
	/**
	 * Given a list of objects return an index-list representing the longest incrementing subsequence.
	 * @param objects
	 * @return
	 */
	public static<T extends Comparable<T>> Integer[] solve(T objects[]) {
		if(objects.length == 0)
			return new Integer[] {};
		
		ArrayList<IndexedItem<T>> last = new ArrayList<IndexedItem<T>>();
		
		int index = 0;
		for(T a : objects) {	
			IndexedItem<T> v = new IndexedItem<T>(index++, a);			
			int insertionPoint = Collections.binarySearch(last, v);
			if(insertionPoint > -1) // v was found in the array already. We can skip.
				continue;			
			
			insertionPoint *= -1;
			insertionPoint--;
//			System.out.println(insertionPoint + " " + a);
//			System.out.println(ListToString(last));
//			System.out.println();
			
			// -(insertion point - 1)			
			if(insertionPoint < last.size())
			{
				if(v.compareTo(last.get(insertionPoint)) < 0) {
					if(insertionPoint > 0)
						v.previous = last.get(insertionPoint - 1);
					last.set(insertionPoint, v);
				}						
			} else {
				if(last.size() > 0)
					v.previous = last.get(last.size() - 1);				
				last.add(v);				
			}
		}
		
		Integer[] answer = new Integer[last.size()];
		int i = answer.length - 1;
		for(IndexedItem<T> item = last.get(last.size() - 1); item != null; item = item.previous)
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
