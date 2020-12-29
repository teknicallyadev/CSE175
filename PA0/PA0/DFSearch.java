import java.util.HashSet;

//PSEUDOCODE FROM BOOK THANK GOD FOR THE BOOK FFS THIS SHIT TOOK TOO LONG

public class DFSearch
{
    public Map graph;
    public String initialLoc;
    public String destinationLoc;
    public int limit;
    public int depth;
    public int expansionCount;
    public SearchDisplay display;

    public DFSearch(Map graph, String initialLoc, String destinationLoc, int limit, SearchDisplay display)
    {
        this.graph = graph;
        this.initialLoc = initialLoc;
        this.destinationLoc = destinationLoc;
        this.limit = limit-1; //0 to 99
        this.display = display;
    }

    public Node search(boolean RepeatedState)
    {
        this.depth = 0;
        expansionCount = 0;

        //frontier <- a FIFO queue with node as the only element
        Frontier frontier = new Frontier();

        //node <- a node with STATE = problem.INITIAL-STATE
        Node parent = new Node(graph.findLocation(initialLoc));

        frontier.addToTop(parent);

        //explored <- an empty set
        HashSet<String> explored = new HashSet<>();

        //if EMPTY?(frontier) then return failure
        if(frontier.isEmpty())
        {
            return null;
        }

        explored.add(parent.loc.name);

        if(RepeatedState)
        {
            //loop do
            while(depth <= this.limit)
            {
                //node <- POP(frontier) /*chooses the shallowest node in frontier*/
                parent = frontier.removeTop();

                //if problem.GOAL-TEST(node.STATE) then return SOLUTION(node)
                if(parent.isDestination(destinationLoc))
                {
                    return parent;
                }
                parent.expand();
                this.expansionCount++;

                for(Node child : parent.children)
                {
                    if(!explored.contains(child.loc.name)) {
                        frontier.addToTop(child);
                        explored.add(child.loc.name);
                    }
                }
                depth++;
            }
        }
        else
        {
            while(!frontier.isEmpty() && depth <= this.limit)
            {
                parent = frontier.removeTop();

                if(parent.isDestination(destinationLoc))
                {
                    return parent;
                }
                expansionCount++;
                parent.expand();
                frontier.addToTop(parent.children);
                depth++;
            }
            return null;
        }
        return null;
    }
}