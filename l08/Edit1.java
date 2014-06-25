import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.*;

class Edit1 extends JFrame
{  JTextArea ta;

   Edit1()
   {  ta = new JTextArea("",24,80);
      ta.setLineWrap(true);

      add(BorderLayout.CENTER,ta);
   }
}

class TestEdit1
{  static BufferedReader fin;

   public static void main(String [] args) throws IOException
   {  Edit1 e = new Edit1();
      e.setTitle("Edit1");
      e.setSize(400,200);
      e.setVisible(true);
      e.addWindowListener(new WindowAdapter()
                          {  public void windowClosing(WindowEvent e)
                             {  System.exit(0); }
                          });

      fin = new BufferedReader(new FileReader("test.txt"));
      String next = fin.readLine();
      while(next!=null)
      {  e.ta.append(next+"\n");
         next = fin.readLine();
      }
   }
}

