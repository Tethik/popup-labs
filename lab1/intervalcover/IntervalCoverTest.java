import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;


public class InternalCover {
	
	public static class Interval implements Comparable<Interval>, Comparator<Interval> {		
		public double left;
		public double right;
		public int index;
		
		public Interval(int index, double left, double right) {
			this.index = index;
			this.left = left;
			this.right = right;
		}

		@Override
		public int compareTo(Interval o) {			
			return Double.compare(left, o.left);
		}

		@Override
		public int compare(Interval o1, Interval o2) {
			int cmp = o1.compareTo(o2);
//			System.out.println(o1 + " " + o2 + " = " + cmp);
			return cmp;
		}		
		
		@Override
		public boolean equals(Object obj) {
			Interval other = (Interval) obj;
			return other.left == left && other.right == right;
		}
		
		@Override		
		public String toString() {		
			return "(" + left + ", " + right + ")";
		}
	}
	
	
	private static int upper_bound(Interval[] intervals, int start, double left) {
		Interval cmp = new Interval(0, left, 0);
		
		int end = Arrays.binarySearch(intervals, start, intervals.length, cmp, cmp);
		if(end < 0) {
			// Arrays.binarySearch returns -(insertion point - 1) if key is not found.
			end *= -1;			
		} else {
			end += 1;
		}
		return end;
	}
	
	/**
	 * 
	 * @param goal
	 * @param intervals
	 * @return
	 */
	public static Interval[] solve(Interval goal, Interval[] intervals) {
		if(intervals.length == 0)
			return new Interval[] {};
		
		Arrays.sort(intervals);
//		for(Interval i : intervals)
//			System.out.println(i.left + " " + i.right);
//		System.out.println();
		
		ArrayList<Interval> answer = new ArrayList<>();
		
		double L = goal.left;
		double R = goal.right;
		
		assert(L <= R);
		
		int besti = 0;
		
		while(answer.size() == 0 || L < R) {
			int end = upper_bound(intervals, besti, L); 
			
//			System.out.println("Binary search for: " + L + " returns " + end);
			boolean set = false;
			for(int i = besti; i < end && i < intervals.length; i++) {				
				if(intervals[i].right > intervals[besti].right && intervals[i].left <= L || !set) {
					set = true;
					besti = i;
				}
			}
			
//			System.out.println(intervals[besti]);
			
			if(!set || (intervals[besti].right <= L && intervals[besti].right < R) || intervals[besti].left > L)
				return new Interval[] {};
			
			answer.add(intervals[besti]);
			L = intervals[besti].right;
		}

		return answer.toArray(new Interval[answer.size()]);
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

		try {
			for(;;) {
				String line = reader.readLine();
				if(line == null || line.trim() == "")
					return;
				String[] parts = line.split(" ");
				double left = Double.parseDouble(parts[0]);
				double right = Double.parseDouble(parts[1]);
				Interval goal = new Interval(0, left, right);
				
				int howmany = Integer.parseInt(reader.readLine());
				Interval[] intervals = new Interval[howmany];
				for(int i = 0; i < howmany; ++i) {
					parts = reader.readLine().split(" ");
					left = Double.parseDouble(parts[0]);
					right = Double.parseDouble(parts[1]);
					intervals[i] = new Interval(i, left, right);
				}
				
				Interval[] solution = solve(goal, intervals);
				if(solution.length > 0) {
					System.out.println(solution.length);
					for(Interval s : solution) {
						System.out.print(s.index);
						System.out.print(" ");
					}
					System.out.println();
				} else {
					System.out.println("impossible");
				}				
			}			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

}
