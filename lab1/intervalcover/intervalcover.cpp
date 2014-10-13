#include "intervalcover.hpp"

#include <algorithm>
#include <iostream>
#include <vector>

using namespace std;

// Used to save the original index before sorting.
struct IndexedInterval : Interval {
	int index;
	
	IndexedInterval(unsigned i, double l, double r) : Interval(l,r), index(i) { }
};

vector<int> cover(Interval& goal, vector<Interval> intervals) {
	if(intervals.size() == 0) {
		vector<int> empty;
		return empty;
	}
	
	vector<IndexedInterval> _intervals;
	for(unsigned int i = 0; i < intervals.size(); ++i) 
		_intervals.push_back(IndexedInterval(i, intervals[i].left, intervals[i].right));	
	
	sort(_intervals.begin(), _intervals.end());
	
	vector<int> solution;	
	double L = goal.left;
	double R = goal.right;
	
	vector<int> empty;
	auto best = _intervals.begin();
	bool set = false;
	bool covered = false;
	for(auto ptr = _intervals.begin(); ptr != _intervals.end(); ++ptr) {			
		if(ptr->left > L) {
			if(!set) {
				return empty;
			} 
			
			solution.push_back(best->index);
			set = false;
			L = best->right;			
			if((covered = (L >= R))) {
				break;
			}
		}
		
		if(ptr->right >= best->right && ptr->left <= L) {
			best = ptr;
			set = true;
		}			
	}	
	
	if(set) {
		solution.push_back(best->index);
		L = best->right;
		covered = (L >= R); // zzz	
	} 
	
	if(!covered) {
		return empty;
	}
		//~ 
	//~ while(solution.size() == 0 || L < R) {													
		//~ for(;ptr != _intervals.end() && ptr->left <= L; ++ptr) {			
			//~ if(ptr->right > best->right || !set) {
				//~ best = ptr;
				//~ set = true;
			//~ }			
		//~ }		
		//~ 
		//~ if((best->right <= L && best->right < R) || best->left > L) {			
			//~ vector<int> empty;
			//~ return empty;
		//~ }				
		//~ 
		//~ solution.push_back(best->index);		
		//~ L = best->right;		
	//~ }	
	
	return solution;
}
