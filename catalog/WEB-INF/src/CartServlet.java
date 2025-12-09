
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


public class CartServlet extends HttpServlet {
	private CartManager cartManager;
	private DatabaseManager db;
	private static final long serialVersionUID = 1003L;
	private String usersPath;
	private String cartsPath;
	@Override
	public void init() {
		this.usersPath = getServletContext().getRealPath("/WEB-INF/database/users.txt");
		this.cartsPath = getServletContext().getRealPath("/WEB-INF/database/carts.txt");
		this.db = new DatabaseManager(usersPath, cartsPath);
		this.cartManager = new CartManager(db.getUserCarts());
		
	}
	@Override
	public void destroy() {
		db.writeUserCarts(cartManager.getUserCarts());
	}
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(false);
		if(session == null) {
			response.sendRedirect("/catalog/login.html");
			return;
		}
		if(session.getAttribute("email") == null) {
			session.invalidate();
			response.sendRedirect("/catalog/login.html");
			return;
		}
		
		PrintWriter out = response.getWriter();
		Map<CartItem, Integer> cart = cartManager.getUserCart((String)session.getAttribute("email"));
		if(cart == null || 
				cart.isEmpty()) {
			out.println("<a href='/catalog/catalog.html'>Your cart is empty, return to catalog.</a>");
			return;
		}
		else {
			out.println(CartSummaryHtmlGenerator.getCartSummaryPage(cart));
		}
	}
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(false);
		if(session == null) {
			response.sendRedirect("/catalog/login.html");
			return;
		}
		if(session.getAttribute("email") == null) {
			session.invalidate();
			response.sendRedirect("/catalog/login.html");
			return;
		}
		String email = (String)session.getAttribute("email");
		String imgAddress = request.getParameter("imgAddress");
		String itemName = request.getParameter("itemName");
		String itemPrice = request.getParameter("itemPrice");
		int itemPriceSaved = Integer.valueOf(itemPrice);
		CartItem cartItem = new CartItem(imgAddress, itemName, itemPriceSaved);
		
		
		try {
		cartManager.addToCart(email, cartItem);
		} catch (Exception e) {
			response.sendError(HttpServletResponse.SC_BAD_REQUEST, e.toString());
			return;
		}
		
		response.sendRedirect("/catalog/catalog.html");
	}
	
}
