import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;

public class YouWin extends State {

    ////////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////// Variables ////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////
    //Background
    private Background background;

    private BufferedImage image;

    // Text fonts
    private Font titleFont;
    private Font textFont;
    private Font footerFont;

    //Text colours
    private Color colour;

    // Creating about menu
    private int option = 0;
    private String[] menu = {
            "Go to quiz",
            "Back to menu"
    };

    ////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////// Creating GameOver ///////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////

    public YouWin(Manager m) {

        //Creating game state manager in game over screen
        this.m = m;

        //Setting colour
        colour = Color.WHITE;

        //Setting fonts
        titleFont = new Font("Arial", Font.PLAIN, 28);
        textFont = new Font("Arial", Font.PLAIN, 12);
        footerFont = new Font("Arial", Font.PLAIN, 10);

        //Try to load background graphics
        try {

            background = new Background("Images/background.png", 0);

            image = ImageIO.read(getClass().getResourceAsStream("Images/image2.gif")).getSubimage(0, 0, 31, 34);

        }
        catch(Exception e) { //If loading fails
            e.printStackTrace();
            System.out.println("Error loading about YouWin graphics.");
            System.exit(0);
        }

    }

    ////////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////// Menu /////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////
    // Selecting button to go back to the main menu
    private void click() {
        if (option == 0) {
            m.setState(Manager.QUIZ);
        }
        if (option == 1) {
            m.setState(Manager.MENU);
        }
    }


    ////////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////// Other ////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////
    //Creating drawString that recognises line breaks
    void drawString(Graphics g, String string, int x, int y) {
        for (String i : string.split("\n")) {
            g.drawString(i, x, y += g.getFontMetrics().getHeight());
        }
    }

    ////////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////// Init /////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////
    @Override
    public void init() {

    }

    ////////////////////////////////////////////////////////////////////////////////////
    /////////////////////////////////// Update /////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////
    @Override
    public void update() {
        //Updating background
        background.update();
    }

    @Override
    public void draw(Graphics2D g) {
        // draw background
        background.draw(g);

        // set text colour
        g.setColor(colour);

        // draw title
        g.setFont(titleFont);
        g.drawString("Congratulations!", 60, 70);

        g.setFont(textFont);
        g.drawString("You won the game!", 100, 100);
        //Your time = end;
        //g.drawString(Game.end2);

        g.drawImage(image, 280, 200, null);

        for (int i = 0; i < menu.length; i++) {
            if(i != option) {
                g.setColor(Color.WHITE);
            } else {
                g.setColor(Color.RED);
            }
            g.drawString(menu[i], 120, 180 + i * 15);

        }

    }

    ////////////////////////////////////////////////////////////////////////////////////
    //////////////////////////////// Key Events ////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////
    @Override
    public void keyPressed ( int k){
        if (k == KeyEvent.VK_ENTER) {
            click();
        }
        // If option goes below 0, go back to 1
        if(k == KeyEvent.VK_UP || k == KeyEvent.VK_W)  {
            option--;
            if (option == -1){
                option = 1;
            }
        }

        // If option goes higher than 3, go back to 0
        if(k == KeyEvent.VK_DOWN || k == KeyEvent.VK_S) {
            option++;
            if (option == menu.length){
                option = 0;
            }
        }
    }

    @Override
    public void keyReleased ( int k){

    }
}