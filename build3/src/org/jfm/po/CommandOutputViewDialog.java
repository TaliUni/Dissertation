package org.jfm.po;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
 * Title:        Java File Manager
 * Description:
 * Copyright:    Copyright (c) 2001
 * Company:      Home
 * @author Giurgiu Sergiu
 * @version 1.0
 */

public class CommandOutputViewDialog extends JDialog {
  private JPanel panel1 = new JPanel();
  private BorderLayout borderLayout1 = new BorderLayout();
  private JScrollPane scroll = new JScrollPane();
  private JTextArea text = new JTextArea();
  private Process process=null;

  public CommandOutputViewDialog(Frame frame, String title, boolean modal) {
    super(frame, title, modal);
    try {
      jbInit();
      //pack();
    }
    catch(Exception ex) {
      ex.printStackTrace();
    }
  }

  public CommandOutputViewDialog() {
    this(null, "", false);
  }

  public void setMonitoringProcess(Process p){
   process=p;

   new Thread(new Runnable(){
      public void run(){
        try{
        int read=1;
         byte[] data=new byte[1024];
          while(read>=0){
            read  =process.getInputStream().read(data);
            if(data!=null && data.length>0){
              text.setText(text.getText()+new String(data,0,read));
            }
          }
        }
        catch(java.io.IOException e){}
      }
   }).start();

   new Thread(new Runnable(){
      public void run(){
        try{
        int read=1;
        byte[] data=new byte[1024];
          while(read>=0){
             read=process.getErrorStream().read(data);
             if(data!=null && data.length>0){  
               text.setText(text.getText()+new String(data,0,read));
             }
          }
        }catch(java.io.IOException e){}
      }
   }).start();

   text.addKeyListener(new KeyAdapter() {
     public void keyTyped(KeyEvent e) {
      try{
        process.getOutputStream().write(e.getKeyChar());
      }catch(Exception ex){}
     }
   });
   
   new Thread(new Runnable(){
    public void run(){
      try {
          //i'm not interested in the return value of the process 
          //i just want to know when it's over
          process.waitFor();
      }
      catch (InterruptedException ex) {}
      finally {
        CommandOutputViewDialog.this.dispose();
      }
    }
   }).start();

  }

  void jbInit() throws Exception {
    panel1.setLayout(borderLayout1);
    getContentPane().add(panel1);
    panel1.add(scroll, BorderLayout.CENTER);
    scroll.getViewport().add(text, null);
    this.setSize(200,200);
  }

  /**Overridden so we can exit when window is closed*/
  protected void processWindowEvent(WindowEvent e) {
    super.processWindowEvent(e);
    if (e.getID() == WindowEvent.WINDOW_CLOSING) {
      process.destroy();
    }
  }

}