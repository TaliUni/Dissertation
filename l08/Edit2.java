import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.*;

class Edit2 extends JFrame
{  JScrollPane sp;
   JTextArea ta;

   Edit2()
   {  sp = new JScrollPane();
      sp.setHorizontalScrollBarPolicy
       (ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

      ta = new JTextArea("",24,80);
      ta.setLineWrap(true);

      sp.setViewportView(ta);

      add(BorderLayout.CENTER,sp);
   }
}

class TestEdit2
{  static BufferedReader fin;

   public static void main(String [] args) throws IOException
   {  Edit2 e = new Edit2();
      e.setTitle("Edit2");
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

