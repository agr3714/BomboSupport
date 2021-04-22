# Bombo Ticket Support System

Author: Audrey Rovero

Program for the Bombo Ticket Support System for SandBox Union coding project.

## About Program

The Bombo Ticket Support System was made using Java and MySql.
GUI was made using Java Swing.
Java JDBC was used to connect to and execute queries for the database.
Java files were written and tested using Visual Studio Code.
MySQL Workbench was used to create the database.

For the email feature in the application, Java Mail (mail.jar, most recent) and smpt.gmail were used. Credentials for
the sending email are located in SendEmail.java.

The workspace contains 3 folders, where:

- `src`: contains the java source files
- `lib`: the folder for dependencies
- `sql`: the folder containing the sql tables of the database

## Installation

1. Make sure that mail.jar (Java Mail) and mySql-connector-java.jar libraries are included (in Visual Studio Code include in Referenced Libraries).
2. If using Visual Studio use Run Java on Bombo.java
3. Otherwise use: javac *.java to compile, java Bombo to run

#### Library Links
- [Java Mail](https://www.oracle.com/java/technologies/javamail-api.html) 
- [MySQL Java JDBC connector - Connecter/J](https://www.mysql.com/products/connector/)

## Features

The Bombo Ticket Support System supports the following features:
- Creating a new client or employee account with username, password, and email
- Logging into a client or employee account with username and password
- Creating a new help ticket as a client, help tickets have a max length of 500.
- Selecting and updating tickets as a employee, who can change status of ticket and make comment (max length 200).
- Updating a ticket as an employee will send an email to the client involved

### Email Feature

 Emails are done in SendEmail.java using Java Mail and are sent from the gmail account bombohelp01.
 Since the sender email information is available in SendEmail.java it can be edited for ease of testing. 

### Accounts
 Both client and employee accounts are stored in user table in db and can only be accessed with correct username and password input.
 Usernames must be unique and passwords are never shown in the application, thus providing a layer of security for user information since
 only username and email will be shown when employees assign themselves to a ticket.

 Some available accounts to test with are:
 - Client: username: bClient, password: pass03, email: bombohelp01
 - Employee: username: aEmployee, password: pass02





## Database Information

The database, bongodb, was created using MySql Workbench and contains 4 tables, which are:
- the user table, which contains information about a client or employee, such as username, password and email
- the user_type table, which contains information on the different user types, as of now the two types are client and employee
- the ticket table, which contains all tickets that have been submitted by clients
- the ticket_status table , which contains information on the different ticket status types, as of now the status types are in progess and complete

The 4 tables mentioned above are located in the sql folder.

DBConnection.java handles connecting to the database and all sql queries. 
db.properties contains the user, password and url information for the database








