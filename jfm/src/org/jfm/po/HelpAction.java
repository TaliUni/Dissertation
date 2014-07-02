package org.jfm.po;

import javax.swing.Action;
import javax.swing.SwingUtilities;

import org.jfm.help.HelpBrowser;
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

public class HelpAction implements Action {
  private boolean enabled=true;
  
  public HelpAction() {
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
	  final HelpBrowser browser=new HelpBrowser(null,"JFM Help",false);
	  browser.setBaseURL(Options.getPreferences().get(Options.JFM_HELP_URL,"http://localhost/index.html"));
	  SwingUtilities.invokeLater(new Runnable() {
		public void run() {
			browser.loadHomePage();
		}
	});
	  browser.setVisible(true);
  }
}