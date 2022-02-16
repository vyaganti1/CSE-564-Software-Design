import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;

public class Circle implements Shape{
	Rectangle bounds;
	String label;
	String color;
	
	public Circle(int x, int y, int size, String label, String color) {
		bounds = new Rectangle(x, y, size, size);
		this.color = color;
		this.label = label;
	}
	
	/**
	 * This function is used to draw the city in GUI as a Circle with the city
	 * 
	 * @param g object of class Graphics
	 */
	public void draw(Graphics g) {
		int x = bounds.x; 
		int y = bounds.y; 
		int h = bounds.height; 
		int w = bounds.width;
		g.drawOval(x, y, w, h); 
		Color c = g.getColor(); 
		g.setColor(Color.orange);
		g.fillOval(x + 1, y + 1, w - 1, h - 1); 
		g.setColor(Color.red); 
		g.setColor(c); 
	}
	
	/**
	 * This function is used to get the x co-ordinate of the city.
	 * 
	 * @return x co-ordinate of the city
	 */
	public int getX() { 
		  return bounds.x; 
	}
	
	/**
	 * This function is used to get the y co-ordinate of the city.
	 * 
	 * @return y co-ordinate of the city
	 */
	public int getY() { 
		  return bounds.y; 
	}
	
	/**
	 * This function is used to get the name of the city.
	 * 
	 * @return name of the city
	 */
	public String getCityName() { 
		return label; 
	}

	/**
	 * This function is used to get the center point of the city.
	 * 
	 * @return center point of the city
	 */
	public Point center() {
		return new Point(bounds.x + bounds.width / 2,
				  bounds.y + bounds.height / 2);
	}

	/**
	 * This function checks that is the mouse is pressed on particular city or not. 
	 * @param x x co-ordinate of point where mouse is pressed.
	 * @param y y co-ordinate of point where mouse is pressed.
	 */
	public boolean contains(int x, int y) {
		return bounds.contains(x, y);
	}

	public void move(int x, int y) {
		bounds.x = x;
		bounds.y = y;
	}
	
	/**
	 * This function is used to connect two cities i.e draw a line between them in GUI. 
	 * @param b city to which first city is to be connected.
	 * @param g object of class Graphics2D to draw the line between cities.
	 */
	public void drawConnect(City b, Graphics2D g) {
		g.drawLine(center().x,
				  center().y, b.center().x, b.center().y);
		
	}

	/**
	 * This function is used to get the size of the city.
	 * 
	 * @return size of the city
	 */
	public int getSize() {
		return bounds.width;
	}

	/**
	 * This function is used to get the color of the city.
	 * 
	 * @return color of the city
	 */
	public String getColor() {
		return color;
	}
}
