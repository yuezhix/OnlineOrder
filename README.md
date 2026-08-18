# OnlineOrder

OnlineOrder is a restaurant ordering web application built with Spring Boot, React, and PostgreSQL. Customers can register, sign in, browse restaurant menus, add food to a cart, and check out.

The compiled React application is included in `src/main/resources/public`, so Spring Boot serves both the web interface and REST API from the same application.

## Features

- Account registration and session-based login
- Public restaurant and menu browsing
- Customer-specific shopping carts
- Item quantity and cart total updates
- Cart and restaurant caching with Caffeine
- PostgreSQL persistence through Spring Data JDBC
- Executable JAR and Docker image builds

## Tech Stack

| Area | Technology |
|---|---|
| Web UI | React, Ant Design |
| Backend | Java 21, Spring Boot 4.1, Spring MVC |
| Security | Spring Security, JDBC user management |
| Database | PostgreSQL 15, Spring Data JDBC |
| Cache | Spring Cache, Caffeine |
| Build | Gradle, Docker |
| Test | JUnit 5, Mockito |

## Project Structure

<p align="center">
  <img src="./docs/onlineorder-architecture.svg?v=2" alt="OnlineOrder point-to-point architecture" width="100%">
</p>

| Layer | Responsibility |
|---|---|
| `controller` | Handles signup, menu, cart, and checkout requests. |
| `service` | Contains business rules, transactions, and cache operations. |
| `repository` | Reads and writes customers, restaurants, menus, carts, and order items. |
| `entity` | Maps Java records to database tables. |
| `model` | Defines request bodies and API response objects. |
| `resources/public` | Contains the packaged React application. |

Spring Security allows public access to static files, signup, login, logout, restaurants, and menus. Cart endpoints require an authenticated session. Passwords are encoded before they are stored.

Restaurant data and cart responses are cached for 60 seconds. Cart writes clear the affected cache entry so later reads return current data.

## API

| Method | Endpoint | Access | Description |
|---|---|---|---|
| `POST` | `/signup` | Public | Create a customer and an empty cart |
| `POST` | `/login` | Public | Start an authenticated session |
| `POST` | `/logout` | Public | End the current session |
| `GET` | `/restaurants/menu` | Public | List restaurants with menu items |
| `GET` | `/restaurant/{restaurantId}/menu` | Public | List one restaurant's menu |
| `GET` | `/cart` | Authenticated | Read the current customer's cart |
| `POST` | `/cart` | Authenticated | Add a menu item to the cart |
| `POST` | `/cart/checkout` | Authenticated | Clear the current cart |

## Run Locally

Requirements:

- Java 21
- Docker Desktop

Start PostgreSQL:

```bash
docker compose up -d db
```

Start the application:

```bash
./gradlew bootRun
```

Open [http://localhost:8080](http://localhost:8080).

### Configuration

| Variable | Default |
|---|---|
| `DATABASE_URL` | `localhost` |
| `DATABASE_PORT` | `5432` |
| `DATABASE_USERNAME` | `postgres` |
| `DATABASE_PASSWORD` | `secret` |
| `INIT_DB` | `always` |

`database-init.sql` creates the schema and sample restaurant data. Set `INIT_DB=never` when the database should not be recreated at startup.

## Test and Build

Run the tests:

```bash
./gradlew test
```

Build the executable JAR:

```bash
./gradlew bootJar
```

The JAR is written to `build/libs/OnlineOrder-0.0.1-SNAPSHOT.jar`.

Build the Docker image:

```bash
docker build -t onlineorder:latest .
```

## Current Scope

Checkout clears the active cart. Payment processing, order history, and delivery tracking are not included.
