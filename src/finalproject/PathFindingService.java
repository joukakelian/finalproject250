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
                    ArrayList<Tile> path = new ArrayList<>();
                    path.add(min);
                    path.add(n);
                    double computePathCost = g.computePathCost(path);
        
                    if (min.costEstimate + computePathCost < n.costEstimate) {
                        n.costEstimate = min.costEstimate + computePathCost;
                        n.predecessor = min;
                    }
                }
            }
        }
        
        ArrayList<Tile> path = new ArrayList<>();
        while (dest != null && dest != startNode) {
            path.add(0,dest);
            dest = dest.predecessor;
            
        }
        path.add(0,startNode);
       
        return path;
    }
    
    public ArrayList<Tile> findPath(Tile start, Tile end) {
        ArrayList<Tile> path = new ArrayList<>();
        if (end == null) findPath(start);
        else {
            TilePriorityQ queue = new TilePriorityQ(g.verticesList);
    
            start.costEstimate = 0;
            for (var tile : queue.heap) {
                if (tile != start) tile.costEstimate = Integer.MAX_VALUE;
                tile.predecessor = null;
            }
    
            while (queue.size != 0) {
                Tile min = queue.removeMin();
        
                for (var n : g.getNeighbors(min)) {
                    if (queue.heap.contains(n)) {
                        ArrayList<Tile> edgePath = new ArrayList<>();
                        edgePath.add(min);
                        edgePath.add(n);
                        double computePathCost = g.computePathCost(edgePath);
                
                        if (min.costEstimate + computePathCost < n.costEstimate) {
                            n.costEstimate = min.costEstimate + computePathCost;
                            n.predecessor = min;
                        }
                    }
                }
                if (min == end) break;
            }
    
            while (end != null && end != start) {
                path.add(0, end);
                end = end.predecessor;
            }
    
            path.add(0, start);
        }
    
        return path;
    }

    public ArrayList<Tile> findPath(Tile start, LinkedList<Tile> waypoints) {
        ArrayList<Tile> completePath = new ArrayList<>();
    
        if (waypoints.size() == 0) findPath(start);
        
        else {
           
            for (var tile : findPath(start, waypoints.get(0))) {
                completePath.add(tile);
            }
            completePath.remove(completePath.size() - 1);
            
    
            if (waypoints.size() != 1) {
                for (int i = 0; i < waypoints.size() - 1; i++) {
            
                    for (var tile : findPath(waypoints.get(i), waypoints.get(i + 1))) {
                        completePath.add(tile);
                    }
                    completePath.remove(completePath.size() - 1);
                }
            }
    
            for (var tile : findPath(waypoints.get(waypoints.size() - 1))) {
                completePath.add(tile);
            }
    
        }
        return completePath;
    }
        
}

