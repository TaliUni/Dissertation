package org.jfm.po.copy;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;


/**
 * Title:        Java File Manager
 * Description:  
 * Copyright:    Copyright (c) 2001
 * Company:      Home
 * @author Giurgiu Sergiu
 * @version 1.0
 */

public class CopyConfirmDialog extends JDialog {
  private JPanel panel1 = new JPanel();
  private JPanel buttonsPanel = new JPanel();
  private JPanel infPanel = new JPanel();
  private JButton okButton = new JButton();
  private JButton cancelButton = new JButton();
  private JLabel jLabel1 = new JLabel();
  private JTextField copyFromTextField = new JTextField();
  private JLabel jLabel2 = new JLabel();
  private JTextField copyToTextField = new JTextField();
  private GridBagLayout gridBagLayout1 = new GridBagLayout();
  private FlowLayout flowLayout1 = new FlowLayout();
  private BorderLayout borderLayout1 = new BorderLayout();
  private String copyFrom;
  private String copyTo;
  private boolean cancelled=true;

  public CopyConfirmDialog(Frame frame, String title, boolean modal) {
    super(frame, title, modal);
    try {
      jbInit();
      pack();
    }
    catch(Exception ex) {
      ex.printStackTrace();
    }
  }

  public CopyConfirmDialog() {
    this(null, "", false);
  }
  
  public void setMoveOperation()
  {
	  jLabel1.setText("Move file(s):");
  }
  
  void jbInit() throws Exception {
    panel1.setLayout(borderLayout1);
    buttonsPanel.setLayout(flowLayout1);
    infPanel.setLayout(gridBagLayout1);
    okButton.setText("OK");
    okButton.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        okButton_actionPerformed(e);
      }
    });
    cancelButton.setText("Cancel");
    cancelButton.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        cancelButton_actionPerformed(e);
      }
    });
    
    jLabel1.setText("Copy file(s):");
    jLabel2.setText("To Directory:");
    getContentPane().add(panel1);
    panel1.add(buttonsPanel, BorderLayout.SOUTH);
    panel1.add(infPanel, BorderLayout.CENTER);
    buttonsPanel.add(okButton, null);
    buttonsPanel.add(cancelButton, null);
    infPanel.add(jLabel1,   new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(10, 7, 0, 8), 25, 8));
    infPanel.add(copyFromTextField,     new GridBagConstraints(1, 0, 1, 1, 1.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(10, 5, 0, 10), 336, 5));
    infPanel.add(copyToTextField,     new GridBagConstraints(1, 1, 1, 1, 1.0, 0.0
            ,GridBagConstraints.NORTHWEST, GridBagConstraints.HORIZONTAL, new Insets(10, 5, 10, 10), 336, 5));
    infPanel.add(jLabel2,   new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0
            ,GridBagConstraints.NORTHWEST, GridBagConstraints.NONE, new Insets(10, 6, 10, 1), 12, 8));
    panel1.getRootPane().setDefaultButton(okButton);
  }

  void okButton_actionPerformed(ActionEvent e) {
    setCancelled(false);
    this.dispose();
  }

  void cancelButton_actionPerformed(ActionEvent e) {
    setCancelled(true);
    this.dispose();
  }
  public void setCopyFrom(String copyFrom) {
    this.copyFrom = copyFrom;
    copyFromTextField.setText(copyFrom);
  }
  public String getCopyFrom() {
    copyFrom=copyFromTextField.getText();
    return copyFrom;
  }
  public void setCopyTo(String copyTo) {
    this.copyTo = copyTo;
    copyToTextField.setText(copyTo);
  }
  public String getCopyTo() {
    copyTo=copyToTextField.getText();
    return copyTo;
  }
  public void setCancelled(boolean cancelled) {
    this.cancelled = cancelled;
  }
  public boolean isCancelled() {
    return cancelled;
  }

}