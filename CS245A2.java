import java.io.*;
import java.util.*;
import java.lang.*;

// move some of this into a separate class please this is a lot
// separate class = the graph
// separate function = reading the file


public class CS245A2{

  public CS245A2(){
    System.out.println("Starting program: \nAccessing file...");
  }

  public static void main(String[] args){
    GraphImplementation graph = new GraphImplementation();
    CS245A2 program = new CS245A2();
    File file = new File("/Users/saigemadden/documents/sem3/cs245/assignment2/tmdb_5000_credits.csv");

    int line_num = 0;
    try{
      LinkedList<LinkedList<String>> moviesAndActors = new LinkedList<>();
      int totalNumActors = 0;
      Scanner scan = new Scanner(file);
      String line;

      scan.nextLine(); // skips over first line which has formatting
      line_num++;
      while(scan.hasNextLine()){
      //for(int i=0; i<353; i++){
        line = scan.nextLine();
        line_num++;
        // split line up based on info we want - how should I do this?
        //String delim = "[";
        //String[] line_arr = line.split();
        line = line.substring(line.indexOf(",")+1);
        line = line.substring(line.indexOf(",")+1); // now should just be cast and crew
        String cast = line.substring(line.indexOf("["), line.indexOf("]")); // this gives us all of the cast
        String[] actors = cast.split("\\},");
        //System.out.println(cast + "\n\n");
        //System.out.println(actors[0] + "\n\n");
        // members now has each character's info
        if(cast.length() > 1){ // if the cast length is one, that means the only character is [
          //LinkedList<String> movie = new LinkedList<>(); // all the ones that the edges need to be added for
          for(int j=0; j<actors.length; j++) {
            int index = actors[j].indexOf("name");
            if(index != -1){
              actors[j] = actors[j].substring(index, actors[j].indexOf("\"\", \"\"order"));
              actors[j] = actors[j].substring(actors[j].lastIndexOf("\"") + 1); // shaves off ""
              graph.addVertex(actors[j]);
              //movie.add(0, actors[j]);
            }
          }

          //moviesAndActors.add(0, movie);
          //System.out.println(actors[j]);
          totalNumActors += actors.length;



          // now to add the edges
          // could do a nested for loop - is there a way that is more efficient?
          // only adding with the first one because the indexes aren't referring to the graph but to the actors
          for(int z=0; z<actors.length; z++){
            for(int c=0; c<actors.length; c++){
              if(z != c){ // if z == c then don't want to add the edge because would be adding a connection to itself
                try{
                  graph.addEdge(graph.findValue(actors[z]), graph.findValue(actors[c])); // know that the vertices exist so no reason to worry about findValue returning negative one
                } catch(Exception e){
                  System.out.println("\nProblem: line " + line_num + "\n " + actors[z] + " " + actors [c] + "\n");
//                  for(int w=0; w<actors.length; w++){
//                      System.out.println(actors[w]);
//                  }
                }
              }

            }
          }




        }


        //System.out.println(moviesAndActors.get(0).get(0)); // backwards, so will be the last actor of the last movie // right now will print out same thing twice is cast empty

        //System.out.println("\n" + totalNumActors + "\n\n");
      }

       // backwards, so will be the last actor of the last movie
      // now have a count of all of the actors - not really but should be easy
      // now have a collection of who is in which movie with whom and also have an index associated with the people



    } catch(Exception e){
      System.out.println("Error reading file " + line_num + e.getMessage());
      e.printStackTrace();
      return;
    }

    Scanner input = new Scanner(System.in);
    System.out.print("\nHere: ");
    input.nextLine();

    graph.print();


//    System.out.print("Actor 1 name: ");
//    String actor1 = input.nextLine();
//    System.out.print("Actor 2 name: ");
//    String actor2 = input.nextLine();
//    System.out.println("Path between " + actor1 + " and " + actor2 + ": ");

  }

}
