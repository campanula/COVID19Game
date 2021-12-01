import javax.swing.*;
public class Main {
        public static void main(String[] args) {
            JFrame f = new JFrame("Immunity");
            f.setContentPane(new Window());
            f.setResizable(false);
            f.pack();
            f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            f.setVisible(true);
        }
}
