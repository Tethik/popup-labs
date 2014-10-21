import static org.junit.Assert.*;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Random;

import org.junit.Ignore;
import org.junit.Test;


public class EulerTest {
	

	@Test
	public void testKattis() {
		Euler euler = new Euler(4);
		euler.addEdge(0, 1);
		euler.addEdge(1, 2);
		euler.addEdge(1, 3);
		euler.addEdge(2, 3);
		List<Integer> p =  euler.path();
		assertEquals(0, p.size());
		System.out.println(p);
		
		euler = new Euler(2);
		euler.addEdge(0, 1);
		euler.addEdge(1, 0);
		p =  euler.path();
		assertEquals(3, p.size());
		System.out.println(p);
		
		euler = new Euler(2);
		euler.addEdge(0, 1);		
		p =  euler.path();
		assertEquals(2, p.size());
		System.out.println(p);
	}
	
	@Test
	public void testComposite() {
		Euler euler = new Euler(4);
		euler.addEdge(0, 1);
		euler.addEdge(1, 0);
		euler.addEdge(2, 3);
		euler.addEdge(3, 2);
		
		assertEquals(0, euler.path().size());
	}
	
	@Test
	public void testCyclesAtStart() {
		Euler euler = new Euler(4);
		euler.addEdge(0, 1);
		euler.addEdge(1, 0);
		euler.addEdge(1, 2);
		euler.addEdge(2, 1);
		euler.addEdge(1, 3);
		List<Integer> p =  euler.path();
		System.out.println(p);
		assertEquals(6, p.size());
	}
	
	@Test
	public void testCycleAtEnd() {
		Euler euler = new Euler(6);
		euler.addEdge(0, 1);
		euler.addEdge(1, 2);
		euler.addEdge(2, 3);
		euler.addEdge(3, 4);
		euler.addEdge(4, 1);
		euler.addEdge(1, 5);
		euler.addEdge(5, 1);
		
		List<Integer> p =  euler.path();
		System.out.println(p);
		assertEquals(8, p.size());
	}
	
	@Test
	public void testNormalCycle() {
		Euler euler = new Euler(3);
		euler.addEdge(0, 1);
		euler.addEdge(1, 2);
		euler.addEdge(2, 0);
		List<Integer> p =  euler.path();
		System.out.println(p);
		assertEquals(4, p.size());
	}
	
	@Test
//	@Ignore
	public void benchmarkBigRandom() {
		int maxnodes = 10000;
		int maxedges = 500000;
		Euler euler = new Euler(maxnodes);
		Random random = new Random(1337);
		
		for(int i = 0; i < maxnodes-1; ++i) {
			euler.addEdge(i, i+1);
		}	
		euler.addEdge(maxnodes-1, 0);
		
		int prev = random.nextInt(maxnodes);		
		euler.addEdge(0, prev);
		for(int i = 0; i < maxedges; ++i) {
			int next = random.nextInt(maxnodes);
			euler.addEdge(prev, next);
			prev = next;
		}
		euler.addEdge(prev, 0);
		
		
		List<Integer> p =  euler.path();
		assertTrue(p.size() > 0);
	}
	
	@Test
//	@Ignore
	public void EveryThingCyclesToTheCenter() {
		int maxnodes = 10000;
		int maxedges = 500000;
		Euler euler = new Euler(maxnodes);
		Random random = new Random(1337);
		
		for(int i = 0; i < maxedges; ++i) {
			int n = random.nextInt(maxnodes);
			euler.addEdge(0, n);
			euler.addEdge(n, 0);
		}
		
		List<Integer> p =  euler.path();
		assertTrue(p.size() > 0);
	}
	
	@Test
	public void ImakeThePath() {
		int maxnodes = 30;
		int maxedges = maxnodes*maxnodes / 2;
		Random random = new Random();
		
		for(int j = 0; j < 1000; ++j) {
			int nEdges = 1 + random.nextInt(maxedges - 1);
			
			ArrayList<Edge> edges = new ArrayList<>();
			Euler euler = new Euler(maxnodes);
			int prev = random.nextInt(maxnodes);
			for(int i = 0; i < nEdges; ++i) {
				int next = random.nextInt(maxnodes);
				Edge edge = new Edge(prev, next);			
				edges.add(edge);
				prev = next;
			}
			
//			System.out.println("#");
			Collections.shuffle(edges);
			for(int i = 0; i < nEdges; ++i) {
				euler.addEdge(edges.get(i).from, edges.get(i).to);
			}
			
//			System.out.println(edges);
			
			List<Integer> path = euler.path();
//			System.out.println(path);
			prev = path.get(0);
			for(int i = 1; i < path.size(); ++i) {
				int next = path.get(i);
				assertTrue(edges.remove(new Edge(prev,next)));
				prev = next;
			}
		}
		
		
	}

}
