import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public class ServerThread extends Thread {

	private Socket socket;
	private ObjectOutputStream out;
	private ObjectInputStream in;

	private BugList BList = new BugList();
	private UserList UList = new UserList();

	private String message;
	private String name;
	private int employeeID;
	private String email;
	private String department;
	
	private int assignedBug;
	private int assignedUser;
	
	private int id = 0;
	private String application;
	private String date;
	private String platform;
	private String description;
	private String status;

	private boolean bugCheck;
	private boolean verifyBugAssignment;
	
	private boolean verifyLogin = false;

	private BugList bugListThread;
	private UserList userListThread;
	
	Date myDate = new Date();


	public ServerThread(Socket s, BugList bl, UserList ul) {
		socket = s;
		bugListThread = bl;
		userListThread = ul;
	}

	public void run() {
		// 3. get Input and Output streams
		try {
			out = new ObjectOutputStream(socket.getOutputStream());
			out.flush();
			in = new ObjectInputStream(socket.getInputStream());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//Reads in from files
		readUsers();
		readBugs();
		
		//Shows all users in userLinkedList
		//System.out.println(userListThread.getList());
		//System.out.println(bugListThread.getList());


		// Conversation from the server to the client
		try {
			do {
				sendMessage("Enter 1 to register:\nEnter 2 to Login: \nEnter 3 to Add bug: \nEnter 4 to assign a bug to a developer:");
				message = (String) in.readObject();
				
				//Register user
				if (message.equalsIgnoreCase("1")) {
					
					assignedBug = 0;
					
					sendMessage("Please enter name:");
					name = (String) in.readObject();
					
					employeeID = userListThread.createID();
					
					sendMessage("Your employee ID is: " + employeeID);

					sendMessage("Please enter email:");
					email = (String) in.readObject();

					sendMessage("Please enter department:");
					department = (String) in.readObject();

					FileWriter fw = new FileWriter("users.txt", true);
					PrintWriter out = new PrintWriter(fw);

					// Add user to the list....
					userListThread.addUser(name, employeeID, email, department, assignedBug);

					//Saves user details to file
					out.println(name + "," + employeeID + "," + email + "," + department + "," + assignedBug);

					// Close the file.
					out.close();
				//Login
				} else if (message.equalsIgnoreCase("2")) {
					
					sendMessage("Please enter email:");
					email = (String) in.readObject();

					sendMessage("Please enter employee ID:");
					String employeeIDTemp = (String) in.readObject();
					employeeID = Integer.parseInt(employeeIDTemp);
					
					verifyLogin = userListThread.checkLogin(email, employeeID);
					
					if(verifyLogin == true) {
						sendMessage("Login is successful!");
						sendLoginVerfication(verifyLogin);
					}else {
						sendMessage("Login is unsucsessfull please try again.");
						sendLoginVerfication(verifyLogin);
					}
				//Create bug
				}else if (message.equalsIgnoreCase("3") && verifyLogin == true) {
					
					id = bugListThread.createID();
					
					sendMessage("You bug has been assigned id of: " + id);
					
					sendMessage("Please enter application name:");
					application = (String) in.readObject();
					
					//Gets current date and time
					SimpleDateFormat currTimeStamp = new SimpleDateFormat("yyyy-MM-dd:HH-mm-ss");
					date = currTimeStamp.format(myDate);
					
					sendMessage("Please enter platform:");
					platform = (String) in.readObject();
					
					sendMessage("Please enter bug description:");
					description = (String) in.readObject();
					
					sendMessage("Please enter status of bug:");
					status = (String) in.readObject();
					
					FileWriter fw = new FileWriter("bugs.txt", true);
					PrintWriter out = new PrintWriter(fw);
					
					// Add bugs to the list....
					bugListThread.addBug(id, application, date, platform, description, status);
					
					//Saves bug details to file
					out.println(id + "," + application + "," + date + "," + platform + "," + description + "," + status);

					// Close the file.
					out.close();
				//assign Bug
				}else if (message.equalsIgnoreCase("4") && verifyLogin == true) {
					
					sendMessage("Please enter employeeID to assign a bug to:");
					String assignedUserTemp = (String) in.readObject();
					assignedUser = Integer.parseInt(assignedUserTemp);
					
					sendMessage("Please enter bugID to assign:");
					String assignedBugTemp = (String) in.readObject();
					assignedBug = Integer.parseInt(assignedBugTemp);
					
					bugCheck = bugListThread.checkID(assignedBug);
					
					verifyBugAssignment = userListThread.assignBug(assignedUser, assignedBug, bugCheck);
					
					if(verifyBugAssignment == true) {
						sendMessage("Bug ID: " + assignedBug + " has been assigned to user ID " + assignedUser);
					}else {
						sendMessage("Bug ID or User ID does not exist, Please try again.");
					}
					
					userListThread.updateData();
					
				}else {
					sendMessage("You are not logged in, Please login to access this function!");
				}
				

				sendMessage("Please enter 1 to go back to menu or 2 to exit");
				message = (String) in.readObject();

			} while (message.equalsIgnoreCase("1"));
		} catch (IOException e) {

		} catch (ClassNotFoundException e) {

		}

	}

	public void sendMessage(String msg) {
		try {
			out.writeObject(msg);
			out.flush();
			System.out.println("server>" + msg);
		} catch (IOException ioException) {
			ioException.printStackTrace();
		}
	}
	
	void sendLoginVerfication(boolean loginStatus)
	{
		try{
			out.writeObject(loginStatus);
			out.flush();
			//System.out.println("server>" + loginStatus);
		}
		catch(IOException ioException){
			ioException.printStackTrace();
		}
	}

	// Read in from file and populate users linkedList
	public void readUsers() {
		try {
			// create scanner instance - Tried with CSV but gave weird characters first name
			Scanner scanner = new Scanner(Paths.get("users.txt").toFile());

			// set comma as delimiter
			scanner.useDelimiter(",");

			int choice = 1;

			// read all fields
			while (scanner.hasNext()) {

				switch (choice) {
				case 1:
					choice = 2;
					name = scanner.next();
					 //System.out.println("This is name " + name);
					break;
				case 2:
					choice = 3;
					employeeID = scanner.nextInt();
					 //System.out.println("This is employeeID " + employeeID);
					break;
				case 3:
					choice = 4;
					email = scanner.next();
					 //System.out.println("This is email " + email);
					break;
				case 4:
					choice = 5;
					department = scanner.next();
					 //System.out.println("This is department " + department);
					break;
				case 5:
					choice = 1;
					String assignedBugTemp = scanner.nextLine();
					assignedBugTemp = assignedBugTemp.replace(",", "");
					assignedBug = Integer.parseInt(assignedBugTemp);
					//System.out.println("This is assignedBug " + assignedBug);
					userListThread.addUser(name, employeeID, email, department, assignedBug);
					break;
				}

			}

			// close the scanner
			scanner.close();

		} catch (FileNotFoundException ex) {
			System.out.println("File with all users is not available!");
			ex.printStackTrace();
		}
	}
	
	// Read in from file and populate bugs linkedList
	public void readBugs() {
		// Read in from file and populate bugs linkedList
		try {
			Scanner scanner = new Scanner(Paths.get("bugs.txt").toFile());

			// set comma as delimiter
			scanner.useDelimiter(",");

			int choice = 1;

			// read all fields
			while (scanner.hasNext()) {
				
				
				switch (choice) {
				case 1:
					choice = 2;
					id = scanner.nextInt();
					System.out.println(id);
					break;
				case 2:
					choice = 3;
					application = scanner.next();
					//System.out.println(application);
					break;
				case 3:
					choice = 4;
					date = scanner.next();
					//System.out.println(date);
					break;
				case 4:
					choice = 5;
					platform = scanner.next();
					//System.out.println(platform);
					break;
				case 5:
					choice = 6;
					description = scanner.next();
					//System.out.println(description);
					break;
				case 6:
					choice = 1;
					status = scanner.nextLine();
					status = status.replace(",", "");
					//System.out.println(status);
					bugListThread.addBug(id, application, date, platform, description, status);
					break;
				}

			}

			// close the scanner
			scanner.close();

		} catch (FileNotFoundException ex) {
			System.out.println("File with all bugs is not available!");
			ex.printStackTrace();
		}
	}
}
