🛒 Online Shopping Backend

A Spring Boot–based backend application for an online shopping system, implementing secure authentication, product management, order processing, and admin analytics using REST APIs.

🚀 Tech Stack

Java 17

Spring Boot

Spring Security + JWT

Spring Data JPA

H2 In-Memory Database

Swagger / OpenAPI

Maven

📌 Features Implemented
🔐 Authentication & Security

User registration and login

JWT-based authentication

Role-based access control (ADMIN / USER)

Secured APIs using Spring Security

🛍 Product Management

Admin can add and delete products

Users can view product list

Pagination supported

📦 Order Management

Logged-in users can place orders

Users can view their order history

Orders linked to users

📊 Admin Analytics

View total number of orders

View total revenue

Accessible only by ADMIN

📄 API Documentation

Integrated Swagger

All APIs testable via browser

JWT authorization supported in Swagger UI

🗂 Project Structure
controller/   → REST APIs
service/      → Business logic
repository/   → Database access
model/        → Entities
dto/          → Request/Response objects
config/       → Security & Swagger config
exception/    → Global exception handling
util/         → JWT utilities

▶️ How to Run the Project
./mvnw clean spring-boot:run


Application runs on:

http://localhost:8080

🔍 Swagger API Documentation

Access Swagger UI:

http://localhost:8080/swagger-ui.html

🗄 H2 Database Console
http://localhost:8080/h2-console


JDBC URL: jdbc:h2:mem:testdb
Username: sa
Password: (empty)

🧪 Testing Flow (Quick)

Register user

Login to get JWT token

Authorize using Swagger 🔒

Create/View products

Place orders

View analytics (ADMIN only)