# Zomato Clone Backend

🚀 Key Features
Authentication & Security: Secure token-based JWT authentication with role-based access control (User/Admin).

Database & Persistence: Relational data mapping using Spring Data JPA.

Payment Gateway: Integrated with Razorpay for seamless checkout workflows.

Robust Error Handling: Global exception handling delivering structured, predictable JSON error payloads.

API Documentation: Built-in interactive API playground via OpenAPI 3 / Swagger UI.

Container Ready: Full Docker and Docker Compose multi-container support out of the box.


🛠️ Prerequisites
Ensure you have the following installed on your local environment:

Java 17 or higher

Maven 3.x (or use the included mvnw wrapper)

MySQL 8.x (if testing with a production-like database profile)

Docker & Docker Compose (Optional, for containerization)

⚙️ Configuration & Environment VariablesThe application relies on environment variables for configuration. A template file .env.example is provided in the root directory.Production Environment VariablesVariableDescriptionRequirement / DefaultDB_URLJDBC database connection string (e.g., jdbc:mysql://localhost:3306/tastybuddy)Required in ProdDB_USERNAMEDatabase usernameRequired in ProdDB_PASSWORDDatabase passwordRequired in ProdDB_DRIVER_CLASS_NAMEDatabase driver classcom.mysql.cj.jdbc.DriverJPA_DIALECTHibernate SQL Dialectorg.hibernate.dialect.MySQLDialectJPA_DDL_AUTOData definition structure strategyvalidate (Highly recommended for Prod)JWT_SECRETHS256/HS384/HS512 signing secret keyRequired (Must be a strong, random string)RAZORPAY_KEYRazorpay API Key IDRequired for paymentsRAZORPAY_SECRETRazorpay API Secret KeyRequired for payments⚠️ Production Security Notice:The application will safely reject startup in production mode if JWT_SECRET is left at the default placeholder value. Keep all real secrets out of source control. If Razorpay credentials are missing, payment endpoints will gracefully fall back to returning a 503 Service Unavailable status code.Optional / Development OverridesPORT: Port the server binds to (Defaults to 8080).JWT_EXPIRATION_MS: Token lifetime control.CORS_ALLOWED_ORIGINS: Comma-separated list of origins permitted to communicate with the API.APP_SEED_DATA: Set to true to auto-populate basic food catalogs on start.APP_SEED_ADMIN_EMAIL / APP_SEED_ADMIN_PASSWORD: Configures default admin bootstrap credentials.💻 Local Development Setup1. Quick Start (In-Memory / No DB Setup)For rapid local testing, you can run the application using the default profile which utilizes an in-memory repository stack and seeds data automatically.Copy .env.example to a new file named .env and fill in local adjustments if necessary.Run the application wrapper:Bash./mvnw.cmd spring-boot:run
The service will spin up by default at: http://localhost:8080💡 Default Bootstrap Admin Credentials:Username: admin@tastybuddy.localPassword: Admin@1232. Docker Compose Strategy (App + MySQL Containerized)To test the environment cleanly alongside an automated MySQL container infrastructure:Bashdocker compose up --build
🐳 Manual Docker DeploymentsTo package and run the application as a standalone single-container system:Bash# Build the image
docker build -t tastybuddy-backend .

# Run the container utilizing your environment file
docker run --env-file .env -p 8080:8080 tastybuddy-backend
🧪 Build and Test LifecycleExecute unit and integration tests across components:Bash./mvnw.cmd test
Compile, run tests, and package a production-optimized executable .jar file:Bash./mvnw.cmd clean package
The final build artifact will be generated within the target/ output folder.📖 API & Observability EndpointsOnce the application is running locally, you can use these core endpoints to verify and inspect the system state:System DiagnosticsHealth Checks: GET http://localhost:8080/actuator/healthSystem Diagnostics info: GET http://localhost:8080/actuator/infoAPI DocumentationSwagger Interactive UI: GET http://localhost:8080/swagger-ui.htmlOpenAPI specification JSON: GET http://localhost:8080/v3/api-docsCore Route Blueprint ReferencePublic/Authentication:POST /api/users/register — Create new accounts.POST /api/auth/login — Exchange credentials for an access token.Food Catalog:GET /api/foods — Browse all menu items.GET /api/foods/categories — Browse food taxonomies.GET /api/foods/{id} — Specific dish details.Cart & Ordering Workflow:PUT /api/cart/{id} — Manage items inside active sessions.DELETE /api/cart — Clear cart status.GET /api/orders/{id} — Check specific order state and checkout records.Administrative Actions:GET /api/admin/users — Fetch platform consumer listing.GET /api/admin/orders — View generalized cross-system logs.PATCH /api/admin/orders/{id}/status — Advance or modify order workflows.
