package org.jfm.po;

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

public class FilePropertiesDialog extends JDialog {
  private JPanel panel1 = new JPanel();
  private JLabel jLabel1 = new JLabel();
  private JLabel fileNameLabel = new JLabel();
  private JLabel jLabel2 = new JLabel();
  private JLabel fullPathLabel = new JLabel();
  private JLabel jLabel3 = new JLabel();
  private JLabel contentTypeLabel = new JLabel();
  private org.jfm.filesystems.JFMFile file;
  private JPanel jPanel1 = new JPanel();
  private TitledBorder titledBorder1;
  private JCheckBox readAttribute = new JCheckBox();
  private JCheckBox writeAttribute = new JCheckBox();
  private JCheckBox hiddenAttribute = new JCheckBox();
  private JButton setAttributesButton = new JButton();
  private JLabel jLabel4 = new JLabel();
  private JLabel lastModifiedLabel = new JLabel();
  private GridBagLayout gridBagLayout1 = new GridBagLayout();

  public FilePropertiesDialog(Frame frame, String title, boolean modal) {
    super(frame, title, modal);
    try {
      jbInit();
      pack();
    }
    catch(Exception ex) {
      ex.printStackTrace();
    }
  }

  public FilePropertiesDialog() {
    this(null, "", false);
  }

  void jbInit() throws Exception {
    titledBorder1 = new TitledBorder(BorderFactory.createLineBorder(new Color(153, 153, 153),2),"Attributes");
    panel1.setLayout(gridBagLayout1);
    this.setTitle("File Properties");
    jLabel1.setText("Name:");
    fileNameLabel.setText("N/A");
    jLabel2.setText("Full Path:");
    fullPathLabel.setText("N/A");
    jLabel3.setText("Content Type:");
    contentTypeLabel.setText("N/A");
    jPanel1.setBorder(titledBorder1);
    jPanel1.setLayout(null);
    readAttribute.setText("Read");
    readAttribute.setBounds(new Rectangle(15, 23, 116, 15));
    writeAttribute.setText("Write");
    writeAttribute.setBounds(new Rectangle(15, 55, 116, 15));
    hiddenAttribute.setText("Hidden");
    hiddenAttribute.setBounds(new Rectangle(15, 86, 116, 15));
    setAttributesButton.setBounds(new Rectangle(15, 124, 148, 28));
    setAttributesButton.setText("Set attributes");
    setAttributesButton.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        setAttributesButton_actionPerformed(e);
      }
    });
    jLabel4.setText("Last modified:");
    lastModifiedLabel.setText("N/A");
    jPanel1.add(readAttribute, null);
    jPanel1.add(writeAttribute, null);
    jPanel1.add(hiddenAttribute, null);
   // jPanel1.add(setAttributesButton, null);
    panel1.add(jLabel4,  new GridBagConstraints(0, 3, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(12, 13, 0, 0), 5, 2));
    panel1.add(lastModifiedLabel,  new GridBagConstraints(1, 3, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(12, 9, 0, 15), 184, 2));
    getContentPane().add(panel1);
    panel1.add(jLabel1,  new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(20, 13, 0, 31), 25, -3));
    panel1.add(fileNameLabel,  new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(20, 9, 0, 15), 184, -3));
    panel1.add(jLabel2,  new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(20, 13, 0, 25), 12, 0));
    panel1.add(fullPathLabel,  new GridBagConstraints(1, 1, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(20, 9, 0, 15), 184, 0));
    panel1.add(jLabel3,  new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(19, 13, 0, 0), 7, 2));
    panel1.add(contentTypeLabel,  new GridBagConstraints(1, 2, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(19, 9, 0, 15), 184, 2));
    panel1.add(jPanel1,  new GridBagConstraints(0, 4, 2, 1, 1.0, 1.0
            ,GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(10, 13, 7, 8), 318, 163));

    readAttribute.setEnabled(false);
    writeAttribute.setEnabled(false);
    hiddenAttribute.setEnabled(false);
  }

  public void setFile(org.jfm.filesystems.JFMFile file) {
    this.file = file;
    fillFields();
  }

  private void fillFields(){
    fileNameLabel.setText(file.getName());
    fullPathLabel.setText(file.getPath());
    if(!file.isDirectory()){
      try{
        contentTypeLabel.setText(file.getMimeType());
      }catch(Exception ex){}
    }else{
      contentTypeLabel.setText("directory");
    }

    java.text.SimpleDateFormat format=new java.text.SimpleDateFormat("EEE, MMM d, yyyy 'at' hh:mm:ss");
    lastModifiedLabel.setText(format.format(new java.util.Date(file.lastModified())));
    readAttribute.setSelected(file.canRead());
    writeAttribute.setSelected(file.canWrite());
    hiddenAttribute.setSelected(file.isHidden());
  }

  void setAttributesButton_actionPerformed(ActionEvent e) {
      /**@todo find a cool way to set the attributes of a file.*/
  }
}
