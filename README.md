# UBookIt - Textbook Delivery Tracking (Backend)

## Project Description

The Greek academic textbook distribution platform **Eudoxus** handles textbook declarations for university students, but it lacks a delivery option — students must physically collect books from partnered bookstores. This creates problems for those who work, live far from campus, or lack personal transport.

**UBookIt** extends the Eudoxus workflow by adding a delivery tracking service. After declaring their textbooks, students can opt to have them delivered to their home or a nearby locker, bundled together, and track the delivery in real time.

This project is part of the group assignment for the **"Information Systems Development and Architecture"** course at Athens University of Economics and Business.

## Use Case: Track an Order

From the group project's use case diagram, the system includes four use cases: *See list of books*, *Select books for delivery*, *Pay for order / Complete order*, and *Track an Order*. This application implements the **"Track an Order"** use case.

**Actor:** Student (signed-in user who wants to track their textbook delivery)

**Preconditions:**
- The user is signed in
- The user has at least one placed and paid order

**Main Flow:**
1. The user signs in to the system
2. The system displays a list of the user's orders (order ID, status, bookstore, city, price)
3. The user selects a specific order to track
4. The system returns detailed tracking information: pickup bookstore, delivery destination, driver's live position on a map, ETA, progress percentage, and the route as map coordinates
5. The user monitors the delivery in real time until completion

**Alternative Flows:**
- **Invalid order ID format** — the system returns a `400 Bad Request` with a validation error message
- **Order not found** — the system returns a `404 Not Found` with a descriptive error message

**Postconditions:** The user is informed about the delivery status and can follow the driver's position on the map.

## Architecture

The application follows the **MVC (Model-View-Controller)** pattern using **Java Spring Boot**:

```
com.tracking.ubookit
├── config/          # CORS and web configuration
├── constant/        # Application-wide constants
├── controller/      # REST controller (handles HTTP requests)
├── dto/             # Data Transfer Objects (API response shapes)
├── exception/       # Custom exceptions and global error handler
├── model/           # JPA entity (database model)
├── repository/      # Spring Data JPA repository (data access)
└── service/         # Business logic layer
```

| MVC Layer      | Component                | Role                                                    |
|----------------|--------------------------|---------------------------------------------------------|
| **Model**      | `OrderDao` (JPA entity)  | Maps to the `orders_tracking` MySQL table               |
| **View**       | REST API (JSON)          | Consumed by the frontend React application              |
| **Controller** | `TrackingController`     | Receives HTTP requests, delegates to the service layer  |
| **Service**    | `TrackingService`        | Contains business logic, transforms entities into DTOs  |

Error handling is centralized in `GlobalExceptionHandler`, which catches custom exceptions (`InvalidOrderIdException`, `OrderNotFoundException`) and Spring's `NoResourceFoundException`, returning consistent JSON error responses with appropriate HTTP status codes.

## Technology Stack

| Technology            | Purpose                    |
|-----------------------|----------------------------|
| Java 21               | Language                   |
| Spring Boot 4.0.3     | MVC Framework              |
| Spring Data JPA       | Data access (Hibernate ORM)|
| MySQL                 | Database                   |
| Maven                 | Build automation           |
| Lombok                | Boilerplate reduction      |
| Jackson               | JSON serialization         |

## Setup & Running the Application

### Prerequisites

- **Java 21** (JDK)
- **Maven 3.9+**
- **MySQL** database (local or remote)

### Database Configuration

Database credentials are **not** committed to the repository. They must be provided in one of two ways:

**Option A — Environment variables:**

```bash
export DB_URL=jdbc:mysql://localhost:3306/ubookit
export DB_USERNAME=root
export DB_PASSWORD=yourpassword
```

**Option B — Local properties file:**

