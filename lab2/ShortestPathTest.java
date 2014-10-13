import static org.junit.Assert.*;

import java.util.Random;

import org.junit.Test;

/**
 * Authors: Per Classon, Joakim Uddholm
 */
public class ShortestPathTest {

	@Test
	public void test() {
		AdjacencyListGraph g = new AdjacencyListGraph(4);

		g.addEdge(new Edge(0, 1, 2));
		g.addEdge(new Edge(1, 2, 2));
		g.addEdge(new Edge(3, 0, 2));

		int s = 3;

		Path p = ShortestPath.dijkstra(g, s);
		
		assertEquals(2, p.getDistance(0));
		assertEquals(4, p.getDistance(1));
		assertEquals(6, p.getDistance(2));
		assertEquals(0, p.getDistance(3));
		
		s = 0;

		p = ShortestPath.dijkstra(g, s);
		
		assertEquals(0, p.getDistance(0));
		assertEquals(2, p.getDistance(1));
		assertEquals(4, p.getDistance(2));
		assertEquals(Long.MAX_VALUE, p.getDistance(3));
	}
	
	@Test
	public void test2() {
		AdjacencyListGraph g = new AdjacencyListGraph(2);
		g.addEdge(new Edge(0, 1, 100));
		int s = 0;
		Path p = ShortestPath.dijkstra(g, s);
		assertEquals(100, p.getDistance(1));
		assertEquals(0, p.getDistance(0));
		
		s = 1;
		p = ShortestPath.dijkstra(g, s);
		assertEquals(0, p.getDistance(1));
		assertEquals(Long.MAX_VALUE, p.getDistance(0));
	}
	

	@Test
	public void minTest() {
		AdjacencyListGraph g = new AdjacencyListGraph(3);

		int s = 0;
		
		g.addEdge(new Edge(0, 1, 0));
		g.addEdge(new Edge(1, 2, 0));
		g.addEdge(new Edge(0, 2, 1));

		Path p = ShortestPath.dijkstra(g, s);
		
		assertEquals(0, p.getDistance(2));
	}
	
	@Test
	public void testSingular() {
		AdjacencyListGraph g = new AdjacencyListGraph(1);
		Path p = ShortestPath.dijkstra(g, 0);		
		assertEquals(0, p.getDistance(0));
	}
	
	@Test
	public void benchmarkHuge() {
		// Worst case parameters on kattis.
		int n = 10000;
		int e = 30000;
		int q = 100;
		int s = 0;
		
		Random random = new Random(1337);
		AdjacencyListGraph g = new AdjacencyListGraph(n);
		for(int i = 0; i < e; ++i) {
			int weight = random.nextInt(100);
			int u = random.nextInt(n);
			int v = random.nextInt(n);
			g.addEdge(new Edge(u, v, weight));
		}
		
		Path p = ShortestPath.dijkstra(g, s);
		
		for(int i = 0; i < q; ++i) {
			p.getPath(q);
		}
		
	}

	
}
