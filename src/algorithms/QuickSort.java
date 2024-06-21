package algorithms;

import java.awt.Color;

import views.Visualizer;

public class QuickSort extends SortAbstraction {

    private boolean[] sorted;

    // Define colors for QuickSort visualization
    private final Color ACTIVE_COLOR = new Color(0x074173); // Color for active elements
    private final Color PIVOT_COLOR = new Color(0x074173); // Color for pivot elements
    private final Color FINAL_COLOR = new Color(0xDAD3BE); // Color for sorted elements

    public void sort(Visualizer visualizer) {
        int[] array = visualizer.getArray();
        sorted = new boolean[array.length];
        for (int i = 0; i < array.length; i++) {
            sorted[i] = false;
        }
        quickSort(array, 0, array.length - 1, visualizer);
        visualizer.drawAll(array, FINAL_COLOR); // Mark the entire array as sorted
        visualizer.updateAnimation();
    }

    private int partition(int[] arr, int low, int high, Visualizer visualizer) {
        int pivot = arr[high];
        int i = (low - 1);

        for (int j = low; j <= high - 1; j++) {
            if (arr[j] < pivot) {
                i++;
                visualizer.swap(i, j, false, sorted, high, ACTIVE_COLOR, PIVOT_COLOR);
            }
        }
        visualizer.swap(i + 1, high, false, sorted, high, ACTIVE_COLOR, PIVOT_COLOR);
        sorted[i + 1] = true;
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
