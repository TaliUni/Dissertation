package l08;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.*;

class JOP extends JFrame
{  JOptionPane jop;

   JOP()
   {  jop = new JOptionPane();
      jop.showMessageDialog(this,"Proceed?","Proceed",
                            JOptionPane.QUESTION_MESSAGE);
      jop.showConfirmDialog(this,"Danger!","Danger",
                            JOptionPane.YES_NO_CANCEL_OPTION,
                            JOptionPane.WARNING_MESSAGE);

      jop.showInputDialog(this,"Choice:","Choice",
                          JOptionPane.INFORMATION_MESSAGE);
                          
   }
}

class TestJOP
{  public static void main(String [] args) throws IOException
   {  JOP e = new JOP();
      e.setTitle("Edit3");
      e.setSize(400,200);
      e.setVisible(true);
      e.addWindowListener(new WindowAdapter()
                          {  public void windowClosing(WindowEvent e)
                             {  System.exit(0); }
                          });

   }
}

