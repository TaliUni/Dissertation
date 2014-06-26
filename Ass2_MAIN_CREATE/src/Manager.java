
public class Manager {
//creates all GUI's and set's ready to be opened
	
	private SuperCompetitorList list;
	
	//creates new SuperCompetitorList
	public Manager()
	{
		 list = new SuperCompetitorList();
	}
	
	//creates run method to be called by Main class
	//creates a GUI for each of GUI classes
	public void run()
	{
		list.readFile("inputData.txt");

		DijGUI dg = new DijGUI(list);
		OFM_GUI og = new OFM_GUI(list);
		TSB_GUI tg = new TSB_GUI(list);
	}
}