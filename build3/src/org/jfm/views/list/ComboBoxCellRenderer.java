/*
 * Created on Aug 31, 2004
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package org.jfm.views.list;

import java.awt.Component;

import javax.swing.JList;
import javax.swing.JLabel;
import javax.swing.DefaultListCellRenderer;


/**
 * This renderer displays an icon along with the text, if the value is of type JFMFile
 * @author sergiu
 */
public class ComboBoxCellRenderer extends DefaultListCellRenderer {

	/* (non-Javadoc)
	 * @see javax.swing.ListCellRenderer#getListCellRendererComponent(javax.swing.JList, java.lang.Object, int, boolean, boolean)
	 */
	public Component getListCellRendererComponent(JList list, Object value,
			int index, boolean isSelected, boolean cellHasFocus) {
		JLabel component=(JLabel)super.getListCellRendererComponent(list,value,index,isSelected,cellHasFocus);
		
		if(value instanceof ComboBoxCellObject){
			ComboBoxCellObject obj=(ComboBoxCellObject)value;
			if(obj.getFile()!=null){
				component.setIcon(obj.getFile().getIcon());
			}
		}
		
		return component;
	}

}
