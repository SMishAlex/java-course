# [Building Web Apps in Java](https://github.com/qala-io/java-course/blob/master/docs/programme/web-apps.md)
#### Servlet
* Servlet is [java interface](https://docs.oracle.com/javaee/6/api/javax/servlet/Servlet.html)
> A servlet is a small Java program that runs within a Web server. Servlets receive and respond to requests from Web clients, usually across HTTP
* Servlet is [part of Java EE](https://docs.oracle.com/javaee/5/tutorial/doc/bnafd.html)
* Servlet Life Cycle:
>The life cycle of a servlet is controlled by the container in which the servlet has been deployed. When a request is mapped to a servlet, the container performs the following steps.
> 
> 1. If an instance of the servlet does not exist, the web container
> 
>   * Loads the servlet class.
> 
>   * Creates an instance of the servlet class.
> 
>   * Initializes the servlet instance by calling the init method. Initialization is covered in Initializing a Servlet.
> 
> 2. Invokes the service method, passing request and response objects. Service methods are discussed in Writing Service Methods.
> 
> If the container needs to remove the servlet, it finalizes the servlet by calling the servlet’s destroy method. Finalization is discussed in Finalizing a Servlet.
#### Web.xml
The web.xml file provides [configuration and deployment information](https://docs.oracle.com/cd/E13222_01/wls/docs81/webapp/web_xml.html) for the web components that comprise a web application.

#### Filter
 * [The Java EE interface](https://tomcat.apache.org/tomcat-5.5-doc/servletapi/javax/servlet/Filter.html)
 * Basically can allow/deny access to the web resources, also can modify content or headers before and after processing. 
 
#### [Thread Pool Load Test](./step3)