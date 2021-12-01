import java.util.ArrayList;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;

public class Player extends GameObject {


    ////////////////////////////////////////////////////////////////////////////////////
    /////////////////////////////// PC variables  //////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////

    // Health
    public static int health;


    private int maxHealth;

    // When hit
    private boolean flinching;
    private long flinchTimer;
    public static boolean dead;
    public static boolean winz;
    public int rev;

    ////////////////////////////////////////////////////////////////////////////////////
    /////////////////////////////// PC attacks  ////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////

    //Arrays
    private ArrayList<Sanitiser> sanitisers; // Creating sanitiser array
    private ArrayList<Mask> masks;  // Creating mask away

    // Attack variables
    //Sanitiser
    private boolean shootSanitiser;
    private int sanitiserDamage;

    //Mask
    private boolean shootMask;
    private int maskDamage;


    ////////////////////////////////////////////////////////////////////////////////////
    /////////////////////////////// PC animations  /////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////

    private ArrayList<BufferedImage[]> PCGraphics; // Creating array to hold animations in

    // Specifying which animation links to which action
    private static final int IDLE = 0;
    private static final int WALKING = 1;
    private static final int JUMPING = 2;
    private static final int SANITISER = 2;
    private static final int MASK = 2;

    // Specifying number of frames in each animation
    private final int[] numberOfFrames = {
            2, 2, 1,
    };

    ////////////////////////////////////////////////////////////////////////////////////
    /////////////////////////////// Creating PC  ///////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////

    public Player(BlockMap bm) {
        super(bm);

        // Dimensions of the PC - corresponds to the size of animation PNG
        width = 30;
        height = 30;
        boxWidth = 20;
        boxHeight = 20;

        // Specifying the speed of each PC action
        move = 0.5;
        maxMove = 1.6;
        stopMove = 0.4;
        fall = 0.10;
        jump = -4.8;
        stopJump = 0.3;

        // load player graphics
        try {
            BufferedImage loadPCGraphics = ImageIO.read(getClass().getResourceAsStream("Images/playercharacter.gif"));

            PCGraphics = new ArrayList<BufferedImage[]>();
            for (int i = 0; i < 3; i++) {

                BufferedImage[] pc =
                        new BufferedImage[numberOfFrames[i]];

                for (int z = 0; z < numberOfFrames[i]; z++) {
                    pc[z] = loadPCGraphics.getSubimage(z * width, i * height, width, height);
                }

                PCGraphics.add(pc);
            }
        } catch (Exception e) { // if cannot load
            e.printStackTrace();
            System.out.println("Error getting the player graphics");
        }

        //Initial values
        Right = true;

        //Health bar
        health = maxHealth = 3;

        // Mask attack
        maskDamage = 5;
        masks = new ArrayList<Mask>();

        // Sanitiser attack
        sanitiserDamage = 5;
        sanitisers = new ArrayList<Sanitiser>();

        // Setting default animation as the idle animation
        animation = new Animation();
        action = IDLE;
        animation.aniArray(PCGraphics.get(IDLE));
        animation.delay(2000);

    }

    // Grab the players health
    public int health() {
        return health;
    }

    // Grab the players max health
    public int maxHealth() {
        return maxHealth;
    }

    // Attack 1
    public void shootSanitiser() {
        shootSanitiser = true;
    }

    // Attack 2
    public void shootMask() {
        shootMask = true;
    }


    public void checkAttack(ArrayList<Enemy> enemies) {
        // loop through enemies
        for (int i = 0; i < enemies.size(); i++) {
            Enemy en = enemies.get(i);

            // sanitisers
            for (int j = 0; j < sanitisers.size(); j++) {
                if (sanitisers.get(j).inter(en)) {
                    en.hit(sanitiserDamage);
                    sanitisers.get(j).whenHit();
                    break;
                }
            }

            // masks
            for (int j = 0; j < masks.size(); j++) {
                if (masks.get(j).inter(en)) {
                    en.hit(maskDamage);
                    masks.get(j).whenHit();
                    break;
                }
            }

            // check enemy collision
            if (inter(en)) {
                hit(en.getDamage());
            }

        }


    }

    public void checkWon(ArrayList<Winner> winners) {
        // loop through enemies
        for (int i = 0; i < winners.size(); i++) {
            Winner wn = winners.get(i);

            // when player touches winpoint
            if (inter(wn)) {
                touch(wn.getWon());
            }
        }
    }
    // When player is hit

