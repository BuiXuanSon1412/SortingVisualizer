package views;

import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;

import utils.ArrayGenerator;
import algorithms.SortAbstraction;

public class Visualizer extends Canvas {
    private final int VISUALIZER_WIDTH = 750;
    private final int VISUALIZER_HEIGHT = 500;
    private final int TILE = 3;
    private final int SPACE = 4;
    private final Color VISUALIZER_BACKGROUND_COLOR = Color.BLACK;

    private final long DELAY = 1000 / 1000;
    private int[] array;

    private BufferStrategy bs;
    private Graphics g;

    private Thread thread;

    // Constructor
    public Visualizer() {
        // VisualizerPanel settings
        this.setPreferredSize(new Dimension(VISUALIZER_WIDTH, VISUALIZER_HEIGHT));
        this.setBackground(VISUALIZER_BACKGROUND_COLOR);
    }

    public void addNotify() {
        super.addNotify();
        createBufferStrategy(2); // Specify the number of buffers
        bs = getBufferStrategy();
    }

    public void paint(Graphics g) {
        super.paint(g);

        ArrayGenerator arrayGenerator = new ArrayGenerator();
        array = arrayGenerator.randomGenerate();

        g.setColor(Color.WHITE);

        int baseY = this.getHeight() - (this.getHeight() - array.length * TILE) / 2;
        int paddingX = (this.getWidth() - array.length * SPACE) / 2;

        for (int i = 0; i < array.length; i++) {
            int iX = SPACE * i + paddingX;
            g.fillRect(iX, baseY - TILE * array[i], TILE, TILE * array[i]);
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

        drawBase();
        updateAnimation();
    }

    public void pauseSorting() {

    }

    public void resumeSorting() {

    }

    public void updateAnimation() {
        bs.show();
        g.dispose();
        try {
            Thread.sleep(DELAY);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void swap(int i, int j) {
        int paddingX = (this.getWidth() - array.length * SPACE) / 2;

        int xi = paddingX + SPACE * i;
        int xj = paddingX + SPACE * j;
        int d = xi - xj;

        while (xi - xj != -d) {
            drawBase();
            setColor(i, Color.DARK_GRAY);
            setColor(j, Color.DARK_GRAY);
            remove(i, xi++);
            remove(j, xj--);
            drawUnit(i, xi);
            drawUnit(j, xj);
            updateAnimation();
        }
        
        int tmp = array[i];
        array[i] = array[j];
        array[j] = tmp;
        drawBase();
        updateAnimation();
    }

    public void drawBase() {
        g = bs.getDrawGraphics();
        g.setColor(VISUALIZER_BACKGROUND_COLOR);
        g.fillRect(0, 0, VISUALIZER_WIDTH, VISUALIZER_HEIGHT);

        g.setColor(Color.WHITE);
        int baseY = this.getHeight() - (this.getHeight() - array.length * TILE) / 2;
        int paddingX = (this.getWidth() - array.length * SPACE) / 2;

        for (int i = 0; i < array.length; i++) {
            int iX = SPACE * i + paddingX;
            g.fillRect(iX, baseY - TILE * array[i], TILE, TILE * array[i]);
        }
    }

    public void remove(int i, int xi) {
        int baseY = this.getHeight() - (this.getHeight() - array.length * TILE) / 2;

        g.setColor(VISUALIZER_BACKGROUND_COLOR);
        g.fillRect(xi, baseY - TILE * array[i], TILE, TILE * array[i]);

        int paddingX = (this.getWidth() - array.length * SPACE) / 2;
        int ti = (xi - paddingX) / SPACE;

        g.setColor(Color.WHITE);
        g.fillRect(ti * SPACE + paddingX, baseY - TILE * array[ti], TILE, TILE * array[ti]);
        if (ti + 1 < array.length) {
            g.setColor(Color.WHITE);
            g.fillRect((ti + 1) * SPACE + paddingX, baseY - TILE * array[ti + 1], TILE, TILE * array[ti + 1]);
        }
    }

    public void drawUnit(int i, int xi) {
        int baseY = this.getHeight() - (this.getHeight() - array.length * TILE) / 2;
        g.setColor(Color.BLUE);
        g.fillRect(xi, baseY - TILE * array[i], TILE, TILE * array[i]);
    }

    public void setColor(int i, Color color) {
        int baseY = this.getHeight() - (this.getHeight() - array.length * TILE) / 2;
        int paddingX = (this.getWidth() - array.length * SPACE) / 2;
        g.setColor(color);
        g.fillRect(paddingX + i * SPACE, baseY - TILE * array[i], TILE, TILE * array[i]);
    }

    public void moveFrom(int j, int i) {

    }
    public int getValue(int i) {
        return array[i];
    }

    public int getLength() {
        return array.length;
    }

}