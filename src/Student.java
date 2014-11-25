import agent.Agent;

/**
 * A generic Student agent.
 * 
 * @author Branden Ogata
 * 
 */

public class Student extends Agent
{
  boolean hasComputer;

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

}
