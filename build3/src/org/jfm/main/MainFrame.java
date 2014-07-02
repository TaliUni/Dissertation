package org.jfm.main;

import java.awt.*;
import java.awt.event.*;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;

import javax.swing.*;

import org.jfm.event.*;
import org.jfm.filesystems.JFMFileSystem;
import org.jfm.main.configurationdialog.ConfigurationDialog;
import org.jfm.views.FontDialog;
import org.jfm.views.JFMView;
import org.jfm.views.JFMViewRepresentation;


/**
 * Title:        Java File Manager
 * Description:
 * Copyright:    Copyright (c) 2001
 * Company:      Home
 * @author Giurgiu Sergiu
 * @version 1.0
 */

public class MainFrame extends JFrame {
  private JPanel contentPane;

  private JMenuBar jMenuBar1 = new JMenuBar();
  private JMenu jMenuFile = new JMenu();
  private JMenu jMenuConfiguration = new JMenu("Configuration");
  
  private JMenuItem jMenuConfigurationDialog = new JMenuItem("Show Configuration");
  
  private JMenuItem jMenuConfigurationBackground = new JMenuItem("Background");
  private JMenuItem jMenuConfigurationForeground = new JMenuItem("Foreground");
  private JMenuItem jMenuConfigurationFont = new JMenuItem("Font");
  
  private JMenuItem jMenuFileExit = new JMenuItem();
  private JMenuItem jMenuFileSearch =new JMenuItem("Search");
  private JMenu jMenuHelp = new JMenu();
  private JMenuItem jMenuHelpAbout = new JMenuItem();
  private JMenu jMenuView=new JMenu("View");
  private JMenu jMenuFilesystem=new JMenu("Filesystem");

  private JMenu jMenuViewLookandFeel = new JMenu("Look&Feel");

  private ButtonGroup viewGroup=new ButtonGroup();
  private ButtonGroup filesystemGroup=new ButtonGroup();

  private JLabel statusBar = new JLabel();
  private BorderLayout borderLayout1 = new BorderLayout();
  private MainPanel mainPanel=new MainPanel();



  /**Construct the frame*/
  public MainFrame() {
    enableEvents(AWTEvent.WINDOW_EVENT_MASK);
    try {
      jbInit();
    }
    catch(Exception e) {
      e.printStackTrace();
    }
  }

