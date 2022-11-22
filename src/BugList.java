import java.util.Iterator;
import java.util.LinkedList;

public class BugList {
	
	private LinkedList<Bug> bugs;
	
	public BugList() {
		bugs = new LinkedList<>();
	}
	
	public synchronized LinkedList<Bug> getAllBugs(){
		return bugs;
	}
	
	public synchronized void addBug(int id, String application, String date, String platform, String description, String status) {
		Bug b = new Bug(id, application,date, platform, description, status);
		bugs.add(b);
	}
	
	public synchronized String getList()
	{
		Iterator<Bug> iter = bugs.iterator();
		Bug temp;
		String result="";
		while(iter.hasNext())
		{
			temp = iter.next();
			result = result + temp.getId()+" "+temp.getDate()+" "+temp.getDescription()+" "+temp.getApplication()+"\n";
		}
		
		return result;
	}
}
