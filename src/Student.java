import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Random;





/**
 * A Student agent that will only visit non-visited cells. Backtrack if there are cells that are
 * visited.
 * 
 * Note that there is actually a bug in the agents not updating their maps.
 * However, because this is a backtrack algorithm, we don't want them update
 * their maps to visited squares because they'll backtrack and then do absolutely nothing
 * while one agent does all the work.
 * 
 * @author Jack
 *
 */

public class Student extends Agent {
	private boolean hasComputer;
	 private ArrayList<Square> map = new ArrayList<Square>();
		private int x=0;
		private int y=0;
		private int a = x+1;
		private int b = x-1;
		private int c = y+1;
		private int d = y-1;
		private int r = 0;
		private int w = 0;
		private Direction lastMove = Direction.HERE;
		Random generator = new Random();
		private int pickOne = generator.nextInt(8);

		private ArrayList<Direction> history = new ArrayList<Direction>(); //backtracking

		
		private Message sendMessage;
		private MessageQueue receiveMessages;
		
		private ArrayList<Square> pathToHome = null;
		private boolean returnHome = false;
		private int counterForHome = 0;
		
		private boolean iAmDone;
		

	
  /**
   * Creates a Student agent.
   * 
   * @param id      The int equal to the ID number for this Student.
   * @param name    The String equal to the name of this Student.
   * 
   */
  
