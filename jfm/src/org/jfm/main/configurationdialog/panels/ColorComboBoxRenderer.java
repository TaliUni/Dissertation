/*
 * Created on 11-Oct-2004
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package org.jfm.main.configurationdialog.panels;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.ListCellRenderer;
import javax.swing.UIManager;
import javax.swing.border.CompoundBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.MatteBorder;

/**
 * The cell renderer for the color selection combobox
 * @author sergiu
 */
public class ColorComboBoxRenderer extends JPanel implements ListCellRenderer {
     
	 private Color   m_color = Color.black;
     private Color   m_focusColor = (Color)UIManager.get("List.selectionBackground");
     private Color m_nonFocusColor = Color.white;

	
	
	public ColorComboBoxRenderer(){
		super();
		setPreferredSize(new Dimension(100,15));
	}
	
	    public Component getListCellRendererComponent(
        JList list,Object value,int index,
        boolean isSelected,boolean cellHasFocus)
    {
	    	if(isSelected || cellHasFocus){
	    		setBorder(new CompoundBorder(new MatteBorder(2, 10, 2, 10, m_focusColor), new LineBorder(Color.black)));
	    	}else{
	    		setBorder(new CompoundBorder(new MatteBorder(2, 10, 2, 10, m_nonFocusColor), new LineBorder(Color.black)));	    		
	    	}
	    	if(value instanceof Color){
	    		m_color = (Color)value;
	    	}	    	    	
	    	return this;
    }
	    
    public void paintComponent(Graphics g)
    {
        setBackground(m_color);
        super.paintComponent(g);
    }
	    
}
