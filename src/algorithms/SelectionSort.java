package algorithms;

import java.awt.Color;

import views.Visualizer;

public class SelectionSort extends SortAbstraction {

    private final Color ACTIVE_COLOR = new Color(0xB7B597); // Color for active elements being compared
    private final Color MIN_INDEX_COLOR = new Color(0x6B8A7A); // Color for current minimum index
    private final Color SORTED_COLOR = new Color(0xDAD3BE); // Color for sorted elements

    public void sort(Visualizer visualizer) {

        int num = visualizer.getLength();

        for (int i = 0; i < num; i++) {
            int minIndex = i;
            for (int j = i + 1; j < num; j++) {
                visualizer.drawAll(visualizer.getArray(), SORTED_COLOR);
                visualizer.setColor(j, ACTIVE_COLOR);
                visualizer.setColor(minIndex, MIN_INDEX_COLOR);
                visualizer.setColor(i, MIN_INDEX_COLOR);
                visualizer.updateAnimation();
                if (visualizer.getValue(minIndex) > visualizer.getValue(j)) {
                    minIndex = j;
                }
            }

            if (i != minIndex) {
                visualizer.swap(i, minIndex, false, null, -1, ACTIVE_COLOR, MIN_INDEX_COLOR);
            } else {
                visualizer.drawAll(visualizer.getArray(), SORTED_COLOR);
                visualizer.updateAnimation();
            }
        }
    }
}
