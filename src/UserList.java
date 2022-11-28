import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.LinkedList;

public class UserList {

	private LinkedList<User> users;

	public UserList() {
		users = new LinkedList<>();
	}

	public synchronized LinkedList<User> getAllUsers() {
		return users;
	}
	
	//Add a user to the linked list
	public synchronized void addUser(String name, int employeeID, String email, String department, int assingedBug) {
		User u = new User(name, employeeID, email, department, assingedBug);
		users.add(u);
	}
	
	//Shows all info in the User linkedList
	public synchronized String getList() {
		Iterator<User> iter = users.iterator();
		User temp;
		String result = "";
		while (iter.hasNext()) {
			temp = iter.next();
			result = result + temp.getName() + " " + temp.getEmployeeID() + " " + temp.getEmail() + " "
					+ temp.getDepartment() + " " + temp.getAssignedBug() + "\n";
		}

		return result;
	}
	
	//Creates an id by getting the last id from the users and incrementing it
	public synchronized int createID() {
		Iterator<User> iter = users.iterator();
		User temp;
		int lastID = 0;
		while (iter.hasNext()) {
			temp = iter.next();
			lastID = temp.getEmployeeID();
		}

		lastID++;

		return lastID;
	}

	//Checks login details exits in users.
	public synchronized boolean checkLogin(String email, int id) {
		Iterator<User> iter = users.iterator();
		boolean emailCheck = false;
		boolean idCheck = false;
		boolean check = false;
		User temp;

		// Checks through all details to check if they match
		while (iter.hasNext()) {
			temp = iter.next();

			// System.out.println(temp.getEmail() + email);
			// System.out.println(temp.getEmployeeID() + id);

			if (temp.getEmail().equals(email)) {
				emailCheck = true;
				// System.out.println("Email true");
			} else {
				emailCheck = false;
			}

			if (temp.getEmployeeID() == id) {
				idCheck = true;
				// System.out.println("id true");
			} else {
				idCheck = false;
			}

			if (emailCheck == true && idCheck == true) {
				check = true;
			}
		}

		return check;
	}

	//Assigns a bug to the user and checks if bug they want to assign exists
	public synchronized boolean assignBug(int id, int assignBug, boolean bugCheck) {

		Iterator<User> iter = users.iterator();
		boolean idCheck = false;
		User temp;

		// Checks employeeID exists
		while (iter.hasNext()) {
			temp = iter.next();

			System.out.println();

			if (temp.getEmployeeID() == id && bugCheck == true) {
				idCheck = true;
				temp.setAssignedBug(assignBug);
				return idCheck;

			} else {
				idCheck = false;
			}

		}

		return idCheck;

	}
	
	//Updates the user.txt file after linked list is changed
	public synchronized void updateData() {
		Iterator<User> iter = users.iterator();
		User temp;

		// Deletes old out dated file
		File oldFile = new File("users.txt");
		oldFile.delete();
		
		//Creates a new file and writes users back in
		while (iter.hasNext()) {
			try {
				temp = iter.next();

				FileWriter fw = new FileWriter("users.txt", true);
				PrintWriter out = new PrintWriter(fw);

				// Add user to the list....
				out.println(temp.getName() + "," + temp.getEmployeeID() + "," + temp.getEmail() + ","
						+ temp.getDepartment() + "," + temp.getAssignedBug());

				// Close the file.
				out.close();
			} catch (Exception e) {
				System.out.println("File with all users is not available!");
			}

		}
	}
}
