import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingUtilities;

class Workspace extends JPanel implements MouseListener, MouseMotionListener, Observer {

	private City clickedCity;
	
	private int preX, preY;
	private boolean pressOut = false;
	private boolean dragged = false;
	private static String action = "create";
	private static String connections = "tsp";
	private ArrayList<Connection> tempConnection = new ArrayList<Connection>();
	private ArrayList<Integer> connectionsState = new ArrayList<Integer>();
	
	/**
	 * This constructor initializes the mouse listeners.
	 */
	public Workspace() {
		getGraphics();
		addMouseMotionListener(this);
		addMouseListener(this);
	}
	
	/**
	 * This function draws the cities in GUI and connect them with line.
	 * 
	 * @param graphics object of class Graphics used to draws the cities in GUI and connect them with line.
	 */	
	public void paintComponent(Graphics graphics) {
		super.paintComponent(graphics);
		Graphics2D g2 = (Graphics2D) graphics;
		g2.setColor(Color.red);
		Blackboard bb = Blackboard.createInstance();
		
		ArrayList<Connection> connection = bb.getConnections();
		if (connection.size() >= 1) {
			for (int itr = 0; itr < connection.size(); itr++) {
				connection.get(itr).draw(g2);
			}
		}
		
		ArrayList<City> cities = bb.getCities();
		if (cities.size() >= 1) {
			for (int itr = 0; itr < cities.size(); itr++) {
				cities.get(itr).draw(g2);
			}
		}
	}
	
	/**
	 * This function clears the GUI i.e removes the drawn cities.
	 */
	public void newMenuItem() {
		Blackboard bb = Blackboard.createInstance();
		bb.clearCities();
		bb.clearConnections();
		repaint();
	}
	
	/**
	 * This function saves the cities with its information in the file.
	 */
	public void saveCities() {
		String fileName = JOptionPane.showInputDialog(this, "Please enter the file name : ", null);
		if (fileName != null && fileName.trim().length() > 0) {
			Blackboard bb = Blackboard.createInstance();
			bb.writeInFileCities(fileName);
			bb.writeInFileConnections(fileName);
		}
	}

	/**
	 * This function loads the cities with its information from the file.
	 */
	public void loadCities() {
		Blackboard bb = Blackboard.createInstance();
		bb.clearCities();
		String fileName = JOptionPane.showInputDialog(this, "Please enter the file name : ", null);
		if (fileName != null && fileName.trim().length() > 0) {
			bb.readTextFileCities(fileName);
			bb.readTextFileConnections(fileName);
			repaint();
		}
		
	}
	
