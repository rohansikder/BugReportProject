import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.LinkedList;

public class BugList {

	private LinkedList<Bug> bugs;

	public BugList() {
		bugs = new LinkedList<>();
	}

	public synchronized LinkedList<Bug> getAllBugs() {
		return bugs;
	}
	
	//Adds bug to linked list
	public synchronized void addBug(int id, String application, String date, String platform, String description,
			String status) {
		Bug b = new Bug(id, application, date, platform, description, status);
		bugs.add(b);
	}
	
	//Creates an id by getting the last id from the bugs and incrementing it
	public synchronized int createID() {
		Iterator<Bug> iter = bugs.iterator();
		Bug temp;
		int uniqueId = 0;
		while (iter.hasNext()) {
			temp = iter.next();
			uniqueId = temp.getId();
		}

		uniqueId++;

		return uniqueId;
	}

	//Checks if BugId exists
	public synchronized boolean checkID(int id) {
		Iterator<Bug> iter = bugs.iterator();
		Bug temp;
		boolean idCheck = false;
		int tempId;
		while (iter.hasNext()) {
			temp = iter.next();
			tempId = temp.getId();

			if (tempId == id) {
				idCheck = true;
			}
		}

		return idCheck;
	}
	
	//Changes status of bug in the linked list
	public synchronized void setStatus(int bugId, String status) {
		Iterator<Bug> iter = bugs.iterator();
		Bug temp;
		int tempId;
		while (iter.hasNext()) {
			
			temp = iter.next();
			tempId = temp.getId();

			if (tempId == bugId) {
				temp.setStatus(status);
			}
		}

	}
	
	//Prints out all unassigned bugs - Checks if status is ASSIGNED
	public synchronized String getUnassignedBugs() {
		Iterator<Bug> iter = bugs.iterator();
		Bug temp;
		String unassignedBugs = "\nBelow Bugs are not assigned: \n";
		while (iter.hasNext()) {
			temp = iter.next();
			
			if(temp.getStatus().equalsIgnoreCase("ASSIGNED") == false) {
				unassignedBugs = unassignedBugs + temp.getId() + " " + temp.getApplication() + " " + temp.getDate() + " "
						+ temp.getPlatform() + " " + temp.getDescription() + " " + temp.getStatus() + "\n";
			}
		}

		return unassignedBugs;
	}
	
	//Updates bugs.txt file after linkedList is updated.
	public synchronized void updateData() {
		Iterator<Bug> iter = bugs.iterator();
		Bug temp;
		
		// Deletes old out dated file
		File oldFile = new File("bugs.txt");
		oldFile.delete();
		
		//Writes all of linkedList info back into text file
		while (iter.hasNext()) {		
			try {
				temp = iter.next();

				FileWriter fw = new FileWriter("bugs.txt", true);
				PrintWriter out = new PrintWriter(fw);

				// Add bug to the list....
				out.println(temp.getId() + "," + temp.getApplication() + "," + temp.getDate() + ","
						+ temp.getPlatform() + "," + temp.getDescription() + "," + temp.getStatus());

				// Close the file.
				out.close();
			} catch (Exception e) {
				System.out.println("File with all bugs is not available!");
			}
			
		}
	}


}
