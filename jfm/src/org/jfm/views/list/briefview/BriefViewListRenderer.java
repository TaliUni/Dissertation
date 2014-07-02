/*
 * Created on 1-Sep-2004
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package org.jfm.views.list.briefview;

import java.awt.Component;

import javax.swing.DefaultListCellRenderer;
import javax.swing.Icon;
import javax.swing.JList;
import javax.swing.JLabel;
import javax.swing.UIManager;

import org.jfm.filesystems.JFMFile;
import org.jfm.main.Options;
import org.jfm.views.CellBorder;


/**
 * The cell renderer for the brief list view component. 
 * @author sergiu
 */
public class BriefViewListRenderer extends DefaultListCellRenderer {
	
//TODO FIND OUT WHY THE CELLS IN BRIEF VIEW ARE BIGGER THAN THE CELLS IN DETAIL VIEW
	
	private CellBorder border=new CellBorder(Options.getForegroundColor(),1,true);
	
	/**
	 * Simple constructor
	 */
	public BriefViewListRenderer() {
		super();		
	}

    public Component getListCellRendererComponent(JList list,Object value,
        int index,boolean isSelected,boolean cellHasFocus){
    	JLabel c=(JLabel)super.getListCellRendererComponent(list,value,index,isSelected,cellHasFocus);
    	if(cellHasFocus){
    		border.setLineColor(Options.getForegroundColor());
    		setBorder(border);
    	}
	    setBackground(list.getBackground());
	    
	    JFMFile f=(JFMFile)value;
	    if(f.isMarked()){
	    	setForeground(Options.getMarkedColor());
	    }else{
	    	setForeground(list.getForeground());	
	    }
    	c.setIcon(getIcon(f));
    	return c;
    }
    
  /**
   * Retrieves the icon to associate with this file. If the icon returned by the file is <code>null</null> then
   * ask the UIManager to give us an icon (from the LookAndFeel) 
   * @param file The file for which we want get the icon
   * @return the icon associated with the file
   */
  private Icon getIcon(JFMFile file){
  	Icon icon=null;  	
  	icon=file.getIcon();  	
  	if(icon==null){
      if(file.isDirectory()){
        if("..".equals(file.getDisplayName())){
          icon=(Icon)UIManager.get("FileChooser.upFolderIcon");
        }else{
          icon=(Icon)UIManager.get("FileView.directoryIcon");
        }
      }else{
        icon=(Icon)UIManager.get("FileView.fileIcon");
      }  		
  	}
  	return icon;
  }    
}
