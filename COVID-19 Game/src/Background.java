import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;

public class Background {
    ////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////// Variables  //////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////
    private double x;
    private double y;

    // Background
    private BufferedImage background;

    // scale
    private double moveScale;

    ////////////////////////////////////////////////////////////////////////////////////
    /////////////////////////////// Creating Background  ///////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////
    public Background(String b, double ms) {
        moveScale = ms;
        try {
            background = ImageIO.read(getClass().getResourceAsStream(b));
        }
        catch(Exception e){
            e.printStackTrace();
            System.out.println("Error retrieving background.java graphics");
        }
    }
    // Setting background position
    public void setPosition(double x, double y) {
        this.x = (x * moveScale) % 320;
        this.y = (y * moveScale) % 240;
    }

    ////////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////// Init /////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////
    public void init() {

    }

    ////////////////////////////////////////////////////////////////////////////////////
    /////////////////////////////////// Update /////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////
    public void update() {

    }


    ////////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////// Draw /////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////
    public void draw(Graphics2D g) {
        g.drawImage(background, (int)x, (int)y, null);
        if(x<0) {
            g.drawImage(background, (int)x + 320, (int)y, null);
            }
        if ( x > 0) {
            g.drawImage(background, (int)x - 320, (int)y, null);
        }
        }
}
