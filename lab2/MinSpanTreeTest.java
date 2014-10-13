import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import org.junit.Test;

public class MinSpanTreeTest {

	@Test
	public void testKattis() {
		UndirectedGraph graph = new UndirectedGraph(4);
		graph.addEdge(new Edge(0,1,1));
		graph.addEdge(new Edge(1,2,1));
		graph.addEdge(new Edge(1,3,3));
		graph.addEdge(new Edge(2,3,0));
		
		Edge[] solution = new Edge[] {
			new Edge(0,1,1),
			new Edge(1,2,1),
			new Edge(2,3,0)
		};
		assertArrayEquals(solution, MinSpanTree.solve(graph));
	}
	
	@Test
	public void testKattis2() {		
		UndirectedGraph graph = new UndirectedGraph(2);
		graph.addEdge(new Edge(0,1,100));
		
		Edge[] solution = new Edge[] {
			new Edge(0,1,100)
		};
		assertArrayEquals(solution, MinSpanTree.solve(graph));
	}

	
	@Test
	public void testKattis3() {
		UndirectedGraph graph = new UndirectedGraph(3);		
		assertNull(MinSpanTree.solve(graph));
	}
	
	
	@Test
	public void benchmarkLarge() {
		Random random = new Random(1337);
		for(int j = 0; j < 10; j++) {
			int n = 20000;
			int e = 30000;
			UndirectedGraph graph = new UndirectedGraph(n);			
			
			for(int i = 0; i < e; ++i) {
				int y = 1 + random.nextInt(n-1);
				int x = random.nextInt(y);
				int w = random.nextInt(n);
				graph.addEdge(new Edge(x,y,w));
			}
			MinSpanTree.solve(graph);
		}
	}
	
	@Test
	public void benchmarkForest() {	
		Random random = new Random(1337);
		for(int j = 0; j < 10; j++) {
			int n = 20000;
			UndirectedGraph graph = new UndirectedGraph(n);	
			
			for(int i = 1; i < n; ++i) {
				int y = i;
				int x = i-1;
				int w = 0;
				graph.addEdge(new Edge(x,y,w));
			}
			
			for(int i = 0; i < 10000; ++i) {
				int y = 1 + random.nextInt(n-1);
				int x = random.nextInt(y);
				int w = 1 + random.nextInt(n);
				graph.addEdge(new Edge(x,y,w));
			}
			
			Edge[] edges = MinSpanTree.solve(graph);
			
			for(int i = 1; i < n; ++i) {
				int y = i;
				int x = i-1;
				int w = 0;
				Edge e = edges[i-1];
				assertEquals(e.x, x);
				assertEquals(e.y, y);
				assertEquals(e.weight, w);
			}
			
			int cost = 0;
			for(Edge e : edges) {
				cost += e.weight;
			}
			
			assertEquals(0, cost);
		}
	}
	
	@Test
	public void testBasicReverse() {
		UndirectedGraph graph = new UndirectedGraph(2);
		graph.addEdge(new Edge(1, 0, 3));
		Edge[] solution = new Edge[] {
			new Edge(0, 1, 3)
		};
		assertArrayEquals(solution, MinSpanTree.solve(graph));
	}
	
	@Test
	public void testMoreFlippingShit() {
		UndirectedGraph graph = new UndirectedGraph(3);
		graph.addEdge(new Edge(1, 0, 3));
		graph.addEdge(new Edge(0, 2, -1));
		graph.addEdge(new Edge(1, 2, 1337));
		Edge[] solution = new Edge[] {			
			new Edge(0, 1, 3),
			new Edge(0, 2, -1),
		};
		assertArrayEquals(solution, MinSpanTree.solve(graph));
	}
	
	@Test
	public void testSinglePoint() {
		UndirectedGraph graph = new UndirectedGraph(1);
		graph.addEdge(new Edge(0, 0, 3));
		assertArrayEquals(new Edge[0], MinSpanTree.solve(graph));		
	}
	
	@Test
	public void testLexigraphical() {
		int n = 100000;
		ArrayList<Integer> values = new ArrayList<>();
		for(int i = 1; i < n; ++i)
			values.add(i);
		
		Random random = new Random();
		Collections.shuffle(values);
		UndirectedGraph graph = new UndirectedGraph(n);
		for(int i = 0; i < n-1; ++i)
			graph.addEdge(new Edge(0, values.get(i), random.nextInt()));

		Edge[] edges = MinSpanTree.solve(graph);
		
		for(int i = 1; i < edges.length; ++i) {
			assertEquals(0, edges[i].x);
			assertTrue(edges[i].y > edges[i-1].y);
		}
	}
}
