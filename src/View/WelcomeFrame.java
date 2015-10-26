package View;

import Controller.Game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * Created by Dave on 10/03/2015.
 */
public class WelcomeFrame extends JFrame {
    Container contentPane;
    WelcomeControls welcomeControls;
    MainSound sounds;

    public WelcomeFrame(){
        super("Dave's SharkSweeper");
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
        setPreferredSize(new Dimension(300,350));
        setLayout(new BorderLayout());
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });

        sounds = new MainSound();
        welcomeControls = new WelcomeControls(sounds);
        contentPane = getContentPane();
        contentPane.add(welcomeControls,BorderLayout.CENTER);
        JLabel header = new JLabel("Copyright David Goldstein 2015, v. 1.0. For Sarah");
        header.setHorizontalAlignment(SwingConstants.CENTER);
        header.setFont(new Font("TimesRoman", Font.ITALIC, 10));
        add(header, BorderLayout.SOUTH);
        pack();
        setVisible(true);
    }
}
class WelcomeControls extends JPanel{
    JButton newGame,mute;
    StretchIcon muted,unMuted;
    JTextField rowsField,colsField,minesField;
    MainSound sounds;


    WelcomeControls(MainSound sounds){
        setLayout(new GridLayout(4,2));
        setBorder(BorderFactory.createLineBorder(Color.black));
        this.sounds = sounds;
        initButtons();
    }

    private void initButtons(){
        //rows
        JLabel rowsLabel = new JLabel("Enter Rows: ");
        rowsLabel.setHorizontalAlignment(JLabel.CENTER);
        rowsLabel.setDisplayedMnemonic(KeyEvent.VK_N);
        rowsLabel.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
        rowsField = new JTextField(20);
        rowsLabel.setLabelFor(rowsField);

        //cols
        JLabel colsLabel = new JLabel("Enter Columns: ");
        colsLabel.setHorizontalAlignment(JLabel.CENTER);
        colsLabel.setDisplayedMnemonic(KeyEvent.VK_N);
        colsLabel.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
        colsField = new JTextField(20);
        colsLabel.setLabelFor(colsField);

        //mine numbers
        JLabel minesLabel = new JLabel("Enter Sharks: ");
        minesLabel.setHorizontalAlignment(JLabel.CENTER);
        minesLabel.setDisplayedMnemonic(KeyEvent.VK_N);
        minesLabel.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
        minesField = new JTextField(20);
        minesLabel.setLabelFor(minesField);

        //mute button and game button
        newGame = new JButton("Create New Game");
        newGame.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                makeNewGame();
            }
        });
        newGame.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        mute = new JButton();
        mute.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        unMuted = new StretchIcon("icons/unmuted.png",false);
        muted = new StretchIcon("icons/muted.png",false);
        mute.setIcon(unMuted);
        mute.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(sounds.isMuted()){
                    sounds.unmute();
                    mute.setIcon(unMuted);
                    sounds.loopSound("Sounds/waves.wav");
                    sounds.loopSound("Sounds/piano/song0.wav");
                }
                else{
                    sounds.mute();
                    mute.setIcon(muted);
                }
            }
        });
        sounds.loopSound("Sounds/waves.wav");
        sounds.loopSound("Sounds/piano/song0.wav");


        this.add(rowsLabel);
        this.add(rowsField);
        this.add(colsLabel);
        this.add(colsField);
        this.add(minesLabel);
        this.add(minesField);
        this.add(mute);
        this.add(newGame);
    }
    private void makeNewGame(){
        int rows,cols,mines;
        int lives = 3;
        int upperLimit = 25;
        try {
            rows = Integer.parseInt(rowsField.getText());
            cols = Integer.parseInt(colsField.getText());
            mines = Integer.parseInt(minesField.getText());
            if(rows < 2 || rows > upperLimit ||
                cols < 2 || cols > upperLimit){
               throw new IndexOutOfBoundsException();
            }
            if(mines < 0 || mines > rows * cols){
                throw new Exception();
            }
            new Game(rows,cols,mines,lives,sounds);
        } catch (NumberFormatException e){
            new PopUpFrame("Input must be an Integer!");
        } catch (IndexOutOfBoundsException e){
            new PopUpFrame("Input must be in range 0-" + upperLimit + "!");
        }catch (NullPointerException e){
            new PopUpFrame("could not find resource");
        }catch (Exception e) {
            new PopUpFrame("Mines are not in valid range or you're entering something weird");
            e.printStackTrace();
        }

    }
}
