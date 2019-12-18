# xy-inc
<i>This app was designed to respond to the [ZUP Backend Developer Test](https://github.com/ZupIT/zup-test/tree/master/backend/pleno). The text below explains the project, its prerequisites, actions for its implementation and execution.</i> <br>


## About the project:

This project was developed, following the request, in Kotlin language. Some applications, frameworks and pluggins were used in the development process, like: [Git](https://git-scm.com/), [GitHub](https://github.com/), [Junit](https://junit.org/junit4/), [Maven](https://maven.apache.org/), [Maven Surefire](https://maven.apache.org/surefire/index.html), [Mockito](https://site.mockito.org), [Postgresql](https://www.postgresql.org/download/), [Postman](https://www.getpostman.com/) and [Spring-Boot](https://spring.io/projects/spring-boot). To create an API that resolves the order in the [ZUP Backend Developer Test](https://github.com/ZupIT/zup-test/tree/master/backend/pleno).


## Prerequisites:

* have TCP port 8080 free (changeable in properties file src>resources>application.properties). <br>
* have [Postgresql](https://www.postgresql.org/download/) installed, with a database named "xy-inc" created by user "user" that has the password "123", running on localhost on port 5432 (changeable in properties file src>resources>application.properties). <br>
* have [JDK version 8](https://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html) installed, with your environment variables properly configured (JAVA_HOME). <br>
* have [Maven](https://maven.apache.org/download.cgi) installed, with your environment variables properly configured (MAVEN_HOME). <br>


## Installation and Execution:

* First make sure you are meeting the prerequisites described above. <br>

* From the terminal enter the folder where you want to clone this project, then type the following command: <br>
  ``` $ git clone https://github.com/Matheus-Soares-Duarte/xy-inc.git ``` <br>
  
* At the root of the cloned directory run: <br>
  ``` $ mvn clean install ``` <br>

* Then, to run the application, run the command: <br>
  ``` $ mvnw spring-boot:run ``` <br>

* Now you can open an HTTP client and run the services of this program as explained and exemplified in the Actions section. <br>


## Actions:
In this project, there are four possible actions, all related to points of interest (POIs): <br>

* <b> Find: </b> to find and display information at a point of interest already registered in the database that has the requested data. For your request, you must pass the attributes: POI Name, X Coordinate, and Y Coordinate. You cannot search for the POI with blank name or negative or non-integer coordinates.
  * <b>HTTP Request Method: </b> GET.
  * <b>Request Example (application running on localhost:8080):</b> <br>
    * <b>Situation:</b> Find the point of interest called "Lanchonete" at the coordinates (27, 12). <br>
    * <b>URL: </b> ``` http://localhost:8080/poi/find?name=Lanchonete&x=27&y=12 ``` <br><br>
    
* <b> Find All: </b> to find and display the information for all points of interest registered in the database.
  * <b>HTTP Request Method: </b> GET.
  * <b>Request Example (application running on localhost:8080):</b> <br>
    * <b>Situation:</b> Find all the points of interest in the database. <br>
    * <b>URL: </b> ``` http://localhost:8080/poi ``` <br><br>

* <b> Find For Proximity: </b> to find and display information of points of interest already recorded in the database that are between the entered coordinates and the maximum distance entered. For your request, you need to pass the attributes: X coordinate, Y coordinate, and maximum distance. You cannot search for POI with coordinates or maximum distance negative or non-integer.
  * <b>HTTP Request Method: </b> GET.
  * <b>Request Example (application running on localhost:8080):</b> <br>
    * <b>Situation:</b> Find the points of interest within 10 meters at the coordinates (20, 10). <br>
    * <b>URL: </b> ``` http://localhost:8080/poi/findForProximity?x=20&y=10&maxDistance=10 ``` <br><br>

* <b> Insert: </b> to record points of interest, this request need 3 attributes: POI Name, X coordinate (non negative integer) and Y coordinate (non negative integer). Duplicate POIs cannot be registered, that is to say, POIs with the same name and coordinates as any one POIs stored in the database. It is also not possible to register POIs with blank names or coordinates negative or non-integer. If no coordinate is entered, they will have a value set to 0 by default.
  * <b>HTTP Request Method: </b> POST.
  * <b>Request Example (application running on localhost:8080):</b> <br>
    * <b>Situation:</b> Insert a point of interest called "Lanchonete" at the coordinates (27, 12). <br>
    * <b>URL: </b> ``` http://localhost:8080/poi ``` <br>
    * <b>BODY: </b> 
    ``` 
    {
        "name": "Lanchonete",
        "x": 27,
        "y": 12
    }
    ``` 
    <br><br>

## Automated Tests:

For this project unit tests were done using [JUnit 4](https://github.com/junit-team/junit4/wiki/Download-and-Install) and [Mockito](https://site.mockito.org). Using [Maven Surefire](https://maven.apache.org/surefire/index.html) to execute them in parallel (in this application we use 3 threads).<br><br>
15 tests were performed for the PointOfInterest Services class, as follows:
 * 5 for the Find function:
  * correct search returning POI = `function find should find point of interest`
  * search for unregistered POI = `function find should not find point of interest when not found requested element`
  * search POI with negative coordinates = `function find should not find point of interest when the entered coordinates have negative values`
  * search POI with non-integer coordinates = `function find should not find point of interest when the entered coordinates have non integer values`
  * search POI with blank name = `function find should not find point of interest when the entered name is blank`

* 2 for the FindAll function:
  * correct database element search = `function findAll should find all point of interest in database`
  * empty database search = `function findAll should not find point of interest when database is empty`

* 4 for the FindForProximity function:
  * correct search by returning POIs =  `function findForProximity should find the points of interests`
  * negative values search = `function findForProximity should not find point of interest when the entered have negative values`
  * non-integer values search = `function findForProximity should not find point of interest when the entered have non integers values`
  * correct search without return of POIs = `function findForProximity should not find points of interest near when they are not found elements consistent with those requested`

* 4 for the Insert function
  * correct insertion of POI = `function insert should insert point of interest`
  * insertion of a POI already registered in the database = `function insert should not insert point of interest when found requested element`
  * POI with negative coordinate insertion = `function insert should not insert point of interest when the entered coordinates have negative values` 
  * POI with blank name insertion = `function insert should not insert point of interest when the entered name is blank`

<i>Some cases, such as insertion with non-integer coordinates, have not been tested because spring boot already returns the appropriate exception when trying to convert types to application call.</i> <br>
