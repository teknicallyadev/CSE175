import java.util.*;
//Code from Lectures <- David Noelle and Collaborated with Kiran
public class UniformCostSearch {
    RoadMap graph;
    String initialLoc;
    String destLoc;
    int limit;
    SearchDisplay display;
    public int expansionCount;
    //Hash Set Replacement
    LocationSet explored = new LocationSet();

    public UniformCostSearch(RoadMap graph, String initialLoc, String destLoc, int limit, SearchDisplay display) {
        this.graph = graph;
        this.initialLoc = initialLoc;
        this.destLoc = destLoc;
        this.limit = limit;
        this.display = display;
    }

    public Node search(boolean repeat) {
        //Frontier for sorted values
        SortedFrontier sorted = new SortedFrontier(SortBy.g);
        //Set expansionCount = 0;
        expansionCount = 0;
        //Get initial location
        Location initialLoc = graph.findLocation(this.initialLoc);
        //Initial Node
        Node initial = new Node(initialLoc, null);
        //Give initial node to frontier
        sorted.addSorted(initial);
        //Actual Algorithm
        while (!sorted.isEmpty()) {
            //Initial node takes in next node in frontier
            initial = sorted.removeTop();
            //Reached limit
            if (initial.depth >= this.limit) {
                return null;
            }
            //Update display
            if (display != null) {
                display.updateDisplay(initial, sorted, explored, expansionCount);
            }
            //If destLoc is reached
            if (initial.isDestination(this.destLoc)) {
                return initial;
            }
            initial.expand();
            expansionCount++;
            //Expand node and add to explored
            if (!repeat) {
                sorted.addSorted(initial.children);
            } else {
                //Check child nodes
                for (Node child : initial.children) {
                    if (repeat) {
                        if (!explored.contains(child.loc.name)) {
                            sorted.addSorted(child);
                            explored.add(child.loc.name);
                        } else {
                            if (sorted.contains(child) && sorted.find(child).partialPathCost > child.partialPathCost) {
                                sorted.remove(sorted.find(child));
                                sorted.addSorted(child);
                            }
                        }
                    }
                }
            }
        }
        return null;
    }
}
