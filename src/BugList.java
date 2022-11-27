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

	public synchronized void addBug(int id, String application, String date, String platform, String description,
			String status) {
		Bug b = new Bug(id, application, date, platform, description, status);
		bugs.add(b);
	}

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
	
	public synchronized void updateData() {
		Iterator<Bug> iter = bugs.iterator();
		Bug temp;
		
		// Deletes old out dated file
		File oldFile = new File("bugs.txt");
		oldFile.delete();

		while (iter.hasNext()) {		
			try {
				temp = iter.next();

				FileWriter fw = new FileWriter("bugs.txt", true);
				PrintWriter out = new PrintWriter(fw);

				// Add user to the list....
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
