package build1;

import javax.swing.*;
import java.awt.*;



public class Build1 extends JFrame
{
    //JPanel mainPanel = new JPanel();
    //JFrame mainFrame = new JFrame();
    JFileChooser fileDil;// = new FileDialog(this);
    
    public void Build1()
    {
        fileDil = new JFileChooser();
        fileDil.setVisible(true);
        fileDil.setSize(50,50);
        add(fileDil);
        pack();
        setVisible(true);
      //  add(fileDil);
       // mainFrame.setSize(400,200);
      //  mainFrame.setVisible(true);
        
    }
    
    public static void main(String[] args) 
    {
        Build1 buildMy = new Build1();
        buildMy.setSize(100,100);
        buildMy.setVisible(true);
        System.out.println("test");
        
    }
    
    
}