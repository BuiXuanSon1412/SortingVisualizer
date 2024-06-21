package views;

import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import algorithms.MergeSort;
import algorithms.QuickSort;
import algorithms.SelectionSort;
import algorithms.ShellSort;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.awt.event.ActionEvent;

public class Screen extends JPanel {
    private final int SCREEN_WIDTH = 1000; // Increased width to accommodate chat box
    private final int SCREEN_HEIGHT = 530;
    private String currentSorting;

    private Visualizer visualizer;
    private Help help;
    private ChatWithBots chatWithBots;
    private JButton[] optionButton = new JButton[5];
    private JButton enterButton, uploadButton;
    private JTextField input;
    private JSlider speed;
    private JMenuItem chatMenuItem; // Reference to the ChatWithBots menu item

    private EventHandler eventHandler = new EventHandler();

    // Constructor to arrange components on screen: visualizer, button, ...
    public Screen() {
        /* general settings */
        this.setDoubleBuffered(true);
        this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
        this.setLayout(new BorderLayout());
        this.setBackground(Color.WHITE);
        /* option panel */
        int OPTION_HEIGHT = 30;
        int BUTTON_WIDTH = 135;
        int BUTTON_HEIGHT = 20;

        JPanel option = new JPanel();
        option.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 5));
        option.setPreferredSize(new Dimension(SCREEN_WIDTH, OPTION_HEIGHT));
        option.setBackground(Color.WHITE);
        option.setDoubleBuffered(true);

        String[] buttonLabels = { "Generate", "Selection Sort", "Merge Sort", "Shell Sort", "Quick Sort" };
        for (int i = 0; i < 5; i++) {
            JButton button = new JButton(buttonLabels[i]);
            button.setPreferredSize(new Dimension(BUTTON_WIDTH, BUTTON_HEIGHT));
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
        header.setPreferredSize(new Dimension(SCREEN_WIDTH, OPTION_HEIGHT));
        header.setBackground(Color.WHITE);
        header.setDoubleBuffered(true);

        JMenuBar menuBar = new JMenuBar();
        menuBar.setBackground(Color.WHITE);
        JMenu menu = new JMenu("Options");
        menu.setBackground(Color.WHITE);
        JMenuItem[] item = new JMenuItem[3]; // Increased size for new item
        String[] items = { "Help", "ChatWithBots", "Exit" };
        for (int i = 0; i < 3; i++) { // Updated loop to include new item
            item[i] = new JMenuItem(items[i]);
            item[i].setBackground(Color.WHITE);
            item[i].addActionListener(eventHandler);
            menu.add(item[i]);
        }
        chatMenuItem = item[1]; // Assign ChatWithBots menu item to reference
        menuBar.add(menu);
        header.add(menuBar);

        input = new JTextField();
        input.setPreferredSize(new Dimension(3 * BUTTON_WIDTH / 2, BUTTON_HEIGHT + 1));
        header.add(input);

        enterButton = new JButton("Enter");
        enterButton.setPreferredSize(new Dimension((int) (BUTTON_WIDTH / 1.4), BUTTON_HEIGHT));
        enterButton.setBackground(Color.WHITE);
        enterButton.addActionListener(eventHandler);
        header.add(enterButton);

        uploadButton = new JButton("Upload");
        uploadButton.setPreferredSize(new Dimension((int) (BUTTON_WIDTH / 1.4), BUTTON_HEIGHT));
        uploadButton.setBackground(Color.WHITE);
        uploadButton.addActionListener(eventHandler);
        header.add(uploadButton);

        JTextField fps = new JTextField("150fps");
        fps.setPreferredSize(new Dimension(55, BUTTON_HEIGHT + 1));
        fps.setBackground(Color.WHITE);
        fps.setEditable(false);
        visualizer.setSpeed(150);

        speed = new JSlider(JSlider.HORIZONTAL, 1, 1000, 150);
        speed.setPreferredSize(new Dimension((int) (BUTTON_WIDTH / 1.4), BUTTON_HEIGHT));
        speed.setBackground(Color.WHITE);
        speed.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                int value = speed.getValue();
                fps.setText(value + "fps");
                visualizer.setSpeed(value);
            }
        });

        header.add(speed);
        header.add(fps);
        this.add(header, BorderLayout.NORTH);

        /* chatbox panel */
        chatWithBots = new ChatWithBots();
        chatWithBots.setPreferredSize(new Dimension(250, SCREEN_HEIGHT));
        chatWithBots.setBackground(Color.WHITE);
        chatWithBots.setVisible(false);
        this.add(chatWithBots, BorderLayout.WEST);
    }

    private void showManual() {
        help = new Help(currentSorting);
        visualizer.setPreferredSize(new Dimension(visualizer.getWidth() - 201, visualizer.getHeight()));
        this.add(help, BorderLayout.EAST);
        this.revalidate();
        this.repaint();
    }

    private void hideManual() {
        this.remove(help);
        this.revalidate();
        this.repaint();
        help = null;
    }

    public void switchAll(boolean mode) {
        for (int i = 0; i < 5; i++) {
            optionButton[i].setEnabled(mode);
        }
        enterButton.setEnabled(mode);
        uploadButton.setEnabled(mode);
    }

    public void enableGenerate() {
        optionButton[0].setEnabled(true);
        enterButton.setEnabled(true);
        uploadButton.setEnabled(true);
    }

    private void toggleChat() {
        chatWithBots.setVisible(!chatWithBots.isVisible());
        chatMenuItem.setText(chatWithBots.isVisible() ? "Hide ChatWithBots" : "ChatWithBots"); // Update menu item text
        this.revalidate();
        this.repaint();
    }

    private class EventHandler implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            Object source = e.getSource();
            if (source instanceof JButton) {
                JButton button = (JButton) source;
                if (button.getText().equals("Generate")) {
                    visualizer.generateRandomArray();
                    switchAll(true);
                } else if (button.getText().equals("Selection Sort")) {
                    switchAll(false);
                    visualizer.animateSorting(new SelectionSort());
                    currentSorting = "Selection Sort";
                    if (help != null) {
                        hideManual();
                        showManual();
                    }
                } else if (button.getText().equals("Merge Sort")) {
                    switchAll(false);
                    visualizer.animateSorting(new MergeSort());
                    currentSorting = "Merge Sort";
                    if (help != null) {
                        hideManual();
                        showManual();
                    }
                } else if (button.getText().equals("Shell Sort")) {
                    switchAll(false);
                    visualizer.animateSorting(new ShellSort());
                    currentSorting = "Shell Sort";
                    if (help != null) {
                        hideManual();
                        showManual();
                    }
                } else if (button.getText().equals("Quick Sort")) {
                    switchAll(false);
                    visualizer.animateSorting(new QuickSort());
                    currentSorting = "Quick Sort";
                    if (help != null) {
                        hideManual();
                        showManual();
                    }
                } else if (button.getText().equals("Upload")) {
                    JFileChooser fileChooser = new JFileChooser();
                    int result = fileChooser.showOpenDialog(Screen.this.getParent());
                    if (result == JFileChooser.APPROVE_OPTION) {
                        File file = fileChooser.getSelectedFile();
                        System.out.println(file.getAbsolutePath());
                        try (FileReader fr = new FileReader(file);
                                BufferedReader br = new BufferedReader(fr)) {
                            String str = br.readLine();
                            visualizer.generateInputArray(str);
                        } catch (IOException ex) {
                            System.out.println("Error reading file: " + ex.getMessage());
                        }
                        switchAll(true);
                    }

                } else if (button.getText().equals("Enter")) {
                    String seq = input.getText();
                    if (seq != "" || seq != null) {
                        visualizer.generateInputArray(seq);
                    }
                    switchAll(true);
                }
            } else if (source instanceof JMenuItem) {
                JMenuItem item = (JMenuItem) source;
                if (item.getText().equals("Help")) {
                    showManual();
                    item.setText("Hide help");
                } else if (item.getText().equals("Hide help")) {
                    hideManual();
                    item.setText("Help");
                } else if (item == chatMenuItem) { // Check specifically for chatMenuItem
                    toggleChat();
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
