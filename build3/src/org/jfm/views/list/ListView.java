package org.jfm.views.list;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

import org.jfm.main.Options;
import org.jfm.views.JFMView;
import org.jfm.event.Broadcaster;
import org.jfm.event.ChangeDirectoryEvent;
import org.jfm.event.ChangeDirectoryListener;
import org.jfm.filesystems.JFMFile;

/**
 * Title:        Java File Manager
 * Description:
 * Copyright:    Copyright (c) 2001
 * Company:      Home
 * @author Giurgiu Sergiu
 * @version 1.0
 */

public abstract class ListView extends JFMView {

  protected JScrollPane scroll = null;
  protected JPanel topPanel=null;
  protected JComboBox rootsCombo=new JComboBox();
  protected ComboBoxCellRenderer comboRenderer=new ComboBoxCellRenderer();
  private boolean shouldFireComboEvent=true;
  
  public ListView(String fs) {
  	super(fs);
    try {
      jbInit();
    }
    catch(Exception ex) {
      ex.printStackTrace();
    }  	
  }

  private void jbInit() throws Exception {
  	topPanel=new JPanel(new FlowLayout(FlowLayout.LEFT)){
    	public void paintComponent(Graphics g){
    		super.paintComponent(g);
				/*Dimension size=getSize();
				Graphics2D g2=(Graphics2D)g;
				Paint oldPaint=g2.getPaint();
				g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
				g2.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS, RenderingHints.VALUE_FRACTIONALMETRICS_ON);
				g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
				g2.setPaint(new GradientPaint(0,0,new Color(0,0,150),size.width,size.height,new Color(240,240,240)));
				g2.fillRect(0,0,size.width,size.height);
				g2.setPaint(oldPaint);*/
				
    	}  		
  	};
    topPanel.add(rootsCombo);
    topPanel.add(statusLabel);
    setupRootsCombo();        
    Options.setActivePanel(this);
    statusLabel.setText(filesystem.getStartDirectory().getAbsolutePath());
    statusLabel.setForeground(UIManager.getColor("Label.disabledForeground"));
    Broadcaster.addChangeDirectoryListener(new ChangeDirectoryListener(){
      public void changeDirectory(ChangeDirectoryEvent e){
    	  if(getCurrentWorkingDirectory()!=null)
    	  {
    		  JFMFile f= getCurrentWorkingDirectory().getRootDriveFile();
    		  for(int i=0;i<rootsCombo.getItemCount();i++)
    		  {
    			ComboBoxCellObject obj=  (ComboBoxCellObject)rootsCombo.getItemAt(i);
    			if(obj.getFile().equals(f))
    			{
    				shouldFireComboEvent=false;
    				//rootsCombo.setSelectedIndex(i);
    			}
    		  }
    	  }
      }
    });
    
  }

  public void requestFocus(){
    //super.requestFocus();
    //getViewComponent().requestFocus();
  	getViewComponent().requestFocusInWindow();
  }
  
  /**
   * This method returns the current selected file from the view. If no file is selected it just returns null.
   * @return JFMFile the current selected file.
   */
  public abstract JFMFile getSelectedFile();

  /**
   * This method returns the current selected files from the view. If no files are selected it just returns null.
   * @return JFMFile the current selected files.
   */
  public abstract JFMFile[] getSelectedFiles();

  public abstract JFMFile getCurrentWorkingDirectory();
  

    private void setupRootsCombo(){
    	rootsCombo.setRenderer(comboRenderer);
    	rootsCombo.setEditable(false);    	
    	JFMFile[] roots=filesystem.listRoots();
    	if(roots!=null){
    		for(int i=0;i<roots.length;i++){
    			rootsCombo.addItem(new ComboBoxCellObject(roots[i]));
    			if(filesystem!=null){
    				@SuppressWarnings("unused")
    				JFMFile startDir=filesystem.getStartDirectory();
    				//JFMFile curDir=getCurrentWorkingDirectory();
    				if(filesystem.getStartDirectory().getRootDriveFile().equals(roots[i])){
    					rootsCombo.setSelectedIndex(i);
    				}
    			}
    		}
    	}    	
    	rootsCombo.addItemListener(new ItemListener(){
    		public void itemStateChanged(ItemEvent e){
    			if(e.getStateChange()==ItemEvent.DESELECTED){
    				return;
    			}
    			if(!shouldFireComboEvent)
    			{
    				shouldFireComboEvent=true;
    				//return;
    			}
    			changeDirectory(((ComboBoxCellObject)rootsCombo.getSelectedItem()).getFile());
    		}
    	});
    }
    
    /**
     * Change the current directory to the one specified by the file, making 
     * that file the current working directory
     * @param file
     */
    protected abstract void changeDirectory(JFMFile file);

    /**
     * Returns the view component (JTable or a JList)
     * @return the view component
     */
    protected abstract JComponent getViewComponent();  
}
