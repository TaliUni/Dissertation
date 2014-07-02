/*
 * Created on 25-Sep-2004
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package org.jfm.main.configurationdialog;

import javax.swing.tree.DefaultMutableTreeNode;

import org.jfm.main.configurationdialog.panels.ConfigurationPanel;

/**
 * A node in the configuration tree
 * @author sergiu
 */
public class ConfigurationTreeNode extends DefaultMutableTreeNode {

	public ConfigurationTreeNode(){
		super();		
	}
	public ConfigurationTreeNode(ConfigurationPanel userObject){
		super(userObject);
		setUserObject(userObject);		
	}
	
	
	public void setUserObject(Object userObject){
		if(userObject instanceof ConfigurationPanel){
			super.setUserObject(userObject);
		}else{
			throw new InternalError("The userobject on an tree node must be a ConfigurationPanel");
		}
	}
	
}
