import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.*;

class Edit4 extends JFrame implements ActionListener
{  JScrollPane sp;
   JTextArea ta;

   JMenuBar jb;
   JMenu file;
   JMenuItem MNew,MOpen,MClose,MExit;

   boolean editing;

   JFileChooser files;

   Edit4()
   {  sp = new JScrollPane();
      sp.setHorizontalScrollBarPolicy
       (ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

      ta = new JTextArea("",24,80);
      ta.setLineWrap(true);
      ta.setEditable(false);

      editing = false;

      sp.setViewportView(ta);

      add(BorderLayout.CENTER,sp);

      jb = new JMenuBar();
      file = new JMenu("File");

      MNew = new JMenuItem("New");
      MOpen = new JMenuItem("Open");
      MClose = new JMenuItem("Close");
      MExit = new JMenuItem("Exit");

      MNew.addActionListener(this);
      MOpen.addActionListener(this);
      MClose.addActionListener(this);
      MExit.addActionListener(this);

      file.add(MNew);
      file.add(MOpen);
      file.add(MClose);
      file.add(MExit);

      jb.add(file);

      setJMenuBar(jb);

      files = new JFileChooser();
   }

   public void doOpen()
   {  try{
      int response = files.showOpenDialog(this);
      if(response==JFileChooser.APPROVE_OPTION)
      {  File f = files.getSelectedFile();
         BufferedReader fin =
          new BufferedReader(new FileReader(f));
         String next = fin.readLine();
         while(next!=null)
         {  ta.append(next+"\n");
            next = fin.readLine();
         }
         fin.close();
         editing = true;
         ta.setEditable(true);
      }
      }catch(IOException e){};
   }

   public void doClose() 
   {  try{
      int response = files.showSaveDialog(this);
      if(response==JFileChooser.APPROVE_OPTION)
      {  File f = files.getSelectedFile();
         BufferedWriter fout =
          new BufferedWriter(new FileWriter(f));
         fout.write(ta.getText());
         fout.close();
         editing = false;
         ta.setEditable(false);
      }
      }catch(IOException e){};
   }        

   public void doExit()
   {  JOptionPane op = new JOptionPane();
      int response =
       op.showConfirmDialog(this,"Exit without Close?","Exit",
                            JOptionPane.OK_CANCEL_OPTION,
                            JOptionPane.QUESTION_MESSAGE);
      if(response==JOptionPane.OK_OPTION)
       System.exit(0);
   }

   public void doNew()
   {  ta.setText("");
      editing = true;
      ta.setEditable(true);
   }
   
   public void actionPerformed(ActionEvent e)
   {  if(e.getSource()==MExit)
          doExit();
      if(!editing)
      {  if(e.getSource()==MNew)
          doNew();
         else
         if(e.getSource()==MOpen)
          doOpen();
      }
      else
      if(e.getSource()==MClose)
       doClose();
   }
}

class TestEdit4
{  static BufferedReader fin;

   public static void main(String [] args) throws IOException
   {  Edit4 e = new Edit4();
      e.setTitle("Edit4");
      e.setSize(400,200);
      e.setVisible(true);
      e.addWindowListener(new WindowAdapter()
                          {  public void windowClosing(WindowEvent e)
                             {  System.exit(0); }
                          });

   }
}

