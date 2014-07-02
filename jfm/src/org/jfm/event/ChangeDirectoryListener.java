package org.jfm.event;

/**
 * Title:        Java File Manager
 * Description:  
 * Copyright:    Copyright (c) 2001
 * Company:      Home
 * @author Giurgiu Sergiu
 * @version 1.0
 */

public interface ChangeDirectoryListener extends BroadcastListener {
  
  public void changeDirectory(ChangeDirectoryEvent event);
}