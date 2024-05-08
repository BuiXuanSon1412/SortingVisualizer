package algorithms;

import views.Visualizer;

public class MergeSort extends SortAbstraction {

    public void sort(Visualizer visualizer) {
        int[] array = visualizer.getArray();
        mergeSort(array, 0, array.length - 1, visualizer);
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
        int[] temp = new int[array.length];
        for (int i = left; i <= right; i++) {
            temp[i] = array[i];
        }

        int i = left;
        int j = mid + 1;
        int k = left;

        while (i <= mid && j <= right) {
            if (temp[i] <= temp[j]) {
                visualizer.setIteratingColor(i);
                visualizer.setMarkedColor(j);
                array[k++] = temp[i++];
            } else {
                visualizer.setIteratingColor(j);
                visualizer.setMarkedColor(i);
                array[k++] = temp[j++];
            }
            visualizer.updateAnimation();
        }

        while (i <= mid) {
            visualizer.setIteratingColor(i);
            array[k++] = temp[i++];
            visualizer.updateAnimation();
        }
        // Set sorted color for elements from left to right
        for (int idx = left; idx <= right; idx++) {
            visualizer.setSortedColor(idx);
        }
    }
}
