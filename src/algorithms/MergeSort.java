package algorithms;

import java.awt.Color;

import views.Visualizer;

public class MergeSort extends SortAbstraction {

    public void sort(Visualizer visualizer) {
        int[] array = visualizer.getArray();
        mergeSort(array, 0, array.length - 1, visualizer);
        visualizer.drawAll(visualizer.getArray(), Color.WHITE);
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
                visualizer.moveFrom(i, j, k, left, right, temp);
                i++;

            } else {
                visualizer.moveFrom(j, i, k, left, right, temp);
                j++;
            }
            k++;
        }
        while (i <= mid) {
            visualizer.moveFrom(i, j, k, left, right, temp);
            k++;
            i++;
        }
        while (j <= right) {
            visualizer.moveFrom(j, i, k, left, right, temp);
            k++;
            j++;
        }
    }
}
