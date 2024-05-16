package algorithms;

import java.awt.Color;

import views.Visualizer;

public class SelectionSort extends SortAbstraction {

    public void sort(Visualizer visualizer) {
        
        int num = visualizer.getLength();
        
        for (int i = 0; i < num; i++) {
            int minIndex = i;
            for (int j = i + 1; j < num; j++) {
                visualizer.drawBase();
                visualizer.setColor(j, Color.CYAN);
                visualizer.setColor(minIndex, Color.BLUE);
                visualizer.setColor(i, Color.BLUE);
                visualizer.updateAnimation();               
                if (visualizer.getValue(minIndex) > visualizer.getValue(j)) {
                    minIndex = j;
                }
            }
            
            if (i != minIndex) {
                visualizer.swap(i, minIndex);
            } else {
                visualizer.drawBase();
                visualizer.updateAnimation();
            }
        }
        
        
        //visualizer.remove(50, 349);
        //visualizer.updateAnimation();
        //visualizer.setColor(0, Color.BLUE);
        //visualizer.setColor(12, Color.CYAN);
        //System.out.println(visualizer.getValue(1) + " - " + visualizer.getValue(15));
        //visualizer.swap(1, 15);
        //visualizer.drawBase();
        //System.out.println(visualizer.getValue(1) + " - " + visualizer.getValue(15));
        //visualizer.drawBase();
        /*
        for (int i = 0; i < num; i++) {
            visualizer.setColor(i, Color.CYAN);
            visualizer.setColor(i, Color.GRAY);
        }*/
    }
}