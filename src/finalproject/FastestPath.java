package finalproject;

import finalproject.system.Tile;
import finalproject.tiles.MetroTile;

public class FastestPath extends PathFindingService {
    public FastestPath(Tile start) {
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
                if (neigh instanceof MetroTile && vertex instanceof MetroTile) {
                    ((MetroTile) neigh).fixMetro(vertex);
                    g.addEdge(vertex, neigh, ((MetroTile) neigh).metroTimeCost);
                }
                else if (neigh.isWalkable()) g.addEdge(vertex, neigh, neigh.timeCost);
            }
        }
	}

}
