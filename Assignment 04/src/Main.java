import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

/**
 * Assignment 04.Connecting the Dots, CSE 564
 * Professor: Javier Gonzalez-Sanchez
 *
 * @author Kenil Vipulkumar Patel (kpatel99@asu.edu), Srikar Vodeti,
 * 		   Nikhil Karthik Bindem, Venkatesh Yaganti.
 */
public class Main extends JFrame {
	String textMsg = "Create City";
	
	/**
	 * This constructor is used to initialize the menu bar (with it's options) and the object of Workspace, 
	 * where cities will be created and dragged.
	 * It also creates the instance of class TSPNN and adds the object of class Workspace as a observer in class TSPNN.
	 */
	public Main() {
		Workspace drawArea = new Workspace();
		MessageDisplayer messageDisplayer = MessageDisplayer.getInstance();
		JMenuBar mb = new JMenuBar();
		JMenu file = new JMenu("File");
		
		TSPNN h1 = TSPNN.createInstance();
		TSPPro h2 = TSPPro.createInstance();
		KMeans h3 = KMeans.createInstance();
		h1.setSuccessor(h2);
		h2.setSuccessor(h3);
		h1.addObserver(drawArea);
		h2.addObserver(drawArea);
		h3.addObserver(drawArea);

        JMenuItem newData = new JMenuItem("New");
        JMenuItem save = new JMenuItem("Save");
        JMenuItem load = new JMenuItem("Load");
  
        newData.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ev) {
				drawArea.newMenuItem();
				messageDisplayer.setMessage("The working area is cleared.");
	    	}
		});
        
        save.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ev) {
				drawArea.saveCities();
	    	}
		});
        
        load.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ev) {
				drawArea.loadCities();
	    	}
		});
        
        file.add(newData);
        file.add(save);
        file.add(load);
        mb.add(file);
        
        JMenu connection = new JMenu("Connection");
        JMenuItem tsp = new JMenuItem("TSP - nearest neighbor");
        /*tsp.setEnabled(false);*/
        JMenuItem tspPro = new JMenuItem("TSP - pro");
        JMenuItem cluster = new JMenuItem("Clusters");
        JMenuItem userConnect = new JMenuItem("User Connect");
        
        JMenu action = new JMenu("Action");
        JMenuItem move = new JMenuItem("Move");
        JMenuItem connect = new JMenuItem("Connect");
        JMenuItem create = new JMenuItem("Create");
        create.setEnabled(false);
  
        tsp.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ev) {
				/*tsp.setEnabled(false);
				tspPro.setEnabled(true);
				cluster.setEnabled(true);
				userConnect.setEnabled(true);*/
				Workspace.setConnections("tsp");
				Thread thread = new Thread(h1);
		    	thread.start();
				
				move.setEnabled(true);
				create.setEnabled(false);
				connect.setEnabled(true);
				Workspace.setAction("create");
				messageDisplayer.setMessageColor(Color.CYAN);
				messageDisplayer.setMessage("TSP : nearest neighbor algorithm started.");
	    	}
		});
        
        tspPro.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ev) {
				/*tsp.setEnabled(true);
				tspPro.setEnabled(false);
				cluster.setEnabled(true);
				userConnect.setEnabled(true);*/
				Workspace.setConnections("tspPro");
				Thread thread = new Thread(h1);
		    	thread.start();
				
				move.setEnabled(true);
				create.setEnabled(false);
				connect.setEnabled(true);
				Workspace.setAction("create");
				messageDisplayer.setMessageColor(Color.CYAN);
				messageDisplayer.setMessage("TSP : pro algorithm started.");
	    	}
		});
        
        cluster.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ev) {
				/*tsp.setEnabled(true);
				tspPro.setEnabled(true);
				cluster.setEnabled(false);
				userConnect.setEnabled(true);*/
				Workspace.setConnections("cluster");
				
				boolean isItRight = clusterClicked(h3);
				
				if (isItRight) {
					Thread thread = new Thread(h1);
			    	thread.start();
				}
				
				move.setEnabled(true);
				create.setEnabled(false);
				connect.setEnabled(true);
				Workspace.setAction("create");
				messageDisplayer.setMessageColor(Color.CYAN);
				messageDisplayer.setMessage("Clustering algorithm started.");
	    	}
		});
        
        userConnect.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ev) {
				tsp.setEnabled(true);
				tspPro.setEnabled(true);
				cluster.setEnabled(true);
				userConnect.setEnabled(false);
				Workspace.setConnections("userConnect");
				drawArea.userConnectClicked();
				
				move.setEnabled(true);
				create.setEnabled(true);
				connect.setEnabled(false);
				Workspace.setAction("connect");
				messageDisplayer.setMessageColor(Color.CYAN);
				messageDisplayer.setMessage("Existing connections are removed and you can create new connections.");
	    	}
		});
        
        connection.add(tsp);
        connection.add(tspPro);
        connection.add(cluster);
        connection.add(userConnect);
        mb.add(connection);
  
        move.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ev) {
				move.setEnabled(false);
				connect.setEnabled(true);
				create.setEnabled(true);
				Workspace.setAction("move");
				MessageDisplayer messageDisplayer = MessageDisplayer.getInstance();
				messageDisplayer.setMessage("You can move the cities.");
	    	}
		});
        
        connect.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ev) {
				move.setEnabled(true);
				connect.setEnabled(false);
				create.setEnabled(true);
				Workspace.setAction("connect");
				messageDisplayer.setMessage("You can create new connections irrespective of older connections.");
	    	}
		});
        
        create.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ev) {
				move.setEnabled(true);
				connect.setEnabled(true);
				create.setEnabled(false);
				Workspace.setAction("create");
				messageDisplayer.setMessage("You can create new cities.");
	    	}
		});
        
        action.add(move);
        action.add(connect);
        action.add(create);
        mb.add(action);
        setJMenuBar(mb);
        
        drawArea.setBackground(Color.WHITE);
        drawArea.setPreferredSize(new Dimension(500, 450));
        
        messageDisplayer.setBackground(Color.BLACK);
        messageDisplayer.setPreferredSize(new Dimension(500, 50));
        
        add(drawArea, BorderLayout.CENTER);
        add(messageDisplayer, BorderLayout.PAGE_END);
	}
	
	/**
	 * It sets the size of Main(JFrame) and makes it visible.
	 * @param args
	 */
	public static void main(String[] args) {
		Main main = new Main();
		main.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		main.setSize(1910, 1000);
		main.setVisible(true);
	}

	private boolean clusterClicked(KMeans kMeans) {
		String clusterStr = "";
		boolean isItRight = false;
		int clusters = 1;
		int iterations = 100;
		Blackboard bb = Blackboard.createInstance();
		
		do {
			clusterStr = JOptionPane.showInputDialog(this, "Please enter the number of clusters you want : ", null);
			isItRight = isNumeric(clusterStr);
			if(isItRight) {
				clusters = Integer.parseInt(clusterStr);
			}
		}
		while(!isItRight);
		bb.setNoOfClusters(clusters);
		
		isItRight = false;
		do {
			clusterStr = JOptionPane.showInputDialog(this, "Please enter the number of iterations : ", null);
			isItRight = isNumeric(clusterStr);
			if(isItRight) {
				iterations = Integer.parseInt(clusterStr);
			}
		}
		while(!isItRight);
		bb.setMaxIterations(iterations);
		
		return isItRight;
	}
	
	private static boolean isNumeric(String strNum) {
	    if (strNum == null) {
	        return false;
	    }
	    try {
	        int i = Integer.parseInt(strNum);
	    } catch (Exception nfe) {
	        return false;
	    }
	    return true;
	}
}