🛒 Online Shopping Backend

Spring Boot backend for an online shopping system with secure authentication, product and order management, and admin analytics exposed via REST APIs.

## Tech Stack
- Java 17
- Spring Boot
- Spring Security + JWT
- Spring Data JPA
- H2 in-memory database
- Swagger / OpenAPI
- Maven (wrapper included)

## Features
**Authentication & Security**
- User registration and login
- JWT-based authentication
- Role-based access control (ADMIN/USER)
- Secured APIs using Spring Security

**Product Management**
- Admin can add and delete products
- Users can view the product list
- Pagination supported

**Order Management**
- Logged-in users can place orders
- Users can view their order history
- Orders are linked to users

**Admin Analytics**
- View total number of orders
- View total revenue
- Restricted to ADMIN role

**API Documentation**
- Integrated Swagger UI
- JWT authorization supported in Swagger UI

## Project Structure
```
controller/   REST APIs
service/      Business logic
repository/   Database access
model/        Entities
dto/          Request/Response objects
config/       Security & Swagger config
exception/    Global exception handling
util/         JWT utilities
```

## Getting Started
**Prerequisites**
- Java 17
- Git

**Clone and Run**
```bash
git clone https://github.com/Avishkar74/Online_Shopping_Backend.git
cd Online_Shopping_Backend
./mvnw clean spring-boot:run
# On Windows PowerShell you can also run:
# .\mvnw.cmd clean spring-boot:run
```

Application runs at: http://localhost:8080

## API Documentation
- Swagger UI: http://localhost:8080/swagger-ui.html

## H2 Database Console
- Console: http://localhost:8080/h2-console
- JDBC URL: `jdbc:h2:mem:testdb`
- Username: `sa`
- Password: *(empty)*

## Quick Testing Flow
1) Register a user
2) Login to get a JWT token
3) Authorize in Swagger UI (Authorize button) 🔒
4) Create or view products
5) Place orders
6) View analytics (ADMIN only)

## Build & Test
```bash
./mvnw test
```

## Contributing
- Create a branch for your change (e.g., `add-readme`)
- Run the app/tests locally
- Open a Pull Request with a short summary of changes

## License
Specify the project license here (e.g., MIT) or add a `LICENSE` file.