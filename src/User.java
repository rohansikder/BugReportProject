
public class User {
	
	private String name;
	private int employeeID;
	private String email;
	private String department;
	private int assignedBug;
	
	public User(String name, int employeeID, String email, String department, int assignedBug) {
		super();
		this.name = name;
		this.employeeID = employeeID;
		this.email = email;
		this.department = department;
		this.assignedBug = assignedBug;
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

	public int getAssignedBug() {
		return assignedBug;
	}

	public void setAssignedBug(int assignedBug) {
		this.assignedBug = assignedBug;
	}
	
	
	
	
	
	
}
