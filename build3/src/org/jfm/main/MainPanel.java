package org.jfm.main;

import java.awt.*;
import org.jfm.po.*;
import org.jfm.views.*;
import org.jfm.event.*;

import javax.swing.*;
import java.util.Vector;
import java.util.prefs.Preferences;

/**
 * Title:        Java File Manager
 * Description:
 * Copyright:    Copyright (c) 2001
 * Company:      Home
 * @author Giurgiu Sergiu
 * @version 1.0
 */
@SuppressWarnings("unchecked")
public class MainPanel extends JPanel {
  private BorderLayout borderLayout1 = new BorderLayout();

  private ButtonsPanel btPanel=new ButtonsPanel();
  private JSplitPane split = new JSplitPane();
  private Preferences prefs=Options.getPreferences();
  private JFMView leftPanel=null;
  private JFMView rightPanel=null;
  //-----------------------------me ------------------------------------------
  private TextArea ta = new TextArea(70,50);
  //------------------------------end me--------------------------------------
  
//-----------------------------me ------------------------------------------
  
  //------------------------------end me--------------------------------------

  public MainPanel() {
    try {
      jbInit();
    }
    catch(Exception ex) {
      ex.printStackTrace();
    }
  }

  void jbInit() throws Exception {
    removeSplitKeyBindings();
    this.setLayout(borderLayout1);
    
  //-----------------------------me ------------------------------------------
    this.add(ta, BorderLayout.WEST);
    ta.setText("Testing");
    //------------------------------end me--------------------------------------

    this.add(btPanel,BorderLayout.SOUTH);
    this.add(split,  BorderLayout.CENTER);

    leftPanel=createLeftView();
    rightPanel=createRightView();

    leftPanel.requestFocus();
    split.add(leftPanel, JSplitPane.LEFT);
    split.add(rightPanel, JSplitPane.RIGHT);
    split.setDividerLocation(350);
    setButtonsPanel();
    Broadcaster.addChangeViewListener(new ChangeViewListener(){
       public void viewChanged(ChangeViewEvent ev){       		
       	 	if(Options.getActivePanel().equals(leftPanel)){
       	 		if(ev.getViewRep()!=null){
       	 			Options.getPreferences().put(Options.JFM_LEFTVIEWPANEL_PREF,ev.getViewRep().getClassName());
       	 		}
       	 		if(ev.getFilesystemClassName()!=null){
       	 			Options.getPreferences().put(Options.JFM_LEFTVIEWPANEL_FILESYSTEM_PREF,ev.getFilesystemClassName());
       	 		}
       	 		leftPanel=createLeftView();
       	 		split.setLeftComponent(leftPanel);
       	 	}else{
       	 		if(ev.getViewRep()!=null){
       	 			Options.getPreferences().put(Options.JFM_RIGHTVIEWPANEL_PREF,ev.getViewRep().getClassName());
       	 		}
       	 		if(ev.getFilesystemClassName()!=null){
       	 			Options.getPreferences().put(Options.JFM_RIGHTVIEWPANEL_FILESYSTEM_PREF,ev.getFilesystemClassName());
       	 		}
       	 		rightPanel=createRightView();
       	 		split.setRightComponent(rightPanel);
       	 	}
       	 	split.setDividerLocation(split.getWidth()/2);
       }
    });
    
    Broadcaster.addChangeDirectoryListener(new ChangeDirectoryListener(){
    	public void changeDirectory(ChangeDirectoryEvent event){
    		if(event.getDirectory()==null ||event.getDirectory().getAbsolutePath()==null ) return;
    		if(event.getSource()==leftPanel)
    		{
    			Options.getPreferences().put(Options.JFM_LEFTVIEWPANELDIR_PREF, event.getDirectory().getAbsolutePath());
    		}
    		else
    		{
    			Options.getPreferences().put(Options.JFM_RIGHTVIEWPANELDIR_PREF, event.getDirectory().getAbsolutePath());
    		}
    	}
    });
  }

  private JFMView createRightView() {
  	String viewClass=prefs.get(Options.JFM_RIGHTVIEWPANEL_PREF,JFMView.DEFAULT_VIEW.getClassName());
  	String filesystem=prefs.get(Options.JFM_RIGHTVIEWPANEL_FILESYSTEM_PREF,null);
    //create the default view
    JFMView view = JFMView.createView(JFMView.getViewRepresentation(viewClass),filesystem);
    prefs.put(Options.JFM_RIGHTVIEWPANEL_FILESYSTEM_PREF,view.getFilesystemName());
    //Options.setActivePanel(view);
    view.requestFocus();
    return view;
  }

