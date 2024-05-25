package utils;
import java.util.Random;

public class ArrayGenerator {
    private final int iMinimum = 1;
    private final int iMaximum = 100;
    private final int iNum = 100;
    
    public int[] randomGenerate() {
        int[] array = new int[iNum];
        Random random = new Random();
        for (int i = 0; i < array.length; i++) {
            array[i] = random.nextInt(iMaximum - iMinimum) + iMinimum;
        }
        return array;
    }
    public int[] inputGenerate(String seq) {
        int num = 1;
        for (int i = 0; i < seq.length(); i++) {
            char c = seq.charAt(i);
            if (c == ' ') {
                ++num;
            }
            else if (c > '9' || c < '0') {
                return null;
            }
        }
        int[] array = new int[num];
        int n = 0;
        int j = 0;
        for (int i = 0; i < seq.length(); i++) {
            char c = seq.charAt(i);
            if (c != ' ') {
                n = 10 * n + (c - '0');
            } else if (n != 0) {
                array[j++] = n;
                n = 0;
            }
        }
        array[j] = n;
        
        return array;
    }
}
