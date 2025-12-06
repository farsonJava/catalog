# Servlets with Tomcat
Simple backend servlets made from using the Tomcat Servlet container for studying server-side logic on a lower level.

This project was developed for educational purposes during the course of my studies on the inner workings of servlet containers,
the java-servlet api + HTTP, design patterns as well as common implementations of data structures in backend applications. 

 ### note: 
The front end aspect of this project is minimal and serves only as a hook for implementing backend processes. Persistence 
is achieved in this project using I/O streams in order to showcase the usage of init() and destroy() methods from
the java servlet api so far as they can be the points at which data is retrieved (on initialization) or written to a database
(on destroy). 

In order to run this application you need Tomcat 9+. 

# Challenges
## Understanding the client model and HTTP to control the flow of java servlet programs by secure session handling

Although HTTP methods are stateless, they suggest a client model. In order to become comfortable with 
the servlet API and have command over the control flow of your program it's important to distinguish between the various components
of an HTTP request including headers, body, and cookies. 

Recognizing their utility and how they are captured in java objects as classes
and their properties allowed me to efficiently reason about my application. I was able
to visualize the flow of the data passed into the program so that I could make security optimizations based on
awareness of scope. 

The synergy between session attributes, Java objects, and request parameters is key to controlling the logic of our backend application.
Tomcat propagates session attributes until shutdown, request parameters exist only for individual requests, and our 
Java objects are serialized by the persistence mechanism, allowing them to persist across program restarts—or conceptually, across user logins and logouts.

### session object Vs. request object:

<img width="764" height="437" alt="image6" src="https://github.com/user-attachments/assets/14dc0352-2cce-4439-a69b-383497ddb696" />

This is code taken from the loginAction method in the access servlet that's part of a series of conditional statements which is evaluating session data to prevent
users from being able to log in with stale session data after reverting back to the login page. The conditional statement in the article shown above is checking whether the 
session attributes are not null, and then whether they correspond to the request attribute data which was sent by the client using an HTML submission form.

If the session data and the request data correspond then the userToLogin check will be skipped and the client will gain access to the site and retain session
data in the form of their email without having to submit it again. At this stage in the program once the client is logged in, their session data becomes valuable
and sensitive in the context of the site as they are able to make purchases associated with that account on the basis of the session attribute storing their 
email- whenever the client hits the servlet endpoints available from the main body of the site which requires a login, the respective servlet logic determines
which account is going to participate in the transaction based on the session attribute "email". 

As such, it now becomes apparent that a pattern for storing data across requests after login has to include session attributes, and that session handling
across your application is a key security concern. 

### servlet accessed after login:
<img width="933" height="547" alt="a123" src="https://github.com/user-attachments/assets/ae86d23d-7f3b-4132-ac64-c0837be9a798" />

Looking at the article above it's much easier to understand whats going on. The add to cart logic determines who's cart is being added to on the basis of a sensitive session attribute
that was only saved through passing rigorous login logic that was protected by a password. If that attribute doesn't exist or isn't saved in the session attribute our servlet won't know to
who's cart must an item be added and therefore you are redirected to the login page. 

### note:
As this is a very minimal front end design, our backend logic uses a redirect method to the same page after clicking on a store item and adding it to cart, reloading the entire HTML page.
Through frameworks or libraries that manipulate the DOM in javascript these sorts of workarounds are not required. 

Modern backend systems are based on JSON objects, not HTML defined attributes from forms. Javascript would perform an asynchronous fetch to create requests and recieve responses that 
are predicated on JSON data encoded in the bodies of the HTTP methods. Javascript would use the browser runtime to interact with DOM libraries in order to update its client side state segmentally without 
reloading the entire page. In this instance it would update the view of the cart to include whichever item was clicked on in the shop. 

## Working with the domain model: nested data-structure solution


<img width="608" height="182" alt="domain" src="https://github.com/user-attachments/assets/3839c365-56e0-45eb-ad25-2b2b12570b88" />

The most complex element of the domain model culminated in the "CartManager" class which contained an instance field "userCarts", which is
a hashmap with a key of a string (user email) and a value of another hashmap (which contained a key of items, and a value of the quantity contained within the cart).

The most critical issue that arose during the development of this project was that the hashmap nested within userCarts which was meant to be associated with a user's email key
was decoupled from the user model and only defined within the CartManager class- therefore its creation and instantiation needed to happen within its own methods. 
### The solution I settled on:
<img width="516" height="238" alt="cart3" src="https://github.com/user-attachments/assets/1cdd9090-2fbf-4fee-9f7c-00d4850a52fd" />

The solution in the article above ensures that the creation of the hashmap nested within userCarts is going to happen in the event that the email retrieved from the session attribute
is not a key associated with the hashmap (which was inevitable for the first time a user puts something in their cart).

Earlier in development a custom equals method for the User class defined its email property as the compared value. We can use the email attribute as though it logically represents a user object through the .equals method in our application. It's a safe piece of meaningful data that we don't mind exposing and nonetheless we procured it after a secure login-check so it qualifies as a good candidate for a unique session identifier for a user. But not only that- it also allows us to keep full User objects out of our cart servlet class and deal with them by referencing their email only. 

In this instance, our cartServlet that will use our CartManager method addToCart will have access to a session attribute "email" and it will ask if there is a value stored for that key in the userCarts object- if there isn't, it will procure one. Using the getOrDefault
method we are able to safely deal with instances where we either already have a value higher than one or not, and increment it by one regardless.

### note:
If you scroll back up to article "servlet accessed after login" you will see the addToCart method within the doPost method. You may observe that the "item" object is composed of data extracted from attributes in the request object.
These are all attributes contained in the HTML form- its values depend on which item in the shop you click on. The email is of course derived from the session state, and the item is built out of the content of the request. 
