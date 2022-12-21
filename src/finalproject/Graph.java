package finalproject;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.HashMap; // CUSTOM IMPORTED

import finalproject.system.Tile;
import finalproject.system.TileType;
import finalproject.tiles.*;

public class Graph {
    private ArrayList<Edge> edgeList = new ArrayList<>();
    public ArrayList<Tile> verticesList;
    
    public Graph(ArrayList<Tile> vertices) {
        this.verticesList = vertices;
    }
    
    public void addEdge(Tile origin, Tile destination, double weight) {
        Edge e = new Edge(origin, destination, weight);
        edgeList.add(e);
        destination.edgeWeight = weight;
    }
    
    public ArrayList<Edge> getAllEdges() {
        return edgeList;
    }
    
    public ArrayList<Tile> getNeighbors(Tile t) {
        ArrayList<Tile> tNeighbors = new ArrayList<>();
        for (var vertex : verticesList) {
            if (vertex == t) {
                for (var edge : getAllEdges()) {
                    if (edge.getStart() == t) {
                        tNeighbors.add(edge.getEnd());
                    }
                }
            }
        }
        return tNeighbors;
    }
    
    public double computePathCost(ArrayList<Tile> path) {
        double pathCost = 0.0;
        for (int i = 1; i < path.size(); i++) {
            for (int j = 0; j < edgeList.size(); j++) {
                Tile src = path.get(i - 1);
                Tile dest = path.get(i);
                if (edgeList.get(j).getStart() == src && edgeList.get(j).getEnd() == dest) {
                    pathCost += edgeList.get(j).weight;
                }
            }
        }
        return pathCost;
    }
    
    public static class Edge {
        Tile origin;
        Tile destination;
        double weight;
        
        public Edge(Tile s, Tile d, double cost) {
            this.origin = s;
            this.destination = d;
            this.weight = cost;
        }
        
        public Tile getStart() {
            return this.origin;
        }
        
        public Tile getEnd() {
            return this.destination;
        }
        
    }
    
}