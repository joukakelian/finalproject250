package finalproject;

import java.util.ArrayList;
import java.util.Arrays;


import finalproject.system.Tile;

public class TilePriorityQ {
    public ArrayList<Tile> heap = new ArrayList<>();
    public int size = -1;
    
    public TilePriorityQ(ArrayList<Tile> vertices) {
        for (var vertex : vertices) {
            size++;
            heap.add(vertex);
            downheap(size);
        }
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
    
    private boolean isLeaf(int i) {
        if (rightIndex(i) >= size || leftIndex(i) >= size) {
            return true;
        }
        return false;
    }
    public void downheap(int index) {
        while (index > 0 && heap.get(parentIndex(index)).costEstimate > heap.get(index).costEstimate) {
            swap(parentIndex(index), index);
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
    private void minHeapify(int i) {
        // If the node is a non-leaf node and any of its child is smaller
        if (!isLeaf(i)) {
            if (heap.get(i).costEstimate > heap.get(leftIndex(i)).costEstimate ||
                    heap.get(i).costEstimate > heap.get(rightIndex(i)).costEstimate) {
                if (heap.get(leftIndex(i)).costEstimate < heap.get(rightIndex(i)).costEstimate) {
                    swap(i, leftIndex(i));
                    minHeapify(leftIndex(i));
                } else {
                    swap(i, rightIndex(i));
                    minHeapify(rightIndex(i));
                }
            }
        }
    }
    public Tile removeMin() {
        Tile popped = heap.get(0);
        
        
        heap.remove(popped);
        size--;
        if (size == 1 && heap.get(0).costEstimate > heap.get(1).costEstimate) swap(0,1);
        else minHeapify(0);
        return popped;
    }
    
    public void updateKeys(Tile t, Tile newPred, double newEstimate) {
        int index = heap.indexOf(t);
    
        t.predecessor = newPred;
        t.costEstimate = newEstimate;
    
        upheap(index);
    }
    
}