package Controller;


import Model.PuzzleObserver;
import View.PuzzleButton;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Created by Dave on 17/02/2015.
 * detects changes from puzzle and notifies Game
 */
public class GameNotifier extends MouseAdapter implements PuzzleObserver {
    Game game;
    GameNotifier(Game game){
        this.game = game;
    }

    public void playerDied(int id){
        game.playerDied(id);
    }

    public void squarePush(int id,int surroundingMines,boolean isMine){
        game.squarePush(id,surroundingMines,isMine);
    }
    public void notifyFlagged(int id){
        game.flagSquare(id);
    }
    public void notifyUntouched(int id){
        game.setSquareAsNull(id);
    }
    public void notifyGameWon(){game.gameWon();}
    public void notifyMinePushed(int id){game.minePushed(id);}

    @Override
    public void mousePressed(MouseEvent e) {
        PuzzleButton button = (PuzzleButton) e.getComponent();
        String actionCommand = button.getType();

        //right click or control click or middle mouse button
        if (SwingUtilities.isRightMouseButton(e) ||
                e.isControlDown() ||
                SwingUtilities.isMiddleMouseButton(e)) {
            if(actionCommand.equals("square_button")){
                game.squarePushed(button.getId(),true);
            }
        }

        //left click
        else if (e.getButton() == MouseEvent.BUTTON1) {
            if(actionCommand.equals("square_button")){
                game.squarePushed(button.getId(),false);
            }
            else if (actionCommand.equals("reset_button")) {
                game.newGame();
            }
            else if (actionCommand.equals("stats_button")) {
                game.statsPushed();
            }
            else if (actionCommand.equals("help_button")) {
                game.helpPushed();
            }

        }


    }
}
