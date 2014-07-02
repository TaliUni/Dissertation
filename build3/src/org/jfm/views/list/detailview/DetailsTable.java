package org.jfm.views.list.detailview;

import javax.swing.*;

import javax.swing.table.*;

import java.awt.Rectangle;
import java.awt.event.*;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jfm.filesystems.JFMFile;
import org.jfm.main.Options;
import org.jfm.views.list.PanelChangeRequestListener;


/**
 * Title:        Java File Manager
 * Description:
 * Copyright:    Copyright (c) 2001
 * Company:      Home
 * @author Giurgiu Sergiu
 * @version 1.0
 */
public class DetailsTable extends JTable {
  private DetailsTableCellRenderer renderer=new DetailsTableCellRenderer();
  private PanelChangeRequestListener panelChangeListener=null;
  private List<Integer> markedRows=new java.util.ArrayList<Integer>();
  
  public DetailsTable() {
    super();
    removeDefaultKeys();
    addKeys();
    //setModel(model);
    addMouseActions();
    setOtherProperties();
  }


  public void setPanelChangeRequestListener(PanelChangeRequestListener l){
  	panelChangeListener=l;
  }

  private void setOtherProperties(){
    this.getTableHeader().setReorderingAllowed(false);
    this.setShowGrid(false);
    this.setColumnSelectionAllowed(false);
    this.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    //this.setDragEnabled(true);
  }