	/**
	 * This function specifies the operations to be performed when mouse is pressed.
	 * 
	 * @param e object of class MouseEvent containing details of mouse events.
	 */
	public void mousePressed(MouseEvent e) {
		try {			
			clickedCity = isItCity(e);
			
			if (clickedCity != null) {
				pressOut = false;
				if(SwingUtilities.isRightMouseButton(e)) {
					editCity();
				}
				MessageDisplayer messageDisplayer = MessageDisplayer.getInstance();
				if (action.equals("move")) {
					findConnections(clickedCity.getIndex());
					messageDisplayer.setMessageColor(Color.CYAN);
					messageDisplayer.setMessage("Nice! It's a first step of moving on.");
				} else if (action.equals("connect")) {
					Blackboard bb = Blackboard.createInstance();
					FactoryConnections fc = FactoryConnections.getFC();
					int connectionSize = bb.getConnections().size();
					if (connectionSize == 0) {
						bb.addConnection(fc.createConnection(connectionSize, clickedCity.getIndex(), -1, 
								(int) clickedCity.center().getX() , (int) clickedCity.center().getY(),
								(int) clickedCity.center().getX(), (int) clickedCity.center().getY()));
					} else {
						bb.addConnection(fc.createConnection(connectionSize - 1, clickedCity.getIndex(), -1, 
								(int) clickedCity.center().getX() , (int) clickedCity.center().getY(), 
								(int) clickedCity.center().getX(), (int) clickedCity.center().getY()));
					}
					messageDisplayer.setMessageColor(Color.CYAN);
					messageDisplayer.setMessage("Hey, You just initialized a new connection.");
				} 
			} else {
				pressOut = true;
			}			
		}  catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	/**
	 * This function specifies the operations to be performed when mouse is dragged.
	 * 
	 * @param e object of class MouseEvent containing details of mouse events.
	 */
	public void mouseDragged(MouseEvent e) {
		if (action.equals("move")) {
			mouseDraggedMove(e);
		} else if (action.equals("connect")) {
			if (clickedCity != null) {
				MessageDisplayer messageDisplayer = MessageDisplayer.getInstance();
				Blackboard bb = Blackboard.createInstance();
				int connectionSize = bb.getConnections().size();
				Connection temp = bb.getConnections().get(connectionSize - 1);
				if (temp.getCity2Index() == -1) {
					temp.setCor2(preX + e.getX(), preY + e.getY());
				}
				messageDisplayer.setMessageColor(Color.YELLOW);
				messageDisplayer.setMessage("Hey, that's great! You are trying to make a new connection.");
				repaint();
			}
		}
		
		dragged = true;
	}
	
	/**
	 * This function specifies the operations to be performed when mouse is released.
	 * 
	 * @param e object of class MouseEvent containing details of mouse events.
	 */
	public void mouseReleased(MouseEvent e) {
		MessageDisplayer messageDisplayer = MessageDisplayer.getInstance();
		try {
			if (!SwingUtilities.isRightMouseButton(e)) {
				if (action.equals("move")) {
					mouseReleasedMove(e);
				} else if (action.equals("create")) {
					if (!dragged) {
						if (pressOut) {
							mouseReleasedCreate(e);
						} else {
							JOptionPane.showMessageDialog(null, "City already exists where you want to create a new city.");
						}
					}
				} else if (action.equals("connect")) {
					City tempCity = isItCity(e);
					Blackboard bb = Blackboard.createInstance();
					int connectionSize = bb.getConnections().size();
					if (tempCity != null && clickedCity != null) {
						if (!bb.containsConnection(clickedCity.getIndex(), tempCity.getIndex())) {
							bb.getConnections().get(connectionSize - 1).setCity2Index(tempCity.getIndex());
							bb.getConnections().get(connectionSize - 1).setCor2(
									(int) tempCity.center().getX(), 
									(int) tempCity.center().getY());
							messageDisplayer.setMessageColor(Color.GREEN);
							messageDisplayer.setMessage("Mission Accomplished! RESPECT++");
						} else {
							bb.removeConnection(connectionSize - 1);
							messageDisplayer.setMessageColor(Color.GRAY);
							messageDisplayer.setMessage("Man! You are already connected.");
						}
					} else if (clickedCity != null) {
						bb.removeConnection(connectionSize - 1);
						messageDisplayer.setMessageColor(Color.RED);
						messageDisplayer.setMessage("Connection Failed! You didn't try enough");
					}
					repaint();
				}
			}
		} catch(Exception ex) {
		}
	}

	/**
	 * This function is used to specify the operations to be performed when mouse is moved.
	 * 
	 * @param e object of class MouseEvent containing details of mouse events.
	 */
	public void mouseMoved(MouseEvent e) {
	}

	/**
	 * This function is used to specify the operations to be performed when mouse click happens.
	 * 
	 * @param e object of class MouseEvent containing details of mouse events.
	 */
	public void mouseClicked(MouseEvent e) {
		if (action.equals("create")) {
			clickedCity = isItCity(e);
			
			if (clickedCity == null) {
				mouseReleasedCreate(e);
			} else {
				JOptionPane.showMessageDialog(null, "City already exists where you want to create a new city.");
			}
		}
	}

	/**
	 * This function is used to specify the operations to be performed when mouse is entered.
	 * 
	 * @param e object of class MouseEvent containing details of mouse events.
	 */
	public void mouseEntered(MouseEvent e) {
	}

	/**
	 * This function is used to specify the operations to be performed when mouse is exited.
	 * 
	 * @param e object of class MouseEvent containing details of mouse events.
	 */
	public void mouseExited(MouseEvent e) {
	}

	/**
	 * This function specifies the operations to be performed when TSPSovler is done executing.
	 * 
	 * @param o object of class Observable.
	 * 
	 * @param arg object of class Object.
	 */
	public void update(Observable o, Object arg) {
		Blackboard bb = Blackboard.createInstance();
		bb.clearConnections();
		ArrayList<City> cities = bb.getCities();
		FactoryConnections fc = FactoryConnections.getFC();
		MessageDisplayer messageDisplayer = MessageDisplayer.getInstance();
		
		if (connections.equals("tsp")) {
			int[] shortestPath = bb.getShortestPath();
			if (shortestPath.length > 1) {
				for (int itr = 0; itr < shortestPath.length; itr++) {
					if (itr < shortestPath.length - 1) {
						bb.addConnection(fc.createConnection(itr, shortestPath[itr], shortestPath[itr + 1], 
								(int) cities.get(shortestPath[itr]).center().getX() , 
								(int) cities.get(shortestPath[itr]).center().getY(), 
								(int) cities.get(shortestPath[itr + 1]).center().getX(), 
								(int) cities.get(shortestPath[itr + 1]).center().getY()));
					} else {
						bb.addConnection(fc.createConnection(itr, shortestPath[itr], shortestPath[0], 
								(int) cities.get(shortestPath[itr]).center().getX() , 
								(int) cities.get(shortestPath[itr]).center().getY(), 
								(int) cities.get(shortestPath[0]).center().getX(), 
								(int) cities.get(shortestPath[0]).center().getY()));
					}
				}
				messageDisplayer.setMessageColor(Color.GREEN);
				messageDisplayer.setMessage("TSP : nearest neighbor algorithm completed.");
			}
		} else if (connections.equals("tspPro")) {
			int[] shortestPath = bb.getShortestPath();
			if (shortestPath.length > 1) {
				for (int itr = 0; itr < shortestPath.length; itr++) {
					if (itr < shortestPath.length - 1) {
						bb.addConnection(fc.createConnection(itr, shortestPath[itr], shortestPath[itr + 1], 
								(int) cities.get(shortestPath[itr]).center().getX() , 
								(int) cities.get(shortestPath[itr]).center().getY(), 
								(int) cities.get(shortestPath[itr + 1]).center().getX(), 
								(int) cities.get(shortestPath[itr + 1]).center().getY()));
					} else {
						bb.addConnection(fc.createConnection(itr, shortestPath[itr], shortestPath[0], 
								(int) cities.get(shortestPath[itr]).center().getX() , 
								(int) cities.get(shortestPath[itr]).center().getY(), 
								(int) cities.get(shortestPath[0]).center().getX(), 
								(int) cities.get(shortestPath[0]).center().getY()));
					}
				}
				messageDisplayer.setMessageColor(Color.GREEN);
				messageDisplayer.setMessage("TSP : Pro algorithm completed.");
			}
		} else if (connections.equals("cluster")) {
			ArrayList<Integer>[] clusters = bb.getClusters();
			int count = 0;
			
			for (int itrI = 0; itrI < clusters.length; itrI++) {
				for (int itrJ = 0; itrJ < clusters[itrI].size() - 1; itrJ++) {
					bb.addConnection(fc.createConnection(count, 
							cities.get(clusters[itrI].get(itrJ)).getIndex(),
							cities.get(clusters[itrI].get(itrJ + 1)).getIndex(),
							(int) cities.get(clusters[itrI].get(itrJ)).center().getX(), 
							(int) cities.get(clusters[itrI].get(itrJ)).center().getY(),
							(int) cities.get(clusters[itrI].get(itrJ + 1)).center().getX(), 
							(int) cities.get(clusters[itrI].get(itrJ + 1)).center().getY()));
					count += 1;
				}
			}
			messageDisplayer.setMessageColor(Color.GREEN);
			messageDisplayer.setMessage("Clustering algorithm completed.");
		}
		repaint();
	}

	/**
	 * This is a getter function for class member action 
	 * 
	 * @return the value of class member
	 */
	public static String getAction() {
		return action;
	}
	
	/**
	 * This is a function to be performed when user connection is clicked.
	 */
	public void userConnectClicked() {
		Blackboard bb = Blackboard.createInstance();
		bb.clearConnections();
		repaint();
	}
	
	/**
	 * This is a setter function for type of action to be performed on the cities.
	 * 
	 * @param act, type of action to be performed on the cities.
	 */
	public static void setAction(String act) {
		action = act;
	}
	
	/**
	 * This is a getter function of algorithm type to be executed.
	 * 
	 * @return connections, name of algorithm to be executed.
	 */
	public static String getConnections() {
		return connections;
	}
	
	/**
	 * This is a setter function of algorithm type to be executed.
	 * 
	 * @param con, name of algorithm to be executed.
	 */
	public static void setConnections(String con) {
		connections = con;
	}
	
	private void mouseDraggedMove(MouseEvent e) {
		MessageDisplayer messageDisplayer = MessageDisplayer.getInstance();
		try {
			if (!pressOut) {
				clickedCity.move(preX + e.getX(), preY + e.getY());
				for (int itr = 0; itr < tempConnection.size() ; itr++) {
					if (connectionsState.get(itr) == 1) {
						tempConnection.get(itr).setCor1((int) clickedCity.center().getX(), (int) clickedCity.center().getY());
					} else if (connectionsState.get(itr) == 2) {
						tempConnection.get(itr).setCor2((int) clickedCity.center().getX(), (int) clickedCity.center().getY());
					}
				}
				messageDisplayer.setMessageColor(Color.YELLOW);
				messageDisplayer.setMessage("Nice! You are trying to move on.");
				repaint();
			}
		}  catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	private void mouseReleasedMove(MouseEvent e) {
		MessageDisplayer messageDisplayer = MessageDisplayer.getInstance();
		try {
			if (!pressOut && clickedCity.contains(e.getX(), e.getY())) {
				clickedCity.move(preX + e.getX(), preY + e.getY());
				
				TSPNN tsp = TSPNN.createInstance();
				Thread thread = new Thread(tsp);
		    	thread.start();
	        	
		    	messageDisplayer.setMessageColor(Color.GREEN);
		    	messageDisplayer.setMessage("Congratulations! You finally moved on.");
		    	repaint();
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	private void mouseReleasedCreate(MouseEvent e) {
		MessageDisplayer messageDisplayer = MessageDisplayer.getInstance();
		try {
			String cityName = JOptionPane.showInputDialog(this, "Please enter the name of city you want to add : ", null);
			
			Blackboard bb = Blackboard.createInstance();
			ArrayList<City> cities = bb.getCities();
            
			FactoryCity fc = FactoryCity.getFC();
			if (cityName != null && cityName.trim().length() > 0) {
				if (cities.size() > 0) {
					bb.addCity(fc.createCity(cityName,cities.size(), e.getX(), e.getY()));
				} else {
					bb.addCity(fc.createCity(cityName,0, e.getX(), e.getY()));
				}
				TSPNN tsp = TSPNN.createInstance();
				Thread thread = new Thread(tsp);
		    	thread.start();
				repaint();
            }
			
			messageDisplayer.setMessageColor(Color.GREEN);
			messageDisplayer.setMessage("Congratulations! You just created a new city.");
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	private City isItCity(MouseEvent e) {
		City tempCity = null;
		boolean cityFound = false;
		Blackboard bb = Blackboard.createInstance();
		ArrayList<City> cities = bb.getCities();
		for (int itr = 0; itr < cities.size(); itr++) {
			if(cities.get(itr).contains(e.getX(), e.getY())) {
				cityFound = true;
				tempCity = cities.get(itr); 
				break;
			}
		}

		if (cityFound) {
			return tempCity;
		} else {
			return null;
		}
	}
	
	private void findConnections(int cityIndex) {
		Blackboard bb = Blackboard.createInstance();
		ArrayList<Connection> connections = bb.getConnections();

		for (int itr = 0; itr < connections.size(); itr++) {
			if (connections.get(itr).getCity1Index() == cityIndex) {
				connectionsState.add(1);
				tempConnection.add(connections.get(itr)); 
			} else if (connections.get(itr).getCity2Index() == cityIndex) {
				connectionsState.add(2);
				tempConnection.add(connections.get(itr)); 
			}
		}
	}

	/**
	 *  This function changes the city attributes.
	 */
	public void editCity() {
		String[] shapeDecorators = new String[] {"Square", "Circle Square", "Circle Square group", "Square Group"};
		JComboBox<String> shapeDecoratorCombo = new JComboBox<String>(shapeDecorators);
		String[] colors = new String[] {"cyan", "red", "blue", "yellow", "green"};
		String[] hexColors = new String[] {"#00FFFF", "#ff0000", "#0000FF", "#FFFF00", "#90EE90"};
		JComboBox<String> colorsCombo = new JComboBox<String>(colors);
		SpinnerModel model = new SpinnerNumberModel(20, 20, 80, 10);     
		JSpinner spinner = new JSpinner(model);
		Object[] fields = {
				"Select Decorator", shapeDecoratorCombo,
				"Select color", colorsCombo,
				"Select size", spinner
		};
		
		JOptionPane.showConfirmDialog(null, fields, "Edit a City ", JOptionPane.OK_CANCEL_OPTION);
		int selectedDecorator = shapeDecoratorCombo.getSelectedIndex();
		String selectedColor = hexColors[colorsCombo.getSelectedIndex()];
		int selectedSize = (int) spinner.getValue();
		City newCity = null;
		
		switch(selectedDecorator) {
			case 0:
					newCity = new City(clickedCity.getCityName(), clickedCity.getIndex(),
							 clickedCity.getX(), clickedCity.getY(), selectedSize, selectedColor);
					break;
			case 1:
					newCity = new City(clickedCity.getCityName(), clickedCity.getIndex(),
							 clickedCity.getX(), clickedCity.getY(), selectedSize, selectedDecorator, selectedColor);
					break;
			case 2:
					newCity = new City(clickedCity.getCityName(), clickedCity.getIndex(),
							 clickedCity.getX(), clickedCity.getY(), selectedSize, selectedDecorator, selectedColor);
					break;
			case 3:
					newCity = new City(clickedCity.getCityName(), clickedCity.getIndex(),
							 clickedCity.getX(), clickedCity.getY(), selectedSize, selectedDecorator, selectedColor);
					break;
		}
		Blackboard bb = Blackboard.createInstance();
		ArrayList<City> cities = bb.getCities();
		if(newCity != null) {
			cities.remove(clickedCity.getIndex());
			cities.add(clickedCity.getIndex(), newCity);
			repaint();	
		}
	}
}
