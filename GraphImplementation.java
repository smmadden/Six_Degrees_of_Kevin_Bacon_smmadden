import java.util.*;

public class GraphImplementation {

    /*
        Adjacency list implementation of a graph to store the actors
        Uses a hashtable to to quickly find an actor's index in the graph
    */

    private ArrayList<GraphNode> graph; // array of linked lists
    private Hashtable<String, Integer> indices; // key = actor's name, value = index in the graph
    private int vertices; // number of actors added to the graph, used to track index

    // constructor
    public GraphImplementation(){
        graph = new ArrayList<>();
        indices = new Hashtable<>();
        vertices = 0;
    }

    class GraphNode{
        private String value; // the value of the node, in this case the name of the actor
        private int index; // to keep track of the index of the actor in the graph to avoid doing a find each time
        private GraphNode next; // next node in the linked list

        // constructors

        public GraphNode(String value, int index){
            this.value = value;
            this.index = index;
            next = null;
        }

        public GraphNode(GraphNode node){
            // creates a copy of the parameter node that is a parameter, used to add edges more efficiently
            value = node.value;
            index = node.index;
            next = null;
        }

        // getters and setters

        public String getValue(){
            return value;
        }

        public int getIndex(){
            return index;
        }

        public GraphNode getNext(){
            return next;
        }

        public void setValue(String val){
            value = val;
        }

        public void setIndex(int ind){
            index = ind;
        }

        public void setNext(GraphNode node){
            next = node;
        }

    }

    public void addVertex(String value){
        // makes sure the same value is not added twice
        if(findValue(value) == -1){
            // add a new node to the graph and to the hashtable
            graph.add(new GraphNode(value, vertices));
            indices.put(value.toLowerCase(), vertices); // added value as lowercase to make finding simplified
            vertices++;
        }

    }

    public void addEdge(int src, int tar) throws Exception {
        // throws an exception if graph is empty or only has one value (no two vertices for an edge) or if the vertex is out of bounds
        if(graph.size() <= 1 || src >= vertices || src < 0 || tar >= vertices || tar < 0){
            throw new Exception();
        }

        // this is an undirected graph so need to add an edge to both the src and the tar
        GraphNode source = graph.get(src);
        GraphNode target = graph.get(tar);

        // adds the new nodes to the beginning of the linked list to save time
        if(source.next == null){
            source.next = new GraphNode(graph.get(tar));
        } else {
            GraphNode node = new GraphNode(graph.get(tar));
            node.next = source.next;
            source.next = node;
        }

        if(target.next == null){
            target.next = new GraphNode(graph.get(src));
        } else {
            GraphNode node = new GraphNode(graph.get(src));
            node.next = target.next;
            target.next = node;
        }
    }

    public int findValue(String value){
        // finds the index of the value in the array using the hashtable
        value = value.toLowerCase(); // values stored in lowercase in hashtable
        if(indices.get(value) == null){
            // if the value is not in the hashtable, return -1
            return -1;
        } else {
            // return the index stored in the hashtable
            return indices.get(value);
        }
    }

    public void findShortestPath(String source, String target){
        // uses Dijkstra's algorithm to find the shortest path from the source to the target
        int src = findValue(source);
        int tar = findValue(target);

        // if either the source or target cannot be found in the graph, end the program
        if(src == -1 ){
            System.out.println(source + " does not exist in the graph.");
            return;
        } else if(tar == -1){
            System.out.println(target + " does not exist in the graph.");
            return;
        }

        // arrays to keep track of the path, the distance from the source, and if the vertex is known
        int[] path = new int[vertices];
        int[] dist = new int[vertices];
        boolean[] known = new boolean[vertices];

        // populate the arrays with their starting values
        for(int i=0; i<vertices; i++){
            path[i] = -1;
            dist[i] = Integer.MAX_VALUE;
            known[i] = false;
        }

        // source vertex's path will remain -1 and the distance will be 0
        dist[src] = 0;
        known[src] = true;

        int curr = src;
        while(!known[tar]){ // while the target vertex is not known

            // update all paths
            GraphNode node = graph.get(curr); // node is the actor, node.next and so on are all of the neighbors
            while(node.next != null){
                node = node.next;
                // if the total distance of the path is less than the current distance, then update
                if(!known[node.index] && dist[node.index] > dist[curr]+1 ) {
                    // need to update both path and distance
                    path[node.index] = curr;
                    dist[node.index] = dist[curr] + 1;
                }
            }

            // finds the index of the next minimum distance that is not known
            int minDist = findMin(path, dist, known);

            if(minDist == -1){ // no min was found, therefore there is no pathway between the two
                System.out.println("There is no possible pathway between " + graph.get(src).value + " and " + graph.get(tar).value);
                return;
            }

            // set the vertex for the minimum distance to known, then change the current vertex for the next loop
            known[minDist] = true;
            curr = minDist;

        }

        // prints the pathway recursively
        printPath(path, tar);
        System.out.println();
    }

    private int findMin(int[] path, int[] dist, boolean[] known){
        // finds the next minimum distance to be selected
        int min = Integer.MAX_VALUE;
        int index = -1;
        for(int i=0; i<dist.length; i++){
            // if the vertex is unknown, there is a viable pathway, and the distance is less than the min
            if(!known[i] && path[i] != -1 && dist[i] < min){
                // update the minimum and the index
                min = dist[i];
                index = i;
            }
        }
        // if the conditions are never met, returns -1
        return index;
    }

    private void printPath(int[] path, int index){
        // prints the entire path from the index back to the source
        if(path[index] == -1){ // base case: the path of the source is -1, so print
            System.out.print(graph.get(index).value);
        } else {
            printPath(path, path[index]);
            System.out.print(" --> " + graph.get(index).value);
        }
    }

}
