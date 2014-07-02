package org.jfm.event;

/**
 * Title:        Java File Manager
 * Description:  
 * Copyright:    Copyright (c) 2001
 * Company:      Home
 * @author Giurgiu Sergiu
 * @version 1.0
 */

public interface ChangePanelListener extends BroadcastListener {
  public void changePanel(ChangePanelEvent e);
}