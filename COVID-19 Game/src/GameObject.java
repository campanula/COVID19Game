import java.awt.*;

public abstract class GameObject {

    ////////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////// Variables ////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////

    // movement
    protected boolean left;
    protected boolean right;
    protected boolean up;
    protected boolean down;
    protected boolean jumping;
    protected boolean falling;
    protected double move;
    protected double maxMove;
    protected double stopMove;
    protected double fall;
    protected double maxFall;
    protected double jump;
    protected double stopJump;

    // animation
    protected Animation animation;
    protected int action;
    protected int prevAction;
    protected boolean Right;

    // Specs
    protected int width;
    protected int height;
    protected int boxWidth;
    protected int boxHeight;

    // Map
    protected BlockMap blockMap;
    protected int blockSize;
    protected boolean tLeft;
    protected boolean tRight;
    protected boolean bLeft;
    protected boolean bRight;

    // Positioning values
    protected double x;
    protected double y;
    protected double vex;
    protected double vey;

    // collision
    protected int row;
    protected int col;
    protected double xmap;
    protected double ymap;
    protected double xdest;
    protected double ydest;
    protected double x2;
    protected double y2;

    ////////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////// Creating GameObject ////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////
    public GameObject(BlockMap bm) {
        blockMap = bm;
        blockSize = bm.getblockSize();
    }

    public int getx() { return (int)x; }
    public int gety() { return (int)y; }
    public int getWidth() { return width; }
    public int getHeight() { return height; }

    public void setPosition(double x, double y) {
        this.x = x;
        this.y = y;
    }
    public void setVector(double vex, double vey) {
        this.vex = vex;
        this.vey = vey;
    }

    public void setMapPosition() {
        xmap = blockMap.getX();
        ymap = blockMap.getY();
    }

    public void setLeft(boolean l) { left = l; }
    public void setRight(boolean r) { right = r; }
    public void setUp(boolean u) { up = u; }
    public void setDown(boolean d) { down = d; }
    public void setJump(boolean j) { jumping = j; }

    public Rectangle rect() {
        return new Rectangle((int)x - boxWidth, (int)y - boxHeight, boxWidth, boxHeight);
    }

    public boolean inter(GameObject r) {
        Rectangle r1 = rect();
        Rectangle r2 = r.rect();
        return r1.intersects(r2);
    }

    public void mapBounds(double x, double y) {
        int left = (int)(x - boxWidth / 2) / blockSize;
        int right = (int)(x + boxWidth / 2 - 1) / blockSize;
        int top = (int)(y - boxHeight / 2) / blockSize;
        int bottom = (int)(y + boxHeight / 2 - 1) / blockSize;
        if(top < 0) {
            tLeft = false;
            tRight = false;
            bLeft = false;
            bRight = false;
            return;
        }
        if(bottom >= blockMap.getNumberOfRows()){
            tLeft = false;
            tRight = false;
            bLeft = false;
            bRight = false;
            return;
        }
        if(left < 0) {
            tLeft = false;
            tRight = false;
            bLeft = false;
            bRight = false;
            return;
        }
        if(right >= blockMap.getNumberOfColumns()) {
            tLeft = false;
            tRight = false;
            bLeft = false;
            bRight = false;
            return;
        }
        int tleft = blockMap.getType(top, left);
        int tright = blockMap.getType(top, right);
        int bleft = blockMap.getType(bottom, left);
        int bright = blockMap.getType(bottom, right);


        tLeft = tleft == Block.BLOCKED;
        tRight = tright == Block.BLOCKED;
        bLeft = bleft == Block.BLOCKED;
        bRight = bright == Block.BLOCKED;
    }

    public void checkMapCollision() {

        col = (int)x / blockSize;
        row = (int)y / blockSize;

        xdest = x + vex;
        ydest = y + vey;

        x2 = x;
        y2 = y;

        mapBounds(x, ydest);
        if(vey < 0) {
            if(tLeft) {
                vey = 0;
                y2 = row * blockSize + boxHeight / 2;
            }
            if(tRight) {
                vey = 0;
                y2 = row * blockSize + boxHeight / 2;
            }
            else {
                y2 += vey;
            }
        }
        if(vey > 0) {
            if(bLeft) {
                vey = 0;
                falling = false;
                y2 = (row + 1) * blockSize - boxHeight / 2;
            }
            if(bRight) {
                vey = 0;
                falling = false;
                y2 = (row + 1) * blockSize - boxHeight / 2;
            }
            else {
                y2 += vey;
            }
        }

        mapBounds(xdest, y);
        if(vex < 0) {
            if(tLeft) {
                vex = 0;
                x2 = col * blockSize + boxWidth / 2;
            }
            if(bLeft) {
                vex = 0;
                x2 = col * blockSize + boxWidth / 2;
            }
            else {
                x2 += vex;
            }
        }
        if(vex > 0) {
            if(tRight) {
                vex = 0;
                x2 = (col + 1) * blockSize - boxWidth / 2;
            }
            if(bRight) {
                vex = 0;
                x2 = (col + 1) * blockSize - boxWidth / 2;
            }
            else {
                x2 += vex;
            }
        }

        if(!falling) {
            mapBounds(x, ydest + 1);
            if(!bLeft) {
                if(!bRight) {
                    falling = true;
                }
            }
        }

    }



    ////////////////////////////////////////////////////////////////////////////////////
    /////////////////////////////////// Draw //////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////
    public void draw(Graphics2D g){{
        if(!Right) {
            g.drawImage(
                    animation.ani(),
                    (int)(x + xmap - width / 2 + width),
                    (int)(y + ymap - height / 2),
                    -width,
                    height,
                    null
            );

        }
        else if(Right){
            g.drawImage(
                    animation.ani(),
                    (int)(x + xmap - width / 2),
                    (int)(y + ymap - height / 2),
                    null
            );
        }

    }
    }

}