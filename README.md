🎓 Student Management System

A full-stack Student Management System built with Spring Boot, Spring Security (JWT), PostgreSQL, and Angular.

The system provides secure authentication and role-based access control for Admin, Teacher, and Students.

🚀 Project Links
🌐 Frontend Repository

Frontend built with Angular + Tailwind CSS

🔗 https://github.com/PradiptaDutta2005/Student_management-frontend

📘 API Documentation

Complete API documentation available on Postman

🔗 https://documenter.getpostman.com/view/43583592/2sBXcKAce1

🏗 System Architecture
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
✨ Features
🔐 Authentication

JWT based authentication

Secure login system

BCrypt password encryption

Role based access control

👨‍💼 Admin Features

Register teachers

Register students

Create subjects

Assign teachers to subjects

View users

👨‍🏫 Teacher Features

View assigned subjects

View students

Mark attendance

Upload study notes

View uploaded notes

👨‍🎓 Student Features

View attendance

Download study materials

🛠 Tech Stack
Backend

Spring Boot

Spring Security

JWT Authentication

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

🔐 Security

JWT authentication is used for all protected endpoints.

Authorization Rules
/auth/**      → Public
/admin/**     → ADMIN
/teacher/**   → TEACHER
/student/**   → STUDENT

All protected requests must include:

Authorization: Bearer <JWT_TOKEN>
⚙️ Running the Project
1️⃣ Clone the Repository
git clone https://github.com/PradiptaDutta2005/Student_management-springboot
2️⃣ Configure Database

Update application.properties

spring.datasource.url=jdbc:postgresql://localhost:5432/studentdb
spring.datasource.username=postgres
spring.datasource.password=yourpassword
3️⃣ Run the Application
mvn spring-boot:run
🌐 Server

The backend server will start at:

http://localhost:8080
📸 Project Screenshots

Add screenshots here once the UI is ready.

Example:

docs/screenshots/login.png
docs/screenshots/dashboard.png
docs/screenshots/admin-panel.png
📂 Project Structure
student-management-springboot
│
├── controller
├── service
├── repository
├── entity
├── dto
├── config
├── security
│
└── application.properties