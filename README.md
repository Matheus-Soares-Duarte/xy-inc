# xy-inc
<i>This app was designed to respond to the [Backend Developer Teste of ZUP](https://github.com/ZupIT/zup-test/tree/master/backend/pleno). The text below explains the project, its prerequisites, actions for its implementation and execution.</i> <br>


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


## Prerequisites:

* have TCP port 8080 free (changeable in properties file src> resources> application.properties). <br>
* have [Postgresql](https://www.postgresql.org/download/) installed, with a database named "xy-inc" created by user "user" that has the password "123", running on localhost on port 5432 (changeable in properties file src> resources> application.properties). <br>
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
