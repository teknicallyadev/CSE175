//
// SearchDisplay
//
// This class provides a way to graphically view the progression of a
// search for a shortest route on a map. A Map object is used to
// describe the map layout. A Node object is used to characterize a
// path that is currently under consideration, with the path to be
// displayed being the one from the root of the search tree to the given
// node. A Frontier object is used display the states in the current
// fringe of the search. An optional LocationSet object specifies
// previously expanded locations.
//
// David Noelle -- Fri Sep 11 22:21:17 PDT 2020
//

import java.awt.*;
import javax.swing.*;


public class SearchDisplay extends JPanel {
	static final long serialVersionUID = 0;
	
	static final int displayWidth = 1000;   // pixel width of display
	static final int displayHeight = 1000;  // pixel height of display
	static final int borderSize = 50;       // pixel frame thickness
	
	static final int locDiameter = 9;       // pixel diameter of locations
	static final int roadThickness = 1;     // pixel thickness of roads
	
	static final long pauseDelay = 1000;    // milliseconds per update
	
	Map graph;                        // the map
	Node pathEnd;                     // the node about to be expanded
	Frontier fringe;                  // the search frontier
	LocationSet closedList = null;    // the closed list
	
	String algorithm;         // the name of the search algorithm
	int nodesExpanded;        // the node expansion count
	
	JFrame window = null;     // the graphics window
	double minX;              // minimum longitude on the map
	double maxX;              // maximum longitude on the map
	double minY;              // minimum latitude on the map
	double maxY;              // maximum longitude on the map
	
	// pauseRunnable -- A Runnable object that can be used to sleep
	// the program for "pauseDelay" milliseconds.
	final Runnable pauseRunnable = new Runnable() {
		public void run() {
			try {
				  Thread.sleep(pauseDelay);
			  } catch (Exception e) {			  
			  } 
		}
	};
	
	// Default constructor ...
	public SearchDisplay() {
		this.graph = null;
		this.pathEnd = null;
		this.fringe = null;
		this.closedList = null;
		this.window = null;
	}
	
	// Constructor with only the map ...
	public SearchDisplay(Map network) {
		this.graph = network;
		this.pathEnd = null;
		this.fringe = null;
		this.closedList = null;
		this.window = null;
		recordMapCoordinates();
	}
	
	// Constructor without closed list ...
	public SearchDisplay(Map network, Node current, Frontier fringe) {
		this.graph = network;
		this.pathEnd = current;
		this.fringe = fringe;
		this.closedList = null;
		this.window = null;
		recordMapCoordinates();
	}
	
	// Constructor with closed list ...
	public SearchDisplay(Map network, Node current, Frontier fringe,
						 LocationSet closedSet) {
		this.graph = network;
		this.pathEnd = current;
		this.fringe = fringe;
		this.closedList = closedSet;
		this.window = null;
		recordMapCoordinates();
	}

	// recordMapCoordinates -- Extract information from the map needed to
	// translate map coordinates into image pixel coordinates.
	void recordMapCoordinates() {
		minX = 1000000.0;
		maxX = -1000000.0;
		minY = 1000000.0;
		maxY = -1000000.0;
		
		if (graph != null) {
			for (Location loc : graph.locations) {
				if (loc.longitude < minX) {
					minX = loc.longitude;
				}
				if (loc.longitude > maxX) {
					maxX = loc.longitude;
				}
				if (loc.latitude < minY) {
					minY = loc.latitude;
				}
				if (loc.latitude > maxY) {
					maxY = loc.latitude;
				}
			}
		} else {
			minX = 0.0;
			maxX = 0.0;
			minY = 0.0;
			maxY = 0.0;
		}
	}
	
	// getXCoord -- Translate the given map x coordinate into a
	// corresponding image x coordinate.
	int getXCoord(double longitude) {
		double fraction = (longitude - minX) / (maxX - minX);
		int pixelX = (int) Math.round(fraction * (double) (displayWidth - 2 * borderSize))
						+ borderSize;
		return (pixelX);
	}
	
	// getYCoord -- Translate the given map y coordinate into a
	// corresponding image y coordinate. Note that the origin is
	// the upper-left corner of the image.
	int getYCoord(double latitude) {
		double fraction = (latitude - minY) / (maxY - minY);
		int pixelY = (int) Math.round(fraction * (double) (displayHeight - 2 * borderSize))
						+ borderSize;
		pixelY = displayHeight - pixelY;
		return (pixelY);		
	}
	
	// init -- Initialize and open the search display window.
	public void init() {
		window = new JFrame("Search Display");
		window.setContentPane(this);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setSize(displayWidth, displayHeight);
		window.setResizable(false);
		// Center the window on the computer screen ...
		Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
		window.setLocation(((screen.width - window.getWidth())/2),
						   ((screen.height - window.getHeight())/2));
		// Make the window visible ...
		window.setVisible(true);
	}
	
