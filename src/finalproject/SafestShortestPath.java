package finalproject;


import java.util.ArrayList;
import java.util.LinkedList;

import finalproject.system.Tile;
import finalproject.tiles.MetroTile;

public class SafestShortestPath extends ShortestPath {
	public int health;
	public Graph costGraph;
	public Graph damageGraph;
	public Graph aggregatedGraph;

	//TODO level 8: finish class for finding the safest shortest path with given health constraint
	public SafestShortestPath(Tile start, int health) {
		super(start);
		this.source = start;
		this.health = health;
		
		generateGraph();
	}
	
	public void generateGraph() {
		super.generateGraph();
		GraphTraversal traverse = new GraphTraversal();
		
		this.costGraph = new Graph(traverse.DFS(this.source));
		
		for (var vertex : g.verticesList) {
			for (var neigh : vertex.neighbors) {
				if (neigh instanceof MetroTile && vertex instanceof MetroTile) {
					((MetroTile) neigh).fixMetro(vertex);
					costGraph.addEdge(vertex, neigh, ((MetroTile) neigh).metroDistanceCost);
				}
				else if (neigh.isWalkable()) costGraph.addEdge(vertex, neigh, neigh.distanceCost);
			}
		}
		
		
		this.damageGraph = new Graph(traverse.DFS(this.source));
		this.aggregatedGraph = new Graph(traverse.DFS(this.source));
		
		for (var vertex : g.verticesList) {
			for (var neigh : vertex.neighbors) {
				if (neigh.isWalkable()) {
					damageGraph.addEdge(vertex, neigh, neigh.damageCost);
					aggregatedGraph.addEdge(vertex, neigh, neigh.damageCost);
				}
			}
		}
		
	}
	
	public ArrayList<Tile> findPath(Tile start, LinkedList<Tile> waypoints) {
		super.g = costGraph;
		
		ArrayList<Tile> pc = super.findPath(start, waypoints);
		double pcDmgCost = 0.0;
		for (var tile : pc) {
			pcDmgCost += tile.damageCost;
		}
		if (pcDmgCost < health) return pc;
		double pcDistCost = costGraph.computePathCost(pc);
		
		super.g = damageGraph;
		ArrayList<Tile> pd = super.findPath(start, waypoints);
		double pdDmgCost = 0.0;
		for (var tile : pd) {
			pdDmgCost += tile.damageCost;
		}
		if (pdDmgCost > health) return null;
		double pdDistCost = damageGraph.computePathCost(pd);
		
		while (true) {
			double lambda = (pcDistCost - pdDistCost) / (pdDmgCost - pcDmgCost);
			for (var edge : aggregatedGraph.getAllEdges()) {
				edge.weight = edge.destination.distanceCost + (lambda * edge.destination.damageCost);
			}
			
			super.g = aggregatedGraph;
			ArrayList<Tile> pr = super.findPath(start, waypoints);
			double agCost = aggregatedGraph.computePathCost(pr);
			if (agCost == pcDistCost) {
				return pd;
			}
			else if (agCost <= health) pd = pr;
			else pc = pr;
		}
	
	}
	
}
