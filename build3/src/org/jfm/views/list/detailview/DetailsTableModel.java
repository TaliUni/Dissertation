package org.jfm.views.list.detailview;


import javax.swing.table.AbstractTableModel;
import java.util.Vector;
import org.jfm.event.*;

import org.jfm.filesystems.JFMFile;
import org.jfm.filesystems.JFMFileSystem;
import org.jfm.views.JFMView;


/**
 * Title:        Java File Manager
 * Description:
 * Copyright:    Copyright (c) 2001
 * Company:      Home
 * @author Giurgiu Sergiu
 * @version 1.0
 */
@SuppressWarnings("unchecked")
public class DetailsTableModel extends AbstractTableModel {

    private Object[] columnNames=new Object[]{"Name","Size","Date Modified"};
    private Vector rowData = new Vector();
    private org.jfm.filesystems.JFMFileSystem filesystem;
    private org.jfm.filesystems.JFMFile workingDirectory;
    
    /**
     * This is the view that this model belongs to. In order to save the current working directory in the 
     * users' preferences, we have to call view's method setCurrentDirectory 
     */
    @SuppressWarnings("unused")
    private JFMView view;

    public org.jfm.filesystems.JFMFile getWorkingDirectory(){
      return workingDirectory;
    }

    public DetailsTableModel(JFMFileSystem fs) {
          setFilesystem(fs);
          addListeners();
    }

  private void addListeners(){
  	
   /* Broadcaster.addChangeDirectoryListener(new ChangeDirectoryListener(){
      public void changeDirectory(ChangeDirectoryEvent e){
         if(!(e.getSource() instanceof DetailsTableModel)){
          browseDirectory((JFMFile)((Vector)rowData.elementAt(0)).elementAt(0));
        }
      }
    });*/
  }


    public int getColumnIndex(Class colClass){
      if(colClass==null) return -1;
      for(int i=0;i<columnNames.length;i++){      	
        if(this.getColumnClass(i)==colClass) return i;
      }

      return -1;
    }

    /**
     * Returns a List of all the files found in the directory.
     * @return Vector
     */
    public Vector getCurrentFiles(){
      Vector files=new Vector();
      for(int i=0;i<rowData.size();i++){
        files.addElement(((Vector)rowData.elementAt(i)).elementAt(0));
      }
      return files;
    }

    /**
     * Returns the JFMFile found at the specific index.
     * @return Vector
     */
    public JFMFile getFileAt(int index){
      JFMFile file=null;
      try {
       file=(JFMFile)getValueAt(index, 0);
      }catch (Exception ex){
        ex.printStackTrace();
      }
      return file;
    }


    public void browseDirectory(final JFMFile el){
    	clear(); //removing the old rows
    	final java.text.SimpleDateFormat format=new java.text.SimpleDateFormat("MM/dd/yyyy");
    	final Vector allFiles=new Vector();
    	//setting the working directory
    	workingDirectory=el;    	
    	if(el.getParentFile()!=null){
    		Vector firstRow=new Vector();
    		JFMFile parent=el.getParentFile();
    		firstRow.addElement(parent);
    		parent.setDisplayName("..");
    		firstRow.addElement("up dir");
    		firstRow.addElement(format.format(new  java.util.Date(el.lastModified())));
    		addRow(firstRow);
    		allFiles.addElement(parent);      	
    	}
    	JFMFile[] files=el.listFiles();
    	if(files==null) return;
    	
    	java.util.Arrays.sort(files);
    	
    	for(int i=0;i<files.length;i++){
    		Vector v=new Vector();
    		v.addElement(files[i]);
    		
    		if(files[i].isDirectory()){
    			v.addElement("dir");
    		}else{
    			v.addElement(String.valueOf(files[i].length())+" bytes");
    		}
    		v.addElement(format.format(new  java.util.Date(files[i].lastModified())));
    		addRow(v);
    		allFiles.addElement(files[i]);
    	}      		
    	ChangeDirectoryEvent dirEvent=new ChangeDirectoryEvent(el);      
    	dirEvent.setSource(DetailsTableModel.this);
    	Broadcaster.notifyChangeDirectoryListeners(dirEvent);
    }

    public Class getColumnClass(int c) {
        return getValueAt(0, c).getClass();
    }

    /** Removes all rows from the data model. */
    public void clear() {
      int oldSize = rowData.size();
      rowData.clear();
      this.fireTableRowsDeleted(0, oldSize);
    }

    /** Add a row to the data model. */
    @SuppressWarnings("unchecked")
    public void addRow(Vector newRow) {
      rowData.add(newRow);
      this.fireTableRowsInserted(rowData.size(),rowData.size());
    }

    /** Add a row to the data model. */
    public void addRow(Object[] newRow) {
      addRow(convertToVector(newRow));
    }


    /**Returns the column name*/
    public String getColumnName(int column) { return columnNames[column].toString(); }

    /**Returns the number of rows*/
    public int getRowCount() { return this.rowData.size(); }

    /**Returns the number of columns*/
    public int getColumnCount() { return columnNames.length; }

    /**Returns the element at the specified row and column*/
    public Object getValueAt(int row, int column) {
    	if(rowData.size()<=row)
    	{
    		return null;
    	}
        return ((Vector)rowData.elementAt(row)).elementAt(column);
    }

    /**Returns whether the cell is editable. It isn't.*/
    public boolean isCellEditable(int row, int column) {
      return false;
    }

    /**Sets an object at the specified row and column*/
    public void setValueAt(Object value, int row, int column) {
        ((Vector)rowData.elementAt(row)).setElementAt(value, column);
        fireTableCellUpdated(row, column);
    }

    /**
     * Returns a vector that contains the same objects as the array.
     * @param anArray  the array to be converted
     * @return  the new vector; if <code>anArray</code> is <code>null</code>,
     *				returns <code>null</code>
     */
    protected static Vector convertToVector(Object[] anArray) {
        if (anArray == null)
            return null;

        Vector v = new Vector(anArray.length);
        for (int i=0; i < anArray.length; i++) {
            v.addElement(anArray[i]);
        }
        return v;
    }

    /**
     * Returns a vector of vectors that contains the same objects as the array.
     * @param anArray  the double array to be converted
     * @return the new vector of vectors; if <code>anArray</code> is
     *				<code>null</code>, returns <code>null</code>
     */
    protected static Vector convertToVector(Object[][] anArray) {
        if (anArray == null)
            return null;

        Vector v = new Vector(anArray.length);
        for (int i=0; i < anArray.length; i++) {
            v.addElement(convertToVector(anArray[i]));
        }
        return v;
    }
    
  public org.jfm.filesystems.JFMFileSystem getFilesystem() {
    return filesystem;
  }
  public void setFilesystem(org.jfm.filesystems.JFMFileSystem filesystem) {
    this.filesystem = filesystem;
  }
	/**
	 * @param view The view to set.
	 */
	public void setView(JFMView view) {
		this.view = view;
	}
}
