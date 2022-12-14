package finalproject;

import java.util.ArrayList;
import java.util.Arrays;


import finalproject.system.Tile;

public class TilePriorityQ {
	public static ArrayList<Tile> heap = new ArrayList<>();
	public static int size = -1;
	public TilePriorityQ (ArrayList<Tile> vertices) {
		for (var vertex : vertices) {
			size++;
			heap.add(size, vertex);
			downheap(size);
		}
	}
	private static int parentIndex(int i) {
		return (i-1)/2;
	}
	private static int leftIndex(int i) {
		return ((2*i)+1);
	}
	private static int rightIndex(int i) {
		return ((2*i)+2);
	}
	
	public void downheap(int index) {
		while (index > 0 && heap.get(parentIndex(index)).costEstimate > heap.get(index).costEstimate) {
			swap(parentIndex(index),index);
			index = parentIndex(index);
		}
	}

	private void upheap(int index) {
		int maxIndex = index;
		
		if (leftIndex(index) <= size && heap.get(leftIndex(index)).costEstimate < heap.get(maxIndex).costEstimate) {
			maxIndex = leftIndex(index);
		}
		
		if (rightIndex(index) <= size && heap.get(rightIndex(index)).costEstimate < heap.get(maxIndex).costEstimate) {
			maxIndex = rightIndex(index);
		}
		
		if (index != maxIndex) {
			swap(index, maxIndex);
			upheap(maxIndex);
		}
	}
	private void swap(int i, int j) {
		Tile temp = heap.get(i);
		heap.set(i, heap.get(j));
		heap.set(j, temp);
	}
	public Tile removeMin() {
		Tile result = heap.get(0);
		heap.set(0, heap.get(size));
		size--;
		upheap(0);
		return result;
	}
	
	public void updateKeys(Tile t, Tile newPred, double newEstimate) {
		if (heap.contains(t)) {
			int index = heap.indexOf(t);
			t.predecessor = newPred;
			
			if (t.costEstimate > newEstimate) {
				t.costEstimate = newEstimate;
				downheap(index);
			}
			else if (t.costEstimate < newEstimate) {
				t.costEstimate = newEstimate;
				upheap(index);
			}
		}
	}
}
