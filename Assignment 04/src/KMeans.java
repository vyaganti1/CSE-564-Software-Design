import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Scanner;

public class KMeans extends Observable implements Handler {
	private static KMeans _instance;
	protected Handler successor;
	
	private KMeans() {
	}
	
	/**
	 * This function is used to create the object class KMeans and if it is already created, it returns the old object.
	 * That is, there will only be one object this class with different references.
	 * 
	 * @return _instance object of class KMeans. 
	 */
	public static KMeans createInstance() {
		if (_instance == null) {
			_instance = new KMeans();
		}
		
		return _instance;
	}
	
	/**
	 * This function sets the working successor in chain of command
	 * 
	 * @param algo, type of algorithm for next successor.
	 */
	public void setSuccessor(Handler successor) {
		this.successor = successor;
	}
	
	/**
	 * This function runs the clustering algorithm.
	 */
	public void clustering() {
		try {
			Blackboard bb = Blackboard.createInstance();
			ArrayList<City> cities = bb.getCities();
			int records = cities.size();
			int clusters = bb.getNoOfClusters();
			int maxIterations = bb.getMaxIterations();

			double[][] points = new double[records][2];
			for (int itr = 0; itr < records; itr++) {
				points[itr][0] = cities.get(itr).getX();
				points[itr][1] = cities.get(itr).getY();
			}

			sortPointsByX(points);

			double[][] means = new double[clusters][2];
			for(int i=0; i<means.length; i++) {
				means[i][0] = points[(int) (Math.floor((records*1.0/clusters)/2) + i*records/clusters)][0];
				means[i][1] = points[(int) (Math.floor((records*1.0/clusters)/2) + i*records/clusters)][1];
			}

			ArrayList<Integer>[] oldClusters = new ArrayList[clusters];
			ArrayList<Integer>[] newClusters = new ArrayList[clusters];

			for(int i=0; i<clusters; i++) {
				oldClusters[i] = new ArrayList<Integer>();
				newClusters[i] = new ArrayList<Integer>();
			}

			oldClusters = formClusters(oldClusters, means, points);
			int iterations = 0;

			while(true) {
				means = updateMeans(oldClusters, means, points);
				newClusters = formClusters(newClusters, means, points);

				iterations++;

				if(iterations > maxIterations || checkEquality(oldClusters, newClusters))
					break;
				else
					oldClusters = resetClusters(oldClusters, newClusters);
			}
			
			bb.setClusters(oldClusters);
			setChanged();
			notifyObservers();
		} catch (Exception e) {
			
		}
	}

	private void sortPointsByX(double[][] points) {
		double[] temp;

		// Bubble Sort
		for(int i=0; i<points.length; i++)
		    for(int j=1; j<(points.length-i); j++)
			if(points[j-1][0] > points[j][0]) {
			    temp = points[j-1];
			    points[j-1] = points[j];
			    points[j] = temp;
			}
	}

	private double[][] updateMeans(ArrayList<Integer>[] clusterList, double[][] means, double[][] points) {
		double totalX = 0;
		double totalY = 0;
		for(int i=0; i<clusterList.length; i++) {
			totalX = 0;
			totalY = 0;
			for(int index: clusterList[i]) {
				totalX += points[index][0];
				totalY += points[index][1];
			}
			means[i][0] = totalX/clusterList[i].size();
			means[i][1] = totalY/clusterList[i].size();
		}
		return means;
	}

	private ArrayList<Integer>[] formClusters(ArrayList<Integer>[] clusterList, double[][] means, double[][] points) {
		double distance[] = new double[means.length];
		double minDistance = 999999999;
		int minIndex = 0;

		for(int i=0; i<points.length; i++) {
			minDistance = 999999999;
			for(int j=0; j<means.length; j++) {
				distance[j] = Math.sqrt(Math.pow((points[i][0] - means[j][0]), 2) + Math.pow((points[i][1] - means[j][1]), 2));
				if(distance[j] < minDistance) {
					minDistance = distance[j];
					minIndex = j;
				}
			}
			clusterList[minIndex].add(i);
		}
		return clusterList;
	}

	private boolean checkEquality(ArrayList<Integer>[] oldClusters, ArrayList<Integer>[] newClusters) {
		for(int i=0; i<oldClusters.length; i++) {
			// Check only lengths first
			if(oldClusters[i].size() != newClusters[i].size())
				return false;

			// Check individual values if lengths are equal
			for(int j=0; j<oldClusters[i].size(); j++)
				if(oldClusters[i].get(j) != newClusters[i].get(j))
					return false;
		}

		return true;
	}

	private ArrayList<Integer>[] resetClusters(ArrayList<Integer>[] oldClusters, ArrayList<Integer>[] newClusters) {
		for(int i=0; i<newClusters.length; i++) {
			// Copy newClusters to oldClusters
			oldClusters[i].clear();
			for(int index: newClusters[i])
				oldClusters[i].add(index);

			// Clear newClusters
			newClusters[i].clear();
		}
		
		return oldClusters;
	}

	private void displayOutput(ArrayList<Integer>[] clusterList, double[][] points) {
		for(int i=0; i<clusterList.length; i++) {
			String clusterOutput = "\n[";
			for(int index: clusterList[i])
				clusterOutput += "(" + points[index][0] + ", " + points[index][1] + "), ";
			System.out.println(clusterOutput.substring(0, clusterOutput.length()-2) + "]");
		}
		
		for(int i=0; i<clusterList.length; i++) {
			String clusterOutput = "\n[ ";
			for(int index: clusterList[i]) {
				clusterOutput += index + " ";
			}
		}
	}

	/**
	 * This function handles the request for this algorithm's request.
	 * 
	 * @param algo, type of algorithm for next successor.
	 */
	@Override
	public void handleRequest(String algo) {
		if (algo.equals("cluster")) {
			clustering();
		}
	}
}
