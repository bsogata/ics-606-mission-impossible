package navigation;
/**
 * The various directions to travel in this simulation.
 * 
 * Credit to Zach Tomaszewski as the original author of portions of this code.
 * 
 * @author Jack Lam
 * @author Branden Ogata
 * 
 */

public enum Direction
{
  N, NE, E, SE, S, SW, W, NW, HERE;

  /**
   * Returns the X/column change on the screen that is associated with this Direction.
   * 
   * -1 for W, 0 for N/S, and +1 for E.
   * 
   * @return An int equal to the change in X from moving in this Direction.
   * 
   */

  public int getColModifier()
  {
    int mod;
    switch (this)
    {
      case NW:
      case W:
      case SW:
        mod = -1;
        break;
      case NE:
      case E:
      case SE:
        mod = +1;
        break;
      default:
        mod = 0;
        break;
    }
    return mod;
  }

  /**
   * Returns the Y/row change on the screen that is associated with this direction.
   * 
   * -1 for N, 0 for E/W, and +1 for south.
   * 
   * @return An int equal to the change in Y from moving in this Direction.
   * 
   */

  public int getRowModifier()
  {
    int mod;
    switch (this)
    {
      case N:
      case NE:
      case NW:
        mod = -1;
        break;
      case S:
      case SE:
      case SW:
        mod = +1;
        break;
      default:
        mod = 0;
        break;
    }
    return mod;
  }

  /** As {@link #getColModifier()} */
  public int getXModifier()
  {
    return this.getColModifier();
  }

  /** As {@link #getRowModifier()} */
  public int getYModifier()
  {
    return this.getRowModifier();
  }

  /**
   * Returns the Direction opposite of this Direction.
   * 
   * @return A Direction in the opposite Direction of this Direction.
   * 
   */

  public Direction reverse()
  {
    if (this == HERE)
    {
      return this;
    }
    else
    {
      int reversed = (this.ordinal() + 4) % 8;
      Direction[] dirs = Direction.values();
      return dirs[reversed];
    }
  }

}