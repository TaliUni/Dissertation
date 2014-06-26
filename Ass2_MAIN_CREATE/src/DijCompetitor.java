//Assignment 2 
//Author David Jarmin
//This program uses a Photography Competition as an example
//Categories include Portrait, Landscape, Nature, and B&W

public class DijCompetitor extends SuperCompetitor
{
	//instance variables
	private String competitorLevel; //level of experience e.g beginner, intermediate, advanced
	private String competitorCategory; // attribute re to type of photo.
	//categories will be landscape, B&W, portrait, travel and nature.
	
	public DijCompetitor(int compNumber, Name compName, 
			int []scores, String compLevel, String compCategory)
	{
		super (compNumber,compName,scores);
		
		competitorLevel = compLevel;
		competitorCategory = compCategory;
	}
	
	//return level of competitor
	public String getCompetitorLevel()
	{
		return competitorLevel;
	}
	
	//Code modified from lecture notes on arrays by M.Farrow.
	//Returns overall score 
	//Returns an overall averaged score with an adjustment weighting
	//based on the competitor level.
	//The score of advanced level competitors is reduced by 0.8,
	//the score of intermediate level competitors is reduced by 0.9,
	//the score of beginner level competitors is left unchanged.

	public double getOverallScore()
	{
		int total =0;
		for (int scoresIndex=0; scoresIndex < this.getScores().length;
			scoresIndex++)
		{
			total += this.getScores()[scoresIndex];
		}
		
		if (competitorLevel.equals("Advanced"))
		{
			return (double) total/this.getScores().length*0.8;
		}
		
		else if (competitorLevel.equals("Intermediate"))
		{
			return (double) total/this.getScores().length*0.9;
		}
		
		else if (competitorLevel.equals("Beginner"))
		{
			return (double) total/this.getScores().length*1;
		}
		return total;
	}
	
	//return specialist category photographs are entered into (attribute)
	public String getCompetitorCategory()
	{
		return competitorCategory;
	}
	
    //Create a method to return full details.
	//Returns String containing all details of the competitor

	public String getFullDetails()
	{
		return 
				"Competitor Number: " + this.getCompetitorNum() + "\n" 
				+ "Name: " + this.getCompetitorName().getFirstAndLastName() + "\n" 
				+ this.getCompetitorName().getFirstName() + " is at " 
		        + competitorLevel + " level " + "in the "
				+ competitorCategory + " category of the Photography competition.\n"
				+ "Scores: " + scoresRoundsReport() + "\n"
				+"These give an overall score of: " + String.format("%.2f",getOverallScore());
		
		
	}
	
	//Create a method to return Short details
	//returns a String containing subset of the details in a brief format
	//competitor number (CN); initials; overall score
	public String getShortDetails()
	{
		return "Short Details: \nPhotographer: " + this.getCompetitorNum() 
				+ " (" + this.getCompetitorName().getFirstInitial() + this.getCompetitorName().getLastInitial() + ")"
				+ " has an overall score of " + String.format("%.2f",getOverallScore()) + ".";
	}
}