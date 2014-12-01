/** 
* Creates squares for a map used to keep track of where the agent is.
* The squares also contain information if the agent has visited the square
* or if there is an obstacle.

*/
class Square
{
	//Coordinates of the squares in the map
	private int x = 0;
	private int y = 0;
	private boolean isVisited = false;
	private boolean isObsticle = false;
	private boolean isHere = false;
	
	//values used for A* search. disregard if not needed;
	private int f,g,h;
	private Square parent = null;
	
	public Square(int r, int s, boolean isVisited, boolean isObsticle)
	{
		x = r;
		y = s;
		isVisited = isVisited;
		isObsticle = isObsticle;		
	}
	
	//Returns x value of square.
	public int getX()
	{
		return x;
	}
	
	//Returns y value of square.
	public int getY()
	{
		return y;
	}
	
	
	//Returns the truth value if the square was visited or not.
	public boolean wasVisited()
	{
		return isVisited;
	}
	
	//Returns the truth value if the square is an obsticle or not.
	public boolean isObsticle()
	{
		return isObsticle;
	}
	
	//Sets isVisit value to true.
	public void setToVisit()
	{
		isVisited = true;
	}	
	
	//Sets isObsticle value to true.
	public void setToObsticle()
	{
		isObsticle = true;
	}  
	
	public void setIsHereTrue(){
		isHere = true;
	}
	
	public void setIsHereFalse(){
		isHere = false;
	}
	
	public boolean getIsHere(){
		return isHere;
	}

	public int getF() {
		return f;
	}

	public void setF(int f) {
		this.f = f;
	}

	public int getG() {
		return g;
	}

	public void setG(int g) {
		this.g = g;
	}

	public int getH() {
		return h;
	}

	public void setH(int h) {
		this.h = h;
	}

	public Square getParent() {
		return parent;
	}

	public void setParent(Square parent) {
		this.parent = parent;
	}
	
	
	
}