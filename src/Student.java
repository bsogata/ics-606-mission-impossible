
 /*****************************************************************
     Simple1.java:  Minimal agent with anonymous behaviour
 *****************************************************************/
     
 import jade.core.Agent;
import jade.core.behaviours.*;
     
/**
 * A simple student agent.
 * 
 * @author Jack Lam
 * @author Branden Ogata
 *
 */

 public class Student extends Agent 
 {       
     protected void setup() 
     {
         addBehaviour(  // -------- Anonymous SimpleBehaviour 
 
             new SimpleBehaviour( this ) 
             {
                 int n=0;
                 
                 public void action() 
                 {
                     System.out.println( "Hello World! My name is " + 
                         myAgent.getLocalName() );
                     n++;
                 }
         
                 public boolean done() {  return n>=3;  }
             }
         );
     }   //  --- setup ---

	public void reset() {
		// TODO Auto-generated method stub
		
	}

	public Direction move(char[] s) {
		// TODO Auto-generated method stub

		Direction[] dirs = Direction.values();
		//don't want to pick the last dir though, since HERE means stop
		while (true)
		{  
			int pickOne = (int) (Math.random() * dirs.length - 1);
			if ((s[dirs[pickOne].ordinal()] != Room.WALL) && (s[dirs[pickOne].ordinal()] != Room.DROP))
			{	
				return dirs[pickOne];
			}

			else continue;

		}
	}
	

	  /**
	   * Handy for double-checking that passed surroundings are valid
	   * (though the only reason they wouldn't be is programmer error somewhere).
	   * <p>
	   * Returns if everything's fine, or throws an IllegalArgumentException if not.
	   *
	   * @see #move
	   */
	  protected void validateSurroundings(char[] surroundings)
	                                     throws IllegalArgumentException {
	    if (surroundings.length != 8) {
	      throw new IllegalArgumentException("Surroundings array the wrong length (" +
	                                         surroundings.length + " instead of 8)");
	    }
	    for (int i = 0; i < surroundings.length; i++) {
	      switch (surroundings[i]) {
	        //XXX: A Vroomba currently can't handle seeing another version of itself
	        case Room.FLOOR:
	        case Room.DIRT:
	        case Room.WALL:
	        case Room.DROP:
	          break;  //good
	        default:
	          throw new IllegalArgumentException("Invalid character in surroundings (" +
	                                            surroundings[i] + ")");
	      }
	    }
	    return; //array was fine
	  }


 }   //  --- class Simple1