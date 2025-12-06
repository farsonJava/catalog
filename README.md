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

Modern backend systems are based on JSON objects, not HTML defined attributes from forms. Javascript would perform an asynchronous fetch to create requests and receive responses that 
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

Earlier in development a custom equals method for the User class defined its email property as the compared value. We can use the email attribute as though it logically represents a user object through the .equals method in our application. It's a safe piece of meaningful data that we don't mind exposing and nonetheless we procured it after a secure login-check so it qualifies as a good candidate for a unique session identifier for a user. But not only that- it also allows us to keep full User objects out of our cart servlet class and deal with them by referencing their email only. In addition to this, hashmaps in java internally use the .equals method: in a system where we re-create existing objects in our logic and we want to treat them as keys, overriding their equals (and hashcode) methods allows us
to treat them as equal objects for the sake of their functionality as keys in the event that we have differing objects in memory that we intend to portray the same key.

In this instance, our cartServlet that will use our CartManager method addToCart will have access to a session attribute "email" and it will ask if there is a value stored for that key in the userCarts object- if there isn't, it will procure one. Using the getOrDefault
method we are able to safely deal with instances where we either already have a value higher than one or not, and increment it by one regardless.

### note:
If you scroll back up to article "servlet accessed after login" you will see the addToCart method within the doPost method. You may observe that the "item" object is composed of data extracted from attributes in the request object.
These are all attributes contained in the HTML form- its values depend on which item in the shop you click on. The email is of course derived from the session state, and the item is built out of the content of the request. 

As we mentioned earlier, 
.equals method overriding allows our hashmap to recognize equality despite objects having different memory addresses and not really being the same. In the instance of our cartItem object, its name property determines its equality and therefore
we are able to reason within this method about new cartItem objects being created as the same keys (if they have the same name) to keep increasing their quantity- while retaining their complex properties and allowing them to be parameterized dynamically by whatever data in the request
corresponds to their fields. 

This latter point speaks to the scalability of the approach; if we wanted to introduce a new item, our hashmap data structure is already primed to snap into place- we can define a new cart item only using front-end request parameters since
our logic is just looking for their names to be the same for its functionality as a key to click so we can start counting their quantity in our cart.

### CartItem equals override method:
<img width="535" height="291" alt="hash" src="https://github.com/user-attachments/assets/fc7bbdfa-32c7-468d-9741-c32c9f9bb1b2" />

A key insight from this project is that complex domain model objects must override equals and hashCode when they are used as keys in a hashmap in order to enable correct key equality, fully utilizing the hashmap data structure.

## Web App Architecture: Implementing controller design through orchestrating HTML form content, web xml and servlet logic. 

The system of process selection in this web app is defined by the various elements that compose an endpoint, and the design chosen for encapsulating
all servlet access logic in the same class is that of the "controller". This is implemented by having every HTTP request contain the same "POST" method when its associated with the
access servlet, so that the HTML "value" attribute is evaluated in a switch case that delegates to a respective function call.

web.xml's servlet mapping ensures that access logic is centralized in a single servlet and every POST request associated with the "access" entry 
is routed through a doPost method with a switch case which acts as a controller. This means that the combination of "POST", catalog/access/ + a value attribute
that is associated with an access servlet function call, can be inserted anywhere in the program to access logic from that single servlet class.

HTML Form → POST → AccessServlet → switch → Method → Redirect

### note:
Notice the limitations of not leveraging the DOM API- the implication of being able to access the access servlet endpoint from any page in our web app means that we are forced to create redirects 
to the page we expect that function to be called from. This introduces additional coupling between backend logic and frontend navigation complicating overall design. One way this can be avoided is through using modern frontend frameworks like those of javascript.


### Servlet definition, common entry point for all access logic

<img width="425" height="170" alt="servlet" src="https://github.com/user-attachments/assets/74fa7f05-ff40-4c85-a007-ab72265e7e53" />

