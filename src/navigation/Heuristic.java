package navigation;

/**
 * A listing of heuristic algorithms to use.
 * 
 * @author Branden Ogata
 *
 */

public enum Heuristic
{
  /**
   * A pseudorandom approach.
   * 
   */
  RANDOM,
  
  /**
   * A naive approach that only looks one step ahead with Manhattan distance.
   * 
   */
  NAIVE,
  
  /**
   * Uses A* to find a complete path to the destination.
   * 
   */
  A_STAR;
}
