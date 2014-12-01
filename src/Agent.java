

/**
 * The template for non-JADE agents.
 * 
 * @author Branden Ogata
 *
 */

public abstract class Agent
{
  /**
   * The unique ID number for this Agent.
   * 
   * This developer is not sure how to enforce uniqueness from within an individual agent.
   * 
   */
  protected int id;
  
  /**
   * The name of this Agent.
   * 
   */
  protected String name;
  
  protected int teamid;
  
  /**
   * Creates a new Agent.
   * 
   * @param id      The int equal to the ID number for this Agent.
   * @param name    The String equal to the name of this Agent.
   * 
   */
  
  public Agent(int id, String name)
  {
    this.id = id;
    this.name = name;
  }
  
  public Agent(int id, int teamid, String name){
	  this.id = id;
	  this.teamid = teamid;
	  this.name = name;
  }

  /**
   * Returns the id of this Agent.
   *
   * @return The int equal to the id of this Agent.
   * 
   */
  
  public int getId()
  {
    return this.id;
  }

  /**
   * Sets the id of this Agent.
   *
   * @param id    The int equal to the id to set in this Agent.
   *
   */
  
  public void setId(int id)
  {
    this.id = id;
  }

  /**
   * Returns the name of this Agent.
   *
   * @return The String equal to the name of this Agent.
   * 
   */
  
  public String getName()
  {
    return this.name;
  }

  /**
   * Sets the name of this Agent.
   *
   * @param name    The String equal to the name to set in this Agent.
   *
   */
  
  public void setName(String name)
  {
    this.name = name;
  }

  /**
   * Determines what move this Agent will make.
   * 
   * This is left for subclasses to implement.
   * 
   */
  
  public int getTeamid(){
	  return teamid;
  }
  
  public void setTeamid(int teamid){
	  this.teamid = teamid;
  }
  
  /**
   * Returns the hash code for this Agent.
   * 
   * @return An int equal to the hash code for this Agent.
   * 
   */
  
  @Override
  public int hashCode()
  {
    final int prime = 31;
    int result = 1;
    result = prime * result + id;
    return result;
  }

  /**
   * Compares this Agent to another Object for equality.
   * 
   * @param obj    The Object to compare this Agent to.
   * 
   * @return A boolean that is true if the parameter is equal to this Agent,
   *                           false otherwise.
   * 
   */
  
  @Override
  public boolean equals(Object obj)
  {
    boolean isEqual = false;
    
    if (this == obj)
    {
      isEqual = true;
    }
    else if (obj instanceof Agent)
    {
      Agent temp = (Agent) obj;
      
      isEqual = this.id == temp.id;
    }
    
    return isEqual;
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

}
