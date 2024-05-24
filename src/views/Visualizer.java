package views;

import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;

import utils.ArrayGenerator;
import algorithms.SortAbstraction;

public class Visualizer extends Canvas {
    private final int TILE = 3;
    private final int SPACE = 4;
    private final Color VISUALIZER_BACKGROUND_COLOR = Color.BLACK;

    private final long DELAY = 1000 / 1000;
    private int[] array;

    private BufferStrategy bs;
    private Graphics g;

    private Thread thread;

    public Visualizer() {
        this.setBackground(VISUALIZER_BACKGROUND_COLOR);
        ArrayGenerator arrayGenerator = new ArrayGenerator();
        array = arrayGenerator.randomGenerate();

    }

    public void addNotify() {
        super.addNotify();
        createBufferStrategy(2);
        bs = getBufferStrategy();
    }

    public void paint(Graphics g) {
        super.paint(g);
        g.setColor(Color.WHITE);

        int baseY = (this.getHeight() + array.length * TILE) / 2;
        int paddingX = (this.getWidth() - array.length * SPACE) / 2;

        for (int i = 0; i < array.length; i++) {
            int iX = SPACE * i + paddingX;
            g.fillRect(iX, baseY - TILE * array[i] / 2, TILE, TILE * array[i] / 2);
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

        drawAll(Color.WHITE);
        updateAnimation();
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
            drawAll(Color.WHITE);
            setColor(i, Color.DARK_GRAY);
            setColor(j, Color.DARK_GRAY);
            drawUnit(i, ++xi, Color.BLUE);
            drawUnit(j, --xj, Color.BLUE);
            updateAnimation();
        }

        int tmp = array[i];
        array[i] = array[j];
        array[j] = tmp;
        drawAll(Color.WHITE);
        updateAnimation();
    }

    public void drawAll(Color color) {
        g = bs.getDrawGraphics();
        g.setColor(VISUALIZER_BACKGROUND_COLOR);
        g.fillRect(0, 0, this.getWidth(), this.getHeight());

        g.setColor(color);
        int baseY = (this.getHeight() + array.length * TILE) / 2;
        int paddingX = (this.getWidth() - array.length * SPACE) / 2;

        for (int i = 0; i < array.length; i++) {
            int iX = SPACE * i + paddingX;
            g.fillRect(iX, baseY - TILE * array[i] / 2, TILE, TILE * array[i] / 2);
        }
    }

    public void removeUnit(int i, int xi, Color color) {
        int baseY = (this.getHeight() + array.length * TILE) / 2;

        g.setColor(VISUALIZER_BACKGROUND_COLOR);
        g.fillRect(xi, baseY - TILE * array[i] / 2, TILE, TILE * array[i] / 2);

        int paddingX = (this.getWidth() - array.length * SPACE) / 2;
        int ti = (xi - paddingX) / SPACE;

        g.setColor(color);
        g.fillRect(ti * SPACE + paddingX, baseY - TILE * array[ti] / 2, TILE, TILE * array[ti] / 2);
        if (ti + 1 < array.length) {
            g.fillRect((ti + 1) * SPACE + paddingX, baseY - TILE * array[ti + 1] / 2, TILE, TILE * array[ti + 1] / 2);
        }
    }

    public void drawUnit(int i, int xi, Color color) {
        int baseY = (this.getHeight() + array.length * TILE) / 2;
        g.setColor(color);
        g.fillRect(xi, baseY - TILE * array[i] / 2, TILE, TILE * array[i] / 2);
    }

    public void setColor(int i, Color color) {
        int baseY = (this.getHeight() + array.length * TILE) / 2;
        int paddingX = (this.getWidth() - array.length * SPACE) / 2;
        g.setColor(color);
        g.fillRect(paddingX + i * SPACE, baseY - TILE * array[i] / 2, TILE, TILE * array[i] / 2);
    }

    public void moveFrom(int i, int j, int k, int left, int right, int[] temp, Color color) {
        int dx = (k - i) * SPACE;
        int dy = array.length * TILE / 2;
        double d = Math.sqrt((double) dx * dx + dy * dy);
        double idx = dx / d;
        double idy = dy / d;

        int paddingX = (this.getWidth() - array.length * SPACE) / 2;
        int baseY = (this.getHeight() + array.length * TILE) / 2;

        float x = 0;
        float y = 0;

        int mid = (left + right) / 2;

        while (true) {
            g = bs.getDrawGraphics();
            g.setColor(VISUALIZER_BACKGROUND_COLOR);
            g.fillRect(0, 0, this.getWidth(), this.getHeight());
             
            g.setColor(Color.DARK_GRAY);
             
            for (int h = 0; h < temp.length; h++) {
                int iX = SPACE * h + paddingX;
                g.fillRect(iX, baseY - TILE * temp[h] / 2, TILE, TILE * temp[h] / 2);
            }
            // draw boundary lines
            g.setColor(Color.YELLOW);
            g.drawLine(paddingX + left * SPACE - 1, baseY, paddingX + left * SPACE - 1, baseY - array.length * TILE);
            g.drawLine(paddingX + right * SPACE + TILE, baseY, paddingX + right * SPACE + TILE,
                    baseY - array.length * TILE);
            g.setColor(Color.RED);
            g.drawLine(paddingX + mid * SPACE + TILE, baseY, paddingX + mid * SPACE + TILE,
                    baseY - array.length * TILE / 2);

            // draw merging partition
            drawSegment(left, right, temp, Color.WHITE);
            if (i <= mid) {
                drawSegment(left, i - 1, temp, Color.DARK_GRAY);
                drawSegment((left + right) / 2 + 1, j - 1, temp, Color.DARK_GRAY);
            } else {
                drawSegment(left, j - 1, temp, Color.DARK_GRAY);
                drawSegment((left + right) / 2 + 1, i - 1, temp, Color.DARK_GRAY);
            }
            drawTempSegment(left, k - 1, color);
            // draw movement
            g.setColor(color);
            g.fillRect((int) (paddingX + i * SPACE + x), (int) (baseY - TILE * temp[i] / 2 - y), TILE,
                    TILE * temp[i] / 2);
            updateAnimation();
            if ((int) y == dy)
                break;
            x += idx;
            y += idy;
        }
        array[k] = temp[i];
    }

    public void drawSegment(int i, int j, int[] arr, Color color) {
        g.setColor(color);

        int baseY = (this.getHeight() + array.length * TILE) / 2;
        int paddingX = (this.getWidth() - array.length * SPACE) / 2;

        for (int k = i; k <= j; k++) {
            int iX = SPACE * k + paddingX;
            g.fillRect(iX, baseY - TILE * arr[k] / 2, TILE, TILE * arr[k] / 2);
        }
    }

    public void drawTempSegment(int i, int j, Color color) {
        int baseY = this.getHeight() / 2;
        int paddingX = (this.getWidth() - array.length * SPACE) / 2;
        for (int k = i; k <= j; k++) {
            g.setColor(color);
            g.fillRect(paddingX + k * SPACE, baseY - TILE * array[k] / 2, TILE, TILE * array[k] / 2);
        }
    }

    public int getValue(int i) {
        return array[i];
    }

    public int getLength() {
        return array.length;
    }

    public int[] getArray() {
        return array;
    }
}