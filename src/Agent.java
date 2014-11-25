

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
  private int id;
  
  /**
   * The name of this Agent.
   * 
   */
  private String name;
  
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
}
