import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 
 * Class to match patterns in strings.
 * 
 * @author Per Classon
 * @author Joakim Uddholm
 *
 */
public class StringMatcher {
	private HashMap<String, int[]> tables;

	public StringMatcher(HashMap<String, int[]> tables) {
		this.tables = tables;
	}

	/**
	 * Compute prefix table
	 * 
	 * @param pattern
	 * @return table
	 */
	int[] computePrefixTable(char[] pattern) {
		int[] table = new int[pattern.length + 1];
		int i = 2;
		int j = 0;
		table[0] = -1;
		table[1] = 0;
		while (i < table.length) {
			if (pattern[i - 1] == pattern[j]) {
				table[i++] = ++j;
			} else if (j > 0) {
				j = table[j];
			} else {
				table[i++] = 0;
				j = 0;
			}
		}
		return table;
	}

	/**
	 * Find pattern indexes in text.
	 * 
	 * @param pattern
	 * @param text
	 * @param table
	 * @return list of indexes
	 */
	List<Integer> find(char[] pattern, char[] text, int[] table) {
		List<Integer> positions = new ArrayList<>();
		int i = 0;
		int j = 0;
		while (i < text.length) {
			while (j > -1 && text[i] != pattern[j]) {
				j = table[j];
			}
			i++;
			j++;

			if (j == pattern.length) {
				positions.add(i - pattern.length);
				j = table[j];
			}
		}

		return positions;
	}

	/**
	 * Find pattern indexes in text.
	 * 
	 * @param pattern
	 * @param text
	 * @return list of indexes
	 */
	List<Integer> findPositions(char[] pattern, char[] text) {
		if (pattern.length == 1) {
			return findCharInText(pattern[0], text);
		}
		String patternString = pattern.toString();
		int[] table = null;
		if (tables.containsKey(patternString)) {
			table = tables.get(patternString);
		} else {
			table = computePrefixTable(pattern);
			tables.put(patternString, table);
		}
		return find(pattern, text, table);
	}

	/**
	 * Find indexes of char in text.
	 * 
	 * @param c
	 * @param text
	 * @return list of indexes
	 */
	private List<Integer> findCharInText(char c, char[] text) {
		List<Integer> positions = new ArrayList<Integer>();
		for (int i = 0; i < text.length; i++) {
			if (text[i] == c) {
				positions.add(i);
			}
		}
		return positions;
	}

	public static void main(String[] args) throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out));

		String line = null;
		while ((line = reader.readLine()) != null) {
			if (line.length() == 0)
				break;
			StringMatcher matcher = new StringMatcher(new HashMap<String, int[]>());
			char[] pattern = line.toCharArray();
			char[] text = reader.readLine().toCharArray();
			List<Integer> positions = matcher.findPositions(pattern, text);
			StringBuilder sb = new StringBuilder();
			for (Integer p : positions) {
				sb.append(p).append(' ');
			}
			writer.write(sb.toString().trim());
			writer.write('\n');
		}
		writer.flush();
		writer.close();
	}

	public List<Integer> find(String pattern, String text) {
		return findPositions(pattern.toCharArray(), text.toCharArray());
	}
}
