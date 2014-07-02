package org.jfm.po;

import javax.swing.Action;
import java.beans.PropertyChangeListener;
import java.awt.event.ActionEvent;
import org.jfm.main.Options;
import org.jfm.views.fview.*;
import org.jfm.filesystems.JFMFile;

/**
 * Title:        Java File Manager
 * Description:
 * Copyright:    Copyright (c) 2001
 * Company:      Home
 * @author Giurgiu Sergiu
 * @version 1.0
 */

public class EditFileAction implements Action {

  public EditFileAction() {
  }
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

  public void actionPerformed(ActionEvent e) {
     JFMFile viewFile;
     viewFile=Options.getActivePanel().getSelectedFile();
     /*if(Options.getActivePanel()==Options.LEFT_PANEL){
      int firstSelectedFileIndex=Options.getLeftPanelSelections()[0];
      if(Options.getLeftFiles()==null || Options.getLeftFiles().size()<=0) return;

      viewFile=(FileElement)Options.getLeftFiles().elementAt(firstSelectedFileIndex);
     }else{
      int firstSelectedFileIndex=Options.getRightPanelSelections()[0];
      if(Options.getRightFiles()==null || Options.getRightFiles().size()<=0) return;
      viewFile=(FileElement)Options.getRightFiles().elementAt(firstSelectedFileIndex);
     }*/

      FileViewDialog d=new FileViewDialog(Options.getMainFrame(),viewFile.getPath(),false);
      d.setLocationRelativeTo(Options.getMainFrame());
      d.setContent(viewFile,true);
      d.setVisible(true);
  }

}
