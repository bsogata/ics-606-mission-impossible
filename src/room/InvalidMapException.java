package room;

/**
 * Indicates that a Room map is invalid.
 * 
 * Credit to Zach Tomaszewski as the original author of portions of this code.
 * 
 * @author Jack Lam
 * @author Branden Ogata
 * 
 */

public class InvalidMapException extends Exception
{

  public InvalidMapException()
  {
    super();
  }

  public InvalidMapException(String mesg)
  {
    super(mesg);
  }
}