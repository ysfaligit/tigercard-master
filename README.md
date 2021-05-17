# Tigercard

<h2>Project Guide & Reference : <a href="https://github.com/ysfaligit/tigercard-master/blob/main/TigerCard.pdf">
TigerCard.pdf</a></h2>
<legend>Tiger Card API records daily trip of user & calculates Daily & Weekly fares.
<br>

Project with SAMPLE DATA is deployed in HEROKU Cloud at : <a href="https://tigercard-sahaj.herokuapp.com/">https://tigercard-sahaj.herokuapp.com </a>
<br>
API Docs : https://tigercard-sahaj.herokuapp.com/swagger-ui.html
</legend>

<br>
<header>Zones</header>
<li>All Zones : <a href="https://tigercard-sahaj.herokuapp.com/zone/">https://tigercard-sahaj.herokuapp.com/zone/</a> </li>
<li><a href="https://tigercard-sahaj.herokuapp.com/zone/1">https://tigercard-sahaj.herokuapp.com/zone/1</a> </li>

<br>
<header>Rates</header>
<li>All Rates : <a href="https://tigercard-sahaj.herokuapp.com/rate/">https://tigercard-sahaj.herokuapp.com/rate/</a> </li>

<br>
<header>Cappings</header>
<li>All Cappings : <a href="https://tigercard-sahaj.herokuapp.com/capping/">https://tigercard-sahaj.herokuapp.com/capping/</a> </li>
<li>Capping from Zone 1 - Zone 2 : 
<a href="https://tigercard-sahaj.herokuapp.com/capping/1/2">https://tigercard-sahaj.herokuapp.com/capping/1/2 </a> 
</li>
</a> </li>
<li>Capping from Zone 1 - Zone 1 : 
<a href="https://tigercard-sahaj.herokuapp.com/capping/1/1">https://tigercard-sahaj.herokuapp.com/capping/1/1 </a> 
</li>
</a> </li>

<br>
<header>Tiger Card</header>
<li>All Cards Registered : <a href="https://tigercard-sahaj.herokuapp.com/card/">https://tigercard-sahaj.herokuapp.com/card/</a> </li>
<li>GET : <a href="https://tigercard-sahaj.herokuapp.com/card/1000">https://tigercard-sahaj.herokuapp.com/card/1000</a> </li>


<br>
<header>Ride Rules Defined </header>
<li>All Ride Rules : <a href="https://tigercard-sahaj.herokuapp.com/riderules/">https://tigercard-sahaj.herokuapp.com/riderules/</a> </li>

<br>
<header>Trips</header>
<li>Register Single Trip  : <a href="https://tigercard-sahaj.herokuapp.com/trip/save">https://tigercard-sahaj.herokuapp.com/trip/save</a> </li>
<li>Register Multiple Trips  : <a href="https://tigercard-sahaj.herokuapp.com/trip/saveAll">https://tigercard-sahaj.herokuapp.com/trip/saveAll</a> </li>
- Refer <a href="https://github.com/ysfaligit/tigercard-master/blob/main/testData/dailyTrips.json">dailyTrips.json</a> for input format.
<li>Get All Trips for Card  : <a href="https://tigercard-sahaj.herokuapp.com/trip/1000">https://tigercard-sahaj.herokuapp.com/trip/1000</a> </li>
<li>Get Trips Total for Card  : <a href="https://tigercard-sahaj.herokuapp.com/trip/1000/tripsTotal">https://tigercard-sahaj.herokuapp.com/trip/1000/tripsTotal</a> </li>

<br>
<header>Database</header>
<u>url</u> : <a href="https://tigercard-sahaj.herokuapp.com/h2-ui/login.jsp">https://tigercard-sahaj.herokuapp.com/h2-ui/login.jsp</a>
<br>
<u>Username</u> : sa
<br>
<u>Password</u> :

<br>
<h2>Tech Stack</h2>
<li>Java</li>
<li>Spring Boot</li> 
<li>Spring JPA</li>
<li>H2 In Memory Database</li>
<li> Ehachache as Cache Manager (15mins Cache)</li>
<li> SwaggerUI to generate REST Docs</li>

<br>

<h2>Steps of Deployment</h2>
<li><a href="https://www.java.com/en/download/help/download_options.html">Install Java</a>
<li><a href="https://maven.apache.org/install.html">Install Maven</a>
<li>Build & Run Project : <b>./mvnw clean package spring-boot:run</b></li>
<li>Once the project is up, pre-requisite data will be loaded in DB from 
<a href="https://github.com/ysfaligit/tigercard-master/blob/main/src/main/resources/data.sql">data.sql</a></li>
<li>Access Project at : <a href="http://localhost:8080">http://localhost:8080</a></li>
<li>API DOCS : <a href="http://localhost:8080/swagger-ui.html">http://localhost:8080/swagger-ui.html</a></li>

