# Tigercard

Tech Stack <br>
    <li> Java, Spring Boot, 
    <li>Spring JPA
    <li>H2 In Memory Database
    <li> Ehachache as Cache Manager
    <li> SwaggerUI to generate REST Docs


<br>

<h1>Steps of Deployment</h1>
<li> Install Java : https://www.java.com/en/download/help/download_options.html
<li> Install Maven : https://maven.apache.org/install.html
<li>Build Project by executing : mvn clean package</li>
<li>Run : java -jar target/tigercard-master-0.0.1-SNAPSHOT.jar</li>
<li>Access Project at : <a href="http://localhost:8080">http://localhost:8080</a></li>
<li>API DOCS : http://localhost:8080/swagger-ui.html</li>

<h4>Run Test Cases</h4>
<li>Run : mvn test</li>


<h1>DB Details</h1>
After bringing up the application, <br>DB can be accessed @ : 
<a href="http://localhost:8080/h2-ui/login.jsp">http://localhost:8080/h2-ui/login.jsp</a>

<u>JDBC URL</u> : jdbc:h2:mem:tigercard
<br>
<u>Username</u> : sa
<u>Password</u> : 

<br>

<h1>Test Cases</h1>
<h3>testDailyCapReachedByCard:</h3>
    <p>This test case covers example illustrated at <b>TigerCard.pdf : page - 4, Example 1. Daily cap reached</b>.

<br>
Steps of execution :
<li> Builds schema</li>
<li>Load data mentioned in example</li>
<li>Fetches the data using get method</li>
<li>Validates the output</li>

<br>

<h3>testWeeklyCapReachedByCard</h3>
<p>This test case covers example illustrated at 
<b><a href="https://github.com/ysfaligit/tigercard-master/blob/main/TigerCard.pdf">
TigerCard.pdf : page - 4, Example 2. Weekly cap reached
</a></b>.
<br>
Steps of execution :
<li> Builds schema</li>
<li>Load data mentioned in example</li>
<li>Fetches the data using get method</li>
<li>Validates the output</li>
