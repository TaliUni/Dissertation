package org.jfm.views.list.detailview;

import javax.swing.table.DefaultTableCellRenderer;
import java.awt.Component;

import javax.swing.*;

import org.jfm.filesystems.JFMFile;
import org.jfm.main.Options;
import org.jfm.views.CellBorder;

/**
 * Title:        Java File Manager
 * Description:
 * Copyright:    Copyright (c) 2001
 * Company:      Home
 * @author Giurgiu Sergiu
 * @version 1.0
 */

public class DetailsTableCellRenderer extends DefaultTableCellRenderer {

  private CellBorder border=new CellBorder(Options.getForegroundColor(),1,true);

  public DetailsTableCellRenderer() {
    super();
  }

 // public void setForeground(Color c) {
     //super.setForeground(c);
  //}

  //public void setBackground(Color c) {
    // super.setBackground(c);
  //}

  public Component getTableCellRendererComponent(JTable table, Object value,boolean isSelected, boolean hasFocus, int row, int column) {
    //Component c=super.getTableCellRendererComponent(table,value,isSelected,hasFocus,row,column);
    //if(!(c instanceof JLabel)) return c;
  	setFont(table.getFont());
  	setValue(value);
  	DetailsTableModel model=(DetailsTableModel)table.getModel();
  	
  	JFMFile selectedFile=model.getFileAt(row);
  	if(selectedFile!=null && selectedFile.isMarked()){
  		setForeground(Options.getMarkedColor());  		
  	}else{
  		setForeground(table.getForeground());  		
  	}
  	
  	setBackground(table.getBackground());  	
  	if(column==0 && table.isRowSelected(row) && table.isFocusOwner()){
  		border.setLineColor(Options.getForegroundColor());
  		setBorder(border);
  	}else{
  		setBorder(noFocusBorder);
  	}  		

  	if(column==table.getTableHeader().getColumnModel().getColumnIndex("Size")){
  		setHorizontalAlignment(JLabel.RIGHT);
  	}else{
  		setHorizontalAlignment(JLabel.LEFT);
  	}
  	
  	if(!(value instanceof JFMFile)){
  		setIcon(null);
  	}else{
  		JFMFile f=(JFMFile)value;
  		setIcon(getIcon(f));
  	}
  	return this;
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
    	 // System.out.println("name:"+file.getName()+", displayname="+file.getDisplayName());    	  
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
