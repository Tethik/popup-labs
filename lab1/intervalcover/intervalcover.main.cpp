#include "intervalcover.hpp"

#include <iostream>

using namespace std;


// Kattis Input/Output
int main() {
	
	Interval goal;
	while(!cin.eof() && cin >> goal.left) {
		cin >> goal.right;
		
		unsigned int number_of_intervals;
		cin >> number_of_intervals;
		
		vector<Interval> intervals;	
		for(unsigned int i = 0; i < number_of_intervals; ++i) {
			Interval n;
			cin >> n.left;
			cin >> n.right;
			intervals.push_back(n);
		}
		 
		vector<int> result = cover(goal, intervals);		
		
		if(result.size() == 0) {
			cout << "impossible" << endl;			
		} else {		
			cout << result.size() << endl;
			for(unsigned int i = 0; i < result.size(); ++i) {
				cout << result[i] << " ";
			}
			cout << endl;
		}
	}
	return 0;
}