	// updateAlgorithm -- Set the algorithm name to the given string.
	public void updateAlgorithm(String name) {
		algorithm = name;
	}
	
	// updateDisplay -- Update the display data structures using the arguments and then
	// re-render the image.
	public void updateDisplay(Node currentNode, Frontier fringe, LocationSet closedList,
							  int expandedNodes) {
		this.pathEnd = currentNode;
		this.fringe = fringe;
		this.closedList = closedList;
		this.nodesExpanded = expandedNodes;
		// Produce an event to update the window ...
		this.repaint();
		// Wait a while before returning from this function ...
		try {
			SwingUtilities.invokeAndWait(this.pauseRunnable);
		} catch (Exception e) {  }
	}
	
	// closeDisplay -- Remove the display.
	public void closeDisplay() {
		window.setVisible(false);
		window = null;
	}
	
	// paintComponent -- Override method to display the panel contents.
	protected void paintComponent(Graphics g) {
        if ((g != null) && (graph != null)) {
        	// Copy the Graphics2D drawing context to avoid changing the one that is
    		// passed in as an argument ...
    		Graphics2D g2 = (Graphics2D) g.create();
    		
    		// Turn on antialiasing ...
    		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
    		// Paint the background white ...
    		g2.setPaint(Color.WHITE);
            g2.fillRect(0, 0, this.getWidth(), this.getHeight());
            
            // Paint title ...
            String title = algorithm + " - nodes expanded = " + Integer.toString(nodesExpanded);
            g2.setColor(Color.BLACK);
            g2.drawString(title, (this.getWidth() / 2), (borderSize / 2));
            
            // Set up for painting the map ...
            BasicStroke roadStroke = new BasicStroke(roadThickness);
            
        	// Paint locations and roads ...
            int centerX, centerY;                // location center in pixels
            int locRadius = locDiameter / 2;     // radius of location circles
            int roadX1, roadX2, roadY1, roadY2;  // road endpoint coordinates
            // Iterate over map locations ...
            for (Location loc : graph.locations) {
            	// By default, paint a location black ...
            	g2.setColor(Color.BLACK);
            	// Paint locations on the closed list red ...
            	if ((closedList != null) && (closedList.contains(loc))) {
            		g2.setColor(Color.RED);
            	}
            	// Paint locations in the frontier blue ...
            	if ((fringe != null) && (fringe.contains(loc))) {
            		g2.setColor(Color.BLUE);
            	}
            	// Paint the location of the node currently being considered
            	// for expansion green ...
            	if ((pathEnd != null) && (pathEnd.loc.equals(loc))) {
            		g2.setColor(Color.GREEN);
            	}
            	// Convert the location coordinates to pixels ...
            	centerX = getXCoord(loc.longitude);
            	centerY = getYCoord(loc.latitude);
            	// Paint the location ...
            	g2.fillOval((centerX - locRadius), (centerY - locRadius),
            				locDiameter, locDiameter);
            	// Paint the location name ...
            	g2.drawString(loc.name,
            				  (centerX + locDiameter), (centerY - locDiameter));
            	// By default, paint roads black ...
        		g2.setColor(Color.BLACK);
        		g2.setStroke(roadStroke);
            	// Paint roads ...
            	for (Road r : loc.roads) {
            		// Convert road end coordinates into pixel coordinates ...
            		roadX1 = getXCoord(r.fromLocation.longitude);
            		roadY1 = getYCoord(r.fromLocation.latitude);
            		roadX2 = getXCoord(r.toLocation.longitude);
            		roadY2 = getYCoord(r.toLocation.latitude);
            		g2.drawLine(roadX1, roadY1, roadX2, roadY2);
            	}
            }
            
            // Paint current path ...
            if (pathEnd != null) {
            	// Make roads along the current path thicker and blue ...
            	roadStroke = new BasicStroke(roadThickness * 4);
            	g2.setStroke(roadStroke);
            	g2.setColor(Color.BLUE);
            	// Paint roads along the current path, starting at the
            	// current node and working back to the root of the
            	// search tree ...
            	Node toNode = pathEnd;
            	while (toNode.parent != null) {
            		// Convert road end coordinates into pixel coordinates ...
            		roadX1 = getXCoord(toNode.parent.loc.longitude);
            		roadY1 = getYCoord(toNode.parent.loc.latitude);
            		roadX2 = getXCoord(toNode.loc.longitude);
            		roadY2 = getYCoord(toNode.loc.latitude);
            		g2.drawLine(roadX1, roadY1, roadX2, roadY2);
            		toNode = toNode.parent;
            	}
            	
            }
        }
	}
	
}
