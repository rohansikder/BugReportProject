
import java.io.*;
import java.net.*;
import java.nio.file.Paths;
import java.util.Scanner;
import java.io.FileNotFoundException;

//Rohan Sikder - G00389052
public class Provider {
	ServerSocket providerSocket;
	Socket connection = null;
	ObjectOutputStream out;
	ObjectInputStream in;
	String message;
	ServerThread s;
	UserList sharedUser;
	BugList sharedBug;
	
	//User details variables
	String name;
	int employeeID;
	String email;
	String department;
	int assignedBug;
	
	//Bug Details variables
	int id;
	String application;
	String date;
	String platform;
	String description;
	String status;
	
	Provider() {
	}

	void run() {
		try {
			// 1. creating a server socket
			providerSocket = new ServerSocket(2004, 10);
			sharedUser = new UserList();
			sharedBug = new BugList();
			
			readUsers();
			readBugs();
			
			// 2. Wait for connection
			while (true) {
				System.out.println("Waiting for connection");
				connection = providerSocket.accept();
				System.out.println("Connection received from " + connection.getInetAddress().getHostName());
				s = new ServerThread(connection, sharedBug, sharedUser);
				s.start();
			}
		} catch (IOException ioException) {
			ioException.printStackTrace();
		} finally {
			// 4: Closing connection
			try {
				in.close();
				out.close();
				providerSocket.close();
			} catch (IOException ioException) {
				ioException.printStackTrace();
			}
		}
	}

	void sendMessage(String msg) {
		try {
			out.writeObject(msg);
			out.flush();
			System.out.println("Server>" + msg);
		} catch (IOException ioException) {
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
						// System.out.println("This is name " + name);
						break;
					case 2:
						choice = 3;
						employeeID = scanner.nextInt();
						// System.out.println("This is employeeID " + employeeID);
						break;
					case 3:
						choice = 4;
						email = scanner.next();
						// System.out.println("This is email " + email);
						break;
					case 4:
						choice = 5;
						department = scanner.next();
						// System.out.println("This is department " + department);
						break;
					case 5:
						choice = 1;
						String assignedBugTemp = scanner.nextLine();
						assignedBugTemp = assignedBugTemp.replace(",", "");
						assignedBug = Integer.parseInt(assignedBugTemp);
						// System.out.println("This is assignedBug " + assignedBug);
						sharedUser.addUser(name, employeeID, email, department, assignedBug);
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
						//System.out.println(id);
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
						sharedBug.addBug(id, application, date, platform, description, status);
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

	//Sends Flag to check if user is logged in
	void sendLoginVerfication(boolean loginStatus) {
		try {
			out.writeObject(loginStatus);
			out.flush();
			// System.out.println("server>" + loginStatus);
		} catch (IOException ioException) {
			ioException.printStackTrace();
		}
	}

	public static void main(String args[]) {
		Provider server = new Provider();
		while (true) {
			server.run();
		}
	}
}
