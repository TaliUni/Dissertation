//import all the GUI classes
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.IOException;

/**
 * Simple GUI for CompetitorList application
 */
public class DijGUI extends JFrame  implements ActionListener
{
    //Code adapted from lectures notes
	//The competitor list to have data pulled from.
    private SuperCompetitorList competitorList;
    
    //GUI components
    JTextArea details;
    JTextField searchField;
    JLabel labelEntID, close1, close2, close3, close4;
    JScrollPane scrollList;
    JButton fullDetails, shortDetails;
    JPanel northPanel, centerPanel, southPanel;

 
    /**
     * Mostly copied from lecture notes
     * Create the frame with its panels.
     * @param list	The competitorList list to be searched.
     */
    public DijGUI(SuperCompetitorList list)
    {
        this.competitorList = list;
        //this.setLayout(new BorderLayout(5,5));
        
        //set up window title
        setTitle("Return Details by Competitor");
        setLocation(10,10);
        
        //disable standard close button
	    setDefaultCloseOperation(this.DO_NOTHING_ON_CLOSE);
 
        //methods to set up North and Center Panels
        setupNorthPanel();
        setupCenterPanel();
        setupSouthPanel();
	 
        //pack and set visible
        pack();
        setVisible(true);
    } 
    
    //Adapted from lecture notes
    private void setupNorthPanel()
    {
    	//create North panel
    	//add label, fields and buttons
    	northPanel = new JPanel();
    	northPanel.setLayout(new GridLayout(2,1));
    	JPanel nTopPanel = new JPanel();
    	JPanel nBottomPanel = new JPanel();
    	
    	labelEntID = new JLabel("Enter Competitor Number");
    	searchField = new JTextField(5);
    	nTopPanel.add(labelEntID);
    	nTopPanel.add(searchField);
    	
    	fullDetails = new JButton("View Full Details");
    	fullDetails.addActionListener(this);
    	shortDetails = new JButton("View Short Details");
    	shortDetails.addActionListener(this);
    	nBottomPanel.add(fullDetails);
    	nBottomPanel.add(shortDetails);
    	
    	northPanel.add(nBottomPanel);
        northPanel.add(nTopPanel);
    	    
        this.add(northPanel, BorderLayout.NORTH);
        
    }
    
    //Adapted from lecture notes
    private void setupCenterPanel()
    {
    	//create Center panel
    	//textarea to display details in
    	centerPanel = new JPanel();
    	centerPanel.setLayout(new GridLayout(2,1));
         
        details= new JTextArea(10,50);     
        details.setEditable(false);
        centerPanel.add(details);
      
        this.add(centerPanel, BorderLayout.CENTER);
    }
    private void setupSouthPanel()
    {
    	southPanel = new JPanel();
    	southPanel.setLayout(new GridLayout(4,1));
    	close1 = new JLabel("To close all GUI's:");
    	close2 = new JLabel("Go to 'View by Competitor Type' GUI");
    	close3 = new JLabel("Enter filename for output results file");
    	close4 = new JLabel("Click 'close' button");
    	southPanel.add(close1);
    	southPanel.add(close2);
    	southPanel.add(close3);
    	southPanel.add(close4);
    	this.add(southPanel, BorderLayout.SOUTH);
    }
    
    //Adapted from lecture notes
    //come here when button is clicked
    //find which button and act accordingly
    public void actionPerformed(ActionEvent e) 
    { 
    	if (e.getSource() == fullDetails)
    	{
    		fullDetails();
    	}
    	else if (e.getSource() == shortDetails)
    	{
    		shortDetails();
    	}
    }
    
    //method to get full details
    private void fullDetails()
    {
    	try
    	{
    		SuperCompetitor competitor = getCompetitor();
    		details.setText(competitor.getFullDetails());
    	}
    	catch (NumberFormatException nfe)
		
		{
			String error = "Number conversion error for input data'" + "' - " + nfe.getMessage();
			details.setText(error);
		}
    	catch (NullPointerException npe)
    	{
    		String error = "This competitor number does not exist please enter a valid number";
    		details.setText(error);
    	}
    }
    
    //method to get short details
    private void shortDetails()
    {
    	try
    	{
    	SuperCompetitor competitor = getCompetitor();
    	details.setText(competitor.getShortDetails());
    	}
    	catch (NumberFormatException nfe)
		
		{
			String error = "Number conversion error for input data'" + "' - " + nfe.getMessage();
			details.setText(error);
		}
    	catch (NullPointerException npe)
    	{
    		String error = "This competitor number does not exist please enter a valid number";
    		details.setText(error);
    	}
    }
    
    //method to get Competitor number
    //gets number, trims and adds to searchfield
    private SuperCompetitor getCompetitor()
    {
    	String searchString = searchField.getText().trim();
    	int compNumber = Integer.parseInt(searchString);
    	SuperCompetitor competitor;
    	
    	//if (searchString.length()>0)
    	{
    		competitor = competitorList.getCompetitorByNumber(compNumber);
    	}
    	return competitor;
    }
}
