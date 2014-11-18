package message;

import java.util.ArrayList;
import java.util.List;

/**
 * Stores a single message from one agent to another.
 * 
 * It is suggested that these be stored inside a Deque<Message>, allowing developers to use queue 
 * operations (ex. messages.offer(new Message(...)), messages.poll()).
 * 
 * The fields in this class are immutable on the basis that words cannot be taken back once spoken
 * in the real world, so once a Message is set up it cannot be modified.
 * 
 * @author Branden Ogata
 *
 */

public class Message 
{
  /**
   * The data being transferred.
   * 
   * For convenience, we may assume messages to be in the format:
   *   (x,y):subject:request
   * where x and y are the coordinates of the subject and request is an optional directive for the
   * agents to respond to.
   * 
   */
  private String content;
  
  /**
   * The ID of the team that should receive the message.
   * 
   */
  private int teamId;
  
  /**
   * The ID of the sender.
   * 
   */
  private int senderId;
  
  /**
   * The intended recipients of the message.
   * 
   * Everyone on the team may still receive the message.
   * 
   */
  private List<Integer> recipients;
  
  /**
   * Creates a new Message.
   * 
   * @param content       The String containing the content of the message to send.
   * @param teamId        The int equal to the ID number of the receiving team.
   * @param senderId      The int equal to the ID number of the sending agent.
   * @param recipients    The List<Integer> containing the ID numbers of the intended recipients.
   * 
   */
  
  public Message(String content, int teamId, int senderId, List<Integer> recipients)
  {
    this.content = content;
    this.teamId = teamId;
    this.senderId = senderId;
    this.recipients = new ArrayList<Integer>(recipients);
  }

  /**
   * Returns the content of this Message.
   * 
   * @return A String containing the content of this Message.
   * 
   */
  
  public String getContent()
  {
    return content;
  }

  /**
   * Returns the ID of the team to receive this Message.
   * 
   * @return An int containing the ID of the team for this Message.
   * 
   */
  
  public int getTeamId()
  {
    return teamId;
  }

  /**
   * Returns the ID of the agent sending this Message.
   * 
   * @return An int containing the ID of the sender of this Message.
   * 
   */

  public int getSenderId()
  {
    return senderId;
  }

  /**
   * Returns the intended recipients of this Message.
   * 
   * @return A List<Integer> containing the ID number of the recepients of this Message.
   * 
   */
  
  public List<Integer> getRecipients()
  {
    return recipients;
  }
  
  
}