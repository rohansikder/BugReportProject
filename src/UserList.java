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
	
	public synchronized void  addUser(String name, int employeeID, String email, String department) {
		User u = new User(name, employeeID, email, department);
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
			result = result + temp.getName()+" "+temp.getEmail()+" "+temp.getEmployeeID()+" "+temp.getDepartment()+"\n";
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
	
}
