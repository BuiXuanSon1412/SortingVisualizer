package views;

import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.Canvas;
import java.awt.Color;

import utils.ArrayGenerator;
import algorithms.SortAbstraction;

public class Visualizer extends Canvas {
    private final Color VISUALIZER_BACKGROUND_COLOR = new Color(0x254336);
    private final Color BAR_COLOR = new Color(0xDAD3BE); // Lighter color for better visibility
    private final Color HIGHLIGHT_COLOR = new Color(0xB7B597); // Highlight color for active elements
    private final Color BOUNDARY_COLOR = new Color(0x6B8A7A); // Color for boundaries during merge
    private final Color WARNING_COLOR = Color.RED;
    private final Color TEXT_COLOR = Color.WHITE;
    private final Color FINAL_COLOR = new Color(0xDAD3BE); // Color for final sorted elements

    private final int BOUND = 101;
    private final int TILE_Y = 2;
    private int TILE_X;
    private int SPACE;
    private int baseY, paddingX;

    private long DELAY;

    private int[] array;

    private BufferStrategy bs;
    private Graphics g;

    private Thread thread;

    public Visualizer() {
        this.setBackground(VISUALIZER_BACKGROUND_COLOR);
        ArrayGenerator arrayGenerator = new ArrayGenerator();
        array = arrayGenerator.randomGenerate();
        setBarSize();
    }

    public void addNotify() {
        super.addNotify();
        createBufferStrategy(2);
        bs = getBufferStrategy();
    }

    public void paint(Graphics g) {
        super.paint(g);
        g.setColor(BAR_COLOR);

        baseY = this.getHeight() / 2 + BOUND * TILE_Y;
        paddingX = (this.getWidth() - array.length * SPACE) / 2;

        for (int i = 0; i < array.length; i++) {
            int iX = SPACE * i + paddingX;
            g.fillRect(iX, baseY - TILE_Y * array[i], TILE_X, TILE_Y * array[i]);
        }
    }

    private void setBarSize() {
        int len = array.length;
        if (len > 80) {
            TILE_X = 4;
            SPACE = 5;
        } else if (len > 60) {
            TILE_X = 6;
            SPACE = 7;
        } else if (len > 40) {
            TILE_X = 8;
            SPACE = 9;
        } else if (len > 20) {
            TILE_X = 10;
            SPACE = 11;
        } else {
            TILE_X = 12;
            SPACE = 13;
        }
    }

    public void setSpeed(int fps) {
        DELAY = 1000 / fps;
    }

    public void animateSorting(SortAbstraction sortAbstraction) {
        thread = new Thread(new Runnable() {
            public void run() {
                sortAbstraction.sort(Visualizer.this);
                Screen screen = (Screen) Visualizer.this.getParent();
                screen.enableGenerate();
            }
        });
        thread.start();
    }

    public void generateRandomArray() {
        ArrayGenerator arrayGenerator = new ArrayGenerator();
        array = arrayGenerator.randomGenerate();

        setBarSize();

        baseY = this.getHeight() / 2 + BOUND * TILE_Y;
        paddingX = (this.getWidth() - array.length * SPACE) / 2;

        drawAll(array, BAR_COLOR);
        updateAnimation();
    }

    public void generateInputArray(String seq) {
        ArrayGenerator arrayGenerator = new ArrayGenerator();
        array = arrayGenerator.inputGenerate(seq);
        if (array[0] == 0) {
            drawWarning("Warning: Empty Array");
        } else if (array[0] == -1) {
            drawWarning("Warning: SyntaxError");
        } else if (array[0] == -2) {
            drawWarning("Warning: Length<=100");
        } else if (array[0] == -3) {
            drawWarning("Warning: Maximum:100");
        } else {
            setBarSize();

            baseY = this.getHeight() / 2 + BOUND * TILE_Y;
            paddingX = (this.getWidth() - array.length * SPACE) / 2;

            drawAll(array, BAR_COLOR);
            updateAnimation();
        }
    }

