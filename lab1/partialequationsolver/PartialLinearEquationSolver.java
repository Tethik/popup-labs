import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;

/**
 * Solves Linear Equations with one unique solution using Gaussian Elimination.
 * @author Joakim Uddholm, Per Classon
 *
 */
public class PartialLinearEquationSolver {
	
	/**
	 * Solution class to represent an answer from the solver. Contains two booleans, inconsistent and multiple to
	 * show error codes. 
	 */
	public static class Solution {
		public double[] x;
		public int[] known;
		public boolean inconsistent = false;
		public boolean multiple = false;
	}

	
	/**
	 * Check so that each row of A is linearly independent.
	 * @param A
	 * @return
	 */
//	public static boolean IsConsistent(double[][] A) {
//		if(A.length == 1)
//			return true;
//		
//		rowloop:
//		for(int r = 1; r < A.length; ++r) {
//			double val = A[r][0] / A[0][0];
//			
//			for(int c = 0; c < A[r].length; ++c) {
//				double d = A[r][c] - val*A[0][c];
//				if(!ÄrJuTypNoll(d))
//					continue rowloop;
//			}
//			
//			return false;
//		}		
//		
//		return true;
//	}
	
	/**
	 * Input of the form Ax = b, where A is a n*n matrix and b a n*1 vector.
	 * Returns a solution object.
	 * @param A
	 * @param b
	 * @return
	 */
	public static Solution solve(double[][] A, double[] b) {
		Solution solution = new Solution();		
		double[] x = Arrays.copyOf(b, b.length);	
		int[] bindex = new int[b.length];
		for(int i = 0; i < b.length; ++i) {
			bindex[i] = i;
		}
		
		if(A.length <= 0  || A[0].length != b.length)
			throw new IllegalArgumentException();
		
		for(int j = 0, i = 0; j < A[0].length; ++j, ++i) {
			
//			System.out.println("***********************");
//			System.out.println("BEFORE " + i);
//			printMatrix(A);		
//			System.out.println();
//			printRow(x);
//			
			int swap = i;
			while(swap < A.length && A[swap][j] < 0.000001 && A[swap][j] > -0.000001) {
				swap++;
			}
			if(swap == A.length) {	
				continue;
			} else if(swap > i) {
				double[] tmp = A[swap];
				double bval = x[swap];
				int bin = bindex[swap];
				A[swap] = A[i];
				A[i] = tmp;
				x[swap] = x[i];
				x[i] = bval;
				bindex[swap] = bindex[i];
				bindex[i] = bin;				
			}
		
			// Divide row i by Aij
			double aij = A[i][j];
			x[i] /= aij;			
			for(int l = 0; l < A[i].length; ++l)
				A[i][l] /= aij;			
			A[i][j] = 1;
			
//			System.out.println();
			// Subtract pivot row from other rows.
			for(int r = 0; r < A.length; ++r) {
				if(r == i)
					continue;
				
				double m = A[r][j];
				x[r] -= m*x[i];
				if(x[r] < 0.000001 && x[r] > -0.000001)
					x[r] = 0;
				for(int c = 0; c < A[r].length; ++c) {
					A[r][c] -= m*A[i][c];
					if(A[r][c] < 0.000001 && A[r][c] > -0.000001)
						A[r][c] = 0;
				}
			}
			
//			System.out.println("AFTER");
//			printMatrix(A);		
//			System.out.println();
//			printRow(x);
//			System.out.println("***********************");
		}
		
		// Check for inconsistencies.
		HashSet<Integer> badrows = new HashSet<Integer>();
		for(int r = 0; r < A.length; ++r) {
			boolean zero = true;			
			for(int c = 0; c < A[r].length; ++c) {
				zero &= (A[r][c] < 0.001 && A[r][c] > -0.001);
			}
			
			if(zero) {
				badrows.add(r);
				if(x[r] < 0.001 && x[r] > -0.001) { // b_i == 0
					solution.multiple = true;
				}
				else {
					solution.inconsistent = true;
					return solution;
				}
			}
		}
		
		if(solution.multiple) {
			solution.known = new int[x.length];
			for(int _x = 0; _x < x.length; ++_x) {
				solution.known[_x] = -1;
			}
			
			for(int r = 0; r < A.length; ++r) {
				if(badrows.contains(r)) 
					continue;
				
				// Check for single column = single value.
				int candidate = -1;
				
				for(int c = 0; c < A[0].length; ++c) {
					if(A[r][c] < 0.001 && A[r][c] > -0.001) // Noll. ish.
						continue;
					
					if(candidate > -1) {
						candidate = -1;
						break;
					}
					candidate = c;
				
				}
				
				if(candidate > -1)
					solution.known[candidate] = r;
			}			
		}
		
		solution.x = new double[x.length];
		for(int i = 0; i < x.length; ++i) {
			solution.x[i] = x[bindex[i]];
		}
		
		return solution;
	}
	
	public static void printRow(double[] b) {
		for(int c = 0; c < b.length; c++) {
			System.out.print(b[c]);
			System.out.print(" ");
		}
		System.out.println();
	}
	
	@SuppressWarnings("unused")
	public static void printMatrix(double[][] A) {
		for(int r = 0; r < A.length; r++) {
			printRow(A[r]);
		}
	}
	

	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
	
		int n = Integer.parseInt(reader.readLine());
		while(n > 0) {
			double[][] A = new double[n][n];
			double[] b = new double[n];
			
			for(int i = 0; i < n; ++i) {
				String[] parts = reader.readLine().split(" ");
				for(int j = 0; j < n; ++j) {
					A[i][j] = Double.parseDouble(parts[j]);
				}
			}
			
			String[] parts = reader.readLine().split(" ");
			for(int i = 0; i < n; ++i) {				
				b[i] = Double.parseDouble(parts[i]);
			}
			
			Solution s = solve(A, b);
			if(s.inconsistent) {
				System.out.println("inconsistent");
			} else if(s.multiple) {
				for(int i = 0; i < s.x.length; ++i) {
					if(s.known[i] < 0)
						System.out.print("?");
					else
						System.out.print(s.x[s.known[i]]);
					System.out.print(" ");
				}
//				System.out.println("multiple");
			} else {
				printRow(s.x);
			}			
			n = Integer.parseInt(reader.readLine());
		}
		
	}
}
