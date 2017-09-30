/*  ECS 102 Final Project Mancal
Mancala.java
Colin Stone
costone
Section 1 */

import java.util.*;
import java.io.*;

public class Mancala
{
  private static String playerName;
  private static Random rand = new Random();
  private static int[] playBoard = new int[6]; //(AR)
  private static int[] compBoard = new int[6];
  private static int playStore = 0;
  private static int compStore = 0;
  private static boolean win = false;
  private static topScores topScore = new topScores();
  //main
  public static void main(String[] args) throws IOException 
  {
    //declare variables
    File highScores = new File("highScores.txt"); //(I/O)
    boolean keepGoing = false;
    Scanner scan = new Scanner(System.in);
    do{
      Scanner readLeader = new Scanner(highScores);
      //read in leaderBoard
      Mancala.leaderBoardRead(); //(MYMETH)
      //greet the player, give main menu options
      Mancala.greeting(); //(MYMETH)
      //play mancala game
      Mancala.play(); //(MYMETH)
      //find and display winner,write to leaderboard if necessary
      Mancala.winner(); //(MYMETH)
      //search leaderBoard to see if player was written to leader board, and congratulate
      int topScoreCheck = Mancala.leaderBoardSearch(playerName); //(MYMETH)
      if (topScoreCheck != -1)
      {
        System.out.println("Congratulations "+playerName+" you are now number: "+(topScoreCheck+1)+" on the Mancala leaderboard!");
        System.out.println("\nTop Scores List:");
        for (int i = 0; i < 5; i++)
        { 
          String temp = readLeader.nextLine();
          System.out.println(temp); 
        }
      }
      //offer to restart the game and begin again
      System.out.println("\nWould you like to return to the main menu?\n[1]:Yes\t[2]:No");
        int returnCheck = scan.nextInt();
      //boolean to keep track of response to start over
      if (returnCheck == 1)
        keepGoing = true;
      else
        keepGoing = false;
      readLeader.close();
    } while (keepGoing); //(BOOL)
    scan.close();
    //exit if they say no
    System.exit(0);
  }//end main
  
  public static void leaderBoard() throws IOException //shows leaderBoard if player selects option from main menu
  {
    //create and read in input File for top scores 
    File highScores = new File("highScores.txt"); //(I/O)
    Scanner scan = new Scanner(highScores);
    System.out.println("\nTop Scores List:");
    for (int i = 0; i<5;i++) 
    { 
      String temp = scan.nextLine();
      System.out.println(temp); 
    }
    scan.close();
  }// end leaderboard
  
  public static void greeting() throws IOException //greet player and provide main menu with choice options to play, list leaderboard, search leaderboard, rules listing, or exit
  {
    String answer;
    boolean ready = false;
    Scanner scan = new Scanner(System.in);
    System.out.println("Welcome to Mancala!");
    do{
      //get player name for greeting and rest of game
      System.out.println("Please Enter Player Name: ");
      playerName = scan.nextLine();
      //check if name is already on top scores list
      for (int i = 0; i<5; i++)
      { if (playerName.compareToIgnoreCase(topScore.getName(i)) == 0)
        { //dont ready program if name already taken
          System.out.println("Previous player has already used that name, please enter a different Name:");
          ready = false;
          break;
        }
        else
          ready = true;
      }
    }while (!ready); //(BOOL)
    //set ready back to false for rest of method
    ready = false;
    //greet player
    System.out.println("Welcome " + playerName + "!");
    //main menu listing
    do{
      System.out.println("----------------------------------------------------------------------------------------------------------------------------------");
      System.out.println("\t\t\t\tMain Menu:\nEnter corresponding # for the following choices:\n[1]: Play Mancala!\t[2]: LeaderBoard\t[3]: Leaderboard Search\t[4]: Learn how to play Mancala\t\t[5]: Exit Mancala");
      int response = scan.nextInt();
      scan.nextLine(); //had to add because of nextInt() not allowing nextLine() when asking for name to search further down
      //monitor input and decide what to call based on input
      if (response == 1)
        ready = true; //ready = true to break loop and play
      else if (response == 2)
        Mancala.leaderBoard(); //show leaderboard //(MYMETH)
      else if (response == 3)
      { System.out.println("Please enter the name you would like to search for: "); //ask for name to search leaderboard for
        answer = scan.nextLine();
        int returnSearch = Mancala.leaderBoardSearch(answer); //(SEARCH)
        if (returnSearch != -1)
          System.out.println(answer + " is number "+(returnSearch+1)+" on the top scores list with a score of: "+topScore.getScore(returnSearch)); //return score and name if on top score list *parallel search
        else
          System.out.println(answer+" is not on the current top scores list.");
      }
      else if (response == 4)
        Mancala.rules(); //show rules for game //(MYMETH)
      else if (response == 5)
        System.exit(0); //exit
      else
        ready = false;
    } while(!ready); //(BOOL) //while not ready to play, keep looping main menu for choices after displaying last choice - negates any choice not between 1-5
    scan.close();
  }// end greeting
  
