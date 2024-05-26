package utils;

import java.util.Random;

public class ArrayGenerator {
    private final int MIN = 1;
    private final int MAX = 100;
    private final int MAX_LEN = 100;

    public int[] randomGenerate() {
        int[] array = new int[MAX_LEN];
        Random random = new Random();
        for (int i = 0; i < array.length; i++) {
            array[i] = random.nextInt(MAX - MIN + 1) + MIN;
        }
        return array;
    }

    public int[] inputGenerate(String seq) {

        boolean isEmpty = true;
        for (int i = 0; i < seq.length(); i++) {
            if (seq.charAt(i) != ' ') {
                isEmpty = false;
                break;
            }
        }
        if (isEmpty) {
            int[] err = {0};
            return err;
        }
        int num = 1;
        for (int i = 0; i < seq.length(); i++) {
            char c = seq.charAt(i);
            if (c == ' ') {
                ++num;
            } else if (c > '9' || c < '0') {
                int[] err = {-1}; // Syntax error
                return err;
            }
        }
        if (num > MAX_LEN) {
            int[] err = {-2}; // exceeding length
            return err;
        }
        int[] array = new int[num];
        int n = 0;
        int idx = 0;
        for (int i = 0; i < seq.length(); i++) {
            char c = seq.charAt(i);
            if (c != ' ') {
                n = 10 * n + (c - '0');
            } else if (n != 0) {
                if (n > MAX) {
                    int[] err= {-3};
                    return err;
                }
                array[idx++] = n;
                n = 0;
            }
        }
        if (n != 0) {
            if (n > MAX) {
                int[] err= {-3};
                return err;
            }
            array[idx] = n;
        }
        return array;
    }
}
