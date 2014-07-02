package org.jfm.po;

import javax.swing.Action;

import org.jfm.main.Options;

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

public class QuitAction implements Action {

  public QuitAction() {
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
	 Options.savePreferences();
    System.exit(0);
  }

}