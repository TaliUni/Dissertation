package org.jfm.views.fview;

import javax.swing.*;

import java.awt.BorderLayout;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import org.jfm.main.Options;
import org.jfm.event.*;
import org.jfm.views.JFMView;

import org.jfm.filesystems.JFMFile;



/**
 * Title:        Java File Manager
 * Description:
 * Copyright:    Copyright (c) 2001
 * Company:      Home
 * @author Giurgiu Sergiu
 * @version 1.0
 */

public class FileViewPanel extends JFMView {

  private JScrollPane scroll=new JScrollPane();
  private FView view=new FView();
  //the file that is viewed
  private JFMFile file=null;

  private void setViewFile(){
    int[] indexes=null;
    java.util.Vector files=null;
    // Must get the selected files from the other panel
  /*  int l=getPanelLocation();
    if(l==Options.RIGHT_PANEL){
      files=Options.getLeftFiles();
      indexes=Options.getLeftPanelSelections();
    }else{
      files=Options.getRightFiles();
      indexes=indexes=Options.getRightPanelSelections();
    }*/

    if(indexes==null || indexes.length<=0){
      return;
    }

    if(files==null || files.size()<=0){
      return;
    }

    file=(JFMFile)files.elementAt(indexes[0]);
    view.setEl(file);
    statusLabel.setText(file.getPath());
  }

  
  public JFMFile[] getSelectedFiles(){
    return new JFMFile[]{file};
  }

  public JFMFile getSelectedFile(){
    return file;
  }

  public JFMFile getCurrentWorkingDirectory(){
    return file.getParentFile();
  }

  public void requestFocus(){
    super.requestFocus();
    view.requestFocus();
  }

  public FileViewPanel(String fileSystem) {
	 super(fileSystem);
    try {
      jbInit();
    }
    catch(Exception ex) {
      ex.printStackTrace();
    }
  }

  private void jbInit() throws Exception{
    this.setLayout(new BorderLayout());
    this.add(scroll,BorderLayout.CENTER);
    this.add(statusLabel,BorderLayout.NORTH);
    scroll.setViewportView(view);
    Broadcaster.addFileListSelectionListener(new FileListSelectionListener(){
       public void fileListSelectionChanged(FileListSelectionEvent ev){
          if(!FileViewPanel.this.equals(ev.getSource())){
            setViewFile();
          }
       }
    });

    statusLabel.setForeground(UIManager.getColor("Label.disabledForeground"));
    view.addFocusListener(new FocusListener(){
      public void focusGained(FocusEvent e){
        statusLabel.setForeground(UIManager.getColor("Label.foreground"));
        Options.setActivePanel(FileViewPanel.this);
      }

      public void focusLost(FocusEvent e){
        statusLabel.setForeground(UIManager.getColor("Label.disabledForeground"));
      }
    });
  }
}
