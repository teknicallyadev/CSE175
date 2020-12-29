import java.util.*;
import java.lang.Math;

public class GoodHeuristic extends Heuristic {
	public RoadMap graph;
	public GoodHeuristic(RoadMap graph, Location destination) {
		this.graph = graph;
		this.destination = destination;
	}
	//Euclidean <- Thank you StackOverFlow :D
	public double heuristicValue(Node thisNode) {
		double velocity = 0;

		double x = (this.destination.latitude - thisNode.loc.latitude);
		double y = (this.destination.longitude - thisNode.loc.longitude);

		for(Location loc: graph.locations) {
			for(Road road: loc.roads) {
				if(velocity < (Math.hypot(road.fromLocation.latitude - road.toLocation.latitude, road.fromLocation.longitude - road.toLocation.longitude)/road.cost)){
						velocity  = Math.hypot(road.fromLocation.latitude - road.toLocation.latitude, road.fromLocation.longitude - road.toLocation.longitude)/road.cost;
				}
			}
		}

		return (Math.sqrt(x*x+y*y))/velocity;
	}
}
