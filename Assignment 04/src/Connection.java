import java.awt.Graphics2D;

public class Connection implements ProductConnection{

	private int connectionIndex;
	private int city1Index, city2Index;
	private int corX1, corX2, corY1, corY2;
	
	/**
	 * This is a parameterized constructor of class Connection
	 * 
	 * @param connectionIndex
	 * @param indx1, index of city 1.
	 * @param indx2, index of city 1.
	 * @param x1, x co-ordinate of connection starting point.
	 * @param y1, y co-ordinate of connection starting point.
	 * @param x2, x co-ordinate of connection ending point.
	 * @param y2, y co-ordinate of connection ending point.
	 */
	public Connection(int connectionIndex, int indx1, int indx2, int x1, int y1, int x2, int y2) {
		this.connectionIndex = connectionIndex;
		this.city1Index = indx1; 
		this.city2Index = indx2;
		this.corX1 = x1;
		this.corY1 = y1;
		this.corX2 = x2;
		this.corY2 = y2;
	}
	
	/**
	 * This is a setter function of stating point of the connection. 
	 * 
	 * @param x1, x co-ordinate of connection starting point.
	 * @param y1, y co-ordinate of connection starting point.
	 */
	public void setCor1(int x1, int y1) {
		this.corX1 = x1;
		this.corY1 = y1;
	}
	
	/**
	 * This is a setter function of ending point of the connection.
	 * 
	 * @param x2, x co-ordinate of connection ending point.
	 * @param y2, x co-ordinate of connection ending point.
	 */
	public void setCor2(int x2, int y2) {
		this.corX2 = x2;
		this.corY2 = y2;
	}

	/**
	 * This is a getter function for class data member corX1.
	 * 
	 * @return corX1, class data member.
	 */
	public int getCor1X() {
		return corX1;
	}
	
	/**
	 * This is a getter function for class data member corY1.
	 * 
	 * @return corY1, class data member.
	 */
	public int getCor1Y() {
		return corY1;
	}
	
	/**
	 * This is a getter function for class data member corX2.
	 * 
	 * @return corX2, class data member.
	 */
	public int getCor2X() {
		return corX2;
	}
	
	/**
	 * This is a getter function for class data member corY2.
	 * 
	 * @return corY2, class data member.
	 */
	public int getCor2Y() {
		return corY2;
	}
	
	/**
	 * This is a getter function for index of the connection.
	 * 
	 * @return connectionIndex, class data member.
	 */
	public int getConnectionIndex() {
		return connectionIndex;
	}
	
	/**
	 * This is a setter function of starting city of the connection.
	 * 
	 * @param indx, index of starting city.
	 */
	public void setCity1Index(int indx) {
		city1Index = indx;
	}
	
	/**
	 * This is a getter function of starting city of the connection.
	 * 
	 * @return city1Index, class data member. 
	 */
	public int getCity1Index() {
		return city1Index;
	}
	
	/**
	 * This is a setter function of ending city of the connection.
	 * 
	 * @param indx, index of ending city.
	 */
	public void setCity2Index(int indx) {
		city2Index = indx;
	}
	
	/**
	 * This is a getter function of ending city of the connection.
	 * 
	 * @return city2Index, class data member.  
	 */
	public int getCity2Index() {
		return city2Index;
	}
	
	/**
	 * This functions draws the connection.
	 * 
	 * @param g, object of class Graphics2D. 
	 */
	@Override
	public void draw(Graphics2D g) {
		g.drawLine(this.corX1, this.corY1, this.corX2, this.corY2);
	}

	/**
	 * This function checks if the connection contains both the cities or not,
	 * using their index.
	 * 
	 * @param indx1, index of city 1.
	 * @param indx2, index of city 2.
	 * @return true, if connection exists and false, for the opposite.
	 */
	public boolean contain(int indx1, int indx2) {
		if ((city1Index == indx1 && city2Index == indx2) || (city1Index == indx2 && city2Index == indx1)) {
			return true;
		}
		return false;
		
	}
}
