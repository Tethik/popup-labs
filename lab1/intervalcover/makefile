all: intervalcover.o test intervalcover

clean:
	rm *.o *.out testrunner.cpp

intervalcover.o: intervalcover.hpp intervalcover.cpp
	g++ -std=c++11 -g -Wall intervalcover.cpp -c -o intervalcover.o

testrunner:
	cxxtestgen --error-printer -o testrunner.cpp *.cxxtest.cpp

test: intervalcover.o testrunner 
	g++ -std=c++11 -o test.out testrunner.cpp intervalcover.o
	
intervalcover: intervalcover.o
	g++ -std=c++11 -g -Wall intervalcover.main.cpp -o intervalcover intervalcover.o
