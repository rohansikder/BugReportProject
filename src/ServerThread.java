import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.nio.file.Paths;
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
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		readUsers();

		// Conversation from the server to the client
		try {
			do {
				sendMessage("Press 1 to register.");
				message = (String) in.readObject();

				if (message.equalsIgnoreCase("1")) {
					sendMessage("Please enter name:");
					name = (String) in.readObject();

					sendMessage("Please enter employee ID:");
					String employeeIDTemp = (String) in.readObject();
					employeeID = Integer.parseInt(employeeIDTemp);

					sendMessage("Please enter email:");
					email = (String) in.readObject();

					sendMessage("Please enter department:");
					department = (String) in.readObject();

					FileWriter fw = new FileWriter("users.txt", true);
					PrintWriter out = new PrintWriter(fw);

					// Add user to the list....
					userListThread.addUser(name, employeeID, email, department);

					out.println(name + "," + employeeID + "," + email + "," + department);

					// Close the file.
					out.close();
				} else if (message.equalsIgnoreCase("2")) {

					/*
					 * //message = "name"+"*"+"author"+"?"+"name1"+"*"+"author1"+"?"; message =
					 * lib.getList();
					 * 
					 * //Option 1 //sendMessage(message);
					 * 
					 * //Option2 String[] books = message.split("\\?");
					 * 
					 * sendMessage(""+books.length);
					 * 
					 * for(int i=0;i<books.length;i++) { String[] details = books[i].split("\\*");
					 * 
					 * sendMessage(details[0]); sendMessage(details[1]);
					 * 
					 * }
					 */
				}

				sendMessage("Please enter 1 to repeat or 2 to exit");
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

	public void readUsers() {
		// Read in from file and populate user linkedList
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
					choice = 1;
					department = scanner.nextLine();
					// System.out.println("This is department " + department.replace(",", ""));
					userListThread.addUser(name, employeeID, email, department);
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

}
