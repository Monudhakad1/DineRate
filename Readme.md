# DineRate Backend

A Spring Boot backend application for restaurant discovery and rating system with Elasticsearch integration for advanced search capabilities.

## 📋 Table of Contents

- [Overview](#overview)
- [Features](#features)
- [Tech Stack](#tech-stack)
- [Prerequisites](#prerequisites)
- [Installation](#installation)
- [Configuration](#configuration)
- [Running the Application](#running-the-application)
- [API Documentation](#api-documentation)
- [Project Structure](#project-structure)
- [Testing](#testing)
- [Docker Support](#docker-support)
- [Contributing](#contributing)

## 🎯 Overview

DineRate is a restaurant management and discovery platform that allows users to search for restaurants based on location, cuisine type, ratings, and other criteria. The backend provides RESTful APIs with Elasticsearch-powered search capabilities for fast and efficient restaurant discovery.

## ✨ Features

- **Restaurant Management**: Create, update, and manage restaurant information
- **Advanced Search**: Search restaurants by name, cuisine type, location, and ratings
- **Geolocation Search**: Find nearby restaurants based on latitude/longitude coordinates
- **Photo Upload**: Support for restaurant photo management
- **Rating System**: Track and display restaurant ratings
- **OAuth2 Security**: Secured endpoints with JWT authentication via Keycloak
- **Elasticsearch Integration**: Fast full-text search and filtering capabilities
- **Paginated Results**: Efficient data retrieval with pagination support

## 🛠️ Tech Stack

- **Java 21** - Programming language
- **Spring Boot 3.2.5** - Application framework
- **Spring Data Elasticsearch** - Elasticsearch integration
- **Spring Security** - Authentication and authorization
- **OAuth2 Resource Server** - JWT token validation
- **Elasticsearch** - Search engine and data store
- **Lombok** - Reduce boilerplate code
- **MapStruct** - Object mapping
- **Gradle** - Build tool
- **Docker** - Containerization

## 📦 Prerequisites

Before running this application, ensure you have the following installed:

- **Java 21** or higher
- **Elasticsearch 8.x** running on `http://localhost:9200`
- **Keycloak** (for OAuth2 authentication) running on `http://localhost:9090`
- **Gradle 8.x** (or use the included Gradle wrapper)

## 🚀 Installation

1. **Clone the repository**
   ```bash
   git clone <repository-url>
   cd dineRate_backened
   ```

2. **Make sure Elasticsearch is running**
   ```bash
   # Check if Elasticsearch is accessible
   curl http://localhost:9200
   ```

3. **Configure Keycloak** (if using OAuth2)
   - Create a realm named `DineRate`
   - Configure OAuth2 client settings
   - Update `application.properties` with correct issuer URI

## ⚙️ Configuration

The main configuration file is located at `src/main/resources/application.properties`:

```properties
# Application Name
spring.application.name=dineRate_backened

# Elasticsearch Configuration
spring.elasticsearch.uris=http://localhost:9200
spring.elasticsearch.rest.compatibility-mode=true
spring.data.elasticsearch.repositories.create-index=false

# OAuth2 Resource Server (Keycloak)
spring.security.oauth2.resourceserver.jwt.issuer-uri=http://localhost:9090/realms/DineRate

# File Upload Configuration
spring.servlet.multipart.max-file-size=20MB
spring.servlet.multipart.max-request-size=20MB

# Logging
logging.level.org.springframework.data.elasticsearch=DEBUG
```

### Environment-Specific Configuration

You can override properties using environment variables or create profile-specific configuration files:
- `application-dev.properties`
- `application-prod.properties`

## 🏃 Running the Application

### Using Gradle Wrapper (Recommended)

**Windows:**
```bash
gradlew.bat bootRun
```

**Linux/Mac:**
```bash
./gradlew bootRun
```

### Using Gradle

```bash
gradle bootRun
```

### Building JAR

```bash
./gradlew bootJar
```

The JAR file will be created at `build/libs/dinerate-backend.jar`

Run the JAR:
```bash
java -jar build/libs/dinerate-backend.jar
```

The application will start on **http://localhost:8080**

## 📚 API Documentation

### Base URL
```
http://localhost:8080/api
```

### Endpoints

#### 1. Create Restaurant
```http
POST /api/restaurants
Content-Type: application/json

{
  "name": "Spice Paradise",
  "cuisineType": "Indian",
  "contactInfo": "+91-9876543210",
  "address": {
    "houseNumber": "45",
    "street": "MG Road",
    "city": "Mumbai",
    "state": "Maharashtra",
    "zipCode": "400001",
    "country": "India"
  },
  "operatingHours": {
    "monday": {
      "openTime": "11:00",
      "closeTime": "23:00"
    },
    "tuesday": {
      "openTime": "11:00",
      "closeTime": "23:00"
    }
  },
  "photoIds": ["photo-id-123"]
}
```

#### 2. Search Restaurants
```http
GET /api/restaurants?q=Italian&minRating=4.0&latitude=19.0760&longitude=72.8777&radius=5&page=1&size=10
```

**Query Parameters:**
- `q` (optional) - Search query (restaurant name or cuisine type)
- `minRating` (optional) - Minimum rating filter (e.g., 4.0)
- `latitude` (optional) - User's latitude for geo-based search
- `longitude` (optional) - User's longitude for geo-based search
- `radius` (optional) - Search radius in kilometers
- `page` (optional) - Page number (default: 1)
- `size` (optional) - Results per page (default: 20)

**Example Searches:**

1. **Find all restaurants:**
   ```
   GET /api/restaurants
   ```

2. **Search by cuisine:**
   ```
   GET /api/restaurants?q=Italian
   ```

3. **Search by rating:**
   ```
   GET /api/restaurants?minRating=4.5
   ```

4. **Search near location:**
   ```
   GET /api/restaurants?latitude=19.0760&longitude=72.8777&radius=5
   ```

5. **Combined search:**
   ```
   GET /api/restaurants?q=Indian&minRating=4.0&latitude=19.0760&longitude=72.8777&radius=10
   ```

#### 3. Upload Photo
```http
POST /api/photos/upload
Content-Type: multipart/form-data

Form Data:
- file: [image file]
```

**Response:**
```json
{
  "url": "photo-id-123",
  "uploadedTime": "2026-02-18T10:30:00"
}
```

## 📁 Project Structure

```
dineRate_backened/
├── src/
│   ├── main/
│   │   ├── java/com/dinerate/elastic/dinerate_backened/
│   │   │   ├── config/              # Configuration classes
│   │   │   │   ├── ElasticSearchConfig.java
│   │   │   │   └── SecurityConfig.java
│   │   │   ├── controllers/         # REST Controllers
│   │   │   │   ├── RestaurantController.java
│   │   │   │   └── PhotoController.java
│   │   │   ├── domains/             # Domain models
│   │   │   │   ├── entities/        # Elasticsearch entities
│   │   │   │   │   ├── Restaurants.java
│   │   │   │   │   ├── Address.java
│   │   │   │   │   ├── OperatingHours.java
│   │   │   │   │   └── Photo.java
│   │   │   │   └── dtos/            # Data Transfer Objects
│   │   │   │       ├── RestaurantCreateUpdateRequestDTO.java
│   │   │   │       └── RestaurantsDTO.java
│   │   │   ├── repositories/        # Elasticsearch repositories
│   │   │   │   └── RestaurantRepository.java
│   │   │   ├── services/            # Business logic
│   │   │   │   ├── RestaurantService.java
│   │   │   │   ├── PhotoService.java
│   │   │   │   └── impl/
│   │   │   ├── Mappers/             # MapStruct mappers
│   │   │   │   └── RestaurantsMapper.java
│   │   │   └── exceptions/          # Custom exceptions
│   │   └── resources/
│   │       ├── application.properties
│   │       └── static/
│   └── test/
│       ├── java/                    # Test classes
│       └── resources/
│           └── testdata/            # Test data files
├── uploads/                         # Uploaded photos directory
├── build.gradle                     # Gradle build configuration
├── docker-compose.yaml              # Docker compose file
└── README.md                        # This file
```

## 🧪 Testing

### Run All Tests
```bash
./gradlew test
```

### Run Specific Test
```bash
./gradlew test --tests RestaurantDataLoaderTest
```

### Load Sample Data
The project includes a test class to load sample restaurant data:

```bash
./gradlew test --tests RestaurantDataLoaderTest.createSampleRestaurants
```

This will create 10 sample restaurants in your Elasticsearch index with:
- Various Indian cuisines
- Different locations across India
- Sample photos
- Operating hours

## 🐳 Docker Support

### Using Docker Compose

1. **Start Elasticsearch:**
   ```bash
   docker-compose up -d
   ```

2. **Stop Services:**
   ```bash
   docker-compose down
   ```

### Docker Compose Configuration

The `docker-compose.yaml` file includes configuration for:
- Elasticsearch
- Kibana (for Elasticsearch UI)
- (Optional) Keycloak

## 🔧 Troubleshooting

### Common Issues

1. **Elasticsearch Connection Failed**
   - Ensure Elasticsearch is running: `curl http://localhost:9200`
   - Check `application.properties` for correct URI
   - Verify Elasticsearch compatibility mode is enabled

2. **OAuth2 Authentication Errors**
   - Verify Keycloak is running on the configured port
   - Check realm name matches configuration
   - Ensure JWT issuer URI is correct

3. **File Upload Errors**
   - Check `uploads/` directory exists and has write permissions
   - Verify file size limits in `application.properties`

4. **No property 'location' found**
   - This is fixed by using custom `@Query` annotation in repository
   - Ensure geo-location queries use correct field paths

    
## 👥 Authors

- Your Name - Monu Dhakad


**Made with ❤️ using Spring Boot and Elasticsearch**

