package views;

import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.Canvas;
import java.awt.Color;

import utils.ArrayGenerator;
import algorithms.SortAbstraction;

public class Visualizer extends Canvas {
    private final Color VISUALIZER_BACKGROUND_COLOR = Color.BLACK;
    
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
        g.setColor(Color.WHITE);

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
            TILE_X = 5;
            SPACE = 6;
        } else if (len > 40) {
            TILE_X = 7;
            SPACE = 8;
        } else if (len > 20) {
            TILE_X = 9;
            SPACE = 10;
        } else {
            TILE_X = 11;
            SPACE = 12;
        }
    }

    public void setSpeed(int fps) {
        DELAY = 1000/fps;
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

        drawAll(Color.WHITE);
        updateAnimation();
    }

    public void generateInputArray(String seq) {
        ArrayGenerator arrayGenerator = new ArrayGenerator();
        array = arrayGenerator.inputGenerate(seq);
        System.out.println(seq == "");
        if (array[0] == 0) {
            drawWarning("Warning: Empty Array");
        }else if (array[0] == -1) {
            drawWarning("Warning: SyntaxError");
        } else if (array[0] == -2) {
            drawWarning("Warning: Length<=100");
        } else if (array[0] == -3) {
            drawWarning("Warning: Maximum:100");
        } else {
            setBarSize();

            baseY = this.getHeight() / 2 + BOUND * TILE_Y;
            paddingX = (this.getWidth() - array.length * SPACE) / 2;

            drawAll(Color.WHITE);
            updateAnimation();
        }

    }

    public void drawWarning(String error) {
        g = bs.getDrawGraphics();
        int width = this.getWidth() / 4;
        g.setColor(Color.WHITE);
        g.fillRect(3 * this.getWidth() / 8, 10, width, 20);
        g.setColor(Color.RED);
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

    public void swap(int i, int j, boolean boundary) {

        int xi = paddingX + SPACE * i;
        int xj = paddingX + SPACE * j;
        int d = xi - xj;

        while (true) {
            drawAll(Color.WHITE);
            if (boundary) drawBoundary(i, j);
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
        drawAll(Color.WHITE);
        updateAnimation();
    }

    public void drawAll(Color color) {
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

    public void moveFrom(int i, int j, int k, int left, int right, int[] temp, Color color) {
        int dx = (k - i) * SPACE;
        int dy = BOUND * TILE_Y;
        double d = Math.sqrt((double) dx * dx + dy * dy);
        double idx = dx / d;
        double idy = dy / d;

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
                g.fillRect(iX, baseY - TILE_Y * temp[h], TILE_X, TILE_Y * temp[h]);
            }
            // draw boundary lines
            drawBoundary(left, right);
            g.setColor(Color.RED);
            g.drawLine(paddingX + mid * SPACE + TILE_X, baseY, paddingX + mid * SPACE + TILE_X,
                    baseY - BOUND * TILE_Y);

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
        g.setColor(Color.YELLOW);
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