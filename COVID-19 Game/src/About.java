import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

public class About extends State {

    ////////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////// Variables ////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////
    //Background
    private Background background;

    // Text fonts
    private Font titleFont;
    private Font textFont;
    private Font footerFont;

    //Text colours
    private Color colour;

    // Creating about menu
    private int option = 0;
    private String[] menu = {
            "Press enter to return"
    };

    ////////////////////////////////////////////////////////////////////////////////////
    //////////////////////////////// Creating About ////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////
    public About(Manager m) {

        //Creating game state manager in about
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

        } catch (Exception e) { //If loading fails
            e.printStackTrace();
            System.out.println("Error loading about menu graphics.");
            System.exit(0);
        }

    }

    ////////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////// Menu /////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////
    // Selecting button to go back to the main menu
    private void click() {
        if (option == 0) {
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


    ////////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////// Draw /////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////
    @Override
    public void draw(Graphics2D g) {
        // draw background
        background.draw(g);

        // set text colour
        g.setColor(colour);

        // draw title
        g.setFont(titleFont);
        g.drawString("About", 110, 70);

        // draw main text
        g.setFont(textFont);
        drawString(g,"Immunity is a platfomer that aims to teach the" + "\n" + "best ways of preventing viruses such as COVID-19" + "\n" + "through fun and educational gameplay."
                        + "\n" + "Avoiding every virus enemy is the best way to stay " + "\n" + "safe and win." + "\n" + "But 'attacking' with preventative measures is also" + "\n" + "a possible way to keep safe and win the game.",
                20, 100);


        //draw menu options
        g.setFont(footerFont);
        for (int i = 0; i < menu.length; i++) {
            g.drawString(menu[i], 220, 230);

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
    }

    @Override
    public void keyReleased ( int k){
        if(k == MouseEvent.MOUSE_CLICKED){
            click();
        }
    }
}
