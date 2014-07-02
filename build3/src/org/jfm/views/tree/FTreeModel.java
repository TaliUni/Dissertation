package org.jfm.views.tree;

import javax.swing.tree.*;
import java.util.Arrays;
import org.jfm.main.Options;
import org.jfm.filesystems.*;

/**
 * Title:        Java File Manager
 * Description:  
 * Copyright:    Copyright (c) 2001
 * Company:      Home
 * @author Giurgiu Sergiu
 * @version 1.0
 */

public class FTreeModel extends DefaultTreeModel {
  
@SuppressWarnings("unused")
  private int location;
  private DefaultMutableTreeNode root;
  
  public FTreeModel(int location,DefaultMutableTreeNode root) {
    super(root);
    this.root=root;
    root.add(new DefaultMutableTreeNode("Loading..."));
    setLocation(location);
    initTree(Options.getStartDirectory());
  }
  
  public void setLocation(int l){
    location=l;
  }
  
  public void initTree(String dir){
    JFMFile  f=null;//new FileElement(dir);
    JFMFile rootFile=getRootFile(f);
    root.setUserObject(rootFile);
    fillTreeFromNode(root);
  }
  
  public void fillTreeFromNode(DefaultMutableTreeNode n){
    
  	JFMFile el=(JFMFile)n.getUserObject();    
    if(!el.isDirectory()) return;        
    n.removeAllChildren();
    JFMFile[] paths=el.listFiles();
    
    JFMFile[] childs=paths;
    
    Arrays.sort(childs);
    
    for(int i=0;i<childs.length;i++){
      DefaultMutableTreeNode a_node=new DefaultMutableTreeNode(childs[i]);
      if(childs[i].isDirectory()) a_node.add(new DefaultMutableTreeNode("Loading..."));
      n.add(a_node);
    }
    
    this.nodeStructureChanged(n);
  }
  
  private JFMFile getRootFile(JFMFile f){
    return f.getRootDriveFile();
  }
}