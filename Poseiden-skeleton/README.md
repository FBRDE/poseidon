## Poseidon
The Java application **'Poseidon'** is run with Maven and Spring Boot, on server port 8080.

## Technical:

1. Framework: Spring Boot v2.7.0
2. Java 8
3. Thymeleaf
4. Bootstrap v.4.3.1
5. MySql 8.0.28
6. Spring Data Jpa
7. Spring security


## Installing

1.Install Java:

https://docs.oracle.com/javase/8/docs/technotes/guides/install/install_overview.html

2.Install Maven:

https://maven.apache.org/install.html

3.Install MySql:

https://dev.mysql.com/downloads/installer/


## Database

The file **data.sql** (available in *"doc/data.sql"*) contains scrypt SQL to create database with name "demo" as configuration in application.properties.

## Testing

- The app has unit tests written. You must launch 'mvn test' (all reports available in *"/target/surefire-reports"*).
- To test web app: Use port 8080: http://localhost:8080/

## Installing the application
To install the application, you must go to the "Poseiden-skeleton" folder with a command prompt and run the command: 'mvn install'

## Executing application
To run the application, you must go to the “Poseiden-skeleton” folder with a command prompt and run the command: ‘java -jar target/spring-boot-skeleton-0.0.1-SNAPSHOT.jar’
