import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.*;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Scanner;

public class Quiz extends State {


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
    private String[] quiz = {

    };

    private String[] questionOne = {
            "Wearing a mask",
            "Using hand sanitiser",
            "Social distancing",
            "All of the above"
    };

    private String[] questionTwo = {
            "From eating out-of-date food",
            "From drinking dirty water",
            "Through particles from your mouth and nose",
            "All of the above"
    };

    private String[] questionThree = {
            "Inside public buildings",
            "On public transport",
            "On private hire vehicles such as taxis",
            "All of the above"
    };

    private String[] questionFour = {
            "A new, continuous cough",
            "A loss or change to your sense of smell or taste",
            "A high temperature",
            "All of the above"
    };

    private String[] questionFive = {
            "They may reduce the spread of COVID-19 particles",
            "They are fashionable",
            "Everybody else is wearing them",
            "All of the above"
    };

    private String[] finished = {
            "Retake Quiz?",
            "Return to menu"
    };

    int loop = 0;

    int score = 0;


    ////////////////////////////////////////////////////////////////////////////////////
    //////////////////////////////// Creating Quiz ///////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////

    public Quiz(Manager m) {

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

        } catch (Exception e) { //If loading fails
            e.printStackTrace();
            System.out.println("Error loading about Quiz graphics.");
            System.exit(0);
        }

    }

    ////////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////// Menu /////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////
    // Selecting button to go back to the main menu
    private void click() {
        if (loop == 0) {
            if (option == 0) {
                loop = 1;
            }
            if (option == 1) {
                loop = 1;
            }
            if (option == 2) {
                loop = 1;
            }
            if (option == 3) {
                loop = 1;
                score = score + 1;
            }
        } else if (loop == 1) {
            if (option == 0) {
                loop = 2;
            }
            if (option == 1) {
                loop = 2;

            }
            if (option == 2) {
                loop = 2;
                score = score + 1;
            }
            if (option == 3) {
                loop = 2;
            }
        } else if (loop == 2) {
            if (option == 0) {
                loop = 3;
            }
            if (option == 1) {
                loop = 3;

            }
            if (option == 2) {
                loop = 3;
            }
            if (option == 3) {
                loop = 3;
                score = score + 1;
            }
        } else if (loop == 3) {
            if (option == 0) {
                loop = 4;
            }
            if (option == 1) {
                loop = 4;

            }
            if (option == 2) {
                loop = 4;
            }
            if (option == 3) {
                loop = 4;
                score = score + 1;
            }
        } else if (loop == 4) {
            if (option == 0) {
                loop = 5;
                score = score + 1;
            }
            if (option == 1) {
                loop = 5;

            }
            if (option == 2) {
                loop = 5;
            }
            if (option == 3) {
                loop = 5;
            }
        } else if (loop == 5) {
            if (option == 0) {
                iscore = score;
                score();

                loop = 0;
                score = 0;
            }
            if (option == 1) {
                iscore = score;
                score();

                m.setState(Manager.MENU);
            }
        }
    }

    ////////////////////////////////////////////////////////////////////////////////////
    /////////////////////////////////// Scores /////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////
    public void score() {
        if (loop == 5) {
            if (score != 0) {
                setScores();
            }
        }
    }

    int iscore;
    String name;
    int lb = 0;
    JTextField jtextfield;
    ArrayList<String> nameList = new ArrayList<String>();
    ArrayList<Integer> scoreList = new ArrayList<Integer>();

    public void setScores() {

        JFrame f = new JFrame();
        JPanel topPanel = new JPanel();
        JPanel centerPanel = new JPanel();
        JPanel bottomPanel = new JPanel();

        jtextfield = new JTextField(6);

        JLabel jLabel = new JLabel("Enter name");

        JButton jbutton = new JButton("Submit");

        topPanel.add(jLabel);
        centerPanel.add(jtextfield);
        bottomPanel.add(jbutton);
        f.add(topPanel, BorderLayout.NORTH);
        f.add(centerPanel, BorderLayout.CENTER);
        f.add(bottomPanel, BorderLayout.SOUTH);

        //show JFrame
        f.setTitle("Enter name");
        f.setResizable(false);
        f.pack();
        f.setVisible(true);

        jbutton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                name = jtextfield.getText();
                nameList.add(name);
                scoreList.add(iscore);

                try {
                    getScores();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }

                PrintWriter write = null;
                try {
                    write = new PrintWriter("src/leaderboard.txt");
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }

                for (int i = 0; i < nameList.size(); ++i) {
                    write.print(nameList.get(i) + " ");
                    write.println(scoreList.get(i));
                }
                write.close();


            }
        });
    }

    public void getScores() throws FileNotFoundException {
        try {
            Scanner scanner = new Scanner(new FileReader("leaderboard.txt"));
            while (scanner.hasNext()) {
                nameList.add(lb, scanner.next());
                scoreList.add(lb, scanner.nextInt());
                lb++;
            }
        } catch (IOException exp) {
            exp.printStackTrace();
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
        g.drawString("Quiz", 130, 70);

        g.setFont(textFont);


        String one1 = "What is the best way to avoid catching";
        String one2 = "or spreading COVID-19?";

        String two = "How does COVID-19 spread?";
        String three = "In the UK, where must face masks be worn?";
        String four = "What are the common COVID-19 symptoms?";
        String five = "Why is it important to wear a mask?";

        if (loop == 0) {
            drawString(g, one1 + "\n" + one2, 60, 80);
            quiz = questionOne;
        } else if (loop == 1) {
            g.drawString(two, 80, 95);
            quiz = questionTwo;

        } else if (loop == 2) {
            g.drawString(three, 40, 95);
            quiz = questionThree;
        } else if (loop == 3) {
            g.drawString(four, 40, 95);
            quiz = questionFour;
        } else if (loop == 4) {
            g.drawString(five, 60, 95);
            quiz = questionFive;
        } else if (loop == 5) {
            g.drawString("Congratulations!", 112, 95);
            quiz = finished;
            String score2 = Integer.toString(score);
            g.drawString("You have completed the COVID-19 quiz!", 50, 115);
            g.drawString("Your score is " + score2 + "/5", 112, 135);

        }

        for (int i = 0; i < quiz.length; i++) {
            if (i != option) {
                g.setColor(Color.WHITE);
            } else {
                g.setColor(Color.RED);
            }
            if (!(loop == 5)) {
                g.drawString(quiz[i], 20, 140 + i * 15);
            } else if (loop == 5) {
                g.drawString(quiz[i], 115, 180 + i * 15);
            }
        }

    }


    ////////////////////////////////////////////////////////////////////////////////////
    //////////////////////////////// Key Events ////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////
    @Override
    public void keyPressed(int k) {
        if (k == KeyEvent.VK_ENTER) {
            click();
        }

        // If option goes below 0, go back to 3
        if (k == KeyEvent.VK_UP || k == KeyEvent.VK_W) {
            option--;
            if (!(loop == 5)) {
                if (option == -1) {
                    option = 3;
                }
            } else {
                if (option == -1) {
                    option = 1;
                }
            }

        }

        // If option goes higher than 3, go back to 0
        if (k == KeyEvent.VK_DOWN || k == KeyEvent.VK_S) {
            option++;
            if (option == quiz.length) {
                option = 0;
            }
        }
    }

    @Override
    public void keyReleased(int k) {

    }
}