import java.awt.*;
import java.awt.image.*;
import java.io.*;
import javax.imageio.ImageIO;

public class BlockMap {

    ////////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////// Variables ////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////
    // block map
    private int[][] map;
    private int blockSize;
    private int width;
    private int height;
    private int numberOfRows;
    private int numberOfCols;
    private BufferedImage dataSet;
    private int  blockNumber;
    private Block[][] blocks;
    private double smooth;

    // map bounds
    private int xMinimum;
    private int xMaximum;

    private int yMinimum;
    private int yMaximum;

    // draw map
    private int rowPos;
    private int colPos;
    private int numberOfRowsToDraw;
    private int numberOfColsToDraw;

    // object position
    private double x;
    private double y;

    ////////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////// Creating BlockMap /////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////
    public BlockMap(int blockSize) {
        this.blockSize = blockSize;
        numberOfRowsToDraw = 240 / blockSize + 2;
        numberOfColsToDraw = 320 / blockSize + 2;
        smooth = 0.1;
    }

    public void getBlocks(String a) {

        try {
            dataSet = ImageIO.read(getClass().getResourceAsStream(a));

            blockNumber = dataSet.getWidth() / blockSize;
            blocks = new Block[2][ blockNumber];

            BufferedImage subimage;
            for(int col = 0; col < blockNumber; col++) {
                subimage = dataSet.getSubimage(col * blockSize, 0, blockSize, blockSize
                );
                blocks[0][col] = new Block(subimage, Block.NORMAL);
                subimage = dataSet.getSubimage(col * blockSize, blockSize, blockSize, blockSize
                );
                blocks[1][col] = new Block(subimage, Block.BLOCKED);
            }

        }
        catch(Exception e) {
            e.printStackTrace();
        }

    }

    public void getMap(String s) {

        try {

            InputStream in = getClass().getResourceAsStream(s);
            BufferedReader br = new BufferedReader(
                    new InputStreamReader(in)
            );

            numberOfCols = Integer.parseInt(br.readLine());
            numberOfRows = Integer.parseInt(br.readLine());
            map = new int[numberOfRows][numberOfCols];
            width = numberOfCols * blockSize;
            height = numberOfRows * blockSize;

            xMinimum = 320 - width;
            xMaximum = 0;
            yMinimum = 240 - height;
            yMaximum = 0;

            String delims = "\\s+";
            for(int row = 0; row < numberOfRows; row++) {
                String line = br.readLine();
                String[] tokens = line.split(delims);
                for(int col = 0; col < numberOfCols; col++) {
                    map[row][col] = Integer.parseInt(tokens[col]);
                }
            }

        }
        catch(Exception e) {
            e.printStackTrace();
        }

    }

    public int getblockSize() { return blockSize; }
    public double getX() { return (int)x; }
    public double getY() { return (int)y; }
    public int getWidth() { return width; }
    public int getHeight() { return height; }
    public int getNumberOfRows() { return numberOfRows; }
    public int getNumberOfColumns() { return numberOfCols; }

    public int getType(int row, int col) {
        int rc = map[row][col];
        int r = rc /  blockNumber;
        int c = rc %  blockNumber;
        return blocks[r][c].getType();
    }

    public void smoothing(double t) {
        smooth = t;
    }

    public void setPosition(double x, double y) {

        this.x += (x - this.x) * smooth;
        this.y += (y - this.y) * smooth;

        fixBounds();

        colPos = (int)-this.x / blockSize;
        rowPos = (int)-this.y / blockSize;

    }

    private void fixBounds() {
        if(x < xMinimum) x = xMinimum;
        if(y < yMinimum) y = yMinimum;
        if(x > xMaximum) x = xMaximum;
        if(y > yMaximum) y = yMaximum;
    }

    public void draw(Graphics2D g) {

        for(
                int row = rowPos;
                row < rowPos + numberOfRowsToDraw;
                row++) {

            if(row >= numberOfRows){
                break;
            }

            for(
                    int col = colPos;
                    col < colPos + numberOfColsToDraw;
                    col++) {

                if(col >= numberOfCols) {
                    break;
                }

                if(map[row][col] == 0) {
                    continue;
                }

                int rc = map[row][col];
                int r = rc /  blockNumber;
                int c = rc %  blockNumber;

                g.drawImage(
                        blocks[r][c].getImage(),
                        (int)x + col * blockSize,
                        (int)y + row * blockSize,
                        null
                );

            }

        }

    }

}