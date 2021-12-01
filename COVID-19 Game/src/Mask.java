import java.awt.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

public class Mask extends GameObject {

    ////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////// Variables  //////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////
    private boolean hit; // When mask hits something
    private boolean end; // After mask has hit something

    // Creating array to store animations
    private BufferedImage[] maskGraphics;

    ////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////// Creating Mask  //////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////
    public Mask(BlockMap bm, boolean right) {

        super(bm);

        // Dimensions of the Mask - corresponds to the size of animation PNG
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

        // load mask graphics
        try {
            BufferedImage loadMask = ImageIO.read(getClass().getResourceAsStream("Images/mask.gif"));

            maskGraphics = new BufferedImage[2];
            for(int i = 0; i < maskGraphics.length; i++) {
                maskGraphics[i] = loadMask.getSubimage(i * width, 0, width, height);
            }
        }
        catch(Exception e) {
            e.printStackTrace();
            System.out.println("Error retrieving mask.java graphics");
        }

        // Set mask animation
        animation = new Animation();
        animation.aniArray(maskGraphics);
        animation.delay(80);

        //Set initial variables
        Right = right;
    }

    ////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////// Mask Attacking  //////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////

    public void whenHit() {
        if(hit) {
            return;
        }
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
        // Update position of mask
        checkMapCollision();
        setPosition(x2, y2);

        // Set hit functions after mask hits something
        if(vex == 0) {
            if(!hit) {
                whenHit();
            }
        }

        // Ending animation after mask hits something
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

