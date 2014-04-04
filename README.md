## NAME: Jennie Zhou
## STUDENT ID: 2336-6698-90
## EMAIL: jennie.zhou@usc.edu

## PROGRAM DESCRIPTION
	+ My program can be run by simply compiling the code using "javac *.java", and then "java Breakthrough". Breakthrough.java is the main file that contains the generation of successors and alpha-beta pruning algorithm. I have another Node.java class - each node has a board configuration, corresponding x and y position of the piece that has been moved, the associated heuristic value for that node, the original x and y position from which the piece was moved, the depth of the tree that the node is at, a pointer to the parent node, a boolean indicating if it is the max player or not (true if it is max, false if not), a list to contain the node's children, and a list to contain the history of the best successors following that node. 
	+ I've separated my alpha-beta pruning algorithm into two functions: the maxValue function and the minValue function. Starting with the maxValue function on the root, the maxValue function will generate the successors of the node that is passed in, and then determine the appropriate alpha value by looping through the children and taking the maximum of the alpha value passed into the function and the mininum value of all of that node's children (where the minValue function is called). In the minValue function, the appropriate beta value will be determined by looping through the children of the given node and taking the minimum of the beta value passed into the function, and the maximum value of that node's children (where the maxValue function is called). As a result, my algorithm alternates between the maxValue and minValue function in order to effectively simulate the sequence of moves that will occur in each scenario. I determine the best move to take by storing the heuristic value in the node and, when the pruning algorithm has completed, I loop through the root's children and take the first acceptable node (heuristicValue == 1) to be the best move that Player A should take.
	+ NOTE: When running my program with the sample configuration given in the assignment, my computer runs out of heap space. I have also tried running the program on aludra, and the same problem persists. However, when run on simpler scenarios, the program outputs the correct move and does not run out of heap space.  

## COMPILE & RUNNING MY PROGRAM
	+ Once you are in my assignment folder, execute the following commands:
		- javac *.java
		- java Breakthrough

