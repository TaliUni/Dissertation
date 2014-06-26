import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class SuperCompetitorList 

{
	//create a Scanner object "keyboard" ready to read input from keyboard
	private Scanner scanKeyboard = new Scanner(System.in);
	private ArrayList<SuperCompetitor>competitorList;

	//create an ArrayList called competitorList which will contain 
	//SuperCompetitor objects
	public SuperCompetitorList()
	//DIRECTLY COPIED FROM MF LECTURENOTES
	{
		competitorList = new ArrayList<SuperCompetitor>();
	}
	
	//adds an instance of Competitor to the ArrayList competitorList
	public void addSuperCompetitor(SuperCompetitor competitor)
	//DIRECTLY COPIED FROM MF LECTURENOTES
	{
		competitorList.add(competitor);
	}
	
	//DIRECTLY COPIED FROM MF LECTURE NOTES
	//used scanner to read each line, while there still is a new line, 
	//of a text file of name <filename> then uses the processLine() method
	//on each line
	//checks for exception: FileNotFoundException: if input file not 
	//found allows user to input name of file from keyboard, if still 
	//not found programme exits
	public void readFile(String filename)
	{
		try
		{
			File inputFile = new File(filename); 
			Scanner scannerFile = new Scanner (inputFile); 
			while (scannerFile.hasNextLine()) 
			{
				String readLine = scannerFile.nextLine(); 
				if (readLine.length() !=0)
				{
					processLine(readLine); 
				}
			}
		}
		catch (FileNotFoundException fnf)
		{
			System.out.println (filename + " not found please re-enter filename correctly: ");
			filename = scanKeyboard.nextLine();
			try
			{
				File input = new File(filename); 
				Scanner scanner = new Scanner (input); 
				while (scanner.hasNextLine()) 
				{
					String readLine = scanner.nextLine(); 
					if (readLine.length() !=0)
					{
						processLine(readLine); 
					}
				}
			}
			catch (FileNotFoundException fnf1)
			{
				System.out.println (filename + " still not found, programme exiting.");
				System.exit(0);
			}
		}
	}
	
	//reads a String line that is split by comma's and gives out
	//competitorNumber (int), competitorName (Name), scores (array of 5 scores)
	//it then checks to see what type of competitor is being passed in
	//and splits appropriately according to type, creates a new subclass object appropriately
	//then calls the addSuperCompetitor method to add to an ArrayList
	//All the input is trimmed
	//various errors that could be introduced are caught here, see code for details
	private void processLine(String line)
	{
		try
		{
			//used for calling errors
			int exit = 0;
			
			SuperCompetitor supComp = null; //null to force instantiation
									
			//create a String array of the input text, blocking it at the comma's in 
			//the original text in the line
			String breakUp[] = line.split(",");
			String type = breakUp[0].trim();
			
			//then create competitorNumber, competitorName and competitor's array of scores
			//from the input data
			int compNumber = Integer.parseInt(breakUp[1].trim());
			Name compName = new Name(breakUp[2].trim());
			int [] compScores = getFileScores(breakUp);
		
			//checks that input scores are between 0 and 5 if <0 or >5
			//if not throws exit value, which is used later
			for (int i=0; i<compScores.length; i++)
			{
				if (compScores[i]<0 || compScores[i]>5) 
				{
					exit = 3;
				}
			}
			
			//then check to see which type of competitor dealing with
			//MB - MoutainBikerCompetitor
			//SC - ShootingCompetitor
			//DC - DijCompetitor
			//and then split and assign variables from the rest of the line 
			//according to the individual requirements of each subclass constructor
			//must be if - else if - else to ensure goes to each at correct time etc
			
			//If input line is of type MB - ie MountainBiker
			if (type.equals("MB"))
			{
				String mountainBikerLevel = breakUp[8].trim();
				String mountainBikerType = breakUp[9].trim();
				supComp = new MountainBikerCompetitor
						(compNumber, compName, compScores, mountainBikerLevel, mountainBikerType);
			
				//check to see whether correct levels entered if not throw exit variable of 1
				//this is later checked and programme tells user depending on exit variable
				if (!(mountainBikerLevel.equals("Intermediate")||mountainBikerLevel.equals("Diva")
						||mountainBikerLevel.equals("Newbie")))
				
				{
					exit = 1;
				}
				
				//alternative method to do same as above on type
				if (!mountainBikerType.equals("XC Goddess")&&!mountainBikerType.equals("Downhill Junkie")
					&&!mountainBikerType.equals("Air Maiden"))
				{
					exit = 2;
				}
			}
			
			//If input line is of type DC - ie Photographer or DijCompetitor
			else if (type.equals("DC"))		
			{
				String level = breakUp[8].trim();
				String category = breakUp[9].trim();
				
				//throw an exit value if incorrect level entered
				//needed as overall score depends on level and will be incorrect
				//if level is entered incorrectly
				if (!level.equals("Advanced")&&!level.equals("Intermediate")
						&&!level.equals("Beginner"))
				{
					System.out.println(level);
					exit = 1;
				}
				
				//create DijCompetitor object 
				supComp = new DijCompetitor(compNumber, compName, compScores, level, category);
			}
			
			//If input line is of type SC - ie ShootingCompetitor
			else if(type.equals("SC"))
			{
				
				String competitorAge= breakUp[8].trim();
				String competitorCountry = breakUp[9].trim();
				
				supComp = new ShootingCompetitor(compNumber, 
								compName, compScores, competitorAge, competitorCountry);
			}
			
			else
			{
				exit = 4;
			}

			//the following only produced for MountainBiker and Photographer because
			//in original assessment were already in MountainBiker
			//added to Photographer only for level because level affects overall Score method
			//so important
			//left out for all others as not essential to nature of how the programme works, 
			//and were not put into code for original
			if (exit==1)
			{
				System.out.println("incorrect level entered for this competitor " 
						+ compName.getFullName() + " will be ignored");
			}

			else if (exit ==2)
			{
				System.out.println("incorrect type entered for this Mountain Biker " 
						+ compName.getFullName() + ". This competitor will be ignored");
			}
		
			else if (exit ==3)
			{				
				System.out.println("Scores entered for competitor: " 
						+ compNumber + " " + compName.getFullName() + 
						" are invalid (outwith range 0-5 inclusive), this competitor will be ignored");
			}
			
			else if (exit ==4)
			{				
				System.out.println("Incorrect competitor code entered for: " 
						+ compNumber + " " + compName.getFullName() + 
						" this competitor will be ignored");
			}
				
			
			
			else
			//if the above don't happen then competitor added to competitorList
			//otherwise programme exits
			{
				this.addSuperCompetitor(supComp);
			}
		}
		
		//catches if non numeric data entered when should be numbers
		catch (NumberFormatException nfe)
				
		{
			String error = "Number conversion error in '" + line +  "' - " + nfe.getMessage();
			System.out.println(error);
		}
		
		//catches if wrong number of variables (eg for scores array) has been entered
		catch (ArrayIndexOutOfBoundsException ob)
		{
			String error = "Wrong number of variables entered at '" + line + "' recheck data entry, " +
					"this competitor ignored";
			System.out.println(error);
		}
		
	}

	//method to put the scores into an array from another array
	private int [] getFileScores(String breakUpText[])
	{
					
		int scoresLength = breakUpText.length - 5; 
		String cScores[] = new String[scoresLength];
			

		//this is set to 5 so that if too many scores are entered 
		//throws an array out of bounds exception
			
		int superCompScores[] = new int[scoresLength]; 
		//creates an array of scores for this one competitor
		System.arraycopy (breakUpText, 3, cScores, 0, scoresLength); 

		for (int i=0; i<cScores.length;i++)
		{
			//changes array of scores as string into array of scores as int
			superCompScores[i]=Integer.parseInt(cScores[i].trim());
		}
			return superCompScores;
	}
		
	//returns a Table for MountainBiker competitors
	//which has picked out and added only
	//the mountainBikers from a SuperCompetitorList
	//for some reason whilst this is well formatted for text file, not formatted well for GUI
	//and difficult to get GUI to format well
	public String getMountainBikerTable()
	{
		String table = "Comp#       Name                               Level            Type                Scores            Overall Score \n";
			
		for (SuperCompetitor sc : competitorList)
		{
			if (sc instanceof MountainBikerCompetitor)
			{
				MountainBikerCompetitor mb = (MountainBikerCompetitor) sc;
				table += String.format("%-15s", mb.getCompetitorNum()) + "     " 
				+ String.format("%-20s", mb.getCompetitorName().getFullName()) + "     "
				+ String.format("%-20s", mb.getMountainBikerLevel()) + "     "
				+ String.format("%-20s", mb.getMoutainBikerType()) + "     "
				+ String.format("%-20s", mb.scoresList()) + "     "
				+ String.format("%.1f", mb.getOverallScore())
				+"\n"; 
			}
			
		}
		return table;	
	}
	
	//returns a Table for Photographer competitors
	//which has picked out and added only
	//the photographers from a SuperCompetitorList
	//for some reason whilst this is well formatted for text file, not formatted well for GUI
	//and difficult to get GUI to format well
	public String getPhotographerTable()
	{
		String table = "Comp#       Name                               Category                 Level               Scores             Overall Score \n";
				
		for (SuperCompetitor sc : competitorList)
		{
			if (sc instanceof DijCompetitor)
			{
				DijCompetitor dc = (DijCompetitor) sc;
				table += String.format("%-7s", dc.getCompetitorNum()) + "     "
				+ String.format("%-30s", dc.getCompetitorName().getFullName()) + "     "
				+ String.format("%-20s", dc.getCompetitorCategory()) + "     "
				+ String.format("%-15s", dc.getCompetitorLevel()) + "     "
				+ String.format("%-14s", dc.scoresList()) + "     "
				+ String.format("%.1f", dc.getOverallScore())
				+ "\n"; 
			}
			
		}
		return table;	
	}

	//returns a Table for Shooting competitors
	//which has picked out and added only
	//the shooters from a SuperCompetitorList
	//for some reason whilst this is well formatted for text file, not formatted well for GUI
	//and difficult to get GUI to format well
	public String getShooterTable()
	{
		String table = "Comp#       Name                               Country            Age                 Scores             Overall Score \n";
	
		
		for (SuperCompetitor sc : competitorList)
		{
			if (sc instanceof ShootingCompetitor)
			{
				ShootingCompetitor shc = (ShootingCompetitor) sc;
				table += String.format("%-7s", shc.getCompetitorNum()) + "     "
				+ String.format("%-30s", shc.getCompetitorName().getFullName()) + "     "
				+ String.format("%-14s", shc.getCompetitorCountry()) + "     "
				+ String.format("%-15s", shc.getCompetitorAge()) + "     "
				+ String.format("%-14s", shc.scoresList()) + "     "
				+ String.format("%.1f", shc.getOverallScore())
				+  "\n"; 
			}
			
		}
		return table;	
	}
		
	
		
		
	//Method for finding a SuperCompetitor by their number
	public SuperCompetitor getCompetitorByNumber(int scNumber)
	{
		SuperCompetitor returnSC = null;
		
		for (SuperCompetitor sc : competitorList)
		{
			if (sc.getCompetitorNum() == scNumber)
			{
				returnSC = sc;
			}
			
		}
		return returnSC;
		
	}
	
	//creates a string of data from competitorList to output to
	//output Results File - after updates made from GUI's
	public String updatedOutputForFile()
	{
		String report = "Final Results Table\n"
		+"Mountain Biker Results\n"
		+ getMountainBikerTable()
		+ "\n"
		+"Shooting Competitor Results\n"
		+getShooterTable()
		+"\n"
		+"Photographer Competitor Results\n"
		+getPhotographerTable();
		
		return report;
		
	}
	
	//writes a String to an output file, user passes in
	//the name of the file to write out to and the String to use
	//catches FileNotFoundException
	public void writeReportToFile(String filename, String report)
	//DIRECTLY COPIED FROM MF LECTURENOTES
	{
		FileWriter fw;
		try 
		{
			fw = new FileWriter(filename);
			fw.write(report);
			fw.close();
		}
			
		catch (FileNotFoundException fnf)
		{
			System.out.println(filename + " not found");
			System.exit(0);
		}
			
		catch (IOException ioe)
		{
			ioe.printStackTrace();
			System.exit(1);
		}
	}
}