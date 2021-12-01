import java.awt.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

public class Sanitiser extends GameObject {

    ////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////// Variables  //////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////
    private boolean hit; // When sanitiser hits something
    private boolean end; // After sanitiser has hit something

    // Creating array to store animations
    private BufferedImage[] sanitiserGraphics;

    ////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////// Creating Sanitiser  //////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////
    public Sanitiser(BlockMap tm, boolean right) {

        super(tm);

        // Dimensions of the Sanitiser - corresponds to the size of animation PNG
        width = 30;
        height = 30;
        boxWidth = 15;
        boxHeight = 15;

        //Specifying the attack speed
        move = 3;
        if(left) {
            vex = -move;
        }
        else {
            vex = move;
        }

        // load sanitiser graphics
        try {

            BufferedImage loadSanitiser = ImageIO.read(getClass().getResourceAsStream("Images/sanitiser.gif"));

            sanitiserGraphics = new BufferedImage[2];
            for(int i = 0; i < sanitiserGraphics.length; i++) {
                sanitiserGraphics[i] = loadSanitiser.getSubimage(i * width, 0, width, height);
            }
        }
        catch(Exception e) {
            e.printStackTrace();
            System.out.println("Error retrieving sanitiser.java graphics");
        }

        // Set sanitiser animation
        animation = new Animation();
        animation.aniArray(sanitiserGraphics);
        animation.delay(60);

        // Set initial variables
        Right = right;

    }

    ////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////// Mask Attacking  //////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////

    public void whenHit() {
        if(hit) return;
        hit = true;
    }

    public boolean afterHit() {
        return end;
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

        checkMapCollision();
        setPosition(x2, y2);

        // Set hit functions after sanitiser hits something
        if(vex == 0) {
            if(!hit) {
                whenHit();
            }
        }

        // Ending animation after sanitiser hits something
        animation.update();
        if(hit) {
            if (animation.played()) {
                end = true;
            }
        }

    }

    ////////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////// Draw /////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////
    public void draw(Graphics2D g) {
        setMapPosition();
        super.draw(g);
    }
}
