package org.jfm.main;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import org.jfm.views.fview.FileViewDialog;

import org.jfm.filesystems.JFMFile;

/**
 * This dialog is used for searching files.
 * <p>Title: Java File Manager</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2001</p>
 * <p>Company: Home</p>
 * @author Giurgiu Sergiu
 * @version 1.0
 */
public class SearchDialog extends JDialog {
  private JPanel panel1 = new JPanel();
  private JPanel topPanel = new JPanel();
  private JLabel jLabel1 = new JLabel();
  private JTextField startFromField = new JTextField();
  private JLabel jLabel2 = new JLabel();
  private JTextField filesSearchField = new JTextField();
  private JCheckBox findTextCheckBox = new JCheckBox();
  private JTextField findTextField = new JTextField();
  private JButton searchButton = new JButton();
  private JScrollPane scroll = new JScrollPane();
  private JList filesList = new JList();
  private JPanel buttonsPanel = new JPanel();
  private GridBagLayout gridBagLayout1 = new GridBagLayout();
  private JButton viewFileButton = new JButton();
  private JButton editFileButton = new JButton();
  private JButton closeButton = new JButton();
  private FlowLayout flowLayout1 = new FlowLayout();
  private BorderLayout borderLayout1 = new BorderLayout();
  private boolean isSearching=false;
  private boolean mustStop=false;
  private DefaultListModel listModel=new DefaultListModel();

  public SearchDialog(Frame frame, String title, boolean modal) {
    super(frame, title, modal);
    try {
      jbInit();
      pack();
    }
    catch(Exception ex) {
      ex.printStackTrace();
    }
  }

