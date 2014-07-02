package org.jfm.views;

import javax.swing.*;
import org.jfm.event.*;

import java.util.Vector;

import org.jfm.filesystems.JFMFileSystem;
import org.jfm.filesystems.JFMFile;

/**
 * Title:        Java File Manager
 * Description:
 * Copyright:    Copyright (c) 2001
 * Company:      Home
 * @author Giurgiu Sergiu
 * @version 1.0
 */

public abstract class JFMView extends JPanel {

	protected boolean isActive=false;
	
  protected javax.swing.JLabel statusLabel;

  /**
   * The default view is the org.jfm.views.list.detailview.DetailView. This is the fallback view that will be created if the requested
   * view isn't available.
   */
  public final static JFMViewRepresentation DEFAULT_VIEW=new JFMViewRepresentation("Details","org.jfm.views.list.detailview.DetailView");

  /**
   * This vector contains all registered views (either as plugins or implemented in the application).
   * It contains a list of strings that are the full class name of the view.
   */
  private static Vector registeredViews=new Vector();

  /**
   * The view's filesystem.
   */
  protected JFMFileSystem filesystem=null;

  /**
   * This method returns the current working directory of the view. If it doesn't make sense to return it,
   * null should be an acceptable value.
   * @return JFMFile the current working directory.
   */
  public abstract JFMFile getCurrentWorkingDirectory();

  /**
   * This method returns the current selected file from the view. If no file is selected it just returns null.
   * @return JFMFile the current selected file.
   */
  public abstract JFMFile getSelectedFile();

  /**
   * This method returns the current selected files from the view. If no files are selected it just returns null.
   * @return JFMFile the current selected files.
   */
  public abstract JFMFile[] getSelectedFiles();
  
  public void setActive(boolean flag){
	  isActive=flag;
  }
  
  public boolean isActive(){
	  return isActive;
  }
  
/**
   * This method searches for the defined views, and adds them to the registered view's list.
   */
  public static void registerViews(){
  	//instead of searching, at the moment, we just .... know them
  	JFMViewRepresentation details=new JFMViewRepresentation ("Details","org.jfm.views.list.detailview.DetailView");
  	JFMViewRepresentation brief=new JFMViewRepresentation ("Brief","org.jfm.views.list.briefview.BriefView");
  	@SuppressWarnings("unused")
  	JFMViewRepresentation tree=new JFMViewRepresentation ("Tree","org.jfm.views.tree.TreeViewPanel");
  	@SuppressWarnings("unused")
  	JFMViewRepresentation quickview=new JFMViewRepresentation ("Quick","org.jfm.views.fview.FileViewPanel");
  	
  	JFMView.registerView(details);
  	JFMView.registerView(brief);
  	//JFMView.registerView(tree);
  	//JFMView.registerView(quickview);
  }  
  /**
   * Add a view to the registered views list
   * @param view the view to be registered
   */
  @SuppressWarnings("unchecked")
  public static void registerView(JFMViewRepresentation view){
  	registeredViews.add(view);
  }
  
  /**
   * Returns the registered views
   * @return the registered views
   */
  public static Vector getRegisteredViews(){
  	return registeredViews;
  }
  
  /**
   * Returns a JFMRepresentation instance (taken from the registered views list)
   * that has the className equals with the specified name 
   * @param className the className of the JFMRepresentation class that we're looking for
   * @return a JFMRepresentation instance
   */
  public static JFMViewRepresentation getViewRepresentation(String className){
  	for(int i=0;i<registeredViews.size();i++){
  		JFMViewRepresentation repView=(JFMViewRepresentation)registeredViews.elementAt(i);
  		if(repView.getClassName().equals(className)){
  			return repView;
  		}
  	}
  	return null;
  }
  /**
   * This method creates the view specified by <code>name</code>. If the view is not found the the default view is returned.
   * @param name The views representation
   * @return a representation view
   */
  public static JFMView createView(JFMViewRepresentation representation,String filesystem){
          JFMView view=null;
          if(representation==null){
                  representation=DEFAULT_VIEW;
          }

          //search trough all the registered views, until we find the one that is called as requested
          for(int i=0;i<registeredViews.size();i++){
                  JFMViewRepresentation viewClassName=(JFMViewRepresentation)registeredViews.elementAt(i);
                  if(representation.equals(viewClassName)){
                          try {
                                  Class viewClass=Class.forName(viewClassName.getClassName());
                                  view=(JFMView)viewClass.getConstructor(new Class[]{String.class}).newInstance(new Object[]{filesystem});
                                  //viewClass.newInstance();
                                  return view;
                          } catch (Exception e) {
                                  e.printStackTrace();
                                  //ignore the exception
                          }
                  }
          }

          if(view==null){
                  try {
                          Class viewClass=Class.forName(DEFAULT_VIEW.getClassName());
                          view=(JFMView)viewClass.newInstance();
                  } catch (Exception e) {
                          e.printStackTrace();
                          //ignore the exception
                  }
          }
          return view;
  }
  
  

  private void initializeFilesystem(String name){
          //TODO: the name of the filesystem should  be got from some options class
          //or should be passed as a parameter, or ... I have no idea now how should i get it
          filesystem=JFMFileSystem.createFileSystem(name);
  }

  public String getFilesystemName()
  {	  
	  return (filesystem!=null?filesystem.getClass().getName():null); 
  }

  /**
   * Empty constructor. Calls the init() method.
   */
  public JFMView() {
	  this(null);
  }
  
  public JFMView(String fileSystemName){
    init();
    initializeFilesystem(fileSystemName);  
  }

  /**
   * Initialize the view
   */
  private void init(){
    Broadcaster.addChangePanelListener(new ChangePanelListener(){
      public void changePanel(ChangePanelEvent e){      	
        if(!e.getSource().equals(JFMView.this)){
          JFMView.this.requestFocus();          
        }
      }
    });    
    statusLabel=new javax.swing.JLabel();
    statusLabel.setOpaque(false);
  }
}
