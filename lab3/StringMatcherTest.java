import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import org.junit.Assert;
import org.junit.Test;

public class StringMatcherTest {

	@Test
	public void testFind() {
		StringBuilder builder = new StringBuilder();
		StringBuilder pbuilder = new StringBuilder();
		for (int i = 0; i < 200000; ++i) {
			builder.append('a');
		}

		for (int i = 0; i < 100000; ++i) {
			pbuilder.append('a');
		}

		StringMatcher matcher = new StringMatcher(new HashMap<String, int[]>());
		matcher.findPositions(pbuilder.toString().toCharArray(), builder.toString().toCharArray());
	}

	@Test
	public void testTable() {
		StringMatcher matcher = new StringMatcher(null);
		int[] table = matcher.computePrefixTable("abcabdabc".toCharArray());
		Assert.assertArrayEquals(new int[] { -1, 0, 0, 0, 1, 2, 0, 1, 2, 3 }, table);
	}

	@Test
	public void kattisTest() {
		StringMatcher matcher = new StringMatcher(new HashMap<String, int[]>());
		Assert.assertEquals(new ArrayList<Integer>(Arrays.asList(2, 4)), matcher.find("p", "Popup"));
		Assert.assertEquals(new ArrayList<Integer>(), matcher.find("helo", "Hello there!"));
		Assert.assertEquals(new ArrayList<Integer>(Arrays.asList(5)),
				matcher.find("peek a boo", "you speek a bootiful language"));
		Assert.assertEquals(new ArrayList<Integer>(Arrays.asList(7)), matcher.find("anas", "bananananaspaj"));
	}

	@Test
	public void testShort() {
		StringMatcher matcher = new StringMatcher(new HashMap<String, int[]>());
		Assert.assertEquals(new ArrayList<Integer>(Arrays.asList(0, 1, 2, 3, 4)), matcher.find("A", "AAAAA"));
		Assert.assertEquals(new ArrayList<Integer>(Arrays.asList(0, 1, 2, 3)), matcher.find("AA", "AAAAA"));
		Assert.assertEquals(new ArrayList<Integer>(Arrays.asList(0, 1, 2)), matcher.find("AAA", "AAAAA"));
		Assert.assertEquals(new ArrayList<Integer>(Arrays.asList(0, 1)), matcher.find("AAAA", "AAAAA"));
		Assert.assertEquals(new ArrayList<Integer>(Arrays.asList(0)), matcher.find("A", "A"));
		Assert.assertEquals(new ArrayList<Integer>(Arrays.asList(0, 1, 2)), matcher.find(" ", "   "));
		Assert.assertEquals(new ArrayList<Integer>(Arrays.asList(0, 1, 2)), matcher.find(" ", "   "));
		Assert.assertEquals(new ArrayList<Integer>(), matcher.find("a", "bb"));
	}

	@Test
	public void rando() {
		StringMatcher finder = new StringMatcher(new HashMap<String, int[]>());
		Random rand = new Random();

		for (int i = 0; i < 100; ++i) {
			int pattlength = 2 + rand.nextInt(10);
			StringBuilder pattern = new StringBuilder();
			StringBuilder text = new StringBuilder();

			int textlength = 10 + rand.nextInt(300);
			for (int j = 0; j < pattlength; ++j) {
				pattern.append((char) ('A' + rand.nextInt(2)));
			}

			for (int j = 0; j < textlength; ++j) {
				text.append((char) ('A' + rand.nextInt(2)));
			}

			String pat = pattern.toString();
			String te = text.toString();
			List<Integer> sol = finder.find(pat, te);
			System.out.println(pat);
			System.out.println(te);
			for (int p : sol) {
				System.out.println(p);
				String substr = te.substring(p, p + pat.length());
				assertEquals(pat, substr);
			}
			System.out.println();

		}

	}
}
