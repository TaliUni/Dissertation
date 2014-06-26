//import all the GUI classes
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;


/**
 * Simple GUI for StaffList application
 */
public class TSB_GUI extends JFrame  implements ActionListener
{
    //Code adapted predominantly from lectures notes
	//The competitor list to be searched.
    private SuperCompetitorList competitorList;
    
    //GUI components
    JTextArea displayTable;
   
    //input textfields for choosing competitor type
    JTextField textFieldType, filename;
    JScrollPane scrollList;
    JButton close, choose, help, enterFilename;
    JPanel northPanel, centerPanel, southPanel;
    String outputFilename="default.txt";
 
    /**
     * Mostly copied from lecture notes
     * Create the frame with its panels.
     * pulling in the competitor list
     */
    public TSB_GUI(SuperCompetitorList list)
    {
        this.competitorList = list;
               
        //set up window title
        setTitle("View by Competitor Type");
        
        //set location so all three GUI's come in seperate
        setLocation(600,10);
        
        //disable standard close button
        setDefaultCloseOperation(this.DO_NOTHING_ON_CLOSE);
 
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
    	//Create a new panel called "northPanel" to go into the north of the main JFrame
       	northPanel = new JPanel();
       	//create a gridLayout of 2 columns by 1 row
    	northPanel.setLayout(new GridLayout(1,2));
    	
    	//instantiate buttons to go in northPanel
       	close = new JButton("Close");
       	help = new JButton("Help");
       	
       	//add actionListener to  buttons
    	close.addActionListener(this);
    	help.addActionListener(this);
    	
    	//add  buttons to northPanel
    	northPanel.add(close);
    	northPanel.add(help);
    	
    	//add northPanel to the main JFrame in the NORTH
    	this.add(northPanel, BorderLayout.NORTH);
    }
    
    private void setupCenterPanel()
    {
    	//Create a new panel called "centerPanel" which will go into the 
    	//center of the main JFrame
       	centerPanel = new JPanel();
       	
       	//create a gridlayout of 2 columns by 2 rows
    	centerPanel.setLayout(new GridLayout(2,2));
    
    	//instantiate labels and input textfields
    	choose = new JButton("Choose Competitor Type");
    	choose.addActionListener(this);
    	textFieldType = new JTextField(20);
    	enterFilename = new JButton("Enter output Filename:");
    	enterFilename.addActionListener(this);
    	filename = new JTextField(20);
    	    	
    	//add labels, buttons and input text fields to centerPanel
    	centerPanel.add(choose);
    	centerPanel.add(textFieldType);
    	centerPanel.add(enterFilename);
    	centerPanel.add(filename);
    	
    	//add southPanel to the main JFrame in the CENTER
    	this.add(centerPanel, BorderLayout.CENTER);
    }
    
    private void setupSouthPanel()
    {
    	//Create a new panel called "southPanel" which will go into the south of the main JFrame
       	southPanel = new JPanel();
       	
       	//create a gridlayout of 2 columns by 1 row
    	southPanel.setLayout(new GridLayout(1,2));
    	
    	//instantiate displayTable to a JTextArea of size 20x20
    	displayTable = new JTextArea(50,50);
    	displayTable.setEditable(false);
    	displayTable.setText(
    			"For table of MountainBikers enter: MountainBiker\n"
    			+ "For table of Shooting Competitors enter: Shooter\n"
    			+ "For table of Photographers enter: Photographer\n"
    			+ "\n"
    			
    			//added lots of spaces to end of next line to get textArea size to 
    			//be big enough for table
	    		+ "Enter filename want output file to be updated into in format <filename.txt>                                           "
	    		+ "\nTo close all GUI's click on 'close' button above");
    	//add displayTable to southPanel
    	southPanel.add(displayTable);
    	scrollList = new JScrollPane(southPanel);
    	
    	//add southPanel to the main JFrame in the SOUTH
    	this.add(scrollList, BorderLayout.SOUTH);
    }
    
    //Adapted from lecture notes
    //come here when button is clicked
    //find which button and act accordingly
    public void actionPerformed(ActionEvent e) 
    { 
    	//calls close method
    	if (e.getSource() == close)
    	{
    		close();
    	}
    	
    	//calls choose method
    	else if (e.getSource() == choose)
    	{
    		choose();
    	}
    	
    	//produces text in main textField to advise user of how to use GUI
    	else if(e.getSource()==help)
    	{
    		displayTable.setText("For table of MountainBikers enter: MountainBiker\n"
    	    		+ "For table of Shooting Competitors enter: Shooter\n"
    	    		+ "For table of Photographers enter: Photographer\n"
    	    		+ "\n"
    	    		+ "Enter filename want output file to be updated into in format <filename.txt>"
    	    		+ "\nTo close all GUI's click on 'close' button above");
    	}
    	
    	//allows user to input a filename for results to be output to
    	else if(e.getSource()==enterFilename)
    	{
    		outputFilename = filename.getText().trim();
    		if (outputFilename.equals(""))
    		{
    			displayTable.setText("filename must be input");
    		}
    		else
    		{
    			displayTable.setText("Output file will be called: "
    			+ outputFilename
    			+ "\n"
    			+ "click 'close' button for file to be output and close GUI's");
    		}
    	}
    	
    }
    
    //this closes all GUI's and sends data to output file of name filename (default if user
    //doesn't input using input textfield
    private void close()
    {
    	JOptionPane.showMessageDialog(this, 
    	"all GUI's will now be closed\n" +
    	"Results will be output to " + outputFilename);
    	competitorList.writeReportToFile(outputFilename, competitorList.updatedOutputForFile());
    	System.exit(0);
    }
    
    //produces a table based on which type of competitor is chosen
    //user needs to input "MountainBiker" "Shooter" "Photographer" to get correct table
    private void choose()
    {
    	String type = textFieldType.getText().trim();
    	if (type.equals("MountainBiker"))
    	{
    	displayTable.setText(competitorList.getMountainBikerTable());
    	}
    	else if (type.equals("Shooter"))
    	{
    	displayTable.setText(competitorList.getShooterTable());
    	}
    	else if (type.equals("Photographer"))
    	{
    	displayTable.setText(competitorList.getPhotographerTable());
    	}

    	//if user inputs incorrect type prompts user to enter in correct way
    	else
    	{
    		displayTable.setText("Incorrect type entered, enter as follows:\n" 
    		+ "For table of MountainBikers enter: MountainBiker\n"
    		+ "For table of Shooting Competitors enter: Shooter\n"
    		+ "For table of Photographers enter: Photographer\n");
    	}
   }
    
}
