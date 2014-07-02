/*
 * Created on 26-Sep-2004
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package org.jfm.main.configurationdialog.panels;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;


import org.jfm.event.FontChangeEvent;
import org.jfm.main.Options;
import org.jfm.main.configurationdialog.ConfigurationEventsQueue;
import org.jfm.views.FontDialog;

/**
 * The fonts configuration panel
 * @author sergiu
 */
public class FontConfigurationPanel extends ConfigurationPanel {
	
	private JPanel panel=null;
	
	public FontConfigurationPanel(String name,String title){
		super(name,title);
	}
	
	protected void init(){
		setLayout(new BorderLayout());
		setPanel();		
		
		add(titleLabel,BorderLayout.NORTH);
		add(panel,BorderLayout.CENTER);
	}
	
	private void setPanel(){
		panel=new JPanel();
		panel.setLayout(new GridBagLayout());
		JPanel fileListFontPanel=setupFileListFontPanel();
		panel.add(fileListFontPanel,new GridBagConstraints(0,0,1,1,1,0,GridBagConstraints.CENTER,GridBagConstraints.HORIZONTAL,new Insets(10,0,10,0),0,0));
		panel.add(new JPanel(),new GridBagConstraints(0,1,1,1,1,1,GridBagConstraints.CENTER,GridBagConstraints.BOTH,new Insets(0,0,0,0),0,0));
	}
	
	private JPanel setupFileListFontPanel(){
		JPanel p=new JPanel();
		p.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(),"File list font"));
		p.setLayout(new BorderLayout());
		final JLabel fontDescriptionLabel=new JLabel();
		Font font= Options.getPanelsFont();
		fontDescriptionLabel.setFont(font);
		String	strStyle;
		if (font.isBold()) {
			strStyle = font.isItalic() ? "BoldItalic" : "Bold";		
		} else {
			strStyle = font.isItalic() ? "Italic" : "Plain";
		}		 
		
		fontDescriptionLabel.setText( font.getName() + ", " + strStyle + ", " + font.getSize());
		
		JButton changeFontButton=new JButton("Change font");
		changeFontButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
			  	Font f=FontDialog.showDialog(JOptionPane.getFrameForComponent(FontConfigurationPanel.this),"Choose font",true);
			  	if(f==null)return;
				String	strStyle;
				if (f.isBold()) {
					strStyle = f.isItalic() ? "BoldItalic" : "Bold";		
				} else {
					strStyle = f.isItalic() ? "Italic" : "Plain";
				}		 
			  	fontDescriptionLabel.setFont(f);
			  	fontDescriptionLabel.setText( f.getName() + ", " + strStyle + ", " + f.getSize());			  	
			  	FontChangeEvent event=new FontChangeEvent();
			  	event.setSource(this);
			  	event.setFont(f);
			  	ConfigurationEventsQueue.addPendingEvent(event);
			}
		});
		p.add(fontDescriptionLabel,BorderLayout.CENTER);
		p.add(changeFontButton,BorderLayout.EAST);
		return p;
	}
}
