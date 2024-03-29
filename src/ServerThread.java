import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;

//Rohan Sikder - G00389052
//GitHub - https://github.com/rohansikder/BugReportProject
public class ServerThread extends Thread {

	private Socket socket;
	private ObjectOutputStream out;
	private ObjectInputStream in;

	// User Variables
	private String message;
	private String name;
	private int employeeID;
	private String email;
	private boolean checkEmail = true;
	private String department;

	// Bug assignment variables
	private int assignedBug;
	private int assignedUser;
	private boolean bugCheck;
	private boolean verifyBugAssignment;

	// Bug Variables
	private int id;
	private String application;
	private String date;
	Date myDate = new Date();
	private String platform;
	private String description;
	private String status;
	
	private int statusID;
	private String newStatus;

	// Variables to check login - Change to true if you want to bypass login
	private boolean verifyLogin = false;

	private BugList bugListThread;
	private UserList userListThread;

	
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
			e.printStackTrace();
		}

		// Conversation from the server to the client
		try {
			do {
				sendMessage("Welcome to Bug Tracker app, Please Register and/or login to access all functionaliy: \nEnter 1 to Register:\nEnter 2 to Login: \nEnter 3 to Add bug: \nEnter 4 to assign a bug to a developer: \nEnter 5 to view all bugs that are not assigned to any developers:\nEnter 6 to change ths status of a bug:");
				message = (String) in.readObject();

				// Register user
				if (message.equalsIgnoreCase("1")) {
					
					sendMessage("Please enter name:");
					name = (String) in.readObject();

					sendMessage("Please enter email:");
					email = (String) in.readObject();

					sendMessage("Please enter department:");
					department = (String) in.readObject();
					
					checkEmail = userListThread.checkEmail(email);
					
					if(checkEmail == false) {
						FileWriter fw = new FileWriter("users.txt", true);
						PrintWriter out = new PrintWriter(fw);

						// Add user to the list....
						userListThread.addUser(name,employeeID = userListThread.createID(), email, department, 0);

						// Saves user details to file
						out.println(name + "," + employeeID + "," + email + "," + department + "," + 0);

						// Close the file.
						out.close();
						sendMessage("Account created! Your employee id is " + employeeID);
					} else {
						sendMessage("This email is already has a account. Please try again with a new email.");
						
					}
					
					// Login
				} else if (message.equalsIgnoreCase("2")) {

					sendMessage("Please enter email:");
					email = (String) in.readObject();

					sendMessage("Please enter employee ID:");
					String employeeIDTemp = (String) in.readObject();
					employeeID = Integer.parseInt(employeeIDTemp);
					
					
					//Checks login details 
					verifyLogin = userListThread.checkLogin(email, employeeID);
					
					if (verifyLogin == true) {
						sendMessage("Login is successful!");
						sendLoginVerfication(verifyLogin);
					} else {
						sendMessage("Login is unsucsessfull please try again.");
						sendLoginVerfication(verifyLogin);
					}

					// Create bug
				} else if (message.equalsIgnoreCase("3") && verifyLogin == true) {

					sendMessage("Please enter application name:");
					application = (String) in.readObject();

					// Gets current date and time
					SimpleDateFormat currTimeStamp = new SimpleDateFormat("yyyy-MM-dd:HH-mm-ss");
					date = currTimeStamp.format(myDate);

					sendMessage("Please enter platform:");
					platform = (String) in.readObject();

					sendMessage("Please enter bug description:");
					description = (String) in.readObject();

					sendMessage("Please enter status of bug:(OPEN, CLOSED, ASSIGNED)");
					status = (String) in.readObject();
					
					FileWriter fw = new FileWriter("bugs.txt", true);
					PrintWriter out = new PrintWriter(fw);

					// Add bugs to the list....
					bugListThread.addBug(id = bugListThread.createID(), application, date, platform, description, status.toUpperCase());

					// Saves bug details to file
					out.println(
							id + "," + application + "," + date + "," + platform + "," + description + "," + status.toUpperCase());
					
					sendMessage("You bug has been assigned id of: " + id);
					// Close the file.
					out.close();
					// assign Bug
				} else if (message.equalsIgnoreCase("4") && verifyLogin == true) {

					sendMessage("Please enter employeeID to assign a bug to:");
					String assignedUserTemp = (String) in.readObject();
					assignedUser = Integer.parseInt(assignedUserTemp);

					sendMessage("Please enter bugID to assign:");
					String assignedBugTemp = (String) in.readObject();
					assignedBug = Integer.parseInt(assignedBugTemp);
					
					//Checks if bug exists
					bugCheck = bugListThread.checkID(assignedBug);
					
					verifyBugAssignment = userListThread.assignBug(assignedUser, assignedBug, bugCheck);

					if (verifyBugAssignment == true) {
						bugListThread.setStatus(assignedBug, "ASSIGNED");
						bugListThread.updateData();
						sendMessage("Bug ID: " + assignedBug + " has been assigned to user ID " + assignedUser);
					} else {
						sendMessage("Bug ID or User ID does not exist, Please try again.");
					}	

					userListThread.updateData();
					//Shows all unassigned bugs
				} else if (message.equalsIgnoreCase("5") && verifyLogin == true){
		
					sendMessage(bugListThread.getUnassignedBugs());
				
					//Change status of bugs
				} else if (message.equalsIgnoreCase("6") && verifyLogin == true){
					
					sendMessage("Please enter the BugID to change status: ");
					String statusIDTemp = (String) in.readObject();
					statusID = Integer.parseInt(statusIDTemp);
					
					sendMessage("Please enter new status of bug:(OPEN, CLOSED, ASSIGNED)");
					newStatus = (String) in.readObject();
					
						
					if(bugListThread.checkID(statusID)) {	
						sendMessage("Bug " + statusID + " status is now: " + newStatus.toUpperCase());
						bugListThread.setStatus(statusID, newStatus.toUpperCase());
						bugListThread.updateData();
						
					}else {
						sendMessage("BugId does not exist, Please try again!");
					}
					
					//the if statement breaks out as the user is not logged in
				} else {
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
			System.out.println("Server>" + msg);
		} catch (IOException ioException) {
			ioException.printStackTrace();
		}
	}

	//Sends Flag to check if user is logged in 
	public void sendLoginVerfication(boolean loginStatus) {
		try {
			out.writeObject(loginStatus);
			out.flush();
			// System.out.println("server>" + loginStatus);
		} catch (IOException ioException) {
			ioException.printStackTrace();
		}
	}
}
