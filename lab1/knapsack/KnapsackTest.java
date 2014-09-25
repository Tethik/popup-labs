

import static org.junit.Assert.*;

import org.junit.Test;

public class KnapsackTest {

	@Test
	public void kattisTest() {
		Knapsack k = new Knapsack();
		int[] res;
		res = k.solve(5.3, new int[]{1, 10, 100}, new int[]{5, 5, 5});
		assertArrayEquals(res, new int[]{2});

		res = k.solve(6.1, new int[]{5, 4, 3, 2}, new int[]{4, 3, 2, 1});
		assertArrayEquals(res, new int[]{3, 2, 1});
		
		res = k.solve(0.0, new int[]{5, 4, 3, 2}, new int[]{4, 3, 2, 1});
		assertArrayEquals(res, new int[]{});
		
		res = k.solve(5.5, new int[]{}, new int[]{});
		assertArrayEquals(res, new int[]{});
	}
	

}
