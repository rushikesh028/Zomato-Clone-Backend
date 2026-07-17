# Zomato Clone Backend 🚀

A production-ready Spring Boot backend engine mimicking core food-delivery functionalities like Zomato. It features secure JWT authentication, strict request validation, structured JSON error handling, JPA persistence, and integration with the Razorpay payment gateway.

---

## ✨ Key Features

*   **🔐 Authentication & Security:** 
    *   JWT-based Authentication
    *   Role-Based Access Control (`USER` / `ADMIN`)
    *   Secure Password Encryption
    *   Protected REST APIs
*   **🗄️ Database & Persistence:** 
    *   Spring Data JPA & Hibernate ORM
    *   MySQL Database Support
    *   Entity Relationship Mapping
*   **💳 Payment Integration:** 
    *   Razorpay Payment Gateway Integration
    *   Secure Checkout Workflow
    *   Payment Verification Support
*   **⚠️ Error Handling:** 
    *   Global Exception Handling
    *   Structured JSON Error Responses
    *   Consistent API Error Format
*   **📄 API Documentation:** 
    *   OpenAPI 3 Integration
    *   Swagger UI Support for Interactive API Testing
*   **🐳 Containerization:** 
    *   Docker & Docker Compose Support
    *   Multi-Container Deployment Ready

---

## 🛠️ Tech Stack

| Category | Technology |
| :--- | :--- |
| **Language** | Java 17 |
| **Framework** | Spring Boot |
| **Security** | Spring Security, JWT |
| **ORM** | Hibernate, JPA |
| **Database** | MySQL |
| **Build Tool** | Maven |
| **Payment Gateway** | Razorpay |
| **Documentation** | Swagger / OpenAPI 3 |
| **Containerization**| Docker, Docker Compose |

---

## 📋 Prerequisites

Ensure you have the following installed before running the project:
*   **Java 17** or higher
*   **Maven 3.x** (or use the included `mvnw` wrapper)
*   **MySQL 8.x**
*   **Docker & Docker Compose** (Optional, for containerization)

---

## ⚙️ Environment Variables

Create a `.env` file in the root directory using the template provided in `.env.example`.

### Production Variables

| Variable | Description |
| :--- | :--- |
| `DB_URL` | Database JDBC URL (e.g., `jdbc:mysql://localhost:3306/zomato_clone`) |
| `DB_USERNAME` | Database Username |
| `DB_PASSWORD` | Database Password |
| `JWT_SECRET` | Secret key for signing JWT tokens (Must be a strong, random string) |
| `RAZORPAY_KEY` | Razorpay API Key ID |
| `RAZORPAY_SECRET` | Razorpay API Secret Key |

---

## 💻 Local Setup & Deployment

### Method 1: Using Maven Wrapper
1. Set up your environment variables in the `.env` file.
2. Build the project:
   ```bash
   ./mvnw clean package

Run the application:
./mvnw spring-boot:run
   