  public SearchDialog() {
    this(null, "", false);
  }
  void jbInit() throws Exception {
    panel1.setLayout(borderLayout1);
    topPanel.setLayout(gridBagLayout1);
    filesList.setModel(listModel);
    jLabel1.setText("Start from:");
    jLabel2.setText("Search for:");
    findTextCheckBox.setText("Find text");
    findTextCheckBox.addItemListener(new java.awt.event.ItemListener() {
      public void itemStateChanged(ItemEvent e) {
        findTextCheckBox_itemStateChanged(e);
      }
    });
    findTextField.setEnabled(false);
    searchButton.setText("Search");
    searchButton.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        searchButton_actionPerformed(e);
      }
    });
    buttonsPanel.setLayout(flowLayout1);
    viewFileButton.setText("View");
    viewFileButton.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        viewFileButton_actionPerformed(e);
      }
    });
    editFileButton.setText("Edit");
    editFileButton.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        editFileButton_actionPerformed(e);
      }
    });
    closeButton.setText("Close");
    closeButton.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        closeButton_actionPerformed(e);
      }
    });
    flowLayout1.setAlignment(FlowLayout.LEFT);
    getContentPane().add(panel1);
    panel1.add(topPanel, BorderLayout.NORTH);
    topPanel.add(jLabel1,  new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(5, 14, 6, 11), 11, 0));
    topPanel.add(startFromField,  new GridBagConstraints(1, 0, 1, 1, 1.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(5, 12, 0, 0), 257, 2));
    topPanel.add(jLabel2,  new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(14, 14, 0, 12), 11, 4));
    topPanel.add(filesSearchField,  new GridBagConstraints(1, 1, 1, 1, 1.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(14, 12, 0, 0), 257, 2));
    topPanel.add(findTextCheckBox,  new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(13, 14, 8, 0), 8, -5));
    topPanel.add(findTextField,  new GridBagConstraints(1, 2, 1, 1, 1.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(13, 12, 8, 0), 257, 2));
    topPanel.add(searchButton,  new GridBagConstraints(2, 0, 1, 1, 0.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(5, 25, 0, 33), 11, -2));
    panel1.add(scroll, BorderLayout.CENTER);
    scroll.getViewport().add(filesList, null);
    panel1.add(buttonsPanel, BorderLayout.SOUTH);
    buttonsPanel.add(viewFileButton, null);
    buttonsPanel.add(editFileButton, null);
    buttonsPanel.add(closeButton, null);
    topPanel.getRootPane().setDefaultButton(searchButton);
  }

  void closeButton_actionPerformed(ActionEvent e) {
    this.dispose();
  }

  void findTextCheckBox_itemStateChanged(ItemEvent e) {
    findTextField.setEnabled(e.getStateChange()==ItemEvent.SELECTED);
  }

  void viewFileButton_actionPerformed(ActionEvent e) {

      if(filesList.getSelectedIndex()<0){
        return;
      }

      JFMFile selFile=(JFMFile)filesList.getSelectedValue();
      FileViewDialog d=new FileViewDialog(JOptionPane.getFrameForComponent(this),selFile.getPath(),false);
      d.setLocationRelativeTo(this);
      d.setContent(selFile,false);
      d.setVisible(true);
  }

  void editFileButton_actionPerformed(ActionEvent e) {
      if(filesList.getSelectedIndex()<0){
        return;
      }
      JFMFile selFile=(JFMFile)filesList.getSelectedValue();
      FileViewDialog d=new FileViewDialog(JOptionPane.getFrameForComponent(this),selFile.getPath(),false);
      d.setLocationRelativeTo(this);
      d.setContent(selFile,true);
      d.setVisible(true);
  }

  /**
   * The search action. Begin searching files.
   * @param e
   */
  void searchButton_actionPerformed(ActionEvent e) {
    if(isSearching){
      mustStop=true;
      return;
    }
@SuppressWarnings("unused")
    String startDir=null;
    if(startFromField.getText()==null || startFromField.getText().length()<=0 || startFromField.getText().equals(".")){
      startDir=System.getProperty("user.dir");
    }else{
      startDir=startFromField.getText();
    }

    final JFMFile startFileDir=null;//new File(startDir);
    if(!startFileDir.isDirectory() || !startFileDir.exists()){
      JOptionPane.showMessageDialog(this,"Invalid start directory specified.","Error",JOptionPane.ERROR_MESSAGE);
      return;
    }

    if(filesSearchField.getText()==null || filesSearchField.getText().length()<=0){
      JOptionPane.showMessageDialog(this,"Invalid search mask specified. Use *,? to specify more files.","Error",JOptionPane.ERROR_MESSAGE);
      return;
    }

    listModel.clear();
    //start the search
    new Thread(new Runnable(){
      public void run(){
        searchButton.setText("Stop");
        isSearching=true;
        try{
          search(startFileDir);
        }catch(Exception ex){}
        searchButton.setText("Search");
        isSearching=false;
        mustStop=false;
      }
    }).start();
  }

  /**
   * Searches a directory for files. Throws an exception if it has to stop, just to terminate the thread.
   * @param dir
   * @throws Exception
   */
  private void search(JFMFile dir) throws Exception
  {
    JFMFile[] files=dir.listFiles();
    for(int i=0;i<files.length;i++){
      if(files[i].isDirectory()){
        search(files[i]);
      }else{
        if(mustStop) throw new Exception("I have to stop");
        if(isEligible(files[i])){
          listModel.addElement(files[i]);
        }
      }
    }

  }

  /**
   * Returns true if the name specified is matching the mask.
   * @param name
   * @return
   */
  private boolean isEligible(JFMFile f){
    boolean match=false;
    match=new MaskMatch().doesMatch(filesSearchField.getText(),f.getName());

    if(match && findTextCheckBox.isSelected()){  //search within the file
      match=false;
      try{
        java.io.InputStream buf=f.getInputStream();
        byte[] wholeFile =null;
        if(f.length()>=(long)Integer.MAX_VALUE){
          wholeFile=new byte[Integer.MAX_VALUE];
        }else{
          wholeFile=new byte[(int)f.length()];
        }
        int bytesRead=-1;
        while((bytesRead=buf.read(wholeFile))>=0){
           String stringRead=new String(wholeFile,0,bytesRead);
           if(stringRead.indexOf(findTextField.getText())>=0){
            match=true;
            break;
           }
        }
        buf.close();

      }catch(java.io.IOException ignored){}
    }

    return match;
  }

  /**
   * These classes are used for pattern match. These are not mine, are stolen from a friend of mine Arthur Vitui.
   * I use them, so that i won't be forced to use jdk's 1.4 regexp, to try to maintain 1.3 compatibility.
   */
