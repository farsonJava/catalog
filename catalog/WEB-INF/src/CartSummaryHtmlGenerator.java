import java.util.Map;

public class CartSummaryHtmlGenerator {
	public static String getCartSummaryPage(Map<CartItem, Integer> userCart) {
		StringBuilder sb = new StringBuilder();
		
		sb.append("<html><head><title>My Cart</title><link rel=\"stylesheet\" href=\"styles.css\"></head><body>");
		
	
		sb.append("<h1>Your Cart!</h1>");
				
	
		sb.append("<div class=\"cart-summary\">");
		
		for(Map.Entry<CartItem, Integer> entry: userCart.entrySet()) {
			CartItem currentItem = entry.getKey();
			int quantity = entry.getValue();
			
			sb.append("<div class=\"cart-item-summary\">");
			
		
			sb.append(String.format("<img class=\"summary-image\" src=\"%s\">", currentItem.getImgAddress()));
			sb.append(String.format("<h3>Name: %s Quantity: %d Total Price: %d</h3>", currentItem.getName(), quantity, quantity * currentItem.getPrice()));
			
			sb.append("</div>");
		}
		// button that links to catalog.html
		sb.append("<form><button class=\"cart-button\" type=\"submit\" formaction=\"/catalog/catalog.html\">Keep Shopping</button></form>");
		
	
		sb.append("</div>");
		
		sb.append("</body></html>");
		
		return sb.toString();
	}


}
