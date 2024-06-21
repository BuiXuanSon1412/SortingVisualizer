package algorithms;

import java.awt.Color;

import views.Visualizer;

public class ShellSort extends SortAbstraction {

    private final Color ACTIVE_COLOR = new Color(0x074173); // Color for active elements being compared
    private final Color SORTED_COLOR = new Color(0xDAD3BE); // Color for sorted elements
    private final Color GAP_COLOR = new Color(0x6B8A7A); // Color for elements in the current gap

    public void sort(Visualizer visualizer) {
        int num = visualizer.getLength();
        int gap = 1;
        while (gap < num / 3) {
            gap = gap * 3 + 1; // Use Knuth formula: 3x + 1
        }

        while (gap > 0) {
            for (int i = gap; i < num; i++) {
                int temp = visualizer.getValue(i);
                int j = i;
                while (j >= gap && visualizer.getValue(j - gap) > temp) {
                    visualizer.swap(j - gap, j, true, null, -1, ACTIVE_COLOR, GAP_COLOR);
                    j -= gap;
                }
                visualizer.setColor(j, GAP_COLOR);
                visualizer.updateAnimation();
            }
            gap = (gap - 1) / 3;
        }

        // Mark all elements as sorted at the end
        visualizer.drawAll(visualizer.getArray(), SORTED_COLOR);
        visualizer.updateAnimation();
    }
}
