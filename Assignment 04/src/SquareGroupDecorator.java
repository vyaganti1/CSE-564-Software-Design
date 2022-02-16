import java.awt.Color;
import java.awt.Graphics;

public class SquareGroupDecorator extends ShapeDecorator{

	/**
	 * This is a constructor of class SquareGroupDecorator.
	 * 
	 * @param shape, object of the class Shape.
	 */
	public SquareGroupDecorator(Shape shape) {
		super(shape);
	}

	/**
	 * This function is used to draw the city in GUI as a Square surrounded by squares
	 * 
	 * @param g object of class Graphics
	 */
	public void draw(Graphics g) {
		shape.draw(g); 
		drawSquareGroup(g);
	}
	
	/**
	 * This function is used to draw the set of squares
	 * 
	 * @param g object of class Graphics
	 */
	public void drawSquareGroup(Graphics g) {
		int x = shape.getX();
		int y = shape.getY();
		int size = shape.getSize();
		g.drawRect(x + size, y, size, size); 
		Color c = g.getColor(); 
		g.setColor(Color.decode(shape.getColor()));
		g.fillRect(x + 1 + size, y + 1, size - 1, size - 1); 
		g.setColor(Color.red); 
		g.drawRect(x - size, y, size, size); 
		g.setColor(Color.decode(shape.getColor()));
		g.fillRect(x + 1 - size, y + 1, size - 1, size - 1); 
		g.setColor(Color.red); 
		g.drawRect(x, y + size, size, size); 
		g.setColor(Color.decode(shape.getColor()));
		g.fillRect(x + 1, y + 1 + size, size - 1, size - 1); 
		g.setColor(Color.red); 
		g.drawRect(x, y - size, size, size); 
		g.setColor(Color.decode(shape.getColor()));
		g.fillRect(x + 1, y + 1 - size, size - 1, size - 1); 
		g.setColor(Color.red); 
		g.setColor(c);
	}
}
