/*
 * Created on 25-Sep-2004
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package org.jfm.main.configurationdialog.panels;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.RenderingHints;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

/**
 * This panel represents a panel in the configuration dialog, that is mainly used
 * for one type of settings. This the default one. all others are strongly encouraged to extend this one.
 * @author sergiu
 */
public class ConfigurationPanel extends JPanel {
	
	private String title;
	protected JLabel titleLabel=null;
	private JPanel presentationPanel=new JPanel();
	
	public ConfigurationPanel(String name,String title){
		super(true);
		setName(name);
		setTitle(title);
		createTitleLabel();
		init();
	}
	
	private void createTitleLabel(){
		titleLabel=new JLabel(){
			public void paintComponent(Graphics g){		
				//this is here of for eye-candy purposes
				Dimension size=getSize();
				Graphics2D g2=(Graphics2D)g;
				Paint oldPaint=g2.getPaint();
				g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
				g2.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS, RenderingHints.VALUE_FRACTIONALMETRICS_ON);
				g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
				g2.setPaint(new GradientPaint(0,0,new Color(0,0,150),size.width,size.height,new Color(240,240,240)));
				g2.fillRect(0,0,size.width,size.height);
				g2.setPaint(oldPaint);
				super.paintComponent(g);
			}
		};
		titleLabel.setForeground(Color.WHITE);
		titleLabel.setText(getTitle());
		titleLabel.setFont(titleLabel.getFont().deriveFont(Font.BOLD));
		titleLabel.setHorizontalAlignment(SwingConstants.CENTER);		
	}
	
	/**
	 * This method should be overridden by the subclasses
	 */
	protected void init(){
		setLayout(new BorderLayout());
		add(titleLabel,BorderLayout.NORTH);
		add(presentationPanel,BorderLayout.CENTER);
		
		presentationPanel.setLayout(new BorderLayout());
		JLabel panelText=new JLabel("<html><body><b>Configuration panel for Java File Manager v 0.6</b></body></html>");
		panelText.setHorizontalAlignment(JLabel.CENTER);
		
		presentationPanel.add(panelText,BorderLayout.CENTER);
	}
	
	
	
	public String toString(){
		return getName();
	}
	/**
	 * @return Returns the title.
	 */
	public String getTitle() {
		return title;
	}
	/**
	 * @param title The title to set.
	 */
	public void setTitle(String title) {
		this.title = title;
	}
}
