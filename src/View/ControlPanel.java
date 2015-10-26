package View;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * Created by Dave on 10/02/2015.
 */
public class ControlPanel extends JPanel{
    private int lives,flags;
    private PuzzleButton resetButton,statsButton,helpButton;
    private JLabel livesLabel,flagsLabel;
    private MainSound sounds;

    public ControlPanel(final MouseListener listener, int lives,int flags,MainSound sounds){
        setLayout(new GridLayout(1,3));
        setBorder(BorderFactory.createLineBorder(Color.black));
        this.lives = lives;
        this.flags = flags;
        this.sounds = sounds;

        flagsLabel = new JLabel("Buoys Remaining: " + flags);
        livesLabel = new JLabel("Lives: " + lives);
        livesLabel.setHorizontalAlignment(SwingConstants.CENTER);
        flagsLabel.setHorizontalAlignment(SwingConstants.CENTER);
        resetButton = new PuzzleButton("RESET",-1,"reset_button");
        statsButton = new PuzzleButton("STATS",-1,"stats_button");
        helpButton = new PuzzleButton("HELP",-1,"help_button");

        resetButton.addMouseListener(listener);
        statsButton.addMouseListener(listener);
        helpButton.addMouseListener(listener);
        resetButton.setActionCommand("reset_pushed");
        statsButton.setActionCommand("stats_pushed");
        helpButton.setActionCommand("help_pushed");

        this.add(flagsLabel);
        this.add(resetButton);
        this.add(statsButton);
        this.add(helpButton);
        this.add(livesLabel);
    }

    //paints this Panel
    public void paintComponent(Graphics g){
        super.paintComponent(g);
    }

    public void minePushed(){
        if(lives>0)lives--;
        livesLabel.setText("Lives: " + lives);
    }
    public void addFlag(){
        flags--;
        flagsLabel.setText("Buoys Remaining: "+flags);
    }
    public void takeAwayFlag(){
        flags++;
        flagsLabel.setText("Buoys Remaining: "+flags);
    }
    public void reset(int lives,int mines){
        this.lives = lives;
        flags = mines;
        livesLabel.setText("Lives: " + lives);
        flagsLabel.setText("Buoys: " + flags);
    }
    public void helpPushed(){
        new ControlPopUp();
    }
    public void statsPushed(int squaresPushed, int sharksPushed, int buoysPushed, int gamesWon,int gamesLost) {
        new ControlPopUp(squaresPushed,sharksPushed,buoysPushed,gamesWon,gamesLost);}
}
class ControlPopUp extends JFrame{
    //helpFrame
    ControlPopUp(){
        super("HELP");
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }
        setPreferredSize(new Dimension(450,375));
        setLayout(new BorderLayout());
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                setVisible(false);
                dispose();
            }
        });
        getContentPane().add(new HelpPanel(),BorderLayout.CENTER);
        JLabel header = new JLabel("Welcome to Dave's SharkSweeper!");
        header.setHorizontalAlignment(SwingConstants.CENTER);
        JButton ok = new JButton("ok");
        ok.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
                dispose();
            }
        });
        add(header, BorderLayout.NORTH);
        pack();
        setVisible(true);
    }


    ControlPopUp(int dolphinsPushed, int sharkPushed,int buoysDeployed, int gamesWon,int gamesLost){
        super("STATS");
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }
        setPreferredSize(new Dimension(350,250));
        setLayout(new BorderLayout());
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                setVisible(false);
                dispose();
            }
        });
        add(new StatsPanel(dolphinsPushed,sharkPushed,buoysDeployed,gamesWon,gamesLost));
        pack();
        setVisible(true);
    }

}
class HelpPanel extends JPanel{
    private JLabel tileLabel,sharkLabel,buoyLabel;
    HelpPanel(){
        setLayout(new GridLayout(3,2));
        setBorder(BorderFactory.createLineBorder(Color.black));

        createRow(sharkLabel,"<html> Protect the Dolphins from Sharks <br> which are hidden under the sea. <html>",new StretchIcon("icons/shark.png",true));
        createRow(tileLabel,"<html> Numbered tiles tells you how <br> many sharks are in surrounding tiles. <html>",new StretchIcon("icons/numbers/3.png",true));
        createRow(sharkLabel,"<html> Right / control-click to place buoys <br> on potential  sharks to alert the dolphins! <html>",new StretchIcon("icons/buoy.png",true));

    }
    //creates a label for game rule with Image to right
    private void createRow(JLabel label,String s,StretchIcon icon){
        label = new JLabel(s);
        label.setBorder(BorderFactory.createLineBorder(Color.black));
        label.setHorizontalAlignment(SwingConstants.CENTER);
        this.add(label);
        JLabel pic = new JLabel();
        pic.setIcon(icon);
        pic.setBorder(BorderFactory.createLineBorder(Color.black));
        this.add(pic);
    }
}

class StatsPanel extends JPanel{
    StatsPanel(int squaresPushed, int sharksPushed, int buoysPushed, int gamesWon,int gamesLost){
        setLayout(new GridLayout(5,2));
        setBorder(BorderFactory.createLineBorder(Color.black));
        createRow("Total squares pushed: ",squaresPushed);
        createRow("Total dolphins eaten by sharks: ",sharksPushed);
        createRow("Total buoys deployed: ",buoysPushed);
        createRow("Total games won: ",gamesWon);
        createRow("Total games lost: ",gamesLost);
    }

    //creates row with description of stat and stat on right side
    private void createRow(String s, int n){
        JLabel label = new JLabel(s);
        label.setBorder(BorderFactory.createLineBorder(Color.black));
        label.setHorizontalAlignment(SwingConstants.CENTER);
        this.add(label);
        JLabel stat = new JLabel("" + n);
        stat.setBorder(BorderFactory.createLineBorder(Color.black));
        stat.setHorizontalAlignment(SwingConstants.CENTER);
        this.add(stat);
    }
}
