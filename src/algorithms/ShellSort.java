package algorithms;


import views.Visualizer;

public class ShellSort extends SortAbstraction {

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
                    visualizer.swap(j - gap, j);
                    j -= gap;
                }
            }
            gap = gap / 3 + 1;
        }
    }
}
