package org.jfm.po;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.Vector;
import org.jfm.event.*;
import org.jfm.filesystems.JFMFile;

/**
 * Title:        Java File Manager
 * Description:
 * Copyright:    Copyright (c) 2001
 * Company:      Home
 * @author Giurgiu Sergiu
 * @version 1.0
 */

public class ButtonsPanel extends JPanel {
  private Vector buttons=null;
  private JPanel commandPanel=new JPanel(new BorderLayout());
  private JPanel btPanel=new JPanel();
  private JLabel currentPath=new JLabel();
  private JTextField commandField=new JTextField();
  @SuppressWarnings("unused")
private JFMFile workingDir=null;

  public ButtonsPanel(Vector buttons) {
    try {
      jbInit();
      setButtons(buttons);
    }
    catch(Exception ex) {
      ex.printStackTrace();
    }
  }

  public ButtonsPanel(){
    this(null);
  }

  public void setButtons(Vector newButtons){
    buttons=newButtons;
    btPanel.removeAll();
    if(buttons!= null){
      addButtons();
    }
  }

  private void addButtons(){
    btPanel.setLayout(new BoxLayout(btPanel,BoxLayout.X_AXIS));
    for(int i=0;i<buttons.size();i++){
      if(buttons.elementAt(i) instanceof JButton){
        btPanel.add((JButton)buttons.elementAt(i));
      }
    }

    //this.revalidate();
  }

  public Vector getButtons(){
    return buttons;
  }

  private void jbInit() throws Exception {
    this.setLayout(new BorderLayout());
    this.add(commandPanel,BorderLayout.NORTH);
    this.add(btPanel,BorderLayout.CENTER);
    addCommandPanelStuff();
  }

  private void addCommandPanelStuff(){
   commandField.addActionListener(new ActionListener(){
      public void actionPerformed(ActionEvent e){
        try{
          if(commandField.getText()==null || commandField.getText().length()<=0) return;

          @SuppressWarnings("unused")
          Process p=Runtime.getRuntime().exec(commandField.getText(),null,null);
          //@todo FIX the command output dialog
          //CommandOutputViewDialog d=new CommandOutputViewDialog(JOptionPane.getFrameForComponent(commandField),"Output",false);
          //d.setLocationRelativeTo(commandField);
          //d.setMonitoringProcess(p);
          //d.show();
          commandField.setText("");

        }catch(java.io.IOException ex){
          ex.printStackTrace();
        }
      }
    });

    Broadcaster.addChangeDirectoryListener(new ChangeDirectoryListener(){
      public void changeDirectory(ChangeDirectoryEvent e){
        if(e.getDirectory()!=null){
          //System.setProperty("user.dir",e.getDirectory());
          currentPath.setText(e.getDirectory().getAbsolutePath());
          workingDir=e.getDirectory();
        }
      }
    });


    commandPanel.add(currentPath,BorderLayout.WEST);
    commandPanel.add(commandField,BorderLayout.CENTER);
  }
}
