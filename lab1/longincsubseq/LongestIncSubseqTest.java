import static org.junit.Assert.*;

import java.util.Random;

import org.junit.Test;

public class LongestIncSubseqTest {

	@Test
	public void testKattis1() {
		Integer[] ints = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
		Integer[] answer = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9};
		Integer[] indices = LongestIncSubseq.solve(ints);
		assertEquals(answer, indices);
	}
	
	@Test
	public void testKattis2() {
		Integer[] ints = {1, 1, 1, 1, 1, 1, 1, 1, 1, 1};		
		Integer[] indices = LongestIncSubseq.solve(ints);
		assertEquals(1, indices.length);
		assertEquals(new Integer(1), ints[indices[0]]);
	}
	
	@Test
	public void testKattis3() {
		Integer[] ints = {5, 19, 5, 81, 50, 28, 29, 1, 83, 23};		
		Integer[] answer = {0, 1, 5, 6, 8};
		Integer[] indices = LongestIncSubseq.solve(ints);
		assertEquals(answer, indices);		
	}
	
	@Test
	public void testSingular() {
		Integer[] ints = {1};
		Integer[] answer = {0};
		Integer[] indices = LongestIncSubseq.solve(ints);
		assertEquals(answer, indices);	
	}
	
	@Test
	public void testEmpty() {
		Integer[] ints = {};
		Integer[] answer = {};
		Integer[] indices = LongestIncSubseq.solve(ints);
		assertEquals(answer, indices);	
	}
	
	@Test
	public void testLarge() {
		int size = 1000000;
		
		Integer[] ints = new Integer[size];
		Random rand = new Random();
		for(int i = 0; i < ints.length; ++i)
			ints[i] = rand.nextInt();
		LongestIncSubseq.solve(ints);		
	}
	

}
