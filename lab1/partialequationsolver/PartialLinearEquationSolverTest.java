import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.Random;

import org.junit.Test;


public class PartialLinearEquationSolverTest {

	@Test
	public void testKattis() {
		double[][] A = {
				{1.0, 1.0},
				{0.0, 1.0}
		};
		
		double[] b = { 23, 42 };
		double[] expected = {-19, 42};
		
		assertArrayEquals(expected, PartialLinearEquationSolver.solve(A,b).x, 0.000000001);
	}
	
	@Test
	public void testKattis2() {
		double[][] A = {
				{5}				
		};
		
		double[] b = { 1 };
		double[] expected = {0.2};
		
		assertArrayEquals(expected, PartialLinearEquationSolver.solve(A,b).x, 0.000000001);
	}

	@Test
	public void testKattis3() {
		double[][] A = {
				{1, -2, 0},
				{2, -4, 0},
				{1, -2, 1}
		};
		
		double[] b = { 3, 6, 4 };
		
		assertTrue(PartialLinearEquationSolver.solve(A,b).multiple);
	}
	
	@Test
	public void testKattis4() {
		double[][] A = {
				{1, 1, 0},
				{3, 3, 0},
				{0, 1, 2}
		};
		
		double[] b = { 1,4,8 };
		
		assertTrue(PartialLinearEquationSolver.solve(A,b).inconsistent);
	}
	
	@Test
	public void testZero() {
		double[][] A = {
				{7}
		};
		
		double[] b = { 0 };
		
		assertFalse(PartialLinearEquationSolver.solve(A,b).inconsistent);
		assertFalse(PartialLinearEquationSolver.solve(A,b).multiple);
		assertArrayEquals(new double[]{ 0.0 }, PartialLinearEquationSolver.solve(A,b).x, 0.000000001);
	}
	
	@Test
	public void testMultiple() {
		double[][] A = {
				{1, 1, -3},
				{2, 1, -1},
				{3, 2, -4}
		};
		
		double[] b = { 4,2,6 };
		PartialLinearEquationSolver.Solution s = PartialLinearEquationSolver.solve(A,b);
		assertTrue(s.multiple);
	}
	
	@Test
	public void testMultiple2() {
		double[][] A = {
				{1,1},
				{0,0}
		};
		
		double[] b = { 4,0 };
		PartialLinearEquationSolver.Solution s = PartialLinearEquationSolver.solve(A,b);
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
		PartialLinearEquationSolver.Solution s = PartialLinearEquationSolver.solve(A,b);
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
			
			PartialLinearEquationSolver.Solution s = PartialLinearEquationSolver.solve(A,b);
			assertFalse(s.inconsistent);
			assertFalse(s.multiple);
			assertArrayEquals(ans, s.x, 0.001);			
		}
	}
	
	@Test
	public void testRandomizedMultiple() {
		int n = 20;
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
			
			PartialLinearEquationSolver.Solution s = PartialLinearEquationSolver.solve(A,b);
//			LinearEquationSolver.printMatrix(A);
//			LinearEquationSolver.printRow(b);
//			LinearEquationSolver.printRow(s.x);
//			System.out.println();
//			assertFalse(s.inconsistent);
			assertTrue(s.multiple);
			boolean atleastone = false;
			for(int index : s.known)
				atleastone |= index == -1;
			assertTrue(atleastone);
			
//			assertArrayEquals(ans, s.x, 0.001);			
		}
	}
	
	@Test
	public void testPartialMultiple() {
		double[][] A = {
				{1, -2, 1},
				{2, -4, 0},
				{1, -2, 0}
		};
		
		double[] b = { 4, 6, 3 };
		
		PartialLinearEquationSolver.Solution s = PartialLinearEquationSolver.solve(A,b);
		assertFalse(s.inconsistent);
		assertTrue(s.multiple);
		
		assertEquals(1.0, s.x[s.known[2]], 0.0001);
		
		A = new double[][] {
				{1, -2, 0},
				{2, -4, 0},
				{1, -2, 1},
		};		
		b = new double[] { 3, 6, 4 };
		
		
		s = PartialLinearEquationSolver.solve(A,b);
		assertFalse(s.inconsistent);
		assertTrue(s.multiple);
		assertEquals(1.0, s.x[s.known[2]], 0.0001);
		
		A = new double[][] {
				{1, -2, 0},
				{1, -2, 1},
				{2, -4, 0},
				
		};		
		b = new double[] { 3, 4, 6 };
		
		
		s = PartialLinearEquationSolver.solve(A,b);
		assertFalse(s.inconsistent);
		assertTrue(s.multiple);
		assertEquals(1.0, s.x[s.known[2]], 0.0001);
		
	}
	
	@Test
	public void testReversedOrder() {
		double[][] A = {
				{ 0, 0, 1 },
				{ 0, 1, 0 },
				{ 1, 1, 0 }
		};
		
		double[] b = { 3, 4, 5 };
		double[] ans = {3.0, 4.0, 1.0 };
		PartialLinearEquationSolver.Solution s = PartialLinearEquationSolver.solve(A,b);
		assertFalse(s.inconsistent);
		assertFalse(s.multiple);
		assertArrayEquals(ans, s.x, 0.0001);
	}
	
}
