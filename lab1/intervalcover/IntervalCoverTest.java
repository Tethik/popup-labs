import static org.junit.Assert.*;

import org.junit.Test;


public class IntervalCoverTest {

	@Test
	public void testKattis1() {
		IntervalCover.Interval goal = new IntervalCover.Interval(0, -0.5, 1);
		IntervalCover.Interval[] intervals = {
			new IntervalCover.Interval(0, -0.9, -0.1),
			new IntervalCover.Interval(1, -0.2, 2),
			new IntervalCover.Interval(2, -0.7, 1),
		};
		
		IntervalCover.Interval[] solution = {
			new IntervalCover.Interval(2, -0.7, 1),	
		};
		
		IntervalCover.Interval[] answer = IntervalCover.solve(goal, intervals);
		
		assertArrayEquals(solution, answer);
	}
	
	@Test
	public void testKattis2() {
		IntervalCover.Interval goal = new IntervalCover.Interval(0, 0, 1);
		IntervalCover.Interval[] intervals = {
			new IntervalCover.Interval(0, 0, 0.25),
			new IntervalCover.Interval(1, 0.25, 0.75),
			new IntervalCover.Interval(2, 0.75, 0.999),
		};
		
		IntervalCover.Interval[] solution = {};
		
		IntervalCover.Interval[] answer = IntervalCover.solve(goal, intervals);
		
		assertArrayEquals(solution, answer);
	}
	
	@Test
	public void testSingular() {
		IntervalCover.Interval goal = new IntervalCover.Interval(0, 0, 1);
		IntervalCover.Interval[] intervals = {
			new IntervalCover.Interval(0, 0, 1),
		};
		
		IntervalCover.Interval[] solution = {
				new IntervalCover.Interval(0, 0, 1),
		};
		
		IntervalCover.Interval[] answer = IntervalCover.solve(goal, intervals);
		
		assertArrayEquals(solution, answer);
	}
	
	@Test
	public void testSingularEmpty() {
		IntervalCover.Interval goal = new IntervalCover.Interval(0, 1, 1);
		IntervalCover.Interval[] intervals = {
			new IntervalCover.Interval(0, 0, 1),
		};
		
		IntervalCover.Interval[] solution = {
				new IntervalCover.Interval(0, 0, 1),
		};
		
		IntervalCover.Interval[] answer = IntervalCover.solve(goal, intervals);
		
		assertArrayEquals(solution, answer);
	}
	
	@Test
	public void testSingularFail() {
		IntervalCover.Interval goal = new IntervalCover.Interval(0, 1, 1);
		IntervalCover.Interval[] intervals = {
			new IntervalCover.Interval(0, 0, 0),
			new IntervalCover.Interval(0, 1.1, 2),
		};
		
		IntervalCover.Interval[] solution = {
		};
		
		IntervalCover.Interval[] answer = IntervalCover.solve(goal, intervals);
		
		assertArrayEquals(solution, answer);
	}

	@Test
	public void testSingularBig() {
		IntervalCover.Interval goal = new IntervalCover.Interval(0, 1, 1);
		IntervalCover.Interval[] intervals = {
			new IntervalCover.Interval(0, -10, 10),
		};
		
		IntervalCover.Interval[] solution = {
			new IntervalCover.Interval(0, -10, 10),
		};
		
		IntervalCover.Interval[] answer = IntervalCover.solve(goal, intervals);
		
		assertArrayEquals(solution, answer);
	}
	

	@Test
	public void testSingularSinglePoint() {
		IntervalCover.Interval goal = new IntervalCover.Interval(0, 1, 1);
		IntervalCover.Interval[] intervals = {
			new IntervalCover.Interval(0, 1, 1),
		};
		
		IntervalCover.Interval[] solution = {
			new IntervalCover.Interval(0, 1, 1),
		};
		
		IntervalCover.Interval[] answer = IntervalCover.solve(goal, intervals);
		
		assertArrayEquals(solution, answer);
	}
	
	
	
	
	
	

}