  public static void rules()throws IOException //displaying of rules for game
  {
    //Explain rules **Add diagrams if there is time**
    System.out.println("\t\t\t\tHOW TO PLAY MANCALA:");
    System.out.println("[Objective:] \nHave the most \"stones\" in your mancala after all " + 
                         "the stones on one side of the gameboard are captured.");
    System.out.println("\n[Gameplay]:\nThe Mancala board is made up of two rows of six holes or pits." + 
                         "\nThe player and computer-player will be placed opposite each other with the gameboard in between.");
    System.out.println("Each player places 4 \"stones\" in each of his 6 small cups to begin the game."+
                         " Your scoring cup or store will be on the right side of the screen.");
    System.out.println("Player: " + playerName + " will start the game by choosing which pit to pick up and distribute the \"stones\" from on their side." + 
                         "\nThe game will then deposit one stone into each hole moving to the right across the board until you are out of stones. " +  
                         "\nIf you run into your own store, the game will deposit one stone in it.");
    System.out.println("\n[Special Move:]\n1) If you place the last stone into an empty hole on your side of the board, all of the pieces in the hole directly across from it on your opponent's side of the board go into your store.");
    System.out.println("\n[End of Game:]\nThe game continues with players alternating turns. \nAs soon as all of the pits on one side of the board have been emptied, the game ends"+
                       " and both the computer and player collect the remaining stores from their pits to be placed into their store.\nThe player with the most stones in their mancala wins the game.");
  } //end rules
  
  public static void play() //details actual process behind playing the game. 
  {
    //Play Mancala game
    //declarations
    //intialize player scores to 0
    playStore = 0;
    compStore = 0;
    Scanner scan = new Scanner(System.in);
    //set boards to 4 at start
    for (int i = 0; i<playBoard.length;i++)
    { 
      playBoard[i] = 4; //(AR)
      compBoard[i] = 4;
    }
    System.out.println("Player: " + playerName + " goes First!");
    //displayBoard
    Mancala.displayBoard(); //(MYMETH)
    //Ask player which pit they would like to move
    //distribute stones across board from pit selected
    do {
    //Ask player which pit they would like to move
    System.out.println("Enter pit selection([1-6]): ");
    int selection = scan.nextInt();
    //Create Random Move for Computer Selection
    int randomMove = rand.nextInt(6)+1; //(RANDOM)
    //distribute player move
    System.out.println(playerName + "'s Move! Player Selects: Pit " + selection);
    Mancala.distribute(playBoard, selection); //(AR) //(MYMETH)
    Mancala.displayBoard(); //(MYMETH)
    //distribute computer move
    System.out.println("Computer's Move! Computer Selects: Pit " + randomMove);
    Mancala.computerDistribute(compBoard, randomMove); //(AR) //(MYMETH)
    Mancala.displayBoard(); //(MYMETH)
    win = Mancala.winnerCheck(playBoard, compBoard); //check for winner on each turn aka if one array is all zeroes, winner check will return true if one side is all zeroes and break loop
    } while (!win); //(BOOL) //do while no winner has been declared, still stones on both side of boards
    scan.close();
  } //end play
  
