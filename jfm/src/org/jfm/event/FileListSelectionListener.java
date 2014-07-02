package org.jfm.event;

/**
 * <p>Title: Java File Manager</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2001</p>
 * <p>Company: Home</p>
 * @author Giurgiu Sergiu
 * @version 1.0
 */

public interface FileListSelectionListener extends BroadcastListener {
  
  public void fileListSelectionChanged(FileListSelectionEvent ev);
}