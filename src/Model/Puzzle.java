package Model;

import java.util.*;


/**
 * Created by Dave on 06/02/2015.
 */
public class Puzzle {
    private PuzzleObserver observer;
    private Map<Integer,Square> ids;
    private Integer[] bombList;
    private int rows,cols,mines,flags, lives;
    private boolean gameOver;

    public Puzzle(int rows, int columns, int mines,PuzzleObserver observer, int lives){
        if(rows < 1 || columns < 1 || mines < 1 || mines > (rows * 10) + columns){
            throw new IndexOutOfBoundsException("Puzzle out of bounds");
        }
        this.flags = mines;
        this.mines = mines;
        this.rows = rows;
        this.cols = columns;
        this.lives=lives;
        this.observer = observer;
        this.gameOver=false;
        ids = new HashMap<Integer, Square>(rows*columns);
        Set<Integer> bombs = declareBombs(mines);
        bombList = bombs.toArray(new Integer[0]);

        initializeSquare(rows,columns);
        initializeBombs(bombList,mines);
        initializeNumbers(bombList,mines);
    }


    //creates squares and puts them in map
    private void initializeSquare(int rows,int columns){
        int id=0;
        for(int r=0;r<rows;r++) {
            for (int c=0; c<columns; c++) {
                Square square = new Square(SquareState.UNTOUCHED,r,c,id,false);
                square.setLocation(markLocation(c));
                ids.put(id,square); id++;
            }
        }
    }

    public void squarePushed(int id,boolean flag){
        if(gameOver) return; //do nothing
        Square square = ids.get(id);
        SquareState state = square.getState();

        if(square.containsBomb() && !flag && state==SquareState.UNTOUCHED){ //untouched bomb
            square.setState(SquareState.EXPLODED);
            notifySquarePushed(id,square.getSurroundBombs(),true);
            lives--;
            mines--;
            notifyMinePushed(id);
        }
        else if(flag){ //putting flag
            if(state==SquareState.FLAGGED){ //flag pushed
                observer.notifyUntouched(id);
                square.setState(SquareState.UNTOUCHED);
                flags++;
                if(square.containsBomb()) mines++;
            }
            else if(state==SquareState.UNTOUCHED && flags>0){ //untouched square pushed
                observer.notifyFlagged(id);
                square.setState(SquareState.FLAGGED);
                flags--;
                if(square.containsBomb()) mines--;
            }
        }
        else if(state==SquareState.UNTOUCHED){ //normal sqaure pushed
            if(square.getSurroundBombs()==0) pressSquare(square);
            else if(square.getSurroundBombs() > 0) pressNumberSquare(square);
            notifySquarePushed(id,square.getSurroundBombs(),false);
        }
        if(mines==0) notifyGameWon();
        if(lives==0)notifyPlayerDied(id);
    }

    private void notifyMinePushed(int id){
        observer.notifyMinePushed(id);
    }

    private void notifyGameWon(){
        gameOver=true;
        observer.notifyGameWon();
    }

    private void notifySquarePushed(int id,int surroundingBombs,boolean isBomb){
            observer.squarePush(id,surroundingBombs,isBomb);
    }

    private void notifyPlayerDied(int id){
        gameOver=true;
        observer.playerDied(id);
    }

    private void pressSquare(Square square){
        if(square.getState()==SquareState.UNTOUCHED && !square.containsBomb() && square.getSurroundBombs() == 0){
            int id=square.getId();
            notifySquarePushed(id,square.getSurroundBombs(),false);
            square.setState(SquareState.PRESSED);

            //array of surrounding ids going clockwise from left. Reassigns if not body
            int[] surroundingSquares = {id-1,id-1-cols,id-cols,id+1-cols,id+1,id+1+cols,id+cols,id-1+cols};
            if(ids.get(id).getLocation()==SquareEdge.RIGHT) {
                int[] rightSquares = {id-1,id-1-cols,id-cols,id+cols,id-1+cols};
                surroundingSquares = rightSquares;
            }
            if(ids.get(id).getLocation()==SquareEdge.LEFT){
                int[] leftSquares = {id-cols,id+1-cols,id+1,id+1+cols,id+cols};
                surroundingSquares = leftSquares;
            }
            for(int n : surroundingSquares){
                try{
                    pressSquare(ids.get(n));
                }catch(NullPointerException e){
                    //do nothing
                }
            }
        }
        else if(square.getState()==SquareState.UNTOUCHED && square.getSurroundBombs() > 0){
            notifySquarePushed(square.getId(),square.getSurroundBombs(),false);
            square.setState(SquareState.PRESSED);
        }
    }

