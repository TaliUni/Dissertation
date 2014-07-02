/*
 * Created on 25-Sep-2004
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package org.jfm.main.configurationdialog;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeNode;

/**
 * The confguration tree model
 * @author sergiu
 */
public class ConfigurationTreeModel extends DefaultTreeModel {

	@SuppressWarnings("unused")
	private DefaultMutableTreeNode root;
	
	public ConfigurationTreeModel(TreeNode root){
		super(root);
		this.root=(DefaultMutableTreeNode)root;
	}
	
	
}
