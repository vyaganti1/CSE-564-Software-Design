import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class Blackboard {
	private int noOfClusters;
	private int maxIterations;
	private static Blackboard _instance;
	private ArrayList<City> cities = new ArrayList<City>();
	private ArrayList<Connection> connections = new ArrayList<Connection>();
	private ArrayList<Integer>[] clusters;
	private int[] shortestPath;
	
	
	private Blackboard() {
	}
	
	/**
	 * This function is used to create the object class Blackboard and if it is
	 * already created, it returns the old object.
	 * That is, there will only be one object this class with different references.
	 * 
	 * @return _instance object of class Blackboard. 
	 */
	public static Blackboard createInstance() {
		if (_instance == null) {
			_instance = new Blackboard();
		}
		
		return _instance;
	}
	
	/**
	 * This function is used to add the new object of class City to the 
	 * collection of objects(in ArrayList).
	 * 
	 * @param city, object of class City. 
	 */
	public void addCity(City city) {
		cities.add(city);
	}
	
	/**
	 * This function is used to empty the ArrayList<City>. 
	 */
	public void clearCities() {
		cities.clear();
	}
	
	/**
	 * This function is used to get all the cities in ArrayList.
	 * 
	 *  @return cities object of class ArrayList.
	 */
	public ArrayList<City> getCities() {
		return cities;
	}
	
	/**
	 * This function reads the file.
	 * 
	 * @param path name of the file which is to be read.
	 */
	public void readTextFileCities(String path) {
		int index = 0;
		int dataLength = 0;
		String data[] = null;
		boolean startStoringData = false;
		path = path + ".cities";

		File file = new File(path);
		BufferedReader br;
		try {
			br = new BufferedReader(new FileReader(file));
			String str;
			clearCities();
			while ((str = br.readLine()) != null) {
				if (str.contains("EOF")) {
					break;
				}
				if (startStoringData && index < dataLength) {
					String delimiters = "\\s+";
					String splitedStrData[] = str.split(delimiters);
					
					FactoryCity fc = FactoryCity.getFC();
					if (splitedStrData.length > 3) {
						cities.add(fc.createCity(splitedStrData[3], index, 
								Integer.parseInt(splitedStrData[1]), 
								Integer.parseInt(splitedStrData[2])));
					} else if (splitedStrData.length == 3) {
						cities.add(fc.createCity("", index, 
								Integer.parseInt(splitedStrData[1]), 
								Integer.parseInt(splitedStrData[2])));
					}
					index++;
				}
				if(str.contains("DIMENSION")) {
					try {
						dataLength = Integer.parseInt(str.substring(str.indexOf(":") + 1).trim());
						data = new String[dataLength];
						data[0] = "";
					} catch(Exception e) {
						System.out.println("Try again! Something went wrong");
						e.printStackTrace();
					}
				} else if (str.contains("NODE_COORD_SECTION")) {
					startStoringData = true;
				}
			}
 		} catch (FileNotFoundException e) {
			System.out.println("Try again! Something went wrong");
			e.printStackTrace();
		} catch (Exception e) {
			System.out.println("Try again! Something went wrong");
			e.printStackTrace();
		}
	}
	
	/**
	 * This function reads the file.
	 * 
	 * @param path name of the file which is to be read.
	 */
	public void readTextFileConnections(String path) {
		path = path + ".connections";

		File file = new File(path);
		BufferedReader br;
		try {
			br = new BufferedReader(new FileReader(file));
			String str;
			clearConnections();
			while ((str = br.readLine()) != null) {
				if (str.contains("EOF")) {
					break;
				}
					String delimiters = "\\s+";
					String splitedStrData[] = str.split(delimiters);
					
					FactoryConnections fc = FactoryConnections.getFC();
					connections.add(fc.createConnection(
							Integer.parseInt(splitedStrData[0].trim()), 
							Integer.parseInt(splitedStrData[1].trim()), 
							Integer.parseInt(splitedStrData[2].trim()), 
							Integer.parseInt(splitedStrData[3].trim()), 
							Integer.parseInt(splitedStrData[4].trim()), 
							Integer.parseInt(splitedStrData[5].trim()), 
							Integer.parseInt(splitedStrData[6].trim())
							));
			}
 		} catch (FileNotFoundException e) {
			System.out.println("Try again! Something went wrong");
			e.printStackTrace();
		} catch (Exception e) {
			System.out.println("Try again! Something went wrong");
			e.printStackTrace();
		}
	}

	/**
	 * This function writes the data about cities in the file.
	 * 
	 * @param path name of the file which in data is to be written.
	 */
	public void writeInFileCities(String path) {
		FileWriter fileWriter = null;
		path = path + ".cities";
		try 
		{
			File file = new File(path);
			file.createNewFile();
			int cityCount = 0;
			
			String data[] = new String[cities.size() + 4];
			data[0] = "NAME : " + path;
			data[1] = "TYPE : TSP";
			data[2] = "DIMENSION : " + cities.size();
			data[3] = "NODE_COORD_SECTION";
			
			for (int itr = 4; itr < cities.size() + 4; itr++) {
        		data[itr] = (cityCount + 1) + " " + cities.get(cityCount).getX() + " " + cities.get(cityCount).getY() + " " + cities.get(cityCount).getCityName();
        		cityCount++;
			}
			
            fileWriter = new FileWriter(file);
 
            for (int itr = 0; itr < data.length; itr++) {
                fileWriter.write(data[itr] + "\n");
            }
            
            fileWriter.close();
	    } catch (IOException e) {
	    	System.out.println("An error occurred.");
	    	e.printStackTrace();
	    }
	}
	
	/**
	 * This function writes the data about connections in the file.
	 * 
	 * @param path name of the file which in data is to be written.
	 */
	public void writeInFileConnections(String path) {
		FileWriter fileWriter = null;
		path = path + ".connections";
		try 
		{
			File file = new File(path);
			file.createNewFile();
			
			String data[] = new String[connections.size() + 1];
			
			for (int itr = 0; itr < connections.size(); itr++) {
        		data[itr] = itr + " " + 
        					connections.get(itr).getCity1Index() + " " + 
        					connections.get(itr).getCity2Index() + " " + 
        					connections.get(itr).getCor1X() + " " +
        					connections.get(itr).getCor1Y() + " " +
        					connections.get(itr).getCor2X() + " " +
        					connections.get(itr).getCor2Y();
			}
			
			data[connections.size()] = "EOF";
			
            fileWriter = new FileWriter(file);
 
            for (int itr = 0; itr < data.length; itr++) {
                fileWriter.write(data[itr] + "\n");
            }
            
            fileWriter.close();
	    } catch (IOException e) {
	    	System.out.println("An error occurred.");
	    	e.printStackTrace();
	    }
	}
	
	/**
	 * This is a getter function for shortestPath, class data member.
	 * 
	 * @return shortestPath class data member.
	 */
	public int[] getShortestPath() {
		return shortestPath;
	}
	
	/**
	 * This is a setter function for shortestPath, class data member.
	 * 
	 * @param sp array of integers.
	 */
	public void setShortestPath(int[] sp) {
		shortestPath = sp;
	}
	
	/**
	 * This function is used to add the new object of class Connection to the 
	 * collection of objects(in ArrayList).
	 * 
	 * @param con, object of class Connection.
	 */
	public void addConnection(Connection con) {
		connections.add(con);
	}
	
	/**
	 * This function is used to remove the object of class Connection at specified
	 *  index from the collection of objects(in ArrayList). 
	 * 
	 * @param index, index in ArrayList.
	 */
	public void removeConnection(int index) {
		connections.remove(index);
	}
	
	/**
	 * This function is used to empty the ArrayList<Connection>.
	 */
	public void clearConnections() {
		connections.clear();
	}
	
	/**
	 * This function is used to get all the connections in ArrayList.
	 * 
	 * @return connections, object of class ArrayList<Connection>.
	 */
	public ArrayList<Connection> getConnections( ) {
		return connections;
	}
	
	/**
	 * This is a setter function for clusters, class data member.
	 * 
	 * @param clus, array of object of ArrayList<Integer>.
	 */
	public void setClusters(ArrayList<Integer>[] clus) {
		clusters = new ArrayList[getNoOfClusters()];
		clusters = clus;
	}
	
	/**
	 * This is a getter function for clusters, class data member.
	 * 
	 * @return clusters, class data member.
	 */
	public ArrayList<Integer>[] getClusters() {
		return clusters;
	}
	
	/**
	 * This is a setter function of number of clusters, class data member.
	 * 
	 * @param clus, number of clusters.
	 */
	public void setNoOfClusters(int clus) {
		noOfClusters = clus;
	}
	
	/**
	 * This is a getter function of number of clusters, class data member.
	 * 
	 * @return noOfClusters, class data member.
	 */
	public int getNoOfClusters() {
		return noOfClusters;
	}
	
	/**
	 * This is a setter function of number of iterations, class data member.
	 * 
	 * @param itr, number of iterations.
	 */
	public void setMaxIterations(int itr) {
		maxIterations = itr;
	}
	
	/**
	 * This is a getter function of number of iterations, class data member.
	 * 
	 * @return maxIterations, class data member.
	 */
	public int getMaxIterations() {
		return maxIterations;
	}

	/**
	 * This function checks if connection is already there or not.
	 * 
	 * @param indx1, index of city1.
	 * @param indx2, index of city2.
	 * @return true, if connection exists and false, for opposite.
	 */
	public boolean containsConnection(int indx1, int indx2) {
		boolean doesItExist = false;
		for (int itr = 0; itr < connections.size(); itr++) {
			if (connections.get(itr).contain(indx1, indx2)) {
				doesItExist = true;
				break;
			}
		}
		return doesItExist;
	}
}
