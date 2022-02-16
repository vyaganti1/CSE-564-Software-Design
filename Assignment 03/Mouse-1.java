import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import java.io.*;
import java.util.*;
import java.lang.Math.*;
import java.text.*;
/**
 * A java based GUI application that enables a user to generate see a visualisation of cities and the shortest path between the
 * generated set of cities
 * The user will be able to add a city by clicking, drag a generated city around to change the TSP output, save
 * and load a text file with a set of cities and co-ordinates
 * 
 * @author Venkatesh Yaganti(1223441505/vyaganti )
 * @author Kunj Viral Kumar Mehta (1222383214/kmehta25)
 * @version 1.0
 */

class WorkSpace extends JPanel implements MouseListener,MouseMotionListener
{
	int preX,preY,i=0,i2=0,i3=0;
	boolean pressOut=false;
	City city,movedcity;
	String cname;
	ArrayList<City> cities = new ArrayList<City>();
	/*
	 * Default constructor for Workspace, called when "New" button is pressed
	 * */
	WorkSpace()	
	{	
		addMouseListener(this);
	        addMouseMotionListener(this);
        }
	/**
	 *  Parameterised constructor, used when the used wants to load data from file, i.e. "Load" button is pressed
	 *  
	 *  @param a An empty string is passed to the WorkSpace constructor call in order to differentiate which constructor
	 *  is being called - the default constructor is for the "New" button while the parameterised constructor is for
	 *  the "Load" button
	 * */
	WorkSpace(String a)	
	{
		loadData();
		addMouseListener(this);
	        addMouseMotionListener(this);
        }
	/**
	 * getter for name of the city
	 * @param name Name of city
	 * */
	public void getName(String name)
	{
		this.cname = name;
	}
	
	static ArrayList<Integer> coordsX = new ArrayList<Integer>();
	static ArrayList<Integer> coordsY = new ArrayList<Integer>();
	
	/**
	 * 
	 * This method calculates the distance between the two points and gives the absolute distance between them
	 * @param X ArrayList containing X coordinates
	 * @param Y ArrayList containing Y coordinates
	 * */
	
