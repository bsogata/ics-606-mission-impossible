import java.util.LinkedList;


public class MessageQueue {
	public LinkedList<Message> queue = new LinkedList<Message>();
	
	
	public void sendMessage(Message message){
		queue.offer(message);
	}
	
	public LinkedList<Message> getMessages(){
		return queue;
	}
	
	public  void clearMessageQueue(){
		queue = new LinkedList<Message>();
	}
}
