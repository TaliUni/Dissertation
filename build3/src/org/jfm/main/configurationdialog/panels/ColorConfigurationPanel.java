/*
 * Created on 11-Oct-2004
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package org.jfm.main.configurationdialog.panels;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JComboBox;
import javax.swing.JPanel;

import org.jfm.event.ColorChangeEvent;
import org.jfm.main.Options;
import org.jfm.main.configurationdialog.ConfigurationEventsQueue;

/**
 * The panel for changing colors
 * @author sergiu
 */
public class ColorConfigurationPanel extends ConfigurationPanel {
	
	private JPanel panel=null;
	
	public ColorConfigurationPanel(String name,String title){
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
		//panel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(),"Colors"));
		panel.setLayout(new GridBagLayout());
		JPanel foregroundColorPanel=setupForegroundColorPanel();
		JPanel backgroundColorPanel=setupBackgroundColorPanel();
		JPanel markedColorPanel=setupMarkedColorPanel();
		panel.add(foregroundColorPanel,new GridBagConstraints(0,0,1,1,1,0,GridBagConstraints.CENTER,GridBagConstraints.HORIZONTAL,new Insets(10,0,0,0),0,0));
		panel.add(backgroundColorPanel,new GridBagConstraints(0,1,1,1,1,0,GridBagConstraints.CENTER,GridBagConstraints.HORIZONTAL,new Insets(5,0,0,0),0,0));
		panel.add(markedColorPanel,new GridBagConstraints(0,2,1,1,1,0,GridBagConstraints.CENTER,GridBagConstraints.HORIZONTAL,new Insets(5,0,0,0),0,0));
		panel.add(new JPanel(),new GridBagConstraints(0,3,1,1,1,1,GridBagConstraints.CENTER,GridBagConstraints.BOTH,new Insets(0,0,0,0),0,0));		
	}
	
	private JPanel setupForegroundColorPanel(){
		JPanel p=new JPanel();
		p.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(),"Font color"));
		
		JComboBox combo=new JComboBox();
		combo.setEditable(false);
		combo.setRenderer(new ColorComboBoxRenderer());
		final DefaultComboBoxModel model=new DefaultComboBoxModel();
		combo.setModel(model);
		addComboItems(model);
		Color defaultColor=Options.getForegroundColor();
		if(model.getIndexOf(defaultColor)>=0){
			model.setSelectedItem(defaultColor);
		}else{
			model.addElement(defaultColor);
			model.setSelectedItem(defaultColor);			
		}
		JButton button=new JButton("Add custom");
		button.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				Color c=JColorChooser.showDialog(ColorConfigurationPanel.this,"Font color",Options.getForegroundColor());
			  	if(c==null) return;
				model.addElement(c);
				model.setSelectedItem(c);
			}
		});
		JButton rbutton=new JButton("Reset");
		rbutton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				Color c=Options.getDefaultForegroundColor();
			  	if(c==null) return;
				if(model.getIndexOf(c)>=0){
					model.setSelectedItem(c);
				}else{
					model.addElement(c);
					model.setSelectedItem(c);			
				}
			}
		});
		
		p.add(combo);
		p.add(button);
		p.add(rbutton);
		combo.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
			  	ColorChangeEvent event=new ColorChangeEvent(ColorChangeEvent.FOREGROUND);
			  	event.setSource(this);
				event.setColor((Color)model.getSelectedItem());
				ConfigurationEventsQueue.addPendingEvent(event);				
			}
		});
		return p;
	}

	private JPanel setupBackgroundColorPanel(){
		JPanel p=new JPanel();
		p.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(),"Panel color"));
		JComboBox combo=new JComboBox();
		combo.setEditable(false);
		combo.setRenderer(new ColorComboBoxRenderer());
		final DefaultComboBoxModel model=new DefaultComboBoxModel();
		combo.setModel(model);
		addComboItems(model);
		Color defaultColor=Options.getBackgroundColor();
		if(model.getIndexOf(defaultColor)>=0){
			model.setSelectedItem(defaultColor);
		}else{
			model.addElement(defaultColor);
			model.setSelectedItem(defaultColor);			
		}
		
		JButton button=new JButton("Add custom");
		button.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				Color c=JColorChooser.showDialog(ColorConfigurationPanel.this,"Panel color",Options.getForegroundColor());
			  	if(c==null) return;
				model.addElement(c);
				model.setSelectedItem(c);				
			}
		});
		JButton rbutton=new JButton("Reset");
		rbutton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				Color c=Options.getDefaultBackgroundColor();
			  	if(c==null) return;
				if(model.getIndexOf(c)>=0){
					model.setSelectedItem(c);
				}else{
					model.addElement(c);
					model.setSelectedItem(c);			
				}
			}
		});
		
		p.add(combo);
		p.add(button);	
		p.add(rbutton);
		combo.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
			  	ColorChangeEvent event=new ColorChangeEvent(ColorChangeEvent.BACKGROUND);
			  	event.setSource(this);
				event.setColor((Color)model.getSelectedItem());
				ConfigurationEventsQueue.addPendingEvent(event);				
			}
		});		
		return p;
	}

	private JPanel setupMarkedColorPanel(){
		JPanel p=new JPanel();
		p.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(),"Marked color"));
		JComboBox combo=new JComboBox();
		combo.setEditable(false);
		combo.setRenderer(new ColorComboBoxRenderer());
		final DefaultComboBoxModel model=new DefaultComboBoxModel();
		combo.setModel(model);
		addComboItems(model);
		Color defaultColor=Options.getMarkedColor();
		if(model.getIndexOf(defaultColor)>=0){
			model.setSelectedItem(defaultColor);
		}else{
			model.addElement(defaultColor);
			model.setSelectedItem(defaultColor);			
		}
		
		JButton button=new JButton("Add custom");
		button.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				Color c=JColorChooser.showDialog(ColorConfigurationPanel.this,"Marked color",Options.getMarkedColor());
			  	if(c==null) return;
				model.addElement(c);
				model.setSelectedItem(c);				
			}
		});
		JButton rbutton=new JButton("Reset");
		rbutton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				Color c=Options.getDefaultMarkedColor();
			  	if(c==null) return;
				if(model.getIndexOf(c)>=0){
					model.setSelectedItem(c);
				}else{
					model.addElement(c);
					model.setSelectedItem(c);			
				}
			}
		});
		
		p.add(combo);
		p.add(button);	
		p.add(rbutton);
		combo.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
			  	ColorChangeEvent event=new ColorChangeEvent(ColorChangeEvent.MARKED);
			  	event.setSource(this);
				event.setColor((Color)model.getSelectedItem());
				ConfigurationEventsQueue.addPendingEvent(event);				
			}
		});		
		return p;
	}
	
	private void addComboItems(DefaultComboBoxModel model){
		model.addElement(Color.white);
		model.addElement(Color.lightGray);
		model.addElement(Color.gray);
		model.addElement(Color.darkGray);
		model.addElement(Color.black);
		model.addElement(Color.red);
		model.addElement(Color.pink);
		model.addElement(Color.orange);
		model.addElement(Color.yellow);
		model.addElement(Color.green);
		model.addElement(Color.magenta);
		model.addElement(Color.cyan);
		model.addElement(Color.blue);
	}
}
