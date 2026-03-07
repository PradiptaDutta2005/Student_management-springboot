Student Management System (Spring Boot + Angular)

A full-stack Student Management System built with Spring Boot, Spring Security (JWT), PostgreSQL, and Angular.
The system provides role-based access for Admin, Teacher, and Students with secure authentication.

Project Links
Frontend Repository

Frontend built with Angular + Tailwind CSS

🔗 https://github.com/PradiptaDutta2005/Student_management-frontend

API Documentation

Complete API documentation available on Postman

🔗 https://documenter.getpostman.com/view/43583592/2sBXcKAce1

System Architecture
Angular Frontend
│
│ REST API
▼
Spring Boot Backend
│
Spring Security + JWT
│
▼
PostgreSQL Database
Features
Authentication

JWT based authentication

Secure login system

BCrypt password encryption

Admin

Register teachers

Register students

Create subjects

Assign teachers to subjects

View users

Teacher

View assigned subjects

View students

Mark attendance

Upload study notes

View uploaded notes

Student

View attendance

Download study materials

Tech Stack

Spring Boot

Spring Security

JWT

Spring Data JPA

PostgreSQL

Lombok

Frontend

Angular

Tailwind CSS

Tools

Maven

Postman

Git

IntelliJ IDEA

VS Code

Security

JWT authentication is used for all protected endpoints.

Authorization rules:

/auth/**      → Public
/admin/**     → ADMIN
/teacher/**   → TEACHER
/student/**   → STUDENT

All requests require:

Authorization: Bearer <JWT_TOKEN>
Running Server
Clone repository
git clone https://github.com/PradiptaDutta2005/Student_management-springboot
Configure database

Update application.properties

spring.datasource.url=jdbc:postgresql://localhost:5432/studentdb
spring.datasource.username=postgres
spring.datasource.password=yourpassword
Run application
mvn spring-boot:run

Server runs on:

http://localhost:8080