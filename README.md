# Employee App

An Employee Application that is used to manage the Employee Database Management System.
The application has these following scenarios:
1. Add (register) a new Employee
2. Update the employee details.
3. Retrieve employee information based on a given ID.
4. List all employee records.

Additionally, this particular application was designed in a way to support multiple jurisdictions.

The code itself designed with what so-called as `Hexagonal Architecture` (a.k.a. `Port and Adapters`).
The reason for choosing this particular code architecture because I'm looking to protect the business logic 
by encapsulating it within the `domain` layer regardless of its application interface or any infrastructure 
layers such as database, framework, etc.

## Prerequisites

- Java 17+
- Maven3+

## Running the Application with Maven

1. Compile the project by executing this following command from the project base directory
```shell
mvn clean package
```
2. Run the main starter
```shell
mvn exec:java -Dexec.mainClass="com.cercli.App"
```

## Runnin the Test

`mvn clean test`