    public void drawWarning(String error) {
        g = bs.getDrawGraphics();
        int width = this.getWidth() / 4;
        g.setColor(TEXT_COLOR);
        g.fillRect(3 * this.getWidth() / 8, 10, width, 20);
        g.setColor(WARNING_COLOR);
        g.drawString(error, 3 * this.getWidth() / 8 + 22, 25);
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

    public void swap(int i, int j, boolean boundary, boolean[] sorted, int pivot) {
        int xi = paddingX + SPACE * i;
        int xj = paddingX + SPACE * j;
        int d = xi - xj;

        while (true) {
            drawAll(array, BAR_COLOR);
            if (boundary)
                drawBoundary(i, j);
            if (sorted != null) {
                g.setColor(HIGHLIGHT_COLOR);
                g.fillRect(SPACE * pivot + paddingX, baseY - TILE_Y * array[pivot], TILE_X, TILE_Y * array[pivot]);
                g.setColor(BOUNDARY_COLOR);
                for (int k = 0; k < array.length; k++) {
                    if (sorted[k]) {
                        int iX = SPACE * k + paddingX;
                        g.fillRect(iX, baseY - TILE_Y * array[k], TILE_X, TILE_Y * array[k]);
                    }
                }
            }
            setColor(i, Color.DARK_GRAY);
            setColor(j, Color.DARK_GRAY);
            drawUnit(i, xi, Color.BLUE);
            drawUnit(j, xj, Color.BLUE);
            if (xi - xj == -d)
                break;
            ++xi;
            --xj;
            updateAnimation();
        }

        int tmp = array[i];
        array[i] = array[j];
        array[j] = tmp;
        drawAll(array, BAR_COLOR);
        updateAnimation();
    }

    public void swap(int i, int j, boolean boundary, boolean[] sorted, int pivot, Color activeColor, Color gapColor) {
        int xi = paddingX + SPACE * i;
        int xj = paddingX + SPACE * j;
        int d = xi - xj;

        while (true) {
            drawAll(array, BAR_COLOR);
            if (boundary)
                drawBoundary(i, j);
            if (sorted != null) {
                g.setColor(gapColor);
                g.fillRect(SPACE * pivot + paddingX, baseY - TILE_Y * array[pivot], TILE_X, TILE_Y * array[pivot]);
                g.setColor(FINAL_COLOR);
                for (int k = 0; k < array.length; k++) {
                    if (sorted[k]) {
                        int iX = SPACE * k + paddingX;
                        g.fillRect(iX, baseY - TILE_Y * array[k], TILE_X, TILE_Y * array[k]);
                    }
                }
            }
            setColor(i, Color.DARK_GRAY);
            setColor(j, Color.DARK_GRAY);
            drawUnit(i, xi, activeColor);
            drawUnit(j, xj, activeColor);
            if (xi - xj == -d)
                break;
            ++xi;
            --xj;
            updateAnimation();
        }

        int tmp = array[i];
        array[i] = array[j];
        array[j] = tmp;
        drawAll(array, BAR_COLOR);
        updateAnimation();
    }

    public void drawAll(int[] array, Color color) {
        g = bs.getDrawGraphics();
        g.setColor(VISUALIZER_BACKGROUND_COLOR);
        g.fillRect(0, 0, this.getWidth(), this.getHeight());

        g.setColor(color);

        for (int i = 0; i < array.length; i++) {
            int iX = SPACE * i + paddingX;
            g.fillRect(iX, baseY - TILE_Y * array[i], TILE_X, TILE_Y * array[i]);
        }
    }

    public void drawUnit(int i, int xi, Color color) {
        g.setColor(color);
        g.fillRect(xi, baseY - TILE_Y * array[i], TILE_X, TILE_Y * array[i]);
    }

    public void setColor(int i, Color color) {
        g.setColor(color);
        g.fillRect(paddingX + i * SPACE, baseY - TILE_Y * array[i], TILE_X, TILE_Y * array[i]);
    }

    public void moveFrom(int i, int j, int k, int left, int right, int[] temp, Color activeColor, Color mergeColor) {
        int dx = (k - i) * SPACE;
        int dy = BOUND * TILE_Y;
        double d = Math.sqrt((double) dx * dx + dy * dy);
        double idx = dx / d;
        double idy = dy / d;

        float x = 0;
        float y = 0;

        int mid = (left + right) / 2;

        while (true) {
            drawAll(temp, Color.DARK_GRAY);
            // draw boundary lines
            drawBoundary(left, right);
            g.setColor(mergeColor);
            g.drawLine(paddingX + mid * SPACE + TILE_X, baseY, paddingX + mid * SPACE + TILE_X,
                    baseY - BOUND * TILE_Y);

            // draw merging partition
            drawSegment(left, right, temp, BAR_COLOR);
            if (i <= mid) {
                drawSegment(left, i - 1, temp, Color.DARK_GRAY);
                drawSegment((left + right) / 2 + 1, j - 1, temp, Color.DARK_GRAY);
            } else {
                drawSegment(left, j - 1, temp, Color.DARK_GRAY);
                drawSegment((left + right) / 2 + 1, i - 1, temp, Color.DARK_GRAY);
            }
            drawTempSegment(left, k - 1, activeColor);
            // draw movement
            g.setColor(activeColor);
            g.fillRect((int) (paddingX + i * SPACE + x), (int) (baseY - TILE_Y * temp[i] - y), TILE_X,
                    TILE_Y * temp[i]);
            updateAnimation();
            if ((int) y == dy && (int) x == dx)
                break;
            if ((int) y != dy)
                y += idy;
            if ((int) x != dx)
                x += idx;
        }
        array[k] = temp[i];
    }

    public void drawBoundary(int left, int right) {
        g.setColor(BOUNDARY_COLOR);
        g.drawLine(paddingX + left * SPACE - 1, baseY, paddingX + left * SPACE - 1, baseY - 2 * BOUND * TILE_Y);
        g.drawLine(paddingX + right * SPACE + TILE_X, baseY, paddingX + right * SPACE + TILE_X,
                baseY - 2 * BOUND * TILE_Y);
    }

    public void drawSegment(int i, int j, int[] arr, Color color) {
        g.setColor(color);

        for (int k = i; k <= j; k++) {
            int iX = SPACE * k + paddingX;
            g.fillRect(iX, baseY - TILE_Y * arr[k], TILE_X, TILE_Y * arr[k]);
        }
    }

    public void drawTempSegment(int i, int j, Color color) {
        int baseY = this.getHeight() / 2;
        int paddingX = (this.getWidth() - array.length * SPACE) / 2;
        for (int k = i; k <= j; k++) {
            g.setColor(color);
            g.fillRect(paddingX + k * SPACE, baseY - TILE_Y * array[k], TILE_X, TILE_Y * array[k]);
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
