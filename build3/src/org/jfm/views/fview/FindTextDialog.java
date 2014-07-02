package org.jfm.views.fview;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;
import java.awt.event.*;

/**
 * <p>Title: Java File Manager</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2001</p>
 * <p>Company: Home</p>
 * @author Giurgiu Sergiu
 * @version 1.0
 */

public class FindTextDialog extends JDialog {
  private JPanel panel1 = new JPanel();
  private JPanel jPanel1 = new JPanel();
  private BorderLayout borderLayout1 = new BorderLayout();
  private JTextField findText = new JTextField();
  private JLabel jLabel1 = new JLabel();
  private JPanel optionsPanel = new JPanel();
  private JCheckBox caseSensitiveCheckBox = new JCheckBox();
  private JCheckBox fileStartCheckBox = new JCheckBox();
  private JCheckBox wholeWordCheckBox = new JCheckBox();
  private FlowLayout flowLayout2 = new FlowLayout();
  private JCheckBox regexpCheckBox = new JCheckBox();
  private JLabel jLabel2 = new JLabel();
  private JTextField replaceText = new JTextField();
  private JButton findNextButton = new JButton();
  private JButton replaceButton = new JButton();
  private JButton replaceAllButton = new JButton();
  private FindListener listener=null;
  private JButton closeButton = new JButton();
  private int count=0;
  
  public FindTextDialog(Frame owner, String title, boolean modal) {
    super(owner, title, modal);
    try {
      jbInit();
      //pack();
    }
    catch(Exception ex) {
      ex.printStackTrace();
    }
  }

  public FindTextDialog() {
    this(null, "", false);
  }
  
  public void setFindListener(FindListener l){
    listener=l;
  }
  
  void jbInit() throws Exception {
    this.setResizable(false);
    this.setSize(new Dimension(383, 278));
    panel1.setLayout(borderLayout1);
    jPanel1.setLayout(null);
    findText.setBounds(new Rectangle(116, 12, 249, 21));
    jLabel1.setText("Find:");
    jLabel1.setBounds(new Rectangle(4, 12, 44, 18));
    optionsPanel.setBorder(new TitledBorder(BorderFactory.createLineBorder(new Color(153, 153, 153),1),"Options"));
    optionsPanel.setBounds(new Rectangle(4, 83, 201, 187));
    optionsPanel.setLayout(flowLayout2);
    caseSensitiveCheckBox.setText("Case Sensitive");
    fileStartCheckBox.setText("From file start");
    wholeWordCheckBox.setText("Whole word only");
    flowLayout2.setAlignment(FlowLayout.LEFT);
    this.setForeground(UIManager.getColor("Label.foreground"));
    regexpCheckBox.setText("Regular Expresion");
    String javaVersion=System.getProperty("java.version");
    if(javaVersion!=null && javaVersion.length()>0 && javaVersion.indexOf("1.4")>=0){
        regexpCheckBox.setEnabled(true);
        //this.getRootPane().setWindowDecorationStyle(JRootPane.PLAIN_DIALOG);
    }else{
        regexpCheckBox.setEnabled(false);
        regexpCheckBox.setToolTipText("Must have JDK 1.4 to be able to use this.");
    }
    
    jLabel2.setText("Replace with:");
    jLabel2.setBounds(new Rectangle(4, 48, 94, 19));
    replaceText.setBounds(new Rectangle(116, 48, 250, 21));
    findNextButton.setBounds(new Rectangle(232, 92, 134, 27));
    findNextButton.setText("Find Next");
    findNextButton.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        findNextButton_actionPerformed(e);
      }
    });
    replaceButton.setBounds(new Rectangle(232, 130, 134, 27));
    replaceButton.setText("Replace");
    replaceButton.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        replaceButton_actionPerformed(e);
      }
    });
    replaceAllButton.setBounds(new Rectangle(232, 172, 134, 27));
    replaceAllButton.setText("Replace All");
    replaceAllButton.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        replaceAllButton_actionPerformed(e);
      }
    });
    closeButton.setBounds(new Rectangle(233, 209, 133, 25));
    closeButton.setText("Close");
    closeButton.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        closeButton_actionPerformed(e);
      }
    });
    getContentPane().add(panel1);
    panel1.add(jPanel1, BorderLayout.CENTER);
    jPanel1.add(jLabel1, null);
    optionsPanel.add(caseSensitiveCheckBox, null);
    optionsPanel.add(fileStartCheckBox, null);
    optionsPanel.add(wholeWordCheckBox, null);
  //  optionsPanel.add(regexpCheckBox, null);
    jPanel1.add(jLabel2, null);
    jPanel1.add(findText, null);
    jPanel1.add(replaceText, null);
    jPanel1.add(optionsPanel, null);
    jPanel1.add(findNextButton, null);
    jPanel1.add(replaceButton, null);
    jPanel1.add(replaceAllButton, null);
    jPanel1.add(closeButton, null);
    panel1.getRootPane().setDefaultButton(findNextButton);
    fileStartCheckBox.addChangeListener(new javax.swing.event.ChangeListener() {
      public void stateChanged(javax.swing.event.ChangeEvent e) {
        if(fileStartCheckBox.isSelected()) count=0;
      }
    });
  }

  void findNextButton_actionPerformed(ActionEvent e) {
    listener.find(findText.getText(),null,caseSensitiveCheckBox.isSelected(),fileStartCheckBox.isSelected(),wholeWordCheckBox.isSelected(),regexpCheckBox.isSelected(),count);
    count++;
  }

  void replaceButton_actionPerformed(ActionEvent e) {
    listener.find(findText.getText(),replaceText.getText(),caseSensitiveCheckBox.isSelected(),fileStartCheckBox.isSelected(),wholeWordCheckBox.isSelected(),regexpCheckBox.isSelected(),count);
    count++;
  }

  void replaceAllButton_actionPerformed(ActionEvent e) {
    listener.all(findText.getText(),replaceText.getText(),caseSensitiveCheckBox.isSelected(),fileStartCheckBox.isSelected(),wholeWordCheckBox.isSelected(),regexpCheckBox.isSelected());    
  }

  void closeButton_actionPerformed(ActionEvent e) {
    this.dispose();
  }
}