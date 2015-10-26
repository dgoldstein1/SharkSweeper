package View;


import javax.swing.*;
import java.awt.*;


/**
 * Created by Dave on 18/02/2015.
 */
public class SharkPanel extends JPanel {
    private JLabel imageLabel;
    private String[] sharkPics,dolphinPics;
    private int sharkPic=0,dolphinPic=0;

    public SharkPanel(){
        setLayout(new GridLayout(1,1));
        imageLabel = new JLabel();
        initPics();
        setImage("sp1.JPG");
        add(imageLabel);
    }
    private void initPics(){
        dolphinPics = new String[7];
        sharkPics = new String[6];
        for(int i=0;i<7;i++) dolphinPics[i] = "DolphinPics/d" + i + ".jpg";
        for(int i=0;i<6;i++) sharkPics[i] = "SharkPics/s" + i +".jpg";
    }
    private void setImage(String img){
        try{
            StretchIcon imageIcon = new StretchIcon(img, false);
            imageLabel.setIcon(imageIcon);
        } catch(NullPointerException e){
            new PopUpFrame("Image " + img + " not found");
            e.printStackTrace();
        }
    }
    public void setPicture(SharkPictureType pic){
        if(pic==SharkPictureType.DOLPHIN){
           setImage(dolphinPics[dolphinPic++%7]);
        }
        else if(pic==SharkPictureType.SHARK){
           setImage(sharkPics[sharkPic++%6]);
        }
        else{ //normal background
           setImage("sp1.JPG");
        }
    }

}
