import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.LinkedList;

public class UserList {
	
	private LinkedList<User> users;
	
	public UserList() {
		users = new LinkedList<>();
	}
	
	public synchronized LinkedList<User> getAllUsers(){
		return users;
	}
	
	public synchronized void  addUser(String name, int employeeID, String email, String department, int assingedBug) {
		User u = new User(name, employeeID, email, department, assingedBug);
		users.add(u);
	}
	
	public synchronized String getList()
	{
		Iterator<User> iter = users.iterator();
		User temp;
		String result="";
		while(iter.hasNext())
		{
			temp = iter.next();
			result = result + temp.getName()+" "+temp.getEmployeeID()+" "+temp.getEmail()+" "+temp.getDepartment()+" " + temp.getAssignedBug()+"\n";
		}
		
		
		return result;
	}
	
	public synchronized boolean checkLogin(String email, int id) {
		Iterator<User> iter = users.iterator();
		boolean emailCheck = false;
		boolean idCheck = false;
		boolean check = false;
		User temp;
		
		//Checks through all details to check if they match
		while(iter.hasNext())
		{
			temp = iter.next();
			
			//System.out.println(temp.getEmail() + email);
			//System.out.println(temp.getEmployeeID() + id);
			
			if(temp.getEmail().equals(email)) {
				emailCheck = true;
				//System.out.println("Email true");
			}else {
				emailCheck = false;
			}
			
			if(temp.getEmployeeID() == id) {
				idCheck = true;
				//System.out.println("id true");
			}else {
				idCheck = false;
			}
			
			if(emailCheck == true && idCheck == true) {
				check = true;
			}
		}
		
		 return check;
	}
	
	public synchronized void assignBug(int id, int assignBug) {

		Iterator<User> iter = users.iterator();
		boolean idCheck = false;
		User temp;
		
		//Checks employeeID exists
		while(iter.hasNext())
		{
			temp = iter.next();
			
			System.out.println();
			
			if(temp.getEmployeeID() == id) {
				idCheck = true;
				
				temp.setAssignedBug(assignBug);
				String test = getList();
				System.out.println(test);
				
			}else {
				idCheck = false;
			}
			
			
		}
		
	}
	
	
	public synchronized void updateData() {
		Iterator<User> iter = users.iterator();
		User temp;
		
		//Deletes old outdated file
		File oldFile = new File("users.txt");           //file to be delete  
		oldFile.delete();
		
	    File newFile = new File("filename.txt");
	    try {
			newFile.createNewFile();
		} catch (IOException e1) {
			e1.printStackTrace();
		}

		
		while(iter.hasNext())
		{
			try {
				temp = iter.next();
				
				FileWriter fw = new FileWriter("users.txt", true);
				PrintWriter out = new PrintWriter(fw);

				// Add user to the list....
				out.println(temp.getName() + "," + temp.getEmployeeID() + "," + temp.getEmail() + "," + temp.getDepartment() + "," + temp.getAssignedBug());
				
				// Close the file.
				out.close();
			} catch (Exception e) {
				System.out.println("File with all users is not available!");
			}
			
		}
	}
}
