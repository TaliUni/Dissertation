import javax.swing.*;
import java.awt.*;
import java.io.*;

import java.awt.event.*;
/*
import javax.swing.SwingUtilities;
import javax.swing.filechooser.*;
import java.awt.FileDialog;*/



public class Build1 extends JFrame implements ActionListener
{
    
    FileDialog fileDil;
    JButton openFileDil;
    JPanel panel;
    
    public void Build1()
    {
     setSize(200,200); 
     setUpPanel();
     pack();
     setVisible(true);
      
        
    }
    
    public void setUpPanel()
    {
        panel = new JPanel();
        panel.setLayout(new GridLayout(2,1));
        
        openFileDil = new JButton("open");
        openFileDil.addActionListener(this);
        
        panel.add(openFileDil);
        
        //this.add(panel);
        add(panel);
         
    }
    
    public void addBut()
    {
        add(openFileDil);
    }
    
    public void actionPerformed(ActionEvent e)
      {
        if(e.getSource()==openFileDil)
        {
           fileDil = new FileDialog(this, "fileloader", FileDialog.LOAD);
        fileDil.getDirectory();
        fileDil.setVisible(true); 
        }
      }
    
    
    
    
}