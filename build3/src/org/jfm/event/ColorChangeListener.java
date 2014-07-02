/*
 * Created on Sep 8, 2004
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package org.jfm.event;

/**
 * The listener that get's notified when the  color changes. 
 * @author sergiu
 */
public interface ColorChangeListener extends BroadcastListener {
	
	public void colorChanged(ColorChangeEvent event); 
}
