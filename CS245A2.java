import java.io.*;
import java.util.*;
import java.lang.*;

public class CS245A2{

  public CS245A2(){
      System.out.println("Starting program: \nAccessing file...\n");
  }

  public void readFile(File file, GraphImplementation graph) throws Exception {
      // reads the file and inputs the information into the graph
      // called only once

      // using scanner to read the file
      Scanner scan = new Scanner(file);
      String line;

      /*
        Code below breaks down each line of the file, extracting the "name" value
        of each actor for each movie (assumes correct and consistent formatting)
      */

      // the first line of the file does not contain movie information, so set scanner to next line
      scan.nextLine();
      while(scan.hasNextLine()){
          line = scan.nextLine();
          // skips over the movie id and title
          line = line.substring(line.indexOf(",")+1);
          line = line.substring(line.indexOf(",")+1);

          // cuts off the crew by getting to the substring from the first [ to the second to last ], the end of the information on cast
          line = line.substring(line.indexOf("["), line.lastIndexOf("]"));
          line = line.substring(0, line.lastIndexOf("]"));

          // splits up the line into an array of information for each actor
          String[] actors = line.split("\\},");

          if(line.length() > 1){ // if the cast length is one, that means the only character is [, an empty cast
              // for each actor, get to the information for "name"
              for(int i=0; i<actors.length; i++) {
                  int index = actors[i].indexOf("name");
                  if(index != -1){ // if "name" can be found within the actor information
                      // gets rid of all information except for the value for "name"
                      actors[i] = actors[i].substring(index, actors[i].indexOf("\"\", \"\"order"));
                      actors[i] = actors[i].substring(actors[i].lastIndexOf("\"") + 1); // shaves off ""
                      // adds the name to the graph
                      graph.addVertex(actors[i]);
                  }
              }


              // now adding the edges in the graph so all of the actors for this movie are connected
              for(int i=0; i<actors.length; i++){
                  for(int j=0; j<actors.length; j++){
                      if(i != j){ // if i == j then don't want to add the edge, would be adding a connection to itself
                          graph.addEdge(graph.findValue(actors[i]), graph.findValue(actors[j]));
                      }
                  }
              }
          }
      }
  }

  private String toTitleCase(String s){
      // formatting the string into title case to display
      s = s.strip();
      StringBuilder string = new StringBuilder();
      // capNext is to mark the next character as an upper case letter, starts as true so that the first character entered will be capitalized
      boolean capNext = true;
      char[] c = s.toCharArray();
      for(int i=0; i<c.length; i++){
          if(Character.isSpaceChar(c[i])){ // if the current char is a space, the next char should be capitalized
              capNext = true;
          } else if(capNext){ // else if capNext is true, then the curr char needs to be capitalized
              c[i] = Character.toUpperCase(c[i]);
              capNext = false; // next char should not be capitalized
          } else { // if the char is not marked to be capitalized then it should be lowercase
              c[i] = Character.toLowerCase(c[i]);
          }
      }
      string.append(c);
      return string.toString();
  }

  public static void main(String[] args){
      CS245A2 program = new CS245A2();
      GraphImplementation graph = new GraphImplementation();

      // if there is no argument in the command line with the pathname, cannot execute program
      if(args.length == 0){
          System.out.println("Error: missing file location in command line.\nUsage: java CS245A2 /pathname/example/tmdb_5000_credits.csv");
          return;
      }

      // uses file path from the command line
      File file = new File(args[0]);

      // if there was an error reading the file, then stop the program
      try{
          program.readFile(file, graph);
      } catch(Exception e){
          System.out.println("Error reading file.");
          return;
      }

      Scanner input = new Scanner(System.in);
      boolean cont = true; // to keep track of if the user would like to continue
      int count = 0; // the number of queries
      while(cont){
          count++;

          // user inputs the names of actor 1 and actor 2

          System.out.print("Actor 1 name: ");
          String actor1 = input.nextLine();
          actor1 = program.toTitleCase(actor1); // formatting

          // if the actor cannot be found in the graph, continue asking for another
          while(graph.findValue(actor1) == -1){
              System.out.println("No such actor.");
              System.out.print("Actor 1 name: ");
              actor1 = input.nextLine();
              actor1 = program.toTitleCase(actor1); // formatting

          }

          System.out.print("Actor 2 name: ");
          String actor2 = input.nextLine();
          actor2 = program.toTitleCase(actor2); // formatting

          // if the actor cannot be found in the graph, continue asking for another
          while(graph.findValue(actor2) == -1){
              System.out.println("No such actor.");
              System.out.print("Actor 2 name: ");
              actor2 = input.nextLine();
              actor2 = program.toTitleCase(actor2); // formatting

          }

          // once both actor 1 and actor 2 are found, finds the shortest path between them
          System.out.print("Path between " + actor1 + " and " + actor2 + ": ");
          graph.findShortestPath(actor1, actor2); // finds and prints the path

          // program asks the user if they would like to continue or end the program every 10 entries
          if(count % 10 == 0){
              System.out.print("Would you like to continue? (y/n): ");
              String answer = input.nextLine().toLowerCase();
              // if the input is not y, Y, n, or N then ask again
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
