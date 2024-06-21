package algorithms;

import java.awt.Color;

import views.Visualizer;

public class SelectionSort extends SortAbstraction {

    private final Color DEFAULT_COLOR = new Color(0xDAD3BE); // Default color for unsorted elements
    private final Color ACTIVE_COLOR = new Color(0x074173); // Color for active elements being compared
    private final Color MIN_INDEX_COLOR = new Color(0x074173); // Color for current minimum index
    private final Color SORTED_COLOR = new Color(0xDAD3BE); // Color for sorted elements

    public void sort(Visualizer visualizer) {
        int num = visualizer.getLength();

        // Set all bars to default color before sorting
        visualizer.drawAll(visualizer.getArray(), DEFAULT_COLOR);
        visualizer.updateAnimation();

        for (int i = 0; i < num; i++) {
            int minIndex = i;
            for (int j = i + 1; j < num; j++) {
                visualizer.drawAll(visualizer.getArray(), DEFAULT_COLOR); // Reset all bars to default color
                visualizer.setColor(j, ACTIVE_COLOR); // Set current comparing bar to ACTIVE_COLOR
                visualizer.setColor(minIndex, MIN_INDEX_COLOR); // Set current minimum index bar to MIN_INDEX_COLOR
                visualizer.setColor(i, MIN_INDEX_COLOR); // Set the first element in the unsorted segment to
                                                         // MIN_INDEX_COLOR
                visualizer.updateAnimation();
                if (visualizer.getValue(minIndex) > visualizer.getValue(j)) {
                    minIndex = j;
                }
            }

            if (i != minIndex) {
                visualizer.swap(i, minIndex, false, null, -1, ACTIVE_COLOR, MIN_INDEX_COLOR);
            } else {
                visualizer.drawAll(visualizer.getArray(), DEFAULT_COLOR);
                visualizer.setColor(i, MIN_INDEX_COLOR); // Set sorted element to MIN_INDEX_COLOR
                visualizer.updateAnimation();
            }
        }

        // Set all elements to sorted color after sorting
        visualizer.drawAll(visualizer.getArray(), SORTED_COLOR);
        visualizer.updateAnimation();
    }
}
