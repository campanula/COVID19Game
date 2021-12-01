import javax.swing.JPanel;
import java.awt.*;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.awt.event.*;

public class Window extends JPanel implements Runnable, KeyListener {

    //game thread
    private boolean playing;
    private Thread thread;

    //manager
    private Manager m;

    //graphics
    private Graphics2D g;
    private BufferedImage i;
    private int d = 0;

    // time
    private int FPS = 60;
    private long time = 1000 / FPS;

    public Window() {
        super();
        setPreferredSize(new Dimension(320 * 2, 240 * 2));
        setFocusable(true);
        requestFocus();
    }

    public void addNotify() {
        super.addNotify();
        if (thread == null){
            thread = new Thread(this);
            addKeyListener(this);
            thread.start();
        }
    }

    private void init() {
        i = new BufferedImage(320, 240, BufferedImage.TYPE_INT_RGB);
        g = (Graphics2D) i.getGraphics();

        playing = true;

        m = new Manager();
    }

    public void run() {
        init();
        long start;
        long wTime;

        while(playing){

            start = System.nanoTime();

            update();
            draw();
            paintComponent();

            wTime = time - (System.nanoTime() - start) / 1000000;
            if(wTime < 0) {
                wTime = 5;
            }

            try {
                Thread.sleep(wTime);
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }

    }
    private void update() {
        m.update();
    }

    private void draw() {
        m.draw(g);
    }

    private void paintComponent() {
        Graphics g2 = getGraphics();
        g2.drawImage(i, d, d, 320 * 2, 240 * 2,null);
        g2.dispose();
    }

    public void keyTyped(KeyEvent keyEvent) {}

    public void keyPressed(KeyEvent keyEvent) {
        m.keyPressed(keyEvent.getKeyCode());
    }

    public void keyReleased(KeyEvent keyEvent) {
        m.keyReleased(keyEvent.getKeyCode());
    }
}
