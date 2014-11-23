package kmp;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class StringFinder {
	
	HashMap<String, int[]> kmp_tables = new HashMap<>();
	
	// O(n),  n = len(pattern)
	private void buildTable(String pattern) {
		int pos = 2;
		int cnd = 0;
		int[] table = new int[pattern.length()];
		table[0] = -1;
		kmp_tables.put(pattern, table);
		if(pattern.length() == 1)
			return;
		table[1] = 0;
		
		while(pos < pattern.length()) {			
			if(pattern.charAt(pos-1) == pattern.charAt(cnd)) {
				table[pos++] = ++cnd;
			} else if(cnd > 0) {
				cnd = table[cnd];
			} else {
				table[pos++] = 0;
			}
		}
	}
	
	public void doABCD(String patt) {
		buildTable(patt);
		for(int p : kmp_tables.get(patt))
			System.out.print(p + " ");
		System.out.println();
	}
	
	public int findSingle(String pattern, String text) {
		return findSingle(pattern, text, 0, 0);
	}

	// O(n), n = len(text)
	public int findSingle(String pattern, String text, int m, int i) {
		if(!kmp_tables.containsKey(pattern)) {
			buildTable(pattern);
		}
		int[] table = kmp_tables.get(pattern);
		
		while(m + i < text.length()) {
			if(pattern.charAt(i) == text.charAt(m + i)) {
				// Substring is matching... keep going.
				if(i++ == pattern.length() - 1)
					return m;				
			} else {
				// Go back.
				if(table[i] > -1) {
					m += i - table[i];
					i = table[i];
				} else {
					i = 0;
					m++;
				}
			}
		}
		
		return text.length();
	}
	
	public List<Integer> find(String pattern, String text) {
		List<Integer> positions = new ArrayList<>();
		if(!kmp_tables.containsKey(pattern))
			buildTable(pattern);
		int[] table = kmp_tables.get(pattern);
		int m = 0;
		int i = 0;
		int last = pattern.length() - 1;
		int lastmove = table[last] > 0 && pattern.charAt(last) == pattern.charAt(table[last]) 
				? table[last] : 0;
		while(m < text.length()) {
			int n = findSingle(pattern, text, m, i);
			if(n < text.length())
				positions.add(n);
			i = lastmove;
			m = n+1;
		}
		
		return positions;
	}

	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out));
		
		String pattern = null;
		while((pattern = reader.readLine()) != null) {
			if(pattern.length() == 0)
				break;
			String text = reader.readLine();		
			StringFinder finder = new StringFinder();
			List<Integer> ps = finder.find(pattern, text);
			for(int i = 0; i < ps.size(); ++i) {
				int p = ps.get(i);
				writer.write(Integer.toString(p));
				if(i < ps.size()-1)
					writer.write(' ');
			}
			writer.write('\n');	
		}
		writer.flush();
		writer.close();
	}
	
	

}
