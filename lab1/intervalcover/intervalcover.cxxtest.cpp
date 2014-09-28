#include <cxxtest/TestSuite.h>

#include "intervalcover.hpp"
#include <vector>

using namespace std;

class InternalCoverTestSuite : public CxxTest::TestSuite {
  public:
	void testKattis1() { 
		Interval goal = { -0.5, 1 };
		
		vector<Interval> intervals;
		intervals.push_back(Interval(-0.9, -0.1));
		intervals.push_back(Interval(-0.2, 2));
		intervals.push_back(Interval(-0.7, 1));
		
		std::vector<int> result = cover(goal, intervals);	
		TS_ASSERT_EQUALS(result.size(), 1);
		TS_ASSERT_EQUALS(result[0], 2);
	}
	
	void testKattis2() { 
		Interval goal = { 0, 1 };
		
		vector<Interval> intervals;
		intervals.push_back(Interval(0, 0.25));
		intervals.push_back(Interval(0.25, 0.75));
		intervals.push_back(Interval(0.75, 0.999));
		
		std::vector<int> result = cover(goal, intervals);	
		TS_ASSERT_EQUALS(result.size(), 0);
	}
	
	void testKattis3() { 
		Interval goal = { 0, 1 };
		
		vector<Interval> intervals;
		intervals.push_back(Interval(0, 0.25));
		intervals.push_back(Interval(0.25, 0.75));
		intervals.push_back(Interval(0.75, 1));
		
		std::vector<int> result = cover(goal, intervals);	
		TS_ASSERT_EQUALS(result.size(), 3);
		TS_ASSERT_EQUALS(result[0], 0);
		TS_ASSERT_EQUALS(result[1], 1);
		TS_ASSERT_EQUALS(result[2], 2);		
	}
	
	void testStupidNearlyEmpty() { 
		Interval goal = { 0, 1 };
		
		vector<Interval> intervals;
		intervals.push_back(Interval(0, 1));
		
		std::vector<int> result = cover(goal, intervals);	
		TS_ASSERT_EQUALS(result.size(), 1);
		TS_ASSERT_EQUALS(result[0], 0);	
	}
	
	void testStupidEmpty() { 		
		Interval goal = { 0, 1 };
		
		vector<Interval> intervals;		
		
		std::vector<int> result = cover(goal, intervals);	
		TS_ASSERT_EQUALS(result.size(), 0);				
	}
	
	void testMid() { 
		Interval goal = { 0, 1 };
		
		vector<Interval> intervals;
		intervals.push_back(Interval(0, 0.25));
		intervals.push_back(Interval(0.24, 0.75));
		intervals.push_back(Interval(0.1, 1));
		
		std::vector<int> result = cover(goal, intervals);	
		TS_ASSERT_EQUALS(result.size(), 2);
		TS_ASSERT_EQUALS(result[0], 0);
		TS_ASSERT_EQUALS(result[1], 2);				
	}
	
	void testLong() {
		int howmany = 20000;
		Interval goal = { 0, (double) howmany };
		
		vector<Interval> intervals;		
		for(int i = 0; i < howmany; i++)
		{
			intervals.push_back(Interval((double) i, (double) (i+1)));
		}
		
		std::vector<int> result = cover(goal, intervals);	
		TS_ASSERT_EQUALS(result.size(), howmany);
		
	}
	
	void testLong2() {
		int howmany = 20000;
		Interval goal = { 0, (double) howmany };
		
		vector<Interval> intervals;		
		for(int i = 0; i < howmany-3; i++)
		{
			intervals.push_back(Interval((double) i, (double) (i+1)));
		}
		
		std::vector<int> result = cover(goal, intervals);	
		TS_ASSERT_EQUALS(result.size(), 0);
		
	}
	
	void testTricky() {
		Interval goal = { 0, 16 };
		
		vector<Interval> intervals;
		intervals.push_back(Interval(-1, 15));
		intervals.push_back(Interval(1, 3));
		intervals.push_back(Interval(1, 4));
		intervals.push_back(Interval(4, 6));
		intervals.push_back(Interval(6, 7));
		intervals.push_back(Interval(7, 15));
		intervals.push_back(Interval(1, 16));
		
		std::vector<int> result = cover(goal, intervals);	
		TS_ASSERT_EQUALS(result.size(), 2);		
	}
	
	void testSingular() {
		Interval goal = { 0, 0 };
		
		vector<Interval> intervals;
		intervals.push_back(Interval(-1, 15));
		
		std::vector<int> result = cover(goal, intervals);	
		TS_ASSERT_EQUALS(result.size(), 1);	
	}
	
	void testSingular2() {
		Interval goal = { 0, 0 };
		
		vector<Interval> intervals;		
		
		std::vector<int> result = cover(goal, intervals);	
		TS_ASSERT_EQUALS(result.size(), 0);	
	}
	
	void testTiny() {
		Interval goal = { -0.0000001, 0.000001 };
		
		vector<Interval> intervals;
		intervals.push_back(Interval(1, 15));
		intervals.push_back(Interval(2, 3));
		intervals.push_back(Interval(-1, 15));
		intervals.push_back(Interval(5, 4));
		
		std::vector<int> result = cover(goal, intervals);	
		TS_ASSERT_EQUALS(result.size(), 1);	
	}
	
	void testWeirdFloats() {
		Interval goal = { 1.99999988, 2.0 };
		
		vector<Interval> intervals;
		intervals.push_back(Interval(1.99999988, 1.99999998));
		intervals.push_back(Interval(1.99999998, 2.0));
		
		std::vector<int> result = cover(goal, intervals);	
		TS_ASSERT_EQUALS(result.size(), 2);	
	}
	
	void testSingularMuch() {
		Interval goal = { 1.0, 1.0 };
		vector<Interval> intervals;
		intervals.push_back(Interval(1.0, 1.0));
		
		std::vector<int> result = cover(goal, intervals);	
		TS_ASSERT_EQUALS(result.size(), 1);			
	}
	
	void testBuggyFirstBestValue() {
		Interval goal = { 1.0, 3.0 };
		vector<Interval> intervals;
		intervals.push_back(Interval(1337.0, 1337.0));
		intervals.push_back(Interval(1.0, 3.0));
		
		std::vector<int> result = cover(goal, intervals);	
		TS_ASSERT_EQUALS(result.size(), 1);			
	}
	
	
  
};
