/*
 * Created on 26-Sep-2004
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package org.jfm.main.configurationdialog;

import java.awt.Component;

import javax.swing.JTree;
import javax.swing.tree.DefaultTreeCellRenderer;

/**
 * The renderer for the items in the configuration tree.
 * @author sergiu
 */
public class ConfigurationTreeCellRenderer extends DefaultTreeCellRenderer {

	public ConfigurationTreeCellRenderer (){
		super();		
	}

    /**
      * Configures the renderer based on the passed in components.
      * The value is set from messaging the tree with
      * <code>convertValueToText</code>, which ultimately invokes
      * <code>toString</code> on <code>value</code>.
      * The foreground color is set based on the selection and the icon
      * is set based on on leaf and expanded.
      */
    public Component getTreeCellRendererComponent(JTree tree, Object value,
						  boolean sel,
						  boolean expanded,
						  boolean leaf, int row,
						  boolean hasFocus) {
    	
    	DefaultTreeCellRenderer renderer=(DefaultTreeCellRenderer)super.getTreeCellRendererComponent(tree,value,sel,expanded,leaf,row,hasFocus);
    	renderer.setIcon(null);
    	renderer.setOpenIcon(null);
    	renderer.setClosedIcon(null);
    	renderer.setLeafIcon(null);
    	renderer.setDisabledIcon(null);
    	return renderer;
    }
}
