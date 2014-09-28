#ifndef _INTERNALCOVER_
#define _INTERNALCOVER_

#define DEBUG false

#include <vector>

/**
 * AUTHORS: Joakim Uddholm, Per Classon
 */

/**
 * Used to represent an interval. Has a left and a right position. Sortable using the < operator based on left position.
 */
struct Interval {
	double left;
	double right;
	
	Interval() {}
	Interval(double l, double r) : left(l), right(r) { }
	
	bool operator<(const Interval& other) const
	{
		return left < other.left;
	}
};

/**
 * Solves the interval cover problem given a goal interval and a list of intervals which are to be used to try and cover the goal.
 */
std::vector<int> cover(Interval& goal_interval, std::vector<Interval> intervals);

#endif
