package finalproject;


import finalproject.system.Tile;
import java.util.ArrayList;
public class ShortestPath extends PathFindingService {
    public ShortestPath(Tile start) {
        super(start);
        this.source = start;
        GraphTraversal traverse = new GraphTraversal();
        this.g = new Graph(traverse.BFS(this.source));
        generateGraph();
    }

	@Override
	public void generateGraph() {
        // do bfs traversal and store tiles in arraylist
        // use neighbor field to connect vertices
        
        // for node in vertices, iterate through neighbors and make edge, add neighbor to graph
        for (var vertex : g.verticesList) {
            System.out.println(vertex);
            
            for (var neigh : vertex.neighbors) {
                System.out.println("neighbor: " + neigh);
                if (neigh.isWalkable()) g.addEdge(vertex, neigh, neigh.distanceCost);
            }
        }
	}
    
}