### HTML form specifying "POST", catalog/access servlet entry point, and the value "logout" which will be evaluated in the switch case. (Note name="action". This is the variable in which the value attribute will be stored when its retrieved in our request object.)
<img width="1045" height="23" alt="functoin" src="https://github.com/user-attachments/assets/6d303725-d63a-48bd-9aee-9ff824e10242" />

### The controller that will be hit for all post requests directed to the access servlet. All the methods that are called respective to the action value are within the same servlet class.
<img width="917" height="461" alt="action" src="https://github.com/user-attachments/assets/681cc589-d8a6-4c24-a094-8b9863dc2430" />

### For instance, this logOut method will be invoked if action.equals("logout"). 
<img width="910" height="249" alt="snip" src="https://github.com/user-attachments/assets/8d89a517-99f4-42db-bd91-b70399c84c31" />

## init() and destroy() methods for persistence
<img width="716" height="325" alt="db" src="https://github.com/user-attachments/assets/4bb25daf-f8aa-401f-afbe-502b2ca2f3ba" />

The domain models which are intended for persistence are reloaded by using their respective manager classes. User objects are associated with their email and password, and the concept of registration requires a mechanism that 
saves their data across sessions so they can relog using private information. 

The email property is the bridge which associates users  with other types of data and enables a decoupled relationship. Therefore, the CartManager class can be persisted on its own while being decoupled from User objects- its entirely seperate
from them although it is logically related since the overriden .equals method renders the User's email property as their key (so both users and carts end up representing the same thing in a hashmap). By having made a design where sensitive data is accessed by having your email stored in session data once you have logged in
the CartManager class can be logically related to the users while decoupled as an object, and therefore it may associate transactions with the respective user accounts while remaining a seperate concern. 

The user/cart manager classes contain an array of all saved carts and users which is then serialized using IO streams into a folder called database. This is a provisional system not using JDBC just to demonstrate the structure of database logic, and learn about 
the patterns which are employed for persistent data management. 

## Lack of TDD significantly slowed down debugging process
After working on this project, it quickly became clear during the first hurdle (the nested hashmap design) that reloading the tomcat server continually was an extremely inefficient way of testing and iterating on the program.
For a domain this complex, especially in the context of a web app in which numerous branches of execution can be performed by site navigation patterns alone, a system of tests is necessary for truly evaluating the functionality and security of the program.
Various issues regarding the soundness of session state had to be troubleshooted by navigating the site in unpredictable patterns to create unintended data leaks. After editing the code this had to be replicated over and over. 

This also speaks to the fact that
the current design is insecure and overly complex especially on the basis of how difficult it is to test. This highlights the utility and necessity of modern backend frameworks which abstract away the details of the java servlet to create standardized systems with greater
security and easier testability (such as the Spring Framework). 

With standardizing security by handling lower level boilerplate patterns like login/registration logic, alongside DIP, IOC, and other security features for transactions etc, you are free to focus on the logic behind your program and outsource 
many of the concerns dealt with in this project. 
### Example of code pollution during testing
<img width="870" height="636" alt="codepollution" src="https://github.com/user-attachments/assets/e100c49a-b863-479f-ae28-0885b6dd1798" />

In the article shown above, I created a main method in the cartmanager class while trying to troubleshoot and understand how to implement the nested hashmap design which decoupled users objects from their cart item hashmaps. My initial patchwork quilt solution
stopped null pointer errors but only worked after a restart- I spent a long time creating useless functions and restarting tomcat to test my code before I came up with the aforementioned simpler solution. A lot of time could have been saved if I had a reliable testing framework- one fix often broke something else and I wouldn't recognize there was an error until much later- this experience was also very instructive as for the usefulness of a version control system. Without one, it may be impossible after a certain point to tell 
which change caused which side effect. 

### Conclusion
Having coded my own servlets was incredibly helpful for starting to appreciate modern solutions while giving me a greater understanding of what they may be doing under the hood. Reading about the role of the servlet container, configuring classpaths, and straining to 
implement designs has given me greater control and confidence with higher level frameworks that streamline these processes. 
