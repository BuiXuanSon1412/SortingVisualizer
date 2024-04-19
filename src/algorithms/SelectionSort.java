package algorithms;

import views.Visualizer;

public class SelectionSort extends SortAbstraction {

    public void sort(Visualizer visualizer) {
        int num = visualizer.getNumberOfBars();
        for (int i = 0; i < num; i++) {
            int minIndex = i;
            visualizer.setMarkedColor(i);
            for (int j = i + 1; j < num; j++) {
                visualizer.setIteratingColor(j);
                
                if (visualizer.getValue(minIndex) > visualizer.getValue(j)) {
                    if(minIndex != i) visualizer.resetColor(minIndex);
                    minIndex = j;
                    visualizer.setMarkedColor(minIndex);
                } else {
                    visualizer.resetColor(j);
                }
            }
            
            if (i != minIndex) {
                visualizer.swap(i, minIndex);
            }
            visualizer.setSortedColor(i);
        }
    }
}