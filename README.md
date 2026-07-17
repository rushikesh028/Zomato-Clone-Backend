# TastyBuddy Backend

Spring Boot backend for TastyBuddy with JWT authentication, validation, structured JSON errors, JPA persistence, and Razorpay payment integration.

## Run locally

1. Copy `.env.example` values into your environment.
2. For an in-memory local run, you can leave the database variables unset.
3. The app seeds a small food catalog by default for local usage.
4. Local seed data also creates an admin account by default:
   `admin@tastybuddy.local` / `Admin@123`
5. Start the app:

```powershell
./mvnw.cmd spring-boot:run
```

The service starts on `http://localhost:8080` by default.

Core local endpoints:

- `GET /actuator/health`
- `GET /api/foods`
- `POST /api/users/register`
- `POST /api/auth/login`
- `GET /api/users/me`
- `GET /swagger-ui.html`
- `GET /v3/api-docs`
- `GET /api/foods/categories`
- `GET /api/foods/{id}`
- `PUT /api/cart/{id}`
- `DELETE /api/cart`
- `GET /api/orders/{id}`
- `GET /api/admin/users`
- `GET /api/admin/orders`
- `PATCH /api/admin/orders/{id}/status`

## Production environment variables

Required for production:

- `DB_URL`
- `DB_USERNAME`
- `DB_PASSWORD`
- `DB_DRIVER_CLASS_NAME=com.mysql.cj.jdbc.Driver`
- `JPA_DIALECT=org.hibernate.dialect.MySQLDialect`
- `JPA_DDL_AUTO=validate`
- `JWT_SECRET`

The app rejects production-like startup when `JWT_SECRET` is left at the built-in placeholder. Use a long random value and keep it out of source control.

Required if payment APIs are used:

- `RAZORPAY_KEY`
- `RAZORPAY_SECRET`

If Razorpay credentials are missing, payment endpoints stay unavailable and return `503 Service Unavailable`.

Optional:

- `PORT`
- `JWT_EXPIRATION_MS`
- `CORS_ALLOWED_ORIGINS`
- `JPA_DDL_AUTO`
- `APP_SEED_DATA`
- `APP_SEED_ADMIN_EMAIL`
- `APP_SEED_ADMIN_PASSWORD`
- `FLYWAY_BASELINE_ON_MIGRATE`

## Build and test

```powershell
./mvnw.cmd test
./mvnw.cmd clean package
```

The packaged artifact is created under `target/`.

## Health checks

- `GET /actuator/health`
- `GET /actuator/info`

## API documentation

- Swagger UI: `GET /swagger-ui.html`
- OpenAPI JSON: `GET /v3/api-docs`

## Docker

Build and run:

```powershell
docker build -t tastybuddy-backend .
docker run --env-file .env -p 8080:8080 tastybuddy-backend
```

Or start the app and MySQL together:

```powershell
docker compose up --build
```