<h4>Run Test Cases</h4>
<li>Run : mvn test</li>


<h2>DB Details</h2>
After bringing up the application, <br>DB can be accessed @ : 
<a href="http://localhost:8080/h2-ui/login.jsp">http://localhost:8080/h2-ui/login.jsp</a>

<u>JDBC URL</u> : jdbc:h2:mem:tigercard
<br>
<u>Username</u> : sa
<u>Password</u> :

<br>

<h1>Integration Test Cases</h1>
<h3>testDailyCapReachedByCard:</h3>
    <p>This test case covers example illustrated at 
<b><a href="https://github.com/ysfaligit/tigercard-master/blob/main/TigerCard.pdf">
TigerCard.pdf : page - 4, Example 1. Daily cap reached</a></b>.

<br>
Steps of execution :
<li> Builds schema from <a href="https://github.com/ysfaligit/tigercard-master/blob/main/src/main/resources/data.sql">data.sql</a></li>
<li>Load data mentioned in example from file : 
<a href="https://github.com/ysfaligit/tigercard-master/blob/main/testData/dailyTrips.json">dailyTrips</a>
</li>
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
<li> Builds schema from <a href="https://github.com/ysfaligit/tigercard-master/blob/main/src/main/resources/data.sql">data.sql</a></li>
<li>Load data mentioned in example from file : 
<a href="https://github.com/ysfaligit/tigercard-master/blob/main/testData/weeklyTrips.json">weeklyTrips</a>
</li>
<li>Fetches the data using get method</li>
<li>Validates the output</li>


<br>

<h3>testZoneMethods</h3>
Steps of execution :
<li> Builds schema from <a href="https://github.com/ysfaligit/tigercard-master/blob/main/src/main/resources/data.sql">data.sql</a></li>
<li>Load data mentioned in example from file : 
<a href="https://github.com/ysfaligit/tigercard-master/blob/main/testData/zone.json">zone.json</a>
</li>
<li>Save Zones using  <u>PUT/zone</u> method </li>
<li>Fetches the data using <u>GET/zone</u> method</li>
<li>Validates the output</li>


<br>

<h3>testCappingMethods</h3>
Steps of execution :
<li> Builds schema from <a href="https://github.com/ysfaligit/tigercard-master/blob/main/src/main/resources/data.sql">data.sql</a></li>
<li>Load data mentioned in example from file : 
<a href="https://github.com/ysfaligit/tigercard-master/blob/main/testData/zone.json">zone.json</a>
</li>
<li>Save Zones using <u>PUT/zone</u> method </li>
<li>Fetches the data using <u>GET/zone</u> method</li>
<li>Validates the output</li>
<li>Use newly added ZONE above, in capping sample data.</li>
<li>Load data mentioned in example from file : 
<a href="https://github.com/ysfaligit/tigercard-master/blob/main/testData/capping.json">capping.json</a>
</li>
<li>Save Capping using <u>PUT/capping</u> </li>
<li>Fetches the data using <u>GET/capping</u></li>
<li>Validates the output</li>


<br>

<h3>testRateMethods</h3>
Steps of execution :
<li> Builds schema from <a href="https://github.com/ysfaligit/tigercard-master/blob/main/src/main/resources/data.sql">data.sql</a></li>
<li>Load data mentioned in example from file : 
<a href="https://github.com/ysfaligit/tigercard-master/blob/main/testData/zone.json">zone.json</a>
</li>
<li>Save Zones using <u>PUT/zone</u> method </li>
<li>Fetches the data using <u>GET/zone</u> method</li>
<li>Validates the output</li>
<li>Use newly added ZONE above, in capping sample data.</li>
<li>Load data mentioned in example from file : 
<a href="https://github.com/ysfaligit/tigercard-master/blob/main/testData/rate.json">rate.json</a>
</li>
<li>Save Capping using <u>PUT/rate</u> method </li>
<li>Fetches the data using <u>GET/rate</u> method</li>
<li>Validates the output</li>

<br>

<h3>testRideRuleMethods</h3>
Steps of execution :
<li> Builds schema from <a href="https://github.com/ysfaligit/tigercard-master/blob/main/src/main/resources/data.sql">data.sql</a></li>
<li>Load data mentioned in example from file : 
<a href="https://github.com/ysfaligit/tigercard-master/blob/main/testData/rideRule.json">rideRule.json</a>
</li>
<li>Save Capping using <u>PUT/riderules</u> method </li>
<li>Fetches the data using <u>GET/riderules</u> method</li>
<li>Validates the output</li>