    public void hit(int pain) {
        if (flinching) {
            return;
        } else {
            health -= pain;
            if (health < 0) health = 0;
            if (health == 0) dead = true;

            flinching = true;
            flinchTimer = System.nanoTime();
        }
    }

    public void touch(int rev) {
        if (rev == 0) {
            winz = true;
        }
    }

    //Controlling PC movement
    private void position() {
        if (left) {
            vex -= move;
            if (vex < -maxMove) {
                vex = -maxMove;
            }
        }

        if (right) {
            vex += move;
            if (vex > maxMove) {
                vex = maxMove;
            }
        } else {
            if (vex < 0) {
                vex += stopMove;
                if (vex > 0) {
                    vex = 0;
                }
            }
            if (vex > 0) {
                vex -= stopMove;
                if (vex < 0) {
                    vex = 0;
                }
            }
        }

        // jumping
        if (jumping) {
            if (!falling) {
                vey = jump;
                falling = true;
            }
        }

        // falling
        if (falling) {
            vey += fall;

            if (vey > 0) {
                jumping = false;
            }

            if (vey < 0) {
                if (!jumping) {
                    vey += move;
                }
            }
        }

    }

    public void fallreset() {
        health = maxHealth;
        Right = true;
        action = -1;
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
        // Update positions
        position();
        checkMapCollision();
        setPosition(x2, y2);

        // Stop attacks once played
        if (action == SANITISER) {
            if (animation.played()) {
                shootSanitiser = false;
            }
        }

        if (action == MASK) {
            if (animation.played()) {
                shootMask = false;
            }
        }


        // sanitiser attack
        if (shootSanitiser) {
            if (action != SANITISER) {
                Sanitiser s = new Sanitiser(blockMap, Right);
                s.setPosition(x, y);
                sanitisers.add(s);
            }
        }

        if (shootMask) {
            if (action != MASK) {
                Mask m = new Mask(blockMap, Right);
                m.setPosition(x, y);
                masks.add(m);
            }
        }

        // Sanitiser
        for (int i = 0; i < sanitisers.size(); i++) {
            sanitisers.get(i).update();
            if (sanitisers.get(i).afterHit()) {
                sanitisers.remove(i);
                i--;
            }
        }
        // Mask
        for (int i = 0; i < masks.size(); i++) {
            masks.get(i).update();
            if (masks.get(i).afterHit()) {
                masks.remove(i);
                i--;
            }
        }

        // stop flinching
        if (flinching) {
            long elapsed =
                    (System.nanoTime() - flinchTimer) / 1000000;
            if (elapsed > 1000) {
                flinching = false;
            }
        }

        // set animations
        if (shootSanitiser) {
            if (action != SANITISER) {
                action = SANITISER;
                animation.aniArray(PCGraphics.get(SANITISER));
                animation.delay(100);
                width = 30;
            }
        } else if (shootMask) {
            if (action != MASK) {
                action = MASK;
                animation.aniArray(PCGraphics.get(MASK));
                animation.delay(100);
                width = 30;
            }
        } else if (vey < 0) {
            if (action != JUMPING) {
                action = JUMPING;
                animation.aniArray(PCGraphics.get(JUMPING));
                animation.delay(-1);
                width = 30;
            }
        } else if (left) {
            if (action != WALKING) {
                action = WALKING;
                animation.aniArray(PCGraphics.get(WALKING));
                animation.delay(100);
                width = 30;
            }
        } else if (right) {
            if (action != WALKING) {
                action = WALKING;
                animation.aniArray(PCGraphics.get(WALKING));
                animation.delay(100);
                width = 30;
            }
        } else {
            if (action != IDLE) {
                action = IDLE;
                animation.aniArray(PCGraphics.get(IDLE));
                animation.delay(2000);
                width = 30;
            }
        }

        animation.update();

        // set direction
        if (action != SANITISER) {
            if (action != MASK) {
                if (right) Right = true;
                if (left) Right = false;
            }
        }

    }

    ////////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////// Draw /////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////
    public void draw(Graphics2D g) {

        setMapPosition();

        // draw player
        if (flinching) {
            long elapsed = (System.nanoTime() - flinchTimer) / 1000000;
            if (elapsed / 100 % 2 == 0) {
                return;
            }
        }

        // draw sanitisers
        for (int i = 0; i < sanitisers.size(); i++) {
            sanitisers.get(i).draw(g);
        }

        // draw masks
        for (int i = 0; i < masks.size(); i++) {
            masks.get(i).draw(g);
        }

        super.draw(g);
    }
}