  public Student(int id,int teamid, String name) {
    super(id,teamid, name);
  }
   
	
	public Direction move(char[] s) {
		Direction[] dirs = Direction.values();
		//send maps to other agents
		sendMessage = new Message(map, 1, id);
		Room.nextMessageQueue.sendMessage(sendMessage);
		if(returnHome == true){
			Room.nextMessageQueue.sendMessage(new Message(true, 1, id));
		}
		
		
		//check messages
		receiveMessages = new MessageQueue(Room.currentMessageQueue);
		
		//Check if we can go home
		for(int i=0; i<receiveMessages.getMessages().size(); i++){
			if(receiveMessages.getMessages().get(i).getReturnHome() == true){
				returnHome = true;
				break;
			}
		}
		
		
	
			
			//Synchronize map with new information from agents
			for(int i=0;i<receiveMessages.getMessages().size(); i++){
				if(receiveMessages.getMessages().get(i).getSenderId() == id){
					continue; //I sent this message, don't need to read myself
				}
				 ArrayList<Square> temp = receiveMessages.getMessages().get(i).getMap();
				 if(temp != null){
					 for(int j=0;j<temp.size(); j++){
						 boolean addToMap = true;
						 for(int k=0; k<map.size(); k++){
							 //check if I already have this in my map
							 if(map.get(k).equals(temp.get(j))){
							 	addToMap = false;
							 	//I already have it in my map. update my own map though if it got visited.
							 	
							 	//TO FIX BUG, UNCOMMENT THIS SECTION
							 	//if((temp.get(j).wasVisited() == true) && (map.get(k).wasVisited() == false)){
							 	//	map.get(k).setToVisit();
							 	//}
								 break;
							 }
						 }
						 if(addToMap == true){
							 map.add(temp.get(j));
						 }
					 }
				 }
			}
			
			
			a = x+1;
			b = x-1;
			c = y+1;
			d = y-1;
			
			//Checks to see if the squares around Agent are in the map
			if (!map.contains(getSquare(x,c))) map.add(new Square(x,c,false, false));
			if (!map.contains(getSquare(x,d))) map.add(new Square(x,d,false, false));
			if (!map.contains(getSquare(a,y))) map.add(new Square(a,y,false, false));
			if (!map.contains(getSquare(b,y))) map.add(new Square(b,y,false, false));
			if (!map.contains(getSquare(a,c))) map.add(new Square(a,c,false, false));
			if (!map.contains(getSquare(a,d))) map.add(new Square(a,d,false, false));
			if (!map.contains(getSquare(b,c))) map.add(new Square(b,c,false, false));
			if (!map.contains(getSquare(b,d))) map.add(new Square(b,d,false, false));
			
			//Checks to see if the squares around Agent are obstacles.  If they are,
			//sets those squares to be obstacles.
			if ((s[dirs[0].ordinal()] == Room.WALL))
				{
					getSquare(x,d).setToObstacle();
				}
			if ((s[dirs[1].ordinal()] == Room.WALL))
				{
					getSquare(a,d).setToObstacle();
				}
			if ((s[dirs[2].ordinal()] == Room.WALL) )
				{
					getSquare(a,y).setToObstacle();
				}
			if ((s[dirs[3].ordinal()] == Room.WALL) )
				{
					getSquare(a,c).setToObstacle();
				}
			if ((s[dirs[4].ordinal()] == Room.WALL) )
				{
					getSquare(x,c).setToObstacle();
				}
			if ((s[dirs[5].ordinal()] == Room.WALL) )
				{
					getSquare(b,c).setToObstacle();
				}
			if ((s[dirs[6].ordinal()] == Room.WALL) )
				{
					getSquare(b,y).setToObstacle();
				}
			if ((s[dirs[7].ordinal()] == Room.WALL) )
				{
					getSquare(b,d).setToObstacle();
				}
			
			if(returnHome == false){
				//Checks to see if the Agent is surrounded by visited squares and
				//obsticles.  If it is, it will backtrack its last square.	
				if((getSquare(x,c).wasVisited() || getSquare(x,c).isObstacle()) && 
						(getSquare(x,d).wasVisited()|| getSquare(x,d).isObstacle()) && 
						(getSquare(a,y).wasVisited()|| getSquare(a,y).isObstacle()) && 
						(getSquare(b,y).wasVisited()|| getSquare(b,y).isObstacle()) && 
						(getSquare(a,c).wasVisited()|| getSquare(a,c).isObstacle()) && 
						(getSquare(a,d).wasVisited()|| getSquare(a,d).isObstacle()) && 
						(getSquare(b,c).wasVisited()|| getSquare(b,c).isObstacle()) && 
						(getSquare(b,d).wasVisited()|| getSquare(b,d).isObstacle())) {			


					if(history.size() == 0){
						return Direction.HERE;
					}
					lastMove = history.get(history.size()-1).reverse();
					history.remove(history.size()-1);
					x = x+lastMove.getXModifier();
					y = y+lastMove.getYModifier();
					getSquare(x,y).setToVisit();
					return lastMove;

				}

			else {	
				//Randomly picks a square that the Agent has not visited yet and goes there.
				//Also changes the square to Visited.
				while(true){
					for(int i=0; i<dirs.length -1; i++){
						  if (s[dirs[i].ordinal()] == Room.COMPUTER ){
							  hasComputer = true;
							  returnHome = true;
							  //Send message to all agents to go home
							  Room.nextMessageQueue.sendMessage(new Message(true, 1, id));
							  x = x+dirs[i].getXModifier();
							  y = y + dirs[i].getYModifier();
							  return dirs[i];
						  }
					}
					
				 	pickOne = generator.nextInt(8);

					if ((s[dirs[pickOne].ordinal()] != Room.WALL))
					{
						 r = x+dirs[pickOne].getXModifier();
						 w = y+dirs[pickOne].getYModifier();
						if (!getSquare(r,w).wasVisited())
						{				
							getSquare(x,y).setToVisit();
							x = x+dirs[pickOne].getXModifier();
							y = y+dirs[pickOne].getYModifier();
							getSquare(x,y).setToVisit();
							history.add(dirs[pickOne]);
							return dirs[pickOne];	
						}		
					} 
						else continue;
					}	
			}
		}
		else{ //Perform A* search to return home
			//Square currentLocation = getCurrentSquare();
			
			Square currentLocation = new Square(x,y, false, false);
			//int col = currentLocation.getX();
			//int row = currentLocation.getY();
			
			int col = x;
			int row = y;

			if(pathToHome == null){
				pathToHome = aStarSearch(currentLocation, map.get(0));
				return Direction.HERE;
			}
			else{
				
				if(x == 0 && y == 0){
					//System.out.println("I am done " + id);
					iAmDone = true;
					return Direction.HERE;
				}
				
				Direction nextMove;				
				int nextCol = pathToHome.get(counterForHome).getX();
				int nextRow = pathToHome.get(counterForHome).getY();
				
				int northOrSouth = row - nextRow;
				int westOrEast = col - nextCol;
	
				
				if(northOrSouth > 0){ //Go North
					if(westOrEast < 0){ //Go East
						x = x + Direction.NE.getXModifier();
						y = y + Direction.NE.getYModifier();
						nextMove = Direction.NE; 
					}
					else if(westOrEast > 0){//Go west
						x = x + Direction.NW.getXModifier();
						y = y + Direction.NW.getYModifier();
						nextMove = Direction.NW;
					}
					else{ //just go north
						x = x + Direction.N.getXModifier();
						y = y + Direction.N.getYModifier();
						nextMove = Direction.N;
					}
				}
				else if (northOrSouth < 0){ //South
					if(westOrEast < 0){ //Go East
						x = x + Direction.SE.getXModifier();
						y = y + Direction.SE.getYModifier();
						nextMove = Direction.SE; 
					}
					else if(westOrEast > 0){//Go west
						x = x + Direction.SW.getXModifier();
						y = y + Direction.SW.getYModifier();
						nextMove = Direction.SW;
					}
					else{ //just go south
						x = x + Direction.S.getXModifier();
						y = y + Direction.S.getYModifier();
						nextMove = Direction.S;
					}
				}
				else{ //Don't go north or south
					if(westOrEast < 0){ //Go East
						x = x + Direction.E.getXModifier();
						y = y + Direction.E.getYModifier();
						nextMove = Direction.E; 
					}
					else if(westOrEast > 0){//Go west
						x = x + Direction.W.getXModifier();
						y = y + Direction.W.getYModifier();
						nextMove = Direction.W;
					}
					else{
						x = x + Direction.HERE.getXModifier();
						y = y + Direction.HERE.getYModifier();
						nextMove = Direction.HERE;
					}
				}
				
				counterForHome++;
				return nextMove;
			}	
		}
	}		

