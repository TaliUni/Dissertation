/*
 * Created on Aug 31, 2004
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package org.jfm.views.list;

import org.jfm.filesystems.JFMFile;

/**
 * This is the object that is displayed into the root's combobox. It's toString method returns
 * @author sergiu
 */
public class ComboBoxCellObject {

	private JFMFile file;
	
	public ComboBoxCellObject(JFMFile file){
		setFile(file);
	}
	
	/**
	 * @return Returns the file.
	 */
	public JFMFile getFile() {
		return file;
	}
	/**
	 * @param file The file to set.
	 */
	public void setFile(JFMFile file) {
		this.file = file;
	}
	
	/**
	 * Returns the string representation of this object.
	 * if <code>getFile()!=null</code> returns <code>getFile().getPath()</code>, otherwise it returns an
	 * empty string.
	 */
	public String toString(){
		if(getFile()!=null){
			String systemDisplayName=getFile().getSystemDisplayName();
			return (systemDisplayName!=null?systemDisplayName:getFile().getPath());
		}
		return "";
	}
}
