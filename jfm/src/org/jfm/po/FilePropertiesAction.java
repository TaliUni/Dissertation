package org.jfm.po;

import java.beans.PropertyChangeListener;
import java.awt.event.ActionEvent;
import javax.swing.Action;
import org.jfm.main.Options;

import org.jfm.filesystems.*;


/**
 * <p>Title: Java File Manager</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2001</p>
 * <p>Company: Home</p>
 * @author Giurgiu Sergiu
 * @version 1.0
 */

public class FilePropertiesAction implements Action {

  public FilePropertiesAction() {
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
    int[] indexes=null;
    java.util.Vector files=null;

    //@todo MUST GET THE SELECTED FILES FROM SOMEWHERE
    /*if(Options.getActivePanel()==Options.LEFT_PANEL){
      files=Options.getLeftFiles();
      indexes=Options.getLeftPanelSelections();
    }else{
      files=Options.getRightFiles();
      indexes=indexes=Options.getRightPanelSelections();
    }    */

    if(indexes==null || indexes.length<=0){
      return;
    }

    if(indexes[0]<0 || indexes[1]<0){
      return;
    }

    if(files==null || files.size()<=0){
      return;
    }

    JFMFile el=(JFMFile)files.elementAt(indexes[0]);
    FilePropertiesDialog d=new FilePropertiesDialog(Options.getMainFrame(),"File Properties",false);
    d.setFile(el);
    d.setLocationRelativeTo(Options.getMainFrame());
    d.setVisible(true);
  }

}
