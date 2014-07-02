/*
 * Created on Sep 8, 2004
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package org.jfm.event;

import java.awt.Color;

/**
 * The event that gets fired when the color changes.
 * @author sergiu
 */
public class ColorChangeEvent extends BroadcastEvent {
	
	/**
	 * The type that means the background was changed
	 */
	public static final int BACKGROUND=0;
	
	/**
	 * The type that means the foreground was changed
	 */
	public static final int FOREGROUND=1;
	
	/**
	 * The type that means the mark color was changed
	 */
	public static final int MARKED=2;
	
	
	private int type;
	private Color color;
	
	public ColorChangeEvent(){
		this(BACKGROUND);
	}
	
	public ColorChangeEvent(int type){
		setColorType(type);
	}	
	
	public int getType(){
		int t=BroadcastEvent.UNKNOWN_TYPE;
		
		switch(type){
			case FOREGROUND:
				t=BroadcastEvent.CHANGE_FGCOLOR_TYPE;
				break;
			case BACKGROUND:
				t=BroadcastEvent.CHANGE_BGCOLOR_TYPE;
				break;
			case MARKED:
				t=BroadcastEvent.CHANGE_MKCOLOR_TYPE;
				break;
			default:
				t=BroadcastEvent.UNKNOWN_TYPE;
				break;
		}
		return t;		
	}
	
	/**
	 * @return Returns the type.
	 */
	public int getColorType() {
		return type;
	}
	/**
	 * @param type The type to set.
	 */
	public void setColorType(int type) {
		this.type = type;
	}
	/**
	 * @return Returns the color.
	 */
	public Color getColor() {
		return color;
	}
	/**
	 * @param color The color to set.
	 */
	public void setColor(Color color) {
		this.color = color;
	}
}
