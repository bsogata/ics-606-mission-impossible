import java.awt.Color;
import java.awt.GridLayout;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;


public class Room {

	public static final char FLOOR = ' ';
	  public static final char COMPUTER = 'C';
	  public static final char WALL = '#';
	  public static final char DROP = '^';
	  public static final char STUDENT = 'S';
	  public static final char DIRT = '.';
	  
	  
	  private int moves = 0;   //state regarding the Agent's recent activities
	  private int collisions = 0;
	  private int repeatCollisions = 0;
	  
	  private Direction lastMoveIfCollision = null; //if null, last move was not a crash
	  private Direction lastMove;  //direction of last move, regardless of its result
	  
	  private char[][] room;   //the map of this room
	  private int roomWidth;
	  private int roomHeight;
	  private int floorSpaces; //number of floor squares on the map
	  
	  private Result done = null;  //will be null until cleaning is done

	  private Student agent; //agent stuff
	  private int agentRow;  //current agent location on room map,
	  private int agentCol;  // where top-left square of Room is (0,0)

	  public Room(Student v) {
		roomHeight = 10;
		roomWidth = 10;
		room = new char[roomHeight][roomWidth];
		for(int i =0; i< roomHeight; i++){
			room[0][i] = WALL;
			room[i][0] = WALL;
			room[roomHeight-1][i] = WALL;
			room[i][roomWidth-1] = WALL;
		}
		
		for(int i=1; i< roomHeight-1; i++){
			for(int j = 1; j <roomWidth-1; j++){
				room[i][j] = FLOOR;
				
			}
		}
		
		room[1][1] = STUDENT;
		this.agent = v;
	}
	  public Room(char[][] map, Student v) throws InvalidMapException {
		    this.loadMap(map);
		    this.agent = v;
		  }
	  
	  public Room(String mapFilename, Student v) throws FileNotFoundException,
      InvalidMapException {

try
{
// Open file to read
Scanner filein = new Scanner(new File(mapFilename));

// Read in file to ArrayList
ArrayList<String> contents = new ArrayList<String>();
while (filein.hasNextLine())
{
contents.add(filein.nextLine());
}

char[][] map = new char[contents.size()][];
for (int i = 0; i < contents.size(); i++) {
map[i] = contents.get(i).toCharArray();
}

this.loadMap(map);  //change map here to be the name of your char[][]
this.agent = v;

}

catch (FileNotFoundException fnfe)
{
System.out.println("Could not open file: " + fnfe.getMessage());
}

}
	  
	  
	  
	  private void loadMap(char[][] map) throws InvalidMapException {
		    //get and check room dimensions
		    this.roomHeight = map.length;
		    this.roomWidth = (map.length > 0) ? map[0].length : 0;
		    if (roomHeight == 0 || roomWidth == 0) {
		      throw new InvalidMapException("One of the room dimensions is 0.");
		    }

		    //going to create a copy of the passed map to encapsulate it
		    //(so it can't be changed by caller after constructing a Room)
		    this.room = new char[roomHeight][roomWidth];
		    this.floorSpaces = 0;

		    //load room, validating as we go
		    boolean placeAgent = false;
		    for (int row = 0; row < roomHeight; row++) {  //for each row...
		      if (map[row].length != roomWidth) {
		        throw new InvalidMapException("Not all rows of the map " +
		                                      "are the same length as the first.");
		      }

		      for (int col = 0; col < roomWidth; col++) {  //for each col in this row...
		        //ensure this is a supported character
		        switch (map[row][col]) {
		          case STUDENT:
		            if (placeAgent) {
		              throw new InvalidMapException("Map contains more than one Vroomba.");
		            }else {
		              this.agentCol = col;
		              this.agentRow = row;
		              placeAgent = true;
		            }
		            //fall thru (no break)
		          case FLOOR:
		          case DIRT:
		            this.floorSpaces++;
		          case WALL:
		          case DROP:
		            //copy any of the above cases to the local map
		            room[row][col] = map[row][col];
		            break;
		          default:
		            throw new InvalidMapException("Map contains an unsupported " +
		                                          "character (" + map[row][col] + ")");
		        }
		      }
		    }
	  }



	public class GUI extends JPanel {

		    private JLabel[][] guiMap;  //a graphical map of this room

		    public GUI() {
		      //form a graphical copy of the room map, and load into a board to display
		      guiMap = new JLabel[roomHeight][roomWidth];
		      this.setLayout(new GridLayout(roomHeight, roomWidth));

		      for (int row = 0; row < roomHeight; row++) {
		        for (int col = 0; col < roomWidth; col++) {
		          JLabel square = new JLabel("" + room[row][col]);
		          square.setHorizontalAlignment(SwingConstants.CENTER);
		          square.setOpaque(true);
		          if (room[row][col] == Room.DIRT) {
		            square.setBackground(new Color(0xFF, 0xFF, 0x99));
		          }else if (room[row][col] == Room.STUDENT) {
		            square.setBackground(new Color(0xCC, 0xCC, 0xCC));
		          }else {
		            square.setBackground(Color.white);
		          }
		          guiMap[row][col] = square;
		          this.add(guiMap[row][col]);
		        }
		      }
		    }

