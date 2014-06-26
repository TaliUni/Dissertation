package build1;

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
    //FileDialog is a standalone window and so can't go into a container
    //it stops application from doing anything else until it's finished
    FileDialog fileDil;
    JButton openFileDil;
    JPanel panel;
    TextArea ta;
    
    public  Build1()
    {
        
        
      // setupFileDialog();
     setUpPanel();
     pack();
     setVisible(true);
      
     
        
    }
    
    //on application opening, file dialog immediately comes up
    //application paused for all other action until this is finished
    private void setupFileDialog()
    {
        fileDil = new FileDialog(this, "fileloader", FileDialog.LOAD);
        fileDil.getDirectory();
       fileDil.setVisible(true);
       
    }
    
    public String getFileName()
    {
        return fileDil.getFile();
    }
    
    private void fileToTextArea()
    {
        ta.setText(getFileName());
    }
    
    public void setUpPanel()
    {
        panel = new JPanel();
           
        ta = new TextArea(50,50);
        panel.add(ta);
        
        openFileDil = new JButton();
        openFileDil.addActionListener(this);
        panel.add(openFileDil);
        
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
            System.out.println("testing");
           setupFileDialog();
        ta.setText(getFileName());
        }
      }
    
    
    
    
}