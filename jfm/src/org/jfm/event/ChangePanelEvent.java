package org.jfm.event;

/**
 * Title:        Java File Manager
 * Description:  
 * Copyright:    Copyright (c) 2001
 * Company:      Home
 * @author Giurgiu Sergiu
 * @version 1.0
 */

public class ChangePanelEvent extends BroadcastEvent {

  public ChangePanelEvent() {
  }
  
	/* (non-Javadoc)
	 * @see org.jfm.event.BroadcastEvent#getType()
	 */
	public int getType() {
		return BroadcastEvent.CHANGE_PANEL_TYPE;
	}
	
  private int location;
  public int getLocation() {
    return location;
  }
  public void setLocation(int location) {
    this.location = location;
  }
}