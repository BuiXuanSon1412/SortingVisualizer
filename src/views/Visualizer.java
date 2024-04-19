package views;

import javax.swing.JPanel;

import java.awt.Graphics;
import java.awt.Color;
import java.awt.Dimension;

import utils.ArrayGenerator;
import algorithms.SortAbstraction;

public class Visualizer extends JPanel {
    private final long delay = 5;
    private int[] array;
    private Color[] colors;
    
    private Thread thread;
    // Constructor
    public Visualizer(int iWidth, int iHeight) {
        // VisualizerPanel settings
        this.setPreferredSize(new Dimension(iWidth, iHeight));
        this.setBackground(Color.white);
        this.setDoubleBuffered(true);

        // Auto-generate array
        ArrayGenerator arrayGenerator = new ArrayGenerator();
        array = arrayGenerator.randomGenerate();
        resetColor();
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        int space = 5;
        int iY = 475;
        int tile = 4;
        int padding = 50;
        if (array != null) {
            for (int i = 0; i < array.length; i++) {
                int iX = space * i + padding;
                g.setColor(colors[i]);
                g.fillRect(iX, iY - tile * array[i], tile, tile * array[i]);
            }
        }
    }

    public void animateSorting(SortAbstraction sortAbstraction) {
        thread = new Thread(new Runnable() {
            public void run() {
                    sortAbstraction.sort(Visualizer.this);
                }
            }
        );
        thread.start();
    }

    public void generateRandomArray() {
        ArrayGenerator arrayGenerator = new ArrayGenerator();
        array = arrayGenerator.randomGenerate();
        resetColor();
        repaint();
    }

    public void pauseSorting() {
        
    }
    public void updateAnimation() {
        repaint();
        validate();
        try {
            Thread.sleep(delay);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    public void swap(int i, int j) {
        int tmp = array[i];
        array[i] = array[j];
        array[j] = tmp;
        updateAnimation();
        resetColor(i);
        resetColor(j);
        updateAnimation();
    }
    public void compare(int i, int j) {
        colors[i] = Color.RED;
        colors[j] = Color.RED;
        updateAnimation();
    }
    public int getValue(int i) {
        return array[i];
    }
    public void setSortedColor(int i) {
        colors[i] = Color.BLUE;
        updateAnimation();  
    }
    public void setMarkedColor(int i) {
        colors[i] = Color.YELLOW;
        updateAnimation();
    }
    public void setIteratingColor(int i) {
        colors[i] = Color.RED;
        updateAnimation();
    }
    public void resetColor() {
        colors = new Color[array.length];
        for (int i = 0; i < array.length; i++) {
            colors[i] = Color.GRAY;
        }
    }
    public void resetColor(int i) {
        colors[i] = Color.GRAY;
        repaint();
        validate();
    }
    public int getNumberOfBars() {
        return array.length;
    }
}
