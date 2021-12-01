import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;

public class Menu extends State{

    ////////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////// Variables ////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////
    //Background
    private Background background;

    private BufferedImage menuimage;

    // Text fonts
    private Font titleFont;
    private Font textFont;

    //Text colours
    private Color colour;
    private Color colour2;

    // Creating about menu
    private int option = 0;
    private String[] menu = {
            "Start",
            "About",
            "Controls",
            "Quiz",
            "Leaderboard",
            "Quit"
    };


    ////////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////// Creating Menu ////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////
    public Menu(Manager m) {

        //Creating game state manager in menu
        this.m = m;

        //Setting colour
        colour = Color.WHITE;
        colour2 = Color.RED;

        //Setting fonts
        titleFont = new Font("Arial", Font.PLAIN, 28);
        textFont = new Font("Arial", Font.PLAIN, 12);

        //Try to load background graphics
        try {
            background = new Background("Images/background.png", 0);

            menuimage = ImageIO.read(getClass().getResourceAsStream("Images/menu.gif")).getSubimage(0, 0, 10, 10);

        } catch (Exception e) { //If loading fails
            e.printStackTrace();
            System.out.println("Error loading menu graphics.");
            System.exit(0);
        }

    }

    ////////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////// Menu /////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////
    // Selecting which state to enter
    private void click() {
        if(option == 0){
            m.setState(Manager.GAME);
        }
        if(option == 1){
            m.setState(Manager.ABOUT);
        }
        if(option == 2){
            m.setState(Manager.CONTROLS);
        }
        if(option == 3){
            m.setState(Manager.QUIZ);
        }
        if(option == 4){
            m.setState(Manager.LEADERBOARD);
        }
        if(option == 5){
            System.exit(0);
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


    ////////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////// Draw /////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////
    @Override
    public void draw(Graphics2D g) {
        // draw background
        background.draw(g);

        //draw title
        g.setColor(colour2);
        g.setFont(titleFont);
        //make function to auto center
        g.drawString("I", 100, 70);
        g.setColor(colour);
        g.drawString("mmunity", 107, 70);

        //draw menu options
        g.setFont(textFont);
        for (int i = 0; i < menu.length; i++) {
            if(i != option) {
                g.setColor(Color.WHITE);
            } else {
                g.setColor(Color.RED);
            }
            g.drawString(menu[i], 125, 120 + i * 15);
        }
        // draw floating head
        if(option == 0) g.drawImage(menuimage, 110, 110, null);
        else if(option == 1) g.drawImage(menuimage, 110, 125, null);
        else if(option == 2) g.drawImage(menuimage, 110, 140, null);
        else if(option == 3) g.drawImage(menuimage, 110, 155, null);
        else if(option == 4) g.drawImage(menuimage, 110, 170, null);
        else if(option == 5) g.drawImage(menuimage, 110, 185, null);
    }

    ////////////////////////////////////////////////////////////////////////////////////
    //////////////////////////////// Key Events ////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////
    public void keyPressed(int k) {
        // Press enter to select your option
        if (k == KeyEvent.VK_ENTER) {
            click();
        }

        // If option goes below 0, go back to 5
        if(k == KeyEvent.VK_UP || k == KeyEvent.VK_W)  {
            option--;
            if (option == -1){
                option = 5;
            }

        }
        // If option goes higher than 5, go back to 0
        if(k == KeyEvent.VK_DOWN || k == KeyEvent.VK_S) {
            option++;
            if (option == menu.length){
                option = 0;
            }
        }
    }
    public void keyReleased(int k) {

    }
}
