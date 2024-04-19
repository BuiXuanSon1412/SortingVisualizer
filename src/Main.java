import javax.swing.JFrame;
import views.Screen;

public class Main {
    public static void main(String[] args) {
        JFrame window = new JFrame();
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);
        window.setTitle("Sorting Visualizer");
        window.setSize(768, 576);
        
        Screen screenPanel = new Screen();
        window.add(screenPanel);
        window.pack();

        window.setLocationRelativeTo(null);
        window.setVisible(true);
    }
}
