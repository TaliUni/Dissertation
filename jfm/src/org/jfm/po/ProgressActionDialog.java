package org.jfm.po;

import java.awt.*;
import javax.swing.*;
import javax.swing.event.*;
import java.awt.event.*;




/**
 * Title:        Java File Manager
 * Description:  
 * Copyright:    Copyright (c) 2001
 * Company:      Home
 * @author Giurgiu Sergiu
 * @version 1.0
 */

public class ProgressActionDialog extends JDialog {

  private JPanel panel1 = new JPanel();
  private JLabel fileCopyLabel = new JLabel();
  private JLabel jLabel1 = new JLabel();
  private JProgressBar fileProgressBar = new JProgressBar();
  private JProgressBar totalProgressBar = new JProgressBar();
  private JLabel statusLabel = new JLabel();
  private JPanel buttonsPanel = new JPanel();
  private JButton cancelButton = new JButton();
  private JPanel progressPanel=new JPanel();
  private GridBagLayout gridBagLayout1 = new GridBagLayout();
  private ActionExecuter executer=null;
  
  

  public void setFileProgresssValue(int v){
    fileProgressBar.setValue(v);
  } 

  public void setTotalProgresssValue(int v){
    totalProgressBar.setValue(v);
  } 
    
  public void startAction(final ActionExecuter ex){
    this.executer=ex;
    Thread actionThread=new Thread(new Runnable(){
      public void run(){
          executer.start();
          //cancelButton_actionPerformed(null);
      }
    });
    actionThread.start();
    this.setVisible(true);
  }
  
  
  public ProgressActionDialog(Frame frame, String title, boolean modal) {
    super(frame, title, modal);
    try {
      jbInit();
      pack();
    }
    catch(Exception ex) {
      ex.printStackTrace();
    }
  }

  public ProgressActionDialog() {
    this(null, "", false);
  }
  
  void jbInit() throws Exception {
    panel1.setLayout(new BorderLayout());
    cancelButton.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        cancelButton_actionPerformed(e);
      }
    });

    panel1.add(progressPanel,BorderLayout.CENTER);
    panel1.add(buttonsPanel,BorderLayout.SOUTH);
    progressPanel.setLayout(gridBagLayout1);
    fileCopyLabel.setText("File progress:");
    jLabel1.setText("Total progress");
    statusLabel.setText("Copying  file:");
    buttonsPanel.setBounds(new Rectangle(5, 105, 549, 35));
    cancelButton.setText("Cancel");
    totalProgressBar.setStringPainted(true);
    fileProgressBar.setStringPainted(true);
    getContentPane().add(panel1);
    progressPanel.add(totalProgressBar,  new GridBagConstraints(1, 2, 1, 1, 0.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(17, 8, 11, 10), 294, 9));
    progressPanel.add(jLabel1,  new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(17, 4, 11, 0), 3, 5));
    progressPanel.add(fileProgressBar,  new GridBagConstraints(1, 1, 1, 1, 0.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(9, 8, 0, 10), 294, 9));
    progressPanel.add(fileCopyLabel,  new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(9, 4, 0, 0), 10, 5));
    progressPanel.add(statusLabel,  new GridBagConstraints(0, 0, 2, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(7, 4, 0, 10), 458, 4));
    //progressPanel.add(buttonsPanel, null);
    buttonsPanel.add(cancelButton, null);
    
    totalProgressBar.addChangeListener(new ChangeListener(){
      public void stateChanged(ChangeEvent e){
        if(totalProgressBar.getValue()>=100){
          //cancelButton_actionPerformed(null);
        	dispose();
        }
      }
    });
  }
  
  //public void 

  void cancelButton_actionPerformed(ActionEvent e) {	  
      executer.cancel();    
      this.dispose();
  }
  
}