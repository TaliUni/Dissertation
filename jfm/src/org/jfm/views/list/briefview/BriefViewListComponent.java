/*
 * Created on 1-Sep-2004
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package org.jfm.views.list.briefview;

import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.Action;
import javax.swing.InputMap;
import javax.swing.JComponent;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;
import javax.swing.ListCellRenderer;
import javax.swing.ListSelectionModel;

import org.jfm.filesystems.JFMFile;
import org.jfm.main.Options;
import org.jfm.views.list.PanelChangeRequestListener;


import javax.swing.UIManager;
import javax.swing.SwingUtilities;

/**
 * TODO change me!!!
 * @author sergiu
 */
public class BriefViewListComponent extends JList {
	private PanelChangeRequestListener panelChangeListener=null;
	private BriefViewListRenderer renderer=new BriefViewListRenderer();
	private List<Integer> markedRows=new java.util.ArrayList<Integer>();

	/**
	 *
	 */
	public BriefViewListComponent() {
		setFocusTraversalKeysEnabled(false);
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
  	this.setLayoutOrientation(JList.VERTICAL_WRAP);
    this.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
  }
    /**
     * Returns the preferred number of visible rows.
     *
     * @return an integer indicating the preferred number of rows to display
     *         without using a scroll bar
     * @see #setVisibleRowCount
     */
    public int getVisibleRowCount() {
    	//to fill the whole space
        return -1;
    }


   private void removeDefaultKeys(){
    InputMap mainMap=SwingUtilities.getUIInputMap(this,JComponent.WHEN_FOCUSED);
    InputMap secondaryMap=this.getInputMap();
    InputMap keyMap = (InputMap)UIManager.get("List.focusInputMap");

    mainMap.clear();
    secondaryMap.clear();
    keyMap.clear();    
  }

