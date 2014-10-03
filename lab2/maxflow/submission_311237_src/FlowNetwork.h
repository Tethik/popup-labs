/*
 * FlowNetwork.h
 *
 *  Created on: 19 Oct 2012
 *      Author: Tethik
 */

#ifndef FLOWNETWORK_H_
#define FLOWNETWORK_H_
#include <ostream>
#include <vector>
#include <algorithm>
#include <iostream>
#include <queue>
#include <climits>


using namespace std;

class Edge
{
public:
	int to, from, capacity, flow;
	Edge* reverse;

	Edge()
	{
		flow = 0;
		capacity = 0;
		to = 0;
		from = 0;
	}

	Edge(int from, int to, int capacity)
	{
		this->from = from;
		this->to = to;
		this->capacity = capacity;
		flow = 0;
		from = 0;
	}

	int residual()
	{
		return capacity - flow;
	}

	void show(ostream& os)
	{
		os << "(" << to << "," << from << "," << flow << "/" << capacity << ")";
	}
};

class FlowNetwork
{
	vector<pair<Edge*,int> > path;

	public:
	vector<vector<Edge*> > edgeList;

	int totalflow;

	FlowNetwork(int vertices)
	{
		totalflow = 0;
		for(int i = 0; i < vertices; i++)
		{
			//capacityMatrix.push_back(vector<int>(vertices));
			edgeList.push_back(vector<Edge*>());
		}
	}

	void addEdge(int x, int y, int capacity)
	{
		x--; // 0 Komplementering.
		y--;

		Edge* e = new Edge(x, y, capacity);
		Edge* re = new Edge(y, x, 0);
		e->reverse = re;
		re->reverse = e;
		edgeList[x].push_back(e);
		edgeList[y].push_back(re);
	}

	vector<Edge*> maxflow(int source, int sink)
	{
		source--;
		sink--;
		findpath(source, sink);
		totalflow = 0;

		while (path[sink].first != NULL)
		{
			int next = sink; // 0 index komplementering..
			int flow = path[next].second;

			if(flow == 0 || flow == INT_MAX)
				break;

			totalflow += flow;

			while(next != source)
			{
				Edge* prev = (Edge*) path[next].first;
				prev->flow += flow;
				prev->reverse->flow -= flow;
				next = prev->from;
			}

			findpath(source, sink);
		}

		path.clear();

		vector<Edge*> edges;

	    for(int x = 0; x < edgeList.size(); x++)
	    {
	    	for(int y = 0; y < edgeList[x].size(); y++)
	    	{
				if(edgeList[x][y]->flow > 0)
				{
					edges.push_back(edgeList[x][y]);
				}
	    	}
	    }

		return edges;
	}

	void findpath(int source, int sink)
	{
		queue<int> bfs;
		path.clear();

		for(int i = 0; i < edgeList.size(); i++)
		{
			pair<Edge*,int> p(NULL, INT_MAX);
			path.push_back(p);
		}

		path[source].first = NULL;
		bfs.push(source);

		while(bfs.size() > 0)
		{
			int u = bfs.front();
			bfs.pop();

			for(int v = 0; v < edgeList[u].size();	v++)
			{
				Edge* e = edgeList[u][v];

				int residual = e->residual();
				if(residual > 0 && path[e->to].first == NULL && e->to != source)
				{
					path[e->to].first = e;
					path[e->to].second = min(residual, path[e->from].second);

					if(e->to != sink)
					{
						bfs.push(e->to);
					}
					else
						return;
				}
			}
		}
		return;
	}
};



#endif /* FLOWNETWORK_H_ */
