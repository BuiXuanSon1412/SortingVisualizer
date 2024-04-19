package views;

import javax.swing.JPanel;

import algorithms.MergeSort;
import algorithms.SelectionSort;
import algorithms.ShellSort;

import javax.swing.JButton;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Screen extends JPanel {
    private final int iScreenWidth = 768;
    private final int iScreenHeight = 576;
    private final float fScale = 0.95f;
    private Visualizer visualizer;
    private JPanel optionPanel;
    private JButton generateRandomArrayButton;
    private JButton selectionSortButton;
    private JButton mergeSortButton;
    private JButton shellSortButton;
    private JButton pauseButton;
    private ButtonHandler buttonHandler = new ButtonHandler();

    // Constructor to arrange components on screen: visualizer, button, ...
    public Screen() {
        this.setDoubleBuffered(true);
        this.setLayout(new BorderLayout());
         
        int iVisualizerHeight = (int)(fScale * iScreenHeight);
        int iOptionHeight = iScreenHeight - iVisualizerHeight;

        // VisualizerPanel
        visualizer = new Visualizer(iScreenWidth, iVisualizerHeight);
        this.add(visualizer, BorderLayout.NORTH);

        // OptionPanel
        optionPanel = new JPanel();
        optionPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));
        optionPanel.setPreferredSize(new Dimension(iScreenWidth, iOptionHeight));
        optionPanel.setBackground(Color.LIGHT_GRAY);
        optionPanel.setDoubleBuffered(true);

        /* --- generateRandomArrayButton --- */
        generateRandomArrayButton = new JButton("Generate");
        generateRandomArrayButton.setPreferredSize(new Dimension(135, (int)(0.7 * iOptionHeight)));
        generateRandomArrayButton.addActionListener(buttonHandler);
        optionPanel.add(generateRandomArrayButton);
        
        /* --- selecttionSortButton --- */
        selectionSortButton = new JButton("Selection Sort");
        selectionSortButton.setPreferredSize(new Dimension(135, (int)(0.7 * iOptionHeight)));
        selectionSortButton.addActionListener(buttonHandler);
        optionPanel.add(selectionSortButton);
        
        /* --- mergeSortButton --- */
        mergeSortButton = new JButton("Merge Sort");
        mergeSortButton.setPreferredSize(new Dimension(135, (int)(0.7 * iOptionHeight)));
        mergeSortButton.addActionListener(buttonHandler);
        optionPanel.add(mergeSortButton);
        
        /* --- shellSortButton --- */
        shellSortButton = new JButton("Shell Sort");
        shellSortButton.setPreferredSize(new Dimension(135, (int)(0.7 * iOptionHeight)));
        shellSortButton.addActionListener(buttonHandler);
        optionPanel.add(shellSortButton);
        
        /* --- exitButton --- */
        //exitButton = new JButton("Exit");
        
        /* --- pauseButton --- */
        pauseButton = new JButton("Pause");
        pauseButton.setPreferredSize(new Dimension(135, (int)(0.7 * iOptionHeight)));
        optionPanel.add(pauseButton);
        this.add(optionPanel, BorderLayout.SOUTH);


        
    }
    private class ButtonHandler implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            JButton button = (JButton) (e.getSource());
            if (button == generateRandomArrayButton) {
                visualizer.generateRandomArray();
            } else if (button.getText().equals("Selection Sort")) {
                visualizer.animateSorting(new SelectionSort());

            } else if (button.getText().equals("Merge Sort")) {
                visualizer.animateSorting(new MergeSort());
                
            } else if (button.getText().equals("Shell Sort")) {
                visualizer.animateSorting(new ShellSort());
            }
        }
    }
}
