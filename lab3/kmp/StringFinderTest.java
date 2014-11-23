package kmp;

import static org.junit.Assert.*;

import java.util.List;
import java.util.Random;

import org.junit.Ignore;
import org.junit.Test;

public class StringFinderTest {

	@Test
	public void testFind() {
		StringBuilder builder = new StringBuilder();
		StringBuilder pbuilder = new StringBuilder();
		for(int i = 0; i < 200000; ++i) {
			builder.append('a');
		}
		
		for(int i = 0; i < 100000; ++i) {
			pbuilder.append('a');
		}
		
		StringFinder finder = new StringFinder();
		finder.find(pbuilder.toString(), builder.toString());
	}
	
	@Test
	public void doAbc() {
		StringFinder finder = new StringFinder();
		finder.doABCD("aaaa");
		finder.doABCD("abcd");
		finder.doABCD("abcdabd");
		finder.doABCD("aalalal");
		finder.doABCD("lalblcl");
		finder.doABCD("aabab");
	}
	
	@Ignore
	@Test
	public void rando() {
		StringFinder finder = new StringFinder();
		Random rand = new Random();
		
		for(int i = 0; i < 100000; ++i) {
			int pattlength = 1 + rand.nextInt(10);
			StringBuilder pattern = new StringBuilder();
			StringBuilder text = new StringBuilder();
			
			int textlength = 10 + rand.nextInt(300);
			for(int j = 0; j < pattlength; ++j) {
				pattern.append((char) ('A' + rand.nextInt(2)));
			}
			
			for(int j = 0; j < textlength; ++j) {
				text.append((char) ('A' + rand.nextInt(2)));
			}
			
			String pat = pattern.toString();
			String te = text.toString();
			List<Integer> sol = finder.find(pat, te);
			System.out.println(pat);
			System.out.println(te);
			for(int p : sol) {
				System.out.println(p);
				String substr = te.substring(p, p+pat.length());
				assertEquals(pat, substr);
			}
			System.out.println();
			
		}
		
	}

}
