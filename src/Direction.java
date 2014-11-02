
public enum Direction {
  N, NE, E, SE, S, SW, W, NW, HERE;

  /**
   * Returns the X/column change on the screen that is associated with
   * this direction: -1 for W, 0 for N/S, and +1 for E.
   */
  public int getColModifier() {
    int mod;
    switch (this) {
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
   * Returns the Y/row change on the screen that is associated with
   * this direction: -1 for N, 0 for E/W, and +1 for south.
   */
  public int getRowModifier() {
    int mod;
    switch (this) {
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
  public int getXModifier() {
    return this.getColModifier();
  }

  /** As {@link #getRowModifier()} */
  public int getYModifier() {
    return this.getRowModifier();
  }


  public Direction reverse() {
    if (this == HERE) {
      return this;
    }else {
      int reversed = (this.ordinal() + 4) % 8;
      Direction[] dirs = Direction.values();
      return dirs[reversed];
    }
  }

}