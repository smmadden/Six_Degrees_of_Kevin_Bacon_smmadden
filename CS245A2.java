import java.io.*;
import java.util.*;
import java.lang.*;


public class CS245A2{

  public CS245A2(){
      System.out.println("Starting program: \nAccessing file...\n");
  }

  public boolean readFile(File file, GraphImplementation graph){
      int line_num = 0;
      try{
          Scanner scan = new Scanner(file);
          String line;

          scan.nextLine(); // skips over first line which has formatting
          line_num++;
          while(scan.hasNextLine()){
              //for(int i=0; i<353; i++){
              line = scan.nextLine();
              line_num++;
              // split line up based on info we want
              line = line.substring(line.indexOf(",")+1);
              line = line.substring(line.indexOf(",")+1); // now should just be cast and crew
              //String cast = line.substring(line.indexOf("["), line.indexOf("]")); // this gives us all of the cast
              String cast = line.substring(line.indexOf("["), line.lastIndexOf("]")); // this gives us all of the cast and crew
              cast = cast.substring(0, cast.lastIndexOf("]")); // this should give us all of the cast
              String[] actors = cast.split("\\},");
              //System.out.println(cast + "\n\n");
              //System.out.println(actors[0] + "\n\n");
              // members now has each character's info
              if(cast.length() > 1){ // if the cast length is one, that means the only character is [
                  for(int j=0; j<actors.length; j++) {
                      int index = actors[j].indexOf("name");
                      if(index != -1){
                          actors[j] = actors[j].substring(index, actors[j].indexOf("\"\", \"\"order"));
                          actors[j] = actors[j].substring(actors[j].lastIndexOf("\"") + 1); // shaves off ""
                          graph.addVertex(actors[j]);
                      }
                  }


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
          System.out.println("Error reading file.");
          return false;
      }

      return true;
  }

  private String toTitleCase(String s){
      s = s.strip();
      StringBuilder string = new StringBuilder();
      boolean capNext = true; // starts as true so that the first character will be capitalized
      char[] c = s.toCharArray();
      for(int i=0; i<c.length; i++){
          if(Character.isSpaceChar(c[i])){
              capNext = true;
          } else if(capNext){
              c[i] = Character.toUpperCase(c[i]);
              capNext = false;
          } else {
              c[i] = Character.toLowerCase(c[i]);
          }

      }
      string.append(c);
      return string.toString();
  }


  public static void main(String[] args){
      CS245A2 program = new CS245A2();
      GraphImplementation graph = new GraphImplementation();

      if(args.length == 0){
          System.out.println("Error: missing file location.\nUsage: java CS245A2 /pathname/example/tmdb_5000_credits.csv");
          //return;
      }

      File file = new File("/Users/saigemadden/documents/sem3/cs245/assignment2/tmdb_5000_credits.csv");
      //File file = new File(args[0]);

      if(!program.readFile(file, graph)){ // if there was an error reading the file
          return;
      }




      Scanner input = new Scanner(System.in);
      boolean cont = true; // should i have this at all? Should it just be in an infinite loop or should it ask every 5 or 10 or something
      int count = 0;
      while(cont){
          count++;
          System.out.print("Actor 1 name: ");
          String actor1 = input.nextLine();
          actor1 = program.toTitleCase(actor1);

          while(graph.findValue(actor1) == -1){
              System.out.println("No such actor.");
              System.out.print("Actor 1 name: ");
              actor1 = input.nextLine();
              actor1 = program.toTitleCase(actor1);

          }

          System.out.print("Actor 2 name: ");
          String actor2 = input.nextLine();
          actor2 = program.toTitleCase(actor2);


          while(graph.findValue(actor2) == -1){
              System.out.println("No such actor.");
              System.out.print("Actor 2 name: ");
              actor2 = input.nextLine();
              actor2 = program.toTitleCase(actor2);

          }

          System.out.print("Path between " + actor1 + " and " + actor2 + ": ");
          graph.findShortestPath(actor1, actor2);
          System.out.println();


          if(count % 10 == 0){ // asks if user would like to continue every 10 entries
              System.out.print("Would you like to continue? (y/n): ");
              String answer = input.nextLine().toLowerCase();
              while(!answer.equals("y") && !answer.equals("n")){
                  System.out.print("Invalid answer, please enter y or n: ");
                  answer = input.nextLine().toLowerCase();
              }
              if(answer.equals("n")){
                  cont = false;
              }
          }

      }
  }

}
