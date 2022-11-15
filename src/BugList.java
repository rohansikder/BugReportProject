import java.util.LinkedList;

public class BugList {
	
	private LinkedList<Bug> bugs;
	
	public BugList() {
		bugs = new LinkedList<>();
	}
	
	public synchronized LinkedList<Bug> getAllBugs(){
		return bugs;
	}
	
	public synchronized void addBug(int id, String application, int date, String platform, String description, String status) {
		Bug b = new Bug(id, application,date, platform, description, status);
		bugs.add(b);
	}
}
