package org.jfm.views.fview;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.beans.PropertyChangeListener;
import org.jfm.filesystems.JFMFile;
import org.jfm.views.FontDialog;

/**
 * Title:        Java File Manager
 * Description:
 * Copyright:    Copyright (c) 2001
 * Company:      Home
 * @author Giurgiu Sergiu
 * @version 1.0
 */

public class FileViewDialog extends JDialog {
  private JPanel panel1 = new JPanel();
  private BorderLayout borderLayout1 = new BorderLayout();
  private JScrollPane scroll=new JScrollPane();
  private FView view=new FView();
  private JMenuBar menu = new JMenuBar();
  private JMenu fileMenu = new JMenu();
  private JMenuItem quitMenuItem = new JMenuItem();
  private JMenuItem saveFileMenu=new JMenuItem("Save");
  private JMenuItem saveAsFileMenu=new JMenuItem("Save As...");
  private JMenu toolsMenu = new JMenu();
  private JMenuItem fontMenuItem = new JMenuItem();
  private JMenuItem findMenuItem = new JMenuItem("Find text");
  private JCheckBoxMenuItem word_wrapMenuItem = new JCheckBoxMenuItem();
  //these are the word separators. If you want to add something, or remove something, this is the place to be.
  private char[] wordSeparatorChars=new char[]{' ','.',',',':','+','-','=','\\','/','?','<','>',';','"','*','(',')','`','\'','\t','\n'};


  public FileViewDialog(Frame frame, String title, boolean modal) {
    super(frame, title, modal);
    try {
      jbInit();
    //  pack();
    }
    catch(Exception ex) {
      ex.printStackTrace();
    }
  }

  public FileViewDialog() {
    this(null, "", false);
  }

