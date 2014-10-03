/*
 * main.cpp
 *
 *  Created on: 19 Oct 2012
 *      Author: Tethik
 */

#include <iostream>
#include <vector>
#include "FlowNetwork.h"

using namespace std;


int main(int argc, char** argv)
{

	int vertices;
	cin >> vertices;

	int source, sink;
    cin >> source;
    cin >> sink;

    int edges;
    cin >> edges;

    FlowNetwork fw(vertices);

    for(int i = 0; i < edges; i++)
    {
    	int x,y,capacity;
    	cin >> x;
    	cin >> y;
    	cin >> capacity;
    	fw.addEdge(x,y,capacity);
    }

    /// Output.
    cout << vertices << '\n';
    vector<Edge*> paths = fw.maxflow(source, sink);
    cout << source << " " << sink << " " << fw.totalflow << '\n';
    cout << paths.size() << '\n';

    for(int i = 0; i < paths.size(); i++)
    	cout << paths[i]->from+1 << " " << paths[i]->to+1 << " " << paths[i]->flow << '\n';

    cout.flush();
	return 0;
}



