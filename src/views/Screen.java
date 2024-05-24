package views;

import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.filechooser.FileSystemView;

import algorithms.MergeSort;
import algorithms.SelectionSort;
import algorithms.ShellSort;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionListener;
import java.io.File;
import java.awt.event.ActionEvent;

public class Screen extends JPanel {
    private final int SCREEN_WIDTH = 750;
    private final int SCREEN_HEIGHT = 530;
    private String currentSorting;

    private Visualizer visualizer;
    private JButton[] optionButton = new JButton[4];
    private EventHandler eventHandler = new EventHandler();

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

        JPanel option = new JPanel();
        option.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 5));
        option.setPreferredSize(new Dimension(SCREEN_WIDTH, iOptionHeight));
        option.setBackground(Color.WHITE);
        option.setDoubleBuffered(true);

        String[] buttonLabels = { "Generate", "Selection Sort", "Merge Sort", "Shell Sort" };
        for (int i = 0; i < 4; i++) {
            JButton button = new JButton(buttonLabels[i]);
            button.setPreferredSize(new Dimension(iButtonWidth, iButtonHeight));
            button.setBackground(Color.WHITE);
            button.addActionListener(eventHandler);
            optionButton[i] = button;
            option.add(optionButton[i]);
        }
        this.add(option, BorderLayout.SOUTH);

        /* visualizer panel */
        visualizer = new Visualizer();
        this.add(visualizer, BorderLayout.CENTER);

        /* header panel */
        JPanel header = new JPanel();
        header.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 5));
        header.setPreferredSize(new Dimension(SCREEN_WIDTH, iOptionHeight));
        header.setBackground(Color.WHITE);
        header.setDoubleBuffered(true);

        JMenuBar menuBar = new JMenuBar();
        menuBar.setBackground(Color.WHITE);
        JMenu menu = new JMenu("Options");
        menu.setBackground(Color.WHITE);
        JMenuItem[] item = new JMenuItem[2];
        String[] items = { "Help", "Exit" };
        for (int i = 0; i < 2; i++) {
            item[i] = new JMenuItem(items[i]);
            item[i].setBackground(Color.WHITE);
            item[i].addActionListener(eventHandler);
            menu.add(item[i]);
        }
        menuBar.add(menu);
        header.add(menuBar);

        JTextField input = new JTextField();
        input.setPreferredSize(new Dimension(3 * iButtonWidth, iButtonHeight+1));
        header.add(input);

        JButton enterButton = new JButton("Enter");
        enterButton.setPreferredSize(new Dimension((int) (iButtonWidth/1.4), iButtonHeight));
        enterButton.setBackground(Color.WHITE);
        enterButton.addActionListener(eventHandler);
        header.add(enterButton);

        JButton uploadButton = new JButton("Upload");
        uploadButton.setPreferredSize(new Dimension((int) (iButtonWidth/1.4), iButtonHeight));
        uploadButton.setBackground(Color.WHITE);
        uploadButton.addActionListener(eventHandler);
        header.add(uploadButton);

        this.add(header, BorderLayout.NORTH);
    }

    private void showManual() {
        Help help = new Help(currentSorting);
        visualizer.setPreferredSize(new Dimension(visualizer.getWidth() - 200, visualizer.getHeight()));
        this.add(help, BorderLayout.EAST);
        this.revalidate();
        this.repaint();
    }

    private void disableAll() {
        for (int i = 0; i < 4; i++) {
            optionButton[i].setEnabled(false);
        }
    }

    private class EventHandler implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            Object source = e.getSource();
            if (source instanceof JButton) {
                JButton button = (JButton) source;
                if (button.getText().equals("Generate")) {
                    visualizer.generateRandomArray();
                } else if (button.getText().equals("Selection Sort")) {
                    visualizer.animateSorting(new SelectionSort());
                    disableAll();
                    currentSorting = "Selection Sort";
                } else if (button.getText().equals("Merge Sort")) {
                    visualizer.animateSorting(new MergeSort());
                    disableAll();
                    currentSorting = "Merge Sort";
                } else if (button.getText().equals("Shell Sort")) {
                    visualizer.animateSorting(new ShellSort());
                    disableAll();
                    currentSorting = "Shell Sort";
                } else if (button.getText().equals("Upload")) {
                    JFileChooser fileChooser = new JFileChooser();
                    int result = fileChooser.showOpenDialog(Screen.this.getParent());
                    if (result == JFileChooser.APPROVE_OPTION) {
                        File file = fileChooser.getSelectedFile();
                        System.out.println(file.getAbsolutePath());
                    }
                }
            } else if (source instanceof JMenuItem) {
                JMenuItem item = (JMenuItem) source;
                if (item.getText().equals("Help")) {
                    showManual();
                } else if (item.getText().equals("Exit")) {
                    int result = JOptionPane.showConfirmDialog(Screen.this.getParent(),
                            "Are you sure you want to exit?", "Confirm Exit",
                            JOptionPane.YES_NO_OPTION);
                    if (result == JOptionPane.YES_OPTION) {
                        System.exit(0);
                    }
                }
            }

        }
    }
}
