# Spring Boot Multi-tenant application

### Step 1: mvn clean install compile -DskipTests
		- This will download all the dependencies required by the project and will build an executable jar.

### Step 2: Database Configuration
	
####	SQL Configs
		-> Update db configurations in AppConfig file
		-> While running first time we can set "spring.jpa.hibernate.ddl-auto=create" present in application.properties.
		   This will setup the db tables.
		
		
####	Mongo Configs:
		-> Update mongo related configs in application.properties
		-> When run for the first time collections will be created.
	
		
###	Step 3: Execution
		- If updating anything in the code, please repeat Step 1.
		- If executing via CMD:  Once latest build is done go to target and run below cmd:
			java -jar handson1-0.0.1-SNAPSHOT.jar
		- This will run the program and application will be started, the default application port is 5001. That we have set in application.properties.
		  Port can be modified.
		
#### 	API Endpoint:
		-> http://{host}:5001/metadata/{id} - Get API   	