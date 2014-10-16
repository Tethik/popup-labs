import static org.junit.Assert.*;

import org.junit.Test;


public class MaxFlowTest {

	@Test
	public void testFindPath() {		
		MaxFlow flowa = new MaxFlow(3);
		flowa.addEdge(0, 1, 1);
		flowa.addEdge(1, 2, 3);
		flowa.solve(0, 2);
		
		System.out.println(flowa.getGraph());
	}

}
