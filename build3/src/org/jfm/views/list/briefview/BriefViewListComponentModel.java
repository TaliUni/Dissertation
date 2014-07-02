/*
 * Created on 1-Sep-2004
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package org.jfm.views.list.briefview;

import javax.swing.DefaultListModel;

import org.jfm.event.Broadcaster;
import org.jfm.event.ChangeDirectoryEvent;
import org.jfm.filesystems.JFMFile;


/**
 * TODO change me!!!
 * @author sergiu
 */
public class BriefViewListComponentModel extends DefaultListModel{

    private org.jfm.filesystems.JFMFileSystem filesystem;
    private org.jfm.filesystems.JFMFile workingDirectory;
	
	/**
	 * The Constructor
	 */
	public BriefViewListComponentModel(org.jfm.filesystems.JFMFileSystem filesystem) {
		super();
		setFilesystem(filesystem);		
        addListeners();        
    }

	private void addListeners(){
		//TODO: WHY WE ADDED THIS LISTENER IN THE FIRST PLACE?
		// WHY, US AS A COMPONENT MODEL, NEED TO KNOW WHEN THE DIRECTORY GETS CHANGED?
		/*Broadcaster.addChangeDirectoryListener(new ChangeDirectoryListener(){
			public void changeDirectory(ChangeDirectoryEvent e){
				if(!(e.getSource() instanceof BriefViewListComponentModel)){
					if(size()>0){
						browseDirectory((JFMFile)elementAt(0));
					}
				}
			}
		});*/
	}

    
    public void browseDirectory(JFMFile file){
      removeAllElements(); //removing the old rows
      //setting the working directory
      workingDirectory=file;      
      if(file.getParentFile()!=null){
        JFMFile parent=file.getParentFile();
        parent.setDisplayName("..");
        addElement(parent);      	
      }
      ChangeDirectoryEvent dirEvent=new ChangeDirectoryEvent(file);
      dirEvent.setSource(this);
      Broadcaster.notifyChangeDirectoryListeners(dirEvent);
      JFMFile[] files=file.listFiles();
      if(files==null) return;
      java.util.Arrays.sort(files);      
      for(int i=0;i<files.length;i++){
      	addElement(files[i]);
      }    	
    }
    
    
    public JFMFile getWorkingDirectory(){
    	return workingDirectory;
    }
    
	/**
	 * @return Returns the filesystem.
	 */
	public org.jfm.filesystems.JFMFileSystem getFilesystem() {
		return filesystem;
	}
	/**
	 * @param filesystem The filesystem to set.
	 */
	public void setFilesystem(org.jfm.filesystems.JFMFileSystem filesystem) {
		this.filesystem = filesystem;
	}
}
