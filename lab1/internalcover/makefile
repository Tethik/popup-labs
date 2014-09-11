all: internalcover.o test internalcover

clean:
	rm *.o *.out testrunner.cpp

internalcover.o: internalcover.h internalcover.cpp
	g++ -std=c++11 -g -Wall internalcover.cpp -c -o internalcover.o

testrunner:
	cxxtestgen --error-printer -o testrunner.cpp *.cxxtest.cpp

test: internalcover.o testrunner 
	g++ -std=c++11 -o test.out testrunner.cpp internalcover.o
	
internalcover: internalcover.o
	g++ -std=c++11 -g -Wall intervalcover.main.cpp -o intervalcover internalcover.o
