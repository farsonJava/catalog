# Servlets from Scratch
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
## Understanding the client model and HTTP to master the control flow of java servlet programs

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

### session object Vs. request object

<img width="764" height="437" alt="image6" src="https://github.com/user-attachments/assets/14dc0352-2cce-4439-a69b-383497ddb696" />

This is code taken from the loginAction method in the access servlet that's part of a series of conditional statements which is evaluating session data to prevent
users from being able to log in with stale session data after reverting back to the login page. The conditional statement in the article shown above is checking whether the 
session attributes are not null, and then whether they correspond to the request attribute data which was sent by the client using an HTML submission form.

If the session data and the request data correspond then the userToLogin check will be skipped and the client will gain access to the site and retain session
data in the form of their email without having to submit it again. At this stage in the program once the client is logged in, their session data becomes valuable
and sensitive in the context of the site as they are able to make purchases associated with that account on the basis of the session attribute storing their 
email- whenever the client hits the servlet endpoints availible from the main body of the site which requires a login, the subsequent servlet logic determines
which account is going to participate in the transaction based on the session "email" attribute. 

As such, it now becomes apparent that a pattern for storing data across requests after login has to include session attributes, and that session handling
across your application is a key security concern. 


