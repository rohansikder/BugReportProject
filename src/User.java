
public class User {
	
	private String name;
	private int employeeID;
	private String email;
	private String department;
	
	public User(String name, int employeeID, String email, String department) {
		super();
		this.name = name;
		this.employeeID = employeeID;
		this.email = email;
		this.department = department;
	}
	
	public String getName() {
		return name;
	}

	public int getEmployeeID() {
		return employeeID;
	}

	public String getEmail() {
		return email;
	}

	public String getDepartment() {
		return department;
	}
	
	
}
