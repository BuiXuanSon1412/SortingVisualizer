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
    private final int SCREEN_WIDTH = 750;
    private final int SCREEN_HEIGHT = 530;
    private String currentSorting;

    private Visualizer visualizer;
    private JPanel optionPanel;
    private JPanel helpPanel;

    private JButton[] optionButton = new JButton[5];
    private JButton helpButton;
    private ButtonHandler buttonHandler = new ButtonHandler();

    // Constructor to arrange components on screen: visualizer, button, ...
    public Screen() {
        /* general settings */
        this.setDoubleBuffered(true);
        this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
        this.setLayout(new BorderLayout());
        this.setBackground(Color.WHITE);
        /* option panel */
        int iOptionHeight = 30;
        int iButtonWidth = 135;
        int iButtonHeight = 20;

        optionPanel = new JPanel();
        optionPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
        optionPanel.setPreferredSize(new Dimension(SCREEN_WIDTH, iOptionHeight));
        optionPanel.setBackground(Color.WHITE);
        optionPanel.setDoubleBuffered(true);

        String[] buttonLabels = { "Generate", "Selection Sort", "Merge Sort", "Shell Sort", "Pause", "Exit" };
        for (int i = 0; i < 5; i++) {
            JButton button = new JButton(buttonLabels[i]);
            button.setPreferredSize(new Dimension(iButtonWidth, iButtonHeight));
            button.setBackground(Color.WHITE);
            button.addActionListener(buttonHandler);
            optionButton[i] = button;
            optionPanel.add(optionButton[i]);
        }
        helpButton = new JButton("Help");
        helpButton.setBackground(Color.WHITE);
        helpButton.setPreferredSize(new Dimension(40, iButtonHeight));
        helpButton.addActionListener(buttonHandler);
        optionPanel.add(helpButton);

        this.add(optionPanel, BorderLayout.SOUTH);

        /* visualizer panel */
        visualizer = new Visualizer();
        this.add(visualizer, BorderLayout.WEST);
    }

    private void showManual() {
        Help help = new Help(currentSorting);
        visualizer.setPreferredSize(new Dimension(visualizer.getWidth() - 200, visualizer.getHeight()));
        Screen.this.add(help, BorderLayout.EAST);
        helpButton.setVisible(false);
        Screen.this.revalidate();
        Screen.this.repaint();
    }

    private class ButtonHandler implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            JButton button = (JButton) (e.getSource());
            if (button.getText().equals("Generate")) {
                visualizer.generateRandomArray();
            } else if (button.getText().equals("Selection Sort")) {
                visualizer.animateSorting(new SelectionSort());
                currentSorting = "Selection Sort";
            } else if (button.getText().equals("Merge Sort")) {
                visualizer.animateSorting(new MergeSort());
                currentSorting = "Merge Sort";
            } else if (button.getText().equals("Shell Sort")) {
                visualizer.animateSorting(new ShellSort());
                currentSorting = "Shell Sort";
            } else if (button.getText().equals("Pause")) {
                visualizer.pauseSorting();
                button.setText("Resume");
            } else if (button.getText().equals("Resume")) {
                visualizer.resumeSorting();
                button.setText("Pause");
            } else if (button.getText().equals("Exit")) {

            } else if (button.getText().equals("Help")) {
                showManual();
            }
        }
    }
}
