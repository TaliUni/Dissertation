/*
 * Created on 1-Sep-2004
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package org.jfm.views;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.border.LineBorder;

/**
 * This class creates a nice dotted border
 * @author sergiu
 */
public class CellBorder extends LineBorder {

    final static float dash1[] = {2.0f};
    final static BasicStroke dashed = new BasicStroke(0.2f,BasicStroke.CAP_BUTT,BasicStroke.JOIN_MITER,10.0f, dash1, 0.0f);
	
	/**
	 * @param color
	 */
	public CellBorder(Color color) {
		super(color);
	}

	/**
	 * @param color
	 * @param thickness
	 */
	public CellBorder(Color color, int thickness) {
		super(color, thickness);
	}

	/**
	 * @param color
	 * @param thickness
	 * @param roundedCorners
	 */
	public CellBorder(Color color, int thickness, boolean roundedCorners) {
		super(color, thickness, roundedCorners);		
	}
	
	public void setLineColor(Color c){
		lineColor=c;
	}
	
    /**
     * Paints the border for the specified component with the 
     * specified position and size.
     * @param c the component for which this border is being painted
     * @param g the paint graphics
     * @param x the x position of the painted border
     * @param y the y position of the painted border
     * @param width the width of the painted border
     * @param height the height of the painted border
     */
    public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
    	Graphics2D g2=(Graphics2D)g;
    	g2.setStroke(dashed);
        Color oldColor = g2.getColor();
        int i;        
        g2.setColor(lineColor);
        for(i = 0; i < thickness; i++)  {
	    if(!roundedCorners)
                g2.drawRect(x+i, y+i, width-i-i-1, height-i-i-1);
	    else
                g2.drawRoundRect(x+i, y+i, width-i-i-1, height-i-i-1, 3, 3);
        }
        g2.setColor(oldColor);
    }
}
