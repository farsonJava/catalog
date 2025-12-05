import java.security.InvalidParameterException;
import java.util.HashMap;
import java.util.Map;

public class UserManager {
	public  Map<String, Map<CartItem, Integer>> mapTest;
 private Map<String, User> users;
 public UserManager(Map<String, User> users) {
	 this.users = users;
	 this.mapTest = this.toHashMap();
	 
 }
 public UserManager() {
	 this.users = new HashMap<String, User>();
	 this.mapTest = toHashMap();
 }
 public static void main(String[] args) {
	/* UserManager userManager = new UserManager();
	 User user = new User("arthur.seleznov@gmail.com", "pass123", "Arthur", "Seleznov");
	 User user2 = new User("william.murphy@gmail.com", "pass123", "William", "Murphy");
	 userManager.registerUser(user2);
	 userManager.registerUser(user);
	 System.out.println(userManager.loginUser(user));
	 System.out.println(userManager.getUsers()); */
 }
 
 public Map<String, User> getUsers() {
	 return this.users;
 }
 public void registerUser(User userToRegister) {
	 
	 if(!userToRegister.validated()) {
		 throw new InvalidParameterException();
	 }
	 
	 if(this.users.get(userToRegister.getEmail()) != null) {
		 throw new IllegalStateException();
	 }
	 
	 users.put(userToRegister.getEmail(), userToRegister);
	
 }
 public User loginUser(User userToLogin) {
	 
	 if(userToLogin.getEmail() == null || userToLogin.getEmail().equals("") || 
			 userToLogin.getPassword() == null || userToLogin.getPassword().equals("")) {
		 throw new InvalidParameterException();
		 
	 };
	 if(users.get(userToLogin.getEmail()) == null) {
		 throw new IllegalStateException();
	 }
	 if(!users.get(userToLogin.getEmail()).getEmail().equals(userToLogin.getEmail()) || 
			 !users.get(userToLogin.getEmail()).getPassword().equals(userToLogin.getPassword())) {
		 throw new IllegalStateException();
	 };
	 return users.get(userToLogin.getEmail());
		 
 }
 public Map<String, Map<CartItem, Integer>> toHashMap(){
	 Map<String, Map<CartItem, Integer>> test = new HashMap<>();
	 for(String email : this.getUsers().keySet()) {
		 
		 test.put(email, new HashMap<CartItem, Integer>());
	 }
	 
	 return test;
 }
}
