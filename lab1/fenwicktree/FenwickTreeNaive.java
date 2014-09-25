public class FenwickTreeNaive {
	private int[] a;
	private int[] s;

	public FenwickTreeNaive(int N) {
		a = new int[N];
		s = new int[N + 1];
	}

	public void update(int i, int delta) {
		a[i] += delta;
		for (int j = i + 1; j < s.length; j++) {
			s[j] = s[j - 1] + a[j - 1];
		}
	}
	
	public void init(int[] a) {
		for (int i = 1; i <= a.length; i++) {
			s[i] = s[i - 1] + a[i - 1];
		}
	}

	public int query(int i) {
		return s[i];
	}

	public static void main(String[] args) {
		Kattio io = new Kattio(System.in, System.out);
		int n = io.getInt();
		FenwickTreeNaive tree = new FenwickTreeNaive(n);
		int q = io.getInt();
		for (int i = 0; i < q; i++) {
			String operation = io.getWord().trim();
			if (operation.equals("+")) {
				int in = io.getInt();
				int delta = io.getInt();
				tree.update(in, delta);
			}
			else {
				io.println(tree.query(io.getInt()));
			}
		}
		io.flush();
	}
}
