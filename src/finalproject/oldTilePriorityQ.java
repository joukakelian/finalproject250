package finalproject;

import java.util.ArrayList;

import finalproject.system.Tile;

public class oldTilePriorityQ {
	Tile root;
	public static ArrayList<Tile> heap = new ArrayList<>();
	public static int size = -1;
	
	public oldTilePriorityQ(ArrayList<Tile> vertices) {
		for (var vertex : vertices) {
			size++;
			heap.add(vertex);
			upheap(size);
		}
		downheap(0);
		root = null;
	}
	
	public boolean isEmpty() {
		return (size <= 0);
	}
	
	private static int parentIndex(int i) {
		return (i - 1) / 2;
	}
	
	private static int leftIndex(int i) {
		return ((2 * i) + 1);
	}
	
	private static int rightIndex(int i) {
		return ((2 * i) + 2);
	}
	
	public void downheap(int index) {
		while (leftIndex(index) < size) { // if there is left
			if (rightIndex(index) < size) { // if there is right
				if (heap.get(rightIndex(index)).costEstimate < heap.get(leftIndex(index)).costEstimate) {
					swap(rightIndex(index), leftIndex(index));
				}
			}
			
			if (heap.get(index).costEstimate < heap.get(parentIndex(index)).costEstimate) {
				upheap(index);
			}
			else break;
		}
	}
	
	private void upheap(int index) {
		int parentIndex = parentIndex(index);
		if (parentIndex >= 0 && heap.get(parentIndex).costEstimate > heap.get(index).costEstimate) {
			swap(parentIndex, index);
			upheap(parentIndex);
		}
		
	}
	
	private void swap(int i, int j) {
		Tile temp = heap.get(i);
		heap.set(i, heap.get(j));
		heap.set(j, temp);
	}
	
	public Tile removeMin() {
		Tile min = heap.get(0);
		heap.remove(min);
		
		size--;
		downheap(0);
		
		return min;
	}
	
	public void updateKeys(Tile t, Tile newPred, double newEstimate) {
		int index = heap.indexOf(t);
		
		t.predecessor = newPred;
		t.costEstimate = newEstimate;
		
		upheap(index);
	}
	
	private static void printTree(Tile root, int spaceCount)
	{
		if(root==null)
			return;
		
		int spacing = spaceCount+24;
		
		printTree(root.right, spacing);
		
		System.out.println();
		for(int index=0; index < spacing+root.toString().length()+1; index++) {
			System.out.print(" ");
		}
		if (root.right != null) {
			System.out.print("/");
			System.out.println();
			for(int index=0; index < spacing+root.toString().length(); index++) {
				System.out.print(" ");
			}
			if (root.right != null) {
				System.out.print("/");
			}
		}
		
		System.out.println();
		for(int index=0; index < spacing; index++) {
			System.out.print(" ");
		}
		System.out.println(root.costEstimate);
		
		for(int index=0; index < spacing+root.toString().length(); index++) {
			System.out.print(" ");
		}
		if (root.left != null) {
			System.out.print("\\");
			System.out.println();
			for(int index=0; index < spacing+root.toString().length()+1; index++) {
				System.out.print(" ");
			}
			if (root.left != null) {
				System.out.print("\\");
			}
		}
		printTree(root.left, spacing);
	}
	
	private void swaps(Tile t) {
		System.out.println("\nswapping...");
		System.out.println("t is: " + t.costEstimate);
		if (t.parent != null) System.out.println("p is: " + t.parent.costEstimate);
		printTree(root, 2);
		if (t.parent != null && t.costEstimate < t.parent.costEstimate) {
			Tile p = t.parent;
			Tile pp = t.parent.parent;
			t.parent = pp;
			p.parent = t;
			
			if (p == root) {
				root = t;
			}
			if (pp != null) {
				if (pp.left == p) {
					pp.left = t;
				}
				else {
					pp.right = t;
				}
			}
			
			if (p.left == t) {
				p.left = t.left;
				t.left = p;
				t.right = p.right;
				p.right = null;
			}
			else {
				p.right = t.right;
				t.right = p;
				t.left = p.left;
				p.left = null;
				printTree(root,2);
			}
		}
		
		if (t.left == null && t.right != null) {
			Tile temp = t.right;
			t.right = t.left;
			t.left = temp;
		}
		
		if (t.left != null && t.right != null && t.right.costEstimate < t.left.costEstimate) {
			Tile temp = t.right;
			t.right = t.left;
			t.left = temp;
		}
		if (t.parent != null && t.costEstimate < t.parent.costEstimate) swaps(t);
		printTree(root,2);
		
	}
	public void addToTree(Tile t) {
		printTree(root, 2);
		if (root == null) {
			root = t;
		}
		else {
			Tile prev;
			Tile cur = root;
			
			while (cur != null) {
				System.out.println(cur);
				prev = cur;
				cur = cur.left;
				if (prev.right != null && prev.left != null && prev.parent != null && prev.parent.right != null) {
					swaps(t);
					if (prev.parent.right.left == null) {
						prev.parent.right.left = t;
						t.parent = prev.parent.right;
					}
					else if (prev.parent.right.right == null) {
						prev.parent.right.right = t;
						t.parent = prev.parent.right;
					}
					swaps(t);
					return;
				}
				if (prev.right == null && prev.left != null) {
					swaps(t);
					prev.right = t;
					t.parent = prev;
					swaps(t);
					return;
				}
				else if (cur == null) {
					prev.left = t;
					t.parent = prev;
				}
			}
			swaps(t);
		}
	}
	
	public static void main(String[] args) {
		Tile vertex1 = new finalproject.tiles.DesertTile();
		Tile vertex2 = new finalproject.tiles.DesertTile();
		Tile vertex3 = new finalproject.tiles.PlainTile();
		Tile vertex4 = new finalproject.tiles.PlainTile();
		Tile vertex5 = new finalproject.tiles.PlainTile();
		Tile vertex6 = new finalproject.tiles.PlainTile();
		Tile vertex7 = new finalproject.tiles.PlainTile();
		
		vertex1.costEstimate = 40;
		vertex2.costEstimate = 30;
		vertex3.costEstimate = 20;
		vertex4.costEstimate = 10;
		vertex5.costEstimate = 50;
		vertex6.costEstimate = 60;
		
		ArrayList<Tile> vertices = new ArrayList<>();
		vertices.add(vertex1);
		vertices.add(vertex2);
		vertices.add(vertex3);
		vertices.add(vertex4);
		vertices.add(vertex5);
		vertices.add(vertex6);
		vertices.add(vertex7);
		
		
	}
}
