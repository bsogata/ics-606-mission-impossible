package agent;

import java.awt.Point;
import room.Room;
import navigation.Direction;
import navigation.Pathfinder;
import agent.Agent;

/**
 * A generic Student agent.
 * 
 * @author Branden Ogata
 * 
 */

public class Student extends Agent
{
  /**
   * Indicates whether this Student has reached a computer on the map.
   * 
   */
  boolean hasComputer;

  /**
   * The internal map that this Student keeps.
   * 
   */
  private char[][] map;
  
  /**
   * The coordinates of the cell where this Student intends to move.
   * 
   */
  private Point target;
  
  /**
   * Creates a Student agent.
   * 
   * @param id The int equal to the ID number for this Student.
   * @param name The String equal to the name of this Student.
   * 
   */

  public Student(int id, String name)
  {
    super(id, name);
    this.hasComputer = false;
  }
  
  /**
   * Creates a Student agent.
   * 
   * @param id      The int equal to the ID number for this Student.
   * @param name    The String equal to the name of this Student.
   * @param map     The char[][] containing the initial map for this Student.
   * 
   */

  public Student(int id, String name, char[][] map)
  {
    super(id, name);
    this.map = map;
    this.hasComputer = false;
  }
  

  public void reset()
  {
    // TODO Auto-generated method stub

  }

  public Direction move(char[] s)
  {
    // TODO Auto-generated method stub

    Direction[] dirs = Direction.values();
    // don't want to pick the last dir though, since HERE means stop

    while (true)
    {
      int pickOne = (int) (Math.random() * dirs.length - 1);
      if ((s[dirs[pickOne].ordinal()] != Room.WALL) &&
          (s[dirs[pickOne].ordinal()] != Room.ENTRANCE))
      {
        return dirs[pickOne];
      }

      else
        continue;

    }
  }
  
  /**
   * Returns the best move for this Student to make.
   * 
   * Disclaimer: "Best move" may not actually be the best move.
   * 
   * @param row    The int equal to the row coordinate of this Student.
   * @param col    The int equal to the column coordinate of this Student.
   * 
   * @return A Direction that this Student should move in.
   * 
   */
  
  public Direction moveAgent(int row, int col)
  {
    // If a computer is visible and this Student has not yet reached a computer,
    // then set the target if it has not already been set
    if ((getComputerCoordinates() != null) && (!this.hasComputer) && (this.target == null))
    {
      this.target = getComputerCoordinates();
    }
    // Else if this Student has reached a computer, then set the target to the entrance
    else if ((this.hasComputer) && (this.target == null))
    {
      this.target = getEntranceCoordinates();
    }
    
    // Return the Direction to move
    return (this.target == null) ? 
           (Pathfinder.random(this.map, new Point(col, row)).get(0)) : 
           (Pathfinder.naive(this.map, new Point(col, row), this.target).get(0));
  }

  /**
   * Handy for double-checking that passed surroundings are valid (though the only reason they
   * wouldn't be is programmer error somewhere).
   * <p>
   * Returns if everything's fine, or throws an IllegalArgumentException if not.
   * 
   * @see #move
   */
  protected void validateSurroundings(char[] surroundings) throws IllegalArgumentException
  {
    if (surroundings.length != 8)
    {
      throw new IllegalArgumentException("Surroundings array the wrong length (" +
                                         surroundings.length + " instead of 8)");
    }
    for (int i = 0; i < surroundings.length; i++)
    {
      switch (surroundings[i])
      {
      // XXX: A Vroomba currently can't handle seeing another version of itself
        case Room.FLOOR:
        case Room.DIRT:
        case Room.WALL:
        case Room.DROP:
          break; // good
        default:
          throw new IllegalArgumentException("Invalid character in surroundings (" +
                                             surroundings[i] + ")");
      }
    }
    return; // array was fine
  }

  /**
   * Returns the current map stored in this Student.
   * 
   * @return A char[][] containing the current representation of the map for this Student.
   *  
   */
  
  public char[][] getMap()
  {
    return this.map;
  }

  /**
   * Sets the map for this Student.
   *
   * @param map    The char[][] equal to the map to set in this Student.
   *
   */
  
  public void setMap(char[][] map)
  {
    this.map = map;
  }

  /**
   * Returns the target of this Student.
   *
   * @return The Point equal to the target of this Student.
   *
   */
  
  public Point getTarget()
  {
    return this.target;
  }

  /**
   * Sets the target of this Student.
   *
   * @param target    The Point equal to the target to set in this Student.
   *
   */
  
  public void setTarget(Point target)
  {
    this.target = target;
  }
  
  /**
   * Returns the hasComputer of this Student.
   *
   * @return The boolean equal to the hasComputer of this Student.
   *
   */
  
  public boolean hasComputer()
  {
    return this.hasComputer;
  }

  /**
   * Sets the hasComputer of this Student.
   *
   * @param hasComputer    The boolean equal to the hasComputer to set in this Student.
   *
   */
  
  public void setHasComputer(boolean hasComputer)
  {
    this.hasComputer = hasComputer;
  }

  /**
   * Returns the location of a computer in the map for this Student.
   * 
   * @return A Point containing the coordinates of the computer in this map;
   *         null if the computer does not exist or has not been found yet.
   *         
   */
  
  private Point getComputerCoordinates()
  {
    Point location = null;
    
    for (int i = 0; i < this.map.length; i++)
    {
      for (int j = 0; j < this.map[i].length; j++)
      {
        if (this.map[i][j] == Room.COMPUTER)
        {
          location = new Point(j, i);
        }
      }
    }
    
    return location;
  }
  
  /**
   * Returns the location of the entrance in the map for this Student.
   * 
   * @return A Point containing the coordinates of the entrance in this map;
   *         null if the entrance does not exist or has not been found yet.
   *         
   */
  
  private Point getEntranceCoordinates()
  {
    Point location = null;
    
    for (int i = 0; i < this.map.length; i++)
    {
      for (int j = 0; j < this.map[i].length; j++)
      {
        if (this.map[i][j] == Room.ENTRANCE)
        {
          location = new Point(j, i);
        }
      }
    }
    
    return location;
  }  
}