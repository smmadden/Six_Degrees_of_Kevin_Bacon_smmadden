import java.util.*;

public class GraphImplementation {
    // adjacency list implementation of a graph to store the actors
    // uses a hashtable to keep track of what index each actor is

    private ArrayList<GraphNode> graph;
    private Hashtable<String, Integer> indeces;
    private int vertices;
    // don't need number of vertices because length of graph will be updated as needed

    public GraphImplementation(){
        graph = new ArrayList<>();
        indeces = new Hashtable<>();
        vertices = 0;
    }

    public void addVertex(String value){ // the head of the list is the vertex at that index
        // if the same value is added twice don't do anything
        if(findValue(value) == -1){
            graph.add(new GraphNode(value, vertices));
            indeces.put(value, vertices);
            vertices++;
        }

    }

    public void addEdge(int src, int tar) throws Exception {
        // throw an exception if graph is empty or only has one value (bc not two vertices to make an edge for) or if the vertex is out of bounds
        if(graph.size() <= 1 || src >= vertices || src < 0 || tar >= vertices || tar < 0){
            throw new Exception();
        }
        // this is an undirected graph so an edge will be added to both the src and the tar
        GraphNode source = graph.get(src);
        GraphNode target = graph.get(tar);

        // source and target could never be null - not possible because wouldn't be a vertex if so

        while(source.next != null){
            source = source.next;
        }
        while(target.next != null){
            target = target.next;
        }
        source.next = new GraphNode(graph.get(tar));
        target.next = new GraphNode(graph.get(src));

    }

    public void print(){
        for(int i=0; i<vertices; i++){
            GraphNode node = graph.get(i);
            while(node != null){
                System.out.print(node.value + ", ");
                node = node.next;
            }
            System.out.println();
        }
        System.out.println(vertices);

    }

    // do we need a function for neighbors? is just the list of nodes at a particular index

    public int findValue(String value){ // finds the index of the value in the array
        if(indeces.get(value) == null){
            return -1;
        } else {
            return indeces.get(value);
        }

//        for(int i=0; i<vertices; i++){
//            if(graph.get(i).value.equals(value)){
//                return i;
//            }
//        }
//        // finding a particular value, in this case an actor, in the list
//        return -1; // value not found
    }

    // first index is the actors, second is :
    // vertex[0], path[1], dist[2], known[3]

    public void findShortestPath(String source, String target){
        int src = findValue(source);
        int tar = findValue(target);
        //int[][] table = new int[graph.size()][4];
        int[] path = new int[vertices];
        int[] dist = new int[vertices];
        boolean[] known = new boolean[vertices];

        for(int i=0; i<vertices; i++){
            path[i] = -1;
            dist[i] = Integer.MAX_VALUE;
            known[i] = false;
        }

        // source vertex path will remain -1 and distance will be 0
        dist[src] = 0;
        known[src] = true;

        int curr = src;
        while(!known[tar]){ // while the target vertex is not known
            // update all paths and distances, find min distance, set that to true


            // update all paths
            GraphNode node = graph.get(curr); // node is the actor, node.next and so on are all of the neighbors
            while(node.next != null){
                node = node.next;
                // if the total distance of the entire path, which would be the dist for the current node + 1
                if(!known[node.index] && dist[node.index] > dist[curr]+1 ) { // need to check if the distance is less than first because if not then don't change anything
                    path[node.index] = curr;
                    dist[node.index] = dist[curr] + 1;
                }

            }

            // find min distance of the unknowns
            int minDist = findMin(path, dist, known);

            known[minDist] = true;



            curr = minDist;

        }

        // now the target is known
        // need to print the results or return a string

        // to print, need to trace back the entire path

        printPath(path, tar);

//        for(int i=0; i< path.length; i++){
//            System.out.println(known[i] + " " + path[i] + " " + dist[i]);
//
//        }


    }

    private void printPath(int[] path, int index){
        if(path[index] == -1){ // base case, the path of the source will be -1
            System.out.print(graph.get(index).value); // prints out the value
        } else {
            printPath(path, path[index]);
            System.out.print(" --> " + graph.get(index).value);
        }

    }

    private int findMin(int[] path, int[] dist, boolean[] known){
        int min = Integer.MAX_VALUE;
        int index = -1;

        for(int i=0; i<dist.length; i++){
            if(!known[i] && path[i] != -1 && dist[i] <= min){
                min = dist[i];
                index = i;
            }
        }
        return index;
    }




    class GraphNode{
        private String value;
        private int index; // to keep track of the index of the actor in the list instead of doing a find each time
        private GraphNode next;

        public GraphNode(String value, int index){
            this.value = value;
            this.index = index;
            next = null;
        }

        public GraphNode(GraphNode node){ // creates a copy of the node that is a parameter - easier to add edges this way
            value = node.value;
            index = node.index;
            next = null;
        }

        // getters and setters

    }
}
