package Model;

/**
 * Created by Dave on 06/02/2015.
 */
public class Square {
    private int row,column;
    private Integer id,surroundingBombs;
    private SquareState currentState;
    private SquareEdge location;
    boolean detonated,onEdge;

    public Square(SquareState state, int row, int column, Integer id, boolean detonated){
        this.row=row;
        this.column = column;
        this.id=id;
        this.detonated = detonated;
        this.currentState=state;
        this.surroundingBombs=0;
        this.location = SquareEdge.BODY;
        this.onEdge=false;
    }



    /*
    getters and setters
     */
    public SquareState getState(){return currentState;}

    public void setLocation(SquareEdge location){
        this.location = location;
    }

    public SquareEdge getLocation(){
        return location;
    }

    public boolean isOnEdge() {
        return onEdge;
    }

    public void setOnEdge(boolean onEdge){
        this.onEdge = onEdge;
    }

    public void addSurroundingBomb(){
        surroundingBombs++;
    }

    public int getSurroundBombs() {return surroundingBombs;}

    public void setState(SquareState state){
        currentState = state;
    }

    public int getRow(){
        return row;
    }

    public int getColumn(){
        return column;
    }

    public Integer getId(){
        return id;
    }

    public void setAsContainingBomb(){
        detonated = true;
    }

    public boolean containsBomb(){
        return detonated;
    }

}
