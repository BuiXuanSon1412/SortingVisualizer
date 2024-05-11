import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;


import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import views.Screen;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame window = new JFrame();
            
            window.setResizable(false);
            window.setTitle("Sorting Visualizer");

            Screen screenPanel = new Screen();
            window.add(screenPanel);
            window.pack();

            window.setLocationRelativeTo(null);
            window.setVisible(true);

            window.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
            window.addWindowListener(new WindowAdapter() {
                public void windowClosing(WindowEvent e) {
                    int result = JOptionPane.showConfirmDialog(window, "Are you sure you want to exit?", "Confirm Exit", JOptionPane.YES_NO_OPTION);
                    if (result == JOptionPane.YES_OPTION) {
                        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                        window.dispose();
                    }
                }
            });
            
        });
    }
}
