import java.awt.Graphics;

public class CircleSquareDecorator extends ShapeDecorator {

	public CircleSquareDecorator(Shape shape) {
		super(shape);
	}

	/**
	 * This function is used to draw the city in GUI as a Circle within the square
	 * 
	 * @param g object of class Graphics
	 */
	public void draw(Graphics g) {
		shape.draw(g); 
		drawCircle(g);
	}
	
	/**
	 * This function is used to draw Circle
	 * 
	 * @param g object of class Graphics
	 */
	public void drawCircle(Graphics g) {
		Shape circle = new Circle(shape.getX(), shape.getY(), shape.getSize(), shape.getCityName(), shape.getColor());
		circle.draw(g);
	}
	
}
