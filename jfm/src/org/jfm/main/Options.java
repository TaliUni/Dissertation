package org.jfm.main;

import java.awt.Color;
import java.awt.Font;
import java.util.Enumeration;
import java.util.StringTokenizer;
import java.util.Vector;
import java.util.jar.JarEntry;
import java.util.prefs.Preferences;
import java.util.zip.ZipEntry;

import org.jfm.event.*;
import org.jfm.views.JFMView;
import org.jfm.views.JFMViewRepresentation;

import javax.swing.*;


/**
 * Title:        Java File Manager
 * Description:
 * Copyright:    Copyright (c) 2001
 * Company:      Home
 * @author Giurgiu Sergiu
 * @version 1.0
 */
public class Options {
  public static final int LEFT_PANEL=1;
  public static final int RIGHT_PANEL=2;
  
  /**
   * The key that is used to save the user's preference on what the left panel should be
   */
  public static final String JFM_LEFTVIEWPANEL_PREF="JFM.leftviewpanel";
  /**
   * The key that is used to save the user's preference on what the right panel should be
   */
  public static final String JFM_RIGHTVIEWPANEL_PREF="JFM.rightviewpanel";
  /**
   * The key that is used to save the user's preference on what the right panel last browsed directory is
   */
  public static final String JFM_RIGHTVIEWPANELDIR_PREF="JFM.rightviewpaneldir";
  /**
   * The key that is used to save the user's preference on what the left panel last browsed directory is
   */
  public static final String JFM_LEFTVIEWPANELDIR_PREF="JFM.leftviewpaneldir";
  /**
   * The key that is used to save the user's preference on what filesystem the left panel should have
   */
  public static final String JFM_LEFTVIEWPANEL_FILESYSTEM_PREF="JFM.leftviewpanel.filesystem";
  /**
   * The key that is used to save the user's preference on what filesystem the right panel should have
   */
  public static final String JFM_RIGHTVIEWPANEL_FILESYSTEM_PREF="JFM.rightviewpanel.filesystem";
  
  /**
   * The key that is used to save the user's preference on the default baracuda URL to use
   */
  public static final String JFM_BARACUDA_URL="JFM.baracuda.rooturl";
  /**
   * The key that is used to save the user's preference on the default baracuda username
   */
  public static final String JFM_BARACUDA_USERNAME="JFM.baracuda.username";
  
  /**
   * Help root
   */
  public static final String JFM_HELP_URL="JFM.help.url";
  

  private static String startDirectory=null;
  private static int[] leftSelections=new int[]{-1,-1};
  private static int[] rightSelections=new int[]{-1,-1};
  private static JFMView activePanel;
  private static JFMView inactivePanel;
  private static Vector leftFiles;
  private static Vector rightFiles;
  private static MainFrame frame;

  private static JPopupMenu opMenu=new JPopupMenu();
  private static JMenuItem copyMenu=new JMenuItem("Copy");
  private static JMenuItem moveMenu=new JMenuItem("Move");
  private static JMenuItem deleteMenu=new JMenuItem("Delete");
  private static JMenuItem viewMenu=new JMenuItem("View");
  private static JMenuItem editMenu=new JMenuItem("Edit");
  private static JMenuItem propsMenu=new JMenuItem("Properties");
  
  //users preferences for this application
  private static Preferences preferences=Preferences.userRoot();

  static{
    setUpMenuStuff();
  }

  /**
   * The user's preferences.
   * @return
   */
  public static Preferences getPreferences(){
  	try {
	  	preferences.sync();	
	} catch (Exception e) {
		e.printStackTrace();
	}
  	return preferences;
  }
  
  public static void savePreferences(){
  	try {
		getPreferences().flush();
	} catch (Exception e) {
		e.printStackTrace();
	}
  }
  
  public static Font getDefaultPanelsFont(){
  	return Font.decode("Arial-PLAIN-10");
  }
  
  public static Font getPanelsFont(){
  	String font=getPreferences().get("JFM.panels.font","Arial-PLAIN-10");  	
  	return Font.decode(font);
  }

  public static void setPanelsFont(Font f){
  	if(f==null)return;
	String	strStyle;

	if (f.isBold()) {
	    strStyle = f.isItalic() ? "BOLDITALIC" : "BOLD";
	} else {
	    strStyle = f.isItalic() ? "ITALIC" : "PLAIN";
	}
  	
  	getPreferences().put("JFM.panels.font",f.getFamily()+"-"+strStyle+"-"+f.getSize());  	  	
  }
  
  public static Color getDefaultBackgroundColor(){
  	return new Color(255,255,255,255);
  }
  
  public static Color getBackgroundColor(){
  	int red=255;
  	int green=255;
  	int blue=255;
  	int alpha=255;
  	String colorSpecs=getPreferences().get("JFM.backgroundcolor","255,255,255,255");
  	StringTokenizer tokenizer=new StringTokenizer(colorSpecs,",");
	try {
		red=Integer.parseInt(tokenizer.nextToken());
		green=Integer.parseInt(tokenizer.nextToken());
		blue=Integer.parseInt(tokenizer.nextToken());
		alpha=Integer.parseInt(tokenizer.nextToken());
	} catch (Exception e) {
		e.printStackTrace();
	}
  	return new Color(red,green,blue,alpha);
  }
  
  public static void setForegroundColor(Color c){
  	getPreferences().put("JFM.foregroundcolor",c.getRed()+","+c.getGreen()+","+c.getBlue()+","+c.getAlpha());
  }
  
