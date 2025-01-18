# DormMate Backend

This repository contains the backend code for the DormMate web application, a dormitory management system designed to streamline communication and transactions between dorm owners and tenants.

## About

The DormMate backend is a RESTful API built with Spring Boot and Java. It provides the core business logic, handles database interactions, and exposes endpoints for the frontend to consume.

This backend is designed to be decoupled from the frontend, allowing for a flexible and scalable application architecture.

## Getting Started

### Prerequisites

*   [Java Development Kit (JDK)](https://www.oracle.com/java/technologies/downloads/) (version 11 or later)
*   [Apache Maven](https://maven.apache.org/download.cgi) (version 3.6.0 or later)
*   [MySQL Server](https://dev.mysql.com/downloads/mysql/)

### Installation

1.  **Clone the repository:**
    ```bash
    git clone [your-repository-url]
    cd dormmate-backend
    ```

2.  **Set up your database:**
    *   Create a new database in MySQL (e.g., named `dormmate`).
    *   Create a new user with full permissions to the new database.
    *  Update the `application.properties` in the `src/main/resources` with the new credentials and database details.
3.  **Build the project using maven:**

    ```bash
    mvn clean install
    ```

### Running the Application

1.  **Start the application:**

    ```bash
    mvn spring-boot:run
    ```

2.  The application will start and run on `http://localhost:8080` (or the port specified in `application.properties`).

### Technology Stack

*   **Java:** The core programming language for the backend.
*   **Spring Boot:** The framework used for building the application, providing auto-configuration, dependency injection, and an embedded Tomcat server.
    *   **Spring MVC:** Handles HTTP requests and responses (controllers).
    *   **Spring Data JPA:** Simplifies database interactions through repositories.
    *   **Spring Security:** Manages user authentication and password hashing.
*   **MySQL:** A relational database for storing application data.
*   **Maven:** Used for managing dependencies, building the application, and creating a JAR file.
*   **JUnit 5 and Mockito:** For writing unit tests.
*    **Jackson:** For converting java objects to JSON and vice-versa
*   **BCrypt:** for password hashing in the `User` Entity

### API Endpoints

The DormMate backend exposes a RESTful API with the following base endpoints:

*   `/api/users`: Manages user-related information.
*   `/api/units`: Manages dormitory units.
*   `/api/tenants`: Manages tenant information.
*   `/api/payments`: Manages payment records.
*   `/api/announcements`: Manages announcements.
*   `/api/maintenance-requests`: Manages maintenance requests.
*    `/login`:  Handles the authentication for both admin and user.

You can review the controller classes in the source code (under the `src/main/java/com/dormmatev2/dormmatev2/controller` folder) for the details of the endpoints that you have created for this application.


### Known Issues

*   Authentication and Authorization are not fully implemented in the application
*   The application currently does not have comprehensive test coverage.

### Roadmap (Future Enhancements)

*   Implement full authentication and authorization with Spring Security.
*   Add robust data validation on all endpoints.
*   Improve error handling and exception management.
*   Implement better testing.
*   Implement more robust searching, filtering and pagination.
*   Add the payment submission and email notifications for reminders.

## Contributing

If you would like to contribute to this project, please submit a pull request with the necessary code changes.

## License

[Your License]