public class MountainBikerCompetitor extends SuperCompetitor
{
	
/*Variable descriptions:
 NUM_ROUNDS: sets the number of rounds of the competition to 5
 scores: creates an int array of scores of size equal to the number of rounds
 mountainBikerLevel: There are 3 levels "Newbie", "Intermediate", "Diva"
 mountainBikerType: There are 3 types of classes "Air Maiden", "Downhill Junkie", "XC Goddess"
  */
	
	private String mountainBikerLevel; 
	private String mountainBikerType;
		
	//creates a MoutainBiker object with the following attributes input as parameters
	//competitorNumber, competitorName, competitorLevel, competitorType, an array of scores
	public MountainBikerCompetitor (int mbNumber, Name mbName, int [] mbScores, String mbLevel, 
			String mbType)
	{
		super (mbNumber, mbName, mbScores);
		mountainBikerLevel = mbLevel;
		mountainBikerType = mbType;
	}
		
	//returns the MountainBiker's type of class
	public String getMoutainBikerType()
	{
		String mbType = mountainBikerType;
		return mbType;
	}

	//returns the MountainBiker's level
	public String getMountainBikerLevel()
	{
		String mbLevel = mountainBikerLevel;
		return mbLevel;
	}
	
	/*returns an overall score (double) which is worked out depending on MountainBiker level as follows:
	level: Newbie, overall Score = average of the scores, excluding the lowest score
	level: Intermediate, overall Score = average of the scores, excluding the lowest and highest scores
	level: Diva, overall Score = average of the scores, excluding the highest score*/
	public double getOverallScore()
	{
		double sumScores = 0; //sums scores and forces int to double so that result is double 
		double oScore = 0;
			
		if (mountainBikerLevel.equals("Diva"))
		{
			/* set highest equal to the highest score
			 * then sum the score array
			 * then set oScore = sum of scores - highest score
			 * then divide oScore by the NUM_ROUNDS-1
			 */
			
			int highest = getHighScore();
								 
			for (int i =0; i < this.getScores().length; i++)
			{	
				sumScores += this.getScores()[i]; 
			}
			
			oScore = (sumScores-highest)/(4);
			
		}
			
		else if (mountainBikerLevel.equals("Intermediate"))
		{
			/* set highest equal to the highest score
			 * set lowest equal to the lowest score
			 * then sum the score array
			 * then set oScore = sum of scores - highest score - lowest score
			 * then divide by the NUM_ROUNDS-2
			 */
					
					 
			int highest = getHighScore();
			int lowest = getLowScore();
						 
			for (int i =0; i < getScores().length; i++)
			{	
				sumScores += getScores()[i];
			}
			
			oScore = (sumScores-highest-lowest)/(3);
		}
		
		else if (mountainBikerLevel.equals("Newbie"))
		{
			/* set lowest equal to the lowest score
			 * then sum the score array
			 * then set oScore = sum of scores - lowest score
			 * then divide by the NUM_ROUNDS-3
			 */
				
			int lowest = getLowScore();
			
			for (int i=0; i < getScores().length; i++)
			{
				sumScores += getScores()[i];
			}
			oScore = (sumScores - lowest)/(4);
			
		}
			
		return oScore;
	}
		
	
	//returns the full attributes of a given MountainBiker as detailed text output
	//in following format: eg
	//Full details of Competitor Number 1002, Gordon Walton:
	//Gordon is a Diva in the XC Goddess class.
	//Scores: Round 1: 4, Round 2: 3, Round 3: 5, Round 4: 2, Round 5: 0.
	public String getFullDetails()
	{
		return 
		"Competitor Number: " + this.getCompetitorNum() + "\n" 
		+ "Name: " + this.getCompetitorName().getFirstAndLastName() + "\n" 
		+  this.getCompetitorName().getFirstName() + " is a " + mountainBikerLevel + " in the " 
		+ mountainBikerType + " Mountain Biking class.\n"
		+ "Scores: " + scoresRoundsReport() + "\n"
		+"These give an overall score of: " + String.format("%.2f",getOverallScore());
	}
		
	//returns the attributes of a given MountainBiker as shortened text output
	//in following format: eg
	//Short Details: Mountain Biker Number 1004 (CM) has overall score of 3.0.
	public String getShortDetails()	
	{
		return 
		"Short Details: \nMountain Biker: " + this.getCompetitorNum() 
		+ " (" + this.getCompetitorName().getFirstInitial() + this.getCompetitorName().getLastInitial() + ")"
		+ " has an overall score of " + getOverallScore() + ".";
	}
	
	//returns details of MountainBikerCompetitor in a line, returned at the end, for a table layout form
}