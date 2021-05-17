# Tigercard

<h2>Project Guide & Reference : <a href="https://github.com/ysfaligit/tigercard-master/blob/main/TigerCard.pdf">
TigerCard.pdf</a></h2>

Tech Stack <br>
<li>Java</li>
<li>Spring Boot</li> 
<li>Spring JPA</li>
<li>H2 In Memory Database</li>
<li> Ehachache as Cache Manager</li>
<li> SwaggerUI to generate REST Docs</li>

<br>

<h2>Steps of Deployment</h2>
<li><a href="https://www.java.com/en/download/help/download_options.html">Install Java</a>
<li><a href="https://maven.apache.org/install.html">Install Maven</a>
<li>Build & Run Project : <b>./mvnw clean package spring-boot:run</b></li>
<li>Access Project at : <a href="http://localhost:8080">http://localhost:8080</a></li>
<li>API DOCS : <a href="http://localhost:8080/swagger-ui.html">http://localhost:8080/swagger-ui.html</a></li>

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
