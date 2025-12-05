import java.io.Serializable;

public class User implements Serializable {
	private String email;
	private String password;
	private String firstName;
	private String lastName;
	private static final long serialVersionUID = 10L; 
public User(String email, String password, String firstName, String lastName) {
	this.email = email;
	this.password = password;
	this.firstName = firstName;
	this.lastName = lastName;
	
}
public static void main(String[] args ) {
	/*/ User user = new User("arthur", "lol");
	User user1 = new User("dingus", "lol123");
	User user2 = new User("arthur", "bitch");
	System.out.println(user.equals(user2));
	System.out.println(user.equals(user1));
	
	// Both should return true.
	System.out.println(user.hashCode() == user2.hashCode());
	System.out.println(user.hashCode() != user1.hashCode()); */

}
public User(String email, String password) {
	this.email = email;
	this.password = password;
}
public String getEmail() {
	return email;
}
public void setEmail(String email) {
	this.email = email;
}
public String getPassword() {
	return password;
}
public void setPassword(String password) {
	this.password = password;
}
public String getFirstName() {
	return firstName;
}
public void setFirstName(String firstName) {
	this.firstName = firstName;
}
public String getLastName() {
	return lastName;
}
public void setLastName(String lastName) {
	this.lastName = lastName;
}
@Override
public boolean equals(Object obj) {
	if(this == obj) {
		return true;
	}
	if(obj == null || obj.getClass() != this.getClass()) {
		return false;
	}
	User userToCompare = (User) obj;
	return userToCompare.getEmail().equals(this.email);
	
}

@Override
public int hashCode() {
	return this.email.hashCode();
}
public boolean isNullOrEmpty(String s) {
	if(s == null || s.equals("")) {
		return true;
	}
	return false;
	
}
public boolean validated() {
	if (isNullOrEmpty(this.email) || isNullOrEmpty(this.firstName)|| isNullOrEmpty(this.password) || 
			isNullOrEmpty(this.lastName)) {
		return false;
	}
	return true;
}
public String toString() {
	return this.firstName + " " + this.lastName;
}

}