  private JFMView createLeftView() {
  	String viewClass=prefs.get(Options.JFM_LEFTVIEWPANEL_PREF,JFMView.DEFAULT_VIEW.getClassName());
  	String filesystem=prefs.get(Options.JFM_LEFTVIEWPANEL_FILESYSTEM_PREF,null);
    //create the default view
    //JFMView view = JFMView.createView(JFMView.getViewRepresentation(viewClass),"org.jfm.filesystems.baracuda.JFMBaracudaFilesystem");
  	JFMView view = JFMView.createView(JFMView.getViewRepresentation(viewClass),filesystem);
  	prefs.put(Options.JFM_LEFTVIEWPANEL_FILESYSTEM_PREF,view.getFilesystemName());
    //Options.setActivePanel(view);
    return view;
  }
  

  private void removeSplitKeyBindings(){
    ActionMap newSplitActionMap=new ActionMap();
    split.setActionMap(newSplitActionMap);
  }

  private void setButtonsPanel(){
    Vector buttons=new Vector();

    JButton f1Button=new JButton("Help (F1)");
    HelpAction help=new HelpAction();
    f1Button.getInputMap(WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("F1"),"helpButton");
    f1Button.getActionMap().put("helpButton",help);
    f1Button.addActionListener(help);    
  //-----------------------------me ------------------------------------------
  JButton FileNameButton=new JButton("FileName");
  PanelMenuAction menu=new PanelMenuAction();
 // f2Button.getInputMap(WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("F2"),"menuButton");
  //f2Button.getActionMap().put("menuButton",menu);
  FileNameButton.addActionListener(menu);
    //------------------------------end me--------------------------------------
//    JButton f2Button=new JButton("Menu (F2)");
//    PanelMenuAction menu=new PanelMenuAction();
//    f2Button.getInputMap(WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("F2"),"menuButton");
//    f2Button.getActionMap().put("menuButton",menu);
//    f2Button.addActionListener(menu);

    JButton f3Button=new JButton("View (F3)");
    ViewFileAction view=new ViewFileAction();
    f3Button.getInputMap(WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("F3"),"viewButton");
    f3Button.getActionMap().put("viewButton",view);
    f3Button.addActionListener(view);

    JButton f4Button=new JButton("Edit (F4)");
    EditFileAction edit=new EditFileAction();
    f4Button.getInputMap(WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("F4"),"editButton");
    f4Button.getActionMap().put("editButton",edit);
    f4Button.addActionListener(edit);

    JButton f5Button=new JButton("Copy (F5)");
    CopyAction copy=new CopyAction();
    f5Button.getInputMap(WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("F5"),"copyButton");
    f5Button.getActionMap().put("copyButton",copy);
    f5Button.addActionListener(copy);

    JButton f6Button=new JButton("Move (F6)");
    MoveAction move=new MoveAction();
    f6Button.getInputMap(WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("F6"),"moveButton");
    f6Button.getActionMap().put("moveButton",move);
    f6Button.addActionListener(move);

    JButton f7Button=new JButton("Mkdir (F7)");
    MkdirAction mkdir=new MkdirAction();
    f7Button.getInputMap(WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("F7"),"mkdirButton");
    f7Button.getActionMap().put("mkdirButton",mkdir);
    f7Button.addActionListener(mkdir);

    JButton f8Button=new JButton("Delete (F8)");
    DeleteAction del=new DeleteAction();
    f8Button.getInputMap(WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("F8"),"delButton");
    f8Button.getActionMap().put("delButton",del);
    f8Button.addActionListener(del);

    JButton f10Button=new JButton("Quit");
    QuitAction quit=new QuitAction();
    f10Button.getInputMap(WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("F10"),"quitButton");
    f10Button.getActionMap().put("quitButton",quit);
    f10Button.addActionListener(quit);

    buttons.addElement(f1Button);
  //-----------------------------me ------------------------------------------
    buttons.addElement(FileNameButton);
    //------------------------------end me--------------------------------------
   
    buttons.addElement(f3Button);
    buttons.addElement(f4Button);
    buttons.addElement(f5Button);
    buttons.addElement(f6Button);
    buttons.addElement(f7Button);
    buttons.addElement(f8Button);
    buttons.addElement(f10Button);

    btPanel.setButtons(buttons);
  }
}
