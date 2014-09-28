#include "intervalcover.hpp"

#include <algorithm>
#include <iostream>
#include <vector>

using namespace std;

// Used to save the original index before sorting.
struct IndexedInterval : Interval {
	unsigned int index;
	
	IndexedInterval(unsigned int i, double l, double r) : Interval(l,r), index(i) { }
};

vector<int> cover(Interval& goal, vector<Interval> intervals) {
	if(intervals.size() == 0) {
		vector<int> empty;
		return empty;
	}
	
	// TODO: Skulle kanske kunna spara index i int[] istället för att subklassa här för bättre minnesprestanda.
	vector<IndexedInterval> _intervals;
	for(unsigned int i = 0; i < intervals.size(); ++i) 
		_intervals.push_back(IndexedInterval(i, intervals[i].left, intervals[i].right));	
	
	sort(_intervals.begin(), _intervals.end());
	
	#if DEBUG
	cout << endl << endl;	
	cout << "Sorted:" << endl;
	for(unsigned int i = 0; i < _intervals.size(); ++i)
		cout << _intervals[i].left << " " << _intervals[i].right << endl;		
	cout << endl;
	#endif
	
	vector<int> solution;
	
	double L = goal.left;
	double R = goal.right;
	
	vector<IndexedInterval>::iterator best = _intervals.begin();
	
	while(solution.size() == 0 || L < R) {		
		
		// Binary search. Find element which is > L.
		Interval cmp = { L, R };
		vector<IndexedInterval>::iterator end = upper_bound(best, _intervals.end(), cmp);	
			
		#if DEBUG
		cout << "End element:" << endl,
		cout << end->left << " " << end->right << endl;
		cout << endl;
		#endif

		bool set = false;
		// Find max R. Hopefully not all intervals are < L...								
		for(vector<IndexedInterval>::iterator current = best; current != end; ++current) {			
			if(current->right > best->right || !set) {
				#if DEBUG
				cout << "Found best: " << current->index << " " << current->left << " " << current->right << endl;
				#endif
				best = current;
				set = true;
			}			
		}		
		
		if((best->right <= L && best->right < R) || best->left > L) {			
			vector<int> empty;
			return empty;
		}				
		
		solution.push_back(best->index);		
		L = best->right;		
	}	
	
	return solution;
}
