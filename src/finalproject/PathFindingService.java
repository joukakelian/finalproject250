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
        // make queue of all graph's vertices
        TilePriorityQ queue = new TilePriorityQ(g.verticesList);
        // set cost to inf on all vertices, set pred to null
        for (var vertex : g.verticesList) {
            vertex.costEstimate = Integer.MAX_VALUE;
            vertex.predecessor = null;
        }
        // set startNode cost est to 0
        startNode.costEstimate = 0;
        System.out.println(queue.size <= 0);
        while (queue.size > 0) {
            
            Tile min = queue.removeMin();
            System.out.println("min: " + min);
            for (var n : min.neighbors) {
                ArrayList<Tile> arr = new ArrayList<>();
                arr.add(min);
                arr.add(n);
                double temp = min.costEstimate + g.computePathCost(arr);
                if (temp < n.costEstimate) {
                    n.costEstimate = temp;
                    n.predecessor = min;
                }
            }
        }
        
        ArrayList<Tile> path = new ArrayList<>();
        
        Tile node = g.verticesList.get(0);
        int i = 0;
        while (!node.isDestination && i < g.verticesList.size()) {
            node = g.verticesList.get(i);
            i++;
        }
        // now node id destination
        Tile pred = node.predecessor;
        while (pred != null) {
            path.add(path.size(), pred);
            pred = pred.predecessor;
        }
        path.add(startNode);
        // while queue is not empty
            // remove min tile
            // loop through all neighbors and relax edges linking from minTile towards its neighbor
            // downheap
        // break once reach isDestination
        // backtrack
        
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

