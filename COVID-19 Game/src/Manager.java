import java.awt.*;
import java.util.ArrayList;

public class Manager {
    ////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////// Variables  //////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////

    // Creating static fields for manager to reference
    public static final int MENU = 0;
    public static final int GAME = 1;
    public static final int CONTROLS = 2;
    public static final int LEADERBOARD = 3;
    public static final int ABOUT = 4;
    public static final int GAMEOVER = 5;
    public static final int QUIZ = 6;
    public static final int YOUWIN = 7;

    // Creating state array
    private ArrayList<State> states;
    private int state;

    ////////////////////////////////////////////////////////////////////////////////////
    /////////////////////////////// Creating Manager  //////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////
    public Manager(){
        states = new ArrayList<State>();
        state = MENU;
        states.add(new Menu(this));
        states.add(new Game(this));
        states.add(new Controls(this));
        states.add(new Leaderboard(this));
        states.add(new About(this));
        states.add(new GameOver(this));
        states.add(new Quiz(this));
        states.add(new YouWin(this));
    }

    public void setState(int cState) {
        state = cState;
        states.get(state).init();
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
        states.get(state).update();
    }

    ////////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////// Draw /////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////
    public void draw(Graphics2D g) {
        states.get(state).draw(g);

    }

    ////////////////////////////////////////////////////////////////////////////////////
    //////////////////////////////// Key Events ////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////
    public void keyPressed(int k){

        states.get(state).keyPressed(k);
    }

    public void keyReleased(int k){

        states.get(state).keyReleased(k);
    }
}
