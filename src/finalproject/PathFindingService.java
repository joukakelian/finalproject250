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
    
    // #################################################################################################################
                        // USE UPDATE KEYS TO READJUST THE POSITION AFTER CHANGING THE COST ESTIMATE
    // #################################################################################################################
    
    // finding shortest path to a tile's destination field
    public ArrayList<Tile> findPath(Tile startNode) {
        ArrayList<Tile> visited = new ArrayList<>();
        TilePriorityQ queue = new TilePriorityQ(g.verticesList);
        
        startNode.costEstimate = 0;
        for (var tile: queue.heap) {
            if (tile != startNode) tile.costEstimate = Integer.MAX_VALUE;
            tile.predecessor = null;
        }
        
        Tile dest = null;
        for (var vertex : g.verticesList) {
            if (vertex.isDestination) {
                dest = vertex;
                break;
            }
        }
        while (!queue.isEmpty()) {
            System.out.println("-------------------------------------------");
            Tile min = queue.removeMin();
            System.out.println("adding " + min);
            
            visited.add(min);
            
            for (var n : g.getNeighbors(min)) {
                if (queue.heap.contains(n)) {
                    
                    if (min.costEstimate + n.edgeWeight < n.costEstimate) {
                        queue.updateKeys(n, min, min.costEstimate + n.edgeWeight);
                        System.out.println("neighbor is: " + n);
                    }
                }
            }
        }
        
        
        
        System.out.println("\n------------------> dest is: " + dest);
        ArrayList<Tile> path = new ArrayList<>();
        while (dest != null && dest != startNode) {
            path.add(0,dest);
            dest = dest.predecessor;
            
        }
        path.add(0,startNode);
        System.out.println("\n+++++++++ path is +++++++++");
        System.out.println(path + "\n");
        return path;
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

