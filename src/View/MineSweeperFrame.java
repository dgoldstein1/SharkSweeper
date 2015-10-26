package View;


import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * Created by Dave on 06/02/2015.
 */

public class MineSweeperFrame extends JFrame {
    private ControlPanel controlPanel;
    private PuzzlePanel puzzlePanel;
    private SharkPanel sharkPanel;
    private Container contentPane;
    private MainSound sounds;

    public MineSweeperFrame(int rows, int columns, final MouseListener listener, int lives,int mines, MainSound sounds) {
        super("Dave's SharkSweeper (" + rows + " x " + columns + ")");
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
        setPreferredSize(new Dimension(600, 710));
        setLayout(new BorderLayout());
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                setVisible(false);
                dispose();
            }
        });
        this.sounds = sounds;
        initPanels(rows,columns,listener,lives,mines);
        pack();
        setVisible(true);
    }


    /*
        internal methods
    */
    private void fillEntireBoard(Integer[] numbers, Integer[] bombs, int mines) {
        puzzlePanel.fillEntireBoard(numbers, bombs, mines);
    }

    private void clearBoard() {
        puzzlePanel.clearBoard();
    }

    private void initPanels(int rows,int columns,MouseListener listener,int lives,int mines){
        controlPanel = new ControlPanel(listener, lives,mines, sounds);
        controlPanel.setSize(controlPanel.getMinimumSize());
        puzzlePanel = new PuzzlePanel(rows, columns, listener, sounds);
        puzzlePanel.setSize(puzzlePanel.getMinimumSize());
        sharkPanel = new SharkPanel();
        contentPane = getContentPane();
        contentPane.add(controlPanel, BorderLayout.NORTH);
        contentPane.add(puzzlePanel, BorderLayout.SOUTH);
        contentPane.add(sharkPanel);
    }

    /*
        methods called by game
     */


    public void playerDied(Integer[] numbers, Integer[] bombs, int mines) {
        sharkPanel.setPicture(SharkPictureType.SHARK);
        sounds.playEffect("Sounds/scream1.wav");
        new PopUpFrame("<html>You have been eaten by a Shark!<br><br>You have failed the dolphins :( </html>", 250, 175);
        fillEntireBoard(numbers, bombs, mines);
    }

    public void newGame(int lives,int mines) {
        clearBoard();
        controlPanel.reset(lives,mines);
        sharkPanel.setPicture(SharkPictureType.BACKGROUND);
    }

    public void gameWon(Integer[] numbers, Integer[] bombs, int mines) {
        sharkPanel.setPicture(SharkPictureType.DOLPHIN);
        int n = (int) Math.random() * 3;
        sounds.playEffect("Sounds/dolphin" + n + ".wav");
        new PopUpFrame("<html>You have saved all the Dolphins!<br><br>They thank you for your valiant efforts </html>", 250, 175);
        fillEntireBoard(numbers, bombs, mines);
    }

    public void squarePushed(int id, int surroundingMines, boolean isBomb) {
        puzzlePanel.setSquare(id, surroundingMines, isBomb);
    }

    public void flagSquare(int id){
        controlPanel.addFlag();
        sounds.playEffect("Sounds/buoy.wav");
        puzzlePanel.flagSquare(id);
    }

    public void unFlagSquare(int id) {
        controlPanel.takeAwayFlag();
        puzzlePanel.setSquareAsNull(id);
    }

    public void minePushed(int id) {
        puzzlePanel.setSquare(id, -1, true);
        controlPanel.minePushed();
        int n = (int) (Math.random() * 5);
        sounds.playEffect("Sounds/scream" + n + ".wav");
    }
    public void helpPushed(){
        controlPanel.helpPushed();
    }
    public void statsPushed(int squaresPushed, int sharksPushed, int buoysPushed, int gamesWon,int gamesLost) {
        controlPanel.statsPushed(squaresPushed,sharksPushed,buoysPushed,gamesWon,gamesLost);
    }


}