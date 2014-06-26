//COPIED PRETTY MUCH COMPLETE FROM LECTURE NOTES 
public class Name 
{
	//instance variables
	private String firstName;
	private String middleName;
	private String lastName;
  
	//constructor to create object with a first and last name
	public Name(String fName, String lName) 
	{
		firstName = fName;
		middleName = "";
		lastName = lName;
	}
  
	//constructor to create object with first, middle and last name
	//if there isn't a middle name, that parameter could be an empty String
	public Name(String fName, String mName, String lName) 
	{
		firstName = fName;
		middleName = mName;
		lastName = lName;
	}
  
  
	//constructor to create name from full name
	//in the format first name then space then last name
	//or first name then space then middle name then space then last name
 
  
	//gets the first initial
	public char getFirstInitial()
	{
		return firstName.charAt(0);
	}

	//gets the last initial
	public char getLastInitial()
	{
		return lastName.charAt(0);
	}
	
	public Name (String fullName) 
	{
		  int spacePos1 = fullName.indexOf(' ');
		  firstName = fullName.substring(0, spacePos1);
		  int spacePos2 = fullName.lastIndexOf(' ');
		  
		  if (spacePos1 == spacePos2)
		  {
		  middleName = "";
		  }
		  
		  else 
		  
		  middleName = fullName.substring(spacePos1+1, spacePos2);
		  lastName = fullName.substring(spacePos2 + 1);
		  
	}
  
	//returns the first name
	public String getFirstName() 
	{
		return firstName; 
	}
  
	//returns the last name
	public String getLastName() 
	{
		return lastName; 
	}
  
	//change the first name to the value provided in the parameter
	public void setFirstName(String ln) 
	{
		firstName = ln;
	}
  
	//change the first name to the value provided in the parameter
	public void setMiddleName(String ln) 
	{
		middleName = ln;
	}
	
	//change the middle name to the value provided in the parameter
	public void setLastName(String ln) 
	{
		  lastName = ln;
	}
	
	//returns the last name then a space then the last name
	public String getFirstAndLastName() 
	{
		return firstName + " " + lastName;
	}
  
  
	//returns the full name
	//either first name then space then last name
	//or first name then space then middle name then space
	//  and then last name
	public String getFullName() 
	{
		String result = firstName + " ";
		if (!middleName.equals("")) 
		{
			result += middleName + " ";
		}
	  
		result += lastName;
		return result;
	}	
  
	//return the initial first name then initial middle name
	//and initial last name without space
	// if there is no middle name return the initial first name then initial last name without space 
	public String getInitFullName() 
	{
		String result ="";
		
		//find the initial of firstname
		result+=firstName.charAt(0);
	  
		//if there is a middle name find the initial middle name
		if (!middleName.equals("")) 
		{
			// add the initial middle name to initial first name
			result += middleName.charAt(0);
		}
		
	  //add initial last name to initial first name and initial middle name
	  result +=lastName.charAt(0);
	  return result;
	}
	
	//returns the full name
	//either first name then space then last name
	//or first name then space then initial middle name then space
	//  and then last name
	public String getInitMiddleName() 
	{
	  
		String result =firstName+" ";
		if (!middleName.equals("")) 
		{
		  //add initial middle name to first name
	    result += middleName.charAt(0)+" ";
		}
		result +=lastName;
		return result;
	}

}
