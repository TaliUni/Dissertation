package org.jfm.po;

import javax.swing.Action;
import java.beans.PropertyChangeListener;
import java.awt.event.ActionEvent;

/**
 * Title:        Java File Manager
 * Description:  
 * Copyright:    Copyright (c) 2001
 * Company:      Home
 * @author Giurgiu Sergiu
 * @version 1.0
 */

public class PanelMenuAction implements Action {
  boolean enabled=true;
  
  public PanelMenuAction() {
  }
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
    javax.swing.JOptionPane.showMessageDialog(null,"Not yet implemented");
  }
}