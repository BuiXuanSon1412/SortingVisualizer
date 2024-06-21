package algorithms;

import java.awt.Color;

import views.Visualizer;

public class MergeSort extends SortAbstraction {

    private final Color MERGE_COLOR = new Color(0x6B8A7A); // Color for merging segments
    private final Color ACTIVE_COLOR = new Color(0x074173); // Color for active elements
    private final Color FINAL_COLOR = new Color(0xDAD3BE); // Color for sorted elements

    public void sort(Visualizer visualizer) {
        int[] array = visualizer.getArray();
        mergeSort(array, 0, array.length - 1, visualizer);
        visualizer.drawAll(array, FINAL_COLOR);
        visualizer.updateAnimation();
    }

    private void mergeSort(int[] array, int left, int right, Visualizer visualizer) {
        if (left < right) {
            int mid = (left + right) / 2;
            mergeSort(array, left, mid, visualizer);
            mergeSort(array, mid + 1, right, visualizer);
            merge(array, left, mid, right, visualizer);
        }
    }

    private void merge(int[] array, int left, int mid, int right, Visualizer visualizer) {
        int[] temp = new int[visualizer.getLength()];
        for (int i = 0; i < temp.length; i++) {
            temp[i] = visualizer.getValue(i);
        }
        int i = left;
        int j = mid + 1;
        int k = left;

        while (i <= mid && j <= right) {
            if (temp[i] <= temp[j]) {
                visualizer.moveFrom(i, j, k, left, right, temp, ACTIVE_COLOR, MERGE_COLOR);
                i++;
            } else {
                visualizer.moveFrom(j, i, k, left, right, temp, ACTIVE_COLOR, MERGE_COLOR);
                j++;
            }
            k++;
        }
        while (i <= mid) {
            visualizer.moveFrom(i, j, k, left, right, temp, ACTIVE_COLOR, MERGE_COLOR);
            k++;
            i++;
        }
        while (j <= right) {
            visualizer.moveFrom(j, i, k, left, right, temp, ACTIVE_COLOR, MERGE_COLOR);
            k++;
            j++;
        }
        visualizer.drawAll(array, FINAL_COLOR); // Mark the merged segment as sorted
    }
}
