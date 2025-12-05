import java.util.HashMap;
import java.util.Map;



public class CartManager {
	private Map<String, Map<CartItem, Integer>> userCarts;
	public CartManager(Map<String, Map<CartItem, Integer>> userCarts) {
		this.userCarts = userCarts;
	}
	public CartManager() {
		this.userCarts = new HashMap<String, Map<CartItem, Integer>>();
		
	}
	public static void main (String[] args) {
		  UserManager userManager = new UserManager();
	//	CartManager cartManager = new CartManager(userManager.mapTest);
		
		CartItem cartItem = new CartItem("imgAdd", "Hoe", 2);
		CartItem cartItem2 = new CartItem("img0", "Rake", 5);
		CartItem cartItem3 = new CartItem("img4", "Shovel", 6);
		
		User user1 = new User("polo@", "pass123", "polo", "Seleznov");
		User user2 = new User("doom@", "pass123", "doom", "Murphy");
		User user3 = new User("trooper@", "pass123", "trooper", "Delaware");
		
		userManager.registerUser(user1);
		userManager.registerUser(user2);
		userManager.registerUser(user3);
		CartManager cartManager = new CartManager(userManager.mapTest);
		cartManager.cartCreation(userManager, cartManager);
		

		cartManager.addToCart(user1.getEmail(), cartItem);
		
		cartManager.getUserCart(user2.getEmail());
		cartManager.addToCart(user3.getEmail(), cartItem3);
		
		cartManager.getUserCart(user3.getEmail());
		cartManager.addToCart(user3.getEmail(), cartItem2);
		cartManager.addToCart(user3.getEmail(), cartItem2);
		
		System.out.println(cartManager.getUserCart(user1.getEmail()));
		System.out.println(cartManager.getUserCarts());
		System.out.println(cartManager.getUserCart(user2.getEmail())); 
		cartManager.cartCreation(userManager, cartManager); 
	}
	public Map <String, Map<CartItem, Integer>>getUserCarts() {
		return this.userCarts;
	}
	public Map<CartItem, Integer> getUserCart(String email) {
		
		return this.userCarts.get(email);
	}
	public void addToCart(String email, CartItem item) {
		Map<CartItem, Integer> cart = this.userCarts.get(email);
		if(cart == null) {
			cart = new HashMap<CartItem, Integer>();
			this.userCarts.put(email, cart);
		//	throw new IllegalArgumentException("Cart does not exist for user " + email); 
			
		}
	int current = cart.getOrDefault(item, 0);
	cart.put(item, current+1);
	
	}
	public void cartCreation(UserManager userManager, CartManager previousCart) {
		Map<String, Map<CartItem, Integer>> cartMap = new HashMap<>();
	 for(String key : userManager.getUsers().keySet()) {
		 if(previousCart.getUserCart(key) == null) {
		cartMap.put(key, new HashMap<CartItem, Integer>());
		
	}
	// this.userCarts = cartMap; 
	 for(String key2 : previousCart.getUserCarts().keySet()) {
		 if(previousCart.getUserCart(key2) != null) {
		cartMap.put(key2, previousCart.getUserCart(key2));
		 }
	 }
	 
	 this.userCarts = cartMap;
	
}
	}
	}
