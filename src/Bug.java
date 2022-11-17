
public class Bug {
	
	private int id;
	private String application;
	private int date;
	private String platform;
	private String description;
	private String status;
	
	public Bug(int id, String application, int date, String platform, String description, String status) {
		super();
		this.id = id;
		this.application = application;
		this.date = date;
		this.platform = platform;
		this.description = description;
		this.status = status;
	}
	
	public int getId() {
		return id;
	}

	public String getApplication() {
		return application;
	}

	public int getDate() {
		return date;
	}

	public String getPlatform() {
		return platform;
	}

	public String getDescription() {
		return description;
	}

	public String getStatus() {
		return status;
	}
	
	
	
}