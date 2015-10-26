package Controller;

import Model.Puzzle;
import View.MainSound;
import View.MineSweeperFrame;


/**
 * Created by Dave on 11/02/2015.
 */

public class Game {
    private Puzzle puzzle;
    private MineSweeperFrame mainFrame;
    private GameNotifier gameNotifier;
    private int rows,cols,mines,lives;
    private int squaresPushed, dolphinsEaten,buoysPushed,gamesWon,gamesLost;

    public Game(int rows, int cols, int mines, int lives, MainSound sounds) {
        this.rows = rows;this.cols = cols;this.mines = mines;this.lives = lives;
        gameNotifier = new GameNotifier(this);
        puzzle = new Puzzle(rows, cols, mines,gameNotifier,lives);
        mainFrame = new MineSweeperFrame(rows, cols, gameNotifier,lives,mines,sounds);
        puzzle.printPuzzle();
    }

    /*
      methods called by gameNotifier (observer)
     */

    public void newGame(){
        puzzle = new Puzzle(rows,cols,mines,gameNotifier,lives);
        mainFrame.newGame(lives,puzzle.getNMines());
    }
    public void squarePushed(int id,boolean flag){
        squaresPushed++;
        puzzle.squarePushed(id,flag);
    }
    public void gameWon() {
        gamesWon++;
        mainFrame.gameWon(puzzle.getSquareNumbers(),puzzle.getBombList(),mines);
    }
    public void squarePush(int id, int surroundingMines,boolean isBomb) {
        mainFrame.squarePushed(id,surroundingMines,isBomb);
    }
    public void flagSquare(int id){
        buoysPushed++;
        mainFrame.flagSquare(id);
    }
    public void setSquareAsNull(int id){
        mainFrame.unFlagSquare(id);
    }
    public void playerDied(int id) {
        gamesLost++;
        mainFrame.playerDied(puzzle.getSquareNumbers(),puzzle.getBombList(),mines);}
    public void minePushed(int id){
        dolphinsEaten++;
        mainFrame.minePushed(id);}
    public void helpPushed(){mainFrame.helpPushed();}
    public void statsPushed(){mainFrame.statsPushed(squaresPushed,dolphinsEaten,buoysPushed,gamesWon,gamesLost);}
}

