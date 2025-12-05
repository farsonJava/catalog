import java.io.IOException;
import java.io.PrintWriter;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.io.FileNotFoundException;
import java.io.File;
import java.io.EOFException;
import java.util.Map;
import java.util.HashMap;

public class DatabaseManager {
	// Replace below paths with your full path to users.txt and carts.txt in the project database folder.
	private final String USERS_PATH = "/Users/arthurseleznov/Documents//apache-tomcat-9.0.111//webapps//catalog//WEB-INF//database//users.txt";
	private final String CARTS_PATH = "/Users/arthurseleznov/Documents//apache-tomcat-9.0.111//webapps//catalog//WEB-INF//database//carts.txt";


	public Map<String, User> getUsers() {
		try{
			File usersDb = new File(this.USERS_PATH);
			
			if (usersDb.isFile() && usersDb.length() != 0L) {
				FileInputStream fis = new FileInputStream(usersDb);
				ObjectInputStream ois = new ObjectInputStream(fis);
				Map<String, User> users = (Map<String, User>) ois.readObject();
				ois.close();
				return users;
			}
		} catch (FileNotFoundException e) {
			System.out.println("FAILED TO FIND SAVED DATA.");
		} catch (IOException e) {
			System.out.println("FAILED TO SAVE DATA.");
		} catch (Exception e) {
			System.out.println("UNEXPECTED ERROR OCCURED.");
		}
		return new HashMap<>();
	}
	
	public Map<String, Map<CartItem, Integer>> getUserCarts() {
		try{
			File cartsDb = new File(this.CARTS_PATH);
			
			if (cartsDb.isFile() && cartsDb.length() != 0L) {
				FileInputStream fis = new FileInputStream(cartsDb);
				ObjectInputStream ois = new ObjectInputStream(fis);
				Map<String, Map<CartItem, Integer>> userCarts = (Map<String, Map<CartItem, Integer>>) ois.readObject();
				ois.close();
				return userCarts;
			}
		} catch (FileNotFoundException e) {
			System.out.println("FAILED TO FIND SAVED DATA.");
		} catch (IOException e) {
			System.out.println("FAILED TO SAVE DATA.");
		} catch (Exception e) {
			System.out.println("UNEXPECTED ERROR OCCURED.");
		}
		return new HashMap<>();
	}
	
	
	public void writeUsers(Map<String, User> users) {
		try {
			File usersDb = new File(this.USERS_PATH);
			
			if(usersDb.isFile() && usersDb.length() != 0L) {
				usersDb.delete();
				usersDb.createNewFile();
			}
			
			FileOutputStream fos = new FileOutputStream(usersDb);
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			oos.writeObject(users);
			
			oos.flush();
			oos.close();
		} catch (IOException e) {
			System.out.println("ERROR SAVING DATA");
		} catch (Exception e) {
			System.out.println("UNEXPECTED ERROR OCCURED.");
		}
	}
	
	public void writeUserCarts(Map<String, Map<CartItem, Integer>> userCarts) {
		try {
			File cartsDb = new File(this.CARTS_PATH);
			
			if(cartsDb.isFile() && cartsDb.length() != 0L) {
				cartsDb.delete();
				cartsDb.createNewFile();
			}
			
			FileOutputStream fos = new FileOutputStream(cartsDb);
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			oos.writeObject(userCarts);
			
			oos.flush();
			oos.close();
		} catch (IOException e) {
			System.out.println("ERROR SAVING DATA");
			System.out.println(e);
		} catch (Exception e) {
			System.out.println("UNEXPECTED ERROR OCCURED.");
		}
	}
	public static void main(String[] args) {
		/*/ UserManager userManager = new UserManager();
		CartManager cartManager = new CartManager();
		
		CartItem cartItem = new CartItem("imgAdd", "Hoe", 2);
		CartItem cartItem2 = new CartItem("img0", "Rake", 5);
		CartItem cartItem3 = new CartItem("img4", "Shovel", 6);
		
		User user1 = new User("arthur.seleznov@gmail.com", "pass123", "Arthur", "Seleznov");
		User user2 = new User("john.murphy@gmail.com", "pass123", "John", "Murphy");
		User user3 = new User("scott.delaware@gmail.com", "pass123", "Scott", "Delaware");
		
		userManager.registerUser(user1);
		userManager.registerUser(user2);
		userManager.registerUser(user3);
		
		cartManager.cartCreation(userManager);
		

		cartManager.addToCart(user1.getEmail(), cartItem);
		
		cartManager.getUserCart(user2.getEmail());
		cartManager.addToCart(user3.getEmail(), cartItem3);
		
		cartManager.getUserCart(user3.getEmail());
		cartManager.addToCart(user3.getEmail(), cartItem2);
		cartManager.addToCart(user3.getEmail(), cartItem2);
		
		System.out.println(cartManager.getUserCart(user1.getEmail()));
		System.out.println(cartManager.getUserCarts());
		System.out.println(cartManager.getUserCart(user2.getEmail()));
		DatabaseManager db = new DatabaseManager();
		
		db.writeUserCarts(cartManager.getUserCarts());
		db.writeUsers(userManager.getUsers()); */
	}
}