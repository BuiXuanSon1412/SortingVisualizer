package algorithms;

import java.awt.Color;

import views.Visualizer;

public class SelectionSort extends SortAbstraction {

    public void sort(Visualizer visualizer) {
        
        int num = visualizer.getLength();
        
        for (int i = 0; i < num; i++) {
            int minIndex = i;
            for (int j = i + 1; j < num; j++) {
                visualizer.drawAll(visualizer.getArray(), Color.WHITE);
                visualizer.setColor(j, Color.CYAN);
                visualizer.setColor(minIndex, Color.BLUE);
                visualizer.setColor(i, Color.BLUE);
                visualizer.updateAnimation();               
                if (visualizer.getValue(minIndex) > visualizer.getValue(j)) {
                    minIndex = j;
                }
            }
            
            if (i != minIndex) {
                visualizer.swap(i, minIndex, false, null, -1);
            } else {
                visualizer.drawAll(visualizer.getArray(), Color.WHITE);
                visualizer.updateAnimation();
            }
        }
    }
}