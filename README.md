# catalog
Simple backend servlets made from using the Tomcat Servlet container for studying server-side logic on a lower level.

This project was developed for educational purposes during the course of my studies on the inner workings of servlet containers,
the java-servlet api + HTTP, design patterns as well as common implementations of data structures in backend applications. 

The front end aspect of this project is minimal and serves only as a hook for implementing backend processes. Persistence 
is achieved in this project using file I/O streams in order to showcase the usage of init() and destroy() methods from
the java servlet api so far as they can be the points at which data is retrieved (on initialization) or written to a database
(on destroy). 

In order to run this application you need Tomcat 9+. 
