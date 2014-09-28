import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.Random;

import org.junit.Test;


public class LinearEquationSolverTest {

	@Test
	public void testKattis() {
		double[][] A = {
				{1.0, 1.0},
				{0.0, 1.0}
		};
		
		double[] b = { 23, 42 };
		double[] expected = {-19, 42};
		
		assertArrayEquals(expected, LinearEquationSolver.solve(A,b).x, 0.000000001);
	}
	
	@Test
	public void testKattis2() {
		double[][] A = {
				{5}				
		};
		
		double[] b = { 1 };
		double[] expected = {0.2};
		
		assertArrayEquals(expected, LinearEquationSolver.solve(A,b).x, 0.000000001);
	}

	@Test
	public void testKattis3() {
		double[][] A = {
				{1, -2, 0},
				{2, -4, 0},
				{1, -2, 1}
		};
		
		double[] b = { 3, 6, 4 };
		
		assertTrue(LinearEquationSolver.solve(A,b).multiple);
	}
	
	@Test
	public void testKattis4() {
		double[][] A = {
				{1, 1, 0},
				{3, 3, 0},
				{0, 1, 2}
		};
		
		double[] b = { 1,4,8 };
		
		assertTrue(LinearEquationSolver.solve(A,b).inconsistent);
	}
	
	@Test
	public void testZero() {
		double[][] A = {
				{7}
		};
		
		double[] b = { 0 };
		
		assertFalse(LinearEquationSolver.solve(A,b).inconsistent);
		assertFalse(LinearEquationSolver.solve(A,b).multiple);
		assertArrayEquals(new double[]{ 0.0 }, LinearEquationSolver.solve(A,b).x, 0.000000001);
	}
	
	@Test
	public void testMultiple() {
		double[][] A = {
				{1, 1, -3},
				{2, 1, -1},
				{3, 2, -4}
		};
		
		double[] b = { 4,2,6 };
		LinearEquationSolver.Solution s = LinearEquationSolver.solve(A,b);
		assertTrue(s.multiple);
	}
	
	@Test
	public void testMultiple2() {
		double[][] A = {
				{1,1},
				{0,0}
		};
		
		double[] b = { 4,0 };
		LinearEquationSolver.Solution s = LinearEquationSolver.solve(A,b);
		assertTrue(s.multiple);
		assertFalse(s.inconsistent);
	}
	
	@Test
	public void testConsistent() {
		double[][] A = {
				{1, 3, -5},
				{1, -2, 4},
				{2, 1, -1}
		};
		
//		assertFalse(LinearEquationSolver.IsConsistent(A));
		
		double[] b = { 0,2,0 };
		LinearEquationSolver.Solution s = LinearEquationSolver.solve(A,b);
		assertTrue(s.inconsistent);
	}
	
	@Test
	public void testRandomized() {
		int n = 100;
		int times = 4;
		double[] ans = new double[n];
		Random random = new Random();
		
		
		for(int i = 0; i < times; ++i) {
			for(int u = 0; u < n; ++u) {
				ans[u] = random.nextDouble()*10000;
			}
			
			double[][] A = new double[n][n];
			double[] b = new double[n];
			
			for(int r = 0; r < n; ++r) {
				double sum = 0.0;
				for(int c = 0; c < n; ++c) {
					A[r][c] = random.nextDouble()*10000;
					sum += A[r][c]*ans[c];
				}
				b[r] = sum;
			}
			
			LinearEquationSolver.Solution s = LinearEquationSolver.solve(A,b);
			assertFalse(s.inconsistent);
			assertFalse(s.multiple);
			assertArrayEquals(ans, s.x, 0.001);			
		}
	}
	
	@Test
	public void testRandomizedMultiple() {
		int n = 100;
		int times = 1000;
		double[] ans = new double[n];
		Random random = new Random();
		
		
		for(int i = 0; i < times; ++i) {
			for(int u = 0; u < n; ++u) {
				ans[u] = random.nextDouble()*10000;
			}
			
			double[][] A = new double[n][n];
			double[] b = new double[n];
			
			for(int r = 0; r < n-1; ++r) {
				double sum = 0.0;
				for(int c = 0; c < n; ++c) {
					A[r][c] = Math.random()*10000;
					sum += A[r][c]*ans[c];
				}
				b[r] = sum;
			}
			
			int last = n-1;
			int mul = 1+random.nextInt(10000);
			A[last] = Arrays.copyOf(A[0], A[0].length);
			for(int j = 0; j < n; ++j)
				A[last][j] *= mul;
			b[n-1] = b[0] * mul;
			
			LinearEquationSolver.Solution s = LinearEquationSolver.solve(A,b);
//			LinearEquationSolver.printMatrix(A);
//			LinearEquationSolver.printRow(b);
//			LinearEquationSolver.printRow(s.x);
//			System.out.println();
			assertFalse(s.inconsistent);
			assertTrue(s.multiple);
//			assertArrayEquals(ans, s.x, 0.001);			
		}
	}
	
}