	//Resets the Agent's values.
	public void reset()
	{
		x = 0;
		y = 0;
		r = 0;
		w = 0;
		a = x+1;
		b = x-1;
		c = y+1;
		d = y-1;
		pickOne = generator.nextInt(8);

		map = new ArrayList<Square>();
		history = new ArrayList<Direction>();
		map.add(new Square(0, 0, false, false));
		
		counterForHome =0;
		returnHome = false;
		hasComputer = false;
		pathToHome = null;
		
		 sendMessage = null;
		 receiveMessages = null;
		
		iAmDone = false;
	}
	
	public Square getSquare(int r, int s)
	{
		//Loops through the ArrayList looking for x and y.
		//If found, return the corresponding square.
		//Return null otherwise
		for( int j= 0; j < map.size(); j++)
		{
			if (map.get(j).getX() == r)
			{
				if (map.get(j).getY() == s)
				{
					return map.get(j);
				}
				else continue;
			}
			else continue;
		}
		return null;
	}		

	//Get square for my current position at x,y
	public Square getCurrentSquare(){
		for( int j= 0; j < map.size(); j++)
		{
			if (map.get(j).getX() == x)
			{
				if (map.get(j).getY() == y)
				{
					return map.get(j);
				}
				else continue;
			}
			else continue;
		}
		return null;
	}
    
