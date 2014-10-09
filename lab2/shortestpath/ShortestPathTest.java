import static org.junit.Assert.*;

import org.junit.Test;

/*
 * Authors: Per Classon, Joakim Uddholm
 */
public class ShortestPathTest {

	@Test
	public void test() {
		Graph g = new Graph(4);

		g.addEdge(0, 1, 2);
		g.addEdge(1, 2, 2);
		g.addEdge(3, 0, 2);

		int s = 3;

		Path p = ShortestPath.dijkstra(g, s);
		
		assertEquals(2, p.getDistance(0));
		assertEquals(4, p.getDistance(1));
		assertEquals(6, p.getDistance(2));
		assertEquals(0, p.getDistance(3));
	}
	
	@Test
	public void test2() {
		Graph g = new Graph(2);
		g.addEdge(0, 1, 100);
		int s = 0;
		Path p = ShortestPath.dijkstra(g, s);
		assertEquals(100, p.getDistance(1));
		assertEquals(0, p.getDistance(0));
	}
	

	@Test
	public void minTest() {
		Graph g = new Graph(3);

		int s = 0;
		
		g.addEdge(0, 1, 0);
		g.addEdge(1, 2, 0);
		g.addEdge(0, 2, 1);

		Path p = ShortestPath.dijkstra(g, s);
		
		System.out.println(p.getDistance(2));
	}

	
}