  public static Color getDefaultForegroundColor(){
  	return new Color(0,0,0,255);
  }
  
  public static Color getForegroundColor(){
  	int red=0;
  	int green=0;
  	int blue=0;
  	int alpha=255;
  	String colorSpecs=getPreferences().get("JFM.foregroundcolor","0,0,0,255");
  	StringTokenizer tokenizer=new StringTokenizer(colorSpecs,",");
	try {
		red=Integer.parseInt(tokenizer.nextToken());
		green=Integer.parseInt(tokenizer.nextToken());
		blue=Integer.parseInt(tokenizer.nextToken());
		alpha=Integer.parseInt(tokenizer.nextToken());
	} catch (Exception e) {
		e.printStackTrace();
	}
  	return new Color(red,green,blue,alpha);
  }
  
  public static void setBackgroundColor(Color c){
  	getPreferences().put("JFM.backgroundcolor",c.getRed()+","+c.getGreen()+","+c.getBlue()+","+c.getAlpha());
  }

  public static void setMarkedColor(Color c){
  	getPreferences().put("JFM.markedcolor",c.getRed()+","+c.getGreen()+","+c.getBlue()+","+c.getAlpha());
  }
  
  public static Color getDefaultMarkedColor(){
  	return new Color(255,0,0,255);
  }
  
  public static Color getMarkedColor(){
  	int red=255;
  	int green=0;
  	int blue=0;
  	int alpha=255;
  	String colorSpecs=getPreferences().get("JFM.markedcolor","255,0,0,255");
  	StringTokenizer tokenizer=new StringTokenizer(colorSpecs,",");
	try {
		red=Integer.parseInt(tokenizer.nextToken());
		green=Integer.parseInt(tokenizer.nextToken());
		blue=Integer.parseInt(tokenizer.nextToken());
		alpha=Integer.parseInt(tokenizer.nextToken());
	} catch (Exception e) {
		e.printStackTrace();
	}
  	return new Color(red,green,blue,alpha);
  }
  
  public static void setDirectoriesSelectedOnAsterisk(boolean flag){
  	getPreferences().put("JFM.dirsselectedonasterisk",Boolean.toString(flag));
  }  
  public static boolean getDirectoriesSelectedOnAsterisk(){
  	return Boolean.valueOf(getPreferences().get("JFM.dirsselectedonasterisk","true")).booleanValue();
  }

  public static void setDirectoriesSelectedOnPlus(boolean flag){
  	getPreferences().put("JFM.dirsselectedonplus",Boolean.toString(flag));
  }  
  public static boolean getDirectoriesSelectedOnPlus(){
  	return Boolean.valueOf(getPreferences().get("JFM.dirsselectedonplus","true")).booleanValue();
  }
  
  
  private static void setUpMenuStuff(){
    opMenu.add(copyMenu);
    opMenu.add(moveMenu);
    opMenu.add(deleteMenu);
    opMenu.addSeparator();
    opMenu.add(viewMenu);
    opMenu.add(editMenu);
    opMenu.addSeparator();
    opMenu.add(propsMenu);

    copyMenu.addActionListener(new org.jfm.po.CopyAction());
    moveMenu.addActionListener(new org.jfm.po.MoveAction());
    deleteMenu.addActionListener(new org.jfm.po.DeleteAction());
    viewMenu.addActionListener(new org.jfm.po.ViewFileAction());
    editMenu.addActionListener(new org.jfm.po.EditFileAction());
    propsMenu.addActionListener(new org.jfm.po.FilePropertiesAction());
  }

  public static JPopupMenu getPopupMenu(){
    return opMenu;
  }

  public static void setMainFrame(MainFrame f){
    frame=f;
  }

  public static MainFrame getMainFrame(){
    return frame;
  }

  public static void setRightFiles(Vector v){
    rightFiles=v;
  }

  public static Vector getRightFiles(){
    return rightFiles;
  }

  public static void setLeftFiles(Vector v){
    leftFiles=v;
  }

  public static Vector getLeftFiles(){
    return leftFiles;
  }

  public static void setStartDirectory(String path){
    startDirectory=path;
  }

  public static String getStartDirectory(){
    if(startDirectory==null){
      setStartDirectory(System.getProperty("user.dir"));
    }
    return startDirectory;
  }

  public static void setRightPanelSelections(int[] sel){
    rightSelections=sel;
    FileListSelectionEvent ev=new FileListSelectionEvent();
    ev.setSource(null);
    ev.setFilesIndexes(sel);
    ev.setPanelLocation(RIGHT_PANEL);
    Broadcaster.notifyFileListSelectionListener(ev);
  }

  public static int[] getRightPanelSelections(){
    return rightSelections;
  }

  public static void setLeftPanelSelections(int[] sel){
    leftSelections=sel;
    FileListSelectionEvent ev=new FileListSelectionEvent();
    ev.setSource(null);
    ev.setFilesIndexes(sel);
    ev.setPanelLocation(LEFT_PANEL);
    Broadcaster.notifyFileListSelectionListener(ev);
  }

  public static int[] getLeftPanelSelections(){
    return leftSelections;
  }

  public static void setActivePanel(JFMView panel){
	  if(activePanel!=null){
		  activePanel.setActive(false);
	  }
	if(inactivePanel!=activePanel)
	{
		inactivePanel=activePanel;
	}
    activePanel=panel;
    activePanel.setActive(true);
  }

  public static JFMView getActivePanel(){
    return activePanel;
  }

  public static JFMView getInactivePanel(){
	  return inactivePanel;
  }
}
