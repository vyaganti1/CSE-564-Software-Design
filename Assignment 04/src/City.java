import java.awt.*;

public class City implements ProductCity{

	private Shape shape;
	private String label;
	private int index;
	
	/**
	 * This constructor is used to initialize the name of city, city number, and
	 * it's position (as x and y co-ordinates) and it's dimensions.
	 * 
	 * @param label used for city name
	 * @param index used for city index
	 * @param x     used to give x co-ordinate of the city
	 * @param y     used to give y co-ordinate of the city
	 * @param w     used to give width co-ordinate of the cities
	 * @param h     used to give height co-ordinate of the cities
	 */
	public City(String label, int index, int x, int y, int size, String color) {
		this.label = label;
		shape = new Square(x, y, size, label, color);
		this.index = index;
	}

	/**
	 * This is a parameterized constructor of class City.
	 * 
	 * @param label, city name.
	 * @param index, city index.
	 * @param x, x co-ordinate of starting point of city.
	 * @param y, y co-ordinate of starting point of city. 
	 * @param size, size of the city.
	 * @param shapeType, shape of the city.
	 * @param color, color of the city.
	 */
	public City(String label, int index, int x, int y, int size, int shapeType, String color) {
		this.label = label;
		if(shapeType == 1) {
			shape = new CircleSquareDecorator(new Square(x, y ,size, label, color));
		}
		else if(shapeType == 2) {
			shape = new CircleSquareGroupDecorator(new SquareGroupDecorator(new Square(x, y ,size, label, color)));
		}
		else if(shapeType == 3) {
			shape = new SquareGroupDecorator(new Square(x, y ,size, label, color));
		}	
		this.index = index;
	}
	
	/**
	 * This function is used to get the x co-ordinate of the city.
	 * @return x co-ordinate of the city
	 */
	public int getX() { return shape.getX(); }
	
	/**
	 * This function is used to get the y co-ordinate of the city.
	 * @return y co-ordinate of the city
	 */
	public int getY() { return shape.getY(); }
	
	/**
	 * This function is used to get the city number.
	 * @return index of the city
	 */
	public int getIndex() { return index; }
	
	/**
	 * This function is used to get the name of the city.
	 * @return name of the city
	 */
	public String getCityName() { return label; }
	
	/**
	 * This function is used to draw the city in GUI as a rectangle with the city name. 
	 * @param g object of class Graphics
	 */
	public void draw(Graphics g) {
		shape.draw(g);
	}
	
	/**
	 * When the city is dragged in the GUI, this function changes the co-ordinates of the city being dragged. 
	 * @param x x co-ordinate of the city
	 * @param y y co-ordinate of the city
	 */
	public void move(int x, int y) {
		shape.move(x, y);
	}

	public Point center() {
		return shape.center();
	}
	
	/**
	 * This function is used to connect two cities i.e draw a line between them in GUI. 
	 * @param b city to which first city is to be connected.
	 * @param g object of class Graphics2D to draw the line between cities.
	 */
	public void drawConnect(City b, Graphics2D g) {
		shape.drawConnect(b, g);
	}
	
	/**
	 * This function checks that is the mouse is pressed on particular city or not. 
	 * @param x x co-ordinate of point where mouse is pressed.
	 * @param y y co-ordinate of point where mouse is pressed.
	 */
	public boolean contains(int x, int y) {
		return shape.contains(x, y);
	}
}
