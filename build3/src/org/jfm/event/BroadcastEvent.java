package org.jfm.event;

/**This is the base class for any event*/
public abstract class BroadcastEvent {

  private Object source;
  private Object userObj;
  
  public static final int UNKNOWN_TYPE=0;
  public static final int CHANGE_DIR_TYPE=1;
  public static final int CHANGE_PANEL_TYPE=2;
  public static final int CHANGE_VIEW_TYPE=3;
  public static final int CHANGE_FONT_TYPE=4;
  public static final int FILE_LIST_SELECTION_TYPE=6;
  public static final int CHANGE_FILESYSTEM_TYPE=7;
  public static final int CHANGE_FGCOLOR_TYPE=8;
  public static final int CHANGE_BGCOLOR_TYPE=9;
  public static final int CHANGE_MKCOLOR_TYPE=10;
  public static final int CHANGE_HELP_URL=11;
  
  protected BroadcastEvent() {
  }

  /**Constructs an event with the specified source. */
  public BroadcastEvent(Object source) {
    this.source = source;
  }

  /**Returns the source. */
  public Object getSource() {
    return source;
  }

  /**Sets the source who generated the event. */
  public void setSource(Object newSource) {
    source = newSource;
  }

  /**Sets the user object. */
  public void setUserObject(Object obj)
  {
     userObj=obj;
  }

  /**Returns the user object. */
  public Object getUserObject()
  {
     return userObj;
  }
	/**
	 * @return Returns the type of the event.
	 */
	public abstract int getType();

	
	public boolean equals(Object obj){
		if(obj==null || !(obj instanceof BroadcastEvent)){
			return false;
		}
		
		return getType()==((BroadcastEvent)obj).getType();
	}
}

