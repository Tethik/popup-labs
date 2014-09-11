#ifndef _INTERNALCOVER_
#define _INTERNALCOVER_

#define DEBUG false

#include <vector>

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

std::vector<int> cover(Interval& goal_interval, std::vector<Interval> intervals);

#endif
