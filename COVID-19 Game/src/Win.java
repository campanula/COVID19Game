import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;

public class Win extends Winner{

    ////////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////// Variables ////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////

    // Creating array to store animations
    private BufferedImage[] winGraphics;

    ////////////////////////////////////////////////////////////////////////////////////
    //////////////////////////////// Creating Virus ////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////
    public Win(BlockMap tm){
        super(tm);

        // Dimensions of the Virus - corresponds to the size of animation PNG
        width = 30;
        height = 30;
        boxWidth = 30;
        boxHeight = 30;

/*        // Specifying the speed of each virus action
        move = 0.3;
        maxMove = 0.3;
        fall = 0.2;
        maxFall = 10.0;*/

        //load winpoint graphics
        try {
            BufferedImage loadVirus = ImageIO.read(getClass().getResourceAsStream("Images/virus.gif"));

            winGraphics = new BufferedImage[4];
            for (int i = 0; i < winGraphics.length; i++) {
                winGraphics[i] = loadVirus.getSubimage(i*width, 0, width, height);

            }
        } catch (Exception e){ //if error
            e.printStackTrace();
            System.out.println("Error retrieving win.java graphics");
        }

        // Setting animation settings
        animation = new Animation();
        animation.aniArray(winGraphics);
        animation.delay( 500);

        //Set initial variables
        rev = 0;
        right = true;
        Right = true;
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
        //update position of winpoint
        checkMapCollision();
        setPosition(x2,y2);

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