  public static void winner() throws IOException
  {
    //declare variables
    String name = "";
    int score = 0;
    //add up points for both players at end of game with stones still left on either side
    for (int i = 0; i<compBoard.length; i++)
      compStore+=compBoard[i];
    for (int i = 0; i<playBoard.length; i++)
      playStore+=playBoard[i];
    //Display winner
    if (playStore>compStore)
    { System.out.println(playerName + " Wins!");
      //take in values for topScore board
      name = playerName; //set name and score values when player wins so that they can be written to score board further down if they need to be
      score = playStore;
    }
    else if (compStore>playStore)
    { System.out.println("Computer Wins");
      name = "Computer"; //not going to write computer wins to leaderBoard, just set score to 0 so that sorts to bottom of leaderBoard and doesnt display
      score = 0;
    }
    else
    { System.out.println("Game ends in a Draw!");
      win = false; //I don't know if you actually can even reach this
      }
    //display final scores and board
    System.out.println("\t\t\tFinal Scores: ");
    System.out.println("___________________________________________________________________________________");
    System.out.println("|Comp Store\t|\t|\t|\t|\t|\t|Your Store\t");
    System.out.println("|\t|    "+0+"\t|    "+0+"\t|    "+0+"\t|    "+0+"\t|    "+0+"\t|    "+0+"\t|\t|");
    System.out.println("|\t|\t|\t|\t|\t|\t|\t|\t|");    
    System.out.println("|    "+compStore+"\t|---------\t|---------\t|---------\t|---------\t|---------\t|---------\t|    "+playStore+"\t|");                     
    System.out.println("|\t|\t|\t|\t|\t|\t|\t|\t|");
    System.out.println("|\t|    "+0+"\t|    "+0+"\t|    "+0+"\t|    "+0+"\t|    "+0+"\t|    "+0+"\t|\t|");
    System.out.println("|\t|\t|\t|\t|\t|\t|\t|\t|");
    System.out.println("-----------------------------------------------------------------------------------");
    System.out.println("\t   Pit:1\t   Pit:2\t   Pit:3\t   Pit:4\t   Pit:5\t   Pit:6");
    //make call to leaderBoardSort to put new winning score in leaderBoard for writing if greater than any other values already on leaderBoard
    Mancala.sort(topScore, name, score); //(SORT) //(MYMETH) //(MYMETH(O))
    //print new leaderBoard to file highScores.txt
    Mancala.printSortedLeaderBoard(topScore); //(MYMETH) //(MYMETH(O))
    
  } //end winner
  public static void displayBoard() //displays boards for player to see
  {
    //System.out.println("\t\tComputer: ");
    System.out.println("___________________________________________________________________________________");
    System.out.println("|Comp Store\t|\t|\t|\t|\t|\t|Your Store\t");
    System.out.println("|\t|    "+compBoard[0]+"\t|    "+compBoard[1]+"\t|    "+compBoard[2]+"\t|    "+compBoard[3]+"\t|    "+compBoard[4]+"\t|    "+compBoard[5]+"\t|\t|");
    System.out.println("|\t|\t|\t|\t|\t|\t|\t|\t|");    
    System.out.println("|    "+compStore+"\t|---------\t|---------\t|---------\t|---------\t|---------\t|---------\t|    "+playStore+"\t|");                     
    System.out.println("|\t|\t|\t|\t|\t|\t|\t|\t|");
    System.out.println("|\t|    "+playBoard[0]+"\t|    "+playBoard[1]+"\t|    "+playBoard[2]+"\t|    "+playBoard[3]+"\t|    "+playBoard[4]+"\t|    "+playBoard[5]+"\t|\t|");
    System.out.println("|\t|\t|\t|\t|\t|\t|\t|\t|");
    System.out.println("-----------------------------------------------------------------------------------");
    System.out.println("\t   Pit:1\t   Pit:2\t   Pit:3\t   Pit:4\t   Pit:5\t   Pit:6");
  } //end displayBoard
  
