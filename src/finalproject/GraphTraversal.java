package finalproject;

import finalproject.system.Tile;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;

public class GraphTraversal {

	// input Tile start, traverse map find all reachable tiles, return ArrayList w Tiles in order of visitation
	//TODO level 1: implement BFS traversal starting from s
	public static ArrayList<Tile> BFS(Tile s) {
		HashSet<Tile> visitedMap = new HashSet<>();
		ArrayList<Tile> toVisit = new ArrayList<>();
		ArrayList<Tile> visited = new ArrayList<>();
		
		toVisit.add(s);
		visited.add(s);
		
		while (!toVisit.isEmpty()) {
			Tile cur;
			
			if (toVisit.size() != 1)
				cur = toVisit.remove(toVisit.size()-2); // cur is previous tile
			else cur = toVisit.remove(0);
			
			if (!visitedMap.contains(cur) && !toVisit.contains(cur)) {
				visitedMap.add(cur);
				
				for (var tile : cur.neighbors) {
					if (!visited.contains(tile) && tile.isWalkable()) {
						visited.add(tile);
						toVisit.add(tile);
					}
				}
			}
		}
		return visited;
	}

	// ############################################ DFS ################################################################

	public static ArrayList<Tile> DFS(Tile s) {
		HashSet<Tile> visitedMap = new HashSet<>(); // if no entry for a vertex, not visited
		ArrayList<Tile> toVisit = new ArrayList<>(); // like stack but need to manually add and remove
		ArrayList<Tile> visited = new ArrayList<>();
		
		toVisit.add(s);
		
		while (!toVisit.isEmpty()) {
			Tile cur = toVisit.remove(toVisit.size()-1); // pop
			
			if (!visited.contains(cur)) visited.add(cur);
			
			if (!visitedMap.contains(cur) && !toVisit.contains(cur)) {
				visitedMap.add(cur);
				for (var tile : cur.neighbors) {
					if (!toVisit.contains(tile) && tile.isWalkable()) {
						toVisit.add(tile); // push
					}
				}
			}
		}
		return visited;
	}

}  