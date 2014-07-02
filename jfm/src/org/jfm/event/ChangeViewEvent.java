package org.jfm.event;

import org.jfm.views.JFMViewRepresentation;

/**
 * <p>Title: Java File Manager</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2001</p>
 * <p>Company: Home</p>
 * @author Giurgiu Sergiu
 * @version 1.0
 */

public class ChangeViewEvent extends BroadcastEvent {

	private JFMViewRepresentation viewRep;
	private String filesystemClassName;
	
  public ChangeViewEvent() {
  }

	/* (non-Javadoc)
	 * @see org.jfm.event.BroadcastEvent#getType()
	 */
	public int getType() {
		return BroadcastEvent.CHANGE_VIEW_TYPE;
	}
  
  /**
	 * @return Returns the viewRep.
	 */
	public JFMViewRepresentation getViewRep() {
		return viewRep;
	}
	/**
	 * @param viewRep The viewRep to set.
	 */
	public void setViewRep(JFMViewRepresentation viewRep) {
		this.viewRep = viewRep;
	}

	/**
	 * @return the filesystemClassName
	 */
	public String getFilesystemClassName() {
		return filesystemClassName;
	}

	/**
	 * @param filesystemClassName the filesystemClassName to set
	 */
	public void setFilesystemClassName(String filesystemClassName) {
		this.filesystemClassName = filesystemClassName;
	}
}