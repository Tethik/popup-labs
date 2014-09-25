import static org.junit.Assert.*;

import java.util.Random;

import org.junit.Test;

public class FenwickTreeTest {

	@Test
	public void largeTest() {
		int maxSize = 5000000;
		FenwickTree tree = new FenwickTree(maxSize);
		FenwickTreeNaive tree2 = new FenwickTreeNaive(maxSize);
		int[] a = new int[maxSize];
		Random random = new Random();

		for (int i = 0; i < maxSize; i++) {
			int bound = 1000000000;
			int val = bound;
			val *= (random.nextInt(2) == 0 ? -1 : 1);
			tree.update(i, val);
			a[i] = val;
		}

		tree2.init(a);

		for (int i = 0; i < maxSize; i++) {
			assertEquals(tree.query(i), tree2.query(i));
		}

	}

}