	public static void getData(ArrayList<Integer> X, ArrayList<Integer> Y)
	{
		double xp,yp;
		int xc,yc;
		double dis_mat[][] = new double[X.size()][Y.size()];
		Integer[] x = X.toArray(new Integer[0]);
		Integer[] y = Y.toArray(new Integer[0]);
		for(int a=0;a<X.size();a++)
		{
			for(int b=0;b<Y.size();b++)
			{	
				xp = Math.pow((x[a]-x[b]),2);
				yp = Math.pow((y[a]-y[b]),2);
				dis_mat[a][b] = Math.sqrt(xp+yp);
				DecimalFormat df = new DecimalFormat("#.#####");      
				dis_mat[a][b] = (Double.valueOf(df.format(dis_mat[a][b])));
			}
		}
	}
	/**
	* This method reads the input for Load button, reads the text file as input and adds creates new cities
	*/
	public void loadData()	
	{
		ArrayList<String> n = new ArrayList<String>();
		ArrayList<Integer> x = new ArrayList<Integer>();
		ArrayList<Integer> y = new ArrayList<Integer>();
		try 
		{
			File myObj = new File("./Cities.txt");
			Scanner myReader = new Scanner(myObj);
			while(myReader.hasNextLine()) 
			{
				String a[] = new String[3];
				a = myReader.nextLine().split(" ");
				n.add(a[0]);
				x.add(Integer.parseInt(a[1]));
				y.add(Integer.parseInt(a[2]));
			}
			myReader.close();
		} 
		catch (FileNotFoundException e1) {}
		for(int i=0;i<n.size();i++)
		{
			city = new City(n.get(i),x.get(i),y.get(i),10,10);
			cities.add(city);
		}
		getData(x,y);
	}
	/**
	* This method reads the input for New button, reads the city name when clicked anywhere on GUI screen using the MouseEvent
	* @param MouseEvent e used to identify the mouse click as a MouseEvent object
	*/
	public void mouseClicked(MouseEvent e)
	{
		int x = e.getX();
		int y = e.getY();
		coordsX.add(e.getX());
		coordsY.add(e.getY());
		getData(coordsX, coordsY);
		JFrame f = new JFrame();
		JTextField textField = new JTextField("",20);
		JButton b=new JButton("Submit");
		f.setTitle("Enter City Name");
		b.setBounds(500,500,900,500);
		b.setSize(100,100);
		b.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e)
		{
			city = new City(textField.getText(),x,y,10,10);
			cities.add(city);
	    		f.dispose();
	    	}});	
		f.add(textField);	
		f.add(b); 
		f.setSize(500,500); 
		f.setLayout(new FlowLayout());  
		f.setVisible(true);
	}
	/**
	*This method connects the cities with a line by using the X and Y coordinates obtained using the getX and getY methods
	*@param g : Graphics object used to draw on the canvas
	*/
	public void paintComponent(Graphics g)	
	{	
		Graphics2D g2=(Graphics2D) g;
		g2.setColor(Color.black);
		for(int i=0;i<cities.size();i++)
		{
			cities.get(i).draw(g2);
			if(i>0)
			{
				g.drawLine(cities.get(i).getX(),cities.get(i).getY(),cities.get(i-1).getX(),cities.get(i-1).getY());
			}
		}	
		try
		{
			g.drawLine(cities.get(cities.size()-1).getX(),cities.get(cities.size()-1).getY(),cities.get(0).getX(),cities.get(0).getY());	
		}
		catch(Exception e){}
	}
	/**
	* This method identifies and moves a city when the city is dragged on GUI screen
	* @param e Mouse event referring to when the mouse is pressed and not released (i.e. mouse drag operation)
	*/
	public void mousePressed(MouseEvent e)  
	{
		try	
		{
			for(int i=0;i<cities.size();i++)
			{
				if(cities.get(i).contains(e.getX(),e.getY()))
				{
					movedcity = cities.get(i);
					preX=(int)(movedcity.getX()-e.getX());
    					preY=(int)(movedcity.getY()-e.getY());
		        		movedcity.move(preX+e.getX(),preY+e.getY());
				}
			}
		}
		catch(Exception em) {}
	}
	/**
	* This method uses the variable "movedcity" from above function and changes the dragged position on GUI screen
	*/
	public void mouseDragged(MouseEvent e)  
	{
		try	
		{
			if(!pressOut)
			{
    				movedcity.move(preX+e.getX(),preY+e.getY());
			}
		}
		catch(Exception em) {}
	}
	/**
	* This method uses the variable "movedcity" from previous function and changes the position of city where is
	* released on GUI screen
	*/
	public void mouseReleased(MouseEvent e) 
	{
		try	
		{
			if(cities.get(i).contains(e.getX(),e.getY()))
			{
		        	movedcity.move(preX+e.getX(),preY+e.getY());
			}
		}
		catch(Exception em) {}
	}
	public void mouseExited(MouseEvent e)   {}
	public void mouseEntered(MouseEvent e)  {}
	public void mouseMoved(MouseEvent e)    {}
}

class City 
{	
	
