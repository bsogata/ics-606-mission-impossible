import java.util.LinkedList;


public class MessageQueue {
	public LinkedList<Message> queue = new LinkedList<Message>();
	
	public MessageQueue(){
		queue = new LinkedList<Message>();
	}
	
	public MessageQueue(MessageQueue another){
		this.queue = another.queue;
	}
	
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
