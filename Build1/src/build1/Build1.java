package build1;
/*
Build1
First build for project.
Spec: Have file dialog come up, and print file to screen.
What Does:  opens up GUI, with textarea and button.
Button on clicking opens up a file dialog (LOAD type)
open selecting a file, file dialog closes,
filename (short) is printed to textarea.
button can be used again.
textarea doesn't hold previous info.

BUILD ONE COMPLETE: 26TH JUNE 2014


*/
import javax.swing.*;
import java.awt.*;
import java.io.*;

import java.awt.event.*;




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
        
        
        setUpPanel();
        pack();
        setVisible(true);
        
        setDefaultCloseOperation(this.DISPOSE_ON_CLOSE);
      
     
        
    }
    
    public void setUpPanel()
    {
        panel = new JPanel();
           
        ta = new TextArea(50,50);
        panel.add(ta);
        
        openFileDil = new JButton("open file dialog");
        openFileDil.addActionListener(this);
        panel.add(openFileDil);
        
        add(panel);
        
      
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
    
   
    
   
    
   
   
    
    
    
    
    
    
}