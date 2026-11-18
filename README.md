# Banking Management System – OOAD Part B

## Project Overview
A Java desktop banking application developed for the Object-Oriented Analysis and Design assignment. It allows customers to log in, view accounts, perform banking transactions and access transaction history.

## Technologies
- Java
- JavaFX
- Maven
- JDBC
- H2 Database
- Git and GitHub

## Features
- Customer login
- Savings, investment and cheque accounts
- Account balance viewing
- Deposits and supported withdrawals
- Transaction history
- Persistent database storage
- Automatic database initialisation and sample data

## Project Structure
- `model` – customer, account and transaction classes
- `view` – JavaFX user interface
- `controller` – application controllers
- `dao` – JDBC database operations
- `database` – database connection, schema and sample data

## Database
The application uses an embedded H2 database.

Tables:
- CUSTOMERS
- ACCOUNTS
- TRANSACTIONS

Database name: `bankingdb`  
Username: `sa`  
Password: blank

## Running the Application
Requirements: Java JDK and Maven.

Run:

    mvn clean compile
    mvn javafx:run

In a headless environment such as GitHub Codespaces, configure a virtual display before launching JavaFX.

## Sample Login
Customer ID: `C001`  
PIN: `1234`

## Sample Data
The database is populated with 10 sample customers and their associated accounts.

## GitHub Repository
https://github.com/kerunne-web/ooad
