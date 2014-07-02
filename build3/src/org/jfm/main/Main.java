package org.jfm.main;

import javax.swing.UIManager;

import org.jfm.filesystems.JFMFileSystem;
import org.jfm.views.JFMView;

import java.awt.*;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;


/**
 * Title:        Java File Manager
 * Description:
 * Copyright:    Copyright (c) 2001
 * Company:      Home
 * @author Giurgiu Sergiu
 * @version 1.0
 */
public class Main {
  private boolean packFrame = false;

  /**Construct the application*/
  public Main() {
    MainFrame frame = new MainFrame();
    //Validate frames that have preset sizes
    //Pack frames that have useful preferred size info, e.g. from their layout
    if (packFrame) {
      frame.pack();
    }
    else {
      frame.validate();
    }
    //Center the window
    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    Dimension frameSize = frame.getSize();
    if (frameSize.height > screenSize.height) {
      frameSize.height = screenSize.height;
    }
    if (frameSize.width > screenSize.width) {
      frameSize.width = screenSize.width;
    }
    frame.setLocation((screenSize.width - frameSize.width) / 2, (screenSize.height - frameSize.height) / 2);
    frame.setVisible(true);
  }
  
  
  /**Main method*/
  public static void main(String[] args) {    
	try {    	
      //UIManager.setLookAndFeel("com.sun.java.swing.plaf.gtk.GTKLookAndFeel");
      UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
      JFMView.registerViews();      
      JFMFileSystem.registerFilesystems();
	}
    catch(Exception e) {
      e.printStackTrace();
    }    
   // final SplashWindow splash=new SplashWindow("someimage.gif");
    //splash.setVisible(true);
    new Main();
    EventQueue.invokeLater(new Runnable(){
    	public void run() {
    	//	splash.dispose();
    	}
    });
  }
}
