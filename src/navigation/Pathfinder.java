package navigation;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import room.Room;

/**
 * A collection of pathfinding methods.
 * 
 * @author Branden Ogata
 *
 */

public class Pathfinder
{
  /**
   * A random approach that only looks one move ahead.
   * 
   * @param map      The char[][] containing the individual map for the agent to plan for.
   * @param agent    The Point containing the coordinates of the agent to plan for.
   * 
   * @return A List<Direction> containing the Direction that this algorithm suggests. 
   * 
   */
  
  public static List<Direction> random(char[][] map, Point agent)
  {
    List<Direction> nextMove = new ArrayList<Direction>();
    
    // Create a list of directions that the agent can move in
    List<Direction> possibleMoves = new ArrayList<Direction>();
    
    for (Direction d : Direction.values())
    {
      int newRow = (int) agent.getY() + d.getRowModifier();
      int newCol = (int) agent.getX() + d.getColModifier();
      
      if ((0 <= newRow) && (newRow < map.length) && 
          (0 <= newCol) && (newCol < map[newRow].length) && 
          (map[newRow][newCol] != Room.WALL))
      {
        possibleMoves.add(d);
      }
    }
    
    // Get the best option from the available options
    nextMove.add(getBestMove(possibleMoves, agent, null, Heuristic.RANDOM));
    
    return nextMove;     
  }
  
  
  /**
   * A naive approach that only looks one move ahead.
   * 
   * @param map      The char[][] containing the individual map for the agent to plan for.
   * @param agent    The Point containing the coordinates of the agent to plan for. 
   * @param goal     The Point containing the coordinates of the intended destination.
   * 
   * @return A List<Direction> containing the Direction that this algorithm suggests.
   * 
   */
  
  public static List<Direction> naive(char[][] map, Point agent, Point goal)
  {
    List<Direction> nextMove = new ArrayList<Direction>();
    
    // Create a list of directions that the agent can move in
    List<Direction> possibleMoves = new ArrayList<Direction>();
    
    for (Direction d : Direction.values())
    {
      if (d != Direction.HERE)
      {
        int newRow = (int) agent.getY() + d.getRowModifier();
        int newCol = (int) agent.getX() + d.getColModifier();
        
        if ((0 <= newRow) && (newRow < map.length) && 
            (0 <= newCol) && (newCol < map[newRow].length) &&
            (map[newRow][newCol] != Room.WALL))
        {
          possibleMoves.add(d);
        }
      }
    }
    
    // Shuffle for some randomness
    Collections.shuffle(possibleMoves);
    
    // Get the best option from the available options
    nextMove.add(getBestMove(possibleMoves, agent, goal, Heuristic.NAIVE));
    
    return nextMove; 
  }
  
  /**
   * Returns the move with the highest heuristic value.
   * 
   * @param candidates    The List<Direction> containing the possible moves to choose from.
   * @param agent         The Point containing the coordinates of the agent.
   * @param goal          The Point containing the coordinates of the target.
   * @param heuristic     The Heuristic to use.
   * 
   * @return A Direction that is theoretically optimal.
   * 
   */
  
  private static Direction getBestMove(List<Direction> candidates, Point agent, 
                                       Point goal, Heuristic heuristic)
  {
    double heuristicValue = Double.MAX_VALUE;
    Direction bestMove = null;
    
    String debugOutput = "";
    
    switch (heuristic)
    {
      case RANDOM:
        bestMove = candidates.get((int) (Math.random() * candidates.size()));
        break;
      case NAIVE:
        for (Direction d : candidates)
        {
          int newRow = (int) agent.getY() + d.getRowModifier();
          int newCol = (int) agent.getX() + d.getColModifier();
          
          double currentHeuristicValue = manhattan(new Point(newCol, newRow), goal);
          
          debugOutput += String.format("Direction %s from %s to %s : %f\n", 
                                       d.toString(), agent, goal, currentHeuristicValue);
          
          if (currentHeuristicValue < heuristicValue)
          {
            bestMove = d;
            heuristicValue = currentHeuristicValue;
          }
        }
        
        break;
      case A_STAR:
        break;
      default: 
        break;
    }
    
    System.out.println(debugOutput);
    return bestMove;
  }
  
  /**
   * Calculates the Manhattan distance between two Points.
   * 
   * @param start    The Point containing the coordinates of the first point to evaluate.
   * @param end      The Point containing the coordinates of the second point to evaluate.
   * 
   * @return A double equal to the Manhattan distance between the two given Point instances.
   * 
   */
  
  private static double manhattan(Point start, Point end)
  {
    return (end.getX() - start.getX()) + (end.getY() - start.getY());
  }
}