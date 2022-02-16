import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;

public interface Shape {
	
	public void draw(Graphics g);
	public void drawConnect(City b, Graphics2D g);
	public int getX();
	public int getY();
	public int getSize();
	public String getColor();
	public Point center();
	public String getCityName();
	public boolean contains(int x, int y);
	public void move(int x, int y); 
}
