import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class AccessServlet extends HttpServlet {
	private UserManager userManager;
	private DatabaseManager db;
	private CartManager cartManager;
	private static final long serialVersionUID = 109L;
	private String usersPath;
	private String cartsPath;
	@Override
	public void init() {
		this.usersPath = getServletContext().getRealPath("/WEB-INF/database/users.txt");
		this.cartsPath = getServletContext().getRealPath("/WEB-INF/database/carts.txt");
		this.db = new DatabaseManager(usersPath, cartsPath);
		this.userManager = new UserManager(db.getUsers());
		 cartManager = new CartManager(db.getUserCarts());
		 
	//	cartManager.cartCreation(userManager, cartManager);
	
	}
	@Override 
	public void destroy() {
		db.writeUsers(userManager.getUsers());
		db.writeUserCarts(cartManager.getUserCarts());
	
	}
	public void loginAction(HttpServletRequest request, HttpServletResponse response) throws IOException, 
	ServletException {
		HttpSession session = request.getSession(false);
		String email = request.getParameter("email");
		String password = request.getParameter("password");
		if(email == null || password == null) {
			session.invalidate();
			response.sendRedirect("/catalog/login.html");
		}
		
		if(session != null) {
				//HttpSession session = request.getSession(false);
			
				if(session.getAttribute("email") == null && session.getAttribute("password") == null && 
				!userManager.getUsers().get(request.getParameter("email")).getEmail().equals(request.getParameter("email"))   ||
				!userManager.getUsers().get(request.getParameter("email")).getPassword().equals(request.getParameter("password"))) {
	
					session.invalidate();
					response.sendRedirect("/catalog/login.html");
					return;
			} 
				
				else if (session.getAttribute("email") != null && session.getAttribute("password") != null && session.getAttribute("email").equals(request.getParameter("email")) && 
						session.getAttribute("password").equals(request.getParameter("password"))) {
						response.sendRedirect("/catalog/catalog.html");
						return;
					} else {
						
						User userToLogin = new User(email, password);
						try {
							userManager.loginUser(userToLogin);
						} catch(Exception e) {
							response.sendError(HttpServletResponse.SC_BAD_REQUEST, e.toString());
							return;
						}
						
						HttpSession session2 = request.getSession(true);
						session2.setAttribute("email", userToLogin.getEmail());
						response.sendRedirect("/catalog/catalog.html");
						return;
						
				}
			
		}
		User userToLogin = new User(email, password);
		try {
			userManager.loginUser(userToLogin);
		} catch(Exception e) {
			response.sendError(HttpServletResponse.SC_BAD_REQUEST, e.toString());
			return;
		}
		
		HttpSession session2 = request.getSession(true);
		session2.setAttribute("email", userToLogin.getEmail());
		response.sendRedirect("/catalog/catalog.html");
	
	}
	public void registerAction(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String email = request.getParameter("email");
		String firstName = request.getParameter("firstName");
		String lastName = request.getParameter("lastName");
		String password = request.getParameter("password");
	
		HttpSession session = request.getSession(false);
		if(session != null) {
			session.invalidate();
		//	String s = "Error: Try registering again.";
		//	response.sendError(HttpServletResponse.SC_BAD_REQUEST, s);
			//return;
			
		}
		HttpSession sessionNew = request.getSession(true);
		User registeringUser = new User(email, firstName, lastName, password);
		try {
		userManager.registerUser(registeringUser);
		} catch(Exception e) {
			response.sendError(HttpServletResponse.SC_BAD_REQUEST, e.toString());
			return;
		}
		
		//db.writeUsers(userManager.getUsers());
		
		CartManager manager = new CartManager(db.getUserCarts());
		manager.cartCreation(userManager, manager);
		response.sendRedirect("/catalog/login.html");
	
	}
	public void logOut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(false);
		if(session == null) {
			response.sendRedirect("/catalog/login.html");
			return;
		}
		else {
			session.invalidate();
			response.sendRedirect("/catalog/login.html");
			return;
		}
		
	}
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException  {
		String action = request.getParameter("action");
		if(action == null) {
			String error = "Error occured, try again.";
			response.sendError(HttpServletResponse.SC_BAD_REQUEST, error );
			return;
		}
		switch(action) {
		case "login":
		this.loginAction(request, response);
		return;
		case "register":
		this.registerAction(request, response);
		return;
		case "logout":
		this.logOut(request, response);
		return;
		default: 
		String error = "Bad request, try again.";
		response.sendError(HttpServletResponse.SC_BAD_REQUEST, error);
		return;
		
		}
		
	}
	
	
	
	
}
