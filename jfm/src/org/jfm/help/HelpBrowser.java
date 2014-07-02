package org.jfm.help;

import java.awt.BorderLayout;
import java.awt.Cursor;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Stack;

import javax.print.Doc;
import javax.print.DocFlavor;
import javax.print.DocPrintJob;
import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import javax.print.SimpleDoc;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JEditorPane;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkListener;
import javax.swing.event.HyperlinkEvent.EventType;

public class HelpBrowser extends JDialog {

	private String baseURL;
	private JEditorPane browser;
	private Stack<String> backURLs=new Stack<String>();
	private Stack<String> forwardURLs=new Stack<String>();
	private JButton backButton=new JButton("Back"); 
	private JButton forwardButton=new JButton("Forward");
	private JButton homeButton=new JButton("Home");
	private JButton closeButton=new JButton("Close");
	private JButton printButton=new JButton("Print");
	private String currentURL=null;

	public HelpBrowser(Frame owner, String title, boolean modal)
			throws HeadlessException {
		super(owner, title, modal);
		init();		
	}
	
	private void init()
	{
		setSize(650, 500);
		JPanel contentPane=(JPanel)getContentPane();
		contentPane.setLayout(new BorderLayout());
		browser=new JEditorPane();
		browser.setEditable(false);
		JScrollPane scroll=new JScrollPane(browser);
		scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		contentPane.add(scroll,BorderLayout.CENTER);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		
		JPanel buttonsPanel=new JPanel(new FlowLayout(FlowLayout.LEADING,10,5));
		backButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				if(!backURLs.isEmpty())
				{
					forwardURLs.push(currentURL);
					String url=backURLs.pop();	
					loadPage(url);
				}
			}
		});	
		forwardButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				if(!forwardURLs.isEmpty())
				{
					backURLs.push(currentURL);
					String url=forwardURLs.pop();
					loadPage(url);
				}
			}
		});
		homeButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				backURLs.push(currentURL);
				loadHomePage();
			}
		});
		closeButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		
		printButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				DocFlavor urlInFormat=DocFlavor.URL.AUTOSENSE;
				try {
					java.net.URL u=new java.net.URL(currentURL);
					Doc doc=new SimpleDoc( u ,urlInFormat,null);
					PrintRequestAttributeSet aset = new HashPrintRequestAttributeSet();					
					PrintService service=PrintServiceLookup.lookupDefaultPrintService();					
					if(service!=null)
					{
						DocPrintJob job=service.createPrintJob();
						try{						
							job.print(doc, aset);
						}catch(Exception exc)
						{
							exc.printStackTrace();
							JOptionPane.showMessageDialog(HelpBrowser.this, "Error printing: "+exc.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
						}
					}
					else
					{
						JOptionPane.showMessageDialog(HelpBrowser.this, "Can't find a printer", "Error", JOptionPane.ERROR_MESSAGE);
					}
				} catch (Exception exc) {
					exc.printStackTrace();
				}
			}
		});
		
		buttonsPanel.add(backButton);
		buttonsPanel.add(forwardButton);
		buttonsPanel.add(homeButton);
		//no printing for now
		//buttonsPanel.add(printButton);
		buttonsPanel.add(closeButton);
		
		contentPane.add(buttonsPanel,BorderLayout.NORTH);
		
		browser.addHyperlinkListener(new HyperlinkListener(){
			public void hyperlinkUpdate(HyperlinkEvent e) {
				if(e.getEventType().equals(EventType.ENTERED))
				{
					System.out.println("entered");
					setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
				}
				if(e.getEventType().equals(EventType.EXITED))
				{
					System.out.println("exited");
					setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
				}
				if(e.getEventType().equals(EventType.ACTIVATED))
				{
					backURLs.push(currentURL);
					loadPage(e.getURL().toString());
				}
				
			}
		});
	}

	public void loadHomePage()
	{
		loadPage(null);
	}
	
	public void loadPage(String url)
	{
		this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
		try{
			if(url==null)
			{
				url=getBaseURL();
			}
			browser.setPage(url);
			currentURL=url;			
			backButton.setEnabled(!backURLs.isEmpty());
			forwardButton.setEnabled(!forwardURLs.isEmpty());
		}catch(Exception exc)
		{
			JOptionPane.showMessageDialog(HelpBrowser.this, "Error loading "+url, "Error", JOptionPane.ERROR_MESSAGE);
			exc.printStackTrace();
		}
		this.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
	}

	/**
	 * @return the baseURL
	 */
	public String getBaseURL() {
		return baseURL;
	}

	/**
	 * @param baseURL the baseURL to set
	 */
	public void setBaseURL(String baseURL) {
		this.baseURL = baseURL;
	}
}
