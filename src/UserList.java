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

	@Override
	public String toString() {
		return "UserList [users=" + users + "]";
	}
	
}
