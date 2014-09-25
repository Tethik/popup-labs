import static org.junit.Assert.*;

import java.util.Random;

import org.junit.Test;


public class UnionFindTest {

	@Test
	public void testKattis() {
		UnionFind f = new UnionFind(10);
		assertEquals(false, f.Same(1, 3));
		f.Union(1, 8);
		f.Union(3, 8);		
		assertEquals(true, f.Same(1, 3));
	}
	
	@Test
	public void testKattis2() {
		UnionFind f = new UnionFind(4);
		assertEquals(true, f.Same(0, 0));
		f.Union(0, 1);
		f.Union(1, 2);
		f.Union(0, 2);		
		assertEquals(false, f.Same(0, 3));
	}
	
	@Test
	public void testPerformance() {
		int max_items = 1000000;
		UnionFind f = new UnionFind(max_items);
		Random random = new Random();		
		
		for(int i = 0; i < 1000000; ++i) {
			f.Union(random.nextInt(max_items), random.nextInt(max_items));
			f.Same(random.nextInt(max_items), random.nextInt(max_items));
		}	
	}
	
	@Test
	public void testBigUnionsPerformance() {
		int max_items = 1000000;
		UnionFind f = new UnionFind(max_items);
		for(int i = 0; i < max_items / 2; ++i) {
			f.Union(i, i + 1);
		}
		
		for(int i = max_items / 2; i < max_items - 1; ++i) {
			f.Union(i, i + 1);
		}
		
		f.Union(0, max_items-1);
	}

}
