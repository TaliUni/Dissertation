/*
 * Created on 31-Aug-2004
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package org.jfm.views.list.briefview;

import java.awt.BorderLayout;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

import javax.swing.JComponent;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

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
import org.jfm.views.list.PanelChangeRequestListener;

/**
 * This is the view that shows the files as a list 
 * @author sergiu
 */
public class BriefView extends org.jfm.views.list.ListView{

  private BriefViewListComponentModel model;
  private BriefViewListComponent list=new BriefViewListComponent();
  private int selectedRow=-1;
	
	
	public BriefView(String fs){
		super(fs);
	    try {
	      init();
	    }
	    catch(Exception ex) {
	      ex.printStackTrace();
	    }	    
	}
	
	protected JComponent getViewComponent(){
		return list;
	}
	
	private void init() throws Exception{
		model=new BriefViewListComponentModel(filesystem);
		list.setModel(model);
		
		this.setLayout(new BorderLayout());
		scroll = new JScrollPane(list);
		scroll.getViewport().setBackground(Options.getBackgroundColor());
		list.setBackground(Options.getBackgroundColor());
		list.setForeground(Options.getForegroundColor());
		list.setFont(Options.getPanelsFont());
		
		add(scroll,BorderLayout.CENTER);
		add(topPanel,BorderLayout.NORTH);

		Broadcaster.addFontChangeListener(new FontChangeListener(){
			public void fontChanged(FontChangeEvent ev){
				list.setFont(Options.getPanelsFont());
			}
		});	
		Broadcaster.addChangeDirectoryListener(new ChangeDirectoryListener(){
			public void changeDirectory(ChangeDirectoryEvent e){
				if(e.getSource().equals(list.getModel())){
					statusLabel.setText(e.getDirectory().getAbsolutePath());
					return;
				}

				if((e.getSource() instanceof javax.swing.Action) && isActive){						
					model.browseDirectory(getCurrentWorkingDirectory());
					list.getMarkedFiles().clear();
				}
				
				//if we were the target of a Copyaction or Move action, then we're not active, but we still should update ourselves
				if(((e.getSource() instanceof CopyAction)  || (e.getSource() instanceof MoveAction)) && !isActive){						
					model.browseDirectory(getCurrentWorkingDirectory());
					list.getMarkedFiles().clear();
				}
				
			}
		});	
		Broadcaster.addColorChangeListener(new ColorChangeListener(){
			public void colorChanged(ColorChangeEvent event){
				if(event.getColorType()==ColorChangeEvent.BACKGROUND){
					scroll.getViewport().setBackground(Options.getBackgroundColor());
					list.setBackground(Options.getBackgroundColor());
				}
				if(event.getColorType()==ColorChangeEvent.FOREGROUND){
					list.setForeground(Options.getForegroundColor());
				}
			}
		});
		list.addFocusListener(new FocusListener(){
			public void focusGained(FocusEvent e){
				statusLabel.setForeground(UIManager.getColor("Label.foreground"));
				if(selectedRow>=0){
					list.setSelectedIndex(selectedRow);
				}
				if(e.getOppositeComponent()!=null ) 
				{
					Options.setActivePanel(BriefView.this);
				}				
				ChangeDirectoryEvent event=new ChangeDirectoryEvent();
				event.setDirectory(getCurrentWorkingDirectory());
				event.setSource(BriefView.this);
				Broadcaster.notifyChangeDirectoryListeners(event);				
			}			
			public void focusLost(FocusEvent e){
				statusLabel.setForeground(UIManager.getColor("Label.disabledForeground"));
				selectedRow=list.getSelectedIndex();
			}
		});		
		list.getSelectionModel().addListSelectionListener(new ListSelectionListener(){
			public void valueChanged(ListSelectionEvent e){
				if(e.getValueIsAdjusting()) return;
				if(list.getSelectionModel().isSelectionEmpty()) return;				
				int[] selections=new int[2];
				int firstIndex = list.getSelectionModel().getMinSelectionIndex();
				int lastIndex = list.getSelectionModel().getMaxSelectionIndex();				
				selections[0]=firstIndex;
				selections[1]=lastIndex;
			}
		});		
		list.addMouseListener(new MouseAdapter(){
			public void mouseClicked(MouseEvent event){
				if(SwingUtilities.isRightMouseButton(event)){
					if(list.locationToIndex(event.getPoint())<0){
						return;
					}
					Options.getPopupMenu().show(list,event.getX(),event.getY());
				}
			}
		});
		list.setPanelChangeRequestListener(new PanelChangeRequestListener(){
			public void requestPanelChange() {
				ChangePanelEvent ev=new ChangePanelEvent();
				ev.setSource(BriefView.this);
				Broadcaster.notifyChangePanelListeners(ev);		}
		});
		
		changeDirectory(filesystem.getStartDirectory());
	}
	
	

	/* (non-Javadoc)
	 * @see org.jfm.views.list.BriefView#changeDirectory(org.jfm.filesystems.JFMFile)
	 */
	protected void changeDirectory(JFMFile file) {
		model.browseDirectory(file);
	}

	/* (non-Javadoc)
	 * @see org.jfm.views.JFMView#getCurrentWorkingDirectory()
	 */
	public JFMFile getCurrentWorkingDirectory() {
		return model.getWorkingDirectory();
	}
	/* (non-Javadoc)
	 * @see org.jfm.views.JFMView#getSelectedFile()
	 */
	public JFMFile getSelectedFile() {
		int row=list.getSelectedIndex();
		if(row<0)return null;
		
		return (JFMFile)model.getElementAt(row);
	}
	/* (non-Javadoc)
	 * @see org.jfm.views.JFMView#getSelectedFiles()
	 */
	public JFMFile[] getSelectedFiles() {
	    List<Integer> rows=list.getMarkedFiles();
	    JFMFile[] files=new JFMFile[rows.size()];   
	    for (int i = 0; i < rows.size(); i++) {
	      files[i]=(JFMFile)model.getElementAt(rows.get(i).intValue());
	    }
	    if(rows.size()==0)
	    {
	    	files=new JFMFile[1];
	    	files[0]=getSelectedFile();
	    }
	    return files;				
	}
}
