package org.jfm.main.configurationdialog.panels;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.jfm.event.HelpURLChangeEvent;
import org.jfm.main.Options;
import org.jfm.main.configurationdialog.ConfigurationEventsQueue;

public class HelpConfigurationPanel extends ConfigurationPanel {

	private JPanel panel=null;
	
	public HelpConfigurationPanel(String name, String title) {
		super(name, title);
	}
	
	
	protected void init(){
		setLayout(new BorderLayout());
		setPanel();		
		
		add(titleLabel,BorderLayout.NORTH);
		add(panel,BorderLayout.CENTER);
	}

	private void setPanel(){
		panel=new JPanel();
		panel.setLayout(new GridBagLayout());
		JPanel helpURLPanel=setupHelpURLPanel();
		panel.add(helpURLPanel,new GridBagConstraints(0,0,1,1,1,0,GridBagConstraints.CENTER,GridBagConstraints.HORIZONTAL,new Insets(10,0,10,0),0,0));
		panel.add(new JPanel(),new GridBagConstraints(0,1,1,1,1,1,GridBagConstraints.CENTER,GridBagConstraints.BOTH,new Insets(0,0,0,0),0,0));
	}


	private JPanel setupHelpURLPanel() {
		JPanel p=new JPanel();
		p.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(),"Help"));
		p.setLayout(new BorderLayout());
		JLabel helpURLDescriptionLabel=new JLabel("Help base URL:");
		final JTextField helpURLtextField=new JTextField(Options.getPreferences().get(Options.JFM_HELP_URL, ""));
		JButton setButton=new JButton("Set");
		JLabel noteLabel=new JLabel("<html><body><b>Note:</b><ul><li>After modifying the contents of the field, press the Set button.</li>"+
				"<li>Valid URL's have the form <i>http://www.example.com/</i></li></ul></body></html>");
		setButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				boolean isValid=false;
				try{
					@SuppressWarnings("unused")
					URL url=new URL(helpURLtextField.getText());
					isValid=true;
				}catch(Exception exc){}
				if(!isValid)
				{
					JOptionPane.showMessageDialog(HelpConfigurationPanel.this, "Invalid URL entered", "Error", JOptionPane.ERROR_MESSAGE);
					return;
				}
				HelpURLChangeEvent event=new HelpURLChangeEvent(helpURLtextField.getText());
				event.setSource(this);
				ConfigurationEventsQueue.addPendingEvent(event);
			}
		});
		p.add(helpURLDescriptionLabel,BorderLayout.WEST);
		p.add(helpURLtextField,BorderLayout.CENTER);
		p.add(setButton,BorderLayout.EAST);
		p.add(noteLabel,BorderLayout.SOUTH);
		return p;
	}
}