		    /**
		     * Updates this GUI view to represent the state of the underlying Room.
		     */
		   public void update() {
		      for (int row = 0; row < roomHeight; row++) {
		        for (int col = 0; col < roomWidth; col++) {
		          guiMap[row][col].setText("" + room[row][col]);
		          if (room[row][col] == Room.STUDENT) {
		            //darken the color of this square if necessary
		            Color color = guiMap[row][col].getBackground();
		            if (color.getRed() > 0) {
		              //darken, keeping in grey scale
		              int darker = color.getRed() - 0x33;
		              color = new Color(darker, darker, darker);
		              guiMap[row][col].setBackground(color);
		              if (darker < 0x66) {
		                guiMap[row][col].setForeground(Color.white);
		              }
		            }
		          }
		        }
		      }
		    }
		  }
	  
	  


	public boolean moveAgent() {
		 //first, construct a snapshot of the agent's surroundings
	    char[] surround = new char[8];
	    for (Direction d : Direction.values()) {
	      if (d != Direction.HERE) {
	        surround[d.ordinal()] = this.getFeature(d);
	      }
	    }

	    //now ask agent to move
	    Direction move = this.agent.move(surround);
	    this.lastMove = move;
	    this.moves++;

	    //and see where that takes us...
	    if (move == Direction.HERE) {
	      //agent chose to stop
	      this.moves--;  //oops, not actually a move
	      done = (this.isClean()) ? Result.POWER_OFF_CLEAN : Result.POWER_OFF_DIRTY;
	      if (this.moves == 0 && done == Result.POWER_OFF_DIRTY) {
	        done = Result.INOPERATIVE;
	      }
	      return false;

	    }else if (this.getFeature(move) == WALL) {
	      //agent crashed
	      this.collisions++;
	      if (this.lastMoveIfCollision == move) {
	        //moved this way and crashed last turn too
	        this.repeatCollisions++;
	        if (this.repeatCollisions >= 3) {
	          done = Result.REPEATED_CRASH;
	          return false;
	        }
	      }else {
	        //starting a fresh sequence of collisions
	        this.repeatCollisions = 1;
	      }
	      this.lastMoveIfCollision = move;
	      //collided, but not enough to be done yet: return below

	    }else if (this.getFeature(move) == DROP) {
	      done = Result.DROP;
	      return false;

	    }else {
	      //actually moved successfully!
	      //first, leaves clean floor behind...
	      this.room[agentRow][agentCol] = FLOOR;
	      //...then moves...
	      this.agentRow += move.getRowModifier();
	      this.agentCol += move.getColModifier();
	      //...to new location on map
	      this.room[agentRow][agentCol] = STUDENT;

	      //clear the collision streak tracking
	      this.lastMoveIfCollision = null;
	      //valid move, so return below
	    }


	      return true; 
	    
	}

	 public boolean isClean() {
		//IMPLEMENT THIS
		    //(Hint: you can implement this with a single line (or two) of code.)

				if (count('.') == 0) return true;
				else return false;
				
		   
		  }
	 protected char getFeature(int row, int col) {
		    if (row < 0 || row >= this.roomHeight ||
		        col < 0 || col >= this.roomWidth) {
		      //this is off the map, so treat as a wall
		      return WALL;
		    }else {
		      return this.room[row][col];
		    }
		  }
	 
	  protected char getFeature(Direction d) {
		    int row = this.agentRow + d.getRowModifier();
		    int col = this.agentCol + d.getColModifier();
		    return this.getFeature(row, col);
		  }
	 
	  protected int count(char feature) {
		    int count = 0;
		    for (int row = 0; row < roomHeight; row++) {
		      for (int col = 0; col < roomWidth; col++) {
		        if (this.room[row][col] == feature) {
		          count++;
		        }
		      }
		    }
		    return count;
		  }
	 
	  public String getStatus() {
		    return this.getStatus(false);
		  }

	  public String getStatus(boolean asMove) {
		    String status = "";
		    if (this.done == null || asMove) {
		      status += "CLEANING: " + this.lastMove;
		    }else {
		      status += "RESULT: " + this.done;
		    }
		    status += "\t Moves: " + this.moves;
		    status += "\t Collisions: " + this.collisions + "\n";
		    return status;
		  }
	  
	  
	  public Room.Result getResult() {
		    return this.done;
		  }

/**
 * An enumeration of the various possible outcomes of a agent
 * cleaning a room.
 */
	  public enum Result {
		  /** Room has more than one floor space, but agent never moved at all */
		  INOPERATIVE,
		  /** agent repeated the same collision 3 times in a row */
		  REPEATED_CRASH,
		  /** agent fell over a DROP */
		  DROP,
		  /** agent turned off, but room was not yet clean */
		  POWER_OFF_DIRTY,
		  /** agent exceeded max number of moves allowed and room still dirty */
		  TIME_OUT_DIRTY,
		  /** agent exceeded max number of moves allowed, but room is clean */
		  TIME_OUT_CLEAN,
		  /** agent turned off because room was clean */
		  POWER_OFF_CLEAN;
		}
  
}
/**
 * Indicates that a Room map is invalid.
 */
class InvalidMapException extends Exception {

  public InvalidMapException() {
    super();
  }
  public InvalidMapException(String mesg) {
    super(mesg);
  }
}



