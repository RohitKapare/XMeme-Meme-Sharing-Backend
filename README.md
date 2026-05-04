# XMeme: Meme Sharing Backend

A RESTful backend web application where users can post, browse, update, and delete memes. Built with **Spring Boot**, backed by **MongoDB**, and fully documented with **Swagger/OpenAPI**.

---

## What the Project Does

XMeme is a simple backend service that lets users:

- **Post** a meme (with a name, image URL, and caption).
- **Browse** the 100 newest memes.
- **Fetch** a single meme by its unique ID.
- **Update** a meme's URL and/or caption.
- **Delete** a meme.

All data is persisted in **MongoDB** and exposed through a clean REST API following MVCS layered architecture.

---

## Key Features

- **POST** a meme: creates a new entry and returns its unique ID.
- **GET** latest 100 memes: sorted by creation date (newest first).
- **GET** a single meme by ID.
- **PATCH** a meme: update URL and/or caption (name is immutable).
- **DELETE** a meme by ID.
- Duplicate detection: returns `409 Conflict` if the same name + URL + caption already exists.
- Input validation with appropriate HTTP response codes (`400`, `404`, `409`).
- Interactive API docs via **Swagger UI** at `/swagger-ui.html`.

---

## Tech Stack

| Layer | Technology |
|---|---|
| Language | Java 11 |
| Framework | Spring Boot 2.7.1 |
| Database | MongoDB 6.0 |
| Data Access | Spring Data MongoDB |
| API Docs | SpringDoc OpenAPI (Swagger UI) |
| Build Tool | Gradle |
| Containerisation | Docker |
| Code Quality | Checkstyle, SpotBugs, JaCoCo |
| Utilities | Lombok, ModelMapper |

---

## Getting Started

### Prerequisites

- **Java 11** (JDK)
- **MongoDB** running on `localhost:27017`  
  *(or use the Docker option below — no local MongoDB needed)*
- **Gradle** wrapper included (`./gradlew`)

### Running Locally

1. **Clone the repository**

   ```bash
   git clone <repository-url>
   cd rakapare86-ME_BUILDOUT_XMEME_JAVA-master
   ```

2. **Start MongoDB** (if not already running)

   ```bash
   mongod --dbpath /data/db
   ```

3. **Build and run the application**

   ```bash
   # On Linux/macOS
   ./gradlew bootRun

   # On Windows
   gradlew.bat bootRun
   ```

4. The server starts on **port 8081** by default.  
   Open the Swagger UI at: [http://localhost:8081/swagger-ui.html](http://localhost:8081/swagger-ui.html)

5. *(Optional)* Load sample meme data:

   ```bash
   cd sample-data
   ./load-sample-data.sh
   ```

### Running with Docker

The provided `Dockerfile` builds an all-in-one image that includes MongoDB and the application.

```bash
# Build the image
docker build -t xmeme-app .

# Run the container
docker run -p 8081:8081 xmeme-app
```

The application will be available at [http://localhost:8081](http://localhost:8081).

---

## API Overview

Base URL: `http://localhost:8081`

| Method | Endpoint | Description | Success Code |
|--------|----------|-------------|--------------|
| `POST` | `/memes` | Create a new meme | `201 Created` |
| `GET` | `/memes` | Fetch latest 100 memes | `200 OK` |
| `GET` | `/memes/{id}` | Fetch a meme by ID | `200 OK` |
| `PATCH` | `/memes/{id}` | Update URL and/or caption | `200 OK` |
| `DELETE` | `/memes/{id}` | Delete a meme | `204 No Content` |

### Example: Create a Meme

**Request**
```http
POST /memes
Content-Type: application/json

{
  "name": "Alice",
  "url": "https://example.com/funny-meme.jpg",
  "caption": "When the build finally passes"
}
```

**Response** `201 Created`
```json
{
  "id": "64a1f3c2e4b0a23d5f789012"
}
```

> Full interactive documentation is available via Swagger UI at `/swagger-ui.html` once the app is running.

---

## Project Structure

```
src/main/java/com/crio/starter/
├── App.java                        # Application entry point
├── config/                         # Spring & OpenAPI configuration
├── controller/                     # REST controllers (MemeController)
├── entity/                         # MongoDB document models (Meme)
├── exception/                      # Custom exceptions & global handler
├── exchange/                       # Request/Response DTOs
├── repository/                     # Spring Data MongoDB repositories
└── service/                        # Business logic layer (MemeService)
```

---

## Configuration

Application configuration is in `src/main/resources/application.properties`:

```properties
# MongoDB connection URI
spring.data.mongodb.uri=mongodb://localhost:27017/greetings?authSource=admin

# Server port
server.port=8081
```

Override these values with environment variables or a custom `application.properties` for different environments.

---

## Testing

Run the test suite with:

```bash
# On Linux/macOS
./gradlew test

# On Windows
gradlew.bat test
```

The HTML report is generated at `build/reports/jacoco/test/html/index.html`.

---

