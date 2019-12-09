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
