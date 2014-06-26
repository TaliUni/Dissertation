import java.util.Scanner;


abstract public class SuperCompetitor 
{
	private int compNum; //competitor number
	private Name compName; //competitor name
	private static final int NUM_ROUNDS = 5; //the number of rounds for each competition
	private int [] compScores = new int [NUM_ROUNDS]; //array of scores for each competitor
	private Scanner scanKeyboard = new Scanner(System.in);
		
	//Superconstructor common variables Number, Name, array of scores
	public SuperCompetitor (int competitorNumber, Name competitorName, int []competitorScores)
	{
		compNum = competitorNumber;
		compName = competitorName;
		compScores = competitorScores;
	}
	
	//Return competitors name
	public Name getCompetitorName()
	{
		return compName;
	}
	
	//Return competitors number
	public int getCompetitorNum()
	{
		return compNum;
	}
	
	//abstract methods that are created individually in each subclass
	public abstract double getOverallScore();
	public abstract String getFullDetails();
	public abstract String getShortDetails();
	
	//returns overallScore as a String
	public String overallScoreString()
	{
		 String overallScore = String.format("%.1f", getOverallScore());
		 return overallScore;
	}
	
	//adds a score related to a given round to the score array 
	//at the correct point in the array
	//throws an error to the user if the score is outwith {0,5} or
	//round is outwith {0,5}
	public void addScore (int score, int round) 
	{
		int index = round-1;
		compScores[index] = score;
	}
	
	public int[] getScores()
	{
		int scoresArray[] = new int [NUM_ROUNDS];
		{
			for (int i=0; i<compScores.length; i++)
			{
			scoresArray[i] = compScores[i];
			}
		}
		return scoresArray;
	}
	
	//returns a list of the individual scores within the scores array separated by spaces
	public String scoresList()
		{
			String report ="";
			for (int i=0; i<compScores.length; i++)
			{
				report += compScores[i] + " ";
			}
			return report;
		}
	
	//returns a report of the scores for a Competitor gained in each round
	//in following format:
	//Round 1: 1, Round 2: 2 ..... Round 5: 0.
	public String scoresRoundsReport() 
	{
		String [] sReport = new String [NUM_ROUNDS]; //array used to put report together
		String scorereport = ""; //final String to return
	
		//starting i at 1 so can add in the Round number easily
		for (int i=1; i < compScores.length; i++)
		{
			sReport[i-1] =  "Round " + i + ": " + compScores[i-1] + ", ";
		}
				
		int round = compScores.length;
		int index = compScores.length;
				
		for (int i=0; i< sReport.length-1; i++)
		{
			scorereport = scorereport + sReport[i];
		}
				
		scorereport = scorereport + "Round " + round + ": " +compScores[index-1] + ".";
		return scorereport;
	}
	
	//returns highest score (int) for a particular Competitor
	public int getHighScore()
	{
		//set highest equal to first entry in array then check to see if any other score in the array
		//is higher, if it is, set highest equal to that, and so on
		 int highest = compScores[0];
		 for (int i =0; i < compScores.length; i++)
		 {
			 if (highest < compScores[i])
			 {
				 highest = compScores[i];
			 }
		}
		 return highest;
	}
	
	//returns lowest score (int) for a particular MountainBiker
	public int getLowScore()
	{
		//set lowest equal to first entry in array then check to see if any other score in the array
		//is lower, if it is, set lowest equal to that, and so on
		int lowest = compScores[0];
		for (int i =0; i < compScores.length; i++)
		{
			if (lowest > compScores[i])
			{
				lowest = compScores[i];
			}
		}
	return lowest;
	}
}
