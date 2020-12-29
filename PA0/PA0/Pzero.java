//
// Pzero
//
// This class provides a "main" method that acts as a driver program for
// evaluating breadth-first search and depth-first search algorithms, as
// applied to shortest-path searches on a map. A Map object is used to 
// read in and record a map from two files:  one containing a list of
// locations and the other containing a list of road segments. The user
// is then prompted for a starting location on this map and a destination
// location. The two search algorithms in question are then tested on the
// specified search problem, and the effect of repeated state checking is
// also examined. A depth limit is provided to the search algorithms, and
// the algorithms are expected to terminate and report failure if that depth
// limit is ever reached during search. Summary results are sent to the 
// standard output stream.
//
// David Noelle -- Sat Sep 12 18:12:23 PDT 2020
//


import java.io.*;


public class Pzero	 {

	public static void main(String[] args) {
		try {
			Map graph = new Map();
			InputStreamReader converter = new InputStreamReader(System.in);
			BufferedReader in = new BufferedReader(converter);
			String initialLoc;
			String destinationLoc;
			Node solution;
			int limit = 100; // depth limit, to avoid infinite loops
			boolean useSearchDisplay = false; // graphically animate the search process
			SearchDisplay display = null;
			
		
			System.out.println("UNINFORMED SEARCH ALGORITHM COMPARISON");
			// Read map ...
			if (!(graph.readMap())) {
				System.err.println("Error: Unable to read map.");
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

			// Testing BFS without repeated state checking ...
			if (display != null) {
				display.updateAlgorithm("BFS - no checking");
			}
			System.out.println("TESTING BFS WITHOUT REPEATED STATE CHECKING");
			BFSearch bfs = new BFSearch(graph, initialLoc, destinationLoc, limit, display);
			solution = bfs.search(false);
			System.out.println("Solution:");
			if (solution == null) {
				System.out.println("None found.");
			} else {
				solution.reportSolution(System.out);
				System.out.printf("Depth = %d.\n", solution.depth);
				System.out.printf("Path Cost = %f.\n", solution.partialPathCost);
			}
			System.out.printf("Number of Node Expansions = %d.\n", bfs.expansionCount);

			// Testing BFS with repeated state checking ...
			if (display != null) {
				display.updateAlgorithm("BFS - with checking");
			}
			System.out.println("TESTING BFS WITH REPEATED STATE CHECKING");
			solution = bfs.search(true);
			System.out.println("Solution:");
			if (solution == null) {
				System.out.println("None found.");
			} else {
				solution.reportSolution(System.out);
				System.out.printf("Depth = %d.\n", solution.depth);
				System.out.printf("Path Cost = %f.\n", solution.partialPathCost);
			}
			System.out.printf("Number of Node Expansions = %d.\n", bfs.expansionCount);

			// Testing DFS without repeated state checking ...
			if (display != null) {
				display.updateAlgorithm("DFS - no checking");
			}
			System.out.println("TESTING DFS WITHOUT REPEATED STATE CHECKING");
			DFSearch dfs = new DFSearch(graph, initialLoc, destinationLoc, limit, display);
			solution = dfs.search(false);
			System.out.println("Solution:");
			if (solution == null) {
				System.out.println("None found.");
			} else {
				solution.reportSolution(System.out);
				System.out.printf("Depth = %d.\n", solution.depth);
				System.out.printf("Path Cost = %f.\n", solution.partialPathCost);
			}
			System.out.printf("Number of Node Expansions = %d.\n", dfs.expansionCount);

			// Testing DFS with repeated state checking ...
			if (display != null) {
				display.updateAlgorithm("DFS - with checking");
			}
			System.out.println("TESTING DFS WITH REPEATED STATE CHECKING");
			solution = dfs.search(true);
			System.out.println("Solution:");
			if (solution == null) {
				System.out.println("None found.");
			} else {
				solution.reportSolution(System.out);
				System.out.printf("Depth = %d.\n", solution.depth);
				System.out.printf("Path Cost = %f.\n", solution.partialPathCost);
			}
			System.out.printf("Number of Node Expansions = %d.\n", dfs.expansionCount);

			// Done ...
			System.out.println("ALGORITHM COMPARISON COMPLETE");
			// Close search display, if one is being used ...
			if (display != null) {
				display.closeDisplay();
			}
		} catch (IOException e) {
			// Something went wrong ...
		}
		// Terminate the application ...
		System.exit(1);
	}
    
}