Create `ubookit/src/main/resources/application-local.properties` (this file is gitignored) with:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/ubookit
spring.datasource.username=root
spring.datasource.password=yourpassword
```

### Build & Run

```bash
cd ubookit
./mvnw clean package
./mvnw spring-boot:run
```

The backend starts at **http://localhost:8080**.

On startup, the database schema is auto-managed by Hibernate (`ddl-auto=update`) and sample data is loaded automatically via `data.sql`.

## API Endpoints

| Method | Endpoint                     | Description                        |
|--------|------------------------------|------------------------------------|
| GET    | `/api/orders`                | Retrieve all orders (summary list) |
| GET    | `/api/orders/{id}/tracking`  | Get tracking details for an order  |

## Frontend

The frontend is a separate React application that consumes this REST API. It displays the order list and renders the tracking map with the driver position and delivery route.

- **Repository:** [UBookIt (Frontend)](https://github.com/anna-megalou/UBookIt)
- **Runs at:** `http://localhost:3000/tracking/`

CORS is configured in `WebConfig` to allow cross-origin requests from any origin to `/api/**`.

## Step-by-Step Usage Scenario

This scenario walks through the complete use case using the frontend and backend together.

### 1. Start the Backend

```bash
cd ubookit
./mvnw spring-boot:run
```

Wait until the console prints that the application has started on port 8080.

### 2. Start the Frontend

Follow the setup instructions in the [UBookIt frontend repository](https://github.com/anna-megalou/UBookIt). The frontend runs at `http://localhost:3000/tracking/`.

```bash
npm install
npm run dev
```

### 3. View All Orders

Open **http://localhost:3000/tracking/** in a browser. The page loads and displays a list of all orders:

| Order ID | Status      | Store               | City   | Price |
|----------|-------------|----------------------|--------|-------|
| U44653   | Processing  | Politeia Bookstore   | Athens | 5.00  |
| U44654   | Delivering  | Public Syntagma      | Athens | 5.00  |
| U44655   | Completed   | Ianos Bookstore      | Athens | 5.00  |
| U44656   | Completed   | Papasotiriou Books   | Athens | 5.00  |

### 4. Track a Delivering Order

Click on order **U44654** (status: Delivering). The tracking page shows:
- **Pickup location:** Public Syntagma (marked on the map)
- **Destination:** Office (marked on the map)
- **Driver position:** A marker between the two points on the map
- **Route:** A line drawn on the map from pickup to destination
- **ETA:** 12 minutes
- **Progress:** 55%

### 5. Track a Processing Order

Click on order **U44653** (status: Processing). The tracking page shows:
- **Pickup location:** Politeia Bookstore (marked on the map)
- **No driver movement** — the driver has not departed yet
- **ETA:** 0 minutes, **Progress:** 0%

### 6. Track a Completed Order

Click on order **U44655** (status: Completed). The tracking page shows:
- The full delivery route on the map
- The driver marker at the destination (delivery completed)
- **Progress:** 100%

### 7. Error Handling — Invalid Order ID

If the frontend or a direct API call uses an invalid order ID format (e.g. `INVALID` instead of `U` followed by digits):

```bash
curl http://localhost:8080/api/orders/INVALID/tracking
```

Response (HTTP `400`):

```json
{
  "message": "ERROR",
  "code": 1,
  "description": "Invalid order ID format: INVALID. Expected format: U followed by digits (e.g. U44653)"
}
```

### 8. Error Handling — Non-Existent Order

If the order ID has the correct format but does not exist:

```bash
curl http://localhost:8080/api/orders/U99999/tracking
```

Response (HTTP `404`):

```json
{
  "message": "ERROR",
  "code": 1,
  "description": "Order not found: U99999"
}
```

## Important Notes

- The order ID must follow the pattern `U` followed by digits (e.g. `U44653`). Any other format is rejected with a `400` error.
- The `route` field is stored as a JSON text column in MySQL and is deserialized into coordinate objects at runtime by Jackson.
- Database credentials are **never** committed to the repository — provide them via environment variables or the `application-local.properties` file as described in the Setup section.
- The backend and frontend run on different ports (8080 and 3000), so CORS is configured to allow cross-origin requests.
