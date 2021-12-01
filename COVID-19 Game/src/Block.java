import java.awt.image.BufferedImage;

public class Block {
    private int t;
    private BufferedImage i;

    // Blocks
    public static final int BLOCKED = 1;
    public static final int NORMAL = 0;

    public BufferedImage getImage() {
        return i;
    }
    public int getType() {
        return t;
    }

    public Block(BufferedImage i, int t) {
        this.i = i;
        this.t = t;
    }
}