    //helper for if first square pushed is number
    private void pressNumberSquare(Square square) {
        notifySquarePushed(square.getId(), square.getSurroundBombs(), false);
        square.setState(SquareState.PRESSED);
        int id = square.getId();

        //array of surrounding ids going clockwise from left
        int[] surroundingSquares = {id-1,id-1-cols,id-cols,id+1-cols,id+1,id+1+cols,id +cols,id-1+cols};
        if(ids.get(id).getLocation()==SquareEdge.RIGHT) {
            int[] rightSquares = {id-1,id-1-cols,id-cols,id+cols,id-1+cols};
            surroundingSquares = rightSquares;
        }
        if(ids.get(id).getLocation()==SquareEdge.LEFT){
            int[] leftSquares = {id-cols,id+1-cols,id+1,id+1+cols,id+cols};
            surroundingSquares = leftSquares;
        }
        for (int n : surroundingSquares) {
            try {
                if (ids.get(n).getSurroundBombs() == 0)
                    pressSquare(ids.get(n));
            } catch (NullPointerException e) {
                //do nothing
            }
        }
    }

    /*
        startup puzzle
     */
    private SquareEdge markLocation(int col){
        if(col==0) return SquareEdge.LEFT;
        else if(col==cols-1) return SquareEdge.RIGHT;
        else return SquareEdge.BODY;
    }

    private void initializeNumbers(Integer[] bombList,int mines){
        Square square;
        for(int i=0;i<mines;i++){
            square = ids.get(bombList[i]);
            int id = square.getId();

            //array of surrounding ids going clockwise from left
            int[] surroundingSquares = {id-1,id-1-cols,id-cols,id+1-cols,id+1,id+1+cols,id +cols,id-1+cols};
            if(ids.get(id).getLocation()==SquareEdge.RIGHT) {
                int[] rightSquares = {id-1,id-1-cols,id-cols,id+cols,id-1+cols};
                surroundingSquares = rightSquares;
            }
            if(ids.get(id).getLocation()==SquareEdge.LEFT){
                int[] leftSquares = {id-cols,id+1-cols,id+1,id+1+cols,id+cols};
                surroundingSquares = leftSquares;
            }
            for (int n : surroundingSquares) {
                try {
                    ids.get(n).addSurroundingBomb();
                } catch (NullPointerException e) {
                    //do nothing
                }
            }

        }
    }
    //returns array of bomb ids
    private Set<Integer> declareBombs(int mines){
        Set<Integer> bombs = new HashSet<Integer>();
        int toAdd;
        while(bombs.size() < mines) {
            toAdd = (int) (Math.random() * ((rows * cols) - 1));
            bombs.add(toAdd);
        }
        return bombs;
    }


    //sets squares from bomb list to detonated
    private void initializeBombs(Integer[] bombs,int mines){
        for(int i=0;i<mines;i++){
           ids.get(bombs[i]).setAsContainingBomb();
        }
    }

    public void printPuzzle(){
        int numberSquares = rows*cols;
        for(int i=0;i<numberSquares;i++){
            if(i%cols==0)System.out.println("");
            if(ids.get(i).containsBomb()) System.out.print (" " + "x"+ " ");
            else System.out.print(" "+ ids.get(i).getSurroundBombs()+ " ");
        }
    }

    public Integer[] getSquareNumbers(){
        int totalSquares = rows*cols;
        Integer[] squareNumbers = new Integer[totalSquares];
        for(int i=0;i<totalSquares;i++){
            squareNumbers[i] = ids.get(i).getSurroundBombs();
        }
        return squareNumbers;
    }

    public Integer[] getBombList(){return bombList;}
    public int getNMines(){return mines;}


}