 	private ArrayList<Square> aStarSearch(Square start, Square goal){
 		int currentRow, currentCol;
 		ArrayList<Square> closedSet = new ArrayList<Square>(); 	
 		ArrayList<Square> openSet = new ArrayList<Square>();
 		ArrayList<Square> pathToGoal = new ArrayList<Square>();
 		boolean foundGoal = false;
 		start.setF(0);
 		Square q;
 		openSet.add(start);
 		
 		if(openSet.isEmpty()){
 			System.out.println("openset is eempty!!!");
 		}
 		
		while (!openSet.isEmpty() && foundGoal == false) {
			
		
			// find smallest f in openset
			int tempi = 0;
			q = openSet.get(0);
			for (int i = 1; i < openSet.size(); i++) {
				if (openSet.get(i).getF() < q.getF()) {
					q = openSet.get(i);
					tempi = i;
				}
			}
			
			//pop q off the openset
			openSet.remove(tempi);
			closedSet.add(q);
			
			//Generate successors to q
			ArrayList<Square> successors = new ArrayList<Square>();
			currentRow = q.getY();
			currentCol = q.getX();
			
			Square temp;
			//North
			temp = getSquare(currentCol, currentRow-1);
			if(temp != null && !temp.isObstacle() ){
				successors.add(temp);
			}
			//NorthEast
			temp = getSquare(currentCol+1, currentRow-1);
			if(temp != null && !temp.isObstacle() ){
				successors.add(temp);
			}
			//East
			temp = getSquare(currentCol+1, currentRow);
			if(temp != null && !temp.isObstacle() ){				
				successors.add(temp);
			}
			//SouthEast
			temp = getSquare(currentCol+1, currentRow+1);
			if(temp != null && !temp.isObstacle() ){
				successors.add(temp);
			}
			//South
			temp = getSquare(currentCol, currentRow+1);
			if(temp != null && !temp.isObstacle()){
				successors.add(temp);
			}
			//SouthWest
			temp = getSquare(currentCol-1, currentRow+1);
			if(temp != null && !temp.isObstacle( )){
				successors.add(temp);
			}
			//West
			temp = getSquare(currentCol-1, currentRow);
			if(temp != null && !temp.isObstacle()){
				
				successors.add(temp);
			}
			//NorthWest
			temp = getSquare(currentCol-1, currentRow-1);
			if(temp != null && !temp.isObstacle()){
				
				successors.add(temp);
			}
			
			//For each succussor find shortest path to goal
			for(int i =0; i< successors.size(); i++){
				if((successors.get(i).getY() == goal.getY()) && (successors.get(i).getX() == goal.getX())){
					//Found my goal.
					successors.get(i).setParent(q);
					closedSet.add(successors.get(i));
					foundGoal = true;
					break;
				}
				successors.get(i).setG(q.getG() + 1);
				//Chebyshev distance
				int temph = Math.max(Math.abs(successors.get(i).getY() - goal.getY()), Math.abs(successors.get(i).getX()- goal.getX()));		
				successors.get(i).setH(temph);
				successors.get(i).setF(successors.get(i).getH() + successors.get(i).getG());
				

				
				boolean doNothing = false;

				
				//is this in my closedset? if it is, do not add to openset.
				for(int j=0;j<closedSet.size(); j++){
					if((closedSet.get(j).equals(successors.get(i)))  ){
							doNothing = true;
					}
				}
				
				//is this in my openset? if I am, check to see if F is better. update with new values
				for(int j=0;j<openSet.size(); j++){
					if(openSet.get(j).equals(successors.get(i)) &&
							( successors.get(i).getF() <= openSet.get(j).getF())){
							doNothing = true;
							openSet.get(j).setF(successors.get(i).getF());
							openSet.get(j).setParent(q);
					}
				}
				
				if(doNothing == false){
					successors.get(i).setParent(q);
					openSet.add(successors.get(i));
				}
				
				
		
				
		
			
				
				
			}
			//done. Push q to closed list
			if(closedSet.size() == 0){
				System.out.println("q is null!!!!");
			}
		}
//END OF WHILE LOOP ENTER PHASE 2	
		
		if(closedSet.size() == 0){
			System.out.println("q is null!!!!");
		}
		
		Square qq = closedSet.get(closedSet.size() -1);
	
		while(true){
			if(qq.getX() == start.getX() && qq.getY()==start.getY()){
				break;
			}
			pathToGoal.add(qq);
			qq = qq.getParent();
		}
		
		
		//Path found. Need to reverse it.
		for(int i =0; i<pathToGoal.size()/2; i++){
			Square temp = pathToGoal.get(i);
			pathToGoal.set(i, pathToGoal.get(pathToGoal.size() - i - 1));
			pathToGoal.set(pathToGoal.size() - i - 1, temp);
		}
		//all done!! time to return
		return pathToGoal;
 	}
 	  
	public boolean getiAmDone() {
		return iAmDone;
	}
 
 
  }

