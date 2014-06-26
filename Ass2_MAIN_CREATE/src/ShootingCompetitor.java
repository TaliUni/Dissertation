
/**F21SF1 Assessment 2
 * 
 * @author Ola Saeed
 *
 */


public class ShootingCompetitor extends SuperCompetitor
{
	private String compCountry;//Country of competitor will be string
	private String compAge;
	

	//constructor to create object with competitor's id, name, score array, age, 
	//country
	public ShootingCompetitor(int competitorId, Name competitorName, int[] competitorScores,
		String competitorAge, String competitorCountry)
	{
		super (competitorId,competitorName,competitorScores);
		compCountry = competitorCountry;
		compAge = competitorAge;
	}

	// return competitor's country
	public String getCompetitorCountry()
	{
		return compCountry;
	}
	
	public String getCompetitorAge()
	{
		return compAge;
	}


	/**
	 * @return average of scores 
	 **/ 
	public double getAverage()
	{
		int total = 0;
		for(int score :this.getScores())
		{
			total+=score;
		}
		
		return (double)total/this.getScores().length;
	}

	/**
	 *  to do average you have to do sum all the score ,
	 * then divide by (number of scores subtract -2)array 
	 * after that you do averege without maximum and minimum
	 */ 
	public double getOverallScore()
	{
		int diff;
		int total = 0;
		for(int score : this.getScores())
		{
			total+=score;
		}
		
		diff = total-this.getHighScore()-this.getLowScore();

		return  (double)diff/(this.getScores().length -2);
	}

	/**
	 * this method will return a full
	 * details of this comparator
	 * @param 
	 * @return String: details
	 */ 
	public String getFullDetails()
	{
		String details=
		"Competitor Number: " + this.getCompetitorNum() + "\n" 
		+ "Name: " + this.getCompetitorName().getFirstAndLastName() + "\n" 
		+  this.getCompetitorName().getFirstName() + " is a Shooting Competitor aged " + compAge + " from " + compCountry + "\n"
		+ "Scores: " + scoresRoundsReport() + "\n"
		+"These give an overall score of: " + String.format("%.2f",getOverallScore());
		return details; 
	}

	/**
	 * this method will return short details of competitor include the competitor number ,
	 * competitor initial full name and overall score string.format("%.1f") % mean the string contains
	 * formatting information,- left aligned,default is right aligned
	 * 2 is the minimum width,output will be padded with spaces
	 * f means it is real number  
	 */ 
	public String getShortDetails()
	{
		String details= "Short Details: \nShooting Competitor:" + this.getCompetitorNum() 
				+ " (" + this.getCompetitorName().getFirstInitial() + this.getCompetitorName().getLastInitial() + ")"
				+ " has an overall score of " + String.format("%.2f",getOverallScore()) + ".";			
		return details;
	}

}

