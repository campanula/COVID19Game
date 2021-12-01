import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class Leaderboard extends State {

    ////////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////// Variables ////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////
    //Background
    private Background background;
    private JTextArea textArea;

    // Text fonts
    private Font titleFont;
    private Font textFont;
    private Font footerFont;

    //Text colours
    private Color colour;

    // Creating lb menu
    private int option = 0;
    private String[] menu = {
            "Press enter to return"
    };


    public void showScores() throws FileNotFoundException {
        try (BufferedReader br = new BufferedReader(new FileReader("src/output.txt"))) {
            textArea.read(br, null);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    ////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////// Creating Leaderboard ////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////

    public Leaderboard(Manager m) {

        //Creating game state manager in leaderboard
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

        }
        catch(Exception e) { //If loading fails
            e.printStackTrace();
            System.out.println("Error loading about leaderboard graphics.");
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

    @Override
    public void draw(Graphics2D g) {
        // draw background
        background.draw(g);

        // set text colour
        g.setColor(colour);

        // draw title
        g.setFont(titleFont);
        g.drawString("Leaderboard", 80, 70);

        //draw menu options
        g.setFont(footerFont);
        for (int i = 0; i < menu.length; i++) {
            g.drawString(menu[i], 220, 230);

        }

        textArea = new JTextArea(20, 20);

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

    }
}
