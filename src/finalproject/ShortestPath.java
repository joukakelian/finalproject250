package finalproject;


import finalproject.system.Tile;

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
        for (var vertex : g.verticesList) {
            System.out.println(vertex);
            
            for (var neigh : vertex.neighbors) {
                if (neigh.isWalkable()) g.addEdge(vertex, neigh, neigh.distanceCost);
            }
        }
	}
    
}