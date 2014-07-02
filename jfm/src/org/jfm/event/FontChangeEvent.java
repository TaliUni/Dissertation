/*
 * Created on Sep 8, 2004
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package org.jfm.event;

import java.awt.Font;

/**
 * @author sergiu
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class FontChangeEvent extends BroadcastEvent {

	private Font font;
	
	
	/**
	 * @return Returns the font.
	 */
	public Font getFont() {
		return font;
	}
	/**
	 * @param font The font to set.
	 */
	public void setFont(Font font) {
		this.font = font;
	}
	
	/* (non-Javadoc)
	 * @see org.jfm.event.BroadcastEvent#getType()
	 */
	public int getType() {
		return BroadcastEvent.CHANGE_FONT_TYPE;
	}
	
}
