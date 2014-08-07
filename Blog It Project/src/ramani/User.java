package ramani;
import java.sql.Date;

public final class User {
	
	private String fullname;
	private String gender;
	private Date DoB;
	private String place;
	private final String username;
	private final String password;
	
	public User(String username,String password) {
		this.username = username;
		this.password = password;
	}
	 
	public User(String fullname , String gender , Date DoB , String place , String username , String password)
	{
		this.fullname = fullname;
		this.gender = gender;
		this.DoB = DoB;
		this.place = place;
		this.username = username; 
		this.password = password;
	}

	public String getUserName() {
		return username;
	}

	public String getFullname() {
		return fullname;
	}
	
	public String getGender() {
		return gender;
	}
	
	public String getPlace() {
		return place;
	}
	
	public Date getDoB() {
		return DoB;
	}
	
	public String getPassword() {
		return password;
	}

}