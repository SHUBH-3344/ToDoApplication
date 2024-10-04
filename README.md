# ToDo List Application

A simple and intuitive ToDo List application built with Spring Boot and a MySQL database. This application allows users to create, manage, and delete tasks efficiently.

## Features

- User registration and authentication
- Create, read, update, and delete tasks
- Mark tasks as completed
- User-friendly interface

## Technologies Used

- Java
- Spring Boot
- Spring Security
- MySQL
- HikariCP (for connection pooling)
- Lombok (for reducing boilerplate code)
- Maven (for dependency management)

## Installation

### Prerequisites

- Java 17 or later
- MySQL server installed
- Maven installed

### Steps

1. Clone the repository:
   
   git clone https://github.com/SHUBH-3344/ToDOApplication.git
   cd ToDOApplication.

2. Set up the database:
Create a database named todoapp.
Update the application.properties file with your database credentials.

spring.datasource.url=jdbc:mysql://localhost:3306/todoapp
spring.datasource.username=your_username
spring.datasource.password=your_password

3. Build and run the application:
   mvn clean install

4. java -jar target/ToDoListApplication-0.0.1-SNAPSHOT.jar

Usage
Navigate to http://localhost:8080 in your web browser.
Register a new user account.
Log in with your credentials.
Start adding, managing, and organizing your tasks!

API Endpoints
POST /api/register: Register a new user
POST /api/authenticate: Authenticate a user and receive a JWT token
GET /api/tasks: Retrieve all tasks for the logged-in user
POST /api/tasks/addTask: Create a new task
PUT /api/tasks/{id}: Update an existing task
DELETE /api/tasks/{id}: Delete a task