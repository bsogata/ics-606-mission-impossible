import java.util.ArrayList;


/**
 * 
 * A helper class to help make the number and type of agents. 
 * @author Jack
 *
 */
public class AgentRunnerHelper {

/**
 * Modify inside of this method to change the number and type of agents to use.
 * 
 * @return - list of agents to use in program
 */
public static ArrayList<Agent>  makeAgents(){
	ArrayList<Agent> studentList = new ArrayList<Agent>();
	
	
	for(int i =0; i<10; i++){
		studentList.add(new Student(i, 1, "a"));
	  }
	
	return studentList;
}
	  
}
