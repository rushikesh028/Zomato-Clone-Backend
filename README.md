# Zomato Clone Backend

✨ Key Features
🔐 Authentication & Security
JWT-based Authentication
Role-Based Access Control (USER / ADMIN)
Secure Password Encryption
Protected REST APIs
🗄️ Database & Persistence
Spring Data JPA
Hibernate ORM
MySQL Database Support
Entity Relationship Mapping
💳 Payment Integration
Razorpay Payment Gateway
Secure Checkout Workflow
Payment Verification Support
⚠️ Error Handling
Global Exception Handling
Structured JSON Error Responses
Consistent API Error Format
📄 API Documentation
OpenAPI 3 Integration
Swagger UI Support
Interactive API Testing
🐳 Containerization
Docker Support
Docker Compose Support
Multi-Container Deployment
🛠️ Tech Stack
Category	Technology
Language	Java 17
Framework	Spring Boot
Security	Spring Security, JWT
ORM	Hibernate, JPA
Database	MySQL
Build Tool	Maven
Payment Gateway	Razorpay
Documentation	Swagger/OpenAPI
Containerization	Docker, Docker Compose
📋 Prerequisites

Install the following before running the project:

Java 17+
Maven 3.x
MySQL 8.x
Docker (Optional)
Docker Compose (Optional)
⚙️ Environment Variables

Create a .env file using .env.example.

Production Variables
Variable	Description
DB_URL	Database JDBC URL
DB_USERNAME	Database Username
DB_PASSWORD	Database Password
DB_DRIVER_CLASS_NAME	MySQL Driver Class
JPA_DIALECT	Hibernate Dialect
JPA_DDL_AUTO	Schema Strategy
JWT_SECRET	JWT Signing Secret
RAZORPAY_KEY	Razorpay Key ID
RAZORPAY_SECRET	Razorpay Secret Key
Recommended Values
DB_DRIVER_CLASS_NAME=com.mysql.cj.jdbc.Driver
JPA_DIALECT=org.hibernate.dialect.MySQLDialect
JPA_DDL_AUTO=validate
Optional Variables
Variable	Default
PORT	8080
JWT_EXPIRATION_MS	Custom
CORS_ALLOWED_ORIGINS	Custom
APP_SEED_DATA	false
APP_SEED_ADMIN_EMAIL	Optional
APP_SEED_ADMIN_PASSWORD	Optional
🔒 Security Notes
Never commit secrets to GitHub.
Use a strong JWT secret.
Store credentials in environment variables.
Application startup will fail if JWT secret uses a placeholder value.
Missing Razorpay credentials return 503 Service Unavailable for payment APIs.
💻 Running Locally
1️⃣ Quick Start

Run directly using Maven:

./mvnw spring-boot:run

Application URL:

http://localhost:8080
Default Admin Account
Email: admin@tastybuddy.local
Password: Admin@123
🐳 Docker Compose Setup

Run application with MySQL:

docker compose up --build
🐳 Manual Docker Deployment
Build Docker Image
docker build -t tastybuddy-backend .
Run Docker Container
docker run --env-file .env -p 8080:8080 tastybuddy-backend
🧪 Build & Test
Run Tests
./mvnw test
Create Production Build
./mvnw clean package

Generated JAR:

target/
📖 API Documentation
Swagger UI
GET /swagger-ui.html

Open:

http://localhost:8080/swagger-ui.html
OpenAPI JSON
GET /v3/api-docs
📊 Monitoring & Health Checks
Health Endpoint
GET /actuator/health
Application Info
GET /actuator/info
🔑 Authentication APIs
Register User
POST /api/users/register
Login
POST /api/auth/login

Returns JWT Access Token.

🍕 Food Catalog APIs
Get All Foods
GET /api/foods
Get Categories
GET /api/foods/categories
Get Food By ID
GET /api/foods/{id}
🛒 Cart APIs
Update Cart
PUT /api/cart/{id}
Clear Cart
DELETE /api/cart
📦 Order APIs
Get Order Details
GET /api/orders/{id}
👨‍💼 Admin APIs
Get All Users
GET /api/admin/users
Get All Orders
GET /api/admin/orders
Update Order Status
PATCH /api/admin/orders/{id}/status
📂 Project Structure
src
 ├── controller
 ├── service
 ├── repository
 ├── entity
 ├── dto
 ├── security
 ├── exception
 ├── config
 └── util

docker-compose.yml
Dockerfile
pom.xml
README.md
