import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import org.junit.Test;

public class MinSpanTreeTest {

	@Test
	public void testKattis() {
		MinSpanTree tree = new MinSpanTree(4);
		tree.add(new MinSpanTree.Edge(0,1,1));
		tree.add(new MinSpanTree.Edge(1,2,1));
		tree.add(new MinSpanTree.Edge(1,3,3));
		tree.add(new MinSpanTree.Edge(2,3,0));
		
		MinSpanTree.Edge[] solution = new MinSpanTree.Edge[] {
			new MinSpanTree.Edge(0,1,1),
			new MinSpanTree.Edge(1,2,1),
			new MinSpanTree.Edge(2,3,0)
		};
		assertArrayEquals(solution, tree.solve());
	}
	
	@Test
	public void testKattis2() {
		MinSpanTree tree = new MinSpanTree(2);
		tree.add(new MinSpanTree.Edge(0,1,100));
		
		MinSpanTree.Edge[] solution = new MinSpanTree.Edge[] {
			new MinSpanTree.Edge(0,1,100)
		};
		assertArrayEquals(solution, tree.solve());
	}

	
	@Test
	public void testKattis3() {
		MinSpanTree tree = new MinSpanTree(3);
		assertNull(tree.solve());
	}
	
	
	@Test
	public void benchmarkLarge() {
		Random random = new Random(1337);
		for(int j = 0; j < 10; j++) {
			int n = 20000;
			int e = 30000;
			MinSpanTree tree = new MinSpanTree(n);
			
			for(int i = 0; i < e; ++i) {
				int y = 1 + random.nextInt(n-1);
				int x = random.nextInt(y);
				int w = random.nextInt(n);
				tree.add(new MinSpanTree.Edge(x,y,w));
			}
			tree.solve();
		}
	}
	
	@Test
	public void benchmarkForest() {	
		Random random = new Random(1337);
		for(int j = 0; j < 10; j++) {
			int n = 20000;
			MinSpanTree tree = new MinSpanTree(n);
			
			for(int i = 1; i < n; ++i) {
				int y = i;
				int x = i-1;
				int w = 0;
				tree.add(new MinSpanTree.Edge(x,y,w));
			}
			
			for(int i = 0; i < 10000; ++i) {
				int y = 1 + random.nextInt(n-1);
				int x = random.nextInt(y);
				int w = 1 + random.nextInt(n);
				tree.add(new MinSpanTree.Edge(x,y,w));
			}
			
			MinSpanTree.Edge[] edges = tree.solve();
			
			for(int i = 1; i < n; ++i) {
				int y = i;
				int x = i-1;
				int w = 0;
				MinSpanTree.Edge e = edges[i-1];
				assertEquals(e.x, x);
				assertEquals(e.y, y);
				assertEquals(e.w, w);
			}
			
			int cost = 0;
			for(MinSpanTree.Edge e : edges) {
				cost += e.w;
			}
			
			assertEquals(0, cost);
		}
	}
	
	@Test
	public void testBasicReverse() {
		MinSpanTree tree = new MinSpanTree(2);
		tree.add(new MinSpanTree.Edge(1, 0, 3));
		MinSpanTree.Edge[] solution = new MinSpanTree.Edge[] {
			new MinSpanTree.Edge(0, 1, 3)
		};
		assertArrayEquals(solution, tree.solve());
	}
	
	@Test
	public void testMoreFlippingShit() {
		MinSpanTree tree = new MinSpanTree(3);
		tree.add(new MinSpanTree.Edge(1, 0, 3));
		tree.add(new MinSpanTree.Edge(0, 2, -1));
		tree.add(new MinSpanTree.Edge(1, 2, 1337));
		MinSpanTree.Edge[] solution = new MinSpanTree.Edge[] {			
			new MinSpanTree.Edge(0, 1, 3),
			new MinSpanTree.Edge(0, 2, -1),
		};
		assertArrayEquals(solution, tree.solve());
	}
	
	@Test
	public void testSinglePoint() {
		MinSpanTree tree = new MinSpanTree(1);
		tree.add(new MinSpanTree.Edge(0, 0, 3));
		assertArrayEquals(new MinSpanTree.Edge[0], tree.solve());		
	}
	
	@Test
	public void testLexigraphical() {
		int n = 100000;
		ArrayList<Integer> values = new ArrayList<>();
		for(int i = 1; i < n; ++i)
			values.add(i);
		
		Random random = new Random();
		Collections.shuffle(values);
		MinSpanTree tree = new MinSpanTree(n);
		for(int i = 0; i < n-1; ++i)
			tree.add(new MinSpanTree.Edge(0, values.get(i), random.nextInt()));

		MinSpanTree.Edge[] edges = tree.solve();
		
		for(int i = 1; i < edges.length; ++i) {
			assertEquals(0, edges[i].x);
			assertTrue(edges[i].y > edges[i-1].y);
		}
	}
	
	@Test
	public void test() {
		
	}
	
	
	
	

}
