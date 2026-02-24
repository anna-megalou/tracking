# UBookIt - Textbook Delivery Tracking

## Project Description

In today's digital age, the Greek academic textbook distribution system **"Eudoxus"** plays a
vital role in organizing the declaration and pickup of university textbooks. However, the system
has a gap in the so-called **"last mile"** of the process — the stage where textbooks are
delivered to the final recipient.

Currently, after declaring their textbooks through Eudoxus, students must pick them up in person
from partnered distribution points (usually bookstores). This model creates practical problems
for many students: those who work alongside their studies struggle with bookstore hours, those
living outside the city face increased costs, and those in Athens without personal transport must
make multiple trips through traffic.

**UBookIt** addresses this problem by extending the Eudoxus workflow with a delivery tracking
service. After declaring their textbooks, students can choose to have them delivered to their
home or a nearby locker — bundled together — eliminating unnecessary trips and hassle.

> *"I receive my textbooks at home or at a locker near me, without running around bookstores
> and carrying heavy loads."*

This application is built as part of the group project for the **"Information Systems Development
and Architecture"** course at Athens University of Economics and Business.

## Use Case: Track an Order

From the group project's use case diagram, the system includes four use cases:
*See list of books*, *Select books for delivery*, *Pay for order / Complete order*, and
*Track an Order*. This application implements the **"Track an Order"** use case.

### Actors
- **User (Student)**: A signed-in student who wants to track the delivery of their textbook order

### Preconditions
- The user is signed in (<<includes>> Sign In)
- The user has at least one order that has been placed and paid for

### Main Flow
1. The user signs in to the system
2. The system displays a list of all the user's orders with summary info (order ID, status, bookstore, city, price)
3. The user selects a specific order to track
4. The system returns detailed tracking information: pickup bookstore location, delivery destination, driver's current position on the map, ETA, delivery progress percentage, and the route as map coordinates
5. The user can monitor the delivery in real time until it is completed

### Alternative Flows
- **Order not found**: If the user enters an invalid or non-existent order ID, the system returns an appropriate error message
- **Invalid order ID format**: If the order ID does not match the expected format, the system rejects the request with a validation error

### Postconditions
- The user is informed about the current delivery status and can follow the driver's position on the map

## Architecture

The application follows the **MVC (Model-View-Controller)** pattern using Java Spring Boot:

```
com.tracking.ubookit
├── config/          # CORS and web configuration
├── constant/        # Application-wide constants
├── controller/      # REST controllers (handle HTTP requests)
├── dto/             # Data Transfer Objects (API request/response shapes)
├── exception/       # Custom exceptions and global error handler
├── model/           # JPA entities (database models)
├── repository/      # Spring Data JPA repositories (data access)
└── service/         # Business logic layer
```

- **Model**: `OrderDao` (JPA entity) mapped to the `orders_tracking` MySQL table
- **View**: REST API returning JSON responses (consumed by the frontend)
- **Controller**: `TrackingController` handles incoming HTTP requests and delegates to the service
- **Service**: `TrackingService` contains the business logic and data transformation

## Technology Stack

- **Java 21**
- **Spring Boot 4.0.3**
- **Spring Data JPA** with Hibernate ORM
- **MySQL** database
- **Maven** for build automation
- **Lombok** to reduce boilerplate code
- **Jackson** for JSON serialization

## Setup & Running the Application

### Prerequisites

- Java 21 (JDK)
- Maven 3.9+
- Access to a MySQL database (or use the provided remote database)

### Configuration

Database credentials are configured via environment variables. Copy the example properties
and set the values:

```bash
export DB_URL=jdbc:mysql://localhost:3306/ubookit
export DB_USERNAME=root
export DB_PASSWORD=yourpassword
```

Alternatively, you can create a local `application-local.properties` file in
`src/main/resources/` and override the values there (this file is gitignored).

### Build & Run

```bash
cd ubookit

# Build the project
./mvnw clean package

# Run the application
./mvnw spring-boot:run
```

The application will start on `http://localhost:8080`.

### Database Initialization

On startup, the application automatically initializes the database with sample order data
via `data.sql`. The schema is auto-managed by Hibernate (`ddl-auto=update`).

## Usage Scenario (Step-by-Step)

1. **Start the application** using `./mvnw spring-boot:run`
2. **View all orders**: Send a GET request to `http://localhost:8080/api/orders`
   - You will receive a JSON list of all orders with their ID, status, store, city, and price
3. **Track a specific order**: Send a GET request to `http://localhost:8080/api/orders/U44654/tracking`
   - Replace `U44654` with any valid order ID from step 2
   - You will receive detailed tracking data: pickup/destination locations, driver coordinates, ETA, delivery progress, and the route as a list of coordinates
4. **Try an invalid order ID**: Send a GET request to `http://localhost:8080/api/orders/INVALID/tracking`
   - The API returns a `400 Bad Request` with a descriptive error message
5. **Try a non-existent order**: Send a GET request to `http://localhost:8080/api/orders/U99999/tracking`
   - The API returns a `404 Not Found` response

### Sample Orders

| Order ID | Status      | Store               | City   |
|----------|-------------|----------------------|--------|
| U44653   | Processing  | Politeia Bookstore   | Athens |
| U44654   | Delivering  | Public Syntagma      | Athens |
| U44655   | Completed   | Ianos Bookstore      | Athens |
| U44656   | Completed   | Papasotiriou Books   | Athens |

## Important Notes

- The order ID format must match the pattern `U` followed by digits (e.g. `U44653`). Invalid formats are rejected with a `400` error.
- The `route` field stores coordinate paths as JSON text in the database and is deserialized at runtime.
- Database credentials are **not** included in the repository. They must be provided via environment variables or a separate properties file (see Setup section).

## API Endpoints

| Method | Endpoint                     | Description                        |
|--------|------------------------------|------------------------------------|
| GET    | `/api/orders`                | Retrieve all orders (summary)      |
| GET    | `/api/orders/{id}/tracking`  | Get tracking details for an order  |

## Frontend

The frontend is a separate component that consumes the REST API. It displays the order list
and renders the tracking details (including a map with the driver position and route).
The CORS configuration in `WebConfig` allows cross-origin requests from any origin to
support the decoupled frontend-backend architecture.
