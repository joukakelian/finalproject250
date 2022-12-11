package finalproject;

import finalproject.system.Tile;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;

public class GraphTraversal {

	// input Tile start, traverse map find all reachable tiles, return ArrayList w Tiles in order of visitation
	//TODO level 1: implement BFS traversal starting from s
	public static ArrayList<Tile> BFS(Tile s) {
		// use queue version of dfs to traverse
		// whenever visit, add tile to an arraylist
		
		
		return null;
	}


	//TODO level 1: implement DFS traversal starting from s
	public static ArrayList<Tile> DFS(Tile s) {
		HashSet<Tile> visitedMap = new HashSet<>(); // if no entry for a vertex, not visited
		ArrayList<Tile> toVisit = new ArrayList<>(); // like stack but need to manually add and remove
		ArrayList<Tile> visited = new ArrayList<>();
		
		toVisit.add(s);
		
		while (!toVisit.isEmpty()) {
			System.out.println("toVisit: " + toVisit);
			Tile cur = toVisit.remove(toVisit.size()-1); // pop
			System.out.println("cur is " + cur);
			if (!visited.contains(cur)) visited.add(cur);
			
			if (!visitedMap.contains(cur) && !toVisit.contains(cur)) {
				visitedMap.add(cur);
				System.out.println("before for: " + cur);
				for (var tile : cur.neighbors) {
					if (!toVisit.contains(tile) && tile.isWalkable()) {
						toVisit.add(tile); // push
					}
					System.out.println("adding tile: " + tile);
				}
				
			}
		}
		System.out.println(visited);
		
		return visited;
	}

}  