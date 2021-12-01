import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

public class Game extends State {

    ////////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////// Variables ////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////
    //Map
    private BlockMap blockMap;

    // Game Objects
    private Player player;
    private ArrayList<Enemy> enemies;
    private ArrayList<Winner> winners;

    //Graphics
    private Background bg;
    private HealthBar hb;

    // Player changes
    private int count = 0;
    private ArrayList<Rectangle> ssize;
    private boolean access = false;
    private boolean  gcontinue;
    private boolean fdeath;
    private int d = 0;

    // Game Time
    long end;
    public static String end2;
    long start = System.nanoTime();

    ////////////////////////////////////////////////////////////////////////////////////
    //////////////////////////////// Creating Game /////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////
    public Game(Manager m) {
        this.m = m;
        init();
    }

    ////////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////// Init /////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////
    public void init() {

       blockMap = new BlockMap(30);
        blockMap.getBlocks("Images/mapart.gif");
        blockMap.getMap("Images/game.map");
        blockMap.setPosition(0, 0);
        blockMap.smoothing(1);

        bg = new Background("Images/background.png", 0.1);

        player = new Player(blockMap);
        player.setPosition(50, 200);

        populateEnemies();
        makeWinner();

        hb = new HealthBar(player);

        // start event
        gcontinue = true;
        ssize = new ArrayList<Rectangle>();
        start();

    }

    public void dead(){
        if (Player.dead == true){
            m.setState(Manager.GAMEOVER);
            Player.health = 3;
            Player.dead = false;
        }
    }

    public void winner(){
        if (Player.winz == true){
            end = System.nanoTime();
            end2 = Long.toString(end);
            System.out.println("You took " + (end - start)/ 1000000000 + " seconds");
            m.setState(Manager.YOUWIN);
            Player.winz = false;
        }
    }

    private void populateEnemies() {

        enemies = new ArrayList<Enemy>();

        Virus v;
        Point[] vir = new Point[]{
                new Point(1044, 100),
                new Point(1163, 200),
                new Point(1527, 200),
                new Point(2328,200)
        };


        for (int i = 0; i < vir.length; i++) {
            v = new Virus(blockMap);
            v.setPosition(vir[i].x, vir[i].y);
            enemies.add(v);
        }

    }

    private void makeWinner(){

        winners = new ArrayList<Winner>();

        Win w;
        Point[] win = new Point[]{
                new Point(3495, 200),
        };

        for (int i = 0; i < win.length; i++) {
            w = new Win(blockMap);
            w.setPosition(win[i].x, win[i].y);
            winners.add(w);
        }
    }

    ////////////////////////////////////////////////////////////////////////////////////
    /////////////////////////////////// Update /////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////
    public void update() {

        // check if player has fell
        if(player.gety() > blockMap.getHeight()) {
            fdeath = access = true;
        }

        // play events
        if( gcontinue) {
            start();}
        if(fdeath) {
            fallen();
        }

        // update player
        player.update();

        blockMap.setPosition(
                320 / 2 - player.getx(),
                240 / 2 - player.gety()
        );

        dead();
        winner();

        // set background
        bg.setPosition(blockMap.getX(), blockMap.getY());

        // attack enemies
        player.checkAttack(enemies);

        // update all enemies
        for (int i = 0; i < enemies.size(); i++) {
            Enemy e = enemies.get(i);
            e.update();
            if (e.getDead()) {
                enemies.remove(i);
                i--;
            }
        }

        player.checkWon(winners);
        for (int i = 0; i < winners.size(); i++) {
            Winner a = winners.get(i);
            a.update();
        }

    }

    ////////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////// Draw /////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////
    public void draw(Graphics2D g) {

        // draw bg
        bg.draw(g);

        // draw map
        blockMap.draw(g);

        // draw player
        player.draw(g);

        // draw enemies
        for (int i = 0; i < enemies.size(); i++) {
            enemies.get(i).draw(g);
        }

        // draw enemies
        for (int i = 0; i < winners.size(); i++) {
            winners.get(i).draw(g);
        }

        // draw hud
        hb.draw(g);

    }

    public void keyPressed(int k) {
        if (k == KeyEvent.VK_A) player.setLeft(true);
        if (k == KeyEvent.VK_D) player.setRight(true);
        if (k == KeyEvent.VK_W) player.setJump(true);
        if (k == KeyEvent.VK_K) player.shootSanitiser();
        if (k == KeyEvent.VK_L) player.shootMask();
    }

    public void keyReleased(int k) {
        if (k == KeyEvent.VK_A) player.setLeft(false);
        if (k == KeyEvent.VK_D) player.setRight(false);
        if (k == KeyEvent.VK_W) player.setJump(false);
    }

    ////////////////////////////////////////////////////////////////////////////////////
    //////////////////////////////// Reset on fall /////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////

    // level reset on fall
    private void fallreset() {
        count = 0;
        player.fallreset();
        player.setPosition(50, 200);
        populateEnemies();
        access = true;
        gcontinue = true;
        start();
    }

    // level started
    private void start() {
        count++;
        if(count == 1) {
            ssize.clear();
            ssize.add(new Rectangle(d, 320 / 2, 320, 240 / 2));
            ssize.add(new Rectangle(320 / 2, d, 320 / 2, 240));
            ssize.add(new Rectangle(d, d, 320, 240 / 2));
            ssize.add(new Rectangle(d, d, 320 / 2, 240));
        }
        if(count == 30){
            if(count == 60) {
                count = 0;
                gcontinue = access = false;
                ssize.clear();
            }
    }
    }

    // player has fallen
    private void fallen() {
        count++;
        if(count == 1) {
            player.health = 0;
        }
        if(count == 60) {
            ssize.clear();
            ssize.add(new Rectangle(
                    320 / 2, 240 / 2, 0, 0));
        }
        fdeath = access = false;
        count = 0;
        fallreset();
    }

}
