import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;

public class Virus extends Enemy{

    ////////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////// Variables ////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////

    // Creating array to store animations
    private BufferedImage[] virusGraphics;

    ////////////////////////////////////////////////////////////////////////////////////
    //////////////////////////////// Creating Virus ////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////
    public Virus(BlockMap tm){
        super(tm);

        // Dimensions of the Virus - corresponds to the size of animation PNG
        width = 30;
        height = 30;
        boxWidth = 30;
        boxHeight = 30;

        // Specifying the speed of each virus action
        move = 0.3;
        maxMove = 0.3;
        fall = 0.2;
        maxFall = 10.0;

        //load virus graphics
        try {
            BufferedImage loadVirus = ImageIO.read(getClass().getResourceAsStream("Images/virus.gif"));

            virusGraphics = new BufferedImage[4];
            for (int i = 0; i < virusGraphics.length; i++) {
                virusGraphics[i] = loadVirus.getSubimage(i*width, 0, width, height);

            }
        } catch (Exception e){ //if error
            e.printStackTrace();
            System.out.println("Error retrieving virus.java graphics");
        }

        // Setting animation settings
        animation = new Animation();
        animation.aniArray(virusGraphics);
        animation.delay( 500);

        //Set initial variables
        health = maxHealth = 3;
        damage = 1;
        right = true;
        Right = true;
    }

    // Controlling virus movement
    private void getNextPosition() {
        if (left) {
            vex -= move;
            if (vex < -maxMove) {
                vex = -maxMove;
            }
        } else if (right) {
            vex += move;
            if (vex > maxMove) {
                vex = maxMove;
            }
        }
        if(falling){
            vey+= fall;
        }
    }

    ////////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////// Init /////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////
    public void init() {

    }

    ////////////////////////////////////////////////////////////////////////////////////
    /////////////////////////////////// Update /////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////
    public void update(){
        //update position of virus
        getNextPosition();
        checkMapCollision();
        setPosition(x2,y2);

        //check flinch
        if(flinching){
            long elapsed = (System.nanoTime() - timer) / 1000000;
            if (elapsed > 400) {
                flinching = false;
            }
        }
        //Turn around when wall is hit
        if (right){
        if(vex == 0) {
            right = false;
            left = true;
            Right = false;
        }
        }
        if (left){
            if(vex == 0) {
                right = true;
                left = false;
                Right = true;
            }
        }
        animation.update();
    }

    ////////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////// Draw /////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////
    public void draw(Graphics2D g){
        setMapPosition();
        super.draw(g);
    }
}