  public static int[] distribute(int[] array, int selection) //method to actually rewrite board after player selects the pit that they wish to distribute across the board. Array tells it what array to distribute across. Selection tells it where to start from. 
  {
    //take selection in array and distribute it across board
    int arrayNum = selection - 1;
    //get number of Moves or stone in hand
    int numStones = array[arrayNum];
    int lastPit = 0;
    //empty selected pit
    array[arrayNum] = 0;
    //while there is marbles in your hand and while still space in array, add plus 1 to array values until end of array or end of marbles
    while(numStones >0 && (array.length-selection)>0)
    {
      for (int i = selection; i<selection+1; i++)
      {array[i]++;
       lastPit = i;} //keep track of pit last added to
      //drop 1 stone in pit
      numStones--;
      //move to next pit to start over
      selection = selection+1;
    }
    //REACH END OF ARRAY
    //while loop, ++ to each array element from beginning of array for numStones left after adding 1 to store
    while(numStones >0) //while there are still stones to distribute
    {
      //if still stones left, add 1 to store if at last slot in array then go back to beginning
      if (numStones>0 && selection== 6)
      { 
          playStore++;
          numStones--;
          selection = 0;
      }
      else{ //else from start of array or selection, add 1 stone until no more left in hand
        for(int i = selection; i<selection+1; i++)
        {array[i]++;
          lastPit = i;}
        numStones--;
        selection = selection+1;
      }
    }
    //special move determination
     if (numStones == 0 && array[lastPit] == 1) //if the last pit you add a stone to was previously empty, grab the stones from the corresponding pit on the other side of the board and add them to your store.
       { 
         System.out.println("Special Move! Player "+playerName+" gets all stones in Computer players pit.");
         playStore = Mancala.specialMove(lastPit, compBoard, playStore); //(MYMETH)
       }
    return array;
  } //end distribute
 
  public static int[] computerDistribute(int[] array, int selection) //distribute across computer board using computer random selection. Moves opposite way than .distribute(). Array tells it wh.at array to distribute across, selection tells it where to start from
  {
    //selection in array # terms
    int arrayNum = selection - 1;
    //get number of Moves or stone in hand
    int numStones = array[arrayNum];
    //empty selected pit
    array[arrayNum] = 0;
    //keep track of lastPit added to
    int lastPit = 0;
    //while there is marbles in your hand and while still space in array, subtract 1 to array values until beginning of array or end of marbles
    while(numStones >0 && arrayNum-1 >= 0)
    {
      for (int i = arrayNum-1; i > arrayNum-2 ; i--)
      {array[i]++;
        lastPit = i;} 
      //drop 1 stone in pit
      numStones--;
      //move to next pit to start over
      arrayNum = arrayNum-1;
    }
    //if at beginning of array and still marbles in your hand, drop 1 in the store
    if (numStones>0 && arrayNum == 0 ){
      numStones--;
      compStore++;}
      //lastPit = 0;}
    //go back to the end of the array
    arrayNum = array.length-1;
    //while loop, ++ to each array element from end of array for numStones left after adding 1 to store
    while(numStones>0)
    {
      if(numStones > 0 && arrayNum == -1)
      { compStore++;
        numStones--;
        arrayNum = array.length-1;}
      else 
      {
        for(int i=arrayNum; i>arrayNum-1; i--)
        {array[i]++;
         lastPit = i;}
        numStones--;
        arrayNum = arrayNum-1
          ;
      }
    }
    //special move if landing in pit with 0 stones in it. Take all stones from corresponding pit on other side of baord and add them to your store
    if (numStones == 0 && array[lastPit] == 1)
       { 
         System.out.println("Special Move! Computer gets all stones in "+playerName+"'s pit.");
         compStore = Mancala.specialMove(lastPit, playBoard, compStore); //(MYMETH)
       }
    return array;
  }// end computer distribute
 
