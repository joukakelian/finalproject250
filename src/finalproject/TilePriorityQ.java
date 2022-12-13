package finalproject;

import java.util.ArrayList;
import java.util.Arrays;


import finalproject.system.Tile;

public class TilePriorityQ {
	static Tile[] heap = new Tile[50];
	static int size = -1;
	public TilePriorityQ (ArrayList<Tile> vertices) {
		for (var vertex : vertices) {
			size++;
			heap[size] = vertex;
			shiftUp(size);
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
	private void shiftUp(int index) {
		while (index > 0 && heap[parentIndex(index)].costEstimate > heap[index].costEstimate) {
			swap(parentIndex(index),index);
			index = parentIndex(index);
		}
	}
	private void shiftDown(int index) {
		int maxIndex = index;
		
		if (leftIndex(index) <= size && heap[leftIndex(index)].costEstimate < heap[maxIndex].costEstimate) {
			maxIndex = leftIndex(index);
		}
		
		if (rightIndex(index) <= size && heap[rightIndex(index)].costEstimate < heap[maxIndex].costEstimate) {
			maxIndex = rightIndex(index);
		}
		
		if (index != maxIndex) {
			swap(index, maxIndex);
			shiftDown(maxIndex);
		}
	}
	private void swap(int i, int j) {
		Tile temp = heap[i];
		heap[i] = heap[j];
		heap[j] = temp;
	}
	public Tile removeMin() {
		Tile result = heap[0];
		heap[0] = heap[size];
		size--;
		shiftDown(0);
		return result;
	}
	
	public void updateKeys(Tile t, Tile newPred, double newEstimate) {
		
	}
	
}
