package algorithms;

import java.awt.Color;

import views.Visualizer;

public class ShellSort extends SortAbstraction {

    public void sort(Visualizer visualizer) {
        int num = visualizer.getLength();
        // define the initial gap between elements
        int gap = 1;
        while (gap < num / 3) {
            gap = gap * 3 + 1; // Use Knuth formula: 3x + 1
        }

        // Start sort algorithm
        while (gap > 0) {
            for (int i = gap; i < num; i++) {
                int temp = visualizer.getValue(i);
                int j = i;

                // move elements whose distance between them = gap
                // and if current element > (j-gap)-th element
                while (j >= gap && visualizer.getValue(j - gap) > temp) {
                    visualizer.setColor(j, Color.BLUE);
                    visualizer.setColor(j - gap, Color.CYAN);
                    visualizer.swap(j, j - gap);
                    j -= gap;
                }
                visualizer.setColor(j, Color.WHITE);
            }

            // decrease the gap and start new iteration
            gap = gap / 3 + 1;
        }
    }
}
