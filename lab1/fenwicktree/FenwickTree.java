/**
 * @author Per Classon pclasson@kth.se, Joakim Uddholm juddholm@kth.se
 */
public class FenwickTree {
	private long[] fenwickTree;

	public FenwickTree(int arrayLength) {
		fenwickTree = new long[arrayLength + 1];
	}
	
	/**
	 * Updates element i in the prefix sum.
	 * @param i The element to update.
	 * @param delta The change of value.
	 */
	public void update(int i, long delta) {
		int inx = i + 1;
		assert inx < (fenwickTree.length - 1);
		assert inx >= 0;
		while (inx < fenwickTree.length) {
			fenwickTree[inx] += delta;
			inx += (inx & -inx);
		}
	}

	/**
	 * Gets the sum of elements 0 to i in our prefix sum.
	 * @param i The upper limit index.
	 * @return The sum.
	 */
	public long query(int i) {
		assert i >= 0;
		assert i <= fenwickTree.length;
		long sum = 0;
		while (i > 0) {
			sum += fenwickTree[i];
			i -= (i & -i);
		}
		return sum;
	}
	
	public static void main(String[] args) {
		Kattio io = new Kattio(System.in, System.out);
		int n = io.getInt();
		FenwickTree tree = new FenwickTree(n);
		long q = io.getLong();
		for (long i = 0; i < q; i++) {
			String operation = io.getWord().trim();
			if (operation.equals("+")) {
				int in = io.getInt();
				long delta = io.getLong();
				tree.update(in, delta);
			}
			else {
				io.println(tree.query(io.getInt()));
			}
		}
		io.flush();
		io.close();
	}
}