  public static boolean winnerCheck(int[] playerBoard, int[] computerBoard) //check if there is a winner at the end of a round of turns. Check playerBoard array and computerBoard array.
  { //boolean to track status of win reaching
    boolean checkWin = false;
    //intialize win count 
    int playCount = 0;
    int compCount = 0;
    //search both arrays to see if either 1 is empty. +1 for every zero in an array. If 6 is reached, someone has won. 
    for (int i = 0; i<6; i++)
    {
      if (playerBoard[i] == 0)
        playCount++;
    }
    for (int i = 0; i<6; i++)
    {
      if (computerBoard[i] == 0)
        compCount++;
    }
    //checkin boolean = true if someone has won.
    if (playCount == 6 || compCount == 6)
      checkWin = true; //(BOOL)
    return checkWin; //return checkWin to let game know if someone has won
  } // end winnerCheck
  
  public static void leaderBoardRead() throws IOException //read in the leaderBoard so that it can be edited, sorted, rewritten etc....
  {
    int i = 0;
    String tempName;
    int tempScore;
    File inputFile = new File("highScores.txt");
    Scanner scan = new Scanner(inputFile);
    //set object topScore = values read in from leaderBoard text File so it can be edited or sorted. But only the first 5. 
    while (scan.hasNext())
    { tempName = scan.next();
      tempScore = scan.nextInt();
      topScore.setName(i, tempName);
      topScore.setScore(i, tempScore);
      i++;
    }
    scan.close();
  }// end winnerCheck
  
  public static int leaderBoardSearch(String target) throws IOException //WORKING //search the object topScore which holds all leaderBoard values for a name = target. Return -1 if not found. Return Index if found. 
  {
    int index = -1;
    for (int i = 0; i<5; i++)
    {
      int nameCheck = (target.compareToIgnoreCase(topScore.getName(i)));
      if (nameCheck == 0)
      {
        index = i;
        break;
      }
      else 
        index = -1;
      }
     return index;
  }
  public static void sort(topScores t, String n, int s) //working as of 12/9/2016 1.59Pm //Sorts object topScore which holds leaderBoard values in two seperate arrays.Sorts the leaderBoard from greatest score to lowest. 
  {
    //select sort top scores
    //fill tempSpot in topScore object (slot 6 on leaderboard) with new name and score being read in by method
    int tempSpot = 5;
    t.setScore(tempSpot, s);
    t.setName(tempSpot, n);
    int min, tempScore;
    String tempName;
    //select sorting loop: based off of lab 19 select sorting code. 
    for (int startIndex = 0; startIndex<6;startIndex++)
    {
      min = startIndex;
      for (int i = startIndex-1; i>=0; i--)
      {
        //compares newScore to every value in the list
        if(t.getScore(i) < t.getScore(tempSpot))
        {
          min = i;
        }
      }
      //if new score is greater than any values at the end of the loop, it switches to whatever spot it belongs in. Does this until for loop is done. 
      tempScore = t.getScore(min);
      tempName = t.getName(min);
      t.setScore(min, t.getScore(startIndex));
      t.setName(min, t.getName(startIndex));
      t.setScore(startIndex, tempScore);
      t.setName(startIndex, tempName);
    }
  }
  
  public static void printSortedLeaderBoard(topScores t) throws IOException //printSorted array to a file from (hopefully) sorted topScores object.
  {
    File leaderBoard = new File("highScores.txt"); //(I/O)
    PrintWriter outputFile = new PrintWriter(leaderBoard);
    for (int i = 0; i<6; i++)
  { 
      outputFile.printf(t.getName(i) + " %d\n", t.getScore(i));
  }
    outputFile.close();
  }
  public static int specialMove(int i, int[] opposingBoard, int ownStore) //called when player lands in a pit with no stones in it, allows player to take from pit on otherside of board and add opposing players stones to own store
  {
    //grab value from opposing players board
    int stones = opposingBoard[i];
    //set opposing players pit equal to 0 because you took stones to add to your own store
    opposingBoard[i] = 0;
    //add those stones picked up to your own store
    ownStore+=stones;
    //return new store value
    return ownStore;
  }
}//end Mancala