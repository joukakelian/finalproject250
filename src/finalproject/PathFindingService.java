package finalproject;

import finalproject.system.Tile;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;

public abstract class PathFindingService {
	Tile source;
	Graph g;
	
	public PathFindingService(Tile start) {
    	this.source = start;
    }

	public abstract void generateGraph();
    
    // finding shortest path to a tile's destination field
    public ArrayList<Tile> findPath(Tile startNode) {
        ArrayList<Tile> visited = new ArrayList<>();
        TilePriorityQ queue = new TilePriorityQ(g.verticesList);
        
        startNode.costEstimate = 0;
        for (var tile: queue.heap) {
            if (tile != startNode) tile.costEstimate = Integer.MAX_VALUE;
            tile.predecessor = null;
        }
        
        System.out.println("*");
        while (!queue.isEmpty()) {
            System.out.println("size is " + queue.size);
            Tile min = queue.removeMin();
            visited.add(min);
            for (var n : g.getNeighbors(min)) {
                ArrayList<Tile> path = new ArrayList<>();
                path.add(min);
                path.add(n);
                g.computePathCost(path);
                if (g.computePathCost(path) < n.costEstimate) {
                    n.costEstimate = min.costEstimate + g.computePathCost(path);
                    n.predecessor = min;
                }
            }
        }
        System.out.println("**");
        Tile dest = null;
        for (var tile : visited) {
            if (tile.isDestination) {
                dest = tile;
            }
        }
        System.out.println("***");
        ArrayList<Tile> findPath = new ArrayList<>();
        
        // infinite loop
        // A <-> B
        while (dest != startNode) {
            //System.out.println(dest);
            findPath.add(0, dest);
            
            dest = dest.predecessor;
            
        }
        findPath.add(0, startNode);
        System.out.println("****");
        return findPath;
    }
    
    //TODO level 5: Implement basic dijkstra's algorithm to path find to a known destination
    public ArrayList<Tile> findPath(Tile start, Tile end) {
    	return null;
    }

    //TODO level 5: Implement basic dijkstra's algorithm to path find to the final destination passing through given waypoints
    public ArrayList<Tile> findPath(Tile start, LinkedList<Tile> waypoints){
    	return null;
    }
        
}