  private void addKeys(){
    InputMap mainMap=new InputMap();    
    mainMap.put(KeyStroke.getKeyStroke("DOWN"),"selectNextRow");
    mainMap.put(KeyStroke.getKeyStroke("KP_DOWN"),"selectNextRow");
    mainMap.put(KeyStroke.getKeyStroke("UP"),"selectPreviousRow");
    mainMap.put(KeyStroke.getKeyStroke("KP_UP"),"selectPreviousRow");
	mainMap.put(KeyStroke.getKeyStroke("LEFT"), "selectPreviousColumn");
	mainMap.put(KeyStroke.getKeyStroke("KP_LEFT"), "selectPreviousColumn");
	mainMap.put(KeyStroke.getKeyStroke("RIGHT"), "selectNextColumn");
	mainMap.put(KeyStroke.getKeyStroke("KP_RIGHT"), "selectNextColumn");     
    mainMap.put(KeyStroke.getKeyStroke("PAGE_UP"),"scrollUp");
    mainMap.put(KeyStroke.getKeyStroke("PAGE_DOWN"),"scrollDown");    
    mainMap.put(KeyStroke.getKeyStroke("ctrl PAGE_UP"),"upOneDirectory"); //MUST ADD an action for this
    mainMap.put(KeyStroke.getKeyStroke("ctrl PAGE_DOWN"),"scrollDownChangeSelection");
    mainMap.put(KeyStroke.getKeyStroke("HOME"),"selectFirstRow");
    mainMap.put(KeyStroke.getKeyStroke("END"),"selectLastRow");
    mainMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_MULTIPLY,0),"markAllAction");
    mainMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_ADD,0),"markAllFilteredAction");
    mainMap.put(KeyStroke.getKeyStroke("ESCAPE"),"cancel");
    mainMap.put(KeyStroke.getKeyStroke("ENTER"),"fileAction"); //MUST ADD an action for this
    mainMap.put(KeyStroke.getKeyStroke("TAB"),"changePanelAction");
    mainMap.put(KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_TAB,0),"changePanelAction");
    mainMap.put(KeyStroke.getKeyStroke("ctrl R"),"refreshAction");
    mainMap.put(KeyStroke.getKeyStroke("INSERT"),"markRowAction");
    //mainMap.put(KeyStroke.getKeyStroke("INSERT"),"selectNextRowExtendSelection");
    
    SwingUtilities.replaceUIInputMap(this,JComponent.WHEN_FOCUSED,mainMap);
   // SwingUtilities.replaceUIInputMap(this,JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT,mainMap);
    //UIManager.put("List.focusInputMap",mainMap);
    javax.swing.ActionMap aMap=SwingUtilities.getUIActionMap(this);
    aMap.put("fileAction",new FileAction());
    aMap.put("upOneDirectory",new UpDirectoryAction());
    aMap.put("changePanelAction",new ChangePanelAction());
    aMap.put("refreshAction",new RefreshAction());
    aMap.put("markRowAction",new MarkRowAction());
    aMap.put("markAllAction",new MarkAllRowAction());
    aMap.put("markAllFilteredAction",new MarkAllFilteredRowAction());
    SwingUtilities.replaceUIActionMap(this,aMap);
  //  UIManager.put("List.actionMap",aMap);
  }

    public ListCellRenderer getCellRenderer() {
    	
	  return renderer;
    }
    
    /*public void updateUI(){
    	
    }*/

    private void addMouseActions(){
      this.addMouseListener(new MouseAdapter(){
        public void mouseClicked(MouseEvent e){
          int row=BriefViewListComponent.this.locationToIndex(e.getPoint());
          if(e.getClickCount()==2 && (row>=0) && (row<BriefViewListComponent.this.getModel().getSize())){//double click on row
            processActionOnRow(row);
            return;
          }
        }
      });
    }

    private void processActionOnRow(int row){
    	final JFMFile el=(JFMFile)getModel().getElementAt(row);
    	if(el.isDirectory()){
    		JFMFile oldworkingDirectory=((BriefViewListComponentModel)getModel()).getWorkingDirectory();
    		((BriefViewListComponentModel)getModel()).browseDirectory(el);
    		int index =((BriefViewListComponentModel)getModel()).indexOf(oldworkingDirectory);
    		if(index>=0){
    			ensureIndexIsVisible(index);
    			setSelectedIndex(index);
    		}else{
    			setSelectedIndex(0);
    		}
    	}else{
    		/**@todo see what happens when we try to execute a file*/
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
      	Pattern pattern=Pattern.compile(filter,Pattern.CASE_INSENSITIVE);
        for(int row=1;row<BriefViewListComponent.this.getModel().getSize();row++){
          JFMFile el=(JFMFile)BriefViewListComponent.this.getModel().getElementAt(row);          
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
          BriefViewListComponent.this.repaint();                    
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
          for(int row=1;row<BriefViewListComponent.this.getModel().getSize();row++){
                  JFMFile el=(JFMFile)BriefViewListComponent.this.getModel().getElementAt(row);
                  if(el.isFile() || Options.getDirectoriesSelectedOnAsterisk()){
                  	el.setMarked(!el.isMarked());
	                  if(el.isMarked()){
	                	  markedRows.add(new Integer(row));
	                  }else{
	                	  markedRows.remove(new Integer(row));
	                  }
                  	
                  }
          }
          BriefViewListComponent.this.repaint();                    
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
          int row=BriefViewListComponent.this.getSelectedIndex();          
          if(row>=1 && row<BriefViewListComponent.this.getModel().getSize()){
                  JFMFile el=(JFMFile)BriefViewListComponent.this.getModel().getElementAt(row);
                  el.setMarked(!el.isMarked());
                  if(el.isMarked()){
                	  markedRows.add(new Integer(row));
                  }else{
                	  markedRows.remove(new Integer(row));
                  }
                  
          }
          if(row<(BriefViewListComponent.this.getModel().getSize()-1)){
                	BriefViewListComponent.this.setSelectedIndex(row+1);
          }
          BriefViewListComponent.this.repaint();                    
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
        JFMFile el=(JFMFile)((BriefViewListComponentModel)BriefViewListComponent.this.getModel()).getWorkingDirectory();
        ((BriefViewListComponentModel)BriefViewListComponent.this.getModel()).browseDirectory(el);
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
          int row=((BriefViewListComponent)e.getSource()).getSelectedIndex();
          if(row>=0 && row<((BriefViewListComponentModel)BriefViewListComponent.this.getModel()).getSize()){
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
