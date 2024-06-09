package algorithms;

import views.Visualizer;

public class QuickSort extends SortAbstraction {
    private boolean[] sorted;
    public void sort(Visualizer visualizer) {
        int[] array = visualizer.getArray();
        sorted = new boolean[array.length];
        for (int i = 0; i < array.length; i++) {
            sorted[i] = false;
        }
        quickSort(array, 0, array.length - 1, visualizer);
    }

    private int partition(int[] arr, int low, int high, Visualizer visualizer) {
        int pivot = arr[high];
        int i = (low - 1);

        for (int j = low; j <= high - 1; j++) {
            if (arr[j] < pivot) {
                i++;
                visualizer.swap(i, j, false, sorted, high);
            }
        }
        visualizer.swap(i + 1, high, false, sorted, high);
        sorted[i+1] = true;
        return (i + 1);
    }

    private void quickSort(int[] arr, int low, int high, Visualizer visualizer) {
        if (low < high) {
            int pi = partition(arr, low, high, visualizer);
            quickSort(arr, low, pi - 1, visualizer);
            for (int i = low; i <= pi - 1; i++) {
                sorted[i] = true;
            }
            quickSort(arr, pi + 1, high, visualizer);
            for (int i = pi + 1; i <= high; i++) {
                sorted[i] = true;
            }
        }
    }
}