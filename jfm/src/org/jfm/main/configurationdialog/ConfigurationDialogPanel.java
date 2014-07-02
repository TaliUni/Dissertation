/*
 * Created on 25-Sep-2004
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package org.jfm.main.configurationdialog;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTree;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.TreePath;
import javax.swing.tree.TreeSelectionModel;

import org.jfm.main.configurationdialog.panels.ColorConfigurationPanel;
import org.jfm.main.configurationdialog.panels.ConfigurationPanel;
import org.jfm.main.configurationdialog.panels.FontConfigurationPanel;
import org.jfm.main.configurationdialog.panels.HelpConfigurationPanel;

/**
 * This is the configuration Dialog Panel. it holds the tree on the left and 
 * it manages to display the appropriate panels on the right.
 * @author sergiu
 */
public class ConfigurationDialogPanel extends JPanel {
	private JScrollPane treeScroll=null;
	private JTree tree=null;
	private ConfigurationTreeModel model=null;
	private ConfigurationTreeNode root=null;
	
	private JSplitPane split=new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
	
	public ConfigurationDialogPanel(){
		init();
	}
	
	private void init(){
		setLayout(new BorderLayout());		
		setupTree();
		split.setLeftComponent(treeScroll);
		//split.setRightComponent(new JPanel());
		split.setDividerSize(2);
		split.setDividerLocation(100);
		
		add(split,BorderLayout.CENTER);
	}
	
	private void setupTree(){
		root= new ConfigurationTreeNode();
		model=new ConfigurationTreeModel(root);
		tree=new JTree(model);
		tree.setRootVisible(false);
		treeScroll=new JScrollPane(tree);
		tree.setCellRenderer(new ConfigurationTreeCellRenderer());		
		treeScroll.setMinimumSize(new Dimension(100,20));
		tree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
		tree.setShowsRootHandles(true);
		ConfigurationPanel display=new ConfigurationPanel("Display","Display settings");
		ConfigurationTreeNode displayNode=new ConfigurationTreeNode(display);
		ConfigurationPanel fonts=new FontConfigurationPanel("Fonts","Fonts settings");
		ConfigurationPanel colors=new ColorConfigurationPanel("Colors","Colors settings");
		ConfigurationPanel help=new HelpConfigurationPanel("Help","Help URL");
		ConfigurationTreeNode fontsNode=new ConfigurationTreeNode(fonts);
		ConfigurationTreeNode colorsNode=new ConfigurationTreeNode(colors);
		ConfigurationTreeNode helpNode=new ConfigurationTreeNode(help);
		
		root.add(displayNode);
		displayNode.add(colorsNode);
		displayNode.add(fontsNode);		
		displayNode.add(helpNode);
		tree.makeVisible(new TreePath(fontsNode.getPath()));
		model.nodeStructureChanged(root);
		
		tree.expandRow(0);
		tree.getSelectionModel().addTreeSelectionListener(new TreeSelectionListener(){
			public void valueChanged(TreeSelectionEvent e){
				treeNodeSelected(e);
			}
		});
		tree.setSelectionRow(0);
	}
	
	private void treeNodeSelected(TreeSelectionEvent e){
		if(e.getNewLeadSelectionPath()!=null){
			ConfigurationTreeNode node=(ConfigurationTreeNode)e.getNewLeadSelectionPath().getLastPathComponent();
			ConfigurationPanel panel=(ConfigurationPanel)node.getUserObject();			
			split.setRightComponent(panel);
		}
	}
}