  private void removeDefaultKeys(){
    InputMap mainMap=this.getInputMap(JTable.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
    InputMap secondaryMap=this.getInputMap();
    InputMap map=this.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
    mainMap.clear();
    secondaryMap.clear();
    map.clear();
  }

  private void addKeys(){
    InputMap mainMap=this.getInputMap(JTable.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
    //InputMap secondaryMap=this.getInputMap();
    mainMap.put(KeyStroke.getKeyStroke("DOWN"),"selectNextRow");
    mainMap.put(KeyStroke.getKeyStroke("KP_DOWN"),"selectNextRow");
    mainMap.put(KeyStroke.getKeyStroke("UP"),"selectPreviousRow");
    mainMap.put(KeyStroke.getKeyStroke("KP_UP"),"selectPreviousRow");
    //mainMap.put(KeyStroke.getKeyStroke("shift DOWN"),"selectNextRow");
    //mainMap.put(KeyStroke.getKeyStroke("shift KP_DOWN"),"selectNextRow");
    //mainMap.put(KeyStroke.getKeyStroke("shift UP"),"selectPreviousRow");
    //mainMap.put(KeyStroke.getKeyStroke("shift KP_UP"),"selectPreviousRow");
    mainMap.put(KeyStroke.getKeyStroke("PAGE_UP"),"scrollUpChangeSelection");
    mainMap.put(KeyStroke.getKeyStroke("PAGE_DOWN"),"scrollDownChangeSelection");
    //mainMap.put(KeyStroke.getKeyStroke("shift PAGE_UP"),"scrollUpChangeSelection");
    //mainMap.put(KeyStroke.getKeyStroke("shift PAGE_DOWN"),"scrollDownExtendSelection");
    mainMap.put(KeyStroke.getKeyStroke("ctrl PAGE_UP"),"upOneDirectory"); //MUST ADD an action for this
    mainMap.put(KeyStroke.getKeyStroke("ctrl PAGE_DOWN"),"scrollDownChangeSelection");
    mainMap.put(KeyStroke.getKeyStroke("HOME"),"selectFirstRow");
    mainMap.put(KeyStroke.getKeyStroke("END"),"selectLastRow");
    //mainMap.put(KeyStroke.getKeyStroke("shift END"),"selectLastRowExtendSelection");
    //mainMap.put(KeyStroke.getKeyStroke("shift HOME"),"selectFirstRowExtendSelection");
    mainMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_MULTIPLY,0),"markAllAction");
    mainMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_ADD,0),"markAllFilteredAction");
    mainMap.put(KeyStroke.getKeyStroke("ESCAPE"),"cancel");
    mainMap.put(KeyStroke.getKeyStroke("ENTER"),"fileAction"); //MUST ADD an action for this
    this.getInputMap(JTable.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("TAB"),"changePanelAction");
    mainMap.put(KeyStroke.getKeyStroke("TAB"),"changePanelAction");
    mainMap.put(KeyStroke.getKeyStroke("ctrl R"),"refreshAction");
    //mainMap.put(KeyStroke.getKeyStroke("INSERT"),"selectNextRowExtendSelection");
    mainMap.put(KeyStroke.getKeyStroke("INSERT"),"markRowAction");
    this.getActionMap().put("fileAction",new FileAction());
    this.getActionMap().put("upOneDirectory",new UpDirectoryAction());
    this.getActionMap().put("changePanelAction",new ChangePanelAction());
    this.getActionMap().put("refreshAction",new RefreshAction());
    this.getActionMap().put("markRowAction",new MarkRowAction());
    this.getActionMap().put("markAllAction",new MarkAllRowAction());
    this.getActionMap().put("markAllFilteredAction",new MarkAllFilteredRowAction());
    
  }

    public TableCellRenderer getCellRenderer(int row, int column) {
	  return renderer;
    }

    private void addMouseActions(){
      this.addMouseListener(new MouseAdapter(){
        public void mouseClicked(MouseEvent e){
          int row=DetailsTable.this.rowAtPoint(e.getPoint());
          if(e.getClickCount()==2 && (row>=0) && (row<((DetailsTableModel)DetailsTable.this.getModel()).getRowCount())){//double click on row
            processActionOnRow(row);
            return;
          }
        }
      });
    }

    private void processActionOnRow(int row){
      //this is always gonna be 0
      //if not THEN CHANGE THIS    	
      final DetailsTableModel model=(DetailsTableModel)getModel();
      int colIndex=0;//((FullTableViewModel)FullTableView.this.getModel()).getColumnIndex(JFMFile.class);      
      final JFMFile el=(JFMFile)model.getValueAt(row,colIndex);      
     //6 System.out.println(el.isDirectory());
      if(el.isDirectory()){
      	final JFMFile oldworkingDirectory=model.getWorkingDirectory();
      	model.browseDirectory(el);
      	int index =model.getCurrentFiles().indexOf(oldworkingDirectory);
      	if(index>=0){
      		ensureIndexIsVisible(index);
      		getSelectionModel().setSelectionInterval(index,index);
      	}else{
      		getSelectionModel().setSelectionInterval(0,0);
      	}
      }
      else{
        /**@todo see what happens when we try to execute a file*/
      }
    }

    /**
     * Scrolls the viewport to the row at the specified index.
     * Stolen from javax.swing.JList.
     * @param index the index of the row.
     */
    public void ensureIndexIsVisible(int index) {    	
        Rectangle cellBounds = this.getCellRect(index, 0,true);
        if (cellBounds != null) {
            scrollRectToVisible(cellBounds);
        }
    }
    
    private class MarkAllFilteredRowAction implements Action{
      //requested methods
      public Object getValue(String key){return null;}
      public void putValue(String key, Object value){}
      public void setEnabled(boolean b){}
      public boolean isEnabled(){return true;}
      public void addPropertyChangeListener(java.beans.PropertyChangeListener listener){}
      public void removePropertyChangeListener(java.beans.PropertyChangeListener listener){}

      public void actionPerformed(java.awt.event.ActionEvent e){
      	if(true)return;
      	//for the moment, disable this, since we don't have an expression parser
      	//that would parse expressions that are common for file names (*.*,a?.txt, etc.)
      	
      	String filter=JOptionPane.showInputDialog(Options.getMainFrame(),"Files type (regular expression accepted):","*");
      	if(filter==null || filter.length()<=0) return;
      	DetailsTableModel model=((DetailsTableModel)DetailsTable.this.getModel());
      	Pattern pattern=Pattern.compile(filter);      	
          for(int row=1;row<model.getRowCount();row++){
              JFMFile el=model.getFileAt(row);
              Matcher matcher=pattern.matcher(el.getName());
              if(matcher.matches() && (el.isFile() || Options.getDirectoriesSelectedOnPlus())){
              	el.setMarked(!el.isMarked());
                  if(el.isMarked()){
                	  markedRows.add(new Integer(row));
                  }else{
                	  markedRows.remove(new Integer(row));
                  }
              	
              }
          }
          DetailsTable.this.repaint();
      }
    }
    
    private class MarkAllRowAction implements Action{
      //requested methods
      public Object getValue(String key){return null;}
      public void putValue(String key, Object value){}
      public void setEnabled(boolean b){}
      public boolean isEnabled(){return true;}
      public void addPropertyChangeListener(java.beans.PropertyChangeListener listener){}
      public void removePropertyChangeListener(java.beans.PropertyChangeListener listener){}

      public void actionPerformed(java.awt.event.ActionEvent e){
      	DetailsTableModel model=((DetailsTableModel)DetailsTable.this.getModel());
          for(int row=1;row<model.getRowCount();row++){
                  JFMFile el=model.getFileAt(row);
                  if(el.isFile() || Options.getDirectoriesSelectedOnAsterisk()){
                  	el.setMarked(!el.isMarked());
	                  if(el.isMarked()){
	                	  markedRows.add(new Integer(row));
	                  }else{
	                	  markedRows.remove(new Integer(row));
	                  }
                  }
          }
          DetailsTable.this.repaint();
      }
    }
    
    private class MarkRowAction implements Action{
      //requested methods
      public Object getValue(String key){return null;}
      public void putValue(String key, Object value){}
      public void setEnabled(boolean b){}
      public boolean isEnabled(){return true;}
      public void addPropertyChangeListener(java.beans.PropertyChangeListener listener){}
      public void removePropertyChangeListener(java.beans.PropertyChangeListener listener){}

      public void actionPerformed(java.awt.event.ActionEvent e){
          int row=((DetailsTable)e.getSource()).getSelectedRow();
          DetailsTableModel model=((DetailsTableModel)DetailsTable.this.getModel());
          if(row>=1 && row<model.getRowCount()){
                  JFMFile el=model.getFileAt(row);
                  el.setMarked(!el.isMarked());
                  if(el.isMarked()){
                	  markedRows.add(new Integer(row));
                  }else{
                	  markedRows.remove(new Integer(row));
                  }
                	  
          }
          if(row<(model.getRowCount()-1)){
          	DetailsTable.this.setRowSelectionInterval(row+1,row+1);
          }
          DetailsTable.this.repaint();
      }
    }
	
    private class RefreshAction implements Action{
      //requested methods
      public Object getValue(String key){return null;}
      public void putValue(String key, Object value){}
      public void setEnabled(boolean b){}
      public boolean isEnabled(){return true;}
      public void addPropertyChangeListener(java.beans.PropertyChangeListener listener){}
      public void removePropertyChangeListener(java.beans.PropertyChangeListener listener){}

      public void actionPerformed(java.awt.event.ActionEvent e){
        JFMFile el=(JFMFile)((DetailsTableModel)DetailsTable.this.getModel()).getWorkingDirectory();
        ((DetailsTableModel)DetailsTable.this.getModel()).browseDirectory(el);
      }
    }


    private class ChangePanelAction implements Action{
      //requested methods
      public Object getValue(String key){return null;}
      public void putValue(String key, Object value){}
      public void setEnabled(boolean b){}
      public boolean isEnabled(){return true;}
      public void addPropertyChangeListener(java.beans.PropertyChangeListener listener){}
      public void removePropertyChangeListener(java.beans.PropertyChangeListener listener){}

      public void actionPerformed(java.awt.event.ActionEvent e){
        if(panelChangeListener!=null){
        	panelChangeListener.requestPanelChange();
        }
      }
    }

    private class FileAction implements Action{
      //requested methods
      public Object getValue(String key){return null;}
      public void putValue(String key, Object value){}
      public void setEnabled(boolean b){}
      public boolean isEnabled(){return true;}
      public void addPropertyChangeListener(java.beans.PropertyChangeListener listener){}
      public void removePropertyChangeListener(java.beans.PropertyChangeListener listener){}

      public void actionPerformed(java.awt.event.ActionEvent e){
          int row=((DetailsTable)e.getSource()).getSelectedRow();
          if(row>=0 && row<((DetailsTableModel)DetailsTable.this.getModel()).getRowCount()){
                  processActionOnRow(row);
          }
      }
    }

    private class UpDirectoryAction implements Action{
      //requested methods
      public Object getValue(String key){return null;}
      public void putValue(String key, Object value){}
      public void setEnabled(boolean b){}
      public boolean isEnabled(){return true;}
      public void addPropertyChangeListener(java.beans.PropertyChangeListener listener){}
      public void removePropertyChangeListener(java.beans.PropertyChangeListener listener){}

      public void actionPerformed(java.awt.event.ActionEvent e){
        processActionOnRow(0);
      }
    }

	public List<Integer> getMarkedFiles() {		
		return markedRows;
	}
}
