# DineRate Backend

Backend service for a restaurant discovery and rating platform built with **Spring Boot** and **Elasticsearch**.
The system provides REST APIs for restaurant management, search, ratings, and photo uploads.

---

## Overview

DineRate allows users to discover restaurants based on cuisine, rating, and location.
The backend integrates **Elasticsearch** to support fast full-text search and geo-based queries.

---

## Features

* Restaurant management (create and update restaurant data)
* Full-text search using Elasticsearch
* Geolocation search for nearby restaurants
* Restaurant ratings
* Photo upload support
* Pagination for search results
* JWT based authentication using **OAuth2 Resource Server**

---

## Tech Stack

**Language**

Java 21

**Framework**

Spring Boot
Spring Security
Spring Data Elasticsearch

**Tools**

Elasticsearch
Docker
Gradle
MapStruct
Lombok

---

## Requirements

Before running the project make sure the following services are available:

* Java 21
* Elasticsearch 8.x running on `http://localhost:9200`
* Keycloak (for OAuth2 authentication) running on `http://localhost:9090`
* Gradle 8.x (or Gradle wrapper)

---

## Running the Application

Clone the repository

```bash
git clone <repository-url>
cd dinerate_backend
```

Start the application

```bash
./gradlew bootRun
```

The application will start at:

```
http://localhost:8080
```

---

## API Base URL

```
http://localhost:8080/api
```

---

## Example API Endpoints

### Create Restaurant

```
POST /api/restaurants
```

Example request:

```json
{
  "name": "Spice Paradise",
  "cuisineType": "Indian",
  "contactInfo": "+91-9876543210"
}
```

---

### Search Restaurants

```
GET /api/restaurants
```

Supported filters:

* `q` – search by restaurant name or cuisine
* `minRating` – minimum rating
* `latitude` / `longitude` – user location
* `radius` – search radius
* `page` – page number
* `size` – number of results

Example:

```
GET /api/restaurants?q=Indian&minRating=4.0
```

---

### Upload Photo

```
POST /api/photos/upload
```

Request type:

```
multipart/form-data
```

---

## Project Structure

```
src/main/java/com/dinerate

config/        -> Security and Elasticsearch configuration
controllers/   -> REST controllers
domains/       -> Entities and DTOs
repositories/  -> Elasticsearch repositories
services/      -> Business logic
mappers/       -> MapStruct mappers
exceptions/    -> Custom exception handling
```

---

## Docker

Start Elasticsearch using Docker:

```bash
docker-compose up -d
```

Stop services:

```bash
docker-compose down
```

---

## Author

Monu Dhakad
