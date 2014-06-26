//import all the GUI classes
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
 * Simple GUI for StaffList application
 */

//to change 
//getCompetitor() method this returns a Supercompetitor only if the number
//entered is actually valid (ie there is a compettitor of that number)
//but compiler does not like the "if" statement due to supercompetitor perhaps
//not having been instatiated

//change southPanel into a panel that contains a further 3 panels
//each of those then being:
//1. input altered score and textfield
//2. input round and textfield
//3. button to alterscore

//create a method scoresSetText() to put scores info (eg name etc)
//into centertextfield without copying code

//NUMBER EXCEPTIONS FOR ALL INPUT IN GUI
public class OFM_GUI extends JFrame  implements ActionListener
{
    //Code adapted from lectures notes
	//The competitor list to be searched.
    private SuperCompetitorList competitorList;
    
    //GUI components
    JTextArea scores;
    JTextField searchField, inputScore, inputRound;
    JScrollPane scrollList;
    JButton viewScores, alterScores, search;
    JLabel entCompNum, entAlterScores,entRound, close, close1, close2, close3, close4;
    JTextArea displayScores;
    JPanel northPanel, southPanel, centerPanel, scoresPanel, roundPanel;

 
    /**
     * Mostly copied from lecture notes
     * Create the frame with its panels.
     * @param list	The competitorList list to be searched.
     */
    public OFM_GUI(SuperCompetitorList list)
    {
        this.competitorList = list;
        //this.setLayout(new BorderLayout(5,5));
        
        //set up window title
        setTitle("View or Alter Scores For a Competitor");
        setLocation(10,500);
        //disable standard close button
	  setDefaultCloseOperation(this.DO_NOTHING_ON_CLOSE);
 
	  setupNorthPanel();
	  setupCenterPanel();
	  setupSouthPanel();
	  //this.add(centerPanel);

        //pack and set visible
        pack();
        setVisible(true);
    } 
    
    //Adapted from lecture notes
    private void setupNorthPanel()
    {
        /*
    	displayDetails = new JTextArea(15,20);
        displayDetails.setFont(new Font (Font.MONOSPACED, Font.PLAIN,14));
        displayDetails.setEditable(false);
        scrollList = new JScrollPane(displayDetails);
        this.add(scrollList,BorderLayout.CENTER);
        this.add(fullDetails,BorderLayout.CENTER);
        
        fullDetails.addActionListener(this);
        */
    	northPanel = new JPanel();
    	northPanel.setLayout(new GridLayout(1,2));
    	searchField = new JTextField(5);
    	viewScores = new JButton("View Scores");
    	viewScores.addActionListener(this);
    	
    	entCompNum = new JLabel ("Enter Competitor Number:");
    	
    	northPanel.add(entCompNum);
    	northPanel.add(searchField);
        northPanel.add(viewScores);
       
        this.add(northPanel, BorderLayout.NORTH);
    }
    
    private void setupCenterPanel()
    {
      
    	centerPanel = new JPanel();
    	centerPanel.setLayout(new GridLayout(1,3));
    	scores = new JTextArea(15,25);
    	scores.setEditable(false);
    	
      
    	centerPanel.add(scores);
        
        this.add(centerPanel, BorderLayout.CENTER);
    }
    //Adapted from lecture notes
    private void setupSouthPanel()
    {
     
     
    	southPanel = new JPanel();
    	southPanel.setLayout(new GridLayout(8,1));
    	
    		scoresPanel = new JPanel();
    		scoresPanel.setLayout(new GridLayout(1,2));
    	
    	
    			entAlterScores = new JLabel ("Input Altered Score");
    			inputScore = new JTextField();
    			scoresPanel.add(entAlterScores);
    			scoresPanel.add(inputScore);
    	
    		roundPanel = new JPanel();
    		roundPanel.setLayout(new GridLayout(1,2));
    		
    			entRound = new JLabel ("Input Round ");
    			inputRound = new JTextField();
    			roundPanel.add(entRound);
    	    	roundPanel.add(inputRound);
    	
    	
    	
    	alterScores = new JButton("Alter Scores");
    	alterScores.addActionListener(this);
    	close1 = new JLabel("To close all GUI's:");
    	close2 = new JLabel("Go to 'View by Competitor Type' GUI");
    	close3 = new JLabel("Enter filename for output results file");
    	close4 = new JLabel("Click 'close' button");
    	
    	southPanel.add(scoresPanel);
    	southPanel.add(roundPanel);
    	southPanel.add(alterScores);
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
    	if (e.getSource() == viewScores)
    	{
    		viewScores();
    	}
    	else if (e.getSource() == alterScores)
    	{
    		alterScores();
    	}
    	
    }
    
    private SuperCompetitor getCompetitor()
    {
    	
    	String searchString = searchField.getText().trim();
    	int compNumber = Integer.parseInt(searchString);
    	SuperCompetitor competitor;
  
    	competitor = competitorList.getCompetitorByNumber(compNumber);
    	
    	return competitor;
    }
    
    private void viewScores()
    {
    	SuperCompetitor competitor = null;
    	String type="";
    	try
    	{
    		competitor = getCompetitor();
          	
    	if (competitor instanceof ShootingCompetitor)
    	{
   			type = "Shooting Competitor";
   		}
   		
    	else if (competitor instanceof DijCompetitor)
   		{    			
   			type = "Photography Competitor";
    	}
    	
    	else if(competitor instanceof MountainBikerCompetitor)
    	{
    		type = "Mountain Biking Competitor";
    	}
    		
    	
    		scores.setText(type + "\nName: " + competitor.getCompetitorName().getFullName() +"\n" 
    				+ "Scores:  " + competitor.scoresRoundsReport()+"\n"
    				+ "Overall Score: " + competitor.overallScoreString());
    	}
    	
    	catch (NumberFormatException nfe)
		
		{
			String error = "Number conversion error for for competitor number or input score/round'" + "' - " + nfe.getMessage();
			scores.setText(error);
		}

    	catch (NullPointerException npe)
    	{
    		String error = "This competitor number does not exist please enter a valid number";
    		scores.setText(error);
    	}
    	
    }
    private void alterScores()
    {
    	try
    	{
    	//addScore (int score, int round)
    		SuperCompetitor competitor = getCompetitor();
    		int round = Integer.parseInt(inputRound.getText().trim());
    		int score = Integer.parseInt(inputScore.getText().trim());
    		if ((score<0||score>5)||(round<0||round>5))
    		{
    			scores.setText("score and or round invalid, must be in range {0,5} re-enter");
    		}	
    	
    		else
    		{
    	
    			competitor.addScore(score, round);
    			scores.setText("Name: " + competitor.getCompetitorName().getFullName() +"\n" 
    	    	+ "Scores:  " + competitor.scoresRoundsReport()+"\n"
    	    	+ "Overall Score: " + competitor.overallScoreString()
    	    	);

    		}
    	}
catch (NumberFormatException nfe)
		
		{
			String error = "Number conversion error for competitor number or input score/round'" + "' - " + nfe.getMessage();
			scores.setText(error);
		}
    	catch (NullPointerException npe)
    	{
    		String error = "This competitor number does not exist please enter a valid number";
    		scores.setText(error);
    	}

    }
}
