import java.util.*;

class TSPNN extends Observable implements Runnable, Handler {
	private static TSPNN _instance;
	protected Handler successor;
	
	/**
	 * This function sets the working successor in chain of command
	 * 
	 * @param algo, type of algorithm for next successor.
	 */
	public void setSuccessor(Handler successor) {
		this.successor = successor;
	}
	
	private TSPNN() {
	}
	
	/**
	 * This function is used to create the object class TSPNN and if it is already created, it returns the old object.
	 * That is, there will only be one object this class with different references.
	 * 
	 * @return _instance object of class TSPNN. 
	 */
	public static TSPNN createInstance() {
		if (_instance == null) {
			_instance = new TSPNN();
		}
		
		return _instance;
	}
	
	/**
	 * This method is used to calculate the shortest distance between cities. Once done, it notifies the observer about it.
	 * 
	 * @param data array of string containing information (i.e city index and it's x and y co-ordinates) about the cities.
	 */
	public void sovleTSP() {
		try {
			int shortestPath[] = null;
			Blackboard bb = Blackboard.createInstance();
			ArrayList<City> cities = bb.getCities();
			shortestPath = greedyAlgorithm(cities);
			bb.setShortestPath(shortestPath);
			setChanged();
			notifyObservers();
		} catch (Exception e) {
			System.out.println("OOPS! Something went wrong...");
			e.printStackTrace();
		}
	}

	private int[] greedyAlgorithm(ArrayList<City> cities) {
		int tspPath[] = new int[cities.size()];
		String tspPathStr = "";
		tspPath = nodeCoordSectionSorter(cities, 0, tspPath, tspPathStr);
		return tspPath;
	}

	private int[] nodeCoordSectionSorter(ArrayList<City> cities, int count, int tspPath[], String tspPathStr) {
		try {
			if (count == 0) {
				tspPath[count] = cities.get(0).getIndex();
				tspPathStr += "&" + cities.get(0).getIndex() + "&";
				count++;
				nodeCoordSectionSorter(cities, count, tspPath, tspPathStr);
			} else if (count < cities.size()) {
				float min = 1000000000;
				float dist = 0;
				int latestNodeIndex = tspPath[count - 1];
								
				for (int itrI = 0; itrI < cities.size(); itrI++) {
					dist = distanceCaclulator(cities.get(latestNodeIndex), cities.get(itrI));
					if (min > dist && cities.get(latestNodeIndex).getIndex() != cities.get(itrI).getIndex() && !tspPathStr.contains("&" + cities.get(itrI).getIndex() + "&")) {
						min = dist;
						tspPath[count] = cities.get(itrI).getIndex();
					}
				}
				
				tspPathStr += tspPath[count] + "&";
				count++;
				nodeCoordSectionSorter(cities, count, tspPath, tspPathStr);
			} 
		} catch (Exception e) {
			System.out.println("OOPS! Something went wrong.....");
			e.printStackTrace();
		}
		return tspPath;		
	}
	
	private float distanceCaclulator(City city1, City city2)  throws Exception {
		float x1 = city1.getX();
		float y1 = city1.getY();
		
		float x2 = city2.getX();
		float y2 = city2.getY();
		
		float distance = (float) Math.sqrt(Math.pow( x1 - x2, 2) + Math.pow(y1 - y2, 2));
		return distance;
	}

	/**
	 * this function is called when start() function is called of class Thread. 
	 */
	public void run() {
		handleRequest(Workspace.getConnections());
	}

	/**
	 * This function handles the request for this algorithm's request.
	 * 
	 * @param algo, type of algorithm for next successor.
	 */
	@Override
	public void handleRequest(String algo) {
		if (algo.equals("tsp")) {
			sovleTSP();
		} else if (successor != null) {
			successor.handleRequest(algo);
		}
	}

}
