package View;


import Model.SquareState;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseListener;

/**
 * Created by Dave on 10/02/2015.
 */
public class PuzzlePanel extends JPanel {
    private PuzzleButton[] squares;
    private int totalNSquares;
    private MainSound sounds;

    public PuzzlePanel(int rows, int cols,final MouseListener listener, MainSound sounds){
        setLayout(new GridLayout(rows,cols));
        setBorder(BorderFactory.createLineBorder(Color.black));
        setPreferredSize(new Dimension(400,400));
        this.sounds = sounds;
        totalNSquares = rows*cols;
        squares = new PuzzleButton[totalNSquares];
        setUpSquares(listener);
    }

    private void setUpSquares(MouseListener listener){
        for(int i=0;i<totalNSquares;i++){
            squares[i] = new PuzzleButton(i,"square_button", SquareState.UNTOUCHED);
            squares[i].addMouseListener(listener);
            add(squares[i]);
        }
    }


    //displays all squares on board
    public void fillEntireBoard(Integer[] numbers, Integer[] bombs, int mines){
        int i;
        for(i=0;i<totalNSquares;i++){
            squares[i].setState(SquareState.PRESSED,numbers[i]);
         }
        int id;
        for(i=0;i<mines;i++){
            id = bombs[i];
            squares[id].setState(SquareState.EXPLODED,-1);
        }
    }

    public void setSquare(int id,int surroundingMines,boolean mine){
        if(mine) squares[id].setState(SquareState.EXPLODED,-1);
        else{
            squares[id].setState(SquareState.PRESSED,surroundingMines);
        }
    }

    public void clearBoard(){
        for(PuzzleButton square:squares){
            square.setState(SquareState.UNTOUCHED,-1);
        }
    }
    public void flagSquare(int id){
        squares[id].setState(SquareState.FLAGGED,-1);
    }
    public void setSquareAsNull(int id){
        squares[id].setState(SquareState.UNTOUCHED,-1);
    }

}