  /**Component initialization*/
  private void jbInit() throws Exception  {
    Options.setMainFrame(this);
//    image1 = new ImageIcon(org.jfm.main.MainFrame.class.getResource("openFile.gif"));
    //setIconImage(Toolkit.getDefaultToolkit().createImage(MainFrame.class.getResource("[Your Icon]")));
    contentPane = (JPanel) this.getContentPane();
    contentPane.setLayout(borderLayout1);
    statusBar.setBorder(BorderFactory.createLoweredBevelBorder());
    this.setSize(new Dimension(800, 600));

    this.setTitle("Java File Manager");

    statusBar.setText(" ");
    jMenuFile.setText("File");
    jMenuFileExit.setText("Exit");
    jMenuFileExit.addActionListener(new ActionListener()  {
      public void actionPerformed(ActionEvent e) {
        jMenuFileExit_actionPerformed(e);
      }
    });
    jMenuHelp.setText("Help");
    jMenuHelpAbout.setText("About");
    jMenuHelpAbout.addActionListener(new ActionListener()  {
      public void actionPerformed(ActionEvent e) {
        jMenuHelpAbout_actionPerformed(e);
      }
    });
    jMenuFileSearch.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        search();
      }
    });
    jMenuConfigurationBackground.addActionListener(new ActionListener(){
    	public void actionPerformed(ActionEvent e){
    		changeBackgroundColors();
    	}
    });
    jMenuConfigurationForeground.addActionListener(new ActionListener(){
    	public void actionPerformed(ActionEvent e){
    		changeForegroundColors();
    	}
    });
    jMenuConfigurationFont.addActionListener(new ActionListener(){
    	public void actionPerformed(ActionEvent e){
    		changeFont();
    	}
    });
    jMenuConfigurationDialog.addActionListener(new ActionListener(){
    	public void actionPerformed(ActionEvent e){
    		showConfDialog();
    	}
    });    
    
    Vector views=JFMView.getRegisteredViews();
    for(int i=0;i<views.size();i++){
    	final JFMViewRepresentation rep=(JFMViewRepresentation)views.get(i);
    	JRadioButtonMenuItem viewItem=new JRadioButtonMenuItem(rep.getName());
//    	if(i==0)viewItem.setSelected(true);
    	viewItem.addActionListener(new ActionListener() {
  		public void actionPerformed(ActionEvent e) {  				
   				ChangeViewEvent ev=new ChangeViewEvent();
   				ev.setSource(MainFrame.this);
   				ev.setViewRep(rep);
   				Broadcaster.notifyChangeViewListeners(ev);   				
    		}
    	});
    	jMenuView.add(viewItem);
    	viewGroup.add(viewItem);
    }
    
    Hashtable<String, String> registeredFilesystems=JFMFileSystem.getRegisteredFilesystems();
    Enumeration<String> registeredFilesystemsValues=registeredFilesystems.keys();
    while(registeredFilesystemsValues.hasMoreElements())
    {
    	String name=registeredFilesystemsValues.nextElement();
    	final String className=registeredFilesystems.get(name);
    	JRadioButtonMenuItem filesystemItem=new JRadioButtonMenuItem(name);
    	filesystemItem.addActionListener(new ActionListener(){
    		public void actionPerformed(ActionEvent e) {
   				ChangeViewEvent ev=new ChangeViewEvent();
   				ev.setSource(MainFrame.this);
   				ev.setFilesystemClassName(className);
   				Broadcaster.notifyChangeViewListeners(ev);   				    			    		
    		}
    	});
    	jMenuFilesystem.add(filesystemItem);
    	filesystemGroup.add(filesystemItem);
    }
    jMenuView.addSeparator();
    jMenuView.add(jMenuViewLookandFeel);
    addLookandFeels();
    jMenuFile.add(jMenuFileSearch);
    jMenuFile.add(jMenuFileExit);
    jMenuConfiguration.add(jMenuConfigurationDialog);
    //jMenuConfiguration.add(jMenuConfigurationBackground);
    //jMenuConfiguration.add(jMenuConfigurationForeground);
    //jMenuConfiguration.add(jMenuConfigurationFont);
    
    jMenuHelp.add(jMenuHelpAbout);
    jMenuBar1.add(jMenuFile);
    jMenuBar1.add(jMenuConfiguration);
    jMenuBar1.add(jMenuFilesystem);
    jMenuBar1.add(jMenuView);
    jMenuBar1.add(jMenuHelp);
    
    jMenuFileExit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q,KeyEvent.CTRL_MASK));
    jMenuFileSearch.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S,KeyEvent.CTRL_MASK));
    this.setJMenuBar(jMenuBar1);
    contentPane.add(statusBar, BorderLayout.SOUTH);
      contentPane.add(mainPanel,BorderLayout.CENTER);
  }
  
  private void showConfDialog(){
  	ConfigurationDialog d=new ConfigurationDialog(this,"Configuration");
  	d.setLocationRelativeTo(this);
  	d.setVisible(true);
  }

  private void addLookandFeels(){
    final UIManager.LookAndFeelInfo[] lfs=  UIManager.getInstalledLookAndFeels();
    //java.util.Arrays.sort(lfs);
    if(lfs!=null){
      for(int i=0;i<lfs.length;i++){
        JMenuItem look=new JMenuItem(lfs[i].getName());
        final String className=lfs[i].getClassName();
        look.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
              try{
                UIManager.setLookAndFeel(className);
                SwingUtilities.updateComponentTreeUI(MainFrame.this);
              }catch(Exception exception){exception.printStackTrace();}
            }
        });
        jMenuViewLookandFeel.add(look);
      }
    }
  }
  
  private void changeFont(){
  	Font f=FontDialog.showDialog(this,"Choose font",true);
  	if(f==null)return;
  	Options.setPanelsFont(f);
  	FontChangeEvent event=new FontChangeEvent();
  	event.setSource(this);
  	Broadcaster.notifyFontChangeListeners(event);
  }
  
  private void changeForegroundColors(){
  	Color c=JColorChooser.showDialog(this,"Change foreground",Options.getForegroundColor());
  	if(c==null) return;
  	Options.setForegroundColor(c);
  	ColorChangeEvent event=new ColorChangeEvent(ColorChangeEvent.FOREGROUND);
  	event.setSource(this);
  	Broadcaster.notifyColorChangeListeners(event);  	
  }
  
  private void changeBackgroundColors(){  	
  	Color c=JColorChooser.showDialog(this,"Change background",Options.getBackgroundColor());
  	if(c==null) return;
  	Options.setBackgroundColor(c);
  	ColorChangeEvent event=new ColorChangeEvent(ColorChangeEvent.BACKGROUND);
  	event.setSource(this);
  	Broadcaster.notifyColorChangeListeners(event);
  }

  /**File | Seach action*/
  private void search(){
    SearchDialog dialog=new SearchDialog(this,"Search...",false);
    dialog.setLocationRelativeTo(this);
    dialog.setVisible(true);
  }

  /**File | Exit action performed*/
  public void jMenuFileExit_actionPerformed(ActionEvent e) {
  	Options.savePreferences();
    System.exit(0);
  }
  /**Help | About action performed*/
  public void jMenuHelpAbout_actionPerformed(ActionEvent e) {
    MainFrame_AboutBox dlg = new MainFrame_AboutBox(this);
    Dimension dlgSize = dlg.getPreferredSize();
    Dimension frmSize = getSize();
    Point loc = getLocation();
    dlg.setLocation((frmSize.width - dlgSize.width) / 2 + loc.x, (frmSize.height - dlgSize.height) / 2 + loc.y);
    dlg.setModal(true);
    dlg.setVisible(true);
  }
  /**Overridden so we can exit when window is closed*/
  protected void processWindowEvent(WindowEvent e) {
    super.processWindowEvent(e);
    if (e.getID() == WindowEvent.WINDOW_CLOSING) {
      jMenuFileExit_actionPerformed(null);
    }
  }
}