  private void jbInit() throws Exception {
    this.setSize(new Dimension(500,400));
    panel1.setLayout(borderLayout1);
    fileMenu.setText("File");
    quitMenuItem.setText("Quit");
    toolsMenu.setText("Tools");
    fontMenuItem.setText("Font");
    word_wrapMenuItem.setMnemonic('0');
    word_wrapMenuItem.setText("Word wrap");

    findMenuItem.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        findMenuItem_actionPerformed(e);
      }
    });
    getContentPane().add(panel1);
    panel1.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("ESCAPE"),"escape");
    panel1.getActionMap().put("escape",new EscapeAction());
    panel1.add(scroll,BorderLayout.CENTER);
    menu.add(fileMenu);
    menu.add(toolsMenu);
    fileMenu.add(saveFileMenu);
    fileMenu.add(saveAsFileMenu);
    fileMenu.add(quitMenuItem);
    toolsMenu.add(fontMenuItem);
    toolsMenu.add(findMenuItem);
    toolsMenu.add(word_wrapMenuItem);
    this.setJMenuBar(menu);
    scroll.setViewportView(view);

    word_wrapMenuItem.addActionListener(new ActionListener(){
      public void actionPerformed(ActionEvent e){
        boolean wrap=word_wrapMenuItem.getState();
        view.setLineWrap(wrap);
        view.setWrapStyleWord(wrap);
      }
    });

    quitMenuItem.addActionListener(new EscapeAction());
    fontMenuItem.addActionListener(new ActionListener(){
      public void actionPerformed(ActionEvent e){
        FontDialog f=new FontDialog(JOptionPane.getFrameForComponent(FileViewDialog.this),"Fonts",true);
        f.setLocationRelativeTo(FileViewDialog.this);
        f.setVisible(true);
        if(!f.isCancelled())
          view.setFont(f.getSelectedFont());
      }
    });

    saveAsFileMenu.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        JFileChooser choose=new JFileChooser(view.getEl().getAbsolutePath());
        int result=choose.showSaveDialog(FileViewDialog.this);
        if(result!=JFileChooser.APPROVE_OPTION) return;
        @SuppressWarnings("unused")
        java.io.File f=choose.getSelectedFile();
        //@todo Save the file on the requested filesystem
        //saveFile(f);
      }
    });

    saveFileMenu.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        saveFile(view.getEl());
      }
    });

    saveFileMenu.setAccelerator(KeyStroke.getKeyStroke("ctrl S"));
    quitMenuItem.setAccelerator(KeyStroke.getKeyStroke("ctrl Q"));
    word_wrapMenuItem.setAccelerator(KeyStroke.getKeyStroke("ctrl W"));
    fontMenuItem.setAccelerator(KeyStroke.getKeyStroke("ctrl O"));
    findMenuItem.setAccelerator(KeyStroke.getKeyStroke("ctrl F"));
  }

  private void saveFile(JFMFile f){
    try{
      java.io.OutputStream out=f.getOutputStream();
      byte[] data=view.getText().getBytes();
      out.write(data);
      out.close();
    }catch(Exception ex){
      JOptionPane.showMessageDialog(this,"Couldn't write the file...","Error",JOptionPane.ERROR_MESSAGE);
    }
  }

  public void setContent(JFMFile el,boolean editable){
    view.setEl(el);
    view.setEditable(editable);
    saveAsFileMenu.setEnabled(editable);
    if(!el.isDirectory()){
      saveFileMenu.setEnabled(editable);
    }else{
        saveFileMenu.setEnabled(false);
    }
  }


  private class EscapeAction implements Action
  {
    private boolean enabled=true;
    public Object getValue(String key) {
      return null;
    }

    public void putValue(String key, Object value) {
    }

    public void setEnabled(boolean b) {
      enabled=b;
    }
    public boolean isEnabled() {
      return enabled;
    }
    public void addPropertyChangeListener(PropertyChangeListener listener) {
    }
    public void removePropertyChangeListener(PropertyChangeListener listener) {
    }

    public void actionPerformed(java.awt.event.ActionEvent e) {
      FileViewDialog.this.dispose();
    }
  }

  private boolean findRegularText(String findText,int position,boolean caseSensitive,boolean wholeWord){
    int foundTextIndex=-1;
    if(caseSensitive){
      foundTextIndex=view.getText().indexOf(findText,position);
    }else{
      foundTextIndex=view.getText().toLowerCase().indexOf(findText.toLowerCase(),position);
    }

    if(foundTextIndex<0){
      return false;
    }

    if(findText.equals(view.getText())){
      return true;
    }

    if(wholeWord){
      if((foundTextIndex-1)<0 || (foundTextIndex+findText.length()+1)>view.getText().length()){
        return false; //return false if we don't have at least a character before or after the word
      }
      java.util.Arrays.sort(wordSeparatorChars);
      if(java.util.Arrays.binarySearch(wordSeparatorChars,view.getText().charAt(foundTextIndex-1))<0){
        return false;
      }
      if(java.util.Arrays.binarySearch(wordSeparatorChars,view.getText().charAt(foundTextIndex+findText.length()+1))<0){
        return false;
      }
    }

    view.setCaretPosition(foundTextIndex);
    view.moveCaretPosition(foundTextIndex+findText.length());
    //view.select(foundTextIndex,foundTextIndex+findText.length());
    //view.setCaretPosition(foundTextIndex+findText.length());
    return true;
  }

  private boolean replaceRegularText(String findText,String replaceText,int position,boolean caseSensitive,boolean wholeWord){
      int foundTextIndex=-1;
      if(caseSensitive){
        foundTextIndex=view.getText().indexOf(findText,position);
      }else{
        foundTextIndex=view.getText().toLowerCase().indexOf(findText.toLowerCase(),position);
      }

      if(foundTextIndex<0){
        return false;
      }

      if(findText.equals(view.getText())){
        return true;
      }

      if(wholeWord){
        if((foundTextIndex-1)<0 || (foundTextIndex+findText.length()+1)>view.getText().length()){
          return false; //return false if we don't have at least a character before or after the word
        }
        java.util.Arrays.sort(wordSeparatorChars);
        if(java.util.Arrays.binarySearch(wordSeparatorChars,view.getText().charAt(foundTextIndex-1))<0){
          return false;
        }
        if(java.util.Arrays.binarySearch(wordSeparatorChars,view.getText().charAt(foundTextIndex+findText.length()+1))<0){
          return false;
        }
      }

      view.setCaretPosition(foundTextIndex);
      view.moveCaretPosition(foundTextIndex+findText.length());

      int option=JOptionPane.showConfirmDialog(this,"Replace this text?","Confirmation",JOptionPane.YES_NO_OPTION);
      if(option==JOptionPane.YES_OPTION){
        StringBuffer buf=new StringBuffer(view.getText());
        buf.replace(foundTextIndex,foundTextIndex+findText.length(),replaceText);
        view.setText(buf.toString());
        view.setCaretPosition(foundTextIndex);
      }

      return true;
  }

  private boolean findRegexpText(String findText,int position,boolean caseSensitive,boolean wholeWord){
    //this is not good, it has to be replaced
    return true;// java.util.regex.Pattern.matches(findText,view.getText());
  }

  void findMenuItem_actionPerformed(ActionEvent e) {
      final FindTextDialog find=new FindTextDialog(JOptionPane.getFrameForComponent(this),"Find",false);
      find.setFindListener(new FindListener(){
        public void find(String findText,String replaceText,boolean caseSensitive,boolean fileStart,boolean wholeWords,boolean regexp,int count){
          if(count==0 && fileStart){
            view.setCaretPosition(0);
          }
          int startFrom=view.getCaretPosition();
          if(!regexp && (replaceText==null)){
            if(!findRegularText(findText,startFrom,caseSensitive,wholeWords)){
              JOptionPane.showMessageDialog(find,"Text not found");
            }
          }

          if(regexp && (replaceText==null || replaceText.length()<=0)){
            findRegexpText(findText,startFrom,caseSensitive,wholeWords);
          }


          if(replaceText!=null){
            if(!replaceRegularText(findText,replaceText,startFrom,caseSensitive,wholeWords)){
              JOptionPane.showMessageDialog(find,"Text not found");
            }
          }

        }

        public void all(String findText,String replaceText,boolean caseSensitive,boolean fileStart,boolean wholeWords,boolean regexp){
          if(fileStart){
            view.setCaretPosition(0);
          }
          int startFrom=view.getCaretPosition();
          while(replaceRegularText(findText,replaceText,startFrom,caseSensitive,wholeWords));
        }

      });
      find.setLocationRelativeTo(this);
      find.setVisible(true);
  }
}
