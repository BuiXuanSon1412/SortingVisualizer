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
    public int[] inputGenerate(char[] sequence) {
        // transfer sequence argument to array 
        return null;
    }
}
