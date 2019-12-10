# CS 245 Assignment 02: Six Degrees of Kevin Bacon

Six Degrees of Kevin Bacon​ is a knowledge game based on the “small world” or “six degrees of separation” concept. It is possible for a large network to be linked by a limited number of steps through one or more who are well-connected.

# Runtime Analysis
### Runtime of the Graph Functions:
##### findValue()
The runtime of findValue() in this implementation is Θ(1), because to find a particular value in the graph I used a hashtable, which has a runtime of Θ(1).
##### addVertex()
The runtime of addVertex() in this implementation is Θ(1). This function calls findValue, which has a runtime of Θ(1), and then executes add() from ArrayList and put from Hashtable, which are both Θ(1), therefore the overall runtime of the function is Θ(1).
##### addEdge()
The runtime of addEdge() is Θ(1). It calls get() from ArrayList, which has a runtime of Θ(1), then adds the new edge, or node, to the linked list at that index at the beginning of the list, making the runtime Θ(1) rather than depending on the number of edges for that particular vertex.
##### findShortestPath()
The worst case runtime of findShortestPath() in this implementation of Dijkstra's algorithm is Θ(|V|^2). The running time is dominated by the nested while loops. The inner loop takes Θ(|V|) time, because any vertex could have an edge to all vertices, and the outer loop's worst case run time is Θ(|V|) because the algorithm could visit the target value last.
### Runtime of Main Program:
##### readFile()
The runtime of readFile() is Θ(n * m^2) where n is the number of lines in the file, and m is the number of entries in each cast. There are nested loops, where the outer loop runs for every line, and the inner loop loops through the number of actors in the line, adding edges to all of those actors.
