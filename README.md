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




