//
// Pone
//
// This class provides a "main" method that acts as a driver program for
// evaluating a variety of heuristic search algorithms, as applied to 
// shortest-path searches on a map. A Map object is used to read in
// and record a map from two files:  one containing a list of locations and
// the other containing a list of road segments. The user is then prompted
// for a starting location on this map and a destination location. Three
// search algorithms are then tested on this specified search problem:
// uniform-cost search, greedy search, and A* search. Also, the effect of
// repeated state checking is examined. A depth limit is provided to the
// search algorithms, and the algorithms are expected to terminate and 
// report failure if that depth limit is ever reached during search. Summary
// results are sent to the standard output stream.
//
// David Noelle -- Wed Feb 21 17:17:38 PST 2007
//                 Modified Sun Sep 23 18:34:05 PDT 2018
//                   (Made minor changes.)
//                 Modified Mon Sep 28 20:12:19 PDT 2020
//                   (Included SearchDisplay.)
//


import java.io.*;


public class Pone {

    public static void main(String[] args) {
	try {
			RoadMap graph = new RoadMap();
			InputStreamReader converter = new InputStreamReader(System.in);
			BufferedReader in = new BufferedReader(converter);
			String initialLoc;
			String destinationLoc;
			Node solution;
			int limit = 100; // depth limit, to avoid infinite loops
			boolean useSearchDisplay = false; // graphically animate the search process
			SearchDisplay display = null;

			System.out.println("HEURISTIC SEARCH ALGORITHM COMPARISON");
			// Read map ...
			if (!(graph.readMap())) {
				System.err.println("Error:  Unable to read map.");
				return;
			}
			// Get initial and final locations ...
			System.out.println("Enter the name of the initial location:");
			initialLoc = in.readLine();
			System.out.println("Enter the name of the destination location:");
			destinationLoc = in.readLine();
			
			// Initialize search display, if one is to be used ...
			if (useSearchDisplay) {
				display = new SearchDisplay(graph);
				display.init();
			}

			// Testing uniform-cost search without repeated state checking ...
			if (display != null) {
				display.updateAlgorithm("Uniform Cost - no checking");
			}
			System.out.println("TESTING UNIFORM-COST SEARCH WITHOUT REPEATED STATE CHECKING");
			UniformCostSearch ucs = new UniformCostSearch(graph, initialLoc, destinationLoc, limit, display);
			solution = ucs.search(false);
			System.out.println("Solution:");
			if (solution == null) {
				System.out.println("None found.");
			} else {
				solution.reportSolution(System.out);
				System.out.printf("Depth = %d.\n", solution.depth);
				System.out.printf("Path Cost = %f.\n", solution.partialPathCost);
			}
			System.out.printf("Number of Node Expansions = %d.\n", ucs.expansionCount);

			// Testing uniform-cost search with repeated state checking ...
			if (display != null) {
				display.updateAlgorithm("Uniform Cost - with checking");
			}
			System.out.println("TESTING UNIFORM-COST SEARCH WITH REPEATED STATE CHECKING");
			solution = ucs.search(true);
			System.out.println("Solution:");
			if (solution == null) {
				System.out.println("None found.");
			} else {
				solution.reportSolution(System.out);
				System.out.printf("Depth = %d.\n", solution.depth);
				System.out.printf("Path Cost = %f.\n", solution.partialPathCost);
			}
			System.out.printf("Number of Node Expansions = %d.\n", ucs.expansionCount);

			// Testing greedy search without repeated state checking ...
			if (display != null) {
				display.updateAlgorithm("Greedy - no checking");
			}
			System.out.println("TESTING GREEDY SEARCH WITHOUT REPEATED STATE CHECKING");
			GreedySearch gs = new GreedySearch(graph, initialLoc, destinationLoc, limit, display);
			solution = gs.search(false);
			System.out.println("Solution:");
			if (solution == null) {
				System.out.println("None found.");
			} else {
				solution.reportSolution(System.out);
				System.out.printf("Depth = %d.\n", solution.depth);
				System.out.printf("Path Cost = %f.\n", solution.partialPathCost);
			}
			System.out.printf("Number of Node Expansions = %d.\n", gs.expansionCount);

			// Testing greedy search with repeated state checking ...
			if (display != null) {
				display.updateAlgorithm("Greedy - with checking");
			}
			System.out.println("TESTING GREEDY SEARCH WITH REPEATED STATE CHECKING");
			solution = gs.search(true);
			System.out.println("Solution:");
			if (solution == null) {
				System.out.println("None found.");
			} else {
				solution.reportSolution(System.out);
				System.out.printf("Depth = %d.\n", solution.depth);
				System.out.printf("Path Cost = %f.\n", solution.partialPathCost);
			}
			System.out.printf("Number of Node Expansions = %d.\n", gs.expansionCount);

			// Testing A* search without repeated state checking ...
			if (display != null) {
				display.updateAlgorithm("A* - no checking");
			}
			System.out.println("TESTING A* SEARCH WITHOUT REPEATED STATE CHECKING");
			AStarSearch as = new AStarSearch(graph, initialLoc, destinationLoc, limit, display);
			solution = as.search(false);
			System.out.println("Solution:");
			if (solution == null) {
				System.out.println("None found.");
			} else {
				solution.reportSolution(System.out);
				System.out.printf("Depth = %d.\n", solution.depth);
				System.out.printf("Path Cost = %f.\n", solution.partialPathCost);
			}
			System.out.printf("Number of Node Expansions = %d.\n", as.expansionCount);

			// Testing A* search with repeated state checking ...
			if (display != null) {
				display.updateAlgorithm("A* - with checking");
			}
			System.out.println("TESTING A* SEARCH WITH REPEATED STATE CHECKING");
			solution = as.search(true);
			System.out.println("Solution:");
			if (solution == null) {
				System.out.println("None found.");
			} else {
				solution.reportSolution(System.out);
				System.out.printf("Depth = %d.\n", solution.depth);
				System.out.printf("Path Cost = %f.\n", solution.partialPathCost);
			}
			System.out.printf("Number of Node Expansions = %d.\n", as.expansionCount);

			// Done ...
			System.out.println("ALGORITHM COMPARISON COMPLETE");
		} catch (IOException e) {
			// Something went wrong ...
		}
		// Terminate the application ...
		System.exit(1);
	}
    
}
