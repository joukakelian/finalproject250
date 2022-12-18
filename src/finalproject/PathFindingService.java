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
        while (queue.size != 0) {
            Tile min = queue.removeMin();
            for (var n : g.getNeighbors(min)) {
                if (queue.heap.contains(n)) {
                    
                    if (min.costEstimate + n.edgeWeight < n.costEstimate) {
                        queue.updateKeys(n, min, min.costEstimate + n.edgeWeight);
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
    
    public ArrayList<Tile> findPath(Tile start, Tile end) {
        TilePriorityQ queue = new TilePriorityQ(g.verticesList);
    
        start.costEstimate = 0;
        for (var tile: queue.heap) {
            if (tile != start) tile.costEstimate = Integer.MAX_VALUE;
            tile.predecessor = null;
        }
        
        while (queue.size != 0) {
            Tile min = queue.removeMin();
            
            for (var n : g.getNeighbors(min)) {
                if (queue.heap.contains(n)) {
                
                    if (min.costEstimate + n.edgeWeight < n.costEstimate) {
                        queue.updateKeys(n, min, min.costEstimate + n.edgeWeight);
                    }
                }
            }
            if (min == end) break;
        }
        
        ArrayList<Tile> path = new ArrayList<>();
        while (end != null && end != start) {
            path.add(0,end);
            end = end.predecessor;
        
        }
        path.add(0,start);
        System.out.println("\n+++++++++ path is +++++++++");
        System.out.println(path + "\n");
        return path;
    }

    //TODO level 5: Implement basic dijkstra's algorithm to path find to the final destination passing through given waypoints
    public ArrayList<Tile> findPath(Tile start, LinkedList<Tile> waypoints){
    	return null;
    }
        
}

