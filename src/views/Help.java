package views;

import javax.swing.JLabel;
import javax.swing.border.LineBorder;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

public class Help extends JLabel {
    private final int HELP_WIDTH = 201;
    private final int HELP_HEIGHT = 500;

    public Help(String currentSorting) {
        if (currentSorting == "Selection Sort") {
            setText("<html><h3>______Selection Sort______</h3><div>VISUALIZATION:<i><ol><li>The first element in unsorted segment is marked as 'blue'</li><li>'cyan' block iterates to find the minimum  in unsorted segment (then marks it as 'blue')</li><li>Then swap 2 'blue' blocks to find the correct the position of the first element in unsorted segment</li><ol></i></div><div> TIME COMPLEXITY:<i><ul> <li>Best case: O(n^2)</li> <li>Average case: O(n^2)</li> <li>Worst case: O(n^2)</li></ul></i></div></html>");
        } else if (currentSorting == "Merge Sort") {
            setText("<html><h3>________Merge Sort_______</h3><div>VISUALIZATION:<br><br>CORE: <i>Merge 2 adjacent sorted segments<br><ol><li>Make a copy segment of 2 adjacent sorted segments (colored 'white')</li><li>Compare 2 head elements of 2 segments, take the lower one to sort (marks as 'blue')</li><li>Repeatedly, 2 adjacent segments are merged into 1 sorted segment</li></ol></i></div><div> TIME COMPLEXITY:<i><ul><li>Best case: O(nlogn)</li> <li>Average case: O(nlogn)</li> <li>Worst case: O(nlogn)</li></ul></i></div></html>");
        } else if (currentSorting == "Shell Sort") {
            setText("<html><h3>________Shell Sort_______</h3><div>VISUALIZATION:<i><ol><li>Select gap 'h' bounding by 2 'yellow' vertical lines</li><li>From h-th elements iterating, each element finds backward lower one in sequence gapped by 'h' to swap (colored by 'blue') </li><li>Repeatedly, decrease 'h' to 0 (Knuth formula) then stop</li></ol></i></div><div>TIME COMPLEXITY:<i><ul><li>Best case: O(nlogn)</li> <li>Average case: O(n(logn)^2)</li> <li>Worst case: O(n^2)</li></ul></i></div></html>");
        } else if (currentSorting == "Quick Sort") {
            setText("<html><h3>________Quick Sort_______</h3><div>VISUALIZATION:<br><br>CORE: <i>Find correct index for 'pivot' (marked as 'yellow') in a segment<br><ol><li>Take 2 pointers (i & j), 'i' counts the number of elements below 'pivot' value, 'j' iterates to find them and swap with i when find one (marked as 'blue')</li><li>Then swap 'i+1' and 'pivot', locate 'pivot' correctly</li></ol></i></div><div> TIME COMPLEXITY:<i><ul><li>Best case: O(nlogn)</li> <li>Average case: O(nlogn)</li> <li>Worst case: O(n^2)</li></ul></i></div></html>");
        } else {
            setText("<html><h2>_____WELCOME_____</h2><h5>SORTING VISUALIZER APPLICATION</h5><div><h5>DESCRIPTION:</h5><br> visualize three Sorting Algorithms<ul><li>Selection Sort</li><li>Merge Sort</li><li>Shell Sort</li></ul></div><div><h5>FEATURE:</h5><ul><li>Generate Array (input / random)</li><li>Sorting Animation</li><li>Control Animation Speed</li></ul></div></html>");
        }
        setBackground(Color.WHITE);
        setPreferredSize(new Dimension(HELP_WIDTH, HELP_HEIGHT));
        setFont(new Font("Arial", Font.BOLD, 12));
        setBorder(new LineBorder(Color.BLACK));
    }
}