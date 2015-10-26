package View;

import Model.SquareState;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Dave on 18/02/2015.
 */
public class PuzzleButton extends JButton {
    private int id;
    private String type;

    public PuzzleButton(int id,String type, SquareState state){
        this.type = type;
        this.id = id;
        setState(state,-1);
        setBorder(BorderFactory.createLineBorder(Color.BLACK));
        setBorderPainted(false);
        setMargin(new Insets(0, 0, 0, 0));
    }

    public PuzzleButton(String s,int id,String type){
        setText(s);
        this.id=id;
        this.type = type;
    }

    public void setState(SquareState state,int number){
        if(state == SquareState.UNTOUCHED){
            int n = (int)(Math.random() * 4) + 1;
            setIcon(new StretchIcon("icons/untouched/untouched"+n+".png",false));
        }
        else if(state == SquareState.FLAGGED){
            setIcon(new StretchIcon("icons/buoy.png",false));
            setBorder(BorderFactory.createLoweredBevelBorder());
        }
        else if(state == SquareState.EXPLODED){
            setIcon(new StretchIcon("icons/shark.png",false));
            setBorder(BorderFactory.createLoweredBevelBorder());
        }
        else if(state == SquareState.PRESSED){
            if(number>0 && number <9){
                setIcon(new StretchIcon("icons/numbers/" + number + ".png",false));
                setBorder(BorderFactory.createLoweredBevelBorder());
            }
            else{
                setIcon(new StretchIcon("icons/blank.png",false));
                setBorder(BorderFactory.createLoweredBevelBorder());
            }
        }
    }

    public String getType() {return type;}

    public int getId(){ return id;}

}