	private Rectangle bounds;	
	String label;
	/**
	 * parameterised constuctor for City class used to identify the name and coordinates of the city
	 * @param label Name of the city
	 * @param x X coordinate of the city
	 * @param y Y coordinate of the city
	 * @param w width of the box drawn on the canvas that represents the city
	 * @param h height of the box drawn on the canvas that represents the city
	 * */
	City(String label,int x,int y, int w,int h)
	{
		bounds=new Rectangle(x,y,w,h);
		this.label=label;
		try (FileWriter f = new FileWriter("Cities.txt", true); BufferedWriter b = new BufferedWriter(f); PrintWriter p = new PrintWriter(b);) 
		{
			p.println(label+" "+x+" "+y);
		} 
		catch (IOException i) {}
  	}
	/**
	 * getter for X coordinate
	 * */
	public int getX()
	{ 
		return bounds.x; 
	}
	/**
	 * getter for Y coordinate
	 * */
	public int getY()
	{ 
		return bounds.y; 
	}
	/**
	* This method draws a square for the city for identification
	* @param g Object of Graphics class used to identify the square drawn for a particular city
	*/
	public void draw(Graphics g)
	{
		int x=bounds.x,y=bounds.y,h=bounds.height,w=bounds.width;
		g.drawRect(x, y, w, h);
		Color c=g.getColor();
		g.fillRect(x+1, y+1, w-1, h-1);
		g.setColor(Color.red);
		g.setFont(new Font("Times New Roman",Font.PLAIN,10));		
		g.drawString(label, x+w, y);
		g.setColor(c);
	}	
	/**
	 * This method is used to identify the new x and y coordinates upon dragging and dropping and update them in the object
	 * @param x Updated x coordinate
	 * @param y updated y coordinate
	 * */
	public void move(int x,int y)
	{
		bounds.x=x;
		bounds.y=y;
  	}
	/**
	 * This method is used to find the center of the square box based on width and height values
	 * @return Point object 
	 * */
	private Point center()
	{
    		return new Point(bounds.x+bounds.width/2,bounds.y+bounds.height/2);
  	}	
	/**
	 * This method is used to draw a line between 2 cities after implementing TSP
	 * @param b City object, contains information about the name of the city and its coordinates and dimensions
	 * @param g Graphics2D object do signal the method where to draw
	 * */
	public void drawConnet(City b,Graphics2D g)
	{
		g.drawLine(center().x,center().y, b.center().x,b.center().y);
	}
	/**
	 * This method checks whetner the mouse click is within the x and y coordinate bounds
	 * @param x X coordinate
	 * @param y Y coordiante
	 * */
	public boolean contains(int x,int y)
	{
		return bounds.contains(x, y);
	}
}

public class Mouse extends JFrame
{
	/**
	 * Default constructor used when "New" functionality is used
	 * */
	Mouse()
	{
		WorkSpace drawArea = new WorkSpace();
		add(drawArea);
	}
	/**
	 * Parameterised constructor called when "Load" functionality is used
	 * @param a When used in actual program, it will always contain an empty string
	 * i.e. the value of the parameter is never used, it is only used to differentiate itself from the default constructor
	 * */
	Mouse(String a)
	{
		WorkSpace drawArea = new WorkSpace(a);
		add(drawArea);
	}
	/**
	* Creates a frame with 3 buttons. New, Save and Load
	*/
	public static void main(String[] args)
	{
		JFrame frame = new JFrame("Assignment 03");
		frame.setBounds(250, 10, 800, 590);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		frame.setVisible(true);
		JButton btnNewButton = new JButton("New");
		btnNewButton.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				Mouse m = new Mouse();
				m.setTitle("Mobile TSP");
				m.setSize(500,500);
				m.setVisible(true);
				m.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			}
		});
		btnNewButton.setBounds(41, 11, 89, 23);
		frame.getContentPane().add(btnNewButton);

		JButton btnNewButton1 = new JButton("Save");
		btnNewButton1.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				JLabel l = new JLabel();
				l.setText("Saved Successfully...");
				frame.add(l);
				l.setBounds(339, 141, 800, 200);
			}
		});
		btnNewButton1.setBounds(339, 11, 89, 23);
		frame.getContentPane().add(btnNewButton1);

		JButton btnNewButton2 = new JButton("Load");
		btnNewButton2.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				Mouse m1 = new Mouse("");
				m1.setTitle("Mobile TSP");
				m1.setSize(500,500);
				m1.setVisible(true);
				m1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			}
		});
		btnNewButton2.setBounds(650, 11, 89, 23);
		frame.getContentPane().add(btnNewButton2);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}