private class AuxPoz{
      public int pozw;
      public int pozt;
}

private class MaskMatch {
       private AuxPoz aux;

       public MaskMatch() {
              aux=new AuxPoz();
       }

       private int asterix(char[] wildcard,char[] test,AuxPoz aux){
               int fit=1;
               (aux.pozw)++;
               while ((test[aux.pozt]!='\0')&&((wildcard[aux.pozw]=='?')||(wildcard[aux.pozw]=='*'))){
                     if (wildcard[aux.pozw]=='?')
                        (aux.pozt)++;
                     (aux.pozw)++;
               }
               while (wildcard[aux.pozw]=='*')
                     (aux.pozw)++;
               if ((test[aux.pozt]=='\0')&&(wildcard[aux.pozw]!='\0')){
                  return fit=0;
               }
               if ((test[aux.pozt]=='\0')&&(wildcard[aux.pozw]=='\0')){
                  return fit=1;
               }
               else{
                    if (wildcardfit(wildcard,test,aux.pozw,aux.pozt)==0){
                       do{
                          (aux.pozt)++;
                          while ((wildcard[aux.pozw]!=test[aux.pozt])&&(test[aux.pozt]!='\0'))
                                (aux.pozt)++;
                       }
                       while(test[aux.pozt]!='\0'?(wildcardfit(wildcard,test,aux.pozw,aux.pozt)==0):(0!=(fit=0)));
                    }
                    if ((test[aux.pozt]=='\0')&&(wildcard[aux.pozw]=='\0'))
                       fit=1;
                    return fit;
               }
       }

       private int wildcardfit(char[] wildcard,char[] test,int pozw,int pozt){
               int fit=1;
               for (;(wildcard[pozw]!='\0')&&(fit==1)&&(test[pozt]!='\0');(pozw)++){
                   if (wildcard[pozw]=='?')
                      (pozt)++;
                   else if (wildcard[pozw]=='*'){
                        aux.pozw=pozw;
                        aux.pozt=pozt;
                        fit=asterix(wildcard,test,aux);
                        (aux.pozw)--;
                        pozw=aux.pozw;
                        pozt=aux.pozt;
                   }
                   else{
                        if (wildcard[pozw]==test[pozt]) fit=1;
                        else fit=0;
                        (pozt)++;
                   }
               }
               while ((wildcard[pozw]=='*')&&(fit==1))
                     (pozw)++;
               if ((fit==1)&&(test[pozt]=='\0')&&(wildcard[pozw]=='\0'))
                  fit=1;
               else fit=0;
               return fit;
        }

        public boolean doesMatch(String wStr,String tStr){
               boolean match=false;
               char[] wildcard=new char[wStr.length()+2];
               char[] test=new char[tStr.length()+2];
               int i=0,pozw=0,pozt=0;
               wStr.getChars(0,wStr.length(),wildcard,0);
               tStr.getChars(0,tStr.length(),test,0);
               wildcard[wildcard.length-1]='\0';
               wildcard[wildcard.length-2]='\0';
               test[test.length-1]='\0';
               test[test.length-2]='\0';
               aux.pozt=0;
               aux.pozw=0;
               i=wildcardfit(wildcard,test,pozw,pozt);
               if (i==1)
                  match=true;
               else
                  match=false;
               return match;
        }
}
}
