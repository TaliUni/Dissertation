package org.jfm.event;

import org.jfm.filesystems.JFMFile;

/**
 * Title:        Java File Manager
 * Description:
 * Copyright:    Copyright (c) 2001
 * Company:      Home
 * @author Giurgiu Sergiu
 * @version 1.0
 */

public class ChangeDirectoryEvent extends BroadcastEvent {

	/* (non-Javadoc)
	 * @see org.jfm.event.BroadcastEvent#getType()
	 */
	public int getType() {
		return BroadcastEvent.CHANGE_DIR_TYPE;
	}
	
  public ChangeDirectoryEvent() {
    this(null);
  }

  public ChangeDirectoryEvent(JFMFile dir) {
    setDirectory(dir);
  }

  private JFMFile directory;
  public JFMFile getDirectory() {
    return directory;
  }
  public void setDirectory(JFMFile directory) {
    this.directory = directory;
  }
}
