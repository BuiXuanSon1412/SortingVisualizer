package views;

import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

public class Help extends JTextArea {
    private final int HELP_WIDTH = 201;
    private final int HELP_HEIGHT = 500;

    public Help(String currentSorting) {
        if (currentSorting == "Selection Sort") {
            setText("\n_________GUIDE_________\n\n" + currentSorting
                    + "\n\nCYAN: iterating\n\nBLUE: swapping\n\nDARK_GRAY: hidden");
        } else if (currentSorting == "Merge Sort") {
            setText("\n_________GUIDE_________\n\n" + currentSorting
                    + "\n\n\nBLUE: merge\n\nDARK_GRAY: hidden\n\nYELLOW: boundary\n\nRED: partition");
        } else if (currentSorting == "Shell Sort") {
            setText("\n_________GUIDE_________\n\n" + currentSorting
                    + "\n\nBLUE: swapping\n\nDARK_GRAY: hidden");
        } else {
            setText("\n\n\n\n_________GUIDE_________\n\n  SORTING VISUALIZER           APPLICATION\n\n Including:\n   - Selection Sort\n   - Merge Sort\n   - Shell Sort\n\n Features:\n   - Array generation\t    (random/input)\n   - Sorting animation\t    with bars\n   - Control sorting\t    animation");
        }
        setBackground(Color.WHITE);
        setPreferredSize(new Dimension(HELP_WIDTH, HELP_HEIGHT));
        setBorder(new EmptyBorder(2, 2, 2, 2));
        setFont(new Font("Serif", Font.PLAIN, 15));
        setLineWrap(true);
        setWrapStyleWord(true);
        setEditable(false);
    }
}