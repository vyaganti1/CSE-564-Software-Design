import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;

public class ShapeDecorator implements Shape{
	Shape shape;
	Rectangle bounds;
	
	/**
	 * This is a constructor of class ShapeDecorator. 
	 * 
	 * @param shape, object of the class Shape.
	 */
	public ShapeDecorator(Shape shape){
		super();
		this.shape = shape;
	}
	
	/**
	 * This function is used to draw the city in GUI.
	 * 
	 * @param g object of class Graphics
	 */
	public void draw(Graphics g) {
		shape.draw(g);
	}

	/**
	 * This function is used to get the x co-ordinate of the city.
	 * 
	 * @return x co-ordinate of the city
	 */
	public int getX() { 
		 return shape.getX(); 
	}
	
	/**
	 * This function is used to get the y co-ordinate of the city.
	 * 
	 * @return y co-ordinate of the city
	 */
	public int getY() { 
		  return shape.getY(); 
	}
	
	/**
	 * This function is used to get the center point of the city.
	 * 
	 * @return center point of the city
	 */
	public Point center() {
		 return shape.center(); 
	}

	/**
	 * This function checks that is the mouse is pressed on particular city or not. 
	 * @param x x co-ordinate of point where mouse is pressed.
	 * @param y y co-ordinate of point where mouse is pressed.
	 */
	public boolean contains(int x, int y) {
		return shape.contains(x, y);
	}
	
	public void move(int x, int y) {
		shape.move(x, y);
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
	 * This function is used to get the name of the city.
	 * 
	 * @return name of the city
	 */
	public String getCityName() {
		return shape.getCityName();
	}

	/**
	 * This function is used to get the size of the city.
	 * 
	 * @return size of the city
	 */
	public int getSize() {
		return shape.getSize();
	}

	/**
	 * This function is used to get the color of the city.
	 * 
	 * @return color of the city
	 */
	public String getColor() {
		return shape.getColor();
	}

}
