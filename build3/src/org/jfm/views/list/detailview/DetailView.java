/*
 * Created on 31-Aug-2004
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package org.jfm.views.list.detailview;

import java.awt.BorderLayout;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

import org.jfm.event.ColorChangeEvent;
import org.jfm.event.ColorChangeListener;
import org.jfm.event.Broadcaster;
import org.jfm.event.ChangeDirectoryEvent;
import org.jfm.event.ChangeDirectoryListener;
import org.jfm.event.ChangePanelEvent;
import org.jfm.event.FontChangeEvent;
import org.jfm.event.FontChangeListener;
import org.jfm.filesystems.JFMFile;
import org.jfm.main.Options;
import org.jfm.po.CopyAction;

import org.jfm.po.MoveAction;

import org.jfm.views.list.ListView;
import org.jfm.views.list.PanelChangeRequestListener;

import javax.swing.JComponent;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;


/**
 * This panel implements a detailed view of the files 
 * @author sergiu
 */
public class DetailView extends ListView {

  private DetailsTableModel model;
  private DetailsTable table=new DetailsTable();
  private int selectedRow=-1;
	
	/**
	 * Constructor. 
	 */
	public DetailView(String fs) {
		super(fs);
	    try {
	      init();
	    }
	    catch(Exception ex) {
	      ex.printStackTrace();
	    }	    
	}
	
	protected JComponent getViewComponent(){
		if(table==null){
			table=new DetailsTable();
		}
		return table;
	}
	
	private void init() throws Exception{		
		model=new DetailsTableModel(filesystem);
		table.setModel(model);
		this.setLayout(new BorderLayout());
		scroll = new JScrollPane(table);
		scroll.getViewport().setBackground(Options.getBackgroundColor());
		table.setBackground(Options.getBackgroundColor());
		table.setForeground(Options.getForegroundColor());
		table.setFont(Options.getPanelsFont());
		
		add(scroll,BorderLayout.CENTER);
		add(topPanel,BorderLayout.NORTH);
		Broadcaster.addFontChangeListener(new FontChangeListener(){
			public void fontChanged(FontChangeEvent ev){
				table.setFont(Options.getPanelsFont());
			}
		});	
		
		Broadcaster.addColorChangeListener(new ColorChangeListener(){
			public void colorChanged(ColorChangeEvent event){
				if(event.getColorType()==ColorChangeEvent.BACKGROUND){
					scroll.getViewport().setBackground(Options.getBackgroundColor());
					table.setBackground(Options.getBackgroundColor());
				}
				if(event.getColorType()==ColorChangeEvent.FOREGROUND){
					table.setForeground(Options.getForegroundColor());
				}
			}
		});

		Broadcaster.addChangeDirectoryListener(new ChangeDirectoryListener(){
			public void changeDirectory(ChangeDirectoryEvent e){
				if(e.getSource().equals(model)){
					statusLabel.setText(e.getDirectory().getAbsolutePath());
					return;
				}
				/*System.out.println("changedir -----");
				System.out.println(filesystem);
				System.out.println(e.getSource());
				System.out.println((e.getSource() instanceof MkdirAction)+" bool1");
				System.out.println((isActive)+" bool2");*/
				if((e.getSource() instanceof javax.swing.Action) && isActive){	
					final DetailsTableModel model=(DetailsTableModel)table.getModel();
					model.browseDirectory(getCurrentWorkingDirectory());
					table.getMarkedFiles().clear();
				}
				
				//if we were the target of a Copyaction or Move action, then we're not active, but we still should update ourselves
				if(((e.getSource() instanceof CopyAction)  || (e.getSource() instanceof MoveAction)) && !isActive){	
					final DetailsTableModel model=(DetailsTableModel)table.getModel();
					model.browseDirectory(getCurrentWorkingDirectory());
					table.getMarkedFiles().clear();
				}
				
			}
		});		
		table.addFocusListener(new FocusListener(){
			public void focusGained(FocusEvent e){
				statusLabel.setForeground(UIManager.getColor("Label.foreground"));
				if(e.getOppositeComponent()!=null ) 
				{
					Options.setActivePanel(DetailView.this);
				}
				ChangeDirectoryEvent event=new ChangeDirectoryEvent();
				event.setDirectory(getCurrentWorkingDirectory());
				event.setSource(DetailView.this);
				Broadcaster.notifyChangeDirectoryListeners(event);
				if(selectedRow>=0 && selectedRow<model.getRowCount()){
					try {
						table.getSelectionModel().setSelectionInterval(selectedRow,selectedRow);
					}
					catch (Exception ignored) { }
				}
				table.repaint();
			}			
			public void focusLost(FocusEvent e){
				statusLabel.setForeground(UIManager.getColor("Label.disabledForeground"));
				selectedRow=table.getSelectedRow();
				table.repaint();
				// table.getSelectionModel().clearSelection();
			}
		});		
		table.getSelectionModel().addListSelectionListener(new ListSelectionListener(){
			public void valueChanged(ListSelectionEvent e){
				if(e.getValueIsAdjusting()) return;
				if(table.getSelectionModel().isSelectionEmpty()) return;				
				int[] selections=new int[2];
				int firstIndex = table.getSelectionModel().getMinSelectionIndex();
				int lastIndex = table.getSelectionModel().getMaxSelectionIndex();				
				selections[0]=firstIndex;
				selections[1]=lastIndex;
			}
		});		
		table.addMouseListener(new MouseAdapter(){
			public void mouseClicked(MouseEvent event){
				if(SwingUtilities.isRightMouseButton(event)){
					if(table.rowAtPoint(event.getPoint())<0){
						return;
					}
					Options.getPopupMenu().show(table,event.getX(),event.getY());
				}
			}
		});
		table.setPanelChangeRequestListener(new PanelChangeRequestListener(){
			public void requestPanelChange() {
				ChangePanelEvent ev=new ChangePanelEvent();
				ev.setSource(DetailView.this);
				Broadcaster.notifyChangePanelListeners(ev);		
			}
		});
		
		changeDirectory(filesystem.getStartDirectory());
	}

	protected void changeDirectory(JFMFile file){
		model.browseDirectory(file);
	}
	
  public JFMFile getSelectedFile(){
    int row=table.getSelectedRow();
    if(row<0){
      return null;
    }
    return model.getFileAt(row);
  }
  
  public JFMFile[] getSelectedFiles(){
    List<Integer> rows=table.getMarkedFiles();
    JFMFile[] files=new JFMFile[rows.size()];   
    for (int i = 0; i < rows.size(); i++) {
      files[i]=model.getFileAt(rows.get(i).intValue());
    }
    if(rows.size()==0)
    {
    	files=new JFMFile[1];
    	files[0]=getSelectedFile();
    }
    return files;
  }

  public JFMFile getCurrentWorkingDirectory(){
    return model.getWorkingDirectory();
  }
  
}
