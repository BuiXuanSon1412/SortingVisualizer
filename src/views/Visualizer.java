package views;

import javax.swing.JPanel;

import java.awt.Graphics;
import java.awt.Color;
import java.awt.Dimension;

import utils.ArrayGenerator;
import algorithms.SortAbstraction;

public class Visualizer extends JPanel {
    private final int VISUALIZER_WIDTH = 750;
    private final int VISUALIZER_HEIGHT = 500;
    private final Color VISUALIZER_BACKGROUND_COLOR = Color.BLACK;
    
    private final long DELAY = 1000 / 60;
    private int[] array;
    private Color[] colors;

    private Thread thread;

    // Constructor
    public Visualizer() {
        // VisualizerPanel settings
        this.setPreferredSize(new Dimension(VISUALIZER_WIDTH, VISUALIZER_HEIGHT));
        this.setBackground(VISUALIZER_BACKGROUND_COLOR);
        this.setDoubleBuffered(true);

        // Auto-generate array
        ArrayGenerator arrayGenerator = new ArrayGenerator();
        array = arrayGenerator.randomGenerate();
        resetColor();
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (array != null) {
            int space = 4;
            int tile = 3;
            int baseY = this.getHeight() - (this.getHeight() - 100*tile)/2;
            int paddingX = (this.getWidth() - array.length * space)/2;
            
            for (int i = 0; i < array.length; i++) {
                int iX = space * i + paddingX;
                g.setColor(colors[i]);
                g.fillRect(iX, baseY - tile * array[i], tile, tile * array[i]);
            }
        }
    }

    public void animateSorting(SortAbstraction sortAbstraction) {
        thread = new Thread(new Runnable() {
            public void run() {
                sortAbstraction.sort(Visualizer.this);
            }
        });
        thread.start();
    }

    public void generateRandomArray() {
        ArrayGenerator arrayGenerator = new ArrayGenerator();
        array = arrayGenerator.randomGenerate();
        resetColor();
        repaint();
        validate();
    }

    public void pauseSorting() {

    }

    public void resumeSorting() {

    }

    public void updateAnimation() {
        repaint();
        validate();
        try {
            /*
             * long delay = (long)(1000 / fps) - (System.currentTimeMillis() -
             * startingTime);
             * System.out.println(delay);
             * if (delay < 0) delay = 0;
             */
            Thread.sleep(DELAY);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void swap(int i, int j) {
        // long startingTime = System.currentTimeMillis();
        int tmp = array[i];
        array[i] = array[j];
        array[j] = tmp;
        updateAnimation();
        // startingTime = System.currentTimeMillis();
        resetColor(i);
        resetColor(j);
        updateAnimation();
    }

    public void compare(int i, int j) {
        // long startingTime = System.currentTimeMillis();
        colors[i] = Color.RED;
        colors[j] = Color.RED;
        updateAnimation();
    }

    public int getValue(int i) {
        return array[i];
    }

    public void setSortedColor(int i) {
        // long startingTime = System.currentTimeMillis();
        colors[i] = Color.CYAN;
        updateAnimation();
    }

    public void setMarkedColor(int i) {
        // long startingTime = System.currentTimeMillis();
        colors[i] = Color.BLUE;
        updateAnimation();
    }

    public void setIteratingColor(int i) {
        // long startingTime = System.currentTimeMillis();
        colors[i] = Color.DARK_GRAY;
        updateAnimation();
    }

    public void resetColor() {
        colors = new Color[array.length];
        for (int i = 0; i < array.length; i++) {
            colors[i] = Color.WHITE;
        }
    }

    public void resetColor(int i) {
        // long startingTime = System.currentTimeMillis();
        colors[i] = Color.WHITE;
        updateAnimation();
    }

    public int getNumberOfBars() {
        return array.length;
    }
}